package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class BB_YAHY implements DataAction{
	
	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int path = battlefield.Datapathhuo(fightingEvents.getOriginator().getCamp(), fightingEvents.getOriginator().getMan());
		if (path==-1) {return;}
		manData=battlefield.fightingdata.get(path);
		if (manData.getStates()!=0) {return;}
		FightingSkill skill=manData.getSkillType(TypeUtil.BB_YAHY);
		if (skill==null) {return;}
		manData.getSkills().remove(skill);
		
		//1606,1607,1608,
		//1828,1829,1830,
		//1840,1841,1842,
		//1867,1868,1869,
		
		List<FightingSkill> skills=null;
		for (int i = battlefield.fightingdata.size()-1; i>=0; i--) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getType()==1&&data.getStates()==0&&data.getCamp()!=manData.getCamp()) {
				for (int j = data.getSkills().size()-1; j >=0; j--) {
					FightingSkill skillTwo=data.getSkills().get(j);
					int id=skillTwo.getSkillid();
					if (id==1606||id==1607||id==1608||id==1828||id==1829||id==1830||
							id==1840||id==1841||id==1842||id==1868||id==1869) {
						if (skills==null) {skills=new ArrayList<>();}
						skills.add(skillTwo);
					}
				}
			}
		}
		if (skills==null) {return;}
		//尽可能的偷取没有的技能
		boolean isSkill=false;
		FightingSkill fightingSkill=null;
		S:while (skills.size()!=0) {
			fightingSkill=skills.remove(Battlefield.random.nextInt(skills.size()));
			for (int j = manData.getSkills().size()-1; j >=0; j--) {
				FightingSkill skillTwo=manData.getSkills().get(j);
				if (skillTwo.getSkillid()==fightingSkill.getSkillid()) {
					continue S;
				}
			}
			isSkill=true;
			break;
		}
		List<GroupBuff> buffs=null;
		if (isSkill) {
			fightingSkill=fightingSkill.clone();	
			manData.getSkills().add(fightingSkill);
			buffs=battlefield.addBuff(manData,fightingSkill);
		}
		
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState Originator=fightingEvents.getOriginator();
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		Originator.setSkillskin(skill.getSkilltype());
		Originator.setText("我偷取了"+fightingSkill.getSkillname());
		
		if (buffs!=null) {Originator.setBuff(MixDeal.getBuffStr(buffs,true));}
		
		fightingEvents.setOriginator(null);
		Accepterlist.add(Originator);
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);	
		
		if (isSkill&&fightingSkill.getSkilltype().equals(TypeUtil.BB_DT)) {
			MixDeal.first(manData,TypeUtil.BB_DT,battlefield);
		}
	}
}
