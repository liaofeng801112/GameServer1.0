package org.come.bean;

import java.util.List;

import org.come.entity.Roleorder;

/**
 * 查询订单返回bean
 * @author Administrator
 *
 */
public class SearchOrderResultBean {

	/**
	 * 返回的订单集合
	 */
	private List<Roleorder> roleorders;
	
	/**
	 * 总页数
	 */
	private Integer total;

	public List<Roleorder> getRoleorders() {
		return roleorders;
	}

	public void setRoleorders(List<Roleorder> roleorders) {
		this.roleorders = roleorders;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
