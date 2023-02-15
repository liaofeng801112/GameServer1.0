package org.come.bean;

import java.math.BigDecimal;
import java.util.Map;

import org.come.model.GoodModel;
/**
 * 物品bean按ID搜索
 * @author Administrator
 *
 */
public class GoodsBean {
	private Map<BigDecimal, GoodModel> allGoodsMap;

	public Map<BigDecimal, GoodModel> getAllGoodsMap() {
		return allGoodsMap;
	}

	public void setAllGoodsMap(Map<BigDecimal, GoodModel> allGoodsMap) {
		this.allGoodsMap = allGoodsMap;
	}
	
}
