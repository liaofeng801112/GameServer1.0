package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

import come.tool.Role.PartJade;

public class SuitOperBean {
    //类型   0:合成   1:洗炼  2:保留洗炼      3:套装升级    4:玉符升级    
	//    5:拆解   6:转移  7:兑换灵修值  8:收录            9:生成玉符
	//   10:开光11:炼器 12保留炼器  13:清除炼器
	//14炼化 //15一键培养佩饰   16神兵升级
	//17宝石合成//18宝石重铸//19宝石鉴定
	private int type;
	//物品集合  第一个主物品
	private List<BigDecimal> goods;
	//玉符
	private PartJade jade;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<BigDecimal> getGoods() {
		return goods;
	}
	public void setGoods(List<BigDecimal> goods) {
		this.goods = goods;
	}
	public PartJade getJade() {
		return jade;
	}
	public void setJade(PartJade jade) {
		this.jade = jade;
	}
}
