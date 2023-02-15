package org.come.action.phonenumber;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.Phone;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;
import org.come.until.sendsms;

/**
 * 
 * @author zz
 * 
 */
public class PhoneAction implements IAction {

	// private static String URL =
	// "http://www.dongmengzhongchou.com:8080/TestMaven/sendphonenumber";
	private static String URL = "http://103.85.86.54:8080/TestMaven/sendphonenumber";

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		Phone phone = GsonUtil.getGsonUtil().getgson().fromJson(message, Phone.class);
		// 获取验证码
		String mes = phone.getPhone();
		String phoneVersion = "eror";
		if (!"".equals(mes) && mes != null) {
			// 需使用11位手机号可进行注册
			if (mes.length() == 11) {
				// 正常使用
				phoneVersion = sendsms.sendUNtil(mes);
				// 36 测试服使用
				// phoneVersion = HttpClientSend.sendPost(URL, "phone=" + mes);
			}
		}
		// 调取返回到的协议进行加密
		String msg = Agreement.getAgreement().PhoneNumberReturnAgreement(phoneVersion + "," + mes);
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
