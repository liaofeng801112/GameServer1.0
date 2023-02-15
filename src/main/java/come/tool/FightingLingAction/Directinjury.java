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

public class Directinjury implements LingAction{

	@Override
	public void lingAction(ManData manData, List<ManData> help,
			FightingSkill skill, Battlefield battlefield) {
		// TODO Auto-generated method stub
		int sum=skill.getSkillsum();
		if (skill.getSkillid()==3004&&Battlefield.random.nextInt(3)==0) sum++;
		List<ManData> nomydata=MixDeal.get(false,null, 0, manData.getCamp(), 0, 0, 0,0, sum,skill.getCamp(),battlefield,1);
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
			hurt=(int) (hurt*(1-data.getQuality().getRoleklb()/100.0));
//			9224|金城玉池|被速的目标有（2%*等级）的几率免疫灵宝伤害（仅在与玩家之间战斗有效）
			FightingSkill skill2=data.getAppendSkill(9224);
			if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
				hurt=0;
			}
			ChangeFighting changeFighting=new ChangeFighting();
			if (skill.getSkillid()==3002) {
				changeFighting.setChangehp(-(Battlefield.random.nextInt(hurt)+1));		
			}else if (skill.getSkillid()==3003) {
				changeFighting.setChangehp((int)(-hurt*(1-data.getvalue(2))));				
			}else if (skill.getSkillid()==3004) {
				changeFighting.setChangehp((int)(-hurt*data.getvalue(2)));				
			}else if (skill.getSkillid()==3007) {
				changeFighting.setChangehp(-hurt);
				changeFighting.setChangemp(-(hurt>>1));				
			}else if (skill.getSkillid()==3008) {
				changeFighting.setChangehp(-hurt);
				changeFighting.setChangemp(-(hurt<<1));			
			}else {
				changeFighting.setChangehp(-hurt);
			}
			if (skill.getSkillcontinued()!=0) {
				changeFighting.setChangetype(skill.getSkilltype());
				changeFighting.setChangesum(skill.getSkillcontinued());
				changeFighting.setChangevlaue(skill.getSkillgain());
			}
	        FightingState org2=new FightingState();
	        org2.setCamp(data.getCamp());
	        org2.setMan(data.getMan());
	        org2.setSkillskin(skill.getSkillid()+"");	
	        org2.setStartState("被攻击");
	        FightingPackage.ChangeProcess(changeFighting, null, data, org2, skill.getSkilltype(), Accepterlist, battlefield);  
	        if (skill.getSkillid()==3005&&data.getvalue(0)>=0.66) {
	        	FightingState org3=new FightingState();
	        	org3.setCamp(data.getCamp());
	        	org3.setMan(data.getMan());
	        	org3.setStartState("代价");
	        	changeFighting.setChangehp(-hurt);
		        FightingPackage.ChangeProcess(changeFighting, null, data, org3, skill.getSkilltype(), Accepterlist, battlefield);  				        
			}else if (skill.getSkillid()==3006&&data.getvalue(0)<=0.33) {
				FightingState org3=new FightingState();
	        	org3.setCamp(data.getCamp());
	        	org3.setMan(data.getMan());
	        	org3.setStartState("代价");
	        	changeFighting.setChangehp(-hurt/2);
		        FightingPackage.ChangeProcess(changeFighting, null, data, org3, skill.getSkilltype(), Accepterlist, battlefield);  		    
			}
		}
		
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
	}

}	
