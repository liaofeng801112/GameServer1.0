package come.tool.newTrans;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class TransGoodAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult login = GameServer.getAllLoginRole().get(ctx);
		if (login==null) {return;}
		GoodTrans2 goodTrans2= GsonUtil.getGsonUtil().getgson().fromJson(message, GoodTrans2.class);
		TransUtil.TransGood(login.getRolename(), goodTrans2);
	}

}
