package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.PathPoint;
import org.come.bean.RoleMoveBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
/**
 * 角色移动
 * @author 叶豪芳
 * @date : 2017年11月30日 下午4:01:59
 */
public class RoleMoveAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得人物信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {
		     return;
		}
		RoleData roleData=RolePool.getRoleData(roleInfo.getRole_id());
		if (roleData==null) {
			return;
		}		
		// 客户端发来的人物移动信息
		RoleMoveBean roleMoveBean = GsonUtil.getGsonUtil().getgson().fromJson(message, RoleMoveBean.class);
		// 更新自己的角色信息
		PathPoint point=roleMoveBean.getPaths().get(roleMoveBean.getPaths().size()-1);
		roleInfo.setX(new Long(point.getX()));
		roleInfo.setY(new Long(point.getY()));
		// 添加移动人物名字
		roleMoveBean.setRole(roleInfo.getRolename());
		// 遍历map,根据地图ID获得用户输出流
		String msg = Agreement.getAgreement().moveAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleMoveBean));
		SendMessage.sendMessageToMapRoles(roleInfo.getMapid(), msg);
	}

}
