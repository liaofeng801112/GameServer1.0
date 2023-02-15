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

public class TJ_ZTBRAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//新增技能 遮天蔽日
		if (battlefield.CurrentRound < 10) {return;}
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield,10);
		FightingState Originator=events.getOriginator();
		SummonType.xianzhi(manData, skill);
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();
		for (int j = 0; j < datas.size(); j++) {
			ManData data=datas.get(j);
			FightingState Accepter=new FightingState();
			ChangeFighting changeFighting=new ChangeFighting();
			changeFighting.setChangetype("清除异常状态");
			data.ChangeData(changeFighting, Accepter);
			changeFighting.setChangetype("隐身");
			changeFighting.setChangesum(2);
			data.ChangeData(changeFighting, Accepter);
			Originator.setText("遮天蔽日#2");
			Originator.setSkillskin("1251");
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