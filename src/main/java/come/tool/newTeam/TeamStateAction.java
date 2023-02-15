package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Role.RolePool;
/**队伍状态*/
public class TeamStateAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		//L(离队) R(归队) C玩家id(召回)
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		TeamBean bean=TeamUtil.getTeam(loginResult.getTroop_id());
		if (bean==null) {
			return;
		}
		if (message.startsWith("L")) {//离队
			if (bean.isCaptian(loginResult.getRole_id())) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你是队长无法离队"));
				return;
			}
			TeamRole teamRole=bean.getTeamRole(loginResult.getRole_id());
			if (teamRole==null) {return;}
			if (teamRole.getState()!=0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经是离队状态"));
				return;
			}
			//离队流程
			bean.stateLeave(teamRole, -1);
		}else if (message.startsWith("R")) {//归队
			if (bean.isCaptian(loginResult.getRole_id())) {return;}
			TeamRole teamRole=bean.getTeamRole(loginResult.getRole_id());
			if (teamRole==null) {return;}
			if (teamRole.getState()==0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已在队伍中"));
				return;
			}
			String teamName=bean.getTeamName();
			ChannelHandlerContext tCtx=GameServer.getRoleNameMap().get(teamName);
			LoginResult login=tCtx!=null?GameServer.getAllLoginRole().get(tCtx):null;
			if (login==null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("队长不在线"));
			}else if (login.getMapid().longValue()!=loginResult.getMapid().longValue()) {//开始寻路
				StringBuffer buffer=new StringBuffer();
				buffer.append("你的队长在#G");
				buffer.append(GameServer.getMapName(login.getMapid().toString()));
				buffer.append("#Y,只有与队长同地图才能归队");
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(buffer.toString()));
				buffer.setLength(0);
				buffer.append("你的队员");
				buffer.append(loginResult.getRolename());
				buffer.append("想要回归队伍,还是等等他吧");
				SendMessage.sendMessageToSlef(tCtx,Agreement.getAgreement().PromptAgreement(buffer.toString()));
			}else {//归队流程
				bean.stateCome(teamRole);
			}
		}else if (message.startsWith("C")) {//玩家id
			BigDecimal roleId=new BigDecimal(message.substring(1));
			LoginResult login=RolePool.getLoginResult(roleId);
			ChannelHandlerContext tCtx=login!=null?GameServer.getRoleNameMap().get(login.getRolename()):null;
			if (tCtx!=null) {
				StringBuffer buffer=new StringBuffer();
				buffer.append("你的队长在#G");
				buffer.append(GameServer.getMapName(loginResult.getMapid().toString()));
				buffer.append("#Y召你归队");
				SendMessage.sendMessageToSlef(tCtx,Agreement.getAgreement().PromptAgreement(buffer.toString()));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("已向对方发出召集"));
			}else {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("对方不在线"));
			}
		}
	}

}
