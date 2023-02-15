package org.come.bean;

import java.util.List;

import org.come.entity.Goodstable;
/**
 * 背包bean
 * @author 叶豪芳
 * @date : 2017年11月26日 下午8:46:49
 */
public class GoodsResultArrBean {
	private int I;
	// 用户物品变化信息
	private List<Goodstable>  list;
	public List<Goodstable> getList() {
		return list;
	}
	public void setList(List<Goodstable> list) {
		this.list = list;
	}
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
}
