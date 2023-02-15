package org.come.action.login;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.AccountBinding;
import org.come.bean.BindingReturn;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 获取绑定信息
 * 
 * @author zengr
 * 
 */
public class Account_Binding implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		AccountBinding accountBinding = GsonUtil.getGsonUtil().getgson().fromJson(message, AccountBinding.class);

		BindingReturn bindingReturn = new BindingReturn();
		// binding 进行绑定 / getbinding 获取是否有绑定
		String type = accountBinding.getType();
		if ("getbinding".equals(type)) {
			String flag = accountBinding.getFlag();
			UserTable usertable = AllServiceUtil.getUserTableService().selectByFlag(flag);
			if (usertable == null) {
				// 该账号在该区没有绑定游戏账号
				bindingReturn.setType("gobinding");
			} else {
				// 已有绑定
				Short activity = usertable.getActivity();
				if (activity != 0) {
					// 该账号已被封
					bindingReturn.setType("accountError");
					bindingReturn.setMessage("该账号在该区已被封");
				} else {
					// 进入游戏
					bindingReturn.setType("goGame");
					bindingReturn.setUsertable(usertable);
				}
			}
		} else if ("binding".equals(type)) {
			// 进行绑定
			String username = accountBinding.getUsername();
			String password = accountBinding.getPassword();
			String safely = accountBinding.getSafely();
			String flag = accountBinding.getFlag();
			UserTable usertable = AllServiceUtil.getUserTableService().selectByBinding(username, password, safely);
			if (usertable != null) {
				//
				Short activity = usertable.getActivity();
				if (activity != 0) {
					// 账号已被封
					bindingReturn.setType("accountError");
					bindingReturn.setMessage("该账号已被封");
				} else {
					if (usertable.getFlag() != null) {
						// 该账号已绑定
						bindingReturn.setType("accountError");
						bindingReturn.setMessage("该账号已绑定");
					} else {
						// 绑定账号
						// usertable.setUsername(username);
						// usertable.setUserpwd(password);
						// usertable.setSafety(safely);
						usertable.setFlag(flag);
						int updateByBinding = AllServiceUtil.getUserTableService().updateByBinding(usertable);
						if (updateByBinding > 0) {
							// 绑定成功
							bindingReturn.setType("goGame");
							bindingReturn.setUsertable(usertable);
						} else {
							// 绑定失败
							bindingReturn.setType("accountError");
							bindingReturn.setMessage("绑定失败");
						}
					}
				}
			} else {
				// 该区未找到该账号
				bindingReturn.setType("accountError");
				bindingReturn.setMessage("该区未找到该账号");
			}
		} else if ("autoBinding".equals(type)) {
			// 服务器自动绑定
			InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
			// 数据获取
			String username = accountBinding.getUsername();// 账号
			String password = accountBinding.getPassword();// 密码
			String safely = accountBinding.getSafely();// 安全码
			String flag = accountBinding.getFlag();// 账号标识
			String clientIP = insocket.getAddress().getHostAddress();// 用户ip
			String tuiji = accountBinding.getTuiji();// 推荐码
			String phone = accountBinding.getPhone();
			
			UserTable usertable = AllServiceUtil.getUserTableService().selectByFlag(flag);
			if(usertable != null){
				return;
			}

			while (true) {
				UserTable result = AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(username, null);
				if (result == null) {
					break;
				} else {
					username += "zr";
					continue;
				}
			}

			List<BigDecimal> sid = AllServiceUtil.getOpenareatableService().selectTuijiNum(tuiji);
			// 设置账号信息
			UserTable userTable = new UserTable();
			userTable.setUser_id(RedisCacheUtil.getUser_pk());
			userTable.setUsername(username);
			userTable.setUserpwd(password);
			userTable.setSafety(safely);
			userTable.setFlag(flag);
			userTable.setRegisterip(clientIP);
			userTable.setTuiji(tuiji);
			userTable.setQid(sid.get(0));
			userTable.setPhonenumber(phone);
			
			// 注册新用户
			int isSuccess = AllServiceUtil.getUserTableService().insertIntoUser(userTable);
			if (isSuccess > 0) {
				// 绑定成功
				bindingReturn.setType("goGame");
				bindingReturn.setUsertable(userTable);
			} else {
				// 自动绑定失败
				bindingReturn.setType("accountError");
				bindingReturn.setMessage("自动绑定失败");
			}
		}

		// 返回结果
		String result = GsonUtil.getGsonUtil().getgson().toJson(bindingReturn);
		String res = Agreement.getAgreement().Account_BindingAgreement(result);
		SendMessage.sendMessageToSlef(ctx, res);
	}

}
