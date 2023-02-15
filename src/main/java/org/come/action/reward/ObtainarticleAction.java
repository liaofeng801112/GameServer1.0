package org.come.action.reward;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.RewardListBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**
 * 获取赏功堂物品
 * @author Administrator
 *
 */
public class ObtainarticleAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 返回蒋功堂物品
		RewardListBean bean = new RewardListBean();
		bean.setRewardHalls(GameServer.rewardList);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().obtainarticleAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)) );
		
	}

}
