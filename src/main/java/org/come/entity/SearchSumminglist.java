package org.come.entity;

import java.util.List;

/**
 * 召唤兽查询返回
 * 
 * @author Administrator
 * 
 */
public class SearchSumminglist {
	private int sumpage;// 查询总的页码

	private List<RolesummoningRoleUser> rolesummingList;

	public int getSumpage() {
		return sumpage;
	}

	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}

	public List<RolesummoningRoleUser> getRolesummingList() {
		return rolesummingList;
	}

	public void setRolesummingList(List<RolesummoningRoleUser> rolesummingList) {
		this.rolesummingList = rolesummingList;
	}

}
