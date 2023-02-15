package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.lottery.DrawBase;
import org.come.action.monitor.MonitorUtil;
import org.come.action.reward.DrawnitemsAction;
import org.come.action.suit.SuitMixdeal;
import org.come.action.summoning.SummonPetAction;
import org.come.bean.LoginResult;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.Record;
import org.come.entity.RoleSummoning;
import org.come.entity.Titletable;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.EventModel;
import org.come.model.Title;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapZB;
import org.come.task.MonsterUtil;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleEnd;
import come.tool.Battle.BattleMixDeal;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.SceneUtil;
import come.tool.Stall.AssetUpdate;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

/**封装的掉落处理*/
public class DropUtil {

	public static AssetUpdate getDrop(LoginResult login, DropModel model, String msg, AssetUpdate assetUpdate, double expXs1, double expXs2, int num, int type) {
		if (model == null) {
			return assetUpdate;
		}
		ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(login.getRolename());
		RoleData roleData = RolePool.getRoleData(login.getRole_id());
		if (ctx == null || roleData == null) {
			return assetUpdate;
		}

		int lvl = BattleMixDeal.lvlint(login.getGrade());

		StringBuffer buffer = new StringBuffer();
		if (model.getMaxDrop() == null || model.getMaxDrop() > lvl) {
			if (model.getMoney() != null) {//金钱
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
				}
				assetUpdate.updata("D=" + model.getMoney());
				login.setGold(login.getGold().add(model.getMoney()));
				if (type == 24) {
					MonitorUtil.getMoney().addD(model.getMoney().longValue(), 0);
					MonitorUtil.getDropQM1().add(model.getMoney().longValue());
				} else {
					MonitorUtil.getMoney().addD(model.getMoney().longValue(), 1);
					MonitorUtil.getDropQM2().add(model.getMoney().longValue());
				}
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得金钱");
				buffer.append(model.getMoney());
			}
			if (model.getCodeCard() != null) {//仙玉
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
				}
				assetUpdate.updata("X=" + model.getCodeCard());
				login.setCodecard(login.getCodecard().add(model.getCodeCard()));
				MonitorUtil.getMoney().addX(model.getCodeCard().longValue(), 2);
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得仙玉");
				buffer.append(model.getCodeCard());
			}
		}
		if (model.getExp() != null || model.getExpFix() != null) {//经验
			long exp = 0;
			if (model.getExp() != null) {
				exp += model.getExp().longValue() * expXs1;
			}
			if (model.getExpFix() != null) {
				exp += model.getExpFix().longValue();
			}
			exp *= (model.getExps(num) + expXs2);
			if (exp != 0) {
				if (model.getMaxRole() != null && model.getMaxRole() < lvl) {
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你超过了经验最大获取等级");
				} else {
					ExpUtil.RoleExp(login, exp);
					if (assetUpdate == null) {
						assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
					}
					assetUpdate.updata("R" + login.getGrade() + "=" + login.getExperience() + "=" + login.getHp() + "=" + login.getMp());
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得" + exp + "经验");
				}
			}
		}

		if (model.getTypes() != null && (model.getMaxDrop() == null || model.getMaxDrop() > lvl)) {
			for (int i = 0; i < model.getTypes().size(); i++) {
				DropType dropType = model.getTypes().get(i);
				if (dropType.getDropType() == DropType.SCORE) {//积分=xxx
					if (assetUpdate == null) {
						assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
					}
					assetUpdate.updata(dropType.getKey() + "=" + dropType.getValue());
					login.setScore(DrawnitemsAction.Splice(login.getScore(), dropType.getKey() + "=" + dropType.getValue(), 2));
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得");
					buffer.append(dropType.getKey());
					buffer.append(dropType.getValue());
				} else if (dropType.getDropType() == DropType.RECORD) {//记录=xxx
					if (roleData.getPackRecord().addOther(dropType.getValue() + "")) {
						EventModel eventModel = GameServer.getEvent(dropType.getValue());
						if (eventModel != null) {
							eventModel.resetRanking(login.getRole_id(), login.getRolename());
						}
					}
				} else if (dropType.getDropType() == DropType.KILL) {//击杀xx=xxx
					if (assetUpdate == null) {
						assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
					}
					assetUpdate.updata(dropType.getKey() + "=" + dropType.getValue());
					login.setKill(DrawnitemsAction.Splice(login.getKill(), dropType.getKey() + "=" + dropType.getValue(), 5));
				} else if (dropType.getDropType() == DropType.GOOD) {//物品=x
					if (model.getMaxGood() != null && model.getMaxGood() < num) {
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("达到最大物品获取次数");
					} else if (roleData.isGoodFull()) {
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("你背包已满");
					} else {
						XXGDBean bean = getGoods(dropType.getDropGood());
						if (bean != null) {
							BigDecimal id = new BigDecimal(bean.getId());
							Goodstable goodstable = GameServer.getGood(id);
							//特效物品判断是拥有特效
							if (id.longValue() < 0 && roleData.getPackRecord().isTX(-id.longValue() + "")) {
								continue;
							}
							if (goodstable == null) {
								continue;
							}
							if (buffer.length() != 0) {
								buffer.append("|");
							}
							buffer.append("你获得");
							buffer.append(bean.getSum());
							buffer.append("个");
							buffer.append(goodstable.getGoodsname());
							if (assetUpdate == null) {
								assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
							}
							AddGoodAction.addGood(assetUpdate, goodstable, login, roleData, bean, type);
							SuitMixdeal.good(goodstable, login.getRolename(), msg, type);
						}
					}
				} else if (dropType.getDropType() == DropType.BOOS) {//放妖=x
					XXGDBean bean = getGoods(dropType.getDropGood());
					if (bean != null) {
						AddMonster(login.getRole_id(), login.getRolename(), bean.getId(), null);
					}
				} else if (dropType.getDropType() == DropType.TITLE) {//称谓=x
					XXGDBean bean = getGoods(dropType.getDropGood());
					if (bean != null) {
						Title title = GameServer.getTitle(bean.getId());
						if (title != null) {
							if (AllServiceUtil.getTitletableService().selectRoleTitle(login.getRole_id(), title.getTitlename()) == null) {
								Titletable titletable = new Titletable();
								titletable.setTitlename(title.getTitlename());
								titletable.setRoleid(login.getRole_id());
								AllServiceUtil.getTitletableService().createRoleTitle(titletable);
								if (buffer.length() != 0) {
									buffer.append("|");
								}
								buffer.append("你获得称谓:");
								buffer.append(title.getTitlename());
							} else {
								buffer.append("你已经拥有称谓:");
								buffer.append(title.getTitlename());
							}
						}
					}
				} else if (dropType.getDropType() == DropType.GANG) {
					if (assetUpdate == null) {
						assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
					}
					assetUpdate.updata("B=" + dropType.getValue());
					login.setContribution(login.getContribution().add(new BigDecimal(dropType.getValue())));
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得帮贡");
					buffer.append(dropType.getValue());
					GangDomain gangDomain = GangUtil.getGangDomain(login.getGang_id());
					if (gangDomain != null) {
						gangDomain.addBG(dropType.getValue());
					}
				}
			}
		}
		if (buffer.length() != 0) {
			if (assetUpdate == null) {
				assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
			}
			assetUpdate.upmsg(buffer.toString());
		}
		return assetUpdate;
	}
