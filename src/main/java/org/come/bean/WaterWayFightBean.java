package org.come.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 水路战斗对小组对象
 * @author 黄建彬
 */
public class WaterWayFightBean {
	
	//小组的队伍Map 集合
	private Map<Integer ,WaterFightBean> gropuTeam=new HashMap<Integer ,WaterFightBean>();

	public Map<Integer, WaterFightBean> getGropuTeam() {
		return gropuTeam;
	}

	public void setGropuTeam(Map<Integer, WaterFightBean> gropuTeam) {
		this.gropuTeam = gropuTeam;
	}
	
//	//战斗场次顺序(1-2=4-3|1-3=2-4|1-4=2-3|(map里面队伍的id),0（表示只有一个队伍）)  第一场战斗|第二场战斗|第三场战斗
//	private String fightNumber;
//	//当前场次
//	private int fightNow;
//	//总场次
//	private int sumFight;
//	//战斗结束标志 0标识没有结束，1表示该组战斗结束
//	private int fightOrNo=0;
	
//	public int getFightOrNo() {
//		return fightOrNo;
//	}
//	public void setFightOrNo(int fightOrNo) {
//		this.fightOrNo = fightOrNo;
//	}
//	public Map<Integer, String> getGropuTeam() {
//		return gropuTeam;
//	}
//	public void setGropuTeam(Map<Integer, String> gropuTeam) {
//		this.gropuTeam = gropuTeam;
//	}
//	public String getFightNumber() {
//		return fightNumber;
//	}
//	public void setFightNumber(String fightNumber) {
//		this.fightNumber = fightNumber;
//	}
//	public int getFightNow() {
//		return fightNow;
//	}
//	public void setFightNow(int fightNow) {
//		this.fightNow = fightNow;
//	}
//	public int getSumFight() {
//		return sumFight;
//	}
//	public void setSumFight(int sumFight) {
//		this.sumFight = sumFight;
//	}
//	

}
