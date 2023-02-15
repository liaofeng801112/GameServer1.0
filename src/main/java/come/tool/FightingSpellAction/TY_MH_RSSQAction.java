package come.tool.FightingSpellAction;

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
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class TY_MH_RSSQAction implements SpellAction{

	@Override
	public void spellAction(ManData manData,FightingSkill skill,FightingEvents events,Battlefield battlefield) {
		// TODO Auto-generated method stub
//		9412|弱水三千   |为己方一个单位回复（50%+10%*等级）气血并添加一个持续2回合的护盾,护盾存在期间会吸收单位受到的所有伤害并储存最多储存目标最大血法之和（70%+10%*等级）的伤害,目标下次释放伤害性师门法术时会附加储存的伤害,若有多个目标则伤害均摊。(仅在与家之间战斗有效)
		if (!PK_MixDeal.isPK(battlefield.BattleType)) {return;}
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield); 		
		FightingState Originator=events.getOriginator();
		events.setOriginator(null);
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		if (datas.size()!=0) {
			ManData data=datas.get(0);
			if (Battlefield.isV(skill.getSkillhurt())) {
				FightingState fightingState=new FightingState();
				ChangeFighting fighting=new ChangeFighting();
				fighting.setChangehp((int)(data.getHp_z()*skill.getSkillhurt()/100.0));
				fighting.setChangetype(TypeUtil.TY_MH_RSSQ);
				fighting.setChangevlaue2((int)((data.getHp_z()+data.getMp_z())*(skill.getSkillhurt()+20)/100.0));
				fighting.setChangesum(2);
				FightingPackage.ChangeProcess(fighting, null, data, fightingState, TypeUtil.HS, Accepterlist, battlefield);			
			}	
		}
		events.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(events);	

	}
}
