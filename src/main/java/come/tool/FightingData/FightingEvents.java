package come.tool.FightingData;

import java.util.List;

/**
 * 战斗事件
 * @author Administrator
 *
 */
public class FightingEvents {
    //发起人
	private FightingState Originator;
	// 接受人集合
	private List<FightingState> Accepterlist;
    //当前回合
	private int CurrentRound;
	//战斗编号
	private int Fightingsum;
	public FightingState getOriginator() {
		return Originator;
	}
	


	public void setOriginator(FightingState originator) {
		Originator = originator;
	}

	public List<FightingState> getAccepterlist() {
		return Accepterlist;
	}

	public void setAccepterlist(List<FightingState> accepterlist) {
		Accepterlist = accepterlist;
	}
	public int getCurrentRound() {
		return CurrentRound;
	}
	public void setCurrentRound(int currentRound) {
		CurrentRound = currentRound;
	}
	public int getFightingsum() {
		return Fightingsum;
	}
	public void setFightingsum(int fightingsum) {
		Fightingsum = fightingsum;
	}
	
	
	
	
}
