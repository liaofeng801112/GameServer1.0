package org.come.action.gang;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.entity.Gang;
import org.come.entity.GangGiveMoneyBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import io.netty.channel.ChannelHandlerContext;
/**
 * 帮派捐钱
 * @author 叶豪芳
 * @date 2018年1月12日 下午4:11:05
 *
 */
public class GangGiveMoneyAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 捐出的金钱
		BigDecimal gold = new BigDecimal(message);
		if (gold.longValue()<=0) {
			return;
		}
		if (GameServer.getAllLoginRole().get(ctx).getGold().compareTo(gold) == -1) {
			return;
		}
		GangGiveMoneyBean giveMoneyBean = new GangGiveMoneyBean();
		// 修改角色的金钱
		roleInfo.setGold(roleInfo.getGold().subtract(gold));
		MonitorUtil.getMoney().useD(gold.longValue());
		// 修改角色捐钱
		roleInfo.setAchievement(roleInfo.getAchievement().add(gold));
		System.out.println("捐款金额:"+gold);
		// 1000万给100点贡献

		roleInfo.setContribution(roleInfo.getContribution().add(gold.divide(new BigDecimal("100000"),0, RoundingMode.DOWN)));
		roleInfo.setUptime("在线");
		// 修改帮派
		Gang gang = AllServiceUtil.getGangService().findRoleGangByGangID(roleInfo.getGang_id());
		if (gang.getBuilder() == null) {
			gang.setBuilder(gold.divide(new BigDecimal("100000"),0,BigDecimal.ROUND_DOWN));
		} else {
			gang.setBuilder(gang.getBuilder().add(gold.divide(new BigDecimal("100000"),0,BigDecimal.ROUND_DOWN)));
		}
		if (gang.getProperty() == null) {
			gang.setProperty(gold);
		} else {
			gang.setProperty(gang.getProperty().add(gold));
		}

		AllServiceUtil.getGangService().updateGang(gang);
		giveMoneyBean.setGang(gang);
		giveMoneyBean.setLoginResult(roleInfo);

		int moneyLenth = gold.toString().length();
		String moneyColor = moneyLenth > 9 ? "#R" : moneyLenth > 8 ? "#G" : moneyLenth > 7 ? "#c00EFEF" : moneyLenth > 6 ? "#Y" : "#W";

		String msg = Agreement.getAgreement().chatAgreement("{\"id\":2,\"message\":\"#Y" + roleInfo.getRolename() + "#G向帮派账房捐赠了金钱"+moneyColor + gold.toString() +"#G两，为帮派做出了巨大贡献。\"}");
		SendMessage.sendMessageToAllRoles(msg);
		SendMessage.sendMessageToSlef(ctx,Agreement.givemoneyAgreement(GsonUtil.getGsonUtil().getgson().toJson(giveMoneyBean)));
	}

}
