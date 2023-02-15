package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class TY_FY_ZYCXAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		9110|知盈处虚|牺牲自己（4%*等级）的速度，使本回合内已被封印命中的单位的速度降低（4%*等级），持续3回合，回合开始前使用。（仅在与玩家之间战斗有效。）
		if (!PK_MixDeal.isPK(battlefield.BattleType)) {return;}
		FightingState Originator=events.getOriginator();
		events.setOriginator(null);
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		List<ManData> datas=MixDeal.get(false,manData,0,-1,0,0,2,0,10,-1,battlefield,1);
		for (int j = 0; j < datas.size(); j++) {
			datas.get(j).addAddState(TypeUtil.TY_FY_ZYCX,skill.getSkillhurt(), 0, 3);
		}
		manData.addAddState(TypeUtil.TY_FY_ZYCX,skill.getSkillhurt(),0,3);
		FightingEvents ksevents=new FightingEvents();
		ksevents.setCurrentRound(battlefield.CurrentRound);
		List<FightingState> Accepterlist=new ArrayList<>();
		Originator.setCamp(manData.getCamp());
		Originator.setMan(manData.getMan());
		Originator.setStartState("法术攻击");
		Accepterlist.add(Originator);
		ksevents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(ksevents);
	}

}
