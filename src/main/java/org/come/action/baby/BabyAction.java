package org.come.action.baby;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.BabyListBean;
import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 获得角色所有宝宝
 * @author Administrator
 *
 */
public class BabyAction implements IAction {
	private BabyListBean babyListBean;
	public BabyAction() {
		babyListBean = new BabyListBean();
	}
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得角色信息
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		// 返回该角色所有宝宝
		List<Baby> babys = AllServiceUtil.getBabyService().selectBabyByRolename(loginResult.getRole_id());
		babyListBean.setBabyList(babys);	
		String msg = Agreement.getAgreement().getBaby(GsonUtil.getGsonUtil().getgson().toJson(babyListBean));
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
