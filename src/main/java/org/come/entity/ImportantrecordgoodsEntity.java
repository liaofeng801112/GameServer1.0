package org.come.entity;

import java.math.BigDecimal;

public class ImportantrecordgoodsEntity {
	/** 版本号 */
	private static final long serialVersionUID = 5533461929429133696L;

	/** 表id */
	private BigDecimal iid;

	/** 记录物品id */
	private BigDecimal goodsid;

	/** 当前数量 */
	private BigDecimal goodsnumber;

	/** 记录类型 */
	private BigDecimal rocordtype;

	/** 用户id */
	private BigDecimal userid;

	/** 角色id */
	private BigDecimal roleid;

	/** 区域id */
	private BigDecimal sid;

	/** 异动时间 */
	private String datetime;

	/**
	 * 获取表id
	 * 
	 * @return 表id
	 */
	public BigDecimal getIid() {
		return this.iid;
	}

	/**
	 * 设置表id
	 * 
	 * @param iid
	 *            表id
	 */
	public void setIid(BigDecimal iid) {
		this.iid = iid;
	}

	/**
	 * 获取记录物品id
	 * 
	 * @return 记录物品id
	 */
	public BigDecimal getGoodsid() {
		return this.goodsid;
	}

	/**
	 * 设置记录物品id
	 * 
	 * @param goodsid
	 *            记录物品id
	 */
	public void setGoodsid(BigDecimal goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * 获取当前数量
	 * 
	 * @return 当前数量
	 */
	public BigDecimal getGoodsnumber() {
		return this.goodsnumber;
	}

	/**
	 * 设置当前数量
	 * 
	 * @param goodsnumber
	 *            当前数量
	 */
	public void setGoodsnumber(BigDecimal goodsnumber) {
		this.goodsnumber = goodsnumber;
	}

	/**
	 * 获取记录类型
	 * 
	 * @return 记录类型
	 */
	public BigDecimal getRocordtype() {
		return this.rocordtype;
	}

	/**
	 * 设置记录类型
	 * 
	 * @param rocordtype
	 *            记录类型
	 */
	public void setRocordtype(BigDecimal rocordtype) {
		this.rocordtype = rocordtype;
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
	 * 获取区域id
	 * 
	 * @return 区域id
	 */
	public BigDecimal getSid() {
		return this.sid;
	}

	/**
	 * 设置区域id
	 * 
	 * @param sid
	 *            区域id
	 */
	public void setSid(BigDecimal sid) {
		this.sid = sid;
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
}