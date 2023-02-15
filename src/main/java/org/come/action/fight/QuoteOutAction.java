package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.QuoteOutBean;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class QuoteOutAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 接受客户端发来的消息
		QuoteOutBean quoteOutBean = GsonUtil.getGsonUtil().getgson().fromJson(message,QuoteOutBean.class);
		quoteOutBean.setValue(Math.abs(quoteOutBean.getValue()));
		LoginResult roleinfo=GameServer.getAllLoginRole().get(ctx);
		if (quoteOutBean.getType()==0) {
			roleinfo.setGold(new BigDecimal(roleinfo.getGold().longValue()-quoteOutBean.getValue()));
			MonitorUtil.getMoney().useD(quoteOutBean.getValue());
		}else {	
			roleinfo.setCodecard(new BigDecimal(roleinfo.getCodecard().longValue()-quoteOutBean.getValue()));
			MonitorUtil.getMoney().useX(quoteOutBean.getValue());
		}
		IAction action=ParamTool.ACTION_MAP.get("getout");
		if (action!=null)action.action(ctx,quoteOutBean.getData());
	}

}
