package org.come.bean;

public class AccountActionBean {

	private Account account;// 账号信息
	private String type;// 类型 (updatePhone 手机号修改,updatePasw 密码修改,updateSafely
						// 安全码修改)

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
