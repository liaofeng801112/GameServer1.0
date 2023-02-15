package org.come.action.title;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.TitleBean;
import org.come.entity.Titletable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Title.TitleUtil;

/**
 * 更改称谓，显示称谓列表
 * @author 叶豪芳
 * @date 2017年12月25日 下午2:43:35
 * 
 */ 
public class TitleListAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取角色的角色ID
		BigDecimal roleID = GameServer.getAllLoginRole().get(ctx).getRole_id();
		
		// 根据角色ID查找角色所有称谓
		List<Titletable> titletables = AllServiceUtil.getTitletableService().selectRoleAllTitle(roleID);
		titletables=TitleUtil.getTitles(roleID, titletables);
		// 返回客户端所有称谓
		TitleBean titleBean = new TitleBean();
		titleBean.setTitletables(titletables);
		
		String msg = Agreement.getAgreement().TitleListAgreement(GsonUtil.getGsonUtil().getgson().toJson(titleBean));
		SendMessage.sendMessageToSlef(ctx,msg);
		
	}

}
