package come.tool.FightingData;


/**
 * 战斗响应
 * @author Administrator
 *
 */
public class FightingResponse {

	// 战斗编号
	private int FightingNumber;
	//玩家数据以及召唤兽数据
	private ManData manData;

	public int getFightingNumber() {
		return FightingNumber;
	}
	public void setFightingNumber(int fightingNumber) {
		FightingNumber = fightingNumber;
	}
	public ManData getManData() {
		return manData;
	}
	public void setManData(ManData manData) {
		this.manData = manData;
	}
	
	
	
}
