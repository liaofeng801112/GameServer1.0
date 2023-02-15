package come.tool.Battle;

/**
 * 战斗状态类型
 * @author Administrator
 *
 */
public enum BattleStateType {
	
	/**0战斗预知*/
	preview(new BattleStatePreview()),
	/**1战斗决策*/
	Policy(new BattleStatePolicy()),
	/**2等待战斗播放*/
	Play(new BattleStatePlay()),
	/**3战斗播放结束同步*/
	PlayEnd(new BattleStatePlayEnd());
	;
	private BattleState battleState;

	private BattleStateType(BattleState battleState) {
		this.battleState = battleState;
	}
    
	public BattleState getBattleState() {
		return battleState;
	}
	public static BattleState getBattleStateById(int battleStateId){
		BattleStateType[] values = BattleStateType.values();
		BattleStateType battleState = values[battleStateId];
		return battleState.getBattleState();
	}
}
