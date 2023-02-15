package org.come.action.pack;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.GoodsResultArrBean;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class PackChangeAction implements IAction{

	public static int size;
	/**修改物品属性 物品数量只能减少或者不变*/
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		// 获得客户端发来的修改后的信息
		GoodsResultArrBean goodsResultArrBean = GsonUtil.getGsonUtil().getgson().fromJson(message, GoodsResultArrBean.class);
		// 修改物品信息
		List<Goodstable> goods = goodsResultArrBean.getList();
		if (goods == null){return;}// 判断修改的物品是否为空
		// 遍历物品列表如果使用次数为0删除物品
		for (Goodstable good : goods) {
			if (loginResult.getRole_id().compareTo(good.getRole_id())!=0) {
				WriteOut.addtxt("使用物品角色ID改变:"+loginResult.getRole_id()+":"+message,9999);
				ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(loginResult.getUserName()), loginResult.getUserName());
				return;
			}
			String value=AllServiceUtil.getGoodsTableService().updateGoodsNum(good,goodsResultArrBean.getI());
			if (value!=null) {
				WriteOut.addtxt("物品突变协议头数据:"+message,9999);
				size++;
				if (size%5==0) {
					ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(loginResult.getUserName()), loginResult.getUserName());	
				}else {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().serverstopAgreement());
				}
				return;
			}
			if (good.getType()!=750&&good.getType()!=0&&good.getType()!=50&&good.getType()!=49&&good.getType()!=2012&&good.getType()!=1) {
				// 内丹和药品类孩子属性的培养道具不记录
				AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 9);		
			}
		}
	}
}
