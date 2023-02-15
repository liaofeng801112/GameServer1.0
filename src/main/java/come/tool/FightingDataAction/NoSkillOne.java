package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;

/**
 * 化无技能 处理上化无状态
 * @author Administrator
 *
 */
public class NoSkillOne implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingEvents hhe=new FightingEvents();
		List<FightingState> hhs=new ArrayList<>();
		FightingState hh=new FightingState();
		ChangeFighting hhc=new ChangeFighting();
		hhc.setChangetype("化无");
		hhc.setChangevlaue(manData.getCamp()+0.1*manData.getMan());
		int path=battlefield.Datapathhuo(fightingEvents.getOriginator().getCamp(),fightingEvents.getOriginator().getMan());
	    if (path!=-1) {
	    	ManData Data=battlefield.fightingdata.get(path);
	    	Data.ChangeData(hhc, hh);
	    	hh.setStartState("代价");
	    	hhs.add(hh);
	    	hhe.setAccepterlist(hhs);
	    	battlefield.NewEvents.add(battlefield.xzsx(manData.getCamp(),manData.getMan()),hhe);
		}
	}
}
