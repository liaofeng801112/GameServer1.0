package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class MoJindan implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill fightingSkill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (fightingSkill==null) return;
		String skilltype=fightingSkill.getSkilltype();
		if (!skilltype.equals("魔界内丹"))return;
		List<ManData> datas=MixDeal.getjieshou(fightingEvents, fightingSkill, manData,null,battlefield); 
		if (datas.size()==0) return;	
		
	    //分光化影	此技能通过牺牲自己的HP给对手造成MP伤害。
	    //天魔解体	此技能通过牺牲自己的HP给对手造成HP伤害。
	    //小楼夜哭	此技能通过牺牲自己的MP给对手造成MP伤害。
	    //青面獠牙	此技能通过牺牲自己的MP给对手造成HP伤害。
		List<FightingState> list=new ArrayList<>();
		int typeInt=fightingSkill.getSkillname().equals("分光化影")?0:fightingSkill.getSkillname().equals("天魔解体")?1:fightingSkill.getSkillname().equals("小楼夜哭")?2:3;
		int hurtBase=0;
		FightingState mys=fightingEvents.getOriginator();
		ChangeFighting my=new ChangeFighting();
		if (typeInt==0||typeInt==1) {
			hurtBase=(int)(manData.getHp()*0.9);
			my.setChangehp(-hurtBase);
		}else {
			hurtBase=(int)(manData.getMp()*0.9);
			my.setChangemp(-hurtBase);
		}
		manData.ChangeData(my, mys);
		mys.setStartState("法术攻击");
		list.add(mys);
		
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			double xs=fightingSkill.getSkillhurt();
			AddState addState=data.xzstate(TypeUtil.MH);
			if (addState!=null) {
				xs-=addState.getStateEffect2();
				if (xs<=0) {xs=0.1;}
			}
			int hurt=(int)(hurtBase*xs/100);
			hurt-=typeInt==0?data.getQuality().getK_ndfg():typeInt==1?data.getQuality().getK_ndtm():
				  typeInt==2?data.getQuality().getK_ndxl():data.getQuality().getK_ndqm();
			if (hurt<=0) {hurt=1;}
			ChangeFighting nomy=new ChangeFighting();
			if (typeInt==0||typeInt==2) {
				nomy.setChangemp(-hurt);
			}else {
				nomy.setChangehp(-hurt);
			}
			FightingState nomys=new FightingState();
			nomys.setStartState("被攻击");
			nomys.setSkillskin(fightingSkill.getSkillname());
			FightingPackage.ChangeProcess(nomy,null,data,nomys,fightingSkill.getSkilltype(),list,battlefield);
		}
		fightingEvents.setOriginator(null);
		fightingEvents.setAccepterlist(list);
		battlefield.NewEvents.add(fightingEvents);
	} 

}
