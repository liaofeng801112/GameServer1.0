package org.come.entity;

import java.math.BigDecimal;

public class GoodsbuyrecordEntity {

	/** 表id */
	private BigDecimal bid;

	/** 销售的物品id */
	private BigDecimal gid;

	/** 销售的单价 */
	private BigDecimal price;

	/** 购买类型 */
	private BigDecimal buytype;

	/** 物品数量 */
	private BigDecimal goodnumber;

	/** 总消耗 */
	private BigDecimal numbermoney;

	/** 记录时间 */
	private String recordtime;

	/** 用户id */
	private BigDecimal userid;

	/** 角色id */
	private BigDecimal roleid;

	/** 区域id */
	private BigDecimal sid;

	/**
	 * 获取表id
	 * 
	 * @return 表id
	 */
	public BigDecimal getBid() {
		return this.bid;
	}

	/**
	 * 设置表id
	 * 
	 * @param bid
	 *            表id
	 */
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	/**
	 * 获取销售的物品id
	 * 
	 * @return 销售的物品id
	 */
	public BigDecimal getGid() {
		return this.gid;
	}

	/**
	 * 设置销售的物品id
	 * 
	 * @param gid
	 *            销售的物品id
	 */
	public void setGid(BigDecimal gid) {
		this.gid = gid;
	}

	/**
	 * 获取销售的单价
	 * 
	 * @return 销售的单价
	 */
	public BigDecimal getPrice() {
		return this.price;
	}

	/**
	 * 设置销售的单价
	 * 
	 * @param price
	 *            销售的单价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取购买类型
	 * 
	 * @return 购买类型
	 */
	public BigDecimal getBuytype() {
		return this.buytype;
	}

	/**
	 * 设置购买类型
	 * 
	 * @param buytype
	 *            购买类型
	 */
	public void setBuytype(BigDecimal buytype) {
		this.buytype = buytype;
	}

	/**
	 * 获取物品数量
	 * 
	 * @return 物品数量
	 */
	public BigDecimal getGoodnumber() {
		return this.goodnumber;
	}

	/**
	 * 设置物品数量
	 * 
	 * @param goodnumber
	 *            物品数量
	 */
	public void setGoodnumber(BigDecimal goodnumber) {
		this.goodnumber = goodnumber;
	}

	/**
	 * 获取总消耗
	 * 
	 * @return 总消耗
	 */
	public BigDecimal getNumbermoney() {
		return this.numbermoney;
	}

	/**
	 * 设置总消耗
	 * 
	 * @param numbermoney
	 *            总消耗
	 */
	public void setNumbermoney(BigDecimal numbermoney) {
		this.numbermoney = numbermoney;
	}

	/**
	 * 获取记录时间
	 * 
	 * @return 记录时间
	 */
	public String getRecordtime() {
		return this.recordtime;
	}

	/**
	 * 设置记录时间
	 * 
	 * @param recordtime
	 *            记录时间
	 */
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
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
}