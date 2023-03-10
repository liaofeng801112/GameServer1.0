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
 * ????????????
 *
 * @author ?????????
 * @date : 2017???11???29??? ??????4:36:04
 */
public class ParamTool {

	public static final Map<String, IAction> ACTION_MAP = new HashMap<String, IAction>();
	// ??????????????????
	public static final Map<String, IAction> ACTION_MAP2 = new HashMap<String, IAction>();

	public static void handles() {
		// ????????????
		IAction action1 = new LoginAction();
		ACTION_MAP.put(AgreementUtil.login, action1);
		// ????????????
		IAction action2 = new RegisterAction();
		ACTION_MAP.put(AgreementUtil.register, action2);
		// ????????????
		IAction action3 = new CreateRoleAction();
		ACTION_MAP.put(AgreementUtil.createrole, action3);
		// ????????????
		IAction action10 = new IntoGangAction();
		ACTION_MAP.put(AgreementUtil.intogang, action10);
		// ????????????
		IAction action11 = new RoleMoveAction();
		ACTION_MAP.put(AgreementUtil.move, action11);
		// // ??????NPC
		// IAction action12 = new NpcPointAction();
		// ACTION_MAP.put(AgreementUtil.npc, action12);
		// // ?????????
		// IAction action14 = new SummoningAction();
		// ACTION_MAP.put(AgreementUtil.pet, action14);
		// ??????
		IAction action15 = new MountAction();
		ACTION_MAP.put("mount", action15);
		// ??????????????????
		IAction action17 = new PackChangeAction();
		ACTION_MAP.put(AgreementUtil.packchange, action17);
		// ????????????
		IAction action19 = new FriendAction();
		ACTION_MAP.put(AgreementUtil.friend, action19);
		// ????????????
		IAction action20 = new AddFriendAction();
		ACTION_MAP.put(AgreementUtil.addfriend, action20);
		// ????????????
		IAction action21 = new DeleteFriendAction();
		ACTION_MAP.put(AgreementUtil.deletefriend, action21);
		// ????????????
		IAction action22 = new FriendChatAction();
		ACTION_MAP.put(AgreementUtil.friendchat, action22);
		// ????????????
		IAction action23 = new DeductiontaelAction();
		ACTION_MAP.put(AgreementUtil.deductiontael, action23);
		// ????????????
		IAction action28 = new ChangeMapAction();
		ACTION_MAP.put(AgreementUtil.changemap, action28);
		// ????????????
		IAction action34 = new GangCreateAction();
		ACTION_MAP.put(AgreementUtil.gangcreate, action34);
		// ??????????????????
		IAction action35 = new GangApplyAction();
		ACTION_MAP.put(AgreementUtil.gangapply, action35);
		// ????????????
		IAction action36 = new GangRetreatAction();
		ACTION_MAP.put(AgreementUtil.gangretreat, action36);
		// ????????????
		IAction action37 = new GangListAction();
		ACTION_MAP.put(AgreementUtil.ganglist, action37);
		// ??????NPC
		IAction action38 = new GiveAction();
		ACTION_MAP.put(AgreementUtil.give, action38);
		// ????????????
		IAction action43 = new GangShotAction();
		ACTION_MAP.put(AgreementUtil.gangshot, action43);
		// ??????????????????
		IAction action44 = new GangAgreeAction();
		ACTION_MAP.put(AgreementUtil.gangagree, action44);
		// ??????????????????
		IAction action45 = new GangRefuseAction();
		ACTION_MAP.put(AgreementUtil.gangrefuse, action45);
		// ??????????????????
		IAction action46 = new GangAppointAction();
		ACTION_MAP.put(AgreementUtil.gangappoint, action46);
		// ??????????????????
		IAction action47 = new GangChangeAction();
		ACTION_MAP.put(AgreementUtil.gangchange, action47);
		// ??????
		IAction action48 = new PawnAction();
		ACTION_MAP.put(AgreementUtil.pawn, action48);
		// ????????????
		IAction action49 = new RetrieveAction();
		ACTION_MAP.put(AgreementUtil.retrieve, action49);
		// ??????????????????
		IAction action54 = new PetChangeAction();
		ACTION_MAP.put(AgreementUtil.petchange, action54);
		// // ????????????
		// IAction action55 = new PetCarryAction();
		// ACTION_MAP.put(AgreementUtil.petcarry, action55);
		// ??????????????????
		IAction action60 = new TitleChangeAction();
		ACTION_MAP.put(AgreementUtil.titlechange, action60);
		// ????????????????????????
		IAction action61 = new TitleListAction();
		ACTION_MAP.put(AgreementUtil.titlelist, action61);
		// ????????????
		IAction action64 = new PackLockAction();
		ACTION_MAP.put(AgreementUtil.packlock, action64);
		// ????????????
		IAction action65 = new PackGiftAction();
		ACTION_MAP.put(AgreementUtil.packgift, action65);
		// ??????
		IAction action66 = new MarryAction();
		ACTION_MAP.put(AgreementUtil.marry, action66);
		// ??????
		IAction action68 = new MakeLoveAction();
		ACTION_MAP.put(AgreementUtil.makelove, action68);
		// ?????????????????????
		IAction action69 = new PetInfoAction();
		ACTION_MAP.put(AgreementUtil.petinfo, action69);
		// npc??????
		IAction action70 = new NpcComposeAction();
		ACTION_MAP.put(AgreementUtil.npccompose, action70);
		// ????????????
		IAction action72 = new PetReleaseAction();
		ACTION_MAP.put(AgreementUtil.petrelease, action72);
		// ????????????
		IAction action73 = new SummonPetAction();
		ACTION_MAP.put(AgreementUtil.summonpet, action73);
		// ??????????????????
		IAction action74 = new RoleChangeAction();
		ACTION_MAP.put(AgreementUtil.rolechange, action74);
		// ????????????
		IAction action75 = new FightRoundAction();
		ACTION_MAP.put(AgreementUtil.fightround, action75);
		// ????????????
		IAction action76 = new FightbattleConnectionAction();
		ACTION_MAP.put(AgreementUtil.battleconnection, action76);
		// ????????????
		IAction action81 = new FightingForeseeAction();
		ACTION_MAP.put(AgreementUtil.fightingforesee, action81);
		// // ????????????
		// IAction action82 = new FightingResponseAction();
		// ACTION_MAP.put(AgreementUtil.fightingresponse, action82);
		// ????????????
		IAction action90 = new FightingendAction();
		ACTION_MAP.put(AgreementUtil.fightingend, action90);
		// // ??????????????????
		// IAction action77 = new NpcGiftAction();
		// ACTION_MAP.put(AgreementUtil.npcgift, action77);
		// // ????????????/??????
		// IAction action78 = new MountCarryAction();
		// ACTION_MAP.put(AgreementUtil.mountcarry, action78);
		// NPC??????
		IAction action79 = new NpcCureAction();
		ACTION_MAP.put(AgreementUtil.npccure, action79);
		// ????????????
		IAction action80 = new GangGiveMoneyAction();
		ACTION_MAP.put(AgreementUtil.givemoney, action80);
		// ??????
		IAction action83 = new PetAlchemyAction();
		ACTION_MAP.put(AgreementUtil.petalchemy, action83);
		// ????????????
		IAction action84 = new BuyMingChaoAction();
		ACTION_MAP.put(AgreementUtil.buymingchao, action84);
		// ??????????????????
		IAction action85 = new FightingRoundAction();
		ACTION_MAP.put(AgreementUtil.fightingroundend, action85);
		// ????????????
		IAction action88 = new RoleTransAction();
		ACTION_MAP.put(AgreementUtil.racialtransformation, action88);
		// ????????????
		IAction action92 = new RoleLevelUpAction();
		ACTION_MAP.put(AgreementUtil.rolelevelup, action92);
		// ??????????????????
		IAction action93 = new MountSkillStuAction();
		ACTION_MAP.put(AgreementUtil.addmountskill, action93);
		// ??????????????????
		IAction action94 = new MountSkillDeleteAction();
		ACTION_MAP.put(AgreementUtil.missmountskill, action94);
		// ?????????????????????(??????,??????,??????)
		IAction action95 = new MountUpdateAction();
		ACTION_MAP.put(AgreementUtil.changemountvalue, action95);
		// ????????????
		IAction action96 = new MountGetAction();
		ACTION_MAP.put(AgreementUtil.mountget, action96);
		// ??????????????????
		IAction action97 = new BabyAction();
		ACTION_MAP.put(AgreementUtil.baby, action97);
		// ??????
		IAction action98 = new UnMarryAction();
		ACTION_MAP.put(AgreementUtil.unmarry, action98);
		// ????????????
		IAction action99 = new BabyBornAction();
		ACTION_MAP.put(AgreementUtil.babyborn, action99);
		// ???????????????
		IAction action101 = new BabyCustodayAction();
		ACTION_MAP.put(AgreementUtil.babycustoday, action101);
		// ????????????
		IAction action102 = new UpdateLingAction();
		ACTION_MAP.put(AgreementUtil.updateling, action102);
		// ????????????
		IAction action105 = new ChangeRoleNameAction();
		ACTION_MAP.put(AgreementUtil.changerolename, action105);
		// npc?????????
		IAction action111 = new NPCDialogAction();
		ACTION_MAP.put(AgreementUtil.npcdialog, action111);
		// ?????????
		IAction action112 = new MiddleAction();
		ACTION_MAP.put(AgreementUtil.middle, action112);
		// ?????????
		IAction action113 = new PrisonAction();
		ACTION_MAP.put(AgreementUtil.getout, action113);
		// ???????????????
		IAction action114 = new QuoteOutAction();
		ACTION_MAP.put(AgreementUtil.quoteout, action114);
		// ??????????????????
		IAction action115 = new UpdaBabyAction();
		ACTION_MAP.put(AgreementUtil.updababy, action115);
		// ??????npc??????
		IAction action116 = new GangMonitorAction();
		ACTION_MAP.put(AgreementUtil.gangmonitor, action116);
		// ??????????????????
		IAction action117 = new GangBattleAction();
		ACTION_MAP.put(AgreementUtil.gangbattle, action117);
		// ????????????
		IAction action118 = new StallAction();
		ACTION_MAP.put(AgreementUtil.stall, action118);
		// ??????????????????
		IAction action119 = new StallBuyAction();
		ACTION_MAP.put(AgreementUtil.stallbuy, action119);
		// ??????????????????
		IAction action120 = new StallGetAction();
		ACTION_MAP.put(AgreementUtil.stallget, action120);
		// ????????????
		IAction action121 = new MountReleaseAction();
		ACTION_MAP.put(AgreementUtil.mountrelease, action121);
		// ???????????? ?????????????????????
		IAction action122 = new AddMonsterAction();
		ACTION_MAP.put(AgreementUtil.updatemonsters, action122);
		// ?????????????????????
		IAction action124 = new ObtainarticleAction();
		ACTION_MAP.put(AgreementUtil.obtainarticle, action124);
		// ????????????
		IAction action125 = new DrawnitemsAction();
		ACTION_MAP.put(AgreementUtil.drawnitems, action125);
		// ?????????????????????
		IAction action126 = new ThrowinarticleAction();
		ACTION_MAP.put(AgreementUtil.throwinarticle, action126);
		// ???????????????
		IAction action127 = new BindingMobileAction();
		ACTION_MAP.put(AgreementUtil.bindingmobile, action127);
		// ??????
		IAction action128 = new AccountStopAction();
		ACTION_MAP.put(AgreementUtil.accountstop, action128);
		// ?????????
		IAction action129 = new OrderByRoleAction();
		ACTION_MAP.put(AgreementUtil.pankinglist, action129);
		// ????????????
		IAction action130 = new BuyShopAction();
		ACTION_MAP.put(AgreementUtil.nbuy, action130);
		// ??????????????????
		IAction action131 = new UseRoleAction();
		ACTION_MAP.put(AgreementUtil.user, action131);
		// ????????????
		IAction action132 = new PackRecordAction();
		ACTION_MAP.put(AgreementUtil.packRecord, action132);
		// ???????????????
		IAction action133 = new enterGameAction();
		ACTION_MAP.put(AgreementUtil.enterGame, action133);
		// ??????????????????
		IAction action134 = new RoleSystemAction();
		ACTION_MAP.put(AgreementUtil.roleSystem, action134);
		// ??????????????????
		IAction action138 = new RolePrivateAction();
		ACTION_MAP.put(AgreementUtil.rolePrivate, action138);

		// ????????????
		IAction action139 = new BookofchalgAction();
		ACTION_MAP.put(AgreementUtil.bookofchalg, action139);

		// ?????????????????????
		IAction action140 = new RefusechalgAction();
		ACTION_MAP.put(AgreementUtil.refusechalg, action140);

		// ????????????
		IAction action141 = new LotteryAction();
		ACTION_MAP.put(AgreementUtil.getfivemsg, action141);

		// ?????????????????????
		IAction action142 = new ExchangeGoodsAction();
		ACTION_MAP.put(AgreementUtil.exchangegoods, action142);
		// ?????? ??????
		IAction action143 = new SuitComposeAction();
		ACTION_MAP.put(AgreementUtil.suitoperate, action143);
		// ??????????????????
		IAction action144 = new QualityCAciton();
		ACTION_MAP.put(AgreementUtil.extrattroper, action144);
		// ????????????????????????
		IAction action145 = new AddGoodAction();
		ACTION_MAP.put(AgreementUtil.addgood, action145);
		// ??????????????????
		IAction action146 = new HatchvalueAction();
		ACTION_MAP.put(AgreementUtil.hatchvalue, action146);
		// ???????????????
		IAction action147 = new HatchaddAction();
		ACTION_MAP.put(AgreementUtil.hatchadd, action147);
		// ????????????
		IAction action148 = new RichMonitorAction();
		ACTION_MAP.put(AgreementUtil.richM, action148);
		// ?????????
		IAction action149 = new ChatAction();
		ACTION_MAP.put(AgreementUtil.chat, action149);
		// ????????????ID????????????
		IAction action150 = new SearcahChatRoleIdAction();
		ACTION_MAP.put(AgreementUtil.searcahChatRoleId, action150);
		// ???????????????????????????
		IAction action151 = new SearcahChatRoleNameAction();
		ACTION_MAP.put(AgreementUtil.searcahChatRoleName, action151);
		IAction action152 = new SearchChatRecordeAction();
		ACTION_MAP.put(AgreementUtil.searchChatRecorde, action152);
		// ??????npc??????
		IAction action153 = new NpcShopAction();
		ACTION_MAP.put(AgreementUtil.shop, action153);
		// ??????????????????
		IAction action154 = new MyOrderSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch3, action154);
		// ??????????????????
		IAction action155 = new AppointBuyAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch4, action155);
		// ??????????????????
		IAction action156 = new MyGoodsSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch5, action156);
		// ????????????
		IAction action157 = new MessageSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch6, action157);
		// ??????????????????
		IAction action158 = new CollectionSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch7, action158);
		// ????????????????????? ???????????? ??????
		IAction action159 = new GoodsControlAction();
		ACTION_MAP.put(AgreementUtil.CBGGood, action159);
		// ??????-????????????
		IAction action160 = new CollectionControlAction();
		ACTION_MAP.put(AgreementUtil.CBGCollect, action160);
		// ????????????
		IAction action161 = new MessageControlAction();
		ACTION_MAP.put(AgreementUtil.CBGMes, action161);
		// ????????????
		IAction action162 = new GoodsSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch1, action162);
		// ??????????????????
		IAction action163 = new MyWaresSearchAction();
		ACTION_MAP.put(AgreementUtil.CBGSearch2, action163);
		// ?????????????????????
		IAction action164 = new GoodsShelfAction();
		ACTION_MAP.put(AgreementUtil.CBGShelf, action164);
		// ???????????????????????????
		IAction action165 = new SearchChatRecordeAction();
		ACTION_MAP.put(AgreementUtil.searchChatRecorde, action165);
		// ????????????
		IAction action166 = new GoodsBuyAction();
		ACTION_MAP.put(AgreementUtil.CBGBuy, action166);
		// ??????????????????
		IAction action167 = new GoodsBackAction();
		ACTION_MAP.put(AgreementUtil.CBGBack, action167);
		// ????????????
		IAction action168 = new ClickMonsterAction();
		ACTION_MAP.put(AgreementUtil.ClickMonsters, action168);
		// //????????????
		// IAction action169 = new DropAction();
		// ACTION_MAP.put(AgreementUtil.drop, action169);
		// ????????????
		IAction action170 = new TransStateAction();
		ACTION_MAP.put(AgreementUtil.TransState, action170);
		// ??????????????????
		IAction action171 = new TransGoodAction();
		ACTION_MAP.put(AgreementUtil.TransGood, action171);
		// ???????????????????????????
		IAction action172 = new UsePetAction();
		ACTION_MAP.put(AgreementUtil.userpet, action172);
		// ??????
		IAction action173 = new MonitorAction();
		ACTION_MAP.put(AgreementUtil.Monitor, action173);
		// ????????????????????????
		IAction action174 = new UseMountAction();
		ACTION_MAP.put(AgreementUtil.usermount, action174);
		// ????????????????????????
		IAction action175 = new UseLingAction();
		ACTION_MAP.put(AgreementUtil.userling, action175);
		// ????????????????????????
		IAction action176 = new UseBabyAction();
		ACTION_MAP.put(AgreementUtil.userbaby, action176);
		// ????????????????????????
		IAction action177 = new UseCardAction();
		ACTION_MAP.put(AgreementUtil.usercard, action177);
		// ??????
		IAction action178 = new TaskAction();
		ACTION_MAP.put(AgreementUtil.TASKN, action178);
		// ??????????????????
		IAction action179 = new FightQlAction();
		ACTION_MAP.put(AgreementUtil.fightQl, action179);

		/** HGC--2019-11-13--start */
		// ???????????????
		IAction action180 = new PhoneBangAction();
		ACTION_MAP.put(AgreementUtil.PhoneBang, action180);
		// ???????????????
		IAction action181 = new UnPhoneBangAction();
		ACTION_MAP.put(AgreementUtil.UnPhoneBang, action181);
		// ????????????????????????
		IAction action182 = new PhoneNumberIsNoGetAction();
		ACTION_MAP.put(AgreementUtil.PhoneNumberIsNoGet, action182);

		/** HGC--2019-11-13--end */

		/** zzh--2019-11-21--start */
		// ??????vip??????
		IAction action183 = new GetVipGradePackAction();
		ACTION_MAP.put(AgreementUtil.Getvipgradepack, action183);
		// ??????vip
		IAction action184 = new VipGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Vipgradesure, action184);
		// ????????????????????????
		IAction action185 = new PayDayGradePayAction();
		ACTION_MAP.put(AgreementUtil.Paydaygradepay, action185);
		// ??????????????????
		IAction action186 = new PayDayGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Paydaygradesure, action186);
		// ????????????????????????
		IAction action187 = new DayForWeekGradeGetAction();
		ACTION_MAP.put(AgreementUtil.Dayforweekgradeget, action187);
		// ??????????????????
		IAction action188 = new DayForWeekGradeSureAction();
		ACTION_MAP.put(AgreementUtil.Dayforweekgradesure, action188);
		// ????????????????????????
		IAction action189 = new DayForOneGetAction();
		ACTION_MAP.put(AgreementUtil.Dayforoneget, action189);
		// ??????????????????
		IAction action190 = new DayForOneSureAction();
		ACTION_MAP.put(AgreementUtil.Dayforonesure, action190);
		// ????????????????????????
		IAction action191 = new ChongJiPackGetAction();
		ACTION_MAP.put(AgreementUtil.chongjipackget, action191);
		// ??????????????????
		IAction action192 = new ChongJiPackSureAction();
		ACTION_MAP.put(AgreementUtil.Chongjipacksure, action192);
		// ??????????????????
		IAction action193 = new ChongJiPackOpenAction();
		ACTION_MAP.put(AgreementUtil.Chongjipackopen, action193);
		/** zzh--2019-11-21--end */

		//????????????
		IAction action194 = new SceneAction();
		ACTION_MAP.put(AgreementUtil.Scene, action194);
		//??????????????????
		IAction action195 = new UsePalAction();
		ACTION_MAP.put(AgreementUtil.userpal, action195);
		//??????????????????
		IAction action196 = new ShopPriceAction();
		ACTION_MAP.put(AgreementUtil.shopPrice, action196);

		//??????
		IAction action197 = new TeamEnlistAction();
		ACTION_MAP.put(AgreementUtil.enlist, action197);
		//????????????
		IAction action198 = new TeamCreateAction();
		ACTION_MAP.put(AgreementUtil.team1, action198);
		//????????????
		IAction action199 = new TeamApplyAction();
		ACTION_MAP.put(AgreementUtil.team2, action199);
		//????????????
		IAction action200 = new TeamStateAction();
		ACTION_MAP.put(AgreementUtil.team4, action200);
		//????????????
		IAction action201 = new TeamOperateAction();
		ACTION_MAP.put(AgreementUtil.team5, action201);
		//????????????
		IAction action202 = new TeamApplyListAction();
		ACTION_MAP.put(AgreementUtil.team6, action202);
		//??????
		IAction action203 = new FindWayAction();
		ACTION_MAP.put(AgreementUtil.findway, action203);
		//?????????
		IAction action204 = new ConfirmAciton();
		ACTION_MAP.put(AgreementUtil.confirm, action204);
		//???????????????
		IAction action205 = new OneArenaAction();
		ACTION_MAP.put(AgreementUtil.oneArena, action205);
		//???????????????
		IAction action206 = new TeamArenaAction();
		ACTION_MAP.put(AgreementUtil.teamArena, action206);
		//???????????????
		IAction action207 = new LaborAction();
		ACTION_MAP.put(AgreementUtil.labor, action207);
		//????????????????????????
		IAction action208 = new VIconAction();
		ACTION_MAP.put(AgreementUtil.vicon, action208);

		// ?????????????????????
		IAction action300 = new PhoneAction();
		ACTION_MAP.put(AgreementUtil.PhoneNumber, action300);
		// ????????????????????????????????????
		IAction action301 = new GetRoleByQuid();
		ACTION_MAP.put(AgreementUtil.UidAndQidForRole, action301);
		// ????????????
		IAction action302 = new SelectRoleByArea();
		ACTION_MAP.put(AgreementUtil.GETAREA, action302);

		// ???????????????????????????
		ACTION_MAP2.put(AgreementUtil.fightround, ACTION_MAP.get(AgreementUtil.fightround));
		ACTION_MAP2.put(AgreementUtil.fightingroundend, ACTION_MAP.get(AgreementUtil.fightingroundend));
		ACTION_MAP2.put(AgreementUtil.battlestate, ACTION_MAP.get(AgreementUtil.battlestate));
		ACTION_MAP2.put(AgreementUtil.move, ACTION_MAP.get(AgreementUtil.move));

		// ???????????????????????????
		IAction action500 = new SalesGoodsChangeOrder();
		ACTION_MAP.put(AgreementUtil.salesGoodsChangeOrder_inter, action500);
		// ??????salegoods ????????????
		IAction action501 = new SaleGoodsStatues();
		ACTION_MAP.put(AgreementUtil.saleGoodsStatues_inter, action501);
		// ?????????????????????
		IAction action502 = new GoodsBuy();
		ACTION_MAP.put(AgreementUtil.goodsBuy_inter, action502);
		// ????????????
		/*IAction action503 = new Payreturn();
		ACTION_MAP.put(AgreementUtil.PAYRETURN, action503);*/
		ACTION_MAP.put(AgreementUtil.FINDDROP, new FindDropAction());

		ACTION_MAP.put(AgreementUtil.GET_EGG, new EggAction());

		// ????????????
		ACTION_MAP.put(AgreementUtil.HJSL, new HjslAction());

		// ????????????
		ACTION_MAP.put(AgreementUtil.LINGXI, new LxAction());
		IAction action999 = new GoodsExchangeAction();
		ACTION_MAP.put(AgreementUtil.GOODSFORGOODS, action999);
		// ????????????
//		IAction action553 = new Account_Binding();
//		ACTION_MAP.put(AgreementUtil.ACCOUNT_BINDING, action553);
		// ????????????
//		IAction action554 = new Account_Login();
//		ACTION_MAP.put(AgreementUtil.ACCOUNT_GOLOGIN, action554);
	}
}