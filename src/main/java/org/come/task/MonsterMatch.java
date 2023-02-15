package org.come.task;

import java.util.ArrayList;
import java.util.List;

import org.come.handler.SendMessage;
import org.come.server.GameServer;

/**怪物匹配队列*/
public class MonsterMatch {
	private int count;//原始计数
	private int countDown;//倒计时
	private List<String> matchs;
	private String TS;
	public MonsterMatch() {
		// TODO Auto-generated constructor stub
		this.count=10;
		this.countDown=10;
		this.matchs=new ArrayList<>();
	}
	public MonsterMatch(int countDown) {
		// TODO Auto-generated constructor stub
		this.count=countDown;
		this.countDown=countDown;
		this.matchs=new ArrayList<>();
	}
	public int getCountDown() {
		return countDown;
	}
	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}
	/**随机取出一个玩家*/
	public String getMatch(){
		synchronized (this) {
			int size=matchs.size();
			if (size==0) {return null;}
			return matchs.remove(GameServer.random.nextInt(size));
		}
	}
	/**添加一个*/
	public boolean addMatch(String name){
		synchronized (this) {
			if (matchs.contains(name)) {return false;}
			matchs.add(name);
			return true;
		}
	}
	/**清空*/
	public void clearMatch(){
		synchronized (this) {
			countDown=count;
			matchs.clear();
		}
	}
	/**遍历发送消息*/
	public void sendMatch(){
		if (TS!=null) {
			for (int i =matchs.size()-1;i>=0;i--) {
				SendMessage.sendMessageByRoleName(matchs.get(i),TS);
			}	
		}
	}
	public String getTS() {
		return TS;
	}
	public void setTS(String tS) {
		TS = tS;
	}
}
