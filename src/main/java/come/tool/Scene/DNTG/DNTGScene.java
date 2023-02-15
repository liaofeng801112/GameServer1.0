package come.tool.Scene.DNTG;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.buy.BuyUtil;
import org.come.action.sys.ChangeMapAction;
import org.come.bean.ChangeMapBean;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.NPCDialog;
import org.come.bean.PathPoint;
import org.come.bean.UseCardBean;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.Door;
import org.come.model.Robots;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMove;
import org.come.task.MonsterMoveBase;
import org.come.task.MonsterUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.Battlefield;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Stall.AssetUpdate;

public class DNTGScene implements Scene{
	public static final long mapIdZ=3201;//主战场地图id
	public static final long mapIdF=3336;//上古战场地图id
	public static final String DNTGBUFF="DNTG";
	//状态标识  0进场准备 1正常  2女武神试炼    3上古战场     4结束
	private int I;
    //天庭复活点
	public ChangeMapBean HOME_TT;
	//花果山复活点
    public ChangeMapBean HOME_HGS;
    //阵营标识  0天庭  1花果山
	private ConcurrentHashMap<BigDecimal,DNTGRole> Camp_TT_Man=new ConcurrentHashMap<>();
	private ConcurrentHashMap<BigDecimal,DNTGRole> Camp_HGS_Man=new ConcurrentHashMap<>();
	private int roleSize;//总人数
	private DNTGCamp TT;
	private DNTGCamp HGS;
	//刷怪点
	private MonsterMoveBase[] DNTGPaths;
	//女武神试炼怪
	private DNTG_NV_Monster dntg_NV_Monster;
	//上古战场
	private DNTG_SG dntg_SG;
	//
	private List<DNTGMonster> monsterMap; 
	//倒计时
	private DNTGCountDown countDown;
	
	private DNTGThread dntgThread;
	private String[] functions;
	
	private int HPRecord;//扣血记录
	
