package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.PackRecord;
import org.come.entity.PackRecordExample;
import org.come.mapper.PackRecordMapper;
import org.come.service.IPackRecordService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class PackRecordServiceImpl implements IPackRecordService {
	
	private PackRecordMapper packRecordMapper;
	
	public PackRecordServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		packRecordMapper = ctx.getBean("packRecordMapper",PackRecordMapper.class);
	}

	@Override
	public int countByExample(PackRecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(PackRecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal roleId) {
		packRecordMapper.deleteByPrimaryKey(roleId);
		return 0;
	}

	@Override
	public int insert(PackRecord record) {
		packRecordMapper.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(PackRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PackRecord> selectByExample(PackRecordExample example) {
		return packRecordMapper.selectByExample(example);
	}

	@Override
	public PackRecord selectByPrimaryKey(BigDecimal roleId) {
		return packRecordMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByExampleSelective(PackRecord record, PackRecordExample example) {
		return packRecordMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(PackRecord record, PackRecordExample example) {
		return packRecordMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(PackRecord record) {
		return packRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PackRecord record) {
		return packRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public void addSLDH(BigDecimal roleid, int add) {
		// TODO Auto-generated method stub
		packRecordMapper.addSLDH(roleid, add);
	}

	@Override
	public void emptySLDH() {
		// TODO Auto-generated method stub
		packRecordMapper.emptySLDH();
	}

}
