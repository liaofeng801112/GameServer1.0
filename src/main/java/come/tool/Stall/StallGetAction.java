package come.tool.Stall;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

public class StallGetAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		int id=Integer.parseInt(message);
		Stall stall=StallPool.getPool().getAllStall().get(id);
		if (stall!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().stallAgreement(GsonUtil.getGsonUtil().getgson().toJson(stall)));
		}
	}

}
