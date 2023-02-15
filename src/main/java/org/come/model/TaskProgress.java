package org.come.model;

public class TaskProgress {

	private int sum;//已完成数
	private int max;//需要完成数
	private int type;//类型
	private int DId;//目标id
	private String DName;//目标名称
	//寻路所需位置
	private int map;
	private int x;
	private int y;
	private int GId;//接受id
	private String GName;//接收名称

	public TaskProgress() {
		// TODO Auto-generated constructor stub
	}
	public TaskProgress(TaskProgress progress) {
		super();
		this.sum = progress.sum;
		this.max = progress.max;
		this.type = progress.type;
		this.DId = progress.DId;
		this.DName = progress.DName;
		this.map = progress.map;
		this.x = progress.x;
		this.y = progress.y;
		this.GId = progress.GId;
		this.GName = progress.GName;
	}
	public void addSum(int add){
		sum+=add;
		if (sum>max) {
			sum=max;
		}
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDId() {
		return DId;
	}
	public void setDId(int dId) {
		DId = dId;
	}
	public String getDName() {
		return DName;
	}
	public void setDName(String dName) {
		DName = dName;
	}
	public int getMap() {
		return map;
	}
	public void setMap(int map) {
		this.map = map;
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
	public int getGId() {
		return GId;
	}
	public void setGId(int gId) {
		GId = gId;
	}
	public String getGName() {
		return GName;
	}
	public void setGName(String gName) {
		GName = gName;
	}
}
