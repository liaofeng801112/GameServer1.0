package org.come.model;

import org.come.bean.PathPoint;

/**
 * 地图怪物出生坐标
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:38:12
 * 
 */ 
public class Sghostpoint {
	// 坐标ID
	private int pointidString;
	// 坐标地图ID
	private int pointkey;
	// 坐标地图名称
	private String pointmap;
	// 可用任务
	private String pointtype;	
	// 坐标
	private String pointpoint;
	//坐标集合
	private PathPoint[] points;
	
	public int getPointidString() {
		return pointidString;
	}

	public void setPointidString(int pointidString) {
		this.pointidString = pointidString;
	}

	public int getPointkey() {
		return pointkey;
	}

	public void setPointkey(int pointkey) {
		this.pointkey = pointkey;
	}

	public String getPointmap() {
		return pointmap;
	}

	public void setPointmap(String pointmap) {
		this.pointmap = pointmap;
	}

	public String getPointtype() {
		return pointtype;
	}

	public void setPointtype(String pointtype) {
		this.pointtype = pointtype;
	}

	public String getPointpoint() {
		return pointpoint;
	}

	public void setPointpoint(String pointpoint) {
		this.pointpoint = pointpoint;
	}
	public PathPoint[] getPoints() {
		return points;
	}
	public void setPoints(PathPoint[] points) {
		this.points = points;
	}
	


}
