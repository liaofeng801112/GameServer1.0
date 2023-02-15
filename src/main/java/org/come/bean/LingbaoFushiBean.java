package org.come.bean;

import java.math.BigDecimal;
import java.util.Map;

import org.come.entity.Goodstable;

/**
 * 灵宝符石
 * @author 叶豪芳
 * @date 2017年12月25日 下午9:37:00
 * 
 */ 
public class LingbaoFushiBean {
	private Map<BigDecimal, Goodstable> allLingbaoFushi;

	public Map<BigDecimal, Goodstable> getAllLingbaoFushi() {
		return allLingbaoFushi;
	}

	public void setAllLingbaoFushi(Map<BigDecimal, Goodstable> allLingbaoFushi) {
		this.allLingbaoFushi = allLingbaoFushi;
	}
	
	
}
