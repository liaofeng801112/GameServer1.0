package come.tool.Battle;

public interface BattleState {
	//数据处理
	boolean handle(BattleData battleData);
	/**0 战斗预知*/
	public static final int HANDLE_PREVIEW = 0;
	/**1 战斗决策*/
	public static final int HANDLE_POLICY = 1;
	/**2 战斗播放*/
	public static final int HANDLE_PLAY = 2;
	/**3 战斗播放结束同步*/
	public static final int HANDLE_PLAY_END = 3;
}
