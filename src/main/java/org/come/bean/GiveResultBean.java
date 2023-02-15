package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Goodstable;
/**
 * 给于返回的bean
 * @author 叶豪芳
 * @date 2017年12月22日 上午11:35:10
 * 
 */ 
public class GiveResultBean {
	// 角色名
	private String rolename;
	// 物品信息
	private Goodstable goodstable;
	// 金币
	private BigDecimal gold;
	// 角色所有物品
	private List<Goodstable> goodstables;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Goodstable getGoodstable() {
		return goodstable;
	}

	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	public List<Goodstable> getGoodstables() {
		return goodstables;
	}

	public void setGoodstables(List<Goodstable> goodstables) {
		this.goodstables = goodstables;
	}
	
}
