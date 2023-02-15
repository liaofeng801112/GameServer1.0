package org.come.action;

import io.netty.channel.ChannelHandlerContext;

/**
 * 所有action的接口
 * @author 叶豪芳
 * @date : 2017年11月26日 下午2:52:19
 */
public abstract interface IAction {
	
	public void action(ChannelHandlerContext ctx, String message);

}
