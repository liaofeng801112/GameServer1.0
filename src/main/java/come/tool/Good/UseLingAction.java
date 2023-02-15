package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.handler.SendMessage;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;


public class UseLingAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		String[] vs=message.split("\\|");
		Lingbao lingbao=AllServiceUtil.getLingbaoService().selectLingbaoByID(new BigDecimal(vs[1]));
		if (lingbao==null) {return;}
		if (lingbao.getRoleid().compareTo(loginResult.getRole_id())!=0) {return;}
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[0]));
		if (good==null) {return;}
		if (good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
		if (good.getUsetime()<=0) {return;}
		long type=good.getType();
		if (type==190) {//灵宝技能书
			lingbaoskill(lingbao, good, ctx, loginResult);
		}else if (type==891) {//灵宝褚天印 灵宝品质提升
			lingbaoQuality(lingbao, good, ctx, loginResult);
		}else if (type==1002) {//灵法宝经验丹
			baoExp(lingbao, good, ctx, loginResult);
		}else if (type==28955) {//灵宝契合经验丹
			baoqh(lingbao, good, ctx, loginResult);
		}
	}
	/**灵宝契合增加丹*/
	public static void baoqh(Lingbao lingbao,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		lingbao.setLingbaoqihe(lingbao.getLingbaoqihe()+10000);
		int addqh = Integer.parseInt(good.getValue().split("=")[1]);//经验值
		String msg=ExpUtil.LFqh(lingbao, addqh);
		AllServiceUtil.getLingbaoService().updateLingbaoRedis(lingbao);
		assetUpdate.setLingbao(lingbao);
		assetUpdate.setMsg(msg);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**灵法宝经验丹*/
	public static void baoExp(Lingbao lingbao,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
	    AssetUpdate assetUpdate=new AssetUpdate();
      	assetUpdate.setType(AssetUpdate.USEGOOD);
      	UsePetAction.useGood(good, 1);
  		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		int addexp = Integer.parseInt(good.getValue().split("=")[1]);//经验值
		String msg=ExpUtil.LFExp(lingbao, addexp);
		AllServiceUtil.getLingbaoService().updateLingbaoRedis(lingbao);
    	assetUpdate.setLingbao(lingbao);
    	assetUpdate.setMsg(msg);
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate))); 	
	}
	/**灵宝品质提高*/
	public static void lingbaoQuality(Lingbao lingbao,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		int pz=getQv(lingbao.getBaoquality());
		if (pz==50) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("已经达到最高品质了"));
			return ;
		}
//概率        20 20 15 15 
//		消耗的数量 1 2 4 10
 	    pz/=10;
 	    pz++;
 	    int v=50;
 	    int sum=1;
 	    switch (pz) {
		    case 2:v=17;sum=1;break;
		    case 3:v=13;sum=2;break;
		    case 4:v=7;sum=4;break;
		    case 5:v=3;sum=10;break;
		}
 	    //判断数量是否足够
 	    if (good.getUsetime()<sum) {
 	    	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该品质提升需要消耗"+sum+"个灵宝诸天印"));
			return;
		}
 	    AssetUpdate assetUpdate=new AssetUpdate();
      	assetUpdate.setType(AssetUpdate.USEGOOD);
      	UsePetAction.useGood(good, sum);
  		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
 	    if (DropUtil.isV(v)) {//成功
 	    	switch (pz) {
 			    case 2:lingbao.setBaoquality("贴身");break;
 			    case 3:lingbao.setBaoquality("珍藏");break;
 			    case 4:lingbao.setBaoquality("无价");break;
 			    case 5:lingbao.setBaoquality("传世");break;
 			    default:lingbao.setBaoquality("把玩");break;
 			}
 	     	AllServiceUtil.getLingbaoService().updateLingbaoRedis(lingbao);
        	assetUpdate.setLingbao(lingbao);
        	assetUpdate.setMsg("提升成功");
 	    }else {//失败
 	    	assetUpdate.setMsg("提升失败");
		}
 	    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));	 
 	}
	/**灵宝技能书使用*/
	public static void lingbaoskill(Lingbao lingbao,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		if (lingbao.getBaotype().equals("法宝")) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("法宝不能打技能"));
			return;
		}
        String[] v=good.getValue().split("=")[1].split("&");
        String skillname=v[GameServer.random.nextInt(v.length)];
        Skill skill=GameServer.getSkill(skillname);
        if (skill==null)return;
        AssetUpdate assetUpdate=new AssetUpdate();
       	assetUpdate.setType(AssetUpdate.USEGOOD);
       	UsePetAction.useGood(good, 1);
   		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
        String msg=lingbao.skilljihe(skill);
        if (msg==null) {
        	AllServiceUtil.getLingbaoService().updateLingbaoRedis(lingbao);
        	assetUpdate.setLingbao(lingbao);
        	assetUpdate.setMsg("学习成功");
		}else {
			assetUpdate.setMsg(msg);
		}
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));	
    }
	//根据品质获取系数
	public static int getQv(String quality){
		switch (quality) {
		case "把玩":return 10;
		case "贴身":return 20;
		case "珍藏":return 30;
		case "无价":return 40;
		case "传世":return 50;
		}
		return 10;
	}
}
