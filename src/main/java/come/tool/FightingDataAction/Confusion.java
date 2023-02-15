package come.tool.FightingDataAction;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.ManData;

/**
 * 混乱处理
 * @author Administrator
 *
 */
public class Confusion implements DataAction {
	@Override
	public void analysisAction(ManData manData,FightingEvents fightingEvents,String type,Battlefield battlefield) {
		// TODO Auto-generated method stub	
		fightingEvents.setAccepterlist(null);
		fightingEvents.getOriginator().setStartState("普通攻击");
		DataActionType.getActionById(1).analysisAction(manData,fightingEvents,"混乱技",battlefield);
	}
}
