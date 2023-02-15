package org.come.extInterBean;

/**
 * 藏宝阁商品购买
 * 
 * @author zz
 * 
 */
public class GoodsBuyReqBean {
	// 商品表ID
	private String saleid;
	// 购买者ID
	private String roleid;
	// 购买者id
	private String userid;

	public String getSaleid() {
		return saleid;
	}

	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
