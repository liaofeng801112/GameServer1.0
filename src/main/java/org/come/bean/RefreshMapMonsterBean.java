package org.come.bean;

import java.util.List;
import java.util.Map;

import org.come.task.MapMonsterBean;
/**
 * 刷新怪物发送的bean
 * @author 叶豪芳
 * @date 2017年12月28日 下午2:40:00
 * 
 */ 
public class RefreshMapMonsterBean {
	
	// 怪物集合
	private Map<String, List<MapMonsterBean>> mapMonsterMap;

	public Map<String, List<MapMonsterBean>> getMapMonsterMap() {
		return mapMonsterMap;
	}

	public void setMapMonsterMap(Map<String, List<MapMonsterBean>> mapMonsterMap) {
		this.mapMonsterMap = mapMonsterMap;
	}
	
	

}
