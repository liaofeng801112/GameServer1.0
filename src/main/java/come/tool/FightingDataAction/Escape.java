package come.tool.FightingDataAction;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.ManData;

public class Escape implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		int gl=80;
		//创建逃跑动作
//		 10 1阵营不能逃跑 2阵营逃跑概率调低
//		 15 16无法逃跑
		if (manData.getType()==2) {
			gl=101;
		}else if (battlefield.BattleType==10) {
			if (manData.getCamp()==1) {
				gl=-1;
			}else {
				gl=50;
			}
		}else if (battlefield.BattleType==15||battlefield.BattleType==16||battlefield.BattleType==101) {
			gl=-1;
		}
        if (Battlefield.random.nextInt(100)<gl) {
			fightingEvents.getOriginator().setStartState("逃跑成功");	
			fightingEvents.getOriginator().setSkillsy("逃跑成功");	
			if (manData.getType()==0) {
				for (int i = 0; i < 4; i++) {
					int rolepath=battlefield.Datapath(manData.getCamp(), manData.getMan()+i*5);
					if (rolepath!=-1) 
					{
					battlefield.fightingdata.get(rolepath).setStates(2);
					battlefield.fightingdata.get(rolepath).getAddStates().clear();
					}	
				}		
			}else {
				manData.setStates(2);
			}		
		}else {
			fightingEvents.getOriginator().setStartState("逃跑失败");
			fightingEvents.getOriginator().setSkillsy("逃跑失败");	
		}
		battlefield.NewEvents.add(fightingEvents);	
	}

	

}
