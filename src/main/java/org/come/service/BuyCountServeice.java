package org.come.service;

import org.come.entity.BuyCount;

public interface BuyCountServeice {
	
	/**添加销售统计*/
	void insertBuyCount(BuyCount buyCount);
	/**修改销售统计*/
	void updateBuyCount(BuyCount buyCount);
	/**查询销售记录*/
	BuyCount selectBuyCount(long Bid);
	
}
