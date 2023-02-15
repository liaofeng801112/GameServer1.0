package org.come.bean;

      import java.util.List;

import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;

/**
 * 在线摆摊bean
 * @author 叶豪芳
 * @date 2017年12月24日 下午12:46:23
 * 
 */ 
public class StallBean {
	// 需要出售的商品列表
	private List<Goodstable> goodstables;
	// 需要出售的召唤兽列表
	private List<RoleSummoning> roleSummonings;
	// 点击人名字
	private String rolename;
	public List<Goodstable> getGoodstables() {
		return goodstables;
	}

	public void setGoodstables(List<Goodstable> goodstables) {
		this.goodstables = goodstables;
	}

	public List<RoleSummoning> getRoleSummonings() {
		return roleSummonings;
	}

	public void setRoleSummonings(List<RoleSummoning> roleSummonings) {
		this.roleSummonings = roleSummonings;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
