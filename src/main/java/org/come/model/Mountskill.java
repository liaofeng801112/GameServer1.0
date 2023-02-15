package org.come.model;

/**
 * 坐骑技能
 * @author 叶豪芳
 * @date 2017年12月24日 下午9:04:57
 * 
 */ 
public class Mountskill {
	// 坐骑ID 
	private Integer mountid;
	// 角色种族
	private String rolerace;
	// 技能名称
	private String skillname;
	// 属性
	private String value;
	
	public Integer getMountid() {
		return mountid;
	}
	public void setMountid(Integer mountid) {
		this.mountid = mountid;
	}
	public String getRolerace() {
		return rolerace;
	}
	public void setRolerace(String rolerace) {
		this.rolerace = rolerace;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
