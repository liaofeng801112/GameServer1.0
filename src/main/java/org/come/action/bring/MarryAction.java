package org.come.action.bring;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

/**结婚,客户端发来结婚请求和对方的名字*/ 
public class MarryAction implements IAction {
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得自己的信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 获得对方的信息
		LoginResult otherRole = GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(message));
		if (roleInfo.getMarryObject()!=null&&!roleInfo.getMarryObject().equals("")) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你自己都已经有对象了"));
			return;
		}
        if (otherRole.getMarryObject()!=null&&!otherRole.getMarryObject().equals("")) {
        	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("对方有对象了"));
        	return;
		}
        // 添加结婚对象
		otherRole.setMarryObject(roleInfo.getRolename());
		// 添加结婚对象
		roleInfo.setMarryObject(otherRole.getRolename());
		// 返回对方自己的名字
		SendMessage.sendMessageByRoleName(roleInfo.getRolename(),  Agreement.marryAgreement(otherRole.getRolename()));
		SendMessage.sendMessageByRoleName(otherRole.getRolename(), Agreement.marryAgreement(roleInfo.getRolename()));	
	}
}
