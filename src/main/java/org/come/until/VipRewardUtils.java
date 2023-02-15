package org.come.until;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.server.GameServer;
import org.come.tool.EquipTool;

import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Stall.AssetUpdate;

/**
 * vip系统 工具类
 * 
 * @author zengr
 * 
 */
public class VipRewardUtils {

	/**
	 * 根据等级获取 (充值等级 以及 充值金额 )
	 * 
	 * @param grade
	 * @return
	 */
	public static String[] gradeAndMoney(String grade) {
		String gradeNum = "0";
		String money = "0";

		String[] split = grade.split("v");
		gradeNum = split[1];

		String[] arr = { gradeNum, money };
		return arr;
	}

	/**
	 * 每日充值
	 * 
	 * @param grade
	 * @return
	 */
	public static int everydayRecharge(String grade) {
		int gradeNum = 0;
		String[] split = grade.split("v");
		gradeNum = Integer.valueOf(split[1]);
		return gradeNum;
	}

	/**
	 * 连续充值
	 * 
	 * @param grade
	 * @return
	 */
	public static int continuityRecharge(String grade) {
		int gradeNum = 0;
		String[] split = grade.split("v");
		gradeNum = Integer.valueOf(split[1]);
		return gradeNum;
	}

	/**
	 * 根据等级获取奖励等级
	 * 
	 * @param grade
	 * @return
	 */
	public static int getRoleGradeReward(int grade) {
		if (grade >= 0 && grade < 70) {
			return 1;
		} else if (grade >= 70 && grade < 102) {
			return 2;
		} else if (grade >= 102 && grade < 188) {
			return 3;
		} else if (grade >= 188 && grade < 208) {
			return 4;
		} else if (grade >= 208 && grade < 296) {
			return 5;
		} else if (grade >= 296 && grade < 336) {
			return 6;
		} else if (grade >= 336 && grade < 419) {
			return 7;
		} else if (grade >= 419 && grade < 459) {
			return 8;
		} else if (grade >= 459 && grade < 480) {
			return 9;
		} else if (grade >= 480) {
			return 10;
		}
		return 0;
	}

