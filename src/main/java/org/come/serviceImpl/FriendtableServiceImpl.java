package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Friendtable;
import org.come.mapper.FriendtableMapper;
import org.come.service.IFriendtableService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class FriendtableServiceImpl implements IFriendtableService{
	
	private FriendtableMapper friendtableMapper;
	
	public FriendtableServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		friendtableMapper = (FriendtableMapper)ctx.getBean("friendtableMapper");
	}

	/**
	 * 根据角色ID查找角色好友
	 */
	@Override
	public List<Friendtable> selectFriendsByID( BigDecimal roleid ) {
		
		List<Friendtable> friendtables = friendtableMapper.selectFriendsByID(roleid);
		return friendtables;
	
	}

	@Override
	public List<Friendtable> selectAllFriend() {
		return friendtableMapper.selectAllFriend();
	}

}
