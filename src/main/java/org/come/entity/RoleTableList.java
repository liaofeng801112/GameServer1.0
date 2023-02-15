package org.come.entity;

import java.util.List;

/**
 * 三端
 * @author zz
 * @time 2019年7月17日10:40:31
 * 
 */
public class RoleTableList {

	private List<RoleTableNew> roleList;
	private String userid;
	private String atid;
	private String username;
	private String pasw;

	public RoleTableList() {
		// TODO Auto-generated constructor stub
	}

	public List<RoleTableNew> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleTableNew> roleList) {
		this.roleList = roleList;
	}
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAtid() {
		return atid;
	}

	public void setAtid(String atid) {
		this.atid = atid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasw() {
		return pasw;
	}

	public void setPasw(String pasw) {
		this.pasw = pasw;
	}
}
