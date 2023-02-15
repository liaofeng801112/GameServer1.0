package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Wechatrecord;
import org.come.entity.WechatrecordExample;
import org.come.mapper.WechatrecordMapper;
import org.come.service.IWechatrecordService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class WechatrecordServiceImpl implements IWechatrecordService {

	private WechatrecordMapper wechatrecordMapper;
	
	public WechatrecordServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		wechatrecordMapper = ctx.getBean("wechatrecordMapper",WechatrecordMapper.class);
	}
	@Override
	public int countByExample(WechatrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(WechatrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal chatId) {
		wechatrecordMapper.deleteByPrimaryKey(chatId);
		return 0;
	}

	@Override
	public int insert(Wechatrecord record) {
		wechatrecordMapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(Wechatrecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Wechatrecord> selectByExample(WechatrecordExample example) {
		
		return wechatrecordMapper.selectByExample(example);
	}

	@Override
	public Wechatrecord selectByPrimaryKey(BigDecimal chatId) {
		return null;
	}

	@Override
	public int updateByExampleSelective(Wechatrecord record, WechatrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(Wechatrecord record, WechatrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Wechatrecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Wechatrecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
