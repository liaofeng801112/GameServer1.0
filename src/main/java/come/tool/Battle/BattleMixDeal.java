package come.tool.Battle;

import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;

import come.tool.oneArena.OneArenaRole;

public class BattleMixDeal {
	static String[] Teams=new String[0];
	/**数据初始化*/
	public static BattleData initData(FightingForesee fightingForesee,long mapid){
		BattleData battleData=new BattleData();
		battleData.setBattleNumber(BattleThreadPool.getIncreasesum());
		battleData.setBattletime(System.currentTimeMillis());
		battleData.setMapid(mapid);
		battleData.setBattleType(fightingForesee.getType());
		battleData.setTeam1(fightingForesee.getYidui().split("\\|"));
		battleData.setTeam2((fightingForesee.getErdui()!=null&&!fightingForesee.getErdui().equals(""))?fightingForesee.getErdui().split("\\|"):Teams);
		Robots robots=null;
		if (fightingForesee.getRobotid()!=null) {
			robots=GameServer.getAllRobot().get(fightingForesee.getRobotid());
		}else if (fightingForesee.getI()!=0) {
			MapMonsterBean bean=MonsterUtil.getMonster(fightingForesee.getI());
			battleData.setMonsterBean(bean);
			if (bean!=null) {
				robots=GameServer.getAllRobot().get(bean.getRobotid()+"");
				battleData.setBoosID(bean.getBoosId());
			}
		}
		battleData.setRobots(robots);
		if (robots!=null) {
			if (battleData.getMonsterBean()==null||battleData.getMonsterBean().getFollow()==null) {
				battleData.getBattlefield().dropModel=robots.getDropModel();
			}
		}
		return battleData;
	}
	/**数据初始化*/
	public static BattleData initData(MapMonsterBean bean,String[] teams,long mapid){
		BattleData battleData=new BattleData();
		battleData.setBattleNumber(BattleThreadPool.getIncreasesum());
		battleData.setMapid(mapid);
		battleData.setBattleType(1);
		battleData.setTeam1(teams);
		battleData.setTeam2(Teams);
		battleData.setBattletime(System.currentTimeMillis());
		Robots robots=null;
		battleData.setMonsterBean(bean);
		robots=GameServer.getAllRobot().get(bean.getRobotid()+"");
		battleData.setBoosID(bean.getBoosId());
		battleData.setRobots(robots);
		if (robots!=null) {
			battleData.getBattlefield().dropModel=robots.getDropModel();
		}
		return battleData;
	}
	/**单人竞技场初始化*/
	public static BattleData initData(String[] teams, OneArenaRole myArenaRole, OneArenaRole otherArenaRole, long mapid) {
		BattleData battleData = new BattleData();
		battleData.setBattleNumber(BattleThreadPool.getIncreasesum());
		battleData.setMapid(mapid);
		battleData.setBattleType(101);
		battleData.setTeam1(teams);
		battleData.setTeam2(Teams);
		battleData.setOneArenaRole1(myArenaRole);
		battleData.setOneArenaRole2(otherArenaRole);
		Robots robots = GameServer.getAllRobot().get("99999");
		battleData.setRobots(robots);
		battleData.getBattlefield().dropModel=robots.getDropModel();
		battleData.setBattletime(System.currentTimeMillis());
		return battleData;
	}

	/**
	 * 武神山守护初始化
	 * @param teams		挑战者团队
	 * @param wssType	守护类型  1人、2地、3天、4天帝印
	 * @param mapid		所在地图
	 * @return
	 */
	public static BattleData initWSSData(String[] teams, int wssType ,long mapid){
		BattleData battleData=new BattleData();
		battleData.setBattleNumber(wssType);
		battleData.setMapid(mapid);
		battleData.setBattleType(886);
		battleData.setTeam1(teams);

		battleData.setWssType(wssType);

		Robots robots=GameServer.getAllRobot().get((4000 + wssType) + "");
		battleData.setRobots(robots);
		battleData.getBattlefield().dropModel=robots.getDropModel();

		battleData.setBattletime(System.currentTimeMillis());
		return battleData;
	}


	/**数据初始化*/
	public static BattleData initData(Robots robots,String[] teams) {
		BattleData battleData = new BattleData();
		battleData.setBattleNumber(BattleThreadPool.getIncreasesum());
		battleData.setBattleType(1);
		battleData.setTeam1(teams);
		battleData.setTeam2(Teams);
		battleData.setBattletime(System.currentTimeMillis());
		battleData.setRobots(robots);
		battleData.getBattlefield().dropModel = robots.getDropModel();
		return battleData;
	}
	/**等级监测*/
	public static String isLvl(int lvl,int[] lvls){
		int manlvl=lvlint(lvl);
		int manturn=lvltrue(lvl);
		if (manlvl<lvls[1]||manlvl>lvls[3]) {
			return Agreement.getAgreement().PromptAgreement("等级不满足在"+lvls[1]+"-"+lvls[3]+"范围");
		}
		if (manturn<lvls[0]) {
			return Agreement.getAgreement().PromptAgreement("转生次数最少"+lvls[0]+"次");
		}
		if (manturn>lvls[2]||(manturn==lvls[2]&&manlvl>lvls[4])) {
			return Agreement.getAgreement().PromptAgreement("最大到"+lvls[2]+"转"+lvls[4]+"级");
		}
		return null;
	}
	/**根据等级解出几级*/
	public static int lvlint(int lvl){
		if (lvl<=102) {return lvl;}
		else if (lvl<=210) {return (lvl-102+14);}
		else if (lvl<=338) {return (lvl-210+14);}
		else if (lvl<=459){return (lvl-338+59);}
		else {return (lvl-459+139);}
	}
	/**人物根据等级解出几转*/
	public static int lvltrue(int lvl){
		if (lvl<=102) {return 0;}
		else if (lvl<=210) {return 1;}
		else if (lvl<=338) {return 2;}
		else if (lvl<=459){return 3;}
		else {return 4;}
	}
	/**召唤兽根据等级解出几级*/
	public static int petLvlint(int lvl){
		if (lvl<=100) {return lvl;}
		else if (lvl<=221) {return (lvl-101);}
		else if (lvl<=362) {return (lvl-222);}
		else if (lvl<=543){return (lvl-363);}
		else {return (lvl-544);}
	}
	/**召唤兽根据等级解出几转*/
	public static int petTurnRount(int lvl){
		if (lvl<=100) {return 0;}
		else if (lvl<=221) {return 1;}
		else if (lvl<=362) {return 2;}
		else if (lvl<=543){return 3;}
		else {return 4;}
	}
	/**星阵转换的编号ID*/
	public static int getID(String name){
		if (name.equals("金牛")) {
			return 3036;
		}else if (name.equals("苍狼")) {
			return 3037;
		}else if (name.equals("赤马")) {
			return 3038;
		}else if (name.equals("黄鹤")) {
			return 3039;
		}else if (name.equals("火猿")) {
			return 3040;
		}else if (name.equals("朱雀")) {
			return 3041;
		}else if (name.equals("玄武")) {
			return 3042;
		}else if (name.equals("白虎")) {
			return 3043;
		}else if (name.equals("青龙")) {
			return 3044;
		}
		return 3044;
	}
}
