package org.come.action.chat;

import io.netty.channel.ChannelHandlerContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.come.action.IAction;
import org.come.bean.ChatBean;
import org.come.bean.LoginResult;
import org.come.entity.RoleTable;
import org.come.entity.Wechatrecord;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.servlet.UserControlServlet;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**好友聊天*/
public class FriendChatAction implements IAction{
	//好友信件聊天信息bean
	private static ChatBean chatBean=new ChatBean();

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
//		if (true) {
//			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("禁止发言"));		
//			return;
//		}
		if(UserControlServlet.isNoTalk(ctx)) return;
		ChatBean chatBean = GsonUtil.getGsonUtil().getgson().fromJson(message, ChatBean.class);
		// 获得聊天内容
		String msg = Agreement.getAgreement().friendchatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
		// 获取对方信息
		RoleTable friend = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(chatBean.getFriendName());
		LoginResult friend1=null;
		ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(chatBean.getFriendName());
		if (ctx2!=null) {
			friend1=GameServer.getAllLoginRole().get(ctx2);
		}else {
			friend = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(chatBean.getFriendName());
		}
		// 获取自己信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo.getGrade()<=102&&roleInfo.getPaysum().intValue()<100) {
			SendMessage.sendMessageToSlef(ctx,ChatAction.MSG);				
			return;
		}
		// 添加聊天记录
		Wechatrecord wechatrecord = new Wechatrecord();
		wechatrecord.setChatGetid(friend1!=null?friend1.getRole_id():friend.getRole_id());
		wechatrecord.setChatMes(chatBean.getMessage());
		wechatrecord.setChatSendid(roleInfo.getRole_id());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdayTime = dateFormat.format(new Date());
		wechatrecord.setTime(nowdayTime);
		AllServiceUtil.getWechatrecordService().insert(wechatrecord);
		// 根据好友名字获取对方输出流，发送消息
		if(( GameServer.getRoleNameMap().get(chatBean.getFriendName()) != null )&&(chatBean.getFriendName()!=null)){
			// 获得该用户的输出流发送聊天内容
			SendMessage.sendMessageByRoleName(chatBean.getFriendName(), msg);
		}
	}
	/**
	 * 用系统好友发送好友信件给玩家
	 */
	public static void useServerFriendToRoleForClient(ChatBean chatBean){
		// 获得聊天内容
		String msg = Agreement.getAgreement().friendchatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
		// 获得该用户的输出流发送聊天内容
		SendMessage.sendMessageByRoleName(chatBean.getFriendName(), msg);

	}
	/**
	 * 服务器发送系统好友信件专用类，传入发送的信息，返回给好友信件bean类进行传送
	 * 
	 * 聊天的信息，接收聊天信息的角色
	 */
	public static void createChatBeanForServer(String mes,String rolename){
		//设置发送者的信息
		chatBean.setRolename("大话精灵");
		//接收者的信息
		chatBean.setFriendName(rolename);
		//设置聊天信息
		chatBean.setMessage(mes);
		//设置当前时间搓
		chatBean.setTime(System.currentTimeMillis());
		useServerFriendToRoleForClient(chatBean);
	}
}
