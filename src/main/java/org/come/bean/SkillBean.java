package org.come.bean;

import java.util.Map;

import org.come.model.SkillModel;
/**
 * 人物技能集合
 * @author 叶豪芳
 * @date 2018年1月30日 下午4:50:26
 * 
 */ 
public class SkillBean {
	private Map<String, SkillModel> skillMap;

	public Map<String, SkillModel> getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(Map<String, SkillModel> skillMap) {
		this.skillMap = skillMap;
	}

	
	
}