	private String JG;
	private Map<Integer, MonsterMoveBase> moveMap;
	public DNTGScene() {
		// TODO Auto-generated constructor stub
		BuyUtil.resetShopType(605);
		HPRecord=0;
		functions=new String[]{"攻击","送我到上古战场","送我进去","送我出去","我要回长安"};
		Door door=GameServer.getDoor(522);
		String[] vs=door.getDoorpoint().split("\\|");
		HOME_TT=new ChangeMapBean();
		HOME_TT.setMapid(door.getDoormap());
		HOME_TT.setMapx(Integer.parseInt(vs[0]));
		HOME_TT.setMapy(Integer.parseInt(vs[1]));
    	
		door=GameServer.getDoor(523);
		vs=door.getDoorpoint().split("\\|");
		HOME_HGS=new ChangeMapBean();
		HOME_HGS.setMapid(door.getDoormap());
		HOME_HGS.setMapx(Integer.parseInt(vs[0]));
		HOME_HGS.setMapy(Integer.parseInt(vs[1]));
    	
		Camp_TT_Man=new ConcurrentHashMap<>();
    	Camp_HGS_Man=new ConcurrentHashMap<>();
    	monsterMap=new ArrayList<>();
    	TT =new DNTGCamp(0);
    	HGS=new DNTGCamp(1);
    	//0 天庭1号 to 花果1号
    	//1 天庭2号 to 花果2号
    	//2 天庭3号 to 花果3号
    	
    	//3 花果1号 to 天庭1号
    	//4 花果2号 to 天庭2号
    	//5 花果3号 to 天庭3号
    	DNTGPaths=new MonsterMoveBase[6];
    	for (int i = 0; i < DNTGPaths.length; i++) {
    		DNTGPaths[i]=new MonsterMoveBase(10+i);
		}
    	dntgThread = new DNTGThread(this);
		Thread T1 = new Thread(dntgThread);
		T1.start();
		
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(9);
		bean.setMessage("天庭和花果山互相发起了新一轮进攻");
		JG=Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		moveMap=new HashMap<>();
	}
	/**获取对应建筑物 0大本营 1集市  2-4 箭塔 
	 *              10       */
	public MapMonsterBean getBuild(int value){
		DNTGCamp camp=null;
		if (value<10) {
			camp=TT;
		}else {
			value-=10;
			camp=HGS;
		}
		if (value==0) {return camp.getDBY();}
//		else if (value==1) {return camp.getK_JS();}
		else if (value==2) {return camp.getT_S();}
		else if (value==3) {return camp.getT_Z();}
		else if (value==4) {return camp.getT_X();}
		return null;
	}
	public int getValue(MapMonsterBean bean){
		if (bean.getRobotType()==101) {
			return 0;
		}else if (bean.getRobotType()==105) {
			return 1;
		}else if (bean.getRobotType()==111) {
			return 10;
		}else if (bean.getRobotType()==115) {
			return 11;
		}else {
			return bean.getRobotType()-100;
		}
	}
	/**获取玩家*/
	public DNTGRole getRole(BigDecimal roleId){
		DNTGRole role=Camp_TT_Man.get(roleId);
		if (role==null) {role=Camp_HGS_Man.get(roleId);}
		return role;
	}
	/**添加阵营玩家*/
	public void addCampRole(DNTGRole role){
		roleSize+=1;
		if (role.getCamp()==0) {
			Camp_TT_Man.put(role.getRoleId(), role);
		}else {
			Camp_HGS_Man.put(role.getRoleId(), role);
		}
	}
	/**报名*/
	public synchronized String addEnroll(ChannelHandlerContext ctxRole,LoginResult loginResult){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return "你不是队长";}
		DNTGRole[] roleids=new DNTGRole[teams.length];
		int I=-1;
		for (int i = 0; i < teams.length; i++) {
			LoginResult login=null;
			if (i==0) {
				login=loginResult;
			}else {
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
				if (ctx!=null) {login=GameServer.getAllLoginRole().get(ctx);}
			}
			if (login==null) {return teams[i]+"处于异常状态";}
			if (login.getGrade()<389) {return teams[i]+"未满3转110";}
			DNTGRole role=getRole(login.getRole_id());
			if (role!=null) {
				if (I==-1) {
					I=role.getCamp();
				}else if (I!=role.getCamp()) {
					return "队伍中存在非同阵营的玩家";
				}
			}else {
				if (I!=-1) {return "队伍中存在非同阵营的玩家";}
				role=new DNTGRole(login.getRole_id(),login.getRolename());
			}
			roleids[i]=role;
		}
		DNTGCamp dntgCamp=null;
		if (I==-1) {
			I=Camp_TT_Man.size()>Camp_HGS_Man.size()?1:0;
			if (I==0) {
				dntgCamp=TT;
			}else {
				dntgCamp=HGS;
			}
		}
		String title=I==0?"天庭先锋":"花果山先锋";
		for (int j = 0; j < roleids.length; j++) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(roleids[j].getRoleName());
			LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log!=null&&!title.equals(log.getTitle())) {
				log.setTitle(title);
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().TitleChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(log.getRoleShow())));
			}
			if (roleids[j].getCamp()==-1) {
				roleids[j].setCamp(I);
				addCampRole(roleids[j]);
				if (dntgCamp.getBuff()!=null) {
					RoleData roleData=log!=null?RolePool.getRoleData(log.getRole_id()):null;
					if (roleData!=null) {
						roleData.addLimit(dntgCamp.getBuff().getUseCardBean());
						SendMessage.sendMessageToSlef(ctx,dntgCamp.getBuff().getSendCard());
					}
				}
			}
		}
		//进场传送
		ChangeMapAction.ChangeMap(I==0?HOME_TT:HOME_HGS, ctxRole);
		return null;		
	}	
	/**获取Boos*/
	public Boos getBoos(int v){
		Boos boos=GameServer.boosesMap.get(v+"");
		if (boos==null) {boos=MonsterUtil.booses.get(0);}
		return boos;
	}
	/**获取刷怪的数量   0小怪  1中怪   2大怪   3宝箱*/
	public int monsterSize(int value){
		if (value==0) {
			return 3+(roleSize>40?roleSize/40:0);
		}else if (value==1) {
			return 1+monsterSize(0)/8;
		}else if (value==2||value==3) {
			return 1;
		}else if (value==4) {
			return 3+(roleSize>50?roleSize/50:0);
		}
		return 1;
	}
	/**刷怪*/
	public void open(int CI){
		if (I==3) {//上古刷宝箱
			if (CI%3!=0) {
				Boos boos=getBoos(297);//宝箱
				MonsterUtil.refreshMonsters(boos,null,null,132,0,SceneUtil.DNTGID,null);
			}
			return;
		}
		moveMap.clear();
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < 8; i++) {
//			0小怪 1中怪 2大怪  小于3 天庭方怪物
			if (i%4==2&&CI%3!=2) {continue;}
			Robots robot = GameServer.getAllRobot().get(i==0?"907":i==1?"909":i==2?"910":i==3?"919":i==4?"912":i==5?"914":i==6?"915":"920");
			if (robot==null) {continue;}
			int robotId=robot.getRobotID();
			int size=monsterSize(i%4);
			int hurt=1+((i==2||i==6)?1:0);
			if (buffer.length()!=0) {buffer.append("|");}
	        buffer.append(robot.getRobotID());
	        buffer.append("#");
	        buffer.append(robot.getRobotname());
	        buffer.append("#");
	        buffer.append(robot.getRobotskin());
	        buffer.append("#");
	        buffer.append(i<4?120:121);
	        int maxtime=30;
	        for (int j = 0; j < 3; j++) {
	        	int path=i<4?j+12:j+2;
	        	MapMonsterBean bean=getBuild(path);
				if (bean==null||bean.getHp().getHp()<=0) {continue;}
				MonsterMoveBase movePath=DNTGPaths[i<4?j:j+3];
				moveMap.put(movePath.getBh(), movePath);
				PathPoint point=movePath.getPoints().get(0); 
				for (int k = 0; k < size; k++) {
		    	    // 每个坐标对应的怪物的bean
		    		MapMonsterBean Bean1 = new MapMonsterBean();
				    Bean1.setX(point.getX()+MonsterUtil.getPY());
					Bean1.setY(point.getY()+MonsterUtil.getPY());
					Bean1.setRobotid(robotId);
					Bean1.setRobotname(robot.getRobotname());
					Bean1.setRobotskin(robot.getRobotskin());		
					Bean1.setRobotType(i<4?120:121);
					Bean1.setI(MonsterUtil.getIncreasesum());
					Bean1.setMap(mapIdZ);
					Bean1.setMaxtime(maxtime);
					Bean1.setSX(SceneUtil.DNTGID);
					Bean1.setMove(new MonsterMove(movePath, -1000*k, Bean1.getX(), Bean1.getY()));
					monsterMap.add(new DNTGMonster(Bean1,i<4?0:1,path,hurt));
					MonsterUtil.addEMonster(Bean1);
					MonsterUtil.monsterBuffer1(Bean1, buffer);
				}
			}
		}
		if (moveMap.size()!=0) {
			StringBuffer moveBuffer=new StringBuffer();
			moveBuffer.append("M");
			for (MonsterMoveBase move:moveMap.values()) {
				if (moveBuffer.length()>1) {moveBuffer.append("#");}
				moveBuffer.append(move.getMoveMsg());
			}
			moveBuffer.append("|");
			buffer=moveBuffer.append(buffer);
		}
		// 根据地图发送信息
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));	
		if (CI%10==0) {
			SendMessage.sendMessageToAllRoles(JG);		
		}
	}
	/**移动*/
	public void move(long time){
		StringBuffer buffer=null,buffer2=null;//野怪数据状态
		for (int i = monsterMap.size()-1; i >=0; i--) {
			DNTGMonster monster=monsterMap.get(i);
			if (monster.move()) {//到达终点
				monsterMap.remove(i);
				MonsterUtil.removeMonster2(monster.getBean());
				if (buffer==null) {buffer=new StringBuffer("M");}
				else {buffer.append("#");}
				buffer.append(monster.getBean().getI());
				buffer.append("^2");	
				//判断 扣谁的血
			    MapMonsterBean bean=getBuild(monster.getValue());
				if (bean!=null&&bean.getHp().getHp()>0) {
					int hp=bean.getHp().getHp()-monster.getHurt();
					if (hp<0) {hp=0;}
					bean.getHp().setHp(hp);
					XGHPRecord(monster.getValue());
				}
			}
		}
		if (time%3==0) {//每秒判断刷新一次血条    和双方科技值
			int v=XGHPRecord(-1);
			if (v!=0) {//有箭塔扣血
				for (int i=0;i<5;i++) {
					if (((v>>>i)&0x01)==1) {
						MapMonsterBean bean=getBuild(i);
						if (bean==null) {continue;}
						if (buffer==null) {buffer=new StringBuffer("M");}
						else {buffer.append("#");}
						buffer.append(bean.getI());
						buffer.append("^0^H");	
						buffer.append(bean.getHp().getHp());
						buffer.append(",");
						buffer.append(bean.getHp().getHpMax());	
						if (bean.getHp().getHp()<=20) {
							NChatBean nChatBean=new NChatBean();
							if (bean.getHp().getHp()==0) {
								nChatBean.setId(9);
								nChatBean.setMessage((bean.getRobotType()==101?"天庭大本营":bean.getRobotname())+"已被攻破");
							}else {
								nChatBean.setId(5);
								nChatBean.setMessage((bean.getRobotType()==101?"天庭大本营":bean.getRobotname())+"剩余血量:"+bean.getHp().getHp());
							}
							SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(nChatBean)));
						}
					}
					if (((v>>>(i+10))&0x01)==1) {
						MapMonsterBean bean=getBuild(i+10);
						if (bean==null) {continue;}
						if (buffer==null) {buffer=new StringBuffer("M");}
						else {buffer.append("#");}
						buffer.append(bean.getI());
						buffer.append("^0^H");	
						buffer.append(bean.getHp().getHp());
						buffer.append(",");
						buffer.append(bean.getHp().getHpMax());	
						if (bean.getHp().getHp()<=20) {
							NChatBean nChatBean=new NChatBean();
							if (bean.getHp().getHp()==0) {
								nChatBean.setId(9);
								nChatBean.setMessage((bean.getRobotType()==111?"花果山大本营":bean.getRobotname())+"已被攻破");
							}else {
								nChatBean.setId(5);
								nChatBean.setMessage((bean.getRobotType()==111?"花果山大本营":bean.getRobotname())+"剩余血量:"+bean.getHp().getHp());
							}
							SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(nChatBean)));
						}
					}
				}
				if (dntg_NV_Monster!=null) {
					if (((v>>>(20))&0x01)==1) {
						MapMonsterBean bean=dntg_NV_Monster.getBean();
						if (bean!=null) {
							if (buffer==null) {buffer=new StringBuffer("M");}
							else {buffer.append("#");}
							buffer.append(bean.getI());
							buffer.append("^0^H");	
							buffer.append(bean.getHp().getHp());
							buffer.append(",");
							buffer.append(bean.getHp().getHpMax());
						}		
					}
					if (((v>>>(21))&0x01)==1) {//天宫排行榜改变
						if (buffer2==null) {
							buffer2=new StringBuffer();
							buffer2.append(SceneUtil.DNTGID);
						}
						buffer2.append("|");
						buffer2.append(dntg_NV_Monster.getTTRanking().getRankingSting());
					}
	                if (((v>>>(22))&0x01)==1) {//花果山排行榜改变
	                	if (buffer2==null) {
							buffer2=new StringBuffer();
							buffer2.append(SceneUtil.DNTGID);
						}
	                	buffer2.append("|");
						buffer2.append(dntg_NV_Monster.getHGSRanking().getRankingSting());
					}
				}
				if (dntg_SG!=null) {
					if (((v>>>(25))&0x01)==1) {//天宫功勋改变
						if (buffer2==null) {
							buffer2=new StringBuffer();
							buffer2.append(SceneUtil.DNTGID);
						}
						buffer2.append("|S0");
						buffer2.append(dntg_SG.getTT_GX());
					}
					if (((v>>>(26))&0x01)==1) {//花果山功勋改变
                    	if (buffer2==null) {
    						buffer2=new StringBuffer();
    						buffer2.append(SceneUtil.DNTGID);
    					}
                    	buffer2.append("|S1");
    					buffer2.append(dntg_SG.getHGS_GX());
					}
				}
				if (((v>>>(23))&0x01)==1) {//天宫科技值改变
					if (buffer2==null) {
						buffer2=new StringBuffer();
						buffer2.append(SceneUtil.DNTGID);
					}
					buffer2.append("|K0");
					buffer2.append(TT.getKJValue());
				}
                if (((v>>>(24))&0x01)==1) {//花果山科技值改变
                	if (buffer2==null) {
						buffer2=new StringBuffer();
						buffer2.append(SceneUtil.DNTGID);
					}
					buffer2.append("|K1");
					buffer2.append(HGS.getKJValue());
				}
			}		
		}
		if (buffer!=null&&buffer.length()!=0) {
			SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().battleStateAgreement(buffer.toString()));
		}
		if (buffer2!=null&&buffer2.length()!=0) {
			String msg=Agreement.getAgreement().sceneAgreement(buffer2.toString());
			SendMessage.sendMessageToMapRoles(mapIdZ,msg);
			SendMessage.sendMessageToMapRoles(mapIdF,msg);
		}
	}
	/**修改状态*/
	public void upI(int ST){
		I=ST;
	}
	/**判断大闹是否结束 */
	public void isDNTG(long time){
		if (TT.getBuff()!=null&&TT.getBuff().isTime()) {
			TT.setBuff(null);
		}
        if (HGS.getBuff()!=null&&HGS.getBuff().isTime()) {
        	HGS.setBuff(null);
		}
		if (time<10800&&TT.getDBY().getHp().getHp()>0&&HGS.getDBY().getHp().getHp()>0) {
			//大本营还有血 活动不结束
			return;
		}
		if (I==4) {
			return;
		}
		I=4;
		NChatBean chatBean=new NChatBean();
		if (TT.getDBY().getHp().getHp()>HGS.getDBY().getHp().getHp()) {
			addQJJF(Camp_TT_Man, 0.25);
			addQJJF(Camp_HGS_Man,0.10);
			chatBean.setMessage("本次大闹天宫结束,获胜方:天庭");
		}else if (HGS.getDBY().getHp().getHp()>TT.getDBY().getHp().getHp()) {
			addQJJF(Camp_HGS_Man, 0.25);
			addQJJF(Camp_TT_Man,  0.10);
			chatBean.setMessage("本次大闹天宫结束,获胜方:花果山");
		}else {
			addQJJF(Camp_TT_Man, 0.15);
			addQJJF(Camp_HGS_Man,0.15);
			chatBean.setMessage("本次大闹天宫结束,平局收场");
		}
		chatBean.setId(4);
		String msg=Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
		SendMessage.sendMessageToMapRoles(mapIdZ,msg);		
		SendMessage.sendMessageToMapRoles(mapIdF,msg);
	}
	/***/
	public void removeMonsterBean(){
		StringBuffer buffer=new StringBuffer("M");
		TT.removeMapMonster(buffer);
		HGS.removeMapMonster(buffer);
	    if (dntg_NV_Monster!=null) {
			if (buffer.length()>1) {buffer.append("#");}
			buffer.append(dntg_NV_Monster.getBean().getI());
			buffer.append("^2");
			MonsterUtil.removeMonster2(dntg_NV_Monster.getBean());
		}
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().battleStateAgreement(buffer.toString()));
	}
	/**
	 * 活动状态   
	 * 0刷新女武神试炼倒计时
	 * 1女武神试炼开始 同时取消 女武神试炼倒计时
	 * 2关闭女武神试炼
	 * 3刷新上古战场倒计时
	 * 4上古战场开始     同时取消 上古战场倒计时
	 * 5关闭上古战场
	 */
	public void activity(int state){
		if (state==0) {
			countDown=new DNTGCountDown(0,30);
			StringBuffer buffer=new StringBuffer();
			buffer.append(SceneUtil.DNTGID);
			buffer.append("|D");
			countDown.toString(buffer);
			String msg=Agreement.getAgreement().sceneAgreement(buffer.toString());
			SendMessage.sendMessageToMapRoles(mapIdZ,msg);
			SendMessage.sendMessageToMapRoles(mapIdF,msg);
		}else if (state==1) {
			countDown=null;
			String msg=Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|D");
			SendMessage.sendMessageToMapRoles(mapIdZ,msg);
			SendMessage.sendMessageToMapRoles(mapIdF,msg);
			NVOpen();
		}else if (state==2) {
			NVEnd();
		}else if (state==3) {
			countDown=new DNTGCountDown(1,30);
			StringBuffer buffer=new StringBuffer();
			buffer.append(SceneUtil.DNTGID);
			buffer.append("|D");
			countDown.toString(buffer);
			String msg=Agreement.getAgreement().sceneAgreement(buffer.toString());
			SendMessage.sendMessageToMapRoles(mapIdZ,msg);
			SendMessage.sendMessageToMapRoles(mapIdF,msg);
		}else if (state==4) {
			countDown=null;
			String msg=Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|D");
			SendMessage.sendMessageToMapRoles(mapIdZ,msg);
			SendMessage.sendMessageToMapRoles(mapIdF,msg);
			SGOpen();
		}else if (state==5) {//上古战场关闭
			SGEnd();
		}
	}
	//101天庭大本营     102-104  天庭箭塔          105天庭集市      
	//111花果山大本营 112-114  花果山箭塔      115花果山集市  
	//120天庭进攻怪     121           花果山进攻怪   直接点击进入战斗
	//130女武神试炼     131           上古传送门      132上古宝箱
	//大本营    攻击
	//箭塔        攻击    送我进去  送我出去   
	//集市        生产    偷袭
	/**对话框显示*/
	public String getDialog(MapMonsterBean bean,LoginResult loginResult){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return null;}
		DNTGRole dntgRole=getRole(loginResult.getRole_id());
		if (dntgRole==null) {return null;}
		NPCDialog npcDialog=null;
        if (I==0) {
        	npcDialog=new NPCDialog();
        	npcDialog.setMsg("活动准备阶段");
		}else if (I==4) {
		  	npcDialog=new NPCDialog();
			npcDialog.setMsg("活动已结束");
			List<String> functions=new ArrayList<>();
			functions.add("我要回长安");
			npcDialog.setFunctions(functions);
		}else {
			int robotType=bean.getRobotType();
			if (robotType==101||robotType==111) {
				if (robotType==(dntgRole.getCamp()!=0?101:111)) {
					npcDialog=new NPCDialog();	
					List<String> functions=new ArrayList<>();
					functions.add("攻击");
					npcDialog.setFunctions(functions);
				}
			}else if ((robotType>=102&&robotType<=104) ||(robotType>=112&&robotType<=114)) {
		    	npcDialog=new NPCDialog();	
				List<String> functions=new ArrayList<>();
				if (bean.getHp()!=null&&bean.getHp().getHp()>0&&(robotType>=102&&robotType<=104?0:1)!=dntgRole.getCamp()) {
					functions.add("攻击");
				}else {
					functions.add("送我进去");
					functions.add("送我出去");
				}
				npcDialog.setFunctions(functions);
			}else if (robotType==130) {
				if (dntg_NV_Monster==null) {
					return null;
				}
				npcDialog=new NPCDialog();	
				List<String> functions=new ArrayList<>();
				functions.add("攻击");
				npcDialog.setFunctions(functions);
			}else if (robotType==131) {
				npcDialog=new NPCDialog();	
				List<String> functions=new ArrayList<>();
				functions.add("送我到上古战场");
				npcDialog.setFunctions(functions);
			}
		}
        if (npcDialog!=null) {
        	npcDialog.setType("M"+bean.getI());
			return Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
		}
		return null;
	}
	/**上交上古宝箱*/
	public String SGBX(LoginResult loginResult,String[] teams,int type){
		DNTGRole dntgRole=getRole(loginResult.getRole_id());
		if (dntgRole==null) {return null;}	
		if (dntgRole.getCamp()!=(type==42?0:1)) {
			return "我不是你们阵营的NPC";
		}
		MapMonsterBean bean=MonsterUtil.getFollowMonster(teams);
		if (bean==null||bean.getRobotType()!=132) {
			return "你没有上古宝箱";
		}
		MonsterUtil.removeMonster2(bean);
		if (dntg_SG!=null) {
			DNTGAward dntgAward=GameServer.getAllDntg(11);
			if (dntgAward!=null) {
				for (int i = 0; i < teams.length; i++) {
					ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
					LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
					DNTGRole role=log!=null?getRole(log.getRole_id()):null;
					if (role!=null) {
						AssetUpdate assetUpdate=upDNTGAward(dntgAward, role, null, i);
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
					}
				}
			}
			if (!dntg_SG.isEnd()) {
				SGEnd();
			}
		}
		return null;
	}
	public String isJG(String[] teams){
		long time=System.currentTimeMillis();
		DNTGRole[] roleids=new DNTGRole[teams.length];
		for (int i = 0; i < teams.length; i++) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
			LoginResult login=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			DNTGRole role=login!=null?getRole(login.getRole_id()):null;
			String value=role!=null?role.isTime(time):null;
			if (value!=null) {
				return value;
			}
			roleids[i]=role;
		}
		for (int i = 0; i < roleids.length; i++) {
			if (roleids[i]!=null) {
				roleids[i].setTime(time);
			}
		}
		return null;
	}
	/**选项的功能响应*/
	public void Function(MapMonsterBean bean,LoginResult loginResult,String msg){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return;}
		DNTGRole dntgRole=getRole(loginResult.getRole_id());
		if (dntgRole==null) {return;}	
		int robotType=bean.getRobotType();
		String prompt=null;
		if (msg.equals(functions[0])) {//攻击    101  102-104   111  112-114  120 121   130
			if (robotType==101||robotType==111||robotType==120||robotType==121) {
				if (dntgRole.getCamp()!=(robotType==101||robotType==120?0:1)) {//可攻击
					if (robotType==101&&TT.getBoosXS()==3) {
						prompt="最少需要破一个箭塔才能攻击首领";
					}else if (robotType==111&&HGS.getBoosXS()==3) {
						prompt="最少需要破一个箭塔才能攻击首领";
					}
					prompt=isJG(teams);
					if (prompt==null) {
						BattleThreadPool.addDNTGBattle(loginResult,teams,bean,getMonsterXS(bean));	
					}	
				}else {
					prompt="不可攻击同阵营";
				}
			}else if ((robotType>=102&&robotType<=104)
					||(robotType>=112&&robotType<=114)) {
				if (bean.getHp()==null||bean.getHp().getHp()<=0) {
					prompt="该箭塔已被攻破";
				}else if ((robotType>=102&&robotType<=104?0:1)==dntgRole.getCamp()) {
					prompt="不可攻击同阵营";
				}else {//可攻击
					prompt=isJG(teams);
					if (prompt==null) {
						BattleThreadPool.addDNTGBattle(loginResult,teams,bean,getMonsterXS(bean));	
					}	
				}
			}else if (robotType==130) {
				if (dntg_NV_Monster==null||dntg_NV_Monster.getBean()==null||dntg_NV_Monster.getBean().getHp().getHp()<=0) {//可攻击
					prompt="活动已经结束";
				}else {
					prompt=isJG(teams);
					if (prompt==null) {
						BattleThreadPool.addDNTGBattle(loginResult,teams,bean,1);	
					}	
				}
			}
		}else if (msg.equals(functions[1])) {//送我到上古战场
			
		}else if (msg.equals(functions[2])||msg.equals(functions[3])) {//送我进去  送我出去
			int value=getValue(bean);
			boolean is=(msg.equals(functions[2]));
			Door door=null;
			if (value==2) {
				door=GameServer.getDoor(is?509:508);
			}else if (value==3) {
				door=GameServer.getDoor(is?511:510);
			}else if (value==4) {
				door=GameServer.getDoor(is?513:512);
			}else if (value==12) {
				door=GameServer.getDoor(is?521:520);
			}else if (value==13) {
				door=GameServer.getDoor(is?519:518);
			}else if (value==14) {
				door=GameServer.getDoor(is?517:516);	
			}
            if (door!=null) {
				ChangeMapBean changeMapBean=new ChangeMapBean();
				changeMapBean.setMapid(door.getDoormap());
				String[] vs=door.getDoorpoint().split("\\|");
				changeMapBean.setMapx(Integer.parseInt(vs[0]));
				changeMapBean.setMapy(Integer.parseInt(vs[1]));
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(loginResult.getRolename());
				if (ctx!=null) {
					ChangeMapAction.ChangeMap(changeMapBean,ctx);	
				}
			}
		}else if (msg.equals(functions[4])) {//我要回长安
			ChangeMapBean changeMapBean=new ChangeMapBean();
			changeMapBean.setMapid("1207");
			changeMapBean.setMapx(4294);
			changeMapBean.setMapy(2887);
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(loginResult.getRolename());
			if (ctx!=null) {
				ChangeMapAction.ChangeMap(changeMapBean,ctx);	
			}
		}
		if (prompt!=null) {
			SendMessage.sendMessageByRoleName(loginResult.getRolename(), Agreement.getAgreement().PromptAgreement(prompt));
		}
	}	
	@Override
	public int battleEnd(BattleEnd battleEnd,LoginResult loginResult,MapMonsterBean bean,int v) {
		// TODO Auto-generated method stub
		DNTGRole dntgRole=getRole(loginResult.getRole_id());
		if (dntgRole==null) {return 0;}
		if (v==0||v==1) {//胜利
			if (I==4) {
				return 0;
			}
			if (bean!=null) {
				DNTGAward award=getDNTGAward(bean);
				if (award!=null) {
					battleEnd.setAssetUpdate(upDNTGAward(award,dntgRole, battleEnd.getAssetUpdate(),v));
					if (dntg_SG!=null&&!dntg_SG.isEnd()) {
						SGEnd();
					}
				}
			}else if (dntg_SG!=null) {
				DNTGAward award=GameServer.getAllDntg(12);
				if (award!=null) {
					battleEnd.setAssetUpdate(upDNTGAward(award,dntgRole, battleEnd.getAssetUpdate(),v));
					if (!dntg_SG.isEnd()) {
						SGEnd();
					}
				}
			}	
		}else {
			dntgRole.setTime(System.currentTimeMillis());
			if (v==2) {
				if (loginResult.getMapid()==mapIdZ) {
					return dntgRole.getCamp()==0?522:523;//传送回营地	
				}else if (loginResult.getMapid()==mapIdF) {
					return dntgRole.getCamp()==0?524:525;//传送回营地		
				}	
			}
		}
		return 0;
	}
	/**奖励处理*/
	public AssetUpdate upDNTGAward(DNTGAward award,DNTGRole dntgRole,AssetUpdate assetUpdate,int v){
		if (assetUpdate==null) {
			assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		}
		if (award.getAddKJValue()!=0) {
            if (v==0) {
				if (dntgRole.getCamp()==0) {
					TTKJValue(award.getAddKJValue());
				}else {
					HGSKJValue(award.getAddKJValue());
				}
			}
			assetUpdate.upmsg("为乙方阵营添加#R"+award.getAddKJValue()+"#Y点科技值");			
		}
		if (award.getStealKJValue()!=0) {
			if (v==0) {
            	if (dntgRole.getCamp()==0) {
					HGSKJValue(-award.getStealKJValue());
				}else {
					TTKJValue(-award.getStealKJValue());
				}
			}
			assetUpdate.upmsg("偷取对方阵营#R"+award.getAddKJValue()+"#Y点科技值");	
		}
		if (award.getAddGX()!=0) {
			if (v==0) {
            	if (dntgRole.getCamp()==0) {
            		TTGXValue(award.getStealKJValue());
				}else {
            		HGSGXValue(award.getStealKJValue());
				}
			}
			assetUpdate.upmsg("为乙方阵营添加#R"+award.getStealKJValue()+"#Y点功勋");	
		}
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(dntgRole.getRoleName());
		LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
		RoleData roleData=log!=null?RolePool.getRoleData(log.getRole_id()):null;
		UseCardBean limit=roleData!=null?roleData.getLimit(DNTGBUFF):null;
		StringBuffer buffer=null;
		if (award.getAddJ()!=0) {
			int add=award.getAddJ();
			if (limit!=null&&limit.getValue().indexOf("积分")!=-1) {
				add*=1.1;
			}
			dntgRole.addDNJF(add);
			assetUpdate.upmsg("获取了"+add+"积分");
            if (buffer==null) {
            	buffer=new StringBuffer();
            	buffer.append(SceneUtil.DNTGID);
			}
			buffer.append("|J");
			buffer.append(dntgRole.getDNJF());
		}
		if (award.getAddM()!=0) {
			int add=award.getAddM();
			if (limit!=null&&limit.getValue().indexOf("金币")!=-1) {
				add*=1.2;
			}
			dntgRole.addDNJB(add);
			assetUpdate.upmsg("获取了"+add+"金币");	
			if (buffer==null) {
            	buffer=new StringBuffer();
            	buffer.append(SceneUtil.DNTGID);
			}
			buffer.append("|M");
			buffer.append(dntgRole.getDNJB());
		}
		if (buffer!=null) {
			assetUpdate.setSceneMsg(buffer.toString());
		}
		return assetUpdate;
	}
	/**获取奖励*/
	public DNTGAward getDNTGAward(MapMonsterBean bean){
		//101天庭大本营     102-104  天庭箭塔          105天庭集市      
		//111花果山大本营 112-114  花果山箭塔      115花果山集市  
		//120天庭进攻怪     121           花果山进攻怪   
		//130女武神试炼     131           上古传送门      132上古宝箱
		if (bean.getRobotType()==120||bean.getRobotType()==121) {
			if (bean.getRobotid()==910||bean.getRobotid()==915) {
				return GameServer.getAllDntg(5);		
			}else if (bean.getRobotid()==909||bean.getRobotid()==914) {
				return GameServer.getAllDntg(4);		
			}else {
				return GameServer.getAllDntg(3);
			}
		}else if (bean.getRobotType()==130) {
			return GameServer.getAllDntg(7);
		}else if (bean.getRobotType()==132) {
			return GameServer.getAllDntg(10);
		}else if (bean.getRobotType()==101||bean.getRobotType()==111) {
			return GameServer.getAllDntg(6);
		}else if (bean.getRobotType()>=102&&bean.getRobotType()<=115) {
			return GameServer.getAllDntg(1);
		}
		return null;
	}
	/**修改阵营科技值*/
	public synchronized void TTKJValue(int value){
		TT.addKJValue(value);
		XGHPRecord(23);
	}
    public synchronized void HGSKJValue(int value){
    	HGS.addKJValue(value);
    	XGHPRecord(24);
	}
    /**添加上古功勋*/
	public synchronized void TTGXValue(int value){
		if (dntg_SG!=null) {
			dntg_SG.setTT_GX(dntg_SG.getTT_GX()+value);
			XGHPRecord(25);
		}
	}
    public synchronized void HGSGXValue(int value){
    	if (dntg_SG!=null) {
			dntg_SG.setHGS_GX(dntg_SG.getHGS_GX()+value);
			XGHPRecord(26);
		}
	}
	//101天庭大本营     102-104  天庭箭塔          105天庭集市      
	//111花果山大本营 112-114  花果山箭塔      115花果山集市  
	//120天庭进攻怪     121           花果山进攻怪   
	//130女武神试炼     131           上古传送门      132上古宝箱
    @Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
    	MapMonsterBean bean=battleData.getMonsterBean();
    	if (type==2) {
    		if (bean.getRobotType()==120||bean.getRobotType()==121) {
    			if (buffer.length()!=0) {buffer.append("|");}
    			buffer.append("M");
    			buffer.append(bean.getI());
    			buffer.append("^2");
				for (int i = monsterMap.size() - 1; i >= 0; i--) {
					DNTGMonster monster = monsterMap.get(i);
					if (monster.getBean().getI().intValue() != bean.getI().intValue()) {continue;}
					monsterMap.remove(i);
					MonsterUtil.removeMonster2(bean);
				}
    		}else if (bean.getFollow()!=null) {
				if (bean.getFollow().getTime() == 0) {bean.getFollow().setTime(1);} 
				else {bean.getFollow().setTime(System.currentTimeMillis());}
				bean.setType(0);
				ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[0]);
				LoginResult log2=ctx2!=null?GameServer.getAllLoginRole().get(ctx2):null;
				if (log2!=null) {
					bean.getFollow().setFollowID(log2.getRole_id());
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("M");
					buffer.append(bean.getI());
					buffer.append("^");
					buffer.append(bean.getType());
					buffer.append("^G");
					buffer.append(log2.getRole_id());
					StringBuffer bufferTwo = new StringBuffer();
					bufferTwo.append("#R");
					bufferTwo.append(log2.getRolename());
					bufferTwo.append("#Y在");
					bufferTwo.append(GameServer.getMapName(log2.getMapid().toString()));
					bufferTwo.append("(");
					bufferTwo.append(log2.getX() / 20);
					bufferTwo.append(",");
					bufferTwo.append(log2.getY() / 20);
					bufferTwo.append(")抢到#R");
					bufferTwo.append(bean.getRobotname());
					DNTGRole dntgRole = getRole(log2.getRole_id());
					if (dntgRole != null) {
						bufferTwo.append(",正在前往");
						bufferTwo.append(dntgRole.getCamp() == 0 ? "天庭": "花果山");
						bufferTwo.append("营地邀功");
					}
					NChatBean chatBean = new NChatBean();
					chatBean.setId(9);
					chatBean.setMessage(bufferTwo.toString());
					SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
				}
			}else if (bean.getRobotType()==130) {
    			if (dntg_NV_Monster!=null&&dntg_NV_Monster.getBean().getHp().getHp()>0) {
					int hp=bean.getHp().getHp()-1;
					if (hp<0) {hp=0;}
					bean.getHp().setHp(hp);
					XGHPRecord(20);
					ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[0]);
					LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
					if (log!=null) {
						boolean is=false;
						DNTGRole dntgRole=getRole(log.getRole_id());
						if (dntgRole!=null) {
							DNTG_NV_Ranking ranking=dntgRole.getCamp()==0?dntg_NV_Monster.getTTRanking():dntg_NV_Monster.getHGSRanking();
							is=ranking.upRanking(dntgRole);
	                        StringBuffer bufferTwo=new StringBuffer();
							bufferTwo.append(SceneUtil.DNTGID);
							bufferTwo.append("|N2");
							bufferTwo.append(dntgRole.getNVNum());
							bufferTwo.append("$");
							if (is) {
							    bufferTwo.append(ranking.getPlace(dntgRole));
							    XGHPRecord(dntgRole.getCamp()==0?21:22);
							}else {
								bufferTwo.append("0");
							}
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().sceneAgreement(bufferTwo.toString()));
						}
					}
					if (hp<=0) {
						StringBuffer bufferTwo=new StringBuffer();
						bufferTwo.append(bean.getRobotname());
						bufferTwo.append("已被#R");
						bufferTwo.append(teams[0]);
						bufferTwo.append("#Y所带领的队伍完成最后一击");
						NChatBean chatBean=new NChatBean();
						chatBean.setId(9);
						chatBean.setMessage(bufferTwo.toString());
						SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
						DNTGAward award=GameServer.getAllDntg(8);
						if (award!=null) {
							for (int j = 0; j < teams.length; j++) {
								ChannelHandlerContext ctx1=j==0?ctx:GameServer.getRoleNameMap().get(teams[j]);
								if (ctx1==null) {continue;}
								LoginResult log1=j==0?log:GameServer.getAllLoginRole().get(ctx1);
								if (log1==null) {continue;}
								DNTGRole dntgRole=getRole(log1.getRole_id());
								if (dntgRole!=null) {
								    AssetUpdate assetUpdate=upDNTGAward(award, dntgRole, null, j);
									SendMessage.sendMessageToSlef(ctx1,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
								}						
							}
						}
						NVEnd();
					}else {
						if (buffer.length()!=0) {buffer.append("|");}
						buffer.append("M");
						buffer.append(bean.getI());
						buffer.append("^");
						buffer.append(bean.getType());
						buffer.append("^H");
						buffer.append(bean.getHp().getHp());
						buffer.append(",");
						buffer.append(bean.getHp().getHpMax());
					}
    			}
			} else if (bean.getRobotType() >= 101 && bean.getRobotType() <= 115) {
				if (bean.getHp().getHp() > 0) {
					int hurt = 1;
					ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(teams[0]);
					LoginResult log = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
					RoleData roleData = log != null ? RolePool.getRoleData(log.getRole_id()) : null;
					UseCardBean limit = roleData != null ? roleData.getLimit(DNTGBUFF) : null;
					if (limit != null && limit.getValue().indexOf("建筑物") != -1) {hurt += 2;}
					int hp = bean.getHp().getHp() - hurt;
					if (hp < 0) {hp = 0;}
					bean.getHp().setHp(hp);
					int value = getValue(bean);
					XGHPRecord(value);
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("M");
					buffer.append(bean.getI());
					buffer.append("^");
					buffer.append(bean.getType());
					buffer.append("^H");
					buffer.append(bean.getHp().getHp());
					buffer.append(",");
					buffer.append(bean.getHp().getHpMax());
				}
			}else {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^2");
				MonsterUtil.removeMonster2(bean);
			}
    	}else if (type==1) {
    		if (bean.getHp()==null||!bean.getHp().isMuch()) {
    			bean.setType(type);
    			if (bean.getMove()!=null) {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("M");
					buffer.append(bean.getI());
					buffer.append("^1");
				}
			}
		}else if (type==0) {
			if (bean.getHp()==null||!bean.getHp().isMuch()) {
				bean.setType(type);
				if (bean.getMove()!=null) {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("M");
					buffer.append(bean.getI());
					buffer.append("^0");
				}
			}
		}
		return null;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		if (DNTGScene.mapIdZ==mapId) {
			TT.getMapMonster(buffer);
			HGS.getMapMonster(buffer);
			if (dntg_NV_Monster!=null) {
				MapMonsterBean bean=dntg_NV_Monster.getBean();
				moveMap=MonsterUtil.monsterBuffer(bean, buffer, moveMap);
			}
			for (int i = monsterMap.size()-1; i >=0; i--) {
				DNTGMonster dntgMonster=monsterMap.get(i);
				MapMonsterBean bean=dntgMonster.getBean();
				moveMap=MonsterUtil.monsterBuffer(bean, buffer, moveMap);
			}
		}
		return moveMap;
	}

	/**
	 * 修改扣血记录   -1是清空记录 范围 清空前的记录
	 * 20 女武神 
	 * 21 女武神天宫排行榜改变
	 * 22 女武神花果山排行榜改变
	 * 23 天宫    科技值改变
 	 * 24 花果山科技值改变
 	 * 25 天宫    功勋改变
 	 * 26 花果山功勋改变
	 */
	public synchronized int XGHPRecord(int v){
		if (v==-1) {
			v=HPRecord;
			HPRecord=0;
			return v;
		}
		HPRecord=HPRecord|(1<<v);
		return HPRecord;
	}
	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		if (I==4) {return false;}
		return true;
	}

	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * //		C 所属阵营
