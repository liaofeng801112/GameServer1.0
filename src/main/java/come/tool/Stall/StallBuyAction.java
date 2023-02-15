package come.tool.Stall;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class StallBuyAction implements IAction{
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
//		if (true) {
//        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("禁止物品流通")); 
//			return;
//		}
		try {
			StallBuy stallBuy = GsonUtil.getGsonUtil().getgson().fromJson(message,StallBuy.class);
			StallPool.getPool().BuyStall(GameServer.getAllLoginRole().get(ctx), stallBuy);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
