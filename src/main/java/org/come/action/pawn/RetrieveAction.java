package org.come.action.pawn;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 取回物品
 * @author 叶豪芳
 * @date 2017年12月22日 下午11:26:46
 * 
 */ 
public class RetrieveAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {
			return;
		}
		// 获取典当的物品信息
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(message));
		if (goodstable==null) {
			return;
		}
		if (loginResult.getRole_id().compareTo(goodstable.getRole_id())!=0) {
			return;
		}
		if (goodstable.getUsetime()<=0||goodstable.getStatus()!=2) {
			return;
		}
		int sum=goodstable.getUsetime();
		// 添加记录
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, goodstable.getUsetime(), 11);
		if (EquipTool.canSuper(goodstable.getType())) {//可叠加
			List<Goodstable> lists=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsIDAndState(goodstable.getRole_id(),goodstable.getGoodsid(),0);
			if(lists.size() != 0){
				AllServiceUtil.getGoodsTableService().deleteGoodsByRgid(goodstable.getRgid());
				long v=goodstable.getQuality();
				goodstable=lists.get(0);
				if (v%2==1) {goodstable.setQuality(v);}
				goodstable.setUsetime(goodstable.getUsetime()+sum);
				AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			}else {// 新增一条物品为典当状态
				AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, null, null, 0);
			}
		}else {
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, null, null, 0);
		}
		// 返回客户端典当的物品
		String pawn = Agreement.getAgreement().pawnAgreement(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
		SendMessage.sendMessageToSlef(ctx, pawn);
	}

}
