package org.come.action.role;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.RoleTransBean;
import org.come.entity.Goodstable;
import org.come.entity.Mount;
import org.come.entity.Species;
import org.come.handler.SendMessage;
import org.come.model.TaskData;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Calculation.BaseValue;
import come.tool.Calculation.RoleReborn;
import come.tool.FightingData.Sepcies_MixDeal;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
/**
 * 人物转换角色，修改角色种族和基础成长
 * @author 叶豪芳
 * @date 2018年1月10日 下午2:56:59
 *
 */
public class RoleTransAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		int zhuantype=Integer.parseInt(message.substring(0, 1));
		BigDecimal species_id=new BigDecimal(message.substring(2));
		Species species=Sepcies_MixDeal.getSpecies(species_id);
		Goodstable goodstable=null;
		if (loginResult.getMarryObject() != null&& !loginResult.getMarryObject().equals("")) {
			if (Sepcies_MixDeal.getSex(species_id)!=Sepcies_MixDeal.getSex(loginResult.getSpecies_id())) {
				SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("你已结婚，只能转同一性别的角色！"));
				return;
			}
		}
		if (species_id.compareTo(loginResult.getSpecies_id())!=0&&roleData.getPrivateData().isSkill("T")) {
			SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("换职业需要清空天演策"));
			return;
		}
		if (zhuantype==0) {
			if (loginResult.getGold().compareTo(new BigDecimal(100000)) < 0) {
				SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("金钱不足 10W 两！"));
				return;
			}
		}else {
			int grade = 0;
			if (loginResult.getGrade() == 102) {
				grade = 1;
			}else if (loginResult.getGrade() == 210) {
				grade = 2;
			}else if (loginResult.getGrade() == 338) {
				grade = 3;
			}else if (loginResult.getGrade() == 459) {
				grade = 4;
			}else {
				SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("等级不满足条件"));
				return;
			}
			if (grade==4) {
				TaskData taskData=GameServer.getTaskName("天地劫(挑战转鬼)");
				if (taskData!=null&&roleData.getTaskWC(taskData.getTaskSetID())<1) {
					SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("还未完成天地劫任务"));
					return;
				}
			}

			List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), new BigDecimal(192));
			if (goods.size()==0) {
				SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("你并没有携带冥钞，速去准备吧！"));
				return;
			}
			goodstable=goods.get(0);
		}
		if (goodstable!=null) {
			goodstable.goodxh(1);
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.setData("G"+goodstable.getRgid()+"="+goodstable.getUsetime());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
			AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		}else {
			loginResult.setGold(new BigDecimal(loginResult.getGold().longValue() - 100000));
			MonitorUtil.getMoney().useD(100000L);
		}
		boolean isM=species.getRace_id().compareTo(loginResult.getRace_id())!=0;
		// 角色性别// 种类名称// 种族ID	// 种类ID// 种族名称
		loginResult.setSex(species.getSex());
		loginResult.setLocalname(species.getLocalname());
		loginResult.setRace_id(species.getRace_id());
		loginResult.setSpecies_id(species.getSpecies_id());
		loginResult.setRace_name(Sepcies_MixDeal.getRaceString(species.getSpecies_id()));
		if (zhuantype==0) {
			roleData.getPrivateData().setSkills("S", changeSkill(roleData.getPrivateData().getSkill("S"), species_id));
			roleData.getPrivateData().setSkills("F", null);
		}else {
			loginResult.setExperience(new BigDecimal(0));
			loginResult.setGrade(loginResult.getGrade() + 1);
			loginResult.setTurnAround(loginResult.getTurnAround() + 1);
			/**包裹卡*/
			if (loginResult.getAttachPack() > 2) {
				loginResult.setAttachPack(2);
			}
			roleData.setGoodMax(24 + (loginResult.getAttachPack() + (loginResult.getTurnAround() >= 4 ? 3 : loginResult.getTurnAround())) * 24);
			if (loginResult.getTurnAround() <= 3) {// 重置转生抗性
				String v = RoleReborn.reborn(roleData.getPrivateData().getSkill("S"), roleData.getPrivateData().getBorn());
				roleData.getPrivateData().setSkills(null);
				roleData.getPrivateData().setSkills("F", null);
				roleData.getPrivateData().setBorn(v);
				roleData.setBorns(BaseValue.reborn(roleData.getPrivateData().getBorn()));
			} else {
				roleData.getPrivateData().setSkills("S", changeSkill(roleData.getPrivateData().getSkill("S"), species_id));
				roleData.getPrivateData().setSkills("F", null);
			}
		}
		roleData.setSkills(BaseValue.reSkill(roleData.getPrivateData(),loginResult));
		int lvl = BattleMixDeal.lvlint(loginResult.getGrade());
		loginResult.setBone(lvl);
		loginResult.setSpir(lvl);
		loginResult.setPower(lvl);
		loginResult.setSpeed(lvl);
		if (loginResult.getTurnAround() == 4) {
			loginResult.setCalm(lvl);
		}
		loginResult.setHp(new BigDecimal(0));
		loginResult.setMp(new BigDecimal(0));
		SendMessage.sendMessageToMapRoles(ctx,loginResult.getMapid(),Agreement.getAgreement().upRoleShowAgreement(GsonUtil.getGsonUtil().getgson().toJson(loginResult.getRoleShow())));
		RoleTransBean roleTransBean=new RoleTransBean();
		roleTransBean.setLoginResult(loginResult);
		roleTransBean.setPrivateData(roleData.getPrivateData());
		if (isM) {
			List<Mount> mounts=AllServiceUtil.getMountService().selectMountsByRoleID(loginResult.getRole_id());
			for (int i = 0; i < mounts.size(); i++) {
				Mount mount=mounts.get(i);
				Mount mount2=GameServer.getMount(Sepcies_MixDeal.getRace(loginResult.getSpecies_id()), mount.getMountid());
				if (mount2!=null) {
					mount.setMountname(mount2.getMountname());
					AllServiceUtil.getMountService().updateMountRedis(mount);
				}
			}
			roleTransBean.setMounts(mounts);
		}
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().RacialTransformationAgreement(GsonUtil.getGsonUtil().getgson().toJson(roleTransBean)));
	}
	/**更换技能集合*/
	public static String changeSkill(String[] vs,BigDecimal se){
		String[] ses=getSepciesS(getSepciesN(se));
		if (vs!=null) {
			StringBuffer buffer=new StringBuffer();
			for (int i = 0; i < vs.length; i++) {
				String[] vss = vs[i].split("_");
				if (buffer.length()!=0) {buffer.append("#");}
				buffer.append(changeSkillId(Integer.parseInt(vss[0]),ses));
				buffer.append("_");
				buffer.append(vss[1]);
			}
			return buffer.toString();
		}
		return null;
	}
	/**获取将转换成的技能id*/
	public static int changeSkillId(int id,String[] ses){
		int p=0;
		if ((id>=1006&&id<=1010)||(id>=1046&&id<=1050)||(id>=1021&&id<=1025)||(id>=1071&&id<=1075)||(id>=1081&&id<=1085)) {
			p=0;
		}else if ((id>=1011&&id<=1015)||(id>=1051&&id<=1055)||(id>=1026&&id<=1030)||(id>=1061&&id<=1065)||(id>=1071&&id<=1075)||(id>=1091&&id<=1095)) {
			p=1;
		}else {
			p=2;
		}
		String leixing=ses[p];
		int lvl=id%5;
		if (lvl==0) {lvl=5;}
		if (leixing.equals("封印")) {id=1005+lvl;}
		else if (leixing.equals("昏睡")) {id=1010+lvl;}
		else if (leixing.equals("混乱")) {id=1000+lvl;}
		else if (leixing.equals("毒")) {id=1015+lvl;}
		else if (leixing.equals("雷")) {id=1045+lvl;}
		else if (leixing.equals("水")) {id=1050+lvl;}
		else if (leixing.equals("风")) {id=1040+lvl;}
		else if (leixing.equals("火")) {id=1055+lvl;}
		else if (leixing.equals("震慑")) {id=1020+lvl;}
		else if (leixing.equals("加攻")) {id=1025+lvl;}
		else if (leixing.equals("加速")) {id=1035+lvl;}
		else if (leixing.equals("盘丝")) {id=1030+lvl;}
		else if (leixing.equals("遗忘")) {id=1070+lvl;}
		else if (leixing.equals("鬼火")) {id=1060+lvl;}
		else if (leixing.equals("三尸虫")) {id=1065+lvl;}
		else if (leixing.equals("魅惑")) {id=1075+lvl;}
		else if (leixing.equals("霹雳")) {id=1080+lvl;}
		else if (leixing.equals("沧波")) {id=1085+lvl;}
		else if (leixing.equals("甘霖")) {id=1090+lvl;}
		else if (leixing.equals("扶摇")) {id=1095+lvl;}
		return id;
	}
	/**获取种族*/
	public static String getSepciesN(BigDecimal se){
		int id=se.intValue();
		if (id==23001||id==23002||id==23003) {return "男鬼";}
		else if (id==23004||id==23005||id==23006) {return "女鬼";}
		else if (id==24001||id==24002||id==24003) {return "男龙";}
		else if (id==24004||id==24005||id==24006) {return "女龙";}
		else if (id==22001||id==22002||id==22003||id==22007||id==22009) {return "男仙";}
		else if (id==22004||id==22005||id==22006||id==22008||id==22010) {return "女仙";}
		else if (id==21001||id==21002||id==21003||id==21007||id==21009) {return "男魔";}
		else if (id==21004||id==21005||id==21006||id==21008||id==21010) {return "女魔";}
		else if (id==20001||id==20002||id==20003||id==20007||id==20009) {return "男人";}
		else if (id==20004||id==20005||id==20006||id==20008||id==20010) {return "女人";}
		return null;
	}
	/**获取种族*/
	public static String[] getSepciesS(String se){
		if (se.equals("男鬼")) {return new String[]{"遗忘","鬼火","三尸虫"};}
		else if (se.equals("女鬼")) {return new String[]{"遗忘","鬼火","魅惑"};}
		else if (se.equals("男仙")) {return new String[]{"雷","水","风"};}
		else if (se.equals("女仙")) {return new String[]{"雷","水","火"};}
		else if (se.equals("男魔")) {return new String[]{"震慑","加攻","加速"};}
		else if (se.equals("女魔")) {return new String[]{"震慑","加攻","盘丝"};}
		else if (se.equals("男人")) {return new String[]{"封印","昏睡","混乱"};}
		else if (se.equals("女人")) {return new String[]{"封印","昏睡","毒"};}
		else if (se.equals("男龙")) {return new String[]{"霹雳","甘霖","扶摇"};}
		else if (se.equals("女龙")) {return new String[]{"霹雳","甘霖","沧波"};}
		return null;
	}
}
