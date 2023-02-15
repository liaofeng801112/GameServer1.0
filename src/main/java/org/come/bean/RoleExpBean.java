package org.come.bean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 角色、召唤兽等级经验
 * @author 叶豪芳
 * @date 2017年11月15日 下午4:26:23
 * 
 */ 
public class RoleExpBean {
	// 等级经验
	private Map<Integer, BigDecimal> rolePetExpMap;

	public Map<Integer, BigDecimal> getRolePetExpMap() {
		return rolePetExpMap;
	}

	public void setRolePetExpMap(Map<Integer, BigDecimal> rolePetExpMap) {
		this.rolePetExpMap = rolePetExpMap;
	}
	
	
}
