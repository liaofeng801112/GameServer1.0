package org.come.bean;


/**
 * 通过接口访问返回的bean类
 * @author Administrator
 *
 */
public class RequestReturnBean {
	//返回消息协议标识（自定义）
	
	/**
	 * 报文协议修改
	 * 1003 物品记录查询
	 * 1004 用户信息查询
	 * 1005 账号启封号
	 * 1006 登录管理，返回区域信息,管理员信息
	 * 1007 区域管理
	 */
	private String requresHeader="1003";
	
	//请求成功或者失败,默认失败，成功success
	private String style="error";
	
	//返回的消息内容
	private String content;
	
	//时间标识符
	private String returnDate;

	public String getRequresHeader() {
		return requresHeader;
	}

	public void setRequresHeader(String requresHeader) {
		this.requresHeader = requresHeader;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	
	

}
