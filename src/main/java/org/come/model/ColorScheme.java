package org.come.model;

public class ColorScheme {

	//id
	private int id;
	//颜色备注
	private String name;
	//变色方案值
	private String value;
	//最低成长
	private int min;
	//最高成长
	private int max;
	//使用类型 0通用 否则只有专属召唤兽可以用
	private int zid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getZid() {
		return zid;
	}
	public void setZid(int zid) {
		this.zid = zid;
	}
}
