package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Region;
import org.come.entity.RoleTableNew;
import org.come.mapper.RegionMapper;
import org.come.service.RegionService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

/**
 * 三端
 * 
 * @author zz
 * @time 2019年7月15日
 * 
 */
public class RegionServiceImpl implements RegionService {

	private RegionMapper regionMapper;

	public RegionServiceImpl() {
		// TODO Auto-generated constructor stub
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		regionMapper = (RegionMapper) ctx.getBean("regionMapper");
	}

	@Override
	public List<Region> selectRegion(BigDecimal quId, String raName) {
		// TODO Auto-generated method stub
		return regionMapper.selectRegion(quId, raName);
	}

	@Override
	public List<RoleTableNew> selectRole(BigDecimal userId, Integer quid) {
		// TODO Auto-generated method stub
		return regionMapper.selectRole(userId, quid);
	}

	@Override
	public List<String> selectRegionAll() {
		// TODO Auto-generated method stub
		return regionMapper.selectRegionAll();
	}

}
