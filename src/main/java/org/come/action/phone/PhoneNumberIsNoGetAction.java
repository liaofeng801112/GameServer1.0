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

/**
 * HGC--2019-11-13 获取绑定的手机号
 * 
 * @author Administrator
 * 
 */
public class PhoneNumberIsNoGetAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 接收客户端发来的数据
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {
			return;
		}
		UserTable userTable = AllServiceUtil.getUserTableService().selectByPrimaryKey(loginResult.getUser_id());
		PhoneNumberSGBean numberSGBean = new PhoneNumberSGBean();
		String phoneNumber = userTable.getPhonenumber();
		if (phoneNumber != null && !"".equals(phoneNumber)) {
			numberSGBean.setPhone(phoneNumber);
		} else {
			numberSGBean.setPhone("nophone");
		}
		numberSGBean.setNumbertime(userTable.getPhonetime());
		String phoneNumberIsNoGetAgreement = Agreement.getAgreement().PhoneNumberIsNoGetAgreement(GsonUtil.getGsonUtil().getgson().toJson(numberSGBean));
		SendMessage.sendMessageToSlef(ctx, phoneNumberIsNoGetAgreement);
	}
}
