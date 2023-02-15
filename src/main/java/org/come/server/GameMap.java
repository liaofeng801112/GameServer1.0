package org.come.server;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.task.MapMonsterBean;

/**地图数据*/
public class GameMap {
	
	
	private long mapId;//地图ID
	private int type;//0正常地图   1帮派共用地图  2副本队友公用地图
	private ConcurrentHashMap<BigDecimal,GameMap> hashMap;//公用区分数据
	private List<MapMonsterBean> robotList;//地图野怪
	public GameMap(long mapId) {
		// TODO Auto-generated constructor stub
		this.mapId=mapId;
	}
	/**添加野怪*/
	
	public int getType() {
		return type;
	}
}
