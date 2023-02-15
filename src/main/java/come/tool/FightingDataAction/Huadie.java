package come.tool.FightingDataAction;

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

public class Huadie implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill fightingSkill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (fightingSkill==null) return;
		List<ManData> datas=MixDeal.getjieshou(fightingEvents, fightingSkill, manData,null,battlefield); 
		if (datas.size()==0) return;	
		ManData data=datas.get(0);
		ChangeFighting nomy=new ChangeFighting();
		FightingState nomys=new FightingState();
		boolean c=false;
		if (Battlefield.random.nextInt(100)<fightingSkill.getSkillhurt()) {
			nomy.setChangehp(-(int)(manData.getHp()*fightingSkill.getSkillgain()/100));
			nomys.setStartState("被攻击");	
			c=true;
		}else {
			nomys.setStartState(TypeUtil.JN);
		}
		nomys.setSkillskin(fightingSkill.getSkillid()+"");
		FightingEvents ass=new FightingEvents();
		List<FightingState> a=new ArrayList<>();
		FightingPackage.ChangeProcess(nomy,manData,data,nomys,fightingSkill.getSkilltype(),a,battlefield);
		FightingState mys=new FightingState();
		mys.setCamp(manData.getCamp());
		mys.setMan(manData.getMan());
		mys.setStartState("法术攻击");
		mys.setEndState(fightingSkill.getSkillname());
		manData.daijia(fightingSkill,mys, battlefield);
		if (c) {
			mys.setHp_Change(-(manData.getHp()-1));
			manData.setHp(1);		
		}
		a.add(mys);
		ass.setAccepterlist(a);
		battlefield.NewEvents.add(ass);
	}

}
