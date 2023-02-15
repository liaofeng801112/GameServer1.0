package org.come.model;

import java.util.List;

import come.tool.Good.DropModel;
import come.tool.Good.TSModel;

/**
 * 地图怪物信息
 * @author 叶豪芳
 * @date 2017年12月28日 上午3:50:16
 * 
 */ 
public class Robots {
	private String robotid;// 怪物ID
	private String robotname;// 怪物名称
	private String robotskin;// 皮肤
	private transient int robottime;// 存在时间
	private transient String robotlvl;// 怪物等级
	private transient String robotboss;// 主怪
	private transient String robotmonster;// 小怪
	private transient String robotcount;// 数量
	private transient String robotreward;// 掉落类型
	private transient String unknow;
	private transient String lvllimit;//等级限制
	private transient int robotType;//掉落类型
	private transient int dropLimit;//掉落限制
	private transient TSModel tsModel;//掉落限制

	private transient int[] lvls;
	private transient List<Integer> taskIds;
	private transient List<String> monsterList;
	private transient DropModel dropModel;
	private transient boolean isJL;//是否需要记录次数
	
	private transient int robotID;//robotID
	
	public String getRobotid() {
		return robotid;
	}
	public void setRobotid(String robotid) {
		this.robotid = robotid;
	}
	public String getRobotname() {
		return robotname;
	}
	public void setRobotname(String robotname) {
		this.robotname = robotname;
	}
	public String getRobotskin() {
		return robotskin;
	}
	public void setRobotskin(String robotskin) {
		this.robotskin = robotskin;
	}
	public int getRobottime() {
		return robottime;
	}
	public void setRobottime(int robottime) {
		this.robottime = robottime;
	}
	public String getRobotlvl() {
		if (robotlvl==null||robotlvl.equals(""))robotlvl="0";
		return robotlvl;
	}
	public void setRobotlvl(String robotlvl) {
		this.robotlvl = robotlvl;
	}
	public String getRobotboss() {
		return robotboss;
	}
	public void setRobotboss(String robotboss) {
		this.robotboss = robotboss;
	}
	public String getRobotmonster() {
		return robotmonster;
	}
	public void setRobotmonster(String robotmonster) {
		this.robotmonster = robotmonster;
	}
	public String getRobotcount() {
		return robotcount;
	}
	public void setRobotcount(String robotcount) {
		this.robotcount = robotcount;
	}
	public String getRobotreward() {
		return robotreward;
	}
	public void setRobotreward(String robotreward) {
		this.robotreward = robotreward;
	}
	public String getUnknow() {
		return unknow;
	}
	public void setUnknow(String unknow) {
		this.unknow = unknow;
	}
	public String getLvllimit() {
		return lvllimit;
	}
	public void setLvllimit(String lvllimit) {
		this.lvllimit = lvllimit;
	}
	public int[] getLvls() {
		return lvls;
	}
	public void setLvls(int[] lvls) {
		this.lvls = lvls;
	}
	public int getRobotType() {
		return robotType;
	}
	public void setRobotType(int robotType) {
		this.robotType = robotType;
	}
	public List<Integer> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}
	public List<String> getMonsterList() {
		return monsterList;
	}
	public void setMonsterList(List<String> monsterList) {
		this.monsterList = monsterList;
	}
	public DropModel getDropModel() {
		return dropModel;
	}
	public void setDropModel(DropModel dropModel) {
		this.dropModel = dropModel;
		if (dropModel!=null) {
			if (dropModel.getExps()!=null||dropModel.getMaxGood()!=null) {
				isJL=true;
			}
		}
	}
	public boolean isJL() {
		return isJL;
	}
	public void setJL(boolean isJL) {
		this.isJL = isJL;
	}
	public int getDropLimit() {
		return dropLimit;
	}
	public void setDropLimit(int dropLimit) {
		this.dropLimit = dropLimit;
	}
	public TSModel getTsModel() {
		return tsModel;
	}
	public void setTsModel(TSModel tsModel) {
		this.tsModel = tsModel;
	}
	public int getRobotID() {
		return robotID;
	}
	public void setRobotID(int robotID) {
		this.robotID = robotID;
	}
	
}
