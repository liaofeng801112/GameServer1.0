package come.tool.Transplant;

import java.util.List;

import org.come.entity.UserTable;

public class UserDataBean {

	private UserTable userTable;
	
	private List<RoleDataBean> roleDataBeans;
	
	public UserDataBean(UserTable userTable, List<RoleDataBean> roleDataBeans) {
		super();
		this.userTable = userTable;
		this.roleDataBeans = roleDataBeans;
	}

	public UserTable getUserTable() {
		return userTable;
	}

	public void setUserTable(UserTable userTable) {
		this.userTable = userTable;
	}

	public List<RoleDataBean> getRoleDataBeans() {
		return roleDataBeans;
	}

	public void setRoleDataBeans(List<RoleDataBean> roleDataBeans) {
		this.roleDataBeans = roleDataBeans;
	}
	
}
