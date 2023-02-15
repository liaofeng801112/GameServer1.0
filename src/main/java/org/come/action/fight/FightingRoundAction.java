package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.FightingEvents;
/**
 * 战斗回合结束，客户端发送战斗编号，发送编号给战斗人，告知回合结束
 * @author 叶豪芳
 * @date 2017年11月24日 下午9:53:23
 * 
 */ 
public class FightingRoundAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 接受客户端发来的消息
		FightingEvents fightingEvents = GsonUtil.getGsonUtil().getgson().fromJson(message, FightingEvents.class);
		BattleData battleData=BattleThreadPool.BattleDatas.get(fightingEvents.getFightingsum());
		if (battleData==null) return;
		battleData.addPlayEnd(fightingEvents.getCurrentRound());
	}
}
