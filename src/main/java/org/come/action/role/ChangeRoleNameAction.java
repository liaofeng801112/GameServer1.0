package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.ChangeRoleNameBean;
import org.come.bean.LoginResult;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisControl;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
/**
 * 修改角色名字
 * @author Administrator
 *
 */
public class ChangeRoleNameAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取客户端发来的消息
		ChangeRoleNameBean changeRoleNameBean = GsonUtil.getGsonUtil().getgson().fromJson(message, ChangeRoleNameBean.class);
		// 新名字
		String newName = changeRoleNameBean.getNewName();
		// 旧名字
		String oldName = changeRoleNameBean.getOldName();
		// 判断角色名称是否存在
		RoleTable role = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(newName);
		// 不存在,修改成功
		if( null == role ){
			// 获得角色信息
			LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);

			// 修改角色名字
			roleInfo.setRolename(newName);
			RoleData data=RolePool.getRoleData(roleInfo.getRole_id());
			data.setLoginResult(roleInfo);
			// 添加角色名map
			GameServer.getRoleNameMap().put(roleInfo.getRolename(), ctx);

			// 根据地图ID存集合
			GameServer.getMapRolesMap().get(roleInfo.getMapid()).put(roleInfo.getRolename(), ctx);

			// 清除旧名字存的map
			GameServer.getRoleNameMap().remove(oldName);
			GameServer.getMapRolesMap().get(roleInfo.getMapid()).remove(oldName);

			// 删除消耗物品
			AllServiceUtil.getGoodsTableService().deleteGoodsByRgid(changeRoleNameBean.getRgid());

			// 修改成功
			changeRoleNameBean.setFlag(true);
			
			// 群发给其他用户
			String msg = Agreement.getAgreement().ChangeRoleNameAgreement(GsonUtil.getGsonUtil().getgson().toJson(changeRoleNameBean));
			SendMessage.sendMessageToMapRoles(roleInfo.getMapid(), msg);
			RoleTable roleTable=new RoleTable();
			roleTable.setRole_id(roleInfo.getRole_id());
			roleTable.setRolename(roleInfo.getRolename());
			roleTable.setTitle(roleInfo.getTitle());
			// 修改玩家数据
			AllServiceUtil.getRoleTableService().updateByPrimaryKey(roleTable);
			RedisControl.userController("R",roleInfo.getRole_id().toString(),RedisControl.CALTER,GsonUtil.getGsonUtil().getgson().toJson(roleInfo));
			
			// 判断结婚对象存在且不在线的时候
			if( roleInfo.getMarryObject() != null && GameServer.getRoleNameMap().get(roleInfo.getMarryObject()) == null ){
				// 查找结婚对象信息
				RoleTable marryRole = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(roleInfo.getMarryObject());
				// 如果结婚对象的称谓为它对象的名字也要修改
				if( marryRole.getTitle() != null && marryRole.getTitle().indexOf(oldName) != -1 ){
					marryRole.setTitle(marryRole.getTitle().replace(oldName, newName));
				}
				// 修改结婚对象新的结婚对象
				marryRole.setMarryObject(newName);
				// 修改结婚对象数据
				AllServiceUtil.getRoleTableService().updateByPrimaryKey(marryRole);
			}
		}else{
			// 修改失败
			changeRoleNameBean.setFlag(false);

			// 返回客户端
			String msg = Agreement.getAgreement().ChangeRoleNameAgreement(GsonUtil.getGsonUtil().getgson().toJson(changeRoleNameBean));
			SendMessage.sendMessageToSlef(ctx, msg);
		}

	}
}
