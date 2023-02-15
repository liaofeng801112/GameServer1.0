package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class L_LLAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		events.setOriginator(null);
		List<FightingState> Accepterlist=new ArrayList<>();
		Accepterlist.add(Originator);
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);	
		
		FightingEvents events2=new FightingEvents();
		List<FightingState> Accepterlist2=new ArrayList<>();
		FightingState state=new FightingState();
		ChangeFighting change=new ChangeFighting();
		change.setChangetype(TypeUtil.L_LL);
		change.setChangevlaue(20);
		change.setChangesum(2);
		manData.ChangeData(change, state);
		state.setStartState("代价");
		Accepterlist2.add(state);
		events2.setAccepterlist(Accepterlist2);
		battlefield.NewEvents.add(events2);	
	}

}
