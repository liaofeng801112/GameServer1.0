package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.SummonType;

public class FM_PTGJ implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
							   String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		//SummonType.xianzhi(manData, skill);
		FightingState org=new FightingState();
		org.setEndState(type);
		org.setCamp(manData.getCamp());
		org.setMan(manData.getMan());

//		if (type.equals("兵临城下")) {
//			manData.executeFbgs(0, org);
//		}

		FightingEvents events=new FightingEvents();
		List<FightingState> States=new ArrayList<>();
		States.add(org);
		events.setAccepterlist(States);
		battlefield.NewEvents.add(events);
		DataActionType.getActionById(1).analysisAction(manData, fightingEvents, type,battlefield);

		int fmsld =manData.getFmsld()-3;
		if (fmsld<0) {fmsld=0;}
		manData.setFmsld(fmsld);
	}

}