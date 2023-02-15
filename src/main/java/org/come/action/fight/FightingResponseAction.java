package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.FightingResponse;
/**
 * 战斗响应
 * @author 叶豪芳
 * @date 2018年1月12日 下午8:15:36
 * 
 */ 
public class FightingResponseAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

        FightingResponse fightingResponse  = GsonUtil.getGsonUtil().getgson().fromJson(message, FightingResponse.class);
		BattleData battleData=BattleThreadPool.BattleDatas.get(fightingResponse.getFightingNumber());
		if (battleData==null) return;
		battleData.addPreview(fightingResponse.getManData());
	}

}
