package org.come.entity;

import java.math.BigDecimal;
/**
 * 角色好友信息
 * @author 叶豪芳
 *
 */
public class Friendtable {
	// 角色ID
	private BigDecimal roleid;
	// 好友的角色ID
	private BigDecimal role_id;
	// 好友角色名
	private String rolename;
	// 种族名称
	private String race_name;
    // 好友等级
	private BigDecimal grade;
	// 在线状态(1表示在线，0表示不在线)
	private int OnlineState;
	// 种类ID
    private BigDecimal species_id;
	
	
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
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
	public int getOnlineState() {
		return OnlineState;
	}
	public void setOnlineState(int onlineState) {
		OnlineState = onlineState;
	}
	public BigDecimal getSpecies_id() {
		return species_id;
	}
	public void setSpecies_id(BigDecimal species_id) {
		this.species_id = species_id;
	}
	
}
