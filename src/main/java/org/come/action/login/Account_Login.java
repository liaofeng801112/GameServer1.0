package org.come.action.login;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.RoleTableList;
import org.come.entity.RoleTableNew;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class Account_Login implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		if (GameServer.OPEN)
			return;
		// 将用户名密码写入登入bean中
		UserTable account = GsonUtil.getGsonUtil().getgson().fromJson(message, UserTable.class);
		
		UserTable user = AllServiceUtil.getUserTableService().selectByFlag(account.getFlag());
		if(user == null){
			return;
		}
		
		// 用户名密码
		String username = user.getUsername();
		String userpwd = user.getUserpwd();
		if (userpwd == null || userpwd.equals("")) {
			return;
		}
		// 查询是否有该用户
		UserTable userTable = AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(username, userpwd);
		if (userTable != null) {
			// 如果有人已在线提示对方下线
			String msgs = Agreement.getAgreement().inlineLoginAgreement();
			// 查找对方输出流
			if (GameServer.getInlineUserNameMap().get(username) != null) {
				ChannelHandlerContext ctx2 = GameServer.getInlineUserNameMap().get(username);
				if (ctx2 != ctx || GameServer.getAllLoginRole().get(ctx2) != null) {
					SendMessage.sendMessageByUserName(username, msgs);
					GameServer.userDown(GameServer.getInlineUserNameMap().get(username));
				}
			}
			// 添加用户名和通道集合
			GameServer.getInlineUserNameMap().put(username, ctx);
			GameServer.getSocketUserNameMap().put(ctx, username);
			// 手机验证登录 三端( 没有此功能 )
			String selectAtid = AllServiceUtil.getOpenareatableService().selectAtid(userTable.getQid() + "");
			// -- 20200212 zrikka
			// 通过 区id 获取 总区id
			String belongId = AllServiceUtil.getOpenareatableService().selectBelong(userTable.getQid() + "");
			if (belongId == null) {
				return;
			}
			// 用户id
			BigDecimal uid = userTable.getUser_id();
			// 通过用户id和 总区id 查询 所有角色 -- 20200212 zrikka
			List<RoleTableNew> role = AllServiceUtil.getRegionService().selectRole(uid, Integer.valueOf(belongId));
			RoleTableList list = new RoleTableList();
			list.setRoleList(role);
			list.setAtid(selectAtid);
			list.setUserid(userTable.getUser_id() + "");
			list.setUsername(userTable.getUsername());
			list.setPasw(userTable.getUserpwd());
			String content = GsonUtil.getGsonUtil().getgson().toJson(list);
			String mes = Agreement.getAgreement().successLoginAgreement(content);
			SendMessage.sendMessageToSlef(ctx, mes);
		} else {
			String msg = Agreement.getAgreement().erroLoginAgreement();
			SendMessage.sendMessageToSlef(ctx, msg);
		}
	}

}
