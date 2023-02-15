package org.come.bean;

import java.math.BigDecimal;
import java.util.List;
/**
 * NPC装备合成bean
 * @author 叶豪芳
 * @date 2017年12月30日 下午2:39:11
 * 
 */ 
public class NpcComposeBean {

	//类型
	private String composetype;
	
	//物品集合  集合第一个是主物品
	private List<BigDecimal> goodstables;
	
	public String getComposetype() {
		return composetype;
	}
	public void setComposetype(String composetype) {
		this.composetype = composetype;
	}
	public List<BigDecimal> getGoodstables() {
		return goodstables;
	}
	public void setGoodstables(List<BigDecimal> goodstables) {
		this.goodstables = goodstables;
	}
	

}
