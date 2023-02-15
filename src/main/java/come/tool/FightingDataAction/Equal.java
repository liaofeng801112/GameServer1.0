package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;

public class Equal implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill skill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (skill==null){return;}
        if (!PK_MixDeal.isPK(battlefield.BattleType)) {
			return;
		}
        AddState addState=new AddState();
		addState.setStatename("冷却");
		addState.setStateEffect(7012);
		addState.setSurplus(15);	
		manData.getAddStates().add(addState);
	
		List<ManData> datas=MixDeal.get(false, null, 1, -1, 0, 0, 0, 0, 20, -1, battlefield, 1);
		for (int i = datas.size()-1; i >=0; i--) {
			if (datas.get(i).getType()>=2) {
				datas.remove(i);
			}
		}
		/**hp总 mp总*/
		int camp=manData.getCamp();
		int hpb = 0,mpb = 0;
		for (int i = 0; i < datas.size(); i++) {
			hpb+=datas.get(i).getHp_z();
			mpb+=datas.get(i).getMp_z();			
		}
		hpb/=datas.size();
		mpb/=datas.size();	
		fightingEvents.setAccepterlist(null);fightingEvents.setOriginator(null);
		List<FightingState> acclist=new ArrayList<>();
		
		ChangeFighting fighting=new ChangeFighting();
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			int hpv=hpb,mpv=mpb;
			if (data.getCamp()==camp) {
				hpv=(int) (hpv*skill.getSkillgain()/100);
				mpv=(int) (mpv*skill.getSkillgain()/100);
			}else {
				hpv=(int) (hpv*skill.getSkillhurt()/100);
				mpv=(int) (mpv*skill.getSkillhurt()/100);
			}
			hpv-=data.getHp();
			mpv-=data.getMp();
			fighting.setChangehp(hpv);
			fighting.setChangemp(mpv);
			FightingState Fman=new FightingState();
			Fman.setSkillskin(skill.getSkillid()+"");	
			data.ChangeData(fighting, Fman);
			acclist.add(Fman);
		}
		fightingEvents.setAccepterlist(acclist);
		battlefield.NewEvents.add(fightingEvents);	
	}
}
