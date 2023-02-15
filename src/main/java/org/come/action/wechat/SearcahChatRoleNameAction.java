package org.come.action.wechat;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.Role_bean;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 根据角色名查找好友
 * @author Administrator
 *
 */
public class SearcahChatRoleNameAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		if(message == null) return;
		// 查找角色信息
		RoleTable roleInfo = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(message);
		if( roleInfo != null ){
			// 封装bean返回客户端
			Role_bean bean = new Role_bean();
			// 如果角色在线，取最新信息
			if( GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null ){
				LoginResult lastInfo = GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(roleInfo.getRolename()));
				bean.setGangname(lastInfo.getGangname());
				bean.setGrade(lastInfo.getGrade());
				bean.setRace_name(lastInfo.getRace_name());
				bean.setRole_id(lastInfo.getRole_id());
				bean.setRolename(lastInfo.getRolename());
				bean.setTitle(lastInfo.getTitle());
			}else{// 不在线
				bean.setGangname(roleInfo.getGangname());
				bean.setGrade(roleInfo.getGrade());
				bean.setRace_name(roleInfo.getRacename());
				bean.setRole_id(roleInfo.getRole_id());
				bean.setRolename(roleInfo.getRolename());
				bean.setTitle(roleInfo.getTitle());
			}
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().searcahChatRoleNameAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
		}
		
		
	}

}
