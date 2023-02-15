package org.come.bean;

import java.util.List;

import org.come.model.Door;
import org.come.model.Npctable;
/**
 * 点击NPC返回的bean
 * @author 叶豪芳
 * @date : 2017年12月12日 下午5:39:07
 */
public class NpcResultBean {
	// npc信息
	private Npctable npctable;
	// 功能列表
	private List<Door> doors;
	
	public Npctable getNpctable() {
		return npctable;
	}
	public void setNpctable(Npctable npctable) {
		this.npctable = npctable;
	}
	public List<Door> getDoors() {
		return doors;
	}
	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}
	
	

}
