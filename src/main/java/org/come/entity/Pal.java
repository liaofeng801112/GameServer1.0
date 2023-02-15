package org.come.entity;

import java.math.BigDecimal;

public class Pal {

	private BigDecimal id;//数据库表id
	private int pId;//伙伴id
	private double grow;//成长 
	private int lvl;//等级
	private long exp;//经验
	private String parts;//装备id
	private BigDecimal roleId;//角色id
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public double getGrow() {
		return grow;
	}
	public void setGrow(double grow) {
		this.grow = grow;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public long getExp() {
		return exp;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
	public String getParts() {
		return parts;
	}
	public void setParts(String parts) {
		this.parts = parts;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	
}
