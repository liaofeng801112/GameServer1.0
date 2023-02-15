package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.Calculation;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class ControlAction implements SpellAction{
	@Override
	public void spellAction(ManData manData,FightingSkill skill,FightingEvents events,Battlefield battlefield) {
		// TODO Auto-generated method stub		

		//需要记录命中的数
		int size=0;
		FightingSkill skill1=null;//返蓝
		FightingSkill skill2=null;//4法 多一次
		FightingSkill skill3=null;//备用
		List<FightingSkill> skills=getSkills(manData,skill,battlefield.BattleType);
		if (skill.getSkillid()==1005) {
		    skill1=manData.getSkillType(TypeUtil.TY_HL_HKDL);
		    if (skill1!=null&&!Battlefield.isV(skill1.getSkillhurt())) {skill1=null;}
		}else if (skill.getSkillid()==1010) {
			skill1=manData.getSkillType(TypeUtil.TY_FY_HMZH);
			if (skill1!=null&&!Battlefield.isV(skill1.getSkillhurt())) {skill1=null;}
		}else if (skill.getSkillid()==1015) {
			skill1=manData.getSkillType(TypeUtil.TY_HS_HCTY);
			if (skill1!=null&&!Battlefield.isV(skill1.getSkillhurt())) {skill1=null;}
		}else if (skill.getSkillid()==1009) {
			skill2=manData.getSkillType(TypeUtil.TY_FY_XXYJ);
			skill3=manData.getSkillType(TypeUtil.TY_FY_CJCH);
		}else if (skill.getSkillid()==1004) {
			skill2=manData.getSkillType(TypeUtil.TY_HL_CYMY);
		}else if (skill.getSkilltype().equals(TypeUtil.ZD)) {
//			愁兰泣露|召唤兽受到毒伤害时，其主人有20%的几率受到等于召唤兽所受伤害（20%*等级）的伤害。（仅在与玩家之间战斗有效）
			if (PK_MixDeal.isPK(battlefield.BattleType)) {
				skill2=manData.getSkillType(TypeUtil.TY_ZD_CLQL);			
			}
		}
		
		String type=skill.getSkilltype();
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield);
		if (datas.size()==0){
			FightingState Originator=events.getOriginator();
			if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skill.getSkillname());
			events.setOriginator(null);
			Accepterlist.add(Originator);
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
			return;		
		}	
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		int state=manData.getStates();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		if (state==0&&state!=manData.getStates()) {state=1;}
		else {state=0;}
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState Accepter=MixDeal.DSMY(manData,data,skill.getSkillid(),battlefield);
			if (Accepter!=null) {//法术闪避
				Accepterlist.add(Accepter);
			}else {
				data.addBear(type);
				Accepter=new FightingState();
				ChangeFighting change=TypeProbabilityNoHurt(manData, data, skill, skills,battlefield);
				if (!change.getChangetype().equals("")) {
					size++;
					if (data.getType()==0) {
						if (skill2!=null&&skill2.getSkilltype().equals(TypeUtil.TY_FY_XXYJ)&&Battlefield.isV(skill2.getSkillhurt())) {
							AddState addState=data.getGainState();
							if (addState!=null) {
								addState.setSurplus(addState.getSurplus()-1);
							}
						}else if (skill2!=null&&skill2.getSkilltype().equals(TypeUtil.TY_HL_CYMY)&&Battlefield.isV(skill2.getSkillhurt())) {
							ManData pet=battlefield.getSeek(data,1);
							if (pet!=null&&pet.getStates()==0&&pet.xzstate(TypeUtil.FY)==null) {
								FightingState fightingState=MixDeal.DSMY(manData,pet,skill.getSkillid(),battlefield);
								if (fightingState!=null) {//法术闪避
									Accepterlist.add(fightingState);
								}else {
									pet.addBear(type);
									fightingState=new FightingState();
									ChangeFighting changeFighting=TypeProbabilityNoHurt(manData, pet, skill, skills,battlefield);
									FightingPackage.ChangeProcess(changeFighting, null, pet, fightingState,type, Accepterlist, battlefield);					
								}
								fightingState.setSkillskin(skill.getSkillid()+"");	
							}
						}else if (skill2!=null&&skill2.getSkilltype().equals(TypeUtil.TY_ZD_CLQL)&&Battlefield.isV(20)) {
							ManData pet=battlefield.getSeek(data,1);
							if (pet!=null&&pet.getStates()==0) {
								FightingState fightingState=new FightingState();
								ChangeFighting changeFighting=new ChangeFighting();
								changeFighting.setChangehp((int)(change.getChangehp()*skill2.getSkillhurt()/100.0));
								FightingPackage.ChangeProcess(changeFighting, null, pet, fightingState,"施法毒", Accepterlist, battlefield);					
							}
						}
						if (skill3!=null&&Battlefield.isV(skill2.getSkillhurt())) {
							ManData pet=battlefield.getSeek(data,1);
							if (pet!=null) {
								AddState addState=pet.getGainState();
								if (addState!=null) {
									FightingState fightingState=new FightingState();
									fightingState.setCamp(pet.getCamp());
						            fightingState.setMan(pet.getMan());
						            fightingState.setStartState(TypeUtil.JN);
									fightingState.setEndState_2(addState.getStatename());
									pet.getAddStates().remove(addState);	
									Accepterlist.add(fightingState);
								}		
							}
						}
					}
				}
				FightingPackage.ChangeProcess(change,manData,data,Accepter,type.equals(TypeUtil.ZD)?"施法毒":type, Accepterlist, battlefield);		
			}
			Accepter.setSkillskin(skill.getSkillid()+"");	
		}
		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
		}

