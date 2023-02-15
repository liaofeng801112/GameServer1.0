package org.come.action.friend;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.FriendResultBean;
import org.come.entity.Friend;
import org.come.entity.Friendtable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 添加好友，返回好友列表
 * @author Administrator
 *
 */
public class AddFriendAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 接收客户端发来的信息
		Friend friend = GsonUtil.getGsonUtil().getgson().fromJson(message, Friend.class);
		// 添加好友
		boolean isSuccess = AllServiceUtil.getFriendService().addFriend(friend);
		if(isSuccess){
				// 获取角色ID，查找角色好友
				List<Friendtable> friendtables = AllServiceUtil.getFriendtableService().selectFriendsByID(friend.getRoleid());
				FriendResultBean friendResultBean=new FriendResultBean();
				friendResultBean.setFriendtables(friendtables);				
				// 返回客户端好友信息
				String msg = Agreement.getAgreement().friendAgreement(GsonUtil.getGsonUtil().getgson().toJson(friendResultBean));
				SendMessage.sendMessageToSlef(ctx, msg);
		}
	}

}
