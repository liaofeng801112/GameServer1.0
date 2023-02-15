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

public class DieGroup implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//判断召唤兽是否存在且不处于封印状态
		FightingSkill skill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (skill==null){return;}
		ManData bb=battlefield.getSeek(manData,1);
		if (bb==null||bb.getStates()!=0||bb.xzstate(TypeUtil.FY)!=null) {
			return;
		}
		int hurt=manData.getHp_z()+bb.getHp_z();
		hurt=(int) (hurt*skill.getSkillhurt()/100D);
		List<ManData> datas=MixDeal.get(false, null, 0, manData.getCamp(), 0, 0, 0, 0, 10, -1, battlefield, 1);
		fightingEvents.setAccepterlist(null);fightingEvents.setOriginator(null);
		List<FightingState> acclist=new ArrayList<>();
		FightingState Fman=new FightingState();
		ChangeFighting fighting=new ChangeFighting();
		fighting.setChangehp(-manData.getHp_z());
		manData.ChangeData(fighting, Fman);
		acclist.add(Fman);
		FightingState Fbb=new FightingState();
		fighting.setChangehp(-bb.getHp_z());
		bb.ChangeData(fighting, Fbb);
		acclist.add(Fbb);
		for (int i = 0; i < datas.size(); i++) {
			fighting.setChangehp(-hurt);
			ManData data=datas.get(i);
			FightingState Fdata=new FightingState();
			FightingPackage.ChangeProcess(fighting, null,data,Fdata,skill.getSkilltype(),acclist,battlefield);
		}
		fightingEvents.setAccepterlist(acclist);
		battlefield.NewEvents.add(fightingEvents);	
	}
}
