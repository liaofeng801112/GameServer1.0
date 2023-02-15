package org.come.action.gang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.come.action.IAction;
import org.come.bean.GangResultBean;
import org.come.bean.LoginResult;
import org.come.entity.Gang;
import org.come.entity.Gangapplytable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;
/**
 * 进入帮派，客户端传来帮派ID,返回帮派信息
 * @author 叶豪芳
 * @date 2017年12月25日 下午2:30:16
 * 
 */ 
public class IntoGangAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得帮派ID查找帮派信息
		BigDecimal gangid = GameServer.getAllLoginRole().get(ctx).getGang_id();
		if (gangid.intValue()==0) {
			return;
		}
		GangDomain domain=GangUtil.getGangDomain(gangid);
		Gang gang = domain.getGang();
		//根据帮派ID查找帮派成员
		List<LoginResult> members = new ArrayList<LoginResult>();
		List<LoginResult> roleTables = AllServiceUtil.getRoleTableService().findGangManberByGangID(gangid);
		//添加不在线的角色
		for (int i=0,length=roleTables.size();i<length;i++) {
			LoginResult loginResult=roleTables.get(i);
			ChannelHandlerContext gangCtx=GameServer.getRoleNameMap().get(loginResult.getRolename());
			LoginResult result=gangCtx!=null?GameServer.getAllLoginRole().get(gangCtx):null;
			if (result!=null) {
				loginResult.setRolename(result.getRolename());
				loginResult.setGangpost(result.getGangpost());
				loginResult.setRace_name(result.getRace_name());
				loginResult.setGrade(result.getGrade());
				loginResult.setContribution(result.getContribution());
				loginResult.setUptime("在线");
				members.add(0,loginResult);
			}else {
				members.add(loginResult);
			}
		}
		// 根据帮派ID查找帮派申请列表
		List<Gangapplytable> gangapplytables = AllServiceUtil.getGangapplyService().getGangapplytables(gangid);
		// 返回客户端帮派bean
		GangResultBean gangResultBean = new GangResultBean();
		gangResultBean.setGang(gang);//帮派信息
		gangResultBean.setGangGroup(domain.getGangGroup());
		gangResultBean.setRoleTables(members);//成员信息
		gangResultBean.setGangapplytables(gangapplytables);//申请列表
		// 将帮派信息返回客户端
		String msg = Agreement.getAgreement().IntogangAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangResultBean));
		SendMessage.sendMessageToSlef(ctx, msg);
		
	}

}
