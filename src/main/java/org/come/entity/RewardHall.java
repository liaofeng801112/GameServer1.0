package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RewardHall {
    /**
     * ID
     */
    private BigDecimal id;

    /**
     * 物品
     */
    private String goodstable;

    /**
     * 数量
     */
    private BigDecimal goodnum;

    /**
     * 价格
     */
    private BigDecimal goodprice;

    /**
     * 玩家ID
     */
    private BigDecimal roleId;

    /**
     * 投放时间
     */
    private Date throwtime;
    
    private int version;

    /**
     * ID
     * @return ID ID
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * 物品
     * @return Goodstable 物品
     */
    public String getGoodstable() {
        return goodstable;
    }

    /**
     * 物品
     * @param goodstable 物品
     */
    public void setGoodstable(String goodstable) {
        this.goodstable = goodstable == null ? null : goodstable.trim();
    }

    /**
     * 数量
     * @return Goodnum 数量
     */
    public BigDecimal getGoodnum() {
        return goodnum;
    }

    /**
     * 数量
     * @param goodnum 数量
     */
    public void setGoodnum(BigDecimal goodnum) {
        this.goodnum = goodnum;
    }

    /**
     * 价格
     * @return Goodprice 价格
     */
    public BigDecimal getGoodprice() {
        return goodprice;
    }

    /**
     * 价格
     * @param goodprice 价格
     */
    public void setGoodprice(BigDecimal goodprice) {
        this.goodprice = goodprice;
    }

    /**
     * 玩家ID
     * @return Role_ID 玩家ID
     */
    public BigDecimal getRoleId() {
        return roleId;
    }

    /**
     * 玩家ID
     * @param roleId 玩家ID
     */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    /**
     * 投放时间
     * @return ThrowTime 投放时间
     */
    public Date getThrowtime() {
        return throwtime;
    }

    /**
     * 投放时间
     * @param throwtime 投放时间
     */
    public void setThrowtime(Date throwtime) {
        this.throwtime = throwtime;
    }

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
}