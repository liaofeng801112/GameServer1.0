package org.come.task;

import java.math.BigDecimal;

import org.come.bean.PathPoint;

public class MonsterFollow {
	//跟随玩家id
	private BigDecimal followID;
	//坐标集合
	private PathPoint[] points;
	//记录上次战斗结束时间
	private long time;
	public MonsterFollow(PathPoint[] points) {
		// TODO Auto-generated constructor stub
		this.points=points;
	}
	public BigDecimal getFollowID() {
		return followID;
	}
	public void setFollowID(BigDecimal followID) {
		this.followID = followID;
	}
	public PathPoint[] getPoints() {
		return points;
	}
	public void setPoints(PathPoint[] points) {
		this.points = points;
	}

	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	public static long PROTECTTIME=4;
	/***/
	public String isTime(){
		if (time==0) {return null;}
		long JG=PROTECTTIME-((System.currentTimeMillis()-time)/1000);
		if (JG>0) {return "剩余"+JG+"秒的保护期";}
		return null;
	}
}
