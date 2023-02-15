package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**招募信息*/
public class TeamEnlistAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		if (message.length()!=0) {
			LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
			TeamBean bean=TeamUtil.getTeam(loginResult.getTroop_id());
			if (bean!=null&&bean.isCaptian(loginResult.getRole_id())) {
				TeamBean teamBean=GsonUtil.getGsonUtil().getgson().fromJson(message,TeamBean.class);
				bean.upEnlist(teamBean);	
				TeamUtil.addEnlist(bean);
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("招募发布成功"));
			}	
		}else {
			SendMessage.sendMessageToSlef(ctx,TeamUtil.getEnlist());
		}
		
	}

}
