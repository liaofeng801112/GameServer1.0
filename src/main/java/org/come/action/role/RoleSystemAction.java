package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Role.RoleSystem;

public class RoleSystemAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 获得角色信息
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		RoleSystem roleSystem = GsonUtil.getGsonUtil().getgson().fromJson(message, RoleSystem.class);
		RoleSystem system=roleData.getRoleSystem();
		system.set(roleSystem);
//		roleData.setRoleSystem(roleSystem);
	}

}
