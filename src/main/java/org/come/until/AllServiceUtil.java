package org.come.until;

import org.come.service.*;
import org.come.serviceImpl.*;
import org.come.service.MeridiansService;
/**
 * 数据库操作类
 * @author Administrator
 *
 */
public class AllServiceUtil {
	// 宝宝操作
	private static IBabyService babyService;
	
	// 好友
	private static IFriendService friendService;
	
	// 好友信息
	private static IFriendtableService friendtableService;
	
	// 帮派申请表
	private static IGangapplyService gangapplyService;
	
	// 帮派表
	private static IGangService gangService;
	
	// 物品表
	private static IGoodsTableService goodsTableService;
	
	// 灵宝
	private static ILingbaoService lingbaoService;
	
	// 坐骑
	private static IMountService mountService;
	
	// 坐骑技能
	private static IMountskillService mountskillService;
	
	// 召唤兽
	private static IRoleSummoningService roleSummoningService;
	
	// 人物信息
	private static IRoleTableService roleTableService;
	
	// 种族
	private static ISpeciesService speciesService;
	
	// 称谓
	private static ITitletableService titletableService;
	
	// 用户信息
	private static IUserTableService userTableService;
	
	//充值信息插入数据库供用户查询使用
	private static ExpensesReceiptsService expensesReceiptsService;
	//帮派战斗记录操作
	private static GangBattleService gangBattleService;
	
	private static IGoodsrecordService goodsrecordService;
	
	private static IRewardHallMallService rewardHallMallService;
	
	// 背包物品
	private static IPackRecordService packRecordService;
	
	// 兑换码兑换
	private static IGoodsExchangeService goodsExchangeService;
	
	// 黑名单
	private static IHatersService hatersService;
	
	// 聊天记录
	private static IWechatrecordService wechatrecordService;
	
	// 藏宝阁商品
	private static ISalegoodsService salegoodsService;
	
	// 收藏
	private static ICollectionService collectionService;
	
	// 消息
	private static IMessageService messageService;
	
	// 订单
	private static IRoleorderService roleorderService;
	
	// 人物物品查询
	private static GoodsRoleUsertService goodsRoleUsertService;
	
	//IP封禁
	private static IpaddressmacService ipaddressmacService;
	//礼包
	private static ChongjipackServeice chongjipackServeice;
	//vip
	private static PayvipBeanServer payvipBeanServer;
	//服务器信息记录
	private static RecordService recordService;

	/**伙伴*/
	private static PalService palService;
	/**销售记录*/
	private static BuyCountServeice buyCountServeice;
	/** HGC-2019-11-19 */
	/** 重要物资汇总记录 */
	private static IImportantgoodtrcordService importantgoodtrcordService;
	
	private static AppVersionService appVersionService;
	private static OpenareatableService openareatableService;
	private static RegionService regionService;
	
