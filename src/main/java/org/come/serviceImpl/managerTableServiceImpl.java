package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.managerTable;
import org.come.mapper.managerTableMapper;
import org.come.service.managerTableService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class managerTableServiceImpl implements managerTableService{
	//实例化
		private managerTableMapper managerTableMapper;
	public managerTableServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		managerTableMapper = (managerTableMapper)ctx.getBean("managerTableMapper");
	}
	
	@Override
	public int deleteByPrimaryKey(BigDecimal manaeid) {
		// TODO Auto-generated method stub
		return managerTableMapper.deleteByPrimaryKey(manaeid);
	}

	@Override
	public int insert(managerTable record) {
		// TODO Auto-generated method stub
		return managerTableMapper.insert(record);
	}

	@Override
	public int insertSelective(managerTable record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public managerTable selectByPrimaryKey(BigDecimal manaeid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(managerTable record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(managerTable record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public managerTable selectByUsername(managerTable record) {
		// TODO Auto-generated method stub
		return managerTableMapper.selectByUsername(record);
	}

	@Override
	public List<managerTable> selectManageForPage(int pageNumber) {
		// TODO Auto-generated method stub
		return managerTableMapper.selectManageForPage(pageNumber);
	}

}
