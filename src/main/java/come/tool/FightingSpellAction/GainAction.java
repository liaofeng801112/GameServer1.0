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
import come.tool.FightingData.TypeUtil;

public class GainAction implements SpellAction{

//	9223|意犹未尽|使用乾坤借速时，降低施法耗蓝（100*等级+100）。

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
	
		List<FightingSkill> skills=ControlAction.getSkills(manData,skill,battlefield.BattleType);
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
        double qian=0D;
        if (skill.getSkillid()==1029) {
        	AddState addState=manData.xzstate(TypeUtil.TY_LL_QXGR);
        	if (addState!=null) {qian+=addState.getStateEffect();}
        }else if (skill.getSkillid()==1030) {
			AddState addState=manData.xzstate(TypeUtil.TY_LL_BS);
			if (addState!=null) {skills=ControlAction.addSkill(manData.skillId(9207), skills);}
		}else if (skill.getSkillid()==1039) {
			AddState addState=manData.xzstate(TypeUtil.TY_JS_YWDJ);
			if (addState!=null) {skills=ControlAction.addSkill(manData.skillId(9231), skills);}
		}else if (skill.getSkillid()==1040) {
			AddState addState=manData.xzstate(TypeUtil.TY_JS_KHCH);
			if (addState!=null) {skills=ControlAction.addSkill(manData.skillId(9232), skills);}
		}else if (skill.getSkillid()==1034) {
			AddState addState=manData.xzstate(TypeUtil.TY_KX_YZYT);
			if (addState!=null) {
				qian+=addState.getStateEffect();
			    skills=ControlAction.addSkill(manData.skillId(9250), skills);
			}
		}else if (skill.getSkillid()==1035) {
			AddState addState=manData.xzstate(TypeUtil.TY_KX_HYCF);
			if (addState!=null) {qian+=addState.getStateEffect();}
		}
        double xs=Calculation.getCalculation().mofa(skill.getSkillgain(),manData,type,qian);
		
        FightingSkill skill1=null;//9241|金茧缠身
        FightingSkill skill2=null;//9244|卧薪尝胆
        FightingSkill skill3=null;//9247|云开月明
        if (skill.getSkillid()==1034) {
        	skill1=manData.skillId(9241);
        	if (skill1!=null) {
        		skill2=manData.skillId(9244);
        		skill3=manData.skillId(9247);
			}
        }
        FightingSkill bb_e_qhjs=null;
        if (skill.getSkilltype().equals(TypeUtil.JS)) {
        	bb_e_qhjs=manData.getSkillType(TypeUtil.BB_E_QHJS);
		}
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		if (bb_e_qhjs!=null) {Originator.setText("强化加速#2");}
		int state=manData.getStates();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		if (state==0&&state!=manData.getStates()) {state=1;}
		else {state=0;}
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			data.addBear(type);
			FightingState Accepter=new FightingState();
			ChangeFighting change=TypeGain(manData,data, type, skill,xs,skills, battlefield);
			
			if (bb_e_qhjs!=null) {change.setChangevlaue(change.getChangevlaue()+bb_e_qhjs.getSkillgain());}
			
