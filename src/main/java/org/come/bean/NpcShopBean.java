package org.come.bean;

import java.util.List;
import java.util.Map;

import org.come.model.Shop;

/**
 * 商店
 * @author 叶豪芳
 * @date 2017年11月15日 下午4:38:32
 * 
 */ 
public class NpcShopBean {
	
	// 根据商店物品类型储存每个商店的物品
	private Map<String, List<Shop>> npcShopMap;

	public Map<String, List<Shop>> getNpcShopMap() {
		return npcShopMap;
	}

	public void setNpcShopMap(Map<String, List<Shop>> npcShopMap) {
		this.npcShopMap = npcShopMap;
	}
	
	
}
