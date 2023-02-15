package come.tool.Battle;

import java.util.List;

import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingManData;
import come.tool.FightingData.FightingRound;

/**
 * 战斗决策
 * @author Administrator
 *
 */
public class BattleStatePolicy implements BattleState{

	@Override
	public boolean handle(BattleData battleData) {
		// TODO Auto-generated method stub
		//已接收决策数
		int size1=battleData.getRoundPolicy(battleData.getRound()).size();
		//需要的决策数		
		int size2=battleData.getBattlefield().noyesum();
		long Pass=System.currentTimeMillis()-battleData.getBattletime();
		if (size1>=size2||Pass>BattleThread.OVERTIME_POLICY) {
			List<FightingEvents> events=battleData.getBattlefield().ReceiveEvent();		
			battleData.setPlayTime(events.size()*BattleThread.OVERTIME_PLAY_MIN);
			battleData.getBattlefieldPlay().put(battleData.getRound(), events);
			/**处理后 同步播放数据*/
		    List<FightingManData> playdatas=battleData.getBattlefield().Transformation();
			battleData.getPlayDatas().put(battleData.getRound()+1, playdatas);	
			/**将播放数据发给参与者*/
			FightingRound fightingRound=new FightingRound();
		    fightingRound.setRound(events);
			fightingRound.setCurrentRound(battleData.getRound());        
			fightingRound.setFightingsumber(battleData.getBattleNumber());	
			String msg=Agreement.FightingRoundDealAgreement(GsonUtil.getGsonUtil().getgson().toJson(fightingRound));
			for (String string : battleData.getParticipantlist()){
				SendMessage.sendMessageByRoleName(string,msg);
			}
			battleData.changeState(BattleState.HANDLE_PLAY);
		}
		return false;
	}
}
