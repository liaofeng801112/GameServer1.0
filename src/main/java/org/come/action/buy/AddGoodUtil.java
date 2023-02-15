package org.come.action.buy;

import io.netty.channel.ChannelHandlerContext;

import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

/**
 * 封装的添加单个物品
 * @author Administrator
 */
public class AddGoodUtil {

	public static void addGood(ChannelHandlerContext ctx,Goodstable goodstable){
		if (goodstable==null)return;
		String msg = Agreement.getAgreement().AddGood(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
