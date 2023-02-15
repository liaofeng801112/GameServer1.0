package org.come.action.gang;

import java.math.BigDecimal;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Gang;
import org.come.entity.Gangapply;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import come.tool.newGang.GangUtil;
/**
 * 申请加入帮派,客户端发来Gang的信息,加入帮派申请列表中
 * @author 叶豪芳
 * @date 2017年12月20日 上午11:13:40
 * 
 */ 
public class GangApplyAction implements IAction {
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo.getGang_id().intValue()!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已经有帮派了"));		
			return;
		}
		BigDecimal gangid=new BigDecimal(message);
		Gang gang=GangUtil.getGang(gangid);
		if (gang==null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮派不存在"));		
			return;
		}
		Gangapply gangapply=AllServiceUtil.getGangapplyService().selectGangApply(roleInfo.getRole_id(), gangid);
		if(gangapply!=null){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已存在申请表里"));		
			return;
		}
		gangapply=new Gangapply();
		gangapply.setGangid(gangid);
		gangapply.setRoleid(roleInfo.getRole_id());
		AllServiceUtil.getGangapplyService().insertIntGangapple(gangapply);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你申请加入#G"+gang.getGangname()));		
	}

}
