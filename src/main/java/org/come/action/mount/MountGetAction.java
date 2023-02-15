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
 * 获得坐骑
 * @author Administrator
 *
 */
public class MountGetAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		Mount mount2 = GsonUtil.getGsonUtil().getgson().fromJson(message, Mount.class);
		
		// 加入坐骑
		AllServiceUtil.getMountService().insertMount(mount2);
		
//		if( mount2.getMountskill() != null && mount2.getMountskill().size() != 0 ){
//			for (MountSkill mountSkill : mount2.getMountskill()) {
//				mountSkill.setMid(mount2.getMid());
//				AllServiceUtil.getMountskillService().insertMountskills(mountSkill);
//			}
//		}
		BigDecimal roleID = GameServer.getAllLoginRole().get(ctx).getRole_id();
		List<Mount> mounts = AllServiceUtil.getMountService().selectMountsByRoleID(roleID);
//		if( mounts != null ){
//			for (Mount mount : mounts) {
//				List<MountSkill> mountskill = AllServiceUtil.getMountskillService().selectMountskillsByMountid(mount.getMid());
//				mount.setMountskill(mountskill);
//			}
//		}
		// 放进坐骑返回的bean中
		MountResult mountResult = new MountResult();
		mountResult.setMounts(mounts);
		// 发送给客户端
		String msg = Agreement.getAgreement().MountAgreement(GsonUtil.getGsonUtil().getgson().toJson(mountResult));
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
