package org.come.bean;

public class Coordinates {

	private int mapID;
	private int x;
	private int y;
	
	public Coordinates(int mapID, int x, int y) {
		super();
		this.mapID = mapID;
		this.x = x;
		this.y = y;
	}
	public int getMapID() {
		return mapID;
	}
	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
