package come.tool.Scene.SLDH;

import java.math.BigDecimal;

import come.tool.Role.RoleShow;

/**玩家信息*/
public class SLDHRole {

	private BigDecimal roleID;
	private RoleShow roleShow;
	private SLDHTeam sldhTeam;//所在队伍
	private int I;//奖励领取0未领取
	
	public SLDHRole(BigDecimal roleID, RoleShow roleShow) {
		super();
		this.roleID = roleID;
		this.roleShow = roleShow;
		this.I = 0;
	}
	public BigDecimal getRoleID() {
		return roleID;
	}
	public void setRoleID(BigDecimal roleID) {
		this.roleID = roleID;
	}
	public RoleShow getRoleShow() {
		return roleShow;
	}
	public void setRoleShow(RoleShow roleShow) {
		this.roleShow = roleShow;
	}
	public SLDHTeam getSldhTeam() {
		return sldhTeam;
	}
	public void setSldhTeam(SLDHTeam sldhTeam) {
		this.sldhTeam = sldhTeam;
	}
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
	
}
