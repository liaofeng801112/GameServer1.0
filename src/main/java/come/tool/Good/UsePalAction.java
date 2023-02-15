package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.suit.SuitComposeAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Pal;
import org.come.handler.SendMessage;
import org.come.model.PalData;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Calculation.BaseLimit;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class UsePalAction implements IAction{
//	81228	等级突破丹	7500
//	81229	伙伴经验书	7501
//	81230	成长进阶丹	7502
//	81231	武器		    7503
//	81232	衣服男		7504
//	81233	帽子男		7505
//	81234	项链		    7506
//	81235	鞋子		    7507
//	81236	衣服女		7508
//	81237	帽子女		7509
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		//伙伴参战休息   P伙伴id|位置    没有位置数据表示添加在最后面    位置-1表示休息     其他数字表示已参战和其他参战伙伴位置调整0 1 2 3 
		//伙伴激活          C准备激活的伙伴表id
		//伙伴物品使用     伙伴id|物品ID  脱装备时发 伙伴id|物品ID|T
		
		//等级突破30 60 90 120
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		String[] vs=message.split("\\|");
		int type=0;
		if (vs[0].startsWith("P")) {//伙伴参战休息 
			vs[0]=vs[0].substring(1);
			type=1;
		}else if (vs[0].startsWith("C")) {//伙伴激活
			palJH(Integer.parseInt(vs[0].substring(1)), ctx, loginResult);
			return;
		}
		Pal pal=AllServiceUtil.getPalService().selectPalByID(new BigDecimal(vs[0]));
		if (pal==null) {return;}
		if (pal.getRoleId().compareTo(loginResult.getRole_id())!=0) {return;}	
		if (type==0) {
			Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[1]));
			if (good==null) {return;}
			if (good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
			if (good.getUsetime()<=0) {return;}
			if (good.getType()==7500||good.getType()==7501) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该道具已失效"));
				return;
			} 
