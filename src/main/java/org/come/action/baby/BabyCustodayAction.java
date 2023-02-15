package org.come.action.baby;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Random;

import org.come.action.IAction;
import org.come.bean.BabyListBean;
import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 宝宝抚养权,产生宝宝,如果yes就自己抚养，no就对方抚养
 * @author Administrator
 *
 */
public class BabyCustodayAction implements IAction {
	private Random random;
	public BabyCustodayAction() {
		random = new Random();
	}

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 获得角色信息
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		BabyListBean babyListBean=new BabyListBean();
		// 男孩女孩
		Baby baby=new Baby();
		baby.setChildSex(random.nextInt(2));
		// 判断是抚养
		if( message.equals("yes") ){
			baby.setBabyname(loginResult.getRolename()+"的宝宝");
			baby.setRoleid(loginResult.getRole_id());
			AllServiceUtil.getBabyService().createBaby(baby);
			// 返回该角色所有宝宝
			List<Baby> babys = AllServiceUtil.getBabyService().selectBabyByRolename(loginResult.getRole_id());
			babyListBean.setBabyList(babys);
            String msg = Agreement.getAgreement().getBaby(GsonUtil.getGsonUtil().getgson().toJson(babyListBean));
			SendMessage.sendMessageToSlef(ctx, msg);
		}else{
			// 不抚养
			baby.setBabyname(loginResult.getMarryObject()+"的宝宝");
			RoleTable roleTable=AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(loginResult.getMarryObject());
			if (roleTable==null)return;
			baby.setRoleid(roleTable.getRole_id());
			AllServiceUtil.getBabyService().createBaby(baby);
			// 如果对方在线，发送宝宝消息
			if( GameServer.getRoleNameMap().get(loginResult.getMarryObject()) != null ){
				// 返回该角色所有宝宝
				List<Baby> babys = AllServiceUtil.getBabyService().selectBabyByRolename(roleTable.getRole_id());
				babyListBean.setBabyList(babys);
				String msg = Agreement.getAgreement().getBaby(GsonUtil.getGsonUtil().getgson().toJson(babyListBean));
				// 根据对方名字发送消息
				SendMessage.sendMessageByRoleName(loginResult.getMarryObject(), msg);
			}
		}
	}
}