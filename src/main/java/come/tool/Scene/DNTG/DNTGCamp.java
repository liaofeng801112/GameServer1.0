package come.tool.Scene.DNTG;

import org.come.model.Robots;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterHp;
import org.come.task.MonsterUtil;

import come.tool.Scene.SceneUtil;

public class DNTGCamp {

	private int KJValue;//科技值
	
    private MapMonsterBean DBY;//大本营

    private MapMonsterBean K_JS;//集市
    
    private MapMonsterBean T_S;//上塔
    private MapMonsterBean T_Z;//中塔
    private MapMonsterBean T_X;//下塔
    
    private DNTGBuff buff;//阵营buff
	public DNTGCamp(int camp) {
		// TODO Auto-generated constructor stub
		//101天庭大本营     102-104  天庭箭塔          105天庭集市      
		//111花果山大本营 112-114  花果山箭塔      115花果山集市  
		//120天庭进攻怪     121           花果山进攻怪   
		//130女武神试炼     131           上古传送门      132上古宝箱
//		大本营    攻击
//		箭塔        攻击    送我进去  送我出去   
//		集市        生产    偷袭
		Robots robots=GameServer.getAllRobot().get(camp==0?"911":"916");
		DBY =new MapMonsterBean(DNTGScene.mapIdZ,MonsterUtil.getIncreasesum(),camp==0?101:111,SceneUtil.DNTGID);
		DBY.setRobotname("大本营");DBY.setRobotid(robots.getRobotID());DBY.setRobotskin(camp==0?"400577":"500027");
		DBY.setX(camp==0?2120:7360);DBY.setY(camp==0?900:3520);
		DBY.setHp(new MonsterHp(250,true));
		MonsterUtil.addEMonster(DBY);
		
//		robots=GameServer.getAllRobot().get(camp==0?"908":"913");
//		K_JS=new MapMonsterBean(DNTGScene.mapIdZ,MonsterUtil.getIncreasesum(),camp==0?105:115,SceneUtil.DNTGID);
//		K_JS.setRobotname(camp==0?"天庭集市":"花果山集市");K_JS.setRobotid(Integer.parseInt(robots.getRobotid()));K_JS.setRobotskin("10001");
//		K_JS.setX(camp==0?1000:2000);K_JS.setY(camp==0?1000:2000);
//		K_JS.setHp(new MonsterHp(100,true));
//		MonsterUtil.addEMonster(K_JS);
		robots=GameServer.getAllRobot().get(camp==0?"908":"913");
		T_S =new MapMonsterBean(DNTGScene.mapIdZ,MonsterUtil.getIncreasesum(),camp==0?102:112,SceneUtil.DNTGID);
		T_S.setRobotname(camp==0?"天庭上塔":"花果山上塔");T_S.setRobotid(robots.getRobotID());T_S.setRobotskin(camp==0?"500029":"500028");
		T_S.setX(camp==0?2580:8640);T_S.setY(camp==0?1000:2800);
		T_S.setHp(new MonsterHp(500,true));
		MonsterUtil.addEMonster(T_S);
		T_Z =new MapMonsterBean(DNTGScene.mapIdZ,MonsterUtil.getIncreasesum(),camp==0?103:113,SceneUtil.DNTGID);
		T_Z.setRobotname(camp==0?"天庭中塔":"花果山中塔");T_Z.setRobotid(T_S.getRobotid());T_Z.setRobotskin(T_S.getRobotskin());
		T_Z.setX(camp==0?2100:6800);T_Z.setY(camp==0?1760:3060);
		T_Z.setHp(new MonsterHp(500,true));
		MonsterUtil.addEMonster(T_Z);
		T_X =new MapMonsterBean(DNTGScene.mapIdZ,MonsterUtil.getIncreasesum(),camp==0?104:114,SceneUtil.DNTGID);
		T_X.setRobotname(camp==0?"天庭下塔":"花果山下塔");T_X.setRobotid(T_S.getRobotid());T_X.setRobotskin(T_S.getRobotskin());
		T_X.setX(camp==0?740:6640);T_X.setY(camp==0?1600:3720);
		T_X.setHp(new MonsterHp(500,true));
		MonsterUtil.addEMonster(T_X);
	}
	/**获取野怪信息*/
	public void removeMapMonster(StringBuffer buffer){
		if (buffer.length()>1) {buffer.append("#");}
		buffer.append(DBY.getI());
		buffer.append("^2");
		MonsterUtil.removeMonster2(DBY);
		if (buffer.length()>1) {buffer.append("#");}
		buffer.append(T_S.getI());
		buffer.append("^2");
		MonsterUtil.removeMonster2(T_S);
		if (buffer.length()>1) {buffer.append("#");}
		buffer.append(T_Z.getI());
		buffer.append("^2");
		MonsterUtil.removeMonster2(T_Z);
		if (buffer.length()>1) {buffer.append("#");}
		buffer.append(T_X.getI());
		buffer.append("^2");
		MonsterUtil.removeMonster2(T_X);
	}
	/**获取野怪信息*/
	public void getMapMonster(StringBuffer buffer){
		for (int i = 0; i < 5; i++) {
			if (i==1) {continue;}
			MapMonsterBean bean=i==0?DBY:i==1?K_JS:i==2?T_S:i==3?T_Z:T_X;
			MonsterUtil.monsterBuffer(bean,buffer,null);
		}
	}
	public MapMonsterBean getDBY() {
		return DBY;
	}
	public void setDBY(MapMonsterBean dBY) {
		DBY = dBY;
	}
//	public MapMonsterBean getK_JS() {
//		return K_JS;
//	}
//	public void setK_JS(MapMonsterBean k_JS) {
//		K_JS = k_JS;
//	}
	public MapMonsterBean getT_S() {
		return T_S;
	}
	public void setT_S(MapMonsterBean t_S) {
		T_S = t_S;
	}
	public MapMonsterBean getT_Z() {
		return T_Z;
	}
	public void setT_Z(MapMonsterBean t_Z) {
		T_Z = t_Z;
	}
	public MapMonsterBean getT_X() {
		return T_X;
	}
	public void setT_X(MapMonsterBean t_X) {
		T_X = t_X;
	}
	public int getKJValue() {
		return KJValue;
	}
	public void setKJValue(int kJValue) {
		KJValue = kJValue;
	}
	public void addKJValue(int add) {
		KJValue+=add;
	}
	public DNTGBuff getBuff() {
		return buff;
	}
	public void setBuff(DNTGBuff buff) {
		this.buff = buff;
	}
	public double getBoosXS(){
		double xs=0D;
		if (T_S.getHp().getHp()>0) {xs+=1;}
		if (T_Z.getHp().getHp()>0) {xs+=1;}
		if (T_X.getHp().getHp()>0) {xs+=1;}
		return xs;
	}
}
