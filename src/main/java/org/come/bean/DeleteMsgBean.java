package org.come.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量删除消息bean
 * @author Administrator
 *
 */
public class DeleteMsgBean {

	/**
	 * 删除的ID
	 */
	private List<BigDecimal> ids;

	public List<BigDecimal> getIds() {
		return ids;
	}

	public void setIds(List<BigDecimal> ids) {
		this.ids = ids;
	}
	
}
