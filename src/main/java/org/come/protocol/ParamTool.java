package org.come.protocol;

import java.util.HashMap;
import java.util.Map;
import come.tool.hjsl.HjslAction;
import org.come.action.IAction;
import org.come.action.baby.BabyAction;
import org.come.action.baby.BabyBornAction;
import org.come.action.baby.BabyCustodayAction;
import org.come.action.baby.UpdaBabyAction;
import org.come.action.bring.MakeLoveAction;
import org.come.action.bring.MarryAction;
import org.come.action.bring.UnMarryAction;
import org.come.action.buy.BuyMingChaoAction;
import org.come.action.buy.BuyShopAction;
import org.come.action.buy.NpcShopAction;
import org.come.action.buy.ShopPriceAction;
import org.come.action.chat.ChatAction;
import org.come.action.chat.FriendChatAction;
import org.come.action.chooseRole.GetRoleByQuid;
import org.come.action.chooseRole.SelectRoleByArea;
import org.come.action.exchange.ExchangeGoodsAction;
import org.come.action.festival.HatchaddAction;
import org.come.action.festival.HatchvalueAction;
import org.come.action.fight.FightQlAction;
import org.come.action.fight.FightRoundAction;
import org.come.action.fight.FightbattleConnectionAction;
import org.come.action.fight.FightingForeseeAction;
import org.come.action.fight.FightingRoundAction;
import org.come.action.fight.FightingendAction;
import org.come.action.fight.PrisonAction;
import org.come.action.fight.QuoteOutAction;
import org.come.action.friend.AddFriendAction;
import org.come.action.friend.DeleteFriendAction;
import org.come.action.friend.FriendAction;
import org.come.action.gang.GangAgreeAction;
import org.come.action.gang.GangApplyAction;
import org.come.action.gang.GangAppointAction;
import org.come.action.gang.GangBattleAction;
import org.come.action.gang.GangChangeAction;
import org.come.action.gang.GangCreateAction;
import org.come.action.gang.GangGiveMoneyAction;
import org.come.action.gang.GangListAction;
import org.come.action.gang.GangMonitorAction;
import org.come.action.gang.GangRefuseAction;
import org.come.action.gang.GangRetreatAction;
import org.come.action.gang.GangShotAction;
import org.come.action.gang.IntoGangAction;
import org.come.action.give.GiveAction;
import org.come.action.gl.EggAction;
import org.come.action.gl.FindDropAction;
import org.come.action.gl.GoodsExchangeAction;
import org.come.action.gl.LxAction;
import org.come.action.lingbao.UpdateLingAction;
import org.come.action.lottery.LotteryAction;
import org.come.action.monitor.MonitorAction;
import org.come.action.monster.AddMonsterAction;
import org.come.action.monster.ClickMonsterAction;
import org.come.action.mount.MountAction;
import org.come.action.mount.MountGetAction;
import org.come.action.mount.MountReleaseAction;
import org.come.action.mount.MountSkillDeleteAction;
import org.come.action.mount.MountSkillStuAction;
import org.come.action.mount.MountUpdateAction;
import org.come.action.npc.NPCDialogAction;
import org.come.action.npc.NpcComposeAction;
import org.come.action.npc.NpcCureAction;
import org.come.action.pack.PackChangeAction;
import org.come.action.pack.PackGiftAction;
import org.come.action.pack.PackLockAction;
import org.come.action.pack.PackRecordAction;
import org.come.action.pawn.PawnAction;
import org.come.action.pawn.RetrieveAction;
import org.come.action.phone.PhoneBangAction;
import org.come.action.phone.PhoneNumberIsNoGetAction;
import org.come.action.phone.UnPhoneBangAction;
import org.come.action.phonenumber.PhoneAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.action.reward.ObtainarticleAction;
import org.come.action.reward.ThrowinarticleAction;
import org.come.action.rich.RichMonitorAction;
import org.come.action.role.*;
import org.come.action.sale.AppointBuyAction;
import org.come.action.sale.CollectionControlAction;
import org.come.action.sale.CollectionSearchAction;
import org.come.action.sale.GoodsBackAction;
import org.come.action.sale.GoodsBuyAction;
import org.come.action.sale.GoodsControlAction;
import org.come.action.sale.GoodsSearchAction;
import org.come.action.sale.GoodsShelfAction;
import org.come.action.sale.MessageControlAction;
import org.come.action.sale.MessageSearchAction;
import org.come.action.sale.MyGoodsSearchAction;
import org.come.action.sale.MyOrderSearchAction;
import org.come.action.sale.MyWaresSearchAction;
import org.come.action.suit.QualityCAciton;
import org.come.action.suit.SuitComposeAction;
import org.come.action.summoning.PetAlchemyAction;
import org.come.action.summoning.PetChangeAction;
import org.come.action.summoning.PetInfoAction;
import org.come.action.summoning.PetReleaseAction;
import org.come.action.summoning.SummonPetAction;
import org.come.action.sys.AccountStopAction;
import org.come.action.sys.BindingMobileAction;
import org.come.action.sys.ChangeMapAction;
import org.come.action.sys.ConfirmAciton;
import org.come.action.sys.FindWayAction;
import org.come.action.sys.LoginAction;
import org.come.action.sys.MiddleAction;
import org.come.action.sys.OrderByRoleAction;
import org.come.action.sys.RegisterAction;
import org.come.action.sys.enterGameAction;
import org.come.action.title.TitleChangeAction;
import org.come.action.title.TitleListAction;
import org.come.action.vip.ChongJiPackGetAction;
import org.come.action.vip.ChongJiPackOpenAction;
import org.come.action.vip.ChongJiPackSureAction;
import org.come.action.vip.DayForOneGetAction;
import org.come.action.vip.DayForOneSureAction;
import org.come.action.vip.DayForWeekGradeGetAction;
import org.come.action.vip.DayForWeekGradeSureAction;
import org.come.action.vip.GetVipGradePackAction;
import org.come.action.vip.PayDayGradePayAction;
import org.come.action.vip.PayDayGradeSureAction;
import org.come.action.vip.VipGradeSureAction;
import org.come.action.wechat.SearcahChatRoleIdAction;
import org.come.action.wechat.SearcahChatRoleNameAction;
import org.come.action.wechat.SearchChatRecordeAction;
import org.come.extInterface.SaleGoodsStatues;
import org.come.extInterface.SalesGoodsChangeOrder;
import org.come.extInterface.sale.GoodsBuy;

