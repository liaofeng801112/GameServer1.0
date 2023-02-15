package come.tool.Scene.PKLS;

import java.math.BigDecimal;

public class LSTeam {

	//队伍id集合
	private BigDecimal[] roleids;

	public LSTeam(BigDecimal[] roleids) {
		super();
		this.roleids = roleids;
	}
	/**判断是否包含角色id*/
	public boolean contains(BigDecimal roleid){
		for (int i = 0; i < roleids.length; i++) {
			if (roleids[i].compareTo(roleid)==0) {
				return true;
			}
		}
		return false;
	}
	public BigDecimal[] getRoleids() {
		return roleids;
	}

	public void setRoleids(BigDecimal[] roleids) {
		this.roleids = roleids;
	}
}
