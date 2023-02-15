package come.tool.Scene;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import come.tool.Scene.TAST.MrScene;
import org.come.bean.LoginResult;
import org.come.task.MonsterMoveBase;

import come.tool.Scene.BTY.BTYScene;
import come.tool.Scene.BWZ.BWZScene;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Scene.PKLS.PKLSScene;
import come.tool.Scene.RC.RCScene;
import come.tool.Scene.SLDH.SLDHScene;
import come.tool.Scene.TGDB.TGDBScene;
import come.tool.Scene.ZZS.ZZSScene;

public class SceneUtil {
	//蟠桃园
	public static final int BTYID=1001;
	//天宫夺宝
	public static final int TGDBID=1002;
	//种族赛*人
	public static final int ZZSID_0=1003;
	//种族赛*魔
	public static final int ZZSID_1=1004;
	//种族赛*仙
	public static final int ZZSID_2=1005;
	//种族赛*鬼
	public static final int ZZSID_3=1006;

	//种族赛*龙
	public static final int ZZSID_4=10061;

	//长安保卫战
	public static final int BWZID=1007;
	//擂台赛
	public static final int LTSID=1008;
	//日常开启副本
	public static final int RCID=1009;
	//全民竞技
	public static final int PKLSID=1010;
	//大闹天宫
	public static final int DNTGID=1011;
	//水陆大会
	public static final int SLDHID=1012;
	//全民竞技
	//ddd
	public static final int MRID=1013;
	private static ConcurrentHashMap<Integer,Scene> sceneMap;
	public static void init(){
		sceneMap=new ConcurrentHashMap<>();
		sceneMap.put(RCID, new RCScene());
		sceneMap.put(PKLSID, new PKLSScene());	
		sceneMap.put(SLDHID, new SLDHScene());
		
	}
	/**判断是否有活动开启*/
	public static void activityOpen(String week,int day,int hour,int minute,int second){
		if (week.equals("Monday")) {//周1，晚20：00，天宫寻宝
			if (minute == 0) {
				if (hour == 20) {
					sceneMap.put(TGDBID, new TGDBScene());
				} else if (hour == 22) {
					sceneMap.remove(TGDBID);
				}
			}
		else 	if (hour == 19 && minute == 30) {
				Scene scene = sceneMap.get(SLDHID);
				if (scene == null) {
					scene = new SLDHScene();
					sceneMap.put(SLDHID, scene);
				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene = (SLDHScene) scene;
					sldhScene.open();
				}
			}
		}
//		}else if (week.equals("Monday")) {//周一晚上8：00  水陆大会
//			if (hour==19&&minute==30) {
//				Scene scene=sceneMap.get(SLDHID);
//				if (scene==null) {
//					scene=new SLDHScene();
//					sceneMap.put(SLDHID, scene);
//				}
//				if (!scene.isEnd()) {
//					SLDHScene sldhScene=(SLDHScene) scene;
//					sldhScene.open();
//				}
//			}
		else if (week.equals("Tuesday")) {//周二晚上8：00  水陆大会
			if (hour==19&&minute==30) {
				Scene scene=sceneMap.get(SLDHID);
				if (scene==null) {
					scene=new SLDHScene();
					sceneMap.put(SLDHID, scene);

				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene=(SLDHScene) scene;
					sldhScene.open();
				}
			}
		}else if (week.equals("Wednesday")) {//周三晚上8：00  水陆大会
			if (hour==19&&minute==30) {
				Scene scene=sceneMap.get(SLDHID);
				if (scene==null) {
					scene=new SLDHScene();
					sceneMap.put(SLDHID, scene);
				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene=(SLDHScene) scene;
					sldhScene.open();
				}
			}
//		}else if (week.equals("Thursday")) {//周4晚上8：00  水陆大会
//			if (hour==19&&minute==30) {
//				Scene scene=sceneMap.get(SLDHID);
//				if (scene==null) {
//					scene=new SLDHScene();
//					sceneMap.put(SLDHID, scene);
//				}
//				if (!scene.isEnd()) {
//					SLDHScene sldhScene=(SLDHScene) scene;
//					sldhScene.open();
//				}
//			}
//		}else if (week.equals("Friday")) {//周5晚上8：00  水陆大会
//			if (hour==19&&minute==30) {
//				Scene scene=sceneMap.get(SLDHID);
//				if (scene==null) {
//					scene=new SLDHScene();
//					sceneMap.put(SLDHID, scene);
//				}
//				if (!scene.isEnd()) {
//					SLDHScene sldhScene=(SLDHScene) scene;
//					sldhScene.open();
//				}
//			}
//		}else if (week.equals("Saturday")) {//周六晚上8：00 水陆大会
//			if (hour==19&&minute==30) {
//				Scene scene=sceneMap.get(SLDHID);
//				if (scene==null) {
//					scene=new SLDHScene();
//					sceneMap.put(SLDHID, scene);
//				}
//				if (!scene.isEnd()) {
//					SLDHScene sldhScene=(SLDHScene) scene;
//					sldhScene.open();
//				}
//			}
//		}else if (week.equals("Sunday")) {//周日晚上8：00  水陆大会
//			if (hour==19&&minute==30) {
//				Scene scene=sceneMap.get(SLDHID);
//				if (scene==null) {
//					scene=new SLDHScene();
//					sceneMap.put(SLDHID, scene);
//				}
//				if (!scene.isEnd()) {
//					SLDHScene sldhScene=(SLDHScene) scene;
//					sldhScene.open();
//				}
//			}
		}else if (week.equals("Wednesday")) {//周3晚上8：00  种族比武
			
		}else if (week.equals("Thursday")) {//周4晚上20：00  守护蟠桃园
			if (hour==19&&minute==30) {
				Scene scene=sceneMap.get(DNTGID);
				if (scene==null||!scene.isEnd()) {
					sceneMap.put(DNTGID, new DNTGScene());	
				}
			}
			if (hour == 19 && minute == 30) {
				Scene scene = sceneMap.get(SLDHID);
				if (scene == null) {
					scene = new SLDHScene();
					sceneMap.put(SLDHID, scene);
				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene = (SLDHScene) scene;
					sldhScene.open();
				}
			}
		}else if (week.equals("Thursday")) {//周4晚上20：00  守护蟠桃园
			if (hour==19&&minute==30) {
				Scene scene=sceneMap.get(MRID);
				if (scene==null||!scene.isEnd()) {
					sceneMap.put(MRID, new DNTGScene());
				}
			}
			if (hour == 19 && minute == 30) {
				Scene scene = sceneMap.get(SLDHID);
				if (scene == null) {
					scene = new SLDHScene();
					sceneMap.put(SLDHID, scene);
				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene = (SLDHScene) scene;
					sldhScene.open();
				}
			}
		}else if (week.equals("Saturday")) {//周六，中午13：30-15:30点，天宫寻宝，  16:00-18：00，长安保卫战。
			if (hour==13&&minute==30) {
				sceneMap.put(TGDBID, new TGDBScene());			
			}else if (hour==15&&minute==30) {
				sceneMap.remove(TGDBID);
			}
			if (hour == 19 && minute == 30) {
				Scene scene = sceneMap.get(SLDHID);
				if (scene == null) {
					scene = new SLDHScene();
					sceneMap.put(SLDHID, scene);
				}
				if (!scene.isEnd()) {
					SLDHScene sldhScene = (SLDHScene) scene;
					sldhScene.open();
				}
			}
		}else if (week.equals("Sunday")) {//周日，中午13：30-15:30点，守护蟠桃园，  16:00-18：00，长安保卫战。  20：00  种族比武
			if (hour==14&&minute==0) {
				Scene scene=sceneMap.get(BWZID);
				if (scene==null||!scene.isEnd()) {
				    sceneMap.put(BWZID, new BWZScene());
				}
			}
		}
		if (hour == 19 && minute == 30) {
			Scene scene = sceneMap.get(SLDHID);
			if (scene == null) {
				scene = new SLDHScene();
				sceneMap.put(SLDHID, scene);
			}
			if (!scene.isEnd()) {
				SLDHScene sldhScene = (SLDHScene) scene;
				sldhScene.open();
			}
		}
		if (minute==0) {
			if (hour==0) {
				sceneMap.remove(BTYID);
				sceneMap.remove(BWZID);
				if (week.equals("Monday")) {
					Scene scene=sceneMap.remove(DNTGID);
					if (scene!=null) {
						DNTGScene dntgScene=(DNTGScene) scene;
						dntgScene.removeMonsterBean();
					}
				}
			}else if (hour==5) {
				Scene scene=sceneMap.get(RCID);
				if (scene!=null) {
					RCScene rcScene=(RCScene) scene;
					rcScene.clean();
				}
			}
		}
	}
	/**额外开启活动*/
	public static void additionalScene(int Id){
		Scene scene=sceneMap.get(Id);
		if (scene==null||!scene.isEnd()) {
			if (Id==BTYID) {
				sceneMap.put(BTYID, new BTYScene());
			}else if (Id==TGDBID) {
				sceneMap.put(TGDBID, new TGDBScene());
			}else if (Id==BWZID) {
				sceneMap.put(BWZID, new BWZScene());
			}else if (Id==LTSID) {
				sceneMap.put(LTSID, new LTSScene());
			}else if (Id==MRID) {
				sceneMap.put(MRID, new MrScene());
			}
		}
	}
	/***/
	public static Scene getScene(int id){
		return sceneMap.get(id);		
	}
	/**获取玩家所在种族赛*/
	public static ZZSScene getZZS(LoginResult loginResult){
		for (int i = 0; i < 5; i++) {
			Scene scene=null;
			if (i==0) {scene=getScene(ZZSID_0);}
			else if (i==1){scene=getScene(ZZSID_1);}
			else if (i==2){scene=getScene(ZZSID_2);}
			else if (i==3){scene=getScene(ZZSID_3);}
			else if (i==4){scene=getScene(ZZSID_4);}
			if (scene!=null) {
				ZZSScene zzsScene=(ZZSScene) scene;
				if (zzsScene.getRole(loginResult)!=null) {
					return zzsScene;
				}
			}
		}
		Scene scene=null;
		if (loginResult.getRace_id().intValue()==10001) {scene=getScene(ZZSID_0);}
		else if (loginResult.getRace_id().intValue()==10002){scene=getScene(ZZSID_1);}
		else if (loginResult.getRace_id().intValue()==10003){scene=getScene(ZZSID_2);}
		else if (loginResult.getRace_id().intValue()==10004){scene=getScene(ZZSID_3);}
		else if (loginResult.getRace_id().intValue()==10005){scene=getScene(ZZSID_4);}

		if (scene==null) {return null;}
		return (ZZSScene) scene;
	}
	public static Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		if (mapId==BTYScene.mapId) {
			Scene scene=sceneMap.get(BTYID);
			if (scene!=null) {return scene.getMapMonster(buffer,moveMap,mapId);}
		}else if (mapId==BWZScene.mapId) {
			Scene scene=sceneMap.get(BWZID);
			if (scene!=null) {return scene.getMapMonster(buffer,moveMap,mapId);}
		}else if (mapId==DNTGScene.mapIdZ||mapId==DNTGScene.mapIdF) {
			Scene scene=sceneMap.get(DNTGID);
			if (scene!=null) {return scene.getMapMonster(buffer,moveMap,mapId);}
		}else if (mapId==MrScene.mapId){
			Scene scene=sceneMap.get(MRID);
			if (scene!=null){return scene.getMapMonster(buffer,moveMap,mapId);}
		}
		return moveMap;
	}
	public static void ZZSBattleEnd(String m1,String m2,int type){
		for (int i = 0; i < 5; i++) {
			Scene scene=null;
			if (i==0) {scene=getScene(ZZSID_0);}
			else if (i==1){scene=getScene(ZZSID_1);}
			else if (i==2){scene=getScene(ZZSID_2);}
			else if (i==3){scene=getScene(ZZSID_3);}
			else if (i==4){scene=getScene(ZZSID_4);}
			if (scene!=null) {
				ZZSScene zzsScene=(ZZSScene) scene;
				zzsScene.BattleEnd(m1,m2,type);
			}
		}
	}
	/**判断是否需要获取副本信息*/
	public static boolean isSceneMsg(long oldMapId,long mapId){
		if (oldMapId!=mapId) {
			if (mapId==DNTGScene.mapIdZ||mapId==DNTGScene.mapIdF||oldMapId==DNTGScene.mapIdZ||oldMapId==DNTGScene.mapIdF) {
				return true;
			}		
		}
		return false;
	}
	/**获取副本信息*/
	public static String getSceneMsg(LoginResult loginResult,long oldMapId,long mapId){
		if (oldMapId==mapId) {
			return null;
		}
		if (mapId==DNTGScene.mapIdZ||mapId==DNTGScene.mapIdF||oldMapId==DNTGScene.mapIdZ||oldMapId==DNTGScene.mapIdF) {
			if (mapId!=DNTGScene.mapIdZ&&mapId!=DNTGScene.mapIdF) {
				return DNTGID+"";
			}else {
				Scene scene=sceneMap.get(DNTGID);
				if (scene!=null) {
					return scene.getSceneMsg(loginResult,oldMapId,mapId);
				}
			}
		}
		return null;
	}
}
