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
import come.tool.FightingData.TypeUtil;

public class YinYang implements LingAction {

	@Override
	public void lingAction(ManData manData, List<ManData> help,
			FightingSkill skill, Battlefield battlefield) {
		// TODO Auto-generated method stub
		ManData data=null;
		int path=-1;
		path=battlefield.Datapathhuo(manData.getCamp(), manData.getMan()-10);
		if (path==-1)return;
		data=battlefield.fightingdata.get(path);
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
		FightingState org2=new FightingState();
		org2.setCamp(data.getCamp());
		org2.setMan(data.getMan());
		org2.setStartState(TypeUtil.JN);
		org2.setSkillskin(skill.getSkillid()+"");
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype(skill.getSkilltype());
		changeFighting.setChangevlaue(skill.getSkillgain());
		changeFighting.setChangesum(skill.getSkillcontinued());
		FightingPackage.ChangeProcess(changeFighting, null, data, org2, "增益", Accepterlist, battlefield);
        Accepterlist.add(org2);
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
	
	}

}
