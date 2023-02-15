package org.come.action.bring;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
/**
 * 洞房，客户端发来对方的名字，返回宝宝出生日期
 * @author 叶豪芳
 * @date 2017年12月30日 上午12:36:26
 * 
 */ 

public class MakeLoveAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 根据对方名字查找对方的信息
		LoginResult otherRole = GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(message));

		// 发给客户端的宝宝生产时间
		String msg = Agreement.makeloveAgreement("86400");

		// 通知洞房
		SendMessage.sendMessageToSlef(ctx, msg);
		SendMessage.sendMessageByRoleName(message, msg);
		
		// 判断对方是否是女的
		if( otherRole.getSex().equals("女") ){
			// 修改角色有待产宝宝状态
			otherRole.setHavebaby(24*60*60);
			// 设置当前洞房时间
			otherRole.setMakeloveTime(System.currentTimeMillis());
		}else{
			// 修改角色有待产宝宝状态
			GameServer.getAllLoginRole().get(ctx).setHavebaby(24*60*60);
			// 设置当前洞房时间
			GameServer.getAllLoginRole().get(ctx).setMakeloveTime(System.currentTimeMillis());
		}
	}
}
