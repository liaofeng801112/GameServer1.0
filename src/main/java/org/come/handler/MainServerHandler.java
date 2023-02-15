package org.come.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.come.action.IAction;
import org.come.action.pay.Payreturn;
import org.come.protocol.AgreementUtil;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.tool.NewAESUtil;
import org.come.tool.WriteOut;


public class MainServerHandler extends SimpleChannelInboundHandler<String> {
	private int lossConnectCount = 0;
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// System.out.println("已经8秒未收到客户端的消息了！");
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				lossConnectCount++;
				if (lossConnectCount>3){// 关闭通道
					GameServer.userDown(ctx);
					ctx.close();
					WriteOut.addtxt("心跳关闭连接", 9999);
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}

	}
	static final String jiu="发送旧协议头:";
	static final String tm="转时间报错:";
	static final String ys="疑是重复发包:";
	static final String CL="处理报错:";
	static final String JS="旧时间包:";
    static final String QG="//";
	private long time=0;
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//		System.out.println("进入之前");
		 // 收到消息直接打印输出
		try {
			if (msg.length() < 6) {lossConnectCount=0;return;}// 过滤心跳

			if (GameServer.OPEN) {return;}
			String cd = msg.substring(0, 4);
			String ab = null;
			IAction action = ParamTool.ACTION_MAP2.get(cd);
			if (action != null) {
				if (action instanceof Payreturn) {
					System.out.println("有人在刷玉！！！");
					return;
				}
				ab = msg.substring(6);
				action.action(ctx,ab);
				return;
			}
			msg = NewAESUtil.AESJDKDncode(msg);
			System.out.println("解析后的msg:" +  msg);
			// 收到消息直接打印输出
			if (msg == null) {System.out.println("空数据");return;}
			int wz = msg.indexOf(QG);
			if (wz < 13) {WriteOut.addtxt(jiu + msg, 9999);return;}
			try {
				String ef = msg.substring(0, 13);
				long eftime = new Long(ef);
				if (eftime > time) {
					time = eftime;
				} else {
					// System.out.println(ys+msg);
					// WriteOut.addtxt(ys+msg,9999);
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
				WriteOut.addtxt(tm + msg, 9999);
				return;
			}
			cd = msg.substring(13, wz);
			ab = msg.substring(wz + 2);
			action = ParamTool.ACTION_MAP.get(cd);
			if (action != null) {
				if (action instanceof Payreturn) {
					System.out.println("有人在刷玉！！！");
					return;
				}
				action.action(ctx, ab);
				if (cd.equals(AgreementUtil.enterGame)) {
					time = System.currentTimeMillis();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		    try {
		    	String abc = CL + msg + getErrorMessage(e);
				WriteOut.addtxt(abc, 9999);
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("生成日志出现异常");
			}
		    e.printStackTrace();
		}finally{
			ReferenceCountUtil.release(msg);
		}
	}
	public static String getErrorMessage(Exception e){
        if(null == e){return "";}
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return  sw.toString();
    }
	/**
	 * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
	 * channelActive 和channelInActive 在后面的内容中讲述，这里先不做详细的描述
	 */
	public static long a=0;
	public static long b=0;

	public static String VS=null;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		a++;
		SendMessage.sendMessageToSlef(ctx, VS);
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 保存用户信息
		GameServer.userDown(ctx);
		super.handlerRemoved(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		b++;
		// 保存用户信息
		GameServer.userDown(ctx);
		ctx.close();
		super.exceptionCaught(ctx, cause);
	}


}