package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.FightingEvents;
/**
 * 战斗回合转发
 * @author 叶豪芳
 * @date 2018年1月6日 上午10:04:57
 * 
 */ 
public class FightRoundAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 接受客户端发来的消息
		FightingEvents fightingEvents = GsonUtil.getGsonUtil().getgson().fromJson(message, FightingEvents.class);

		BattleData battleData=BattleThreadPool.BattleDatas.get(fightingEvents.getFightingsum());
		if (battleData==null) return;
		battleData.addPolicy(fightingEvents);
	}
}
