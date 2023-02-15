package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
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

public class L_PLCBFYAction implements SpellAction{

	//	9810	惊涛断浪	使用沧海横流时有20%几率使目标进入强化沧波状态，此状态下敌方单位对己方单位使用法宝、召唤兽技能时有{公式一}%几率触发法术躲闪。（仅在与玩家之间战斗有效）
	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill tycSkill=null;
		if (skill.getSkillid()==9510||skill.getSkillid()==9512||skill.getSkillid()==9710||skill.getSkillid()==9711
				||skill.getSkillid()==9811||skill.getSkillid()==9812) {
			tycSkill=skill;
			skill=manData.skillId(MixDeal.getTYSkillId(tycSkill.getSkillid()));
			if (skill==null) {return;}
		}
		boolean isQTHD=tycSkill!=null&&tycSkill.getSkillid()==9512;

		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=null;
		if (isQTHD) {
			datas=battlefield.getTP(battlefield.nomy(manData.getCamp()),events);
		}else {
			datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield);
		}
		if (datas.size()==0){
			FightingState Originator=events.getOriginator();
			if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skill.getSkillname());
			events.setOriginator(null);
			Accepterlist.add(Originator);
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);
//    		9505	奋起直击	如果本回合内使用风雷万钧没有命中敌人，则下回合使用风雷万钧时提升{公式一}%点命中率。
			if (skill.getSkillid()==1084) {
				FightingSkill fightingSkill=manData.getSkillType(TypeUtil.TY_L_PL_FQZJ);
				if (fightingSkill!=null) {
					manData.addAddState2(TypeUtil.TY_L_PL_FQZJ,fightingSkill.getSkillhurt(),0,1);
				}
			}
			return;
		}
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();
		FightingSkill dsSkill=null;
		FightingSkill jgSkill=null;

		double mzjc=manData.getQuality().getRolefmzl()-40 + manData.getQuality().getRolehsds() + manData.executeYszd(3) - manData.executeYszd(2);;//命中加成
		double ljjc=manData.getQuality().getRolefljl();//连击加成
		double dsjc=0;//躲闪加成
		double xs=1;
		AddState addState = manData.xzstate("行气如虹");
		if (addState != null) {
			mzjc = mzjc + (manData.getFmsld()/600);
			ljjc = ljjc + (manData.getFmsld()/215);
		}
		addState = manData.xzstate("法门连击");
		if (addState != null) {
			ljjc=ljjc + (manData.getFmsld()/600);
		}
		addState = manData.xzstate("神不守舍");
		if (addState != null) {
			mzjc=mzjc - (manData.getDffmsld()/143);
		}
		List<FightingSkill> skills=null;
		FightingSkill skillTwo=null;
		if (type.equals(TypeUtil.L_PL)) {
			skills=ControlAction.getL_PL_Skills(manData, skill, battlefield.BattleType);
			FightingSkill fightingSkill=manData.getSkillType(TypeUtil.TY_L_PL_JDBX);
			if (fightingSkill!=null) {mzjc+=fightingSkill.getSkillhurt();}
			if (skill.getSkillid()==1084) {
				fightingSkill=manData.getSkillType(TypeUtil.TY_L_PL_XLTJ);
				if (fightingSkill!=null) {ljjc+=fightingSkill.getSkillhurt();}
				skills=ControlAction.addSkill(manData.getSkillType(TypeUtil.TY_L_PL_SLJL), skills);
				 addState=manData.xzstate(TypeUtil.TY_L_PL_FQZJ);
				if (addState!=null) {mzjc+=addState.getStateEffect();}
			}
			if (PK_MixDeal.isPK(battlefield.BattleType)) {jgSkill=manData.getSkillType(TypeUtil.TY_L_PL_QSYZ);}
			if (isQTHD) {

			}
			fightingSkill=manData.getAppendSkill(9811);
			if (fightingSkill!=null) {
				ljjc-=fightingSkill.getSkillhurt();
			}
		}else if (type.equals(TypeUtil.L_CB)) {
			skills=ControlAction.getL_CB_Skills(manData, skill, battlefield.BattleType);
			FightingSkill fightingSkill=manData.getSkillType(TypeUtil.TY_L_CB_BLJT);
			if (fightingSkill!=null) {mzjc+=fightingSkill.getSkillhurt();}
			fightingSkill=manData.getSkillType(TypeUtil.TY_L_CB_ALXY);
			if (fightingSkill!=null) {xs+=fightingSkill.getSkillhurt()/100D;}
			jgSkill=manData.getSkillType(TypeUtil.TY_L_CB_WBYL);
			dsSkill=jgSkill;
			if (skill.getSkillid()==1089) {// 9805	倒海翻江	使用白浪滔天时有{公式一}%几率触发两次伤害效果。
				skillTwo=manData.getSkillType(TypeUtil.TY_L_CB_DHFJ);
			}
		}else if (type.equals(TypeUtil.L_FY)) {
			skills=ControlAction.getL_FY_Skills(manData, skill, battlefield.BattleType);
			FightingSkill fightingSkill=manData.getSkillType(TypeUtil.TY_L_FY_ZFJY);
			if (fightingSkill!=null) {mzjc+=fightingSkill.getSkillhurt();}
			fightingSkill=manData.getSkillType(TypeUtil.TY_L_FY_LKTY);
			if (fightingSkill!=null) {xs+=fightingSkill.getSkillhurt()/100D;}
			jgSkill=manData.getSkillType(TypeUtil.TY_L_FY_YSZY);
			dsSkill=jgSkill;
			if (skill.getSkillid()==1099) {//9705	挥毫回锋	使用凌虚御风时有{公式一}%几率触发两次伤害效果。
				skillTwo=manData.getSkillType(TypeUtil.TY_L_FY_HHHF);
			}
		}
		if (tycSkill!=null&&(tycSkill.getSkillid()==9710||tycSkill.getSkillid()==9711||tycSkill.getSkillid()==9811||tycSkill.getSkillid()==9812)) {
			skills=ControlAction.addSkill(tycSkill, skills);
			tycSkill=null;
		}
		double qsx=manData.getsx(2,type);
		int maxG=1;
		if (isQTHD) {
			xs=tycSkill.getSkillhurt()/100D;
		}

		long Zap=(long) (manData.getAp()*skill.getSkillhurt()/100D*xs*(1+qsx/100D));
		 addState=manData.xzstate(TypeUtil.L_CB);
		if (addState!=null) {mzjc-=addState.getStateEffect();}
		if (TypeUtil.L_PL.equals(type)) {
			maxG+=skill.getSkillcontinued();
			addState=manData.xzstate(TypeUtil.L_LL);
			if (addState!=null) {Zap*=1.16;}
			addState=manData.xzstate("霹雳连击");
			if (addState!=null) {ljjc+=addState.getStateEffect();}
			if (isQTHD) {
				maxG=3;
			}
		}
		mzjc+=skill.getSkillgain();
		mzjc+=qsx/2;
		FightingSkill skill2=manData.getAppendSkill(9203);
		if (skill2!=null) {mzjc+=skill2.getSkillhurt();skill2=null;}
		GroupBuff buff=battlefield.getBuff(manData.getMan(), TypeUtil.YBYT);
		if (buff!=null) {mzjc+=buff.getValue();}
		buff=battlefield.getBuff(manData.getMan(), TypeUtil.BB_E_HYMB);
		if (buff!=null) {dsjc+=buff.getValue();}
		FightingSkill skill5=manData.getAppendSkill(9347);
		if (skill5!=null) {
			Zap=(long) (Zap*(1-skill5.getSkillhurt()/100D));
			mzjc-=skill5.getSkillhurt();
			ljjc-=skill5.getSkillhurt();
		}
		double skillhitrate=0;
		if (TypeUtil.L_CB.equals(type)) {
			skillhitrate=skill.getSkillhitrate();
			skillhitrate+=qsx/5;
		}else if (TypeUtil.L_FY.equals(type)) {
			skillhitrate=skill.getSkillhitrate();
			skillhitrate+=qsx/5;
		}
		int sw=0;//死亡人数

		for (int i = 0; i < maxG; i++) {
			if (manData.getStates()!=0) {break;}
			for (int j = 0; j < datas.size(); j++) {
				ManData data=datas.get(j);
				if (data.getStates()!=0) {continue;}
				if (i==0) {data.addBear(type);}
				else if (!isQTHD&&!Battlefield.isV(ljjc)) {
					addState = manData.xzstate("积健为雄");
					if (addState != null) {
						//加连击率
						addState = manData.xzstate("法门连击");
						if (addState!= null) {}else {manData.addAddState("法门连击",0,0,2);}
					}
					continue;}
				boolean is=(dsSkill!=null&&Battlefield.isV(dsSkill.getSkillhurt()))?false:Battlefield.isV(MixDeal.getXS(data.getQihe())+data.getsx(4, TypeUtil.SX_SBL)+dsjc-mzjc);
				if (is&&type.equals(TypeUtil.L_PL)) {
					addState=data.xzstate(TypeUtil.TY_L_PL_BLJD);
					if (addState!=null&&Battlefield.isV(addState.getStateEffect())) {is=false;}
					if (tycSkill!=null&&tycSkill.getSkillid()==9510) {
						is=false;
					}
				}
				FightingState Accepter=new FightingState();
				if (is) {//躲闪成功
					Accepter.setCamp(data.getCamp());
					Accepter.setMan(data.getMan());
					Accepter.setStartState(TypeUtil.JN);
					Accepter.setProcessState("躲闪");
				}else {
					AddState addState2=null;
					if (jgSkill!=null&&Battlefield.isV(jgSkill.getSkillhurt())) {
						addState2=data.xzstate("免疫物理");
						if (addState2!=null) {
							data.getAddStates().remove(addState2);
						}
					}
					long ap=PhyAttack.Hurt(Zap,isQTHD?1:i+1,manData,data,TypeUtil.JN,Accepter,Accepterlist,battlefield,0,0);
					hurt(manData, data, i, skillhitrate, ap, skill, skills, Accepter, Accepterlist, battlefield);
					if (addState2!=null) {
						data.getAddStates().add(addState2);
					}
					if (data.getStates()==1) {
						sw++;
						if (tycSkill!=null&&tycSkill.getSkillid()==9511) {
							int add=(int) (data.huoAp()*tycSkill.getSkillhurt()/100D);
							if (add>2000) {add=2000;}
							tycSkill.setSkillgain(tycSkill.getSkillgain()+add);
							if (tycSkill.getSkillgain()<60000) {
								manData.setAp(manData.huoAp()+add);
							}
						}
						if (skills!=null) {
							for (int k = skills.size()-1; k>=0; k--) {
								FightingSkill tSkill=skills.get(k);
								int id=tSkill.getSkillid();
								if (id==9506) {
									if (Battlefield.isV(tSkill.getSkillhurt())) {
										addState=data.xzstate(TypeUtil.KX);
										if (addState!=null) {
											data.getAddStates().remove(addState);
											Accepter.setEndState_2(addState.getStatename());
										}
									}
								}else if (id==9507) {
									data.addAddState(type, tSkill.getSkillhurt(), 0, 2);
								}else if (id==9508) {
									if (Battlefield.isV(40)) {
										double add=data.huoAp()*tSkill.getSkillhurt()/100D;
										if (add>15000) {add=15000;}
										data.addAddState(type,add, 0, 2);
									}
								}
							}
						}
					}
					if (skillTwo!=null&&Battlefield.isV(skillTwo.getSkillhurt())) {
						FightingState Accepter2=new FightingState();
						ChangeFighting fighting=new ChangeFighting();
						fighting.setChangehp((int)-ap);
						FightingPackage.ChangeProcess(fighting,manData,data,Accepter2,TypeUtil.PTGJ,Accepterlist,battlefield);
					}
				}
				if (i==0) {Accepter.setSkillskin(skill.getSkillid()+"");}
			}
		}
		if (tycSkill!=null&&tycSkill.getSkillid()==9510&&sw>=3) {
			manData.addnq((int)(sw*tycSkill.getSkillhurt()), Originator);
		}
		if (events.getOriginator()!=null) {Accepterlist.add(Originator);}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);
		}
		if (manData.getStates()!=0) {//先判断是否能复活
			MixDeal.DeathSkill(manData,Originator,battlefield);
		}
	}
	/**伤害处理*/
	public static void hurt(ManData manData,ManData data,int i,double skillhitrate,long hurt,FightingSkill skill,List<FightingSkill> skills,FightingState Accepter,List<FightingState> Accepterlist,Battlefield battlefield){
		boolean isFZ=false;
		ChangeFighting acec=new ChangeFighting();
		acec.setChangehp((int)-hurt);
		if (i==0&&(TypeUtil.L_CB.equals(skill.getSkilltype())||TypeUtil.L_FY.equals(skill.getSkilltype()))) {
			acec.setChangevlaue(skillhitrate);
			acec.setChangetype(skill.getSkilltype());
			acec.setChangesum(skill.getSkillcontinued());
		}
		String typefs = TypeUtil.PTGJ;
		if (skills!=null) {
			for (int j = skills.size()-1; j>=0; j--) {
				FightingSkill tSkill=skills.get(j);
				int id=tSkill.getSkillid();
				if (id==9503) {
					if (data.getType()==1) {//		9503	幽谷传声	对召唤兽是有霹雳是伤害增加{公式一}%。（仅在与玩家之间战斗有效）
						hurt=(long) (hurt*(1+tSkill.getSkillhurt()/100D));
						acec.setChangehp((int)-hurt);
					}
				}else if (id==9504) {
					isFZ=Battlefield.isV(tSkill.getSkillhurt());
				}else if (id==9702||id==9802) {
					if (Battlefield.isV(tSkill.getSkillhurt())) {
						acec.setChangesum(skill.getSkillcontinued()+1);
					}
				}else if (id==9704||id==9804) {
					if (Battlefield.isV(tSkill.getSkillhurt())) {
						acec.setSkill(tSkill);
					}
				}else if (id==9706||id==9806) {
					AddState addState=data.xzstate(skill.getSkilltype());
					if (addState!=null) {
						hurt=(long) (hurt*(1+tSkill.getSkillhurt()/100D));
						acec.setChangehp((int)-hurt);
					}
				}else if (id==9707) {
					if (data.getType()==1&&Battlefield.isV(10)) {
						hurt=(long) (hurt*(1+tSkill.getSkillhurt()/100D));
						acec.setChangehp((int)-hurt);
					}
				}else if (id==9709) {
					if (data.getvalue(0)<=0.2) {
						hurt=(long) (hurt*(1+tSkill.getSkillhurt()/100D));
						acec.setChangehp((int)-hurt);
					}
				}else if (id==9710) {
					acec.setChangevlaue2(tSkill.getSkillhurt());
				}else if (id==9711) {//9711	气贯碧霄	释放一个强力凌虚御风，将目标气血上限降低至{公式九}点，持续3回合。
					double xs=1-(tSkill.getSkillhurt()/data.getZHP_Z());
					if (xs<0||xs>1) {xs=0;}
					acec.setChangevlaue(xs);
				}else if (id==9807||id==9809||id==9811||id==9812||id==9712) {
					acec.setSkill(tSkill);
				}else if (id==22033) { //法门 行气如虹 判断
					int gailv = manData.getFmsld() /500 ;
					AddState addState = manData.xzstate("行气如虹");
					if (addState != null) {
						gailv = gailv * 2;
					}
					if (Battlefield.isV(gailv)) {
						typefs = "至圣";
						if (Battlefield.isV(50)) {
							long beilv =manData.getFmsld()/334;
							hurt= hurt+((hurt*beilv)/100);
							acec.setChangehp((int)-hurt);
						}
					}
				}

//				9712	西风残照	扶摇状态下的目标受药品和师门的气血回复效果降低{公式一}%。
//				9807	雾锁横江	沧波状态下的召唤兽离场时，如果其主人身上没有沧波状态，则有{公式一}%几率将沧波状态留在主人身上。（仅在与玩家之间战斗有效）

			}
		}
		FightingPackage.ChangeProcess(acec,isFZ?null:manData,data,Accepter,TypeUtil.PTGJ,Accepterlist,battlefield);

	}
}
