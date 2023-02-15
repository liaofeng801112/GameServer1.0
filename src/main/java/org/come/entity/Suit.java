package org.come.entity;

import come.tool.Calculation.BaseSkill;

/**
 * 套装
 */
public class Suit {
	
	private int SuitID; //套装id
	private String Sname; //套装名
	private int Sysex; //适用性别
	private String HaveParts; //拥有部件
	private String HaveSkill; //拥有技能
	private String Introduce; //介绍
	private transient int[] Parts;//缓存部件数组
	private transient BaseSkill[] Suits;//缓存部件数组
	public BaseSkill[] getSuits() {
		if (HaveSkill!=null&&!HaveSkill.equals("")) {
			String[] vs=HaveSkill.split("\\|");
			Suits=new BaseSkill[vs.length];
			for (int i = 0; i < vs.length; i++) {
				String[] v=vs[i].split("-");
				Suits[i]=new BaseSkill(Integer.parseInt(v[1]),Integer.parseInt(v[0]));
			}
		}
		return Suits;
	}
	public int getSuitID() {
		return SuitID;
	}
	public void setSuitID(int suitID) {
		SuitID = suitID;
	}
	public String getSname() {
		return Sname;
	}
	public void setSname(String sname) {
		Sname = sname;
	}
	public int getSysex() {
		return Sysex;
	}
	public void setSysex(int sysex) {
		Sysex = sysex;
	}
	public String getHaveParts() {
		return HaveParts;
	}
	public void setHaveParts(String haveParts) {
		HaveParts = haveParts;
	}
	public String getHaveSkill() {
		return HaveSkill;
	}
	public void setHaveSkill(String haveSkill) {
		HaveSkill = haveSkill;
	}
	public String getIntroduce() {
		return Introduce;
	}
	public void setIntroduce(String introduce) {
		Introduce = introduce;
	}
	public int[] getParts() {
		return Parts;
	}
	public void setParts(int[] parts) {
		Parts = parts;
	}
	
}
