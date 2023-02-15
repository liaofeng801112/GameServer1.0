package org.come.serviceImpl;

import org.come.entity.BuyCount;
import org.come.mapper.BuyCountMapper;
import org.come.service.BuyCountServeice;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class BuyCountServiceImpl implements BuyCountServeice {
	private BuyCountMapper buyCountMapper;
	public BuyCountServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		buyCountMapper = (BuyCountMapper)ctx.getBean("buyCountMapper");
	}
	@Override
	public void insertBuyCount(BuyCount buyCount) {
		// TODO Auto-generated method stub
		buyCountMapper.insertBuyCount(buyCount);
	}
	@Override
	public void updateBuyCount(BuyCount buyCount) {
		// TODO Auto-generated method stub
		buyCountMapper.updateBuyCount(buyCount);
	}
	@Override
	public BuyCount selectBuyCount(long Bid) {
		// TODO Auto-generated method stub
		return buyCountMapper.selectBuyCount(Bid);
	}
	
}
