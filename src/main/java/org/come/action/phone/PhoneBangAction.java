package org.come.action.phone;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.PhoneNumberSGBean;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.TimeUntil;
import org.come.nettyClient.UrlUntil;

/**
 * HGC--2019-11-13 手机绑定验证码
 */
public class PhoneBangAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		int type = 0;
		// 接收客户端发来的数据
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {
			return;
		}
		PhoneNumberSGBean numberSGBean = GsonUtil.getGsonUtil().getgson().fromJson(message, PhoneNumberSGBean.class);
		String phone = numberSGBean.getPhone();
		String safenumber = numberSGBean.getSafenumber();
		if (phone != null && !"".equals(phone) && safenumber != null && !"".equals(safenumber)) {

			int sum = AllServiceUtil.getUserTableService().selectPhoneNumberSum(phone);
			if (sum >= 5) {
				type = -1;
			}else{
				UserTable userTable = AllServiceUtil.getUserTableService().selectByPrimaryKey(loginResult.getUser_id());
				if (userTable.getSafety().equals(safenumber)) {
					if (userTable.getPhonenumber() != null && !"".equals(userTable.getPhonenumber())) {
						type = 2;
					} else {
						userTable.setPhonenumber(phone);
						userTable.setPhonetime(TimeUntil.getPastDate());
						AllServiceUtil.getUserTableService().updateUser(userTable);
						type = 3;
						
						/** zrikka 2020 0416 **/
						// 发送给账号服务器  进行修改 手机号
						UrlUntil.accountAction("updatePhone", userTable.getFlag(), userTable.getPhonenumber());
						/***/
					}
				} else {
					type = 1;
				}
			}
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PhoneBangAgreement(type + ""));
		}
	}

}
