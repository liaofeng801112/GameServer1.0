package come.tool.Role;

import java.math.BigDecimal;

public class RoleCard {
    //角色id
	private BigDecimal roleId;
	//角色名
	private String roleName;
	//用户名
	private String userName;
	
	public RoleCard(BigDecimal roleId, String roleName, String userName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.userName = userName;
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
