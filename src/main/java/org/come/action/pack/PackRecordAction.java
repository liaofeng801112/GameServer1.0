package org.come.action.pack;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.PackRecord;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

public class PackRecordAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 获得角色信息
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {
			return;
		}
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
//		PackRecord packRecord = GsonUtil.getGsonUtil().getgson().fromJson(message, PackRecord.class);
//		roleData.setPackRecord(packRecord);
		PackRecord packRecord =roleData.getPackRecord();
		String cd=message.substring(0,1);
		String ab=message.substring(1);
		int type=Integer.parseInt(cd);
		if (type==0) {//背包记录
			packRecord.setRecord(ab);
		}else if (type==1) {//宝宝支援
			packRecord.setHelpBb(ab);
		}else if (type==2) {//灵宝支援
			packRecord.setHelpLing(ab);
		}else if (type==3) {//套装收录数量
			packRecord.setSuitNum(Integer.parseInt(ab));
		}else if (type==4) {//收录
			
		}else if (type==5) {//更改装备的特效
			boolean isP=false;
			StringBuffer buffer=new StringBuffer();
			String[] abs=ab.split("\\|");
			for (int i = 0; i < abs.length; i++) {
				if (abs[i].startsWith("E")) {
					isP=true;
					packRecord.putTX(abs[i].substring(1).split("_"));
				}else {
					if (buffer.length()!=0) {
						buffer.append("|");
					}
					buffer.append(abs[i]);
				}
			}
			if (!isP) {
				packRecord.putTX("0");
			}
			loginResult.setSkin(buffer.toString());
			// 群发给所有人
			String sendMes=Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(loginResult.getRoleShow()));
			SendMessage.sendMessageToMapRoles(loginResult.getMapid(),sendMes);
		}
	}
}
