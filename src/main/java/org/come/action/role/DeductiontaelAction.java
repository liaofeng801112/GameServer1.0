package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.GsonUtil;
/**
 * 修改银两
 * @author 叶豪芳
 * @date 2017年12月27日 上午11:48:19
 * 
 */ 
public class DeductiontaelAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 扣除角色银两
		long jg=Long.parseLong(message);
		if (jg<=0) {return;}
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		long gold=loginResult.getGold().longValue()-jg;
		if (gold<0) {
			String v="扣除银量异常:"+message+":"+GsonUtil.getGsonUtil().getgson().toJson(loginResult);
			WriteOut.addtxt(v,9999);	
			gold=0;
		}
		loginResult.setGold(new BigDecimal(gold));
		MonitorUtil.getMoney().useD(jg);

	}

}
