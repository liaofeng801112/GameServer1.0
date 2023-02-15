package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import come.tool.oneArena.OneArenaRole;

public interface OneArenaRoleService {

	/**新增玩家排名  排名总数加1*/
	int insertOneArenaRole(OneArenaRole role);
	/**记录排名  重置奖励标识*/
	int updateDayReset();
	/**批量获取排名数据*/
	List<OneArenaRole> selectRankRoles(List<Integer> list);
	/**根据角色id获取数据*/
	OneArenaRole selectRole(BigDecimal roleID);
	/**修改排名*/
	int updateRankRole(BigDecimal roleID,int rank,String skin,String name,int lvl);
	/**获取当前排名*/
	int selectRank(BigDecimal roleID);
	/**获取昨日排名  且还未领取奖励*/
	int selectRankPast(BigDecimal roleID);
	/**修改领取标识*/
	int updateDayResetRole(BigDecimal roleID);
}
