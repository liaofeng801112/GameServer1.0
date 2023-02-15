package org.come.bean;

import java.util.List;

import org.come.entity.Goodstable;
/**
 * 给于返回的物品列表信息
 * @author 叶豪芳
 * @date 2017年12月20日 下午12:11:35
 * 
 */  
public class GoodsListResultBean {
	// 物品列表
	private List<Goodstable> goodstables;

	public List<Goodstable> getGoodstables() {
		return goodstables;
	}

	public void setGoodstables(List<Goodstable> goodstables) {
		this.goodstables = goodstables;
	}

}
