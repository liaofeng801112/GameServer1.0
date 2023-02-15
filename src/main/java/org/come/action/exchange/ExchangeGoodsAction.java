package org.come.action.exchange;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodsexchange;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.DropUtil;
import come.tool.Stall.AssetUpdate;
/**
 * 兑换码兑换物品
 * @author Administrator
 */
public class ExchangeGoodsAction implements IAction {
	static String CHECKTS1=Agreement.getAgreement().PromptAgreement("你不属于补偿领取范围内");
	static String CHECKTS2=Agreement.getAgreement().PromptAgreement("你已经领取过补偿");
	static String CHECKTS3=Agreement.getAgreement().PromptAgreement("你获得补偿礼包");	
	@Override
	public synchronized void action(ChannelHandlerContext ctx, String message) {

		// 获得用户信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {return;}
		Compensation compensation=ExchangeUtil.getCompensation(message);
		if (compensation!=null) {
			int v=compensation.receive(roleInfo.getRole_id());
			if (v==0) {
				SendMessage.sendMessageToSlef(ctx,CHECKTS1);	
			}else if (v==2) {
				SendMessage.sendMessageToSlef(ctx,CHECKTS2);	
			}else if (v==1) {
				// 获得该物品（推广礼盒）
				Goodstable goodstable = GameServer.getGood(compensation.getGoodId());
				goodstable.setRole_id(roleInfo.getRole_id());
				goodstable.setUsetime(1);
				// 插入数据库
				AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				// 发送客户端
				AssetUpdate assetUpdate = new AssetUpdate();
				assetUpdate.setGood(goodstable);
				assetUpdate.setMsg(1+"个"+goodstable.getGoodsname());
				assetUpdate.setType(AssetUpdate.INTEGRATION);
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  				
			}
			return;
		}
//		if (DropUtil.isDH(message, roleInfo)) {return;}
		// 收到兑换码获得兑换信息
		Goodsexchange exchange = AllServiceUtil.getGoodsExchangeService().selectByPrimaryKey(message);
		if(exchange != null){
			// 判断是否被领
			if( exchange.getFlag() == 0 ){
				
				// 修改兑换状态和兑换时间
				exchange.setFlag(1);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowdayTime = dateFormat.format(new Date());
				try {
					Date nowDate = dateFormat.parse(nowdayTime);
					exchange.setOuttime(nowDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AllServiceUtil.getGoodsExchangeService().updateByPrimaryKeySelective(exchange);
				
				// 获得该物品（推广礼盒）
				Goodstable goodstable = GameServer.getAllGoodsMap().get(new BigDecimal(exchange.getGoodsid()));
				goodstable=goodstable.clone();
				goodstable.setRole_id(roleInfo.getRole_id());
				
				// 插入数据库
				AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				
				// 发送客户端
				AssetUpdate assetUpdate = new AssetUpdate();
				assetUpdate.setGood(goodstable);
				assetUpdate.setMsg(1+"个"+goodstable.getGoodsname());
				assetUpdate.setType(AssetUpdate.INTEGRATION);
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
				
			}else{
				String msg = Agreement.getAgreement().tipAgreement("该兑换码已失效！");
				SendMessage.sendMessageToSlef(ctx, msg);
			}
		}else{
			String msg = Agreement.getAgreement().tipAgreement("无效的兑换码！");
			SendMessage.sendMessageToSlef(ctx, msg);
		}
	}

}
