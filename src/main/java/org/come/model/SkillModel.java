package org.come.model;

public class SkillModel {
	// 技能ID
	private String skillid;
	// 技能名称
	private String skillname;
	// 技能类型
	private String skilltype;
	// 技能等级
	private String skilllevel;
	// 成长
	private String grow;
	// 耗蓝介质
	private String dielectric;
	// 介值
	private String value;
	// 阵营
	private String camp;
	// 技能关系
	private String skillralation;
	// 备注
	private String remark;
	public SkillModel(Skill skill) {
		// TODO Auto-generated constructor stub
		this.skillid=skill.getSkillid()+"";
		this.skillname=skill.getSkillname();
		this.skilltype=skill.getSkilltype()+"";
		this.skilllevel=skill.getSkilllevel()+"";
		this.grow=skill.getGrow()+"";
		this.dielectric=skill.getDielectric()+"";
		this.value=skill.getValue()+"";
		this.camp=skill.getCamp()+"";
		this.skillralation=skill.getSkillralation();
		this.remark=skill.getRemark();
	}
	public String getSkillid() {
		return skillid;
	}
	public void setSkillid(String skillid) {
		this.skillid = skillid;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public String getSkilltype() {
		return skilltype;
	}
	public void setSkilltype(String skilltype) {
		this.skilltype = skilltype;
	}
	public String getSkilllevel() {
		return skilllevel;
	}
	public void setSkilllevel(String skilllevel) {
		this.skilllevel = skilllevel;
	}
	public String getGrow() {
		return grow;
	}
	public void setGrow(String grow) {
		this.grow = grow;
	}
	public String getDielectric() {
		return dielectric;
	}
	public void setDielectric(String dielectric) {
		this.dielectric = dielectric;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCamp() {
		return camp;
	}
	public void setCamp(String camp) {
		this.camp = camp;
	}
	public String getSkillralation() {
		return skillralation;
	}
	public void setSkillralation(String skillralation) {
		this.skillralation = skillralation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
