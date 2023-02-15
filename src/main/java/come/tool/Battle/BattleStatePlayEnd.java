package come.tool.Battle;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.ZZS.ZZSScene;

/**3战斗播放结束同步*/
public class BattleStatePlayEnd implements BattleState{
	
	@Override
	public boolean handle(BattleData battleData) {
		
		int type=battleData.getBattlefield().endFighting(1);	
		if (type!=-1) {
			battleData.setWinCamp(type);
			return true;
		}else if (battleData.getBattleType()==31) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(battleData.getTeam1()[0]);
			LoginResult log1=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			ctx=GameServer.getRoleNameMap().get(battleData.getTeam2()[0]);
			LoginResult log2=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log1!=null||log2!=null) {
				ZZSScene zzsScene=null;
				if (log1!=null) {zzsScene=SceneUtil.getZZS(log1);}
				else if (log2!=null) {zzsScene=SceneUtil.getZZS(log2);}
				if (zzsScene!=null&&zzsScene.getI()!=2) {
					return true;
				}
			}
		}
		// 获得战斗人
		List<String> fightList =battleData.getParticipantlist();
        battleData.setRound(battleData.getRound()+1);
		battleData.changeState(BattleState.HANDLE_POLICY);
		/**回合结束 广播进入决策*/
		String msg= Agreement.FightingRoundEndAgreement(battleData.getBattleNumber()+"");
		for (String roleName : fightList){
			SendMessage.sendMessageByRoleName(roleName,msg);   				
		}
		return false;
	}
      
}
