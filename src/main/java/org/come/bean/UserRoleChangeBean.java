package org.come.bean;

import java.util.List;

import org.come.entity.RoleTable;

/**
 * 用户角色迁移Bean
 * <p>
 * Title : UserRoleChangeBean
 * </p>
 * 
 * @author : HGC
 * @date : 2019年10月11日 下午3:16:59
 * @version : 1.0.0
 */
public class UserRoleChangeBean {

	// 被迁移的角色信息
	private List<RoleTable> roleList;
	// 迁移的角色信息
	private List<RoleTable> otherRoleList;

	public UserRoleChangeBean() {
		super();
	}

	public UserRoleChangeBean(List<RoleTable> roleList, List<RoleTable> otherRoleList) {
		super();
		this.roleList = roleList;
		this.otherRoleList = otherRoleList;
	}

	public List<RoleTable> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleTable> roleList) {
		this.roleList = roleList;
	}

	public List<RoleTable> getOtherRoleList() {
		return otherRoleList;
	}

	public void setOtherRoleList(List<RoleTable> otherRoleList) {
		this.otherRoleList = otherRoleList;
	}

}
