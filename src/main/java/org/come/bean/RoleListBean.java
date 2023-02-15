package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 返回所有角色的bean
 * 
 * @author Administrator
 * 
 */
public class RoleListBean {

	private List<Role_bean> roleList;
	// 状态(1表示登陆成功（返回对应账号下的角色集合）2表示用户名错误 3表示密码错误)
	private int statues;
	// 用户ID
	private BigDecimal userId;
	private double usermoney;
	// 区id
	private String qid;
	// 手机号
	private String phone;

	public double getUsermoney() {
		return usermoney;
	}

	public void setUsermoney(double usermoney) {
		this.usermoney = usermoney;
	}

	public List<Role_bean> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role_bean> roleList) {
		this.roleList = roleList;
	}

	public int getStatues() {
		return statues;
	}

	public void setStatues(int statues) {
		this.statues = statues;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
