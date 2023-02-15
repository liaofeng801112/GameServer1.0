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
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class TY_HS_SSXXAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		if (!PK_MixDeal.isPK(battlefield.BattleType)) {return;}
//		9126|生死相许|与敌方单个异性一起进入昏睡状态，施法后，自己下回合待机。成功率为（20%*等级）。（仅在与玩家之间战斗有效。）
//		9129|卧雪眠霜|生死相许的目标可以对非异性使用，对敌方目标使用此技能降低目标仙法鬼火抗性（3%*等级），持续三回合。（仅在与玩家之间战斗有效。）
		List<FightingSkill> skills=ControlAction.getSkills(manData,skill,battlefield.BattleType);
		skills=ControlAction.addSkill(manData.getSkillType(TypeUtil.TY_HS_WXMS), skills);
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield); 
		
		FightingState Originator=events.getOriginator();
		events.setOriginator(null);
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype(TypeUtil.HS);
		changeFighting.setChangesum(5);
		FightingPackage.ChangeProcess(changeFighting, null, manData, Originator, TypeUtil.HS, Accepterlist, battlefield);
		Originator.setStartState("法术攻击");
		
		if (datas.size()!=0) {
			ManData data=datas.get(0);
			if (Battlefield.isV(skill.getSkillhurt())) {
				FightingState fightingState=new FightingState();
				ChangeFighting fighting=new ChangeFighting();
				fighting.setChangetype(TypeUtil.HS);
				fighting.setChangesum(5);
				fighting.setSkills(skills);
				FightingPackage.ChangeProcess(fighting, null, data, fightingState, TypeUtil.HS, Accepterlist, battlefield);			
			}	
		}
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);	
	}

}
