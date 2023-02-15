package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.SummonType;

public class BB_HYMBAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		1313	黑夜蒙蔽	消耗50%气血上限，创造出一片黑夜笼罩在本方上空，3回合内对方师门法术、物理攻击会受到一定程度蒙蔽，本方单位有{公式一}%几率躲闪掉。(前3回合不能使用，每场战斗只能使用1次)
		if(skill.getSkillid()==23009&&battlefield.CurrentRound <= 3) {return;};
		if(skill.getSkillid()==23009) {SummonType.xianzhi(manData,skill);};
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}
		List<GroupBuff> buffs=battlefield.addBuff(manData, skill);
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		if (buffs!=null) {Originator.setBuff(MixDeal.getBuffStr(buffs,true));}
		events.setOriginator(null);
		Accepterlist.add(Originator);
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);	
	}

}
