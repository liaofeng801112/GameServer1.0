package org.come.action.reward;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.RewardDrawingBean;
import org.come.entity.Goodstable;
import org.come.entity.RewardHall;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 投放赏功堂物品
 * @author Administrator
 *
 */
public class ThrowinarticleAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取投放物品
		Goodstable goodstable = GsonUtil.getGsonUtil().getgson().fromJson(message, Goodstable.class);
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(goodstable.getRgid());
		if (good==null) {return;}
		if (good.getUsetime()<goodstable.getUsetime()) {
			return;
		}
		good.setUsetime(goodstable.getUsetime());
		goodstable=good;
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 创建赏公堂对象
		RewardHall hall = new RewardHall();
		hall.setGoodstable(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
		hall.setThrowtime(new Date());
		hall.setRoleId(roleInfo.getRole_id());
		// 添加赏公堂物品
		AllServiceUtil.getRewardHallMallService().insert(hall);
		
		// 缓存添加赏公堂物品
		GameServer.rewardList.add(hall);
		
		// 返回客户端消息
		RewardDrawingBean bean = new RewardDrawingBean();
		bean.setRewardHall(hall);
		SendMessage.sendMessageToAllRoles(Agreement.getAgreement().drawnitemsAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
		
	}

}
