package org.come.bean;

public class AccountBinding {

	private String type;// 操作类型 ( binding 进行绑定 / getbinding 获取是否有绑定 )

	private String username;// 账号
	private String password;// 密码
	private String safely;// 安全码
	private String tuiji;// 推荐码

	private String phone;// 手机号
	private String flag;// 账号标识

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSafely() {
		return safely;
	}

	public void setSafely(String safely) {
		this.safely = safely;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTuiji() {
		return tuiji;
	}

	public void setTuiji(String tuiji) {
		this.tuiji = tuiji;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
