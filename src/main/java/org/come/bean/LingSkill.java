package org.come.bean;

/**
 * 灵宝技能
 * @author Administrator
 *
 */
public class LingSkill {
    //技能名称
	private String skillname;
	//技能合计数
	private int skillsum;
	//技能作用人数
	private int Effectsum;	
	//是否为擅长技能
	private boolean shanchang=false;
	
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public int getSkillsum() {
		return skillsum;
	}
	public void setSkillsum(int skillsum) {
		this.skillsum = skillsum;
	}
	public int getEffectsum() {
		return Effectsum;
	}
	public void setEffectsum(int effectsum) {
		Effectsum = effectsum;
	}

	public boolean isShanchang() {
		return shanchang;
	}

	public void setShanchang(boolean shanchang) {
		this.shanchang = shanchang;
	}
	
	
	
}
