package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.ChongjipackBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Good.DropUtil;

/** 每日特惠购买 */
public class DayForOneSureAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取该角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 判断是否已经领取对应的礼包
		BigDecimal daygetorno = roleInfo.getDaygetorno();
		if (daygetorno.intValue() == 1) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您已经购买过该礼包!"));
			return;
		}

		// 满足兑换条件
		// ------将奖励的东西给玩家----------
		// 获取每日特惠列表
		List<ChongjipackBean> chongList = GameServer.getPackgradecontrol().get(6);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(d);
		for (int i = 0; i < chongList.size(); i++) {
			if(format.equals(chongList.get(i).getHuitime())){
				// 物品=80655$5&80656$5
				String rewardStr = chongList.get(i).getPackgoods();
				Double jg = Double.valueOf(chongList.get(i).getCanpaymoney());
				if (roleInfo.getCodecard().longValue() < jg) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("仙玉不足"));
					return;
				}
				roleInfo.setCodecard(new BigDecimal(roleInfo.getCodecard().longValue() - jg));
				StringBuffer str = new StringBuffer();
				str.append("X=");
				str.append(-jg.longValue());
				DropUtil.getDrop2(roleInfo, rewardStr, "", 29, 1D, null, str.toString());
			}
		}
		roleInfo.setDaygetorno(new BigDecimal(1));
		// -----------------
	}
	
	public static void main(String[] args) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("当前时间：" + sdf.format(d));
	}

}
