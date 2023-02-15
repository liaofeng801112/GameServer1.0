package org.come.model;

import java.math.BigDecimal;

/**
 * 内丹等级
 * @author 叶豪芳
 * @date 2017年11月27日 下午8:24:11
 * 
 */ 
public class NeidanExp {

	// 内丹等级
	private Integer level;
	
	// 内丹所需等级
	private BigDecimal exp;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigDecimal getExp() {
		return exp;
	}

	public void setExp(BigDecimal exp) {
		this.exp = exp;
	}

	
}
