package org.come.bean;

import java.math.BigDecimal;

public class QuackGameBean {
	private int type; //1： 5个   2：21个    3:    4   物品id_数量_0
	private String petmsg; //图片信息   
	private BigDecimal money; //中奖的金额 
	private String petmsg2;//补充字段    消耗的货币类型|消耗的替代物品 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPetmsg() {
		return petmsg;
	}
	public void setPetmsg(String petmsg) {
		this.petmsg = petmsg;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getPetmsg2() {
		return petmsg2;
	}
	public void setPetmsg2(String petmsg2) {
		this.petmsg2 = petmsg2;
	}
}
