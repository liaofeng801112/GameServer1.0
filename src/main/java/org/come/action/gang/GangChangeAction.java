package org.come.action.gang;

import java.math.BigDecimal;

import io.netty.channel.ChannelHandlerContext;

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
 * 帮派退位让贤,修改双方的职务
 * @author 叶豪芳
 * @date 2017年12月22日 上午1:11:08
 * 
 */ 
public class GangChangeAction implements IAction{
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (!roleInfo.getGangpost().equals("帮主")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你不是帮主无法卸任"));	
			return;
		}
		BigDecimal roleId=new BigDecimal(message);
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
		gangDomain.upGangMaster(roleTable.getRolename());
		
		RoleTable role=new RoleTable(0,roleInfo);
		//修改帮主之后的职务
		roleInfo.setGangpost(roleTable.getGangpost());
		role.setGangpost(roleTable.getGangpost());
		AllServiceUtil.getRoleTableService().updateGang(role);
		//修改职位为帮主
		if (result!=null) {
			result.setGangpost("帮主");	
		}
		roleTable.setGangpost("帮主");
		AllServiceUtil.getRoleTableService().updateGang(roleTable);
		
		//发送调整通知
		GangChangeBean gangChangeBean=new GangChangeBean(result,"你的职位调整为#G帮主");
		SendMessage.sendMessageByRoleName(result.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));
		gangChangeBean=new GangChangeBean(roleInfo,"你的职位调整为#G"+roleInfo.getGangpost());
		SendMessage.sendMessageByRoleName(roleInfo.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));
		
		
	}

}
