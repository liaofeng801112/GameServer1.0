package org.come.entity;

import java.math.BigDecimal;

public class Ipaddressmac {
    /**
     * 编号
     */
    private BigDecimal ipid;

    /**
     * ip地址
     */
    private String addresskey;

    /**
     * 插入的时间
     */
    private String ctime;

    /**
     * 编号
     * @return IPID 编号
     */
    public BigDecimal getIpid() {
        return ipid;
    }

    /**
     * 编号
     * @param ipid 编号
     */
    public void setIpid(BigDecimal ipid) {
        this.ipid = ipid;
    }

    /**
     * ip地址
     * @return ADDRESSKEY ip地址
     */
    public String getAddresskey() {
        return addresskey;
    }

    /**
     * ip地址
     * @param addresskey ip地址
     */
    public void setAddresskey(String addresskey) {
        this.addresskey = addresskey == null ? null : addresskey.trim();
    }

    /**
     * 插入的时间
     * @return CTIME 插入的时间
     */
    public String getCtime() {
        return ctime;
    }

    /**
     * 插入的时间
     * @param ctime 插入的时间
     */
    public void setCtime(String ctime) {
        this.ctime = ctime == null ? null : ctime.trim();
    }
}