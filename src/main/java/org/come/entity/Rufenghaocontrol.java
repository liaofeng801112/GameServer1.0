package org.come.entity;

import java.math.BigDecimal;

/**
 * (RUFENGHAOCONTROL)
 * 
 * @author bianj
 * @version 1.0.0 2019-12-03
 */
public class Rufenghaocontrol {

	/** 表id */
	private BigDecimal id;

	/** 区域id */
	private BigDecimal quid;

	/**  */
	private BigDecimal rid;

	/** 用户名 */
	private String username;

	/** 角色名 */
	private String rolename;

	/** 原因 */
	private String reason;

	/** 封号状态，1表示封号，2表示解封 */
	private BigDecimal type;

	/** 账号注册ip */
	private String registerip;

	/** 最后登录ip */
	private String lasstloginip;

	/** 操作对象 */
	private String controlobject;

	/** 代理编号 */
	private BigDecimal dailiid;

	/** 总充值积分 */
	private BigDecimal totalsum;

	/** 时间 */
	private String datetime;

	/**
	 * 获取表id
	 * 
	 * @return 表id
	 */
	public BigDecimal getId() {
		return this.id;
	}

	/**
	 * 设置表id
	 * 
	 * @param id
	 *            表id
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * 获取区域id
	 * 
	 * @return 区域id
	 */
	public BigDecimal getQuid() {
		return this.quid;
	}

	/**
	 * 设置区域id
	 * 
	 * @param quid
	 *            区域id
	 */
	public void setQuid(BigDecimal quid) {
		this.quid = quid;
	}

	/**
	 * 获取rid
	 * 
	 * @return rid
	 */
	public BigDecimal getRid() {
		return this.rid;
	}

	/**
	 * 设置rid
	 * 
	 * @param rid
	 *            rid
	 */
	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取角色名
	 * 
	 * @return 角色名
	 */
	public String getRolename() {
		return this.rolename;
	}

	/**
	 * 设置角色名
	 * 
	 * @param rolename
	 *            角色名
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 获取原因
	 * 
	 * @return 原因
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * 设置原因
	 * 
	 * @param reason
	 *            原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 获取封号状态，1表示封号，2表示解封
	 * 
	 * @return 封号状态
	 */
	public BigDecimal getType() {
		return this.type;
	}

	/**
	 * 设置封号状态，1表示封号，2表示解封
	 * 
	 * @param type
	 *            封号状态，1表示封号，2表示解封
	 */
	public void setType(BigDecimal type) {
		this.type = type;
	}

	/**
	 * 获取账号注册ip
	 * 
	 * @return 账号注册ip
	 */
	public String getRegisterip() {
		return this.registerip;
	}

	/**
	 * 设置账号注册ip
	 * 
	 * @param registerip
	 *            账号注册ip
	 */
	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}

	/**
	 * 获取最后登录ip
	 * 
	 * @return 最后登录ip
	 */
	public String getLasstloginip() {
		return this.lasstloginip;
	}

	/**
	 * 设置最后登录ip
	 * 
	 * @param lasstloginip
	 *            最后登录ip
	 */
	public void setLasstloginip(String lasstloginip) {
		this.lasstloginip = lasstloginip;
	}

	/**
	 * 获取操作对象
	 * 
	 * @return 操作对象
	 */
	public String getControlobject() {
		return this.controlobject;
	}

	/**
	 * 设置操作对象
	 * 
	 * @param controlobject
	 *            操作对象
	 */
	public void setControlobject(String controlobject) {
		this.controlobject = controlobject;
	}

	/**
	 * 获取代理编号
	 * 
	 * @return 代理编号
	 */
	public BigDecimal getDailiid() {
		return this.dailiid;
	}

	/**
	 * 设置代理编号
	 * 
	 * @param dailiid
	 *            代理编号
	 */
	public void setDailiid(BigDecimal dailiid) {
		this.dailiid = dailiid;
	}

	/**
	 * 获取总充值积分
	 * 
	 * @return 总充值积分
	 */
	public BigDecimal getTotalsum() {
		return this.totalsum;
	}

	/**
	 * 设置总充值积分
	 * 
	 * @param totalsum
	 *            总充值积分
	 */
	public void setTotalsum(BigDecimal totalsum) {
		this.totalsum = totalsum;
	}

	/**
	 * 获取时间
	 * 
	 * @return 时间
	 */
	public String getDatetime() {
		return this.datetime;
	}

	/**
	 * 设置时间
	 * 
	 * @param datetime
	 *            时间
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}