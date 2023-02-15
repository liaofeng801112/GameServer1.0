package org.come.action.lottery;

import org.come.server.GameServer;

public class DrawBase {

	private int[] ids;
	private String[] idBooss;//放妖用的
	private int sum;
	private double v;
	public int getId() {
		if (ids.length==1) {return ids[0];}
		return ids[GameServer.random.nextInt(ids.length)];
	}
	public String getDropId() {
		if (ids!=null) {
			if (ids.length==1) {return ids[0]+"";}
			return ids[GameServer.random.nextInt(ids.length)]+"";	
		}else if (idBooss!=null) {
			if (idBooss.length==1) {return idBooss[0];}
			return idBooss[GameServer.random.nextInt(idBooss.length)];	
		}
		return null;
	}
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public String[] getIdBooss() {
		return idBooss;
	}
	public void setIdBooss(String[] idBooss) {
		this.idBooss = idBooss;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public double getV() {
		return v;
	}
	public void setV(double v) {
		this.v = v;
	}
	
}
