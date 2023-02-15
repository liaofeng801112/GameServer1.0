package org.come.bean;

import java.math.BigDecimal;
import java.util.Date;

public class managerTable {
    /**
     * 管理员ID
     */
    private BigDecimal manaeid;

    /**
     * 管理员登录名
     */
    private String username;

    /**
     * 管理员密码
     */
    private String pwd;

    /**
     * 管理员姓名
     */
    private String relname;

    /**
     * 管理员创建时间
     */
    private Date createtime;
    
    /**
     * 管理员权限
     */
    private Integer  flag;
    
    /**
     * 管理员种类
     * 10000增加管理员
     * 10001修改
     * 10002删除
     * 10003查询
     * 66666登录
     * @return
     */
    
    private int controlStyle;
    
    
    /**
     * 页码
     * @return
     */
    private int pageNumber;
    private String newname;//20210916
    private String newpwd;//20210916
    
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getControlStyle() {
		return controlStyle;
	}

	public void setControlStyle(int controlStyle) {
		this.controlStyle = controlStyle;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
     * null
     * @return MANAEID null
     */
    public BigDecimal getManaeid() {
        return manaeid;
    }

    /**
     * null
     * @param manaeid null
     */
    public void setManaeid(BigDecimal manaeid) {
        this.manaeid = manaeid;
    }

    /**
     * null
     * @return USERNAME null
     */
    public String getUsername() {
        return username;
    }

    /**
     * null
     * @param username null
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * null
     * @return PWD null
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * null
     * @param pwd null
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * null
     * @return RELNAME null
     */
    public String getRelname() {
        return relname;
    }

    /**
     * null
     * @param relname null
     */
    public void setRelname(String relname) {
        this.relname = relname == null ? null : relname.trim();
    }

    /**
     * null
     * @return CREATETIME null
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * null
     * @param createtime null
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNewname() {
        return this.newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public String getNewpwd() {
        return this.newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
}