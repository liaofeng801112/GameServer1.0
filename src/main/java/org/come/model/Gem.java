package org.come.model;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;

public class Gem {

	//宝石编号 对应物品表id
	private int bid;
	//宝石名
	private String bname;
	//宝石属性
	private String value;
	//宝石属性
	private List<GemBase> gemBases;
	public void initGemBase(){
		gemBases=new ArrayList<>();
//		加强风#加强火#加强水#加强雷#加强鬼火#加强中毒=2.8|加强昏睡#加强封印#加强混乱#加强遗忘#强震慑#加强攻击法术效果#加强防御法术效果=1.2|加强魅惑=2.4
		String[] values=value.split("\\|");
		for (int i = 0; i < values.length; i++) {
			String[] vs=values[i].split("=");
			double zhi=Double.parseDouble(vs[1]);
			vs=vs[0].split("#");
			for (int j = 0; j < vs.length; j++) {
				gemBases.add(new GemBase(vs[j],zhi));
			}
		}
	}
	/**获取指定宝石属性*/
	public GemBase getGemBase(String type){
		if (type==null) {return rGemBase();}
		for (int i = gemBases.size()-1; i >=0; i--) {
			GemBase base=gemBases.get(i);
			if (base.getType().equals(type)) {return base;}
		}
		return gemBases.get(0);	
	}
	/**随机一个宝石属性*/
	public GemBase rGemBase(){return gemBases.get(Battlefield.random.nextInt(gemBases.size()));}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
