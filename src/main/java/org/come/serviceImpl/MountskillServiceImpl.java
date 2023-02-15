package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.MountSkill;
import org.come.mapper.MountskillMapper;
import org.come.service.IMountskillService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class MountskillServiceImpl implements IMountskillService{
	private MountskillMapper mountskillMapper;
	
	public MountskillServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		mountskillMapper = (MountskillMapper) ctx.getBean("mountskillMapper");
	
	}

	@Override
	public List<MountSkill> selectMountskillsByMountid( BigDecimal mid ) {
		List<MountSkill> mountskill = mountskillMapper.selectMountskillsByMountid(mid);
		return mountskill;
	}
	
	/**
	 * 删除坐骑所有技能
	 */
	@Override
	public void deleteMountskills(BigDecimal mid) {
		mountskillMapper.deleteMountskills(mid);
		
	}
	
	/**
	 * 添加坐骑技能
	 */
	@Override
	public void insertMountskills(MountSkill mountSkill) {
		mountskillMapper.insertMountskills(mountSkill);
	}
	/**
	 * 查找所有坐骑所有技能
	 */
	@Override
	public List<MountSkill> selectAllMountskills() {
		return mountskillMapper.selectAllMountskills();
	}


}
