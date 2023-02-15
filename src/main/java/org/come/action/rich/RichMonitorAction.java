package org.come.action.rich;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class RichMonitorAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		InputBean InputBean=GsonUtil.getGsonUtil().getgson().fromJson(message, InputBean.class);
		if (InputBean.getType()==2) {
			Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(InputBean.getId());
			if (goodstable!=null) {
				// 单独返回的消息
				String messages = Agreement.getAgreement().richMAgreement(InputBean.getType(),GsonUtil.getGsonUtil().getgson().toJson(goodstable));
				SendMessage.sendMessageToSlef(ctx,messages);
			}
		}else if (InputBean.getType()==3) {
			RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(InputBean.getId());
			if (pet!=null) {
				// 单独返回的消息
				String messages = Agreement.getAgreement().richMAgreement(InputBean.getType(),GsonUtil.getGsonUtil().getgson().toJson(pet));
				SendMessage.sendMessageToSlef(ctx,messages);
			}
		}else if (InputBean.getType()==4) {
			Lingbao lingbao=AllServiceUtil.getLingbaoService().selectLingbaoByID(InputBean.getId());
			if (lingbao!=null) {
				// 单独返回的消息
				String messages = Agreement.getAgreement().richMAgreement(InputBean.getType(),GsonUtil.getGsonUtil().getgson().toJson(lingbao));
				SendMessage.sendMessageToSlef(ctx,messages);
			}
		}
	}

}
