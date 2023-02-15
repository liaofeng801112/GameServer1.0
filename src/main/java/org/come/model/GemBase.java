package org.come.model;

import java.math.BigDecimal;

public class GemBase {

	private String type;
	private double value;
	
	public GemBase(String type, double value) {
		super();
		this.type = type;
		this.value = value;
	}
	/**根据价值换算value*/
	public String getGemValue(int lvl,int G){
		StringBuffer buffer=new StringBuffer();
		buffer.append("等级=");
		buffer.append(lvl);
		buffer.append("|");
		buffer.append(type);
		buffer.append("=");
		BigDecimal big=new BigDecimal(lvl*G/100.0*value);
		if (big.doubleValue()==big.longValue()) {
			buffer.append(big.longValue());
		}else {
			buffer.append(big.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());			
		}
		buffer.append("|价值=");
		buffer.append(G);
		return buffer.toString();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
