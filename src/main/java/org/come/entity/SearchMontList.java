package org.come.entity;

import java.util.List;

/**
 * 查询坐骑表
 * 
 * @author Administrator
 * 
 */
public class SearchMontList {
	private int sumpage;

	private List<MountRoleUser> montlist;

	public int getSumpage() {
		return sumpage;
	}

	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}

	public List<MountRoleUser> getMontlist() {
		return montlist;
	}

	public void setMontlist(List<MountRoleUser> montlist) {
		this.montlist = montlist;
	}

}
