package org.come.action.pack;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.server.GameServer;
/**
 * 背包加锁，客户端发来密码,修改背包密码
 * @author 叶豪芳
 * @date 2017年12月27日 上午11:01:55
 * 
 */ 
public class PackLockAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 修改角色信息
		GameServer.getAllLoginRole().get(ctx).setPassword(message);
		
	}

}
