package org.come.nettyClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class GameClientHandler extends SimpleChannelInboundHandler<String> {
	private GameClient client;

	public GameClientHandler(GameClient client) {
		this.client = client;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// System.out.println("获取异常" + cause);
		cause.printStackTrace();
		ctx.close();
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// biubiu
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				StringBuffer key = new StringBuffer();
				key.append(client.getIp());
				key.append("|");
				key.append(client.getPort());
				ClientSendMessage.toServer(key.toString(), "bibi");
			}
		}
		super.userEventTriggered(ctx, evt);
	}

	/** 收到服务器的消息 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) {
	
		try {
			if (msg != null) {
				// 进行服务器返回的消息处理
				ClientMapAction.serverAction.get(MessageProcessUntil.ServerMes).controlMessFromServer(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		StringBuffer key = new StringBuffer();
		key.append(client.getIp());
		key.append("|");
		key.append(client.getPort());
		// 将通信赋值 键: ip|port
		ClientMapAction.nettyAction.put(key.toString(), ctx);
		ClientMapAction.flagAction.put(key.toString(), true);
		// MessagrFlagUntil.SendMesToServer = ctx;
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		StringBuffer key = new StringBuffer();
		key.append(client.getIp());
		key.append("|");
		key.append(client.getPort());
		// 断线重来连告诉服务器将之前的数据保存
		System.out.println("断线");
		if (ClientMapAction.flagAction.get(key.toString())) {
			ClientMapAction.flagAction.put(key.toString(), false);
			// MessagrFlagUntil.CanDoConnectOrNo = false;
			client.doConnect();
		}
		super.channelInactive(ctx);
	}

}