package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class L_GLAction implements SpellAction{

//	9601	玉露清霜	使用沛然莫御时增加{公式一}%甘霖回血程度。--
//	9602	春盈四海	回合结束时为己方非死亡单位回复气血时有10%几率为其回复{公式一}点法力。--
//	9603	星离雨散	使用甘霖法术时有20%几率为目标增加{公式一}%点躲闪率。--
//	9604	雨霾风障	使用沛然莫御后，目标在回合结束阶段触发复活效果时有20%几率获得一个等于回血量{公式一}%的护盾，持续3回合。--
//	9605	山雨欲来	回合结束后复活己方单位时有{公式一}%的几率为其增加10点怨气。--
//	9606	密语滂沱	触发甘霖回血效果后，如果目标的当前血量百分比低于30%，则有{公式一}%几率触发双重回血效果。--
//	9607	雨落菩提	使用沛然莫御后，目标在回合结束阶段触发复活效果时有{公式一}%几率清除身上的一个被控制状态。--
//	9608	残云聚雨	回合结束后复活己方单位时有{公式一}%的几率不清除甘霖状态。--
//	9609	霜浓露重	逆鳞状态下使用甘霖复活己方单位时，回复效果增加{公式一}%。--
//	9610	沐雨经霜	对一个己方人物单位使用沛然莫御，同时为其回复{公式一}点怒气并立即刷新其处于冷却中的天演策技能，冷却10回合。（仅在与玩家之间战斗有效）
//	9611	沛雨甘泉	施放一个泽被万物，同时提升目标{公式一}%气血上限，持续3回合。
//	9612	震风陵雨	释放一个强力泽被万物，目标数增加{公式三十七}个，此状态下的目标在回合开始前如果周围距离1以内有死亡目标，则有{公式一}%几率将自身的甘霖状态转移给死亡目标。

//	1094	沛然莫御
	@Override
	public void spellAction(ManData manData,FightingSkill skill,FightingEvents events,Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill tycSkill=null;
		if (skill.getSkillid()==9610||skill.getSkillid()==9611||skill.getSkillid()==9612) {
			tycSkill=skill;
			skill=manData.skillId(MixDeal.getTYSkillId(tycSkill.getSkillid()));
			if (skill==null) {return;}
		}
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
		List<FightingSkill> skills=ControlAction.getL_GL_Skills(manData,skill,battlefield.BattleType);
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();
		double glv=manData.getsx(2,TypeUtil.L_GL);
		double glc=manData.getsx(2,"甘霖回血");
		if (skill.getSkillid()==1094) {
			FightingSkill skill2=manData.skillId(9601);
			if (skill2!=null) {
				glc+=skill2.getSkillhurt();
			}
			skills=ControlAction.addSkill(manData.getSkillType(TypeUtil.TY_L_GL_YMFZ), skills);
			skills=ControlAction.addSkill(manData.getSkillType(TypeUtil.TY_L_GL_YLPT), skills);
		}
		

		int skillhurt=(int) ((skill.getSkillhurt()+glv)*(1+glc/100D)*2);
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			data.addBear(type);
			FightingState Accepter=new FightingState();
            if (tycSkill!=null) {
//    			9610	沐雨经霜	对一个己方人物单位使用沛然莫御，同时为其回复{公式一}点怒气并立即刷新其处于冷却中的天演策技能，冷却10回合。（仅在与玩家之间战斗有效）
//    			9611	沛雨甘泉	施放一个泽被万物，同时提升目标{公式一}%气血上限，持续3回合。
//    			9612	震风陵雨	释放一个强力泽被万物，目标数增加{公式三十七}个，此状态下的目标在回合开始前如果周围距离1以内有死亡目标，则有{公式一}%几率将自身的甘霖状态转移给死亡目标。
            	if (tycSkill.getSkillid()==9610) {
                	data.addnq((int)(tycSkill.getSkillhurt()), Accepter);
                	data.TY_L_GL_MYJS();
    			}else if (tycSkill.getSkillid()==9611) {
    				ChangeFighting fighting=new ChangeFighting();
    				fighting.setChangetype(tycSkill.getSkilltype());
    				fighting.setChangevlaue(tycSkill.getSkillhurt());
    				fighting.setChangesum(3);
    				data.ChangeData(fighting,Accepter);
    			}
			}
			ChangeFighting acec=new ChangeFighting();
			if (data.getStates()==0) {
				acec.setChangevlaue(skillhurt);
				acec.setChangetype(type);
				acec.setChangesum(skill.getSkillcontinued());
				if (skills!=null) {
					for (int j = skills.size() - 1; j >= 0; j--) {
						FightingSkill fightingSkill=skills.get(j);
						int id=fightingSkill.getSkillid();
						if (id==9603) {
							if (Battlefield.isV(20)) {
								acec.setSkill(fightingSkill);
							}
						}else if (id>=9602&&id<=9608) {							
							acec.setSkill(fightingSkill);
						}
					}
				}
				if (tycSkill!=null&&tycSkill.getSkillid()==9612) {
					acec.setSkill(tycSkill);
				}
			}else {
				acec.setChangehp(skillhurt);
				if (skills!=null) {
					for (int j = skills.size()-1; j>=0; j--) {
						FightingSkill fightingSkill=skills.get(j);
						if (fightingSkill.getSkillid()==9608) {
							acec.setChangehp((int)(skillhurt*(1+fightingSkill.getSkillhurt()/100D)));
							break;
						}
					}
				}
			}
			FightingPackage.ChangeProcess(acec,manData,data,Accepter,type,Accepterlist,battlefield);			
			Accepter.setSkillskin(skill.getSkillid()+"");
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
	/**处理事件*/
	public ChangeFighting L_GLChange(ManData myData,ManData data,FightingSkill skill,List<FightingSkill> skills,Battlefield battlefield){
		return null;
	}
}
