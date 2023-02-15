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
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class TY_MYAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		9389|流风回雪|回合开始前使用,使用后清除身上所有负面状态（等级2并回复自身血法上限的60%；等级三同时免疫物理攻击，持续1回合；等级四改为同时免疫所有攻击，持续1回合 ； 等级5改为同时免疫所有的攻击和控制，持续1回合）
//		9262|销声匿迹|给自己释放一个可以持续3回合的护盾，该护盾消耗法力会抵挡所受的气血伤害。每回合最多吸收最大法力值（10%+5%*等级）的伤害。（仅在与NPC之间战斗有效）
		if (skill.getSkillid()==9262&&PK_MixDeal.isPK(battlefield.BattleType)) {return;}
		
		FightingState Originator=events.getOriginator();
		events.setOriginator(null);
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		List<FightingState> Accepterlist=new ArrayList<>();
		Originator.setCamp(manData.getCamp());
		Originator.setMan(manData.getMan());
		ChangeFighting changeFighting=new ChangeFighting();
		if (skill.getSkillid()==9389) {
			if (skill.getSkillhurt()>=2) {
				changeFighting.setChangehp((int)(manData.getHp_z()*0.6));
				changeFighting.setChangemp((int)(manData.getMp_z()*0.6));	
			}
			changeFighting.setChangetype(TypeUtil.TY_SSC_LFHX);
			changeFighting.setChangevlaue(skill.getSkillhurt());	
			changeFighting.setChangevlaue2(skill.getSkillhurt());
			Originator.setSkillskin(skill.getSkilltype());
		}else if (skill.getSkillid()==9262) {
			changeFighting.setChangetype(TypeUtil.TY_L_XSNJ);
			changeFighting.setChangevlaue(manData.getMp_z()*skill.getSkillhurt()/100.0);	
			changeFighting.setChangevlaue2(changeFighting.getChangevlaue());	
			changeFighting.setSkills(ControlAction.getSkills(manData,skill,battlefield.BattleType));
		}
		FightingPackage.ChangeProcess(changeFighting, null, manData, Originator, TypeUtil.JN, Accepterlist, battlefield);	
		Originator.setStartState("法术攻击");
		FightingEvents ksevents=new FightingEvents();
		ksevents.setCurrentRound(battlefield.CurrentRound);
		ksevents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(ksevents);
	}

}
