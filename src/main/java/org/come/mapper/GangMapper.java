package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Gang;
/**
 * 帮派操作
 * @author 叶豪芳
 * @date : 2017年11月27日 上午11:08:04
 */
@MyBatisAnnotation
public interface GangMapper {

	// 根据帮派ID查找帮派
	Gang findRoleGangByGangID(BigDecimal gangid);
	
	// 根据帮派ID查找帮派
	Gang findGangByGangName(String gangname);
	
	// 查询所有帮派
	List<Gang> findAllGang();

	// 创建帮派
	boolean createGang(Gang gang);
	
	// 修改帮派
	void updateGang( Gang gang );

}