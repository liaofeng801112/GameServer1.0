package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

public class GoodsBackBean {

	/**
	 * 需取回的商品id
	 */
	private List<BigDecimal> ids;

	public List<BigDecimal> getIds() {
		return ids;
	}

	public void setIds(List<BigDecimal> ids) {
		this.ids = ids;
	}
	
}
