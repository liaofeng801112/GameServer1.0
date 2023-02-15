package org.come.action.buy;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.monster.ClickMonsterAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.bean.BuyShopBean;
import org.come.bean.LoginResult;
import org.come.bean.PathPoint;
import org.come.entity.GoodsbuyrecordEntity;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.handler.SendMessage;
import org.come.model.Eshop;
import org.come.model.Lshop;
import org.come.model.Robots;
import org.come.model.Shop;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.SplitLingbaoValue;

import come.tool.Battle.BattleMixDeal;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGRole;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSUtil;
import come.tool.Scene.ZZS.ZZSRole;
import come.tool.Scene.ZZS.ZZSScene;
import come.tool.Stall.AssetUpdate;

public class BuyShopAction implements IAction {
	
	static final String ST = "师徒积分";// 45
	static final String BY = "绑玉";// 89
	static final String TT = "天庭积分";// 10
	static final String BZ = "帮战积分";// 61
	static final String DYT = "大雁塔积分";// 120
	static final String DG = "地宫积分";// 121
	static final String XF = "寻芳积分";// 123
	static final String MK = "木魁积分";// 124
	static final String SL = "水陆积分";// 126
	static final String ZZS = "种族赛积分";// 1106
	static final String BDJZ = "比斗奖章";// 600
	static final String XMZ = "星芒";// 601
	static final String DNTG="大闹天宫积分";//605
	static final String QMJJ = "竞技积分";// 2027
	static final String YZF = "勇者积分";// 602
	static final String WSS="武神山积分";//887
	public static String[] CJS;

