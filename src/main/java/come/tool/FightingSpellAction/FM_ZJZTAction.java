package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;
import come.tool.FightingDataAction.PhyAttack;
import come.tool.FightingData.SummonType;

public class FM_ZJZTAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//"凝神一击",利刃加身,无坚不摧 神不守舍 幻影迷踪 兽魂俯首 法魂护体  鱼龙潜跃 失魂落魄 刚柔兼备
		int mubiaosum = 0;
		int huihesum = 0;
		int skillid = skill.getSkillid();
		int fmsld = manData.getFmsld();
		if (skillid == 22002) { //利刃加身
			mubiaosum = 3+(int)Math.floor(fmsld/2600);
			huihesum = 2+(int)Math.floor(fmsld/3400);
		}else if (skillid == 22004) { //神不守舍
			mubiaosum = 1;
			huihesum = 3;
		}else if (skillid == 22010) {//无坚不摧
			mubiaosum = 4+(int)Math.floor(fmsld/3400);
			huihesum = 2+(int)Math.floor(fmsld/2600);
		}else if (skillid == 22008) {//凝神一击
			mubiaosum = 1+(int)Math.floor(fmsld/2600);
			huihesum = 1+(int)Math.floor(fmsld/2600);
		}else if (skillid == 22014) {//幻影迷踪
			mubiaosum = 4+(int)Math.floor(fmsld/3400);
			huihesum = 2+(int)Math.floor(fmsld/2600);
		}else if (skillid == 22018) {//兽魂俯首
			mubiaosum = 10;
			huihesum = 2+(int)Math.floor(fmsld/2600);
		}else if (skillid == 22024) {//法魂护体
			mubiaosum = 3+(int)Math.floor(fmsld/2600);
			huihesum = 3;
		}else if (skillid == 22028) {//失魂落魄
			mubiaosum = 2+(int)Math.floor(fmsld/3400);
			huihesum = 4;
		}else if (skillid == 22030) {//鱼龙潜跃
			mubiaosum = 3+(int)Math.floor(fmsld/3400);
			huihesum = 3;
		}else if (skillid == 22020) {//刚柔兼备
			mubiaosum = 4+(int)Math.floor(fmsld/2600);
			huihesum = 2+(int)Math.floor(fmsld/2600);
		}



		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield,mubiaosum);
		//	List<FightingSkill> skills=getSkills(manData,skill,battlefield.BattleType);
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		//if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		SummonType.xianzhi(manData, skill);
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		String type=skill.getSkilltype();

		for (int j = 0; j < datas.size(); j++) {
			ManData data=datas.get(j);

			FightingState Accepter=new FightingState();
			ChangeFighting changeFighting=new ChangeFighting();

			changeFighting.setChangetype(type);
			changeFighting.setChangesum(huihesum);//skill.getSkillcontinued()

			if(skillid == 22002) {
				if (manData.getFmsld()>0 ) {
					data.setDffmsld(manData.getFmsld());
					data.setNrlrjs((int)(manData.getFmsld()*22.85));
					//changeFighting.setChangevlaue(manData.getFmsld());
				}
			}
			if(skillid == 22003) {
				if (manData.getFmsld()>0 ) {
					data.setDffmsld(manData.getFmsld());
					data.setNrjycz((int)(manData.getFmsld()*8.53));

				}
			}
			if(skillid == 22004) {
				if (manData.getFmsld()>0 ) {
					data.setDffmsld(manData.getFmsld());
				}
			}
			if(skillid == 22010) {
				if (manData.getFmsld()>0 ) {
					data.setFmwjbc(manData.getFmsld());
				}
			}
			if (skillid == 22030) {
				changeFighting.setChangevlaue(manData.getFmsld()*16.07);
			}
			if (skillid == 22020) {
				changeFighting.setChangevlaue(manData.getFmsld());
			}

			if (skillid == 22018) {
				if (manData.getFmsld()>0 ) {
					data.setFmshfs(manData.getFmsld());
					//data.fmshfssx();
				}
				if (data.getType()!=1) {
					changeFighting.setChangetype(null);
					changeFighting.setChangesum(0);
				}
			}

			//	changeFighting.setChangevlaue(manData.getFmsld());
//					if (data.getStates()==1) { 目标死亡
//						sw++;
//						if (tycSkill!=null&&tycSkill.getSkillid()==9511) {
//							int add=(int) (data.huoAp()*tycSkill.getSkillhurt()/100D);
//							if (add>2000) {add=2000;}
//							tycSkill.setSkillgain(tycSkill.getSkillgain()+add);
//							if (tycSkill.getSkillgain()<60000) {
//								manData.setAp(manData.huoAp()+add);
//							}
//						}
//
//					}

			FightingPackage.ChangeProcess(changeFighting,manData,data,Accepter,type,Accepterlist,battlefield);
			if(skillid == 22018) {
				data.fmshfssx();

				if (data.getHuoyue()>data.getShanghai()) {
					if(data.getHuoyue()>data.getKangluobao()) {
						if(data.getHuoyue()>data.getYuanzhu()) {
							Accepter.setUp("HP", data.getHp_z());
						}else {

						}
					}else if(data.getKangluobao()>data.getYuanzhu()) {

					}else {

					}
				}else if(data.getShanghai()>data.getKangluobao()) {
					if(data.getShanghai()>data.getYuanzhu()) {
						Accepter.setUp("MP", data.getMp_z());
					}else {

					}
				}else if(data.getKangluobao()>data.getYuanzhu()) {

				}else {

				}
				Accepterlist.add(Accepter);
			}
		}



		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);
		}
		santiaochong(skillid,manData,mubiaosum,battlefield,type);
	}


	public static void santiaochong(int skillid,ManData manData,
									int mubiaosum,Battlefield battlefield,String type){
		int camp=manData.getCamp();
		FightingEvents fEvents=new FightingEvents();
		List<FightingState> Accepterlist=new ArrayList<>();
		FightingState Accepter=new FightingState();
		if(skillid == 22024&&manData.getFmsld()>0) {
			List<ManData> datas1=SSCAction.minhp(camp, mubiaosum,battlefield);
			for (int i = 0; i < mubiaosum &&i<datas1.size(); i++) {
				ManData data=datas1.get(i);
				int hp = manData.getFmsld() * 22;
				FightingState fightingState=new FightingState();
				ChangeFighting changeFightinghf=new ChangeFighting();
				changeFightinghf.setChangehp(hp);
				data.ChangeData(changeFightinghf, fightingState);
				fightingState.setStartState("药");
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangetype("法魂护体");
				changeFighting.setChangevlaue(manData.getFmsld() *14);
				changeFighting.setChangesum(3);
				FightingPackage.ChangeProcess(changeFighting,manData,data,Accepter,type,Accepterlist,battlefield);
				Accepterlist.add(fightingState);
			}
		}
		if (Accepterlist.size()!=0) {
			fEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(fEvents);
		}
	}
}