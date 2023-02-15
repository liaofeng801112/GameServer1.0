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

/** 每日充值兑换 */
public class PayDayGradeSureAction implements IAction {

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
		// 判断是否已经领取对应的礼包
		List<ChongjipackBean> chongList = GameServer.getPackgradecontrol().get(4);
		ChongjipackBean chongListBean = null;
		// 充值等级
		int condition = VipRewardUtils.everydayRecharge(message);
		BigDecimal daypaysum = roleInfo.getDaypaysum();// 日累计充值
		for(int i = 0 ; i < chongList.size() ; i++){
			int chongziGrade = condition;
			if(chongziGrade == chongList.get(i).getPackgradetype()){
				chongListBean = chongList.get(i);
				if (daypaysum.doubleValue() < Double.valueOf(chongList.get(i).getCanpaymoney())) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
					return;
				}
				break;
			}
		}
		// 满足兑换条件
		String roleVipInfo = roleInfo.getVipget();
		if (VipRewardUtils.checkYesOrNo(roleVipInfo, "2", condition+"")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您已经领取该礼包!"));
			return;
		}
		// 其中 date 格式 封装成 vip|Vipget。
		StringBuffer vipInfo = new StringBuffer();
		VipRewardUtils.setVipget(roleInfo, "2", condition + "");
		vipInfo.append(roleInfo.getVipget());

		// ------将奖励的东西给玩家----------
		// 获取每日充值礼包列表
		// 物品=80655$5&80656$5
		String rewardStr = chongListBean.getPackgoods();
		DropUtil.getDrop(roleInfo, rewardStr, "", 27, 1D, vipInfo.toString());
		// -----------------------------
	}

}
