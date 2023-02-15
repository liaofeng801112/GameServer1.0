package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;
/**
 * 涅槃处理
 * @author Administrator
 *
 */
public class Nirvana implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		int path=battlefield.Datapath(fightingEvents.getOriginator().getCamp(), fightingEvents.getOriginator().getMan());
		if (path==-1) return;
		manData=battlefield.fightingdata.get(path);
		FightingSkill skill=null;
		for (int i = manData.getSkills().size()-1; i >=0 ; i--) {
			FightingSkill skill2=manData.getSkills().get(i);
			if (skill2.getSkilltype().equals(TypeUtil.BB_JR)) {
				if (skill==null) {
					skill=skill2;		
				}		
				manData.getSkills().remove(i);
			}else if (skill2.getSkilltype().equals("复活")) {
				skill=skill2;
				manData.getSkills().remove(i);
			}
		}  
		if (skill==null) {return;}
		String text=null;
		FightingState org=new FightingState();
		List<FightingState> States=new ArrayList<>();
		States.add(org);
		ChangeFighting changeFighting=new ChangeFighting();
		if (skill.getSkilltype().equals(TypeUtil.BB_JR)) {
			FightingSkill skill2=manData.getSkillType(TypeUtil.BB_E_DNBS);
			if (skill2!=null&&Battlefield.isV(skill2.getSkillgain())) {
				text="大难不死#2";
				changeFighting.setChangehp((int)(manData.getHp_z()*skill2.getSkillgain()/100D));
				changeFighting.setChangemp((int)(manData.getMp_z()*skill2.getSkillgain()/100D));
			}else {
				changeFighting.setChangehp(10);			
			}
			manData.addHuDun2(2, States);
		}else {
			changeFighting.setChangehp(manData.getHp_z());
			changeFighting.setChangemp(manData.getMp_z());
		}
		changeFighting.setChangetype("清除状态");
		fightingEvents=new FightingEvents();
    	 org=new FightingState();
    	manData.ChangeData(changeFighting, org);
    	org.setStartState(TypeUtil.JN);
    	org.setSkillskin(skill.getSkillid()+"");
    	org.setText(text);
    	 States=new ArrayList<>();
    	States.add(org);
    	fightingEvents.setAccepterlist(States);
    	battlefield.NewEvents.add(fightingEvents);	
	}

}
