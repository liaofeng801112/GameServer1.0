package org.come.entity;

import java.math.BigDecimal;

/**
 * 战斗角色
 *
 * 存储武神山活动中守护烛火的队伍数据
 *
 * @author BigGreen
 *
 */
public class BattleRole {
    //角色id
    private BigDecimal roleid;
    //角色名称
    private String rolename;

    //角色队伍
    private Integer teamid;

    //角色属性 json
    private String property;
    //角色宠物属性
    private String petproperty;
    //角色孩子属性
    private String babyproperty;
    //角色灵宝属性
    private String lingbaoproperty;

    private long starttime;

    public BigDecimal getRoleid() {
        return roleid;
    }
    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    public Integer getTeamid() {
        return teamid;
    }
    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }
    public String getProperty() {
        return property;
    }
    public void setProperty(String property) {
        this.property = property;
    }
    public String getPetproperty() {
        return petproperty;
    }
    public void setPetproperty(String petproperty) {
        this.petproperty = petproperty;
    }
    public String getBabyproperty() {
        return babyproperty;
    }
    public void setBabyproperty(String babyproperty) {
        this.babyproperty = babyproperty;
    }
    public String getLingbaoproperty() {
        return lingbaoproperty;
    }
    public void setLingbaoproperty(String lingbaoproperty) {
        this.lingbaoproperty = lingbaoproperty;
    }
    public long getStarttime() {
        return starttime;
    }
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }


}
