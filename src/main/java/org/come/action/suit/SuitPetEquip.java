package org.come.action.suit;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.action.npc.NpcComposeAction;
import org.come.action.reward.DrawnitemsAction;
import org.come.bean.LoginResult;
import org.come.bean.QualityClBean;
import org.come.bean.SuitOperBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Alchemy;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.GsonUtil;

import come.tool.FightingData.Battlefield;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class SuitPetEquip {

	/** 41:召唤兽装备培养 */
	public static void type41(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 消耗金钱
		BigDecimal money = new BigDecimal(1000000);
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		// 长度不足
		if (goods.size() < 2) {
			return;
		}
		// 获取当前第一个物品（召唤兽装备）
		Goodstable summonEquip = goods.get(0);
		// 判断物品是否是召唤兽装备
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		// 获取当前培养材料（513玄铁晶石）
		Goodstable summonGoods = goods.get(1);
		// 类型不对
		if (summonGoods.getType() != 513) {
			return;
		}
		// 扣除材料数量并判断是否足够
		summonGoods.goodxh(1);
		// 数量不够
		if (summonGoods.getUsetime() < 0) {
			return;
		}
		// 拆解物品value数据
		String[] value = summonEquip.getValue().split("\\|");
		// 获取通灵的属性值和装备的等级、装备的品质
		Integer mysticism = Integer.parseInt(value[5].split("=")[1]);
		Integer level = Integer.parseInt(value[0].split("=")[1]);
		Integer quality = Integer.parseInt(value[4].split("=")[1]);
		// 判断是否获取到通灵值；判断是否获取到装备的等级；判断时候获取到装备的品质
		if (mysticism == null || level == null || quality == null) {
			return;
		}
		// 判断通灵值是否满级
		if (mysticism >= 6000) {
			return;
		}
		// 添加10点通灵值
		int num = 10;
		mysticism += num;
		// 判断通灵值是否满值(1000为满值)
		if (level != 6 && mysticism >= level * 1000) {// 是否满足升级需求
			// 升级
			mysticism = 0;
			level++;
			getbasics(value, quality, level, null);
			// 获取下一个等级的装备并修改当前装备升级后的信息
			Goodstable nextSummonEquip = GameServer.getAllGoodsMap().get(summonEquip.getGoodsid().add(new BigDecimal(1)));
			summonEquip.setSkin(nextSummonEquip.getSkin());
			summonEquip.setInstruction(nextSummonEquip.getInstruction());
			summonEquip.setGoodsname(nextSummonEquip.getGoodsname());
			summonEquip.setGoodsid(nextSummonEquip.getGoodsid());
		}
		// 修改属性值：&属性1=xxx&属性2=xxx...
		String[] refinins = value[6].split("&");
		String amendRefiningAttribute = amendRefiningAttribute(refinins, summonEquip.getType(), quality, level, mysticism);
		value[6] = amendRefiningAttribute;

		value[5] = "通灵=" + mysticism;
		value[0] = "等级=" + level;

		// 修改金额
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer.length() != 0) {
				buffer.append("|");
			}
			buffer.append(value[i]);
		}
		summonEquip.setValue(buffer.toString());
		assetUpdate.setGood(summonEquip);
		assetUpdate.setMsg("培养成功,获得" + num + "点通灵值");
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 42:召唤兽装备分解 */
	public static void type42(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		Goodstable summonEquip = goods.get(0);
		// 判断是否是召唤兽装备
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		// 扣除物品
		summonEquip.goodxh(1);
		if (summonEquip.getUsetime() < 0) {
			return;
		}
		int num = 30;
		// 修改当前比斗奖章
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "比斗奖章" + "=" + num, 2));
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.setData("比斗奖章" + "=" + num);
		assetUpdate.setMsg("分解成功,获得了" + num + "个比斗奖章");
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 43:召唤兽装备重铸 */
	public static void type43(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 消耗金钱
		BigDecimal money = new BigDecimal(1000000);
		// 金钱不足
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		// 召唤兽装备 + 玄铁晶石 + 内丹精华 + 九彩*3+100万大话币
		if (goods.size() < 6) {
			return;
		}
		// 判断召唤兽装备
		Goodstable summonEquip = goods.get(0);
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		// 判断玄铁晶石
		// 获取当前培养材料（513玄铁晶石）
		Goodstable summonGoods = goods.get(1);
		// 类型不对
		if (summonGoods.getType() != 513) {
			return;
		}
		// 判断内丹精华
		Goodstable summonElixir = goods.get(2);
		if (summonElixir.getType() != 497) {
			return;
		}
		// 判断九彩
		for (int i = 3; i < goods.size(); i++) {
			if (goods.get(i).getType() != 498) {
				return;
			}
		}
		// 扣除物品
		for (int i = 1; i < goods.size(); i++) {
			goods.get(i).goodxh(1);
			if (goods.get(i).getUsetime() < 0) {
				return;
			}
		}
		String[] value = summonEquip.getValue().split("\\|");
		// 获取通灵的属性值和装备的等级、装备的品质
		Integer level = Integer.parseInt(value[0].split("=")[1]);
		Integer mysticism = Integer.parseInt(value[5].split("=")[1]);
		// 新生成品质数值
		int newQuality = Battlefield.random.nextInt(21) + 80;
		// 随机生成黄字
		String randomBasics = randomBasics();
		getbasics(value, newQuality, level, randomBasics);
		value[4] = "品质=" + newQuality;
		value[6] = petAlchemy(summonEquip.getType(), newQuality, level, mysticism);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer1.length() != 0) {
				buffer1.append("|");
			}
			buffer1.append(value[i]);
		}
		summonEquip.setValue(buffer1.toString());
		assetUpdate.setGood(summonEquip);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		assetUpdate.setMsg("#G重铸成功");
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 获取召唤兽装备的炼化属性 */
	public static String petAlchemy(long type, int pz, int lvl, int mysticism) {
		// 炼化属性
		List<Alchemy> alchemies = GameServer.getAllAlchemy().get(getTypeName(type));
		int nextInt = Battlefield.random.nextInt(3) + 1;
		ArrayList<Integer> integers = new ArrayList<>();
		int num = 0;
		while (alchemies.size() > 3 && nextInt != integers.size()) {
			num++;
			if (num > 100000) {break;}
			int nextInt2 = Battlefield.random.nextInt(alchemies.size());
			
			if (integers.contains(nextInt2)) {
				continue;
			}
			integers.add(nextInt2);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("炼化属性");
		for (int i = 0; i < integers.size(); i++) {
			buffer.append("&");
			Alchemy alchemy = alchemies.get(integers.get(i));
			if (alchemy.getAlchemykey().endsWith("等级")) {
				buffer.append(alchemy.getAlchemykey() + "=" + lvl);
			} else {
				BigDecimal add = calculateRefining(alchemy, pz, mysticism);
				buffer.append(alchemy.getAlchemykey() + "=" + add);
			}
		}
		return buffer.toString();
	}

	/** 44:召唤兽装备技能重悟 */
	public static void type44(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 消耗金钱
		BigDecimal money = new BigDecimal(5000000);
		// 金钱不足
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		// 装备+隐越神石+500万大话币
		if (goods.size() < 2) {
			return;
		}
		// 判断召唤兽装备
		Goodstable summonEquip = goods.get(0);
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		// 判断隐越神石
		// 获取当前培养材料（515隐越神石）
		Goodstable summonGoods = goods.get(1);
		// 类型不对
		if (summonGoods.getType() != 515) {
			return;
		}
		summonGoods.goodxh(1);
		if (summonGoods.getUsetime() < 0) {
			return;
		}
		// 扣除物品
		String[] value = summonEquip.getValue().split("\\|");
		String summonEquipSkill = isSummonEquipSkill(value);
		if (summonEquipSkill == null) {
			return;
		}

		boolean is = Battlefield.random.nextInt(100) < 80;
		if (is) {
			// // 重悟技能1300-1334
			int id = Battlefield.random.nextInt(36) + 1300;
			while (id == 1300 || id == 1301 || id == 1306 || id == 1307 || id == 1313 || id == 1314) {
				if (id == 1314 && Battlefield.random.nextInt(100) < 40) {
					break;
				} else if (Battlefield.random.nextInt(100) < 60) {
					break;
				}
				id = Battlefield.random.nextInt(36) + 1300;
			}
			Skill skill = GameServer.getSkill(id + "");// 根据技能id获取
			if (skill == null) {
				return;
			}
			// 召唤兽装备技能品质
			int skillQuality = Battlefield.random.nextInt(50) + 1;
			BigDecimal divide = new BigDecimal(skillQuality).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP);
			value[7] = "觉醒技&" + skill.getSkillid() + "&" + divide + "&" + summonEquipSkill.split("&")[3];
			QualityClBean qualityClBean = new QualityClBean();
			qualityClBean.setRgid(summonEquip.getRgid());
			qualityClBean.setType(44);
			StringBuffer buffer1 = new StringBuffer();
			for (int i = 0; i < value.length; i++) {
				if (buffer1.length() != 0) {
					buffer1.append("|");
				}
				buffer1.append(value[i]);
			}
			qualityClBean.setNewAttr(value[7]);
			// 保存
			QualityCPool.getcPool().addExtra(qualityClBean);
			SendMessage.sendMessageToSlef(ctx, Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(qualityClBean)));
		}

		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);

		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		assetUpdate.setMsg(is ? null : "重悟技能失败");
		SuitComposeAction.saveGoods(goods, false);

		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}

	/** 45:召唤兽装备开启觉醒技 */
	public static void type45(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 消耗金钱
		BigDecimal money = new BigDecimal(10000000);
		// 金钱不足
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		// 召唤兽装备+千年混石+1000万大话币
		if (goods.size() < 2) {
			return;
		}
		// 判断召唤兽装备
		Goodstable summonEquip = goods.get(0);
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		// 判断千年混石
		// 获取当前培养材料（514千年混石）
		Goodstable summonGoods = goods.get(1);
		// 类型不对
		if (summonGoods.getType() != 514) {
			return;
		}
		summonGoods.goodxh(1);
		if (summonGoods.getUsetime() < 0) {
			return;
		}
		String[] value = summonEquip.getValue().split("\\|");
		if (isSummonEquipSkill(value) != null) {
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		int nextInt = Battlefield.random.nextInt(100);
		if (nextInt < 15) {// 成功开启
			// 开启技能
			int id = Battlefield.random.nextInt(36) + 1300;
			Skill skill = GameServer.getSkill(id + "");// 根据技能id获取
			if (skill == null) {
				return;
			}
			// 召唤兽装备技能品质
			int skillQuality = Battlefield.random.nextInt(50) + 1;
			BigDecimal divide = new BigDecimal(skillQuality).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP);
			String skillMessage = "觉醒技&" + skill.getSkillid() + "&" + divide + "&" + 0;
			StringBuffer buffer1 = new StringBuffer();
			for (int i = 0; i < value.length; i++) {
				if (buffer1.length() != 0) {
					buffer1.append("|");
				}
				buffer1.append(value[i]);
			}
			buffer1.append("|" + skillMessage);
			summonEquip.setValue(buffer1.toString());
			// 保存数据
			assetUpdate.setMsg("#G开启成功" + skill.getSkillname());
			assetUpdate.setGood(summonEquip);
			SuitComposeAction.saveGoods(goods, false);
		} else {// 开启失败
			assetUpdate.setMsg("开启失败");
			SuitComposeAction.saveGoods(goods, true);
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 46:召唤兽装备提升觉醒技等级 */
	public static void type46(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		PartJade jade = suitOperBean.getJade();
		if (jade == null) {
			return;
		}
		// 奖章数
		int suitid = jade.getSuitid();
		if (new BigDecimal(suitid).compareTo(loginResult.getScoretype("比斗奖章")) > 0) {
			return;
		}
		// 召唤兽装备+比斗奖章+ 奖章数*6000大话币
		BigDecimal money = new BigDecimal(suitid * 6000);
		if (money.compareTo(loginResult.getGold()) > 0) {
			return;
		}
		if (goods.size() < 1) {
			return;
		}
		// 判断召唤兽装备
		Goodstable summonEquip = goods.get(0);
		if (!Goodtype.isSummonEquip(summonEquip.getType())) {
			return;
		}
		String[] value = summonEquip.getValue().split("\\|");
		// 召唤兽装备技能
		String summonEquipSkill = isSummonEquipSkill(value);
		if (summonEquipSkill == null) {
			return;
		}
		String[] skillMessage = summonEquipSkill.split("&");
		// 当前经验
		long parseLong = Long.parseLong(skillMessage[3]);
		if (parseLong >= 48450) {
			return;
		}
		if (Battlefield.random.nextInt(100) < 5) {// 双倍经验
			parseLong += suitid;
		}
		parseLong += suitid;
		if (parseLong > 48450) {
			parseLong = 48450;
		}
		skillMessage[3] = String.valueOf(parseLong);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < skillMessage.length; i++) {
			if (buffer.length() != 0) {
				buffer.append("&");
			}
			buffer.append(skillMessage[i]);
		}
		value[7] = buffer.toString();
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer1.length() != 0) {
				buffer1.append("|");
			}
			buffer1.append(value[i]);
		}
		// buffer1.append("|" + skillMessage);
		summonEquip.setValue(buffer1.toString());
		QualityClBean qualityClBean = new QualityClBean();
		qualityClBean.setRgid(summonEquip.getRgid());
		qualityClBean.setType(46);
		qualityClBean.setNewAttr(value[7]);
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "比斗奖章" + "=" + suitid, 3));
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.updata("比斗奖章=" + (-suitid));
		assetUpdate.setGood(summonEquip);
		assetUpdate.setMsg("此次升级了" + suitid + "经验");
		SuitComposeAction.saveGoods(goods, false);
		// SendMessage.sendMessageToSlef(ctx,
		// Agreement.getAgreement().PromptAgreement("此次升级了" + suitid + "经验"));
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		SendMessage.sendMessageToSlef(ctx, Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(qualityClBean)));
	}

	/** 修改黄字属性 */
	public static void getbasics(String[] value, Integer quality, Integer level, String basicsName) {
		// 黄字属性
		String[] basics = value[3].split("=");
		// 修改黄字属性：黄字属性公式= 品质/100*等级*10(精度不太会缺失)
		basics[1] = (int) (quality / 100D * level * 10) + "";
		// 修改数值
		if (basicsName != null) {
			value[3] = basicsName + "=" + basics[1];
		} else {
			value[3] = basics[0] + "=" + basics[1];
		}
	}

	/** 根据类型获取对应的装备类型名称 */
	public static String getTypeName(long type) {
		if (type == 510) {
			return "兽环";
		} else if (type == 511) {
			return "兽铃";
		} else if (type == 512) {
			return "兽甲";
		}
		return null;
	}

	/**
	 * 修改炼化属性
	 * 
	 * @param refinins
	 *            炼化属性值
	 * @param type
	 *            装备类型
	 * @param quality
	 *            装备品质
	 * @param level
	 *            装备等级
	 */
	public static String amendRefiningAttribute(String[] refinins, long type, Integer quality, Integer level, int mysticism) {
		// 炼化属性&xxx=xx&xxx=xx
		for (int i = 1; i < refinins.length; i++) {
			String typeName = getTypeName(type);
			if (typeName == null) {
				typeName = "兽环";
			}
			if (typeName != null) {
				if (!refinins[i].split("=")[0].endsWith("等级")) {
					Alchemy alchemy = NpcComposeAction.getAlchemy(typeName, refinins[i].split("=")[0]);
					if (alchemy == null) {
						continue;
					} else {
						BigDecimal add = calculateRefining(alchemy, quality, mysticism);
						refinins[i] = refinins[i].split("=")[0] + "=" + add;
					}
				} else {
					refinins[i] = refinins[i].split("=")[0] + "=" + level;
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < refinins.length; i++) {
			if (buffer.length() != 0) {
				buffer.append("&");
			}
			buffer.append(refinins[i]);
		}
		return buffer.toString();
	}

	/** 召唤兽装备是否有技能 */
	public static String isSummonEquipSkill(String[] value) {
		// 召唤兽装备判断是否已经有觉醒技
		for (int i = 0; i < value.length; i++) {
			if (value[i].startsWith("觉醒技")) {
				return value[i];
			}
		}
		return null;
	}

	/** 根骨 灵性 力量 敏捷里面随机一个 */
	public static String randomBasics() {
		int nextInt = Battlefield.random.nextInt(4);
		if (nextInt == 0) {
			return "根骨";
		} else if (nextInt == 1) {
			return "灵性";
		} else if (nextInt == 2) {
			return "力量";
		} else {
			return "敏捷";
		}
	}

	/**
	 * 计算炼化属性<br>
	 * 炼化非内丹等级的数值公式 （ （最大值-最小值） x 通灵值/6000+ 最小值）* 品质/100
	 */
	public static BigDecimal calculateRefining(Alchemy alchemy, Integer quality, int mysticism) {
		BigDecimal sv = new BigDecimal(alchemy.getAlchemysv());
		BigDecimal mv = new BigDecimal(alchemy.getAlchemymv());
		BigDecimal add = ((mv.subtract(sv)).multiply(new BigDecimal(mysticism)).divide(new BigDecimal(6000), 10, BigDecimal.ROUND_HALF_UP).add(sv)).multiply(new BigDecimal(quality)).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_DOWN);
		return add;
	}
	/** 经验换算等级 */
	public static int expChangeLevel(long exp) {
		int i = 1;
		while (i <= 20) {
			long num = (i*250+50+300)*i/2;
			if (num > exp) {
				return i;
			}
			i++;
		}
		return 20;
	}

	/** 获取当前等级总经验 */
	public static long getMaxExp(long level) {
		return level * 250 + 50;
	}

	/** 获取当前经验 */
	public static long getNowExp(long exp) {
		long maxExp = getMaxExp(expChangeLevel(exp));
		long num = (maxExp*250+50+300) * maxExp / 2;
		return exp - num;
	}
}
