package org.come.action.buy;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.model.Shop;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

public class ShopPriceAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		//0表示商城 1npc商店  3限购
//		前端发送    商品类型|商品id
//      返回后端    商品类型|商品id|价格
		String[] v=message.split("\\|");
		int type=Integer.parseInt(v[0]);
		BigDecimal sid=new BigDecimal(v[1]);
		if (type==0) {//商城
			
		}else if (type==1) {//NPC商店
			Shop shop = GameServer.getAllShopGoods().get(sid.toString());
			if (shop!=null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().ShopPriceAgreement(message+"|"+shop.getPrice()));
			}
		}
	}
}
