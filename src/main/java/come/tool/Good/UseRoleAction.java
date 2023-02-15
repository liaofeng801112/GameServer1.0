package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.come.action.IAction;
import org.come.action.buy.AddGoodUtil;
import org.come.action.monitor.MonitorLimit;
import org.come.action.monitor.MonitorUtil;
import org.come.action.summoning.SummonPetAction;
import org.come.bean.LoginResult;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.Mount;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleThreadPool;
import come.tool.Battle.FightingForesee;
import come.tool.FightingData.Sepcies_MixDeal;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

/**
 * 人物的物品使用
 * @author Administrator
 */
public class UseRoleAction implements IAction{
	public static Random random=new Random();
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult == null) {
			return;
		}
		int path = message.indexOf("|");
		Goodstable goodstable = null;
		if (path == -1) {
			goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(message));
		} else {
			goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(message.substring(0, path)));
			message = message.substring(path + 1);
		}
		if (goodstable == null) {
			return;
		}
		if (goodstable.getRole_id().compareTo(loginResult.getRole_id()) != 0) {
			return;
		}
		if (goodstable.getUsetime() <= 0) {
			return;
		}
		long type = goodstable.getType();
		goodstable.setUsetime(goodstable.getUsetime() - 1);
		if (type == 60001 || type == 60002) {
			Novice(goodstable, ctx, loginResult);
			return;
		} else if (type == 121 || type == 122) {
			if (type == 121) {
				unSilence(goodstable, message, ctx, loginResult);
			} else {
				unSeal(goodstable, message, ctx, loginResult);
			}
			return;
		}

		//添加记录
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, 9);
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		if (type == 100) {//大话币使用卡
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			String[] vs = goodstable.getValue().split("=");
			BigDecimal m = new BigDecimal(vs[1]);
			assetUpdate.updata("D=" + m.longValue());
			loginResult.setGold(loginResult.getGold().add(m));
			MonitorUtil.getMoney().addD(m.longValue(), 3);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} else if (type == 888) {//仙玉使用卡
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			String[] vs = goodstable.getValue().split("=");
			BigDecimal m = new BigDecimal(vs[1]);
			assetUpdate.updata("X=" + m.longValue());
			loginResult.setCodecard(loginResult.getCodecard().add(m));
			MonitorUtil.getMoney().addX(m.longValue(), 2);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		} else if (type == 7005) {//仙器礼盒
			String[] v = goodstable.getValue().split("\\|");
			String[] v1 = v[0].split("=")[1].split(" ");
			BigDecimal id = new BigDecimal(v1[random.nextInt(v1.length)]);
			String js = v[1].split("=")[1];
//			// 从物品表获取该仙器物品
			Goodstable good = GameServer.getGood(id);
//			// 获得该类型所有仙器
			List<String> xianqis = GameServer.getXianqiTypeValue().get(id + "|" + js);
//			// 随机出个属性
			int[] a = randomArray(0, xianqis.size() - 1, 1);
			// 修改物品的属性
			good.setValue(xianqis.get(a[0]));
			good.setRole_id(loginResult.getRole_id());// 添加角色ID
			good.setQuality(goodstable.getQuality());
			// 新增该件物品
			AllServiceUtil.getGoodsTableService().insertGoods(good);
			AddGoodUtil.addGood(ctx, good);
			// 添加记录
			AllServiceUtil.getGoodsrecordService().insert(good, null, good.getUsetime(), 3);
		} else if (type == 2041) {//经验丹
			DropUtil.getDrop(loginResult, goodstable.getValue(), null, 22, 1D, null);
		} else if (type == 502) {//召唤兽卡
			PetCard(goodstable, ctx, loginResult);
		} else if (type == 717) {//坐骑卡
			MountCard(goodstable, ctx, loginResult);
		} else if (type == 118) {//抽奖
			Dorp dorp = GameServer.getDorp(goodstable.getValue().split("=")[1]);
			if (dorp == null) {
				return;
			}
			DropUtil.getDrop(loginResult, dorp.getDorpValue(), goodstable.getGoodsname(), 22, 1D, null);
		} else if (type == 2051 || type == 2052 || type == 2057 || type == 1007) {//藏宝图
			Dorp dorp = GameServer.getDorp(type + "");
			if (dorp == null) {
				return;
			}
			DropUtil.getDrop(loginResult, dorp.getDorpValue(), goodstable.getGoodsname(), 22, 1D, null);
		} else if (type == 2525) {//挑战卡
			ChallengeCard(goodstable, ctx, loginResult);
		} else if (Goodtype.TimingGood(type)) {//药瓶使用
			UseMixdeal.TimingGood(goodstable, ctx, loginResult);
		} else if (Goodtype.Medicine(type)) {//药瓶使用
			UseMixdeal.Medicine(goodstable, ctx, loginResult);
		} else if (Goodtype.BlueBack(type)) {//回蓝符文
			UseMixdeal.BlueBack(goodstable, ctx, loginResult);
		} else if (type == 112) {//属性卡
			UseMixdeal.baseCard(goodstable, ctx, loginResult);
		} else if (type == 113 || type == 88 || type == 99 || type == 111) {//其他时效类相关
			UseMixdeal.qtCard(goodstable, ctx, loginResult);
		} else if (type == 1006) {
			hz(goodstable, ctx, loginResult);
		} else if (type == 6699) {//包裹卡
			this.uscExpandCard(goodstable, ctx, loginResult);
		} else if (type == 66668) {//月卡
			UseMixdeal.vipSss(goodstable, ctx, loginResult);
		} else if (type == 924) {//双倍药水
			UseMixdeal.Potion(goodstable, ctx, loginResult);
		}else if (type == 9002) { //法门修炼丹

			if(loginResult.getFmsld() == null) {
				loginResult.setFmsld(0);
			}
			if(loginResult.getFmsld()>10000) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("法门熟练度已满"));
				return;
			}
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			loginResult.setFmsld(loginResult.getFmsld() + 10);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}

		else if (type == 935) {//VIP充值卷
			LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			String[] vs = goodstable.getValue().split("=");
			BigDecimal addC = new BigDecimal(vs[1]);
			assetUpdate.updata("C=" + addC.longValue());
			assert login != null;
			login.setPaysum(login.getPaysum().add(addC));
			login.setMoney((login.getMoney() != null ? login.getMoney() : 0) + addC.intValue());
			MonitorUtil.getMoney().addC(addC.intValue());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}
	}

	//解封卡
	public static void unSeal(Goodstable good,String msg,ChannelHandlerContext ctx, LoginResult login) {
		if (!MonitorLimit.unSeal(msg, "物品:"+good.getRgid())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("账号:"+msg+"没有被封号,不需要使用该道具"));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		UsePetAction.useGood(good,1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setMsg("你已帮账号:"+msg+"解除封号状态");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	//解除禁言卡
	public static void unSilence(Goodstable good,String msg,ChannelHandlerContext ctx, LoginResult login) {
		if (!msg.matches("[0-9]+")) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("请输入数字"));
			return;
		}
		if (!MonitorLimit.unSilence(new BigDecimal(msg), "物品:"+good.getRgid())) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("玩家id:"+msg+"没有被禁言,不需要使用该道具"));
			return;
		}
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USEGOOD);
		UsePetAction.useGood(good,1);
		assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setMsg("你已帮玩家ID:"+msg+"解除禁言");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}
	// 徽章使用
	public static void hz(Goodstable good,ChannelHandlerContext ctx,LoginResult login) {
		login.setSkill_id(Integer.parseInt(good.getValue()));
		SendMessage.sendMessageToMapRoles(login.getMapid(),Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(login.getRoleShow())));
	}
	/**召唤兽卡使用*/
	public void PetCard(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		RoleSummoning pet=GameServer.getPet(new BigDecimal(good.getValue()));
		if (pet==null) {return;}
		pet.setBasishp(pet.getHp());
		pet.setBasismp(pet.getMp());
		// 设置忠诚
		pet.setFaithful(100);
		pet.setGrade(0);pet.setTurnRount(0);
		pet.setBone(0);pet.setSpir(0);
		pet.setPower(0);pet.setSpeed(0);
		pet.setCalm(0);pet.setDragon(0);
		pet.setSpdragon(0);
		pet.setAlchemynum(0);pet.setExp(new BigDecimal(0));
		pet.setOpenSeal(1);
		pet.setRoleid(login.getRole_id());
		if (pet.getSsn()!=null&&pet.getSsn().equals("0")) {
//			pet.setHp(SummonPetAction.getchu(pet.getHp()));pet.setMp(SummonPetAction.getchu(pet.getMp()));
//			pet.setAp(SummonPetAction.getchu(pet.getAp()));pet.setSp(SummonPetAction.getchu(pet.getSp()));
//			pet.setGrowlevel(SummonPetAction.getgroup(pet.getGrowlevel()));
		}
		String yb = pet.getResistance();
		if (yb == null|| yb.equals("")) {
			int p=random.nextInt(SummonPetAction.kxs.length);
			int p2=random.nextInt(SummonPetAction.kxs.length);
			while (p2==p) {p2=random.nextInt(SummonPetAction.kxs.length);}
			pet.setResistance(SummonPetAction.kxs[p]+"|"+SummonPetAction.kxs[p2]);
		}
		AllServiceUtil.getRoleSummoningService().insertRoleSummoning(pet);
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.USERGOOD);
		assetUpdate.setPet(pet);
		assetUpdate.updata("P"+good.getRgid()+"="+good.getUsetime());
		assetUpdate.setMsg("你已帮玩家ID:"+good.getGoodsname()+"解除禁言");
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));

	}
	/**坐骑卡使用*/
	public void MountCard(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		int lvl=Integer.parseInt(good.getValue())/100;
		Mount mount=GameServer.getMount(Sepcies_MixDeal.getRace(login.getSpecies_id()), lvl);
		if (mount!=null) {//获取这只坐骑
			mount.setRoleid(login.getRole_id());
			AllServiceUtil.getMountService().insertMount(mount);
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USERGOOD);
			assetUpdate.setMount(mount);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}
	}
	/**挑战卡使用*/
	public void ChallengeCard(Goodstable good,ChannelHandlerContext ctx,LoginResult login){
		String robotID=good.getValue().split("=")[1];
		FightingForesee fightingForesee = new FightingForesee();
		fightingForesee.setRobotid(robotID);
		fightingForesee.setYidui(login.getTeam());
		fightingForesee.setType(1);
		BattleThreadPool.addBattle(ctx, fightingForesee);
	}
	/**包裹卡使用*/
	public void uscExpandCard(Goodstable goodstable, ChannelHandlerContext ctx, LoginResult loginResult) {
		RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
		roleData.upPackNum(goodstable, ctx, loginResult);
	}
	/**礼包类使用*/
	public void Novice(Goodstable goodstable,ChannelHandlerContext ctx,LoginResult loginResult){
		AssetUpdate assetUpdate=null;

		try {
			RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
			boolean is=false;
			boolean isNovice=false;
			if (goodstable.getType()==60001) {is=true;}
			String[] v = goodstable.getValue().split("\\|");
			for (int i = 0; i < v.length; i++) {
				if (v[i].startsWith("物品")) {
					v=v[i].split("=")[1].split("\\&");
					for (int j = 0; j < v.length; j++) {
						String[] v4 = v[j].split("\\$");
						BigDecimal id=new BigDecimal(v4[0]);
						int sum=Integer.parseInt(v4[1]);
						Goodstable good=GameServer.getGood(id);
						if (good==null) {continue;}
						good.setRole_id(loginResult.getRole_id());
						if ((good.getType()==60001||good.getType()==60002)&&!isNovice) {
							isNovice=true;
							goodstable.setGoodsname(good.getGoodsname());
							goodstable.setUsetime(1);
							goodstable.setValue(good.getValue());
							goodstable.setType(good.getType());
							goodstable.setInstruction(good.getInstruction());
							if (assetUpdate==null) {assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);}
							assetUpdate.setGood(goodstable);

							continue;
						}
						if (is) {good.setQuality(new Long(1));}
						long sid=goodstable.getGoodsid().longValue();
						if ((sid>=70001&&sid<=70030)||(sid>=69001&&sid<=69015)||(goodstable.getType()==825)||goodstable.getType()==-1) {
							if (assetUpdate==null) {
								assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
							}
							XXGDBean xxgdBean=new XXGDBean();
							xxgdBean.setId(id.toString());
							xxgdBean.setSum(sum);
							AddGoodAction.addGood(assetUpdate, goodstable, loginResult, roleData, xxgdBean, AssetUpdate.USEGOOD);
						}else if (EquipTool.canSuper(good.getType())) {
							List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(),good.getGoodsid());
							if (sameGoodstable.size() != 0) {
								if (is) {sameGoodstable.get(0).setQuality(new Long(1));}
								sameGoodstable.get(0).setUsetime(sameGoodstable.get(0).getUsetime()+sum);
								AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
								good=sameGoodstable.get(0);
							}else {
								good.setUsetime(sum);
								AllServiceUtil.getGoodsTableService().insertGoods(good);
							}
							AddGoodUtil.addGood(ctx,good);
							AllServiceUtil.getGoodsrecordService().insert(good,null,sum,3);
						}else {
							for (int k = 0; k < sum; k++) {
								AllServiceUtil.getGoodsTableService().insertGoods(good);
								AddGoodUtil.addGood(ctx,good);
								AllServiceUtil.getGoodsrecordService().insert(good,null,1,3);
							}
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		if (assetUpdate!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
		}
	}
	/**
	 * 随机指定范围内N个不重复的数
	 * 在初始化的无重复待选数组中随机产生一个数放入结果中，
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
	 * 然后从len-2里随机产生下一个随机数，如此类推
	 * @param max  指定范围最大值
	 * @param min  指定范围最小值
	 * @param n  随机数个数
	 * @return int[] 随机数结果集
	 */
	public static int[] randomArray(int min,int max,int n){
		int len = max-min+1;

		if(max < min || n > len){
			return null;
		}

		//初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min+len; i++){
			source[i-min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}

}
