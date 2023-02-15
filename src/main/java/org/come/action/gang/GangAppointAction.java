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
/**
 * 帮派职位任命,客户端发来角色名|帮派职务,修改角色信息
 * @author 叶豪芳
 * @date 2017年12月22日 上午1:09:29
 * 
 */ 
public class GangAppointAction implements IAction{

	private String[] POSTS=new String[]{"护法","长老","堂主","香主","精英","帮众"};
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (!roleInfo.getGangpost().equals("帮主")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你不是帮主无法调整职位"));	
			return;
		}
		String[] v=message.split("\\|");
		BigDecimal roleId=new BigDecimal(v[0]);
		if (roleId.compareTo(roleInfo.getRole_id())==0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("无法调整你自己"));	
			return;
		}
		String post=v[1];
		boolean is=true;
		for (int i = 0; i < POSTS.length; i++) {
			if (post.equals(POSTS[i])) {
				is=false;
				break;
			}
		}
		if (is) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没有这个职位"));	
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
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("他不是你们帮的成员无法调整他的职位"));	
			return;
		}
		roleTable.setGangpost(post);
		AllServiceUtil.getRoleTableService().updateGang(roleTable);
		if (result!=null) {
			result.setGangpost(post);
			//发送通知完成职位调整
			GangChangeBean gangChangeBean=new GangChangeBean(result,"你的职位调整为#G"+post);
			SendMessage.sendMessageByRoleName(result.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));
		}
	
	}
}
