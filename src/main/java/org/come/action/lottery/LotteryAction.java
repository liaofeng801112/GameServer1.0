package org.come.action.lottery;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.monster.ClickMonsterAction;
import org.come.action.suit.SuitComposeAction;
import org.come.bean.LoginResult;
import org.come.bean.QuackGameBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.model.EventModel;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.DropUtil;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.LaborDay.LaborScene;
import come.tool.Stall.AssetUpdate;

public class LotteryAction implements IAction {

	public static String CHECKTS1=Agreement.getAgreement().PromptAgreement("该奖池未开放");
	public static String CHECKTS2=Agreement.getAgreement().PromptAgreement("抽奖卷不够");
	public static String CHECKTS4=Agreement.getAgreement().PromptAgreement("你未有资格领取");
	public static String CHECKTS5=Agreement.getAgreement().PromptAgreement("你已经领取");
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		if (message.startsWith("G")) {
			GGL(ctx,message.length()<=1?null:message.substring(1));
		}else if (message.startsWith("C")) {
			CJ(ctx,message.length()<=1?null:message.substring(1));
		}else if (message.startsWith("E")) {
			HD(ctx, message.length()<=1?null:message.substring(1));
		}else {
			GGL(ctx,message);
		}
	}
	/**活动面板*/
	public void HD(ChannelHandlerContext ctx,String message){
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		EventModel eventModel=null;
		if (message.startsWith("G")) {//领奖用的
			int id=Integer.parseInt(message.substring(1));
			eventModel=GameServer.getEvent(id);
			if (eventModel==null) {return;}
			int type=data.getPackRecord().isOther(id+"");
			if (type==0) {
				SendMessage.sendMessageToSlef(ctx,CHECKTS4);
			}else if (type==2) {
				SendMessage.sendMessageToSlef(ctx,CHECKTS5);
			}else if (type==1) {
				String Did=eventModel.getAward(loginResult.getRole_id());
				if (Did==null) {return;}
				Dorp dorp=GameServer.getDorp(Did);
				if (dorp==null) {return;}
				DropUtil.getDrop(loginResult,dorp.getDorpValue(),"首杀礼包", 22,1D,null);	
			}
			return;
		}
		//获取排行用的
		int id=Integer.parseInt(message);
		eventModel=GameServer.getEvent(id);
		if (eventModel==null||eventModel.getMsg()==null) {return;}
		SendMessage.sendMessageToSlef(ctx,eventModel.getMsg());
	}
    /**抽奖*/
	public void CJ(ChannelHandlerContext ctx,String message){
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
        if (message.startsWith("C")) {//获取奖池信息
        	int cid=Integer.parseInt(message.substring(1));
        	Draw draw=GameServer.getDraw(cid);
			if (draw==null) {SendMessage.sendMessageToSlef(ctx,CHECKTS1);return;}
			SendMessage.sendMessageToSlef(ctx,draw.getText());
        	return;
		}
        String msg=ClickMonsterAction.isTime4s(loginResult.getRole_id());
		if (msg!=null) {
			SendMessage.sendMessageToSlef(ctx,msg);
			return;
		}
//      奖池id|类型0:抽奖,1:十连抽|仙玉补充0:不补充1:补充|物品id|物品id......
        String[] vs=message.split("\\|");
      	int cid=Integer.parseInt(vs[0]);//奖池id
      	int cType=Integer.parseInt(vs[1]);//抽奖类型
      	int cBc=Integer.parseInt(vs[2]);//仙玉补充
     	Draw draw=GameServer.getDraw(cid);
		if (draw==null) {SendMessage.sendMessageToSlef(ctx,CHECKTS1);return;}	
		BigDecimal dj=draw.getMoney();//定价
		BigDecimal goodsid=draw.getGoodid();//物品id
		List<Goodstable> goods=null;
		if (vs.length>3) {
			List<BigDecimal> ids=new ArrayList<>();
			for (int i = 3; i < vs.length; i++) {
				ids.add(new BigDecimal(vs[i]));
			}
			goods=SuitComposeAction.getGoods(ids, loginResult.getRole_id(),0);
			if (goods==null) {return;}
			for (int i = 0; i < goods.size(); i++) {
				Goodstable good=goods.get(i);
				if (good.getGoodsid().compareTo(goodsid)!=0) {return;}
				if (good.getUsetime()<=0) {SendMessage.sendMessageToSlef(ctx,CHECKTS2);return;}
				good.goodxh(1);
			}
			if (cBc==0) {if ((cType==0?1:10)!=goods.size()) {SendMessage.sendMessageToSlef(ctx,CHECKTS2);return;}}
		}else {
			if (cBc==0) {SendMessage.sendMessageToSlef(ctx,CHECKTS2);return;}
		}
		//判断是否有足够的仙玉补充
		BigDecimal money=null;
		if (cBc==1) {
			int size=(cType==0?1:10)-(goods==null?0:goods.size());
			if (size>0) {money=new BigDecimal(dj.longValue()*size);}
		}
		if (money!=null) {
			if (draw.getMoneyType()==0&&loginResult.getCodecard().compareTo(money)<0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("仙玉不够"));return;
			}else if (draw.getMoneyType()==1&&loginResult.getGold().compareTo(money)<0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("大话币不够"));return;
			}else if (draw.getMoneyType()!=0&&draw.getMoneyType()!=1) {
				return;
			}
		}
	 	if (goods!=null) {
	 		for (int i = goods.size()-1; i>=0; i--) {
				Goodstable good=goods.get(i);
				for (int j = 0; j < i; j++) {
					if (goods.get(j).getRgid().compareTo(good.getRgid())==0) {
						goods.remove(i);
						break;
					}
				}
			}	
		}
	 	AssetUpdate asset=new AssetUpdate(AssetUpdate.USEGOOD);
	 	if (money!=null) {
	 		if (draw.getMoneyType()==0) {
	 			asset.updata("X="+(-money.longValue()));
		 		loginResult.setCodecard(loginResult.getCodecard().subtract(money));
		 		MonitorUtil.getMoney().useX(money.longValue());	
			}else if (draw.getMoneyType()==1) {
				asset.updata("D="+(-money.longValue()));
		 		loginResult.setGold(loginResult.getGold().subtract(money));
		 		MonitorUtil.getMoney().useD(money.longValue());
			}	
	 	}
		if (goods!=null) {
	 	    s:for (int i = 0; i < goods.size(); i++) {
				Goodstable good = goods.get(i);
				BigDecimal id = good.getRgid();
				for (int j = i + 1; j < goods.size(); j++) {
					if (goods.get(j).getRgid().compareTo(id) == 0) {
						continue s;
					}
				}
				asset.updata("G"+good.getRgid()+"="+good.getUsetime());
		    }
	 	    SuitComposeAction.saveGoods(goods,false);	
		}
	 	QuackGameBean bean=DrawUtil.CJ(cType,draw,asset,loginResult,data);
	 	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(asset)));
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().getfivemsgAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
		if (draw.getMoneyType()==0) {
			/**添加劳动节记录*/
			LaborScene.addRankValue(1, cType==0?1:10, loginResult);	
		}		
	}
    /**刮刮乐*/
	public void GGL(ChannelHandlerContext ctx,String message){
		if (message==null||message.equals("")) {
			SendMessage.sendMessageToSlef(ctx, PIXIU.getPixiu().getSendAwards());
			return;
		}
		BigDecimal rgid=new BigDecimal(message);
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(rgid);
		if (goodstable==null||goodstable.getType()!=889) {return;}		
		goodstable.setUsetime(goodstable.getUsetime()-1);	
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		if ( goodstable.getUsetime() <= 0 ){//删除时记录刮刮乐消耗的道具
			AllServiceUtil.getGoodsrecordService().insert(goodstable,null,1,9);		
		}
		//开始抽奖
		PIXIU.getPixiu().lottery(ctx);
	}
}
