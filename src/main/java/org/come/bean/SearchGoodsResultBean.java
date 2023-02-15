package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Salegoods;

/**
 * 商品搜索返回bean
 * @author Administrator
 *
 */
public class SearchGoodsResultBean {
	/**
	 * 商品集合
	 */
	private List<Salegoods> salegoods;
	
	/**
	 * 总页数
	 */
	private int total;

	/**
	 * 收藏列表
	 */
	private List<BigDecimal> collections;
	public List<Salegoods> getSalegoods() {
		return salegoods;
	}

	public void setSalegoods(List<Salegoods> salegoods) {
		this.salegoods = salegoods;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<BigDecimal> getCollections() {
		return collections;
	}

	public void setCollections(List<BigDecimal> collections) {
		this.collections = collections;
	}
	
}
