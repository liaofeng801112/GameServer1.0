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

public class TJ_TLHZAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//新增技能 偷梁换柱
		if (battlefield.CurrentRound < 3) {return;}
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield,3);
		FightingState Originator=events.getOriginator();
		SummonType.xianzhi(manData, skill); //添加冷却
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();
		manData.addAddState("偷梁惩罚",0,0,3);
		for (int j = 0; j < datas.size(); j++) {
			ManData data=datas.get(j);
			FightingState Accepter=new FightingState();
			ChangeFighting changeFighting=new ChangeFighting();
			changeFighting.setChangetype(type);
			changeFighting.setChangesum(2);
			Originator.setText("偷梁换柱#2");
			FightingPackage.ChangeProcess(changeFighting,manData,data,Accepter,type,Accepterlist,battlefield);
		}
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