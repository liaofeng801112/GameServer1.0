package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Region;
import org.come.entity.RoleTableNew;

/**
 * 三端
 * @author zz
 * @time 2019年7月15日
 * 
 */
public interface RegionService {
	/**
	 * 查询区域
	 * 
	 * @return
	 */
	List<Region> selectRegion(BigDecimal quId, String raName);

	/**
	 * 查询角色
	 * 
	 * @param userId
	 *            用户id
	 * @param quid
	 *            区id
	 * @return
	 */
	List<RoleTableNew> selectRole(BigDecimal userId, Integer quid);
	
	/**
	 * 查询所有区
	 */
	List<String> selectRegionAll();
}
