package org.come.bean;

import java.util.List;

import org.come.entity.Titletable;
/**
 * 返回的角色所有称谓bean
 * @author 叶豪芳
 * @date 2017年12月25日 下午8:45:21
 * 
 */ 
public class TitleBean {
	// 角色的所有称谓
	private List<Titletable> titletables;

	public List<Titletable> getTitletables() {
		return titletables;
	}

	public void setTitletables(List<Titletable> titletables) {
		this.titletables = titletables;
	}
	
	

}
