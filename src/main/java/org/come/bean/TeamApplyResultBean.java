package org.come.bean;

import java.util.List;

/**
 * 申请列表
 * @author 叶豪芳
 *
 */
public class TeamApplyResultBean {
	// 申请列表所有角色信息
	private List<String> roleInfoList;
	//判断是否能加入队伍,1
	private int joinTeam;

	public List<String> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<String> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public int getJoinTeam() {
		return joinTeam;
	}

	public void setJoinTeam(int joinTeam) {
		this.joinTeam = joinTeam;
	}

}
