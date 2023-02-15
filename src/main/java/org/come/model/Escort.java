package org.come.model;

import java.util.List;

/**护送*/
public class Escort {
	private int id;//护送ID;
	private int type;//护送类型
	private List<EscortPath> escortPaths;//护送路径
	private List<EscortEvents> escortEvents;//突发事件
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<EscortPath> getEscortPaths() {
		return escortPaths;
	}
	public void setEscortPaths(List<EscortPath> escortPaths) {
		this.escortPaths = escortPaths;
	}
	public List<EscortEvents> getEscortEvents() {
		return escortEvents;
	}
	public void setEscortEvents(List<EscortEvents> escortEvents) {
		this.escortEvents = escortEvents;
	}
	
}
