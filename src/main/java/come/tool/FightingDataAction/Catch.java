package come.tool.FightingDataAction;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;

public class Catch implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		if (!(battlefield.BattleType==0||battlefield.BattleType==1||battlefield.BattleType==2)){
			return;
		}
		
		FightingState state=fightingEvents.getAccepterlist().get(0);
		int path=battlefield.Datapathhuo(state.getCamp(),state.getMan());
		if (path!=-1) {
			ManData data=battlefield.fightingdata.get(path);
			if (data.getType()==2&&data.getZs()==-1) {
				state.setStartState("被攻击");
				state.setHp_Change(-data.getHp());
				state.setSkillskin("捕捉");
				data.setStates(1);
				data.setHp(0);
				MixDeal.DeathSkill(data,state,battlefield);
				fightingEvents.getAccepterlist().clear();
				fightingEvents.setOriginator(null);
				fightingEvents.getAccepterlist().add(state);
				battlefield.NewEvents.add(fightingEvents);
				return;
			}
			if (battlefield.BattleType!=0) {return;}
			int id=data.capture();
		    if (id==-1) {
				//捕获失败处理
		    	state.setStartState("捕捉失败");
			}else {
				state.setEndState(manData.getCamp()+"|"+manData.getMan()+"|"+id);
				state.setStartState("捕捉成功");
				data.setStates(2);
			}
		    fightingEvents.setOriginator(state);
			fightingEvents.setAccepterlist(null);
			battlefield.NewEvents.add(fightingEvents);	
		}	
	}
}
