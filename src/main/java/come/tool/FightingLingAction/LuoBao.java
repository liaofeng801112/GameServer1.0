package come.tool.FightingLingAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class LuoBao implements LingAction {

	@Override
	public void lingAction(ManData manData, List<ManData> help,
			FightingSkill skill, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int sum=skill.getSkillsum();
		List<ManData> nomydata=MixDeal.get(false,null, 0, manData.getCamp(), 0, 1, 0,0, sum,skill.getCamp(),battlefield,1);
       
		FightingEvents fightingEvents=new FightingEvents();
        List<FightingState> Accepterlist=new ArrayList<>();
        FightingState org=new FightingState();
        org.setCamp(manData.getCamp());
        org.setMan(manData.getMan());
        org.setStartState("法术攻击");
        Accepterlist.add(org);
		for (int i = help.size()-1; i >=0; i--) {
			 FightingState org1=new FightingState();
		     org1.setCamp(help.get(i).getCamp());
		     org1.setMan(help.get(i).getMan());
		     org1.setStartState("法术攻击");
		     Accepterlist.add(org1);
		}
	
		for (int i = nomydata.size()-1; i >=0; i--) {
			ManData data=nomydata.get(i);
			double hurt=skill.getSkillhurt()+manData.getShanghai()-data.getKangluobao();
			ChangeFighting changeFighting=new ChangeFighting();
			if (Battlefield.random.nextInt(100)<hurt) {
				changeFighting.setChangehp(-data.getHp_z());
			}
	        FightingState org2=new FightingState();
	        org2.setCamp(data.getCamp());
	        org2.setMan(data.getMan());
	        org2.setSkillskin(skill.getSkillid()+"");	
	        FightingPackage.ChangeProcess(changeFighting, null, data, org2, skill.getSkilltype(), Accepterlist, battlefield);  
	        org2.setStartState(TypeUtil.JN);
		}
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
		
	}
}
