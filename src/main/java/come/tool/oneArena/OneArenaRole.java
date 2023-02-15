package come.tool.oneArena;

import java.math.BigDecimal;

import org.come.bean.LoginResult;

public class OneArenaRole {
	private BigDecimal roleId;//角色id
	private int place;//名次
	private String skin;//头像路径
	private String name;//名称
	private int lvl;//等级
	public OneArenaRole() {
		// TODO Auto-generated constructor stub
	}
	public OneArenaRole(LoginResult role) {
		// TODO Auto-generated constructor stub
		this.roleId=role.getRole_id();
		this.skin=role.getSpecies_id().toString();
		this.name=role.getRolename();
		this.lvl=role.getGrade().intValue();
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
}
