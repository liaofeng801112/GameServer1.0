package org.come.bean;

import java.util.List;
/**
 * 返回该用户下所有角色的信息
 * @author 叶豪芳
 * @date : 2017年11月26日 下午8:46:11
 */
public class UserRoleArrBean {
	private List<LoginResult>  list;
	private int phonestatues;
	public UserRoleArrBean() {
		// TODO Auto-generated constructor stub
	}
	public List<LoginResult> getList() {
		return list;
	}
	public void setList(List<LoginResult> list) {
		this.list = list;
	}
	public int getPhonestatues() {
		return phonestatues;
	}
	public void setPhonestatues(int phonestatues) {
		this.phonestatues = phonestatues;
	}
	

}