	//单人竞技战报表
	private static OneArenaNotesService oneArenaNotesService;
	private static OneArenaRoleService  oneArenaRoleService;
	//经脉
	private static MeridiansService  	meridiansService;
	private AllServiceUtil(){
		
	}
	/**
	 * 初始化所有的操作类
	 * @return
	 */
	public static void initServices(){
		babyService = new BabyServiceImpl();
		friendService = new FriendServiceImpl();
		friendtableService = new FriendtableServiceImpl();
		gangapplyService = new GangapplyServiceImpl();
		gangService = new GangServiceImpl();
		goodsTableService = new GoodsTableServiceImpl();
		lingbaoService = new LingbaoServiceImpl();
		mountService = new MountServiceImpl();
		mountskillService = new MountskillServiceImpl();
		roleSummoningService = new RoleSummoningServiceImpl();
		roleTableService = new RoleTableServiceImpl();
		speciesService = new SpeciesServiceImpl();
		titletableService = new TitleServiceImpl();
		userTableService = new UserTableServiceImpl();
		expensesReceiptsService=new ExpensesReceiptsServiceImpl();
		gangBattleService=new GangBattleServiceImpl();
		goodsrecordService = new GoodsrecordServiceImpl();
		rewardHallMallService = new RewardHallMallServiceImpl();
		packRecordService = new PackRecordServiceImpl();
		goodsExchangeService = new GoodsexchangeServiceImpl();
		hatersService = new HatersServiceImpl();
		wechatrecordService = new WechatrecordServiceImpl();
		salegoodsService = new SalegoodsServiceImpl();
		collectionService = new CollectionServiceImpl();
		messageService = new MessageServiceImpl();
		roleorderService = new RoleorderServiceImpl();
		// 2019-07-03
		goodsRoleUsertService = new GoodsRoleUsertServiceImpl();
		ipaddressmacService=new IpaddressmacImpl();
		recordService=new RecordServiceImpl();
		chongjipackServeice = new ChongjipackServeiceImpl();
		payvipBeanServer = new PayvipBeanServerImpl();
		/**2019-11-19*/
		importantgoodtrcordService = new ImportantgoodtrcordImpl();
		
		palService=new PalServiceImpl();
		buyCountServeice=new BuyCountServiceImpl();
		
		regionService = new RegionServiceImpl();
		openareatableService = new OpenareatableServiceImpl();
		appVersionService = new AppVersionServiceImpl();
		
		oneArenaNotesService=new OneArenaNotesServiceImpl();
		oneArenaRoleService =new OneArenaRoleServiceImpl();
		meridiansService = new MeridiansServiceImpl();//经脉重要之地
    }
	public static IBabyService getBabyService() {
		return babyService;
	}
	public static PayvipBeanServer getPayvipBeanServer() {
		return payvipBeanServer;
	}
    public static void setBabyService(IBabyService babyService) {
		AllServiceUtil.babyService = babyService;
	}
	public static IFriendService getFriendService() {
		return friendService;
	}
	public static void setFriendService(IFriendService friendService) {
		AllServiceUtil.friendService = friendService;
	}
	public static IFriendtableService getFriendtableService() {
		return friendtableService;
	}
	public static void setFriendtableService(IFriendtableService friendtableService) {
		AllServiceUtil.friendtableService = friendtableService;
	}
	public static IGangapplyService getGangapplyService() {
		return gangapplyService;
	}
	public static void setGangapplyService(IGangapplyService gangapplyService) {
		AllServiceUtil.gangapplyService = gangapplyService;
	}
	public static IGangService getGangService() {
		return gangService;
	}
	public static void setGangService(IGangService gangService) {
		AllServiceUtil.gangService = gangService;
	}
	public static IGoodsTableService getGoodsTableService() {
		return goodsTableService;
	}
	public static void setGoodsTableService(IGoodsTableService goodsTableService) {
		AllServiceUtil.goodsTableService = goodsTableService;
	}
	public static ILingbaoService getLingbaoService() {
		return lingbaoService;
	}
	public static void setLingbaoService(ILingbaoService lingbaoService) {
		AllServiceUtil.lingbaoService = lingbaoService;
	}
	public static IMountService getMountService() {
		return mountService;
	}
	public static void setMountService(IMountService mountService) {
		AllServiceUtil.mountService = mountService;
	}
	public static IMountskillService getMountskillService() {
		return mountskillService;
	}
	public static void setMountskillService(IMountskillService mountskillService) {
		AllServiceUtil.mountskillService = mountskillService;
	}
	public static IRoleSummoningService getRoleSummoningService() {
		return roleSummoningService;
	}
	public static void setRoleSummoningService(
			IRoleSummoningService roleSummoningService) {
		AllServiceUtil.roleSummoningService = roleSummoningService;
	}
	public static IRoleTableService getRoleTableService() {
		return roleTableService;
	}
	public static void setRoleTableService(IRoleTableService roleTableService) {
		AllServiceUtil.roleTableService = roleTableService;
	}
	public static ISpeciesService getSpeciesService() {
		return speciesService;
	}
	public static void setSpeciesService(ISpeciesService speciesService) {
		AllServiceUtil.speciesService = speciesService;
	}
	public static ITitletableService getTitletableService() {
		return titletableService;
	}
	public static void setTitletableService(ITitletableService titletableService) {
		AllServiceUtil.titletableService = titletableService;
	}

	public static IUserTableService getUserTableService() {
		return userTableService;
	}

	public static void setUserTableService(IUserTableService userTableService) {
		AllServiceUtil.userTableService = userTableService;
	}

	public static ExpensesReceiptsService getExpensesReceiptsService() {
		return expensesReceiptsService;
	}

	public static void setExpensesReceiptsService(
			ExpensesReceiptsService expensesReceiptsService) {
		AllServiceUtil.expensesReceiptsService = expensesReceiptsService;
	}

	public static GangBattleService getGangBattleService() {
		return gangBattleService;
	}

	public static ChongjipackServeice getChongjipackServeice(){
		return chongjipackServeice;
	}
	
	public static void setGangBattleService(GangBattleService gangBattleService) {
		AllServiceUtil.gangBattleService = gangBattleService;
	}

	public static IGoodsrecordService getGoodsrecordService() {
		return goodsrecordService;
	}

	public static void setGoodsrecordService(IGoodsrecordService goodsrecordService) {
		AllServiceUtil.goodsrecordService = goodsrecordService;
	}

