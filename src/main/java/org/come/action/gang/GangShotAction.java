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

import come.tool.Role.RolePool;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;
/**
 * 踢出帮派
 * @author 叶豪芳
 * @date 2017年12月22日 上午1:03:00
 * 
 */ 
public class GangShotAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (!roleInfo.getGangpost().equals("帮主")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你不是帮主无法权限踢人"));	
			return;
		}
		BigDecimal roleId=new BigDecimal(message);
		if (roleInfo.getRole_id().compareTo(roleId)==0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("自己踢自己?"));	
			return;
		}
		LoginResult result=RolePool.getLoginResult(roleId);
		RoleTable roleTable=null;
		if (result!=null) {//在线调整职位
			roleTable=new RoleTable(0,result);
		}else {//离线调整职位
			roleTable=AllServiceUtil.getRoleTableService().selectGang(roleId);
			if (roleTable==null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没有这个玩家"));	
				return;
			}
		}
		if (roleInfo.getGang_id().compareTo(roleTable.getGang_id())!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("他不是你们帮的成员无法卸任给他"));	
			return;
		}
		GangDomain gangDomain=GangUtil.getGangDomain(roleInfo.getGang_id());
		gangDomain.removeGangRole();
		
		roleTable.setGang_id(new BigDecimal(0));
		roleTable.setGangpost(null);
		roleTable.setGangname(null);
		AllServiceUtil.getRoleTableService().updateGang(roleTable);
		if (result!=null) {
			gangDomain.downGangRole(result.getRole_id());
			
			result.setGang_id(new BigDecimal(0));
			result.setGangpost(null);
			result.setGangname(null);
			//发送通知完成职位调整
			
			ChannelHandlerContext ctxA=GameServer.getRoleNameMap().get(result.getRolename());
			if (ctxA!=null) {
				String sendMes=Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(result.getRoleShow()));
				SendMessage.sendMessageToMapRoles(ctxA,result.getMapid(),sendMes);	
			}
			GangChangeBean gangChangeBean=new GangChangeBean(result,"你被请离帮派");
			SendMessage.sendMessageByRoleName(result.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));			
		}
	}

}
