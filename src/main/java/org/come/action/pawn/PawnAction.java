package org.come.action.pawn;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.PawnGoodsMessBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 典当物品,收到物品信息,修改物品状态为2(典当),返回典当物品
 * @author 叶豪芳
 * @date 2017年12月22日 上午1:15:26
 * 
 */ 
public class PawnAction implements IAction{
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取典当的物品信息
		PawnGoodsMessBean pawnGoods = GsonUtil.getGsonUtil().getgson().fromJson(message, PawnGoodsMessBean.class);
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(pawnGoods.getRgid());
		if (goodstable==null) {
			return;
		}
		// 判断使用次数,修改物品状态为典当状态2
		int sum1=goodstable.getUsetime();
		int sum2=pawnGoods.getNumber();
		if (sum1<=0||sum2<=0||sum2>sum1) {return;}
		// 添加记录
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, sum2, 10);	
		// 剩余的使用次数
		int userTime = sum1 - sum2;
		if(!EquipTool.canSuper(goodstable.getType())){//消耗完了 或者不可叠加
			// 修改数据库 修改物品状态
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, null, null, 2);
		}else{// 修改数据库
			List<Goodstable> lists=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsIDAndState(goodstable.getRole_id(),goodstable.getGoodsid(),2);
			if(lists.size() != 0){
				goodstable.setUsetime(userTime);
				AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
				goodstable=lists.get(0);
				goodstable.setUsetime(goodstable.getUsetime()+sum2);
				AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			}else {
				if (userTime > 0) {
					goodstable.setUsetime(userTime);
					AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
					goodstable=goodstable.clone();
					goodstable.setStatus(2);// 设置物品状态
					goodstable.setUsetime(sum2);// 设置使用次数
					AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				}else {
					goodstable.setUsetime(sum2);// 设置使用次数
					AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, null, null, 2);
				}
			}
		}
		// 返回客户端典当的物品
		String pawn = Agreement.getAgreement().pawnAgreement(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
		SendMessage.sendMessageToSlef(ctx, pawn);
	}

}
