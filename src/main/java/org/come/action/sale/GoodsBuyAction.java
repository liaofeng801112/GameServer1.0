package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.BuyBtnGetBean;
import org.come.bean.LoginResult;
import org.come.entity.CollectionExample;
import org.come.entity.CollectionExample.Criteria;
import org.come.entity.Salegoods;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 购买商品
 * @author Administrator
 *
 */
public class GoodsBuyAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 接收商品ID
		BigDecimal saleid = new BigDecimal(message);
		// 返回对象
		BuyBtnGetBean bean = new BuyBtnGetBean();
		// 获取商品信息
		Salegoods sale = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(saleid);
		// 获取销售人名称
		LoginResult role = AllServiceUtil.getRoleTableService().selectRoleID(sale.getRoleid());
		// 获取角色信息（点击人）
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 判断指定购买的角色
		if (sale.getBuyrole()!=null&&sale.getBuyrole().compareTo(new BigDecimal(0))!=0&&roleInfo.getRole_id().compareTo(sale.getBuyrole())!=0){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().tipAgreement("非指定角色！！"));
			bean.setFlag(false);
			String msg = Agreement.getAgreement().CBGBuyAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		// 获取收藏人数
		CollectionExample example = new CollectionExample();
		Criteria c = example.createCriteria();
		c.andSaleidEqualTo(saleid);
		int count = AllServiceUtil.getCollectionService().countByExample(example);
		bean.setGoodsRoleName(role.getRolename());
		bean.setInGetPeoSum(count+"");
		bean.setFlag(true);
		String msg = Agreement.getAgreement().CBGBuyAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
