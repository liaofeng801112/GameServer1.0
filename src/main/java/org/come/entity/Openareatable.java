package org.come.entity;

import java.math.BigDecimal;

/**
 * 开区表
 * 
 * @author zengr
 * 
 */
public class Openareatable {
	// 表id
	private BigDecimal tb_id;
	// 区域id
	private BigDecimal ot_areaid;
	// 区域名称
	private String ot_areaname;
	// 区域归属id
	private BigDecimal ot_belong;
	// 分成模式(格式5-5)
	private String ot_distribution;
	// 代理id
	private String ot_atid;
	// 创建时间
	private String ot_cretime;
	// 创建者管理员id
	private BigDecimal ot_ctremanageid;
	// 更新管理员id
	private BigDecimal ot_upatemanageid;
	// 更新时间(同步更新到游戏服务器)
	private String ot_updatetime;
	// 备注
	private String ot_memo;

	public BigDecimal getTb_id() {
		return tb_id;
	}

	public void setTb_id(BigDecimal tb_id) {
		this.tb_id = tb_id;
	}

	public BigDecimal getOt_areaid() {
		return ot_areaid;
	}

	public void setOt_areaid(BigDecimal ot_areaid) {
		this.ot_areaid = ot_areaid;
	}

	public String getOt_areaname() {
		return ot_areaname;
	}

	public void setOt_areaname(String ot_areaname) {
		this.ot_areaname = ot_areaname;
	}

	public BigDecimal getOt_belong() {
		return ot_belong;
	}

	public void setOt_belong(BigDecimal ot_belong) {
		this.ot_belong = ot_belong;
	}

	public String getOt_distribution() {
		return ot_distribution;
	}

	public void setOt_distribution(String ot_distribution) {
		this.ot_distribution = ot_distribution;
	}

	public String getOt_atid() {
		return ot_atid;
	}

	public void setOt_atid(String ot_atid) {
		this.ot_atid = ot_atid;
	}

	public String getOt_cretime() {
		return ot_cretime;
	}

	public void setOt_cretime(String ot_cretime) {
		this.ot_cretime = ot_cretime;
	}

	public BigDecimal getOt_ctremanageid() {
		return ot_ctremanageid;
	}

	public void setOt_ctremanageid(BigDecimal ot_ctremanageid) {
		this.ot_ctremanageid = ot_ctremanageid;
	}

	public BigDecimal getOt_upatemanageid() {
		return ot_upatemanageid;
	}

	public void setOt_upatemanageid(BigDecimal ot_upatemanageid) {
		this.ot_upatemanageid = ot_upatemanageid;
	}

	public String getOt_updatetime() {
		return ot_updatetime;
	}

	public void setOt_updatetime(String ot_updatetime) {
		this.ot_updatetime = ot_updatetime;
	}

	public String getOt_memo() {
		return ot_memo;
	}

	public void setOt_memo(String ot_memo) {
		this.ot_memo = ot_memo;
	}

}
