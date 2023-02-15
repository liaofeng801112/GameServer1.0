package org.come.bean;

import java.util.Map;

import org.come.model.Linbaoskill;


/**
 * 灵宝技能
 * @author 叶豪芳
 * @date 2017年12月25日 下午9:12:04
 * 
 */ 
public class LingbaoSKillBean {
	private Map<String, Linbaoskill> allLingbaoskill;

	public Map<String, Linbaoskill> getAllLingbaoskill() {
		return allLingbaoskill;
	}

	public void setAllLingbaoskill(Map<String, Linbaoskill> allLingbaoskill) {
		this.allLingbaoskill = allLingbaoskill;
	}
	
	
}
