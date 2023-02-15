package org.come.bean;

import java.math.BigDecimal;

public class GangChangeBean {
	private BigDecimal gangid;
	private String gangName;
	private String post;
	private String msg;
	public GangChangeBean(LoginResult result,String msg) {
		// TODO Auto-generated constructor stub
		this.gangid=result.getGang_id();
		this.gangName=result.getGangname();
		this.post=result.getGangpost();
		this.msg=msg;
	}
	public BigDecimal getGangid() {
		return gangid;
	}
	public void setGangid(BigDecimal gangid) {
		this.gangid = gangid;
	}
	public String getGangName() {
		return gangName;
	}
	public void setGangName(String gangName) {
		this.gangName = gangName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
