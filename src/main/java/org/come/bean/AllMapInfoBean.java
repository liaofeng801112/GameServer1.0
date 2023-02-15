package org.come.bean;

import java.util.Map;

/**
 * 所有地图信息
 * @author 叶豪芳
 * @date 2017年11月15日 下午3:32:44
 * 
 */ 
public class AllMapInfoBean {
	// 所有地图的信息
	private Map<String, MapInfoBean> allMapInfo;

	public Map<String, MapInfoBean> getAllMapInfo() {
		return allMapInfo;
	}

	public void setAllMapInfo(Map<String, MapInfoBean> allMapInfo) {
		this.allMapInfo = allMapInfo;
	}

	
	
}