	public static IRewardHallMallService getRewardHallMallService() {
		return rewardHallMallService;
	}

	public static void setRewardHallMallService(
			IRewardHallMallService rewardHallMallService) {
		AllServiceUtil.rewardHallMallService = rewardHallMallService;
	}
	public static IPackRecordService getPackRecordService() {
		return packRecordService;
	}
	public static void setPackRecordService(IPackRecordService packRecordService) {
		AllServiceUtil.packRecordService = packRecordService;
	}
	public static IGoodsExchangeService getGoodsExchangeService() {
		return goodsExchangeService;
	}
	public static void setGoodsExchangeService(IGoodsExchangeService goodsExchangeService) {
		AllServiceUtil.goodsExchangeService = goodsExchangeService;
	}
	public static IHatersService getHatersService() {
		return hatersService;
	}
	public static void setHatersService(IHatersService hatersService) {
		AllServiceUtil.hatersService = hatersService;
	}
	public static IWechatrecordService getWechatrecordService() {
		return wechatrecordService;
	}
	public static void setWechatrecordService(IWechatrecordService wechatrecordService) {
		AllServiceUtil.wechatrecordService = wechatrecordService;
	}
	public static ISalegoodsService getSalegoodsService() {
		return salegoodsService;
	}
	public static void setSalegoodsService(ISalegoodsService salegoodsService) {
		AllServiceUtil.salegoodsService = salegoodsService;
	}
	public static ICollectionService getCollectionService() {
		return collectionService;
	}
	public static void setCollectionService(ICollectionService collectionService) {
		AllServiceUtil.collectionService = collectionService;
	}
	public static IMessageService getMessageService() {
		return messageService;
	}
	public static void setMessageService(IMessageService messageService) {
		AllServiceUtil.messageService = messageService;
	}
	public static IRoleorderService getRoleorderService() {
		return roleorderService;
	}
	public static void setRoleorderService(IRoleorderService roleorderService) {
		AllServiceUtil.roleorderService = roleorderService;
	}
	public static GoodsRoleUsertService getGoodsRoleUsertService() {
		return goodsRoleUsertService;
	}
	public static void setGoodsRoleUsertService(GoodsRoleUsertService goodsRoleUsertService) {
		AllServiceUtil.goodsRoleUsertService = goodsRoleUsertService;
	}
	public static IpaddressmacService getIpaddressmacService() {
		return ipaddressmacService;
	}
	public static void setIpaddressmacService(
			IpaddressmacService ipaddressmacService) {
		AllServiceUtil.ipaddressmacService = ipaddressmacService;
	}
	public static RecordService getRecordService() {
		return recordService;
	}
	public static void setRecordService(RecordService recordService) {
		AllServiceUtil.recordService = recordService;
	}
	public static IImportantgoodtrcordService getImportantgoodtrcordService() {
		return importantgoodtrcordService;
	}
	public static void setImportantgoodtrcordService(
			IImportantgoodtrcordService importantgoodtrcordService) {
		AllServiceUtil.importantgoodtrcordService = importantgoodtrcordService;
	}
	public static PalService getPalService() {
		return palService;
	}
	public static void setPalService(PalService palService) {
		AllServiceUtil.palService = palService;
	}
	public static BuyCountServeice getBuyCountServeice() {
		return buyCountServeice;
	}
	public static void setBuyCountServeice(BuyCountServeice buyCountServeice) {
		AllServiceUtil.buyCountServeice = buyCountServeice;
	}
	public static AppVersionService getAppVersionService() {
		return appVersionService;
	}
	public static void setAppVersionService(AppVersionService appVersionService) {
		AllServiceUtil.appVersionService = appVersionService;
	}
	public static OpenareatableService getOpenareatableService() {
		return openareatableService;
	}
	public static void setOpenareatableService(OpenareatableService openareatableService) {
		AllServiceUtil.openareatableService = openareatableService;
	}
	public static RegionService getRegionService() {
		return regionService;
	}
	public static void setRegionService(RegionService regionService) {
		AllServiceUtil.regionService = regionService;
	}
	public static OneArenaNotesService getOneArenaNotesService() {
		return oneArenaNotesService;
	}
	public static void setOneArenaNotesService(OneArenaNotesService oneArenaNotesService) {
		AllServiceUtil.oneArenaNotesService = oneArenaNotesService;
	}
	public static OneArenaRoleService getOneArenaRoleService() {
		return oneArenaRoleService;
	}
	public static void setOneArenaRoleService(OneArenaRoleService oneArenaRoleService) {
		AllServiceUtil.oneArenaRoleService = oneArenaRoleService;
	}
	public static MeridiansService getMeridiansService() {
		return meridiansService;
	}
}
