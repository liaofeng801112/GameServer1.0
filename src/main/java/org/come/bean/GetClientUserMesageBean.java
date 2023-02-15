package org.come.bean;

import java.util.List;

import come.tool.Role.RoleShow;
import come.tool.Stall.StallBean;
/**
 * 返回用户角色信息
 * @author 叶豪芳
 * @date : 2017年11月30日 下午2:37:18
 */
public class GetClientUserMesageBean {
	//判断是否到了新地图
	private int ismap;
	private List<RoleShow> roleShows;
	//怪物集合
	private String monster;
	// 记录摆摊
	private List<StallBean> stallBeans;
	//副本数据
	private String sceneMsg;
	public String getSceneMsg() {
		return sceneMsg;
	}
	public void setSceneMsg(String sceneMsg) {
		this.sceneMsg = sceneMsg;
	}
	public int getIsmap() {
		return ismap;
	}
	public void setIsmap(int ismap) {
		this.ismap = ismap;
	}
	public List<RoleShow> getRoleShows() {
		return roleShows;
	}
	public void setRoleShows(List<RoleShow> roleShows) {
		this.roleShows = roleShows;
	}
	public String getMonster() {
		return monster;
	}
	public void setMonster(String monster) {
		this.monster = monster;
	}
	public List<StallBean> getStallBeans() {
		return stallBeans;
	}
	public void setStallBeans(List<StallBean> stallBeans) {
		this.stallBeans = stallBeans;
	}
}