//		M 金币
//		J 积分
//		L 神力加持数据
//      K 科技值 
//		D 倒计时数据
//		N 女武神试炼数据
//		S 上古数据
//		"!", "|", "*", "&", "@", "#", "$", "%", "^", "/" 
//		|  &   $
//		SceneID|C0|M123|J123|LID$LVL&ID$LVL|DTYPE&TIME|N2(玩家自身数据)击杀次数$名次|N0/1(阵营排行榜)名称$击杀次数&名称$击杀次数
	 * */
	@Override
	public String getSceneMsg(LoginResult loginResult, long oldMapId, long mapId) {
		// TODO Auto-generated method stub
		DNTGRole dntgRole=getRole(loginResult.getRole_id());
		if (dntgRole==null) {return null;}
		StringBuffer buffer=new StringBuffer();
		buffer.append(SceneUtil.DNTGID);
		if (oldMapId!=DNTGScene.mapIdZ&&oldMapId!=DNTGScene.mapIdF) {
			buffer.append("|C");
			buffer.append(dntgRole.getCamp());
			buffer.append("|M");
			buffer.append(dntgRole.getDNJB());
			buffer.append("|J");
			buffer.append(dntgRole.getDNJF());		
			buffer.append("|K0");
			buffer.append(TT.getKJValue());	
			buffer.append("|K1");
			buffer.append(HGS.getKJValue());	
			if (countDown!=null) {//倒计时数据
				buffer.append("|D");
				countDown.toString(buffer);
			}
		}
		if (mapId==DNTGScene.mapIdZ) {
			if (oldMapId==DNTGScene.mapIdF) {clearMap(buffer,1);}
			//填充女武神试炼数据
			if (dntg_NV_Monster!=null) {
				dntg_NV_Monster.toString(dntgRole, buffer);
			}
		}else if (mapId==DNTGScene.mapIdF) {
            if (oldMapId==DNTGScene.mapIdZ) {clearMap(buffer,0);}	
			//填充上古战场数据
            if (dntg_SG!=null) {
				dntg_SG.toString(buffer);
			}
		}
		return buffer.toString();
	}
    /**清除展示*/
	public void clearMap(StringBuffer buffer,int type){
		if (type==0) {//清空 主战场的展示
			buffer.append("|N");
		}else if (type==1) {//清空 上古战场的展示
			buffer.append("|S");
		}
	}
	/**神力加持学习*/
	public void learnSLJC(LoginResult login,Skill skill){
		if (!isEnd()) {//活动已经结束
			return;
		}
		DNTGRole dntgRole=getRole(login.getRole_id());
		RoleData roleData=RolePool.getRoleData(login.getRole_id());
		if (dntgRole==null||roleData==null) {
			return;
		}
		
		String msg=dntgRole.upSLJC(skill,dntgRole.getCamp()==0?TT.getKJValue():HGS.getKJValue());
		if (msg!=null) {
			SendMessage.sendMessageByRoleName(login.getRolename(), Agreement.getAgreement().PromptAgreement(msg));
			return;
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(SceneUtil.DNTGID);
		buffer.append("|M");
		buffer.append(dntgRole.getDNJB());
		buffer.append("|L");
		buffer.append(dntgRole.getSLJC());
		SendMessage.sendMessageByRoleName(login.getRolename(),Agreement.getAgreement().sceneAgreement(buffer.toString()));
	}
	/**女武神活动开启*/
	public synchronized void NVOpen(){
		if (I==4) {
			return;
		}
		I=2;
		dntg_NV_Monster=new DNTG_NV_Monster();
		MapMonsterBean bean=dntg_NV_Monster.getBean();
        String msg=Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|N20$0");
		SendMessage.sendMessageToMapRoles(mapIdZ,msg);
		
		StringBuffer buffer=new StringBuffer();
	    buffer.append(bean.getRobotid());
	    buffer.append("#");
	    buffer.append(bean.getRobotname());
	    buffer.append("#");
	    buffer.append(bean.getRobotskin());
	    buffer.append("#");
	    buffer.append(bean.getRobotType());
	    MonsterUtil.monsterBuffer1(bean, buffer);
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));
		NChatBean chatBean=new NChatBean();
		chatBean.setId(9);
		chatBean.setMessage("女武神活动开启了");
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));	
	}
	/**女武神活动结束*/
	public synchronized void NVEnd(){
		if (dntg_NV_Monster==null) {
			return;
		}
		if (I==4) {
			return;
		}
		int end=dntg_NV_Monster.end();
		NChatBean chatBean=new NChatBean();
		chatBean.setId(9);
		StringBuffer buffer=new StringBuffer();
		buffer.append("女武神试炼结束,");	
		ConcurrentHashMap<BigDecimal,DNTGRole> map=null;
		DNTGBuff buff=null;
		String[] teams=null;
		if (end==-1) {
			buffer.append("平局");	
		}else if (end==0) {
			buff=new DNTGBuff(Battlefield.random.nextInt(3),60);
			TT.setBuff(buff);
			map=Camp_TT_Man;
			buffer.append("获胜方:天宫,获得大闹先锋BUFF:");	
			buffer.append(buff.toString());	
		}else if (end==1) {
			buff=new DNTGBuff(Battlefield.random.nextInt(3),60);
			HGS.setBuff(buff);
			map=Camp_HGS_Man;
			buffer.append("获胜方:花果山,获得大闹先锋BUFF:");	
			buffer.append(buff.toString());	
		}
		DNTGRole TTRole=dntg_NV_Monster.getTTRanking().getOne();
		if (TTRole!=null) {
			buffer.append(",天庭击杀榜第一队伍:#R");
			buffer.append(TTRole.getRoleName());
			buffer.append("#Y,获得大闹先锋统帅BUFF");
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(TTRole.getRoleName());
			LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log!=null) {
				String[] team=log.getTeam().split("\\|");
				for (int i = 0; i < team.length; i++) {
					ctx=GameServer.getRoleNameMap().get(team[i]);
					log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
					RoleData roleData=log!=null?RolePool.getRoleData(log.getRole_id()):null;
					if (roleData!=null) {
						roleData.addLimit(buff.getUseCardBean());
						SendMessage.sendMessageToSlef(ctx,buff.getSendCard());
					}
				}
				if (end==0) {
					teams=team;
				}
			}
		}
		DNTGRole HGSRole=dntg_NV_Monster.getHGSRanking().getOne();
        if (HGSRole!=null) {
        	buffer.append(",花果山击杀榜第一队伍:#R");
			buffer.append(HGSRole.getRoleName());
			buffer.append("#Y,获得大闹先锋统帅BUFF");
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(HGSRole.getRoleName());
			LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log!=null) {
				String[] team=log.getTeam().split("\\|");
				for (int i = 0; i < team.length; i++) {
					ctx=GameServer.getRoleNameMap().get(team[i]);
					log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
					RoleData roleData=log!=null?RolePool.getRoleData(log.getRole_id()):null;
					if (roleData!=null) {
						roleData.addLimit(buff.getUseCardBean());
						SendMessage.sendMessageToSlef(ctx,buff.getSendCard());
					}
				}
				if (end==1) {
					teams=team;
				}
			}
		}
		chatBean.setMessage(buffer.toString());
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
		if (buff!=null&&map!=null) {
			Iterator<Entry<BigDecimal, DNTGRole>> entries = map.entrySet().iterator();
			s:while (entries.hasNext()) {
				Entry<BigDecimal, DNTGRole> entrys = entries.next();
				DNTGRole dntgRole=entrys.getValue();
				if (teams!=null) {
					for (int i = 0; i < teams.length; i++) {
						if (teams[i].equals(dntgRole.getRoleName())) {
							continue s;
						}
					}
				}
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(dntgRole.getRoleName());
				LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
				RoleData roleData=log!=null?RolePool.getRoleData(log.getRole_id()):null;
				if (roleData!=null) {
					roleData.addLimit(buff.getUseCardBean());
					SendMessage.sendMessageToSlef(ctx,buff.getSendCard());
				}
			}
		}
		I=1;
		MonsterUtil.removeMonster2(dntg_NV_Monster.getBean());
		SendMessage.sendMessageToMapRoles(dntg_NV_Monster.getBean().getMap(),Agreement.getAgreement().battleStateAgreement("M"+dntg_NV_Monster.getBean().getI()+"^2"));
		dntg_NV_Monster=null;
		String msg=Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|N");
		SendMessage.sendMessageToMapRoles(mapIdZ,msg);
	}
	/**上古活动开启*/
	public synchronized void SGOpen(){
		if (I==4) {
			return;
		}
		dntg_SG=new DNTG_SG();
		NChatBean chatBean=new NChatBean();
		chatBean.setId(9);
		chatBean.setMessage("上古试炼开启了,请玩家前往各自营地通过上古传送门进入上古战场,上古试炼开启期间主战场静止");
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
		I=3;
	}
	/**上古活动结束*/
	public synchronized void SGEnd(){
		if (dntg_SG==null) {
			return;
		}
		if (I==4) {
			return;
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("上古试炼结束,");
		ConcurrentHashMap<BigDecimal,DNTGRole> map=null;
		double xs=0D;
		int end=dntg_SG.getEnd();
		if (end==-1) {
			buffer.append("平局");	
		}else if (end==0) {
			TTKJValue(1000);
			map=Camp_TT_Man;
			buffer.append("获胜方:天宫,奖励当前积分加百分之10科技值加1000");
			xs=0.1D;			
		}else if (end==1) {
			HGSKJValue(1000);
			map=Camp_HGS_Man;
			buffer.append("获胜方:花果山,奖励当前积分加百分之10科技值加1000");	
			xs=0.1D;
		}
		if (map!=null&&xs!=0) {
			addQJJF(map, xs);
		}
		NChatBean chatBean=new NChatBean();
		chatBean.setId(9);
		chatBean.setMessage(buffer.toString());
		SendMessage.sendMessageToMapRoles(mapIdZ,Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));		
		I=1;
		dntg_SG=null;
		String msg=Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|S");
		SendMessage.sendMessageToMapRoles(mapIdZ,msg);
		
	}
	/**添加积分*/
	public void addQJJF(ConcurrentHashMap<BigDecimal,DNTGRole> map,double xs){
		Iterator<Entry<BigDecimal, DNTGRole>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<BigDecimal, DNTGRole> entrys = entries.next();
			DNTGRole dntgRole=entrys.getValue();
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(dntgRole.getRoleName());
			LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log!=null) {
				int add=(int) (dntgRole.getDNJF()*xs);
				if (add!=0) {
					if (add>100000) {add=100000;}
					dntgRole.addDNJF(add);
					if (log.getMapid()==mapIdZ||log.getMapid()==mapIdF) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().sceneAgreement(SceneUtil.DNTGID+"|J"+dntgRole.getDNJF()));	
					}
				}
			}
		}
	}
	public int getI() {
		return I;
	}	
	/**根据科技值获取怪物加强程度*/
	public double getMonsterXS(MapMonsterBean bean){
		//101天庭大本营     102-104  天庭箭塔          105天庭集市      
		//111花果山大本营 112-114  花果山箭塔      115花果山集市  
		//120天庭进攻怪     121           花果山进攻怪   
		//130女武神试炼     131           上古传送门      132上古宝箱
		double xs=1D;
		if (bean.getRobotType()==120||bean.getRobotType()>=101&&bean.getRobotType()<=105) {
			int kjv=TT.getKJValue()/80;
			if (kjv>=300) {kjv=300;}
			xs+=kjv*0.01;
			if (bean.getRobotType()==101) {xs+=TT.getBoosXS();}
		}else if (bean.getRobotType()==121||bean.getRobotType()>=111&&bean.getRobotType()<=115) {
			int kjv=HGS.getKJValue()/80;
			if (kjv>=300) {kjv=300;}
			xs+=kjv*0.01;
			if (bean.getRobotType()==111) {xs+=HGS.getBoosXS();}
		}
		return xs;
	}
}
