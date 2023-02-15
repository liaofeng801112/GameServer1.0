package org.come.entity;
/**
 * (CHONGJIPACK)
 * 
 * @author bianj
 * @version 1.0.0 2019-11-21
 */
public class ChongjipackBean {
    /** 版本号 */
    private static final long serialVersionUID = 203745024714917095L;
    
    /** 礼包 id */
    private Integer id;
    
    /** 礼包类型  (1 新手礼包 2 小 资冲级礼包 3 豪华冲级礼包、4、每日充值、5、连充 6、 每日特惠) */
    private Integer packtype;
    
    /** 礼包级别  (表示对应的礼包级别默认 为 0 例如：每日充值三档，分别为 1，2，3)*/
    private Integer packgradetype;
    
    /** 领取等级 (x 转 xx 级)*/
    private String packgrade;
    
    /** 领取的物品(物品=80655$5&80656$5 */
    private String packgoods;
    
    /** 当前礼包领取次数 */
    private Integer getnumber;
    
    /** 异动时间 */
    private String datetime;
    
    /** 获取需要支付的金额 */
    private Integer canpaymoney;
    
    /** 展示时间*/
    private String huitime;
    
    /**
     * 获取礼包 id
     * 
     * @return 礼包 id
     */
    public Integer getId() {
        return this.id;
    }
     
    /**
     * 设置礼包 id
     * 
     * @param id
     *          礼包 id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * 获取礼包类型
     * 
     * @return 礼包类型
     */
    public Integer getPacktype() {
        return this.packtype;
    }
     
    /**
     * 设置礼包类型
     * 
     * @param packtype
     *          礼包类型
     */
    public void setPacktype(Integer packtype) {
        this.packtype = packtype;
    }
    
    /**
     * 获取礼包级别
     * 
     * @return 礼包级别
     */
    public Integer getPackgradetype() {
        return this.packgradetype;
    }
     
    /**
     * 设置礼包级别
     * 
     * @param packgradetype
     *          礼包级别
     */
    public void setPackgradetype(Integer packgradetype) {
        this.packgradetype = packgradetype;
    }
    
    /**
     * 获取领取等级
     * 
     * @return 领取等级
     */
    public String getPackgrade() {
        return this.packgrade;
    }
     
    /**
     * 设置领取等级
     * 
     * @param packgrade
     *          领取等级
     */
    public void setPackgrade(String packgrade) {
        this.packgrade = packgrade;
    }
    
    /**
     * 获取领取的物品
     * 
     * @return 领取的物品
     */
    public String getPackgoods() {
        return this.packgoods;
    }
     
    /**
     * 设置领取的物品
     * 
     * @param packgoods
     *          领取的物品
     */
    public void setPackgoods(String packgoods) {
        this.packgoods = packgoods;
    }
    
    /**
     * 获取当前礼包领取次数
     * 
     * @return 当前礼包领取次数
     */
    public Integer getGetnumber() {
        return this.getnumber;
    }
     
    /**
     * 设置当前礼包领取次数
     * 
     * @param getnumber
     *          当前礼包领取次数
     */
    public void setGetnumber(Integer getnumber) {
        this.getnumber = getnumber;
    }
    
    /**
     * 获取异动时间
     * 
     * @return 异动时间
     */
    public String getDatetime() {
        return this.datetime;
    }
     
    /**
     * 设置异动时间
     * 
     * @param datetime
     *          异动时间
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    /**
     * 获取获取需要支付的金额
     * 
     * @return 获取需要支付的金额
     */
    public Integer getCanpaymoney() {
        return this.canpaymoney;
    }
     
    /**
     * 设置获取需要支付的金额
     * 
     * @param canpaymoney
     *          获取需要支付的金额
     */
    public void setCanpaymoney(Integer canpaymoney) {
        this.canpaymoney = canpaymoney;
    }
    
    /**
     * 获取huitime
     * 
     * @return huitime
     */
    public String getHuitime() {
        return this.huitime;
    }
     
    /**
     * 设置huitime
     * 
     * @param huitime
     *          huitime
     */
    public void setHuitime(String huitime) {
        this.huitime = huitime;
    }
}