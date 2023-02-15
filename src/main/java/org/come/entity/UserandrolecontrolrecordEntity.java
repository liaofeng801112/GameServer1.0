package org.come.entity;

import java.math.BigDecimal;

public class UserandrolecontrolrecordEntity {

	/** 表id */
	private BigDecimal id;

	/** 用户id */
	private BigDecimal userid;

	/** 角色id */
	private BigDecimal roleid;

	/** 类型 */
	private BigDecimal type;

	/** 属性 */
	private String value;

	/** 时间 */
	private String time;

	/** 区id */
	private BigDecimal sid;

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
	 * 获取用户id
	 * 
	 * @return 用户id
	 */
	public BigDecimal getUserid() {
		return this.userid;
	}

	/**
	 * 设置用户id
	 * 
	 * @param userid
	 *            用户id
	 */
	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	/**
	 * 获取角色id
	 * 
	 * @return 角色id
	 */
	public BigDecimal getRoleid() {
		return this.roleid;
	}

	/**
	 * 设置角色id
	 * 
	 * @param roleid
	 *            角色id
	 */
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public BigDecimal getType() {
		return this.type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(BigDecimal type) {
		this.type = type;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 设置属性
	 * 
	 * @param value
	 *            属性
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取时间
	 * 
	 * @return 时间
	 */
	public String getTime() {
		return this.time;
	}

	/**
	 * 设置时间
	 * 
	 * @param time
	 *            时间
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 获取区id
	 * 
	 * @return 区id
	 */
	public BigDecimal getSid() {
		return this.sid;
	}

	/**
	 * 设置区id
	 * 
	 * @param sid
	 *            区id
	 */
	public void setSid(BigDecimal sid) {
		this.sid = sid;
	}
}