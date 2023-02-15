package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Roleorder;
import org.come.entity.RoleorderExample;
import org.come.mapper.RoleorderMapper;
import org.come.service.IRoleorderService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class RoleorderServiceImpl implements IRoleorderService {
	
	private RoleorderMapper mapper;
	
	public RoleorderServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		mapper = ctx.getBean("roleorderMapper",RoleorderMapper.class);
	}

	@Override
	public int countByExample(RoleorderExample example) {
		
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(RoleorderExample example) {
		mapper.deleteByExample(example);
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal saleid) {
		mapper.deleteByPrimaryKey(saleid);
		return 0;
	}

	@Override
	public int insert(Roleorder record) {
		mapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(Roleorder record) {
		mapper.insertSelective(record);
		return 0;
	}

	@Override
	public List<Roleorder> selectByExample(RoleorderExample example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public Roleorder selectByPrimaryKey(BigDecimal saleid) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(saleid);
	}

	@Override
	public int updateByExampleSelective(Roleorder record, RoleorderExample example) {
		mapper.updateByExampleSelective(record, example);
		return 0;
	}

	@Override
	public int updateByExample(Roleorder record, RoleorderExample example) {
		mapper.updateByExample(record, example);
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Roleorder record) {
		mapper.updateByPrimaryKeySelective(record);
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Roleorder record) {
		mapper.updateByPrimaryKey(record);
		return 0;
	}

	@Override
	public List<Roleorder> selectRoleOrders(BigDecimal roleid) {
		
		return mapper.selectRoleOrders(roleid);
	}

}
