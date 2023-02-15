package org.come.action.npc;

import come.tool.newTask.TaskRecord;
import come.tool.teamArena.TeamArenaMatch;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.come.action.IAction;
import org.come.action.buy.AddGoodUtil;
import org.come.action.suit.SuitComposeAction;
import org.come.bean.LoginResult;
import org.come.bean.NpcComposeBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Alchemy;
import org.come.model.Decorate;
import org.come.model.GodStone;
import org.come.model.Newequip;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Mixdeal.AnalysisString;

/**
 * 装备合成，客户端发来操作类型和需要操作的装备，根据类型对装备进行操作
 * @author 叶豪芳
 * @date 2017年12月30日 下午2:42:06
 *
 */
public class NpcComposeAction implements IAction {

	private static Random random= new Random();
	private static String[] TJ;
	static Map<String,String> map=new HashMap<>();
	static{
		TJ=new String[21];
		for (int i = 0; i < TJ.length; i++) {
			TJ[i]=(8001+i)+"";
		}
		map.put("忽视抗混","加强混乱");
		map.put("忽视抗封","加强封印");
		map.put("忽视抗睡","加强昏睡");
		map.put("忽视抗毒","加强中毒");
		map.put("忽视抗风","加强风");
		map.put("忽视抗水","加强水");
		map.put("忽视抗雷","加强雷");
		map.put("忽视抗火","加强火");
		map.put("忽视鬼火","加强鬼火");
		map.put("忽视遗忘","加强遗忘");

		map.put("加强混乱","忽视抗混");
		map.put("加强封印","忽视抗封");
		map.put("加强昏睡","忽视抗睡");
		map.put("加强中毒","忽视抗毒");
		map.put("加强风","忽视抗风");
		map.put("加强水","忽视抗水");
		map.put("加强雷","忽视抗雷");
		map.put("加强火","忽视抗火");
		map.put("加强鬼火","忽视鬼火");
		map.put("加强遗忘","忽视遗忘");
	}
	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		// 接收客户端发来的数据
		NpcComposeBean npcComposeBean = GsonUtil.getGsonUtil().getgson().fromJson(message, NpcComposeBean.class);
		// 需要操作的装备集合（第一个为主装备）
		List<BigDecimal> goodlist = npcComposeBean.getGoodstables();
		List<BigDecimal> goodstables = new ArrayList<>();
		for (int i = 0; i < goodlist.size(); i++) {
			if (!goodstables.contains(goodlist.get(i))) {
				goodstables.add(goodlist.get(i));
			}
		}
		Goodstable[] goods=new Goodstable[goodstables.size()];
		if (goods.length<2) {return;}
		for (int i = 0; i < goodstables.size(); i++) {
			Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(goodstables.get(i));
			if (good==null||good.getUsetime()<=0||good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
			goods[i]=good;
			if (i!=0) {good.goodxh(1);}
			else {good.setUsetime(1);}
		}
		String type=npcComposeBean.getComposetype();
		if (type.equals("我要合成仙器")) {
			Compose_1(goods,ctx,loginResult);
		}else if (type.equals("我要升级仙器")) {
			Compose_2(goods, ctx, loginResult);
		}else if (type.equals("我要洗炼仙器")) {
			Compose_33(goods, ctx, loginResult);//TODO 我要改变仙器的属性
		} else if (type.equals("我要重铸仙器")) {
			Compose_3(goods, ctx, loginResult);//TODO 我要改变仙器的模型
		}else if (type.equals("我要打造普通装备")||type.equals("打造11-16级装备")) {
			Compose_4(goods, ctx, loginResult);
		}else if (type.equals("我要合成炼妖石")) {
			Compose_5(goods, ctx, loginResult);
		}else if (type.equals("我要培养饰品")) {
			Compose_6(goods, ctx, loginResult);
		}else if (type.equals("我要重铸饰品")) {
			Compose_7(goods, ctx, loginResult);
		}else if (type.equals("我要合成符石")||type.equals("我要洗练符石")) {
			Compose_8(goods, ctx, loginResult);
		}else if (type.equals("我要上神兵石")) {
			Compose_9(goods, ctx, loginResult);
		}else if (type.equals("我要培养护身符")) {
			Compose_10(goods, ctx, loginResult);
		}else if (type.equals("我要重铸护身符")) {
			Compose_11(goods, ctx, loginResult);
		}else if (type.equals("培养彩晶石")) {
			Compose_12(goods, ctx, loginResult);
		}else if (type.equals("我要解封神饰")) {
			Compose_35(goods, ctx, loginResult);
		}
	}
	/**我要合成仙器*/
	public void Compose_1(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (goods[0].getType()!=7099) {return;}
		if (!Goodtype.GodEquipment_xian(goods[1].getType())) {return;}
		String[] gongneng = null;
		if (goods[0].getValue()!=null&&!goods[0].getValue().equals("")) {
			gongneng=goods[0].getValue().split("\\|");
		}
		String[] str = goods[1].getValue().split("\\|");
		String god="";
		for (String s : str) {
			if (s.startsWith("阶数")) {
				god = s;
				break;
			}
		}
		if (gongneng==null) {
			if (god.equals("阶数=8")) {return;}
			goods[0].setValue(god + "|"+"灵气=1点");
			goods[0].setQuality(goods[1].getQuality());
		}else {
			if (!gongneng[0].equals(god)) {return;}
			else if (ReikiFull(gongneng)) {return;}
			else if (AnalysisString.jiaoyi(goods[0].getQuality())!=AnalysisString.jiaoyi(goods[1].getQuality())) {return;}
			goods[0].setValue(god+"|灵气="+(Integer.parseInt(gongneng[1].split("=")[1].split("点")[0])+1)+"点");
		}
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);
	}
	/**判断瓶子灵气是否满了 */
	public boolean ReikiFull(String[] vlaue){
		if (vlaue[0].equals("阶数=1")||vlaue[0].equals("阶数=2")) {
			if (Reikisum(vlaue[1])>=8) {
				return true;
			}
		}
		else if (vlaue[0].equals("阶数=3")) {
			if (Reikisum(vlaue[1])>=6) {
				return true;}
		}
		else if (vlaue[0].equals("阶数=4")) {
			if (Reikisum(vlaue[1])>=5) {
				return true;
			}
		} else {
			if (Reikisum(vlaue[1])>=3) {
				return true;
			}
		}
		return false;
	}
	/**判断灵气点数*/
	public int Reikisum(String vlaue){
		Pattern pattern = Pattern.compile("=(.*?)点");// 匹配的模式
		Matcher m = pattern.matcher(vlaue);
		while (m.find()) {
			int i = 1;
			return Integer.parseInt(m.group(i));
		}
		return 0;
	}
	/**返回等号后面的值*/
	public static int Numerical(String vlaue){
		if (vlaue.split("\\=").length==1) {return 0;}
		return Integer.parseInt(vlaue.split("\\=")[1]);
	}
	/**我要升级仙器*/
	public void Compose_2(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (goods[0].getType()!=7099) {return;}
		if (!Goodtype.Ore(goods[1].getType())) {return;}
		String[] gongneng = null;
		if (goods[0].getValue()!=null&&!goods[0].getValue().equals("")) {
			gongneng=goods[0].getValue().split("\\|");
		}
		if (gongneng==null) {return;}
		if (!ReikiFull(gongneng)) {return;}
		int kslvl = Integer.parseInt(goods[1].getValue().split("=")[1]);
		int lvl=Integer.parseInt(gongneng[0].split("=")[1]);
		if (lvl+5!=kslvl) {return;}
		if (lvl>=8) {return;}
		lvl++;
		BigDecimal id=new BigDecimal(GameServer.random.nextInt(23)+7000);
//		// 从物品表获取该仙器物品
		Goodstable good = GameServer.getGood(id);
		BigDecimal nsid=good.getGoodsid();
		List<String> xianqis = GameServer.getXianqiTypeValue().get(id+"|"+lvl);
		int[] a = randomArray(0, xianqis.size()-1, 1);
		good.setValue(xianqis.get(a[0]));
		good.setRgid(goods[0].getRgid());
		good.setRole_id(login.getRole_id());
		good.setQuality(goods[0].getQuality());
		good.setGoodsid(goods[0].getGoodsid());
		good.setStatus(goods[0].getStatus());
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
		goods[0]=null;
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}
	/**我要重铸仙器*/
	public void Compose_3(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.GodEquipment_xian(goods[0].getType())) {return;}
		if (goods[1].getType()!=212) {return;}//毁梦石
		BigDecimal id=new BigDecimal(GameServer.random.nextInt(24)+7000);
		String js=goods[0].getValue().split("\\|")[0].split("=")[1];
		Goodstable good = GameServer.getGood(id);
		BigDecimal nsid=good.getGoodsid();
		List<String> xianqis = GameServer.getXianqiTypeValue().get(id+"|"+js);
		int[] a = randomArray(0, xianqis.size()-1, 1);
		good.setValue(xianqis.get(a[0]));
		good.setRgid(goods[0].getRgid());
		good.setRole_id(login.getRole_id());
		good.setQuality(goods[0].getQuality());
		good.setGoodsid(goods[0].getGoodsid());
		good.setStatus(goods[0].getStatus());
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
		goods[0]=null;
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}
	/**我要打造普通装备 打造11-16级装备*/
	public void Compose_4(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.OrdinaryEquipment(goods[0].getType())) {return;}
		if (!Goodtype.Ore(goods[1].getType())) {return;}
		int goodid=goods[0].getGoodsid().intValue();//装备物品id
		int zblvl=Integer.parseInt(goods[0].getValue().split("\\|")[0].split("=")[1]);//装备等级
		int kslvl = Integer.parseInt(goods[1].getValue().split("=")[1]);
		Goodstable good=null;
		if (zblvl<10) {
			BigDecimal id=new BigDecimal(goodid-zblvl+kslvl+1);
			good = GameServer.getGood(id);// 从物品表获取该仙器物品
			BigDecimal nsid=good.getGoodsid();
			good.setRgid(goods[0].getRgid());
			good.setRole_id(login.getRole_id());
			good.setQuality(goods[0].getQuality());
			good.setGoodsid(goods[0].getGoodsid());
			good.setStatus(goods[0].getStatus());
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
			AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
			goods[0]=null;
		}else {
			boolean up=false;
			if (zblvl>=10&&zblvl<=13) {
				if (kslvl!=8&&kslvl!=9) {return;}
				else if (kslvl==9) {up=true;}
			}else if (zblvl==14) {
				if (kslvl!=9&&kslvl!=10) {return;}
				else if (kslvl==10) {up=true;}
			}else if (zblvl==15) {
				if (kslvl!=10&&kslvl!=11) {return;}
				else if (kslvl==11) {up=true;}

			}else if (zblvl==16) {
				if (kslvl!=11) {return;}
			}else {
				return;
			}
			if (up) {goodid++;}
			BigDecimal id=new BigDecimal(goodid);
			good = GameServer.getGood(id);// 从物品表获取该仙器物品
			BigDecimal nsid=good.getGoodsid();
			good.setRgid(goods[0].getRgid());
			good.setRole_id(login.getRole_id());
			good.setValue(good.getValue()+getNewequip(good));
			good.setQuality(goods[0].getQuality());
			good.setGoodsid(goods[0].getGoodsid());
			good.setStatus(goods[0].getStatus());
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
			AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
			goods[0]=null;
		}
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}
	/**我要合成炼妖石*/
	public void Compose_5(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.ExerciseMonsterOre(goods[0].getType())) {return;}
		if (!Goodtype.ExerciseMonsterOre(goods[1].getType())) {return;}
		if (goods[0].getType()!=goods[1].getType()) {return;}
		String getType = SynthesisOre(goods[0],goods[1]);
		if(getType != null){// 成功
			goods[0].setValue(getType);
			updata(goods);
			AddGoodUtil.addGood(ctx,goods[0]);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成成功"));
		}else{
			goods[0].setUsetime(0);
			updata(goods);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成失败"));
		}
	}
	/**我要培养饰品*/
	public void Compose_6(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.Accessories(goods[0].getType())) {return;}
		if (goods[1].getType()!=1008&&!Goodtype.Accessories(goods[1].getType())) {return;}
		//饰品描述信息
		String[] gongneng =goods[0].getValue().split("\\|");
		int max=0;
		for (int i = 0; i < gongneng.length; i++) {
			if(gongneng[i].length()>=2&&gongneng[i].substring(0, 2).equals("培养")){
				String[] num = gongneng[i].split("=")[1].split("/");
				max  =Integer.parseInt(num[1]);
			}
		}
		if (max==0) {return;}
		if (Numerical(gongneng[0])>6){return;}
		BigDecimal ysid=goods[0].getGoodsid();
		SuitComposeAction.AccC(goods[0], 1);
		BigDecimal nsid=goods[0].getGoodsid();
		goods[0].setGoodsid(ysid);
		Goodstable good=goods[0];
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
		goods[0]=null;
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}
	/**我要重铸饰品*/
	public void Compose_7(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (goods[0].getType() == 927||goods[0].getType() == 928 ||goods[0].getType()==929||goods[0].getType()==930||goods[0].getType()==931){
			updata(goods);
			AddGoodUtil.addGood(ctx,goods[0]);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("神饰无法使用普通矿石重铸"));
			return;
		}
		if (!Goodtype.Accessories(goods[0].getType())) {return;}//配饰
		if (!Goodtype.Ore(goods[1].getType())) {return;}//矿石
		String[] gongneng =goods[0].getValue().split("\\|");//饰品描述信息
		//检测矿石等级
		int kslvl = Integer.parseInt(goods[1].getValue().split("=")[1]);
		if (Numerical(gongneng[0])!=(kslvl-3)) {return;}
		goods[0].setValue(getGoodsAttribute(goods[0]));
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);


	}




	/**我要合成符石  我要洗练符石*/
	public void Compose_8(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		for (int i = 0; i < goods.length; i++) {
			if (goods[i].getType()!=188) {
				return;
			}
		}
		BigDecimal id=null;
		if (goods.length==2) {//洗练
			//第一个符石的等级
			int ore_1=Numerical(Goodtype.StringParsing(goods[0].getValue())[0]);
			int ore_2=Numerical(Goodtype.StringParsing(goods[1].getValue())[0]);
			if (ore_1==1||ore_1==2||ore_1==3) {return;}
			if (ore_1-3!=ore_2) {return;}
			id=new BigDecimal(GameServer.random.nextInt(5)*5+ore_1);
		}else if (goods.length==5) {//合成
			int lvl=Numerical(Goodtype.StringParsing(goods[0].getValue())[0]);
			if (lvl>=5) {return;}
			for (int i = 1; i < goods.length; i++) {
				if (lvl!=Numerical(Goodtype.StringParsing(goods[i].getValue())[0])) {return;}
			}
			lvl++;
			id=new BigDecimal(GameServer.random.nextInt(5)*5+lvl);
		}
		if (id==null) {return;}
		Goodstable good = GameServer.getGood(id);// 从物品表获取该仙器物品
		BigDecimal nsid=good.getGoodsid();
		good.setRgid(goods[0].getRgid());
		good.setRole_id(login.getRole_id());
		good.setQuality(goods[0].getQuality());
		good.setGoodsid(goods[0].getGoodsid());
		good.setStatus(goods[0].getStatus());
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
		goods[0]=null;
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}
	/**我要上神兵石*/
	public void Compose_9(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.GodEquipment_God(goods[0].getType())) {return;}
		if (goods[1].getType()!=191) {return;}
		String[] v=goods[0].getValue().split("\\|");
		GodStone godStone=GameServer.getGodStone(goods[0].getGoodsname(),v);
		if (godStone!=null) {
			String value=SuitComposeAction.newExtra(v,2,"神兵属性&"+godStone.getValue());
			goods[0].setValue(value);
		}
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);
	}
	/**我要培养护身符*/
	public void Compose_10(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.Amulet2(goods[0].getType())) {return;}
		if (!Goodtype.Amulet2(goods[1].getType())) {return;}
		goods[0].setValue(resetHSF(goods[0], random.nextInt(170)+31,0, 0));
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);
	}
	/**我要重铸护身符 升级护身符*/
	public void Compose_11(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.Amulet2(goods[0].getType())) {return;}
		if (!Goodtype.Ore(goods[1].getType())) {return;}
		String[] vs=goods[0].getValue().split("\\|");
		int pz=0;
		for (int i = 0; i < vs.length; i++) {
			String[] vsz=vs[i].split("=");
			if (vsz[0].equals("品质")) {
				pz =Integer.parseInt(vsz[1].split("/")[0]);
				break;
			}
		}
		if (pz<300) {return;}//TODO 品质等于300
		int kslvl = Integer.parseInt(goods[1].getValue().split("=")[1]);
		if (kslvl!=9&&kslvl!=10) {return;}
		int lvl=Integer.parseInt(vs[0].split("=")[1]);
		BigDecimal id=null;
		Goodstable good=null;
		if (kslvl==10) {
			lvl++;
			if (lvl>7) {return;}
			id=goods[0].getGoodsid().add(new BigDecimal(1));
			good = GameServer.getGood(id);// 从物品表获取该仙器物品
			BigDecimal nsid=good.getGoodsid();
			good.setRgid(goods[0].getRgid());
			good.setRole_id(login.getRole_id());
			good.setQuality(goods[0].getQuality());
			String[] hsvs=vs;
			String[] hsvss=good.getValue().split("\\|");
			hsvs[0]=hsvss[0];hsvs[1]=hsvss[1];hsvs[2]=hsvss[2];
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < hsvs.length; i++) {
				if (i!=0) {buffer.append("|");}
				buffer.append(hsvs[i]);
			}
			good.setValue(buffer.toString());
			good.setValue(resetHSF(good,0,0, 0));
			good.setGoodsid(goods[0].getGoodsid());
			good.setStatus(goods[0].getStatus());
			AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
			AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
			goods[0]=null;
		}else {
			int sj=random.nextInt(900);
			if (sj>800) {
				sj=random.nextInt(850)+100;
				if (sj>850) {sj=random.nextInt(800)+200;}
			}
			goods[0].setValue(resetHSF(goods[0],0,sj, 0));
		}
		updata(goods);
		AddGoodUtil.addGood(ctx,good!=null?good:goods[0]);
	}
	/**培养彩晶石*/
	public void Compose_12(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (goods[0].getType()!=729) {return;}
		if (goods[1].getType()!=729) {return;}
		resetCJS(goods[0], 2+random.nextInt(5));
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);
	}
	/**我要洗练仙器*/
	public void Compose_33(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (!Goodtype.GodEquipment_xian(goods[0].getType())) {return;}
		if (goods[1].getType()!=915) {return;}
		int id = goods[0].getGoodsid().intValue();//仙器模型
		String js =goods[0].getValue().split("\\|")[0].split("=")[1];//仙器属性
		Goodstable good = GameServer.getGood(BigDecimal.valueOf(id));//仙器模型
		BigDecimal nsid=good.getGoodsid();
		List<String> xianqis = GameServer.getXianqiTypeValue().get(id+"|"+js);
		int[] a = randomArray(0, xianqis.size()-1, 1);
		good.setValue(xianqis.get(a[0]));
		good.setRgid(goods[0].getRgid());
		good.setRole_id(login.getRole_id());
		good.setQuality(goods[0].getQuality());
		good.setGoodsid(goods[0].getGoodsid());
		good.setStatus(goods[0].getStatus());
		AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, null, nsid, null);
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 8);
		goods[0]=null;
		updata(goods);
		AddGoodUtil.addGood(ctx,good);
	}

	/**解封神饰*/
	public void Compose_35(Goodstable[] goods,ChannelHandlerContext ctx,LoginResult login){
		if (goods[0].getType()!=927&&goods[0].getType()!=928&&goods[0].getType()!=929&&goods[0].getType()!=930&&goods[0].getType()!=931){
			updata(goods);
			AddGoodUtil.addGood(ctx,goods[0]);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("请仔细检查你的饰品，貌似不是神饰品"));
			return;
		}
		if (!Goodtype.Accessories(goods[0].getType())) {return;}//配饰
		if (goods[1].getType()!=932) {return;}
		String[] gongneng =goods[0].getValue().split("\\|");//饰品描述信息
		goods[0].setValue(getGoodsAttributex7(goods[0]));
		updata(goods);
		AddGoodUtil.addGood(ctx,goods[0]);
		}


