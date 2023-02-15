package org.come.model;

public class aCard {

	// id
	private int id;
	// 类型 0是大话币购买 1仙玉
	private int type;
	// 皮肤
	private String skin;
	// 卡类型 强法 抗性 物理
	private String gn;
	// 卡片类型 0普通 1珍惜 2神兽
	private int cardType;
	// 名称
	private String name;
	// 时间 单位分
	private int time;
	// 消耗金钱
	private int money;
	// 属性
	private String value;
	//造型皮肤
	private String zskin;
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

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getGn() {
		return gn;
	}

	public void setGn(String gn) {
		this.gn = gn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public String getZskin() {
		return zskin;
	}

	public void setZskin(String zskin) {
		this.zskin = zskin;
	}
	 
}
