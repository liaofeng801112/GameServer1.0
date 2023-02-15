package org.come.action.pack;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.PackGiftBean;
import org.come.protocol.AgreementUtil;
import org.come.protocol.ParamTool;
import org.come.until.GsonUtil;
/**
 * 背包礼包
 * @author 叶豪芳
 * @date 2017年12月27日 下午8:38:33
 * 
 */ 
public class PackGiftAction implements IAction {
     
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得客户端发来的礼包的物品ID
		PackGiftBean packGiftBean = GsonUtil.getGsonUtil().getgson().fromJson(message, PackGiftBean.class);
		ParamTool.ACTION_MAP.get(AgreementUtil.user).action(ctx,packGiftBean.getGoodstable().getRgid().toString());
	}

}
