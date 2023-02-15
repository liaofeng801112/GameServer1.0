package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchOrderResultBean;
import org.come.entity.Roleorder;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 我的货物（查已付款未取货的订单 就是已付款状态）
 * 
 * @author Administrator
 * 
 */
public class MyGoodsSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);		

		// 分页查询
		PageHelper.startPage(Integer.parseInt(message), 15);
		List<Roleorder> list = AllServiceUtil.getRoleorderService().selectRoleOrders(roleInfo.getRole_id());
		PageInfo<Roleorder> pageInfo = new PageInfo<>(list);
		
		// 返回对象
		SearchOrderResultBean resultBean = new SearchOrderResultBean();
		resultBean.setRoleorders(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch5Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);
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