import come.tool.Good.AddGoodAction;
import come.tool.Good.UseBabyAction;
import come.tool.Good.UseCardAction;
import come.tool.Good.UseLingAction;
import come.tool.Good.UseMountAction;
import come.tool.Good.UsePalAction;
import come.tool.Good.UsePetAction;
import come.tool.Good.UseRoleAction;
import come.tool.PK.BookofchalgAction;
import come.tool.PK.RefusechalgAction;
import come.tool.Scene.LaborAction;
import come.tool.Scene.SceneAction;
import come.tool.Scene.VIconAction;
import come.tool.Stall.StallAction;
import come.tool.Stall.StallBuyAction;
import come.tool.Stall.StallGetAction;
import come.tool.newTask.TaskAction;
import come.tool.newTeam.TeamApplyAction;
import come.tool.newTeam.TeamApplyListAction;
import come.tool.newTeam.TeamCreateAction;
import come.tool.newTeam.TeamEnlistAction;
import come.tool.newTeam.TeamOperateAction;
import come.tool.newTeam.TeamStateAction;
import come.tool.newTrans.TransGoodAction;
import come.tool.newTrans.TransStateAction;
import come.tool.oneArena.OneArenaAction;
import come.tool.teamArena.TeamArenaAction;

/**
 * 收到协议
 *
 * @author 叶豪芳
 * @date : 2017年11月29日 下午4:36:04
 */
public class ParamTool {

	public static final Map<String, IAction> ACTION_MAP = new HashMap<String, IAction>();
	// 不加密的接收
	public static final Map<String, IAction> ACTION_MAP2 = new HashMap<String, IAction>();

