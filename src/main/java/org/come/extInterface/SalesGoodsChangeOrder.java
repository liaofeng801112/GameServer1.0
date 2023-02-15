package org.come.extInterface;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.extInterBean.SalesGoodsChangeOrderReqBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 进行物品状态的更改
 * 
 * @author Administrator
 * 
 */
public class SalesGoodsChangeOrder implements IAction {

	/**
	 * redis状态操作
	 */
	// private Jedis jedis;

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		SalesGoodsChangeOrderReqBean request = GsonUtil.getGsonUtil().getgson().fromJson(message, SalesGoodsChangeOrderReqBean.class);
		// 商品ID
		String Sale_id = request.getSale_id();
		String type = request.getType();

		String Sale_id_statues = "Sale_id_statues=error";
		// 更改状态
		if ((Sale_id != null) && (type != null)) {
			AllServiceUtil.getSalegoodsService().updateFlag(new BigDecimal(Sale_id), Integer.valueOf(type));
			Sale_id_statues = "Sale_id_statues=success";
		}

		// 返回结果
		String msg = Agreement.getAgreement().salesGoodsChangeOrderAgreement(Sale_id_statues);
		SendMessage.sendMessageToSlef(ctx, msg);

	}
}
