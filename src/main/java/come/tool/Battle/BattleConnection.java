package come.tool.Battle;

import java.util.List;

import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingManData;

/**
 * 用于战斗重连和观战
 * @author Administrator
 */
public class BattleConnection {
	//播放数据
	private List<FightingManData> datas;
	//播放指令
	private List<FightingEvents> playeEvents;	
	//战斗状态
	private int state;
	//战斗时间戳
	private long time;
	//指令状态
	private int eventState;
	//回合数
	private int Round;
	/**战斗编号*/
	public int FightingNumber;
	/**战斗类型*/
	public int BattleType;
	/**buff数据*/
	public String buff;

	public List<FightingManData> getDatas() {
		return datas;
	}
	public void setDatas(List<FightingManData> datas) {
		this.datas = datas;
	}
	public List<FightingEvents> getPlayeEvents() {
		return playeEvents;
	}
	public void setPlayeEvents(List<FightingEvents> playeEvents) {
		this.playeEvents = playeEvents;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getEventState() {
		return eventState;
	}
	public void setEventState(int eventState) {
		this.eventState = eventState;
	}
	public int getRound() {
		return Round;
	}
	public void setRound(int round) {
		Round = round;
	}
	public int getFightingNumber() {
		return FightingNumber;
	}
	public void setFightingNumber(int fightingNumber) {
		FightingNumber = fightingNumber;
	}
	public int getBattleType() {
		return BattleType;
	}
	public void setBattleType(int battleType) {
		BattleType = battleType;
	}
	public String getBuff() {
		return buff;
	}
	public void setBuff(String buff) {
		this.buff = buff;
	}
}