//	1  _ 1
//	1.6_ 1.3
//	2.5_ 1.7
//	3.7_ 2.2
//	5.2_ 2.8

	/**
	 * 暂时用的战斗解析  expXs1 基础经验加成 双倍 道具 等    expXs2 队长加成  任务环数加成  系统倍数加成
	 */
	public static long getDrop(LoginResult login, RoleSummoning pet, DropModel model, String msg, BattleEnd battleEnd
			, double expXs1, double expXs2, int num, int type, int sum, int dropXS, int ndXS) {
		long goodExp = 0;
		if (model == null) {
			return goodExp;
		}
		ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(login.getRolename());
		RoleData roleData = RolePool.getRoleData(login.getRole_id());
		if (ctx == null || roleData == null) {
			return goodExp;
		}
		double xs = ndXS == 0 ? 1 : ndXS == 1 ? 1.3 : ndXS == 2 ? 1.7 : ndXS == 3 ? 2.2 : 2.8;
		int lvl = BattleMixDeal.lvlint(login.getGrade());
		StringBuffer buffer = new StringBuffer();
		if (model.getMaxDrop() == null || model.getMaxDrop() > lvl) {
			if (model.getMoney() != null) {//金钱
				long money = model.getMoney().longValue();
				money *= xs;
				if (dropXS != 0) {
					money *= (1 + dropXS);
				}
//				if (sum>1200) {money*=0.4;}
//				else if (sum>600) {money*=0.6;}
//				else if (sum>300) {money*=0.8;}
				if (money != 0) {
					battleEnd.upAssetData("D=" + money);
					login.setGold(login.getGold().add(new BigDecimal(money)));
					if (type == 24) {
						MonitorUtil.getMoney().addD(money, 0);
						MonitorUtil.getDropQM1().add(money);
					} else {
						MonitorUtil.getMoney().addD(money, 1);
						MonitorUtil.getDropQM2().add(money);
					}
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得金钱");
					buffer.append(money);
				}
			}
			if (model.getCodeCard() != null) {//仙玉
				battleEnd.upAssetData("X=" + model.getCodeCard());
				login.setCodecard(login.getCodecard().add(model.getCodeCard()));
				MonitorUtil.getMoney().addX(model.getCodeCard().longValue(), 2);
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得仙玉");
				buffer.append(model.getCodeCard());
			}
		}
		if (model.getExp() != null || model.getExpFix() != null) {//经验
			long exp = 0;
			if (model.getExp() != null) {
				exp += model.getExp().longValue() * expXs1;
			}
			if (model.getExpFix() != null) {
				exp += model.getExpFix().longValue();
			}
			exp *= (model.getExps(num) + expXs2);
			exp *= xs;
			if (dropXS != 0) {
				exp *= (1 + dropXS);
			}
			if (exp != 0) {
				if (model.getMaxRole() != null && model.getMaxRole() < lvl) {
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你超过了经验最大获取等级");
				} else {
					ExpUtil.RoleExp(login, exp);
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得" + exp + "经验");
				}
				if (pet != null) {
					if (model.getMaxPet() != null && model.getMaxPet() < BattleMixDeal.petLvlint(pet.getGrade())) {
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("你的召唤兽超过了经验最大获取等级");
					} else {
						exp *= 2;
						ExpUtil.PetExp(pet, exp);
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("你的召唤兽" + pet.getSummoningname() + "获得" + exp + "经验");
						goodExp = exp;
					}
				}
			}
		}

		if (model.getTypes() != null && (model.getMaxDrop() == null || model.getMaxDrop() > lvl)) {
			for (int i = 0; i < model.getTypes().size(); i++) {
				DropType dropType = model.getTypes().get(i);
				if (dropType.getDropType() == DropType.SCORE) {//积分=xxx
					int value = dropType.getValue();
					value *= xs;
					battleEnd.upAssetData(dropType.getKey() + "=" + value);
					login.setScore(DrawnitemsAction.Splice(login.getScore(), dropType.getKey() + "=" + value, 2));
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得");
					buffer.append(dropType.getKey());
					buffer.append(value);
				} else if (dropType.getDropType() == DropType.RECORD) {//记录=xxx
					if (roleData.getPackRecord().addOther(dropType.getValue() + "")) {
						EventModel eventModel = GameServer.getEvent(dropType.getValue());
						if (eventModel != null) {
							eventModel.resetRanking(login.getRole_id(), login.getRolename());
						}
					}
				} else if (dropType.getDropType() == DropType.KILL) {//击杀xx=xxx
					battleEnd.upAssetData(dropType.getKey() + "=" + dropType.getValue());
					login.setKill(DrawnitemsAction.Splice(login.getKill(), dropType.getKey() + "=" + dropType.getValue(), 5));
				} else if (dropType.getDropType() == DropType.GOOD) {//物品=x
					if (model.getMaxGood() != null && model.getMaxGood() < num) {
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("达到最大物品获取次数");
					} else if (roleData.isGoodFull()) {
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("你背包已满");
					} else {
						XXGDBean bean = getGoods(dropType.getDropGood(), getEmptyXS(sum, num, dropXS, ndXS));
						if (bean != null) {
							BigDecimal id = new BigDecimal(bean.getId());
							Goodstable goodstable = GameServer.getGood(id);
							//特效物品判断是拥有特效
							if (id.longValue() < 0 && roleData.getPackRecord().isTX(-id.longValue() + "")) {
								continue;
							}
							if (goodstable == null) {
								continue;
							}
							if (buffer.length() != 0) {
								buffer.append("|");
							}
							buffer.append("你获得");
							buffer.append(bean.getSum());
							buffer.append("个");
							buffer.append(goodstable.getGoodsname());
							if (battleEnd.getAssetUpdate() == null) {
								battleEnd.setAssetUpdate(new AssetUpdate(AssetUpdate.USEGOOD));
							}
							AddGoodAction.addGood(battleEnd.getAssetUpdate(), goodstable, login, roleData, bean, type);
							SuitMixdeal.good(goodstable, login.getRolename(), msg, type);
						}
					}
				} else if (dropType.getDropType() == DropType.BOOS) {//放妖=x
					XXGDBean bean = getGoods(dropType.getDropGood());
					if (bean != null) {
						AddMonster(login.getRole_id(), login.getRolename(), bean.getId(), null);
					}
				} else if (dropType.getDropType() == DropType.TITLE) {//称谓=x
					XXGDBean bean = getGoods(dropType.getDropGood());
					if (bean != null) {
						Title title = GameServer.getTitle(bean.getId());
						if (title != null) {
							if (AllServiceUtil.getTitletableService().selectRoleTitle(login.getRole_id(), title.getTitlename()) == null) {
								Titletable titletable = new Titletable();
								titletable.setTitlename(title.getTitlename());
								titletable.setRoleid(login.getRole_id());
								AllServiceUtil.getTitletableService().createRoleTitle(titletable);
								if (buffer.length() != 0) {
									buffer.append("|");
								}
								buffer.append("你获得称谓:");
								buffer.append(title.getTitlename());
							} else {
								buffer.append("你已经拥有称谓:");
								buffer.append(title.getTitlename());
							}
						}
					}
				} else if (dropType.getDropType() == DropType.GANG) {
					int value = dropType.getValue();
					value *= xs;
					battleEnd.upAssetData("B=" + value);
					login.setContribution(login.getContribution().add(new BigDecimal(value)));
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你获得帮贡");
					buffer.append(value);
					GangDomain gangDomain = GangUtil.getGangDomain(login.getGang_id());
					if (gangDomain != null) {
						gangDomain.addBG(value);
					}
				}
			}
		}
		if (buffer.length() != 0) {
			battleEnd.upAssetMsg(buffer.toString());
		}
		return goodExp;
	}

	public static void getDrop(LoginResult loginResult, String value, String msg, int type, double xs, String data) {
		getDrop4(loginResult, value, msg, type, xs, data, null, null, null);
	}

	public static void getDrop2(LoginResult loginResult, String value, String msg, int type, double xs, String data, String dataMes) {
		getDrop4(loginResult, value, msg, type, xs, data, dataMes, null, null);
	}

	public static void getDrop3(LoginResult loginResult, String value, String msg, int type, double xs, String data, String dataMes, String message) {
		getDrop4(loginResult, value, msg, type, xs, data, dataMes, message, null);
	}

	public static void getDrop4(LoginResult loginResult, String value, String msg, int type, double xs, String data, String dataMes, String message, String task) {
		if (value == null || value.equals("")) {
			return;
		}
		ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(loginResult.getRolename());
		if (ctx == null) {
			return;
		}
		boolean is = true;
		if (type == 999) {
			is = false;
			type = 25;
		}
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		AssetUpdate assetUpdate = null;
		StringBuffer buffer = new StringBuffer();
		String[] vs = value.split("\\|");
		for (String v : vs) {
			String[] thing = v.split("=");
			if (thing[0].equals("物品")) {
				if (is && roleData.isGoodFull()) {
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append("你背包已满");
				} else {
					XXGDBean bean = getGoods(thing[1]);
					if (bean != null) {
						BigDecimal id = new BigDecimal(bean.getId());
						Goodstable goodstable = GameServer.getGood(id);
						//特效物品判断是拥有特效
						if (id.longValue() < 0 && roleData.getPackRecord().isTX(-id.longValue() + "")) {
							continue;
						}
						if (goodstable == null) {
							continue;
						}
						if (assetUpdate == null) {
							assetUpdate = new AssetUpdate(type);
						}
						if (buffer.length() != 0) {
							buffer.append("|");
						}
						buffer.append("你获得");
						buffer.append(bean.getSum());
						buffer.append("个");
						buffer.append(goodstable.getGoodsname());

						AddGoodAction.addGood(assetUpdate, goodstable, loginResult, roleData, bean, type);
						SuitMixdeal.good(goodstable, loginResult.getRolename(), msg, type);
					}
				}
			} else if (thing[0].equals("金钱")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				long money = new Long(thing[1]);
				assetUpdate.updata("D=" + money);
				loginResult.setGold(loginResult.getGold().add(new BigDecimal(money)));
				MonitorUtil.getMoney().addD(money, 3);
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得金钱");
				buffer.append(money);
				MonitorUtil.getDropHM().add(money);
			} else if (thing[0].equals("仙玉")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				long money = new Long(thing[1]);
				assetUpdate.updata("X=" + money);
				loginResult.setCodecard(loginResult.getCodecard().add(new BigDecimal(money)));
				MonitorUtil.getMoney().addX(money, 1);
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得仙玉");
				buffer.append(money);
			} else if (thing[0].equals("经验")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				long addexp = (long) (new Long(thing[1]) * xs);
				ExpUtil.RoleExp(loginResult, addexp);
				assetUpdate.updata("R" + loginResult.getGrade() + "=" + loginResult.getExperience() + "=" + loginResult.getHp() + "=" + loginResult.getMp());
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得经验");
				buffer.append(addexp);
			} else if (thing[0].equals("称谓")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				XXGDBean bean = getGoods(thing[1]);
				if (bean != null) {
					Title title = GameServer.getTitle(bean.getId());
					if (title != null) {
						if (AllServiceUtil.getTitletableService().selectRoleTitle(loginResult.getRole_id(), title.getTitlename()) == null) {
							Titletable titletable = new Titletable();
							titletable.setTitlename(title.getTitlename());
							titletable.setRoleid(loginResult.getRole_id());
							AllServiceUtil.getTitletableService().createRoleTitle(titletable);
//							assetUpdate.setMsg("你获得称谓:"+title.getTitlename());
							buffer.append("你获得称谓:");
							buffer.append(title.getTitlename());
						} else {
//							assetUpdate.setMsg("你已经拥有称谓:"+title.getTitlename());
							buffer.append("你已经拥有称谓:");
							buffer.append(title.getTitlename());
						}
					}
				}
			} else if (thing[0].equals("记录")) {
				if (roleData.getPackRecord().addOther(thing[1])) {
					EventModel eventModel = GameServer.getEvent(Integer.parseInt(thing[1]));
					if (eventModel != null) {
						eventModel.resetRanking(loginResult.getRole_id(), loginResult.getRolename());
					}
				}
			} else if (thing[0].equals("放妖")) {
				XXGDBean bean = getGoods(thing[1]);
				if (bean != null) {
					AddMonster(loginResult.getRole_id(), loginResult.getRolename(), bean.getId(), null);
				}
			} else if (thing[0].endsWith("积分") || thing[0].equals("比斗奖章") || thing[0].equals("星芒")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				assetUpdate.updata(thing[0] + "=" + thing[1]);
				loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), thing[0] + "=" + thing[1], 2));
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得");
				buffer.append(thing[0]);
				buffer.append(thing[1]);
			} else if (thing[0].startsWith("击杀")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				assetUpdate.updata(thing[0] + "=" + thing[1]);
				loginResult.setKill(DrawnitemsAction.Splice(loginResult.getKill(), thing[0] + "=" + thing[1], 5));
			} else if (thing[0].equals("帮贡")) {
				if (assetUpdate == null) {
					assetUpdate = new AssetUpdate(type);
				}
				long money = new Long(thing[1]);
				assetUpdate.updata("B=" + money);
				loginResult.setContribution(loginResult.getContribution().add(new BigDecimal(money)));
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append("你获得帮贡");
				buffer.append(money);
				GangDomain gangDomain = GangUtil.getGangDomain(loginResult.getGang_id());
				if (gangDomain != null) {
					gangDomain.addBG(money);
				}
			}
		}
		if (assetUpdate != null) {
			if (message != null) {
				assetUpdate.upmsg(message);
			}
			if (buffer.length() != 0) {
				assetUpdate.upmsg(buffer.toString());
			}
			if (data != null) {
				assetUpdate.setVip(data);
			}
			if (dataMes != null) {
				assetUpdate.updata(dataMes);
			}
			if (task != null) {
				assetUpdate.setTask(task);
			}
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}
	}

	/**
	 * 放妖
	 */
	public static void AddMonster(BigDecimal roleID, String rolename, String message, MapZB mapZB) {
		String[] vs = message.split("#");
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].equals("1001") || vs[i].equals("1002") || vs[i].equals("1007") || vs[i].equals("1008")) {
				SceneUtil.additionalScene(Integer.parseInt(vs[i]));
			} else {
				Boos boos = GameServer.boosesMap.get(vs[i]);
				if (boos != null) {
					MonsterUtil.refreshMonsters(boos, roleID, rolename, mapZB);
				}
			}
		}
	}

	// 获得的物品
	public static XXGDBean getGoods(String goodses) {
		String[] goods = goodses.split("&");
		double lk = Double.parseDouble(goods[0]);
		if (isV(lk)) {
			return null;
		}
		for (int i = 1; i < goods.length; i++) {
			String[] canGetGoods = goods[i].split("\\$");
			if (isV(Double.parseDouble(canGetGoods[2]))) {
				String[] getGoods = canGetGoods[0].split("-");
				XXGDBean bean = new XXGDBean();
				bean.setId(getGoods[SummonPetAction.random.nextInt(getGoods.length)]);
				bean.setSum(Integer.parseInt(canGetGoods[1]));
				return bean;
			}
		}
		return null;
	}

	/**
	 * 掉落加成
	 */
	public static int getEmptyXS(int sum, int num, int dropXS, int ndXS) {
		int empty = -5 * dropXS;
		empty = -5 * ndXS;
		num /= 60;//同次掉落获得次数
		if (num > 15) {
			num = 15;
		}
		empty += num;
		sum /= 120;//整体战斗次数
		if (sum > 10) {
			sum = 10;
		}
		empty += sum;
		return empty;
	}

	// 获得的物品
	public static XXGDBean getGoods(DropGood dropGood, int addEmpty) {
		if (dropGood.getEmpty() > 0) {
			if (addEmpty == 0) {
				if (isV(dropGood.getEmpty())) {
					return null;
				}
			} else {
				if (dropGood.getEmpty() >= 70) {
					addEmpty /= 3;
				} else if (dropGood.getEmpty() >= 50) {
					addEmpty /= 2;
				} else if (dropGood.getEmpty() <= 20) {
					addEmpty *= 1.5;
				}
				if (isV(dropGood.getEmpty() + addEmpty)) {
					return null;
				}
			}
		}
		for (int i = 0; i < dropGood.getDraws().length; i++) {
			DrawBase drawBase = dropGood.getDraws()[i];
			if (isV(drawBase.getV())) {
				XXGDBean bean = new XXGDBean();
				bean.setId(drawBase.getDropId());
				bean.setSum(drawBase.getSum());
				return bean;
			}
		}
		return null;
	}

	// 获得的物品
	public static XXGDBean getGoods(DropGood dropGood) {
		if (isV(dropGood.getEmpty())) {
			return null;
		}
		for (int i = 0; i < dropGood.getDraws().length; i++) {
			DrawBase drawBase = dropGood.getDraws()[i];
			if (isV(drawBase.getV())) {
				XXGDBean bean = new XXGDBean();
				bean.setId(drawBase.getDropId());
				bean.setSum(drawBase.getSum());
				return bean;
			}
		}
		return null;
	}

	/**
	 * 判断是否属于概率内
	 */
	public static boolean isV(double value) {
		value *= 100;
		return value > SummonPetAction.random.nextInt(10000);
	}
}
	/**兑换*/
