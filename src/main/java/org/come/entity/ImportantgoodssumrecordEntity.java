package org.come.entity;

import java.math.BigDecimal;

public class ImportantgoodssumrecordEntity{
    
    /** 表id */
    private BigDecimal id;
    
    /** 物品id */
    private BigDecimal gid;
    
    /** 数量 */
    private BigDecimal goodnumber;
    
    /** 记录时间 */
    private String datetime;
    
    /** 区id */
    private BigDecimal sid;
    
    /**
     * 获取表id
     * 
     * @return 表id
     */
    public BigDecimal getId() {
        return this.id;
    }
     
    /**
     * 设置表id
     * 
     * @param id
     *          表id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
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
     *          物品id
     */
    public void setGid(BigDecimal gid) {
        this.gid = gid;
    }
    
    /**
     * 获取数量
     * 
     * @return 数量
     */
    public BigDecimal getGoodnumber() {
        return this.goodnumber;
    }
     
    /**
     * 设置数量
     * 
     * @param goodnumber
     *          数量
     */
    public void setGoodnumber(BigDecimal goodnumber) {
        this.goodnumber = goodnumber;
    }
    
    /**
     * 获取记录时间
     * 
     * @return 记录时间
     */
    public String getDatetime() {
        return this.datetime;
    }
     
    /**
     * 设置记录时间
     * 
     * @param datetime
     *          记录时间
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    /**
     * 获取区id
     * 
     * @return 区id
     */
    public BigDecimal getSid() {
        return this.sid;
    }
     
    /**
     * 设置区id
     * 
     * @param sid
     *          区id
     */
    public void setSid(BigDecimal sid) {
        this.sid = sid;
    }
}