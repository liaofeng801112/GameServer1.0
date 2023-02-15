package org.come.bean;

/**
 * HGC--2019-11-13--手机号和安全码
 * 
 * @author HGC
 * 
 */
public class PhoneNumberSGBean {

	/** 绑定的手机号(无则nophone) */
	private String phone;
	/** 安全码 */
	private String safenumber;
	/** 验证码异动时间 */
	private String numbertime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSafenumber() {
		return safenumber;
	}

	public void setSafenumber(String safenumber) {
		this.safenumber = safenumber;
	}

	public String getNumbertime() {
		return numbertime;
	}

	public void setNumbertime(String numbertime) {
		this.numbertime = numbertime;
	}

}
