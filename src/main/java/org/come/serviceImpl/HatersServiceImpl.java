package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Haters;
import org.come.entity.HatersExample;
import org.come.mapper.HatersMapper;
import org.come.service.IHatersService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class HatersServiceImpl implements IHatersService {
	private HatersMapper hatersMapper;
	
	public HatersServiceImpl(){
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		hatersMapper = ctx.getBean("hatersMapper",HatersMapper.class);
		
	}
	@Override
	public int countByExample(HatersExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(HatersExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal roleid) {
		return hatersMapper.deleteByPrimaryKey(roleid);
	}

	@Override
	public int insert(Haters record) {
		hatersMapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(Haters record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Haters> selectByExample(HatersExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Haters selectByPrimaryKey(BigDecimal roleid) {
		return hatersMapper.selectByPrimaryKey(roleid);
	}

	@Override
	public int updateByExampleSelective(Haters record, HatersExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(Haters record, HatersExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Haters record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Haters record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
