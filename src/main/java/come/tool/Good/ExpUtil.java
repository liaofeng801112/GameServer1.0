package come.tool.Good;

import java.math.BigDecimal;

import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.PalData;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import come.tool.Battle.BattleEnd;
import come.tool.Battle.BattleMixDeal;

public class ExpUtil {
	/**扣除经验处理*/
	public static void RoleRemoveExp(LoginResult loginResult,long removeExp){
		int minLvl=minRoleLvl(loginResult);
		int grade=loginResult.getGrade();
		long exp=loginResult.getExperience().longValue()-removeExp;
		boolean up=false;
		while (exp<0&&(grade>minLvl)) {
			grade--;
			int lvl=BattleMixDeal.lvlint(grade);
			exp+=getRoleExp(loginResult.getTurnAround(),lvl);
			loginResult.setGrade(lvl);
		    loginResult.setBone(lvl);
		    loginResult.setSpir(lvl);
		    loginResult.setPower(lvl);
		    loginResult.setSpeed(lvl);
			if (loginResult.getTurnAround()>=4) {loginResult.setCalm(lvl);}
			up=true;
		}
		loginResult.setExperience(new BigDecimal(exp));
		if (up) {
			loginResult.setGrade(grade);
			loginResult.setHp(new BigDecimal(0));
			loginResult.setMp(new BigDecimal(0));
		}
	}
	/**进行属性加点,人物升级*/
	public static void increasePointAndValue(LoginResult loginResult){
		// 进行等级的增加
		loginResult.setGrade(loginResult.getGrade() + 1);
		// 属性加点
		loginResult.setBone(loginResult.getBone() + 1);
		loginResult.setSpir(loginResult.getSpir() + 1);
		loginResult.setPower(loginResult.getPower() + 1);
		loginResult.setSpeed(loginResult.getSpeed() + 1);
		if (loginResult.getTurnAround() >= 4) {
			loginResult.setCalm(loginResult.getCalm() + 1);
		}
		loginResult.setHp(new BigDecimal(0));
		loginResult.setMp(new BigDecimal(0));
	}
	/**获得经验处理*/
	public static void RoleExp(LoginResult loginResult,long addexp){
//		//判断等级最大能升多少级
//		//获取当前等级生的最大等级
		int mostLevel=maxRoleLvl(loginResult);	
		int grade=loginResult.getGrade();
		long exp=loginResult.getExperience().longValue()+addexp;
		long maxexp=getRoleExp(loginResult.getTurnAround(),BattleMixDeal.lvlint(grade));
		boolean up=false;
		while ((exp>=maxexp)&&(grade<mostLevel)) {
			exp-=maxexp;
			grade++;
			loginResult.setGrade(grade);
		    loginResult.setBone(loginResult.getBone()+1);
		    loginResult.setSpir(loginResult.getSpir()+1);
		    loginResult.setPower(loginResult.getPower()+1);
		    loginResult.setSpeed(loginResult.getSpeed()+1);
			if (loginResult.getTurnAround()>=4) {
			    loginResult.setCalm(loginResult.getCalm()+1);
			}
			maxexp=getRoleExp(loginResult.getTurnAround(),BattleMixDeal.lvlint(grade));
			up=true;
		}
		loginResult.setExperience(new BigDecimal(exp));
		if (up) {
			loginResult.setGrade(grade);
			loginResult.setHp(new BigDecimal(0));
			loginResult.setMp(new BigDecimal(0));
			SendMessage.sendMessageToMapRoles(loginResult.getMapid(),Agreement.getAgreement().RoleLevelUpAgreement(loginResult.getRolename()+"|"+grade));	
		}
	}
	/**依据经验进行升级判断,内丹升级*/
	public static void IncreaseNedanExp(RoleSummoning pet,Goodstable goodstable,BattleEnd battleEnd,long addexp){
		String[] vs = goodstable.getValue().split("\\|");
		String[] stringLevel = vs[2].split("=")[1].split("转");
		int  zs =Integer.parseInt(stringLevel[0]);
		int  lvl=Integer.parseInt(stringLevel[1]);
		long exp=Long.parseLong(vs[3].split("=")[1])+addexp;
		int petlvl=BattleMixDeal.petLvlint(pet.getGrade());	
		int maxlvl=getNedanMostLevel(zs);
		if (zs>=pet.getTurnRount()&&lvl>=petlvl) {
//			ZhuFrame.getZhuJpanel().addPrompt2("内丹等级不可超过召唤兽等级");
			return;
		}
		if (lvl>=200) {
//	        ZhuFrame.getZhuJpanel().addPrompt2("当前内丹已达最大等级！！！");
	        return;
	    }
		long maxexp=getBBNeiExp(zs, lvl+1);
	    xx:while (exp>=maxexp&&exp>0) {//判断是否最高级最高转
			if (lvl+1>maxlvl) {
				if (zs>=4) {
//					ZhuFrame.getZhuJpanel().addPrompt2("当前内丹已达最大等级！！！");
					break xx;
				}else if (zs+1>pet.getTurnRount()) {
//					ZhuFrame.getZhuJpanel().addPrompt2("内丹转生次数不可超过召唤兽");
					break xx;
				}else {
					zs++;
					lvl=0;
					maxexp=getBBNeiExp(zs, lvl+1);
					exp=0;
				}
			}else if (zs>=pet.getTurnRount()&&lvl+1>petlvl) {
//				ZhuFrame.getZhuJpanel().addPrompt2("内丹等级不可超过召唤兽等级");
				break xx;
			}else {
				exp=exp-maxexp;
				lvl++;
				maxexp=getBBNeiExp(zs, lvl+1);
			}	
	    }	
		StringBuffer buffer=new StringBuffer();
		buffer.append(vs[0]);
		buffer.append("|");
		buffer.append(vs[1]);
		buffer.append("|内丹等级=");
		buffer.append(zs);
		buffer.append("转");
		buffer.append(lvl);
		buffer.append("|经验=");
		buffer.append(exp);
		goodstable.setValue(buffer.toString());
		AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
		battleEnd.upAssetData("G"+goodstable.getRgid()+"="+zs+"="+lvl+"="+exp);
		battleEnd.upMsg("你的召唤兽"+pet.getSummoningname()+"的"+goodstable.getGoodsname()+"内丹获得"+addexp+"经验");
	}
	/**判断最大等级*/
	public static int maxRoleLvl(LoginResult loginResult){
		switch (loginResult.getTurnAround()) {
		case 0:return 102;
        case 1:return 210;
        case 2:return 338;
        case 3:return 459;
        case 4:return loginResult.getGrade();
		}
		return 519;
	}
	/**判断最低等级*/
	public static int minRoleLvl(LoginResult loginResult){
		switch (loginResult.getTurnAround()) {
		case 0:return 1;
        case 1:return 103;
        case 2:return 211;
        case 3:return 339;
        case 4:return 460;
		}
		return 460;
	}
	public static long getRoleExp(int TurnAround,int grade){
		if (grade>199) grade=199;
		long exp=GameServer.getExp(grade);
		if (TurnAround>=3)exp*=3;
		if (grade>100&&exp<5000000) {
			exp=new BigDecimal("6181894660").longValue();
		}
		return exp;
	}
	/**内丹经验*/
	public static long getBBNeiExp(int TurnAround,int grade){
		return (long) (getRoleExp(TurnAround, grade)*0.7);
	}
	/***/
	public static void PetExp(RoleSummoning pet,long addexp){
		//获取当前等级生的最大等级
		int mostLevel=getPetMostLevel(pet);	
		int grade=pet.getGrade();
		long exp=pet.getExp().longValue()+addexp;
		long maxexp=getRoleExp(pet.getTurnRount(),BattleMixDeal.petLvlint(grade));
		while ((exp>=maxexp)&&(grade<mostLevel)) {
			exp-=maxexp;
			grade++;
			pet.setBone(pet.getBone()+1);
			pet.setSpir(pet.getSpir()+1);
			pet.setPower(pet.getPower()+1);
			pet.setSpeed(pet.getSpeed()+1);
			// 升级后忠诚变为100
			pet.setFaithful(100);
			if (pet.getTurnRount()>=4) {pet.setCalm(pet.getCalm()+1);}
			maxexp=getRoleExp(pet.getTurnRount(),BattleMixDeal.petLvlint(grade));
		}
		pet.setExp(new BigDecimal(exp));
		if (grade>pet.getGrade()) {
			pet.setGrade(grade);
			pet.setFaithful(100);
			pet.setBasishp(0);
			pet.setBasismp(0);
		}
	}
	/**依据转生等级判断升级的最大等级(召唤兽)*/
	public static int getPetMostLevel(RoleSummoning pet){
		switch (pet.getTurnRount()) {
		case 0:return 100;
        case 1:return 221;
        case 2:return 362;
        case 3:return 543;
		}
		return 744;
	}
	/**添加坐骑经验 和坐骑熟练度*/
	public static void MountAddES(BattleEnd battleEnd,Mount mount,int addexp,int addsld){
		battleEnd.upMsg("坐骑 " + mount.getMountname() + "获得" + addexp + "点经验," + addsld + "点技能熟练度");
		int fs = 0;
		if (addexp > 0) {// 判断坐骑的等级是否达到100级
			if (mount.getMountlvl() < 100 || (mount.getMountlvl() > 100 && mount.getMountlvl() < 200)) {// 达到最高等级
				// 进行升级判断
				MountExp(mount, addexp);	
			} else {
				fs++;
				battleEnd.upMsg("坐骑 " + mount.getMountname() + " 已达最高等级100级！！");
			}
		}
		if (addsld > 0) {
			int up = 100000;
			if (mount.getMountlvl() > 100) {
				up = 150000;
			}
			if (mount.getProficiency() < up) {// 达到峰值
				int proficiency = mount.getProficiency() + addsld;
				if (proficiency > up) {
					proficiency = up;
				}
				mount.setProficiency(proficiency);
			} else {
				fs++;
				battleEnd.upMsg("坐骑 " + mount.getMountname() + " 的技能熟练度已达到峰值！！");
			}
		}
		if (fs!=2) {
			AllServiceUtil.getMountService().updateMountRedis(mount);
			battleEnd.upAssetData("M"+mount.getMid()+"="+mount.getMountlvl()+"="+mount.getExp()+"="+mount.getProficiency());
		}
	}
	/**
	 * 依据经验进行升级判断,坐骑升级
	 * @throws Exception 
	 */
	public static void MountExp(Mount mount,int addexp){
		//判断等级最大能升多少级
		int grade = mount.getMountlvl();
		int exp = mount.getExp()+addexp;
		long maxexp = getMountExp(grade);
		while ((exp>=maxexp)) {
			if (grade==100||grade>=200) {break;}
			grade++;
			maxexp=getMountExp(grade);
		}
		mount.setExp(exp);
		if (grade>mount.getMountlvl()) {
			mount.setMountlvl(grade);
		}
	}
	public static int getMountExp(int grade){
		if (grade>100) {grade-=90;}//升到下一级所需经验
		int nextexp = (grade+1)*(grade+1)*15;
		return nextexp;
	}
	/**依据转生等级判断升级的最大等级(内丹)*/
	public static int getNedanMostLevel(int turn){
		return turn==0?100:turn==1?120:turn==2?140:turn==3?170:200;
	}
	/**灵宝法宝升级*/
	public static String LFExp(Lingbao lingbao,long addexp){
	     int lvl=lingbao.getLingbaolvl().intValue();
	     long exp=lingbao.getLingbaoexe().longValue();
	     long maxexp=LFExp(lvl);
	     exp+=addexp;
	     StringBuffer buffer=new StringBuffer();
	     buffer.append("你的");
	     buffer.append(lingbao.getBaoname());
	     buffer.append("获得了");
	     buffer.append(LFExptoString(addexp));
	     buffer.append("道行");
		 boolean l=false;
	     while (exp>=maxexp) {
			 if (lvl>=200) {
			    break;	
			 }
			 if (lvl!=0&&lvl%30==0) {
				 exp=maxexp;
				 buffer.append("|突破后才可继续升级");
				 break;	
			 }
			 exp-=maxexp;
			 lvl++;
			 maxexp=LFExp(lvl);
			 l=true;
		}
	    if (l) {
	        buffer.append("|你的");
	 	    buffer.append(lingbao.getBaoname());
	 	    buffer.append("升级了");
		}
		lingbao.setLingbaolvl(new BigDecimal(lvl));
		lingbao.setLingbaoexe(new BigDecimal(exp));
		return buffer.toString();
	}
	/**灵宝契合度*/
	public static String LFqh(Lingbao lingbao, long addqh) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("你的");
		buffer.append(lingbao.getBaoname());
		buffer.append("获得了");
		buffer.append(LFqhtoString(addqh));
		buffer.append("契合度");
		return buffer.toString();
	}
	public static void PalExp(BattleEnd battleEnd,Pal pal,long addExp){
		PalData palData=GameServer.getPalData(pal.getpId());
		if (palData==null) {return;}
		int  lvl=pal.getLvl();
		long exp=pal.getExp();
		long maxExp=ExpUtil.palExp(lvl);
		if ((lvl==60||lvl==100||lvl==140||lvl==180)&&exp>=maxExp) {
			battleEnd.upMsg("伙伴已经达到等级上限,请先去突破");
		}else if (lvl>=200) {
			return;
		}
		exp+=addExp;
	    while (exp>maxExp) {
            if (lvl==60||lvl==100||lvl==140||lvl==180||lvl==200) {
				break;
			}
	    	exp-=maxExp;
	    	lvl++;
	    	maxExp=ExpUtil.palExp(lvl);
		}
	    pal.setExp(exp);
	    pal.setLvl(lvl);
	    AllServiceUtil.getPalService().updatePal(pal);
	    battleEnd.upAssetMsg("你的伙伴:#R"+palData.getName()+"#Y获得#R"+addExp+"#Y经验");
	    battleEnd.getAssetUpdate().setPal(pal);
	}
	//获取灵宝法宝的经验
	public static long LFExp(int lvl){return lvl*lvl*15-(lvl-1)*(lvl-1)*15;}
	//获取灵宝法宝的等级总经验
	public static long LFExp2(int lvl){return lvl*lvl*15;}
	//灵宝经验描述转换    12时辰一天  一年365天   1年1天1时辰 
	public static long YEAR=12*365;
	public static long DAY=12;
	public static String LFExptoString(long exp){
	     StringBuffer buffer=new StringBuffer();
	     buffer.append(exp/YEAR);
	     buffer.append("年");
	     exp%=YEAR;
	     buffer.append(exp/DAY);
	     buffer.append("天");
	     buffer.append(exp%=DAY);
	     buffer.append("时辰");
	     return buffer.toString();
	}
	public static String LFqhtoString(long exp){
		StringBuffer buffer=new StringBuffer();
		buffer.append("10000点");
		return buffer.toString();
	}
	//获取伙伴经验 
	public static long palExp(int lvl){
		return 3000*(lvl-20);
	}
}
