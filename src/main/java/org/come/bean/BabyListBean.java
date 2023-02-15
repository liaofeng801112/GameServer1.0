package org.come.bean;

import java.util.List;

import org.come.entity.Baby;

/**
 * 宝宝集合bean 
 * @author 黄建彬
 *
 */
public class BabyListBean {
	private  List<Baby> babyList;
	public List<Baby> getBabyList() {
		return babyList;
	}
	public void setBabyList(List<Baby> babyList) {
		this.babyList = babyList;
	}
}
