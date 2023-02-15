package org.come.action.lingbao;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.Lingbao;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
/**
 * 更新灵宝   根据操作字段修改数据库  不回传
 * @author Administrator
 *
 */
public class UpdateLingAction implements IAction {
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		Lingbao lingbao = GsonUtil.getGsonUtil().getgson().fromJson(message,Lingbao.class );
		Lingbao lingbaoRedis=AllServiceUtil.getLingbaoService().selectLingbaoByID(lingbao.getBaoid());
		if (lingbaoRedis==null||loginResult.getRole_id().compareTo(lingbaoRedis.getRoleid())!=0) {
			return;
		}
		if( lingbao.getOperation() == null || lingbao.getOperation().equals("")){// 修改灵宝
			Integer yss=lingbaoRedis.getEquipment();//原来的状态
	    	Integer nss=lingbao.getEquipment();//新的状态
			int ylvl=lingbaoRedis.getLingbaolvl().intValue();
			int nlvl=lingbao.getLingbaolvl().intValue();
			if (nlvl-ylvl==1) {
				if (ylvl%30==0) {
					lingbaoRedis.setLingbaolvl(lingbao.getLingbaolvl());
					if (lingbaoRedis.getLingbaoexe().compareTo(lingbao.getLingbaoexe())>0) {
						lingbaoRedis.setLingbaoexe(lingbao.getLingbaoexe());	
					}
				}
			}
			lingbaoRedis.setSkillsum(lingbao.getSkillsum());
			lingbaoRedis.setFusum(lingbao.getFusum());	
			lingbaoRedis.setSkills(lingbao.getSkills());
			lingbaoRedis.setKangxing(lingbao.getKangxing());
			lingbaoRedis.setFushis(lingbao.getFushis());	
			lingbaoRedis.setEquipment(lingbao.getEquipment());
			AllServiceUtil.getLingbaoService().updateLingbaoRedis(lingbaoRedis);
			if (nss!=null&&nss!=yss) {//物品状态
				RoleData data=RolePool.getRoleData(lingbao.getRoleid());
				if (data!=null) {
					data.CLing(lingbao.getBaoid(), lingbao.getBaotype(),nss==1);
				}	
			} 
		}else if( lingbao.getOperation().equals("删除") ){// 删除灵宝
			AllServiceUtil.getLingbaoService().deleteLingbao(lingbao.getBaoid());
		}
	}
}
