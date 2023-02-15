package org.come.bean;

import java.util.Map;

import org.come.model.Monster;

/**
 * 所有怪物信息
 * @author 叶豪芳
 * @date 2018年1月29日 上午10:57:41
 * 
 */ 
public class MonsterBean {
	
	private Map<String, Monster> monsterMap;

	public Map<String, Monster> getMonsterMap() {
		return monsterMap;
	}

	public void setMonsterMap(Map<String, Monster> monsterMap) {
		this.monsterMap = monsterMap;
	}

	
	
}
