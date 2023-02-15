package org.come.entity;

import java.math.BigDecimal;

public class BuytypeEntity {

	/** 表id */
	private BigDecimal tid;

	/** 类型 */
	private BigDecimal buytype;

	/** 类型名称仙玉/大话币/积分 */
	private String typename;

	/** 记录时间 */
	private String time;

	/**
	 * 获取表id
	 * 
	 * @return 表id
	 */
	public BigDecimal getTid() {
		return this.tid;
	}

	/**
	 * 设置表id
	 * 
	 * @param tid
	 *            表id
	 */
	public void setTid(BigDecimal tid) {
		this.tid = tid;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public BigDecimal getBuytype() {
		return this.buytype;
	}

	/**
	 * 设置类型
	 * 
	 * @param buytype
	 *            类型
	 */
	public void setBuytype(BigDecimal buytype) {
		this.buytype = buytype;
	}

	/**
	 * 获取类型名称仙玉/大话币/积分
	 * 
	 * @return 类型名称仙玉/大话币/积分
	 */
	public String getTypename() {
		return this.typename;
	}

	/**
	 * 设置类型名称仙玉/大话币/积分
	 * 
	 * @param typename
	 *            类型名称仙玉/大话币/积分
	 */
	public void setTypename(String typename) {
		this.typename = typename;
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

	@Override
	public String toString() {
		return "BuytypeEntity [tid=" + tid + ", buytype=" + buytype + ", typename=" + typename + ", time=" + time + "]";
	}

}