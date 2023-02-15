package come.tool.teamArena;

import come.tool.newTeam.TeamBean;

public class TeamArenaGroup {
	private TeamBean team1;
	private TeamBean team2;
	private long time;
	public TeamArenaGroup(TeamBean team1, TeamBean team2,long time) {
		super();
		this.team1 = team1;
		this.team2 = team2;
		this.time=time;
	}
	public TeamBean getTeam1() {
		return team1;
	}
	public void setTeam1(TeamBean team1) {
		this.team1 = team1;
	}
	public TeamBean getTeam2() {
		return team2;
	}
	public void setTeam2(TeamBean team2) {
		this.team2 = team2;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
