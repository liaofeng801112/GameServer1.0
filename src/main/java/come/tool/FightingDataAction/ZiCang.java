package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;

public class ZiCang implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingState org=new FightingState();
		org.setEndState(type);
		if (manData.daijia(org,battlefield)) {
			return;
		}
		org.setCamp(manData.getCamp());
		org.setMan(manData.getMan());
		org.setStartState("药");
		FightingEvents events=new FightingEvents();
		List<FightingState> States=new ArrayList<>();
		States.add(org);
		events.setAccepterlist(States);
		battlefield.NewEvents.add(events);
		DataActionType.getActionById(1).analysisAction(manData, fightingEvents, type,battlefield);
	}

}
