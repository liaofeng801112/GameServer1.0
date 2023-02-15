package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Mount;
import org.come.entity.MountRoleUser;

public interface IMountService {
	
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
	
	/**修改坐骑属性*/
	void updateMount( Mount mount );
	/**修改坐骑属性刚从redis取出*/
	void updateMountRedis(Mount mount);
	/**
	 * 添加坐骑
	 * @param mount
	 */
	void insertMount( Mount mount );
	void deleteMountsByMidsql(BigDecimal roleID);
	void updateMountsql(Mount mount);
	void insertMountsql(Mount mount);
	BigDecimal selectMaxID();
	

	List<MountRoleUser> selectMount(MountRoleUser mru);

	Integer selectMountCount(MountRoleUser mru);
	
	/** HGC-2020-01-20 */
    /** 批量删除角色坐骑 */
    void deleteMountsByMidList(List<BigDecimal> roleID);

    /** 批量修改坐骑属性 */
    void updateMountList(List<Mount> mount);

    /** 批量添加坐骑 */
    void insertMountList(List<Mount> mount);

    /** 单条插入 */
    void insertMountSingle(Mount mount);
}
