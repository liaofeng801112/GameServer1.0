package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Mount;
import org.come.entity.MountRoleUser;
/**
 * 坐骑
 * @author 叶豪芳
 * @date : 2017年12月2日 下午3:46:48
 */
@MyBatisAnnotation
public interface MountMapper {
	
	// 查找所有坐骑
	List<Mount> selectAllMounts();
	/**
	 * 查找角色所有坐骑
	 */
	List<Mount> selectMountsByRoleID( BigDecimal roleID );
	
	/**
	 * 查找角色坐骑
	 */
	Mount selectMountsByMID( BigDecimal mid );
	
	/**
	 * 删除角色坐骑
	 */
	void deleteMountsByMid( BigDecimal roleID );
	
	/**
	 * 修改坐骑属性
	 * @param mount
	 */
	void updateMount( Mount mount );
	
	/**
	 * 添加坐骑
	 * @param mount
	 */
	void insertMount( Mount mount );
	BigDecimal selectMaxID();
	

	List<MountRoleUser> selectMount(@Param("mru") MountRoleUser mru);

	Integer selectMountCount(@Param("mru") MountRoleUser mru);
	
	
	   /** HGC-2020-01-20 */
    /** 删除角色坐骑 */
    void deleteMountsByMidList(List<BigDecimal> roleID);

    /** 修改坐骑属性 */
    void updateMountList(List<Mount> mount);

    /** 添加坐骑 */
    void insertMountList(List<Mount> mount);

    /** 单条插入 */
    void insertMountSingle(Mount mount);

}