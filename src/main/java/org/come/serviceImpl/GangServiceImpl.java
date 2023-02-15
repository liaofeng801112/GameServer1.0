package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Gang;
import org.come.mapper.GangMapper;
import org.come.service.IGangService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class GangServiceImpl implements IGangService{
	
	private GangMapper gangMapper;
	
	public GangServiceImpl() {

		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		gangMapper = (GangMapper) ctx.getBean("gangMapper");
		
	}

	/*
	 * 查找帮派(non-Javadoc)
	 * @see org.come.service.IGangService#findRoleGangByGangID(java.math.BigDecimal)
	 */
	@Override
	public Gang findRoleGangByGangID(BigDecimal gangid) {

		Gang gang = gangMapper.findRoleGangByGangID(gangid);
		return gang;
		
	}
	
	/*
	 * 创建帮派(non-Javadoc)
	 * @see org.come.service.IGangService#findRoleGangByGangID(java.math.BigDecimal)
	 */
	@Override
	public boolean createGang(Gang gang) {

		boolean isSuccess = gangMapper.createGang(gang);
		
		return isSuccess;
	}
	
	/*
	 * 根据帮派ID查找帮派(non-Javadoc)
	 * @see org.come.service.IGangService#findRoleGangByGangID(java.math.BigDecimal)
	 */
	@Override
	public Gang findGangByGangName(String gangname) {

		Gang gang = gangMapper.findGangByGangName(gangname);
		
		return gang;
	}

	// 查询所有帮派
	@Override
	public List<Gang> findAllGang() {
		List<Gang> gangs = gangMapper.findAllGang();
		return gangs;
	}

	// 修改帮派
	@Override
	public void updateGang(Gang gang) {
		gangMapper.updateGang(gang);
	}

}
