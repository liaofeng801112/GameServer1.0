package org.come.action.monster;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Good.DropUtil;

public class ClickMonsterAction implements IAction {
	public static String CHECKTS1=Agreement.getAgreement().PromptAgreement("你点的太快了,休息一下吧");
	public static String CHECKTS2=Agreement.getAgreement().PromptAgreement("别看了,已经消失了");
	
	private static ConcurrentHashMap<BigDecimal,Long> clickMap=new ConcurrentHashMap<>();
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 获得该角色ID
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		int I=Integer.parseInt(message);
		MapMonsterBean bean=MonsterUtil.getMonster(I);
		if (bean==null) {SendMessage.sendMessageToSlef(ctx,CHECKTS2);return;}
		if (bean.getRobotType()==0) {
			return;
		}else if (bean.getRobotType()==1) {//点击获取物品的类型
			String vs=isTime10s(loginResult.getRole_id());
			if (vs!=null) {SendMessage.sendMessageToSlef(ctx,vs);return;}
			MonsterUtil.removeMonster(bean,2);
			Robots robots=GameServer.getAllRobot().get(bean.getRobotid()+"");
			if (robots!=null) {
				String value=BattleMixDeal.isLvl(loginResult.getGrade(), robots.getLvls());
        		if (value!=null) {SendMessage.sendMessageToSlef(ctx,value);return;}
				DropUtil.getDrop(loginResult, robots.getRobotreward(), robots.getUnknow(),21,1D,null);
			}
		}else if (bean.getRobotType()==2) {//点击获取物品的类型
			SendMessage.sendMessageToSlef(ctx,bean.getShopMsg());
		}
	}
	/**时间间隔检测*/
	public static String isTime4s(BigDecimal role_id){
		Long time=clickMap.get(role_id);
		long time2=System.currentTimeMillis();
		if (time==null) {time=0L;}
		if (time2-time<4000) {return CHECKTS1;}
		clickMap.put(role_id,time2);
		return null;
	}
	/**时间间隔检测*/
	public static String isTime10s(BigDecimal role_id){
		Long time=clickMap.get(role_id);
		long time2=System.currentTimeMillis();
		if (time==null) {time=0L;}
		if (time2-time<10000) {return CHECKTS1;}
		clickMap.put(role_id,time2);
		return null;
	}
	/**时间间隔检测*/
	public static String isTime20s(BigDecimal role_id){
		Long time=clickMap.get(role_id);
		long time2=System.currentTimeMillis();
		if (time==null) {time=0L;}
		if (time2-time<20000) {return CHECKTS1;}
		clickMap.put(role_id,time2);
		return null;
	}
}
