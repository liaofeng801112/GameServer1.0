package org.come.action.baby;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
/**产生宝宝*/
public class BabyBornAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 修改角色待产倒计时
		GameServer.getAllLoginRole().get(ctx).setHavebaby(null);
		// 提示对方抚养权
		String msg = Agreement.getAgreement().BabyCustodayAgreement("抚养");
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
