package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.mapper.SalegoodsMapper;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.ISalegoodsService;
import org.come.until.GsonUtil;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class SalegoodsServiceImpl implements ISalegoodsService {
	
	private SalegoodsMapper salegoodsMapper;
	
	public SalegoodsServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		salegoodsMapper = ctx.getBean("salegoodsMapper",SalegoodsMapper.class);
	}
	@Override
	public int countByExample(SalegoodsExample example) {
		return salegoodsMapper.countByExample(example);
	}
	@Override
	public int deleteByExample(SalegoodsExample example) {
		salegoodsMapper.deleteByExample(example);
		return 0;
	}
	@Override
	public int deleteByPrimaryKey(BigDecimal saleid) {
		salegoodsMapper.deleteByPrimaryKey(saleid);
		deleteFlag(saleid);
		return 0;
	}
	@Override
	public int insert(Salegoods record) {
		salegoodsMapper.insert(record);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public int insertSelective(Salegoods record) {
		salegoodsMapper.insertSelective(record);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public List<Salegoods> selectByExample(SalegoodsExample example) {
		// TODO Auto-generated method stub
		return salegoodsMapper.selectByExample(example);
	}
	@Override
	public Salegoods selectByPrimaryKey(BigDecimal saleid) {
		// TODO Auto-generated method stub
		return salegoodsMapper.selectByPrimaryKey(saleid);
	}
	@Override
	public int updateByExampleSelective(Salegoods record, SalegoodsExample example) {
		salegoodsMapper.updateByExampleSelective(record, example);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public int updateByExample(Salegoods record, SalegoodsExample example) {
		salegoodsMapper.updateByExample(record, example);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public int updateByPrimaryKeySelective(Salegoods record) {
		salegoodsMapper.updateByPrimaryKeySelective(record);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public int updateByPrimaryKey(Salegoods record) {
		salegoodsMapper.updateByPrimaryKey(record);
		updateFlag(record.getSaleid(),record.getFlag());
		return 0;
	}
	@Override
	public List<Salegoods> selectByAll() {
		// TODO Auto-generated method stub
		return salegoodsMapper.selectByAll();
	}
	@Override
	public void updateFlag(BigDecimal saleid,Integer flag) {
		if (saleid==null||flag==null) {return;}
		RedisControl.insertKey(RedisParameterUtil.SALESGOODS_STATUES,saleid.toString(),GsonUtil.getGsonUtil().getgson().toJson(flag));		
	}
	@Override
	public void deleteFlag(BigDecimal saleid) {
		RedisControl.delForKey(RedisParameterUtil.SALESGOODS_STATUES, saleid.toString());
	}
	@Override
	public Integer selectFlag(BigDecimal saleid) {
		return RedisControl.getV(RedisParameterUtil.SALESGOODS_STATUES, saleid.toString(), Integer.class);
	}
	@Override
	public Salegoods selectSaleGoodsByRoleid(String roleid) {
		// TODO Auto-generated method stub
		return salegoodsMapper.selectSaleGoodsByRoleid(roleid);
	}
}
