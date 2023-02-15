package org.come.action.suit;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.gl.util.Config;
import io.netty.util.internal.StringUtil;
import org.come.action.IAction;
import org.come.action.buy.AddGoodUtil;
import org.come.action.monitor.MonitorUtil;
import org.come.action.npc.NpcComposeAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.bean.LoginResult;
import org.come.bean.QualityClBean;
import org.come.bean.SuitOperBean;
import org.come.entity.Goodstable;
import org.come.entity.PackRecord;
import org.come.entity.Suit;
import org.come.handler.SendMessage;
import org.come.model.Alchemy;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Mixdeal.AnalysisString;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class SuitComposeAction implements IAction {
	public static String[] Extras=new String[]{"炼化属性","炼器属性","神兵属性","套装属性","宝石属性","觉醒技", "五行属性","星阵属性" };
	public static String CHECKTS1=Agreement.getAgreement().PromptAgreement("#G开光成功");
	public static String CHECKTS2=Agreement.getAgreement().PromptAgreement("开光失败");
	public static String CHECKTS3=Agreement.getAgreement().PromptAgreement("#G升级成功");
	public static String CHECKTS4=Agreement.getAgreement().PromptAgreement("升级失败");
	public static String CHECKTS5=Agreement.getAgreement().PromptAgreement("材料里面存在绑定和非绑定材料");


	public static Random random=new Random();
	private static String[] TJ;//武器特技
	private static String[] YFTJ;//衣服特技
	private static String[] XLTJ;//项链特技
	private static String[] MZTJ;//帽子特技
	private static String[] XTJ;//鞋特技
	private static String[] HSTJ;//护身符特技
	static{
		TJ=new String[21];//武器特技
		for (int i = 0; i < TJ.length; i++) {
			TJ[i]=(8001+i)+"";

		}
//		YFTJ=new String[13];//衣服特技
//		for (int i = 0; i < YFTJ.length; i++) {
//			YFTJ[i]=(8020+i)+"";
//		}
//		XLTJ=new String[4];//项链特技
//		for (int i = 0; i < XLTJ.length; i++) {
//			XLTJ[i]=(8053+i)+"";
//		}
//		MZTJ=new String[13];//帽子特技
//		for (int i = 0; i < MZTJ.length; i++) {
//			MZTJ[i]=(8020+i)+"";
//		}
		XTJ=new String[12];//鞋特技
		for (int i = 0; i < XTJ.length; i++) {
			XTJ[i]=(8057+i)+"";
		}
		HSTJ=new String[31];//护符特技
		for (int i = 0; i < HSTJ.length; i++) {
			HSTJ[i]=(8022+i)+"";
		}
	}
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 接收客户端发来的数据
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		SuitOperBean suitOperBean = GsonUtil.getGsonUtil().getgson().fromJson(message, SuitOperBean.class);
		//0:合成   1:洗炼  2:保留洗炼  3:套装升级    4:玉符升级
		//5:拆解    6:转移    7:兑换灵修值  8收录  9添加玉符
		//10:开光 11:炼器 12保留炼器  13:清除炼器 14炼化
		//15一键培养佩饰   16神兵升级
		//17宝石合成//18宝石重铸//19宝石鉴定
		switch (suitOperBean.getType()) {
			case 0://0:合成
				type0(loginResult, ctx, suitOperBean);
				break;
			case 1://1:洗炼
			case 2://2:保留洗炼
				type1(loginResult, ctx, suitOperBean);
				break;
			case 3://3:套装升级
				type3(loginResult, ctx, suitOperBean);
				break;
			case 4://4:玉符升级
				type4(loginResult, ctx, suitOperBean);
				break;
			case 5://5:拆解
				type5(loginResult, ctx, suitOperBean);
				break;
			case 6:// 6:转移
				type6(loginResult, ctx, suitOperBean);
				break;
			case 7://7:兑换灵修值
				type7(loginResult, ctx, suitOperBean);
				break;
			case 8://8收录
				type8(loginResult, ctx, suitOperBean);
				break;
			case 9://9添加玉符
				type9(loginResult, ctx, suitOperBean);
				break;
			case 10://10:开光
				type10(loginResult, ctx, suitOperBean);
				break;
			case 11://11:炼器
			case 12://12:保留炼器
			case 13://13:清除炼器
				type11(loginResult, ctx, suitOperBean);
				break;
			case 14://14:炼化
				type14(loginResult, ctx, suitOperBean);
				break;
			case 15://15:一键培养
				type15(loginResult, ctx, suitOperBean);
				break;
			case 16://16:神兵升级
				type16(loginResult, ctx, suitOperBean);
				break;
			case 17://17宝石合成
				GemCompose.type17(loginResult, ctx, suitOperBean);
				break;
			case 18://18宝石重铸
				GemCompose.type18(loginResult, ctx, suitOperBean);
				break;
			case 19://19宝石鉴定
				GemCompose.type19(loginResult, ctx, suitOperBean);
				break;
			case 20://20宝石打造
				GemCompose.type20(loginResult, ctx, suitOperBean);
				break;
			case 21://21兑换
				GemCompose.type21(loginResult, ctx, suitOperBean);
				break;
			case 31://31培养
				WingCompose.type31(loginResult, ctx, suitOperBean);
				break;
			case 32://32升星
				WingCompose.type32(loginResult, ctx, suitOperBean);
				break;
			case 33://33品质
				WingCompose.type33(loginResult, ctx, suitOperBean);
				break;
			case 34://34重铸
				WingCompose.type34(loginResult, ctx, suitOperBean);
				break;
			case 35://35炼化
				WingCompose.type35(loginResult, ctx, suitOperBean);
				break;
			case 36://36翅膀升星石合成
				WingCompose.type36(loginResult, ctx, suitOperBean);
				break;
			case 41://41:召唤兽装备培养
				SuitPetEquip.type41(loginResult, ctx, suitOperBean);
				break;
			case 42://召唤兽装备分解
				SuitPetEquip.type42(loginResult, ctx, suitOperBean);
				break;
			case 43://召唤兽装备重铸
				SuitPetEquip.type43(loginResult, ctx, suitOperBean);
				break;
			case 44://召唤兽装备重悟技能
				SuitPetEquip.type44(loginResult, ctx, suitOperBean);
				break;
			case 45://召唤兽装备开启觉醒技
				SuitPetEquip.type45(loginResult, ctx, suitOperBean);
				break;
			case 46://召唤兽装备提升觉醒技等级
				SuitPetEquip.type46(loginResult, ctx, suitOperBean);
				break;
			case 51:// 星卡培养
				StarCard.type51(loginResult, ctx, suitOperBean);
				break;
			case 52:// 星卡升级
				StarCard.type52(loginResult, ctx, suitOperBean);
				break;
			case 53:// 星卡重洗神通
				StarCard.type53(loginResult, ctx, suitOperBean);
				break;
			case 54:// 星卡重洗五行
				StarCard.type54(loginResult, ctx, suitOperBean);
				break;
			case 55:// 星卡炼星
				StarCard.type55(loginResult, ctx, suitOperBean);
				break;
			case 56:// 斗转星移
				StarCard.type56(loginResult, ctx, suitOperBean);
				break;
			case 57:// 星卡魂归
				StarCard.type57(loginResult, ctx, suitOperBean);
				break;
			case 58:// 星卡星阵属性删除
				StarCard.type58(loginResult, ctx, suitOperBean);
				break;
			case 59:// 星卡战力补充
				StarCard.type59(loginResult, ctx, suitOperBean);
				break;
			case 61:// 伙伴装备培养
				SuitPalEquip.type61(loginResult, ctx, suitOperBean);
				break;
			case 62:// 伙伴装备升级
				SuitPalEquip.type62(loginResult, ctx, suitOperBean);
				break;
			case 71://一级单人竞技场属性炼化
			case 72://二级单人竞技场属性炼化
			case 73://三级单人竞技场属性炼化
			case 74://四级单人竞技场属性炼化
			case 75://五级单人竞技场属性炼化
			case 76://六级单人竞技场属性炼化
				SuitPalEquip.type70(loginResult, ctx, suitOperBean);
				break;
			case 101://宝石合成(强化系统)
				GemIntensify.type101(loginResult, ctx, suitOperBean);
				break;
			case 102://拆卸(强化系统)
				GemIntensify.type102(loginResult, ctx, suitOperBean,null);
				break;
			case 103://镶嵌(强化系统)
				GemIntensify.type103(loginResult, ctx, suitOperBean);
				break;
			case 104://开孔(强化系统)
				GemIntensify.type104(loginResult, ctx, suitOperBean);
				break;
		}
	}
	/**0:合成*/
	public void type0(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		PackRecord record=data.getPackRecord();
		BigDecimal money=new BigDecimal(100000);
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {
			return;
		}
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
		if (goodstable==null) {return;}
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		PartJade jade=suitOperBean.getJade();
		int pz=0;
		for (int i = 1; i < 6; i++) {if (jade.getJade(i)>0) {pz=i;break;}}
		if (pz==0) {return;}
		PartJade partJade=record.getPartJade(jade.getSuitid(), jade.getPartId());
		if (partJade.getJade(pz)<1) {return;}//玉符不足
		int type=Goodtype.EquipmentType(goodstable.getType());//判断装备部位
		if (jade.getPartId()==11) {
			if (type!=10) {
				return;
			}
		}else if (type!=jade.getPartId()) {
			return;
		}
		if (getExtra(goodstable.getValue(), Extras[3])!=null) {//不为套装
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		//扣除代价
		partJade.setJade(pz, -1);
		record.setPartJade(partJade);
		jade=partJade;
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		//修改物品
		Suit suit=GameServer.getSuit(jade.getSuitid());
		Goodstable good=GameServer.getGood(goodstable.getGoodsid());
		goodstable.setGoodsname(suit.getSname()+"·"+good.getGoodsname());
		goodstable.setSkin("tz"+jade.getSuitid()+"_"+jade.getPartId());
		String SuitV=SuitV(suit.getSuitID(),jade.getPartId(), pz);
		goodstable.setValue(suitGenerate(goodstable.getValue(), SuitV));
		assetUpdate.setGood(goodstable);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, 13);//添加记录
		assetUpdate.setJade(jade);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	public void type1(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {
			return;
		}
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
		if (goodstable==null) {return;}

		BigDecimal money=new BigDecimal(80000);
		if (suitOperBean.getType()==2) {
			money=new BigDecimal(100000);
		}
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		BigDecimal lxz=loginResult.getScoretype("灵修值");
		if (lxz.compareTo(new BigDecimal(30))<0) {return;}//灵修值不足
		String extra=getExtra(goodstable.getValue(),Extras[3]);
		if (extra==null) {return;}
		//扣除代价
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值=30", 3));

		String[] extras=extra.split("&");
		int suitid=Integer.parseInt(extras[1]);
		int partid=Integer.parseInt(extras[2]);
		int pz=SuitMixdeal.getPZlvl(extras[3]);
		String SuitV=SuitV(suitid, partid, pz);
		String value=newExtra(goodstable.getValue().split("\\|"),3,SuitV);
		if (suitOperBean.getType()==1) {
			goodstable.setValue(value);
			AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, 13);//添加记录
		}
		QualityClBean clBean=new QualityClBean();
		clBean.setRgid(goodstable.getRgid());
		clBean.setType(-4);
		clBean.setNewAttr(SuitV);
		if (suitOperBean.getType()==2) {
			clBean.setType(4);
			QualityCPool.getcPool().addExtra(clBean);
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));
	}
	public void type3(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		PackRecord record=data.getPackRecord();
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {return;}
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
		if (goodstable==null) {return;}
		BigDecimal money=new BigDecimal(10000000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		String extra=getExtra(goodstable.getValue(), Extras[3]);
		if (extra==null) {return;}
		String[] vs=extra.split("&");
		int suitid=Integer.parseInt(vs[1]);
		int partid=Integer.parseInt(vs[2]);
		int pz=SuitMixdeal.getPZlvl(vs[3])+1;
		PartJade partJade=record.getPartJade(suitid,partid);
		if (partJade.getJade(pz)<=0) {return;}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		partJade.setJade(pz, -1);
		record.setPartJade(partJade);
		assetUpdate.setJade(partJade);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < vs.length; i++) {
			if (i!=0) {
				buffer.append("&");
			}
			if (i!=3) {
				buffer.append(vs[i]);
			}else {
				buffer.append(SuitMixdeal.getPZName(pz));
			}
		}
		extra=buffer.toString();
		String[] vss=goodstable.getValue().split("\\|");
		vss[0]="套装品质="+SuitMixdeal.getPZName(pz);
		goodstable.setValue(newExtra(vss, 3, extra));
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, 13);//添加记录
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	public void type4(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		PackRecord record=data.getPackRecord();
		PartJade jade=suitOperBean.getJade();
		PartJade partJade=record.getPartJade(jade.getSuitid(),jade.getPartId());
		int pz=0;
		for (int i = 1; i < 6; i++) {if (jade.getJade(i)>0) {pz=i;break;}}
		if (pz==0) {return;}
		BigDecimal money=SuitMixdeal.returnJadeMoney(pz);
		int sum=SuitMixdeal.returnJadeNum(pz);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		if (partJade.getJade(pz)<sum) {return;}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		partJade.setJade(pz, -sum);
		partJade.setJade(++pz, 1);
		record.setPartJade(partJade);
		assetUpdate.setJade(partJade);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	public void type5(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {return;}
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
		if (goodstable==null) {return;}
		if (getExtra(goodstable.getValue(), Extras[3])==null) {return;}
		BigDecimal money=new BigDecimal(100000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		Goodstable good=GameServer.getGood(goodstable.getGoodsid());
		goodstable.setGoodsname(good.getGoodsname());
		goodstable.setSkin(good.getSkin());
		goodstable.setValue(suitCancel(goodstable.getValue()));
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, 13);//添加记录
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		assetUpdate.setGood(goodstable);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}

	public void type6(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {return;}
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
		if (good==null) {return;}
		Goodstable good2=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(1));
		if (good2==null) {return;}
		String extra=getExtra(good.getValue(), Extras[3]);
		if (extra==null) {return;}
		if (getExtra(good2.getValue(), Extras[3])!=null) {return;}
		String[] vs=extra.split("&");
		int lxzx=SuitMixdeal.getSxlxz(SuitMixdeal.getPZlvl(vs[3]));
		BigDecimal lxz=loginResult.getScoretype("灵修值");
		if (lxz.compareTo(new BigDecimal(lxzx))<0) {return;}//灵修值不足
		BigDecimal money=new BigDecimal(10000000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		//扣除代价
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值="+lxzx, 3));
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		String name=good.getGoodsname();
		String skin=good.getSkin();
		Goodstable goodstable=GameServer.getGood(good.getGoodsid());
		good.setGoodsname(goodstable.getGoodsname());
		good.setSkin(goodstable.getSkin());
		good.setValue(suitCancel(good.getValue()));
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 13);//添加记录
		good2.setGoodsname(name.split("·")[0]+"·"+good2.getGoodsname());
		good2.setSkin(skin);
		good2.setValue(suitGenerate(good2.getValue(), extra));
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good2);
		AllServiceUtil.getGoodsrecordService().insert(good2, null, 1, 13);//添加记录
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		assetUpdate.setGood(good);
		assetUpdate.setGood(good2);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}

	public void type7(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		PackRecord record=data.getPackRecord();

		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		PartJade jade=suitOperBean.getJade();
		int lxzv=0;
		if (suitOperBean.getGoods()==null||suitOperBean.getGoods().size()==0) {
			//使用玉符换
			int pz=0;
			for (int i = 1; i < 6; i++) {if (jade.getJade(i)>0) {pz=i;break;}}
			if (pz==0) {return;}
			PartJade partJade=record.getPartJade(jade.getSuitid(), jade.getPartId());
			if (jade.getJade(pz)<=0||jade.getJade(pz)>partJade.getJade(pz)) {return;}
			partJade.setJade(pz, -jade.getJade(pz));
			record.setPartJade(partJade);
			assetUpdate.setJade(partJade);
			lxzv=SuitMixdeal.returnExcNum(pz)*jade.getJade(pz);
		}else {//九天玄玉换
			Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(suitOperBean.getGoods().get(0));
			if (good==null) {return;}
			if (jade.getJade1()>good.getUsetime()||jade.getJade1()<=0) {return;}
			good.setUsetime(good.getUsetime()-jade.getJade1());
			lxzv=SuitMixdeal.returnExcNum(6)*jade.getJade1();
			AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			AllServiceUtil.getGoodsrecordService().insert(good, null, jade.getJade1(), 13);//添加记录
		}
		//添加灵修值
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值="+lxzv, 2));
		assetUpdate.updata("灵修值="+lxzv);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}

	public void type8(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		int type=0;//0消耗玉符收录 1激活 2删除
		PackRecord record=data.getPackRecord();
		PartJade jade=suitOperBean.getJade();
		int pz=0;
		if (jade.getPartId()!=0) {
			for (int i = 1; i < 6; i++) {if (jade.getJade(i)>0) {pz=i;break;}}
			if (pz==0) {
				type=1;
			}
		}else {
			type=2;
		}
		PartJade partJade=null;
		BigDecimal sxlxz=null;//灵修
		BigDecimal money=null;
		if (type==0) {
			partJade=record.getPartJade(jade.getSuitid(), jade.getPartId());
			if (partJade.getJade(pz)<=0) {return;}
			int sum=0;
			String[] vs=record.getCollect(partJade.getSuitid());
			if (vs!=null) {sum=vs.length;}
//        	sxlxz=new BigDecimal((sum+1)*50);//灵修
			sxlxz=new BigDecimal(50);//灵修
			money=new BigDecimal((sum+1)*1000000);
			BigDecimal lxz=loginResult.getScoretype("灵修值");
			if (lxz.compareTo(sxlxz)<0) {return;}//灵修值不足
			if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		}else if (type==1) {
			sxlxz=new BigDecimal(200);//灵修
			money=new BigDecimal(500000);
			BigDecimal lxz=loginResult.getScoretype("灵修值");
			if (lxz.compareTo(sxlxz)<0) {return;}//灵修值不足
			if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		if (type==0) {//消耗
			partJade.setJade(pz, -1);
			record.setPartJade(partJade);
			assetUpdate.setJade(partJade);
			loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值="+sxlxz, 3));
			loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
			record.setCollect(partJade.getSuitid(), partJade.getPartId());
		}else if (type==1) {
			loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值="+sxlxz, 3));
			loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
			record.setCollect(jade.getSuitid(), jade.getPartId());
		}else {
			record.setCollect(jade.getSuitid());
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().packRecordAgreement(4+record.getCollect()));
	}

	public void type9(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData data=RolePool.getRoleData(loginResult.getRole_id());
		if (data==null) {return;}
		PackRecord record=data.getPackRecord();

		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.SUIT);
		PartJade jade=suitOperBean.getJade();
		PartJade partJade=record.getPartJade(jade.getSuitid(), jade.getPartId());
		if (jade.getJade1()<=0) {
			return;
		}
		BigDecimal sxlxz=new BigDecimal(jade.getJade1()*10);//灵修
		BigDecimal money=new BigDecimal(jade.getJade1()*1000000);
		BigDecimal lxz=loginResult.getScoretype("灵修值");
		if (lxz.compareTo(sxlxz)<0) {return;}//灵修值不足
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		partJade.setJade(1, jade.getJade1());
		record.setPartJade(partJade);
		assetUpdate.setJade(partJade);
		//修改灵修值
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "灵修值="+sxlxz, 3));
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
//      assetUpdate.setValueD("灵修值=-"+lxzv);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}
	public void type10(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(100000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		List<Goodstable> goods=getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		String extra=getExtra(goods.get(0).getValue(), Extras[1]);
		if (extra==null) {
			extra="炼器属性&0";
		}
		String[] v=extra.split("&");
		int kglvl=Integer.parseInt(v[1]);
		//30 21 12 6 3
		if (kglvl>=5) {return;}
		int gl=0;
		switch (kglvl) {
			case 0:gl=32;break;
			case 1:gl=24;break;
			case 2:gl=18;break;
			case 3:gl=12;break;
			case 4:gl=6;break;
		}
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
		}
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		if (gl>random.nextInt(108)) {//升级成功
			kglvl++;
			goods.get(0).setUsetime(1);
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < v.length; i++) {
				if (buffer.length()!=0) {
					buffer.append("&");
				}
				if (i==1) {buffer.append(kglvl);}
				else {buffer.append(v[i]);}
			}
			goods.get(0).setValue(newExtra(goods.get(0).getValue().split("\\|"), 1, buffer.toString()));
			AddGoodUtil.addGood(ctx, goods.get(0));
			SendMessage.sendMessageToSlef(ctx, CHECKTS1);
			saveGoods(goods,false);
		}else {//升级失败
			goods.get(0).setUsetime(1);
			SendMessage.sendMessageToSlef(ctx, CHECKTS2);
			saveGoods(goods,true);
		}

	}
	public void type11(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(100000);
		//BigDecimal money2=null;
		int money2=0;
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		List<Goodstable> goods=getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), suitOperBean.getType()==13?1:4);
		if (goods==null) {return;}
		if (suitOperBean.getType()!=13) {
			int lock=0;
			int size=0;
			PartJade jade=suitOperBean.getJade();
			if (jade!=null) {
				lock=jade.getSuitid();
				for (int i = 0; i < 6; i++) {
					if ((int)(lock/Math.pow(10,i))%10!=0) {
						size++;
					}
				}
				if (size>3) {return;}
				if (size > 0) {
					money2 += GameServer.getAllLianHua().get1000(size).getMoney();
				}
			}
			if (money2>0) {
				BigDecimal m2= new BigDecimal(money2);
				loginResult.setCodecard(loginResult.getCodecard().subtract(m2));
				MonitorUtil.getMoney().useX(m2.longValue());
			}

			loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
			String extra=getExtra(goods.get(0).getValue(), Extras[1]);
			if (extra==null) {extra="炼器属性&0";}
			String[] v=extra.split("&");
			int kglvl=Integer.parseInt(v[1]);
			if (kglvl>5) {return;}
			size++;
			extra=RefinersV(v,kglvl,lock,size);
			String value=newExtra(goods.get(0).getValue().split("\\|"),1,extra);
			goods.get(0).setValue(value);
			QualityClBean clBean=new QualityClBean();
			clBean.setRgid(goods.get(0).getRgid());
			clBean.setType(-2);
			clBean.setNewAttr(extra);
			SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));
		}else {
			loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
			String value=newExtra(goods.get(0).getValue().split("\\|"),1,null);
			goods.get(0).setValue(value);
			AddGoodUtil.addGood(ctx, goods.get(0));
		}
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
		}
		saveGoods(goods,false);
	}

	public void type14(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(100000);
		int money2=0;
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		List<Goodstable> goods=getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null||goods.size()<1) {return;}
		int max = 5;
		int lock=0;
		if (Goodtype.GodEquipment_xian(goods.get(0).getType())||Goodtype.GodEquipment_Ding(goods.get(0).getType())){
			max= Config.getInt("xq_lianhua");//==5
			long type=goods.get(1).getType();
			if (!Goodtype.GodEquipment_xian(type)&&!Goodtype.xianlihe(type)&&type!=7010) {return;}
		}else if (Goodtype.GodEquipment_God(goods.get(0).getType())) {
			max= Config.getInt("sb_lianhua");
			if (goods.get(1).getType()!=191) {return;}
		}else if (Goodtype.OrdinaryEquipment(goods.get(0).getType())) {
			max = Config.getInt("pt_lianhua");
		}else if (goods.size()==6) {
			if (goods.get(1).getType()!=497||goods.get(2).getType()!=499||
					goods.get(3).getType()!=498||goods.get(4).getType()!=498||goods.get(5).getType()!=498) {
				return;
			}
		}else {
			return;
		}

		if (GameServer.lianhua==0) {
			if(max != 1)  {
				PartJade jade=suitOperBean.getJade();
				if (jade!=null) {
					lock=jade.getSuitid();
					int size=0;
					for (int i = 0; i < 8; i++) {
						if ((int)(lock/Math.pow(10,i))%10!=0) {
							size++;
						}
					}
					if (size > 0) {
						money2 += GameServer.getAllLianHua().get2000(size).getMoney();
					}
				}
			}
		}
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		if (money2!=0) {
			BigDecimal m2= new BigDecimal(money2);
			loginResult.setCodecard(loginResult.getCodecard().subtract(m2));
			MonitorUtil.getMoney().useX(m2.longValue());
		}
		String extra=RefinersV(goods.get(0),max,lock);
		QualityClBean clBean=new QualityClBean();
		clBean.setRgid(goods.get(0).getRgid());
		clBean.setType(1);
		clBean.setNewAttr(extra);
		QualityCPool.getcPool().addExtra(clBean);
		SendMessage.sendMessageToSlef(ctx,Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(clBean)));
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
		}
		saveGoods(goods,true);
	}
	public void type15(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(100000);
		List<Goodstable> goods=getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		if (suitOperBean.getJade()==null) {return;}
		int sum=suitOperBean.getJade().getJade1();//一次性培养的数量
		if (sum<=0) {return;}
		if (goods.get(1).getUsetime()<sum) {return;}
		money=new BigDecimal(money.longValue()*sum);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		goods.get(1).goodxh(sum);
		BigDecimal ysid=goods.get(0).getGoodsid();
		AccC(goods.get(0), sum);
		BigDecimal nsid=goods.get(0).getGoodsid();
		goods.get(0).setGoodsid(ysid);
		saveGoods(goods,true);
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(goods.get(0), null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(goods.get(0),null,1,13);//添加记录
		AddGoodUtil.addGood(ctx, goods.get(0));
	}
	public void type16(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		BigDecimal money=new BigDecimal(100000);
		List<Goodstable> goods=getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 2);
		if (goods==null) {return;}
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		int l=0;
		if (Goodtype.GodEquipment_God(goods.get(1).getType())) {l=2;}
		String[] vs=goods.get(0).getValue().split("\\|");
		int ylvl=Integer.parseInt(vs[0].split("=")[1]);
		if (l==2&&ylvl<=3) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("4级及以上神兵才可以精炼"));
			return;
		}
		int v=1;
