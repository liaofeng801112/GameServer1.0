package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.Record;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
/**
 * 账号封号
 * @author Administrator
 *
 */
public class AccountStopAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		GameServer.userDown(ctx);
		// 断开连接
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().serverstopAgreement());
		// 获取账号名
		UserTable table = new UserTable();
		table.setUsername(message);
		table.setActivity((short)1);
		// 修改用户信息
		AllServiceUtil.getUserTableService().updateUser(table);
		AllServiceUtil.getRecordService().insert(new Record(5,message));
	}

}
