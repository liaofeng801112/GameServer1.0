package org.come.bean;

import java.util.Map;

import org.come.model.Save;

/**
 * 守护表
 * @author Administrator
 *
 */
public class SaveBean {
	// 贡献值与守护集合
	private Map<Integer, Save> allSaveMap;

	public Map<Integer, Save> getAllSaveMap() {
		return allSaveMap;
	}

	public void setAllSaveMap(Map<Integer, Save> allSaveMap) {
		this.allSaveMap = allSaveMap;
	}
}
