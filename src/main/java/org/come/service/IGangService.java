package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Gang;

public interface IGangService {

	// 根据帮派ID查找帮派
	Gang findRoleGangByGangID(BigDecimal gangid);
	
	// 根据帮派ID查找帮派
	Gang findGangByGangName(String gangname);

	// 创建帮派
	boolean createGang(Gang gang);
	
	// 查询所有帮派
	List<Gang> findAllGang();
	
	// 修改帮派
	void updateGang( Gang gang );

}
