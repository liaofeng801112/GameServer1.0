package org.come.extInterBean;

/**
 * 进行物品状态的更改
 * 
 * @author Administrator
 * 
 */
public class SalesGoodsChangeOrderReqBean {
	// 商品id
	private String Sale_id;
	// 更改的类型
	private String type;

	public String getSale_id() {
		return Sale_id;
	}

	public void setSale_id(String sale_id) {
		Sale_id = sale_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
