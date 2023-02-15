package org.come.bean;

import java.util.List;

import org.come.entity.Goodstable;
/**
 * 使用礼包获得的物品ID
 * @author 叶豪芳
 * @date 2017年12月27日 下午9:00:16
 * 
 */ 
public class PackGiftBean {
	//使用的礼包表id
	private Goodstable goodstable;
	//物品id和数量
	private List<String> goods;
    public Goodstable getGoodstable() {
		return goodstable;
	}
	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}
	public List<String> getGoods() {
		return goods;
	}
	public void setGoods(List<String> goods) {
		this.goods = goods;
	}
}
