package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.handler.SendMessage;
import org.come.model.aCard;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class UseCardAction implements IAction{
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (message.startsWith("R")) {
			ReceiveDBExp(ctx, message.substring(1),loginResult,roleData);
		}else if (message.startsWith("S")) {
			SuspendDBExp(ctx,loginResult,roleData);
		}else if (message.startsWith("Q")) {
			QueryDBExp(ctx,loginResult,roleData);
		}else if (message.startsWith("T")) {//时效道具超时
			String[] vs=message.split("\\|");
			vs[0]=vs[0].substring(1);
			for (int i = 0; i < vs.length; i++) {
				roleData.removeLimit(vs[i]);
			}
		}else {
			UCard(ctx, message,loginResult,roleData);
		}	
	}
	/**领取双倍时间 R*/
	public void ReceiveDBExp(ChannelHandlerContext ctx, String message,LoginResult login,RoleData data){
		int dbexp = data.getPrivateData().getDBExp();
		// 剩余时间
		int time=Integer.parseInt(message);
		int exp = 21600 - dbexp;
		if (exp < time){
			time = exp;
		}
		if (exp == 0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你双倍时间已经用完了,明天请早"));
			return;
		}
		data.getPrivateData().setDBExp(dbexp + time);
		UseCardBean limit=data.getLimit("经验");
        if (limit==null) {
        	limit=new UseCardBean("双倍经验", "经验", "shuang", time*1000+System.currentTimeMillis(), null);
        	data.addLimit(limit);
		}else {
			limit.setTime(limit.getTime()+time*1000);
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你领取了#G " + time/60 + " #Y分钟的双倍时间");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
	}
	/**冻结双倍时间 S*/
	public void SuspendDBExp(ChannelHandlerContext ctx, LoginResult login,RoleData data){
		UseCardBean limit=data.removeLimit("经验");
		if (limit==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没有双倍时间"));
			return;
		}
		int surplus = fenzhong(limit.getTime());
		data.getPrivateData().setDBExp(data.getPrivateData().getDBExp() - surplus * 60);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		limit.setTime(-1);
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你冻结了#G " + surplus + " #Y分钟的双倍时间");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
	/**查询剩余双倍时间 Q*/
	public void QueryDBExp(ChannelHandlerContext ctx, LoginResult login,RoleData data){
		int dbexp = data.getPrivateData().getDBExp();
		int exp = (21600 - dbexp) / 60;
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你剩余#G " + exp + " #Y分钟的双倍时间"));
	}
	/**判断剩余多少分钟*/
	public static int fenzhong(long time) {
		return (int) ((time - System.currentTimeMillis()) / 60000);
	}
	/**使用变身卡*/
	public void UCard(ChannelHandlerContext ctx, String message,LoginResult loginResult,RoleData roleData){
//		//0是变身造型的   1是不变身造型的
		String[] vs=message.split("\\|");
		if (vs[0].equals("0")||vs[0].equals("1")) {
			boolean is=vs[0].equals("0");
			int id=Integer.parseInt(vs[1]);
			aCard card=GameServer.getCard(id);
			if (card==null) {return;}
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			if (card.getType()==0) {
				if (loginResult.getGold().longValue()<card.getMoney()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不足"));
					return;
				}
				loginResult.setGold(new BigDecimal(loginResult.getGold().longValue()-card.getMoney()));
				assetUpdate.updata("D=-"+card.getMoney());
				MonitorUtil.getMoney().useD(card.getMoney());
			}else if (card.getType()==1) {
				if (loginResult.getCodecard().longValue()<card.getMoney()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("仙玉不足"));
					return;
				}
				loginResult.setCodecard(new BigDecimal(loginResult.getCodecard().longValue()-card.getMoney()));
				MonitorUtil.getMoney().useX(card.getMoney());
				assetUpdate.updata("X=-"+card.getMoney());
			}
			MonitorUtil.addCard(card.getType(), card.getMoney());
			
			String value=is?("皮肤="+card.getZskin()+"|"+card.getValue()):card.getValue();
			UseCardBean limit=roleData.getLimit("变身卡");
			if (limit==null) {
				limit=new UseCardBean(card.getName(), "变身卡", card.getSkin(), System.currentTimeMillis()+card.getTime()*60000, value);
				roleData.addLimit(limit);
			}else if (card.getName().equals(limit.getName())&&value.equals(limit.getValue())) {
				limit.setTime(limit.getTime()+card.getTime()*60000);
			}else {
				limit.setName(card.getName());
				limit.setSkin(card.getSkin());
				limit.setValue(value);
				limit.setTime(System.currentTimeMillis()+card.getTime()*60000);	
			}
			assetUpdate.setMsg("你成功使用了"+card.getName());
			limit.setlCard(roleData.getPackRecord().isCard(vs[1]));	
			assetUpdate.setUseCard(limit);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
			limit.setlCard(null);
		}
	}
	
}
