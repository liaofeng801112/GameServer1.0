package org.come.extInterBean;

public class RoleSellRoleInfo {

	// 角色名
	private String rolename;
	// 角色id
	private String roleid;
	// 账号id
	private String userid;
	// 种族id
	private String species_id;
	// 等级
	private String grade;
	// 种族名称
	private String race_name;
	// 是否已出售 ( 1 未出售 2 已出售)
	private String sell = "1";
	// 订单id
	private String saleid = "0";

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSpecies_id() {
		return species_id;
	}

	public void setSpecies_id(String species_id) {
		this.species_id = species_id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRace_name() {
		return race_name;
	}

	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getSaleid() {
		return saleid;
	}

	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}

}
