package org.come.mapper;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.BuyCount;

@MyBatisAnnotation
public interface BuyCountMapper {
	
	/**添加销售统计*/
	void insertBuyCount(BuyCount buyCount);
	/**修改销售统计*/
	void updateBuyCount(BuyCount buyCount);
	/**查询销售记录*/
	BuyCount selectBuyCount(long Bid);
}
