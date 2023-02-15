package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.MountSkill;
/**
 * 坐骑
 * @author 叶豪芳
 * @date : 2017年12月2日 下午3:46:48
 */
@MyBatisAnnotation
public interface MountskillMapper {
	
	/**
	 * 查找所有坐骑所有技能
	 */
	List<MountSkill> selectAllMountskills();
	/**
	 * 查找坐骑所有技能
	 */
	List<MountSkill> selectMountskillsByMountid(BigDecimal mid);
	
	/**
	 * 删除坐骑所有技能
	 */
	void deleteMountskills(BigDecimal mid);
	
	/**
	 * 添加坐骑技能
	 */
	void insertMountskills( MountSkill mountSkill );
}