package come.tool.PK;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
/**取消约战*/
public class RefusechalgAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx,String message) {
		// TODO Auto-generated method stub
		LoginResult login=GameServer.getAllLoginRole().get(ctx);
		if (login==null) {
			return;
		}
		PkMatch pkMatch=PKPool.getPkPool().seekPkMatch(login.getRole_id());
		if (pkMatch!=null) {
			PKPool.getPkPool().cancelPkMatch(pkMatch);
		}
	}
}
