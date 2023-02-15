package org.come.bean;

import java.math.BigDecimal;

/**
 * 给于发送的bean
 * @author 叶豪芳
 * @date 2017年12月20日 上午11:45:31
 * 
 */ 
public class GiveGoodsBean {
	// 给于的物品信息
//	private Goodstable goodstable;
	private BigDecimal rgid;
	// 数量
	private int sum;
	// 金币
	private BigDecimal gold;
	// 对方的ID
	private BigDecimal otherID;
	// 对方的名字
	private String otherName;
	// 回收类型 0给予玩家 1金币 2仙玉  3限时回收
	private int type;
	
	public BigDecimal getOtherID() {
		return otherID;
	}

	public void setOtherID(BigDecimal otherID) {
		this.otherID = otherID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}

	public BigDecimal getRgid() {
		return rgid;
	}

	public void setRgid(BigDecimal rgid) {
		this.rgid = rgid;
	}

}
