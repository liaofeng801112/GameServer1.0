package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Message {
    /**
     * 表ID
     */
    private BigDecimal mesid;

    /**
     * 角色ID
     */
    private BigDecimal roleid;

    /**
     * 商品ID
     */
    private BigDecimal saleid;

    /**
     * 消息内容
     */
    private String mescontent;

    /**
     * 收到时间
     */
    private Date gettime;

    /**
     * 表ID
     * @return MESID 表ID
     */
    public BigDecimal getMesid() {
        return mesid;
    }

    /**
     * 表ID
     * @param mesid 表ID
     */
    public void setMesid(BigDecimal mesid) {
        this.mesid = mesid;
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
     * 消息内容
     * @return MESCONTENT 消息内容
     */
    public String getMescontent() {
        return mescontent;
    }

    /**
     * 消息内容
     * @param mescontent 消息内容
     */
    public void setMescontent(String mescontent) {
        this.mescontent = mescontent == null ? null : mescontent.trim();
    }

    /**
     * 收到时间
     * @return GETTIME 收到时间
     */
    public Date getGettime() {
        return gettime;
    }

    /**
     * 收到时间
     * @param gettime 收到时间
     */
    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }
}