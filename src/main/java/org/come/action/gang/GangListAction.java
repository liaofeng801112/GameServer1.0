package org.come.action.gang;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.GangListBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

import come.tool.newGang.GangUtil;
/**
 * 帮派列表,返回所有帮派集合
 * @author 叶豪芳
 * @date 2017年12月20日 上午11:19:30
 * 
 */ 
public class GangListAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		GangListBean gangListBean = new GangListBean();
		// 放进bean中返回客户端
		gangListBean.setGangs(GangUtil.getGangs());
		String msg = Agreement.getAgreement().ganglistAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangListBean));
		SendMessage.sendMessageToSlef(ctx,msg);
	}

}
