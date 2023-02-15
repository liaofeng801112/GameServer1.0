package org.come.action.npc;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.NpcCureBean;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * NPC治疗
 * @author 叶豪芳
 * @date 2018年1月12日 上午11:56:54
 * 
 */ 
public class NpcCureAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		NpcCureBean npcCureBean = GsonUtil.getGsonUtil().getgson().fromJson(message, NpcCureBean.class);

		// 治疗召唤兽
		if( npcCureBean.getRoleSummoning() != null ){
			// 修改宠物信息
			AllServiceUtil.getRoleSummoningService().updateRoleSummoning(npcCureBean.getRoleSummoning());
		}
	}

}
