package org.come.entity;

/**
 * (PAYVIP)
 * 
 * @author bianj
 * @version 1.0.0 2019-11-21
 */
public class PayvipBean {
    /** 版本号 */
    private static final long serialVersionUID = 6418302556494912817L;
    
    /**  */
    private Integer id;
    
    /** 充值金额 */
    private Integer paynum;
    
    /** 奖励格式 */
    private String givegoods;
    
    /** 等级 */
    private Integer grade;
    
    /** 描述 */
    private String instructiontext;
    
    /** 持续加成 */
    private String increationtext;
    
    /** 异动时间 */
    private String datetime;
    
    /** 领取次数 */
    private Integer getnumber;
    
    /**
     * 获取id
     * 
     * @return id
     */
    public Integer getId() {
        return this.id;
    }
     
    /**
     * 设置id
     * 
     * @param id
     *          id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * 获取充值金额
     * 
     * @return 充值金额
     */
    public Integer getPaynum() {
        return this.paynum;
    }
     
    /**
     * 设置充值金额
     * 
     * @param paynum
     *          充值金额
     */
    public void setPaynum(Integer paynum) {
        this.paynum = paynum;
    }
    
    /**
     * 获取奖励格式
     * 
     * @return 奖励格式
     */
    public String getGivegoods() {
        return this.givegoods;
    }
     
    /**
     * 设置奖励格式
     * 
     * @param givegoods
     *          奖励格式
     */
    public void setGivegoods(String givegoods) {
        this.givegoods = givegoods;
    }
    
    /**
     * 获取等级
     * 
     * @return 等级
     */
    public Integer getGrade() {
        return this.grade;
    }
     
    /**
     * 设置等级
     * 
     * @param grade
     *          等级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    
    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getInstructiontext() {
        return this.instructiontext;
    }
     
    /**
     * 设置描述
     * 
     * @param instructiontext
     *          描述
     */
    public void setInstructiontext(String instructiontext) {
        this.instructiontext = instructiontext;
    }
    
    /**
     * 获取持续加成
     * 
     * @return 持续加成
     */
    public String getIncreationtext() {
        return this.increationtext;
    }
     
    /**
     * 设置持续加成
     * 
     * @param increationtext
     *          持续加成
     */
    public void setIncreationtext(String increationtext) {
        this.increationtext = increationtext;
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
     * 获取领取次数
     * 
     * @return 领取次数
     */
    public Integer getGetnumber() {
        return this.getnumber;
    }
     
    /**
     * 设置领取次数
     * 
     * @param getnumber
     *          领取次数
     */
    public void setGetnumber(Integer getnumber) {
        this.getnumber = getnumber;
    }
}