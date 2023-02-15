package come.tool.FightingDataAction;

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

public class SplitSoul implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
							   String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		boolean isHSSF=TypeUtil.BB_E_HSSF.equals(type);
		type=TypeUtil.PTGJ;
		FightingSkill fightingSkill=manData.getAttacks("暗影离魂").getSkill();
		int sum=fightingSkill.getSkillsum()+(Battlefield.random.nextInt(100)<fightingSkill.getSkillgain()?1:0);
		List<ManData> nomyDatas=MixDeal.getdaji(sum, manData.getCamp(), fightingEvents, battlefield);
		if (nomyDatas.size()==0)return;

		// 孟极触发分魂先画出抬头动作
		FightingEvents gjEvents = new FightingEvents();
		List<FightingState> list = new ArrayList<>();
		FightingState gjz = new FightingState();
		gjz.setCamp(manData.getCamp());
		gjz.setMan(manData.getMan());
		gjz.setStartState("特效1");
		list.add(gjz);

		//4000是孟极 分身影子
		//FightingEvents moveEvents=new FightingEvents();
		for (int i =  nomyDatas.size()-1; i >=0; i--) {
			ManData data = nomyDatas.get(i);
			FightingState move = new FightingState();
			move.setCamp(manData.getCamp());
			move.setMan(manData.getMan());
			move.setSkillskin("4000");
			move.setStartState("技能移动");
			move.setEndState(data.getCamp()+"|"+data.getMan()+"|3");
			list.add(move);
		}
		//moveEvents.setAccepterlist(moves);
		//battlefield.NewEvents.add(moveEvents);

		gjEvents.setAccepterlist(list);
		battlefield.NewEvents.add(gjEvents);

		//MixDeal.skillmove(nomyDatas, battlefield,manData,"4000");



		double ljl;
		int ljv;
		long ap = 0;
		long zap=0;
		long zap7=0;
		long zap8=0;
		List<FightingEvents> gjEventss=new ArrayList<>();
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

		// 势不可遏附加伤害
		long sbke = 0;

		for (int i =  nomyDatas.size()-1; i >=0; i--) {
			ManData data=nomyDatas.get(i);
			ljl=manData.getQuality().getRolefljl()+manData.ljv-(data.getSkillType(TypeUtil.TJ_YCDY)==null?0:15);
			ljv=(int) manData.getQuality().getRolefljv();
			FightingSkill skill5=manData.getAppendSkill(9811);
			if (skill5!=null) {
				ljl-=skill5.getSkillhurt();
			}
			if (i!=0) {ljl=ljl*0.7;ljv=(int) (ljv*0.7);}
			//最大攻击次数
			int maxg=1+(Battlefield.random.nextInt(100)<ljl?ljv:0);
			if (PK_MixDeal.isPK(battlefield.BattleType)) {
				GroupBuff buff=battlefield.getBuff(data.getCamp(), TypeUtil.BB_QZ);
				if (buff!=null) {
					if (maxg>3) {
						maxg=3;
					}
				}
			}
			//已攻击次数
			int g=0;
			while (data.getStates()==0&&g<maxg) {
				if (gjEventss.size()<=g) {
					FightingEvents gjEvents2=new FightingEvents();
					List<FightingState> zls1=new ArrayList<>();
					gjEvents2.setAccepterlist(zls1);
					gjEventss.add(gjEvents2);
				}
				List<FightingState> zls=gjEventss.get(g).getAccepterlist();

				// 处理灵犀-火冒三丈、攻守兼备
				int hmgs = manData.executeHmsz(zls);
				if (hmgs == 2) {
					// 势不可揭额外攻击
					sbke = manData.getAp();
				} else {
					sbke = 0;
				}

				// 本次攻击为连击，并且为连击的第一次攻击
				if (g == 0 && maxg > 1) {
					// 处理灵犀-惊涛拍岸
					maxg = manData.executeJtpa(list,maxg);
				}

				ap=manData.getAp();
				if (i!=0){ap=(int) (ap*fightingSkill.getSkillhurt()/100.0);}
				if (i==0) {ap+=zap;}

				g++;
				FightingState ace=new FightingState();
				ace.setStartState("被攻击");
				if (g==1) {
					ace.setSkillskin("4001");
				}else {
					ace.setSkillskin(g%3==1?"4002":g%3==2?"4003":"4004");
				}
				if (skill_7!=null&&Battlefield.isV(skill_7.getSkillgain())) {ap+=zap7;ace.setText("大圣附身#2");ace.setSkillskin(TypeUtil.BB_E_DSFS);}
				else if (skill_8!=null&&Battlefield.isV(skill_8.getSkillgain())) {ap+=zap8;ace.setText("乾坤一掷#2");ace.setSkillskin(TypeUtil.BB_E_QKYZ);}
				ap=PhyAttack.Hurt(sbke+ap,g,manData,data,type,ace,zls,battlefield,0,0);
				ChangeFighting acec=new ChangeFighting();
				acec.setChangehp((int)-ap);

				if (i==0&&skill_5!=null&&Battlefield.isV(skill_5.getSkillgain())) {
					FightingPackage.ChangeProcess(acec,null, data, ace, TypeUtil.ZSSH, zls, battlefield);
					ace.setText("不堪一击的选手#2");
				}else {
					FightingPackage.ChangeProcess(acec,null, data, ace, TypeUtil.PTGJ, zls, battlefield);
				}
				if (i==0) {PhyAttack.feedback(type, manData, ap, battlefield, zls);}
				PhyAttack.neidan(type,manData,data,ap,battlefield, zls, g,i,0);
				if (i==0&&zap!=0&&zzs!=null) {
					ace.setText("合力一击#2");
					for (int k = 0; k < zzs.size(); k++) {
						ManData zz=zzs.get(k);
						zls.add(MixDeal.skillmove(data,zz,"9"));
					}
				}
				if (i==0&&skill_6!=null&&(isHSSF||Battlefield.isV(skill_6.getSkillgain()/20))) {
					List<ManData> datas=battlefield.getZW(data);
					for (int j = datas.size()-1; j >=0; j--) {
						FightingState ace1=new FightingState();
						ManData nomyData2=datas.get(j);
						if (nomyData2.getStates()!=0)continue;
						ChangeFighting acec1=new ChangeFighting();
						ap=PhyAttack.Hurt((long)(sbke+manData.getAp()*(skill_6.getSkillgain())/100D),g,manData,nomyData2,type,ace1,zls,battlefield,0,0);
						acec1.setChangehp((int)-ap);
						FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, type, zls, battlefield);
					}
				}
				if (data.getStates()!=0||data.xzstate(TypeUtil.FY)!=null){//死亡中断连击
					g=maxg;

					if (data.getStates()!=0) {
						// 目标死亡判定一往无前
						manData.executeYwwq(gjz);
					}
				}
			}

			// 灵犀-化险为夷
			manData.addDun(ap, gjz);
		}
		for (int i = 0; i < gjEventss.size(); i++) {
			battlefield.NewEvents.add(gjEventss.get(i));
		}

		//被打击人的状态
		ManData nomyData=nomyDatas.get(0);
		if (nomyData.getStates()!=0) {
			//追击判断
			FightingSkill skill=manData.getSkillType("追击");
			if (skill!=null) {
				if (Battlefield.random.nextInt(100)+1<manData.getSkillType("追击").getSkillhurt()) {
					List<ManData> zjdata=MixDeal.get(false, nomyData, 0, manData.getCamp(), 0, 0, 0, 0, 1, -1, battlefield,1);
					if (zjdata.size()!=0) {
						nomyData=zjdata.get(0);//获取追击人
						FightingEvents events=new FightingEvents();
						FightingState org=new FightingState();
						org.setCamp(manData.getCamp());
						org.setMan(manData.getMan());
						org.setStartState(TypeUtil.PTGJ);
						events.setOriginator(org);
						List<FightingState> acs=new ArrayList<>();
						FightingState ac=new FightingState();
						ac.setCamp(nomyData.getCamp());
						ac.setMan(nomyData.getMan());
						acs.add(ac);
						// 怒不可揭判定
						manData.executeNbkj(1);
						events.setAccepterlist(acs);
						DataActionType.getActionById(1).analysisAction(manData,events,"追击",battlefield);
						return;
					}
				}
			}
		}

		// 灵犀-战意分裂（不加追击数）
		if (manData!=null&&manData.getStates()==0) {
			if (manData.executeZhanyi()) {
				List<ManData> zjdata=MixDeal.get(false, nomyData,0,manData.getCamp(), 0, 0, 0, 0, 1, -1, battlefield,1);
				if (zjdata.size()!=0) {
					nomyData=zjdata.get(0);//获取分裂人
					FightingEvents events=new FightingEvents();
					FightingState org=new FightingState();
					org.setCamp(manData.getCamp());
					org.setMan(manData.getMan());
					org.setStartState(TypeUtil.PTGJ);
					org.setText("看我的#G冲冠一怒");
					org.setSkillskin("cgyn");
					events.setOriginator(org);
					List<FightingState> acs=new ArrayList<>();
					FightingState ac=new FightingState();
					ac.setCamp(nomyData.getCamp());
					ac.setMan(nomyData.getMan());
					acs.add(ac);
					// 怒不可揭判定
					manData.executeNbkj(1);
					events.setAccepterlist(acs);
					DataActionType.getActionById(1).analysisAction(manData,events,"分裂",battlefield);
					return;
				}
			}
		}

		//分裂判断
		FightingSkill skill=manData.getSkillType("分裂");
		if (skill!=null) {
			if (Battlefield.random.nextInt(100)+1<manData.getSkillType("分裂").getSkillhurt()) {
				List<ManData> zjdata=MixDeal.get(false, nomyData,0,manData.getCamp(), 0, 0, 0, 0, 1, -1, battlefield,1);
				if (zjdata.size()!=0) {
					nomyData=zjdata.get(0);//获取分裂人
					FightingEvents events=new FightingEvents();
					FightingState org=new FightingState();
					org.setCamp(manData.getCamp());
					org.setMan(manData.getMan());
					org.setStartState(TypeUtil.PTGJ);
					events.setOriginator(org);
					List<FightingState> acs=new ArrayList<>();
					FightingState ac=new FightingState();
					ac.setCamp(nomyData.getCamp());
					ac.setMan(nomyData.getMan());
					acs.add(ac);
					// 怒不可揭判定
					manData.executeNbkj(1);
					events.setAccepterlist(acs);
					DataActionType.getActionById(1).analysisAction(manData,events,"分裂",battlefield);
					return;
				}
			}
		}
	}
}
