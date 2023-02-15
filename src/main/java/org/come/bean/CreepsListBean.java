package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Goodstable;
import org.come.model.Monster;

/**
 * 类说明
 * 
 * @author 黄建彬
 * @date 2017年12月29日 新建
 * 
 * 点击野怪获取野怪的集合
 */ 

public class CreepsListBean {
	// 战斗时候的野鬼集合
	private List<Monster> fightCreeps;
	
	// 获得物品
	private Goodstable goodstable;
	
	// 经验
	private BigDecimal exp;
	
	// 金钱
	private BigDecimal gold;
	
	// 技能
	private Integer skilled;
	
	private int maxlvl;
	

	public List<Monster> getFightCreeps() {
		return fightCreeps;
	}

	public void setFightCreeps(List<Monster> fightCreeps) {
		this.fightCreeps = fightCreeps;
	}

	public Goodstable getGoodstable() {
		return goodstable;
	}

	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}

	public BigDecimal getExp() {
		return exp;
	}

	public void setExp(BigDecimal exp) {
		this.exp = exp;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	public Integer getSkilled() {
		return skilled;
	}

	public void setSkilled(Integer skilled) {
		this.skilled = skilled;
	}

	public int getMaxlvl() {
		return maxlvl;
	}

	public void setMaxlvl(int maxlvl) {
		this.maxlvl = maxlvl;
	}
	
	
}
