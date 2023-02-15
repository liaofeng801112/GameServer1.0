package org.come.action.npc;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

public class NPCDialogAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		
		String msg = Agreement.getAgreement().NPCDialogAgreement(message);
		// 获得角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		String[] roles = null;
		if( roleInfo.getTeamInfo() != null && !roleInfo.getTeamInfo().equals("") ){
			roles = GameServer.getAllLoginRole().get(ctx).getTeamInfo().split("\\|");
		}else{
			roles = new String[]{roleInfo.getRolename()};
		}
		for (int i = 0; i < roles.length; i++) {
			SendMessage.sendMessageByRoleName(roles[i], msg);
		}
	}

}
