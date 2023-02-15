package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchGoodsResultBean;
import org.come.bean.SearchMyGoodsBean;
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
 * 我的商品查询
 * @author Administrator
 *
 */
public class MyWaresSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取搜索条件
		SearchMyGoodsBean bean = GsonUtil.getGsonUtil().getgson().fromJson(message, SearchMyGoodsBean.class);
		
		// 根据条件搜索商品
		SalegoodsExample example = new SalegoodsExample();
		Criteria c = example.createCriteria();
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 角色
		c.andRoleidEqualTo(roleInfo.getRole_id());
		// 状态
		if (bean.getFlag() != null && !"".equals(bean.getFlag())) {
			c.andFlagEqualTo(bean.getFlag());
		}
		
		// 分页查询
		PageHelper.startPage(bean.getPageNum(), 15);
		List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
		PageInfo<Salegoods> pageInfo = new PageInfo<>(list);
		
		// 返回对象
		SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
		resultBean.setSalegoods(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		
		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch2Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
