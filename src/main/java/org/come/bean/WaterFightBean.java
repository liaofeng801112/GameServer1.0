package org.come.bean;

/**
 * 水路战斗队伍序列
 * @author Administrator
 * 单个队伍的战斗序列bean
 *
 */
public class WaterFightBean {

	//队伍 string
	private String teamGroup;
	//战斗场次顺序(1-2|1-3|1-4(map里面队伍的id),0（表示只有一个队伍）)  第一场战斗|第二场战斗|第三场战斗
	private String fightNumber;
	//当前场次
	private int fightNow;
	//总场次
	private int sumFight;
	//战斗结束标志 0标识没有结束，1表示该组战斗结束
	private int fightOrNo=0;
	
	//是否进行战斗中(0表示没有战斗，1表示战斗进行中)
	private int state=0;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTeamGroup() {
		return teamGroup;
	}
	public void setTeamGroup(String teamGroup) {
		this.teamGroup = teamGroup;
	}
	public String getFightNumber() {
		return fightNumber;
	}
	public void setFightNumber(String fightNumber) {
		this.fightNumber = fightNumber;
	}
	public int getFightNow() {
		return fightNow;
	}
	public void setFightNow(int fightNow) {
		this.fightNow = fightNow;
	}
	public int getSumFight() {
		return sumFight;
	}
	public void setSumFight(int sumFight) {
		this.sumFight = sumFight;
	}
	public int getFightOrNo() {
		return fightOrNo;
	}
	public void setFightOrNo(int fightOrNo) {
		this.fightOrNo = fightOrNo;
	}
	
	
	
	
	
}
