package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;
import come.tool.FightingDataAction.PhyAttack;

public class TJ_DNTGAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//新增技能 大闹天宫
		List<ManData> nomyDatas=MixDeal.getdaji(5, manData.getCamp(), events, battlefield);
		if (nomyDatas.size()==0)return;
		MixDeal.BB_DNTG(nomyDatas, battlefield,manData,null,skill);
		MixDeal.skillmove(nomyDatas, battlefield,manData,"10");
		List<FightingEvents> gjEventss=new ArrayList<>();
		long zap=0;
		long zap7=0;
		long zap8=0;
//		1305	同仇敌忾								物理攻击时有25%几率召唤本方所有在场召唤兽一起作战，本次攻击获得其它召唤兽的攻击力加成(上限15万)。
		FightingSkill skill_4=null,skill_5=null,skill_6=null,skill_7=null,skill_8=null;//箭无虚发,一帆风顺,一鼓作气,同仇敌忾,一击毙命,横扫四方
		for (int i=manData.getSkills().size()-1;i>=0;i--) {
			FightingSkill sl=manData.getSkills().get(i);
			if (sl.getSkillbeidong()!=1) {continue;}
			if (sl.getSkilltype().equals(TypeUtil.BB_E_TCDK)) {skill_4=sl;}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_YJBM)) {if (!PK_MixDeal.isPK(battlefield.CurrentRound)) {skill_5=sl;}}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_HSSF)) {skill_6=sl;}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_DSFS)) {skill_7=sl;zap7=(long) (manData.getKangluobao()*175);}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_QKYZ)) {
				skill_8=sl;
				zap8=PhyAttack.getMoney(manData, battlefield);
				if (zap8>=500000) {zap8=500000;}
				zap8=(long) (zap8/2.8);
			}
		}
		List<ManData> zzs=null;
		double ljjc = 0;
		if (skill_4!=null&&Battlefield.isV(skill_4.getSkillgain())) {
			zzs=new ArrayList<>();
			for (int i = battlefield.fightingdata.size()-1; i >=0; i--) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getType()!=1||data.getCamp()!=manData.getCamp()||data.getStates()!=0) {continue;}
				if (data.getMan()==manData.getMan()) {continue;}
				zap+=data.getAp();
				zzs.add(data);
			}
			if (zap>=150000) {zap=150000;}
		}
		int Zap=manData.getAp();

		for (int i =  nomyDatas.size()-1; i >=0; i--) {
			ManData data=nomyDatas.get(i);
			//已攻击次数
			int g=0;
			int maxg=GMax(manData, data, ljjc, battlefield);
			while (data.getStates()==0&&g<maxg) {
				if (gjEventss.size()<=g) {
					FightingEvents gjEvents=new FightingEvents();
					List<FightingState> zls1=new ArrayList<>();
					gjEvents.setAccepterlist(zls1);
					gjEventss.add(gjEvents);
				}
				List<FightingState> zls=gjEventss.get(g).getAccepterlist();
				g++;
				//long ap=i==0?Zap:(long) (Zap*skill.getSkillhurt()/100.0);
				long ap=Zap;
				if (i==0) {ap+=zap;}
				FightingState ace=new FightingState();
				if (skill_7!=null&&Battlefield.isV(skill_7.getSkillgain())) {ap+=zap7;ace.setText("大圣附身#2");ace.setSkillskin(TypeUtil.BB_E_DSFS);}
				else if (skill_8!=null&&Battlefield.isV(skill_8.getSkillgain())) {ap+=zap8;ace.setText("乾坤一掷#2");ace.setSkillskin(TypeUtil.BB_E_QKYZ);}
				ace.setStartState("被攻击");

				ap=PhyAttack.Hurt(ap,1,manData,data,TypeUtil.PTGJ,ace,zls,battlefield,0,0);
				ChangeFighting acec=new ChangeFighting();
				acec.setChangehp((int)-ap);
				if (i==0&&skill_5!=null&&Battlefield.isV(skill_5.getSkillgain())) {
					FightingPackage.ChangeProcess(acec,null,data,ace,TypeUtil.ZSSH,zls,battlefield);
					ace.setText("不堪一击的选手#2");
				}else {
					FightingPackage.ChangeProcess(acec,null,data,ace,TypeUtil.PTGJ,zls,battlefield);
				}
				if (i==0) {PhyAttack.feedback(TypeUtil.PTGJ, manData, ap, battlefield, zls);}
				PhyAttack.neidan(TypeUtil.PTGJ,manData,data,Zap,battlefield, zls,i==0?3:4,i,skill.getSkillgain());
				if (i==0&&skill_6!=null&&(Battlefield.isV(skill_6.getSkillgain()/20))) {
					List<ManData> datas=battlefield.getZW(data);
					for (int j = datas.size()-1; j >=0; j--) {
						FightingState ace1=new FightingState();
						ManData nomyData2=datas.get(j);
						if (nomyData2.getStates()!=0)continue;
						ChangeFighting acec1=new ChangeFighting();
						ap=PhyAttack.Hurt((long)(Zap*(skill_6.getSkillgain())/100D),g,manData,nomyData2,TypeUtil.PTGJ,ace1,zls,battlefield,0,0);
						acec1.setChangehp((int)-ap);
						FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, TypeUtil.PTGJ, zls, battlefield);
					}
				}
				FightingState move=MixDeal.skillmove(data,manData,"9");
				zls.add(move);
				if (i==0&&zap!=0&&zzs!=null) {
					move.setText("合力一击#2");
					for (int k = 0; k < zzs.size(); k++) {
						ManData zz=zzs.get(k);
						zls.add(MixDeal.skillmove(data,zz,"9"));
					}
				}
			}
		}
		for (int i = 0; i < gjEventss.size(); i++) {
			battlefield.NewEvents.add(gjEventss.get(i));
		}
	}

	/**封装的连击次数*/
	public static int GMax(ManData manData,ManData nomyData,double jc,Battlefield battlefield){
		jc += manData.ljv;
		if (nomyData==null) {
			return 1+manData.ljs(jc);
		}
		int maxg=1+manData.ljs(jc-(nomyData.getSkillType(TypeUtil.TJ_YCDY)==null?0:15));
		if (PK_MixDeal.isPK(battlefield.BattleType)) {
			GroupBuff buff=battlefield.getBuff(nomyData.getCamp(), TypeUtil.BB_QZ);
			if (buff!=null) {
				if (maxg>3) {maxg=3;}
			}
		}
		return maxg;
	}

}