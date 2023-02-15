package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Mount;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;


public class UseMountAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		String[] vs=message.split("\\|");
		Mount mount=AllServiceUtil.getMountService().selectMountsByMID(new BigDecimal(vs[1]));
		if (mount==null) {return;}
		if (mount.getRoleid().compareTo(loginResult.getRole_id())!=0) {return;}
		if (vs[0].equals("DH")) {
			DHMount(mount, ctx, loginResult);
			return;
		}
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[0]));
		if (good==null) {return;}
		if (good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
		if (good.getUsetime()<=0) {return;}
		long type=good.getType();
		if (type == 718) {// 坐骑技能卡
		  // useMountSkillCard();
		} else if (type == 719) {// 坐骑遗忘所有技能
		  // mountMissSkills(mount, good, ctx, loginResult);
		} else if (type == 720) {// 随机生成坐骑的初始值
		  // randomMountValue();
		} else if (type == 721) {// 坐骑的灵性,力量,根骨各加一
			addMountValue(mount, good, ctx, loginResult);
		} else if (type == 801){// 增加坐骑的经验
			addMountExp(mount, good, ctx, loginResult);
		} else if (type == 802){// 增加坐骑的技能熟练度
			addMountProficiency(mount, good, ctx, loginResult);
		}
	}
	/**坐骑点化*/
	public void DHMount(Mount mount,ChannelHandlerContext ctx,LoginResult login){
//		坐骑点化功能，点化要求当前坐骑要求必须满等级，满熟练，
//		点化后等级将退回0级，熟练度清0，坐骑点化后，管制加1，各项初值均提升3，熟练度上限增加为150000,管制范围扩大，能力更强。
		if (mount.getMountlvl()!=100||mount.getProficiency()<100000) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑不符合点化要求"));
			return;
		}
		if (mount.getSid()!=null||mount.getOthrersid()!=null||mount.getSid3()!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑还管制着召唤兽"));
			return;
		}
		mount.setMountlvl(101);
		mount.setExp(0);
		mount.setProficiency(0);
		mount.setSpri(mount.getSpri()+3);
		mount.setPower(mount.getPower()+3);
		mount.setBone(mount.getBone()+3);
		AllServiceUtil.getMountService().updateMountRedis(mount);
		AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	assetUpdate.setMount(mount);
    	assetUpdate.setMsg("点化成功");
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**坐骑增加技能熟练度*/
	public void addMountProficiency(Mount mount,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
        //判断该坐骑的技能熟练度是否达到10000
		int up=100000;
		if (mount.getMountlvl()>100) {up=150000;}
		if(mount.getProficiency()>=up){//达到峰值
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑 "+mount.getMountname()+"的技能熟练度已达到峰值！！"));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		int addvalue = Integer.parseInt(good.getValue().split("=")[1]);//物品的技能熟练度
		int proficiency = mount.getProficiency() + addvalue;
		if(proficiency > up){proficiency = up;}
		mount.setProficiency(proficiency);
		AllServiceUtil.getMountService().updateMountRedis(mount);
		assetUpdate.setMount(mount);
	    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}	
	/**坐骑增加经验*/
	public void addMountExp(Mount mount,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		//判断坐骑的等级是否达到100级
		int lvl=mount.getMountlvl();
		if(lvl == 100||lvl>=200){//达到最高等级
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑 "+mount.getMountname()+" 已达最高等级100级！！"));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		int addexp = Integer.parseInt(good.getValue().split("=")[1]);//经验值
		ExpUtil.MountExp(mount, addexp);//进行升级判断
		AllServiceUtil.getMountService().updateMountRedis(mount);
		assetUpdate.setMount(mount);
	    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**坐骑的灵性,力量,根骨各加一*/
	public void addMountValue(Mount mount,Goodstable good,ChannelHandlerContext ctx,LoginResult login) {
        if (mount.getUseNumber()>=3) {
        	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能使用3次筋骨提气丹"));
			return;
		}
        AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		mount.setBone(mount.getBone() + 1);
		mount.setSpri(mount.getSpri() + 1);
		mount.setPower(mount.getPower() + 1);
		mount.setUseNumber(mount.getUseNumber()+1);// 使用次数加一
		AllServiceUtil.getMountService().updateMountRedis(mount);
		assetUpdate.setMount(mount);
	    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**坐骑遗忘所有技能*/
	public void mountMissSkills(Mount mount,Goodstable good,ChannelHandlerContext ctx,LoginResult login) {
		
	}
	/**使用坐骑技能卡的方法*/
	public void useMountSkillCard(Mount mount,Goodstable good,ChannelHandlerContext ctx,LoginResult login) {

	}
}
