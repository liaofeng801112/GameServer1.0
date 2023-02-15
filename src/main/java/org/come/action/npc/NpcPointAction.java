package org.come.action.npc;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.NPCDialog;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

/**
 * 点击NPC操作类
 * @author 叶豪芳
 * @date : 2017年11月30日 下午4:06:41
 */
public class NpcPointAction implements IAction{

	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		NPCDialog npcDialog = new NPCDialog();
		// 将谈话信息返回客户端
		String result = Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
		SendMessage.sendMessageToSlef(ctx, result);
	}

}
