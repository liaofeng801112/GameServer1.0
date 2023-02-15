package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
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
 * 消息搜索
 * @author Administrator
 *
 */
public class MessageSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 查询条件
		MessageExample example = new MessageExample();
		Criteria c = example.createCriteria();
		c.andRoleidEqualTo(roleInfo.getRole_id());
		example.setOrderByClause("gettime desc");
		
		// 页数
		int pageNum = 1;
		if( message != null ){
			pageNum = Integer.parseInt(message);
		}
		
		// 分页查询
		PageHelper.startPage(pageNum, 15);
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
