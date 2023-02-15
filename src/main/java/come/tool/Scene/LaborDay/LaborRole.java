package come.tool.Scene.LaborDay;

import java.math.BigDecimal;

import org.come.bean.LoginResult;

public class LaborRole {
	private BigDecimal roleId;
	private String name;
	private int lvl;
	private int cz;//充值金额
	private int czcj;//已经仙玉抽奖次数的次数
	private int cj;//抽奖次数
	public LaborRole(LoginResult loginResult) {
		// TODO Auto-generated constructor stub
		this.roleId=loginResult.getRole_id();
		this.name=loginResult.getRolename();
		this.lvl=loginResult.getGrade().intValue();
	}
	/**获取剩余抽奖次数*/
	public int getCZCJNum() {		
		return (cz/LaborScene.MONEY)-czcj;
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
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getCz() {
		return cz;
	}
	public void setCz(int cz) {
		this.cz = cz;
	}
	public int getCj() {
		return cj;
	}
	public void setCj(int cj) {
		this.cj = cj;
	}
	public int getCzcj() {
		return czcj;
	}
	public void setCzcj(int czcj) {
		this.czcj = czcj;
	}
}
