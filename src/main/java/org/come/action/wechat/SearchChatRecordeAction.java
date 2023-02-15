package org.come.action.wechat;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.WechatFriendListBean;
import org.come.entity.Wechatrecord;
import org.come.entity.WechatrecordExample;
import org.come.entity.WechatrecordExample.Criteria;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 获取好友的聊天记录
 * @author Administrator
 *
 */
public class SearchChatRecordeAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取自己的信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 获取查询条件
		WechatFriendListBean friendListBean = GsonUtil.getGsonUtil().getgson().fromJson(message, WechatFriendListBean.class);
		
		// 获取对方ID
		BigDecimal otherID = new BigDecimal(friendListBean.getGetId());
		
		// 查询聊天记录
		WechatrecordExample example = new WechatrecordExample();
		Criteria ct = example.createCriteria();
		List<BigDecimal> values = new ArrayList<>();
		values.add(otherID);
		values.add(roleInfo.getRole_id());
		ct.andChatGetidIn(values);
		ct.andChatSendidIn(values);
		if( friendListBean.getTime() != null && !"".equals(friendListBean.getTime()) ){
			ct.andTimeLike(friendListBean.getTime()+"%");
		}
		example.setOrderByClause("to_date(time,'yyyy-mm-dd hh24:mi:ss')");
		// 分页查询
		PageHelper.startPage(friendListBean.getSearchPage(), friendListBean.getPageNumber());
		List<Wechatrecord> list = AllServiceUtil.getWechatrecordService().selectByExample(example);
		PageInfo<Wechatrecord> pageInfo = new PageInfo<>(list);
		
		// 封装返回客户端
		friendListBean.setWechatFriendList(pageInfo.getList());
		friendListBean.setSumPage((int)pageInfo.getTotal());
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().searchChatRecordeAgreement(GsonUtil.getGsonUtil().getgson().toJson(friendListBean)));
	}

}
