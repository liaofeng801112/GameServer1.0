package org.come.pay.pojo;

import org.come.bean.LoginResult;

public class CatUserRole {
    private long role_id;
    private long user_id;
    private long gold;
    private long codecard;
    private String rolename;
    private String userName;
    private String userPwd;
    private String password;

    public CatUserRole(LoginResult ret) {
        this.codecard = ret.getCodecard().longValue();
        this.gold = ret.getGold().longValue();
        this.password = ret.getPassword();
        this.role_id = ret.getRole_id().longValue();
        this.rolename = ret.getRolename();
        this.user_id = ret.getUser_id().longValue();
        this.userName = ret.getUserName();
        this.userPwd = ret.getUserPwd();
    }

    public String getRolename() {
        return this.rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRole_id() {
        return this.role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getGold() {
        return this.gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getCodecard() {
        return this.codecard;
    }

    public void setCodecard(long codecard) {
        this.codecard = codecard;
    }
}
