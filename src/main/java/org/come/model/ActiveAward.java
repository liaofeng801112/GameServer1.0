package org.come.model;

import come.tool.Good.DropModel;

public class ActiveAward {
	private int active;//活跃值
	private String value;//奖励描述
    private transient DropModel dropModel;
    
	public ActiveAward(int active) {
		super();
		this.active = active;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DropModel getDropModel() {
		return dropModel;
	}
	public void setDropModel(DropModel dropModel) {
		this.dropModel = dropModel;
	}
    
}
