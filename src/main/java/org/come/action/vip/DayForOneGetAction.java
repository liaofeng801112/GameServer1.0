package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.ChongjipackBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

/** 每日特惠 */
public class DayForOneGetAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 三端 每日特惠列表
		List<ChongjipackBean> chongjipack = GameServer.getPackgradecontrol()
				.get(6);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(d);
		for (int i = 0; i < chongjipack.size(); i++) {
			if (format.equals(chongjipack.get(i).getHuitime())) {
				// 将信息返回给前端
				String sendmes = Agreement.getAgreement()
						.DayforonegetAgreement(
								GsonUtil.getGsonUtil().getgson()
										.toJson(chongjipack.get(i)));
				SendMessage.sendMessageToSlef(ctx, sendmes);
				break;
			}
		}
	}

}
