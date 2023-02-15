package org.come.entity;

import java.math.BigDecimal;

public class GoodssaledayrecordEntity {

	/** 表id */
	private BigDecimal id;

	/** 物品id */
	private BigDecimal gid;

	/** 销售总量 */
	private BigDecimal buysum;

	/** 总消耗 */
	private BigDecimal paysum;

	/** 记录时间 */
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
	 * 获取物品id
	 * 
	 * @return 物品id
	 */
	public BigDecimal getGid() {
		return this.gid;
	}

	/**
	 * 设置物品id
	 * 
	 * @param gid
	 *            物品id
	 */
	public void setGid(BigDecimal gid) {
		this.gid = gid;
	}

	/**
	 * 获取销售总量
	 * 
	 * @return 销售总量
	 */
	public BigDecimal getBuysum() {
		return this.buysum;
	}

	/**
	 * 设置销售总量
	 * 
	 * @param buysum
	 *            销售总量
	 */
	public void setBuysum(BigDecimal buysum) {
		this.buysum = buysum;
	}

	/**
	 * 获取总消耗
	 * 
	 * @return 总消耗
	 */
	public BigDecimal getPaysum() {
		return this.paysum;
	}

	/**
	 * 设置总消耗
	 * 
	 * @param paysum
	 *            总消耗
	 */
	public void setPaysum(BigDecimal paysum) {
		this.paysum = paysum;
	}

	/**
	 * 获取记录时间
	 * 
	 * @return 记录时间
	 */
	public String getDatetime() {
		return this.datetime;
	}

	/**
	 * 设置记录时间
	 * 
	 * @param datetime
	 *            记录时间
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}