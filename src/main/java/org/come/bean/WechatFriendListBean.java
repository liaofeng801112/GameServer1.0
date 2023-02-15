package org.come.bean;

import java.util.List;

import org.come.entity.Wechatrecord;

public class WechatFriendListBean {
	private List<Wechatrecord > wechatFriendList;
	private int searchPage;//查询的页码
	private String time;//-查询的时间
	private int sumPage;//总页码数
	private String sendId;//发送者ID
	private String getId;//接收者Id  
	private int pageNumber;//-每页查询的条数
	public List<Wechatrecord> getWechatFriendList() {
		return wechatFriendList;
	}
	public void setWechatFriendList(List<Wechatrecord> wechatFriendList) {
		this.wechatFriendList = wechatFriendList;
	}
	public int getSearchPage() {
		return searchPage;
	}
	public void setSearchPage(int searchPage) {
		this.searchPage = searchPage;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getSumPage() {
		return sumPage;
	}
	public void setSumPage(int sumPage) {
		this.sumPage = sumPage;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getGetId() {
		return getId;
	}
	public void setGetId(String getId) {
		this.getId = getId;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}
