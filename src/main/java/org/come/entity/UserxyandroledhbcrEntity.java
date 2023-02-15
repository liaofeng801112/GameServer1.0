package org.come.entity;

import java.math.BigDecimal;

public class UserxyandroledhbcrEntity {

	/** 表id */
	private BigDecimal id;

	/** 用户id */
	private BigDecimal userid;

	/** 用户名称 */
	private String username;

	/** 角色id */
	private BigDecimal roleid;

	/** 角色名称 */
	private String rolename;

	/** 类型名称仙玉/大话币/积分 */
	private BigDecimal type;

	/** 仙玉总 */
	private BigDecimal xsum;
	
	/**当前仙玉*/
	private BigDecimal xnow;

	/** 仙玉消耗 */
	private BigDecimal xdsum;

	/** 大话币总 */
	private BigDecimal dsum;

	/** 大话币消耗 */
	private BigDecimal sssum;

	/** 记录时间 */
	private String time;

	/** 区域id(当前区域id为空) */
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
	 * 获取用户名称
	 * 
	 * @return 用户名称
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 设置用户名称
	 * 
	 * @param username
	 *            用户名称
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * 获取角色名称
	 * 
	 * @return 角色名称
	 */
	public String getRolename() {
		return this.rolename;
	}

	/**
	 * 设置角色名称
	 * 
	 * @param rolename
	 *            角色名称
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 获取类型名称仙玉/大话币/积分
	 * 
	 * @return 类型名称仙玉/大话币/积分
	 */
	public BigDecimal getType() {
		return this.type;
	}

	/**
	 * 设置类型名称仙玉/大话币/积分
	 * 
	 * @param type
	 *            类型名称仙玉/大话币/积分
	 */
	public void setType(BigDecimal type) {
		this.type = type;
	}

	/**
	 * 获取仙玉总
	 * 
	 * @return 仙玉总
	 */
	public BigDecimal getXsum() {
		return this.xsum;
	}

	/**
	 * 设置仙玉总
	 * 
	 * @param xsum
	 *            仙玉总
	 */
	public void setXsum(BigDecimal xsum) {
		this.xsum = xsum;
	}

	/**
	 * 获取仙玉消耗
	 * 
	 * @return 仙玉消耗
	 */
	public BigDecimal getXdsum() {
		return this.xdsum;
	}

	/**
	 * 设置仙玉消耗
	 * 
	 * @param xdsum
	 *            仙玉消耗
	 */
	public void setXdsum(BigDecimal xdsum) {
		this.xdsum = xdsum;
	}

	/**
	 * 获取大话币总
	 * 
	 * @return 大话币总
	 */
	public BigDecimal getDsum() {
		return this.dsum;
	}

	/**
	 * 设置大话币总
	 * 
	 * @param dsum
	 *            大话币总
	 */
	public void setDsum(BigDecimal dsum) {
		this.dsum = dsum;
	}

	/**
	 * 获取大话币消耗
	 * 
	 * @return 大话币消耗
	 */
	public BigDecimal getSssum() {
		return this.sssum;
	}

	/**
	 * 设置大话币消耗
	 * 
	 * @param sssum
	 *            大话币消耗
	 */
	public void setSssum(BigDecimal sssum) {
		this.sssum = sssum;
	}

	/**
	 * 获取记录时间
	 * 
	 * @return 记录时间
	 */
	public String getTime() {
		return this.time;
	}

	/**
	 * 设置记录时间
	 * 
	 * @param time
	 *            记录时间
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 获取区域id(当前区域id为空)
	 * 
	 * @return 区域id(当前区域id为空)
	 */
	public BigDecimal getSid() {
		return this.sid;
	}

	/**
	 * 设置区域id(当前区域id为空)
	 * 
	 * @param sid
	 *            区域id(当前区域id为空)
	 */
	public void setSid(BigDecimal sid) {
		this.sid = sid;
	}
	
	/**
	 * 获取当前仙玉
	 * @return
	 */
	public BigDecimal getXnow() {
		return xnow;
	}

	/**
	 * 设置当前仙玉
	 * @return
	 */
	public void setXnow(BigDecimal xnow) {
		this.xnow = xnow;
	}

	@Override
	public String toString() {
		return "UserxyandroledhbcrEntity [id=" + id + ", userid=" + userid + ", username=" + username + ", roleid=" + roleid + ", rolename=" + rolename + ", type=" + type + ", xsum=" + xsum
				+ ", xnow=" + xnow + ", xdsum=" + xdsum + ", dsum=" + dsum + ", sssum=" + sssum + ", time=" + time + ", sid=" + sid + "]";
	}
	
	
}