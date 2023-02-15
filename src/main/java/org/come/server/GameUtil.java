package org.come.server;

import java.util.concurrent.ConcurrentHashMap;

public class GameUtil {
	public static ConcurrentHashMap<Long,GameMap> allGameMap=new ConcurrentHashMap<>();
	private static Object object=new Object();
	/**获取对象*/
	public static GameMap getGameMap(long mapID){
		GameMap gameMap=allGameMap.get(mapID);
		if (gameMap==null) {
			synchronized (object) {//避免异步同时实例化
				gameMap=allGameMap.get(mapID);
				if (gameMap==null) {
					gameMap=new GameMap(mapID);
					allGameMap.put(mapID, gameMap);	
				}
			}
		}
		return gameMap;
	}
}
