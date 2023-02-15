package org.come.bean;

import java.math.BigDecimal;

import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;

/**
 * 交易实施bean,返回对方物品信息或召唤兽信息
 * @author 叶豪芳
 * @date 2017年12月22日 下午8:54:00
 * 
 */ 
public class TransPlanBean {
	// 物品信息
	private Goodstable goodstable;

	// 召唤兽信息
	private RoleSummoning summoning;

	// 金币
	private BigDecimal gold;

	// 对方角色ID
	private BigDecimal roleID;

	public Goodstable getGoodstable() {
		return goodstable;
	}

	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}

	public RoleSummoning getSummoning() {
		return summoning;
	}

	public void setSummoning(RoleSummoning summoning) {
		this.summoning = summoning;
	}

	public BigDecimal getRoleID() {
		return roleID;
	}

	public void setRoleID(BigDecimal roleID) {
		this.roleID = roleID;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

}
