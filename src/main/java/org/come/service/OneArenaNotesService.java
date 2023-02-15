package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import come.tool.oneArena.OneArenaNotes;

public interface OneArenaNotesService {

	/**查询当前日期前最大ID*/
	BigDecimal selectMaxID(String time);
	/**查询玩家战报且ID大于最小ID 按id 大到小排序  */
	List<OneArenaNotes> selectRole(BigDecimal roleId,BigDecimal min);
	/**添加战报*/
	int insertOneArenaNotes(OneArenaNotes notes);
}
