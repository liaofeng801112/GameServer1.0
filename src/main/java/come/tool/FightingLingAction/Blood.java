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

public class Blood implements LingAction{

	@Override
	public void lingAction(ManData manData, List<ManData> help,
			FightingSkill skill, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int sum=skill.getSkillsum();
		int fengyin=0;
		if (skill.getSkillid()==3032)fengyin=1;
		int camp=battlefield.nomy(manData.getCamp());
		List<ManData> nomydata=MixDeal.get(false,null, 0,camp, 0, 0,fengyin,0, sum,skill.getCamp(),battlefield,1);
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
			int hurt=(int)(skill.getSkillhurt()*manData.getShanghai());
			ManData data=nomydata.get(i);
			//后续添加灵宝抗性计算
			ChangeFighting changeFighting=new ChangeFighting();
			changeFighting.setChangehp(hurt);
			if (skill.getSkillcontinued()!=0) {
				changeFighting.setChangetype(skill.getSkilltype());
				changeFighting.setChangesum(skill.getSkillcontinued());
				changeFighting.setChangevlaue(skill.getSkillgain());
			}
		    if (skill.getSkillid()==3020) {
			    if (Battlefield.random.nextInt(100)<skill.getSkillgain())
			    changeFighting.setChangetype("清除异常状态");
			}else if (skill.getSkillid()==3032) {
				changeFighting.setChangehp((int)(skill.getSkillgain()*data.getHp_z()));
				changeFighting.setChangetype("清除异常状态");
			}else if (skill.getSkillid()==3023) {
				changeFighting.setChangevlaue2(skill.getSkillgain());
			}
		    FightingState org2=new FightingState();
	        org2.setCamp(data.getCamp());
	        org2.setMan(data.getMan());
	        org2.setSkillskin(skill.getSkillid()+"");	
	        org2.setStartState(TypeUtil.JN);
	        FightingPackage.ChangeProcess(changeFighting, null, data, org2, skill.getSkilltype(), Accepterlist, battlefield);  		
	        if (skill.getSkillid()==3014&&data.getvalue(0)<=0.33) {
	        	FightingState org3=new FightingState();
	        	org3.setCamp(data.getCamp());
	        	org3.setMan(data.getMan());
	        	org3.setStartState("代价");
		        FightingPackage.ChangeProcess(changeFighting, null, data, org3, skill.getSkilltype(), Accepterlist, battlefield);  				        
			}else if (skill.getSkillid()==3015&&data.getvalue(0)<=0.66) {
				FightingState org3=new FightingState();
	        	org3.setCamp(data.getCamp());
	        	org3.setMan(data.getMan());
	        	org3.setStartState("代价");
	        	changeFighting.setChangehp(changeFighting.getChangehp()/2);
		        FightingPackage.ChangeProcess(changeFighting, null, data, org3, skill.getSkilltype(), Accepterlist, battlefield);  		    
			}
		}	
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
	
	}

}
