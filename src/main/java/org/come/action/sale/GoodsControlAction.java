package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Collection;
import org.come.entity.CollectionExample;
import org.come.entity.CollectionExample.Criteria;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Message;
import org.come.entity.RoleSummoning;
import org.come.entity.Salegoods;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;

/**
 * 藏宝阁商品下架 重新上架 取回
 * 
 * @author Administrator
 * 
 */
public class GoodsControlAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 获取商品信息
		Salegoods goods = GsonUtil.getGsonUtil().getgson().fromJson(message, Salegoods.class);

		// 查找该商品
		Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(goods.getSaleid());
		if( roleInfo.getRole_id().compareTo(salegoods.getRoleid()) != 0 ){
			System.out.println("此物品:"+salegoods.getSalename()+",非本人所有:"+roleInfo.getRolename());
			return;
		}
		Integer selectFlag = AllServiceUtil.getSalegoodsService().selectFlag(salegoods.getSaleid());
		if(selectFlag == 3){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().tipAgreement("该商品已被下单"));
			return;
		}
		if (salegoods.getFlag() == 4) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().tipAgreement("该商品已被下单"));
			return;
		}
		// 如果有收藏，对收藏人的提示
		String tip = "";
		// 判断操作
		if (goods.getFlag() != 5) {
			// 已下单的商品不能下架上架
			// 上下架直接修改状态
			salegoods.setFlag(goods.getFlag());
			if (goods.getFlag() == 2) {
				// 为上架时修改上架时间
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
				tip = salegoods.getSalename() + "上架了";
			}else{
				tip = salegoods.getSalename() + "下架了";
			}
			AllServiceUtil.getSalegoodsService().updateByPrimaryKey(salegoods);
		} else if (goods.getFlag() == 5){
			tip = salegoods.getSalename() + "已被取回";
			// 取回时，删除商品，并且修改对应的数据表角色ID,发送该件商品回客户端
			AllServiceUtil.getSalegoodsService().deleteByPrimaryKey(salegoods.getSaleid());

			// 返回客户端的bean
			AssetUpdate update = new AssetUpdate();

			// 根据类型查找表数据
			if (salegoods.getSaletype() == 3 || salegoods.getSaletype() == 5) {// 物品表
				// 查找该物品
				Goodstable goods2 = AllServiceUtil.getGoodsTableService().getGoodsByRgID(salegoods.getOtherid());
				AllServiceUtil.getGoodsrecordService().insert(goods2, null, 1, 13);
				// 修改物品角色ID为负
				AllServiceUtil.getGoodsTableService().updateGoodsIndex(goods2, goods2.getRole_id().multiply(new BigDecimal(-1)), null, null);
				// 添加返回bean
				List<Goodstable> goodstables = new ArrayList<>();
				goodstables.add(goods2);
				update.setGoods(goodstables);

			} else if (salegoods.getSaletype() == 4) {// 召唤兽表
				// 查找该召唤兽
				RoleSummoning pet = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(salegoods.getOtherid());
				// 修改物品角色ID为负
				AllServiceUtil.getRoleSummoningService().updateRoleSummoningIndex(pet, roleInfo.getRole_id());

				// 添加返回bean
				List<RoleSummoning> pets = new ArrayList<>();
				pets.add(pet);
				update.setPets(pets);
				
				List<Goodstable> goodstables = new ArrayList<>();
				// 获取召唤兽内丹饰品ID,修改角色ID为负
				List<BigDecimal> goodses=pet.getGoods();
				if (goodses!=null) {
					for (BigDecimal bigDecimal : goodses) {
						Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(bigDecimal);
						AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, goodstable.getRole_id().multiply(new BigDecimal(-1)), null, null);
						goodstables.add(goodstable);
					}	
				}	
				update.setGoods(goodstables);
			} else if (salegoods.getSaletype() == 6) {// 灵宝表
				// 查找该灵宝
				Lingbao lingbao = AllServiceUtil.getLingbaoService().selectByPrimaryKey(salegoods.getOtherid());
				// 修改物品角色ID为负
				AllServiceUtil.getLingbaoService().updateLingbaoIndex(lingbao,roleInfo.getRole_id());
				// 添加返回bean
				List<Lingbao> lingbaos = new ArrayList<>();
				lingbaos.add(lingbao);
				update.setLingbaos(lingbaos);
				
				// 修改灵宝符石
				List<Goodstable> goodstables = new ArrayList<>();
				if( lingbao.getFushis() != null && !"".equals(lingbao.getFushis()) ){
					String[] baos = lingbao.getFushis().split("\\|");
					for (String string : baos) {
						Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(string));
						AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, goodstable.getRole_id().multiply(new BigDecimal(-1)), null, null);
						goodstables.add(goodstable);
					}
				}
				update.setGoods(goodstables);
			}else if( salegoods.getSaletype() == 2 ){
				update.updata("D="+salegoods.getOtherid().longValue());
				// 角色添加金钱
				roleInfo.setGold(roleInfo.getGold().add(salegoods.getOtherid()));
			}
			update.setMsg(1+"个"+salegoods.getSalename());
			// 类型
			update.setType(AssetUpdate.CGB);
			update.reset();
			// 发送前端取回的东西
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(update)));
		}
		
		// 判断是否有收藏该件商品的人
		CollectionExample example = new CollectionExample();
		Criteria c = example.createCriteria();
		c.andSaleidEqualTo(salegoods.getSaleid());
		List<Collection> list = AllServiceUtil.getCollectionService().selectByExample(example);
		if( list != null && list.size() != 0 ){
			
			for (Collection collection : list) {
				// 提示消息
				Message message2 = new Message();
				message2.setRoleid(collection.getRoleid());	
				message2.setSaleid(collection.getSaleid());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowdayTime = dateFormat.format(new Date());
				Date nowDate = null;
				try {
					nowDate = dateFormat.parse(nowdayTime);
					message2.setGettime(nowDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message2.setMescontent(tip);
				AllServiceUtil.getMessageService().insert(message2);
			}
			
		}
		
	}
}
