package come.tool.Scene.SLDH;

/**分组情况*/
public class SLDHGroup {

	private SLDHTeam team1;
	private SLDHTeam team2;
	//战况 0 无   1 战斗中      2 一队胜利  3二队胜利
	private int state;
	private int fightId;//战斗id
	public SLDHGroup(SLDHTeam team1, SLDHTeam team2) {
		super();
		this.team1 = team1;
		this.team2 = team2;
	}
	public SLDHTeam getTeam(boolean is) {
		if (is) {
			return state==2?team1:team2;
		}else {
			return state==2?team2:team1;
		}
	}
	public SLDHTeam getTeam1() {
		return team1;
	}
	public void setTeam1(SLDHTeam team1) {
		this.team1 = team1;
	}
	public SLDHTeam getTeam2() {
		return team2;
	}
	public void setTeam2(SLDHTeam team2) {
		this.team2 = team2;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getFightId() {
		return fightId;
	}
	public void setFightId(int fightId) {
		this.fightId = fightId;
	}
	
}
