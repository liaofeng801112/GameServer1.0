package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.ChongjipackBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.VipRewardUtils;

import come.tool.Good.DropUtil;

/** 连续充值领取 */
public class DayForWeekGradeSureAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 判断数据是否为空
		if (message == null || "".equals(message)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("操作错误"));
			return;
		}
		// 获取该角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo == null) {
			return;
		}
		BigDecimal dayandpayorno = roleInfo.getDayandpayorno();// 连续充值数
		// 判断是否满足条件领取
		int cRecharge = VipRewardUtils.continuityRecharge(message);
		if (dayandpayorno.intValue() < cRecharge) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
			return;
		}
		// 判断是否已经领取对应的礼包
		String roleVipInfo = roleInfo.getVipget();
		if (VipRewardUtils.checkYesOrNo(roleVipInfo, "3", cRecharge + "")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您已经领取该礼包!"));
			return;
		}
		// 满足兑换条件
		StringBuffer vipInfo = new StringBuffer();
		// 其中 date 格式 封装成 vip|Vipget。
		VipRewardUtils.setVipget(roleInfo, "3", cRecharge + "");
		vipInfo.append(roleInfo.getVipget());

		// ------将奖励的东西给玩家----------
		// 获取连续充值礼包列表
		List<ChongjipackBean> chongList = GameServer.getPackgradecontrol().get(5);
		for (int i = 0; i < chongList.size(); i++) {
			if (cRecharge == chongList.get(i).getPackgradetype()) {
				// 物品=80655$5&80656$5
				String rewardStr = chongList.get(i).getPackgoods();
				DropUtil.getDrop(roleInfo, rewardStr, "", 28, 1D, vipInfo.toString());
				break;
			}
		}
		// -----------------
	}

}
