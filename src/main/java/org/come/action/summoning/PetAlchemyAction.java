package org.come.action.summoning;
/**
 * 炼妖
 * @author 叶豪芳
 * @date 2018年1月12日 下午9:06:59
 * 
 */ 
import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.PetAlchemyBean;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class PetAlchemyAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 接收召唤兽信息
		PetAlchemyBean petAlchemyBean = GsonUtil.getGsonUtil().getgson().fromJson(message, PetAlchemyBean.class);
		
		// 如果是初始化召唤兽信息，修改召唤兽信息为初始状态
		if( petAlchemyBean.getGoodstable().getType() == 701 ){

		}else{
			// 修改宠物信息
			AllServiceUtil.getRoleSummoningService().updateRoleSummoning(petAlchemyBean.getRoleSummoning());
		}
		// 删除炼妖石
		AllServiceUtil.getGoodsTableService().deleteGoodsByRgid(petAlchemyBean.getGoodstable().getRgid());
		
		// 添加物品
		AllServiceUtil.getGoodsrecordService().insert(petAlchemyBean.getGoodstable(), null, 1, 12);
	}
}
