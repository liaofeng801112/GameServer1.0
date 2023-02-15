package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Collection;
import org.come.entity.CollectionExample;
import org.come.entity.CollectionExample.Criteria;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
/**
 * 收藏和取消收藏
 * @author Administrator
 *
 */
public class CollectionControlAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		
		if(message ==null) return;
		// 查找收藏
		CollectionExample example = new CollectionExample();
		Criteria c = example.createCriteria();
		c.andSaleidEqualTo(new BigDecimal(message));
		c.andRoleidEqualTo(roleInfo.getRole_id());
		List<Collection> list = AllServiceUtil.getCollectionService().selectByExample(example);
		
		// 判断是收藏还是取消
		if( list != null && list.size() != 0 ){
			// 取消收藏
			AllServiceUtil.getCollectionService().deleteByPrimaryKey(list.get(0).getColid());
		}else{
			// 收藏
			Collection collection = new Collection();
			collection.setRoleid(roleInfo.getRole_id());
			collection.setSaleid(new BigDecimal(message));
			AllServiceUtil.getCollectionService().insert(collection);
		}
	}

}