//	   	1升2 5000成功 2升3 4000成功 3升4 2500成功 4升5 500成功 5升6 1成功
		switch (ylvl) {
			case 1:v=l==2?3000:5500;break;
			case 2:v=l==2?2000:4000;break;
			case 3:v=l==2?800:2500;break;
			case 4:v=l==2?85:600;break;
			case 5:v=l==2?5:80;break;
		}
		if (v>random.nextInt(12000)) {//成功
			ylvl++;
			if (ylvl>6) {ylvl=6;}
			goods.get(0).setValue(SuitMixdeal.GetGodEquipment(vs,ylvl));
			SuitMixdeal.sbsj(ylvl,loginResult.getRolename(), goods.get(0).getGoodsname());
			l=1;
			SendMessage.sendMessageToSlef(ctx,CHECKTS3);
		}else {//失败
			SendMessage.sendMessageToSlef(ctx,CHECKTS4);
		}
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		for (int i = l!=0?1:0; i < goods.size(); i++) {goods.get(i).goodxh(1);}
		saveGoods(goods,l==2);
		if (l!=0) {AddGoodUtil.addGood(ctx, goods.get(0));}
	}
	/**禁交易判断 is0不能禁交易与非禁交易合成  1可以 -1存在禁交易物品 0不修改物品贵重   其他值修改物品对应贵重*/
	public static int isTrans(List<Goodstable> list,int is){
		boolean isT=AnalysisString.jiaoyi(list.get(0).getQuality());
		int value=-1;
		for (int i=1,length=list.size();i<length;i++) {
			if (isT!=AnalysisString.jiaoyi(list.get(i).getQuality())) {
				value=list.get(i).getQuality().intValue();
				break;
			}
		}
		if (value==-1) {
			return 0;
		}else if (is==0) {
			return -1;
		}
		if (isT) {
			return 0;
		}else {
			return value;
		}
	}

	/**获取list good*/
	public static List<Goodstable> getGoods(List<BigDecimal> rgids,BigDecimal role_id,int min){
		if (rgids==null) {return null;}
		List<Goodstable> goods=new ArrayList<>();
		s:for (int i = 0; i < rgids.size(); i++) {
			BigDecimal id=rgids.get(i);
			for (int j = 0; j < goods.size(); j++) {
				if (goods.get(j).getRgid().compareTo(id)==0) {
					goods.add(goods.get(j));
					continue s;
				}
			}
			Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(id);
			if (good==null||good.getRole_id().compareTo(role_id)!=0) {
				return null;
			}
			goods.add(good);
		}
		if (min>goods.size()) {
			return null;
		}
		return goods;
	}
	/**保存list*/
	public static void saveGoods(List<Goodstable> goods,boolean l){
		s:for (int i = l?1:0; i < goods.size(); i++) {
			Goodstable good=goods.get(i);
			BigDecimal id=good.getRgid();
			for (int j = i+1; j < goods.size(); j++) {
				if (goods.get(j).getRgid().compareTo(id)==0) {
					continue s;
				}
			}
			//保存
			AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			long gType=good.getType();
			if (gType!=212&&!(gType>=497&&gType<=500)&&gType!=505&&gType!=7005&&gType!=191&&gType!=915) {
				AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 13);//添加记录
			}
		}
	}
	/**佩饰培养*/
	public static void AccC(Goodstable good,int size){
		String[] qs = good.getValue().split("\\|");
//    	等级=4|敏捷要求=60|敏捷=6|加气血=1200|力量=6|灵性=6|培养=0/30|标签=4|值=1
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < qs.length; i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			if(qs[i].length()>=2&&qs[i].substring(0, 2).equals("培养")){
				String[] num = qs[i].split("=")[1].split("/");
				int value = Integer.parseInt(num[0]);
				int max = Integer.parseInt(num[1]);
				value+=size;
				if (value>max) {
					Goodstable good2=GameServer.getGood(good.getGoodsid().add(new BigDecimal(1)));
					if (good2.getValue().startsWith(qs[0])) {
						good2=GameServer.getGood(good.getGoodsid().add(new BigDecimal(2)));
					}
					good.setGoodsid(good2.getGoodsid());
					good.setSkin(good2.getSkin());
					buffer.setLength(0);
					buffer.append(NpcComposeAction.getGoodsAttribute(good2));

					break;
				}else {
					buffer.append("培养=");
					buffer.append(value);
					buffer.append("/");
					buffer.append(max);
				}
			}else {
				buffer.append(qs[i]);
			}
		}
		good.setValue(buffer.toString());
	}
	/**炼化*/
	public static String RefinersV(Goodstable good,int max,int lock){
		double xs=0;
		String[] TJS=null;
		StringBuffer buffer=new StringBuffer();
		buffer.append("炼化属性");
		String[] vl=good.getValue().split("\\|");
		//是普通装备的话-10级
		long Gtype=good.getType();
		int lvl=0;
		if (Gtype==8888) {
			lvl=SuitMixdeal.getPZlvl(vl[0].split("=")[1]);
			lvl=lvl/2+3;
		}else if (Goodtype.isPalEquip(Gtype)) {
			lvl=3+Integer.parseInt(vl[0].split("=")[1])/2;
		}else {
			lvl=Integer.parseInt(vl[0].split("=")[1]);
			if (Goodtype.OrdinaryEquipment(Gtype)) {lvl-=10;}
		}
		if (Gtype==612) {//护身符获取系数   属性值=满属性值×品质/1000×等级/6
			for (int i = 0; i < vl.length; i++) {
				String[] vsz=vl[i].split("=");
				if (vsz[0].equals("等级")) {lvl=Integer.parseInt(vsz[1]);}
				else if (vsz[0].equals("品质")) {xs=Integer.parseInt(vsz[1].split("/")[0]);}
			}
			xs=xs/1000.0*lvl/6.0;
		}
		int ZBType=Goodtype.EquipmentType(Gtype);
		String etype=SuitMixdeal.lianhua(Gtype);
		if (lvl>6||lvl<=0) {lvl=1;}
		if (etype==null) {return null;}
		List<Alchemy> alchemies=GameServer.getAllAlchemy().get(etype);
		int size=SuitMixdeal.getAlchemySum(Gtype,max);//判断随机的条数
		String[] as=new String[5];		//存储最大炼化条数
		int p=0;
		if (lock!=0) {
			String extra=getExtra(vl, Extras[0]);
			if (extra!=null) {
				int t=-1;
				String[] extras=extra.split("&");
				if (extras[extras.length-1].startsWith("特技")) {
					t=extras.length-1;
				}
				for (int i = 0; i <6; i++) {
					if ((int)(lock/Math.pow(10,i))%10!=0) {
						if (t==-1||i<t-1) {
							if (i<extras.length-1) {
								if (!extras[i+1].startsWith("特技")) {
									as[p++]=extras[i+1];
								}
							}
						}else if (t!=-1) {
							String[] tjs=extras[t].split("=");
							if ((i-t+2)<tjs.length) {
								if (TJS==null) {TJS=new String[2];}
								for (int j = 0; j < TJS.length; j++) {
									if (TJS[j]==null) {
										TJS[j]=tjs[i-t+2];
										break;
									}
								}
							}
						}
					}
				}
				if (p>=size) {size=p+1;}
				if (size>max) {size=max;}
			}
		}else {
			if (lvl>1&&random.nextInt(5)<=1){lvl--;}
		}
		int u=0;
		for (int i = p; i < as.length; i++) {
			Alchemy alchemy=null;
			boolean a=true;
			while (a) {
				u=0;
				a=false;
				int v=random.nextInt(alchemies.size());
				alchemy=alchemies.get(v);
				for (int j = 0; j < as.length; j++) {
					if (as[j]!=null) {
						if (as[j].startsWith(alchemy.getAlchemykey())) {
							u++;
							if (u>=5) {//相同条数
//							if (u>=2) {//相同条数
								a=true;
							}else if (random.nextInt(11)<=4) {
								a=true;
							}
						}
					}
				}
			}
			as[p++]=lh(lvl,alchemy,xs);
		}
		for (int i = 0; i < size; i++) {
			if (as[i]!=null) {
				buffer.append("&");
				buffer.append(as[i]);
			}
		}
		if (TJS==null) {
			if (ZBType==0) {TJS=TJ;}
			else if (ZBType==4 ) {TJS=HSTJ;}
			else if (ZBType==3 ) {TJS=YFTJ;}
			else if (ZBType==2 ) {TJS=XLTJ;}
			else if (ZBType==1 ) {TJS=MZTJ;}
			else if (ZBType==5 ) {TJS=XTJ;}
			//判断是否出特技
			if (TJS!=null) {
				size=SuitMixdeal.getTJSum(Gtype);
				if (size!=0) {
					int y=-1;
					buffer.append("&特技");
					for (int i = 0; i < size; i++) {
						int sj=random.nextInt(TJS.length);
						if (sj==y) {continue;}
						y=sj;
						buffer.append("=");
						buffer.append(TJS[sj]);
					}
				}
			}
		}else {
			buffer.append("&特技");
			for (int i = 0; i < TJS.length; i++) {
				if (TJS[i]!=null) {
					buffer.append("=");
					buffer.append(TJS[i]);
				}
			}
		}
		return buffer.toString();
	}
	/**生成炼器属性*/
	public static String RefinersV(String[] vs,int lvl,int lock,int min){
		if (min>5) {min=5;}
		int sum=RefinersS(lvl);
		if (min>sum) {sum=min;}
		List<Alchemy> alchemies=GameServer.getAllAlchemy().get("武器开光");
		StringBuffer buffer=new StringBuffer();
		buffer.append("炼器属性&");
		buffer.append(lvl);
		List<String> a=new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			if ((int)(lock/Math.pow(10,i))%10!=0) {
				int v=i+2;
				if (v<vs.length) {a.add(vs[v].split("=")[0]);}
			}
		}
		int vvv=0;
		for (int i = 0; i < sum; i++) {
			if ((int)(lock/Math.pow(10,i))%10!=0) {
				int v=i+2;
				if (v<vs.length) {
					buffer.append("&");
					buffer.append(vs[v]);
					continue;
				}
			}
			Alchemy alchemy=null;
			s:while (true) {
				vvv++;
				if (vvv>50000) {break;}
				int size=0;
				int v=random.nextInt(alchemies.size());
				alchemy=alchemies.get(v);
				for (int j = 0; j < a.size(); j++) {
					if (a.get(j).equals(alchemy.getAlchemykey())) {
						size++;
						if (random.nextInt(5)<=1) {continue s;}
						if (size>=4) {continue s;}
					}
				}
				break;
			}
			a.add(alchemy.getAlchemykey());
			buffer.append("&");
			buffer.append(lh(2+((lvl+1)/2),alchemy,0));
		}
		for (int i = sum; i < 5; i++) {
			if ((int)(lock/Math.pow(10,i))%10!=0) {
				int v=i+2;
				if (v<vs.length) {
					buffer.append("&");
					buffer.append(vs[v]);
				}
			}
		}
		return buffer.toString();
	}
	/**根据装备类型获取最大炼器条数*/
	public static int RefinersS(int max){
		int size = 1;
		if (random.nextInt(100)<80) {size++;}
		if (random.nextInt(100)<70) {size++;}
		if (max>3) {
			if (random.nextInt(100)<60){
				size++;
			}
			if (max>4) {
				if (random.nextInt(100)<50){
					size++;
				}
			}
		}
		return max>size?size:max;
	}
	/**生成套装属性*/
	public String SuitV(int suitid,int partid,int pz){
//		套装属性&套装id=xx&套装品质=xx&套装属性1=xx&套装属性2=xx
		StringBuffer buffer=new StringBuffer();
		buffer.append("套装属性&");
		buffer.append(suitid);
		buffer.append("&");
		buffer.append(partid);
		buffer.append("&");
		buffer.append(SuitMixdeal.getPZName(pz));
		List<Alchemy> alchemies=GameServer.getAllAlchemy().get("套装");
		int v=random.nextInt(alchemies.size());
		buffer.append("&");
		buffer.append(lh(pz,alchemies.get(v),0));
		if (40>random.nextInt(100)) {
			int v2=random.nextInt(alchemies.size());
			if (v2!=v) {
				buffer.append("&");
				buffer.append(lh(pz,alchemies.get(v2),0));
			}
		}
		return buffer.toString();
	}
	/**
	 * 根据等级和属性值返随机后的数值
	 * xs=0 随机  xs!=0 按系数或者数值
	 */
	public static String lh(int lvl,Alchemy alchemy,double xs){
		//属性值=满属性值×品质/1000×等级/6
		String key=alchemy.getAlchemykey();
		//小数点的个数
		int size=1;
		if (key.equals("力量")||key.equals("根骨")||key.equals("灵性")||key.equals("敏捷")||
				key.equals("加攻击")||key.equals("加速度")||key.equals("加气血")||key.equals("加魔法")||
				key.equals("反击次数")||key.equals("连击次数")||key.equals("加强三尸虫")) {
			size=0;
		}
		String newv=null;
		double min=Double.parseDouble(alchemy.getAlchemysv());
		double max=Double.parseDouble(alchemy.getAlchemymv());
		if (xs!=0) {
			max=max*xs;
			max=getDouble(max, max, size);
		}else {

			//修改炼化炼器不再卡上限，改为卡下限
			min = min + (max - min) / 6 * (lvl - 1) / 2;
			//max=(max-min)/6*lvl+min;
			max=getDouble(min, max, size);
		}
		if (size==0) {
			newv=key+"="+(max+"").split("\\.")[0];
		}else {
			newv=key+"="+max;
		}
		return newv;
	}
	/**随机生成指定精确度的小数*/
	public static double getDouble(double min,double max,int type){
		StringBuffer a=new StringBuffer();for (int i = 0; i < type; i++) {a.append("0");}
		max-=min;max=max/4*random.nextInt(5);
		DecimalFormat df=new DecimalFormat("#."+a);
		double b=Double.valueOf(df.format(random.nextDouble()*max+min));
		return b;
	}
	/**刷新额外属性存在覆盖 没有生成 */
	public static String newExtra(String[] v,int type,String newEx){
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < v.length; i++) {
			if (v[i].startsWith(Extras[type])) {
				if (newEx!=null&&!newEx.equals("")) {
					if (i!=0) {buffer.append("|");}
					buffer.append(newEx);
					newEx=null;
				}
			}else {
				if (i!=0) {buffer.append("|");}
				buffer.append(v[i]);
			}
		}
		if (newEx!=null) {
			buffer.append("|");
			buffer.append(newEx);
		}
		return buffer.toString();
	}
	/**获取指定额外属性的值*/
	public static String getExtra(String value,String extra){
		String[] v=value.split("\\|");
		return getExtra(v, extra);
	}
	public static String getExtra(String[] v,String extra){
		for (int i = 0; i < v.length; i++) {
			if (v[i].startsWith(extra)) {
				return v[i];
			}
		}
		return null;
	}
	/**转为套装*/
	public String suitGenerate(String value,String extra){
		String[] extras=extra.split("&");
		int suitid=Integer.parseInt(extras[1]);
		Suit suit=GameServer.getSuit(suitid);
		StringBuffer buffer=new StringBuffer();
		buffer.append("套装品质=");
		buffer.append(extras[3]);
		buffer.append("|装备部位=");
		buffer.append(extras[2]);
		//原始装备存在此属性则不继续添加
		if (value.indexOf("性别要求") == -1) {
			buffer.append("|性别要求=");
			buffer.append(suit.getSysex());
		}
		buffer.append("|");
		buffer.append(value);
		buffer.append("|");
		buffer.append(extra);
		return buffer.toString();
	}
	/**取消套装*/
	public String suitCancel(String value){
		String[] vs=value.split("\\|");
		StringBuffer buffer=new StringBuffer();
		String e = "0";  // 装备部位  2 9 10 11  无性别要求可以去掉性别
		for (int i = 0; i < vs.length; i++) {
			if (StringUtil.isNullOrEmpty(vs[i])) {
				continue;
			}
			if (vs[i].startsWith("套装品质")) {
				continue;
			}
			if (vs[i].startsWith("装备部位")) {
				e = vs[i].split("=")[1];
				continue;
			}
			if (i == 2 && vs[i].startsWith("性别要求")) {
				continue;
			}
			if (vs[i].startsWith(Extras[3])) {
				continue;
			}
			if (buffer.toString().length() > 0) {
				buffer.append("|");
			}
			buffer.append(vs[i]);
		}
		return buffer.toString();
	}
}
