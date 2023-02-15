package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.DeleteMsgBean;
import org.come.bean.LoginResult;
import org.come.bean.SearchMesResultBean;
import org.come.entity.Message;
import org.come.entity.MessageExample;
import org.come.entity.MessageExample.Criteria;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 消息操作
 * 
 * @author Administrator
 * 
 */
public class MessageControlAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		// 获取删除的消息ID
		DeleteMsgBean ids = GsonUtil.getGsonUtil().getgson().fromJson(message, DeleteMsgBean.class);

		if (ids != null && ids.getIds() != null && ids.getIds().size() != 0) {

			// 批量删除
			for (BigDecimal id : ids.getIds()) {
				AllServiceUtil.getMessageService().deleteByPrimaryKey(id);
			}

		}

		// 重新查询消息
		// 查询条件
		MessageExample example = new MessageExample();
		Criteria c = example.createCriteria();
		c.andRoleidEqualTo(roleInfo.getRole_id());
		example.setOrderByClause("gettime desc");

		// 分页查询
		PageHelper.startPage(1, 15);
		List<Message> list = AllServiceUtil.getMessageService().selectByExample(example);
		PageInfo<Message> pageInfo = new PageInfo<>(list);

		SearchMesResultBean resultBean = new SearchMesResultBean();
		resultBean.setMessages(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());

		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch6Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);
	}

}
