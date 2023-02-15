package come.tool.FightingSpellAction;

public enum SpellActionType {
	/**0  none*/
	Null(null),
	/**1  控制类法术*/
	Control(new ControlAction()),
	/**2 强力师门技能 */
	TYNum(new TYSkillAction()),
	/**3  生死相许 */
	TY_HS_SSXX(new TY_HS_SSXXAction()),
	/**4 震慑*/
    ZS(new ZSAction()),
	/**5 增益类法术*/
	Gain(new GainAction()),
	/**6 惊才艳艳*/
	TY_LL_JCYY(new TY_LL_JCYYAction()),
	/**7 弱水三千*/
	TY_MH_RSSQ(new TY_MH_RSSQAction()),
	/**8 三尸虫法术*/
	SSC(new SSCAction()),
	/**9 知盈处虚*/
	TY_FY_ZYCX(new TY_FY_ZYCXAction()),
	/**10 天演策给自己加持状态*/
	TY_MY(new TY_MYAction()),
	/**11 伤害性法术*/
	Hurt(new HurtAction()),
	/**12 疾风迅雷法术*/
	TY_L_JFXL(new TY_L_JFXLAction()),
	/**13 势如破竹*/
	BB_SRPZ(new BB_SRPZAction()),	
	/**14 作鸟兽散*/
	BB_ZNSS(new BB_ZNSSAction()),	
	/**15 忽如一夜*/
	BB_HRYY(new BB_HRYYAction()),	
	/**16 患难与共*/
	BB_HNYG(new BB_HNYGAction()),	
	/**17 黑夜蒙蔽*/
	BB_HYMB(new BB_HYMBAction()),	
	/**18 无极*/
	BB_WJ(new BB_WJAction()),	
	/**19 横扫四方*/
	BB_HSSF(new BB_HSSFAction()),	
	/**20 霹雳*/
	L_PLCBFY(new L_PLCBFYAction()),	
	/**21 甘霖*/
	L_GL(new L_GLAction()),	
	/**22 逆鳞*/
	L_LL(new L_LLAction()),	
	/**23 灵魂封魔*/
	FengMo(new FengMoAction()),
	//24 法门 -回合前施加状态
	FM_QX(new FM_QXAction()),
	//25 法门-对敌方施加状态
	FM_ZJZTAction(new FM_ZJZTAction()),
	TJ_TLHZAction(new TJ_TLHZAction()),
	TJ_ZTBRAction(new TJ_ZTBRAction()),
	TJ_DNTGAction(new TJ_DNTGAction()),
	TJ_WJGZAction(new TJ_WJGZAction()),
	XP_JJSSAction(new XP_JJSSAction()),
	;
	private SpellAction action;
	private SpellActionType (SpellAction action){
	  	this.action = action;
	}
	public SpellAction getTarget() {
		return action;
	}
	public static SpellAction getActionById(int actionId){
		SpellActionType[] values = SpellActionType.values();
		SpellActionType actionType = values[actionId];
		return actionType.getTarget();
	}
	
}
