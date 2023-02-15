package org.come.model;

import java.math.BigDecimal;

import org.come.entity.BuyCount;

/**
 * 商城类
 * 
 * @author 叶豪芳
 * @date : 2017年11月29日 下午3:21:29
 */
public class Shop {
	// 物品id
	private String shopid;	
	// 物品id [1]
	private BigDecimal shopiid;
	// 名称
	private String shopname;
	// 类型
	private int shoptype;
	// 价格
	private long shopprice;
	// 皮肤
	private String shopskin;
	// 说明
	private String shoptext;
	//等级条件
	private transient String lvl;
	//最大次数0就是没限制
	private transient int num;
	//价格递增数量
	private transient int priceNum;
	private transient int[] lvls;
	private Boolean isPrice;//变动价格
	private transient BuyCount buyCount;
	public Shop() {
		// TODO Auto-generated constructor stub
	}
	public long getPrice(){
		if (priceNum==0) {
			return shopprice;
		}
		return buyCount.countPrice(1, priceNum, shopprice);
	}
	public long getPrice(int num){
		if (priceNum==0) {
			return shopprice*num;
		}
		return buyCount.countPrice(num, priceNum, shopprice);
	}
	public boolean addPrice(int num,long jg){
		if (priceNum==0) {
			buyCount.addCount(num, jg);
			return false;
		}
		int size=(int) (buyCount.getWeekNum()/priceNum);
		buyCount.addCount(num, jg);
		return buyCount.getWeekNum()/priceNum!=size;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public BigDecimal getShopiid() {
		return shopiid;
	}
	public void setShopiid(BigDecimal shopiid) {
		this.shopiid = shopiid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public int getShoptype() {
		return shoptype;
	}
	public void setShoptype(int shoptype) {
		this.shoptype = shoptype;
	}
	public long getShopprice() {
		return shopprice;
	}
	public void setShopprice(long shopprice) {
		this.shopprice = shopprice;
	}
	public String getShopskin() {
		return shopskin;
	}
	public void setShopskin(String shopskin) {
		this.shopskin = shopskin;
	}
	public String getShoptext() {
		return shoptext;
	}
	public void setShoptext(String shoptext) {
		this.shoptext = shoptext;
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
	public int getPriceNum() {
		return priceNum;
	}
	public void setPriceNum(int priceNum) {
		this.priceNum = priceNum;
	}
	public BuyCount getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(BuyCount buyCount) {
		this.buyCount = buyCount;
	}
	public Boolean getIsPrice() {
		return isPrice;
	}
	public void setIsPrice(Boolean isPrice) {
		this.isPrice = isPrice;
	}
	
}
