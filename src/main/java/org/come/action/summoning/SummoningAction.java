package org.come.action.summoning;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.PetResultBean;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 召唤兽,返回召唤兽信息
 * @author 叶豪芳
 * @date : 2017年12月1日 下午8:31:01
 */
public class SummoningAction implements IAction{
	/**
	 * 获取角色的召唤兽(non-Javadoc)
	 * @see org.come.action.IAction#action(java.net.Socket, java.lang.String)
	 */
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
			BigDecimal roleid = GameServer.getAllLoginRole().get(ctx).getRole_id();

			// 获得全部召唤兽
			List<RoleSummoning> roleSummonings = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleid);

			// 将集合放进返回bean中
			PetResultBean petResultBean = new PetResultBean();
			petResultBean.setRoleSummonings(roleSummonings);

			// 返回客户端
			String msg = Agreement.getAgreement().petAgreement(GsonUtil.getGsonUtil().getgson().toJson(petResultBean));
			SendMessage.sendMessageToSlef(ctx, msg);
	}

}