//			if (good.getType()==7500) {//等级突破丹
//				palUPGood(pal,good,ctx,loginResult);
//			}else if (good.getType()==7501) {//伙伴经验丹
//				palExpGood(pal,good,ctx,loginResult);
//			}
			else if (good.getType()==7502) {//成长进阶丹
				palJJGood(pal,good,ctx,loginResult);
			}else if (Goodtype.isPalEquip(good.getType())) {//伙伴装备
				palEquipGood(pal, good, ctx, loginResult, vs.length==2);
			}
		}else if (type==1) {//伙伴参战休息 
			palCZ(pal.getId(),loginResult, vs.length==2?Integer.parseInt(vs[1]):3);
		}
	}
	/**伙伴参战休息*/
	public void palCZ(BigDecimal id,LoginResult login,int p){
		RoleData data=RolePool.getRoleData(login.getRole_id());
		if (p==-1) {
			data.getPs().remove(id);
		}else if (p>=data.getPs().size()) {
			data.getPs().remove(id);
			data.getPs().add(id);
		}else {
			BigDecimal idTwo=data.getPs().get(p);
			int path=data.getPs().indexOf(id);
			data.getPs().set(p, id);
			if (path==-1) {
				if (data.getPs().size()<4) {
					data.getPs().add(idTwo);
				}
			}else {
				data.getPs().set(path, idTwo);
			}
		}
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < data.getPs().size(); i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(data.getPs().get(i));
		}
		login.setPals(buffer.length()!=0?buffer.toString():null);
	}
	/**伙伴激活*/
    public void palJH(int pid,ChannelHandlerContext ctx,LoginResult login){
		PalData palData=GameServer.getPalData(pid);
		if (palData==null) {return;}
		//先判断玩家是否已经存在同ID伙伴
		List<Pal> pals=AllServiceUtil.getPalService().selectPalByRoleID(login.getRole_id());
		for (int i = 0; i < pals.size(); i++) {
			if (pals.get(i).getpId()==palData.getPalId()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经激活该伙伴了"));
				return;
			}
		}
		StringBuffer buffer=new StringBuffer();
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		if (palData.getXh()!=null) {
			if (palData.getXh().startsWith("D")) {
				long xh=Long.parseLong(palData.getXh().substring(1));
				if (xh>login.getGold().longValue()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你金钱不足"+xh));
					return;
				}
				assetUpdate.updata("D=-"+xh);
				login.setGold(login.getGold().add(new BigDecimal(-xh)));
				MonitorUtil.getMoney().useD(xh);	
				buffer.append("你花费");
				buffer.append(xh);
				buffer.append("金钱获得");
			}else if (palData.getXh().startsWith("X")) {
				long xh=Long.parseLong(palData.getXh().substring(1));
                if (xh>login.getCodecard().longValue()) {
                	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你仙玉不足"+xh));
					return;
				}
                assetUpdate.updata("X=-"+xh);
                login.setCodecard(login.getCodecard().add(new BigDecimal(-xh)));
                MonitorUtil.getMoney().useX(xh);	
                buffer.append("你花费");
				buffer.append(xh);
				buffer.append("仙玉获得");
			}
		}else {
		    buffer.append("你通过免费激活获得");
		}
		buffer.append(palData.getName());
		assetUpdate.setMsg(buffer.toString());
		/**生成伙伴*/
		Pal pal=new Pal();
		pal.setpId(palData.getPalId());
		pal.setRoleId(login.getRole_id());
		AllServiceUtil.getPalService().insertPal(pal);
		assetUpdate.setPal(pal);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
    /**等级突破丹*/
    public void palUPGood(Pal pal,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
    	PalData palData=GameServer.getPalData(pal.getpId());
		if (palData==null) {return;}
		int  lvl=pal.getLvl();
		long exp=pal.getExp();
		long maxExp=ExpUtil.palExp(lvl);
		int sum=lvl==60?10:lvl==100?20:lvl==140?40:lvl==180?80:0;
		if (sum==0||maxExp>exp) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还未达到等级上限"));
			return;
		}
		if (sum>good.getUsetime()) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("物品数量不足"+sum));
			return;
		}
		exp-=maxExp;
    	lvl++;
     	maxExp=ExpUtil.palExp(lvl);
    	while (exp>maxExp) {
            if (lvl==60||lvl==100||lvl==140||lvl==180||lvl==200) {
 				break;
 			}
 	    	exp-=maxExp;
 	    	lvl++;
 	    	maxExp=ExpUtil.palExp(lvl);
 		}
    	pal.setExp(exp);
  	    pal.setLvl(lvl);
  	    good.goodxh(sum);
	    AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
	    AllServiceUtil.getPalService().updatePal(pal);
	    AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
	    assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
	    assetUpdate.upmsg ("你的伙伴:#R"+palData.getName()+"#Y成功突破等级上限");
	    assetUpdate.setPal(pal);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
    }
	/**伙伴经验丹*/
	public void palExpGood(Pal pal,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		PalData palData=GameServer.getPalData(pal.getpId());
		if (palData==null) {return;}
		int  lvl=pal.getLvl();
		long exp=pal.getExp();
		long maxExp=ExpUtil.palExp(lvl);
		if ((lvl==60||lvl==100||lvl==140||lvl==180)&&exp>=maxExp) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("伙伴已经达到等级上限,请先去突破"));
			return;
		}else if (lvl>=200) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("已达升级上限"));
			return;
		}
		long addExp=Long.parseLong(good.getValue().split("=")[1]);
		exp+=addExp;
	    while (exp>maxExp) {
            if (lvl==60||lvl==100||lvl==140||lvl==180||lvl==200) {
				break;
			}
	    	exp-=maxExp;
	    	lvl++;
	    	maxExp=ExpUtil.palExp(lvl);
		}
	    pal.setExp(exp);
	    pal.setLvl(lvl);
	    good.goodxh(1);
	    AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
	    AllServiceUtil.getPalService().updatePal(pal);
	    AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
	    assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
	    assetUpdate.upmsg ("你的伙伴:#R"+palData.getName()+"#Y获得#R"+addExp+"#Y经验");
	    assetUpdate.setPal(pal);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  		
	}
	/**伙伴进阶丹1 1.1 1.22 1.36*/
	public void palJJGood(Pal pal,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		PalData palData=GameServer.getPalData(pal.getpId());
		if (palData==null) {return;}
		double grow=pal.getGrow();
		if (grow>=1.36) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的伙伴已经达到最高品质"));
			return;
		}
		double nGrow=1;
        int gl=SuitComposeAction.random.nextInt(1000);
        if (gl<4) {
        	nGrow=1.36;
		}else if (gl<25) {
			nGrow=1.22;
		}else if (gl<160) {
			nGrow=1.1;
		}
		good.goodxh(1);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		if (grow!=nGrow) {
			pal.setGrow(nGrow);
			AllServiceUtil.getPalService().updatePal(pal);
			assetUpdate.setPal(pal);	
		}
		assetUpdate.upmsg ("你的伙伴:#R"+palData.getName()+"#Y品质变更为"+(nGrow==1?"#G资质平平":nGrow==1.1?"#B出类拔萃":nGrow==1.22?"#c800080神通广大":nGrow==1.36?"#cFF7F00万中无一":""));
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
	}
	/**伙伴装备穿戴 true是穿戴  fasle是脱下*/
	public void palEquipGood(Pal pal,Goodstable good,ChannelHandlerContext ctx,LoginResult login,boolean isEquip){
		PalData palData=GameServer.getPalData(pal.getpId());
		if (palData==null) {return;}
		if (good.getStatus()!=(isEquip?0:1)) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的装备状态异常"));
			return;
		}
		if (isEquip) {
			BaseLimit baseLimit=good.getEquip().getBaseLimit();
			if (baseLimit!=null) {
//				if (pal.getLvl()<baseLimit.getLvl()) {
//					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的伙伴等级不够"));
//					return;
//				}
				if (baseLimit.getSex()!=2&&palData.getSex()!=baseLimit.getSex()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的伙伴性别不对"));
					return;
				}
			}
		}else {
			RoleData roleData=RolePool.getRoleData(login.getRole_id());
			if (roleData.isGoodFull()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("背包已满,先清空背包"));
				return;
			}
		}
		int path=palEquipPath(good.getType());
		if (path==-1) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("非伙伴装备"));
			return;
		}
		StringBuffer buffer=new StringBuffer();
		boolean is=isEquip;
		BigDecimal oldRgid = null;
		if (pal.getParts()!=null&&!pal.getParts().equals("")) {
			String[] vs=pal.getParts().split("\\|");	
		    String qz=path+"=";
			for (int i = 0; i < vs.length; i++) {
				if (vs[i].startsWith(qz)) {
					oldRgid=new BigDecimal(vs[i].substring(qz.length()));
					if (!isEquip&&oldRgid.compareTo(good.getRgid())==0) {
						is=true;
					}
				}else {
					if (buffer.length()!=0) {
						buffer.append("|");
					}
					buffer.append(vs[i]);
				}
			}
		}
		if (!is) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的伙伴装备状态异常"));
			return;
		}
		Goodstable goodTwo=null;
		if (isEquip) {
			if (buffer.length()!=0) {
				buffer.append("|");
			}
			buffer.append(path);
			buffer.append("=");
			buffer.append(good.getRgid());
			if (oldRgid!=null) {
				goodTwo=AllServiceUtil.getGoodsTableService().getGoodsByRgID(oldRgid);	
			}
		}
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, null, isEquip?1:0);
		if (goodTwo!=null) {
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodTwo, null, null, 0);
		}
		pal.setParts(buffer.length()!=0?buffer.toString():null);
		AllServiceUtil.getPalService().updatePal(pal);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.setPal(pal);
		assetUpdate.setGood(good);
		if (goodTwo!=null) {
			assetUpdate.setGood(goodTwo);
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	} 
//	4 20 100
//	81231	武器		    7503
//	81232	衣服男		7504
//	81233	帽子男		7505
//	81234	项链		    7506
//	81235	鞋子		    7507
//	81236	衣服女		7508
//	81237	帽子女		7509
	/**判断装备的位置*/
	public static int palEquipPath(long type){
//		0 武器  1衣服  2帽子  3项链 4鞋子
		if (type==7503) {
			return 0;
		}else if (type==7504||type==7508) {
			return 1;
		}else if (type==7505||type==7509) {
			return 2;
		}else if (type==7506) {
			return 3;
		}else if (type==7507) {
			return 4;
		}
		return -1;	
	}
}
