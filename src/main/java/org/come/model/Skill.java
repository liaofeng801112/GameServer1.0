package org.come.model;


public class Skill {
	// 技能ID
	private int skillid;
	// 技能名称
	private String skillname;
	// 技能类型
	private int skilltype;
	// 技能等级
	private int skilllevel;
	// 成长
	private double grow;
	// 耗蓝介质
	private double dielectric;
	// 介值
	private double value;
	// 阵营
	private int camp;
	// 技能关系
	private String skillralation;
	//成长1
	private double grow1;
	// 介值1
	private double value1;
	// 备注
	private String remark;


	public int getSkillid() {
		return skillid;
	}
	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public int getSkilltype() {
		return skilltype;
	}
	public void setSkilltype(int skilltype) {
		this.skilltype = skilltype;
	}
	public int getSkilllevel() {
		return skilllevel;
	}
	public void setSkilllevel(int skilllevel) {
		this.skilllevel = skilllevel;
	}
	public double getGrow() {
		return grow;
	}
	public void setGrow(double grow) {
		this.grow = grow;
	}
	public double getDielectric() {
		return dielectric;
	}
	public void setDielectric(double dielectric) {
		this.dielectric = dielectric;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
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
	public double getGrow1() {
		/* 111 */     return this.grow1;
		/*     */   }

	public void setGrow1(double grow1) {
		/* 115 */     this.grow1 = grow1;
		/*     */   }

	public double getValue1() {
		/* 119 */     return this.value1;
		/*     */   }

	public void setValue1(double value1) {
		/* 123 */     this.value1 = value1;
		/*     */   }


}