package org.come.action.summoning;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.RoleSummoning;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 修改宠物信息
 * @author 叶豪芳
 * @date 2017年12月23日 下午8:03:21
 * 
 */ 
public class PetChangeAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得宠物信息
		RoleSummoning roleSummoning = GsonUtil.getGsonUtil().getgson().fromJson(message, RoleSummoning.class);
		// 修改宠物信息
		AllServiceUtil.getRoleSummoningService().updateRoleSummoning(roleSummoning);
	}
}
