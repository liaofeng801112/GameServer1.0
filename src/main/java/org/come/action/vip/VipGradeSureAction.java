package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.PayvipBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.VipRewardUtils;

import come.tool.Good.DropUtil;

/** vip兑换 */
public class VipGradeSureAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 判断数据是否为空
		if (message == null || "".equals(message)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement()
					.PromptAgreement("操作错误"));
			return;
		}
		// [0] 充值等级 , [1] 充值金额
		String[] condition = VipRewardUtils.gradeAndMoney(message);
		Integer paynum = 1000000;
		List<PayvipBean> payvipList = GameServer.getPayvipList();
		for (int i = 0; i < payvipList.size(); i++) {
			if (condition[0].equals(payvipList.get(i).getGrade() + "")) {
				paynum = payvipList.get(i).getPaynum();
				break;
			}
		}
		// 获取该角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo == null) {
			return;
		}
		// 判断是否满足条件领取
		BigDecimal paysum = roleInfo.getPaysum();
		if (paysum.doubleValue() < Double.valueOf(paynum)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
			return;
		}
		// 判断是否已经领取对应的礼包
		String roleVipInfo = roleInfo.getVipget();
		if (VipRewardUtils.checkYesOrNo(roleVipInfo, "1", condition[0])) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement()
					.PromptAgreement("您已经领取该礼包!"));
			return;
		}
		// 其中 date 格式 封装成 vip|Vipget。
		StringBuffer vipInfo = new StringBuffer();
		VipRewardUtils.setVipget(roleInfo, "1", condition[0] + "");
		vipInfo.append(roleInfo.getVipget());

		// ------将奖励的东西给玩家----------
		// 获取的物品
		// List<PayvipBean> payvipList = GameServer.getPayvipList();
		for (int i = 0; i < payvipList.size(); i++) {
			if (condition[0].equals(payvipList.get(i).getGrade() + "")) {
				// 物品=80655$5&80656$5
				String givegoods = payvipList.get(i).getGivegoods();
				DropUtil.getDrop(roleInfo, givegoods, "", 26, 1D,
						vipInfo.toString());
				break;
			}
		}
		// -----------------------------
	}

}
