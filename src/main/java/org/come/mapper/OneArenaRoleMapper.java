package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;

import come.tool.oneArena.OneArenaRole;

@MyBatisAnnotation
public interface OneArenaRoleMapper {

	/**新增玩家排名  排名总数加1*/
	int insertOneArenaRole(@Param("role")OneArenaRole role);
	/**记录排名  重置奖励标识*/
	int updateDayReset();
	/**批量获取排名数据*/
	List<OneArenaRole> selectRankRoles(@Param("list")List<Integer> list);
	/**根据角色id获取数据*/
	OneArenaRole selectRole(@Param("roleID")BigDecimal roleID);
	/**修改排名*/
	int updateRankRole(@Param("roleID")BigDecimal roleID,@Param("rank")int rank,@Param("skin")String skin,@Param("name")String name,@Param("lvl")int lvl);
	/**获取当前排名*/
	Integer selectRank(@Param("roleID")BigDecimal roleID);
	/**获取昨日排名  且还未领取奖励*/
	Integer selectRankPast(@Param("roleID")BigDecimal roleID);
	/**修改领取标识 isV修改为1   条件  roleid  and  isv=0*/
	int updateDayResetRole(@Param("roleID")BigDecimal roleID);
}
