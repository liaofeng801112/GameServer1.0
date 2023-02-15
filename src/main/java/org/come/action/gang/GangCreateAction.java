package org.come.action.gang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;
import org.come.action.IAction;
import org.come.bean.GangChangeBean;
import org.come.bean.LoginResult;
import org.come.entity.Gang;
import org.come.entity.Goodstable;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;
import come.tool.newGang.GangUtil;
/**
 * 创建帮派，客户端发来gang的实体类，广播角色信息
 * @author 叶豪芳
 * @date 2017年12月19日 上午12:46:53
 * 
 */ 
public class GangCreateAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo.getGang_id().intValue()!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已经有帮派了"));		
			return;
		}
		Gang gang = GsonUtil.getGsonUtil().getgson().fromJson(message, Gang.class);
        if (gang.getGangname().length()>10) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮派名称过长"));
			return;
		}
        if (gang.getIntroduction().length()>140) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮派宗旨过长"));
			return;
		}
		// 判断帮派名字是否存在
		Gang roleGang = AllServiceUtil.getGangService().findGangByGangName(gang.getGangname());
		if (roleGang!=null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮派名称已存在"));
			return;
		}
		List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(roleInfo.getRole_id(), new BigDecimal(501));
		Goodstable good=null;
		for (int i = 0; i < goods.size(); i++) {
			if (goods.get(i).getUsetime()>0) {
				good=goods.get(i);
				break;
			}
		}
		if (good==null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你背包没有三界召集令"));
			return;
		}
		//消耗三界召集令
		AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		good.goodxh(1);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  

		gang.setGangnumber(new BigDecimal(1));
		gang.setBuilder(new BigDecimal(0));
		gang.setGanggrade(new BigDecimal(1));
		gang.setFounder(roleInfo.getRolename());
		gang.setGangbelong(roleInfo.getRolename());
		boolean isSuccess = AllServiceUtil.getGangService().createGang(gang);
		if (isSuccess) {//创建成功
			
			GangUtil.addGangDomain(gang, roleInfo.getRole_id(), ctx);
			
			RoleTable roleTable=new RoleTable(0, roleInfo);
			roleTable.setGang_id(gang.getGangid());
			roleTable.setGangpost("帮主");
			roleTable.setGangname(gang.getGangname());
			AllServiceUtil.getRoleTableService().updateGang(roleTable);
			roleInfo.setGang_id(gang.getGangid());
			roleInfo.setGangpost("帮主");
			roleInfo.setGangname(gang.getGangname());
			//发送调整
			String sendMes=Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleInfo.getRoleShow()));
			SendMessage.sendMessageToMapRoles(ctx,roleInfo.getMapid(),sendMes);	
			GangChangeBean gangChangeBean=new GangChangeBean(roleInfo,"创建帮派成功");
			SendMessage.sendMessageByRoleName(roleInfo.getRolename(),Agreement.GangChangeAgreement(GsonUtil.getGsonUtil().getgson().toJson(gangChangeBean)));
			
		}	
	}
}
