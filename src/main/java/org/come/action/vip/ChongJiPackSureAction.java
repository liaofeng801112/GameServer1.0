package org.come.action.vip;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.entity.ChongjipackBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.VipRewardUtils;

import come.tool.Stall.AssetUpdate;

/** 冲级礼包兑换 */
public class ChongJiPackSureAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// 判断数据是否为空
		if (message == null || "".equals(message)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("操作错误"));
			return;
		}
		// 获取该角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo == null) {
			return;
		}
		// 1 表示小资礼包 / 2 表示土豪礼包
		int lowOrHihtpack = roleInfo.getLowOrHihtpack();
		// message 的格式 packgrade | theNumber
		String[] mesArr = message.split("\\|");
		// 1 代表免费， 2 代表小资，3 代表土豪
		String packgrade = mesArr[0];
		// 判断是否满足条件领取
		if ("2".equals(packgrade)) {
			if (lowOrHihtpack != 1) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
				return;
			}
		} else if ("3".equals(packgrade)) {
			if (lowOrHihtpack != 2) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
				return;
			}
		}
		// 为对应的领取等级 礼包序号为 1-10
		String theNumber = mesArr[1];
		// 判断是否已经领取对应的礼包
		String roleVipInfo = roleInfo.getVipget();
		int roGraReward = VipRewardUtils.getRoleGradeReward(roleInfo.getGrade());// 角色奖励等级
		if (roGraReward < Integer.valueOf(theNumber)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您当前不满足领取条件!"));
			return;
		}

		String packType = "5";
		if ("1".equals(packgrade)) {
			packType = "4";
		}

		if (VipRewardUtils.checkYesOrNo(roleVipInfo, packType, theNumber)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您已经领取该礼包!"));
			return;
		}

		// 满足兑换条件
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(30);
		StringBuffer vipInfo = new StringBuffer();
		// 其中 date 格式 封装成 vip|Vipget。
		VipRewardUtils.setVipget(roleInfo, packType, theNumber);
		vipInfo.append(roleInfo.getVipget());
		assetUpdate.setVip(vipInfo.toString());

		// ------将奖励的东西给玩家----------
		// 获取冲级礼包列表
		List<ChongjipackBean> chongList = GameServer.getPackgradecontrol().get(Integer.valueOf(packgrade));
		for (int i = 0; i < chongList.size(); i++) {
			if (theNumber.equals(chongList.get(i).getPackgradetype() + "")) {
				// 物品=80655$5&80656$5
				String rewardStr = chongList.get(i).getPackgoods();
				// 获取的物品
				chongjiPack(rewardStr, ctx, roleInfo, assetUpdate);
				break;
			}
		}
		// -----------------------------
	}

	/**
	 * 
	 * @param value
	 * @param ctx
	 * @param loginResult
	 * @param assetUpdate
	 */
	public void chongjiPack(String value, ChannelHandlerContext ctx, LoginResult loginResult, AssetUpdate assetUpdate) {
		try {
			String[] v = value.split("\\|");
			StringBuffer msg = new StringBuffer();
			for (int i = 0; i < v.length; i++) {
				if (v[i].startsWith("物品")) {
					v = v[i].split("=")[1].split("\\&");
					for (int j = 0; j < v.length; j++) {
						Goodstable goodstable = new Goodstable();
						String[] v4 = v[j].split("\\$");
						BigDecimal id = new BigDecimal(v4[0]);
						int sum = Integer.parseInt(v4[1]);
						Goodstable good = GameServer.getGood(id);
						if (good == null) {
							continue;
						}
						good.setRole_id(loginResult.getRole_id());
						if (good.getType() == 60001 || good.getType() == 60002) {
							goodstable.setUsetime(1);
							goodstable.setValue(good.getValue());
							goodstable.setType(good.getType());
							continue;
						}
						if (EquipTool.canSuper(good.getType())) {
							List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), good.getGoodsid());
							if (sameGoodstable.size() != 0) {
								sameGoodstable.get(0).setUsetime(sameGoodstable.get(0).getUsetime() + sum);
								AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
								good = sameGoodstable.get(0);
							} else {
								good.setUsetime(sum);
								AllServiceUtil.getGoodsTableService().insertGoods(good);
							}
							assetUpdate.setGood(good);
							msg.append(good.getGoodsname());
							msg.append("|");
							// AddGoodUtil.addGood(ctx,good);
							AllServiceUtil.getGoodsrecordService().insert(good, null, sum, 3);
						} else {
							for (int k = 0; k < sum; k++) {
								AllServiceUtil.getGoodsTableService().insertGoods(good);
								assetUpdate.setGood(good);
								msg.append(good.getGoodsname());
								msg.append("|");
								// AddGoodUtil.addGood(ctx,good);
								AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 3);
							}
						}
					}
					break;
				}
			}
			assetUpdate.setMsg(msg.toString());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
	}
}
