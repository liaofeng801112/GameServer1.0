package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**队伍申请*/
public class TeamApplyAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
        LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		TeamBean bean=TeamUtil.getTeam(loginResult.getTroop_id());
		if (bean!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经有队伍了"));
			return;
		}
		
		bean=TeamUtil.getTeamRole(new BigDecimal(message));
		if (bean==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你申请的人还没有队伍"));
			return;
		}
		if (bean.getTeamSize()>=5) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你申请的队伍已满"));
			return;
		}
		//加入申请列表
		if (bean.applyTeam(loginResult.getTeamRole())) {//成功申请
			//发送给队长
			String teamName=bean.getTeamName();
			ChannelHandlerContext tCtx=GameServer.getRoleNameMap().get(teamName);
			if (tCtx!=null) {
				SendMessage.sendMessageToSlef(tCtx,Agreement.getAgreement().team2Agreement(GsonUtil.getGsonUtil().getgson().toJson(loginResult.getTeamRole())));
			}
			//返回提示信息给申请人
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你申请加入#R"+teamName+"#Y队伍"));
		}else {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经在申请列表中"));
		}
	}

}
