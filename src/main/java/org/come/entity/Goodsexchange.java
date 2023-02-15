package org.come.entity;

import java.util.Date;

public class Goodsexchange {
    /**
     * 兑换码
     */
    private String goodsguid;

    /**
     * 标志 （1兑换，0表示没有兑换）
     */
    private Integer flag;

    /**
     * 物品记录（物品id&物品ID&物品ID）
     */
    private String goodsid;

    /**
     * 兑换时间 
     */
    private Date outtime;

    /**
     * 兑换码
     * @return GOODSGUID 兑换码
     */
    public String getGoodsguid() {
        return goodsguid;
    }

    /**
     * 兑换码
     * @param goodsguid 兑换码
     */
    public void setGoodsguid(String goodsguid) {
        this.goodsguid = goodsguid == null ? null : goodsguid.trim();
    }

    /**
     * 标志 （1兑换，0表示没有兑换）
     * @return FLAG 标志 （1兑换，0表示没有兑换）
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 标志 （1兑换，0表示没有兑换）
     * @param flag 标志 （1兑换，0表示没有兑换）
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 物品记录（物品id&物品ID&物品ID）
     * @return GOODSID 物品记录（物品id&物品ID&物品ID）
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * 物品记录（物品id&物品ID&物品ID）
     * @param goodsid 物品记录（物品id&物品ID&物品ID）
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * 兑换时间 
     * @return OUTTIME 兑换时间 
     */
    public Date getOuttime() {
        return outtime;
    }

    /**
     * 兑换时间 
     * @param outtime 兑换时间 
     */
    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }
}