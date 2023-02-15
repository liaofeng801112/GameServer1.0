package org.come.bean;

import java.math.BigDecimal;
import java.util.Map;
/**
 * 内丹经验，根据等级取经验
 * @author Administrator
 *
 */
public class NeidanExpBean {
	
	private Map<Integer, BigDecimal> neidanExp;

	public Map<Integer, BigDecimal> getNeidanExp() {
		return neidanExp;
	}

	public void setNeidanExp(Map<Integer, BigDecimal> neidanExp) {
		this.neidanExp = neidanExp;
	}

	
}
