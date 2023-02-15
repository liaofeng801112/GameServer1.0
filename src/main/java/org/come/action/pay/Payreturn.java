package org.come.action.pay;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Date;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.ApplyBean;
import org.come.bean.ApplyPayBean;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.ExpensesReceipts;
import org.come.entity.Ipaddressmac;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisPoolUntil;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.APIHttpClient;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.util.StringUtil;

import io.netty.channel.ChannelHandlerContext;

/**
 * 充值回调
 * 
 * @author zengr
 * 
 */
public class Payreturn implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx1, String mes) {
		// TODO Auto-generated method stub
//		System.out.println("充值回调信息: " + mes);

		// System.out.println(mes);
		ExpensesReceipts expensesReceipts = GsonUtil.getGsonUtil().getgson().fromJson(mes, ExpensesReceipts.class);
		// 依据角色名去查询数据库的账号对应的quID
		try {
			String[] vs = expensesReceipts.getPlayeracc().split("\\|");
			expensesReceipts.setPlayeracc(vs[0]);
		} catch (Exception e) {}
			ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(expensesReceipts.getPlayeracc());
			if (ctx != null) {// 在线充值
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#G就你也来刷我？？？垃圾废物#1"));
				String IP=null;
				try {
					InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
					IP = insocket.getAddress().getHostAddress();
				} catch (Exception e) { }

				if (StringUtil.isNotEmpty(IP)) {
					Ipaddressmac ip = new Ipaddressmac();
					ip.setAddresskey(IP);
					ip.setCtime(new Date().toGMTString());
					ip.setIpid(new BigDecimal(System.currentTimeMillis()));
					AllServiceUtil.getIpaddressmacService().insert(ip);
				}

				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().serverstopAgreement());
			}

	}
}
