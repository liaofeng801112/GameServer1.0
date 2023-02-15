package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.entity.ChongjipackBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

/** 每日充值实例化 */
public class PayDayGradePayAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 每日充值列表
		List<ChongjipackBean> chongjipack = GameServer.getPackgradecontrol().get(4);
		String mes = GsonUtil.getGsonUtil().getgson().toJson(chongjipack);
		// 将信息返回给前端
		String sendmes = Agreement.getAgreement().PaydaygradepayAgreement(mes);
		SendMessage.sendMessageToSlef(ctx, sendmes);
	}

}
