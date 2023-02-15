package org.come.action.role;


import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
/**
 * 人物升级
 * @author 叶豪芳
 * @date 2017年11月25日 下午4:04:22
 * 
 */ 
public class RoleLevelUpAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取角色信息
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);		
		SendMessage.sendMessageToMapRoles(loginResult.getMapid(),Agreement.getAgreement().RoleLevelUpAgreement(message));
	}
}