	public static void handles() {
		// 用户登入
		IAction action1 = new LoginAction();
		ACTION_MAP.put(AgreementUtil.login, action1);
		// 注册用户
		IAction action2 = new RegisterAction();
		ACTION_MAP.put(AgreementUtil.register, action2);
		// 创建角色
		IAction action3 = new CreateRoleAction();
		ACTION_MAP.put(AgreementUtil.createrole, action3);
		// 进入帮派
		IAction action10 = new IntoGangAction();
		ACTION_MAP.put(AgreementUtil.intogang, action10);
		// 角色移动
		IAction action11 = new RoleMoveAction();
		ACTION_MAP.put(AgreementUtil.move, action11);
		// // 点击NPC
		// IAction action12 = new NpcPointAction();
		// ACTION_MAP.put(AgreementUtil.npc, action12);
		// // 召唤兽
		// IAction action14 = new SummoningAction();
		// ACTION_MAP.put(AgreementUtil.pet, action14);
		// 坐骑
		IAction action15 = new MountAction();
		ACTION_MAP.put("mount", action15);
		// 关闭背包返回
		IAction action17 = new PackChangeAction();
		ACTION_MAP.put(AgreementUtil.packchange, action17);
		// 好友列表
		IAction action19 = new FriendAction();
		ACTION_MAP.put(AgreementUtil.friend, action19);
		// 添加好友
		IAction action20 = new AddFriendAction();
		ACTION_MAP.put(AgreementUtil.addfriend, action20);
		// 删除好友
		IAction action21 = new DeleteFriendAction();
		ACTION_MAP.put(AgreementUtil.deletefriend, action21);
		// 好友聊天
		IAction action22 = new FriendChatAction();
		ACTION_MAP.put(AgreementUtil.friendchat, action22);
		// 扣除银两
		IAction action23 = new DeductiontaelAction();
		ACTION_MAP.put(AgreementUtil.deductiontael, action23);
		// 传送地图
		IAction action28 = new ChangeMapAction();
		ACTION_MAP.put(AgreementUtil.changemap, action28);
		// 创建帮派
		IAction action34 = new GangCreateAction();
		ACTION_MAP.put(AgreementUtil.gangcreate, action34);
		// 申请加入帮派
		IAction action35 = new GangApplyAction();
		ACTION_MAP.put(AgreementUtil.gangapply, action35);
		// 退出帮派
		IAction action36 = new GangRetreatAction();
		ACTION_MAP.put(AgreementUtil.gangretreat, action36);
		// 帮派列表
		IAction action37 = new GangListAction();
		ACTION_MAP.put(AgreementUtil.ganglist, action37);
		// 给与NPC
		IAction action38 = new GiveAction();
		ACTION_MAP.put(AgreementUtil.give, action38);
		// 踢出帮派
		IAction action43 = new GangShotAction();
		ACTION_MAP.put(AgreementUtil.gangshot, action43);
		// 帮派允许加入
		IAction action44 = new GangAgreeAction();
		ACTION_MAP.put(AgreementUtil.gangagree, action44);
		// 帮派拒绝加入
		IAction action45 = new GangRefuseAction();
		ACTION_MAP.put(AgreementUtil.gangrefuse, action45);
		// 帮派职务任命
		IAction action46 = new GangAppointAction();
		ACTION_MAP.put(AgreementUtil.gangappoint, action46);
		// 帮派退位让贤
		IAction action47 = new GangChangeAction();
		ACTION_MAP.put(AgreementUtil.gangchange, action47);
		// 典当
		IAction action48 = new PawnAction();
		ACTION_MAP.put(AgreementUtil.pawn, action48);
		// 取回典当
		IAction action49 = new RetrieveAction();
		ACTION_MAP.put(AgreementUtil.retrieve, action49);
		// 修改宠物属性
		IAction action54 = new PetChangeAction();
		ACTION_MAP.put(AgreementUtil.petchange, action54);
		// // 携带宠物
		// IAction action55 = new PetCarryAction();
		// ACTION_MAP.put(AgreementUtil.petcarry, action55);
		// 确认更改称谓
		IAction action60 = new TitleChangeAction();
		ACTION_MAP.put(AgreementUtil.titlechange, action60);
		// 更改称谓显示列表
		IAction action61 = new TitleListAction();
		ACTION_MAP.put(AgreementUtil.titlelist, action61);
		// 背包加锁
		IAction action64 = new PackLockAction();
		ACTION_MAP.put(AgreementUtil.packlock, action64);
		// 背包礼包
		IAction action65 = new PackGiftAction();
		ACTION_MAP.put(AgreementUtil.packgift, action65);
		// 结婚
		IAction action66 = new MarryAction();
		ACTION_MAP.put(AgreementUtil.marry, action66);
		// 洞房
		IAction action68 = new MakeLoveAction();
		ACTION_MAP.put(AgreementUtil.makelove, action68);
		// 获取召唤兽信息
		IAction action69 = new PetInfoAction();
		ACTION_MAP.put(AgreementUtil.petinfo, action69);
		// npc合成
		IAction action70 = new NpcComposeAction();
		ACTION_MAP.put(AgreementUtil.npccompose, action70);
		// 宠物放生
		IAction action72 = new PetReleaseAction();
		ACTION_MAP.put(AgreementUtil.petrelease, action72);
		// 合成神兽
		IAction action73 = new SummonPetAction();
		ACTION_MAP.put(AgreementUtil.summonpet, action73);
		// 角色属性改变
		IAction action74 = new RoleChangeAction();
		ACTION_MAP.put(AgreementUtil.rolechange, action74);
		// 战斗回合
		IAction action75 = new FightRoundAction();
		ACTION_MAP.put(AgreementUtil.fightround, action75);
		// 战斗重连
		IAction action76 = new FightbattleConnectionAction();
		ACTION_MAP.put(AgreementUtil.battleconnection, action76);
		// 战斗预知
		IAction action81 = new FightingForeseeAction();
		ACTION_MAP.put(AgreementUtil.fightingforesee, action81);
		// // 战斗相应
		// IAction action82 = new FightingResponseAction();
		// ACTION_MAP.put(AgreementUtil.fightingresponse, action82);
		// 战斗结束
		IAction action90 = new FightingendAction();
		ACTION_MAP.put(AgreementUtil.fightingend, action90);
		// // 领取新手礼包
		// IAction action77 = new NpcGiftAction();
		// ACTION_MAP.put(AgreementUtil.npcgift, action77);
		// // 坐骑骑乘/休息
		// IAction action78 = new MountCarryAction();
		// ACTION_MAP.put(AgreementUtil.mountcarry, action78);
		// NPC治疗
		IAction action79 = new NpcCureAction();
		ACTION_MAP.put(AgreementUtil.npccure, action79);
		// 帮派捐钱
		IAction action80 = new GangGiveMoneyAction();
		ACTION_MAP.put(AgreementUtil.givemoney, action80);
		// 炼妖
		IAction action83 = new PetAlchemyAction();
		ACTION_MAP.put(AgreementUtil.petalchemy, action83);
		// 购买冥钞
		IAction action84 = new BuyMingChaoAction();
		ACTION_MAP.put(AgreementUtil.buymingchao, action84);
		// 战斗回合结束
		IAction action85 = new FightingRoundAction();
		ACTION_MAP.put(AgreementUtil.fightingroundend, action85);
		// 种族转换
		IAction action88 = new RoleTransAction();
		ACTION_MAP.put(AgreementUtil.racialtransformation, action88);
		// 人物升级
		IAction action92 = new RoleLevelUpAction();
		ACTION_MAP.put(AgreementUtil.rolelevelup, action92);
		// 坐骑学习技能
		IAction action93 = new MountSkillStuAction();
		ACTION_MAP.put(AgreementUtil.addmountskill, action93);
		// 坐骑遗忘技能
		IAction action94 = new MountSkillDeleteAction();
		ACTION_MAP.put(AgreementUtil.missmountskill, action94);
		// 修改坐骑的属性(根骨,灵性,力量)
		IAction action95 = new MountUpdateAction();
		ACTION_MAP.put(AgreementUtil.changemountvalue, action95);
		// 添加坐骑
		IAction action96 = new MountGetAction();
		ACTION_MAP.put(AgreementUtil.mountget, action96);
		// 获得角色宝宝
		IAction action97 = new BabyAction();
		ACTION_MAP.put(AgreementUtil.baby, action97);
		// 离婚
		IAction action98 = new UnMarryAction();
		ACTION_MAP.put(AgreementUtil.unmarry, action98);
		// 产生宝宝
		IAction action99 = new BabyBornAction();
		ACTION_MAP.put(AgreementUtil.babyborn, action99);
		// 宝宝抚养权
		IAction action101 = new BabyCustodayAction();
		ACTION_MAP.put(AgreementUtil.babycustoday, action101);
		// 灵宝操作
		IAction action102 = new UpdateLingAction();
		ACTION_MAP.put(AgreementUtil.updateling, action102);
		// 修改名字
		IAction action105 = new ChangeRoleNameAction();
		ACTION_MAP.put(AgreementUtil.changerolename, action105);
		// npc对话框
		IAction action111 = new NPCDialogAction();
		ACTION_MAP.put(AgreementUtil.npcdialog, action111);
		// 中量级
		IAction action112 = new MiddleAction();
		ACTION_MAP.put(AgreementUtil.middle, action112);
		// 送监狱
		IAction action113 = new PrisonAction();
		ACTION_MAP.put(AgreementUtil.getout, action113);
		// 引用送监狱
		IAction action114 = new QuoteOutAction();
		ACTION_MAP.put(AgreementUtil.quoteout, action114);
		// 更新孩子数据
		IAction action115 = new UpdaBabyAction();
		ACTION_MAP.put(AgreementUtil.updababy, action115);
		// 帮战npc点击
		IAction action116 = new GangMonitorAction();
		ACTION_MAP.put(AgreementUtil.gangmonitor, action116);
		// 帮战功能反应
		IAction action117 = new GangBattleAction();
		ACTION_MAP.put(AgreementUtil.gangbattle, action117);
		// 发布摆摊
		IAction action118 = new StallAction();
		ACTION_MAP.put(AgreementUtil.stall, action118);
		// 购买摆摊物品
		IAction action119 = new StallBuyAction();
		ACTION_MAP.put(AgreementUtil.stallbuy, action119);
		// 获取摊位信息
		IAction action120 = new StallGetAction();
		ACTION_MAP.put(AgreementUtil.stallget, action120);
		// 放生坐骑
		IAction action121 = new MountReleaseAction();
		ACTION_MAP.put(AgreementUtil.mountrelease, action121);
		// 添加怪物 不接收前端发送
		IAction action122 = new AddMonsterAction();
		ACTION_MAP.put(AgreementUtil.updatemonsters, action122);
		// 获取赏功堂物品
		IAction action124 = new ObtainarticleAction();
		ACTION_MAP.put(AgreementUtil.obtainarticle, action124);
		// 抽中物品
		IAction action125 = new DrawnitemsAction();
		ACTION_MAP.put(AgreementUtil.drawnitems, action125);
		// 投放赏功堂物品
		IAction action126 = new ThrowinarticleAction();
		ACTION_MAP.put(AgreementUtil.throwinarticle, action126);
		// 绑定手机号
		IAction action127 = new BindingMobileAction();
		ACTION_MAP.put(AgreementUtil.bindingmobile, action127);
		// 封号
		IAction action128 = new AccountStopAction();
		ACTION_MAP.put(AgreementUtil.accountstop, action128);
		// 排行榜
		IAction action129 = new OrderByRoleAction();
		ACTION_MAP.put(AgreementUtil.pankinglist, action129);
		// 购买物品
		IAction action130 = new BuyShopAction();
		ACTION_MAP.put(AgreementUtil.nbuy, action130);
		// 人物物品使用
		IAction action131 = new UseRoleAction();
		ACTION_MAP.put(AgreementUtil.user, action131);
		// 背包记录
		IAction action132 = new PackRecordAction();
		ACTION_MAP.put(AgreementUtil.packRecord, action132);
		// 进入游戏改
		IAction action133 = new enterGameAction();
		ACTION_MAP.put(AgreementUtil.enterGame, action133);
		// 保存系统设置
		IAction action134 = new RoleSystemAction();
		ACTION_MAP.put(AgreementUtil.roleSystem, action134);
		// 更新私密数据
		IAction action138 = new RolePrivateAction();
		ACTION_MAP.put(AgreementUtil.rolePrivate, action138);

		// 下挑战书
		IAction action139 = new BookofchalgAction();
		ACTION_MAP.put(AgreementUtil.bookofchalg, action139);

		// 拒绝别人的挑战
		IAction action140 = new RefusechalgAction();
		ACTION_MAP.put(AgreementUtil.refusechalg, action140);

		// 通灵宝卷
		IAction action141 = new LotteryAction();
		ACTION_MAP.put(AgreementUtil.getfivemsg, action141);

		// 兑换码兑换物品
		IAction action142 = new ExchangeGoodsAction();
		ACTION_MAP.put(AgreementUtil.exchangegoods, action142);
		// 套装 系列
		IAction action143 = new SuitComposeAction();
		ACTION_MAP.put(AgreementUtil.suitoperate, action143);
		// 额外属性替换
		IAction action144 = new QualityCAciton();
		ACTION_MAP.put(AgreementUtil.extrattroper, action144);
		// 加验证的添加物品
		IAction action145 = new AddGoodAction();
		ACTION_MAP.put(AgreementUtil.addgood, action145);
		// 获取孵化进度
		IAction action146 = new HatchvalueAction();
		ACTION_MAP.put(AgreementUtil.hatchvalue, action146);
		// 加孵化进度
		IAction action147 = new HatchaddAction();
		ACTION_MAP.put(AgreementUtil.hatchadd, action147);
		// 展示点击
		IAction action148 = new RichMonitorAction();
		ACTION_MAP.put(AgreementUtil.richM, action148);
		// 聊天栏
		IAction action149 = new ChatAction();
		ACTION_MAP.put(AgreementUtil.chat, action149);
		// 根据角色ID查找好友
		IAction action150 = new SearcahChatRoleIdAction();
		ACTION_MAP.put(AgreementUtil.searcahChatRoleId, action150);
		// 根据角色名查找好友
		IAction action151 = new SearcahChatRoleNameAction();
		ACTION_MAP.put(AgreementUtil.searcahChatRoleName, action151);
		IAction action152 = new SearchChatRecordeAction();
		ACTION_MAP.put(AgreementUtil.searchChatRecorde, action152);
		// 点击npc商店
		IAction action153 = new NpcShopAction();
		ACTION_MAP.put(AgreementUtil.shop, action153);
		// 我的订单搜索
		IAction action154 = new MyOrderSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch3, action154);
		// 指定购买搜索
		IAction action155 = new AppointBuyAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch4, action155);
		// 我的货物搜索
		IAction action156 = new MyGoodsSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch5, action156);
		// 消息搜索
		IAction action157 = new MessageSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch6, action157);
		// 收藏查询搜索
		IAction action158 = new CollectionSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch7, action158);
		// 藏宝阁商品下架 重新上架 取回
		IAction action159 = new GoodsControlAction();
		ACTION_MAP.put(AgreementUtil.CBGGood, action159);
		// 收藏-收藏取消
		IAction action160 = new CollectionControlAction();
		ACTION_MAP.put(AgreementUtil.CBGCollect, action160);
		// 消息操作
		IAction action161 = new MessageControlAction();
		ACTION_MAP.put(AgreementUtil.CBGMes, action161);
		// 商品搜索
		IAction action162 = new GoodsSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch1, action162);
		// 我的商品搜索
		IAction action163 = new MyWaresSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch2, action163);
		// 藏宝阁商品上架
		IAction action164 = new GoodsShelfAction();
		ACTION_MAP.put(AgreementUtil.CBGShelf, action164);
		// 获取好友的聊天记录
		IAction action165 = new SearchChatRecordeAction();
		ACTION_MAP.put(AgreementUtil.searchChatRecorde, action165);
		// 商品购买
		IAction action166 = new GoodsBuyAction();
		ACTION_MAP.put(AgreementUtil.CBGBuy, action166);
		// 我的货物取回
		IAction action167 = new GoodsBackAction();
		ACTION_MAP.put(AgreementUtil.CBGBack, action167);
		// 点击野怪
		IAction action168 = new ClickMonsterAction();
		ACTION_MAP.put(AgreementUtil.ClickMonsters, action168);
		// //掉落拆解
		// IAction action169 = new DropAction();
		// ACTION_MAP.put(AgreementUtil.drop, action169);
		// 交易状态
		IAction action170 = new TransStateAction();
		ACTION_MAP.put(AgreementUtil.TransState, action170);
		// 交易货物变化
		IAction action171 = new TransGoodAction();
		ACTION_MAP.put(AgreementUtil.TransGood, action171);
		// 召唤兽物品使用变化
		IAction action172 = new UsePetAction();
		ACTION_MAP.put(AgreementUtil.userpet, action172);
		// 检测
		IAction action173 = new MonitorAction();
		ACTION_MAP.put(AgreementUtil.Monitor, action173);
		// 坐骑物品使用变化
		IAction action174 = new UseMountAction();
		ACTION_MAP.put(AgreementUtil.usermount, action174);
		// 坐骑物品使用变化
		IAction action175 = new UseLingAction();
		ACTION_MAP.put(AgreementUtil.userling, action175);
		// 坐骑物品使用变化
		IAction action176 = new UseBabyAction();
		ACTION_MAP.put(AgreementUtil.userbaby, action176);
		// 七十二变使用变化
		IAction action177 = new UseCardAction();
		ACTION_MAP.put(AgreementUtil.usercard, action177);
		// 任务
		IAction action178 = new TaskAction();
		ACTION_MAP.put(AgreementUtil.TASKN, action178);
		// 获取战斗抗性
		IAction action179 = new FightQlAction();
		ACTION_MAP.put(AgreementUtil.fightQl, action179);

		/** HGC--2019-11-13--start */
		// 绑定手机号
		IAction action180 = new PhoneBangAction();
		ACTION_MAP.put(AgreementUtil.PhoneBang, action180);
		// 解绑手机号
		IAction action181 = new UnPhoneBangAction();
		ACTION_MAP.put(AgreementUtil.UnPhoneBang, action181);
		// 获取绑定的手机号
		IAction action182 = new PhoneNumberIsNoGetAction();
		ACTION_MAP.put(AgreementUtil.PhoneNumberIsNoGet, action182);

		/** HGC--2019-11-13--end */

		/** zzh--2019-11-21--start */
		// 获取vip列表
		IAction action183 = new GetVipGradePackAction();
		ACTION_MAP.put(AgreementUtil.Getvipgradepack, action183);
		// 兑换vip
		IAction action184 = new VipGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Vipgradesure, action184);
		// 获取每日充值列表
		IAction action185 = new PayDayGradePayAction();
		ACTION_MAP.put(AgreementUtil.Paydaygradepay, action185);
		// 兑换每日充值
		IAction action186 = new PayDayGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Paydaygradesure, action186);
		// 获取连续充值列表
		IAction action187 = new DayForWeekGradeGetAction();
		ACTION_MAP.put(AgreementUtil.Dayforweekgradeget, action187);
		// 兑换连续充值
		IAction action188 = new DayForWeekGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Dayforweekgradesure, action188);
		// 获取每日特惠列表
		IAction action189 = new DayForOneGetAction();
		ACTION_MAP.put(AgreementUtil.Dayforoneget, action189);
		// 兑换每日特惠
		IAction action190 = new DayForOneSureAction();
		ACTION_MAP.put(AgreementUtil.Dayforonesure, action190);
		// 获取冲级礼包列表
		IAction action191 = new ChongJiPackGetAction();
		ACTION_MAP.put(AgreementUtil.chongjipackget, action191);
		// 兑换冲级礼包
		IAction action192 = new ChongJiPackSureAction();
		ACTION_MAP.put(AgreementUtil.Chongjipacksure, action192);
		// 开通冲级礼包
		IAction action193 = new ChongJiPackOpenAction();
		ACTION_MAP.put(AgreementUtil.Chongjipackopen, action193);
		/** zzh--2019-11-21--end */

		//副本操作
		IAction action194 = new SceneAction();
		ACTION_MAP.put(AgreementUtil.Scene, action194);
		//伙伴相关操作
		IAction action195 = new UsePalAction();
		ACTION_MAP.put(AgreementUtil.userpal, action195);
		//获取商品价格
		IAction action196 = new ShopPriceAction();
		ACTION_MAP.put(AgreementUtil.shopPrice, action196);

		//招募
		IAction action197 = new TeamEnlistAction();
		ACTION_MAP.put(AgreementUtil.enlist, action197);
		//创建队伍
		IAction action198 = new TeamCreateAction();
		ACTION_MAP.put(AgreementUtil.team1, action198);
		//申请加队
		IAction action199 = new TeamApplyAction();
		ACTION_MAP.put(AgreementUtil.team2, action199);
		//队伍状态
		IAction action200 = new TeamStateAction();
		ACTION_MAP.put(AgreementUtil.team4, action200);
		//队伍操作
		IAction action201 = new TeamOperateAction();
		ACTION_MAP.put(AgreementUtil.team5, action201);
		//申请列表
		IAction action202 = new TeamApplyListAction();
		ACTION_MAP.put(AgreementUtil.team6, action202);
		//寻路
		IAction action203 = new FindWayAction();
		ACTION_MAP.put(AgreementUtil.findway, action203);
		//确认框
		IAction action204 = new ConfirmAciton();
		ACTION_MAP.put(AgreementUtil.confirm, action204);
		//单人竞技场
		IAction action205 = new OneArenaAction();
		ACTION_MAP.put(AgreementUtil.oneArena, action205);
		//组队竞技场
		IAction action206 = new TeamArenaAction();
		ACTION_MAP.put(AgreementUtil.teamArena, action206);
		//劳动节交互
		IAction action207 = new LaborAction();
		ACTION_MAP.put(AgreementUtil.labor, action207);
		//活动按钮显示隐藏
		IAction action208 = new VIconAction();
		ACTION_MAP.put(AgreementUtil.vicon, action208);

		// 获取手机验证码
		IAction action300 = new PhoneAction();
		ACTION_MAP.put(AgreementUtil.PhoneNumber, action300);
		// 区域和用户查询对应的角色
		IAction action301 = new GetRoleByQuid();
		ACTION_MAP.put(AgreementUtil.UidAndQidForRole, action301);
		// 获取区域
		IAction action302 = new SelectRoleByArea();
		ACTION_MAP.put(AgreementUtil.GETAREA, action302);

		// 不加密协议头的对象
		ACTION_MAP2.put(AgreementUtil.fightround, ACTION_MAP.get(AgreementUtil.fightround));
		ACTION_MAP2.put(AgreementUtil.fightingroundend, ACTION_MAP.get(AgreementUtil.fightingroundend));
		ACTION_MAP2.put(AgreementUtil.battlestate, ACTION_MAP.get(AgreementUtil.battlestate));
		ACTION_MAP2.put(AgreementUtil.move, ACTION_MAP.get(AgreementUtil.move));

		// 进行物品状态的更改
		IAction action500 = new SalesGoodsChangeOrder();
		ACTION_MAP.put(AgreementUtil.salesGoodsChangeOrder_inter, action500);
		// 进行salegoods 物品进行
		IAction action501 = new SaleGoodsStatues();
		ACTION_MAP.put(AgreementUtil.saleGoodsStatues_inter, action501);
		// 藏宝阁商品购买
		IAction action502 = new GoodsBuy();
		ACTION_MAP.put(AgreementUtil.goodsBuy_inter, action502);
		// 充值回调
		/*IAction action503 = new Payreturn();
		ACTION_MAP.put(AgreementUtil.PAYRETURN, action503);*/
		ACTION_MAP.put(AgreementUtil.FINDDROP, new FindDropAction());

		ACTION_MAP.put(AgreementUtil.GET_EGG, new EggAction());

		// 幻境试炼
		ACTION_MAP.put(AgreementUtil.HJSL, new HjslAction());

		// 灵犀操作
		ACTION_MAP.put(AgreementUtil.LINGXI, new LxAction());
		IAction action999 = new GoodsExchangeAction();
		ACTION_MAP.put(AgreementUtil.GOODSFORGOODS, action999);
		// 账号绑定
//		IAction action553 = new Account_Binding();
//		ACTION_MAP.put(AgreementUtil.ACCOUNT_BINDING, action553);
		// 账号登录
//		IAction action554 = new Account_Login();
//		ACTION_MAP.put(AgreementUtil.ACCOUNT_GOLOGIN, action554);
	}
}