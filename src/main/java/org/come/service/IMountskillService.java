package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.MountSkill;

public interface IMountskillService {
	
	/**
	 * 查找所有坐骑所有技能
	 */
	List<MountSkill> selectAllMountskills();
	/**
	 * 查找坐骑所有技能
	 */
	List<MountSkill> selectMountskillsByMountid( BigDecimal mid );
	
	/**
	 * 删除坐骑所有技能
	 */
	void deleteMountskills(BigDecimal mid);
	
	/**
	 * 添加坐骑技能
	 */
	void insertMountskills( MountSkill mountSkill );
}
