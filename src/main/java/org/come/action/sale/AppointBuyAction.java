package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchGoodsResultBean;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.entity.SalegoodsExample.Criteria;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 指定购买查询
 * @author Administrator
 *
 */
public class AppointBuyAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 查找商品绑定买家是该角色的商品
		SalegoodsExample example = new SalegoodsExample();
		Criteria c = example.createCriteria();
		c.andBuyroleEqualTo(roleInfo.getRole_id());
		
		// 页数
		int pageNum = 1;
		if( message != null ){
			pageNum = Integer.parseInt(message);
		}
		// 分页查询
		PageHelper.startPage(pageNum, 15);
		List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
		PageInfo<Salegoods> pageInfo = new PageInfo<>(list);
		
		// 返回对象
		SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
		resultBean.setSalegoods(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		
		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch4Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);

	}

}
