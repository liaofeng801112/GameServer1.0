package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class BB_RHTY implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int path = battlefield.Datapathhuo(fightingEvents.getOriginator().getCamp(), fightingEvents.getOriginator().getMan());
		if (path==-1) {return;}
		manData=battlefield.fightingdata.get(path);
		if (manData.getStates()!=0) {return;}
		FightingSkill skill=manData.getSkillType(TypeUtil.BB_RHTY);
		if (skill==null) {return;}
		manData.getSkills().remove(skill);
		
		FightingState Originator=fightingEvents.getOriginator();
		Originator.setStartState("法术攻击");
		Originator.setSkillskin(TypeUtil.BB_RHTY);	
		Originator.setEndState_1(TypeUtil.BB_RHTY);
		manData.addAddState(TypeUtil.BB_RHTY, skill.getSkillhurt(), 0, skill.getSkillcontinued());
		
		List<FightingState> Accepterlist=new ArrayList<>();
		Accepterlist.add(Originator);
		
		path = battlefield.Datapathhuo(manData.getCamp(), manData.getMan()-5);
		ManData data=path!=-1?battlefield.fightingdata.get(path):null;
		if (data!=null) {
			FightingState fightingState=new FightingState(data.getCamp(), data.getMan(), TypeUtil.JN);
			fightingState.setEndState_1(TypeUtil.BB_RHTY);
			data.addAddState(TypeUtil.BB_RHTY, skill.getSkillhurt(), 0, skill.getSkillcontinued());
			Accepterlist.add(fightingState);
		}
		
		fightingEvents.setOriginator(null);
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
	}
}
