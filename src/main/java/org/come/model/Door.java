package org.come.model;

public class Door {
	//地图id
	private String doorid;
	
	//地图id [1]
	private String doormap;

	//触发矩阵
	private String doorrect;

	//坐标
	private String doorpoint;

	//地图名
	private transient String doormapname;

	//备注
	private transient String doortext;

	//传送名
	private String doorkey;
	
	public String getDoorid() {
		return doorid;
	}
	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}
	public String getDoormap() {
		return doormap;
	}
	public void setDoormap(String doormap) {
		this.doormap = doormap;
	}
	public String getDoorrect() {
		return doorrect;
	}
	public void setDoorrect(String doorrect) {
		this.doorrect = doorrect;
	}
	public String getDoorpoint() {
		return doorpoint;
	}
	public void setDoorpoint(String doorpoint) {
		this.doorpoint = doorpoint;
	}
	public String getDoormapname() {
		return doormapname;
	}
	public void setDoormapname(String doormapname) {
		this.doormapname = doormapname;
	}
	public String getDoortext() {
		return doortext;
	}
	public void setDoortext(String doortext) {
		this.doortext = doortext;
	}
	public String getDoorkey() {
		return doorkey;
	}
	public void setDoorkey(String doorkey) {
		this.doorkey = doorkey;
	}


}
