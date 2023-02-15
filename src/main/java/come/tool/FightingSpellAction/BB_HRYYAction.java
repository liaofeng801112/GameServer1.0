package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingManData;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class BB_HRYYAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		if (!Battlefield.isV(skill.getSkillgain())) {return;}
		if (!manData.getSkills().remove(skill)) {return;}
		FightingEvents fightingEvents=new FightingEvents();
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState fightingState=new FightingState();
		fightingState.setStartState(TypeUtil.JN);
		fightingState.setCamp(manData.getCamp());
		fightingState.setMan(manData.getMan());
		fightingState.setText("忽如一夜春风来,千树万树梨花开!#2");
		Accepterlist.add(fightingState);
		
		for (int i = battlefield.fightingdata.size()-1;i>=0;i--) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getType()!=0||data.getCamp()!=manData.getCamp()) {continue;}
			if (data.getMan()+5==manData.getMan()) {continue;}
			for (int j = data.getPets().size()-1; j >=0; j--) {//判断是否有需要闪现的人物
				if (data.getPets().get(j).getPlay() != 0) {continue;}
				int p = data.getPets().get(j).getsx(10000);
				if (p == 0||p == 1) {continue;}
				ManData petData = data.getPets().get(j).getPet();
				battlefield.Addbb(petData, petData.getCamp(), petData.getMan());
				FightingState state = new FightingState();
				state.setStartState("召唤");
				state.setCamp(petData.getCamp());
				state.setMan(petData.getMan());
				petData.setStates(0);
				FightingManData fightingManData = new FightingManData();
				fightingManData.setModel(petData.getModel());
				fightingManData.setManname(petData.getManname());
				fightingManData.setCamp(petData.getCamp());
				fightingManData.setMan(petData.getMan());
				fightingManData.setHp_Current(petData.getHp());
				fightingManData.setHp_Total(petData.getHp_z());
				fightingManData.setMp_Current(petData.getMp());
				fightingManData.setMp_Total(petData.getMp_z());
				fightingManData.setState_1(petData.xz());
				fightingManData.setType(petData.getType());
				fightingManData.setManname(petData.getManname());
				fightingManData.setZs(petData.getZs());
				fightingManData.setMsg(petData.getMsg());
				fightingManData.setYqz(petData.getYqz());
				fightingManData.setNqz(petData.getNqz());
				fightingManData.setStates(petData.ztstlist(fightingManData));
				fightingManData.setId(petData.getId());
				if (petData.getSkillType("隐身") != null) {
					state.setEndState_1("隐身");
					AddState addState = new AddState();
					addState.setStatename("隐身");
					addState.setSurplus(3);
					petData.getAddStates().add(addState);
					fightingManData.setAlpha(0.3f);
				}
				state.setFightingManData(fightingManData);
				Accepterlist.add(state);
				MixDeal.Approach(petData,state,battlefield);
			}
		}
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);
	}

}