	static final String LTS = "擂台赛积分";// 505
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		// 获得角色信息
		// 17仙玉购买 18充值积分购买 19副本积分 20大话币购买
		int buyType = 0;
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {
			return;
		}
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		// 接收客户端发来的购买信息
		BuyShopBean buyShopBean = GsonUtil.getGsonUtil().getgson().fromJson(message, BuyShopBean.class);
		if (buyShopBean.getSum() <= 0) {
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		Shop shop = null;
		Eshop eshop;
		BigDecimal goodid;
		long jg;
        boolean isSX=false;
		// zrikka 商城销售记录
		GoodsbuyrecordEntity goodsBuy = new GoodsbuyrecordEntity();
		goodsBuy.setRoleid(loginResult.getRole_id());
		goodsBuy.setUserid(loginResult.getUser_id());
		goodsBuy.setSid(new BigDecimal(0));// 暂定

		if (buyShopBean.getAte() == 0) {
			eshop = GameServer.getAllEshopGoods().get(buyShopBean.getCd());
			if (eshop == null) {
				return;
			}
			String value = BuyUtil.isLimit(loginResult, eshop, buyShopBean);
			if (value != null) {
				SendMessage.sendMessageToSlef(ctx, value);
				return;
			}
			goodid = eshop.getEshopiid();
			if (goodid.longValue() < 0) {// 特效物品判断是拥有特效
				if (roleData.getPackRecord().isTX(-goodid.longValue() + "")) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已拥有该特效"));
					return;
				}
			}
			jg = eshop.getEshopprice() * buyShopBean.getSum();

			// zrikka 商城购买
			goodsBuy.setGid(goodid);
			goodsBuy.setPrice(new BigDecimal(eshop.getEshopprice()));
			goodsBuy.setGoodnumber(new BigDecimal(buyShopBean.getSum()));
			goodsBuy.setNumbermoney(new BigDecimal(jg));

			if (jg <= 0) {
				return;
			}
			if (eshop.getEshoptype().equals("5")) {// 充值积分购买
				if (loginResult.getMoney() < jg) {
					return;
				}
				loginResult.setMoney((int) (loginResult.getMoney() - jg));
				MonitorUtil.getMoney().useC(jg);
				assetUpdate.setData("C=" + (-jg));
				buyType = 18;
				// zrikka 商城购买 充值积分
				goodsBuy.setBuytype(new BigDecimal(3));
			} else {
				if (loginResult.getCodecard().longValue() < jg) {
					return;
				}
				loginResult.setCodecard(new BigDecimal(loginResult.getCodecard().longValue() - jg));
				MonitorUtil.getMoney().useX(jg);
				assetUpdate.setData("X=" + (-jg));
				buyType = 17;
				
				// zrikka 商城购买 仙玉
				goodsBuy.setBuytype(new BigDecimal(2));
			}
			eshop.addPrice(buyShopBean.getSum(), jg);
		} else if (buyShopBean.getAte() == 3) {
			if (buyShopBean.getnId() == null) {
				return;
			}
			MapMonsterBean bean = MonsterUtil.getMonster(buyShopBean.getnId());
			if (bean == null || bean.getRobotType() != 2) {
				SendMessage.sendMessageToSlef(ctx, ClickMonsterAction.CHECKTS2);
				return;
			}
			Robots robots = GameServer.getAllRobot().get(bean.getRobotid() + "");
			if (robots == null) {
				return;
			}
			if (robots.getLvls() != null) {
				String value = BattleMixDeal.isLvl(loginResult.getGrade(), robots.getLvls());
				if (value != null) {
					SendMessage.sendMessageToSlef(ctx, value);
					return;
				}
			}
			Lshop lshop = bean.getShops().get(buyShopBean.getCd());
			if (lshop == null) {
				return;
			}
			String v = ClickMonsterAction.isTime20s(loginResult.getRole_id());
			if (v != null) {
				SendMessage.sendMessageToSlef(ctx, v);
				return;
			}
			goodid = lshop.getGid();
			if (goodid.longValue() < 0) {// 特效物品判断是拥有特效
				if (roleData.getPackRecord().isTX(-goodid.longValue() + "")) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已拥有该特效"));
					return;
				}
			}
			if (buyShopBean.getSum() > lshop.getlNum()) {
				assetUpdate.setMsg("单次最大购买数量" + lshop.getlNum());
				buyShopBean.setSum(lshop.getlNum());
			}
			buyShopBean.setSum(lshop.addNum(buyShopBean.getSum()));
			if (buyShopBean.getSum() == 0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("该物品已售完"));
				return;
			}
			jg = lshop.getMoney().longValue() * buyShopBean.getSum();

			// zrikka 限购
			goodsBuy.setGid(goodid);
			goodsBuy.setPrice(lshop.getMoney());
			goodsBuy.setGoodnumber(new BigDecimal(buyShopBean.getSum()));
			goodsBuy.setNumbermoney(new BigDecimal(jg));

			if (lshop.getType() == 0) {// 大话币
				if (loginResult.getGold().longValue() < jg) {
					return;
				}
				loginResult.setGold(new BigDecimal(loginResult.getGold().longValue() - jg));
				assetUpdate.setData("D=" + (-jg));
				MonitorUtil.getMoney().useD(jg);
				buyType = 20;
				
				// zrikka 限购 大话币
				goodsBuy.setBuytype(new BigDecimal(1));
			} else if (lshop.getType() == 1) {// 仙玉
				if (loginResult.getCodecard().longValue() < jg) {
					return;
				}
				loginResult.setCodecard(new BigDecimal(loginResult.getCodecard().longValue() - jg));
				MonitorUtil.getMoney().useX(jg);
				assetUpdate.setData("X=" + (-jg));
				buyType = 17;
				
				// zrikka 限购 仙玉
				goodsBuy.setBuytype(new BigDecimal(2));
			} else {
				return;
			}
			lshop.addPrice(buyShopBean.getSum(), jg);
		} else {
			shop = GameServer.getAllShopGoods().get(buyShopBean.getCd());
			if (shop == null) {
				return;
			}
			String value = BuyUtil.isLimit(loginResult, shop, buyShopBean);
			if (value != null) {
				SendMessage.sendMessageToSlef(ctx, value);
				return;
			}
			goodid = shop.getShopiid();
			if (goodid.longValue() < 0) {// 特效物品判断是拥有特效
				if (roleData.getPackRecord().isTX(-goodid.longValue() + "")) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已拥有该特效"));
					return;
				}
			}
			//获取总价
			jg = shop.getPrice(buyShopBean.getSum());
			if (jg <= 0) {
				return;
			}
			// zrikka npc商城
			goodsBuy.setGid(goodid);
			goodsBuy.setPrice(new BigDecimal(shop.getShopprice()));
			goodsBuy.setGoodnumber(new BigDecimal(buyShopBean.getSum()));
			goodsBuy.setNumbermoney(new BigDecimal(jg));
			
			String jf = null;
			if (CJS!=null&&shop.getShoptype()>=500&&shop.getShoptype()<(500+CJS.length)) {
				jf = CJS[shop.getShoptype()-500];
			}else if (shop.getShoptype() == 45) {
				jf = ST;
			} else if (shop.getShoptype() == 10) {
				jf = TT;
			} else if (shop.getShoptype() == 61) {
				jf = BZ;
			} else if (shop.getShoptype() == 120) {
				jf = DYT;
			} else if (shop.getShoptype() == 121) {
				jf = DG;
			} else if (shop.getShoptype() == 123) {
				jf = XF;
			} else if (shop.getShoptype() == 124) {
				jf = MK;
			} else if (shop.getShoptype() == 126) {
				jf = SL;
			} else if (shop.getShoptype() == 1106) {
				jf = ZZS;
			} else if (shop.getShoptype() == 515) {
				jf = LTS;
			} else if (shop.getShoptype() == 600) {
				jf = BDJZ;
			} else if (shop.getShoptype() == 601) {
				jf = XMZ;
			} else if (shop.getShoptype() == 602) {
				jf = YZF;
			}else if (shop.getShoptype() == 605) {
				jf = DNTG;
			} else if (shop.getShoptype() == 887) {
				jf = WSS;
			}  else if (shop.getShoptype() == 2027) {
				jf = QMJJ;
			} else if (shop.getShoptype() == 89) {//绑玉
				if (loginResult.getSavegold().longValue() < jg) {return;}
				loginResult.setSavegold(new BigDecimal(loginResult.getSavegold().longValue() - jg));
				assetUpdate.setData("S=" + (-jg));
				buyType = 19;
				goodsBuy.setBuytype(new BigDecimal(5));
			}else {
				if (loginResult.getGold().longValue() < jg) {return;}
				loginResult.setGold(new BigDecimal(loginResult.getGold().longValue() - jg));
				assetUpdate.setData("D=" + (-jg));
				MonitorUtil.getMoney().useD(jg);
				buyType = 20;
				// zrikka npc商城 大话币
				goodsBuy.setBuytype(new BigDecimal(1));
			}
			if (jf != null) {
				if (jf.equals(ZZS)) {
					ZZSScene zzsScene=SceneUtil.getZZS(loginResult);
					if (zzsScene==null) {return;}
                    if (zzsScene.isEnd()) {
                        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("种族赛结束后才能兑换"));
                        return;
                    }
					ZZSRole role=zzsScene.getRole(loginResult);
					if (role==null) {return;}
					if (role.getJf()<jg) {return;}
					role.setJf((int)(role.getJf()-jg));
				}else if (jf.equals(LTS)) {
					PathPoint point=LTSUtil.getLtsUtil().getJF(loginResult.getRole_id());
					if (point==null) {return;}
					if (point.getY()<jg) {return;}
					point.setY((int)(point.getY()-jg));
				}else if (jf.equals(DNTG)) {
					Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
					if (scene==null) {return;}
					DNTGScene dntgScene=(DNTGScene) scene;
                    if (dntgScene.isEnd()) {
                        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("大闹天宫结束后才能兑换"));
                        return;
                    }
					DNTGRole role=dntgScene.getRole(loginResult.getRole_id());
					if (role==null) {return;}
					if (role.getUseDNJF()<jg) {return;}
                 	role.setUseDNJF((int)(role.getUseDNJF()-jg));
				}else {
					if (loginResult.getScoretype(jf).longValue()<jg) {return;}
					// 减少角色的帮派积分
					loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(),jf+"="+jg, 3));
					assetUpdate.setData(jf+"="+(-jg));
				}
				buyType = 19;
				
				// zrikka npc商城 积分
				goodsBuy.setBuytype(new BigDecimal(5));
			}
			//添加购买数量
			isSX=shop.addPrice(buyShopBean.getSum(),jg);
		}
		// 获得购买的物品的ID查找excel表，获得物品信息
		Goodstable goodstable = GameServer.getGood(goodid);
		if (goodstable == null) {
			return;
		}
		if (buyShopBean.getAte() == 3) {
			if (assetUpdate.getMsg() != null) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(assetUpdate.getMsg());
				buffer.append("|你购买了");
				buffer.append(buyShopBean.getSum());
				buffer.append("个");
				buffer.append(goodstable.getGoodsname());
				assetUpdate.setMsg(buffer.toString());
				assetUpdate.setType(AssetUpdate.USEGOOD);
			} else {
				assetUpdate.setMsg(buyShopBean.getSum() + "个" + goodstable.getGoodsname());
			}
		} else {
			assetUpdate.setMsg(buyShopBean.getSum() + "个" + goodstable.getGoodsname());
		}
		// 添加记录
		goodstable.setRole_id(loginResult.getRole_id());
		long yid = goodid.longValue();
		for (int i = 0; i < buyShopBean.getSum(); i++) {
			if (i != 0) {
				goodstable = GameServer.getGood(goodid);
			}
			goodstable.setRole_id(loginResult.getRole_id());
			long sid = goodstable.getGoodsid().longValue();
			if (sid >= 70001 && sid <= 70030) {
				Lingbao lingbao = SplitLingbaoValue.addling(goodstable.getGoodsname(), loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null, buyShopBean.getSum(), buyType);
			} else if (sid >= 69001 && sid <= 69015) {
				Lingbao lingbao = SplitLingbaoValue.addfa(sid, loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null, buyShopBean.getSum(), buyType);
			} else if (goodstable.getType() == 825) {// 是玉符
				if (goodstable.getValue().equals("")) {
					continue;
				}
				String[] v = goodstable.getValue().split("\\|");
				int suitid = Integer.parseInt(v[0]);
				int part = Integer.parseInt(v[1]);
				int pz = Integer.parseInt(v[2]);
				PartJade jade = roleData.getPackRecord().setPartJade(suitid, part, pz, 1);
				assetUpdate.setJade(jade);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null, buyShopBean.getSum(), buyType);
			} else if (goodstable.getType() == -1) {// 是特效
				roleData.getPackRecord().addTX(-sid + "");
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, buyType);
			} else if (EquipTool.canSuper(goodstable.getType())) {// 可叠加
				int sum = yid == sid ? buyShopBean.getSum() : 1;
				// 判断该角色是否拥有这件物品
				List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), goodstable.getGoodsid());
				if (sameGoodstable.size() != 0) {
					// 修改使用次数
					int uses = sameGoodstable.get(0).getUsetime() + sum;
					sameGoodstable.get(0).setUsetime(uses);
					// 修改数据库
					AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
					assetUpdate.setGood(sameGoodstable.get(0));
					AllServiceUtil.getGoodsrecordService().insert(sameGoodstable.get(0), null, buyShopBean.getSum(), buyType);
				} else {
					goodstable.setUsetime(sum);
					// 插入数据库
					AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
					assetUpdate.setGood(goodstable);
					AllServiceUtil.getGoodsrecordService().insert(goodstable, null, buyShopBean.getSum(), buyType);
				}
				if (yid == sid) {
					break;
				}
			} else {
				goodstable.setUsetime(1);
				AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null, buyShopBean.getSum(), buyType);
			}
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		if (isSX) {
			if (shop!=null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().ShopPriceAgreement("1|"+shop.getShopid()+"|"+shop.getPrice()));
			}
		}
		// zrikka 新增商城购买信息
		AllServiceUtil.getGoodsTableService().addGoodsBuyRecord(goodsBuy);		
	}
}
