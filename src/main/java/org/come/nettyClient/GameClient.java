package org.come.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameClient {
	// 25825
	public static int lianhua = 0;
	private NioEventLoopGroup workGroup = new NioEventLoopGroup();
	private Channel channel;
	private Bootstrap bootstrap;

	// netty ip
	private String ip;
	// netty 端口
	private int port;
	// public static String[] potAndIpStrings = new String[8];
	// 私密钥匙
	private static String privateStr;

	public GameClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public static String getPrivateStr() {
		return privateStr;
	}

	public static void setPrivateStr(String privateStr) {
		GameClient.privateStr = privateStr;
	}

	public void start() {
		try {
			bootstrap = new Bootstrap();
			bootstrap.group(workGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ChannelPipeline p = socketChannel.pipeline();
					// 以("\n")为结尾分割的 解码器
					p.addLast("framer", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
					// 字符串解码和编码
					p.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
					p.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
					p.addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
					p.addLast(new GameClientHandler(GameClient.this));
				}
			});
			doConnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 初始化次数
	int a = 0;

	protected void doConnect() throws Exception {
		if (channel != null && channel.isActive()) {
			return;
		}
		// 103.27.6.36 7101
		ChannelFuture future = bootstrap.connect(ip, port);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture futureListener) throws Exception {
				if (futureListener.isSuccess()) {
					// 重置次数
					a = 0;
					channel = futureListener.channel();
					// 进行断线重新连接
					System.out.println("****** ip: " + ip + " 端口: " + port + " 连接中.......");
				} else {
					futureListener.channel().eventLoop().schedule(new Runnable() {
						@Override
						public void run() {
							try {
								doConnect();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 10, TimeUnit.SECONDS);
				}
			}
		});
	}

	// 发送的消息（需要处理的业务逻辑）
	public void sendData() throws Exception {
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 10000; i++) {
			if (channel != null && channel.isActive()) {
				String content = "client msg " + i;
				channel.writeAndFlush(content + "\n");
				// login.sendData();
			}
			Thread.sleep(random.nextInt(20000));
		}
	}

	public void connectClose(ChannelHandlerContext ctx) {
		ctx.close();
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}