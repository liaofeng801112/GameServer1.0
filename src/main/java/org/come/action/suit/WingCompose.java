package org.come.action.suit;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.SuitOperBean;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.WingTraining;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.AddGoodAction;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class WingCompose {

	/**31:培养*/
	public static void type31(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		PartJade jade=suitOperBean.getJade();
		if (jade==null) {return;}
		BigDecimal money=new BigDecimal(1000000L*jade.getPartId());//翅膀培养价格
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足

		Goodstable pGood=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(jade.getSuitid()));
		if (pGood==null||pGood.getRole_id().compareTo(loginResult.getRole_id())!=0||pGood.getUsetime()<jade.getPartId()||jade.getPartId()<=0) {return;}
		pGood.goodxh(jade.getPartId());
		goods.add(pGood);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		assetUpdate.updata("D=-"+money);
		Goodstable good=goods.get(0);
		if (good.getType()!=8888) {return;}
		String[] vs=good.getValue().split("\\|");
		long exp=Long.parseLong(vs[2].split("=")[1]);
		int ylvl=getWingLevel(exp);
		int pexp=wingTraining(pGood,jade.getPartId());
		exp+=pexp;
		if (exp>=4000000) {exp=4000000;}
		if (pexp!=0) {
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < 4; i++) {
				if (buffer.length()!=0) {buffer.append("|");}
				if (i==2) {buffer.append("经验="+exp);}
				else {buffer.append(vs[i]);}
			}
			int nlvl=getWingLevel(exp);
			if (ylvl==nlvl) {
				for (int i = 4; i < vs.length; i++) {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(vs[i]);
				}
			}else {
				int size=nlvl-ylvl;
				int[] arr=new int[4];
				for (int i = 4; i < vs.length; i++) {
					if (vs[i].startsWith("根骨=")) {
						arr[0]=Integer.parseInt(vs[i].split("=")[1]);
					}else if (vs[i].startsWith("力量=")) {
						arr[1]=Integer.parseInt(vs[i].split("=")[1]);
					}else if (vs[i].startsWith("灵性=")) {
						arr[2]=Integer.parseInt(vs[i].split("=")[1]);
					}else if (vs[i].startsWith("敏捷=")) {
						arr[3]=Integer.parseInt(vs[i].split("=")[1]);
					}
				}
				addWingQuality(arr, size, SuitComposeAction.random.nextInt(4));
				for (int i = 0; i < arr.length; i++) {
					if (arr[i]!=0) {
						if (buffer.length()!=0) {buffer.append("|");}
						if (i==0) {buffer.append("根骨=");}
						else if (i==1) {buffer.append("力量=");}
						else if (i==2) {buffer.append("灵性=");}
						else if (i==3) {buffer.append("敏捷=");}
						buffer.append(arr[i]);
					}
				}
				for (int i = 4; i < vs.length; i++) {
					if (!(vs[i].startsWith("根骨=")||vs[i].startsWith("力量=")||vs[i].startsWith("灵性=")||vs[i].startsWith("敏捷="))) {
						if (buffer.length()!=0) {buffer.append("|");}
						buffer.append(vs[i]);
					}
				}
			}
			good.setValue(buffer.toString());
			assetUpdate.setGood(good);
		}
		assetUpdate.setMsg("本次培养获得了"+pexp+"点经验");
		SuitComposeAction.saveGoods(goods,pexp==0);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}
	/**培养*/
	public static int wingTraining(Goodstable pGood,int sum){
		if (pGood.getType()==8891) {
			return Integer.parseInt(pGood.getValue().split("\\|")[0].split("=")[1])*sum;
		}
		double gl=(SuitComposeAction.random.nextInt(70)+31)/100D;
		WingTraining wingTraining=GameServer.getWingTraining(pGood.getType());
		if (wingTraining==null) {
			int pexp=(int) (0.01*sum);
			pexp*=gl;
			return pexp;
		}
		int value=1;
		double base=0;
		try {
			if (wingTraining.getUn()!=-1) {
				String[] vs=pGood.getValue().split("\\|");
				if (vs.length>wingTraining.getUn()) {value=Integer.parseInt(vs[wingTraining.getUn()].split("=")[1]);}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (value>=wingTraining.getBases().length) {base=wingTraining.getBases()[wingTraining.getBases().length-1]/wingTraining.getBases().length;}
		else {base=wingTraining.getBases()[value-1]/value;}
		return (int) (base*sum*value/wingTraining.getValue()*gl)+sum;
	}
	/**32:升星*/
	public static void type32(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		Goodstable good=goods.get(0);
		if (good.getType()!=8888) {return;}
		String[] vs=good.getValue().split("\\|");
		int pz=SuitMixdeal.getPZlvl(vs[0].split("=")[1]);
		int xlvl=Integer.parseInt(vs[1].split("=")[1]);
		if (pz<=0||pz>6||xlvl>=15) {return;}
		if (pz!=goods.size()-1) {return;}//数量不足
		String value=xlvl<=2?"1":xlvl<=5?"2":xlvl<=8?"3":xlvl<=11?"4":"5";
		for (int i = 1; i < goods.size(); i++) {
			Goodstable goodstable=goods.get(i);
			if (goodstable.getType()!=8889) {return;}//使用材料不对
			if (!goodstable.getValue().split("=")[1].equals(value)) {return;}//使用材料不对
			goodstable.goodxh(1);
			if (goodstable.getUsetime()<0) {return;}//数量不够
		}
		BigDecimal money=new BigDecimal(Long.parseLong(value)*10000000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足

		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-"+money);
		xlvl++;
		vs[1]="星级="+xlvl;
		StringBuffer buffer=new StringBuffer();
		vs[0]="品质="+SuitMixdeal.getPZName(pz);
		for (int i = 0; i < vs.length; i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(vs[i]);
		}
		good.setValue(buffer.toString());
		assetUpdate.setGood(good);
		assetUpdate.setMsg("#G升星成功");
		SuitComposeAction.saveGoods(goods,false);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**33:品质*/
	public static void type33(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		BigDecimal money=new BigDecimal(10000000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		Goodstable good=goods.get(0);
		if (good.getType()!=8888) {return;}
		String[] vs=good.getValue().split("\\|");
		int pz=SuitMixdeal.getPZlvl(vs[0].split("=")[1]);
		int xlvl=Integer.parseInt(vs[1].split("=")[1]);
		if (pz<=0||pz>=6||xlvl<15) {return;}
		int size=pz<=3?(int)Math.pow(2,pz-1):pz==4?6:10;
		if (size!=goods.size()-1) {return;}//数量不足
		for (int i = 1; i < goods.size(); i++) {
			Goodstable goodstable=goods.get(i);
			if (goodstable.getType()!=8890) {return;}//使用材料不对
			goodstable.goodxh(1);
			if (goodstable.getUsetime()<0) {return;}//数量不够
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-"+money);
		boolean is=SuitComposeAction.random.nextInt(100)>=(22-pz);
		if (is) {
			assetUpdate.setMsg("品质升级失败");
		}else {
			pz+=1;
			StringBuffer buffer=new StringBuffer();
			vs[0]="品质="+SuitMixdeal.getPZName(pz);
			vs[1]="星级=0";
			for (int i = 0; i < vs.length; i++) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(vs[i]);
			}
			good.setSkin(good.getSkin().substring(0, good.getSkin().length()-1)+pz);
			good.setValue(buffer.toString());
			assetUpdate.setGood(good);
			assetUpdate.setMsg("#G品质升级成功");
		}
		SuitComposeAction.saveGoods(goods,is);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**34:重铸*/
	public static void type34(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		BigDecimal money=new BigDecimal(10000000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		Goodstable good=goods.get(0);
		if (good.getType()!=8888) {return;}
		if (1!=goods.size()-1) {return;}//数量不足
		for (int i = 1; i < goods.size(); i++) {
			Goodstable goodstable=goods.get(i);
			if (goodstable.getType()!=8892) {return;}//使用材料不对
			goodstable.goodxh(1);
			if (goodstable.getUsetime()<0) {return;}//数量不够
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-"+money);

		String[] vs=good.getValue().split("\\|");
		long exp=Long.parseLong(vs[2].split("=")[1]);
		int lvl=getWingLevel(exp);
		int size=lvl;
		int gl=SuitComposeAction.random.nextInt(100);
		if (gl<=20) {size+=SuitComposeAction.random.nextInt(9)+2;}
		else if (gl<=75){size+=SuitComposeAction.random.nextInt(6)+2;}
		else{size+=SuitComposeAction.random.nextInt(4)+2;}
		int[] arr=new int[4];
		addWingQuality(arr, size, SuitComposeAction.random.nextInt(4));

		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < 4; i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(vs[i]);
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]!=0) {
				if (buffer.length()!=0) {buffer.append("|");}
				if (i==0) {buffer.append("根骨=");}
				else if (i==1) {buffer.append("力量=");}
				else if (i==2) {buffer.append("灵性=");}
				else if (i==3) {buffer.append("敏捷=");}
				buffer.append(arr[i]);
			}
		}
		for (int i = 4; i < vs.length; i++) {
			if (!(vs[i].startsWith("根骨=")||vs[i].startsWith("力量=")||vs[i].startsWith("灵性=")||vs[i].startsWith("敏捷="))) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(vs[i]);
			}
		}
		good.setValue(buffer.toString());
		assetUpdate.setGood(good);
		SuitComposeAction.saveGoods(goods,false);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**35:炼化*/
	public static void type35(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		BigDecimal money=new BigDecimal(500000);
		BigDecimal money2=null;
		int lock=0;
		PartJade jade=suitOperBean.getJade();
		if (jade!=null) {
			lock=jade.getSuitid();
			int size=0;
			for (int i = 0; i < 6; i++) {
				if ((int)(lock/Math.pow(10,i))%10!=0) {
					size++;
				}
			}
			if (size!=0) {
				if (size==1) {money2=new BigDecimal(1000);}
				else if (size==2) {money2=new BigDecimal(3000);}
				else {money2=new BigDecimal(6000);}
			}
		}
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		if (money2!=null&&loginResult.getCodecard().compareTo(money2)<0) {return;}//仙玉不足
		Goodstable good=goods.get(0);
		if (good.getType()!=8888) {return;}
		if (3!=goods.size()-1) {return;}//数量不足
		for (int i = 1; i < goods.size(); i++) {
			Goodstable goodstable=goods.get(i);
			if (goodstable.getType()!=8893) {return;}//使用材料不对
			goodstable.goodxh(1);
			if (goodstable.getUsetime()<0) {return;}//数量不够
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-"+money);
		if (money2!=null) {
			loginResult.setCodecard(loginResult.getCodecard().subtract(money2));
			MonitorUtil.getMoney().useX(money2.longValue());
			assetUpdate.updata("X=-"+money2);
		}
		//开始炼化
		String extra=SuitComposeAction.RefinersV(good,4,lock);
		String value=SuitComposeAction.newExtra(good.getValue().split("\\|"),0,extra);
		good.setValue(value);
		assetUpdate.setGood(good);
		SuitComposeAction.saveGoods(goods,false);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**36:36翅膀升星石合成*/
	public static void type36(LoginResult loginResult,ChannelHandlerContext ctx,SuitOperBean suitOperBean){
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		List<Goodstable> goods=SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(),0);
		if (goods==null) {return;}
		BigDecimal money=new BigDecimal(100000);
		if (loginResult.getGold().compareTo(money)<0) {return;}//金钱不足
		int value=-1;
		for (int i = 0; i < goods.size(); i++) {
			Goodstable good=goods.get(i);
			if (good.getType()!=8889) {continue;}
			if (value==-1) {value=Integer.parseInt(good.getValue().split("=")[1]);}
			else if (value!=Integer.parseInt(good.getValue().split("=")[1])){return;}
			good.goodxh(1);
			if (good.getUsetime()<0) {return;}//数量不够
		}
		if (value==-1||value>=5) {return;}
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		SuitComposeAction.saveGoods(goods,false);
		value+=81139;
		BigDecimal sid=new BigDecimal(value);
		XXGDBean bean=new XXGDBean();
		bean.setId(sid.toString());
		bean.setSum(1);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		Goodstable goodstable = GameServer.getGood(sid);
		if( goodstable == null ){return;}
		AddGoodAction.addGood(assetUpdate,goodstable,loginResult,roleData,bean,assetUpdate.getType());
		assetUpdate.setMsg("#G合成成功");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**培养*/
	public static void addWingQuality(int[] arr,int size,int v){
		int gl=0;
		for (int i = 0; i < size; i++) {
			gl=SuitComposeAction.random.nextInt(4);
			if (gl<4) {arr[gl]++;}
			else {//主属性培养
				arr[v]++;
			}
		}
	}
	// 经验转换为等级
	public static int getWingLevel(long exp) {
		int i = 1;
		while (true) {
			if (exp <i*i*100) {return i-1;}
			i++;
			if (i>=200) {return 200;}
		}
	}
	// 当前等级总经验
	public static long getLevelNowExp(int lvl) {
		return lvl*lvl*100;
	}
	// 当前等级当前经验
	public static long getLevelLastExp(long exp) {
		int lvl=getWingLevel(exp);
		return exp-getLevelNowExp(lvl);
	}
}
