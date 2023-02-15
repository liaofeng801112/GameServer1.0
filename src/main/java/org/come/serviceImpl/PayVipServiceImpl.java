package org.come.serviceImpl;

import java.util.List;

import org.come.entity.PayvipBean;
import org.come.mapper.PayVipMapper;
import org.come.service.PayVipService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class PayVipServiceImpl implements PayVipService {

	private PayVipMapper payVipMapper;

	public PayVipServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		payVipMapper = ctx.getBean("payVipMapper", PayVipMapper.class);
	}

	@Override
	public List<PayvipBean> selectAllVip() {
		// TODO Auto-generated method stub
		return payVipMapper.selectAllVip();
	}

}
