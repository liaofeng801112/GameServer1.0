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

public class TJ_WJGZAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//新增技能 万剑归宗
		List<ManData> datas=MixDeal.getdaji(5, manData.getCamp(), events, battlefield);
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState Originator=events.getOriginator();
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价

		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState fightingState=new FightingState();
			ChangeFighting fighting=new ChangeFighting();
			fighting.setChangetype("技能");
			fighting.setChangehp(-10000);
			fightingState.setSkillskin("1254");
			FightingPackage.ChangeProcess(fighting, null, data, fightingState,"技能",Accepterlist, battlefield);
		}
		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);
	}
}