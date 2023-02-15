package org.come.bean;

import java.util.List;

import org.come.entity.Friendtable;
/**
 * 点击好友返回的bean
 * @author 叶豪芳
 *
 */
public class FriendResultBean {
	// 返回的角色列表
	private List<Friendtable> friendtables;

	public List<Friendtable> getFriendtables() {
		return friendtables;
	}

	public void setFriendtables(List<Friendtable> friendtables) {
		this.friendtables = friendtables;
	}
	
	

}
