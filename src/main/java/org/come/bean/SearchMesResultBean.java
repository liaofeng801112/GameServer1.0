package org.come.bean;

import java.util.List;

import org.come.entity.Message;
/**
 * 查询消息返回的bean
 * @author Administrator
 *
 */
public class SearchMesResultBean {

	/**
	 * 查询的收藏集合
	 */
	private List<Message> messages;
	
	/**
	 * 总页数
	 */
	private Integer total;


	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
