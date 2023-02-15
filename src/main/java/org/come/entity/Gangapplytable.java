package org.come.entity;

import java.math.BigDecimal;

/**
 * 进入帮派申请列表
 * @author 叶豪芳
 * @date 2017年12月21日 下午9:53:05
 * 
 */ 
public class Gangapplytable {
	// 角色ID
	private BigDecimal role_id;
	
	// 角色名称
	private String rolename;
	
	// 种族名称
	private String race_name;
	
	// 角色等级
	private BigDecimal grade;
	
	// 帮派ID
	private BigDecimal gangid;

	public BigDecimal getRole_id() {
		return role_id;
	}

	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRace_name() {
		return race_name;
	}

	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}

	public BigDecimal getGangid() {
		return gangid;
	}

	public void setGangid(BigDecimal gangid) {
		this.gangid = gangid;
	}
}
