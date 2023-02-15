package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.UserRoleArrBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;
/**
 * 排行榜
 * @author 叶豪芳
 *
 */
public class OrderByRoleAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取排行榜类型
		if(message == null) return;
		Integer type = Integer.parseInt(message);
		
		// 获取排行榜内容
		List<LoginResult> list = GameServer.allBangList.get(type);
		
		// 返回客户端
		UserRoleArrBean bean = new UserRoleArrBean();
		bean.setList(list);
		String msg = Agreement.getAgreement().pankinglistAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToSlef(ctx, msg);

	}

}
