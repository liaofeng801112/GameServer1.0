package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 绑定手机号
 * @author Administrator
 *
 */
public class BindingMobileAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获得用户信息
		UserTable table = GsonUtil.getGsonUtil().getgson().fromJson(message, UserTable.class);
		
		// 修改用户信息，绑定手机号
		AllServiceUtil.getUserTableService().updateUser(table);

	}

}
