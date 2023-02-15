package come.tool.Stall;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
public class StallAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		try {
			Stall stall = GsonUtil.getGsonUtil().getgson().fromJson(message, Stall.class);
			if (stall.getState()==StallPool.PREPARE) {
				stall.setState(StallPool.OFF);
				//把资产隔离
				LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
				stall.setRoleid(loginResult.getRole_id());
				stall.setRole(loginResult.getRolename());
				StallPool.getPool().addStall(stall,ctx);	
				loginResult.setBooth_id(new BigDecimal(stall.getId()));
			}else if (stall.getState()==StallPool.NO) {
				//收摊了
				LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
				loginResult.setBooth_id(null);
				StallPool.getPool().RetreatStall(stall.getId());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
