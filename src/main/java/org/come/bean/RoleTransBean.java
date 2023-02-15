package org.come.bean;

import java.util.List;

import org.come.entity.Mount;

import come.tool.Role.PrivateData;

public class RoleTransBean {

	private LoginResult loginResult;
	private PrivateData privateData;
	private List<Mount> mounts;
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public PrivateData getPrivateData() {
		return privateData;
	}
	public void setPrivateData(PrivateData privateData) {
		this.privateData = privateData;
	}
	public List<Mount> getMounts() {
		return mounts;
	}
	public void setMounts(List<Mount> mounts) {
		this.mounts = mounts;
	}
	
}
