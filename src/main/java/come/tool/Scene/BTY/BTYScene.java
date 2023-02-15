package come.tool.Scene.BTY;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.PathPoint;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.Dorp;
import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMove;
import org.come.task.MonsterMoveBase;
import org.come.task.MonsterUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Battle.RewardLimit;
import come.tool.Good.DropUtil;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;

public class BTYScene implements Scene{

	//状态标识  0未开启 1开启 2  3胜利 4失败
 	private int I;
 	//当前波数
 	private int CI;
 	//开启时间
 	public long time;
 	public static final long mapId=3324;
 	private int BTYHP=30;
 	private ConcurrentHashMap<String,BTYRole> roleMap;
	private List<BTYMonster> monsterMap;
	private BTYThread btyThread;
	//刷怪点
	private MonsterMoveBase[] btyPaths;
	public BTYScene() {
		// TODO Auto-generated constructor stub
		init();	
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage("守卫蟠桃园活动开启");
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);	
		I=1;
		CI=0;
		btyThread = new BTYThread(this);
		Thread T1 = new Thread(btyThread);
		T1.start();
	}
	/**判断结束*/
	@Override
	public boolean isEnd(){
		if (I==4||I==3) {
			return false;
		}else if (CI==20&&monsterMap.size()==0) {//胜利
			// 发起世界喊话
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("守卫蟠桃园活动胜利,到相关NPC领取奖励");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);	
			I=3;
			return false;
		}else if (BTYHP<=0) {//失败
			Empty();
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("守卫蟠桃园活动失败,到相关NPC领取奖励");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);	
			I=4;
			return false;
		}
		return true;
	}
	/**路径起点初始化*/
    public void init(){
    	roleMap=new ConcurrentHashMap<>();
    	monsterMap=new ArrayList<>();
    	btyPaths=new MonsterMoveBase[4];
    	for (int i = 0; i < btyPaths.length; i++) {
    		btyPaths[i]=new MonsterMoveBase(i);
		}
    }
	/**移动*/
	public void move(){
		int size=0;
		StringBuffer buffer=null;
		for (int i = monsterMap.size()-1; i >=0; i--) {
			BTYMonster monster=monsterMap.get(i);
			if (monster.move()) {//到达终点
				monsterMap.remove(i);
				MonsterUtil.removeMonster2(monster.getBean());
				if (buffer==null) {buffer=new StringBuffer("M");}
				else {buffer.append("#");}
				buffer.append(monster.getBean().getI());
				buffer.append("^2");	
				if (BTYHP>0) {size++;BTYHP--;}
			}
		}
		if (buffer!=null) {
			SendMessage.sendMessageToMapRoles(mapId,Agreement.getAgreement().battleStateAgreement(buffer.toString()));
		}
		if (size!=0) {// 发起世界喊话
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("被"+size+"只怪物突破防线,剩余蟠桃数量"+BTYHP);
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapId,msg);	
		}
	}