	/**
	 * 判断是否已领取对应奖励
	 * 
	 * @param info
	 *            信息 1=1|2|3&&2=1|2|3&&3=1|2|3|4..&&4=1|2|3|4..10
	 * @param action
	 *            操作类型 (1=表示 vip特权,2=表示每日充值领取等级包,3=表示连续充值,4=表示免费冲级礼包,5=表示小资或者土豪)
	 * @param grade
	 *            奖励等级 (1,2,3,...)
	 * @return false 未领取 / true 已领取
	 */
	public static boolean checkYesOrNo(String info, String action, String grade) {
		if (info == null) {
			info = "";
		}
		String[] infoArr = info.split("&&");
		// 遍历
		for (int i = 0; i < infoArr.length; i++) {
			// 根据 = 切割获得 ( [0]操作类型 , [1]奖励等级集合 )
			String[] arr = infoArr[i].split("=");
			if (arr[0].equals(action)) {
				if (arr.length == 1) {
					// 还未领取过奖励
					return false;
				} else {
					// 获得已领取的奖励集合 (根据 | 切割)
					String[] gradeArr = arr[1].split("\\|");
					for (int j = 0; j < gradeArr.length; j++) {
						if (gradeArr[j].equals(grade)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 设置领取奖励记录
	 * 
	 * @param roleInfo
	 * @param action
	 * @param grade
	 */
	public static void setVipget(LoginResult roleInfo, String action,
			String grade) {
		String vipget = roleInfo.getVipget();
		StringBuffer str = new StringBuffer();
		if (vipget == null || "".equals(vipget)) {
			str.append(action);
			str.append("=");
			str.append(grade);
		} else {
			String[] vipgetArr = vipget.split("&&");
			int flag = 0;
			for (int i = 0; i < vipgetArr.length; i++) {
				String[] arr = vipgetArr[i].split("=");
				if (i == 0) {
					str.append(arr[0]);
					str.append("=");
				} else {
					str.append("&&");
					str.append(arr[0]);
					str.append("=");
				}
				String[] rew = arr[1].split("\\|");
				for (int j = 0; j < rew.length; j++) {
					str.append(rew[j]);
					if (j < rew.length - 1) {
						str.append("|");
					}
				}
				if (arr[0].equals(action)) {
					str.append("|");
					str.append(grade);
					flag = 1;
				}
			}
			if (flag == 0) {
				str.append("&&");
				str.append(action);
				str.append("=");
				str.append(grade);
			}
		}
		roleInfo.setVipget(str.toString());
	}

	/**
	 * 将物品给玩家
	 * 
	 * @param goodid
	 * @param goodstable
	 * @param loginResult
	 * @param goodSum
	 * @param assetUpdate
	 * @param buyType
	 * @param roleData
	 */
	public static void giveRoleGoods(BigDecimal goodid, Goodstable goodstable,
			LoginResult loginResult, int goodSum, AssetUpdate assetUpdate,
			int buyType, RoleData roleData) {
		goodstable.setRole_id(loginResult.getRole_id());
		long yid = goodid.longValue();
		for (int i = 0; i < goodSum; i++) {
			if (i != 0) {
				goodstable = GameServer.getGood(goodid);
			}
			goodstable.setRole_id(loginResult.getRole_id());
			long sid = goodstable.getGoodsid().longValue();
			if (sid >= 70001 && sid <= 70030) {
				Lingbao lingbao = SplitLingbaoValue.addling(
						goodstable.getGoodsname(), loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,
						goodSum, buyType);
			} else if (sid >= 69001 && sid <= 69015) {
				Lingbao lingbao = SplitLingbaoValue.addfa(sid,
						loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,
						goodSum, buyType);
			} else if (goodstable.getType() == 825) {// 是玉符
				if (goodstable.getValue().equals("")) {
					continue;
				}
				String[] v = goodstable.getValue().split("\\|");
				int suitid = Integer.parseInt(v[0]);
				int part = Integer.parseInt(v[1]);
				int pz = Integer.parseInt(v[2]);
				PartJade jade = roleData.getPackRecord().setPartJade(suitid,
						part, pz, 1);
				assetUpdate.setJade(jade);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,
						goodSum, buyType);
			} else if (goodstable.getType() == -1) {// 是特效
				roleData.getPackRecord().addTX(-sid + "");
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,
						1, buyType);
			} else if (EquipTool.canSuper(goodstable.getType())) {// 可叠加
				int sum = yid == sid ? goodSum : 1;
				// 判断该角色是否拥有这件物品
				List<Goodstable> sameGoodstable = AllServiceUtil
						.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(
								loginResult.getRole_id(),
								goodstable.getGoodsid());
				if (sameGoodstable.size() != 0) {
					// 修改使用次数
					int uses = sameGoodstable.get(0).getUsetime() + sum;
					sameGoodstable.get(0).setUsetime(uses);
					// 修改数据库
					AllServiceUtil.getGoodsTableService().updateGoodRedis(
							sameGoodstable.get(0));
					assetUpdate.setGood(sameGoodstable.get(0));
					AllServiceUtil.getGoodsrecordService().insert(
							sameGoodstable.get(0), null, goodSum, buyType);
				} else {
					goodstable.setUsetime(sum);
					// 插入数据库
					AllServiceUtil.getGoodsTableService().insertGoods(
							goodstable);
					assetUpdate.setGood(goodstable);
					AllServiceUtil.getGoodsrecordService().insert(goodstable,
							null, goodSum, buyType);
				}
				if (yid == sid) {
					break;
				}
			} else {
				goodstable.setUsetime(1);
				AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,
						goodSum, buyType);
			}
		}
	}

	public static void main(String[] args) {
		String str = "v11";
		String[] split = str.split("v");
		System.out.println(split[1]);
		// LoginResult login = new LoginResult();
		// login.setVipget("1=3&&2=2|1");
		// setVipget(login, "1", "5");
		// System.out.println(login.getVipget());
	}

}
