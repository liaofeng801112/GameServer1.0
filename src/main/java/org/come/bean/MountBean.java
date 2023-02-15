package org.come.bean;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Mount;

/**
 * 坐骑表
 * @author 叶豪芳
 * @date 2017年12月25日 上午11:48:05
 * 
 */ 
public class MountBean {
	
	private ConcurrentHashMap<BigDecimal, ConcurrentHashMap<Integer, Mount>> allMount;

	public ConcurrentHashMap<BigDecimal, ConcurrentHashMap<Integer, Mount>> getAllMount() {
		return allMount;
	}
	public void setAllMount(ConcurrentHashMap<BigDecimal, ConcurrentHashMap<Integer, Mount>> allMount) {
		this.allMount = allMount;
	}
}
