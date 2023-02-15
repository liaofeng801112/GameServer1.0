package org.come.action.lottery;

import java.util.Map;

import come.tool.Role.RoleCard;

/**活动排行榜*/
public class EventRanking {

	private Map<Integer,RoleCard[]> map;

	public Map<Integer, RoleCard[]> getMap() {
		return map;
	}

	public void setMap(Map<Integer, RoleCard[]> map) {
		this.map = map;
	}
	
}
