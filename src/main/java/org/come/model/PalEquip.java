package org.come.model;

import come.tool.Calculation.PalEquipQl;
import come.tool.Calculation.PalQl;

public class PalEquip {

	private int id;//id
	private long type;//装备类型
	private String limit;//装备限制
	private String values;//字段列表
	private String qh;//培养值集合
	private String name;//名称集合
	private String skin;//皮肤集合
	private String in;//说明集合
	
	private String[] limits;
	private PalEquipQl[] qls;
	private int[] qhs;
	private String[] names;
	private String[] skins;
	private String[] ins;
	private int upLvl;//培养等级上限
	
	public PalQl getPalQl(String key,double value,double xs,int lvl,int JC){
		for (int i = 0; i < qls.length; i++) {
			PalQl palQl=qls[i].getPalQl(key,value,xs,lvl,JC);
			if (palQl!=null) {
				return palQl;
			}
		}
		return null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String getQh() {
		return qh;
	}
	public void setQh(String qh) {
		this.qh = qh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public PalEquipQl[] getQls() {
		return qls;
	}
	public void setQls(PalEquipQl[] qls) {
		this.qls = qls;
	}
	public int[] getQhs() {
		return qhs;
	}
	public void setQhs(int[] qhs) {
		this.qhs = qhs;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getSkins() {
		return skins;
	}
	public void setSkins(String[] skins) {
		this.skins = skins;
	}
	public String[] getIns() {
		return ins;
	}
	public void setIns(String[] ins) {
		this.ins = ins;
	}
	public int getUpLvl() {
		return upLvl;
	}
	public void setUpLvl(int upLvl) {
		this.upLvl = upLvl;
	}
	public String[] getLimits() {
		return limits;
	}
	public void setLimits(String[] limits) {
		this.limits = limits;
	}
}
