package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchOrderBean;
import org.come.bean.SearchOrderResultBean;
import org.come.entity.Roleorder;
import org.come.entity.RoleorderExample;
import org.come.entity.RoleorderExample.Criteria;
import org.come.entity.Salegoods;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 我的订单查询
 * 
 * @author Administrator
 * 
 */
public class MyOrderSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取搜索条件
		SearchOrderBean bean = GsonUtil.getGsonUtil().getgson().fromJson(message, SearchOrderBean.class);

		// 根据条件搜索订单
		RoleorderExample example = new RoleorderExample();
		Criteria c = example.createCriteria();
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 角色
		c.andRoleidEqualTo(roleInfo.getRole_id());
		// 状态
		if (bean.getStatus() != null && !"".equals(bean.getStatus())) {
			c.andStatusEqualTo(bean.getStatus());
		}
		// 时间
		if (bean.getTime() != null && !"".equals(bean.getTime())) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 天数
			int day = 0;
			if (bean.getTime() == 1) {
				day = 7;
			} else {
				day = 30;
			}
			String nowdayTime = dateFormat.format(getDate(day));
			Date nowDate = null;
			try {
				nowDate = dateFormat.parse(nowdayTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (bean.getTime() == 3) {
				// 日期以外
				c.andBuytimeLessThan(nowDate);
			} else {
				// 日期以内
				c.andBuytimeGreaterThan(nowDate);
			}

		}

		// 分页查询
		PageHelper.startPage(bean.getPageNum(), 15);
		List<Roleorder> list = AllServiceUtil.getRoleorderService().selectByExample(example);
		PageInfo<Roleorder> pageInfo = new PageInfo<>(list);
		
		// 查询订单商品信息
		for (Roleorder roleorder : pageInfo.getList()) {
			Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(roleorder.getSaleid());
			roleorder.setSalename(salegoods.getSalename());
			roleorder.setSaleskin(salegoods.getSaleskin());
			roleorder.setSaleprice(salegoods.getSaleprice());
			roleorder.setSaletype(salegoods.getSaletype());
			roleorder.setOtherid(salegoods.getOtherid());
		}

		// 返回对象
		SearchOrderResultBean resultBean = new SearchOrderResultBean();
		resultBean.setRoleorders(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());

		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch3Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx,msg);
	}

	/**
	 * 返回几天前的时间
	 * 
	 * @param day
	 * @return
	 */
	public static Date getDate(Integer day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - day);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endDate;
	}

}
