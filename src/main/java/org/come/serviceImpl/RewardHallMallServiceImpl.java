package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;

import org.come.entity.RewardHall;
import org.come.entity.RewardHallExample;
import org.come.mapper.RewardHallMapper;
import org.come.service.IRewardHallMallService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class RewardHallMallServiceImpl implements IRewardHallMallService {

	private RewardHallMapper rewardHallMapper;
	
	public RewardHallMallServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		rewardHallMapper = ctx.getBean("rewardHallMapper", RewardHallMapper.class);
		
	}
	@Override
	public int countByExample(RewardHallExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(RewardHallExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal id) {
		rewardHallMapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insert(RewardHall record) {
		rewardHallMapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(RewardHall record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CopyOnWriteArrayList<RewardHall> selectByExample(RewardHallExample example) {
		
		return rewardHallMapper.selectByExample(example);
	}

	@Override
	public RewardHall selectByPrimaryKey(BigDecimal id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(RewardHall record,
			RewardHallExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(RewardHall record, RewardHallExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(RewardHall record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RewardHall record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
