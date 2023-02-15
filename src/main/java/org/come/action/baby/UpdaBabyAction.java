package org.come.action.baby;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.Baby;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class UpdaBabyAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
	try {
		Baby baby = GsonUtil.getGsonUtil().getgson().fromJson(message,Baby.class);
		AllServiceUtil.getBabyService().updateBaby(baby); 	
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}

}
