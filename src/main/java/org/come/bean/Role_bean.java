package org.come.bean;

import java.math.BigDecimal;

public class Role_bean {

	// 角色ID
	private BigDecimal role_id;
	// 角色名字
	private String rolename;
	// 等级
	private Integer grade;
	// 种族名称
	private String race_name;
	// 角色称谓
	private String title;
	// 帮派名称
	private String gangname;
	// 皮肤(头像)
	private String skin;
	public String getGangname() {
		return gangname;
	}
	public void setGangname(String gangname) {
		this.gangname = gangname;
	}
	public BigDecimal getRole_id() {
		return role_id;
	}
	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getRace_name() {
		return race_name;
	}
	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	
}
