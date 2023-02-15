package org.come.action.sys;

import come.tool.hjsl.HjslAction;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.suit.GemIntensify;
import org.come.bean.ConfirmBean;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;
import come.tool.oneArena.OneArenaAction;

public class ConfirmAciton implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		ConfirmBean confirmBean = GsonUtil.getGsonUtil().getgson().fromJson(message, ConfirmBean.class);
		
		int type=confirmBean.getType();
		if (type==47||type==48||type==49) {//47://升级帮派等级 48://升级科技等级 49://升级驯养师等级
			if (!roleInfo.getGangpost().equals("帮主")) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮主才有权限"));	
				return;
			}	
			GangDomain gangDomain=GangUtil.getGangDomain(roleInfo.getGang_id());
			if (gangDomain==null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没有帮派"));	
				return;
			}
			String msg=gangDomain.upLvl(type);
			if (msg!=null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement(msg));	
				return;
			}
		}else if (type==101) {//单人竞技场次数购买
			OneArenaAction.addNumOneArena(ctx, roleInfo, type);
		}else if (type==102) {//宝石拆卸
			GemIntensify.type102(roleInfo, ctx, null, new BigDecimal(confirmBean.getValue()));
		}else if (type==103) {//幻境试炼
			HjslAction.addNumHjsl(ctx, roleInfo, type);
		}
	}

}
