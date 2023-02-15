package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchCollectionResultBean;
import org.come.entity.Collection;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 收藏查询
 * @author Administrator
 *
 */
public class CollectionSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		// 页数
		int pageNum = 1;
		if( message != null ){
			pageNum = Integer.parseInt(message);
		}
		
		// 分页查询
		PageHelper.startPage(pageNum, 15);
		List<Collection> list = AllServiceUtil.getCollectionService().selectRoleCollect(roleInfo.getRole_id());
		PageInfo<Collection> pageInfo = new PageInfo<>(list);
		
		SearchCollectionResultBean resultBean = new SearchCollectionResultBean();
		resultBean.setCollections(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		
		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch7Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);

	}

}