//	if(getType != null){// 成功
//		goods[0].setValue(getType);
//		updata(goods);
//		AddGoodUtil.addGood(ctx,goods[0]);
//		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成成功"));
//	}else{
//		goods[0].setUsetime(0);
//		updata(goods);
//		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("合成失败"));
//	}

	public static String getGoodsAttributex7( Goodstable goods ){
		// 根据物品的ID查找对应的重铸信息集合
		List<Decorate> decorates = GameServer.getAllDecorate().get(goods.getGoodsid().toString());
		if (!decorates.get(0).getDecotatebvalue().startsWith(goods.getValue().substring(0, 4))) {
			decorates = GameServer.getAllDecorate().get(goods.getGoodsid().add(new BigDecimal(1)).toString());
		}
		// 随机获取一条重铸信息
		int[] a = randomArray(0, decorates.size()-1, 1);
		Decorate decorate = decorates.get(a[0]);
		// 基本属性
		String bvalue = decorate.getDecotatebvalue();
		// 类型
		String type = "";
		if(decorate.getDecotatetype().indexOf("|") != -1){
			type = getAttribute(decorate.getDecotatetype(),2);
		}else{
			type = getAttribute(decorate.getDecotatetype(),1);
		}
		// 属性点
		String value = getAttribute(decorate.getDecotatepvalue(),1);
		// 附加属性
		String fvalue = getAttribute(decorate.getDecotatefvalue(),1);
		// 附加抗性
		String kvalue = getAttribute(decorate.getDecotatekvalue(),1);
		// 培养属性1
		String pv1 = getAttribute(decorate.getDecotatepv1(),1);
		// 培养属性2
		String pv2 = getAttribute(decorate.getDecotatepv2(),1);
		// 升级值
		String mv = decorate.getDecotatemv();
		// 培养值
		String v = decorate.getDecotatev();
		//神属性
		String ssx=getAttribute(decorate.getSlsx(),1);//神属性
		String ssx1=getAttribute(decorate.getSlsx1(),1);//神属性
		String ssx2=getAttribute(decorate.getSlsx2(),1);//神属性
		String ssx3=getAttribute(decorate.getSlsx3(),1);//神属性
		String ssx4=getAttribute(decorate.getSlsx4(),1);//神属性
		String ssx5=getAttribute(decorate.getSlsx5(),1);//神属性
		String ssx6=getAttribute(decorate.getSlsx6(),1);//神属性
		String ssx7=getAttribute(decorate.getSlsx7(),1);//神属性
		String[] vvs=goods.getValue().split("\\|");
		for (int i = 0; i < vvs.length; i++) {
			String[] vvv=vvs[i].split("=");
			if (vvv[0].equals("培养")) {
				v=vvv[1].split("/")[0];
				break;
			}
		}
		// 炼化属性
		String lianhua = "炼化属性&" + pv1.replace("|", "") + "&" + pv2.replace("|", "");
		String attribute =  bvalue + type+ value + fvalue +kvalue;
		if (ssx.equals("0")||ssx.equals("神类属性")) {
			attribute = newlianhua(attribute.split("\\|"), lianhua, null,null);

		}
		if (mv.equals("0")||mv.equals("")) {
			attribute = newlianhua(attribute.split("\\|"), lianhua, null,null)+ssx+ssx1+ssx2+ssx3+ssx4+ssx5+ssx6+ssx7;

		}else {
			attribute = newlianhua(attribute.split("\\|"), lianhua, null,null) + "|培养=" +v+"/"+mv+"#G"+ssx+ssx1+ssx2+ssx3+ssx4+ssx5+ssx6+ssx7;
		}
		return attribute;
	}
	/**
	 * 获得饰品重铸附加属性
 	 */

	public static String getGoodsAttribute( Goodstable goods ){
		// 根据物品的ID查找对应的重铸信息集合
		List<Decorate> decorates = GameServer.getAllDecorate().get(goods.getGoodsid().toString());
		if (!decorates.get(0).getDecotatebvalue().startsWith(goods.getValue().substring(0, 4))) {
			decorates = GameServer.getAllDecorate().get(goods.getGoodsid().add(new BigDecimal(1)).toString());
		}
		// 随机获取一条重铸信息
		int[] a = randomArray(0, decorates.size()-1, 1);
		Decorate decorate = decorates.get(a[0]);
		// 基本属性
		String bvalue = decorate.getDecotatebvalue();
		// 类型
		String type = "";
		if(decorate.getDecotatetype().indexOf("|") != -1){
			type = getAttribute(decorate.getDecotatetype(),2);
		}else{
			type = getAttribute(decorate.getDecotatetype(),1);
		}
		// 属性点
		String value = getAttribute(decorate.getDecotatepvalue(),1);
		// 附加属性
		String fvalue = getAttribute(decorate.getDecotatefvalue(),1);
		// 附加抗性
		String kvalue = getAttribute(decorate.getDecotatekvalue(),1);
		// 培养属性1
		String pv1 = getAttribute(decorate.getDecotatepv1(),1);
		// 培养属性2
		String pv2 = getAttribute(decorate.getDecotatepv2(),1);
		// 升级值
		String mv = decorate.getDecotatemv();
		// 培养值
		String v = decorate.getDecotatev();
		String[] vvs=goods.getValue().split("\\|");
		for (int i = 0; i < vvs.length; i++) {
			String[] vvv=vvs[i].split("=");
			if (vvv[0].equals("培养")) {
				v=vvv[1].split("/")[0];
				break;
			}
		}
		// 炼化属性
		String lianhua = "炼化属性&" + pv1.replace("|", "") + "&" + pv2.replace("|", "");
		String attribute =  bvalue + type+ value + fvalue +kvalue;
		if (mv.equals("0")||mv.equals("")) {
			attribute = newlianhua(attribute.split("\\|"), lianhua, null,null);

		}else {
			attribute = newlianhua(attribute.split("\\|"), lianhua, null,null) + "|培养=" +v+"/"+mv;
		}
		return attribute;
	}
	/**将新的炼化属性添加到字符串中*/
	public static String newlianhua(String[] v,String newlh,String newlq,String newsb){
		for (int i = 0; i < v.length; i++) {
			if (v[i]!=null&&v[i].indexOf("炼化属性")!=-1) {
				//存在炼化属性
				if (newlh==null) {
					newlh=v[i];
				}
				v[i]=null;
			}
			if (v[i]!=null&&v[i].indexOf("炼器属性")!=-1) {
				//存在炼器属性
				if (newlq==null) {
					newlq=v[i];
				}
				v[i]=null;
			}
			if (v[i]!=null&&v[i].indexOf("神兵属性")!=-1) {
				//存在炼器属性
				if (newsb==null) {
					newsb=v[i];
				}
				v[i]=null;
			}
		}
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < v.length; i++) {
			if (i==0) {
				buffer.append(v[i]);
			}else {
				if (v[i]!=null) {
					buffer.append("|"+v[i]);
				}
			}
		}
		if (newlh!=null) {
			buffer.append("|"+newlh);
		}
		if (newlq!=null) {
			buffer.append("|"+newlq);
		}
		if (newsb!=null) {
			buffer.append("|"+newsb);
		}
		return buffer.toString();

	}
	// 获得单个属性
	public static String getAttribute( String decorateType , int i){
		// 获得的属性
		String typees = "";
		// 如果能拆分属性
		if( decorateType.indexOf("|") != -1 ){
			// 拆分属性
			String[] decorateTypes = decorateType.split("\\|");
			int[] a = randomArray(0, decorateTypes.length-1, i);
			for (int j = 0; j < a.length; j++) {
				// 获得的属性
				typees = typees + "|" + decorateTypes[a[j]];
			}
			return typees;
		}else {
			return "|" + decorateType;
		}
	}
	// 获得装备附加属性
	public String getNewequip( Goodstable goods ){
		// 获得所有的升级物品
		Map<String,List<Newequip>> sameNewequipMap = GameServer.getSameNewequipMap();
		// 获得装备的类型
		Long type = goods.getType();
		// 获取装备等级
		String value = goods.getValue();
		String[] goodsInfo = value.split("\\|");
		// 装备等级
		String grade = goodsInfo[0].split("=")[1];
		// 物品类型
		String goodsType = getEquipType(type);
		// 获取升级的信息
		List<Newequip> hatList  = sameNewequipMap.get(goodsType+grade);
		Newequip newequip=null;
		if (hatList.size()!=0) {
			// 随机调一条属性
			newequip = hatList.get(random.nextInt(hatList.size()));
		}else {
			System.err.println("打造装备报错"+goodsType+grade);
			return "";
		}
		// 获得装备要求
		String types = getEquipRequire(newequip.getEquiptype());
		String[] vs=new String[12];
		List<String> vss=new ArrayList<>();
		for (int i = 0; i < vs.length; i++) {
			String v=null;
			switch (i) {
				case 0:v=newequip.getEquipfq();break;
				case 1:v=newequip.getEquipfskb();break;
				case 2:v=newequip.getEquipkwl();break;
				case 3:v=newequip.getEquipwla();break;
				case 4:v=newequip.getEquipkrf();break;
				case 5:v=newequip.getEquipqsc();break;
				case 6:v=newequip.getEquipksc();break;
				case 7:v=newequip.getEquipkxf();break;
				case 8:v=newequip.getEquipfsh();break;
				case 9:v=newequip.getEquipwle();break;
				case 10:v=newequip.getEquipqhp();break;
				case 11:v=newequip.getEquipqmp();break;
			}
			if (v==null||v.equals("")) {
				continue;
			}
			// 拆分属性
			String[] attrStrings = v.split("\\|");
			if(attrStrings.length != 1&&random.nextInt(100) <= Integer.parseInt(attrStrings[0])){
				// 获得的属性
				String[] num = attrStrings[1].split("-");
				// 随机取一个数值
				int[] a = randomArray(Integer.parseInt(num[0]),Integer.parseInt(num[1]),1);
				// 可能获得的属性
				String[] attrbutes = attrStrings[2].split("&");
				String attrs = attrbutes[random.nextInt(attrbutes.length)];
				//判断重复
				int k=0;
				while (vss.contains(attrs)||(map.get(attrs)!=null&&vss.contains(map.get(attrs)))) {
					attrs = attrbutes[random.nextInt(attrbutes.length)];
					k++;
					if (k>10000) {break;}
				}
				vss.add(attrs);
				if (a==null) {
					System.out.println(GsonUtil.getGsonUtil().getgson().toJson(newequip));
				}
				vs[i] = attrs + "=" + a[0];
			}
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(types);
		for (int i = 0; i < vs.length; i++) {
			if (vs[i]==null||vs[i].equals("")) {
				continue;
			}
			buffer.append("|");
			buffer.append(vs[i]);
		}
		return buffer.toString();
	}
	// 区分装备类型
	public static String getEquipType( Long type ){
		// 物品类型
		String goodsType = null;
		if((type==601)||(type==600)||(type==6600)||(type==6601)||(type==600)||(type==7001)){
			goodsType = "帽子";// 帽子
		}else if((type==603)||(type==7002)){
			goodsType = "项链";// 项链类型
		}else if((type==605)||(type==604)||(type==6700)||(type==6701)||(type==7000)){
			goodsType = "衣服";// 衣服类型
		}else if((type==800)||(type==6500)||(type==7004)){
			goodsType = "武器";	// 武器类型
		}else if((type==602)||(type==6900)||(type==7003)){
			goodsType = "鞋子";// 鞋子类型
		}else if(type==612){// 护身符类型
			goodsType = "护身符";
		}
		return goodsType;
	}
	// 获取装备要求
	public String getEquipRequire( String equipTypes ){
		// 获得装备要求
		String[] types = equipTypes.split("\\|");
		String type = new String("|");
		if( types.length != 1 ){
			// 拆分装备要求
			String[] requString = types[1].split("&");
			int[] a = randomArray(0,requString.length-1,Integer.parseInt(types[0]));
			for (int j = 0; j < a.length; j++) {
				type = type + requString[a[j]];
			}
			return type;
		}
		return type;
	}
	/**
	 * 随机指定范围内N个不重复的数
	 * 在初始化的无重复待选数组中随机产生一个数放入结果中，
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
	 * 然后从len-2里随机产生下一个随机数，如此类推
	 * @param max  指定范围最大值
	 * @param min  指定范围最小值
	 * @param n  随机数个数
	 * @return int[] 随机数结果集
	 */
	public static int[] randomArray(int min,int max,int n){
		int len = max-min+1;
		if(max < min || n > len){
			return null;
		}
		//初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min+len; i++){
			source[i-min] = i;
		}
		int[] result = new int[n];
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(random.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}
	/**
	 *根据传入的炼妖石获得合成后的炼妖石
	 */
	public static String SynthesisOre(Goodstable ore,Goodstable ore1){
		//第一个炼妖石的等级
		String Bottletext=ore.getValue();
		String[] gongneng = Bottletext.split("\\|");
		int ore_1=Integer.parseInt(gongneng[0].split("\\=")[1]);
		//第二个炼妖石的等级
		String Bottletext1=ore1.getValue();
		String[] gongneng1 = Bottletext1.split("\\|");
		int ore_2=Integer.parseInt(gongneng1[0].split("\\=")[1]);
		//炼妖石的类型
		long type=ore.getType();
		int ore_max;
		int ore_min;
		if (ore_1>ore_2) {
			ore_max=ore_1;
			ore_min=ore_2;
		} else {
			ore_max=ore_2;
			ore_min=ore_1;
		}
		if (oreUp(ore_max, ore_min)) {
			if (type!=711) {
				int ore_new=ore_max+1;
				if (ore_new>11) {
					ore_new=11;
				}
				int s=ore_new+3;
				return "等级="+ore_new+"|"+gongneng[1].split("\\=")[0]+"="+s;
			}else {
				int ore_new=ore_max+1;
				if (ore_new>11) {
					ore_new=11;
				}
				double s=0.5+ore_new*0.5;
				return "等级="+ore_new+"|抗风="+s+"|抗雷="+s+"|抗水="+s+"|抗火="+s;
			}
		}
		return null;
	}
	/**判断升级是否成功*/
	public static boolean oreUp(int ore_max,int ore_min){
		int ore_cha=ore_max-ore_min;
		int Probability=100-5*ore_cha;
		if (Probability<=1) {
			Probability=1;
		}
		if (random.nextInt(ore_max*(ore_max-ore_min+1)*(100+ore_max*5))<=Probability+420-ore_max*10) {
			return true;
		}
		return false;
	}
	//属性值=满属性值×品质/1000×等级/6
	//刷新护身符属性 品质修改值
	public static String resetHSF(Goodstable goodstable,int add,int sj,int lvl){
		String[] vs=goodstable.getValue().split("\\|");
		int pz=0;
		for (int i = 0; i < vs.length; i++) {
			String[] vsz=vs[i].split("=");
			if (lvl==0&&vsz[0].equals("等级")) {
				lvl=Integer.parseInt(vsz[1]);
			}else if (vsz[0].equals("品质")) {
				pz =Integer.parseInt(vsz[1].split("/")[0]);
				break;
			}
		}
		if (sj!=0) {
			pz=sj;
		}else if (add>0&&pz<=900) {
			if (pz>800) {add*=0.9;}
			if (pz>850) {
				add*=0.9;
				if (add<50) {
					add=51;
				}
			}
			if (pz>880) {
				add*=0.8;
				if (add<20) {
					add=20;
				}
			}
			pz+=add;
			if (pz<0) {
				pz=0;
			}
		}
		if (add==-50) {
			sj=add;
		}
		if (pz>1000) {
			pz=1000;
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append("等级=");
		buffer.append(lvl);
		buffer.append("|");
		buffer.append(vs[1]);
		buffer.append("|");
		buffer.append(vs[2]);
		//属性值=满属性值×品质/1000×等级/6
		for (int i = 3; i < vs.length; i++) {
			if (vs[i].indexOf("炼化属性")!=-1) {
				buffer.append("|");
				buffer.append("炼化属性");
				String[] vss=vs[i].split("\\&");
				for (int j = 1; j < vss.length; j++) {
					buffer.append("&");
					String[] vsz=vss[j].split("=");
					if (vsz[0].equals("特技")) {
						buffer.append(vss[j]);
						continue;
					}
					buffer.append(vsz[0]);
					buffer.append("=");
					Alchemy alchemy=getAlchemy(hsf, vsz[0]);
					if (alchemy!=null) {
						//小数点的个数
						int size=1;
						if (vsz[0].equals("力量")||vsz[0].equals("根骨")||vsz[0].equals("灵性")||vsz[0].equals("敏捷")||
								vsz[0].equals("加攻击")||vsz[0].equals("加速度")||vsz[0].equals("加气血")||vsz[0].equals("加魔法")||
								vsz[0].equals("反击次数")||vsz[0].equals("连击次数")) {
							size=0;
						}
						double max=Double.parseDouble(alchemy.getAlchemymv());
						max=max*pz/1000*lvl/6;
						BigDecimal big=new BigDecimal(max);
						buffer.append(big.setScale(size,BigDecimal.ROUND_HALF_UP));
					}else {
						buffer.append(vsz[1]);
					}
				}
			}else {
				String[] vsz=vs[i].split("=");
				if (vsz[0].equals("品质")) {
					buffer.append("|");
					buffer.append(vsz[0]);
					buffer.append("=");
					buffer.append(pz);
					buffer.append("/");
					buffer.append(1000);
				}else {
					Alchemy alchemy=NpcComposeAction.getAlchemy(Hhsf, vsz[0]);
					if (alchemy!=null) {
						if (sj==0) {
							buffer.append("|");
							String type=alchemy.getAlchemykey();
							buffer.append(type);
							buffer.append("=");
							int size=1;
							if (type.equals("力量")||type.equals("根骨")||type.equals("灵性")||type.equals("敏捷")||
									type.equals("加攻击")||type.equals("加速度")||type.equals("加气血")||type.equals("加魔法")||
									type.equals("反击次数")||type.equals("连击次数")) {
								size=0;
							}
							double max=Double.parseDouble(alchemy.getAlchemymv());
							max=max*pz/1000*lvl/6;
							BigDecimal big=new BigDecimal(max);
							buffer.append(big.setScale(size,BigDecimal.ROUND_HALF_UP));
							int p=type.indexOf("上限");
							if (p!=-1) {
								buffer.append("|");
								type=type.substring(0, p);
								buffer.append(type);
								buffer.append("=-");
								buffer.append(big.setScale(size,BigDecimal.ROUND_HALF_UP));
							}
						}else if (sj!=-1) {
							sj=-1;
							List<Alchemy> als=getAlchemys();
							for (int j = 0; j < als.size(); j++) {
								buffer.append("|");
								alchemy=als.get(j);
								String type=alchemy.getAlchemykey();
								buffer.append(type);
								buffer.append("=");
								int size=1;
								if (type.equals("力量")||type.equals("根骨")||type.equals("灵性")||type.equals("敏捷")||
										type.equals("加攻击")||type.equals("加速度")||type.equals("加气血")||type.equals("加魔法")||
										type.equals("反击次数")||type.equals("连击次数")) {
									size=0;
								}
								double max=Double.parseDouble(alchemy.getAlchemymv());
								max=max*pz/1000*lvl/6;
								BigDecimal big=new BigDecimal(max);
								buffer.append(big.setScale(size,BigDecimal.ROUND_HALF_UP));
							}
						}
					}
				}
			}
		}
		return buffer.toString();
	}
	public static Alchemy getAlchemy(String type,String lei){
		List<Alchemy> alchemies=GameServer.getAllAlchemy().get(type);
		if (alchemies!=null) {
			for (int i = alchemies.size()-1; i >=0; i--) {
				if (alchemies.get(i).getAlchemykey().equals(lei)) {
					return alchemies.get(i);
				}
			}
		}
		return null;
	}
	//随机出相关属性
	public static List<Alchemy> getAlchemys(){
		List<Alchemy> alchemies=new ArrayList<>();
		List<Alchemy> als=GameServer.getAllAlchemy().get(Hhsf);
		int a=0,b=0;
		int size=als.size();
		a=random.nextInt(size);
		b=random.nextInt(size);
		while (a==b&&size>1) {
			b=random.nextInt(size);
		}

		Alchemy alchemy=als.get(a);
		alchemies.add(alchemy);
		int p=alchemy.getAlchemykey().indexOf("上限");
		if (p!=-1) {
			Alchemy alc2=new Alchemy();
			alc2.setAlchemyid(alchemy.getAlchemyid());
			alc2.setAlchemykey(alchemy.getAlchemykey().substring(0, p));
			alc2.setAlchemymv(-Double.parseDouble(alchemy.getAlchemymv())+"");
			alc2.setAlchemysv(alchemy.getAlchemysv());
			alc2.setAlchemytype(alchemy.getAlchemytype());
			alchemies.add(alc2);
		}
		alchemy=als.get(b);
		alchemies.add(alchemy);
		p=alchemy.getAlchemykey().indexOf("上限");
		if (p!=-1) {
			Alchemy alc2=new Alchemy();
			alc2.setAlchemyid(alchemy.getAlchemyid());
			alc2.setAlchemykey(alchemy.getAlchemykey().substring(0, p));
			alc2.setAlchemymv(-Double.parseDouble(alchemy.getAlchemymv())+"");
			alc2.setAlchemysv(alchemy.getAlchemysv());
			alc2.setAlchemytype(alchemy.getAlchemytype());
			alchemies.add(alc2);
		}
		return alchemies;
	}
	static String  hsf="护身符";
	static String Hhsf="黄护身符";
	//    1项属性:117　2项属性:383　3项属性:952　4项属性:1962
//    每项最高增加50点
	//培养彩晶石 根骨 灵性 力量 敏捷
	public void resetCJS(Goodstable goodstable,int add){
		String[] vs=goodstable.getValue().split("\\|");
		int pz=0;
		int size=0;//总点数
		int tf=0;//天生点数
		int sx=0;//品质上限
		int w=-1;//位置
		List<Integer> a=new ArrayList<>();
		for (int i = 0; i < vs.length; i++) {
			String[] vss=vs[i].split("=");
			if (vss[0].equals("品质")) {
				pz=Integer.parseInt(vss[1]);
			}else if (vss[0].equals("根骨")||vss[0].equals("灵性")||vss[0].equals("力量")||vss[0].equals("敏捷")) {
				int p=Integer.parseInt(vss[1]);
				size+=p;
				if (p<50) {a.add(i);}
				tf++;
			}
		}
		size-=tf;
		if (tf==4) {sx=1962;}
		else if (tf==3) {sx=952;}
		else if (tf==2) {sx=383;}
		else {sx=117;}
		pz+=add;
		if (pz>sx) {pz=sx;}
		for (int i =getdian(pz)-size;i>0;i--) {
			if (a.size()!=0) {
				int p=random.nextInt(a.size());
				w=a.get(p);
				String[] vss=vs[w].split("=");
				int vv=Integer.parseInt(vss[1])+1;
				vs[w]=vss[0]+"="+vv;
				if (vv>=50) {a.remove(p);}
			}
		}
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < vs.length; i++) {
			if (i!=0) {buffer.append("|");}
			if (vs[i].startsWith("品质")) {
				buffer.append("品质=");
				buffer.append(pz);
			}else {
				buffer.append(vs[i]);
			}
		}
		goodstable.setValue(buffer.toString());
	}
	//获取当前品质额外点数
	public static int getdian(int pz){
		double s=0;double dz=1;
		for (int i = 0; i < 196; i++) {
			if (i>=160) {
				dz=22;
			}else if (i>=122) {
				dz=15;
			}else if (i>=81) {
				dz=8;
			}else if (i>=40) {
				dz=4;
			}else {
				dz=2;
			}
			s+=dz;
			if (pz<s) {
				return i;
			}
		}
		return 196;
	}
	/**保存属性*/
	public void updata(Goodstable[] goods){
		for (int i = 0; i < goods.length; i++) {
			if (goods[i]!=null) {
				AllServiceUtil.getGoodsTableService().updateGoodRedis(goods[i]);
				if (i==0) {
					AllServiceUtil.getGoodsrecordService().insert(goods[i], null, 1, 8);
				}else {
					long gType=goods[i].getType();
					if (gType!=212&&!(gType>=497&&gType<=500)&&gType!=505&&gType!=915&&gType!=923) {// 添加记录
						AllServiceUtil.getGoodsrecordService().insert(goods[i], null, 1, 6);
					}
				}
			}
		}
	}
}
