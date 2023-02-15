package org.come.extInterface.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.sale.MyOrderSearchAction;
import org.come.bean.LoginResult;
import org.come.entity.Message;
import org.come.entity.Roleorder;
import org.come.entity.Salegoods;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.gl.service.GameService;

import come.tool.Stall.AssetUpdate;
import io.netty.channel.ChannelHandlerContext;

/**
 * 藏宝阁商品购买(/goods/buy)
 * 
 * @author zz
 * 
 */
public class GoodsBuy implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 接收商品ID
		BigDecimal saleid = new BigDecimal(message);
		// 获取商品信息
		Salegoods sale = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(saleid);
		// 获取角色信息（点击人）
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);

		//1未上架   2已上架   3已下单   4已卖出   5已取回
		if ( sale.getFlag() != 2){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("物品已经被其他玩家购买或被卖家下架"));
			return;
		}

		Calendar calendar = Calendar.getInstance();
        calendar.setTime(sale.getUptime());
        calendar.add(Calendar.HOUR_OF_DAY, + 169);// +1今天的时间加一天
        if (new Date().getTime() > calendar.getTime().getTime()) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("物品已经下架"));
			return;
        }

		// 判断指定购买的角色
		if (sale.getBuyrole()!=null&&sale.getBuyrole().compareTo(new BigDecimal(0))!=0&&roleInfo.getRole_id().compareTo(sale.getBuyrole())!=0){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("这件物品指定了卖家，你不可以购买"));
			return;
		}
		// 存放买家费用
		BigDecimal price = sale.getSaleprice();
		// 大话币没有公示期
		if( sale.getSaletype() == 2 ){
			// 判断购买人是否有足够的仙玉
			if (roleInfo.getCodecard().compareTo(sale.getSaleprice()) < 0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你没有足够的仙玉来购买这件物品"));
				return;
			} else {
				// 仙玉够
				roleInfo.setCodecard(roleInfo.getCodecard().subtract(sale.getSaleprice()));
				MonitorUtil.getMoney().useX(sale.getSaleprice().longValue());
			}
		} else if (sale.getSaletype() != 1 && (sale.getSalename() != null && !"".equals(sale.getSalename()))) {

			// 判断物品是否在公示期
			// 当前时间减去1小时小于上架时间则表示在公示期，额外扣除20%仙玉
			if (MyOrderSearchAction.getDate(1).compareTo(sale.getUptime()) == -1) {
				// 计算需要支付的仙玉
				price = new BigDecimal(sale.getSaleprice().longValue() + sale.getSaleprice().longValue() * 20 / 100);
			}

			// 判断购买人是否有足够的仙玉
			if (roleInfo.getCodecard().compareTo(price) < 0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你没有足够的仙玉来购买这件物品"));
				return;
			} else {
				// 仙玉够
				roleInfo.setCodecard(roleInfo.getCodecard().subtract(price));
				MonitorUtil.getMoney().useX(price.longValue());
			}
		} else  {
			return;
		}


		// 添加订单
		Roleorder roleorder = new Roleorder();
		roleorder.setSaleid(sale.getSaleid());
		roleorder.setRoleid(roleInfo.getRole_id());
		roleorder.setStatus(3);
		// 设置下单时间
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdayTime = dateFormat.format(new Date());
		Date nowDate = null;
		try {
			nowDate = dateFormat.parse(nowdayTime);
			roleorder.setBuytime(nowDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AllServiceUtil.getRoleorderService().insert(roleorder);
		// 修改商品状态，并且修改对应的数据表角色ID,发送该件商品回客户端
		sale.setFlag(4);
		AllServiceUtil.getSalegoodsService().updateByPrimaryKey(sale);


		// 获取卖家
		LoginResult role = AllServiceUtil.getRoleTableService().selectRoleID(sale.getRoleid());

		ChannelHandlerContext masterctx = GameServer.getInlineUserNameMap().get(role.getUserName());
		LoginResult login = masterctx != null ? GameServer.getAllLoginRole().get(masterctx) : null;

		// 购买成功后需要通过我的货物取回
		if (login != null) {
			// 卖家在线则直接写入卖家内存
			login.setCodecard(login.getCodecard().add(sale.getSaleprice()));
			// 在线也要同步数据库
			AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);

			// 通知卖家物品卖出，钱已到账
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.CBGGET);
			assetUpdate.setMsg("你寄售的" + sale.getSalename() + "已经被玩家买走了，获得仙玉" + sale.getSaleprice().longValue());
			assetUpdate.updata("X="+sale.getSaleprice().longValue());
			SendMessage.sendMessageByRoleName(role.getRolename(),Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

			// 私人邮件消息
			String msg = "#Y[系统消息] #G你寄售的#R" + sale.getSalename() + "#G已经被玩家#Y" + roleInfo.getRolename() + "#G买走了，获得仙玉#R" + sale.getSaleprice().longValue();
			new GameService().sendMsgToPlayer(msg, login.getRolename());
		} else {
			// 卖家不在线
			role.setCodecard(role.getCodecard().add(sale.getSaleprice()));
			AllServiceUtil.getRoleTableService().updateRoleWhenExit(role);

			// 卖家不在线收不到货款，强制卖家与数据入库一次
			// 卖家信息保存
			UserTable userTable = AllServiceUtil.getUserTableService().selectByPrimaryKey(role.getUser_id());
			userTable.setCodecard(userTable.getCodecard().add(sale.getSaleprice()));
			// 扣除购买人仙玉，并打入卖家账户
			role.setCodecard(role.getCodecard().add(sale.getSaleprice()));
			// 保存角色信息
			AllServiceUtil.getUserTableService().updateUser(userTable);
		}

		// 保存角色信息
		AllServiceUtil.getRoleTableService().updateRoleWhenExit(roleInfo);

		// 发送消息给卖家（藏宝阁消息）
		Message message2 = new Message();
		message2.setRoleid(role.getRole_id());
		message2.setSaleid(sale.getSaleid());
		try {
			nowDate = dateFormat.parse(nowdayTime);
			message2.setGettime(nowDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		message2.setMescontent("物品" + sale.getSalename() + "已卖出");
		AllServiceUtil.getMessageService().insert(message2);

		// 通知买家钱被扣除，并到我的货物中领取货物
		AssetUpdate assetUpdateBuy=new AssetUpdate();
		assetUpdateBuy.setType(AssetUpdate.CBGBUY);
		assetUpdateBuy.setMsg("你已成功购买了" + sale.getSalename() + "，请到《我的货物》中收取");
		assetUpdateBuy.updata("X="+(-price.longValue()));
		SendMessage.sendMessageByRoleName(roleInfo.getRolename(),Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdateBuy)));
	}
}
