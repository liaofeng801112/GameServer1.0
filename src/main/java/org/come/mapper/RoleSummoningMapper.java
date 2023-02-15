package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.RoleSummoning;
import org.come.entity.RolesummoningRoleUser;
/**
 * 召唤兽
 * @author 叶豪芳
 * @date : 2017年12月1日 下午8:07:57
 */

@MyBatisAnnotation
public interface RoleSummoningMapper {
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
	
	/**
	 * 根据ID修改召唤兽的所属角色
	 * @param roleSummoning
	 * @return
	 */
	void updateRoleSummoningRoleID( RoleSummoning roleSummoning );
	
	/**
	 * 根据ID修改召唤兽的属性＼名字
	 * @param roleSummoning
	 * @return
	 */
	void updateRoleSummoning( RoleSummoning roleSummoning );
	
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
	
	List<RolesummoningRoleUser> selectRsRU(@Param("rru") RolesummoningRoleUser rru);

	Integer selectRsRUCount(@Param("rru") RolesummoningRoleUser rru);

	RolesummoningRoleUser selectRoleSummoningById(@Param("summoningid") String summoningid);

	

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