//	/**刷怪*/
	public void open(){
	    if (CI>=20) {return;}
	    CI++;
	    Boos boos=getBoos();
	    Robots robot = GameServer.getAllRobot().get(boos.getBoosrobot());
	    int robotId=robot.getRobotID();
	    int size=boos.getBoosnum();
	    try {
	        int y=0;
	    	y=(GameServer.getMapRolesMap().get(mapId).size()-20)/10;
		    if (y>0) {size*=(1+y*0.1);}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    StringBuffer buffer=new StringBuffer("M");
	    for (int j=0;j<btyPaths.length;j++) {
	    	MonsterMoveBase moveBase=btyPaths[j];
	    	if (buffer.length()>1) {buffer.append("#");}
	    	buffer.append(moveBase.getMoveMsg());
	    }
	    buffer.append("|");  
        buffer.append(robot.getRobotID());
        buffer.append("#");
        buffer.append(robot.getRobotname());
        buffer.append("#");
        buffer.append(robot.getRobotskin());
        buffer.append("#");
        buffer.append(robot.getRobotType());
        int maxtime=boos.getBoosetime();
        for (int j=0;j<btyPaths.length;j++) {
        	MonsterMoveBase moveBase=btyPaths[j];
        	PathPoint point =moveBase.getPoints().get(0); 
        	for (int i = 0; i < size; i++) {
        		MapMonsterBean bean1 = new MapMonsterBean();
        		bean1.setX(point.getX()+MonsterUtil.getPY());
        		bean1.setY(point.getY()+MonsterUtil.getPY());
        		bean1.setRobotid(robotId);
        		bean1.setRobotname(robot.getRobotname());
        		bean1.setRobotskin(robot.getRobotskin());		
        		bean1.setRobotType(robot.getRobotType());
        		bean1.setI(MonsterUtil.getIncreasesum());
        		bean1.setMap(mapId);
        		bean1.setMaxtime(maxtime);
        		bean1.setSX(SceneUtil.BTYID);
        		bean1.setMove(new MonsterMove(moveBase, -1000*i, bean1.getX(), bean1.getY()));
				monsterMap.add(new BTYMonster(bean1,CI));
				MonsterUtil.addEMonster(bean1);
			    MonsterUtil.monsterBuffer1(bean1, buffer);
			    if (i==0) {bean1.setBoosId(RewardLimit.isBoosDrop(boos));}
        	}
        }
		SendMessage.sendMessageToMapRoles(mapId, Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));
		MonsterUtil.boosChat(boos,null,null,null);
	}
	/**清空野怪*/
	public void Empty(){
		StringBuffer buffer=new StringBuffer("M");
		for (int i = monsterMap.size()-1; i >=0 ; i--) {
			BTYMonster monster=monsterMap.get(i);
			monsterMap.remove(i);
			MonsterUtil.removeMonster2(monster.getBean());
			if (buffer.length()>1) {buffer.append("#");}
			buffer.append(monster.getBean().getI());
			buffer.append("^2");	
		}
		if (buffer.length()>1) {
			SendMessage.sendMessageToMapRoles(mapId,Agreement.getAgreement().battleStateAgreement(buffer.toString()));
		}
	}
	/**根据波数获取对应的boos*/
	public Boos getBoos(){
		String id=(CI+153)+"";//154-173
		Boos boos=GameServer.boosesMap.get(id);
		if (boos==null) {
			boos=MonsterUtil.booses.get(0);
		}
		return boos;
	}
	public int getI() {
		return I;
	}
	public BTYRole getRole(String name) {
		return roleMap.get(name);
	}
	static String MSG1=Agreement.getAgreement().PromptAgreement("本次活动还没有结束");
	static String MSG2=Agreement.getAgreement().PromptAgreement("你未参与本次活动无法领取奖励");
	static String MSG3=Agreement.getAgreement().PromptAgreement("你已经领取过奖励");
	static String[] MSGS;
	static{
		MSGS=new String[20];
		for (int i = 0; i < MSGS.length; i++) {
			MSGS[i]=Agreement.getAgreement().PromptAgreement("你获得"+(i+1)+"积分");
		}
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		BTYRole role=roleMap.get(loginResult.getRolename());
		if (I!=3&&I!=4) {
			SendMessage.sendMessageToSlef(ctx,MSG1);
			return;
		}
		if (role==null) {//没有参与
			SendMessage.sendMessageToSlef(ctx,MSG2);
		}else if (role.isAward()) {//已经领取过了
			SendMessage.sendMessageToSlef(ctx,MSG3);
		}else {//领取
			role.setAward(true);
			Dorp dorp=Award(role.getJf());
			if (dorp!=null) {
				DropUtil.getDrop(loginResult,dorp.getDorpValue(),"守卫蟠桃园礼包", 22,1D,null);
			}
		}
	}
	/**根据档次获得物品奖励*/
	public Dorp Award(int jf){
		int dc=jf/100;
		if (dc>6) {dc=6;}
		if (I==3) {dc++;}
		Dorp dorp=GameServer.getDorp((dc+352)+"");
		if (dorp==null) {
			return null;
		}
		return dorp;
	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		for (int i = monsterMap.size()-1; i >=0; i--) {
			BTYMonster btyMonster=monsterMap.get(i);
			MapMonsterBean bean=btyMonster.getBean();
			moveMap=MonsterUtil.monsterBuffer(bean, buffer, moveMap);
		}
		return moveMap;
	}
	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getSceneMsg(LoginResult loginResult, long oldMapId, long mapId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int battleEnd(BattleEnd battleEnd,LoginResult loginResult,MapMonsterBean bean,int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		MapMonsterBean bean=battleData.getMonsterBean();
		bean.setType(type);
		if (buffer.length()!=0) {buffer.append("|");}
		buffer.append("M");
		buffer.append(bean.getI());
		buffer.append("^");
		buffer.append(bean.getType());
		if (type==0||type==2) {
			for (int i = monsterMap.size()-1; i >=0 ; i--) {
				BTYMonster monster=monsterMap.get(i);
				if (monster.getBean().getI().intValue()!=bean.getI().intValue()) {continue;}
				int jf=0;
				if (type==2) {
					monsterMap.remove(i);		
					MonsterUtil.removeMonster2(bean);
					jf=monster.getCI();
				}
				if (I == 1) {
					for (int j = 0; j < teams.length; j++) {
						ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(teams[j]);
						LoginResult log = ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
						if (log == null) {continue;}
						BTYRole btyRole = roleMap.get(log.getRolename());
						if (btyRole == null) {
							btyRole = new BTYRole(log.getRole_id(),log.getRolename());
							roleMap.put(log.getRolename(), btyRole);
						}
						if (jf != 0) {
							btyRole.addJF(jf);
							SendMessage.sendMessageToSlef(ctx,MSGS[monster.getCI() - 1]);
						}
					}
				}
				break;
			}
		}
		return null;
	}
}
