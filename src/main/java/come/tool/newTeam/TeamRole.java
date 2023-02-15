package come.tool.newTeam;

import java.math.BigDecimal;

import org.come.bean.LoginResult;

public class TeamRole {

	private BigDecimal roleId;//角色id
	private String name;//角色名称
	private BigDecimal speciesId;//角色种类id
	private int grade;//角色等级
	private int state;//状态1队长 0队员  -1暂离  -2离线   -3离开队伍
	public TeamRole() {
		// TODO Auto-generated constructor stub
	}
	public TeamRole(LoginResult loginResult) {
		// TODO Auto-generated constructor stub
		this.roleId=loginResult.getRole_id();
		this.name=loginResult.getRolename();
		this.speciesId=loginResult.getSpecies_id();
		this.grade=loginResult.getGrade();
		this.state=0;
	}
	public void upTeamRole(LoginResult loginResult) {
		this.name=loginResult.getRolename();
		this.speciesId=loginResult.getSpecies_id();
		this.grade=loginResult.getGrade();
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(BigDecimal speciesId) {
		this.speciesId = speciesId;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
