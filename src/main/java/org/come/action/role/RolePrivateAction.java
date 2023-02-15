package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.PrivateData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

public class RolePrivateAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		PrivateData privateData = GsonUtil.getGsonUtil().getgson().fromJson(message, PrivateData.class);
	    roleData.upPrivateData(privateData);
	}

}
