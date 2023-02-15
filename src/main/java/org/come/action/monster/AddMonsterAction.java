package org.come.action.monster;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
/**
 * 添加明雷怪,客户端发送boosId
 * @author 叶豪芳
 *
 */
public class AddMonsterAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		WriteOut.addtxt("放妖协议头",9999);
		// 获得该角色ID
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		WriteOut.addtxt("放妖协议头:"+loginResult.getRole_id()+":"+loginResult.getRolename(),9999);
//		// 获得boosID，根据boosID得到boos
//		String[] vs=message.split("#");
//		for (int i = 0; i < vs.length; i++) {
//			if (vs[i].equals("1001")||vs[i].equals("1002")||vs[i].equals("1007")||vs[i].equals("1008")) {
//				SceneUtil.additionalScene(Integer.parseInt(vs[i]));	
//			}else {
//				Boos boos = GameServer.boosesMap.get(vs[i]);
//				if (boos!=null) {
//					MonsterUtil.refreshMonsters(boos,loginResult.getRolename());
//				}	
//			}
//		}	
	}
}
