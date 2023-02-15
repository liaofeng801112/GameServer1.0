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
import come.tool.FightingData.SummonType;

public class FM_QXAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//清心静气","气吞山河","行气如虹","神龙摆尾
		FightingState Originator=events.getOriginator();   //选择自己
		//if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		SummonType.xianzhi(manData, skill);
		//Originator.setStartState("法术攻击");
		//Originator.setSkillsy(skill.getSkillname());  //音效
		int sum = 0;
		int xiaoguo = 0;
		if (skill.getSkillid() == 22012) {
			sum =2+ (int)(Math.floor(manData.getFmsld()/3600));
		}

		else if (skill.getSkillid() == 22000) {
			sum =2+ (int)(Math.floor(manData.getFmsld()/2900));
		}
		else if (skill.getSkillid() == 22032) {
			sum =1+ (int)(Math.floor(manData.getFmsld()/2900));
		}
		else if (skill.getSkillid() == 22034) {
			sum =1+ (int)(Math.floor(manData.getFmsld()/3600));
		}
		else if (skill.getSkillid() == 22026) {
			sum =2+ (int)(Math.floor(manData.getFmsld()/3600));
			xiaoguo = manData.getFmsld();
		}

		manData.addAddState(skill.getSkilltype(),xiaoguo,0,sum);
		Originator.setEndState_1(skill.getSkilltype());
		events.setOriginator(null);
		List<FightingState> Accepterlist=new ArrayList<>();
		Accepterlist.add(Originator);
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);
//		FightingEvents events2=new FightingEvents();
//		List<FightingState> Accepterlist2=new ArrayList<>();
//		FightingState state=new FightingState();
//		ChangeFighting change=new ChangeFighting();
//		change.setChangetype(TypeUtil.L_LL);
//		change.setChangevlaue(20);
//		change.setChangesum(2);
//		//manData.ChangeData(change, state);
//		state.setStartState("代价");
//		Accepterlist2.add(state);
//		events2.setAccepterlist(Accepterlist2);
//		battlefield.NewEvents.add(events2);
	}

}