			FightingPackage.ChangeProcess(change,manData,data,Accepter,type,Accepterlist,battlefield);	
			Accepter.setSkillskin(skill.getSkillid()+"");
			if (skill1!=null) {
				if (Battlefield.isV(skill1.getSkillhurt())) {
					AddState addState=new AddState(skill1.getSkilltype(),skill1.getSkillhurt()/6*4000,skill1.getSkillhurt()/6*4000,3);
					if (skill2!=null) {addState.setSkill(skill2);}
					if (skill3!=null) {addState.setSkill(skill3);}
					data.getAddStates().add(addState);	
				}	
			}
		}
		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
		}
		if (state==1) {//先判断是否能复活
			MixDeal.DeathSkill(manData,Originator,battlefield);
		}
	}
	/**处理增益类型的事件*/
	public ChangeFighting TypeGain(ManData mydata,ManData data,String type,FightingSkill skill,double xs,List<FightingSkill> skills,Battlefield battlefield){
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype(type);
		changeFighting.setChangesum(skill.getSkillcontinued());
		if (skills!=null) {
			for (int i=skills.size()-1;i>=0;i--) {
				FightingSkill skill2=skills.get(i);
				int id=skill2.getSkillid();
				//不概率9201|9203|9205|9206|9224|9226|9229|9242|9246|9249|9404|9405|9406|9408|9409|9410|9411
				//概率    9202|9241
				if (id==9202) {
					if (Battlefield.isV(skill2.getSkillhurt())) {
						changeFighting.setChangesum(changeFighting.getChangesum()+1);
					}
				}else if (id==9243) {
					if (Battlefield.isV(skill2.getSkillhurt())&&data.xzstate(TypeUtil.KX)!=null) {
						changeFighting.setChangesum(changeFighting.getChangesum()+1);
					}
				}else if (id==9403) {
					if (Battlefield.isV(skill2.getSkillhurt())&&data.xzstate(TypeUtil.MH)!=null) {
						changeFighting.setChangesum(changeFighting.getChangesum()+1);
					}
				}else if (id==9228) {
					if (data.getType()==0&&Battlefield.isV(skill2.getSkillhurt())) {
						ManData data2=battlefield.getSeek(data,3);
						if (data2!=null) {
							data2.addAddState(skill2.getSkilltype(), 10, 0, 3);
						}
					}
				}else if (id==9204||id==9242||id==9402) {
					if (data.getType()==1) {
						xs+=skill2.getSkillhurt();
					}
				}else if (id==9222) {
					xs+=skill2.getSkillhurt();
				}else if (id==9225) {
					if (data.xzstate(TypeUtil.JS)!=null) {
						xs+=skill2.getSkillhurt();
					}
				}else if (id==9248) {
					AddState addState=data.xzstate(TypeUtil.FY);
					if (addState!=null) {addState.isEnd();}
					addState=data.xzstate(TypeUtil.HL);
					if (addState!=null) {addState.isEnd();}
					addState=data.xzstate(TypeUtil.HS);
					if (addState!=null) {addState.isEnd();}
					addState=data.xzstate(TypeUtil.YW);
					if (addState!=null) {addState.isEnd();}
				}else if (id==9401) {
					xs+=skill2.getSkillhurt();
				}else if (id==9407) {
					if (Battlefield.isV(skill2.getSkillhurt())) {
						changeFighting.setChangetype2("非控制减益");
					}
				}else if (id==22019) {
					if (skill.getSkillid() >= 1026&&skill.getSkillid()<=1030) { //牛
						data.addAddState("法门破物理", mydata.getFmsld(), 0, changeFighting.getChangesum());
					}else if(skill.getSkillid() >= 1031&&skill.getSkillid()<=1035) {//盘
						data.addAddState("法门减伤害", mydata.getFmsld(), 0, changeFighting.getChangesum());
					}else if(skill.getSkillid() >= 1036&&skill.getSkillid()<=1040) {//速
						data.addAddState("法门加闪避", mydata.getFmsld(), 0, changeFighting.getChangesum());
					}

				}
				else {
					changeFighting.setSkill(skill2);
				}
			}
		}
//		9406|执迷不悟|已经魅惑的目标,受到鬼火和三尸虫攻击时额外获得（3*等级）怨气
		
//		9245|腾蛟起凤|被盘的目标有（20%*等级）几率获得气血上限增加5%的效果。（仅在与NPC之间战斗有效）

	
		
		changeFighting.setChangevlaue(xs);	
	    if (type.equals(TypeUtil.KX)) {
	    	changeFighting.setChangevlaue2(xs/3);
		}else if (type.equals(TypeUtil.MH)) {
			changeFighting.setChangevlaue2(xs/2);
		}	
		return changeFighting;
	}	
}
