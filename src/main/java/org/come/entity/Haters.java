package org.come.entity;

import java.math.BigDecimal;

public class Haters {
    /**
     * null
     */
    private BigDecimal roleid;
    /**
     * null
     */
    private String unknown;

    /**
     * null
     * @return ROLEID null
     */
    public BigDecimal getRoleid() {
        return roleid;
    }

    /**
     * null
     * @param roleid null
     */
    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }

    /**
     * null
     * @return UNKNOWN null
     */
    public String getUnknown() {
        return unknown;
    }

    /**
     * null
     * @param unknown null
     */
    public void setUnknown(String unknown) {
        this.unknown = unknown == null ? null : unknown.trim();
    }
}