package come.tool.Scene.SLDH;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.chat.FriendChatAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.action.sys.ChangeMapAction;
import org.come.bean.AllNpcBean;
import org.come.bean.ChangeMapBean;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.NpcInfoBean;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.readUtil.ReadPoolUtil;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.tool.NewAESUtil;
import org.come.tool.ReadExelTool;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;
import org.come.until.MessageGZIP;
import org.come.until.ReadTxtUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.Battlefield;
import come.tool.Role.RoleShow;
import come.tool.Scene.Scene;
import come.tool.Stall.AssetUpdate;
import come.tool.Title.TitleUtil;

public class SLDHScene implements Scene{
	/**5分钟准备*/
	public static long JG=1000*60*5;
	
	public ChangeMapBean INTO;//水陆大会进场点
    private int I;//0进场报名阶段   1淘汰阶段   2四强阶段   3名次确定阶段   4活动结束
	private int JS;//第N届第M轮水路大会
	private int CI;
	private RoleShow[] lastShows;//上届的水陆大会·战神
	private ConcurrentHashMap<BigDecimal, SLDHRole> roleMap;//玩家数据
	private List<SLDHTeam> teams;//剩余队伍
	private List<SLDHGroup> groups;//分组情况
	private int teamNum;//分组的队伍数量
	private int teamTotal;//参与队伍总数
	private int groupNum;//分组的数量
	private int endNum;//战斗结束的队伍数
	private int ppNum;//匹配次数
    private StringBuffer SLbuffer;//水陆匹配日志打印
	public SLDHScene() {
		// TODO Auto-generated constructor stub
		I = 4;
		roleMap= new ConcurrentHashMap<>();
		teams = new ArrayList<>();
		groups = new ArrayList<>();
		INTO=new ChangeMapBean("3320",1328,1026);
		String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "sldh.txt");
		if (text==null||text.equals("")) {
			JS=1;CI=1;
		}else {
			SLDHBean sldhBean=GsonUtil.getGsonUtil().getgson().fromJson(text,SLDHBean.class);
			JS=sldhBean.getJS();
			CI=sldhBean.getCI();
			lastShows=sldhBean.getLastShows();
		}
		//刷新水陆大会排行榜
		GameServer.allBangList.put(4,AllServiceUtil.getRoleTableService().selectSLDH());
		loadStatue();
	}
	/**玩家报名*/
	public synchronized String addEnroll(ChannelHandlerContext ctxRole,LoginResult loginResult){
		if (I!=0) {return "进场通道已关闭";}
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return "你不是队长";}
		if (loginResult.getGold().longValue()<3000000) {
			return "需缴纳300W入场费";
		}
		if (teams.length!=5) {return "人数不足5人";}
		if (loginResult.getGrade()<70) {return loginResult.getRolename()+"未满70级";}
		SLDHRole cRole=getRole(loginResult.getRole_id());
		SLDHRole[] roles=null;
		if (cRole==null) {
			roles=new SLDHRole[teams.length];
			roles[0]=new SLDHRole(loginResult.getRole_id(),loginResult.getRoleShow());
		}
		for (int i = 1; i < teams.length; i++) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
			LoginResult login=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (login==null) {return teams[i]+"处于异常状态";}
			if (login.getGrade()<70) {return teams[i]+"未满70级";}
			SLDHRole role=getRole(login.getRole_id());
			if (roles==null) {
				if (role==null||cRole.getSldhTeam()!=role.getSldhTeam()) {
					return "你已经报名了水路大会且"+teams[i]+"不是你的队友";
				}
			}else {
				if (role!=null) {
					return teams[i]+"已和其他玩家参与本次水路大会报名";
				}
				roles[i]=new SLDHRole(login.getRole_id(),login.getRoleShow());
			}
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(new BigDecimal(loginResult.getGold().longValue()-3000000));
		assetUpdate.updata("D=-3000000");
		assetUpdate.upmsg("扣除300W金钱");
		SendMessage.sendMessageToSlef(ctxRole,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
		if (roles!=null) {//生成队伍
			SLDHTeam team=new SLDHTeam(roles);
			this.teams.add(team);
			teamTotal++;
			for (int i = 0; i < roles.length; i++) {
				addRole(roles[i]);
				AllServiceUtil.getPackRecordService().addSLDH(roles[i].getRoleID(), 200);
			}
		}
		ChangeMapAction.ChangeMap(INTO, ctxRole);
		return null;		
	}
	/**活动开启*/
	public void open(){
		if (I!=4) {
			WriteOut.addtxt("开启水陆大会时:水陆大会状态处于非关闭状态",9999);
			return;
		}
		if (CI==1) {
			AllServiceUtil.getPackRecordService().emptySLDH();
		}
		SLbuffer=new StringBuffer();//实例化日志
		roleMap.clear();teams.clear();groups.clear();
        teamNum=0;
		teamTotal=0;
		groupNum=0;
		endNum=0;
		ppNum=0;
		I=0;
		SLDHThread thread = new SLDHThread(this);
		Thread T1 = new Thread(thread);
		T1.start();
	}
	/**活动结束*/
	public void end(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("本次水陆大会比赛结束");
		if (groups.size()>=1) {
			SLDHGroup group=groups.get(0);
			SLDHTeam team=group.getTeam(true);
			if (team!=null) {
				buffer.append("#r#R冠军队伍:#G");
				buffer.append(team.getTeamMsg());
			}
			team=group.getTeam(false);
			if (team!=null) {
				buffer.append("#r#R亚军队伍:#G");
				buffer.append(team.getTeamMsg());
			}
			if (groups.size()>=2) {
				group=groups.get(1);
				team=group.getTeam(true);
				if (team!=null) {
					buffer.append("#r#R季军队伍:#G");
					buffer.append(team.getTeamMsg());
				}
			}
		}
		SLbuffer.append("\r\n比赛结果");
		SLbuffer.append(buffer.toString());
		WriteOut.writeTxtFile(SLbuffer.toString(), "sldh");
		SLbuffer=null;
		
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
		I=4;
		//结束刷新排行榜  刷新届数
		//刷新水陆大会排行榜
		List<LoginResult> results=AllServiceUtil.getRoleTableService().selectSLDH();
		GameServer.allBangList.put(4,results);
		CI++;
		if (CI>=5) {
			JS++;
			CI=1;
			if (results.size()==0) {
				lastShows=null;
			}else {
				lastShows=new RoleShow[results.size()<5?results.size():5];
				for (int i = 0; i < lastShows.length; i++) {
					LoginResult result=results.get(i);
					lastShows[i]=new RoleShow();
					lastShows[i].setRole_id(result.getRole_id());
					lastShows[i].setRolename(result.getRolename());
					lastShows[i].setSpecies_id(result.getSpecies_id());	
				}
			}
			loadStatue();
			SLDHBean sldhBean=new SLDHBean();
			sldhBean.setJS(JS);
			sldhBean.setCI(CI);
			sldhBean.setLastShows(lastShows);
			try {
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "sldh.txt", GsonUtil.getGsonUtil().getgson().toJson(sldhBean).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			   
		}
	}
	/**进行分组*/
	public void grouping(){
		if (I==1) {
			SLbuffer.append("\r\n");
			SLbuffer.append("第");
			SLbuffer.append(ppNum);
			SLbuffer.append("轮淘汰的队伍:");
			for (int i = 0,length = groups.size(); i < length; i++) {
				SLDHGroup group=groups.get(i);
				SLDHTeam team=group.getTeam(false);
				if (team!=null) {
					teams.remove(team);
					SLbuffer.append("\r\n状态:");
					SLbuffer.append(group.getState());
					SLbuffer.append(" ");
					SLbuffer.append(team.getTeamMsg());
				}
			}
		}
		ppNum++;
		teamNum=teams.size();//分组的队伍数量
		groupNum=0;//分组的数量
		endNum=0;//战斗结束的队伍数
		if (I==1) {//随机分组分组
			groups.clear();
			if (teams.size()<=2) {I=3;}
			else if (teams.size()<=4) {I=2;}
			List<Integer> list=new ArrayList<>();
			for (int i=0,length=teams.size();i<length; i++) {
				list.add(i);
			}
			while (list.size()!=0) {
				int size=list.size();
				int t1=list.remove(Battlefield.random.nextInt(size));
				SLDHTeam team1=teams.get(t1);
				SLDHTeam team2=null;
				size--;
				if (size!=0) {
					int t2=list.remove(Battlefield.random.nextInt(size));
					team2=teams.get(t2);	
				}
				SLDHGroup group=new SLDHGroup(team1, team2);
				groups.add(group);
			}
			groupNum=groups.size();
			PPAll();
			if (groupNum!=0) {
				SLDHBattleThread battleThread = new SLDHBattleThread(this);
				Thread T1 = new Thread(battleThread);
				T1.start();	
			}else {
				grouping();
			}	
		}else if (I==2) {//四强初选分组
			if (groups.size()<=1) {
				I=3;
				grouping();
				return;
			}
			I=3;
			SLDHGroup group1=groups.get(0);
			SLDHGroup group2=groups.get(1);
			groups.clear();
			SLDHGroup pgroup1=new SLDHGroup(group1.getTeam(true), group2.getTeam(true));
			SLDHGroup pgroup2=new SLDHGroup(group1.getTeam(false), group2.getTeam(false));
			groups.add(pgroup1);
			groups.add(pgroup2);
			groupNum=groups.size();
			PPAll();
			SLDHBattleThread battleThread = new SLDHBattleThread(this);
			Thread T1 = new Thread(battleThread);
			T1.start();	
		}else if (I==3) {
			end();
		}else {
			try {
				WriteOut.addtxt("水陆报错:"+I+":"+MainServerHandler.getErrorMessage(new Exception()), 9999);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	/**匹配信息通知*/
	public void PPAll(){
		SLbuffer.append("\r\n");
		SLbuffer.append("第");
		SLbuffer.append(ppNum);
		SLbuffer.append("轮匹配的性质");
		if (I==1) {SLbuffer.append("淘汰赛");}
		else if (I==2) {SLbuffer.append("四强初赛");}
		else if (I==3) {SLbuffer.append("四强终赛");}
		else {SLbuffer.append("未知性质:"+I);}
		SLbuffer.append("参与匹配的队伍数量");
		SLbuffer.append(teamNum);
		SLbuffer.append("分组数量");
		SLbuffer.append(groupNum);
		SLbuffer.append("匹配的队伍:");
		for (int i=0,length=groups.size();i<length;i++) {
			PPMsg(groups.get(i));
		}
	}
	/**匹配信息*/
	public void PPMsg(SLDHGroup group){
		SLDHTeam team1=group.getTeam1();
		SLDHTeam team2=group.getTeam2();
		StringBuffer buffer=new StringBuffer();
		if (team2==null) {
			buffer.append("队伍:");
			if (team1!=null) {
				buffer.append(team1.getTeamMsg());
				buffer.append(" ");			
			}
			buffer.append("本次轮空");
		}else {
			buffer.append("队伍:");
			buffer.append(team1.getTeamMsg());
			buffer.append(" -VS- ");
			buffer.append("队伍:");
			buffer.append(team2.getTeamMsg());
		}
		SLbuffer.append("\r\n");
		SLbuffer.append(buffer.toString());
		buffer.append("#r有匹配到的队伍请做好准备,3分钟后开始战斗,队伍中有玩家状态异常视为本轮比赛认输");
		TZ(buffer.toString(), team1, team2);
	}
	/**通知处理*/
	public void TZ(String msg,SLDHTeam team1,SLDHTeam team2){
		for (int i = 0; i < team1.getRoles().length; i++) {
			FriendChatAction.createChatBeanForServer(msg, team1.getRoles()[i].getRoleShow().getRolename());
		}
		if (team2!=null) {
			for (int i = 0; i < team2.getRoles().length; i++) {
				FriendChatAction.createChatBeanForServer(msg, team2.getRoles()[i].getRoleShow().getRolename());
			}
		}
	}
	/**结果处理    1:一队获胜 2:二队获胜*/
	public synchronized void groupEnd(SLDHGroup group,int i,String msg){
		if (!groups.contains(group)||group.getState()!=1||I==4) {
			SLbuffer.append("\r\n非正常分组调用战斗结束接口:");
			SLbuffer.append(i);
			SLbuffer.append(" ");
			SLbuffer.append(I);
			SLbuffer.append(" ");
			SLbuffer.append(group.getState());
			SLbuffer.append(" ");
			SLbuffer.append(group.getTeam1()!=null?group.getTeam1().getTeamMsg():"null");
			SLbuffer.append(" ");
			SLbuffer.append(group.getTeam2()!=null?group.getTeam2().getTeamMsg():"null");
			WriteOut.writeTxtFile(SLbuffer.toString(), "sldh");
			return;
		}
		boolean is=groups.size()<=4;
		SLbuffer.append("\r\n队伍:");
		SLbuffer.append(group.getTeam1()!=null?group.getTeam1().getTeamMsg():"null");
		SLbuffer.append(" ");
		SLbuffer.append(group.getTeam2()!=null?group.getTeam2().getTeamMsg():"null");
		SLbuffer.append(" 结果:");
		if (msg!=null) {
			group.setState(i+1);
			if (group.getTeam2()==null) {//轮空
				SLbuffer.append("一队因轮空获胜");
				getReward(group.getTeam1(), is?60:40, getRewardLvl());
				StringBuffer buffer=new StringBuffer();
				buffer.append(msg);
				endAppend(group,group.getTeam1(),buffer,true);
				TZ(buffer.toString(),group.getTeam1(), null);
			}else {
				SLbuffer.append(i==1?"一队因":"二队因");
				SLbuffer.append(msg);
				SLbuffer.append("获胜");
				SLDHTeam team1=(i==1?group.getTeam1():group.getTeam2());
				SLDHTeam team2=(i==1?group.getTeam2():group.getTeam1());
				getReward(team1, is?60:40, getRewardLvl());
				getReward(team2, is?30:20, getRewardLvl());
				StringBuffer buffer=new StringBuffer();
				buffer.append("对面队伍还未准备好,你们直接获得本次胜利。");
				endAppend(group,team1,buffer,true);
				TZ(buffer.toString(),team1, null);
				buffer.setLength(0);
				buffer.append(msg);
				endAppend(group,team2,buffer,false);
				TZ(buffer.toString(),team2, null);
			}
		}else {//战斗分析
			if (i!=1&&i!=2) {
				SLbuffer.append("战斗获胜异常:"+i);
				i=1;
				SLbuffer.append("默认一队获胜");
			}else {
				SLbuffer.append(i==1?"一队通过战斗获胜":"二队通过战斗获胜");
			}
			group.setState(i+1);
			SLDHTeam team1=(i==1?group.getTeam1():group.getTeam2());
			SLDHTeam team2=(i==1?group.getTeam2():group.getTeam1());
			getReward(team1, is?60:40, getRewardLvl());
			getReward(team2, is?30:20, getRewardLvl());
			StringBuffer buffer=new StringBuffer();
			buffer.append("你们队伍战胜了"+team2.getTeamMsg()+"。");
			endAppend(group,team1,buffer,true);
			TZ(buffer.toString(),team1, null);
			buffer.setLength(0);
			buffer.append("你们队伍惜败于"+team1.getTeamMsg()+"。");
			endAppend(group,team2,buffer,false);
			TZ(buffer.toString(),team2, null);
		}
		endNum++;
		if (endNum==groupNum) {grouping();}
	}
	/**添加后缀词*/
	public void endAppend(SLDHGroup group,SLDHTeam team,StringBuffer buffer,boolean is){
		buffer.append("#r");	
		if (I==3) {//结束
			buffer.append("本次水陆大会结束,你们队伍成绩");	
			//2:8强   3:季军   4:亚军   5:冠军	
			int v=2;
			if (is) {
				if (groups.size()>=1&&groups.get(0)==group) {v=5;}
				else if (groups.size()>=2&&groups.get(1)==group) {v=3;}
			}else {
				if (groups.size()>=1&&groups.get(0)==group) {v=4;}
			}
			team.setValue(v);
			buffer.append(v==5?"冠军":v==4?"亚军":v==3?"季军":"8强");	
		}else if (I==2) {
			if (is) {buffer.append("请等待下一轮匹配,角逐冠军和亚军名次");}
			else {buffer.append("请等待下一轮匹配,角逐季军名次");}
		}else if (is) {
			buffer.append("请等待下一轮匹配");
		}else {
			buffer.append("你们队伍本次水陆大会结束,可以退场或者留下来观战其他队伍比赛。");		
			if (groups.size()<=4) {buffer.append("你们队伍成绩8强");}
			else {buffer.append("你们队伍未取得名次");}
		}
	}
	/**获取排名奖励等级*/
	public int getRewardLvl(){
		if (groups.size()<=4) {
			return 2;
		}
		return 1;
	}
	/**获取奖励*/
	public void getReward(SLDHTeam team,int add,int value){
		if (value>team.getValue()) {team.setValue(value);}
		if (add!=0) {
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("水陆积分="+add);
			assetUpdate.setMsg("你获得"+add+"水陆积分");
			String msg=Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate));
			SLDHRole[] roles=team.getRoles();
			for (int i = 0; i < roles.length; i++) {
				SLDHRole role=roles[i];
				AllServiceUtil.getPackRecordService().addSLDH(role.getRoleID(), add);
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role.getRoleShow().getRolename());
				LoginResult roleInfo=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
				if (roleInfo!=null) {
					roleInfo.setScore(DrawnitemsAction.Splice(roleInfo.getScore(),"水陆积分="+add,2));
					SendMessage.sendMessageToSlef(ctx,msg);  	
				}
			}	
		}
		
	}
	/**对应分组开始战斗*/
    public void PKOpen(){
    	for (int i=0,length=groups.size();i<length;i++) {
			SLDHGroup group=groups.get(i);
			isPK(group);
			try {Thread.sleep(40);} catch (Exception e) {}
		}
	}
	/**获取玩家数据*/
	public SLDHRole getRole(BigDecimal roleID){
		return roleMap.get(roleID);
	}
	/**添加玩家数据*/
	public SLDHRole addRole(SLDHRole role){
		return roleMap.put(role.getRoleID(), role);
	}
	/**判断是否能进入战斗*/
	public void isPK(SLDHGroup group){
		if (group.getTeam2()==null) {
			group.setState(1);
			groupEnd(group,1,"本次轮空");
			return;
		}
		String name1=null,name2=null;
		SLDHTeam team1=group.getTeam1();
		for (int i = 0; i < team1.getRoles().length; i++) {
			name1=isAbnormal(team1.getRoles()[i].getRoleShow().getRolename());
			if (name1!=null) {break;}
		}
		if (name1==null) {
			SLDHTeam team2=group.getTeam2();
			for (int i = 0; i < team2.getRoles().length; i++) {
				name2=isAbnormal(team1.getRoles()[i].getRoleShow().getRolename());
				if (name2!=null) {break;}
			}	
		}
		if (name1==null&&name2==null) {//进入战斗
			BattleThreadPool.PKContest(group);
			return;
		}
		if (name2!=null) {
			group.setState(1);
			groupEnd(group,1,name2);
		}else if (name1!=null) {
			group.setState(1);
			groupEnd(group,2,name1);
		}
	}
	/**检测是否处于异常状态*/
	public String isAbnormal(String name){
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(name);
		LoginResult roleInfo=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
		if (roleInfo==null) {return "玩家#R"+name+"#W处于离线状态,导致本次匹配失败";}
		if (roleInfo.getMapid()!=3320) {return "玩家#R"+name+"#W不在当前地图内,导致本次匹配失败";}
		if (roleInfo.getFighting()!=0) {return "玩家#R"+name+"#W处于战斗状态中,导致本次匹配失败";}
		return null;	
	}
	public String getMsg() {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		buffer.append("本次参赛队伍总数:");
		buffer.append(teamTotal);
		buffer.append("队。");
		if (I==0) {
			buffer.append("现在处于进场阶段,");
		}else if (I==1) {
			buffer.append("现在处于淘汰阶段,");
		}else if (I!=4)  {
			buffer.append("现在处于4强选拔阶段,");
		}else if (I==4)  {
			buffer.append("本次水陆大会已经结束,请找魏征获取奖励");
		}
		if (I==1||I==2||I==3) {
			buffer.append("当前阶段参与队伍数:");
			buffer.append(teamNum);
			buffer.append("队。还有");
			buffer.append((groupNum-endNum)*2);
			buffer.append("个队伍还未结束战斗。");
		}
		return buffer.toString();
	}
	/**查询进度接口*/
	public void cx(int type){
		if (I==4||I==0) {return;}
		if (type==0) {//查询比赛进度
			WriteOut.writeTxtFile(SLbuffer.toString(), "sldhGY");
			StringBuffer buffer=new StringBuffer();
			buffer.append(endNum+"/"+groupNum);
			for (int i=0,length=groups.size();i<length;i++) {
				SLDHGroup group=groups.get(i);
				SLbuffer.append("\r\n");
				SLbuffer.append(group.getTeam1().getTeamMsg());
				SLbuffer.append(" -VS- ");
				SLbuffer.append(group.getTeam2()!=null?group.getTeam2().getTeamMsg():"null");
				SLbuffer.append(" ");
				SLbuffer.append(group.getState());
				SLbuffer.append(" ");
				SLbuffer.append(group.getFightId());
				SLbuffer.append(" ");
				BattleData battleData=BattleThreadPool.BattleDatas.get(group.getFightId());
				SLbuffer.append(battleData!=null?"还在战斗":"找不到战斗");
			}
			WriteOut.writeTxtFile(SLbuffer.toString(), "sldhCX");		
		}else if (type==1) {//干预比赛
			for (int i=0,length=groups.size();i<length;i++) {
				SLDHGroup group=groups.get(i);
				if (group.getState()==1||group.getState()==0) {//还在战斗中的
					group.setState(2);	
					SLbuffer.append("\r\n队伍:");
					SLbuffer.append(group.getTeam1()!=null?group.getTeam1().getTeamMsg():"null");
					SLbuffer.append(" ");
					SLbuffer.append(group.getTeam2()!=null?group.getTeam2().getTeamMsg():"null");
					SLbuffer.append(" 结果:强行结束默认一队获胜");
				}
			}
			grouping();
		}
	}
	
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return I!=4;
	}
	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int battleEnd(BattleEnd battleEnd, LoginResult loginResult,
			MapMonsterBean bean, int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getSceneMsg(LoginResult loginResult, long oldMapId, long mapId) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getJS() {
		return JS;
	}
	public int getCI() {
		return CI;
	}
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
	/**替换npc雕像*/
	public void loadStatue() {
		if (lastShows==null) {
			TitleUtil.addTitle(TitleUtil.SL);
			return;
		}
		byte[] npcs = ReadTxtUtil.InputStream2ByteArray(GameServer.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "")+ "GetTXT\\npc.txt");
		try {
			if (npcs.length > 10) {
				byte a = npcs[npcs.length - 4];
				npcs[npcs.length - 4] = npcs[4];
				npcs[4] = a;
			}
			String npcss = NewAESUtil.AESJDKDncode(new String(npcs));
			npcs = npcss.getBytes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mapNpcs = MessageGZIP.uncompressToString(npcs, "utf-8");
		AllNpcBean allNpcBean = GsonUtil.getGsonUtil().getgson().fromJson(mapNpcs, AllNpcBean.class);
		BigDecimal[] ids = new BigDecimal[lastShows.length];
		for (int i = 0; i < lastShows.length; i++) {
			RoleShow show=lastShows[i];
			ids[i]=show.getRole_id();
			int npcid = 400096+i;
			NpcInfoBean infoBean=allNpcBean.getAllNpcInfo().get(npcid+"");
			if (infoBean!=null) {
				infoBean.getNpctable().setNpcname(show.getRolename());
				infoBean.getNpctable().setSkin(show.getSpecies_id()+"0");
			}
		}
		TitleUtil.addTitle(TitleUtil.SL, ids);
		// 重构npc表
		String msg = GsonUtil.getGsonUtil().getgson().toJson(allNpcBean);
		ReadPoolUtil.text(GameServer.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "")+"GetTXT\\npc.txt", msg);
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,
			StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,
			Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		return null;
	}
}
