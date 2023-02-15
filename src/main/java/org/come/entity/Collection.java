package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Collection {
    /**
     * 表ID
     */
    private BigDecimal colid;

    /**
     * 商品ID
     */
    private BigDecimal saleid;

    /**
     * 角色ID
     */
    private BigDecimal roleid;
    
	/**
	 * 商品名称
	 */
	private String salename;

	/**
	 * 皮肤
	 */
	private String saleskin;

	/**
	 * 价格
	 */
	private BigDecimal saleprice;

    /**
     * 类型
     */
    private Integer saletype;
    
    /**
     * 上架时间
     */
    private Date uptime;
    
    /**
     * 对应数据库表ID
     */
    private BigDecimal otherid;
    
    /**
     * 绑定买家ID
     */
    private BigDecimal buyrole;
    

    public BigDecimal getBuyrole() {
		return buyrole;
	}

	public void setBuyrole(BigDecimal buyrole) {
		this.buyrole = buyrole;
	}

	public BigDecimal getOtherid() {
		return otherid;
	}

	public void setOtherid(BigDecimal otherid) {
		this.otherid = otherid;
	}

	public Integer getSaletype() {
		return saletype;
	}

	public void setSaletype(Integer saletype) {
		this.saletype = saletype;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	/**
     * 表ID
     * @return COLID 表ID
     */
    public BigDecimal getColid() {
        return colid;
    }

    /**
     * 表ID
     * @param colid 表ID
     */
    public void setColid(BigDecimal colid) {
        this.colid = colid;
    }

    /**
     * 商品ID
     * @return SALEID 商品ID
     */
    public BigDecimal getSaleid() {
        return saleid;
    }

    /**
     * 商品ID
     * @param saleid 商品ID
     */
    public void setSaleid(BigDecimal saleid) {
        this.saleid = saleid;
    }

    /**
     * 角色ID
     * @return ROLEID 角色ID
     */
    public BigDecimal getRoleid() {
        return roleid;
    }

    /**
     * 角色ID
     * @param roleid 角色ID
     */
    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }

	public String getSalename() {
		return salename;
	}

	public void setSalename(String salename) {
		this.salename = salename;
	}

	public String getSaleskin() {
		return saleskin;
	}

	public void setSaleskin(String saleskin) {
		this.saleskin = saleskin;
	}

	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
    
}