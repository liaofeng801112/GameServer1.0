package org.come.action.gang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.GangChangeBean;
import org.come.bean.LoginResult;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

/**
 * 退出帮派
 * 
 * @author 叶豪芳
 * @date 2017年12月20日 上午11:36:51
 * 
 */
public class GangRetreatAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo.getGang_id().intValue() == 0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你没有帮派退出什么帮派?"));
			return;
		}
		if (roleInfo.getGangpost().equals("帮主")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮主你先卸任才能退出帮派"));
			return;
		}
		GangDomain gangDomain=GangUtil.getGangDomain(roleInfo.getGang_id());
		gangDomain.removeGangRole();
		gangDomain.downGangRole(roleInfo.getRole_id());		
		
		RoleTable roleTable=new RoleTable(0, roleInfo);
		roleTable.setGang_id(new BigDecimal(0));
		roleTable.setGangpost(null);
		roleTable.setGangname(null);
		AllServiceUtil.getRoleTableService().updateGang(roleTable);

		roleInfo.setGang_id(new BigDecimal(0));
		roleInfo.setGangpost(null);
		roleInfo.setGangname(null);
		//发送通知完成职位调整
		
		String sendMes=Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleInfo.getRoleShow()));
		SendMessage.sendMessageToMapRoles(ctx,roleInfo.getMapid(),sendMes);	
		GangChangeBean gangChangeBean=new GangChangeBean(roleInfo,"成功退出帮派");
		SendMessage.sendMessageByRoleName(roleInfo.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));
		
	}

}
