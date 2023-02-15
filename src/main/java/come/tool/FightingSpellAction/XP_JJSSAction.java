package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;
import come.tool.FightingDataAction.PhyAttack;
import come.tool.FightingData.SummonType;

public class XP_JJSSAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		List<FightingState> Accepterlist=new ArrayList<>();
		ManData data=null;
		if (events.getAccepterlist()!=null&&events.getAccepterlist().size()!=0) {
			FightingState ac=events.getAccepterlist().get(0);
			if (ac.getCamp()!=manData.getCamp()||ac.getMan()!=manData.getMan()) {
				int path=battlefield.Datapathhuo(ac.getCamp(), ac.getMan());
				if (path!=-1) {
					data=battlefield.fightingdata.get(path);
				}
			}
		}
		FightingState Originator=events.getOriginator();
		SummonType.xianzhi(manData, skill); //添加冷却
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();
		FightingState Accepter=new FightingState();
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype("荆棘束身");
		changeFighting.setChangesum(2);
		Originator.setText("荆棘束身#2");
		FightingPackage.ChangeProcess(changeFighting,manData,data,Accepter,type,Accepterlist,battlefield);
		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);
		}
	}
}