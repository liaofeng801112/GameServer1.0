package org.come.serviceImpl;

import java.util.List;

import org.come.entity.Goodsexchange;
import org.come.entity.GoodsexchangeExample;
import org.come.mapper.GoodsexchangeMapper;
import org.come.service.IGoodsExchangeService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class GoodsexchangeServiceImpl implements IGoodsExchangeService {

	private GoodsexchangeMapper goodsexchangeMapper;

	public GoodsexchangeServiceImpl() {

		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		goodsexchangeMapper = ctx.getBean("goodsexchangeMapper",GoodsexchangeMapper.class);
	}

	@Override
	public int countByExample(GoodsexchangeExample example) {
		// TODO Auto-generated method stub
		return goodsexchangeMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(GoodsexchangeExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String goodsguid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Goodsexchange record) {
		goodsexchangeMapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(Goodsexchange record) {
		goodsexchangeMapper.insertSelective(record);
		return 0;
	}

	@Override
	public List<Goodsexchange> selectByExample(GoodsexchangeExample example) {
		// TODO Auto-generated method stub
		return goodsexchangeMapper.selectByExample(example);
	}

	@Override
	public Goodsexchange selectByPrimaryKey(String goodsguid) {
		return goodsexchangeMapper.selectByPrimaryKey(goodsguid);
	}

	@Override
	public int updateByExampleSelective(Goodsexchange record, GoodsexchangeExample example) {
		goodsexchangeMapper.updateByExampleSelective(record, example);
		return 0;
	}

	@Override
	public int updateByExample(Goodsexchange record, GoodsexchangeExample example) {
		goodsexchangeMapper.updateByExample(record, example);
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Goodsexchange record) {
		goodsexchangeMapper.updateByPrimaryKeySelective(record);
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Goodsexchange record) {
		goodsexchangeMapper.updateByPrimaryKey(record);
		return 0;
	}
	/**查询所有兑换码 */
	@Override
	public List<Goodsexchange> selectListAll() {
		return goodsexchangeMapper.selectListAll();
	}


}
