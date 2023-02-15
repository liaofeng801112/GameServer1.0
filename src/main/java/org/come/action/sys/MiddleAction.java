package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.Middle;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class MiddleAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		Middle middle=GsonUtil.getGsonUtil().getgson().fromJson(message, Middle.class);
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		loginResult.setTaskDaily(middle.getTaskDaily());	
	}

}
