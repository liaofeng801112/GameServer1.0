package org.come.action.bring;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;

/**离婚*/
public class UnMarryAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得自己的信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		ChannelHandlerContext ctx2=null;
		LoginResult otherRole =null;// 获得对方的信息
		if (roleInfo.getMarryObject()!=null) {
			ctx2=GameServer.getRoleNameMap().get(roleInfo.getMarryObject());
		}
		if (ctx2!=null) {
			otherRole = GameServer.getAllLoginRole().get(ctx2);
		}
		// 在线
		if( otherRole != null ){
			// 修改结婚对象为空
			SendMessage.sendMessageByRoleName(otherRole.getRolename(), Agreement.getAgreement().unMarry(roleInfo.getRolename()));
			otherRole.setMarryObject(null);
		}else{// 不在线
			try {
				// 查找对象信息
				RoleTable marryRole = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(roleInfo.getMarryObject());
				if (marryRole!=null) {
					// 修改结婚对象为空
					marryRole.setMarryObject(null);
					// 称号如果包含结婚对象也设为空
					if( marryRole.getTitle().indexOf(roleInfo.getRolename()) != -1 ){
						marryRole.setTitle(null);
					}
					AllServiceUtil.getRoleTableService().updateByPrimaryKey(marryRole);			
				}	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		// 修改结婚对象为空
		SendMessage.sendMessageByRoleName(roleInfo.getRolename(),  Agreement.getAgreement().unMarry(roleInfo.getMarryObject()));
		roleInfo.setMarryObject(null);
		
	}

}
