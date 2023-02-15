package come.tool.Scene.BWZ;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.PathPoint;
import org.come.handler.SendMessage;
import org.come.model.Boos;
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
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;

public class BWZScene implements Scene{

	//状态标识  0未开启 1开启 2结束
 	private int I;
 	//当前波数
 	private int CI;
 	//开启时间
 	public long time;
 	public static final long mapId=1193;
 	private int BTYHP=50;
	private List<BWZMonster> monsterMap;
	private BWZThread btyThread;
	//刷怪点
	private MonsterMoveBase[] btyPaths;
	private int day;
	public BWZScene() {
		// TODO Auto-generated constructor stub
		Calendar rightNow = Calendar.getInstance(Locale.CHINA);
		day = rightNow.get(Calendar.DAY_OF_WEEK);// 获取时间
		init();
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage("#R长安保卫战开启");
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);	
		I=1;
		CI=0;
		btyThread = new BWZThread(this);
		Thread T1 = new Thread(btyThread);
		T1.start();	
		
		
	}
	/**判断结束*/
	@Override
	public boolean isEnd(){
		if (I==2) {//结束
			return false;
		}else if (CI>=30&&monsterMap.size()==0) {
			I=2;
			return false;
		}else if (BTYHP<=0) {//失败
			Empty();
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("长安保卫战失败");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);	
			I=2;
			return false;
		}
		return true;
	}
	/**路径起点初始化*/
    public void init(){
    	monsterMap=new ArrayList<>();
    	btyPaths=new MonsterMoveBase[5];
    	for (int i = 0; i < btyPaths.length; i++) {
    		btyPaths[i]=new MonsterMoveBase(5+i);
		}
    }
	/**移动*/
	public void move(){
		StringBuffer buffer=null;
		int mmin = 0,mmax = 0;
		for (int i = monsterMap.size()-1; i >=0; i--) {
			BWZMonster monster=monsterMap.get(i);
			if (monster.move()) {//到达终点
				monsterMap.remove(i);
				MonsterUtil.removeMonster2(monster.getBean());
				if (buffer==null) {buffer=new StringBuffer("M");}
				else {buffer.append("#");}
				buffer.append(monster.getBean().getI());
				buffer.append("^2");
				if (BTYHP>0) {
					BTYHP-=monster.getI()==4?5:1;
					if (monster.getI()==4) {mmax++;}
					else {mmin++;}	
				}
			}
		}
		if (buffer!=null) {
			SendMessage.sendMessageToMapRoles(mapId,Agreement.getAgreement().battleStateAgreement(buffer.toString()));
		}
		if (mmin!=0||mmax!=0) {
			buffer.setLength(0);
			buffer.append("被");
			if (mmax!=0) {
				buffer.append(mmax);
				buffer.append("只BOOS");
			}
			if (mmin!=0) {
				if (mmax!=0) {buffer.append(",");}
				buffer.append(mmin);
				buffer.append("只小怪");
			}
			buffer.append("突破防线扣除");
			buffer.append(mmin+5*mmax);
			buffer.append("点血量,剩余城门剩余血量:#R");
			buffer.append(BTYHP);
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage(buffer.toString());
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapId,msg);
		}
	}
//	/**刷怪*/
	public void open(){
		if (CI>=30) {return;}
		CI++;
		StringBuffer buffer=new StringBuffer("M");
		for (int i = 0; i < btyPaths.length; i++) {
			MonsterMoveBase moveBase=btyPaths[i];
	    	if (buffer.length()>1) {buffer.append("#");}
	    	buffer.append(moveBase.getMoveMsg());
		}
		for (int i = 0; i < btyPaths.length; i++) {
			open(getBoos(i), btyPaths[i],i,buffer);
		}
		// 根据地图发送信息
		SendMessage.sendMessageToMapRoles(mapId, Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));		
	}
	public void open(Boos boos,MonsterMoveBase bwzPath,int I,StringBuffer buffer){
	    Robots robot = GameServer.getAllRobot().get(boos.getBoosrobot());
		int robotId=robot.getRobotID();
		 //刷新的数量
	    int size=boos.getBoosnum();
	    size*=CI;
	    buffer.append("|");
        buffer.append(robot.getRobotID());
        buffer.append("#");
        buffer.append(robot.getRobotname());
        buffer.append("#");
        buffer.append(robot.getRobotskin());
        buffer.append("#");
        buffer.append(robot.getRobotType());
        int maxtime=boos.getBoosetime();
		PathPoint point =bwzPath.getPoints().get(0);
    	for (int i = 0; i < size; i++) {
    	    // 每个坐标对应的怪物的bean
    		MapMonsterBean Bean1 = new MapMonsterBean();
		    //坐标
			Bean1.setX(point.getX()+MonsterUtil.getPY());
			Bean1.setY(point.getY()+MonsterUtil.getPY());
			Bean1.setRobotid(robotId);
			Bean1.setRobotname(robot.getRobotname());
			Bean1.setRobotskin(robot.getRobotskin());		
			Bean1.setRobotType(robot.getRobotType());
			Bean1.setI(MonsterUtil.getIncreasesum());
			Bean1.setMap(mapId);
			Bean1.setMaxtime(maxtime);
			Bean1.setSX(SceneUtil.BWZID);
			Bean1.setMove(new MonsterMove(bwzPath, -800*(i%30), Bean1.getX(), Bean1.getY()));
			monsterMap.add(new BWZMonster(Bean1,I));
			MonsterUtil.addEMonster(Bean1);
			MonsterUtil.monsterBuffer1(Bean1, buffer);
		    if (i==0) {Bean1.setBoosId(RewardLimit.isBoosDrop(boos));}
		}
		MonsterUtil.boosChat(boos,null,null,null);
	}
	/**清空野怪*/
	public void Empty(){
		StringBuffer buffer=new StringBuffer("M");
		for (int i = monsterMap.size()-1; i >=0 ; i--) {
			BWZMonster monster=monsterMap.get(i);
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
	public Boos getBoos(int i){
		
		String id=(i+(day==5?282:182))+"";
		
		Boos boos=GameServer.boosesMap.get(id);
		if (boos==null) {
			boos=MonsterUtil.booses.get(0);
		}
		return boos;
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
		if (type==2) {
			for (int i = monsterMap.size()-1; i >=0 ; i--) {
				BWZMonster monster=monsterMap.get(i);
				if (monster.getBean().getI().intValue()!=bean.getI().intValue()) {continue;}
				monsterMap.remove(i);
				MonsterUtil.removeMonster2(bean);
				break;
			}
		}
		return null;
	}
	public int getI() {
		return I;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub

	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		for (int i = monsterMap.size()-1; i >=0; i--) {
			BWZMonster bwzMonster=monsterMap.get(i);
			MapMonsterBean bean=bwzMonster.getBean();
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
}
