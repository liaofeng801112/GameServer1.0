package org.come.bean;

import java.math.BigDecimal;

/**
 * HGC--2019-11-18
 * 
 * @author Administrator
 * 
 */
public class Goodsbuyrecordsumbean {
	private BigDecimal gid;// 物品 id
	private String goodsname;//物品名称
	private BigDecimal salesum;// 销售总量
	private int saleprice;// 销售单价
	private BigDecimal salesumprice;// 销售总价
	private String datetime;// 记录时间
	private int page;// 起始页码

	public BigDecimal getGid() {
		return gid;
	}

	public void setGid(BigDecimal gid) {
		this.gid = gid;
	}

	public BigDecimal getSalesum() {
		return salesum;
	}

	public void setSalesum(BigDecimal salesum) {
		this.salesum = salesum;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public BigDecimal getSalesumprice() {
		return salesumprice;
	}

	public void setSalesumprice(BigDecimal salesumprice) {
		this.salesumprice = salesumprice;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

}
