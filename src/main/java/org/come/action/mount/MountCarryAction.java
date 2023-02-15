package org.come.action.mount;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**
 * 	 坐骑骑乘/休息
 * @author 叶豪芳
 * @date 2018年1月11日 下午2:16:55
 * 
 */ 
public class MountCarryAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 携带坐骑
		if( message != null ){
			// 修改角色信息
			GameServer.getAllLoginRole().get(ctx).setMount_id(Integer.parseInt(message));
		}
		// 休息坐骑
		else{
			// 修改角色信息
			GameServer.getAllLoginRole().get(ctx).setMount_id(null);
		}
		
		// 广播角色信息
		SendMessage.sendMessageToMapRoles(GameServer.getAllLoginRole().get(ctx).getMapid(),Agreement.getAgreement().MountCarryAgreement(GsonUtil.getGsonUtil().getgson().toJson(GameServer.getAllLoginRole().get(ctx))));
	}
}
