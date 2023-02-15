package come.tool.Good;

import come.tool.FightingData.Battlefield;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.reward.DrawnitemsAction;
import org.come.action.suit.SuitMixdeal;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.ColorScheme;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Arith;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Stall.AssetUpdate;

public class UsePetAction implements IAction {
	static int[] highSkill = new int[]{1600, 1601, 1602, 1603, 1604, 1605, 1611, 1612, 1811,
			1815, 1816, 1817, 1818, 1819, 1815, 1816, 1817, 1818, 1819,
			1820, 1821, 1822, 1823, 1824, 1825, 1826, 1827, 1831, 1833,
			1834, 1835, 1836, 1837, 1838, 1839, 1848, 1850, 1852, 1854, 1858, 1859, 1860, 1862, 1864, 1865,
			1871, 1872, 1873, 1874, 1875, 1876, 1877, 1878, 1879, 1880,1882,1883,1884,1885,1886};
	static int[] normalSkill = new int[]{1800, 1801, 1802, 1803, 1804, 1805, 1806, 1807, 1808, 1810, 1811, 1812, 1832, 1843, 1844, 1845, 1846, 1847, 1849, 1851, 1853, 1855, 1856, 1857, 1861, 1863};

	//颜色表
	private static ConcurrentHashMap<Long, NPCDialogBean> maps;

