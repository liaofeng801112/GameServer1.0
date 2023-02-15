package org.come.model;

import java.math.BigDecimal;

/**
 * 灵宝技能
 * @author 叶豪芳
 * @date 2017年12月24日 下午6:58:55
 * 
 */ 
public class Linbaoskill {

	// 技能类型
	private String skilltype;
	// 技能名称
	private String skillname;
	// 描述
	private String describe;
	// 技能限制
	private String skilllimite;
	// 灵宝id
	private BigDecimal baoid;
	// 技能id
	private BigDecimal baoskillid;
	
	public String getSkilltype() {
		return skilltype;
	}
	public void setSkilltype(String skilltype) {
		this.skilltype = skilltype;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getSkilllimite() {
		return skilllimite;
	}
	public void setSkilllimite(String skilllimite) {
		this.skilllimite = skilllimite;
	}
	public BigDecimal getBaoid() {
		return baoid;
	}
	public void setBaoid(BigDecimal baoid) {
		this.baoid = baoid;
	}
	public BigDecimal getBaoskillid() {
		return baoskillid;
	}
	public void setBaoskillid(BigDecimal baoskillid) {
		this.baoskillid = baoskillid;
	}
	
}
