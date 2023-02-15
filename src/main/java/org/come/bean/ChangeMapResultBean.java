package org.come.bean;

import java.util.List;
import java.util.Map;

import org.come.model.Door;
import org.come.model.Npctable;
import org.come.task.MapMonsterBean;
/**
 * 传送地图返回的bean
 * @author Administrator
 *
 */
public class ChangeMapResultBean {
	// 该地图角色信息
	private List<LoginResult> userRoleArr;
	
	// 地图NPC信息
	private List<Npctable> npctables;
	
	// 传送点集合
	private List<Door> doors;
	
	// 怪物集合
	private Map<String, List<MapMonsterBean>> mapMonsterMap;
	
	public List<LoginResult> getUserRoleArr() {
		return userRoleArr;
	}

	public void setUserRoleArr(List<LoginResult> userRoleArr) {
		this.userRoleArr = userRoleArr;
	}

	public List<Npctable> getNpctables() {
		return npctables;
	}

	public void setNpctables(List<Npctable> npctables) {
		this.npctables = npctables;
	}

	public List<Door> getDoors() {
		return doors;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	public Map<String, List<MapMonsterBean>> getMapMonsterMap() {
		return mapMonsterMap;
	}

	public void setMapMonsterMap(Map<String, List<MapMonsterBean>> mapMonsterMap) {
		this.mapMonsterMap = mapMonsterMap;
	}
	
}
