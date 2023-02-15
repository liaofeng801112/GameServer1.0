package org.come.readBean;

import java.util.Map;

import org.come.model.Gamemap;

public class AllMapBean {
	// 所有地图的信息
	private Map<String, Gamemap> allMap;

	public Map<String, Gamemap> getAllMap() {
		return allMap;
	}

	public void setAllMap(Map<String, Gamemap> allMap) {
		this.allMap = allMap;
	}
	
}
