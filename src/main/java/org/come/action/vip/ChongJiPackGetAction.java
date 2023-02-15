package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.entity.ChongjipackBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

/** 实例化冲级礼包 */
public class ChongJiPackGetAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		if (message == null || "".equals(message)) {
			return;
		}
		// 实例化冲级礼包列表   ( type表示等级 1免费 , 2小资 , 3土豪 )
		List<ChongjipackBean> chongjipack = GameServer.getPackgradecontrol().get(Integer.valueOf(message));
		String mes = GsonUtil.getGsonUtil().getgson().toJson(chongjipack);
		// 将信息返回给前端
		String sendmes = Agreement.getAgreement().chongjipackgetAgreement(mes);
		SendMessage.sendMessageToSlef(ctx, sendmes);
	}

}
