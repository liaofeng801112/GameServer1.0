package org.come.bean;

import java.util.List;

import org.come.entity.Gang;
/**
 * 返回所有帮派信息的bean
 * @author 叶豪芳
 * @date 2017年12月20日 下午12:18:14
 * 
 */ 
public class GangListBean {
	// 所有帮派信息
	private List<Gang> gangs;

	public List<Gang> getGangs() {
		return gangs;
	}

	public void setGangs(List<Gang> gangs) {
		this.gangs = gangs;
	}
	
	

}
