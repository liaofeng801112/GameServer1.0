package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.ManData;
/**获取战斗抗性*/
public class FightQlAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult result=GameServer.getAllLoginRole().get(ctx);
		if (result==null||result.getFighting()==0) {
			return;
		}
		BattleData battleData=BattleThreadPool.BattleDatas.get(result.getFighting());
		if (battleData==null) {
			return;
		}
		List<ManData> datas=battleData.getBattlefield().fightingdata;
		for (int i = datas.size()-1; i >=0; i--) {
			ManData data=datas.get(i);
			if (data.getType()==0&&data.getManname().equals(result.getRolename())) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().fightQlAgreement(GsonUtil.getGsonUtil().getgson().toJson(data.getQuality())));
				return;
			}
		}
		SendMessage.sendMessageToSlef(ctx,message);
	}
}
