package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.TBean;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**队伍申请*/
public class TeamApplyListAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
        LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		TeamBean bean=TeamUtil.getTeam(loginResult.getTroop_id());
		if (bean!=null&&bean.isCaptian(loginResult.getRole_id())) {
			TBean<List<TeamRole>> tBean=new TBean<List<TeamRole>>(bean.getApplyTeams());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().team6Agreement(GsonUtil.getGsonUtil().getgson().toJson(tBean)));
		}
	}

}
