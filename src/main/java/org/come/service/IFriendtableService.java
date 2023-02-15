package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Friendtable;

public interface IFriendtableService {
	// 根据角色ID查找该角色的好友信息
	List<Friendtable> selectFriendsByID( BigDecimal roleid );
	// 查找所有好友信息
	List<Friendtable> selectAllFriend();
}
