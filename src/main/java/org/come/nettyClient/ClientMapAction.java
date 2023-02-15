package org.come.nettyClient;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import org.come.protocol.AgreementUtil;

/**
 * 处理服务器接受方法
 * 
 * @author zz
 * 
 */
public class ClientMapAction {
	/**
	 * 处理服务返回消息接口
	 */
	public static Map<String, FromServerAction> serverAction = new HashMap<String, FromServerAction>();
	// 通道连接池 键: ip|port
	public static Map<String, ChannelHandlerContext> nettyAction = new HashMap<String, ChannelHandlerContext>();
	// 通道标识 键: ip|port
	public static Map<String, Boolean> flagAction = new HashMap<String, Boolean>();

	public ClientMapAction() {
		// 实例化服务器方法
		serverAction.put(MessageProcessUntil.ServerMes, new ServerToClientMesControl());
		serverAction.put(AgreementUtil.LOGINVERSION, new VersionControl());// 版本号确定
	}
}