	static {
		maps = new ConcurrentHashMap<>();
	}

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {
			return;
		}
		String[] vs = message.split("\\|");
		if (vs[0].equals("N")) {
			XXPet(ctx, loginResult, vs);
			return;
		}
		RoleSummoning pet = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(new BigDecimal(vs[1]));
		if (pet == null) {
			return;
		}
		if (pet.getRoleid().compareTo(loginResult.getRole_id()) != 0) {
			return;
		}
		if (vs[0].equals("DH")) {
			DHPet(pet, ctx, loginResult);
			return;
		} else if (vs[0].equals("FS")) {
			FSPet(pet, ctx, loginResult);
			return;
		} else if (vs[0].equals("SS")) {
			SSPet(pet, ctx, loginResult);
			return;
		} else if (vs[0].equals("PS")) {
			int type = Integer.parseInt(vs[2]);
			if (type == 0 || type == 1 || type == 2) {
				return;
			}
			PetSkill(pet, ctx, loginResult, type);
			return;
		} else if (vs[0].equals("ND")) {
			Goodstable good = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[2]));
			if (good == null) {
				return;
			}
			if (good.getRole_id().compareTo(loginResult.getRole_id()) != 0) {
				return;
			}
			if (good.getUsetime() <= 0) {
				return;
			}
			NDPet(pet, good, ctx, loginResult);
			return;
		}
		Goodstable good = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[0]));
		if (good == null) {
			return;
		}
		if (good.getRole_id().compareTo(loginResult.getRole_id()) != 0) {
			return;
		}
		if (good.getUsetime() <= 0) {
			return;
		}
		long type = good.getType();
		if (type == 715) {//715    亲密丹
			useGood(good, 1);
			long addQM = Long.parseLong(good.getValue().split("\\=")[1]);
			long value = pet.getFriendliness() + addQM;
			if (value >= 20000000) {
				value = 20000000;
			}
			pet.setFriendliness(value);
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
			assetUpdate.updata("P" + pet.getSid() + "=" + pet.getGrade() + "=" + pet.getExp() + "=" + pet.getFriendliness() + "=" + pet.getBasishp() + "=" + pet.getBasismp());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} else if (type == 503) {//503    召唤兽技能书 openSkillSealSS
			addPetSkill(pet, good, ctx, loginResult);
		} else if (type == 939) {//503    召唤兽技能书 openSkillSealSS
			addpetqiling(pet, good, ctx, loginResult);
		}else if (type == 504) {//504    聚魄丹
			openSkillSeal(pet, good, ctx, loginResult);
		}else if (type == 938) {//504    启灵丹
			oepnqldan(pet, good, ctx, loginResult);
		}  else if (type == 10086) {//10086    高级聚魄丹
			openSkillSealSS(pet, good, ctx, loginResult);
		}else if (type == 2040) {//2040   召唤兽经验丹
			useGood(good, 1);
			long addexp = Long.parseLong(good.getValue().split("\\=")[1]);
			ExpUtil.PetExp(pet, addexp);
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
			assetUpdate.updata("P" + pet.getSid() + "=" + pet.getGrade() + "=" + pet.getExp() + "=" + pet.getFriendliness() + "=" + pet.getBasishp() + "=" + pet.getBasismp());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} else if (type == 2043) {//2043   九转
			useNgauWanPills(pet, good, ctx, loginResult);
		} else if (type == 2113) {//2113   龙之骨
			useKeel(pet, good, ctx, loginResult);
		}else if (type == 918){//  918   超级龙之骨
			useKeelsp(pet,good,ctx,loginResult);
		} else if (type == 716) {//716    超级元气丹
			GrowUpDan(pet, good, ctx, loginResult);
		} else if (type == 192) {//192    龙涎丸
			dragonSaliva(pet, good, ctx, loginResult);
		}else if (type == 667) {//667    伐骨洗髓丹
			useBoneElution(pet, good, ctx, loginResult);
		} else if (type == 919) {//667    超级伐骨洗髓丹
			useBoneElutionsp(pet, good, ctx, loginResult);
		} else if (type == 2323) {//2323   终极培养丹
			train(pet, good, ctx, loginResult);
		} else if (type == 2325) {//2325   超级聚魄丹
			useDraw(pet, good, ctx, loginResult);
		} else if (type == 2326) {//2326   使用聚魄丹的技能
			addPetSkill(pet, good, ctx, loginResult);
		} else if (type == 727) {//727     化形丹
			changeDan(pet, good, ctx, loginResult);
		} else if (type == 2116) {// 神兽飞升丹
			petFlyUpDan(pet, good, ctx, loginResult);
		}else if (type==8002) {//8002   灵藤
			lingteng(pet, good, ctx, loginResult);
		} else if (type == 1005) {
			useGood(good, 1);
			UseMixdeal.gld(good, pet, ctx, loginResult);
		}

	}
	/**灵藤*/
	public void lingteng(RoleSummoning pet,Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		//获取value 物品信息
		String value=good.getValue();
		int ltlvl=-1;
		int ltlvl1=-1;
		int ltlvl2=-1;
		//获取物品等级
		if (good.getGoodsname().indexOf("低")!=-1) {
			ltlvl=3;
		}else if (good.getGoodsname().indexOf("中")!=-1) {
			ltlvl1=6;
		}else if (good.getGoodsname().indexOf("高")!=-1) {
			ltlvl2=9;
		}else {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("获取物品错误"));
			return;
		}

		String[] value1=value.split("=");
		String value2="0";
		String level1 = "0";
		if (value1[1].equals("高级分裂攻击")) {
			value2 = "1833";
		}else if(value1[1].equals("春风佛面")) {
			value2 = "1871";
		}else if(value1[1].equals("春意盎然")) {
			value2 = "1612";
		}else if(value1[1].equals("分花拂柳")) {
			value2 = "1831";
		}else if(value1[1].equals("悬刃")) {
			value2 = "1834";
		}else if(value1[1].equals("遗患")) {
			value2 = "1836";
		}else if(value1[1].equals("报复")) {
			value2 = "1835";
		}else if(value1[1].equals("吉人天相")) {
			value2 = "1838";
		}else if(value1[1].equals("妙手回春")) {
			value2 = "1611";
		}else if(value1[1].equals("视死如归")) {
			value2 = "1872";
		}else if(value1[1].equals("天地同寿")) {
			value2 = "1880";
		}else if(value1[1].equals("扶伤")) {
			value2 = "1858";
		}else if(value1[1].equals("福禄双全")) {
			value2 = "1873";
		}else if(value1[1].equals("炊金馔玉")) {
			value2 = "1600";
		}else if(value1[1].equals("枯木逢春")) {
			value2 = "1601";
		}else if(value1[1].equals("西天净土")) {
			value2 = "1602";
		}else if(value1[1].equals("如人饮水")) {
			value2 = "1603";
		}else if(value1[1].equals("风火燎原")) {
			value2 = "1604";
		}else if(value1[1].equals("高级清明术")) {
			value2 = "1850";
		}else if(value1[1].equals("高级脱困术")) {
			value2 = "1852";
		}else if(value1[1].equals("高级强心术")) {
			value2 = "1854";
		}else if(value1[1].equals("舍身取义")) {
			value2 = "1839";
		}else {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("获取技能错误"));
			return;
		}

		//if (pet.getPetSkillswl().indexOf(value1[0]) == -1) {
		//	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该召唤兽没有"+value1[0]));
		//		return;
		//	}else
		if (pet.getPetSkills() == null || pet.getPetSkills().equals("")) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("召唤兽没有技能"));
			return;
		}
		if (pet.getPetSkills().indexOf(value2) == -1) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该召唤兽没有"+value2));
			return;
		}

		List<String> skillswl=new ArrayList<>();
		//	if (pet.getPetSkillswl()==null|| pet.getPetSkillswl().equals("")) {
		//	skillswl.add(value);
		//}else

		if(pet.getPetSkillswl()!=null&&!pet.getPetSkillswl().equals("")) {
			String[] vs=pet.getPetSkillswl().split("\\|");
			for (int i = 0; i < vs.length; i++) {
				if (!vs[i].equals("")&&vs[i].indexOf(value2) == -1) {
					skillswl.add(vs[i]);
				}else if(vs[i].indexOf(value2) != -1){
					String[] level = vs[i].split("=");
					level1 = level[1];
				}
			}
		}
		if(skillswl.size() >3) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("最多悟灵3个技能"));
			return;
		}

		if (Integer.parseInt(level1) >= 10) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("高级技能已满阶"));
			return;
		}


		//判断技能等级并提升1级
		if (Integer.parseInt(level1) <= ltlvl) {
			if (GameServer.random.nextInt(100) < 20) {
				int level2 = Integer.parseInt(level1) + 1;
				skillswl.add(value2+"="+String.valueOf(level2));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(value1[1]+"的技能等级提升到了"+level2+"阶"));
			}else {
				skillswl.add(value2+"="+String.valueOf(level1));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("很遗憾，升级失败"));
			}
		}else if(Integer.parseInt(level1) <= ltlvl1 && Integer.parseInt(level1) >3) {
			if (GameServer.random.nextInt(100) < 15) {
				int level2 = Integer.parseInt(level1) + 1;
				skillswl.add(value2+"="+String.valueOf(level2));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(value1[1]+"的技能等级提升到了"+level2+"阶"));
			}else {
				skillswl.add(value2+"="+String.valueOf(level1));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("很遗憾，升级失败"));
			}
		}else if(Integer.parseInt(level1) <= ltlvl2 && Integer.parseInt(level1) >6) {
			if (GameServer.random.nextInt(100) < 10) {
				int level2 = Integer.parseInt(level1) + 1;
				skillswl.add(value2+"="+String.valueOf(level2));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(value1[1]+"的技能等级提升到了"+level2+"阶"));
			}else {
				skillswl.add(value2+"="+String.valueOf(level1));
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("很遗憾，升级失败"));
			}
		}else {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("请使用等级对应的灵藤"));
			return;
		}



		//拼接悟灵技能
		StringBuffer buffer1=new StringBuffer();
		for (int i = 0; i < skillswl.size(); i++) {
			if (buffer1.length()!=0) {buffer1.append("|");}
			buffer1.append(skillswl.get(i));
		}
		//保存悟灵技能
		pet.setPetSkillswl(buffer1.toString());
		//扣除物品
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good,1);

		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setPet(pet);

		//喊话 SuitMixdeal.PYJN(login.getRolename(),pet.getSummoningname(),skill.getSkillname());
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);

		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**
	 * 内丹经验转换
	 */
	public void NDPet(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (pet.getExp().longValue() <= 0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你召唤兽没有经验可以转换"));
			return;
		}
		String[] vs = good.getValue().split("\\|");
		String[] stringLevel = vs[2].split("=")[1].split("转");
		int zs = Integer.parseInt(stringLevel[0]);
		long addExp = (long) (pet.getExp().longValue() * 0.2);
		int lvl = Integer.parseInt(stringLevel[1]);
		long exp = Long.parseLong(vs[3].split("=")[1]) + addExp;
		int petlvl = BattleMixDeal.petLvlint(pet.getGrade());
		int maxlvl = ExpUtil.getNedanMostLevel(zs);
		if (zs >= pet.getTurnRount() && lvl >= petlvl) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽等级不够哦，快去修炼吧！！！"));
			return;
		}
		if (lvl >= 300) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前内丹已达最大等级！！！"));
			return;
		}
		long maxexp = ExpUtil.getBBNeiExp(zs, lvl + 1);
		xx:
		while (exp >= maxexp && exp > 0) {//判断是否最高级最高转
			if (lvl + 1 > maxlvl) {
				if (zs >= 4) {
					break xx;
				} else if (zs + 1 > pet.getTurnRount()) {
					break xx;
				} else {
					zs++;
					lvl = 0;
					maxexp = ExpUtil.getBBNeiExp(zs, lvl + 1);
					exp = 0;
				}
			} else if (zs >= pet.getTurnRount() && lvl + 1 > petlvl) {
				break xx;
			} else {
				exp = exp - maxexp;
				lvl++;
				maxexp = ExpUtil.getBBNeiExp(zs, lvl + 1);
			}
		}

		pet.setExp(new BigDecimal(0));//把召唤兽的经验清空为0；
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		StringBuffer buffer = new StringBuffer();
		buffer.append(vs[0]);
		buffer.append("|");
		buffer.append(vs[1]);
		buffer.append("|内丹等级=");
		buffer.append(zs);
		buffer.append("转");
		buffer.append(lvl);
		buffer.append("|经验=");
		buffer.append(exp);
		good.setValue(buffer.toString());

		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
		AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
		assetUpdate.setPet(pet);
		assetUpdate.updata("G" + good.getRgid() + "=" + zs + "=" + lvl + "=" + exp);
		assetUpdate.updata("P" + pet.getSid() + "=" + pet.getGrade() + "=" + pet.getExp() + "=" + pet.getFriendliness() + "=" + pet.getBasishp() + "=" + pet.getBasismp());
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 召唤兽技能替换
	 */
	public static boolean PetSkill(RoleSummoning pet, ChannelHandlerContext ctx, LoginResult login, int type) {
//		0 1 2领取技能 其他的是删除指定技能
		if (type == 0 || type == 1 || type == 2) {
			boolean getNormal, getHigh, getTrain;
			if (type == 1) {
				getNormal = DropUtil.isV(100);
				getHigh = DropUtil.isV(1);
				getTrain = DropUtil.isV(0.05);
			} else if (type == 2) {
				getNormal = DropUtil.isV(100);
				getHigh = DropUtil.isV(5);
				getTrain = DropUtil.isV(0.01);
			} else {
				getNormal = DropUtil.isV(1.0);
				getHigh = DropUtil.isV(0.2);
				getTrain = DropUtil.isV(0.01);
			}
			if (!getNormal && !getHigh && !getTrain) {
				return false;
			}
			// 终极技能
			int skillId = 0;
			String grade = null;
			if (getTrain) {// 随机获得培养终级技能
				skillId = 3034;
				grade = "终极";
			} else if (getHigh) {// 高级技能
				int skillIndex = GameServer.random.nextInt(highSkill.length);
				skillId = highSkill[skillIndex];
				grade = "高级";
			} else if (getNormal) {// 普通技能
				int skillIndex = GameServer.random.nextInt(normalSkill.length);
				skillId = normalSkill[skillIndex];
				grade = "普通";
			}
			if (skillId == 0) {
				return false;
			}

			Skill skill = GameServer.getSkill(skillId + "");
			if (skill == null) {
				return false;
			}
			List<String> skills = new ArrayList<>();
			if (pet.getPetSkills() != null && !pet.getPetSkills().equals("")) {
				String[] vs = pet.getPetSkills().split("\\|");
				for (int i = 0; i < vs.length; i++) {
					if (!vs[i].equals("")) {
						skills.add(vs[i]);
					}
				}
			}
//			if (pet.getPetQlSkills() != null && !pet.getPetQlSkills().equals("")) {
//				String[] vss = pet.getPetQlSkills().split("\\|");
//				for (int i = 0; i < vss.length; i++) {
//					if (!vss[i].equals("")) {
//						skills.add(vss[i]);
//					}
//				}
//			}
			if (pet.getOpenSeal() <= skills.size() || skills.size() >= 8) {//召唤兽技能7-8格自动领悟
				return false;
			}
//			if (pet.getOpenql() <= skills.size() || skills.size() >= 6) {//召唤兽技能7-8格自动领悟
//				return false;
//			}
			if (chongfu(skill, pet, skills, false) != null) {
				return false;
			}
			skills.add(skill.getSkillid() + "");
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < skills.size(); i++) {
				if (buffer.length() != 0) {
					buffer.append("|");
				}
				buffer.append(skills.get(i));
			}
			pet.setPetSkills(buffer.toString());
//			pet.setPetQlSkills(buffer.toString());
			getskills(skills, pet.getSkill());
			getskills(skills, pet.getBeastSkills());
			pet.setSkillData(skillData(skills));
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			SuitMixdeal.JN2(login.getRolename(), pet.getSummoningname(), skill.getSkillname(), grade);
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			assetUpdate.setPet(pet);
			assetUpdate.setMsg("你的召唤兽学会了" + skill.getSkillname());
			if (!grade.equals("普通")) {
				assetUpdate.updata("T悟技");
			}
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} else {
			boolean is = true;
			boolean iss = true;
			String[] vs = null;
			String[] vs1=null;

			List<String> skills = new ArrayList<>();
			List<String> skills1=new ArrayList<>();
			if (type == 1509 || type == 1609 || type == 1814 || type == 1866) { //如果是神兽技能就不领悟
				is = false;
				pet.setBeastSkills(null);
			} else {
				StringBuffer buffer = new StringBuffer();
				StringBuffer buffer1=new StringBuffer();
				if (pet.getPetSkills() != null && !pet.getPetSkills().equals("")) {
					String types = type + "";
					vs = pet.getPetSkills().split("\\|");
					for (int i = 0; i < vs.length; i++) {
						if (vs[i].equals("")) {
						} else if (vs[i].equals(types)) {
							is = false;
						if(pet.getPetSkillswl()!=null&&!pet.getPetSkillswl().equals("")) {
							vs1=pet.getPetSkillswl().split("\\|");
							for (int i1 = 0; i1 < vs1.length; i1++) {
								if (vs1[i1].indexOf(types) != -1) {

								}else{
									if(buffer1.length()!=0) {buffer1.append("|");}
									buffer1.append(vs1[i1]);
									skills1.add(vs1[i1]);
								}
							}
						}
						} else {
							if (buffer.length() != 0) {
								buffer.append("|");
							}
							buffer.append(vs[i]);
							skills.add(vs[i]);
						}
					}
				}
				pet.setPetSkills(buffer.length() == 0 ? null : buffer.toString());
				pet.setPetSkillswl(buffer1.length()==0?null:buffer1.toString());
			}
			if (type == 1509 || type == 1609 || type == 1814 || type == 1866) { //如果是神兽技能就不领悟
				is = false;
				pet.setBeastSkills(null);
			}
			else {
				StringBuffer buffer2 = new StringBuffer();
				if (pet.getPetQlSkills() != null && !pet.getPetQlSkills().equals("")) {
					String types = type + "";
					vs = pet.getPetQlSkills().split("\\|");
					for (int i = 0; i < vs.length; i++) {
						if (vs[i].equals("")) {
						} else if (vs[i].equals(types)) {
							is = false;
						} else {
							if (buffer2.length() != 0) {
								buffer2.append("|");
							}
							buffer2.append(vs[i]);
							skills.add(vs[i]);
						}
					}
				}
				pet.setPetQlSkills(buffer2.length() == 0 ? null : buffer2.toString());
			}
			if (is) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽未携带技能"));
				return false;
			}
