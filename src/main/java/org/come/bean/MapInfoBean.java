package org.come.bean;

import java.util.List;

import org.come.model.Door;
import org.come.model.Gamemap;

/**
 * 地图所有信息
 * @author 叶豪芳
 * @date 2017年11月15日 下午3:30:56
 * 
 */ 
public class MapInfoBean {
	// 地图信息
	private Gamemap gamemap;
	
	// 地图所有传送点
	private List<Door> doors;
	
	// 地图所有NPC信息
	private List<NpcInfoBean> npcInfoBeans;

	public List<Door> getDoors() {
		return doors;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	public List<NpcInfoBean> getNpcInfoBeans() {
		return npcInfoBeans;
	}

	public void setNpcInfoBeans(List<NpcInfoBean> npcInfoBeans) {
		this.npcInfoBeans = npcInfoBeans;
	}

	public Gamemap getGamemap() {
		return gamemap;
	}

	public void setGamemap(Gamemap gamemap) {
		this.gamemap = gamemap;
	}
	
}
