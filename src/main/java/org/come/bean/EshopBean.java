package org.come.bean;

import java.util.List;

import org.come.model.Eshop;
/**
 * 点击在线商城返回的类
 * @author 叶豪芳
 *
 */

public class EshopBean {
	// 返回的商品信息
	private List<Eshop> eshops;

	public List<Eshop> getEshops() {
		return eshops;
	}

	public void setEshops(List<Eshop> eshops) {
		this.eshops = eshops;
	}


}
