package come.tool.Battle;

/**2等待战斗播放*/
public class BattleStatePlay implements BattleState{

	@Override
	public boolean handle(BattleData battleData) {
		// TODO Auto-generated method stub
		//播放的指令数
	
		long Pass=System.currentTimeMillis()-battleData.getBattletime();
		if (Pass<battleData.getPlayTime()) {//最短播放时间
			return false;
		}else if (Pass>battleData.getPlayTime()*6) {//最长播放时间
			battleData.changeState(BattleState.HANDLE_PLAY_END);
			return false;
		}
		if (battleData.getCalculator()!=0) {//已有播放结束
			int size=battleData.getParticipantlist().size();
			if (battleData.getCalculator()>=size) {//全部播放结束
				battleData.changeState(BattleState.HANDLE_PLAY_END);
			}else {
				Pass+=battleData.getBattletime()-battleData.getPlayEndTime();//离第一个播放结束时间差
				if (Pass>BattleThread.OVERTIME_PLAY_END) {
					battleData.changeState(BattleState.HANDLE_PLAY_END);
				}
			}	
		}
		return false;
	}

}
