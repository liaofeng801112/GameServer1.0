package org.come.serviceImpl;

import java.util.List;

import org.come.entity.Friend;
import org.come.mapper.FriendMapper;
import org.come.service.IFriendService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class FriendServiceImpl implements IFriendService{
	private FriendMapper friendMapper;
	
	public FriendServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		friendMapper = (FriendMapper)ctx.getBean("friendMapper");
	}

	// 添加好友
	@Override
	public boolean addFriend( Friend friend ) {
		boolean isSuccess = friendMapper.addFriend(friend);
		return isSuccess;
	}

	// 删除好友
	@Override
	public void deleteFriend(Friend friend) {

		friendMapper.deleteFriend(friend);
		
	}

	@Override
	public List<Friend> allFriend() {
		// TODO Auto-generated method stub
		return friendMapper.allFriend();
	}

}
