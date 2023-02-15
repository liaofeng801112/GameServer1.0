package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.Ipaddressmac;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 注册新用户
 * 
 * @author 叶豪芳
 * @date : 2017年11月26日 下午2:53:38
 */
public class RegisterAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 注册开关，0关闭，1开启
		if (GameServer.registerOnOff == 0)
			return;
		String clientIP = LoginAction.getIP(ctx);
		List<Ipaddressmac> ipaddressmac = AllServiceUtil.getIpaddressmacService().selectIpaddressmac(clientIP);
		if (ipaddressmac.size() != 0) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		// 将注册信息写入用户bean中
		UserTable userTable = GsonUtil.getGsonUtil().getgson().fromJson(message, UserTable.class);
		// -- 三端
		if ("".equals(userTable.getUsername()) || "".equals(userTable.getUserpwd()) || "".equals(userTable.getSafety())) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("信息不可为空");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		// 账号 不可 小于8位 超过20位
		String checkUserAcc = checkUserAcc(userTable.getUsername());
		if (!"true".equals(checkUserAcc)) {
			String msg = Agreement.getAgreement().erroRigisterAgreement(checkUserAcc);
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		// 密码不可少于6位 不可超过16位
		if (userTable.getUserpwd().length() < 4 || userTable.getUserpwd().length() > 16) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("密码格式有误4-16位");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		// 安全码不可少于6位 不可超过16位
		if (userTable.getSafety().length() < 4 || userTable.getSafety().length() > 16) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("安全码格式有误4-16位");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}

		// 推荐码
		String tuiji = userTable.getTuiji();
		if (tuiji == null || "".equals(tuiji)) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("请输入推荐码");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}

		List<BigDecimal> sid = AllServiceUtil.getOpenareatableService().selectTuijiNum(tuiji);
		if (sid.size() == 0) {
			String msg = Agreement.getAgreement().erroRigisterAgreement("没有找到该推荐码,请检查是否正确!");
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		userTable.setQid(sid.get(0));
		// 判断用户是否存在
		UserTable sameUser = AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userTable.getUsername(), null);
		// -- 三端 判断手机号是否已注册账号
//		List<UserTable> sameUser2 = AllServiceUtil.getUserTableService().findUserByPhoneNum(userTable.getPhonenumber());

		if (sameUser == null) {
			userTable.setRegisterip(clientIP);
			// 注册用户
			userTable.setUser_id(RedisCacheUtil.getUser_pk());
			int isSuccess = AllServiceUtil.getUserTableService().insertIntoUser(userTable);

			if (isSuccess > 0) {
				String msg = Agreement.getAgreement().successRigisterAgreement();
				SendMessage.sendMessageToSlef(ctx, msg);
			}

		} else {
			// -- 三端
			String str = "";
			if (sameUser != null) {
				str = "该账号已存在";
			}
			String msg = Agreement.getAgreement().erroRigisterAgreement(str);
			SendMessage.sendMessageToSlef(ctx, msg);

		}

	}

	// 判断是否只包含字母和数字
	public String checkUserAcc(String acc) {
		if (acc.length() < 4 || acc.length() > 20) {
			return "账号格式必须为4-20个字母和数字";
		}
		if (check(acc)) {
			return "true";
		}
		return "账号不可为纯字母、纯数字或带有符号!";
	}

	public boolean check(String acc) {
		String reg = "^(\\d+[A-Za-z]+[A-Za-z0-9]*)|([A-Za-z]+\\d+[A-Za-z0-9]*)$";
		return acc.matches(reg);
	}
}
