package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;

import come.tool.oneArena.OneArenaNotes;

@MyBatisAnnotation
public interface OneArenaNotesMapper {

	/**查询当前日期前最大ID*/
	BigDecimal selectMaxID(@Param("time")String time);
	/**查询玩家战报且ID大于最小ID 按id 大到小排序  */
	List<OneArenaNotes> selectRole(@Param("roleId")BigDecimal roleId,@Param("min")BigDecimal min);
	/**添加战报*/
	int insertOneArenaNotes(@Param("notes")OneArenaNotes notes);
	
	
}
