package org.come.bean;

public class IpActionBean {

	private String ipaddress;// ip
	private String type;// 操作类型 banIp ip封禁 / liftIp ip解禁

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
