package org.come.action.summoning;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.SummonPetBean;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.PetExchange;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;
/**
 * 合成神兽、解封召唤兽，客户端发来消耗物品和召唤的神兽，返回所有召唤兽信息
 * 金柳露
 * @author 叶豪芳
 * @date 2018年1月4日 上午11:34:59
 */ 
public class SummonPetAction implements IAction {
	public static Random random=new Random();
	public static String[] kxs = {"抗混乱=30","抗封印=30","抗昏睡=30","抗中毒=30","物理吸收=30","抗风=30","抗火=30","抗水=30","抗雷=30","抗鬼火=30","抗遗忘=30"};
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		// 获取发来的信息
		SummonPetBean petBean = GsonUtil.getGsonUtil().getgson().fromJson(message, SummonPetBean.class);
		if (petBean.getOpertype()==0) {//插入数据库
			addPet(petBean, ctx);
		}else if (petBean.getOpertype()==1) {//金柳露
			jll(petBean, ctx);
		}else if (petBean.getOpertype()==2) {//宝宝兑换
			exchange(petBean, ctx);
		}
	}
	/**宝宝兑换*/
	public void exchange(SummonPetBean petBean,ChannelHandlerContext ctx){
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		PetExchange exchange=GameServer.getPetExchange(petBean.getPetid().intValue());
		RoleSummoning pet=exchange!=null?GameServer.getPet(exchange.getPid()):null;
		if (pet==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("错误兑换公式"));
			return;
		}
		//验证消耗
		List<Goodstable> list=new ArrayList<>();
		long money=0;
		String goodName = null;
		String[] v=exchange.getConsume().split("\\|");
		
		for (int i = 0; i < v.length; i++) {
			if (v[i].startsWith("D")) {
				money=Long.parseLong(v[i].substring(2));
				if (loginResult.getGold().longValue()<money){
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不够"+money));
					return;
				}
			}else if (v[i].startsWith("G")) {
				String[] vs=v[i].split("=");
				BigDecimal goodid=new BigDecimal(vs[1]);
				int sum=Integer.parseInt(vs[2]);
				Goodstable goodstable=GameServer.getAllGoodsMap().get(goodid);
				if (goodstable==null) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("错误兑换公式"));
					return;
				}
				List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), goodid);
				int SYsum=sum;
				for (int k = 0; k < goods.size(); k++) {
					Goodstable good=goods.get(k);
					if (goodName==null) {
						goodName=good.getGoodsname();
					}
					if (good.getUsetime()>=SYsum) {
						good.setUsetime(good.getUsetime()-SYsum);
						SYsum=0;
						list.add(good);
						break;
					}
					SYsum-=good.getUsetime();
					good.setUsetime(0);
					list.add(good);
				}
				if (SYsum>0) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不够"+sum+"个"+goodstable.getGoodsname()));
					return;
				}
			}
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		if (money!=0) {
			loginResult.setGold(loginResult.getGold().add(new BigDecimal(-money)));
			MonitorUtil.getMoney().useD(money);	
		}
		assetUpdate.updata("D=-"+money);
		if (list.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				Goodstable good=list.get(i);
				AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
				assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
			}
		}
		initPet(pet);
		pet.setRoleid(loginResult.getRole_id());	
		AllServiceUtil.getRoleSummoningService().insertRoleSummoning(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("成功兑换#R"+pet.getSummoningname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  

		StringBuffer buffer=new StringBuffer();
		buffer.append("#Y天空一声巨响,#G");
		buffer.append(loginResult.getRolename());
		buffer.append("#Y终于集齐#G");
		buffer.append(goodName);
		buffer.append("#Y,获得一只#G");
		buffer.append(pet.getSummoningname());
		buffer.append("#Y召唤兽,赶紧将此召唤兽收于麾下！#89");
		NChatBean bean = new NChatBean();
		bean.setId(4);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**金柳露*/
	public void jll(SummonPetBean petBean,ChannelHandlerContext ctx){
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(petBean.getPetid());
		if (pet==null||pet.getTurnRount()!=0) {return;}
		if (pet.getRoleid().compareTo(loginResult.getRole_id())!=0) {return;}
		if (pet.getSsn()!=null&&!pet.getSsn().equals("0")) {return;}
		RoleSummoning pet2 = GameServer.getAllPet().get(new BigDecimal(pet.getSummoningid()));
		if (pet2==null) {
			return;
		}
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(petBean.getGoodid());
		if (good==null) {return;}
		if (good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
		if (good.getUsetime()<=0||good.getType()!=701) {return;}
		String msg=null;
		pet.setHp(pet2.getHp());
		pet.setMp(pet2.getMp());
		pet.setAp(pet2.getAp());
		pet.setSp(pet2.getSp());
		if (good.getValue().equals("1")) {//超级金流露
//			pet.setHp(pet2.getHp());
//			pet.setMp(pet2.getMp());
//			pet.setAp(pet2.getAp());
//			pet.setSp(pet2.getSp());
			pet.setGrowlevel(getgroup(pet2.getGrowlevel()));
			double vOne=Double.parseDouble(pet.getGrowlevel());
			double vTwo=Double.parseDouble(pet2.getGrowlevel());
			int v1=(int) (vOne*1000);
			int v2=(int) (vTwo*1000);
			v1+=(int)(v2*0.05);
			BigDecimal sx = new BigDecimal(v1/1000D);
			sx=sx.setScale(3,BigDecimal.ROUND_HALF_UP);
			pet.setGrowlevel(sx.toString());
			if (sx.doubleValue()>vTwo) {
				msg="你的召唤兽体内一丝金光闪现";
			}else {
				msg="你的召唤兽发生了变化";
			}
		}else {//金流露
//			pet.setHp(getchu(pet2.getHp()));
//			pet.setMp(getchu(pet2.getMp()));
//			pet.setAp(getchu(pet2.getAp()));
//			pet.setSp(getchu(pet2.getSp()));
			pet.setGrowlevel(getgroup(pet2.getGrowlevel()));
			msg="你的召唤兽发生了变化";
		}
		pet.setLyk("");
		pet.setGrade(0);
		pet.setTurnRount(0);
		pet.setExp(new BigDecimal(0));
		pet.setBone(0);
		pet.setSpir(0);
		pet.setPower(0);
		pet.setSpeed(0);
		pet.setCalm(0);
		pet.setDragon(0);
		pet.setSpdragon(0);
		pet.setAlchemynum(0);
		pet.setBasishp(pet.getHp());
		pet.setBasismp(pet.getMp());
		
		good.goodxh(1);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
	    assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
	    assetUpdate.setPet(pet);
		assetUpdate.setMsg(msg);
	    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
	} 
	/**ADDpet*/
	public void addPet(SummonPetBean petBean,ChannelHandlerContext ctx){
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		if (petBean.getPetid().longValue()<200000||petBean.getPetid().longValue()>200091) {
			WriteOut.addtxt(loginResult.getRole_id()+":非法添加召唤兽:"+petBean.getPetid(),9999);	
			return;
		}
		RoleSummoning pet=GameServer.getPet(petBean.getPetid());
		initPet(pet);
		pet.setRoleid(loginResult.getRole_id());	
		AllServiceUtil.getRoleSummoningService().insertRoleSummoning(pet);
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USERGOOD);
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
	}
	/**初始宝宝出来*/
	public void initPet(RoleSummoning pet){
		pet.setBasishp(pet.getHp());
		pet.setBasismp(pet.getMp());
		pet.setFaithful(100);// 设置忠诚
		pet.setGrade(0);
		pet.setTurnRount(0);
		pet.setBone(0);
		pet.setSpir(0);
		pet.setPower(0);
		pet.setSpeed(0);
		pet.setCalm(0);
		pet.setDragon(0);
		pet.setSpdragon(0);
		pet.setAlchemynum(0);
		pet.setExp(new BigDecimal(0));
		pet.setOpenSeal(1);
		pet.setOpenql(0);
		//初值随机  已被取消
//		if (pet.getSsn()!=null&&pet.getSsn().equals("0")) {
//			pet.setHp(getchu(pet.getHp()));
//			pet.setMp(getchu(pet.getMp()));
//			pet.setAp(getchu(pet.getAp()));
//			pet.setSp(getchu(pet.getSp()));
//			pet.setGrowlevel(getgroup(pet.getGrowlevel()));	
//		}	
		String yb = pet.getResistance();
		if (yb == null|| yb.equals("")) {
			int p=random.nextInt(kxs.length);
			int p2=random.nextInt(kxs.length);
			while (p2==p) {
				p2=random.nextInt(kxs.length);
			}
			pet.setResistance(kxs[p]+"|"+kxs[p2]);
		}
	}
    /**
     * 随机初值
     */
	public static int getchu(int v){
		int f=(v>>2)+1;
		if (f<=0) {
			return v;
		}
		return v-random.nextInt(f);		
	}
	/**随机成长*/
	public static String getgroup(String group){
		try {
			double v=Double.parseDouble(group);
			int v2=(int) (v*1000);
			BigDecimal sx = new BigDecimal(getchu(v2)/1000D);
			sx=sx.setScale(3,BigDecimal.ROUND_HALF_UP);
			return sx.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return group;
	}
}
