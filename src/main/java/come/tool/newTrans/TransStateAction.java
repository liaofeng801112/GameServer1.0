package come.tool.newTrans;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

public class TransStateAction implements IAction{
	static String MSG1=Agreement.getAgreement().PromptAgreement("你处于繁忙状态");
	static String MSG2=Agreement.getAgreement().PromptAgreement("对方处于繁忙状态");
	static String MSG3=Agreement.getAgreement().PromptAgreement("对方未开启交易");
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
//        if (true) {
//        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("禁止物品流通")); 
//			return;
//		}
		
//		请求交易0|角色名 同意交易1|角色名 取消交易2 锁定3 取消锁定4 确定5
		LoginResult login = GameServer.getAllLoginRole().get(ctx);
		if (login==null) {return;}
		String[] v=message.split("\\|");
		int zhi1=Integer.parseInt(v[0]);
		if (zhi1==0||zhi1==1) {
			if (login.getBooth_id()!=null||login.getFighting()!=0||TransUtil.isTrans(login.getRolename())) {
				SendMessage.sendMessageByRoleName(login.getRolename(),MSG1); 
				return;
			}
			ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(v[1]);
			if (ctx2==null) {return;}
			LoginResult login2 = GameServer.getAllLoginRole().get(ctx2);
			if (login2==null) {return;}
			if (zhi1==0) {
				RoleData roleData=RolePool.getRoleData(login2.getRole_id());
				if (roleData.getRoleSystem().getIsGood()==0) {
					SendMessage.sendMessageByRoleName(login.getRolename(),MSG3); 
					return;
				}	
			}
			if (login2.getBooth_id()!=null||login2.getFighting()!=0||TransUtil.isTrans(login2.getRolename())) {
				SendMessage.sendMessageByRoleName(login2.getRolename(),MSG2);   				
				return;
			}
			String sendMessage=Agreement.getAgreement().TransStateAgreement(zhi1+"|"+login.getRolename()+"|"+login.getRole_id());
			SendMessage.sendMessageByRoleName(login2.getRolename(),sendMessage); 
			if (zhi1==1) {
				sendMessage=Agreement.getAgreement().TransStateAgreement(zhi1+"|"+login2.getRolename()+"|"+login2.getRole_id());
				SendMessage.sendMessageByRoleName(login.getRolename(),sendMessage); 
			}
			if (zhi1==1) {TransUtil.launchTrans(login, login2);}
		}else if (zhi1==2) {
			TransUtil.removeTrans(login.getRolename());
		}else if (zhi1==3||zhi1==4||zhi1==5) {
			TransUtil.lockingTrans(login.getRolename(), zhi1);
		}
	}

}
