package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import org.come.bean.PathPoint;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;

public class Huayu implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		List<PathPoint> yi=battlefield.huayu;
		List<PathPoint> er=new ArrayList<>();
		//获取双方阵营
		int camp=yi.get(0).getX();
	    for (int i = yi.size()-1; i >=0; i--) {
			if (yi.get(i).getX()!=camp) {
				er.add(yi.get(i));
				yi.remove(i);
			}
		}
	    fightingEvents=new FightingEvents();
        List<FightingState> Accepterlist=new ArrayList<>();      
	    if (yi.size()!=0) {
	    	int camp2=battlefield.nomy(yi.get(0).getX());
	    	List<ManData> datas=MixDeal.get(false,null, 0, camp2, 1, 0, 0, 0, yi.size(),-1, battlefield,1);
		    for (int i = datas.size()-1; i >=0; i--) {
		        ManData data=datas.get(i);
		    	ChangeFighting changeFighting=new ChangeFighting();
		    	changeFighting.setChangehp(yi.get(i).getY());	
		    	FightingState org2=new FightingState();
		        org2.setCamp(data.getCamp());
		    	org2.setMan(data.getMan());	
		    	org2.setStartState("被攻击");
		    	FightingPackage.ChangeProcess(changeFighting, null, data, org2,"化羽", Accepterlist, battlefield);  	  			 	
			}	
		}
	    if (er.size()!=0) {	
	    	List<ManData> datas=MixDeal.get(false,null, 0, camp, 1, 0, 0, 0, er.size(),-1, battlefield,1);
	    	for (int i = datas.size()-1;i>=0; i--) {
			    ManData data=datas.get(i);
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangehp(er.get(i).getY());	
				FightingState org2=new FightingState();
		        org2.setCamp(data.getCamp());
		        org2.setMan(data.getMan());	
		        org2.setStartState("被攻击");
			    FightingPackage.ChangeProcess(changeFighting, null, data, org2,"化羽", Accepterlist, battlefield);  	
			
			}	
		}
	    if (Accepterlist.size()!=0) {
	    	fightingEvents.setAccepterlist(Accepterlist);
	  		battlefield.NewEvents.add(fightingEvents);	
		}
	}

}
