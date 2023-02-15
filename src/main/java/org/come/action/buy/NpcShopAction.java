package org.come.action.buy;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.PathPoint;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGRole;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSUtil;
import come.tool.Scene.ZZS.ZZSRole;
import come.tool.Scene.ZZS.ZZSScene;

public class NpcShopAction implements IAction{

//	static String CHECKTS1=Agreement.getAgreement().PromptAgreement("还未开启兑换");	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {
    		return;
    	}
		if (message.equals("1106")) {
			long meney=0;
			ZZSScene zzsScene=SceneUtil.getZZS(roleInfo);
	    	if (zzsScene!=null) {
	            ZZSRole zzsRole=zzsScene.getRole(roleInfo);
	            if (zzsRole!=null) {
	            	meney=zzsRole.getJf();
				}
	    	} 
	    	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().BuyNPCGoodsAgreement(message+"|"+meney));  		
		}else if (message.equals("515")) {
			long meney=0;
		    PathPoint point=LTSUtil.getLtsUtil().getJF(roleInfo.getRole_id());
		    if (point!=null) {meney=point.getY();}
		  	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().BuyNPCGoodsAgreement(message+"|"+meney));  		
		}else if (message.equals("605")) {
			long meney=0;
			Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
			if (scene!=null) {
				DNTGScene dntgScene=(DNTGScene) scene;
				DNTGRole role=dntgScene.getRole(roleInfo.getRole_id());
				if (role!=null) {
					meney=role.getUseDNJF();
				}
			}
		    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().BuyNPCGoodsAgreement(message+"|"+meney));  		
		}
	}

}
