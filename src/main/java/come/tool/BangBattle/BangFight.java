package come.tool.BangBattle;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.action.gang.GangBattleAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.FightingData.Battlefield;
import come.tool.Good.DropUtil;
import come.tool.Stall.AssetUpdate;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

/**
 * 帮战沙盘
 * @author Administrator
 */
public class BangFight {
    //获取帮战胜利奖励
	public static String SLJL="经验=30000|物品=0&892-893-901-902-903-904-905-905-907-908-909-502-503-504-505-506-507-508-509-510-511-512-513-514-517-518-$1$30&771-772-7050-7051-7052-7053-7054-7055-7056-7057-7058-7059-7060-7061-7062-7063-7064-7065-614-615-197-6698-6683-6688-6673-6678-6658-$1$10";
	public static String SLEXP="经验=30000";
	//获取帮战失败奖励
	public static String SBJL="经验=20000|物品=0&502-503-504-505-770-893-894-895-896-897-727-728-729-730-731-770-888-7050-7051-7052-7053-7054-7055-7056-7057-8470$1$30";
	public static String SBEXP="经验=20000";
	//结束状态清除"gangstate//0|0|0"
	public static String QC=Agreement.getAgreement().gangstate("0|0|0");
	//普通伤害
	public static int HURT=10;
	//龙神伤害
	public static int HURT_LONG=180;
	//高手伤害
	public static int HURT_GAO=600;
	//挑战赛间隔
	public static int TIME_GAO=300;
	//帮战最大时长
	public static int TIME_END=3600;
	//沙盘id
	public int BangFightID;
	//场次
	public int sum;
	//状态  0未开启  1开启  2关闭
	public int BangState;	
	//帮战成员
	public BangPoints Camp_Left_ID;
	private ConcurrentHashMap<String,Member> Camp_Left_Man=new ConcurrentHashMap<>();
	public BangPoints Camp_Right_ID;
	private ConcurrentHashMap<String,Member> Camp_Right_Man=new ConcurrentHashMap<>();
	//中立建筑物
	private Build LongCannon=new Build(3, 0);//0
	//左方建筑物  
	//城门
	private Build Left_Gate=new Build(0, 1);//1
	//火炮
	private Build Left_FIRE_1=new Build(1, 2);//2
	private Build Left_FIRE_2=new Build(1, 3);//3
	//冰炮
	private Build Left_ICE_1=new Build(2, 4);//4
	private Build Left_ICE_2=new Build(2, 5);//5
	//右方建筑物  
	//城门
	private Build Right_Gate=new Build(0, 11);//11
	//火炮
	private Build Right_FIRE_1=new Build(1, 12);//12
	private Build Right_FIRE_2=new Build(1, 13);//13
	//冰炮
	private Build Right_ICE_1=new Build(2, 14);//14
	private Build Right_ICE_2=new Build(2, 15);//15
	//挑战赛倒计时
	private long PKCountDown;
	//0表示开始 1表示已经结束
	public int PKstate;
	//挑战赛 发起方 
	public Member Launch;
	//记录运行的时间
	public long time;
    //获取挑战者的阵营
	public BigDecimal getPK(){
		if (PKstate==0) {
			if (Launch!=null)return Launch.getCamp();
		}
		return null;
	}
	public BangFight(int sum,BangPoints Camp_Left_ID,BangPoints Camp_Right_ID) {
		// TODO Auto-generated constructor stub
		this.Camp_Left_ID  = Camp_Left_ID;
		this.Camp_Right_ID = Camp_Right_ID;
		if (this.Camp_Left_ID==null) {
			this.Camp_Left_ID=new BangPoints(new BigDecimal(-1));
		}
		if (this.Camp_Right_ID==null) {
			this.Camp_Right_ID=new BangPoints(new BigDecimal(-1));
		}
		this.sum=sum;
		PKCountDown=TIME_GAO;
	}
	public Build getBuild(int bh){
		switch (bh) {
		case 0:return LongCannon;
		case 1:return Left_Gate;
		case 2:return Left_FIRE_1;
		case 3:return Left_FIRE_2;
		case 4:return Left_ICE_1;
		case 5:return Left_ICE_2;
		case 11:return Right_Gate;
		case 12:return Right_FIRE_1;
		case 13:return Right_FIRE_2;
		case 14:return Right_ICE_1;
		case 15:return Right_ICE_2;
		}
		return LongCannon;
	}
	//增加
	public boolean addMember(String RoleName,BigDecimal gang_id){
		Map<String,Member> map=getMap(gang_id);
		if (map==null)return false;
		if (map.get(RoleName)!=null){
			map.get(RoleName).setState(1);
			map.get(RoleName).setTime2(30);
			return true;
		}
		map.put(RoleName,new Member(RoleName, gang_id));
		return true;
	}
	//remove
	public boolean removeMember(String RoleName,BigDecimal gang_id){
		Map<String,Member> map=getMap(gang_id);
		if (map==null)return false;
		if (map.get(RoleName)!=null)
			map.get(RoleName).setState(-1);	
		return true;
	}
	public Member getMember(String RoleName,BigDecimal gang_id){
		Map<String,Member> map=getMap(gang_id);
		if (map==null)return null;
		Member member=map.get(RoleName);
		if (member==null) {
			member=new Member(RoleName,gang_id);
			map.put(RoleName,member);
		}
		Map<String,Member> map2=getMap2(gang_id);
		if (map2!=null) {
			if (map2.get(RoleName)!=null) {
				member.setState(map2.get(RoleName).getState());
				map2.remove(RoleName);	
			}
		}
		return member;	
	}
	public Map<String,Member> getMap(BigDecimal gang_id){
		if (gang_id==null)return null;
		if (Camp_Left_ID!=null)
			if (gang_id.compareTo(Camp_Left_ID.getId())==0)
				return Camp_Left_Man;
		if (Camp_Right_ID!=null)
	        if (gang_id.compareTo(Camp_Right_ID.getId())==0)
			    return Camp_Right_Man;	
		return null;
	}
	//获取敌方阵营
	public Map<String,Member> getMap2(BigDecimal gang_id){
		if (gang_id==null)return null;
		if (Camp_Left_ID!=null)
			if (gang_id.compareTo(Camp_Left_ID.getId())!=0)
				return Camp_Left_Man;
		if (Camp_Right_ID!=null)
	        if (gang_id.compareTo(Camp_Right_ID.getId())!=0)
			    return Camp_Right_Man;	
		return null;
	}
	//战报
	public void BattleNews(String msg){
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(msg);
		msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		Msg(msg);
	}
	//**判断阵营
	public boolean iscamp(BigDecimal gang_id){
		if (gang_id==null)return true;
		if (Camp_Left_ID!=null)
			if (gang_id.compareTo(Camp_Left_ID.getId())==0)
				return true;
		if (Camp_Right_ID!=null)
	        if (gang_id.compareTo(Camp_Right_ID.getId())==0)
			    return false;	
		return true;
	}
	//给发送信息
	public void Msg(String msg){
		for (Member value : Camp_Left_Man.values()) { 
			if (value.getState()!=-1) {
				SendMessage.sendMessageByRoleName(value.getKey(),msg);			
			}
		}
        for (Member value : Camp_Right_Man.values()) { 
        	if (value.getState()!=-1) {
				SendMessage.sendMessageByRoleName(value.getKey(),msg);			
			}
		}
	}
	//获取塔的阵营
	public BigDecimal getBuildCamp(int bh){
		if (bh<=5){
			if (Camp_Left_ID==null)return new BigDecimal(-1);
			return Camp_Left_ID.getId();
		}else {
			if (Camp_Right_ID==null)return new BigDecimal(-1);
			return Camp_Right_ID.getId();
		}		
	}
	//根据名字找出玩家
	public Member getrole(String RoleName){
		Member role=null;
		if (role==null) {
			if (Camp_Left_Man!=null) {
				role=Camp_Left_Man.get(RoleName);
			}
		}
		if (role==null) {
			if (Camp_Right_Man!=null) {
				role=Camp_Right_Man.get(RoleName);
			}
		}
		return role;
	}
	//发送帮派战局状况  ture发送全部   false 发送个人信息
	public void getzk(String RoleName,boolean is){
		Member role=getrole(RoleName);
		if (role==null){
			return ;	
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("0|");
		if (BangState==1) {
			buffer.append(1);		
		}else {
			buffer.append(0);
		}
		buffer.append("|");
		buffer.append(role.getState());
		if (is) {
			for (int i = 0; i < 11; i++) {
				Build build=getBuild(i>5?i+5:i);
				buffer.append("|");
				buffer.append(build.getBh());
				buffer.append("=");
				buffer.append(build.getType());
				buffer.append("=");
				buffer.append(build.getSurvival());
				buffer.append("=");
				buffer.append(build.getHp());
			}	
		}
		SendMessage.sendMessageByRoleName(RoleName,Agreement.getAgreement().gangstate(buffer.toString()));			
	} 
	//已知人的状态
    public void getzk_role(Member role){
    	StringBuffer buffer=new StringBuffer();
    	buffer.append("0|");
    	if (BangState==1) {
			buffer.append(1);		
		}else {
			buffer.append(0);
		}
		buffer.append("|");
		buffer.append(role.getState());
		SendMessage.sendMessageByRoleName(role.getKey(),Agreement.getAgreement().gangstate(buffer.toString()));
    }	
    //刷新建筑状态
    public void getzk_Build(Build build){
    	StringBuffer buffer=new StringBuffer();
    	buffer.append(1);
		buffer.append("|");
		buffer.append(build.getBh());
		buffer.append("=");
		buffer.append(build.getType());
		buffer.append("=");
		buffer.append(build.getSurvival());	
		buffer.append("=");
		buffer.append(build.getHp());	
    	Msg(Agreement.getAgreement().gangstate(buffer.toString()));
    }
    //龙神大炮开炮状态
    public void getzk_L_Build(Build build){
//   	 //开炮
    	StringBuffer buffer=new StringBuffer();
    	buffer.append(1);
		buffer.append("|");
		buffer.append(build.getBh());
		buffer.append("=");
		buffer.append(build.getType());
		buffer.append("=");
		buffer.append(build.getSurvival());	
		buffer.append("=");
		buffer.append(build.getHp());	
		buffer.append("|0=3=");
		buffer.append(build.getBh()==1?3:6);	
		buffer.append("=600");
    	Msg(Agreement.getAgreement().gangstate(buffer.toString()));
    }
	//战斗处理
	public void process(){
		if (BangState!=1) {
			return;
		}
		time++;
		for (Member value : Camp_Left_Man.values()) { 
			if (value.process()) {
				getzk_role(value);
			}
		}
        for (Member value : Camp_Right_Man.values()) { 
        	if (value.process()) {
        		getzk_role(value);
			}
		}  
        PKCountDown--;
        if (PKCountDown<=0) {
        	StringBuffer buffer=new StringBuffer();
        	buffer.append("新的一轮挑战赛开始!");
        	if (PKstate==0) {//判断是否有人怂了
            if (Launch==null) {
            	buffer.append("本轮挑战赛没有人参与!");	
			}else {
				Build door=getDoor(Launch.getKey());
                if (door.setHp(door.getHp()-HURT_GAO)) {
                	getzk_Build(door);
				}
				buffer.append("竟然没有人接受#G");
				buffer.append(Launch.getKey());
				buffer.append("#Y的挑战被惩罚扣除城门#G600#Y血量,剩余血量#G"+door.getHp());
				Launch.setState(0);
				getzk_role(Launch);	
			} 		
    		}
        	PKCountDown=TIME_GAO;
        	PKstate=0;
        	Launch=null;
        	BattleNews(buffer.toString());	
		} 
        for (int i = 0; i < 11; i++) {
			Build build=getBuild(i>5?i+5:i);
			if (build.getHp()<=0)continue;
			if (build.getState()==Build.IDLE) {//空闲
				continue;
			}else if (build.getState()==Build.ENERGY) {//充能
				if (build.istime()) {
					getzk_Build(build);
					Member role=getrole(build.getRoleName());
					if (role!=null) {
//	                    addFeat(role, 1);
						role.setState(0);
						getzk_role(role);
						StringBuffer buffer=new StringBuffer();
	                    buffer.append("玩家#G");
	                    buffer.append(build.getRoleName());
	                    buffer.append("#Y给#G");
	                    buffer.append(build.getName());
	                    buffer.append("#Y充能成功");
	                    role=getMember(role);
	                    if (role==null) {
	                    	buffer.append(",可惜不生效");
						}else {
							buffer.append(",对玩家#G");
							buffer.append(role.getKey());
							buffer.append("#Y产生了效果");
						    if (build.getType()==Build.TOWER_FIRE) {
								//火
								daduan(role.getKey(),1);
								IAction action=ParamTool.ACTION_MAP.get("changemap");								
							    action.action(GameServer.getRoleNameMap().get(role.getKey()),iscamp(role.getCamp())?GangBattleAction.HOME_Left:GangBattleAction.HOME_Right);
							}else {
								//冰
								daduan(role.getKey(),2);
								//判断是否有组队也被冰冻
								ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(role.getKey());
								if (ctx2!=null) {
									LoginResult loginResult=GameServer.getAllLoginRole().get(ctx2);
									if (loginResult!=null) {
										String[] v=loginResult.getTeam().split("\\|");
										for (int j = 0; j < v.length; j++) {
											if (!v[j].equals(role.getKey())) {
												daduan(v[j],2);	
											}
											
										}
									}	
								}								
							}
						}
	                    BattleNews(buffer.toString());
					}
					build.setRoleName(null);
				}
			}else if (build.getState()==Build.BEATEN) {//被殴打
				if (build.istime()) {
                    if (build.setHp(build.getHp()-HURT)) {
                    	getzk_Build(build);
					}
                    Member role=getrole(build.getRoleName());
					if (role!=null) {
//				      addFeat(role,6);
					  role.setState(0);
					  getzk_role(role);
                      StringBuffer bufferR=new StringBuffer();
                      bufferR.append("玩家#G");
                      bufferR.append(build.getRoleName());
                      bufferR.append("#Y成功攻击#G");
                      bufferR.append(build.getName());
                      bufferR.append("#Y造成10点伤害,剩余血量#G"+build.getHp());
                      BattleNews(bufferR.toString());
					}
					build.setRoleName(null);
				}
			}else if (build.getState()==Build.ATTACK) {//攻击
				if (build.istime()) {
					Build door=getDoor(build.getRoleName());
                    if (door.setHp(door.getHp()-HURT_LONG)) {
                    	getzk_L_Build(door);
					}
                    Member role=getrole(build.getRoleName());
					if (role!=null) {
//					  addFeat(role,5);
					  role.setState(0);
					  getzk_role(role);
                      StringBuffer buffer=new StringBuffer();
                      buffer.append("玩家#G");
                      buffer.append(build.getRoleName());
                      buffer.append("#Y成功操控#G龙神大炮#Y给敌方城门造成180点伤害,剩余血量#G"+door.getHp());
                      BattleNews(buffer.toString());
					}
					build.setRoleName(null);
				}
			}
		}	
	}
	/**帮战积分获取*/
	public void addFeat(Member role,int f){
		String jf="帮派积分="+f;
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.updata(jf);
		assetUpdate.setType(AssetUpdate.USERGOOD);
		String msg=Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate));
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role.getKey());
        if (ctx!=null) {
			LoginResult log=GameServer.getAllLoginRole().get(ctx);
			if (log!=null) {
				String[] v=log.getTeam().split("\\|");
				for (int i = 0; i < v.length; i++) {
					ctx=GameServer.getRoleNameMap().get(v[i]);
					if (ctx!=null) {
						 log=GameServer.getAllLoginRole().get(ctx);
						 if (log!=null) {
							// 添加角色的帮派积分
							 log.setScore(DrawnitemsAction.Splice(log.getScore(),jf,2));
							 SendMessage.sendMessageToSlef(ctx,msg);  
						 }
					}
				}
			}
		}
	}
	/*获取龙神大炮的操控者的对方城门单位*/
	public Build getDoor(String role){
		if (Camp_Left_Man.get(role)!=null) {
			return Right_Gate;
		}else {
			return Left_Gate;
		}
	}
	/**判断是否帮战结束*/
	public boolean isEnd(){
		if (BangState!=1||time>=TIME_END||Left_Gate.getHp()<=0||Right_Gate.getHp()<=0) {
			isVictory();
			return true;
		}
		return false;
	}
	//判断获胜方
	public void isVictory(){
		if (Left_Gate.getHp()<=0) {
			PVictory(Camp_Right_ID,Camp_Left_ID);
		}else if (Right_Gate.getHp()<=0) {
			PVictory(Camp_Left_ID,Camp_Right_ID);
		}else if (Left_Gate.getHp()>=Right_Gate.getHp()) {
			PVictory(Camp_Left_ID,Camp_Right_ID);
		}else {
			PVictory(Camp_Right_ID,Camp_Left_ID);	
		}
		BangState=2;
	}

	//胜败处理
	public void PVictory(BangPoints s,BangPoints b){
//		s.setRecord(s.getRecord()|(1<<sum));
        //帮战结束
		StringBuffer buffer=new StringBuffer();
		buffer.append("帮战结束");
		GangDomain gangDomain=GangUtil.getGangDomain(s.getId());
		if (gangDomain!=null) {
			buffer.append("帮派:#G");
			buffer.append(gangDomain.getGang().getGangname());
			buffer.append("#Y获得胜利");
		}
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		Map<String,Member> v=(s==Camp_Left_ID?Camp_Left_Man:Camp_Right_Man);
		for (Member value : v.values()) { 
			value.setState(0);
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(value.getKey());
			if (ctx!=null) {
				LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
				if (loginResult!=null) {
					if (value.getTime()>300) {
						DropUtil.getDrop(loginResult,SLJL,"帮战胜利礼包", 22,300,null);		
					}else {
						DropUtil.getDrop(loginResult,SLEXP,"帮战胜利礼包", 22,value.getTime(),null);		
					}
					SendMessage.sendMessageToSlef(ctx, QC);
					SendMessage.sendMessageToSlef(ctx, msg);
				}
			}					
		}
		v=(b==Camp_Left_ID?Camp_Left_Man:Camp_Right_Man);
        for (Member value : v.values()) {
        	value.setState(0);
        	ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(value.getKey());
			if (ctx!=null) {
				LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
				if (loginResult!=null) {
					if (value.getTime()>300) {
						DropUtil.getDrop(loginResult,SBJL,"帮战参与礼包",22,300,null);		
					}else {
						DropUtil.getDrop(loginResult,SBEXP,"帮战参与礼包",22,value.getTime(),null);		
					}
					SendMessage.sendMessageToSlef(ctx, QC);
					SendMessage.sendMessageToSlef(ctx, msg);
				}
			}			
		}
	}
	/**随机一个敌方单位*/
	public Member getMember(Member role){
		Map<String,Member> mans=null;
		if (Camp_Left_Man.get(role.getKey())!=null) {
			mans=Camp_Right_Man;
		}else {
			mans=Camp_Left_Man;
		}
		Member man=null;
		int size=mans.size();
		ss:for (Member value : mans.values()) { 
			if (value.getState()==0||value.getState()==4) {
				LoginResult loginResult=GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(value.getKey()));
				if (loginResult==null||loginResult.getFighting()!=0) {
					continue;
				}
				man=value;
				if (Battlefield.random.nextInt(size)<=2) {
					break ss;
				}
			}
		}
		return man;	
	}
	//打断 0是pk  1是挑战 2是冰 火塔 3是掐断龙神大炮炮火
	public String daduan2(String rolename,int type){
		Member role=getrole(rolename);
		if (role==null){
			return null;
		}
		if (type==0) {
			if (role.getState()==2) {
				return "玩家"+role.getKey()+"处于冰冻状态";
			}else if (role.getState()==3) {
				return "玩家"+role.getKey()+"处于挑战状态";
			}else if (role.getState()==1) {
				return "玩家"+role.getKey()+"处于休息状态";
			}else {
				Build door=getBuild(rolename);
				if (door!=null) {
					door.setRoleName(null);
					door.setState(Build.IDLE);
					door.setTime(0);
				}
				if (role.getState()!=0) {
					role.setState(0);
					getzk_role(role);	
				}
			}
		}else if (type==1) {
			role.setState(0);
			getzk_role(role);
			Launch=null;
			PKstate=1;
		}else if (type==2) {
			if (role.getState()==3) {
				return "玩家"+role.getKey()+"处于挑战状态";
			}else {
				Build door=getBuild(rolename);
				if (door!=null) {
					if (door.getType()==Build.TOWER_LONG) {
						return "玩家正在点火快点阻止他";
					}
					door.setState(Build.IDLE);
					door.setTime(0);
					door.setRoleName(null);
				}
				if (role.getState()!=0) {
					role.setState(0);
					getzk_role(role);	
				}
			}
		}else if (type==3) {
			Build door=getBuild(rolename);
			if (door!=null) {
				door.setState(Build.IDLE);
				door.setTime(0);
				door.setRoleName(null);
			}
			if (role.getState()!=0) {
				role.setState(0);
				getzk_role(role);	
			}
		}
		return null;
	}
	//获取玩家操作的塔 打断
	public void daduan(String rolename,int state){
		Member role=getrole(rolename);
		if (role==null){
			return;
		}
		Build door=getBuild(rolename);
		if (door!=null) {
			door.setRoleName(null);
			door.setState(Build.IDLE);
			door.setTime(0);
		}
		if (role.getState()!=state) {
			if (state==1) {
				role.setTime2(40);	
			}else if (state==2) {
				role.setTime2(20);	
			}
			role.setState(state);
			getzk_role(role);
		}
		
	}
	//获取玩家在操控的塔
	public Build getBuild(String role){
		for (int i = 0; i < 11; i++) {
			Build build=getBuild(i>5?i+5:i);
			if (build.getRoleName().equals(role)) {
				return build;
			}
		}
		return null;
	}
	//处理挑战赛胜利
	public void PKP(String role){
		Build door=getDoor(role);
        if (door.setHp(door.getHp()-HURT_GAO)) {
        	getzk_Build(door);
		}
        StringBuffer buffer=new StringBuffer();
        buffer.append("玩家#G");
        buffer.append(role);
        buffer.append("#Y带领队伍获得挑战赛的胜利给敌方城门造成600点伤害,剩余血量#G"+door.getHp());
        BattleNews(buffer.toString());
	}
}
