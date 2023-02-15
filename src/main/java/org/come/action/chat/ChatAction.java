package org.come.action.chat;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.servlet.UserControlServlet;
import org.come.until.GsonUtil;

public class ChatAction implements IAction{
	public static String MSG=Agreement.getAgreement().PromptAgreement("未转不能发言");
	public static ConcurrentHashMap<BigDecimal, Integer> mapSize=new ConcurrentHashMap<>();
	public static List<String> ggs=new ArrayList<>();
	static{
		ggs.add("群48");
		ggs.add("10万元宝");
	}
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
//		if (true) {
//			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("禁止发言"));		
//			return;
//		}
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {return;}	
		NChatBean nChatBean = GsonUtil.getGsonUtil().getgson().fromJson(message, NChatBean.class);
		if (nChatBean.getId()==5) {//系统信息
			String msg = Agreement.getAgreement().chatAgreement(message);
			SendMessage.sendMessageToAllRoles(msg);
			return;
		}	
		if(UserControlServlet.isNoTalk(ctx)) return;
		if (nChatBean.getId()!=1&&roleInfo.getGrade()<=102&&roleInfo.getPaysum().intValue()<100) {
			SendMessage.sendMessageToSlef(ctx,MSG);				
			return;
		}
		nChatBean.setRoleId(roleInfo.getRole_id());
		nChatBean.setRole(roleInfo.getRolename());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(nChatBean));
		for (int i = ggs.size()-1; i>=0; i--) {
			if (nChatBean.getMessage().indexOf(ggs.get(i))!=-1) {
				Integer size=mapSize.get(roleInfo.getRole_id());
				if (size==null) {
					size=0;
				}
	            if (size>=1200) {
					if (GameServer.random.nextInt(150)==0) {
						ParamTool.ACTION_MAP.get("accountstop").action(ctx, roleInfo.getUserName());
						return;
					}
				}else {
					size++;
					mapSize.put(roleInfo.getRole_id(),size);
				}
				SendMessage.sendMessageToSlef(ctx,msg);
				return;
			}
		}		
		if (nChatBean.getId()==3||nChatBean.getId()==4) {//世界和喇叭
			SendMessage.sendMessageToAllRoles(msg);
		}else if (nChatBean.getId()==0) {//0当前
			SendMessage.sendMessageToMapRoles(roleInfo.getMapid(), msg);
		}else if (nChatBean.getId()==1) {//1队伍
			String[] teams=roleInfo.getTeam().split("\\|");
			for (int i = 0; i < teams.length; i++) {
				SendMessage.sendMessageByRoleName(teams[i], msg);
			}
		}else if (nChatBean.getId()==2) {//2帮派
			if (roleInfo.getGang_id()!=null) {
				SendMessage.sendMessageToGangRoles(roleInfo.getGang_id(), msg);	
			}
		}
	}
}
