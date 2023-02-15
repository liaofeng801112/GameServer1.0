package org.come.bean;

import java.math.BigDecimal;

/**
 * 灵宝法宝装备
 * @author Administrator
 */
public class LingBaoEquipment {

	//装备的灵宝id
	private BigDecimal lingbaoid;
	//装备的法宝id1
	private BigDecimal fabaoid1;
	//装备的法宝id2
	private BigDecimal fabaoid2;
	public BigDecimal getLingbaoid() {
		return lingbaoid;
	}
	public void setLingbaoid(BigDecimal lingbaoid) {
		this.lingbaoid = lingbaoid;
	}
	public BigDecimal getFabaoid1() {
		return fabaoid1;
	}
	public void setFabaoid1(BigDecimal fabaoid1) {
		this.fabaoid1 = fabaoid1;
	}
	public BigDecimal getFabaoid2() {
		return fabaoid2;
	}
	public void setFabaoid2(BigDecimal fabaoid2) {
		this.fabaoid2 = fabaoid2;
	}
	
}
