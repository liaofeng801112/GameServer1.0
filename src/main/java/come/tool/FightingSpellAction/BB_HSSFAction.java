package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;
import come.tool.FightingDataAction.DataActionType;

public class BB_HSSFAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingState org=new FightingState();
		org.setEndState(skill.getSkillname());
		if (manData.daijia(org,battlefield)) {return;}
		org.setCamp(manData.getCamp());
		org.setMan(manData.getMan());
		org.setStartState("药");
		FightingEvents fightingEvents=new FightingEvents();
		List<FightingState> States=new ArrayList<>();
		States.add(org);
		fightingEvents.setAccepterlist(States);
		battlefield.NewEvents.add(fightingEvents);
		DataActionType.getActionById(1).analysisAction(manData,events,TypeUtil.BB_E_HSSF,battlefield);
	}

}
