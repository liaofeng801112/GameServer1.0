package org.come.model;

public class ActiveBase {

	private int id;
	private String aName;//活动名称
	private String skin;//活动图标
	private String head;//标题
	private int sid;//绑定的数据id
	private int num;//完成的次数
	private int value;//单次活跃数值
	private String guide;//引导位置   地图id-x-y-npcId
	private String text;//介绍
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getaName() {
		return aName;
	}
	public void setaName(String aName) {
		this.aName = aName;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
