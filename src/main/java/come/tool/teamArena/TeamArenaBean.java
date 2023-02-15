package come.tool.teamArena;

import java.util.List;

import come.tool.newTeam.TeamRole;

public class TeamArenaBean {
	private List<TeamRole> team1;//队伍一数据
	private List<TeamRole> team2;//队伍二数据
	public List<TeamRole> getTeam1() {
		return team1;
	}
	public void setTeam1(List<TeamRole> team1) {
		this.team1 = team1;
	}
	public List<TeamRole> getTeam2() {
		return team2;
	}
	public void setTeam2(List<TeamRole> team2) {
		this.team2 = team2;
	}
}
