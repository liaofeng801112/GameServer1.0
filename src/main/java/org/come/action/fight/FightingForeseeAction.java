package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.model.Gamemap;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleThreadPool;
import come.tool.Battle.FightingForesee;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

/**
 * 战斗预知
 * @author 叶豪芳
 * @date 2018年1月12日 下午8:10:57
 * 
 */ 
public class FightingForeseeAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {return;}
		FightingForesee fightingResponse  = GsonUtil.getGsonUtil().getgson().fromJson(message,FightingForesee.class);		
		if (fightingResponse.getRobotid()!=null) {
			LoginResult role=GameServer.getAllLoginRole().get(ctx);
			if (role==null) {return;}
			RoleData roleData=RolePool.getRoleData(role.getRole_id());
			if (roleData.isRobotId(Integer.parseInt(fightingResponse.getRobotid()))) {
				WriteOut.addtxt("非法robotID:"+fightingResponse.getRobotid()+":"+role.getRole_id()+":"+role.getRolename(),9999);
				return;
			}
		}
		if (fightingResponse.getType()==5) {
			if (loginResult.getRoleData().getLimit("杀人香")!=null) {
				fightingResponse.setType(10);
				Gamemap gamemap=GameServer.getMap(loginResult.getMapid().toString());
				if (gamemap==null||(gamemap.getPolice()!=null&&!gamemap.getPolice().equals("1"))) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("本地图不允许强行杀人"));
					return;
				}
			}else if (loginResult.getRoleData().getRoleSystem().getIsPk()==0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("先设置允许切磋"));
				return;
			}
		}
        BattleThreadPool.addBattle(loginResult,fightingResponse);
	}
	
	
}
