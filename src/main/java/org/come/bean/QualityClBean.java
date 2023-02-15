package org.come.bean;

import java.math.BigDecimal;

public class QualityClBean {
	private BigDecimal rgid;// 物品id
	private int type;//类型   1:炼化  2:炼器  3:神兵  4:套装洗炼  -4:直接替换新属性   5宝石     44重悟技能   46 升级技能
	private String newAttr;// 新属性  xxxx&xx=xx&xx=xx
	public BigDecimal getRgid() {
		return rgid;
	}
	public void setRgid(BigDecimal rgid) {
		this.rgid = rgid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNewAttr() {
		return newAttr;
	}
	public void setNewAttr(String newAttr) {
		this.newAttr = newAttr;
	}	
}
