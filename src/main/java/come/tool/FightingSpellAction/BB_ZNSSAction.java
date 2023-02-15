package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;
import come.tool.FightingDataAction.DataActionType;

public class BB_ZNSSAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		//先清除召唤兽状态
	    FightingEvents qz=new FightingEvents();
	    List<FightingState> dStates=new ArrayList<>(); 
	    FightingState daijia=new FightingState();
	    ChangeFighting changeFighting=new ChangeFighting();
	    changeFighting.setChangetype("清除状态");
	    manData.ChangeData(changeFighting, daijia);
	    daijia.setStartState("代价");
	    dStates.add(daijia);
	    qz.setAccepterlist(dStates);
	    battlefield.NewEvents.add(qz);
	    //清除人物状态以及回血
	    FightingEvents hr=new FightingEvents();
	    List<FightingState> hrac=new ArrayList<>(); 
	  	List<ManData> datas=MixDeal.getjieshou(hr,skill, manData,null,battlefield);  
	  	for (int i = 0; i < datas.size(); i++) {
	  		FightingState ac=new FightingState();
	  		changeFighting.setChangehp((int)(datas.get(i).getHp_z()*0.5));
		  	changeFighting.setChangemp((int)(datas.get(i).getMp_z()*0.5));
		  	datas.get(i).ChangeData(changeFighting, ac);
		  	ac.setSkillskin("1829");	
		  	ac.setStartState(TypeUtil.JN);
		  	hrac.add(ac);
		}
	  	FightingState acs=new FightingState();
	  	acs.setCamp(manData.getCamp());
	  	acs.setMan(manData.getMan());
	  	acs.setStartState("法术攻击");
	  	hrac.add(acs);
	  	hr.setAccepterlist(hrac);
	  	battlefield.NewEvents.add(hr);
	    //召回召唤兽
	  	DataActionType.getActionById(5).analysisAction(manData,null,"召回",battlefield);	
	  	MixDeal.Appearance(manData,battlefield); 
	}

}
