package org.come.action.festival;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
/**
 * 获取孵化值
 * @author Administrator
 *
 */
public class HatchvalueAction implements IAction {
	
	// 孵化值
	public static AtomicInteger hatch = new AtomicInteger(0);
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().HatchvalueAgreement(hatch.toString()));
	}

}
