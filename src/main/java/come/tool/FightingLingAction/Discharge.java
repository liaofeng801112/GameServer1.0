package come.tool.FightingLingAction;

import java.util.ArrayList;
import java.util.List;

import org.come.bean.PathPoint;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;

public class Discharge implements LingAction {

	@Override
	public void lingAction(ManData manData, List<ManData> help,
			FightingSkill skill, Battlefield battlefield) {
		// TODO Auto-generated method stub
		List<ManData> nomydata=new ArrayList<>();
		int id=skill.getSkillid();
		PathPoint paPoint=new PathPoint(-1,-1);
        for (int i = battlefield.fightingdata.size()-1; i >=0; i--) {
        	ManData data=battlefield.fightingdata.get(i);
			if ((id==3011&&data.getSp()>manData.getSp())&&((id==3012&&data.getSp()<manData.getSp()))) {
				if (data.zuoyong(0, manData.getCamp(), 1, 0,paPoint, 0, 0,1)) {
					nomydata.add(data);
				}
			}
		}
        if (nomydata.size()==0&&skill.getSkillblue()==1) {
        	nomydata=MixDeal.get(false,null,0,manData.getCamp(),1, 0, 0,0,1,-1,battlefield,1);
		}
        if (nomydata.size()==0) return;
        
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
			int hurt=(int)skill.getSkillhurt()/nomydata.size();
			ManData data=nomydata.get(i);
			//后续添加灵宝抗性计算
			hurt=(int) (hurt*(1-data.getQuality().getRoleklb()/100.0));
//			9224|金城玉池|被速的目标有（2%*等级）的几率免疫灵宝伤害（仅在与玩家之间战斗有效）
			FightingSkill skill2=data.getAppendSkill(9224);
			if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
				hurt=0;
			}
			ChangeFighting changeFighting=new ChangeFighting();
			changeFighting.setChangehp(-hurt);	
			FightingState org2=new FightingState();
	        org2.setCamp(data.getCamp());
	        org2.setMan(data.getMan());
	        org2.setSkillskin(skill.getSkillid()+"");	
	        org2.setStartState("被攻击");
		    FightingPackage.ChangeProcess(changeFighting, null, data, org2, skill.getSkilltype(), Accepterlist, battlefield);  	
		}	
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
		
	}

}
