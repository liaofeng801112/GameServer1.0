package org.come.entity;

import java.util.List;

/**
 * 灵宝查询返回的bean
 * 
 * @author Administrator
 * 
 */
public class SearchLingbaoList {
	private int sumpage;// 总页数

	private List<LingbaoRoleUser> lingbaolist;// 当前页面返回的符合数据的灵宝

	public List<LingbaoRoleUser> getLingbaolist() {
		return lingbaolist;
	}

	public void setLingbaolist(List<LingbaoRoleUser> lingbaolist) {
		this.lingbaolist = lingbaolist;
	}

	public int getSumpage() {
		return sumpage;
	}

	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}

}
