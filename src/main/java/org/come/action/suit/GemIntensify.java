package org.come.action.suit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.ConfirmBean;
import org.come.bean.LoginResult;
import org.come.bean.SuitOperBean;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.AddGoodAction;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

/**宝石强化*/
public class GemIntensify {
	
	/**宝石合成(强化系统)*/
	public static void type101(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		int num=suitOperBean.getJade()!=null?suitOperBean.getJade().getSuitid():0;
		if (num<=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没有输入合成的数量"));
			return;
		}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 1);
		if (goods==null||goods.size()!=1||!Goodtype.QHbaoshi(goods.get(0).getType())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成公式不对"));
			return;
		}
		int lvl=Integer.parseInt(goods.get(0).getValue().substring(3));
		if (lvl>=5) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("未开放6级以上的强化宝石"));
			return;
		}
		int size=num*(4+(4-lvl)*2);
		if (goods.get(0).getUsetime()<size) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的材料数量不够合成"+num+"个"+(lvl+1)+"级"+goods.get(0).getGoodsname()));
			return;
		}
		BigDecimal money=new BigDecimal(1000000*lvl*num);
		if (loginResult.getGold().compareTo(money)<0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不足"));
			return;
		}
		BigDecimal id=goods.get(0).getGoodsid().add(new BigDecimal(1));
		Goodstable goodstable = GameServer.getGood(id);
		if( goodstable==null ){
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("物品出现错误联系管理员提交BUG"));
			return;
		}
		XXGDBean bean=new XXGDBean();
		bean.setId(id.toString());
		bean.setSum(num);
		
		goods.get(0).goodxh(size);
		SuitComposeAction.saveGoods(goods,false);	
		loginResult.setGold(loginResult.getGold().subtract(money));  
	   	MonitorUtil.getMoney().useD(money.longValue());
	   	AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(0).getRgid()+"="+goods.get(0).getUsetime());
		assetUpdate.setMsg("合成成功");
		AddGoodAction.addGood(assetUpdate,goodstable,loginResult,roleData,bean,13);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
	/**拆卸(强化系统)*/
	public static void type102(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean,BigDecimal rgid){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		Goodstable good=null;
		if (suitOperBean!=null) {//前端请求
			List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 1);
			good=goods.get(0);
		}else {//二次确认完毕
			good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(rgid);
		}
		if (good==null||!Goodtype.EquipGem(good.getType())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("装备类型不对"));
			return;
		}
		if (good.getStatus()!=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能对背包的物品进行操作"));
			return;
		}
		int qhv=good.getQhv()!=null?good.getQhv():0;
		if (qhv<=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你这个装备还没有镶嵌怎么拆卸"));
			return;
		}
		int lvl=(qhv-1)/3+1;
		int num=lvl==1?0:lvl==2?1:lvl==3?5:lvl==4?20:60;//TODO 宝石ID更换
		BigDecimal sid=new BigDecimal(Goodtype.Weapons(good.getType())?88888:
			Goodtype.Necklace(good.getType())?88893:
				Goodtype.Shoes(good.getType())?88898:
					Goodtype.Helmet(good.getType())?88903:88908);
		Goodstable goodstable = GameServer.getGood(new BigDecimal(sid.longValue()+lvl-1));
		if( goodstable==null ){
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("物品出现错误联系管理员提交BUG"));
			return;
		}
		XXGDBean bean=new XXGDBean();
		bean.setId(goodstable.getGoodsid().toString());
		bean.setSum(1);
		
		Goodstable good2=null;
		if (num!=0) {
			List<Goodstable> goods = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), sid);
			good2=goods.size()!=0?goods.get(0):null;
			if (good2==null||good2.getUsetime()<num) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("需要消耗对应的一级强化石"+num+"个"));
				return;
			}
			if (suitOperBean!=null) {//发送二次确认
				ConfirmBean confirmBean=new ConfirmBean();
				confirmBean.setMSG("你是否要消耗#G"+num+"#W个一级"+good2.getGoodsname()+"去拆卸"+good.getGoodsname()+"#R+"+qhv);
				confirmBean.setType(102);
				confirmBean.setValue(good.getRgid().toString());
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().confirmAgreement(GsonUtil.getGsonUtil().getgson().toJson(confirmBean)));
				return;
			}	
		}
		List<Goodstable> goods=new ArrayList<>();
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		
		qhv--;
		good.setQhv(qhv);
		goods.add(good);
		assetUpdate.setGood(goods.get(0));
		if (good2!=null) {
			good2.goodxh(num);
			goods.add(good2);
			assetUpdate.updata("G"+goods.get(1).getRgid()+"="+goods.get(1).getUsetime());
		}
		assetUpdate.setMsg("拆卸成功!你的"+goods.get(0).getGoodsname()+"变成#R+"+qhv);
		SuitComposeAction.saveGoods(goods,false);
		AddGoodAction.addGood(assetUpdate,goodstable,loginResult,roleData,bean,13);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
	/**镶嵌(强化系统)*/
	public static void type103(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 1);
		if (goods==null||goods.size()!=2||!Goodtype.QHEquipGem(goods.get(0).getType(),goods.get(1).getType())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成公式不对"));
			return;
		}
		if (goods.get(0).getStatus()!=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能对背包的物品进行操作"));
			return;
		}
		if (goods.get(1).getUsetime()<1) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("材料数量不足"));
			return;
		}
		int qhv=goods.get(0).getQhv()!=null?goods.get(0).getQhv():0;
		int lvl=Integer.parseInt(goods.get(1).getValue().substring(3));
		if ((qhv/3+1)!=lvl) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("应该使用"+(qhv/3+1)+"级"+goods.get(1).getGoodsname()));
			return;
		}
		BigDecimal money=new BigDecimal(1000000*lvl);
		if (loginResult.getGold().compareTo(money)<0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不足"));
			return;
		}
		qhv+=1;
		goods.get(0).setQhv(qhv);
		goods.get(1).goodxh(1);
		SuitComposeAction.saveGoods(goods,false);	
		loginResult.setGold(loginResult.getGold().subtract(money));  
	   	MonitorUtil.getMoney().useD(money.longValue());
	   	AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(1).getRgid()+"="+goods.get(1).getUsetime());
		assetUpdate.setGood(goods.get(0));
		assetUpdate.setMsg("镶嵌成功!你的"+goods.get(0).getGoodsname()+"变成#R+"+qhv);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
	/**开孔(强化系统)*/
	public static void type104(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(1000000);
		if (loginResult.getGold().compareTo(money)<0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不足"));
			return;
		}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 1);
		if (goods==null||goods.size()!=2||goods.get(1).getType()!=120||!Goodtype.EquipGem(goods.get(0).getType())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成公式不对"));
			return;
		}
		if (goods.get(0).getStatus()!=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能对背包的物品进行操作"));
			return;
		}
		Goodstable good=goods.get(0);
		if (good.getQht()!=null&&good.getQht()>=15) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("还未开放更高的孔数"));
			return;
		}
		if (goods.get(1).getUsetime()<1) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("材料数量不足"));
			return;
		}
		goods.get(1).goodxh(1);
		loginResult.setGold(loginResult.getGold().subtract(money));  
	   	MonitorUtil.getMoney().useD(money.longValue());
	   	AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(1).getRgid()+"="+goods.get(1).getUsetime());
		
		int lvl=good.getQht()!=null?good.getQht():0;
		int gl=20-lvl-(lvl>10?3:0);
		if (gl>SuitComposeAction.random.nextInt(108)) {//成功
			lvl++;
			good.setQht(lvl);
			assetUpdate.setGood(good);
			SuitComposeAction.saveGoods(goods,false);	
			assetUpdate.setMsg("#G开孔成功");
		}
		else {//失败
			SuitComposeAction.saveGoods(goods,true);
			assetUpdate.setMsg("装备没有丝毫变化");
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
}
