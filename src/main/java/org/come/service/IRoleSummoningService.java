package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.RoleSummoning;
import org.come.entity.RolesummoningRoleUser;

public interface IRoleSummoningService {
	// 查找所有角色召唤兽
	List<RoleSummoning> selectAllRoleSummonings();
	/**
	 * 根据角色ID查找该角色的召唤兽
	 * @param roleid
	 * @return
	 */
	List<RoleSummoning> selectRoleSummoningsByRoleID( BigDecimal roleid );
	
	/**
	 * 根据表ID查找召唤兽信息
	 * @param roleid
	 * @return
	 */
	RoleSummoning selectRoleSummoningsByRgID( BigDecimal sid );
	
	/**修改索引*/
	void updateRoleSummoningIndex( RoleSummoning roleSummoning,BigDecimal role_id );
	
	/**根据ID修改召唤兽的属性＼名字*/
	void updateRoleSummoning( RoleSummoning roleSummoning );
	/**根据ID修改召唤兽的属性＼名字数据来源从redis刚取出*/
	void updatePetRedis(RoleSummoning pet);
	
	/**
	 * 根据表ID删除召唤兽
	 * @param roleSummoning
	 * @return
	 */
	void deleteRoleSummoningBySid( BigDecimal sid );
	/**
	 * 添加召唤兽
	 * @param roleSummoning
	 * @return
	 */
	void insertRoleSummoning(  RoleSummoning roleSummoning  );
	BigDecimal selectMaxID();
	void updateRoleSummoningsql(RoleSummoning roleSummoning);
	void deleteRoleSummoningBySidsql(BigDecimal sid);
	void insertRoleSummoningsql(RoleSummoning roleSummoning);
	
	List<RolesummoningRoleUser> selectRsRU(RolesummoningRoleUser rru);

	Integer selectRsRUCount(RolesummoningRoleUser rru);

	RolesummoningRoleUser selectRoleSummoningById(String summoningid);
	

    /** HGC-2020-01-20 */
    /** 批量删除 */
    void deleteRoleSummoningBySidList(List<BigDecimal> list);

    /** 批量添加 */
    void insertRoleSummoningList(List<RoleSummoning> list);

    /** 批量修改 */
    void updateRoleSummoningList(List<RoleSummoning> list);

    /** 单条插入 */
    void insertRoleSummoningSingle(RoleSummoning roleSummoning);
}
