package org.come.action.fight;


import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
/**
 * 战斗结束,
 * @author 叶豪芳
 * @date 2017年11月24日 下午11:31:12
 * 
 */ 
public class FightingendAction implements IAction {
	static int size=0;
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleinfo=GameServer.getAllLoginRole().get(ctx);	
		if (roleinfo==null) {return;}
		BattleData battleData=BattleThreadPool.BattleDatas.get(roleinfo.getFighting());
		if (battleData!=null){
			battleData.getParticipantlist().remove(roleinfo.getRolename());
			BattleThreadPool.sendBattleState(0,roleinfo.getRolename());
		}
		roleinfo.setFighting(0);
		
	}
}
