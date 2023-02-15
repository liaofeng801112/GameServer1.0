package come.tool.Scene.RC;

import org.come.model.Boos;
import org.come.model.Robots;

public class BBRecord {

	private String[] teams;
	private int size;
	private int BBNum;//宝宝副本胜利次数
	private transient Robots BBrobots;
	private transient Boos BBboos;
	public String getMsg(){		
		if (teams==null) {
			return "还没有挑战记录";
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("当前最高记录:");
		for (int i = 0; i < teams.length; i++) {
			if (i!=0) {buffer.append(",");}
			buffer.append(teams[i]);
		}
		buffer.append("。该队伍连胜次数为");
		buffer.append(size);
		return buffer.toString();	
	}
	public synchronized void UPRecord(int v,String[] names){
		BBNum+=names.length;
		if (v>size) {size=v;teams=names;}
	}
	public String[] getTeams() {
		return teams;
	}
	public void setTeams(String[] teams) {
		this.teams = teams;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getBBNum() {
		return BBNum;
	}
	public void setBBNum(int bBNum) {
		BBNum = bBNum;
	}
	public Robots getBBrobots() {
		return BBrobots;
	}
	public void setBBrobots(Robots bBrobots) {
		BBrobots = bBrobots;
	}
	public Boos getBBboos() {
		return BBboos;
	}
	public void setBBboos(Boos bBboos) {
		BBboos = bBboos;
	}
	/**根据挑战完成次数获取比斗奖章的积分*/
	public String getReward(int v){
		int jf=v*(28+(v/4>=10?10:v/4));
		return "比斗奖章="+jf;
	}
}
