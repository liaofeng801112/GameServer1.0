package org.come.entity;

import org.come.until.AllServiceUtil;

/**购买统计*/
public class BuyCount {
	private long Bid;//表id
	private int Ptype;//商店类型  0商城  1npc商店
	private int shopId;//商品id
	private int shopType;//商品分类
	private long totalNum;//总销售
	private long totalPrice;//总消耗货币
	private long weekNum;//周销售
	private long weekPrice;//周消耗货币
	private long dayNum;//日销售
	private long dayPrice;//日消耗货币
	
	private Boolean isUP=false;//是否被修改
	/**添加销售记录*/
	public void addCount(int sum,long price){
		synchronized (isUP) {
			totalNum+=sum;
			weekNum+=sum;
			dayNum+=sum;
			totalPrice+=price;
			weekPrice+=price;
			dayPrice+=price;
			isUP=true;
		}
	}
	/**每日刷新 day=2是周一*/
	public void Reset(int day){
		synchronized (isUP) {
			if (dayNum!=0||(day==2&&weekNum!=0)) {
				isUP=true;
			}
			dayNum=0;
			dayPrice=0;
			if (day==2) {
				weekNum=0;
				weekPrice=0;
			}		
		}
	}
	/**保存数据库*/
	public void upData(){
		synchronized (isUP) {
			if (isUP) {
				AllServiceUtil.getBuyCountServeice().updateBuyCount(this);
				isUP=false;
			}
		}
	}
	/**涨价商品价格获取 原则每次涨价幅度5% 不上限  购买数量  涨价所需购买次数    单价*/
	public long countPrice(int num,int priceNum,long shopPrice){
		synchronized (isUP) {
			long price=0;
			for (int i = 0; i < num;) {
				if ((weekNum+i)%priceNum==0) {
					int size=num-i;
					if (size>priceNum) {
						size=priceNum;
					}
					price+=(shopPrice*(1+(weekNum+i)/priceNum*0.05))*size;
					i+=size;
				}else {
					price+=(shopPrice*(1+(weekNum+i)/priceNum*0.05));
					i++;
				}
			}
			return price;	
		}
	}
	public long getBid() {
		return Bid;
	}
	public void setBid(long bid) {
		Bid = bid;
	}
	public int getPtype() {
		return Ptype;
	}
	public void setPtype(int ptype) {
		Ptype = ptype;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getShopType() {
		return shopType;
	}
	public void setShopType(int shopType) {
		this.shopType = shopType;
	}
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getWeekNum() {
		return weekNum;
	}
	public void setWeekNum(long weekNum) {
		this.weekNum = weekNum;
	}
	public long getWeekPrice() {
		return weekPrice;
	}
	public void setWeekPrice(long weekPrice) {
		this.weekPrice = weekPrice;
	}
	public long getDayNum() {
		return dayNum;
	}
	public void setDayNum(long dayNum) {
		this.dayNum = dayNum;
	}
	public long getDayPrice() {
		return dayPrice;
	}
	public void setDayPrice(long dayPrice) {
		this.dayPrice = dayPrice;
	}
	
}
