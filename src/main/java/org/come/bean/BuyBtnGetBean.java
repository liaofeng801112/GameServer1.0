package org.come.bean;

/**
 * 点击下单购买返回的数据字段
 * @author Administrator
 *
 */
public class BuyBtnGetBean {
	
	private String inGetPeoSum;//商品收藏人数
	
	private String goodsRoleName;//售卖商品的玩家
	
	private boolean flag;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getInGetPeoSum() {
		return inGetPeoSum;
	}

	public void setInGetPeoSum(String inGetPeoSum) {
		this.inGetPeoSum = inGetPeoSum;
	}

	public String getGoodsRoleName() {
		return goodsRoleName;
	}

	public void setGoodsRoleName(String goodsRoleName) {
		this.goodsRoleName = goodsRoleName;
	}
	
	
	
	

}
