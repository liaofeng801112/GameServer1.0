package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;
/**
 * 将死
 * @author Administrator
 *
 */
public class Mate implements DataAction{
	 
	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
	    int nomycamp=battlefield.nomy(fightingEvents.getOriginator().getCamp());
		int ren=0;
	    if (type.equals(TypeUtil.BB_JS)) {
	    	ren=2;
		}
	    List<ManData> datas=MixDeal.get(true,manData, 1, nomycamp, 1, ren, 1, 0, 10, -1, battlefield,1);
	    if (datas.size()==0) return;
		FightingEvents gjEvents=new FightingEvents();
		List<FightingState> zls=new ArrayList<>();	
		ChangeFighting hf=new ChangeFighting();
		hf.setChangetype("清除异常状态");
		for (int i = 0; i < datas.size(); i++) {
		    ManData data=datas.get(i);
			if (data.getType()!=1) continue;
			FightingState ace=new FightingState();
			data.ChangeData(hf, ace);
			ace.setStartState(TypeUtil.JN);
			zls.add(ace);
		}
		if (zls.size()==0) {
			for (int i = 0; i < battlefield.fightingdata.size(); i++) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getCamp()==fightingEvents.getOriginator().getCamp()&&data.getStates()==0) {
					FightingState ace=new FightingState();
					ace.setCamp(data.getCamp());
					ace.setMan(data.getMan());
					ace.setStartState(TypeUtil.JN);
					zls.add(ace);
					break;
				}
			}
		}
		if (zls.size()==0)return;
		gjEvents.setAccepterlist(zls);	
		gjEvents.getAccepterlist().get(0).setSkillskin("1830");	
		battlefield.NewEvents.add(gjEvents);
	}

}
