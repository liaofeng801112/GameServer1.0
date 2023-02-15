package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Salegoods {
    /**表ID*/
    private BigDecimal saleid;

    /**商品名称*/
    private String salename;

    /**类型*/
    private Integer saletype;

    /**对应数据库表ID*/
    private BigDecimal otherid;

    /**条件分类*/
    private String contiontype;
   
    /**上下架标识  1未上架   2已上架   3已下单   4已卖出   5已取回*/
    private Integer flag;

    /**上架时间*/
    private Date uptime;

    /**上架角色ID*/
    private BigDecimal roleid;

    /**绑定买家ID*/
    private BigDecimal buyrole;

    /**价格*/
    private BigDecimal saleprice;

    /**皮肤*/
    private String saleskin;

    /**表ID*/
    public BigDecimal getSaleid() {
        return saleid;
    }

    /**
     * 表ID
     * @param saleid 表ID
     */
    public void setSaleid(BigDecimal saleid) {
        this.saleid = saleid;
    }

    /**
     * 商品名称
     * @return SALENAME 商品名称
     */
    public String getSalename() {
        return salename;
    }

    /**
     * 商品名称
     * @param salename 商品名称
     */
    public void setSalename(String salename) {
        this.salename = salename == null ? null : salename.trim();
    }

    /**
     * 类型
     * @return SALETYPE 类型
     */
    public Integer getSaletype() {
        return saletype;
    }

    /**
     * 类型
     * @param saletype 类型
     */
    public void setSaletype(Integer saletype) {
        this.saletype = saletype;
    }

    /**
     * 对应数据库表ID
     * @return OTHERID 对应数据库表ID
     */
    public BigDecimal getOtherid() {
        return otherid;
    }

    /**
     * 对应数据库表ID
     * @param otherid 对应数据库表ID
     */
    public void setOtherid(BigDecimal otherid) {
        this.otherid = otherid;
    }

    /**
     * 条件分类
     * @return CONTIONTYPE 条件分类
     */
    public String getContiontype() {
        return contiontype;
    }

    /**
     * 条件分类
     * @param contiontype 条件分类
     */
    public void setContiontype(String contiontype) {
        this.contiontype = contiontype == null ? null : contiontype.trim();
    }

    /**
     * 上下架标识
     * @return FLAG 上下架标识
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 上下架标识
     * @param flag 上下架标识
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 上架时间
     * @return UPTIME 上架时间
     */
    public Date getUptime() {
        return uptime;
    }

    /**
     * 上架时间
     * @param uptime 上架时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    /**
     * 上架角色ID
     * @return ROLEID 上架角色ID
     */
    public BigDecimal getRoleid() {
        return roleid;
    }

    /**
     * 上架角色ID
     * @param roleid 上架角色ID
     */
    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }

    /**
     * 绑定买家ID
     * @return BUYROLE 绑定买家ID
     */
    public BigDecimal getBuyrole() {
        return buyrole;
    }

    /**
     * 绑定买家ID
     * @param buyrole 绑定买家ID
     */
    public void setBuyrole(BigDecimal buyrole) {
        this.buyrole = buyrole;
    }

    /**
     * 价格
     * @return SALEPRICE 价格
     */
    public BigDecimal getSaleprice() {
        return saleprice;
    }

    /**
     * 价格
     * @param saleprice 价格
     */
    public void setSaleprice(BigDecimal saleprice) {
        this.saleprice = saleprice;
    }

    /**
     * 皮肤
     * @return SALESKIN 皮肤
     */
    public String getSaleskin() {
        return saleskin;
    }

    /**
     * 皮肤
     * @param saleskin 皮肤
     */
    public void setSaleskin(String saleskin) {
        this.saleskin = saleskin == null ? null : saleskin.trim();
    }
}