package org.come.entity;

import java.math.BigDecimal;
/**
 * 角色称谓
 * @author 叶豪芳
 * @date 2017年12月25日 下午5:34:55
 * 
 */ 
public class Titletable {
	
	// 表ID
	private BigDecimal titleid;
	// 角色ID
	private BigDecimal roleid;
	// 称谓名称
	private String titlename;
	public Titletable() {
		// TODO Auto-generated constructor stub
	}
	public Titletable(BigDecimal roleid, String titlename) {
		super();
		this.roleid = roleid;
		this.titlename = titlename;
	}
	//时效到期时间
	private Long time;
	public BigDecimal getTitleid() {
		return titleid;
	}
	public void setTitleid(BigDecimal titleid) {
		this.titleid = titleid;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	public String getTitlename() {
		return titlename;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