//		9142|花开蒂落|使用失心狂乱命中超过3个目标时有（4%*等级）的几率返还消耗法力的（10%*等级）。
//		9102|寒梅著花|四面楚歌命中超过4个目标时有（4%*等级）的几率返还消耗法力的（10%*等级）。
//		9123|画船听雨|使用5睡命中超过5个目标时有（4%*等级）几率返还消耗法力的（10%*等级）
		if (skill1!=null) {
			int mpc=0;
			if (size>=3&&skill1.getSkillid()==9142) {
				mpc=(int) (skill.getSkillblue()*skill1.getSkillhurt()*2.5/100);
			}else if (size>=4&&skill1.getSkillid()==9102) {
				mpc=(int) (skill.getSkillblue()*skill1.getSkillhurt()*2.5/100);
			}else if (size>=5&&skill1.getSkillid()==9123) {
				mpc=(int) (skill.getSkillblue()*skill1.getSkillhurt()*2.5/100);
			}
			if (mpc!=0) {
				FightingEvents events2=new FightingEvents();
				List<FightingState> fightingStates=new ArrayList<>();
				FightingState fightingState=new FightingState();
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangemp(mpc);
				manData.ChangeData(changeFighting, fightingState);
				fightingState.setStartState("药");
				fightingStates.add(fightingState);
				events2.setAccepterlist(fightingStates);
				battlefield.NewEvents.add(events2);	
			}
		}
		
		if (state==1) {//先判断是否能复活
			MixDeal.DeathSkill(manData,Originator,battlefield);
		}
	}
	/**获取霹雳作用的技能*/
	public static List<FightingSkill> getL_PL_Skills(ManData myData,FightingSkill skill,int type){
		List<FightingSkill> skills=null;
		if (PK_MixDeal.isPK(type)) {//玩家对战
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_PL_YGCS), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_PL_CYPW), skills);
		}else {
			
		}
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_PL_SHZX), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_PL_BLJD), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_PL_QSYZ), skills);
		return skills;
	}
	/**获取甘霖作用的技能*/
	public static List<FightingSkill> getL_GL_Skills(ManData myData,FightingSkill skill,int type){
		List<FightingSkill> skills=null;
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_CYSH), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_XLYS), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_SYYL), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_MYPT), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_CYJY), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_GL_SNLZ), skills);
		return skills;
	}
	/**获取沧波作用的技能*/
	public static List<FightingSkill> getL_CB_Skills(ManData myData,FightingSkill skill,int type){
		List<FightingSkill> skills=null;
		if (PK_MixDeal.isPK(type)) {//玩家对战
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_CB_WSHJ), skills);
			if (skill.getSkillid()==1090) {
			    skills=addSkill(myData.getSkillType(TypeUtil.TY_L_CB_JTDL), skills);
			}
		}
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_CB_LXFZ), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_CB_BSQT), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_CB_YCBH), skills);
		return skills;
	}
	/**获取扶摇作用的技能*/
	public static List<FightingSkill> getL_FY_Skills(ManData myData,FightingSkill skill,int type){
		List<FightingSkill> skills=null;
		if (PK_MixDeal.isPK(type)) {//玩家对战
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_LGCQ), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_XDSL), skills);
		}
		if (skill.getSkillid()==1099) {
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_FDYB), skills);
		}
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_XFCZ), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_XFCZ), skills);
		skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FY_YYZF), skills);
		return skills;
	}
	/**获取作用的技能*/
	public static List<FightingSkill> getSkills(ManData myData,FightingSkill skill,int type){
		List<FightingSkill> skills=null;
		if (skill.getSkilltype().equals(TypeUtil.FY)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_CHLQ), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_FDSJ), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_DSCB), skills);
			}else {//npc对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_BFCY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_HQQY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_FY_JYST), skills);
			}
		}else if (skill.getSkilltype().equals(TypeUtil.HS)||skill.getSkillid()==9126) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_CJKZ), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_YZCW), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_CMBX), skills);
			}else {//npc对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_MYWS), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_CYWY), skills);
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_HLDJ), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_HS_LBCX), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.HL)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_XLRM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_XLRM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_DJDX), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_XSCL), skills);
			}else {//npc对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_HSSL), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_QJSY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_LXXH), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_HL_YSCL), skills);
			}
		}else if (skill.getSkilltype().equals(TypeUtil.ZD)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
			    skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_YASH), skills);
			    skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_LJXF), skills);
			    skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_RLBB), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_MLYY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_FYSQ), skills);
			}else {//npc对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_LRHS), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_BYHY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_ZD_FXZH), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TZ_SJDG), skills);
			}
		}else if (skill.getSkilltype().equals(TypeUtil.L)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_SNZM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_ALYD), skills);
			}else {

			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_FGLY), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_L_LHHL), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.S)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_SNZM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_S_MYBY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_S_CLZY), skills);
			}else {
				
			}	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_S_LXDD), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.F)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_SNZM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_F_CZDF), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_F_HKJF), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_F_YQDS), skills);
			}else {
				
			}	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_F_SXPC), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.H)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_SNZM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_H_DFCY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_H_HLDX), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_H_HSJY), skills);
				if (skill.getSkillid()==1060) {
					skills=addSkill(myData.getSkillType(TypeUtil.TY_H_XHXC), skills);	
				}
			}else {
				
			}	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_H_JYYX), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.GH)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_YHSX), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_QYBY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_GYCM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_XGNC), skills);
			}else {
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_GSSC), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_YXHY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_BQCH), skills);
			}		
			skills=addSkill(myData.getSkillType(TypeUtil.TY_GH_YJFR), skills);		
		}else if (skill.getSkilltype().equals(TypeUtil.YW)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_ZZJM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_HQMY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_HLYM), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_BSSX), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_MDHL), skills);
			}else {//npc对战
				
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TZ_WHSY), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_TRWJ), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_QQWK), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_YZJH), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_YW_CLJW), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.LL)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_TT), skills);
			}else {
				skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_XCPY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_CY)  , skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_DLTS), skills);
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_ZM), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_LL_YYDJ), skills);
		}else if (skill.getSkilltype().equals(TypeUtil.JS)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_JFJC), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_JCYC), skills);
			}else {
				
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_FYZS), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_PLJH), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_YYCF), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_JS_DGCL), skills);			
		}else if (skill.getSkilltype().equals(TypeUtil.KX)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				
			}else {
				skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_XTLH), skills);	
				skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_TJQF),skills);	
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_FHST), skills);		
			skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_BFWY), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_WYCM), skills);
			skills=addSkill(myData.getSkillType(TypeUtil.TY_KX_XWYZ), skills);	
		}else if (skill.getSkilltype().equals(TypeUtil.MH)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_ZZGX), skills);	
				skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_TYMZ), skills);	
				skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_HCDN), skills);	
				skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_STYY), skills);	
			}
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_QYMM), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_HTJC), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_LXXY), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_NYYX), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_ZMBW), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_NRYZ), skills);	
			skills=addSkill(myData.getSkillType(TypeUtil.TY_MH_RYTH), skills);	
		}else if (skill.getSkilltype().equals(TypeUtil.TY_L_XSNJ)) {
			if (PK_MixDeal.isPK(type)) {//玩家对战
				
			}else {
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_DZXY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_L_XYQK), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_S_AZLS), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_S_XYHT), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_F_FDZL), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_F_WHSF), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_H_JSYY), skills);
				skills=addSkill(myData.getSkillType(TypeUtil.TY_H_YHRJ), skills);
			}
		}
		return skills;
	}
    /***/
	public static  List<FightingSkill> addSkill(FightingSkill skill,List<FightingSkill> skills){
		if (skill==null) {return skills;}
		if (skills==null) {skills=new ArrayList<>();}
		skills.add(skill);
		return skills;
	}
	/**处理无伤害概率类型的事件*/
	public ChangeFighting TypeProbabilityNoHurt(ManData myData,ManData data,FightingSkill skill,List<FightingSkill> skills,Battlefield battlefield){
		String type=skill.getSkilltype();
		ChangeFighting changeFighting=new ChangeFighting();
		if (Calculation.getCalculation().renfa(myData, data, skill.getSkillhitrate(), type)) {
			changeFighting.setChangetype(type);
			changeFighting.setChangesum(skill.getSkillcontinued());
			changeFighting.setChangevlaue(skill.getSkillhurt());	
			//9101|滴水成冰|封印命中召唤兽后，有（20%*等级）几率增加一回合。（仅在与玩家之间战斗有效）
			//9141|心乱如麻|混乱法术命中召唤兽后，有（20%*等级）几率增加1回合。（仅在与玩家之间战斗有效。）
			//9121|画龙点睛|对昏睡的目标再次使用昏睡时，有（10%*等级）几率增加一回合
		    //9164|如履薄冰|目标如果血量低于50%，则受到毒伤害时速度降低(1%*等级），持续2回合。（仅在与玩家之间战斗有效）
			//9167|茅庐夜雨|受到如履薄冰影响的目标，再次受到毒伤害时气血上限降低（1%*等级），持续2回合。（仅在与玩家之间战斗有效）
			//9343|纸醉金迷|被遗忘的召唤兽有（3%*等级）几率不能使用保护。(仅在与玩家之间战斗有效。)
			//9351|梦断魂劳|被遗忘的人物摆脱遗忘后,有20%率进虚弱状态,虚弱状态下的单位受到的伤害增加（10%+2%*等级）,持续2回合。(仅在与玩家之间战斗有效
			//9149|龙血玄黄|对被混乱的目标释放混乱命中时，造成目标血量上限（1%等级）的伤害。（仅在与NPC之间战斗有效。）
			//9341|陶然忘机|使用失心疯时,对法术的遗忘成功率增加（2%*等级）	
			//9346|魂牵梦萦|遗忘命中人物单位时,遗忘对法宝技能也生效,法宝技能遗忘的成功率为（4%*等级）。(仅在与玩家之间战斗有效。
			//9349|百兽失心|遗忘命中召唤兽时,遗忘对召唤兽技能也生效,召唤兽技能遗忘的成功率为（4%*等级）。(仅在与家之间战斗有效)
			//不概率|6034|9124|9104|9106|9107|9109|9122|9144|9146|9147|9148|9149|9152|9341|9344|9345|9346|9347|9348|9349
			//概率|9112|9125|9127|9128|9131|9150
			if (skills!=null) {
				for (int i = 0; i < skills.size(); i++) {
					FightingSkill fightingSkill=skills.get(i);
					int id=fightingSkill.getSkillid();
					if (id==6034) {
						changeFighting.setChangevlaue2(changeFighting.getChangevlaue2()+fightingSkill.getSkillhurt());
					}else if (id==9341) {
						if (skill.getSkillid()==1074) {
							changeFighting.setChangevlaue2(changeFighting.getChangevlaue2()+fightingSkill.getSkillhurt());
						}
					}else if (id==9101||id==9141) {
						if (Battlefield.isV(fightingSkill.getSkillhurt())) {
							changeFighting.setChangesum(changeFighting.getChangesum()+1);
						}
					}else if (id==9121) {
						if (data.xzstate(TypeUtil.HS)!=null) {
							changeFighting.setChangesum(changeFighting.getChangesum()+1);
						}
					}else if (id==9149) {
						if (data.xzstate(TypeUtil.HL)!=null) {
							changeFighting.setChangehp(-(int)(data.getHp_z()*fightingSkill.getSkillhurt()/100.0));
						}
					}else if (id==9343) {
						if (data.getType()==1&&Battlefield.isV(fightingSkill.getSkillhurt())) {
							changeFighting.setSkill(fightingSkill);
						}
					}else if (id==9351) {
						if (data.getType()==0&&Battlefield.isV(20)) {
							changeFighting.setSkill(fightingSkill);
						}
					}else if (id==9162) {
						changeFighting.setChangesum((int)(changeFighting.getChangesum()-fightingSkill.getSkillhurt()));
					}else if (id==9112||id==9125||id==9127||id==9128||id==9131||id==9150) {
						if (Battlefield.isV(fightingSkill.getSkillhurt())) {
							changeFighting.setSkill(fightingSkill);
						}
					}else if (id!=6037&&!(id>=9161&&id<=9171)){
						changeFighting.setSkill(fightingSkill);
					}
				}
			}
		}
		if (type.equals(TypeUtil.ZD)) {
			double upxs=1;
			double hurtxs=1;
			double qds=0;
			if (skills!=null) {
				for (int i = 0; i < skills.size(); i++) {
					FightingSkill fightingSkill=skills.get(i);
					int id=fightingSkill.getSkillid();
					if (id==6037) {
						upxs+=fightingSkill.getSkillhurt()/100;
						changeFighting.setChangesum(1);
					}else if (id==9161) {
						qds+=fightingSkill.getSkillhurt();
					}else if (id==9162||id==9163) {
						qds+=fightingSkill.getSkillhurt();
					}else if (id==9164) {
						data.addAddState(fightingSkill.getSkilltype(),fightingSkill.getSkillhurt(),0,battlefield.CurrentRound+1);
					}else if (id==9165) {
						if (data.getType()==1) {
							qds+=fightingSkill.getSkillhurt();
						}
					}else if (id==9166) {
						qds+=fightingSkill.getSkillhurt();
						hurtxs-=fightingSkill.getSkillhurt()/100;
					}else if (id==9167) {
		                if (data.xzstate(TypeUtil.TY_ZD_RLBB)!=null) {
//		                	 9167|茅庐夜雨|受到如履薄冰影响的目标，再次受到毒伤害时气血上限降低（1%*等级），持续2回合。（仅在与玩家之间战斗有效）
						}
					}
				}
			}
//			  6037|伤筋动骨|释放毒法时提高毒伤上限，降低毒发持续回合至一回合。(仅在与NPC之间战斗有效)
//			  9161|因爱生恨|对异性目标毒伤害加强（1%*等级）。（仅在与玩家之间战斗有效）
//			  9162|烈日寒霜|减少毒的回合数（1*等级），获得加强毒伤害（2%*等级）。（仅在与NPC之间战斗有效）
//			  9163|碧烟横影|减少多体毒目标2个，获得加强毒伤害（15%*等级*5%）（仅在与NPC之间战斗有效）
//			  9164|如履薄冰|目标如果血量低于50%，则受到毒伤害时速度降低(1%*等级），持续2回合。（仅在与玩家之间战斗有效）
//			  9165|帘卷西风|对召唤兽毒伤害加强（2%*等级）。（仅在与玩家之间战斗有效）
//			  9166|焚心枕火|减少毒发伤害（4%*等级），获得加强毒伤害（4%*等级）。（仅在与NPC之间战斗有效）
//			  9167|茅庐夜雨|受到如履薄冰影响的目标，再次受到毒伤害时气血上限降低（1%*等级），持续2回合。（仅在与玩家之间战斗有效）
//			  9168|愁兰泣露|召唤兽受到毒伤害时，其主人有20%的几率受到等于召唤兽所受伤害（20%*等级）的伤害。（仅在与玩家之间战斗有效）
//			  9169|铺天盖地|释放万毒攻心，增加毒法伤害（10%*等级），持续两回合。
//			  9170|哀鸿遍野|释放一个强力万毒攻心，使中毒的目标身上累计（5000*等级）点伤害，之后自己使用毒法每命中目标一次累计伤害翻一倍，目标在之后第3回合开始前受到累计伤害的毒发伤害。（仅在与玩家之间战斗有效）
//			  9171|烽烟四起|释放万毒攻心，毒的目标数为（5+等级）。（仅在与玩家之间战斗有效）  
			AddState addState=myData.xzstate("哀鸿遍野");
			if (addState!=null) {
				data.addAddState(TypeUtil.TY_ZD_AHBY, addState.getStateEffect(), myData.getId(), 3);
			}else {
				addState=data.xzstate(TypeUtil.TY_ZD_AHBY);
				if (addState!=null&&myData.getId()==addState.getStateEffect2()) {
					addState.setStateEffect(addState.getStateEffect()*3);
				}
			}
//	    	9169|铺天盖地|释放万毒攻心，增加毒法伤害（10%*等级），持续两回合。
	    	AddState addState1=myData.xzstate(TypeUtil.TY_ZD_PTGD);
	    	if (addState1!=null) {qds+=addState1.getStateEffect();}
			int up=Calculation.getCalculation().getzdup(myData, skill,qds,data);
			int hurt=Calculation.getCalculation().getzdsh(myData, data, skill,qds);
			up*=upxs;hurt*=hurtxs;
			if (hurt>up) {
			    hurt=up;	
		    }
			hurt-=data.getQuality().getKzds();
			if (hurt<=0) {
			    hurt=1;
			}
			changeFighting.setChangehp(-hurt);	
			FightingSkill fightingSkill=myData.getSkillType(TypeUtil.TJ_MTL);
			if (fightingSkill!=null) {
			    changeFighting.setChangemp((int)(changeFighting.getChangehp()*0.15));
			}
			changeFighting.setChangevlaue(hurt/2);

			FightingSkill fightingSkillS =myData.getSkillType(TypeUtil.TJ_FSSS);
			if (fightingSkillS!=null) {
				changeFighting.setChangemp((int)(changeFighting.getChangehp()*0.2));
			}
			changeFighting.setChangevlaue(hurt/2);
		}
		return changeFighting;
	}
}