//			if (iss) {
//				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽未携带技能"));
//				return false;
//			}
			getskills(skills, pet.getSkill());
			getskills(skills, pet.getBeastSkills());
			pet.setSkillData(skillData(skills));
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			assetUpdate.setPet(pet);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}
		return true;
	}
	/**
	 * 召唤兽神兽技能
	 */
	public void SSPet(RoleSummoning pet, ChannelHandlerContext ctx, LoginResult login) {
		int ssn = Integer.parseInt(pet.getSsn());
		if (ssn != 2 && ssn != 3 && ssn != 4) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您携带的召唤兽不是神兽!!!"));
			return;
		}
		if (login.getGold().compareTo(new BigDecimal(50000000)) < 0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("金币不足!!!"));
			return;
		}
		if (pet.getFriendliness() < 200000) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您的神兽亲密值不足200000!!!"));
			return;
		}
		if (pet.getOpenSeal() != 9) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您的神兽技能格子还未解封!!!"));
			return;
		}
//      //已打开
		String yb = pet.getBeastSkills();
		String skillid = null;//随机一个神兽技能
		int Chances = GameServer.random.nextInt(4);
		if (Chances == 0) {
			skillid = "1509";
		}//涅槃
		else if (Chances == 1) {
			skillid = "1609";
		}//兵临城下
		else if (Chances == 2) {
			skillid = "1814";
		}//潮鸣电掣
		else if (Chances == 3) {
			skillid = "1866";
		}//如虎添翼
		Skill skill = GameServer.getSkill(skillid);
		if (skill == null) {
			return;
		}
		pet.setBeastSkills(skillid);
		if (yb == null || !yb.equals(skillid)) {
			List<String> skills = new ArrayList<>();
			getskills(skills, pet.getPetSkills());
			getskills(skills, pet.getSkill());
			getskills(skills, pet.getBeastSkills());
			pet.setSkillData(skillData(skills));
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-50000000");
		login.setGold(login.getGold().subtract(new BigDecimal(50000000)));
		MonitorUtil.getMoney().useD(50000000L);
		pet.setFriendliness(pet.getFriendliness() - 200000);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("召唤兽学会了" + skill.getSkillname());
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 召唤兽飞升
	 */
	public void FSPet(RoleSummoning pet, ChannelHandlerContext ctx, LoginResult login) {
		int ssn = Integer.parseInt(pet.getSsn());
		if (ssn != 3 && ssn != 4) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您携带的召唤兽不是可飞升的神兽!!!"));
			return;
		}
		boolean bool = false;
		//判断条件是否够
		if (pet.getRevealNum() == 0) {//第1次飞升
			if (pet.getGrade() >= 50) {
				bool = true;
			}
		} else if (pet.getRevealNum() == 1) {//第2次飞升
			if (pet.getGrade() >= 188) {
				bool = true;
			}
		} else if (pet.getRevealNum() == 2) {//第3次飞升
			if (pet.getGrade() >= 316) {
				bool = true;
			}
		}
		if (!bool) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您的神兽" + pet.getSummoningname() + "不符合飞升的条件!"));
			return;
		}
		if (login.getGold().longValue() < 5000000) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("您的银两不足500W"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("D=-5000000");
		login.setGold(login.getGold().subtract(new BigDecimal(5000000)));
		MonitorUtil.getMoney().useD(5000000L);
		if (pet.getRevealNum() == 0) {
			if (ssn == 3) {
				otherPetId(pet, 0);
			}
			BigDecimal grow = mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.1);
			pet.setGrowlevel(Arith.xiaoshu3(grow.doubleValue()));
		} else if (pet.getRevealNum() == 1) {
			if (ssn == 3) {
				otherPetId(pet, 1);
				assetUpdate.updata("NSKIN" + pet.getSid());
			}
			BigDecimal grow = mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.05);
			pet.setGrowlevel(Arith.xiaoshu3(grow.doubleValue()));
		} else if (pet.getRevealNum() == 2) {
			NPCDialogBean bean = new NPCDialogBean(assetUpdate.getI(), 0, pet.getSid(), 60);
			maps.put(bean.getId(), bean);
			assetUpdate.updata("NBASE" + assetUpdate.getI() + "=60");
		}
		pet.setRevealNum(pet.getRevealNum() + 1);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("您的神兽 " + pet.getSummoningname() + "飞升成功!!!");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 召唤兽转生 点化
	 */
	/**召唤兽转生 点化*/
	public void DHPet(RoleSummoning pet,ChannelHandlerContext ctx,LoginResult login){
		int roleTurn=login.getTurnAround();
		int petTurn=pet.getTurnRount();
		if (petTurn>=roleTurn||petTurn>=4) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("快去升级吧!你快驾驭不你的召唤兽了"));
			return;
		}
		int lvl=pet.getGrade();
		if (petTurn==0) {
			if (lvl!=100) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的召唤兽"+pet.getSummoningname()+"等级不够,还需多加历练!"));
				return;
			}
			if(pet.getFriendliness()<100000){
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的召唤兽与您的亲密值不足10万"));
				return;
			}
		}else if (petTurn==1) {
			if (lvl!=221) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的召唤兽"+pet.getSummoningname()+"等级不够,还需多加历练!"));
				return;
			}
			if(pet.getFriendliness()<200000){
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的召唤兽与您的亲密值不足20万"));
				return;
			}
		}else if (petTurn==2) {
			if (lvl!=362) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的召唤兽"+pet.getSummoningname()+"等级不够,还需多加历练!"));
				return;
			}
			if(pet.getFriendliness()<500000){
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的召唤兽与您的亲密值不足50万"));
				return;
			}
		}else if (petTurn==3) {
			if (lvl!=543) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的召唤兽"+pet.getSummoningname()+"等级不够,还需多加历练!"));
				return;
			}
			if(pet.getFriendliness()<2000000){
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的召唤兽与您的亲密值不足200万"));
				return;
			}
		}
		lvl++;petTurn++;
		if (petTurn<=3) {
			if (login.getGold().longValue()<(2000000*petTurn)) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的银两不足"+(200*petTurn)+"万"));
				return;
			}
		} else {
			if (login.getGold().longValue()<20000000) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("您的银两不足2000万"));
				return;
			}
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		if (petTurn<=3) {
			assetUpdate.updata("D=-" + 2000000*petTurn);
			login.setGold(login.getGold().subtract(new BigDecimal(2000000*petTurn)));
			MonitorUtil.getMoney().useD(petTurn*2000000L);
		} else {
			assetUpdate.updata("D=-20000000");
			login.setGold(login.getGold().subtract(new BigDecimal(20000000)));
			MonitorUtil.getMoney().useD(20000000L);
		}
		//设置这只召唤兽的根骨、灵性、力量、敏捷、经验为0
		pet.setBone(0);
		pet.setSpir(0);
		pet.setPower(0);
		pet.setSpeed(0);
		pet.setCalm(0);
		pet.setExp(new BigDecimal(0));
		//等级
		pet.setGrade(lvl);
		pet.setTurnRount(petTurn);
		if (petTurn<=3) {
			pet.setFriendliness(pet.getFriendliness()-(petTurn==1?100000:petTurn==2?200000:500000));
		}else {
			pet.setFriendliness(pet.getFriendliness()-2000000);
		}
		//设置忠诚度为100
		pet.setFaithful(100);
		//成长率加0.1
		BigDecimal grow=mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.1);
		pet.setGrowlevel(Arith.xiaoshu3(grow.doubleValue()));

		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		if (petTurn<=3) {
			assetUpdate.setMsg("#G召唤兽转生成功");
		}else {
			assetUpdate.setMsg("#G召唤兽点化成功");
			if (pet.getSsn().equals("2")) {
				NPCDialogBean bean=new NPCDialogBean(assetUpdate.getI(),0,pet.getSid(),60);
				maps.put(bean.getId(), bean);
				assetUpdate.updata("NBASE"+assetUpdate.getI()+"=60");
			}else if (pet.getSsn().equals("6")) {
				NPCDialogBean bean=new NPCDialogBean(assetUpdate.getI(),0,pet.getSid(),200);
				maps.put(bean.getId(), bean);
				assetUpdate.updata("NBASE"+assetUpdate.getI()+"=200");
			}
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**
	 * 神兽使用神兽飞升丹的方法
	 */
	public void petFlyUpDan(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (!pet.getSsn().equals("2")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("这只召唤兽不能使用神兽飞升丹!!!"));
			return;
		}
		if (pet.getFlyupNum() >= 3) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("神兽 " + pet.getSummoningname() + "的飞升次数已达到上限！"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		pet.setFlyupNum(pet.getFlyupNum() + 1);
		BigDecimal grow = mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.1);
		pet.setGrowlevel(Arith.xiaoshu3(grow.doubleValue()));
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("神兽 " + pet.getSummoningname() + "飞升成功!!！");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 化形丹
	 */
	public static void changeDan(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
//		200123 白泽 200116 年 200117画中仙 200097 垂云叟 200098 范式之魂 200099 浪淘沙 200100 五叶 200101 颜如玉
		int petid = Integer.parseInt(pet.getSummoningid());
		if (petid != 200123 && petid != 200116 && petid != 200117 && petid != 200097 &&
				petid != 200098 && petid != 200099 && petid != 200100 && petid != 200101) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("白泽 年 画中仙 颜如玉 垂云叟 五叶 范式之魂才能使用化形丹"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		String skin = null;
		if (petid == 200123) {
			skin = "400518";
		} else if (petid == 200116) {
			skin = "400523";
		} else if (petid == 200117) {
			skin = "400521";
		} else if (petid == 200097) {
			skin = "400519";
		} else if (petid == 200098) {
			skin = "400520";
		} else if (petid == 200099) {
			skin = "400522";
		} else if (petid == 200100) {
			skin = "400524";
		} else if (petid == 200101) {
			skin = "400525";
		}
		pet.setSummoningskin(skin);
		pet.setColorScheme(null);
		String four = pet.getFourattributes();
		int ran1 = pet.getSI2("hhp");
		int ran2 = pet.getSI2("hmp");
		int ran3 = pet.getSI2("hap");
		int ran4 = pet.getSI2("hsp");
		pet.setHp(pet.getHp() - ran1);
		pet.setMp(pet.getMp() - ran2);
		pet.setAp(pet.getAp() - ran3);
		pet.setSp(pet.getSp() - ran4);
		//先清除之前的龙骨效果
		if (ran1 != 0) {
			four = DrawnitemsAction.Splice(four, "hhp=" + ran1, 4);
		}
		if (ran2 != 0) {
			four = DrawnitemsAction.Splice(four, "hmp=" + ran2, 4);
		}
		if (ran3 != 0) {
			four = DrawnitemsAction.Splice(four, "hap=" + ran3, 4);
		}
		if (ran4 != 0) {
			four = DrawnitemsAction.Splice(four, "hsp=" + ran4, 4);
		}
		ran1 = 0;
		ran2 = 0;
		ran3 = 0;
		ran4 = 0;
		switch (GameServer.random.nextInt(4)) {
			case 0:
				ran1 = 12;
				break;
			case 1:
				ran2 = 12;
				break;
			case 2:
				ran3 = 12;
				break;
			case 3:
				ran4 = 12;
				break;
		}
		pet.setHp(pet.getHp() + ran1);
		pet.setMp(pet.getMp() + ran2);
		pet.setAp(pet.getAp() + ran3);
		pet.setSp(pet.getSp() + ran4);
		if (ran1 != 0) {
			four = DrawnitemsAction.Splice(four, "hhp=" + ran1, 2);
		}
		if (ran2 != 0) {
			four = DrawnitemsAction.Splice(four, "hmp=" + ran2, 2);
		}
		if (ran3 != 0) {
			four = DrawnitemsAction.Splice(four, "hap=" + ran3, 2);
		}
		if (ran4 != 0) {
			four = DrawnitemsAction.Splice(four, "hsp=" + ran4, 2);
		}
		pet.setFourattributes(four);
		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 使用超级聚魄丹
	 */
	public void useDraw(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (pet.getTurnRount() < 3) {// 判断是否为三转召唤兽
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("该召唤兽未3转！"));
			return;
		}
		if (pet.getPetSkills() == null || pet.getPetSkills().equals("")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽没有技能"));
			return;
		}
		if (pet.getGoods() != null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("该召唤兽携带着装备"));
			return;
		}
		if (login.getSummoning_id() != null) {
			if (login.getSummoning_id().compareTo(pet.getSid()) == 0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("这只召唤兽已在参战中！！！"));
				return;
			}
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		// 成功与否，删除召唤兽
		AllServiceUtil.getRoleSummoningService().deleteRoleSummoningBySid(pet.getSid());
		RoleSummoning pet2 = new RoleSummoning();
		pet2.setSid(pet.getSid());
		assetUpdate.setPet(pet2);
		// 判断聚魄丹概率(20%),成功与否
		if (GameServer.random.nextInt(100) < 40) {
			// 随机取召唤兽的技能提取
			List<String> skills = new ArrayList<>();
			if (pet.getPetSkills() != null && !pet.getPetSkills().equals("")) {
				String[] vs = pet.getPetSkills().split("\\|");
				for (int i = 0; i < vs.length; i++) {
					if (!vs[i].equals("")) {
						skills.add(vs[i]);
					}
				}
			}
			String id = skills.get(GameServer.random.nextInt(skills.size()));
			Skill skill = GameServer.getSkill(id);
			good.setType(2326L);
			Integer grade = skill.getSkilltype();
			String instru = "技能=" + skill.getSkillname() + "|技能等级=" + (grade == 1 ? "普通" : grade == 2 ? "高级" : grade == 3 ? "终极" : "终极");
			good.setValue(instru);
			AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 9);
			AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			assetUpdate.setGood(good);
			assetUpdate.setMsg("提取成功!");
		} else {
			useGood(good, 1);
			assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
			assetUpdate.setMsg("提取失败!");
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 终极修炼丹
	 */
	public void train(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (pet.getPetSkills().indexOf("3034") == -1) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("该召唤兽没有???"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		int train = DropUtil.isV(90D) ? 5 : 20;// 随机加的培养值
		assetUpdate.setMsg("#您的 "+pet.getSummoningname()+" 召唤兽"+"提升修炼度:"+ train+" 点");
		pet.setTrainNum(pet.getTrainNum() + train);

//		// 培养值满，随机出现一个终极技能
		if (pet.getTrainNum() >= 999) {
			List<String> skills = new ArrayList<>();
			if (pet.getPetSkills() != null && !pet.getPetSkills().equals("")) {
				String[] vs = pet.getPetSkills().split("\\|");
				for (int i = 0; i < vs.length; i++) {
					if (!vs[i].equals("") && !vs[i].equals("3034")) {
						skills.add(vs[i]);
					}
				}
			}

			List<String> ids = new ArrayList<>();
			ids.add("1606");
			ids.add("1607");
			ids.add("1608");
			ids.add("1828");
			ids.add("1829");
			ids.add("1830");
			ids.add("1840");
			ids.add("1841");
			ids.add("1842");
			ids.add("1867");
			ids.add("1868");
			ids.add("1869");

			for (int i = ids.size() - 1; i >= 0; i--) {
				if (skills.contains(ids.get(i))) {
					ids.remove(i);
				}
			}
			s:
			while (true) {
				if (ids.size() == 0) {
					break;
				}
				String id = ids.remove(GameServer.random.nextInt(ids.size()));
				Skill skill = GameServer.getSkill(id);
				if (skill == null) {
					continue;
				}
				if (skill.getSkillralation() != null && !skill.getSkillralation().equals("")) {
					String[] chongtu = skill.getSkillralation().split("\\|");
					for (int i = 0; i < chongtu.length; i++) {
						if (chongtu[i].equals(skill.getSkillid() + "")) {
							continue;
						}
						if (skills.contains(chongtu[i])) {
							Skill skill2 = GameServer.getSkill(chongtu[i]);
							if (skill2 == null) {
								continue;
							}
							continue s;
						}
					}
				}
				skills.add(id);
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < skills.size(); i++) {
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					buffer.append(skills.get(i));
				}
				pet.setPetSkills(buffer.toString());
				getskills(skills, pet.getSkill());
				getskills(skills, pet.getBeastSkills());
				pet.setSkillData(skillData(skills));
				assetUpdate.updata("T悟技");
				SuitMixdeal.PYJN(login.getRolename(), pet.getSummoningname(), skill.getSkillname());
				break;
			}
			pet.setTrainNum(0);//培养值重置
		}
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 召唤兽使用伐骨洗髓丹的方法  --洗除召唤兽服用的龙之骨效果
	 */
	public void useBoneElution(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		//判断这只召唤兽是否有是使用过龙骨
		if (pet.getDragon() <= 0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽没有服用过龙之骨"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		// 清除龙骨加的成长率
		pet.setGrowlevel(Arith.sub(Double.parseDouble(pet.getGrowlevel()), (0.01 * pet.getDragon())) + "");
		pet.setDragon(0);//龙骨使用次数清零
		// 清除龙骨加的hp、mp、ap、sp
		pet.setHp(pet.getHp() - pet.getSI2("hp"));
		pet.setMp(pet.getMp() - pet.getSI2("mp"));
		pet.setAp(pet.getAp() - pet.getSI2("ap"));
		pet.setSp(pet.getSp() - pet.getSI2("sp"));
		//清空存储的龙骨加的hp、mp、ap、sp
		String four = pet.getFourattributes();
		four = DrawnitemsAction.Splice(four, "hp=" + pet.getSI2("hp"), 4);
		four = DrawnitemsAction.Splice(four, "mp=" + pet.getSI2("mp"), 4);
		four = DrawnitemsAction.Splice(four, "ap=" + pet.getSI2("ap"), 4);
		four = DrawnitemsAction.Splice(four, "sp=" + pet.getSI2("sp"), 4);
		pet.setFourattributes(four);
		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("#G龙之骨已经被清除");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}


	/**
	 * 召唤兽使用超级伐骨洗髓丹的方法  --洗除召唤兽服用的超级龙之骨效果
	 */
	public void useBoneElutionsp(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		//判断这只召唤兽是否有是使用过超级龙之骨
		if (pet.getSpdragon() <= 0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽没有服用过超级龙之骨"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		// 清除超级龙之骨加的成长率
		pet.setGrowlevel(Arith.sub(Double.parseDouble(pet.getGrowlevel()), (0.01 * pet.getSpdragon())) + "");
		pet.setSpdragon(0);//超级龙之骨使用次数清零
		// 清除龙骨加的hp、mp、ap、sp
		pet.setHp(pet.getHp() - pet.getSI2("hps"));
		pet.setMp(pet.getMp() - pet.getSI2("mps"));
		pet.setAp(pet.getAp() - pet.getSI2("aps"));
		pet.setSp(pet.getSp() - pet.getSI2("sps"));
		//清空存储的龙骨加的hp、mp、ap、sp
		String four = pet.getFourattributes();
		four = DrawnitemsAction.Splice(four, "hps=" + pet.getSI2("hps"), 4);
		four = DrawnitemsAction.Splice(four, "mps=" + pet.getSI2("mps"), 4);
		four = DrawnitemsAction.Splice(four, "aps=" + pet.getSI2("aps"), 4);
		four = DrawnitemsAction.Splice(four, "sps=" + pet.getSI2("sps"), 4);
		pet.setFourattributes(four);
		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setMsg("#G超级龙之骨已经被清除");
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 龙涎丸
	 */
	public static void dragonSaliva(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (!(pet.getSsn().equals("5") || pet.getSummoningid().equals("200125"))) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("不是龙涎丸宝宝"));
			return;
		}
		int drac = pet.getDraC();
		if (drac >= 9) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("已经达到最大使用次数"));
			return;
		}
//		0转50级1  50 0转90级2  90 1转50级3  151 1转90级4  191 1转120级5 221
//		2转50级6  272 2转100级7 322 2转140级8 362 3转70级9  433
		int maxsum = 0;
		if (pet.getGrade() >= 433) {
			maxsum = 9;
		} else if (pet.getGrade() >= 362) {
			maxsum = 8;
		} else if (pet.getGrade() >= 322) {
			maxsum = 7;
		} else if (pet.getGrade() >= 272) {
			maxsum = 6;
		} else if (pet.getGrade() >= 221) {
			maxsum = 5;
		} else if (pet.getGrade() >= 191) {
			maxsum = 4;
		} else if (pet.getGrade() >= 151) {
			maxsum = 3;
		} else if (pet.getGrade() >= 90) {
			maxsum = 2;
		} else if (pet.getGrade() >= 50) {
			maxsum = 1;
		}
		if (drac >= maxsum) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽当前等级最多使用" + maxsum + "个"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		drac++;
		pet.setDraC(drac);
		if (drac == 9) {
			pet.setColorScheme("1|0|255|256|0|0|512|256|0|512|0|256");
		}
		double grow = Double.parseDouble(pet.getGrowlevel()) + 0.02;
		pet.setGrowlevel(Arith.xiaoshu3(grow));
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("#G使用成功");
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 变色丹
	 */
	public static void GrowUpDan(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		String value = good.getValue();
		if (value == null || value.equals("")) value = "100|0";
		String[] v = value.split("\\|");
		if (!(v[1].equals("0") || v[1].equals(pet.getSummoningid()))) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽无法使用该类型的元气丹"));
			return;
		}
		//判断丹的类型是变色还是元气
		int type = 0;//0是变色 1是元气
		if (good.getGoodsname().indexOf("元气") != -1) {
			type = 1;
		}
		if (type == 0 && pet.getSsn().equals("6")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("不能吃变色丹"));
			return;
		}
		ColorScheme colorScheme = GameServer.getColors(Integer.parseInt(v[1]));
		if (colorScheme == null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没有该类型的变色方案"));
			return;
		}
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good, 1);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		int gl = Integer.parseInt(v[0]);
		if (gl < GameServer.random.nextInt(100)) {
			assetUpdate.setMsg("召唤兽吃了一点反应都没有");
		} else {
			if (type == 1) {
				double grow = Double.parseDouble(pet.getGrowlevel());
				grow = Arith.sub(grow, Arith.div(pet.getGrowUpDanNum(), 1000.0));
				int zhi = colorScheme.getMin() + GameServer.random.nextInt(colorScheme.getMax() - colorScheme.getMin() + 1);
				grow = Arith.add(grow, Arith.div(zhi, 1000.0));
				pet.setGrowUpDanNum(zhi);
				pet.setGrowlevel(Arith.xiaoshu3(grow));
				assetUpdate.setMsg("#G使用成功,召唤兽成长发生了变化");
			} else {
				assetUpdate.setMsg("召唤兽变色成功");
			}
			if (!pet.getSsn().equals("6")) {
				pet.setColorScheme(colorScheme.getValue());
			}
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			assetUpdate.setPet(pet);
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 使用龙之骨的方法
	 */
	public void useKeel(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		// 先判断这只召唤兽的龙骨数量
		if (pet.getDragon() >= 5) {// 不超过3个
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("龙之骨数量已达到上限！"));
			return;
		}
		useGood(good, 1);
		pet.setDragon(pet.getDragon() + 1);// 龙骨数量加一
		pet.setGrowlevel(mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.01).toString());// 成长率加0.01
		while (true) {// 随机给这只召唤兽的hp、mp、ap、sp随机加6点
			String four = pet.getFourattributes();
			int ran1 = GameServer.random.nextInt(7);
			int ran2 = GameServer.random.nextInt(7);
			int ran3 = GameServer.random.nextInt(7);
			int ran4 = GameServer.random.nextInt(7);
			if (ran1 + ran2 + ran3 + ran4 == 6) {
				pet.setHp(pet.getHp() + ran1);
				pet.setMp(pet.getMp() + ran2);
				pet.setAp(pet.getAp() + ran3);
				pet.setSp(pet.getSp() + ran4);
				if (ran1 != 0) {
					four = DrawnitemsAction.Splice(four, "hp=" + ran1, 2);
				}
				if (ran2 != 0) {
					four = DrawnitemsAction.Splice(four, "mp=" + ran2, 2);
				}
				if (ran3 != 0) {
					four = DrawnitemsAction.Splice(four, "ap=" + ran3, 2);
				}
				if (ran4 != 0) {
					four = DrawnitemsAction.Splice(four, "sp=" + ran4, 2);
				}
				pet.setFourattributes(four);
				break;
			}
		}
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 使用超级龙之骨的方法
	 */
	public void useKeelsp(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (pet.getSpdragon() >= 5) {// 不超过2个
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("超级龙之骨数量已达到上限！"));
			return;
		}
		if (pet.getPower() <= 4 ){
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽飞升后才能使用！！"));
			return;
		}
		useGood(good, 1);
		pet.setSpdragon(pet.getSpdragon() + 1);// 超级龙骨数量加一
		boolean isCJ=good.getGoodsname().indexOf("龙魄")!=-1?true:false;//是否超级龙之骨
		pet.setGrowlevel(mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.01).toString());// 成长率加0.01
		while (true) {// 随机给这只召唤兽的hp、mp、ap、sp随机加6点
			String four = pet.getFourattributes();
			int ran1 = GameServer.random.nextInt(7);
			int ran2 = GameServer.random.nextInt(7);
			int ran3 = GameServer.random.nextInt(7);
			int ran4 = GameServer.random.nextInt(7);
			if (isCJ) {
				ran1=0;ran2=0;ran3=0;ran4=0;
				switch (GameServer.random.nextInt(4)) {
					case 0:ran1=10;break;
					case 1:ran2=10;break;
					case 2:ran3=10;break;
					case 3:ran4=10;break;
				}
			}
			if (ran1 + ran2 + ran3 + ran4 == 10) {
				pet.setHp(pet.getHp() + ran1);
				pet.setMp(pet.getMp() + ran2);
				pet.setAp(pet.getAp() + ran3);
				pet.setSp(pet.getSp() + ran4);
				if (ran1 != 0) {
					four = DrawnitemsAction.Splice(four, "hps=" + ran1, 2);
				}
				if (ran2 != 0) {
					four = DrawnitemsAction.Splice(four, "mps=" + ran2, 2);
				}
				if (ran3 != 0) {
					four = DrawnitemsAction.Splice(four, "aps=" + ran3, 2);
				}
				if (ran4 != 0) {
					four = DrawnitemsAction.Splice(four, "sps=" + ran4, 2);
				}
				pet.setFourattributes(four);
				break;
			}
		}
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 召唤兽直接变一转（一转之后就不能再吃了） -- 九转易筋丸
	 */
	public void useNgauWanPills(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		if (pet.getGrade() > 100) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽已转生"));
			return;
		}
		useGood(good, 1);
		pet.setGrade(101);
		pet.setBone(0);
		pet.setSpir(0);
		pet.setPower(0);
		pet.setSpeed(0);
		pet.setCalm(0);
		pet.setExp(new BigDecimal(0));
		pet.setTurnRount(BattleMixDeal.petTurnRount(101));
		pet.setFriendliness(0L);
		pet.setFaithful(100);

		BigDecimal grow = mathDouble(Double.parseDouble(pet.getGrowlevel()), 0.1);
		pet.setGrowlevel(Arith.xiaoshu3(grow.doubleValue()));
		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G" + good.getRgid() + "=" + good.getUsetime());
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	/**
	 * 计算Double类型相加的算法
	 */
	public static BigDecimal mathDouble(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2);
	}
	public void oepnqldan (RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		int flag = 6;//固定数量9格
		if (pet.getOpenql() >= flag) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽的技能格子都已解开!"));
			return;
		}
		useGood(good, 1);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		if (GameServer.random.nextInt(100) < 10) {//开启几率20%
			pet.setOpenql(pet.getOpenql()+1);
			assetUpdate.updata("T格子");
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			assetUpdate.setPet(pet);
			SuitMixdeal.jpd(login.getRolename(), pet.getSummoningname());
		}else {
			assetUpdate.setMsg("开启失败");
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**
	 * 打开技能封印的方法聚魄丹
	 */
	public void openSkillSeal(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		int flag = 6;//固定数量9格

		//if (pet.getSsn().equals("2") || pet.getSsn().equals("3") || pet.getSsn().equals("4")) {
		//	flag = 9;
		//	}
		//如果召唤兽技能大于或者等于 flag=9 ,会提示“召唤兽的技能格子都已解开”
		if (pet.getOpenSeal() >= flag) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽的技能格子都已解开!"));
			return;
		}
		useGood(good, 1);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		if (GameServer.random.nextInt(100) < 10) {//开启几率20%
			//if (GameServer.random.nextInt(5) == 3) {
			pet.setOpenSeal(pet.getOpenSeal() + 1);//召唤兽技能格子+1
			assetUpdate.updata("T格子");
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			assetUpdate.setPet(pet);
			SuitMixdeal.jpd(login.getRolename(), pet.getSummoningname());
		}else {
			assetUpdate.setMsg("开启失败");
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	public void openSkillSealSS(RoleSummoning pet, Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		int flag = 8;//固定数量9格

		if (pet.getSsn().equals("2") || pet.getSsn().equals("3") || pet.getSsn().equals("4")) {
			flag = 9;
		}
		//如果召唤兽技能大于或者等于 flag=9 ,会提示“召唤兽的技能格子都已解开”
		if (pet.getOpenSeal() >= flag) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("召唤兽的技能格子都已解开!"));
			return;
		}

		useGood(good, 1);
		AssetUpdate assetUpdate = new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		if (GameServer.random.nextInt(100) < 5) {//开启几率15%
			pet.setOpenSeal(pet.getOpenSeal() + 1);//召唤兽技能格子+1
			assetUpdate.updata("T格子");
			AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
			assetUpdate.setPet(pet);
			SuitMixdeal.jpd(login.getRolename(), pet.getSummoningname());
		}else {
			assetUpdate.setMsg("开启失败");
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}


	/**添加召唤兽技能的方法*/
	public static void addPetSkill(RoleSummoning pet,Goodstable good,ChannelHandlerContext ctx,LoginResult login) {
		int sum = pet.getOpenSeal();
		Skill skill = null;
		if(good.getType() == 2326){//聚魄丹使用技能  addpetqiling
			// 拆分技能名称
			String skillName = good.getValue().split("\\|")[0].split("=")[1];
			// 获取技能
			skill=GameServer.getSkill(skillName);
		}else {
			skill = skillid(good.getValue());
		}
		if (skill==null) {return;}
		List<String> skills=new ArrayList<>();
		if (pet.getPetSkills()!=null&&!pet.getPetSkills().equals("")) {
			String[] vs=pet.getPetSkills().split("\\|");
			for (int i = 0; i < vs.length; i++) {
				if (!vs[i].equals("")) {
					skills.add(vs[i]);
				}
			}
		}

		if (sum<=skills.size()||skills.size()>=8) {//召唤兽技能格子已经满了
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("召唤兽技能格子已经满了"));
			return;
		}

		String value=chongfu(skill, pet, skills,true);
		if (value!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(value));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good,1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		skills.add(skill.getSkillid()+"");
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < skills.size(); i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(skills.get(i));
		}
		pet.setPetSkills(buffer.toString());
		getskills(skills,pet.getSkill());
		getskills(skills,pet.getBeastSkills());
		pet.setSkillData(skillData(skills));
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("你的召唤兽学会了"+skill.getSkillname());
		int Id=skill.getSkillid();
		if ((Id>=1606&&Id<=1608)||(Id>=1828&&Id<=1830)||(Id>=1840&&Id<=1842)||(Id>=1867&&Id<=1869)||Id==3034) {//学习终极技能
			assetUpdate.updata("T悟技");
			SuitMixdeal.JN(login.getRolename(),pet.getSummoningname(),skill.getSkillname(),"终级");
		}else if ((Id>=1815&&Id<=1827)||(Id>=1600&&Id<=1605)
				||(Id>=1610&&Id<=1612)||Id==1811||Id==1831||Id==1833||(Id>=1871&&Id<=1880)) {//学习高级技能
			assetUpdate.updata("T悟技");
			SuitMixdeal.JN(login.getRolename(),pet.getSummoningname(),skill.getSkillname(),"高级");
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

	public static void addpetqiling(RoleSummoning pet,Goodstable good,ChannelHandlerContext ctx,LoginResult login) {
		int sum = pet.getOpenql();
		Skill skill = null;
		if(good.getType() == 2326){//聚魄丹使用技能
			// 拆分技能名称
			String skillName = good.getValue().split("\\|")[0].split("=")[1];
			// 获取技能
			skill=GameServer.getSkill(skillName);
		}else {
			skill = skillid(good.getValue());
		}
		if (skill==null) {return;}
		List<String> skills=new ArrayList<>();
		if (pet.getPetQlSkills()!=null&&!pet.getPetQlSkills().equals("")) {
			String[] vs=pet.getPetQlSkills().split("\\|");
			for (int i = 0; i < vs.length; i++) {
				if (!vs[i].equals("")) {
					skills.add(vs[i]);
				}
			}
		}

		if (sum<=skills.size()||skills.size()>=6) {//召唤兽技能格子已经满了
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("召唤兽技能格子已经满了"));
			return;
		}

		String value=chongfu(skill, pet, skills,true);
		if (value!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(value));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		useGood(good,1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		skills.add(skill.getSkillid()+"");
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < skills.size(); i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(skills.get(i));
		}
		pet.setPetQlSkills(buffer.toString());
		getskills(skills,pet.getSkill());
		getskills(skills,pet.getBeastSkills());
		pet.setSkillData(skillData(skills));
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
		assetUpdate.setPet(pet);
		assetUpdate.setMsg("你的召唤兽学会了"+skill.getSkillname());
		int Id=skill.getSkillid();
		if ((Id>=1606&&Id<=1608)||(Id>=1828&&Id<=1830)||(Id>=1840&&Id<=1842)||(Id>=1867&&Id<=1869)||Id==3034) {//学习终极技能
			assetUpdate.updata("T悟技");
			SuitMixdeal.JN(login.getRolename(),pet.getSummoningname(),skill.getSkillname(),"终级");
		}else if ((Id>=1815&&Id<=1827)||(Id>=1600&&Id<=1605)
				||(Id>=1610&&Id<=1612)||Id==1811||Id==1831||Id==1833||(Id>=1871&&Id<=1880)) {//学习高级技能
			assetUpdate.updata("T悟技");
			SuitMixdeal.JN(login.getRolename(),pet.getSummoningname(),skill.getSkillname(),"高级");
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	/**获取要学习的技能id*/
	public static Skill skillid(String value) {
		String[] v=value.split("\\|");
		int up=0;
		for (int i = 0; i < v.length; i+=2) {
			String jl=v[i].split("=")[1];
			double d=Double.parseDouble(jl);
			if (DropUtil.isV(d+up)) {
				v=v[i+1].split("=")[1].split("&");
				return GameServer.getSkill(v[GameServer.random.nextInt(v.length)]);
			}
			up+=d;
		}
		v=v[1].split("=")[1].split("&");
		return GameServer.getSkill(v[GameServer.random.nextInt(v.length)]);
	}
	/**
	 * 判断是否可以学习该技能 true表示没学过
	 */
	public static String chongfu(Skill skill,RoleSummoning pet,List<String> lists,boolean l) {
		String skillID=skill.getSkillid()+"";
		if (lists.contains(skillID)) {
			return "召唤兽已经学过"+skill.getSkillname();
		}
		if (skill.getSkillralation()!=null&&!skill.getSkillralation().equals("")) {
			int lvl=skill.getSkilllevel();
			String[] chongtu = skill.getSkillralation().split("\\|");
			for (int i = 0; i < chongtu.length; i++) {
				if (chongtu[i].equals(skillID)) {continue;}
				if (lists.contains(chongtu[i])) {
					Skill skill2=GameServer.getGetSkill().get(chongtu[i]);
					if (skill2==null) {continue;}
					int lvl2=skill2.getSkilllevel();
					if (l) {
						if (lvl<lvl2) {
							return "不能拥有同类型的更高级技能";
						}else {
							lists.remove(chongtu[i]);
						}
					}else {
						return "";
					}
				}
			}
		}
		int id = skill.getSkillid();
		if (id==1820) {// 金50
			if (Integer.parseInt(pet.getGold())<50&&!lists.contains("1815")) {return "你的召唤兽金属性不足50";}
		}else if (id==1821) {// 木50
			if (Integer.parseInt(pet.getWood())<50&&!lists.contains("1816")) {return "你的召唤兽木属性不足50";}
		}else if (id==1822) {// 土50
			if (Integer.parseInt(pet.getSoil())<50&&!lists.contains("1819")) {return "你的召唤兽土属性不足50";}
		}else if (id==1823) {// 水50
			if (Integer.parseInt(pet.getWater())<50&&!lists.contains("1817")) {return "你的召唤兽水属性不足50";}
		}else if (id==1824) {// 火50
			if (Integer.parseInt(pet.getFire())<50&&!lists.contains("1818")) {return "你的召唤兽火属性不足50";}
		}else if (id==1825) {// 木50 根骨500
			if (pet.getBone() < 500) {return "你的召唤兽根骨属性不足500";}
			if (Integer.parseInt(pet.getWood())<50&&!lists.contains("1816")) {return "你的召唤兽木属性不足50";}
		}else if (id==1826) {// 火50
			if (Integer.parseInt(pet.getFire())<50&&!lists.contains("1818")) {return "你的召唤兽火属性不足50";}
		}else if (id==1827) {// 水50
			if (Integer.parseInt(pet.getWater())<50&&!lists.contains("1817")) {return "你的召唤兽水属性不足50";}
		}else if (id==1246) {
			if (pet.getTurnRount()<4) {return "你的召唤兽未飞升";}
			if (pet.getSpir()<500) {return "你的召唤兽灵性不足500";}
		} else if (id==1247) {//
			if (pet.getTurnRount()<4) {return "你的召唤兽未飞升";}
			if (pet.getBone()<500) {return "你的召唤兽根骨不足500";}
		} else if (id==1248) {//
			if (pet.getTurnRount()<4) {return "你的召唤兽未飞升";}
			if (pet.getPower()<500) {return "你的召唤兽力量不足500";}
		} else if (id==1249) {//
			if (pet.getTurnRount()<4) {return "你的召唤兽未飞升";}
			if (pet.getSpeed()<500) {return "你的召唤兽敏捷不足500";}
		}
		if (pet.getPetSkillswl() == null || pet.getPetSkillswl().equals("")) {
		}else
		{if (id==1600) {
				if (pet.getPetSkillswl().indexOf("1602") != -1||pet.getPetSkillswl().indexOf("1603") != -1||pet.getPetSkillswl().indexOf("1604") != -1||pet.getPetSkillswl().indexOf("1605") != -1||pet.getPetSkillswl().indexOf("1601") != -1) { return "已开启已开启悟灵技能替换失败";}
			} else if (id==1601) {
				if (pet.getPetSkillswl().indexOf("1600") != -1||pet.getPetSkillswl().indexOf("1602") != -1||pet.getPetSkillswl().indexOf("1603") != -1||pet.getPetSkillswl().indexOf("1604") != -1||pet.getPetSkillswl().indexOf("1605") != -1) { return "已开启已开启悟灵技能替换失败";}
			} else if (id==1602) {
				if (pet.getPetSkillswl().indexOf("1600") != -1||pet.getPetSkillswl().indexOf("1603") != -1||pet.getPetSkillswl().indexOf("1604") != -1||pet.getPetSkillswl().indexOf("1605") != -1||pet.getPetSkillswl().indexOf("1601") != -1) { return "已开启已开启悟灵技能替换失败";}
			} else if (id==1603) {
				if (pet.getPetSkillswl().indexOf("1600") != -1||pet.getPetSkillswl().indexOf("1602") != -1||pet.getPetSkillswl().indexOf("1604") != -1||pet.getPetSkillswl().indexOf("1605") != -1||pet.getPetSkillswl().indexOf("1601") != -1) { return "已开启已开启悟灵技能替换失败";}
			} else if (id==1604) {
				if (pet.getPetSkillswl().indexOf("1600") != -1||pet.getPetSkillswl().indexOf("1602") != -1||pet.getPetSkillswl().indexOf("1603") != -1||pet.getPetSkillswl().indexOf("1605") != -1||pet.getPetSkillswl().indexOf("1601") != -1) { return "已开启已开启悟灵技能替换失败";}
			} else if (id==1605) {
				if (pet.getPetSkillswl().indexOf("1600") != -1||pet.getPetSkillswl().indexOf("1602") != -1||pet.getPetSkillswl().indexOf("1603") != -1||pet.getPetSkillswl().indexOf("1604") != -1||pet.getPetSkillswl().indexOf("1601") != -1) { return "已开启已开启悟灵技能替换失败";}
			}else if (id==1611) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1612) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1831) {
				if (pet.getPetSkillswl().indexOf("1831") != -1 ||pet.getPetSkillswl().indexOf("1833") != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1834) {
				if (pet.getPetSkillswl().indexOf("1834") != -1|| pet.getPetSkillswl().indexOf("1836") != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1835) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1836) {
				if (pet.getPetSkillswl().indexOf("1834") != -1|| pet.getPetSkillswl().indexOf("1836") != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1833) {
				if (pet.getPetSkillswl().indexOf("1831") != -1 ||pet.getPetSkillswl().indexOf("1833") != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1871) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1872) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1880) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			} else if (id==1838) {
				if (pet.getPetSkillswl().indexOf(id) != -1) { return "已开启悟灵技能替换失败";}
			}

		}
		return null;
	}
	/**
	 * 召唤兽技能刷新数据
	 */
	public static String skillData(List<String> skills) {
		// 获取所有的技能id
		String skilldata = null;
		for (int i = 0; i < skills.size(); i++) {
			switch (skills.get(i)) {
				case "1800":
				case "1825":
					skilldata = DrawnitemsAction.Splice(skilldata, "HP=27000", 2);
					break;
				case "1801":
					skilldata = DrawnitemsAction.Splice(skilldata, "MP=27000", 2);
					break;
				case "1802":
				case "1826":
					skilldata = DrawnitemsAction.Splice(skilldata, "AP=11000", 2);
					break;
				case "1803":
				case "1827":
					skilldata = DrawnitemsAction.Splice(skilldata, "SP=170", 2);
					break;
				case "1812":
					skilldata = DrawnitemsAction.Splice(skilldata, "SP=-170", 2);
					break;
				case "1814":
					skilldata = DrawnitemsAction.Splice(skilldata, "SP=250", 2);
					break;
				case "1882":
					skilldata = DrawnitemsAction.Splice(skilldata, "HP=32000", 2);
					break;
				case "1883":
					skilldata = DrawnitemsAction.Splice(skilldata, "MP=32000", 2);
					break;
				case "1884":
					skilldata = DrawnitemsAction.Splice(skilldata, "AP=15000", 2);
					break;
				case "1885":
					skilldata = DrawnitemsAction.Splice(skilldata, "SP=200", 2);
					break;

				default:
					break;
			}
		}
		return skilldata;
	}
	/**拼接技能*/
	public static void getskills(List<String> skills, String petskill) {
		if (petskill == null || petskill.equals(""))
			return;
		String[] v = petskill.split("\\|");
		for (int i = 0; i < v.length; i++) {
			skills.add(v[i]);
		}
	}

	/**初始增加选项*/
	public void XXPet(ChannelHandlerContext ctx,LoginResult login,String[] vs){
		long id=Long.parseLong(vs[1]);
		NPCDialogBean bean=maps.remove(id);
		if (bean==null) {return;}
		RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(bean.getOId());
		if (pet==null) {return;}
		if (pet.getRoleid().compareTo(login.getRole_id())!=0) {return;}
		int type=Integer.parseInt(vs[2]);
		if (type==0) {
			pet.setHp(pet.getHp()+bean.getValue());
		}else if (type==1) {
			pet.setMp(pet.getMp()+bean.getValue());
		}else if (type==2) {
			pet.setAp(pet.getAp()+bean.getValue());
		}else if (type==3) {
			pet.setSp(pet.getSp()+bean.getValue());
		}

		pet.setBasishp(0);
		pet.setBasismp(0);
		AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);

		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		assetUpdate.setPet(pet);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}
	public static void useGood(Goodstable good,int sum){
		good.goodxh(sum);//添加记录
		AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 9);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
	}

	//判断变化的是哪一只召唤兽的方法
	public static void otherPetId(RoleSummoning pet,int flag){
		if(flag == 0){
			if(pet.getSummoningid().equals("200102")){
				pet.setSummoningskin("400078");
				pet.setGold("0");pet.setWood("0");
				pet.setSoil("5");pet.setWater("0");
				pet.setFire("95");
			}else if(pet.getSummoningid().equals("200103")){
				pet.setSummoningskin("400080");
				pet.setGold("40");pet.setWood("0");
				pet.setSoil("0");pet.setWater("0");
				pet.setFire("60");
			}else if(pet.getSummoningid().equals("200104")){
				pet.setSummoningskin("400083");
				pet.setGold("0");pet.setWood("60");
				pet.setSoil("40");pet.setWater("0");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200105")){
				pet.setSummoningskin("400072");
				pet.setGold("5");pet.setWood("0");
				pet.setSoil("0");pet.setWater("95");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200106")){
				pet.setSummoningskin("400079");
				pet.setGold("0");pet.setWood("60");
				pet.setSoil("0");pet.setWater("40");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200107")){
				pet.setSummoningskin("400064");
				pet.setGold("0");pet.setWood("0");
				pet.setSoil("15");pet.setWater("85");
				pet.setFire("0");
			}
		}else if(flag == 1){
			if(pet.getSummoningid().equals("200102")){
				pet.setGold("0");pet.setWood("0");
				pet.setSoil("30");pet.setWater("70");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200103")){
				pet.setGold("0");pet.setWood("70");
				pet.setSoil("30");pet.setWater("0");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200104")){
				pet.setGold("25");pet.setWood("0");
				pet.setSoil("0");pet.setWater("0");
				pet.setFire("75");
			}else if(pet.getSummoningid().equals("200105")){
				pet.setGold("0");pet.setWood("0");
				pet.setSoil("30");pet.setWater("70");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200106")){
				pet.setGold("75");pet.setWood("0");
				pet.setSoil("0");pet.setWater("25");
				pet.setFire("0");
			}else if(pet.getSummoningid().equals("200107")){
				pet.setGold("40");pet.setWood("0");
				pet.setSoil("0");pet.setWater("60");
				pet.setFire("0");
			}
		}
	}
}
