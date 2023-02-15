package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Openareatable;
import org.come.mapper.OpenareatableMapper;
import org.come.service.OpenareatableService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class OpenareatableServiceImpl implements OpenareatableService {
	private OpenareatableMapper openareatableMapper;

	public OpenareatableServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		openareatableMapper = (OpenareatableMapper) ctx.getBean("openareatableMapper");
	}

	@Override
	public List<Openareatable> selectAllOpenareatable() {
		// TODO Auto-generated method stub
		return openareatableMapper.selectAllOpenareatable();
	}

	@Override
	public Integer insertOpenareatable(Openareatable openareatable) {
		// TODO Auto-generated method stub
		return openareatableMapper.insertOpenareatable(openareatable);
	}

	@Override
	public Integer updateOpenareatable(Openareatable openareatable) {
		// TODO Auto-generated method stub
		return openareatableMapper.updateOpenareatable(openareatable);
	}

	@Override
	public Integer deleteOpenareatable(BigDecimal tb_id) {
		// TODO Auto-generated method stub
		return openareatableMapper.deleteOpenareatable(tb_id);
	}

	@Override
	public List<BigDecimal> selectTuijiNum(String tuiji) {
		// TODO Auto-generated method stub
		return openareatableMapper.selectTuijiNum(tuiji);
	}

	@Override
	public List<Openareatable> selectAllArea(BigDecimal userid) {
		// TODO Auto-generated method stub
		return openareatableMapper.selectAllArea(userid);
	}

	@Override
	public String selectBelong(String qid) {
		// TODO Auto-generated method stub
		return openareatableMapper.selectBelong(qid);
	}

	@Override
	public String selectAtid(String qid) {
		// TODO Auto-generated method stub
		return openareatableMapper.selectAtid(qid);
	}

}
