package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Region;
import org.come.entity.RoleTableNew;

/**
 * 三端
 * 
 * @author zz
 * @time 2019年7月15日16:13:04
 * 
 */
@MyBatisAnnotation
public interface RegionMapper {

	/**
	 * 查询区域
	 * 
	 * @param quId
	 *            大区域id (为空 即是查询所有区)
	 * @return
	 */
	List<Region> selectRegion(@Param("quId") BigDecimal quId, @Param("raName") String raName);

	/**
	 * 查询角色
	 * 
	 * @param userId
	 *            用户id
	 * @param quid
	 *            区id
	 * @return
	 */
	List<RoleTableNew> selectRole(@Param("userId") BigDecimal userId, @Param("quid") Integer quid);

	/**
	 * 查询所有区
	 */
	List<String> selectRegionAll();

}
