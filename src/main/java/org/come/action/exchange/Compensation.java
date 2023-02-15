package org.come.action.exchange;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class Compensation {

	//补偿的CDK
	private String CCDK;
	//补偿的礼包
	private BigDecimal goodId;
	//补偿的时间段
	private long Cmin;
	private long Cmax;
	//补偿的范围 K表示ID  V表示 状态  0未领取 1已经领取
	private ConcurrentHashMap<BigDecimal,Integer> Cmap;
	public Compensation() {
		// TODO Auto-generated constructor stub
		Cmap=new ConcurrentHashMap<>();
	}
	/**判断是否在包含的时间内*/
	public boolean contain(long time){
		if (time>Cmin&&time<Cmax) {
			return true;
		}
		return false;
	}
	/**添加*/
	public void addMap(BigDecimal ID){
		if (Cmap.get(ID)==null) {
			Cmap.put(ID, 0);
		}
	}
	/**记录领取 0表示不在补偿范围内 1表示可以补偿 2表示已经补偿过了*/
	public int receive(BigDecimal ID){
		Integer id=Cmap.get(ID);
		if (id==null) {
			return 0;
		}
		if (id==0) {
			Cmap.put(ID, 1);
			return 1;
		}
		return 2;
	}
	public String getCCDK() {
		return CCDK;
	}
	public void setCCDK(String cCDK) {
		CCDK = cCDK;
	}
	public long getCmin() {
		return Cmin;
	}
	public void setCmin(long cmin) {
		Cmin = cmin;
	}
	public long getCmax() {
		return Cmax;
	}
	public void setCmax(long cmax) {
		Cmax = cmax;
	}
	public BigDecimal getGoodId() {
		return goodId;
	}
	public void setGoodId(BigDecimal goodId) {
		this.goodId = goodId;
	}
	
}
