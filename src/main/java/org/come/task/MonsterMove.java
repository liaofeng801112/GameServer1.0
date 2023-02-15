package org.come.task;

import org.come.bean.PathPoint;


public class MonsterMove{
	//路径编号
	private MonsterMoveBase moveBase;
	//所需时间
	private int endTime;
	//已移动的时间
	private int time;
	public MonsterMove(MonsterMoveBase moveBase,int time,int x,int y) {
		// TODO Auto-generated constructor stub
		this.moveBase=moveBase;
		this.time=time;
		this.endTime=moveBase.getEndTime();
		PathPoint point=this.moveBase.getPoints().get(0);
		this.endTime+=MonsterMoveBase.gettime(point.getX()-x,point.getY()-y,0.07);
	}
	public boolean isMove(int addTime){
		time+=addTime;
		return time>=endTime;
	}
	public int getBh() {
		return moveBase.getBh();
	}
	public MonsterMoveBase getMoveBase() {
		return moveBase;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
