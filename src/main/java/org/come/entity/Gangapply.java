package org.come.entity;

import java.math.BigDecimal;

/**
 * 帮派申请表
 * @author 叶豪芳
 * @date 2017年12月21日 下午9:18:45
 * 
 */ 
public class Gangapply {
	// 表ID
	private BigDecimal gaid;
	
	// 帮派ID
	private BigDecimal gangid;
	
	// 角色ID
	private BigDecimal roleid;

	public BigDecimal getGaid() {
		return gaid;
	}

	public void setGaid(BigDecimal gaid) {
		this.gaid = gaid;
	}

	public BigDecimal getGangid() {
		return gangid;
	}

	public void setGangid(BigDecimal gangid) {
		this.gangid = gangid;
	}

	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	
	
	

}
