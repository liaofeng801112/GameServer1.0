package org.come.action.mount;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.MountResult;
import org.come.entity.Mount;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 坐骑状态,返回坐骑信息
 * @author 叶豪芳
 * @date 2017年12月22日 上午10:06:00
 * 
 */ 
public class MountAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
			// 根据角色ID查找坐骑列表
		BigDecimal roleID = GameServer.getAllLoginRole().get(ctx).getRole_id();
		List<Mount> mounts = AllServiceUtil.getMountService().selectMountsByRoleID(roleID);
		// 放进坐骑返回的bean中
		MountResult mountResult = new MountResult();
		mountResult.setMounts(mounts);
		// 发送给客户端
		String msg = Agreement.getAgreement().MountAgreement(GsonUtil.getGsonUtil().getgson().toJson(mountResult));
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
