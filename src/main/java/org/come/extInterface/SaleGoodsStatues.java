package org.come.extInterface;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;

/**
 * 进行salegoods 物品进行
 * 
 * @author Administrator
 * 
 */
public class SaleGoodsStatues implements IAction {

	// 获取jedis 存储对象
	// private Jedis jedis;

	@Override
	public void action(ChannelHandlerContext ctx, String Sale_id) {
		// TODO Auto-generated method stub
		// 返回状态
		String Type = "Type=4";
		// 请求商品状态
		if (Sale_id != null) {
			Integer flag = AllServiceUtil.getSalegoodsService().selectFlag(new BigDecimal(Sale_id));
			if (flag != null) {
				if (flag == 1) {
					Type = "Type=1";
				} else if (flag == 2) {
					Type = "Type=2";
				} else if (flag == 3) {
					Type = "Type=3";
				}
			} else {
				Type = "Type=4";
			}
		}

		// 返回
		String msg = Agreement.getAgreement().saleGoodsStatuesAgreement(Type);
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
