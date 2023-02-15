package org.come.extInterBean;

public class ShopBuyRecordReqBean {
	// 商城购买记录查询
	// 购买类型
	private String buyType;
	// 记录时间
	private String recordTime;
	// 购买账号
	private String userName;
	// 购买角色名
	private String roleName;
	// 当前页
	private String nowPage;
	// 起始
	private int start = 0;
	// 结束
	private int end = 0;

	public String getBuyType() {
		return buyType;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
