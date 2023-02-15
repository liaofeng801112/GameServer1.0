package org.come.bean;

import java.util.List;

import org.come.model.Shop;

public class ShopGoodsBean {
	
	//哪个限购商人
	private Integer nId;
	//判断类型 0限购
	private int type;
	//所有的货物
	private List<Shop> shopList;
	
	
	public Integer getnId() {
		return nId;
	}
	public void setnId(Integer nId) {
		this.nId = nId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}
