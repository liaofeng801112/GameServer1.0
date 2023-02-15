package org.come.entity;

import java.math.BigDecimal;

/**
 * (SHANGCHENGSHOP)
 * 
 * @author bianj
 * @version 1.0.0 2019-11-18
 */
public class ShangchengshopEntity {

	/** 物品id */
	private BigDecimal gid;

	/** 物品名称 */
	private String goodsname;

	/** 物品类型 */
	private BigDecimal goodtype;

	/** 物品销售价格 */
	private BigDecimal goodsprice;

	/** 皮肤 */
	private String skin;

	/** 物品说明 */
	private String text;

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
	 * 获取物品类型
	 * 
	 * @return 物品类型
	 */
	public BigDecimal getGoodtype() {
		return this.goodtype;
	}

	/**
	 * 设置物品类型
	 * 
	 * @param goodtype
	 *            物品类型
	 */
	public void setGoodtype(BigDecimal goodtype) {
		this.goodtype = goodtype;
	}

	/**
	 * 获取物品销售价格
	 * 
	 * @return 物品销售价格
	 */
	public BigDecimal getGoodsprice() {
		return this.goodsprice;
	}

	/**
	 * 设置物品销售价格
	 * 
	 * @param goodsprice
	 *            物品销售价格
	 */
	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	/**
	 * 获取皮肤
	 * 
	 * @return 皮肤
	 */
	public String getSkin() {
		return this.skin;
	}

	/**
	 * 设置皮肤
	 * 
	 * @param skin
	 *            皮肤
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 获取物品说明
	 * 
	 * @return 物品说明
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * 设置物品说明
	 * 
	 * @param text
	 *            物品说明
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ShangchengshopEntity [gid=" + gid + ", goodsname=" + goodsname + ", goodtype=" + goodtype + ", goodsprice=" + goodsprice + ", skin=" + skin + ", text=" + text + "]";
	}

}