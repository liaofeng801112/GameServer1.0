package org.come.action.festival;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;
/**
 * 添加孵化值
 * @author Administrator
 *
 */
public class HatchaddAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		// 孵化值加1
		int hatch = HatchvalueAction.hatch.addAndGet(1);
		
		// 如果孵化值达到500，发放物品
		if( hatch == 500 ){
			// 发送公告
			NChatBean bean=new NChatBean();
			bean.setId(4);
			bean.setMessage("元旦孵蛋成功，请注意接收奖励！！！");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);
			// 发送随机奖励给所有玩家
//			GetReward getReward=new GetReward();
//			getReward.setMsg();
//			DropUtil.getDrop(loginResult,dorp.getDorpValue(),"元旦孵蛋奖励", 22);
		}

	}

}
