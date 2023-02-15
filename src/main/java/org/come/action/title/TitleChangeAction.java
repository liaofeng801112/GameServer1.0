package org.come.action.title;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

/**
 * 修改角色称谓,客户端发来称谓名称,更新角色信息广播
 * @author 叶豪芳
 * @date 2017年12月25日 下午2:40:55
 * 
 */ 
public class TitleChangeAction implements IAction{


	@Override
	public void action(ChannelHandlerContext ctx, String message) {		
		//修改角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		roleInfo.setTitle(message);
		//广播角色信息
		SendMessage.sendMessageToMapRoles(roleInfo.getMapid(),Agreement.getAgreement().TitleChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleInfo.getRoleShow())));
	}

}
