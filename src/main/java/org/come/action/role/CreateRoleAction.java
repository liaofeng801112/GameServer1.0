package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Present;
import org.come.entity.RoleSummoning;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.google.gson.Gson;

import come.tool.Calculation.BaseValue;
/**
 * 创建角色
 * @author 叶豪芳
 * @date : 2017年11月26日 下午2:52:03
 */
public class CreateRoleAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		System.out.println(message);
		// 将创建角色的信息写入登入用户返回bean中
		LoginResult createRole = GsonUtil.getGsonUtil().getgson().fromJson(message, LoginResult.class);
		// 判断角色名称是否存在，若存在提示客户端
		RoleTable role = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(createRole.getRolename());
		if( null == role  ){
			List<LoginResult> results = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(createRole.getUserName(), createRole.getUserPwd(),createRole.getServerMeString());
			if (results!=null&&results.size()>5) {
				String mes = Agreement.getAgreement().errorCreateAgreement();
				SendMessage.sendMessageToSlef(ctx,mes);
				return;
			}
			createRole.setHp(new BigDecimal(BaseValue.getRoleValue(createRole.getRace_id(), 0, 0, 0)));
			createRole.setMp(new BigDecimal(BaseValue.getRoleValue(createRole.getRace_id(), 0, 0, 0)));
			createRole.setRole_id(RedisCacheUtil.getRole_pk());
			// 通过 区id 获取 总区id
			String belongId = AllServiceUtil.getOpenareatableService().selectBelong(createRole.getServerMeString());
			if (belongId == null) {
				return;
			}
			createRole.setServerMeString(belongId);
			// 是否创建成功
			boolean isSuccess = AllServiceUtil.getRoleTableService().insertIntoRoleTable(createRole);
			// 创建成功
			if(isSuccess){
				List<Present> presents=GameServer.getPresents();
				for (int i = presents.size()-1; i >=0; i--) {
					Present present=presents.get(i);
					if (present.getType()==0) {
						RoleSummoning pet = GameServer.getPet(present.getId());
						if (pet!=null) {
							pet.setBasishp(pet.getHp());
							pet.setBasismp(pet.getMp());
							pet.setRoleid(createRole.getRole_id());
							AllServiceUtil.getRoleSummoningService().insertRoleSummoning(pet);	
						}		
					}else if (present.getType()==1) {
						Goodstable goodstable = GameServer.getGood(present.getId());
						goodstable.setRole_id(createRole.getRole_id());
						if (goodstable!=null) {
							if (EquipTool.canSuper(goodstable.getType())) {
								goodstable.setUsetime(present.getNum());
								AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
							}else {
								goodstable.setUsetime(1);
								for (int j = 0; j < present.getNum(); j++) {
									AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
								}
							}
						}
					}
				}
//				UserRoleArrBean userbean=new UserRoleArrBean();
//				results = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(createRole.getUserName(), createRole.getUserPwd(),createRole.getServerMeString());
//				userbean.setList(results);
				// 返回成功
//				String mes = Agreement.getAgreement().successCreateAgreement(GsonUtil.getGsonUtil().getgson().toJson(userbean));
//				SendMessage.sendMessageToSlef(ctx, mes);
				System.out.println(createRole.getRole_id());
				
				// 通过新创建角色的id 查询角色信息
				LoginResult loginResult = AllServiceUtil.getRoleTableService().selectRoleID(createRole.getRole_id());
				System.out.println(GsonUtil.getGsonUtil().getgson().toJson(loginResult));
				// 返回成功
				String mes = Agreement.getAgreement().successCreateAgreement(GsonUtil.getGsonUtil().getgson().toJson(loginResult));
				SendMessage.sendMessageToSlef(ctx, mes);
			}
		}
		// 角色名存在提示客户端
		else {
			String mes = Agreement.getAgreement().errorCreateAgreement();
			SendMessage.sendMessageToSlef(ctx, mes);
		}
	}
}
