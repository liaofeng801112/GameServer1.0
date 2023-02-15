package org.come.bean;

import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;

public class PetAlchemyBean {
	
	//放置炼化的召唤兽
	private RoleSummoning roleSummoning;
	
	//放置炼妖石
	private Goodstable goodstable;

	public RoleSummoning getRoleSummoning() {
		return roleSummoning;
	}

	public void setRoleSummoning(RoleSummoning roleSummoning) {
		this.roleSummoning = roleSummoning;
	}

	public Goodstable getGoodstable() {
		return goodstable;
	}

	public void setGoodstable(Goodstable goodstable) {
		this.goodstable = goodstable;
	}
	
	

}
