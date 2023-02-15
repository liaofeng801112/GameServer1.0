package org.come.action.summoning;


import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 获取召唤兽信息
 * @author 叶豪芳
 * @date 2017年12月30日 下午12:22:31
 * 
 */ 
public class PetInfoAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得客户端发来的召唤兽ID
		BigDecimal rgid = new BigDecimal(message);
		
		// 获取召唤兽信息放回客户端
		RoleSummoning roleSummoning = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(rgid);
		String msg = Agreement.getAgreement().PetInfoAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleSummoning));
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
