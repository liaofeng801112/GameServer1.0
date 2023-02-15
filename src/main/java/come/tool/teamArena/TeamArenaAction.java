package come.tool.teamArena;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import come.tool.newTeam.TeamBean;
import come.tool.newTeam.TeamUtil;

public class TeamArenaAction implements IAction{
	//后端发前端
	//D 关闭关闭  提示标语                                                                 String
	//O 打开界面  出现10秒倒计时  如果都没全部同意 就退出    List<TeamRole> teams
	//A 修改状态                                                                                   String	
	//E 获得匹配队伍信息  并进入5秒倒计时                                  List<TeamRole> teams
	//3 + 7 入门/进阶/精锐/英杰/豪侠/宗师/武圣/王者
	//前端发后端
	//O 申请匹配
	//A 同意
	//D 取消
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		if (TeamArenaUtil.teamArenaThread==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("匹配通道未开启!!!#R开放时间为:19:00-20:00"));
			return;
		}
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {return;}
		TeamBean bean=TeamUtil.getTeam(roleInfo.getTroop_id());
		if (bean==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还没有队伍无法参与"));
			return;
		}
		if (message.equals("O")) {//申请匹配
			if (!bean.isCaptian(roleInfo.getRole_id())) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你不是队长"));
				return;
			}
			TeamArenaUtil.addAffirm(ctx,bean);
		}else if (message.equals("A")) {//同意
			TeamArenaUtil.confirm(bean,roleInfo,true);
		}else if (message.equals("D")) {//取消
			TeamArenaUtil.confirm(bean,roleInfo,false);
		}
	}
}
