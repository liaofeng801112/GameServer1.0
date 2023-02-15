package org.come.action.suit;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.QualityClBean;
import org.come.bean.SuitOperBean;
import org.come.bean.UseCardBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Alchemy;
import org.come.model.PalEquip;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.GsonUtil;

import come.tool.Calculation.PalEquipQl;
import come.tool.Calculation.PalQl;
import come.tool.FightingData.Battlefield;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class SuitPalEquip {

	static String PALQH="契合度=";
	static String PALPZ="品质=";
	/**获取指定等级基础属性*/
	public static void PalEquipValue(Goodstable good,long type,int lvl,String[] v,int QH){
		PalEquip palEquip=GameServer.getPalEquip(type);
		if (lvl<1) {
			lvl=1;
		}else if (lvl>palEquip.getUpLvl()) {
			lvl=palEquip.getUpLvl();
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("等级=");
		buffer.append(lvl);
		if (palEquip.getLimits()!=null) {
			String limit=(lvl-1)<palEquip.getLimits().length?palEquip.getLimits()[lvl-1]:palEquip.getLimits()[palEquip.getLimits().length-1];	
			buffer.append("|");
			buffer.append(limit);
		}
		if (v!=null) {
			boolean isV=true;
			boolean isP=false;
			double xs=0.7D;
			for (int i = 1; i < v.length; i++) {
				if (v[i].startsWith(PALQH)) {
					buffer.append("|");
					buffer.append(PALQH);
					buffer.append("0/");
					buffer.append(palEquip.getQhs()[lvl-1]);
					isV=false;
				}else if (v[i].startsWith(PALPZ)) {
					String[] vs=v[i].split("=");
					if (vs[1].equals("黄金")) {xs=1D;}
					else if (vs[1].equals("白银")) {xs=0.85D;}
					buffer.append("|");
					buffer.append(v[i]);
					isP=true;
				}else if (isV) {
					if (isP) {
						try {
							String[] vs=v[i].split("=");
							double value=Double.parseDouble(vs[1]);
							PalQl palQl=palEquip.getPalQl(vs[0],value,xs,lvl,0);
							if (palQl!=null) {
								buffer.append("|");
								buffer.append(palQl.getKey());
								buffer.append("=");
								BigDecimal sx = new BigDecimal((palQl.getValue()+lvl*palQl.getSv())*xs);
								sx=sx.setScale(1,BigDecimal.ROUND_HALF_UP);
								if (sx.doubleValue()==sx.intValue()) {
									buffer.append(sx.intValue());
								}else {
									buffer.append(sx);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}	
					}			
				}else {
					buffer.append("|");
					buffer.append(v[i]);
				}
			}
		}else {
			int gl=SuitComposeAction.random.nextInt(100);
			double xs=0.7D;
			int JC=QH/(palEquip.getQhs()[lvl-1]/5);
			if (gl<4) {xs=1D;buffer.append("|品质=黄金");}
			else if (gl<20) {xs=0.85D;buffer.append("|品质=白银");}
			else {buffer.append("|品质=青铜");}
			for (int j = 0; j < palEquip.getQls().length; j++) {
				PalEquipQl equipQl=palEquip.getQls()[j];
				if (Battlefield.isV(equipQl.getV())) {
					PalQl palQl=equipQl.getPalQl();
					buffer.append("|");
					buffer.append(palQl.getKey());
					buffer.append("=");
					BigDecimal sx = new BigDecimal((palQl.getValue()+lvl*palQl.getSv()+JC*palQl.getSv()/5)*xs);
					sx=sx.setScale(1,BigDecimal.ROUND_HALF_UP);
					if (sx.doubleValue()==sx.intValue()) {
						buffer.append(sx.intValue());
					}else {
						buffer.append(sx);
					}
				}
			}
			buffer.append("|");
			buffer.append(PALQH);
			buffer.append(QH);
			buffer.append("/");
			buffer.append(palEquip.getQhs()[lvl-1]);
		}
		good.setGoodsname(palEquip.getNames()[lvl-1]);
		good.setSkin(palEquip.getSkins()[lvl-1]);
		good.setInstruction(palEquip.getIns()[lvl-1]);
		good.setValue(buffer.toString());
	}
	/**61伙伴装备培养*/
	public static void type61(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {return;}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods == null) {return;}
		// 消耗金钱
		BigDecimal money = new BigDecimal(100000);
		// 金钱不足
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		if (goods.size()!=2||!Goodtype.isPalEquip(goods.get(0).getType())||!(goods.get(1).getType()==7511||Goodtype.isPalEquip(goods.get(1).getType()))) {
			return;
		}
		if (goods.get(0).getRgid().compareTo(goods.get(1).getRgid())==0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("主副物品一致???"));
			return;
		}
		String[] v=goods.get(0).getValue().split("\\|");
		PalEquip palEquip=GameServer.getPalEquip(goods.get(0).getType());
		int lvl=Integer.parseInt(v[0].split("=")[1]);
		int add=1;
		if (goods.get(1).getType()==7511) {
			add=20+SuitComposeAction.random.nextInt(10);
		}else {
			int goodlvl=Integer.parseInt(goods.get(1).getValue().split("\\|")[0].split("=")[1]);
			if (goodlvl>=4) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能用1-3级的伙伴装备培养"));
				return;
			}
			if (goodlvl==1) {
				add=1+SuitComposeAction.random.nextInt(10);
			}else if (goodlvl==2) {
				add=20+SuitComposeAction.random.nextInt(10);
			}else if (goodlvl==3) {
				add=50+SuitComposeAction.random.nextInt(50);
			}
		}
		int qhz=0;
		int max=palEquip.getQhs()[lvl-1];
		int path=-1;
		for (int i = 2; i < v.length; i++) {
			if (v[i].startsWith(PALQH)) {
				path=i;
				String[] vs=v[i].substring(PALQH.length()).split("/");
				qhz=Integer.parseInt(vs[0]);
				break;
			}
		}
		if (qhz>=max||path==-1) {//契合度已满
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("契合度已满"));
			return;
		}
		int oJC=qhz/(max/5);
		qhz+=add;
		if (qhz>max) {qhz=max;}
		int nJC=qhz/(max/5);
		v[path]=PALQH+qhz+"/"+max;
		if (nJC==oJC) {//属性未变化
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < v.length; i++) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(v[i]);
			}
			goods.get(0).setValue(buffer.toString());
		}else{//属性变化
			boolean isV=true;
			boolean isP=false;
			double xs=0.7D;
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < v.length; i++) {
				if (v[i].startsWith(PALQH)) {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(v[i]);
					isV=false;
				}else if (v[i].startsWith(PALPZ)) {
					String[] vs=v[i].split("=");
					if (vs[1].equals("黄金")) {xs=1D;}
					else if (vs[1].equals("白银")) {xs=0.85D;}
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(v[i]);
					isP=true;
				}else if (isV&&isP) {
					try {
						String[] vs=v[i].split("=");
						double value=Double.parseDouble(vs[1]);
						PalQl palQl=palEquip.getPalQl(vs[0],value,xs,lvl,oJC);
						if (palQl!=null) {
							if (buffer.length()!=0) {buffer.append("|");}
							buffer.append(palQl.getKey());
							buffer.append("=");
							BigDecimal sx = new BigDecimal((palQl.getValue()+lvl*palQl.getSv()+nJC*palQl.getSv()/5)*xs);
							sx=sx.setScale(1,BigDecimal.ROUND_HALF_UP);
							if (sx.doubleValue()==sx.intValue()) {
								buffer.append(sx.intValue());
							}else {
								buffer.append(sx);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}else {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(v[i]);
				}
			}
			goods.get(0).setValue(buffer.toString());
		}
		goods.get(1).goodxh(1);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		SuitComposeAction.saveGoods(goods, false);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(1).getRgid()+"="+goods.get(1).getUsetime());
		assetUpdate.upmsg("增加了"+add+"点契合度");
		assetUpdate.setGood(goods.get(0));
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**62伙伴装备升级 重铸*/
	public static void type62(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {return;}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods == null) {return;}
		// 消耗金钱
		BigDecimal money = new BigDecimal(100000);
		// 金钱不足
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		if (goods.size()!=2||!Goodtype.isPalEquip(goods.get(0).getType())||goods.get(1).getType()!=500) {
			return;
		}
		if (goods.get(0).getRgid().compareTo(goods.get(1).getRgid())==0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("主副物品一致???"));
			return;
		}
		String[] v=goods.get(0).getValue().split("\\|");
		int lvl=Integer.parseInt(v[0].split("=")[1]);
		int goodlvl=Integer.parseInt(goods.get(1).getValue().split("=")[1]);
		int type=0;//1升级 2重铸
		if (lvl<=5) {
			if (lvl+5==goodlvl) {
				type=1;
			}else if (lvl+4==goodlvl) {
				type=2;
			}
		}else if (lvl==6) {
			if (goodlvl==10) {
				type=2;
			}
		}
		PalEquip palEquip=GameServer.getPalEquip(goods.get(0).getType());
		int qhz=0;
		int max=palEquip.getQhs()[lvl-1];
		for (int i = 2; i < v.length; i++) {
			if (v[i].startsWith(PALQH)) {
				String[] vs=v[i].substring(PALQH.length()).split("/");
				qhz=Integer.parseInt(vs[0]);
				break;
			
			}
		}
		if (type==1) {
			if (qhz<max) {//契合度未满
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("契合度未满"));
				return;
			}
			PalEquipValue(goods.get(0), goods.get(0).getType(), lvl+1, v, 0);	
		}else if (type==2) {
			PalEquipValue(goods.get(0), goods.get(0).getType(), lvl, null,qhz);	
		}else {
			return;
		}
		goods.get(1).goodxh(1);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		SuitComposeAction.saveGoods(goods, false);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(1).getRgid()+"="+goods.get(1).getUsetime());
		assetUpdate.setGood(goods.get(0));
		assetUpdate.upmsg(type==1?"升级成功":"重铸成功");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
