package org.come.nettyClient;

import io.netty.channel.ChannelHandlerContext;

/**
 * netty 客户端 消息 发送工具
 * 
 * @author zengr
 * 
 */
public class ClientSendMessage {
	static long x = System.currentTimeMillis();
	static String FB = "\n";

	public static void toServer(String key, String sendmes) {
		if (ClientMapAction.flagAction.get(key)) {
			ClientMapAction.nettyAction.get(key).writeAndFlush(sendmes + FB);
		}
	}

	/** 实例化连接 */
	public static void getClientUntil(String ip, int port) throws Exception {
		ChannelHandlerContext SendMesToServer = ClientMapAction.nettyAction.get(ip + "|" + port);
		if (SendMesToServer == null) {
			// 启动服务器连接
			GameClient client = new GameClient(ip, port);
			client.start();
		}
	}
}
