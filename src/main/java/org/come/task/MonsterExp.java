package org.come.task;

import org.come.server.GameServer;

import come.tool.Good.DropModel;

public class MonsterExp {
	private int maxSize;//最大次数
	private long exp;//每次累加经验
	private int size;
	public MonsterExp(int maxSize, long exp) {
		super();
		this.maxSize = maxSize;
		this.exp = exp;
	}
	public String addEXP(MapMonsterBean bean,String name){
		if (size<maxSize) {size++;}
		StringBuffer buffer=new StringBuffer();
		buffer.append("#G");
		buffer.append(bean.getRobotname());
		buffer.append("#Y在#c00FFFF");
		buffer.append(GameServer.getMapName(bean.getMap()+""));
		buffer.append("(");
		buffer.append(bean.getX()/20);
		buffer.append(",");
		buffer.append(bean.getY()/20);
		buffer.append(")击杀#R");
		buffer.append(name);
		buffer.append("#Y为首的队伍。积累");
		buffer.append(exp*size/10000);
		buffer.append("万经验,等待其他队伍来战");
		return buffer.toString();
	}
	/**获取*/
	public DropModel getDropModel(int sum){
		if (size==0) {return null;}
		return new DropModel("经验="+(exp*size/sum));
	}
	public int getMaxSize() {
		return maxSize;
	}
	public long getExp() {
		return exp;
	}
}
