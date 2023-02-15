package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class BB_HNYGAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		if (!Battlefield.isV(skill.getSkillgain())) {return;}
		ManData parent=battlefield.getPetParents(manData);
		if (parent==null) {return;}
		if (!manData.getSkills().remove(skill)) {return;}
		FightingEvents fightingEvents=new FightingEvents();
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState fightingState=new FightingState();
		fightingState.setStartState(TypeUtil.JN);
		fightingState.setCamp(manData.getCamp());
		fightingState.setMan(manData.getMan());
		
	    ChangeFighting changeFighting=new ChangeFighting();
	    changeFighting.setChangetype(skill.getSkilltype());
	    changeFighting.setChangehp((int)(manData.getHp_z()*skill.getSkillgain()*0.7));
	    changeFighting.setChangevlaue(skill.getSkillgain()*0.7);//仙法鬼火物理
	    changeFighting.setChangevlaue2(skill.getSkillgain()*0.3);//冰混睡忘
	    changeFighting.setChangesum(2);
	    manData.ChangeData(changeFighting, fightingState);
	    fightingState.setText("患难与共!#2");
		Accepterlist.add(fightingState);
		
		
		FightingState State2=new FightingState();
		State2.setStartState(TypeUtil.JN);
		State2.setCamp(parent.getCamp());
		State2.setMan(parent.getMan());
		
	    changeFighting=new ChangeFighting();
	    changeFighting.setChangetype(skill.getSkilltype());
	    changeFighting.setChangehp((int)(parent.getHp_z()*skill.getSkillgain()*0.7));
	    changeFighting.setChangevlaue(skill.getSkillgain()*0.7);//仙法鬼火物理
	    changeFighting.setChangevlaue2(skill.getSkillgain()*0.3);//冰混睡忘
	    changeFighting.setChangesum(2);
	    parent.ChangeData(changeFighting, State2);
	    Accepterlist.add(State2);
	    
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);

	}

}
