package org.come.bean;

import java.math.BigDecimal;

public class NChatBean {
	//0当前 1队伍 2帮派 3世界 4传音 5系统 6消息 7系统加传音    8广告滚动   9系统滚动
	private int id;
	//发送者id
	private BigDecimal roleId;
	//发送者名称
	private String role;
	private String skin;
	//内容
	private String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	
}
