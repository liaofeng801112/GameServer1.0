package come.tool.FightingDataAction;
/**
 *   0正常 1普通攻击 2法术 3逃跑   4药 5召回 召唤 6灵宝 7捕捉 8混乱
 * @author Administrator
 *
 */
public enum  DataActionType {

	/**0  none*/
	Null(null),
	/**1  普通攻击*/
	PhyAttack(new PhyAttack()),
	/**2  法术攻击*/
	Spell(new Spell()),
	/**3 逃跑*/
	Escape(new Escape()),
	/**4 药*/
	Yao(new Yao()),
	/**5 召唤召回*/
	PetZhZh(new PetZhZh()),
	/**6 灵宝攻击*/
	Lingbao(new Lingbao()),
	/**7 宝宝捕获*/
	Catch(new Catch()),
	/**8混乱处理*/
	Confusion(new Confusion()),
	/**9 遗产*/
	Heritage(new Heritage()),
	/**10 将死*/
	Mate(new Mate()),
	/**11三尸虫处理(失效)*/
	Worm(null),
	/**12魔界内丹处理*/
	MoJindan(new MoJindan()),
	/**13作鸟兽散处理*/
	PetFlee(new PetFlee()),
	/**14化无处理一 */
	NoSkillOne(new NoSkillOne()),
	/**15化无处理二 */
	NoSkillTwo(new NoSkillTwo()),
	/**16兵临城下处理 */
	ZiCang(new ZiCang()),
	/**17涅槃处理 */
	Nirvana(new Nirvana()),
	/**18移花接木处理 */
	Transfer(new Transfer()),
	/**19化羽归尘后续处理 */
	PoisonNeedles(new Huayu()),
	/**20分魂(孟极)处理 */
	SplitSoul(new SplitSoul()),
	/**21亢龙有悔(北冥龙君)处理 */
	Kanglong(new Kanglong()),
	/**22法宝处理 */
	Fabao(new Fabao()),
	/**23化蝶处理 */
	Huadie(new Huadie()),
	/**24法术连击处理 (失效)*/
	Falian(null),
	/**25偷钱处理 */
	Stealing(new Stealing()),
	/**26同归于尽 */
	DieGroup(new DieGroup()),
	/**27一视同仁 */
	Equal(new Equal()),
	/**28破血狂攻 */
	PXKG(new PXKG()),
	/**29如虎添翼 */
	BB_RHTY(new BB_RHTY()),
	/**30以牙还牙 */
	BB_YAHY(new BB_YAHY()),
	/**31天地同寿 */
	BB_TDTS(new BB_TDTS()),
	/**32初始化状态添加 */
	InitState(new InitState()),
	/**33扭转乾坤 */
	BB_NZQK(new BB_NZQK()),
	/**34天降流火(莲生)处理 */
	LianSheng(new LianSheng()),
	/**35归去来兮(灵听)处理 */
	LingTing(new LingTing()),
	/**36一矢中的（灵犀）处理 */
	YiShiZhongDi(new YiShiZhongDi()),
	/**37法门-物理攻击处理 */
	FM_PTGJ(new FM_PTGJ()),
	;
	private DataAction action;

	private DataActionType (DataAction action){
		this.action = action;
	}
	public DataAction getTarget() {
		return action;
	}
	public static DataAction getActionById(int actionId){
		DataActionType[] values = DataActionType.values();
		DataActionType actionType = values[actionId];
		return actionType.getTarget();
	}
}
