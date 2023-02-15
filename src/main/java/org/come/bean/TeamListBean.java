package org.come.bean;

import java.util.List;

public class TeamListBean {
	// 角色ID
	private LoginResult roleInfo;
	// 队伍所有成员信息
	private List<LoginResult> teamRoles;

	public List<LoginResult> getTeamRoles() {
		return teamRoles;
	}

	public void setTeamRoles(List<LoginResult> teamRoles) {
		this.teamRoles = teamRoles;
	}

	public LoginResult getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(LoginResult roleInfo) {
		this.roleInfo = roleInfo;
	}

	
}
