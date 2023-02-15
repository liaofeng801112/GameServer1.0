package come.tool.FightingLingAction;

public enum LingActionType {

	/**0  none*/
	Null(null),
	/**1  金虹贯日-果上随缘-刹那光电-疾风骤雨 烧法*/
	Directinjury(new Directinjury()),
	/**2   急杀令 断后斩*/
	Discharge(new Discharge()),
	/**3   回血*/
	Blood(new Blood()),	
	/**4 落宝 */
	LuoBao(new LuoBao()),
	/**5 精神错乱*/
	Insane(new Insane()),
	/**6 收队*/
	Retreat(new Retreat()),	
	/**7 阴阳逆转*/
	YinYang(new YinYang()),	
	;
	
	 private LingAction action;
	 private LingActionType (LingAction action){
			this.action = action;
	 }
	 public LingAction getTarget() {
			return action;
	}
	public static LingAction getActionById(int actionId){
		LingActionType[] values = LingActionType.values();
		LingActionType actionType = values[actionId];
		return actionType.getTarget();
	}
}
