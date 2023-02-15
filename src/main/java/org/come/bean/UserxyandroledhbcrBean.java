package org.come.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserxyandroledhbcrBean {

	/** 表id */
	private BigDecimal id;

	/** 用户名称 */
	private String username;

	/** 用户id */
	private BigDecimal userid;

	/** 仙玉总 */
	private List<BigDecimal> xsum;

	/** 当前仙玉 */
	private List<BigDecimal> xnow;

	/** 仙玉消耗 */
	private List<BigDecimal> xdsum;

	/** 大话币总 */
	private List<BigDecimal> dsum;

	/** 大话币消耗 */
	private List<BigDecimal> sssum;

	/** 记录时间 */
	private List<String> time;

	public UserxyandroledhbcrBean(String username, BigDecimal userid, BigDecimal xsum, BigDecimal xnow, BigDecimal xdsum, BigDecimal dsum, BigDecimal sssum, String time) {
		super();
		this.username = username;
		this.userid = userid;
		getXsum().add(xsum);
		getXdsum().add(xdsum);
		getXnow().add(xnow);
		getSssum().add(sssum);
		getTime().add(time);
		getDsum().add(dsum);
	}

	public UserxyandroledhbcrBean() {
		super();
	}

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
	 * 获取大话币总
	 * 
	 * @return 大话币总
	 */
	public List<BigDecimal> getDsum() {
		if (dsum == null) {
			dsum = new ArrayList<>();
		}
		return this.dsum;
	}

	/**
	 * 获取仙玉总
	 * 
	 * @return 仙玉总
	 */
	public List<BigDecimal> getXsum() {
		if (xsum == null) {
			xsum = new ArrayList<>();
		}
		return xsum;
	}

	/**
	 * 设置仙玉总
	 * 
	 * @return xsum 仙玉总
	 */
	public void setXsum(List<BigDecimal> xsum) {
		this.xsum = xsum;
	}

	/**
	 * 设置大话币总
	 * 
	 * @param dsum
	 *            大话币总
	 */
	public void setDsum(List<BigDecimal> dsum) {
		this.dsum = dsum;
	}

	/**
	 * 获取当前仙玉
	 * 
	 * @return 当前仙玉
	 */
	public List<BigDecimal> getXnow() {
		if (xnow == null) {
			xnow = new ArrayList<>();
		}
		return xnow;
	}

	/**
	 * 设置当前仙玉
	 * 
	 * @param 当前仙玉
	 */
	public void setXnow(List<BigDecimal> xnow) {
		this.xnow = xnow;
	}

	/**
	 * 获取仙玉消耗
	 * 
	 * @return xsum 仙玉消耗
	 */
	public List<BigDecimal> getXdsum() {
		if (xdsum == null) {
			xdsum = new ArrayList<>();
		}
		return xdsum;
	}

	/**
	 * 设置仙玉消耗
	 * 
	 * @param xsum
	 *            仙玉消耗
	 */
	public void setXdsum(List<BigDecimal> xdsum) {
		this.xdsum = xdsum;
	}

	/**
	 * 获取大话币消耗
	 * 
	 * @return xsum 大话币消耗
	 */
	public List<BigDecimal> getSssum() {
		if (sssum == null) {
			sssum = new ArrayList<>();
		}
		return sssum;
	}

	/**
	 * 设置大话币消耗
	 * 
	 * @param xsum
	 *            大话币消耗
	 */
	public void setSssum(List<BigDecimal> sssum) {
		this.sssum = sssum;
	}

	/**
	 * 获取记录时间
	 * 
	 * @return 记录时间
	 */
	public List<String> getTime() {
		if (time == null) {
			time = new ArrayList<>();
		}
		return time;
	}

	/**
	 * 设置记录时间
	 * 
	 * @param 记录时间
	 */
	public void setTime(List<String> time) {
		this.time = time;
	}

	/**
	 * 获取用户id
	 * 
	 * @return 用户id
	 */
	public BigDecimal getUserid() {
		return userid;
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

	@Override
	public String toString() {
		return "UserxyandroledhbcrBean [id=" + id + ", username=" + username + ", userid=" + userid + ", xsum=" + xsum + ", xnow=" + xnow + ", xdsum=" + xdsum + ", dsum=" + dsum + ", sssum=" + sssum
				+ ", time=" + time + "]";
	}

}
