package org.come.action.wechat;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.Role_bean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 根据角色ID查找好友
 * @author Administrator
 *
 */
public class SearcahChatRoleIdAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取角色ID
		if(message == null) return;
		BigDecimal roleID = new BigDecimal(message);
		// 查找角色信息
		LoginResult roleInfo = AllServiceUtil.getRoleTableService().selectRoleID(roleID);
		if( roleInfo!= null ){
			Role_bean bean = new Role_bean();
			// 如果角色在线，取最新信息
			if( GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null ){
				roleInfo = GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(roleInfo.getRolename()));
			}
			
			
			// 封装bean返回客户端
			bean.setGangname(roleInfo.getGangname());
			bean.setGrade(roleInfo.getGrade());
			bean.setRace_name(roleInfo.getRace_name());
			bean.setRole_id(roleInfo.getRole_id());
			bean.setRolename(roleInfo.getRolename());
			bean.setTitle(roleInfo.getTitle());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().searcahChatRoleIdAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
		}
	}

}
