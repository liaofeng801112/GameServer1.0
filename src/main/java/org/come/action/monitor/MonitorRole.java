package org.come.action.monitor;

public class MonitorRole {
	
	private int BSum;//战斗次数
	private int XSum;//驯养次数
	private int KSum;//科技领取次数
	private long addBY;//绑玉获得数量
	
	public int addBSum() {
		return BSum++;
	}
	public void addXSum() {
		XSum++;
	}
	public void addKSum() {
		KSum++;
	}
	public int getXSum() {
		return XSum;
	}
	public int getKSum() {
		return KSum;
	}
	public long getAddBY() {
		return addBY;
	}
	public void setAddBY(long addBY) {
		this.addBY = addBY;
	}
	public int getBSum() {
		return BSum;
	}
}
