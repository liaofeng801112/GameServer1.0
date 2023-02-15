package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleConnection;
import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.PK.PKPool;
import come.tool.PK.PkMatch;

/**
 * 战斗重连
 * @author Administrator
 *
 */
public class FightbattleConnectionAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		PkMatch match=PKPool.getPkPool().seekPkMatch(loginResult.getRole_id());
		if (match!=null) {SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("携带战书没法进入观战"));return;}
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return;}
		int fight=Integer.parseInt(message);
		if (fight==-1) {
			BattleData battleData=BattleThreadPool.BattleDatas.get(loginResult.getFighting());
			BattleThreadPool.sendBattleState3(0, teams);
			BattleConnection battleConnection=new BattleConnection();
			battleConnection.setFightingNumber(-1);
			String mes=Agreement.getAgreement().battleConnectionAgreement(GsonUtil.getGsonUtil().getgson().toJson(battleConnection));
			for (int i = 0; i < teams.length; i++) {
				if (battleData!=null){battleData.getParticipantlist().remove(teams[i]);}	
				SendMessage.sendMessageByRoleName(teams[i],mes);	
			}
		}else {
			BattleThreadPool.addConnection(fight,teams);		
		}
	}

}
