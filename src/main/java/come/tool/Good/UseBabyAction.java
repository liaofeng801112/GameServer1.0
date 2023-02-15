package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Talent;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;


public class UseBabyAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		String[] vs=message.split("\\|");
		Baby baby=AllServiceUtil.getBabyService().selectBabyById(new BigDecimal(vs[1]));
		if (baby==null) {return;}
		if (baby.getRoleid().compareTo(loginResult.getRole_id())!=0) {return;}
		Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[0]));
		if (good==null) {return;}
		if (good.getRole_id().compareTo(loginResult.getRole_id())!=0) {return;}
		if (good.getUsetime()<=0) {return;}
		long type=good.getType();
		if (type==50) {//培养类
			
		}else if (type==51) {//金丹
			babySkill(baby, good, ctx, loginResult);
		}else if (type==52) {//琼浆玉液
			babySkillUP(baby, good, ctx, loginResult, Integer.parseInt(vs[2]));
		}else if (type==53) {//返老返童
			
		}
	}
	/**琼浆玉液类使用*/
	public static void babySkillUP(Baby baby,Goodstable good,ChannelHandlerContext ctx,LoginResult login,int path){
		if (!(path>=0&&path<3)) {return;}
		String outcome=baby.getOutcome();
		if (outcome==null||outcome.equals("")){
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("成年后才能强化技能"));
			return;
		}
		String Talents=baby.getTalents();
		if (Talents==null||Talents.equals("")) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("孩子没有可强化的天资"));
			return;
		}
		String[] v=Talents.split("\\|");
	    String[] vs=v[path].split("=");
        int id=Integer.parseInt(vs[0]);
        if (id<=3) {
        	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该天资无法强化"));
      	    return;
	    }
        int lvl=Integer.parseInt(vs[1]);
        if (lvl>=10) {
        	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该技能已经满级了"));
      	    return;
        }
        AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
        Talent talent=GameServer.getTalent(id);
        if (!DropUtil.isV(talent.getFail()*lvl)) {
    	    baby.ChangeTalent(path,id+"="+(lvl+1));
		    AllServiceUtil.getBabyService().updateBabyRedis(baby);
            assetUpdate.setBaby(baby);
            assetUpdate.setMsg("你孩子的"+talent.getTalentName()+"升级成功");
	    }else {
	    	assetUpdate.setMsg("你孩子的"+talent.getTalentName()+"升级失败");
	    }
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));	
	}
	/**金丹类使用*/
	public static void babySkill(Baby baby,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
//		25859
		int path=-1;
    	if (good.getValue()!=null&&!good.getValue().equals("")) {
			path=Integer.parseInt(good.getValue());
		}
        int talentid=1+GameServer.random.nextInt(55);
        if (good.getGoodsname().indexOf("高级")!=-1) {
        	talentid=5+GameServer.random.nextInt(26)*2;
		}
        if (talentid>=3)talentid+=997;
        Talent talent=GameServer.getTalent(talentid);
        if (talent==null)return;
    	AssetUpdate assetUpdate=new AssetUpdate();
    	assetUpdate.setType(AssetUpdate.USEGOOD);
    	UsePetAction.useGood(good, 1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
        if (baby.ChangeTalent(path==-1?GameServer.random.nextInt(3):path,talentid+"")) {
            AllServiceUtil.getBabyService().updateBabyRedis(baby);
            assetUpdate.setBaby(baby);
            assetUpdate.setMsg("孩子学会了"+talent.getTalentName());
        }else {
        	assetUpdate.setMsg("孩子已经学会了"+talent.getTalentName());
        }
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));	
	}
	/**培养类使用*/
	public static void training(Baby baby,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		
	}
}