//	public static boolean isDH(String ab,LoginResult loginResult){
//		String[] vs=ab.substring(1).split("\\|");
//		if (!vs[vs.length-1].equals("#AAFFFO")) {return false;}
//		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(loginResult.getRolename());
//		XXGDBean bean=new XXGDBean();
//		bean.setId(vs[1]);
//		bean.setSum(Integer.parseInt(vs[2]));
//		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
//		AssetUpdate assetUpdate=new AssetUpdate(Integer.parseInt(vs[0]));
//		BigDecimal id=new BigDecimal(bean.getId());
//		Goodstable goodstable = GameServer.getGood(id);
//		//特效物品判断是拥有特效
//		if (id.longValue()<0&&roleData.getPackRecord().isTX(-id.longValue()+"")) {return true;}
//		if( goodstable == null ){return true;}
//		StringBuffer buffer=new StringBuffer();
//		buffer.append("刷物资接口物品id:");
//		buffer.append(id);
//		buffer.append(",");
//		buffer.append(bean.getSum()+"个"+goodstable.getGoodsname());
//		buffer.append(",玩家:");
//		buffer.append(loginResult.getRole_id());
//		buffer.append("_");
//		buffer.append(loginResult.getRolename());
//		AllServiceUtil.getRecordService().insert(new Record(4,buffer.toString()));
//
//		AddGoodAction.addGood(assetUpdate,goodstable,loginResult,roleData,bean,assetUpdate.getType());
//		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
//		String msg="dh:"+ab+(loginResult!=null?loginResult.getRole_id():null);
//		WriteOut.addtxt(msg,9999);
//		return true;
//	}
//
//}
