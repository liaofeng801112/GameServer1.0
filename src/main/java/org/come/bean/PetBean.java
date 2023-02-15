package org.come.bean;

import java.util.Map;

import org.come.entity.RoleSummoning;
/**
 * 所有召唤兽信息
 * @author 叶豪芳
 * @date 2018年2月1日 上午10:00:32
 * 
 */ 
public class PetBean {
	private Map<String, RoleSummoning> allPetInfo;

	public Map<String, RoleSummoning> getAllPetInfo() {
		return allPetInfo;
	}

	public void setAllPetInfo(Map<String, RoleSummoning> allPetInfo) {
		this.allPetInfo = allPetInfo;
	}

	
	
}
