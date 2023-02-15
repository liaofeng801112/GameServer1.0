package come.tool.Scene.TGDB;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.task.MonsterUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;

public class TGDBScene implements Scene{

	//状态标识  0未开启 1开启  2表示1层通关   3 表示2层通关 4 表示3层通关 5 表示4层通关
	private int I;
	private TGDBMonster[] tgdbMonsters;
	
	public TGDBScene() {
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		I=1;
		tgdbMonsters=new TGDBMonster[4];
		tgdbMonsters[0]=new TGDBMonster();
		tgdbMonsters[0].setI(1);
		tgdbMonsters[0].setHp(5);
		tgdbMonsters[0].setBean(getMonster(GameServer.getAllRobot().get("801"),0));
		
		tgdbMonsters[1]=new TGDBMonster();
		tgdbMonsters[1].setI(2);
		tgdbMonsters[1].setHp(5);
		tgdbMonsters[1].setBean(getMonster(GameServer.getAllRobot().get("802"),1));
		
		tgdbMonsters[2]=new TGDBMonster();
		tgdbMonsters[2].setI(3);
		tgdbMonsters[2].setHp(5);
		tgdbMonsters[2].setBean(getMonster(GameServer.getAllRobot().get("803"),2));
		
		tgdbMonsters[3]=new TGDBMonster();
		tgdbMonsters[3].setI(4);
		tgdbMonsters[3].setHp(5);
		tgdbMonsters[3].setBean(getMonster(GameServer.getAllRobot().get("804"),3));
		
		for (int i = 0; i < tgdbMonsters.length; i++) {
			MonsterUtil.addEMonster(tgdbMonsters[i].getBean());			
		}

		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage("#R天宫宝库活动开启");
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);	
	}
	/**根据robot生成的*/
	public MapMonsterBean getMonster(Robots robots,int i){
		MapMonsterBean bean=new MapMonsterBean();
		bean.setI(MonsterUtil.getIncreasesum());
		bean.setRobotid(robots.getRobotID());
		bean.setRobotname(robots.getRobotname());
		bean.setRobotskin(robots.getRobotskin());
		bean.setSX(SceneUtil.TGDBID);
		
		bean.setMap(new Long(i+3325));
		bean.setX(0);
		bean.setY(0);
		return bean;
	} 
	/**发送福利怪*/
	public void open(){
		MonsterUtil.refreshMonsters(getBoos(),null,null,null);
		if (I==5) {
			for (int i = 0; i < tgdbMonsters.length; i++) {
				MonsterUtil.removeMonster2(tgdbMonsters[i].getBean());
			}
		}
	}
	/**根据波数获取对应的boos*/
	public Boos getBoos(){
		String id=(I+138)+"";
		Boos boos=GameServer.boosesMap.get(id);
		if (boos==null) {
			boos=MonsterUtil.booses.get(0);
		}
		return boos;
	}
	static String MSG1=Agreement.getAgreement().PromptAgreement("挑战队列已经满了,请等待其他玩家挑战结束");
	static String MSG2=Agreement.getAgreement().PromptAgreement("我已经战败了,去找其他层的守护者吧");
	static String MSG3=Agreement.getAgreement().PromptAgreement("我在这里干什么???");
	/**判断是否能击杀这个怪*/
	public String isBattle(MapMonsterBean bean){
		for (int i = 0; i < tgdbMonsters.length; i++) {
			TGDBMonster tgdbMonster=tgdbMonsters[i];
			if (bean.getI()==tgdbMonster.getBean().getI()) {
			    if (tgdbMonster.getHp()<=0) {
			    	return MSG2;
				}else {
					return null;
				}
			}
		}
		return MSG3;
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		MapMonsterBean bean=battleData.getMonsterBean();
		for (int i = 0; i < tgdbMonsters.length; i++) {
			TGDBMonster tgdbMonster = tgdbMonsters[i];
			if (bean.getI() == tgdbMonster.getBean().getI()) {
				if (type == 2) {// 击杀了
					if (tgdbMonster.getHp() > 0) {
						tgdbMonster.setHp(tgdbMonster.getHp() - 1);
						if (tgdbMonster.getHp() <= 0) {
							I++;
							open();
						}
					}
				} else if (type == 1) {// 进入战斗
					
				} else if (type == 0) {// 失败了
					
				}
			}
		}
		return null;
	}
    /**判断当前层数状态 0以通过 1正在通过 2未达到*/
	public int isC(int c){
		//状态标识  0未开启 1开启  2表示1层通关   3 表示2层通关 4 表示3层通关 5 表示4层通关
		if (c==I) {
			return 1;
		}else if (c<I) {
			return 0;
		}else {
			return 2;
		}
	}
	public TGDBMonster[] getTgdbMonsters() {
		return tgdbMonsters;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		return moveMap;
	}
	@Override
	public boolean isEnd(){
		if (I==5) {return false;}
		return true;
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
