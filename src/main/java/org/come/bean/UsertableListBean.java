package org.come.bean;

import java.util.List;

import org.come.entity.UserTable;

public class UsertableListBean {
	// 总页码
	private int sumpage;

	// 用户表list
	private List<UserTable> usertablelist;

	// 当前页码
	private int nowpage;

	// 每页展示的页码数
	private int number = 8;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSumpage() {
		return sumpage;
	}

	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}

	public List<UserTable> getUsertablelist() {
		return usertablelist;
	}

	public void setUsertablelist(List<UserTable> usertablelist) {
		this.usertablelist = usertablelist;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

}
