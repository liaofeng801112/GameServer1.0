package org.come.bean;

import java.math.BigDecimal;

/**
 * 申请组队发送的bean
 * @author Administrator
 *
 */
public class TeamApplyBean {
	// 角色ID
	private BigDecimal roleid;
	
	// 角色信息
	private LoginResult rolInfo;
	
	
	
	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public LoginResult getRolInfo() {
		return rolInfo;
	}

	public void setRolInfo(LoginResult rolInfo) {
		this.rolInfo = rolInfo;
	}


}
