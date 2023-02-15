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
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.FightingData.Battlefield;
import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class StarCard {

	/** 51:星卡培养 */
	public static void type51(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
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
		// 获取当前第一个物品（星卡）
		Goodstable starCardEquip = goods.get(0);
		// 判断物品是否是星卡
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 获取当前培养材料（522演星册）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 522) {
			return;
		}
		// 扣除材料数量并判断是否足够
		materialGoods.goodxh(1);
		// 数量不够
		if (materialGoods.getUsetime() < 0) {
			return;
		}
		// 拆解物品value数据
		String[] value = starCardEquip.getValue().split("\\|");
		// 获取通灵的属性值和装备的等级、装备的品质
		String[] level = value[0].split("=")[1].split("/");
		// 当前等级
		Integer lvlNow = Integer.parseInt(level[0]);
		// 当前神力
		int power = Integer.parseInt(value[1].split("=")[1]);

		if (power >= lvlNow * 300) {
			return;
		}
		// 获取材料增加的神力
		Integer addPower = Integer.parseInt(materialGoods.getValue().split("=")[1]);
		power += addPower;
		if (power >= lvlNow * 300) {
			power = lvlNow * 300;
		}
		// 替换数值
		value[1] = "神力=" + power;
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
		starCardEquip.setValue(buffer.toString());
		assetUpdate.setGood(starCardEquip);
		assetUpdate.setMsg("培养成功,获得" + addPower + "点神力");
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 52:星卡升级 */
	public static void type52(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 长度不足
		if (goods.size() < 2) {
			return;
		}
		Goodstable starCardEquip = goods.get(0);
		// 判断物品是否是星卡
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 获取当前培养材料（500矿石）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 500) {
			return;
		}
		int materialsLvl = Integer.parseInt(materialGoods.getValue().split("=")[1]);
		if (materialsLvl != 11) {
			return;
		}
		// 扣除材料数量并判断是否足够
		materialGoods.goodxh(1);
		// 数量不够
		if (materialGoods.getUsetime() < 0) {
			return;
		}
		// 拆解物品value数据
		String[] value = starCardEquip.getValue().split("\\|");
		// 获取通灵的属性值和装备的等级、装备的品质
		String[] level = value[0].split("=")[1].split("/");
		// 当前等级
		Integer lvlNow = Integer.parseInt(level[0]);
		// 当前等级上限
		Integer lvlMax = Integer.parseInt(level[1]);
		// 当前神力
		int power = Integer.parseInt(value[1].split("=")[1]);

		if (power < lvlNow * 300) {
			return;
		}
		if (lvlNow >= lvlMax) {
			return;
		}
		// 消耗金钱
		BigDecimal money = new BigDecimal(lvlNow * 20000000);
		if (loginResult.getGold().compareTo(money) < 0) {
			return;
		}
		lvlNow++;
		value[3] = upgradeAttribute(value, lvlNow);
		value[1] = "神力=0";
		value[0] = "等级=" + lvlNow + "/" + lvlMax;

		changeStarCardSkin(starCardEquip, lvlNow);

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
		starCardEquip.setValue(buffer.toString());
		assetUpdate.setGood(starCardEquip);
		assetUpdate.setMsg("升级成功");
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 53:星卡重洗神通 */
	public static void type53(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
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
		// 星卡 +浑天石
		if (goods.size() < 2) {
			return;
		}
		// 判断星卡
		Goodstable starCardEquip = goods.get(0);
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 判断浑天石
		// 获取当前培养材料（524浑天石）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 524) {
			return;
		}
		// 扣除物品
		materialGoods.goodxh(1);
		if (materialGoods.getUsetime() < 0) {
			return;
		}
//		UPDATE ROLE_TABLE SET UPTIME = '0', TASKDATA ='';
		String[] value = starCardEquip.getValue().split("\\|");
		// 修改炼化属性
		String refining = value[3];
		if (!refining.startsWith("炼化属性")) {
			return;
		}
		// 获取通灵的属性值和装备的等级、装备的品质
		String[] level = value[0].split("=")[1].split("/");
		// 当前等级
		Integer lvlNow = Integer.parseInt(level[0]);
		List<Alchemy> list = GameServer.getAllAlchemy().get("星卡");
		Alchemy alchemy1 = list.get(Battlefield.random.nextInt(list.size()));
		Alchemy alchemy2 = list.get(Battlefield.random.nextInt(list.size()));
		int aptitude = Battlefield.random.nextInt(31) + 70;
		value[3] = anewRefining(lvlNow, aptitude, alchemy1, alchemy2, 1);

		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer1.length() != 0) {
				buffer1.append("|");
			}
			buffer1.append(value[i]);
		}
		QualityClBean qualityClBean = new QualityClBean();
		qualityClBean.setRgid(starCardEquip.getRgid());
		qualityClBean.setType(53);
		qualityClBean.setNewAttr(value[3]);
		// 保存
		QualityCPool.getcPool().addExtra(qualityClBean);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		SuitComposeAction.saveGoods(goods, false);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		SendMessage.sendMessageToSlef(ctx, Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(qualityClBean)));
	}

	/** 54:星卡重洗五行 */
	public static void type54(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
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
		// 星符+523易象石+金钱
		if (goods.size() < 2) {
			return;
		}
		// 判断星卡
		Goodstable starCardEquip = goods.get(0);
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 判断隐越神石
		// 获取当前培养材料（515隐越神石）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 523) {
			return;
		}
		materialGoods.goodxh(1);
		if (materialGoods.getUsetime() < 0) {
			return;
		}
		// 重洗五行属性
		String[] value = starCardEquip.getValue().split("\\|");
		// 五行属性&金=xx&木=xx&水=xx&火=xx&土=xx
		value[4] = anewFiveElements();
		// 星卡技能品质
		QualityClBean qualityClBean = new QualityClBean();
		qualityClBean.setRgid(starCardEquip.getRgid());
		qualityClBean.setType(54);
		qualityClBean.setNewAttr(value[4]);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		loginResult.setGold(loginResult.getGold().subtract(money));
		MonitorUtil.getMoney().useD(money.longValue());
		assetUpdate.updata("D=-" + money);
		SuitComposeAction.saveGoods(goods, false);
		// 保存
		QualityCPool.getcPool().addExtra(qualityClBean);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		SendMessage.sendMessageToSlef(ctx, Agreement.ExtrattroperAgreement(GsonUtil.getGsonUtil().getgson().toJson(qualityClBean)));
	}

	/** 55:星卡练星 */
	public static void type55(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 星符+521炼星石
		if (goods.size() < 2) {
			return;
		}
		// 判断星卡
		Goodstable starCardEquip = goods.get(0);
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 判断521炼星石
		// 获取当前培养材料（521炼星石）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 521) {
			return;
		}

		String[] value = starCardEquip.getValue().split("\\|");
		// 获取通灵的属性值和装备的等级、装备的品质
		String[] level = value[0].split("=")[1].split("/");
		// 当前等级
		Integer lvlNow = Integer.parseInt(level[0]);
		// 最大等级
		Integer lvlMax = Integer.parseInt(level[1]);

		if (lvlMax >= 14) {
			return;
		}

		int num = lvlMax / 2 + 2;
		materialGoods.goodxh(num);
		if (materialGoods.getUsetime() < 0) {
			return;
		}
		boolean is = false;
		if (Battlefield.isV(20-lvlMax/2)) {
			lvlMax++;
			value[0] = "等级=" + lvlNow + "/" + lvlMax;
			is = true;
		}
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer1.length() != 0) {
				buffer1.append("|");
			}
			buffer1.append(value[i]);
		}
		starCardEquip.setValue(buffer1.toString());
		assetUpdate.setGood(starCardEquip);
		SuitComposeAction.saveGoods(goods, false);
		assetUpdate.setMsg(is ? "提升等级成功" : "提升等级失败");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 56:斗转星移 */
	public static void type56(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		// 星符+521炼星石
		if (goods.size() < 2) {
			return;
		}
		// 判断星卡
		Goodstable starCardEquip = goods.get(0);
		if (starCardEquip.getType() != 520) {
			return;
		}
		// 判断521炼星石
		// 获取当前培养材料（521炼星石）
		Goodstable materialGoods = goods.get(1);
		// 类型不对
		if (materialGoods.getType() != 520) {
			return;
		}
		if (starCardEquip.getRgid().compareTo(materialGoods.getRgid()) == 0) {
			return;
		}
		PartJade jade = suitOperBean.getJade();
		if (jade == null) {
			return;
		}
		int suitid = jade.getSuitid();
		int partId = jade.getPartId();
		if (suitid < 0 || suitid > 3 || partId < 0 || partId > 3) {
			return;
		}
		String[] value = starCardEquip.getValue().split("\\|");
		String[] splitOne = value[3].split("&");
		String[] splitTwo = materialGoods.getValue().split("\\|")[3].split("&");
		if (splitOne.length < 5 || splitTwo.length < 5) {
			return;
		}
		String[] splitSatrArrayOne = splitOne[4].split("=");
		String[] splitSatrArrayTwo = splitTwo[4].split("=");
		if (!(isfiveElements(splitSatrArrayOne[1]) && isfiveElements(splitSatrArrayTwo[1]))) {
			return;
		}
		splitSatrArrayOne[4 + suitid] = splitSatrArrayTwo[4 + partId];
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < splitSatrArrayOne.length; i++) {
			if (buffer.length() != 0) {
				buffer.append("=");
			}
			buffer.append(splitSatrArrayOne[i]);
		}
		splitOne[4] = buffer.toString();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < splitOne.length; i++) {
			if (stringBuffer.length() != 0) {
				stringBuffer.append("&");
			}
			stringBuffer.append(splitOne[i]);
		}
		value[3] = stringBuffer.toString();
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer1.length() != 0) {
				buffer1.append("|");
			}
			buffer1.append(value[i]);
		}
		starCardEquip.setValue(buffer1.toString());
		assetUpdate.setGood(starCardEquip);
		materialGoods.setUsetime(materialGoods.getUsetime() - 1);
		SuitComposeAction.saveGoods(goods, false);
		assetUpdate.setMsg("成功斗转星移");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 57:星卡魂归 */
	public static void type57(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		if (goods.size() < 1) {
			return;
		}
		int num = 0;
		for (int i = 0; i < goods.size(); i++) {
			Goodstable goodstable = goods.get(i);
			if (goodstable.getType() != 520) {
				return;
			}
			goodstable.goodxh(1);
			if (goodstable.getUsetime() < 0) {
				return;
			}
			num += 20;
		}
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "星芒" + "=" + num, 2));
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		SuitComposeAction.saveGoods(goods, false);
		assetUpdate.updata("星芒" + "=" + num);
		assetUpdate.setGoods(goods);
		assetUpdate.setMsg("魂归成功");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/** 58:星卡删除星阵属性 */
	public static void type58(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		if (goods.size() < 1) {
			return;
		}
		Goodstable goodstable = goods.get(0);
		if (goodstable.getType() != 520) {
			return;
		}
		String[] value = goodstable.getValue().split("\\|");
		// 炼化属性
		String[] string = value[3].split("&");
		if (string.length < 4) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < string.length - 1; i++) {
			if (buffer.length() != 0) {
				buffer.append("&");
			}
			buffer.append(string[i]);
		}
		value[3] = buffer.toString();
		StringBuffer buffer2 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer2.length() != 0) {
				buffer2.append("|");
			}
			buffer2.append(value[i]);
		}
		goodstable.setValue(buffer2.toString());
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.setGood(goodstable);
		SuitComposeAction.saveGoods(goods, false);
		assetUpdate.setMsg("删除成功");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 59:星卡战力补充 */
	public static void type59(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		if (roleData == null) {
			return;
		}
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods == null) {
			return;
		}
		if (goods.size() < 1) {
			return;
		}
		Goodstable goodstable = goods.get(0);
		if (goodstable.getType() != 520) {
			return;
		}
		PartJade jade = suitOperBean.getJade();
		if (jade == null) {
			return;
		}
		String[] value = goodstable.getValue().split("\\|");
		BigDecimal money = null;
		if (jade.getSuitid() == 0) {
			money = new BigDecimal(1000000);
			if (loginResult.getGold().compareTo(money) < 0) {
				return;
			}
			loginResult.setGold(loginResult.getGold().subtract(money));
			MonitorUtil.getMoney().useD(money.longValue());
		} else if (jade.getSuitid() == 1) {
			money = new BigDecimal(10);
			if (loginResult.getScoretype("星芒").compareTo(money) < 0) {
				return;
			}
			loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(), "星芒" + "=" + money.toString(), 3));
		}
		int parseInt = Integer.parseInt(value[2].split("=")[1]);
		parseInt += 100;
		value[2] = "战力=" + parseInt;
		// 炼化属性
		StringBuffer buffer2 = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if (buffer2.length() != 0) {
				buffer2.append("|");
			}
			buffer2.append(value[i]);
		}
		goodstable.setValue(buffer2.toString());
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		if (jade.getSuitid() == 0) {
			assetUpdate.updata("D=-" + money);
		} else {
			assetUpdate.updata("星芒" + "=-" + money.toString());
		}
		assetUpdate.setGood(goodstable);
		SuitComposeAction.saveGoods(goods, false);
		assetUpdate.setMsg("补充成功");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/** 计算炼化属性 */
	public static BigDecimal calculateRefining(Alchemy alchemy, int aptitude, int lvlNow, boolean type) {
		BigDecimal sv = new BigDecimal(lvlNow);
		BigDecimal mv = new BigDecimal(alchemy.getAlchemymv());
		BigDecimal add;
		if (type) {
			add = (mv.multiply(sv)).multiply(new BigDecimal(aptitude)).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_DOWN);
		} else {
			add = (mv.multiply(sv)).multiply(new BigDecimal(aptitude)).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(14)).divide(new BigDecimal(15), 10, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_DOWN);
		}
		return add;
	}

	/**
	 * 星卡重新获取炼化属性
	 * 
	 * @param lvlNow
	 *            等级
	 * @param aptitude
	 *            资质
	 * @param alchemy1
	 *            属性1
	 * @param alchemy2
	 *            属性2
	 * @param type
	 *            是否重新获得星阵属性 0不获取 1获取
	 * @return 炼化属性整条
	 */
	public static String anewRefining(int lvlNow, int aptitude, Alchemy alchemy1, Alchemy alchemy2, int type) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("炼化属性&资质=" + aptitude + "&");
		if (alchemy2.getAlchemykey().equals(alchemy1.getAlchemykey())) {
			// 最大值*等级*神通品质/100 (保留一位小数)
			BigDecimal add1 = calculateRefining(alchemy1, aptitude, lvlNow, true);
			buffer.append(alchemy1.getAlchemykey() + "=" + add1 + "&");
			BigDecimal add2 = calculateRefining(alchemy2, aptitude, lvlNow, false);
			buffer.append(alchemy2.getAlchemykey() + "=" + add2);
		} else {
			BigDecimal add1 = calculateRefining(alchemy1, aptitude, lvlNow, true);
			buffer.append(alchemy1.getAlchemykey() + "=" + add1 + "&");
			BigDecimal add2 = calculateRefining(alchemy2, aptitude, lvlNow, true);
			buffer.append(alchemy2.getAlchemykey() + "=" + add2);
		}
		if (type == 1) {
			if (Battlefield.isV(3.5)) {
				String starArrayName = getStarArrayName();
				buffer.append("&星阵属性=" + starArrayName);
			}
		}
		return buffer.toString();
	}

	/** 随机星阵之力 */
	public static String getStarArrayName() {
		int nextInt = Battlefield.random.nextInt(9);

		if (nextInt == 0) {
			return "朱雀=火=忽视抗鬼火,抵抗灵宝伤害" + randomfourPlace();
		} else if (nextInt == 1) {
			return "玄武=土=忽视抗遗忘,忽视抗仙法" + randomfourPlace();
		} else if (nextInt == 2) {
			return "白虎=金=忽视抗封印,强毒伤害" + randomfourPlace();
		} else if (nextInt == 3) {
			return "青龙=木=忽视抗混乱,强三尸虫" + randomfourPlace();
		} else if (nextInt == 4) {
			return "火猿=火";
		} else if (nextInt == 5) {
			return "黄鹤=水";
		} else if (nextInt == 6) {
			return "赤马=金";
		} else if (nextInt == 7) {
			return "苍狼=木";
		} else {
			return "金牛=土";
		}
	}

	/** 随机四宫属性 */
	public static String randomfourPlace() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append("=" + GameServer.randomStarPalace());
		}
		return buffer.toString();
	}

	/** 星卡重新获取五行属性 */
	public static String anewFiveElements() {
		// 随机条数
		int num = Battlefield.random.nextInt(5);
		ArrayList<String> list = new ArrayList<>();
		list.add("金");list.add("木");list.add("水");list.add("火");list.add("土");
		for (int i = 0; i < num; i++) {
			list.remove(SuitComposeAction.random.nextInt(list.size()));
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("五行属性");
		for (int i = 0; i < list.size(); i++) {
			int aptitude = Battlefield.random.nextInt(100) + 1;
			buffer.append("&" + list.get(i) + "=" + aptitude);
		}
		return buffer.toString();
	}
	/**
	 * 升级属性提升
	 * 
	 * @param value
	 * @param lvlNow
	 * @return
	 */
	public static String upgradeAttribute(String[] value, int lvlNow) {
		String[] split = value[3].split("&");
		if (!split[0].equals("炼化属性")) {
			return null;
		}
		Alchemy alchemy1 = NpcComposeAction.getAlchemy("星卡", split[2].split("=")[0]);
		Alchemy alchemy2 = NpcComposeAction.getAlchemy("星卡", split[3].split("=")[0]);
		String anewRefining = anewRefining(lvlNow, Integer.parseInt(split[1].split("=")[1]), alchemy1, alchemy2, 0);
		StringBuffer buffer = new StringBuffer();
		buffer.append(anewRefining);
		if (split[split.length - 1].startsWith("星阵属性")) {
			buffer.append("&" + split[split.length - 1]);
		}

		return buffer.toString();
	}

	/** 判断是否是四神兽 */
	public static boolean isfiveElements(String value) {
		if (value.equals("朱雀") || value.equals("玄武") || value.equals("白虎") || value.equals("青龙"))
			return true;
		return false;
	}

	/** 升级更换名称皮肤 */
	public static void changeStarCardSkin(Goodstable goodstable, int level) {
		if (level == 1) {
			goodstable.setGoodsname("地劣星");
			goodstable.setSkin("xk_01");
		} else if (level == 2) {
			goodstable.setGoodsname("地恶星");
			goodstable.setSkin("xk_02");
		} else if (level == 3) {
			goodstable.setGoodsname("地囚星");
			goodstable.setSkin("xk_03");
		} else if (level == 4) {
			goodstable.setGoodsname("地魔星");
			goodstable.setSkin("xk_04");
		} else if (level == 5) {
			goodstable.setGoodsname("地周星");
			goodstable.setSkin("xk_05");
		} else if (level == 6) {
			goodstable.setGoodsname("地猖星");
			goodstable.setSkin("xk_06");
		} else if (level == 7) {
			goodstable.setGoodsname("地轴星");
			goodstable.setSkin("xk_07");
		} else if (level == 8) {
			goodstable.setGoodsname("地威星");
			goodstable.setSkin("xk_08");
		} else if (level == 9) {
			goodstable.setGoodsname("地壮星");
			goodstable.setSkin("xk_09");
		} else if (level == 10) {
			goodstable.setGoodsname("地藏星");
			goodstable.setSkin("xk_10");
		} else if (level == 11) {
			goodstable.setGoodsname("地速星");
			goodstable.setSkin("xk_11");
		} else if (level == 12) {
			goodstable.setGoodsname("地走星");
			goodstable.setSkin("xk_12");
		} else if (level == 13) {
			goodstable.setGoodsname("地暗星");
			goodstable.setSkin("xk_13");
		} else if (level == 14) {
			goodstable.setGoodsname("地魁星");
			goodstable.setSkin("xk_14");
		}
	}
}
