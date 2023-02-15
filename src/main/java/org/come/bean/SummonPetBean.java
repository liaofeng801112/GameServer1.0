package org.come.bean;

import java.math.BigDecimal;
/**
 * 神兽召唤
 * @author Administrator
 *
 */
public class SummonPetBean {

	//进行的操作类型
	private int opertype;// 0:获得野生召唤兽 1:金柳露 2:兑换
	// 新赠的召唤兽id或者修改
	private BigDecimal petid;
	private BigDecimal goodid;//消耗的物品id
	public int getOpertype() {
		return opertype;
	}
	public void setOpertype(int opertype) {
		this.opertype = opertype;
	}
	public BigDecimal getPetid() {
		return petid;
	}
	public void setPetid(BigDecimal petid) {
		this.petid = petid;
	}
	public BigDecimal getGoodid() {
		return goodid;
	}
	public void setGoodid(BigDecimal goodid) {
		this.goodid = goodid;
	}

	
}
