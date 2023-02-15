package org.come.action.rich;

import java.math.BigDecimal;

public class InputBean {
//	 类型 0:保留 1:人物列表 2:物品 3:召唤兽 4:灵宝 
	private int type;
//	 id
	private BigDecimal id;
//	 别名
	private String name;
//	 颜色
	private String color;
	public InputBean() {
		// TODO Auto-generated constructor stub
	}
	public InputBean(int type, BigDecimal id, String name, String color) {
		super();
		this.type = type;
		this.id = id;
		this.name = name;
		this.color = color;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
