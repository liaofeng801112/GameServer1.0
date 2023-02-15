package org.come.action.friend;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.FriendResultBean;
import org.come.entity.Friendtable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 查询视图，返回角色好友列表
 * @author Administrator
 *
 */
public class FriendAction implements IAction{
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
			// 获取角色ID，查找角色好友
			BigDecimal roleid = new BigDecimal(message);
			List<Friendtable> friendtables = AllServiceUtil.getFriendtableService().selectFriendsByID(roleid);
			// 判断好友是否在线
			for (Friendtable friendtable : friendtables) {
				if( GameServer.getRoleNameMap().get(friendtable.getRolename()) != null ){
					friendtable.setOnlineState(1);
				}
			}
			FriendResultBean friendResultBean=new FriendResultBean();
			friendResultBean.setFriendtables(friendtables);		
			// 返回客户端
			String msg = Agreement.getAgreement().friendAgreement(GsonUtil.getGsonUtil().getgson().toJson(friendResultBean));
			SendMessage.sendMessageToSlef(ctx, msg);
	}

}
