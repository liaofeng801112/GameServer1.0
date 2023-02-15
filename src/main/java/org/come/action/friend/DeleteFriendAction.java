package org.come.action.friend;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.Friend;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 删除好友
 * @author 叶豪芳
 *
 */
public class DeleteFriendAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {	
		// 获取需要删除的好友
		Friend friend = GsonUtil.getGsonUtil().getgson().fromJson(message, Friend.class);
		// 删除好友
		AllServiceUtil.getFriendService().deleteFriend(friend);
	}

}
