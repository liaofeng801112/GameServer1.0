package org.come.action.mount;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Mount;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
/**修改坐骑属性*/
public class MountUpdateAction implements IAction {
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		// 接收坐骑信息
		Mount mount = GsonUtil.getGsonUtil().getgson().fromJson(message, Mount.class);
		Mount mountRedis = AllServiceUtil.getMountService().selectMountsByMID(mount.getMid());
		if (mountRedis==null||data==null||loginResult.getRole_id().compareTo(mountRedis.getRoleid())!=0) {
			return;
		}
		mountRedis.setSid(mount.getSid());
		mountRedis.setOthrersid(mount.getOthrersid());
		mountRedis.setSid3(mount.getSid3());
		AllServiceUtil.getMountService().updateMountRedis(mountRedis);
		data.MPet(mount,true);
	}

}
