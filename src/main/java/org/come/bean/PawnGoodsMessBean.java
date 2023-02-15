package org.come.bean;

import java.math.BigDecimal;

/**
 * 
 * @author 黄建彬
 * 用来传送典当的物品，对应的物品和典当的个数
 *
 */
public class PawnGoodsMessBean {

	//典当的物品
	private BigDecimal rgid;
	
	//典当的个数
	private int number;
    
	public BigDecimal getRgid() {
		return rgid;
	}

	public void setRgid(BigDecimal rgid) {
		this.rgid = rgid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
