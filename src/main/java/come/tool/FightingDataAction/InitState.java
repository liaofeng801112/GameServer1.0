package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

public class InitState implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		
		TZ_LZZN(battlefield);/**龙族震怒*/
		TZ_RYDS(battlefield);/**如鱼得水*/
		BB_RHTY(battlefield);/**如虎添翼*/
	}
	/**龙族震怒*/
	public void TZ_LZZN(Battlefield battlefield){
		for (int i=battlefield.fightingdata.size()-1;i>=0;i--) {
			ManData data=battlefield.fightingdata.get(i);
			FightingSkill skill=data.getSkillType(TypeUtil.TZ_LZZN);
			if (skill!=null) {
				data.getSkills().remove(skill);
				for (int j = 0; j < data.getSkills().size(); j++) {
					FightingSkill fightingSkill=data.getSkills().get(j);
					int id=fightingSkill.getSkillid();
					if ((id>=1081&&id<=1090)||(id>=1096&&id<=1100)) {
						fightingSkill.setSkillhurt(fightingSkill.getSkillhurt()+skill.getSkillhurt());
					}
				}
			}
		}
	}
	/**如鱼得水*/
	public void TZ_RYDS(Battlefield battlefield){
		List<FightingState> Accepterlist=null;
		for (int i=battlefield.fightingdata.size()-1;i>=0;i--) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0) {continue;}
			FightingSkill skill=data.getSkillType(TypeUtil.TZ_RYDS);
			if (skill!=null) {
				if (Accepterlist==null) {Accepterlist=new ArrayList<>();}
				FightingState Originator=getFightingState(data.getCamp(), data.getMan(), Accepterlist);
				Originator.setStartState("法术攻击");
				Originator.setSkillskin("1029");	
				Originator.setEndState_1(TypeUtil.LL);
				data.addAddState(TypeUtil.LL, skill.getSkillhurt(), 0, skill.getSkillcontinued());
				data.getSkills().remove(skill);
			}
		}
		if (Accepterlist!=null) {
			FightingEvents events=new FightingEvents();
			events.setAccepterlist(Accepterlist);	
			battlefield.NewEvents.add(events);
		}
	}
	/**如虎添翼*/
	public void BB_RHTY(Battlefield battlefield){
		List<FightingState> Accepterlist=null;
		for (int i=battlefield.fightingdata.size()-1;i>=0;i--) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0) {continue;}
			if (data.getMan()>=5&&data.getMan()<=9) {
				FightingSkill skill=data.getSkillType(TypeUtil.BB_RHTY);
				if (skill!=null) {
					if (Accepterlist==null) {Accepterlist=new ArrayList<>();}
					FightingState Originator=getFightingState(data.getCamp(), data.getMan(), Accepterlist);
					Originator.setStartState("法术攻击");
					Originator.setSkillskin(TypeUtil.BB_RHTY);	
					Originator.setEndState_1(TypeUtil.BB_RHTY);
					data.addAddState(TypeUtil.BB_RHTY, skill.getSkillhurt(), 0, skill.getSkillcontinued());
					int path = battlefield.Datapathhuo(data.getCamp(), data.getMan()-5);
					data=path!=-1?battlefield.fightingdata.get(path):null;
					if (data!=null) {
						FightingState fightingState=getFightingState(data.getCamp(), data.getMan(), Accepterlist);
						fightingState.setEndState_1(TypeUtil.BB_RHTY);
						data.addAddState(TypeUtil.BB_RHTY, skill.getSkillhurt(), 0, skill.getSkillcontinued());
					}
					data.getSkills().remove(skill);
				}
			}
		}
		if (Accepterlist!=null) {
			FightingEvents events=new FightingEvents();
			events.setAccepterlist(Accepterlist);	
			battlefield.NewEvents.add(events);
		}
	}
	/**获取对象*/
	public FightingState getFightingState(int camp,int man,List<FightingState> Accepterlist){
		for (int i = 0; i < Accepterlist.size(); i++) {
			FightingState state=Accepterlist.get(i);
			if (state.getCamp()==camp&&state.getMan()==man) {
				return state;
			}
		}
		FightingState state=new FightingState(camp, man, TypeUtil.JN);
		Accepterlist.add(state);
		return state;
	}
}
