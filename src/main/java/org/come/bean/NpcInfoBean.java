package org.come.bean;

import java.util.List;

import org.come.model.Door;
import org.come.model.Npctable;
import org.come.model.Talk;

/**
 * npc所有信息
 * @author 叶豪芳
 * @date 2018年1月28日 下午8:22:46
 * 
 */ 
public class NpcInfoBean {
	
	// npc信息
	private Npctable npctable;
	
	// npc废话列表
	private List<Talk> talks;
	
    // npc功能id列表
	private List<Door> doors;

	public Npctable getNpctable() {
		return npctable;
	}

	public void setNpctable(Npctable npctable) {
		this.npctable = npctable;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public List<Door> getDoors() {
		return doors;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}
	
	
	

}
