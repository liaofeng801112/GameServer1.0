package come.tool.Good;

import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import io.netty.channel.ChannelHandlerContext;

import net.sf.jsqlparser.statement.select.Limit;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.*;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

import java.math.BigDecimal;


public class UseMixdeal {

	/**药瓶*/
    public static void TimingGood(Goodstable good,ChannelHandlerContext ctx,LoginResult login){


    	RoleData roleData=RolePool.getRoleData(login.getRole_id());
    	String goodname="";
		if (good.getType()==493) {
			goodname="六脉化神丸";
		}else if (good.getType()==492) {
			goodname="玉枢返虚丸";
		}
		UseCardBean limit=roleData.getLimit(goodname);
		if (limit != null){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已拥有此效果不可继续使用"));
			return;
		}
	 	if (limit == null) {
			limit=new UseCardBean(goodname, goodname, good.getSkin(), (Integer.parseInt(good.getValue())*60000L)+System.currentTimeMillis(), null);
			roleData.addLimit(limit);
		}else {
			limit.setTime(limit.getTime()+Integer.parseInt(good.getValue())*60000L);
		}

		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
    }
    /**回蓝符文*/
    public static void BlueBack(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
    	RoleData roleData=RolePool.getRoleData(login.getRole_id());
    	UseCardBean limit=roleData.getLimit("回蓝符");
		if (limit==null) {
			limit=new UseCardBean(good.getGoodsname(), "回蓝符", good.getSkin(),3600000+System.currentTimeMillis(),good.getValue());
			roleData.addLimit(limit);
		}else if (!limit.getName().equals(good.getGoodsname())) {
			limit.setName(good.getGoodsname());
			limit.setSkin(good.getSkin());
			limit.setValue(good.getValue());
			limit.setTime(3600000+System.currentTimeMillis());
		}else {
			limit.setTime(limit.getTime()+3600000);
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
    }
    /**属性卡*/
    public static void baseCard(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
    	RoleData roleData=RolePool.getRoleData(login.getRole_id());
    	String type=null;
    	long time=0;
    	if (good.getGoodsname().indexOf("返老还童丹")!=-1) {
    		type="童卡";
    		time=3600000;
		}else if (good.getGoodsname().indexOf("变身卡")!=-1||good.getGoodsname().indexOf("属性卡")!=-1) {
    		type="变身卡";
    		time=3600000;
		}else if (good.getInstruction().indexOf("强法型")!=-1) {
			type="强法型";
    		time=3600000;
		}else if (good.getInstruction().indexOf("加抗型")!=-1) {
			type="加抗型";
    		time=3600000;
		}else {
			type="增益型";
    		time=3600000;
		}
//    	boolean is=false;
    	UseCardBean limit=roleData.getLimit(type);
    	if (limit==null) {
//            if (type.equals("童卡")) {
//            	is=true;
//			}else if (type.equals("变身卡")&&roleData.getLimit("童卡")==null&&good.getGoodsname().indexOf("变身卡")!=-1) {
//				is=true;
//			}
    		limit=new UseCardBean(good.getGoodsname(), type, good.getSkin(), time+System.currentTimeMillis(),good.getValue());
			roleData.addLimit(limit);
		}else if (limit.getName().equals(good.getGoodsname())&&limit.getValue().equals(good.getValue())) {
			limit.setTime(limit.getTime()+time);
		}else {
			limit.setName(good.getGoodsname());
			limit.setSkin(good.getSkin());
			limit.setValue(good.getValue());
			limit.setTime(time+System.currentTimeMillis());
		}
    	AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	 
    }
    /**其他类型的*/
    public static void qtCard(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
    	RoleData roleData=RolePool.getRoleData(login.getRole_id());

    	if (good.getType()==113) {
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
			UseCardBean limit=roleData.removeLimit("童卡");
	    	if (limit==null) {roleData.removeLimit("变身卡");}
			if (limit!=null) {
				assetUpdate.setUseCard(limit);
			}
	    	assetUpdate.setMsg("你使用了"+good.getGoodsname());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	 
		}else if (good.getType()==111) {
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
			UseCardBean limit=roleData.removeLimit("摄妖香");
	    	if (limit!=null) {
				limit.setTime(-1);
				assetUpdate.setUseCard(limit);
			}
	    	assetUpdate.setMsg("你使用了"+good.getGoodsname());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	 
		}else {
			String type=null;
	    	long time=0;
			if (good.getType()==88) {
				type="摄妖香";
				time=3600000;
			}else {
				type="杀人香";
				time=3600000;
			}
			UseCardBean limit=roleData.getLimit(type);
			if (limit==null) {
				limit=new UseCardBean(type, type, good.getSkin(),time+System.currentTimeMillis(), null);
				roleData.addLimit(limit);
			}else {
				limit.setTime(limit.getTime()+time);
			}
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
			assetUpdate.setUseCard(limit);
			assetUpdate.setMsg("你使用了"+good.getGoodsname());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
		}
    }
    
    /**
	 * 枯荣丹使用
	 */
	public static void gld(Goodstable good,RoleSummoning pet,ChannelHandlerContext ctx,LoginResult login){
		RoleData roleData=RolePool.getRoleData(login.getRole_id());
		UseCardBean limit=roleData.getLimit("枯荣丹");
		if (limit==null) {
			limit=new UseCardBean(good.getGoodsname(), "枯荣丹", good.getSkin(),3600000+System.currentTimeMillis(), pet.getSid().toString());
			roleData.addLimit(limit);
		}else if (limit.getValue().equals(pet.getSid().toString())) {
			limit.setTime(limit.getTime()+3600000);
		}else {
			limit.setName(good.getGoodsname());
			limit.setSkin(good.getSkin());
			limit.setValue(pet.getSid().toString());
			limit.setTime(3600000+System.currentTimeMillis());	
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
	}
	/**
	 * VIP月卡使用
	 */
	public static void vipSss(Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		long time = 1000L * 60L * 60L * 24L * 30L;
		RoleData roleData = RolePool.getRoleData(login.getRole_id());
		UseCardBean limit = roleData.getLimit("VIP");
		if (limit != null){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已拥有此效果不可继续使用"));
			return;
		}
		if (limit == null) {
			limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半");
			roleData.addLimit(limit);
		}  else if (!limit.getName().equals(good.getGoodsname())) {
			limit.setName(good.getGoodsname());
			limit.setSkin(good.getSkin());
			limit.setValue(good.getValue());
			limit.setTime(time + System.currentTimeMillis());

		} else {
			limit.setTime(limit.getTime() + time);
		}
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("激活了" + (time / 1000 / 60 / 60 / 24) + "天VIP特权");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**月药瓶子*/
	public static void Medicine(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		long time = 1000L * 60L * 60L * 24L * 30L;
		RoleData roleData=RolePool.getRoleData(login.getRole_id());
		String goodname="";
		if (good.getType()==921) {
			goodname="超级六脉化神丸_月";
		}else if (good.getType()==922) {
			goodname="超级玉枢返虚丸_月";
		}
		UseCardBean limit=roleData.getLimit(goodname);
		if (limit==null) {
			limit=new UseCardBean(goodname, goodname, good.getSkin(), (Integer.parseInt(good.getValue())* time)+System.currentTimeMillis(), null);
			roleData.addLimit(limit);
		}else {
			limit.setTime(limit.getTime()+Integer.parseInt(good.getValue())* time);
		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		//assetUpdate.setMsg("你使用了"+ goodname + (time / 1000 / 60 / 60 / 24) + "天");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}


	/**双倍经验药水*/
	public static void Potion(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		RoleData data=RolePool.getRoleData(login.getRole_id());

		UseCardBean limit=data.getLimit("双倍药水");
		if (limit==null) {
			limit=new UseCardBean("双倍经验", "经验", "shuang", 3600000+System.currentTimeMillis(), null);
			data.addLimit(limit);
		}else {
			limit.setTime(limit.getTime()+3600000);
		}
//		for (int k =  data.getSkills().size()-1; k >=0; k--) {
//			FightingSkill skillTwo=data.getSkills().get(k);
//			if (skillTwo.getSkillid()>1000&&skillTwo.getSkillid()<=1100) {
//				skillTwo.setSkillsum(10);
//				return;
//			}
//		}
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setUseCard(limit);
		assetUpdate.setMsg("你使用了"+good.getGoodsname());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}



}
