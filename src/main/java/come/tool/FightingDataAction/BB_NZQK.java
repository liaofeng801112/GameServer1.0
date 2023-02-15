package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class BB_NZQK implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int path = battlefield.Datapathhuo(fightingEvents.getOriginator().getCamp(), fightingEvents.getOriginator().getMan());
		if (path==-1) {return;}
		manData=battlefield.fightingdata.get(path);
		if (manData.getStates()!=0) {return;}
		FightingSkill skill=manData.getSkillType(TypeUtil.BB_NZQK);
		if (skill==null) {return;}
		manData.getSkills().remove(skill);
		//先  生成施法指令
		List<FightingState> list=new ArrayList<>();
		FightingState Originator=fightingEvents.getOriginator();
		Originator.setStartState("法术攻击");
		Originator.setSkillskin(TypeUtil.BB_NZQK);
		battlefield.reverseBuff(manData, Originator);
		list.add(Originator);
		FightingEvents events=new FightingEvents();
		events.setAccepterlist(list);
		battlefield.NewEvents.add(events);
	}
}
