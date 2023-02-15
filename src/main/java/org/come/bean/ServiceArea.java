package org.come.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ServiceArea {
    /**
     * 服务区ID
     */
    private BigDecimal sid;

    /**
     * 服务区名称
     */
    private String sname;
    /**
     * 创建时间
     */
    private Date sdate;
    
    /**
     * 代理者
     */
    private String agents;
    
    /**
     * 分成比例
     */
    private String dividedinto;
    
    
    /**
     * 分类
     * @return
     * 10000增加
     * 10001修改
     * 10002删除
     * 10003查询初始化查询
     * 10004分页查询充值详情
     * 10005扥也查询区域信息
     */
    private int controlStyle;
    
    
    
    /**
     * 管理员ID
     */
    private BigDecimal manaeid;
    
   
    /**
     * 页码
     * @return
     */
    private int pageNum;
    
    
    
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getControlStyle() {
		return controlStyle;
	}

	public void setControlStyle(int controlStyle) {
		this.controlStyle = controlStyle;
	}

	public BigDecimal getManaeid() {
		return manaeid;
	}

	public void setManaeid(BigDecimal manaeid) {
		this.manaeid = manaeid;
	}

	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public String getDividedinto() {
		return dividedinto;
	}

	public void setDividedinto(String dividedinto) {
		this.dividedinto = dividedinto;
	}

	/**
     * 服务区ID
     * @return SID 服务区ID
     */
    public BigDecimal getSid() {
        return sid;
    }

    /**
     * 服务区ID
     * @param sid 服务区ID
     */
    public void setSid(BigDecimal sid) {
        this.sid = sid;
    }

    /**
     * 服务区名称
     * @return Sname 服务区名称
     */
    public String getSname() {
        return sname;
    }

    /**
     * 服务区名称
     * @param sname 服务区名称
     */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    /**
     * 创建时间
     * @return SDATE 创建时间
     */
    public Date getSdate() {
        return sdate;
    }

    /**
     * 创建时间
     * @param sdate 创建时间
     */
    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }
}