//	等级=1|...|契合度=xxxx/xxxx
//	等级
//	装备限制
//  品质
//  黄字
//	契合度
//	炼化属性
	//装备升级
	//装备培养
	
	/**70:单人竞技场属性炼化*/
	public static void type70(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {return;}
		int lvl=suitOperBean.getType()-70;
		UseCardBean cardBean=roleData.getLimit("单人竞技场");
		if (cardBean==null||cardBean.getQls().length<lvl) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还未解锁对应的称谓"));
			return;
		}
		BigDecimal money = new BigDecimal(1000000);// 消耗金钱
		if (loginResult.getGold().compareTo(money) < 0) {// 金钱不足
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不足"));
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 1);
		if (goods == null||goods.size()!=1||goods.get(0).getType()!=119||goods.get(0).getUsetime()<=0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没有段位洗练丹"));
			return;
		}
		List<Alchemy> alchemies=GameServer.getAllAlchemy().get(lvl+"级单人竞技场");
		if (alchemies==null||alchemies.size()==0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("没有对应的炼化属性"));
			return;
		}
		String value=getOneArena(alchemies, lvl);
		QualityClBean clBean=new QualityClBean();
    	clBean.setRgid(loginResult.getRole_id());	
    	clBean.setType(suitOperBean.getType());
    	clBean.setNewAttr(value);
    	QualityCPool.getcPool().addExtra(clBean);	
    	
    	goods.get(0).goodxh(1);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		SuitComposeAction.saveGoods(goods, false);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-"+money);
		assetUpdate.updata("G"+goods.get(0).getRgid()+"="+goods.get(0).getUsetime());
		SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));  
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**获取单人竞技属性*/
	public static String getOneArena(List<Alchemy> alchemies,int lvl){
		if (alchemies==null) {
			alchemies=GameServer.getAllAlchemy().get(lvl+"级单人竞技场");
		}
		if (alchemies==null||alchemies.size()==0) {return "根骨=1";}
		Alchemy alchemy=alchemies.get(Battlefield.random.nextInt(alchemies.size()));
		return SuitComposeAction.lh(2+Battlefield.random.nextInt(5),alchemy,0);
	}
}
