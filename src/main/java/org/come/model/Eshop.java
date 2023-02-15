package org.come.model;

import java.math.BigDecimal;

import org.come.entity.BuyCount;

/**
 * 商城类
 * 
 * @author 叶豪芳
 * @date : 2017年11月29日 下午3:21:29
 */
public class Eshop {
	// 物品id
	private String eshopid;	
	// 物品id [1]
	private BigDecimal eshopiid;
	// 名称
	private String eshopname;
	// 类型
	private String eshoptype;
	// 价格
	private long eshopprice;
	// 皮肤
	private String eshopskin;
	// 说明
	private String eshoptext;
	//等级条件
	private transient String lvl;
	//最大次数0就是没限制
	private transient int num;
	private transient int[] lvls;
	private transient BuyCount buyCount;
	public boolean addPrice(int num,long jg){
		buyCount.addCount(num, jg);
		return false;
	}
	public String getEshopid() {
		return eshopid;
	}
	public void setEshopid(String eshopid) {
		this.eshopid = eshopid;
	}
	public BigDecimal getEshopiid() {
		return eshopiid;
	}
	public void setEshopiid(BigDecimal eshopiid) {
		this.eshopiid = eshopiid;
	}
	public String getEshopname() {
		return eshopname;
	}
	public void setEshopname(String eshopname) {
		this.eshopname = eshopname;
	}
	public String getEshoptype() {
		return eshoptype;
	}
	public void setEshoptype(String eshoptype) {
		this.eshoptype = eshoptype;
	}
	public long getEshopprice() {
		return eshopprice;
	}
	public void setEshopprice(long eshopprice) {
		this.eshopprice = eshopprice;
	}
	public String getEshopskin() {
		return eshopskin;
	}
	public void setEshopskin(String eshopskin) {
		this.eshopskin = eshopskin;
	}
	public String getEshoptext() {
		return eshoptext;
	}
	public void setEshoptext(String eshoptext) {
		this.eshoptext = eshoptext;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int[] getLvls() {
		return lvls;
	}
	public void setLvls(int[] lvls) {
		this.lvls = lvls;
	}
	public BuyCount getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(BuyCount buyCount) {
		this.buyCount = buyCount;
	}
	
}
