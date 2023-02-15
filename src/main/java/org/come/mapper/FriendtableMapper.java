package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Friendtable;
/**
 * 查找角色好友
 * @author 叶豪芳
 *
 */
@MyBatisAnnotation
public interface FriendtableMapper {
	// 根据角色ID查找该角色的好友信息
	List<Friendtable> selectFriendsByID( BigDecimal roleid );

	// 查找所有好友信息
	List<Friendtable> selectAllFriend();
}
