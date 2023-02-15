package org.come.serviceImpl;

import java.util.List;

import org.come.entity.GoodsRoleUser;
import org.come.mapper.GoodsRoleUsertMapper;
import org.come.service.GoodsRoleUsertService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class GoodsRoleUsertServiceImpl implements GoodsRoleUsertService {

	private GoodsRoleUsertMapper goodsRoleUsertMapper;

	private final Integer limit = 10;

	public GoodsRoleUsertServiceImpl() {
		// TODO Auto-generated constructor stub
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		goodsRoleUsertMapper = ctx.getBean("goodsRoleUsertMapper", GoodsRoleUsertMapper.class);
	}

	@Override
	public List<GoodsRoleUser> selectGoodsByPage(GoodsRoleUser goodsRoleUser) {
		// TODO Auto-generated method stub
		Integer start = ((goodsRoleUser.getPageNow() - 1) * limit) + 1;
		Integer end = start + limit;
		goodsRoleUser.setStart(start);
		goodsRoleUser.setEnd(end);
		List<GoodsRoleUser> goodsList = goodsRoleUsertMapper.selectGoodsByPage(goodsRoleUser);
		return goodsList;
	}

	@Override
	public Integer selectGoodsCount(GoodsRoleUser goodsRoleUser) {
		// TODO Auto-generated method stub
		Integer goodsCount = goodsRoleUsertMapper.selectGoodsCount(goodsRoleUser);
		return goodsCount;
	}

}
