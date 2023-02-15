package org.come.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * 组队发送所有队伍列表
 * @author 叶豪芳
 * @date 2017年12月25日 下午3:16:33
 * 
 */ 
public class AllTeamListBean {
	// 所有队伍列表
	private Map<BigDecimal, List<LoginResult>> teamMembers;

	public Map<BigDecimal, List<LoginResult>> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Map<BigDecimal, List<LoginResult>> teamMembers) {
		this.teamMembers = teamMembers;
	}


}
