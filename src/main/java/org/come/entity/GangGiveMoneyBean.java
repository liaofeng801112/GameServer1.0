package org.come.entity;

import org.come.bean.LoginResult;
/**
 * 捐钱捐款
 * @author 	gl
 * @WeChat	sinmahod
 */

public class GangGiveMoneyBean {
    private LoginResult loginResult;// 玩家信息
    private Gang gang;// 帮派信息
    public Gang getGang() {
        return gang;
    }
    public void setGang(Gang gang) {
        this.gang = gang;
    }
    public LoginResult getLoginResult() {
        return loginResult;
    }
    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

}
