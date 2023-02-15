package come.tool.teamArena;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.suit.SuitComposeAction;
import org.come.bean.LoginResult;
import org.come.entity.Record;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.Good.DropUtil;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
import come.tool.newTask.TaskRecord;
import come.tool.newTeam.TeamBean;
import come.tool.newTeam.TeamRole;


public class TeamArenaUtil {
	public static long TIME =1000*60*60;//一次活动持续的时间
	public static long ATIME=1000*30;//确认时间
	public static long PTIME=1000*5;//准备时间
	
	public static Object Thread_LOCK = new Object();//线程锁
	public static Object List_LOCK = new Object();//队列锁
	public static Object Jf_LOCK = new Object();//积分操作锁
	
	public static TeamArenaThread teamArenaThread;
	private static List<TeamBean> affirmList; //确认队列
	private static List<TeamBean> matchList;  //匹配队列
	private static List<TeamArenaGroup> prepareList;//准备队列
	
	/**进入确认队列*/
    public static void addAffirm(ChannelHandlerContext ctx,TeamBean bean){
    	List<TeamRole> teams=null;
    	synchronized (bean) {
    		TeamArenaMatch arenaMatch=bean.getTeamArenaMatch();
			if (arenaMatch!=null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你的队伍已在匹配"));
				return;
			}	
			teams=bean.getTeams();
			if (teams.size()!=5) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你的队伍不足5人"));
				return;
			}
			int size=teams.size();
			int max=0;//最大分值
			for (int i = 0; i < size; i++) {
				TeamRole teamRole=teams.get(i);
				RoleData roleData=RolePool.getRoleData(teamRole.getRoleId());
				LoginResult loginResult=roleData!=null?roleData.getLoginResult():null;
				if (teamRole.getState()<0||loginResult==null) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement(teamRole.getName()+"不在队伍中"));
					return;
				}else if (loginResult.getGrade()<=102) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement(teamRole.getName()+"还未转生,无法参与匹配"));
					return;
				}else if ((roleData.getTaskWC(5)-roleData.getTaskLQ(5))>=3) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("留得青山在，不怕没柴烧,"+teamRole.getName()+"已失败三次，明天再来吧！"));
					return;
				}
				synchronized (Jf_LOCK) {
					int jf=loginResult.getPkrecord()!=null?loginResult.getPkrecord().intValue():0;
					if (i==0) {max=jf;}
					else if (max<jf) {max=jf;}
				}
				
			}
			arenaMatch=new TeamArenaMatch(size);
			arenaMatch.setJF(max);
			bean.setTeamArenaMatch(arenaMatch);
			synchronized (List_LOCK) {
				affirmList.add(bean);//加入匹配列表
			}
    	}
    	//发送数据 打开匹配面板
    	bean.sendTeam(Agreement.getAgreement().teamArenaAgreement("O"+GsonUtil.getGsonUtil().getgson().toJson(teams)));		
	}
	/**进入匹配队列*/
	public static void addMatch(TeamBean bean){
		synchronized (List_LOCK) {
			bean.getTeamArenaMatch().setState(1);
			bean.getTeamArenaMatch().setTime(System.currentTimeMillis());
			affirmList.remove(bean);//先把队伍从确认列表移除
			//维持从小到大排序
			for (int i = 0; i < matchList.size(); i++) {
				if (matchList.get(i).getTeamArenaMatch().getJF()<bean.getTeamArenaMatch().getJF()) {
					matchList.add(i,bean);
					return;
				}
			}
			matchList.add(bean);
		}
	}
    /**意外导致退出匹配*/
	public static void quitTeamArena(TeamBean bean,TeamRole role){
		boolean isV=false;
		TeamBean bean2=null;
		synchronized (List_LOCK) {
			TeamArenaMatch arenaMatch=bean.getTeamArenaMatch();
			if (arenaMatch==null) {return;}
			if (arenaMatch.getState()==0) {//确认队列
				bean.setTeamArenaMatch(null);
				isV=affirmList.remove(bean);
			}else if (arenaMatch.getState()==1) {//匹配队列
				bean.setTeamArenaMatch(null);
				isV=matchList.remove(bean);
			}else if (arenaMatch.getState()==2) {//准备队列
				bean.setTeamArenaMatch(null);
				for (int i =prepareList.size()-1;i>=0;i--) {
					TeamArenaGroup teamArenaGroup=prepareList.get(i);
					if (teamArenaGroup.getTeam1()==bean) {
						bean2=teamArenaGroup.getTeam2();
						bean2.setTeamArenaMatch(null);
						prepareList.remove(i);
						isV=true;
						break;
					}else if (teamArenaGroup.getTeam2()==bean) {
						bean2=teamArenaGroup.getTeam1();
						bean2.setTeamArenaMatch(null);
						prepareList.remove(i);
						isV=true;
						break;
					}
				}
			}else {//其他状态不影响
				return;
			}
		}
		if (isV) {
			String send=Agreement.getAgreement().teamArenaAgreement("D"+"玩家"+role.getName()+"状态异常导致匹配失败");
			bean.sendTeam(send);
			if (bean2!=null) {
				bean2.sendTeam(send);
			}
		}
	}
	/**操作权限受限*/
	public static boolean isOperate(ChannelHandlerContext ctx,TeamBean bean){
		if (bean.getTeamArenaMatch()!=null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你的队伍正在匹配,无法操作"));
			return true;
		}
		return false;
	}
	/**准备队列*/
	public static void prepare(long time){
		while (true) {
			TeamArenaGroup teamArenaGroup=null;
			synchronized (List_LOCK) {//获取一个准备队列
				if (prepareList.size()>0) {
					teamArenaGroup=prepareList.get(0);
					if (time-teamArenaGroup.getTime()<PTIME) {break;}
					prepareList.remove(0);
				}
			}
			if (teamArenaGroup==null) {break;}
			String value=BattleThreadPool.addTeamArenaBattle(teamArenaGroup);
			if (value==null) {
				synchronized (List_LOCK) {//成功进入战斗修改
					teamArenaGroup.getTeam1().getTeamArenaMatch().setTime(time);
					teamArenaGroup.getTeam1().getTeamArenaMatch().setState(3);
					teamArenaGroup.getTeam2().getTeamArenaMatch().setTime(time);
					teamArenaGroup.getTeam2().getTeamArenaMatch().setState(3);	
				}
				String send=Agreement.getAgreement().teamArenaAgreement("D")+Agreement.getAgreement().TaskNAgreement("C5=L");
				teamArenaGroup.getTeam1().addTaskAndSendTeam(5, send);
				teamArenaGroup.getTeam2().addTaskAndSendTeam(5, send);
			}else {
				synchronized (List_LOCK) {//退出修改
					teamArenaGroup.getTeam1().setTeamArenaMatch(null);
					teamArenaGroup.getTeam2().setTeamArenaMatch(null);
				}
				String send=Agreement.getAgreement().teamArenaAgreement("D"+value);
				teamArenaGroup.getTeam1().sendTeam(send);
				teamArenaGroup.getTeam2().sendTeam(send);
			}
		}
	}
	/**匹配队列*/
	public static void match(long time){
		int index=0;
		while (true) {
			TeamBean team1=null;
			TeamBean team2=null;
			synchronized (List_LOCK) {
				int size=matchList.size();
				if (size>index+1) {
					team1=matchList.get(index);
					//获取允许分值差每秒加2分
					int jf=team1.getTeamArenaMatch().getJF();
					long gap=(time-team1.getTeamArenaMatch().getTime())/1000;
					//40~100    30秒内  //100-150   1分钟以外
					if (gap<30) {
						gap=40+2*gap;
					}else if (gap<60) {
						gap=100;
					}else {
						gap=2*gap-20;
						if (gap>150) {gap=150;}
					}
					index++;
					for (int i = index; i < size; i++) {
						TeamBean team=matchList.get(i);
						if (team.getTeamArenaMatch().getJF()-jf>gap||i==(size-1)) {
							i-=index;
							team2=matchList.get(index+(i>0?SuitComposeAction.random.nextInt(i):0));
							break;
						}
					}
				}
				if (team1==null) {break;}   //第一个没获取到
				if (team2==null) {continue;}//第二个没获取到
				team1.getTeamArenaMatch().setTime(time);
				team1.getTeamArenaMatch().setState(2);
				team2.getTeamArenaMatch().setTime(time);
				team2.getTeamArenaMatch().setState(2);
				TeamArenaGroup teamArenaGroup=new TeamArenaGroup(team1,team2,time);//加入准备队列
				matchList.remove(team1);
				matchList.remove(team2);
				prepareList.add(teamArenaGroup);
			}
			//发送敌人信息
			team1.sendTeam(Agreement.getAgreement().teamArenaAgreement("E"+GsonUtil.getGsonUtil().getgson().toJson(team2.getTeams())));
			team2.sendTeam(Agreement.getAgreement().teamArenaAgreement("E"+GsonUtil.getGsonUtil().getgson().toJson(team1.getTeams())));
		}
	}
	/**确认匹配队列超时判断*/
	public static void confirmTimeOut(long time){
		for (int i=affirmList.size()-1;i>=0;i--) {
			TeamBean bean=affirmList.get(i);
			synchronized (bean) {
				if (time-bean.getTeamArenaMatch().getTime()>ATIME) {//超时了
					synchronized (List_LOCK) {
						bean.setTeamArenaMatch(null);
						affirmList.remove(bean);
					}
					//发送退出协议头
					bean.sendTeam(Agreement.getAgreement().teamArenaAgreement("D队伍中有人长时间不同意退出匹配队列"));
				}	
			}
		}
	}
	/**确认匹配队列同意*/
	public static void confirm(TeamBean bean,LoginResult roleInfo,boolean is){
		synchronized (bean) {
			TeamArenaMatch arenaMatch=bean.getTeamArenaMatch();
			if (arenaMatch==null) {
				SendMessage.sendMessageByRoleName(roleInfo.getRolename(), Agreement.getAgreement().PromptAgreement("你的队伍不在匹配队列中"));
				return;
			}
			if (arenaMatch.getState()==0) {
				if (is) {//同意
					int type=arenaMatch.isIds(roleInfo.getRole_id());
					if (type==0) {
						AllServiceUtil.getRecordService().insert(new Record(9,"同意数据异常:"+roleInfo.getRole_id()+":"+GsonUtil.getGsonUtil().getgson().toJson(arenaMatch)));
						return;
					}
					if (type==2) {
						addMatch(bean);
					}
					bean.sendTeam(Agreement.getAgreement().teamArenaAgreement("A"+roleInfo.getRole_id()));
				}else {//拒绝
					boolean isV=false;
					synchronized (List_LOCK) {
						bean.setTeamArenaMatch(null);
						isV=affirmList.remove(bean);
					}
					if (isV) {
						bean.sendTeam(Agreement.getAgreement().teamArenaAgreement("D"+roleInfo.getRolename()+"拒绝匹配"));	
					}else {
						AllServiceUtil.getRecordService().insert(new Record(9,"拒绝匹配异常:"+roleInfo.getRole_id()+":"+GsonUtil.getGsonUtil().getgson().toJson(arenaMatch)));
						return;
					}
				}
			}else if (arenaMatch.getState()==1&&!is) {
				boolean isV=false;
				synchronized (List_LOCK) {
					bean.setTeamArenaMatch(null);
					isV=matchList.remove(bean);
				}
				if (isV) {
					bean.sendTeam(Agreement.getAgreement().teamArenaAgreement("D"+roleInfo.getRolename()+"取消匹配"));
				}else {
					AllServiceUtil.getRecordService().insert(new Record(9,"取消匹配异常:"+roleInfo.getRole_id()+":"+GsonUtil.getGsonUtil().getgson().toJson(arenaMatch)));
					return;
				}
			}
		}
	}
	/**活动开启*/
	public static void teamArenaOpen(){
		synchronized (Thread_LOCK) {
			if (teamArenaThread!=null) {
				System.out.println("重复开启组队竞技场:"+System.currentTimeMillis());
				return;
			}
			synchronized (List_LOCK) {
				affirmList=new ArrayList<>();
				matchList=new ArrayList<>();
				prepareList=new ArrayList<>();
			}
			teamArenaThread=new TeamArenaThread();
			Thread T1 = new Thread(teamArenaThread);
			T1.start();	
		}
	}
	/**活动结束*/
	public static void teamArenaEnd(){
		synchronized (Thread_LOCK) {
			teamArenaThread=null;
			try {Thread.sleep(1000);} catch (Exception e) {e.printStackTrace();}
			List<TeamBean> beans=new ArrayList<>();
			synchronized (List_LOCK) {
				for (int i=0,length=affirmList.size();i<length;i++) {
					TeamBean bean=affirmList.get(i);
					bean.setTeamArenaMatch(null);
					beans.add(bean);
				}
				for (int i=0,length=matchList.size();i<length;i++) {
					TeamBean bean=matchList.get(i);
					bean.setTeamArenaMatch(null);
					beans.add(matchList.get(i));
				}
				for (int i=0,length=prepareList.size();i<length;i++) {
					TeamBean bean1=prepareList.get(i).getTeam1();
					TeamBean bean2=prepareList.get(i).getTeam2();
					bean1.setTeamArenaMatch(null);
					bean2.setTeamArenaMatch(null);
					beans.add(bean1);
					beans.add(bean2);
				}
				affirmList =null;
				matchList  =null;
				prepareList=null;
			}
			if (beans.size()!=0) {
				String send=Agreement.getAgreement().teamArenaAgreement("D活动结束匹配通道关闭");
				for (int i = 0,length=beans.size(); i < length; i++) {
					beans.get(i).sendTeam(send);
				}		
			}
		}
	}
	/**战斗结束接口*/
	public static void teamArenaBattleEnd(BattleData battleData,int camp){
		TeamArenaGroup teamArenaGroup=battleData.getTeamArenaGroup();
		if (camp==-1) {//意外结束战斗 不发奖励
			teamArenaGroup.getTeam1().setTeamArenaMatch(null);
			teamArenaGroup.getTeam2().setTeamArenaMatch(null);
			return;
		}
		TeamBean team1=camp==1?teamArenaGroup.getTeam1():teamArenaGroup.getTeam2();//胜利队伍
		TeamArenaMatch match1=team1.getTeamArenaMatch();team1.setTeamArenaMatch(null);
		TeamBean team2=camp==1?teamArenaGroup.getTeam2():teamArenaGroup.getTeam1();//失败队伍
		TeamArenaMatch match2=team2.getTeamArenaMatch();team2.setTeamArenaMatch(null);
		int fc=match1.getJF()-match2.getJF();//分差
		
		int sl=12-fc/35;
		int dw=match1.getJF()/100;
		sl+=dw>=7?-4:dw>=5?-3:dw>=3?-2:dw>=1?-1:0;
		if (sl<=0) {sl=1;}
		int sb=-7+fc/35;
		dw=match2.getJF()/100;
		sb-=dw>=6?-3:dw>=4?-2:dw>=2?-1:0;
		if (sb>=0) {sb=-1;}	
		teamArenaDraw(team1,match1,GameServer.getDorp("2039"), sl);//胜利奖励
		teamArenaDraw(team2,match2,GameServer.getDorp("2040"), sb);//失败奖励
	}
	public static void teamArenaDraw(TeamBean team,TeamArenaMatch match,Dorp dorp,int add){
		BigDecimal[] ids=match.getIds();
		for (int i = 0; i < ids.length; i++) {
			AllServiceUtil.getRoleTableService().addQMJJ(ids[i], add);//增加积分
			TeamRole teamRole=team.getTeamRole(ids[i]);
			RoleData roleData=teamRole!=null?RolePool.getRoleData(teamRole.getRoleId()):null;
			LoginResult loginResult=roleData!=null?roleData.getLoginResult():null;
			if (loginResult!=null) {//在线奖励结算
				String value=dorp!=null?dorp.getDorpValue():"";
				int jf=loginResult.getPkrecord().intValue()+add;
				loginResult.setPkrecord(new BigDecimal(jf));
				String data="K="+add;
				String msg =(add>0?("你增加了"+add+"点段位积分"):("你损失了了"+(-add)+"点段位积分"))+",你当前段位为"+getTilte(jf);
				String task=null;//胜利的人添加胜利次数
				if (add>0) {
					TaskRecord taskRecord=roleData.getTaskRecord(5);
					if (taskRecord==null) {
						taskRecord=new TaskRecord(5);
						roleData.addTaskRecord(taskRecord);
					}
					taskRecord.addRSum(1);
					task="C5=R";
					if (taskRecord.getcSum()<=3) {
						if (value!=null&&value.length()!=0) {value+="|";}
						int dw=jf/100;
						if (dw<0) {dw=0;}
						else if(dw>7) {dw=7;}
						value+="竞技积分="+(3+dw);
					}
				}
				if (roleData.getTaskWC(5)<=3) {//还有奖励
					DropUtil.getDrop4(loginResult,value,add>0?"#G{角色名}#Y在全民竞技中,大杀四方,将对方打的抱头痛哭,幸运的获得了#R{物品名}":"#G{角色名}#Y在全民竞技场中虽败犹荣,意外获得安慰奖励#R{物品名}", 25, 1D, null, data, msg,task);	
				}else {//没有奖励了
					AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
					assetUpdate.upmsg(msg);
					assetUpdate.updata(data);
					assetUpdate.setTask(task);
					SendMessage.sendMessageByRoleName(loginResult.getRolename(), Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
				}
			}
		}
	}
	//入门/进阶/精锐/英杰/豪侠/宗师/武圣/王者
	public static String getTilte(int pk){
		if (pk<100) {
			return "入门"+pk+"点";
		}else if (pk<200) {
			return "进阶"+(pk-100)+"点";
		}else if (pk<300) {
			return "精锐"+(pk-200)+"点";
		}else if (pk<400) {
			return "英杰"+(pk-300)+"点";
		}else if (pk<500) {
			return "豪侠"+(pk-400)+"点";
		}else if (pk<600) {
			return "宗师"+(pk-500)+"点";
		}else if (pk<700) {
			return "武圣"+(pk-600)+"点";
		}else {
			return "王者"+(pk-700)+"点";
		}
	}
}
