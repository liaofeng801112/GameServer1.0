package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class BB_TDTS implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		String skin=fightingEvents.getOriginator().getEndState();
		if (skin==null) {return;}
		int camp=fightingEvents.getOriginator().getCamp();
		ManData data=null;
		for (int i = battlefield.fightingdata.size()-1; i>=0; i--) {
			ManData data2=battlefield.fightingdata.get(i);
			if (data2.getStates()!=0) {continue;}
			if (data2.getType()!=1) {continue;}
			if (data2.getCamp()==camp) {continue;}
			if (data2.getSkin()!=null&&skin.equals(data2.getSkin())) {//带走的目标
				if (data==null||Battlefield.isV(40)) {
					data=data2;
				}
			}
		}
		if (data==null) {return;}
		fightingEvents.setOriginator(null);
		
		List<FightingState> zls=new ArrayList<>();
		FightingState ace=new FightingState();
		ChangeFighting fighting=new ChangeFighting();
		FightingPackage.ChangeProcess(fighting, null, data, ace, TypeUtil.ZSSH, zls, battlefield);
		ace.setSkin(TypeUtil.BB_TDTS);
		fightingEvents.setAccepterlist(zls);
		battlefield.NewEvents.add(fightingEvents);	
	}
}
