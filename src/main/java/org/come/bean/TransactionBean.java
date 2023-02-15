package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;

/**
 * 点击交易返回的面板信息
 * @author 叶豪芳
 * @date 2017年12月20日 下午3:59:55
 * 
 */ 
public class TransactionBean {
	// 对方角色ID
	private String roleName;
	
	// 角色背包物品
	private List<Goodstable> goodstables;

	// 角色召唤兽列表
	private List<RoleSummoning> roleSummonings;
	
	// 金币
	private BigDecimal gold;

	public List<Goodstable> getGoodstables() {
		return goodstables;
	}

	public void setGoodstables(List<Goodstable> goodstables) {
		this.goodstables = goodstables;
	}

	public List<RoleSummoning> getRoleSummonings() {
		return roleSummonings;
	}

	public void setRoleSummonings(List<RoleSummoning> roleSummonings) {
		this.roleSummonings = roleSummonings;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
