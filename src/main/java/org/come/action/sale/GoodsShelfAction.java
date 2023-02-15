package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Message;
import org.come.entity.Record;
import org.come.entity.RoleSummoning;
import org.come.entity.Salegoods;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Mixdeal.AnalysisString;

/**
 * 商品上架
 * @author Administrator
 */
public class GoodsShelfAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 手续费
		BigDecimal middlePay = new BigDecimal(1000000);
		
		// 获取对象
		Salegoods salegoods = GsonUtil.getGsonUtil().getgson().fromJson(message, Salegoods.class);

		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 扣除手续费
		if (roleInfo.getGold().compareTo(middlePay) >= 0) {

			roleInfo.setGold(roleInfo.getGold().subtract(middlePay));
			MonitorUtil.getMoney().useD(middlePay.longValue());
			// 条件分类
			String contiontype = "0";
			// 皮肤
			String saleskin = "";
			// 商品名称
			String salename = "";
			// 根据类型查找表数据
			if (salegoods.getSaletype() == 3 || salegoods.getSaletype() == 5) {// 物品表
				// 查找该物品
				Goodstable goods = AllServiceUtil.getGoodsTableService().getGoodsByRgID(salegoods.getOtherid());
				if (goods==null||EquipTool.canSuper(goods.getType())||goods.getRole_id().compareTo(roleInfo.getRole_id())!=0) {
					return;
				}
				if (AnalysisString.jiaoyi(goods.getQuality())) {
					StringBuffer buffer=new StringBuffer();
					buffer.append(roleInfo.getRole_id());
					buffer.append("欲藏宝阁上架禁交易物品:");
					buffer.append(goods.getRgid());
					buffer.append(":");
					buffer.append(goods.getGoodsname());
					AllServiceUtil.getRecordService().insert(new Record(5,buffer.toString()));
					return;
				}
				contiontype = goods.getType()+"";
				saleskin = goods.getSkin();
				salename = goods.getGoodsname();
				AllServiceUtil.getGoodsrecordService().insert(goods, null, 1, 13);
				// 修改物品角色ID为负
				AllServiceUtil.getGoodsTableService().updateGoodsIndex(goods, goods.getRole_id().multiply(new BigDecimal(-1)), null, null);
			} else if (salegoods.getSaletype() == 4) {// 召唤兽表
				// 查找该召唤兽
				RoleSummoning pet = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(salegoods.getOtherid());
				if (pet==null||pet.getRoleid().compareTo(roleInfo.getRole_id())!=0) {
					return;
				}
				contiontype = pet.getSsn();
				saleskin = pet.getSummoningskin();
				// 根据召唤兽ID查找召唤兽获取召唤兽名称
				RoleSummoning roleSummoning = GameServer.getAllPet().get(new BigDecimal(pet.getSummoningid()));
				if( roleSummoning == null ){
					System.out.println("表格召唤兽信息为空"+pet.getSummoningid());
//					return;
					salename = pet.getSummoningname();
				}else {
					salename = roleSummoning.getSummoningname();
				}
				// 修改召唤兽角色ID为负
				AllServiceUtil.getRoleSummoningService().updateRoleSummoningIndex(pet, pet.getRoleid().multiply(new BigDecimal(-1)));
                
				// 获取召唤兽内丹饰品ID,修改角色ID为负
				List<BigDecimal> goods=pet.getGoods();
				if (goods!=null) {
					for (BigDecimal bigDecimal : goods) {
						Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(bigDecimal);
                        AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, goodstable.getRole_id().multiply(new BigDecimal(-1)), null, null);
					}	
				}	
			} else if( salegoods.getSaletype() == 6 ){// 灵宝表
				// 查找该灵宝
				Lingbao lingbao = AllServiceUtil.getLingbaoService().selectByPrimaryKey(salegoods.getOtherid());
				if (lingbao==null||lingbao.getRoleid().compareTo(roleInfo.getRole_id())!=0) {
					return;
				}
				contiontype = lingbao.getBaotype();
				saleskin = lingbao.getSkin();
				salename = lingbao.getBaoname();
				// 修改物品角色ID为负
				AllServiceUtil.getLingbaoService().updateLingbaoIndex(lingbao, lingbao.getRoleid().multiply(new BigDecimal(-1)));
				// 修改灵宝符石
				if( lingbao.getFushis() != null && !"".equals(lingbao.getFushis()) ){
					String[] baos = lingbao.getFushis().split("\\|");
					for (String string : baos) {
						Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(string));
					    AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, goodstable.getRole_id().multiply(new BigDecimal(-1)), null, null);
					}
				}
			}else{
				// 银票
				saleskin = "8";
				salename = "大话币";
				
				// 扣除大话币
				if (roleInfo.getGold().compareTo(salegoods.getOtherid()) >= 0) {

					roleInfo.setGold(roleInfo.getGold().subtract(salegoods.getOtherid()));
				}else{
					// 提示金币不足
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().tipAgreement("金币不足"));
					return;
				}
			}

			// 设置上架角色
			salegoods.setRoleid(roleInfo.getRole_id());
			// 设置条件分类
			salegoods.setContiontype(contiontype);
			// 设置皮肤
			salegoods.setSaleskin(saleskin);
			// 设置商品名称
			salegoods.setSalename(salename);
			// 设置上架时间
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowdayTime = dateFormat.format(new Date());
			Date nowDate = null;
			try {
				nowDate = dateFormat.parse(nowdayTime);
				salegoods.setUptime(nowDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 设置上下架标识 上架
			salegoods.setFlag(2);

			// 判断是否有绑定买家ID
			if (salegoods.getBuyrole().compareTo(new BigDecimal(0)) != 0) {

				// 提示买家
				Message message2 = new Message();
				message2.setRoleid(salegoods.getBuyrole());
				message2.setMescontent(salename + "指定你购买");
				message2.setSaleid(salegoods.getSaleid());
				message2.setGettime(nowDate);
				
				AllServiceUtil.getMessageService().insert(message2);
			}
			
			// 插入商品
			AllServiceUtil.getSalegoodsService().insert(salegoods);
			
			// 提示上架成功
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().tipAgreement("上架成功"));

		} else {
			// 提示金币不足
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().tipAgreement("金币不足"));
		}
	}

}
