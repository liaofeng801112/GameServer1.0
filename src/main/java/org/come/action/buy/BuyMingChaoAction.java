package org.come.action.buy;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
/**
 * 购买冥钞
 * @author 叶豪芳
 * @date 2017年11月24日 上午12:09:07
 * 
 */ 
public class BuyMingChaoAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
//		// 获得角色id
//		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
//		BigDecimal roleid = loginResult.getRole_id();
//		// 获得冥钞
//		Goodstable goodstable = GameServer.getGood(new BigDecimal(192));
//
//		// 角色ID
//		goodstable.setRole_id(roleid);
//		// 插入数据库
//		AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
//		AddGoodUtil.addGood(ctx, goodstable);
//		
//		// 添加记录
//		AllServiceUtil.getGoodsrecordService().insert(goodstable,null,1, 0);
//		// 修改角色的金钱减5000000
//		loginResult.setGold(loginResult.getGold().subtract(new BigDecimal(100000)));
//		MonitorUtil.getMoney().useD(100000L);
	}

}
