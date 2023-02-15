package org.come.service;


import java.util.List;

import org.come.entity.Friend;

public interface IFriendService {
	// 添加好友
	boolean addFriend( Friend friend );
	// 删除好友
	void deleteFriend( Friend friend );
	
	List<Friend> allFriend();
}
