package org.come.entity;

import java.math.BigDecimal;

public class ImportantgoodsluEntity {

	/** 表id */
	private BigDecimal id;

	/** 物品id */
	private BigDecimal gid;

	/** 物品名称 */
	private String goodsname;

	/** 属性 */
	private String value;

	/** 异动时间 */
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
	 * 获取物品名称
	 * 
	 * @return 物品名称
	 */
	public String getGoodsname() {
		return this.goodsname;
	}

	/**
	 * 设置物品名称
	 * 
	 * @param goodsname
	 *            物品名称
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
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
	 * 获取异动时间
	 * 
	 * @return 异动时间
	 */
	public String getDatetime() {
		return this.datetime;
	}

	/**
	 * 设置异动时间
	 * 
	 * @param datetime
	 *            异动时间
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "ImportantgoodsluEntity [id=" + id + ", gid=" + gid + ", goodsname=" + goodsname + ", value=" + value + ", datetime=" + datetime + "]";
	}

}