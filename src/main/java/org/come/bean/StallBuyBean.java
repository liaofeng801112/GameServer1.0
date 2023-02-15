package org.come.bean;

import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;

/**
 * 摆摊购买bean
 * @author 叶豪芳
 * @date 2017年12月31日 上午5:25:43
 * 
 */ 
public class StallBuyBean {
	
	// 购买物品
	private Goodstable goodstable;
	
	// 摆摊人名字
	private String stallName;
	
	// 购买宠物
	private RoleSummoning roleSummoning;

	public Goodstable getGoodstable() {
		return goodstable;
	}

	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}

	public RoleSummoning getRoleSummoning() {
		return roleSummoning;
	}

	public void setRoleSummoning(RoleSummoning roleSummoning) {
		this.roleSummoning = roleSummoning;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	
}
