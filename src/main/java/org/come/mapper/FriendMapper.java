package org.come.mapper;


import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Friend;
/**
 * 好友表操作
 * @author Administrator
 *
 */
@MyBatisAnnotation
public interface FriendMapper {
	// 添加好友
	boolean addFriend( Friend friend );
	// 删除好友
	void deleteFriend( Friend friend );

	List<Friend> allFriend();
}
