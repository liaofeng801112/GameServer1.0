package org.come.bean;

import org.come.entity.RewardHall;

/**
 * 赏公堂添加删除
 * @author Administrator
 *
 */
public class RewardDrawingBean {
	
	// 操作物品
	private RewardHall rewardHall;
	
	// 获取角色名，为空为投放,不为空为删除，且自己名字为抽中
	private String roleName;

	public RewardHall getRewardHall() {
		return rewardHall;
	}

	public void setRewardHall(RewardHall rewardHall) {
		this.rewardHall = rewardHall;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
