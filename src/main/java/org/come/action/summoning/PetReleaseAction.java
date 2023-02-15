package org.come.action.summoning;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.until.AllServiceUtil;
/**
 * 宠物放生,客户端发来该宠物的标识，删除该宠物
 * @author 叶豪芳
 * @date 2018年1月4日 上午10:42:21
 * 
 */ 
public class PetReleaseAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 删除召唤兽
		AllServiceUtil.getRoleSummoningService().deleteRoleSummoningBySid(new BigDecimal(message));

	}

}
