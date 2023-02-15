package org.come.action.suit;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.action.summoning.SummonPetAction;
import org.come.bean.LoginResult;
import org.come.bean.QualityClBean;
import org.come.bean.SuitOperBean;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Gem;
import org.come.model.GemBase;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.AddGoodAction;
import come.tool.Good.UseRoleAction;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class GemCompose {
 
//	　    1、使用2颗等级相同的宝石即可合成。 
//	　　2、合成前宝石种类、属性都相同，合成后的宝石继承种类、属性。 
//	　　3、合成前宝石种类相同但属性不同，合成后的宝石继承种类、属性在该种类对应的 属性中随机1条。 
//	　　4、合成前宝石种类、属性都不同，合成后的宝石种类、属性随机。此种合成方式下有可能合成出奇异石。 
//	　　5、合成1~4级宝石时，100%几率成功。 
//	　　其它等级合成有一定几率失败。 
//	　　合成失败时第1个位置的宝石不变，第2个位置的宝石会消失于天地之间。
//	69 253
//	79 243
	/**17:宝石合成*/
	public static void type17(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(5000000);
    	if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足  
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		int isT=SuitComposeAction.isTrans(goods, 0);
		if (isT==-1) {SendMessage.sendMessageToSlef(ctx,SuitComposeAction.CHECKTS5);return;}
//		744等级于1级宝石
		Goodstable good1=goods.get(0);
		Goodstable good2=goods.get(1);
		int lvl1=0;int lvl2=0;
		long quality=good1.getQuality();
		String sType1=null;String sType2=null;
		if (good1.getType()==744) {lvl1=1;}
		else if (Goodtype.baoshi(good1.getType())) {
			String[] vs=good1.getValue().split("\\|");
			lvl1=Integer.parseInt(vs[0].split("=")[1]);
			sType1=vs[1].split("=")[0];
		}else {return;}
		if (good2.getQuality()%2==1) {quality=good2.getQuality();}
		if (good2.getType()==744) {lvl2=1;}
		else if (Goodtype.baoshi(good2.getType())) {
			String[] vs=good2.getValue().split("\\|");
			lvl2=Integer.parseInt(vs[0].split("=")[1]);
			sType2=vs[1].split("=")[0];
		}else {return;}
		if (lvl1!=lvl2||lvl1>=10) {return;}
	   	loginResult.setGold(loginResult.getGold().subtract(money));  
	   	MonitorUtil.getMoney().useD(money.longValue());
		if (sType1==null||sType2==null||good1.getType()!=good2.getType()||!sType1.equals(sType2)) {sType1=null;sType2=null;}
		int isUP=isUP(lvl1);
		if (isUP==2&&good1.getType()==good2.getType()) {isUP=1;}
		Goodstable good=null;
		boolean is=true;
		if (isUP==1) {
			lvl1++;
			for (int i = 0; i < goods.size(); i++) {
	    		if (i==0&&goods.get(i).getType()!=744) {continue;}
				goods.get(i).goodxh(1);
			}
			Gem gem=getGem(good1,good2);
			GemBase base=gem.getGemBase(sType1);
			Goodstable goodstable=GameServer.getGood(new BigDecimal(gem.getBid()));
			good=goods.get(0);	
			if (good.getType()==744) {
				is=false;
				good=goodstable;
				good.setRole_id(loginResult.getRole_id());
			}
			good.setType(goodstable.getType());
			good.setGoodsname(goodstable.getGoodsname());
			good.setInstruction(goodstable.getInstruction());
			if (lvl1>=7){good.setSkin((Integer.parseInt(goodstable.getSkin())+2)+"");}
			else if (lvl1>=4){good.setSkin((Integer.parseInt(goodstable.getSkin())+1)+"");}
			else {good.setSkin(goodstable.getSkin());}
			good.setValue(base.getGemValue(lvl1, 95+SuitComposeAction.random.nextInt(9)));
			good.setQuality(quality);
			if (lvl1>=7) {SuitMixdeal.Gem(loginResult.getRolename(), good.getGoodsname(), lvl1);}
			SendMessage.sendMessageToSlef(ctx,SuitComposeAction.CHECKTS3);
		}else if (isUP==2) {
			lvl1++;
			is=false;
			for (int i = 0; i < goods.size(); i++) {goods.get(i).goodxh(1);}	
			good=GameServer.getGood(new BigDecimal(81096));
			good.setRole_id(loginResult.getRole_id());
			good.setValue("等级="+lvl1);
			if (lvl1>=7){good.setSkin((Integer.parseInt(good.getSkin())+1)+"");}
			good.setQuality(quality);
			if (lvl1>=7) {SuitMixdeal.Gem(loginResult.getRolename(), good.getGoodsname(), lvl1);}
		}else{
			good=goods.get(0);
			for (int i = 1; i < goods.size(); i++) {goods.get(i).goodxh(1);}		
			SendMessage.sendMessageToSlef(ctx,SuitComposeAction.CHECKTS4);
		}
		SuitComposeAction.saveGoods(goods,is);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-5000000");
		for (int i = is?1:0; i < goods.size(); i++) {
			assetUpdate.updata("G"+goods.get(i).getRgid()+"="+goods.get(i).getUsetime());
		}
		if (isUP!=0) {
			if (good.getRgid()==null) {
				AllServiceUtil.getGoodsTableService().insertGoods(good);
			}else {
				AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			}
			assetUpdate.setGood(good);
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**判断宝石是否升级成功0失败 1成功 2奇异石*/
	public static int isUP(int lvl){
		if (lvl<=3) {return 1;}
		if (SuitComposeAction.random.nextInt(6)==0) {return 0;}
		else if (SuitComposeAction.random.nextInt(10)==0) {return 2;}
		else {return 1;}
	}
	/**获取宝石种类*/
	public static Gem getGem(Goodstable good1,Goodstable good2){
		String name=good1.getGoodsname();
		if (good1.getType()==744||good2.getType()==744) {name=null;}
		else if (good1.getType()!=good2.getType()) {name=null;}	
		return GameServer.getGem(name);
	} 
//	　1、需要消耗一颗比重铸宝石等级低4级的宝石 
//	　2、重铸100%成功 
//	　3、重铸前后价值不变 
//	　4、重铸可选择任意种类，属性从该种类对应下的属性中随机1条 
//	　5、>=5级宝石可以重铸
	/**18:宝石重铸*/
	public static void type18(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		int isT=SuitComposeAction.isTrans(goods, 0);
		if (isT==-1) {SendMessage.sendMessageToSlef(ctx,SuitComposeAction.CHECKTS5);return;}
		Goodstable good1=goods.get(0);
		Goodstable good2=goods.get(1);
		int lvl1=0;int lvl2=0,G=0;
		if (Goodtype.baoshi(good1.getType())) {
			String[] vs=good1.getValue().split("\\|");
			lvl1=Integer.parseInt(vs[0].split("=")[1]);
			G=Integer.parseInt(vs[2].split("=")[1]);
		}else {return;}
		if (good2.getType()==744) {lvl2=1;}
		else if (Goodtype.baoshi(good2.getType())) {
			String[] vs=good2.getValue().split("\\|");
			lvl2=Integer.parseInt(vs[0].split("=")[1]);
		}else {return;}
		if (lvl1-4!=lvl2) {return;}
		Gem gem=SuitMixdeal.getGemType(suitOperBean.getJade().getSuitid());
		GemBase base=gem.getGemBase(null);
		Goodstable goodstable=GameServer.getGood(new BigDecimal(gem.getBid()));
		good1.setType(goodstable.getType());
		good1.setGoodsname(goodstable.getGoodsname());
		good1.setInstruction(goodstable.getInstruction());
		if (lvl1>=7){good1.setSkin((Integer.parseInt(goodstable.getSkin())+2)+"");}
		else if (lvl1>=4){good1.setSkin((Integer.parseInt(goodstable.getSkin())+1)+"");}
		else {good1.setSkin(goodstable.getSkin());}
		good1.setValue(base.getGemValue(lvl1,G));
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
			assetUpdate.updata("G"+goods.get(i).getRgid()+"="+goods.get(i).getUsetime());
		}	
		assetUpdate.setGood(good1);
		SuitComposeAction.saveGoods(goods,false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		
	}
//	1|仅奇异石需要鉴定 
//	2|需要消耗一颗比奇异石等级低3级的宝石 
//	3|鉴定100%成功 
//	4|鉴定后，种类和属性随机 
//	5|鉴定后的宝石价值较高
	/**19:宝石鉴定*/
	public static void type19(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}	
		int isT=SuitComposeAction.isTrans(goods, 0);
		if (isT==-1) {SendMessage.sendMessageToSlef(ctx,SuitComposeAction.CHECKTS5);return;}
//		奇异石	770	770
		Goodstable good1=goods.get(0);
		Goodstable good2=goods.get(1);
		int lvl1=0;int lvl2=0;
		if (good1.getType()==770) {
			String[] vs=good1.getValue().split("\\|");
			lvl1=Integer.parseInt(vs[0].split("=")[1]);
		}else {return;}
		if (good2.getType()==744) {lvl2=1;}
		else if (Goodtype.baoshi(good2.getType())) {
			String[] vs=good2.getValue().split("\\|");
			lvl2=Integer.parseInt(vs[0].split("=")[1]);
		}else {return;}
		if (lvl1-3!=lvl2) {return;}
		Gem gem=GameServer.getGem(null);
		GemBase base=gem.getGemBase(null);
		Goodstable goodstable=GameServer.getGood(new BigDecimal(gem.getBid()));
		good1.setType(goodstable.getType());
		good1.setGoodsname(goodstable.getGoodsname());
		good1.setInstruction(goodstable.getInstruction());
		if (lvl1>=7){good1.setSkin((Integer.parseInt(goodstable.getSkin())+2)+"");}
		else if (lvl1>=4){good1.setSkin((Integer.parseInt(goodstable.getSkin())+1)+"");}
		else {good1.setSkin(goodstable.getSkin());}
		good1.setValue(base.getGemValue(lvl1, 100+SuitComposeAction.random.nextInt(9)));
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
			assetUpdate.updata("G"+goods.get(i).getRgid()+"="+goods.get(i).getUsetime());
		}	
		assetUpdate.setGood(good1);
		SuitComposeAction.saveGoods(goods,false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**20:宝石装备*/
	public static void type20(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		Goodstable good1=goods.get(0);
		Goodstable good2=goods.get(1);
		int lvl=0;
		if (Goodtype.baoshi(good2.getType())) {
			String[] vs=good2.getValue().split("\\|");
			lvl=Integer.parseInt(vs[0].split("=")[1]);
		}else{return;}
		if (goods.size()==2) {//脱下	
			BigDecimal money=new BigDecimal(3200000*lvl);
	    	if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足  
	       	loginResult.setGold(loginResult.getGold().subtract(money));  
	    	MonitorUtil.getMoney().useD(money.longValue());
	       	String[] vs=good1.getValue().split("\\|");
	    	String extra=SuitComposeAction.getExtra(vs, SuitComposeAction.Extras[4]);
	    	if (extra==null) {return;}
	    	QualityClBean clBean=new QualityClBean();
	    	clBean.setRgid(good1.getRgid());
	    	clBean.setType(5);
	    	StringBuffer buffer=new StringBuffer();
	    	buffer.append("宝石属性");
	    	String[] extras=extra.split("&");
	    	for (int i = 1; i < extras.length; i++) {
				 if (!extras[i].equals(good2.getRgid().toString())) {
					 buffer.append("&");
					 buffer.append(extras[i]);
				}
			}
	    	clBean.setNewAttr(buffer.toString());
	        if (clBean.getNewAttr().equals("宝石属性")) {clBean.setNewAttr(null);}
	        good1.setValue(SuitComposeAction.newExtra(vs, 4, clBean.getNewAttr()));
	        good2.setStatus(0);
	        SuitComposeAction.saveGoods(goods,false);		
	        SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));  
	    }else {//装备
	    	if ((lvl<=4?3:(lvl-1))!=goods.size()){return;}
			BigDecimal money=new BigDecimal(5000000+lvl*1000000);
	    	if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足  
	       	String[] vs=good1.getValue().split("\\|");
	    	String extra=SuitComposeAction.getExtra(vs, SuitComposeAction.Extras[4]);
	    	QualityClBean clBean=new QualityClBean();
	    	clBean.setRgid(good1.getRgid());
	    	clBean.setType(5);
	    	if (extra==null||extra.equals("")) {
	    		clBean.setNewAttr("宝石属性&"+good2.getRgid());
			}else {
				StringBuffer buffer=new StringBuffer();
		    	buffer.append("宝石属性");
		    	String[] extras=extra.split("&");
		    	for (int i=1;i<extras.length;i++) {
					 if (!extras[i].equals(good2.getRgid().toString())) {
						 Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(extras[i]));
						 if (good==null||good.getType()==good2.getType()) {return;}
						 buffer.append("&");
						 buffer.append(extras[i]);
					 }else {return;}
				}
		    	buffer.append("&");
				buffer.append(good2.getRgid().toString());
				clBean.setNewAttr(buffer.toString());
			}
		   	loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
	        good1.setValue(SuitComposeAction.newExtra(vs, 4, clBean.getNewAttr()));
	        good2.setStatus(1);
	        for (int i = 2; i < goods.size(); i++) {goods.get(i).goodxh(1);}	
	        SuitComposeAction.saveGoods(goods,false);		
	        SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));  
		}
	}
	/**21:兑换*/
	public static void type21(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
        if (goods==null) {return;}
		for (int i = 0; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
			if (goods.get(i).getUsetime()<0) {return;}
		}	
		PartJade jade=suitOperBean.getJade();
		if (jade.getSuitid()==0) {//兑换物品
			if (jade.getPartId()==170) {
				BigDecimal id=new BigDecimal(171);
				if (goods.size()!=3) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==80060) {
				BigDecimal id=new BigDecimal(80059);
				if (goods.size()!=5) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==80046) {
				BigDecimal id=new BigDecimal(80047);
				if (goods.size()!=1) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==92381) {
				BigDecimal id=new BigDecimal(188);
				if (goods.size()!=3) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==92383) {
				BigDecimal id=new BigDecimal(772);
				if (goods.size()!=3) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==80165) {
				BigDecimal id=new BigDecimal(80164);
				if (goods.size()!=10) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else {return;}
		}else if (jade.getSuitid()==1) {//兑换召唤兽
			if (jade.getPartId()>=200097&&jade.getPartId()<=200101) {
//				 901, 902, 903, 904, 905, 906, 907, 908, 909, 910 
				if (goods.size()!=10) {return;}
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(new BigDecimal(901+i))!=0) {return;}
				}
			}else if (jade.getPartId()==200096) {
				if (goods.size()!=4) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
			}else if (jade.getPartId()==200095) {
				if (goods.size()!=4) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
			}else if (jade.getPartId()==200094) {
				if (goods.size()!=4) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
			}else if (jade.getPartId()==200093) {
				if (goods.size()!=4) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
			}else if (jade.getPartId()==200092) {
				if (goods.size()!=4) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
			}else if (jade.getPartId()==200135) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
			}else if (jade.getPartId()==200147) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
			}else if (jade.getPartId()==200137) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
			}else if (jade.getPartId()==200145) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(183))!=0) {return;}
			}else if (jade.getPartId()==200132) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(183))!=0) {return;}
			}else if (jade.getPartId()==200133) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(183))!=0) {return;}
			}else if (jade.getPartId()==200134) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
			}else if (jade.getPartId()==200146) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
			}else if (jade.getPartId()==200136) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(174))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(176))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(177))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(179))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(183))!=0) {return;}
			}else if (jade.getPartId()==200144) {
				if (goods.size()!=6) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(175))!=0) {return;}
				if (goods.get(1).getGoodsid().compareTo(new BigDecimal(178))!=0) {return;}
				if (goods.get(2).getGoodsid().compareTo(new BigDecimal(180))!=0) {return;}
				if (goods.get(3).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(4).getGoodsid().compareTo(new BigDecimal(181))!=0) {return;}
				if (goods.get(5).getGoodsid().compareTo(new BigDecimal(182))!=0) {return;}
			}else if (jade.getPartId()==200157) {
				if (goods.size()!=1) {return;}
				if (goods.get(0).getGoodsid().compareTo(new BigDecimal(80047))!=0) {return;}
			}else if (jade.getPartId()==200116||jade.getPartId()==200117||jade.getPartId()==200123) {
				int size=0;
				if (jade.getPartId()==200116) {size=588;}
				else if (jade.getPartId()==200117) {size=488;}
				else {size=468;}
				if (goods.size()!=size) {return;}
				BigDecimal id=new BigDecimal(80167);
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else if (jade.getPartId()==200124||jade.getPartId()==200138||jade.getPartId()==200141||
				jade.getPartId()==200142||jade.getPartId()==200140||jade.getPartId()==200143||jade.getPartId()==200158) {
				int size=0;
				if (jade.getPartId()==200124||jade.getPartId()==200138||jade.getPartId()==200141) {size=188;}
				else if (jade.getPartId()==200142||jade.getPartId()==200140) {size=288;}
				else if (jade.getPartId()==200143||jade.getPartId()==200158) {size=388;}
				if (goods.size()!=size) {return;}
				BigDecimal id=new BigDecimal(80168);
				for (int i = 0; i < goods.size(); i++) {
					if (goods.get(i).getGoodsid().compareTo(id)!=0) {return;}
				}
			}else {return;}
		}else {return;}
		SuitComposeAction.saveGoods(goods,false);	
		if (jade.getSuitid()==0) {
			XXGDBean bean=new XXGDBean();
			bean.setId(jade.getPartId()+"");
			bean.setSum(1);
			BigDecimal id=new BigDecimal(bean.getId());
			Goodstable goodstable = GameServer.getGood(id);
			//特效物品判断是拥有特效
			if (id.longValue()<0&&roleData.getPackRecord().isTX(-id.longValue()+"")) {return;}
			if( goodstable==null ){return;}
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setMsg(bean.getSum()+"个"+goodstable.getGoodsname());
			AddGoodAction.addGood(assetUpdate,goodstable,loginResult,roleData,bean,21);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  					
		}else if (jade.getSuitid()==1) {
			RoleSummoning pet=GameServer.getPet(new BigDecimal(jade.getPartId()));
			if (pet==null) {return;}
			pet.setBasishp(pet.getHp());
			pet.setBasismp(pet.getMp());
			// 设置忠诚
			pet.setFaithful(100);
			pet.setGrade(0);pet.setTurnRount(0);
			pet.setBone(0);pet.setSpir(0);
			pet.setPower(0);pet.setSpeed(0);
			pet.setCalm(0);pet.setDragon(0);
			pet.setSpdragon(0);
			pet.setAlchemynum(0);pet.setExp(new BigDecimal(0));
			pet.setOpenSeal(1);
			pet.setOpenql(0);
			pet.setRoleid(loginResult.getRole_id());
			if (pet.getSsn()==null||pet.getSsn().equals("0")) {
//				pet.setHp(SummonPetAction.getchu(pet.getHp()));pet.setMp(SummonPetAction.getchu(pet.getMp()));
//				pet.setAp(SummonPetAction.getchu(pet.getAp()));pet.setSp(SummonPetAction.getchu(pet.getSp()));
//				pet.setGrowlevel(SummonPetAction.getgroup(pet.getGrowlevel()));	
			}	
			String yb = pet.getResistance();
			if (yb == null|| yb.equals("")) {
				int p=UseRoleAction.random.nextInt(SummonPetAction.kxs.length);
				int p2=UseRoleAction.random.nextInt(SummonPetAction.kxs.length);
				while (p2==p) {p2=UseRoleAction.random.nextInt(SummonPetAction.kxs.length);}
				pet.setResistance(SummonPetAction.kxs[p]+"|"+SummonPetAction.kxs[p2]);
			}
			AllServiceUtil.getRoleSummoningService().insertRoleSummoning(pet);
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			assetUpdate.setPet(pet);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  		
		}
	}
}
