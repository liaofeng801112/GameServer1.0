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
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;

public class PXKG implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		List<ManData> nomyDatas=MixDeal.getdaji(10, !type.equals("混沌技")?manData.getCamp():battlefield.nomy(manData.getCamp()), fightingEvents, battlefield);
		nomyDatas.remove(manData);
		if (nomyDatas.size()==0) {
			return;
		}
		double mzjc=0,ljjc=0,dsjc=0,dsl = 0;//命中加成,连击加成,躲闪加成,躲闪率
		//判断是否触发孩子技能
		FightingSkill skill2=manData.getAppendSkill(9203);
		if (skill2!=null) {mzjc+=skill2.getSkillhurt();skill2=null;}
		GroupBuff buff=battlefield.getBuff(manData.getMan(), TypeUtil.YBYT);
		if (buff!=null) {mzjc+=buff.getValue();}
		buff=battlefield.getBuff(manData.getMan(), TypeUtil.BB_E_HYMB);
		if (buff!=null) {dsjc+=buff.getValue();}
		AddState addState=manData.xzstate(TypeUtil.L_CB);
		if (addState!=null) {mzjc-=addState.getStateEffect();}
		FightingSkill skill5=manData.getAppendSkill(9347);
		if (skill5!=null) {
			mzjc-=skill5.getSkillhurt();
			ljjc-=skill5.getSkillhurt();
		}
		skill5=manData.getAppendSkill(9811);
		if (skill5!=null) {ljjc-=skill5.getSkillhurt();}
		int maxg=PhyAttack.GMax(manData, nomyDatas.get(0), ljjc, battlefield);
		long Zap=manData.getAp();
		for (int i = 0; i < maxg; i++) {
			if (manData.getStates()!=0) {
				break;
			}
			FightingEvents gjEvents=new FightingEvents();
			List<FightingState> zls=new ArrayList<>();
			FightingState gjz=new FightingState();
			gjz.setCamp(manData.getCamp());
			gjz.setMan(manData.getMan());
			gjz.setSkillsy("attack");
			gjz.setStartState(TypeUtil.PTGJ);
			gjz.setEndState("3");
			zls.add(gjz);
			for (int j=nomyDatas.size()-1;j>=0;j--) {
				ManData data=nomyDatas.get(j);
				if (data.getStates()!=0) {
					nomyDatas.remove(j);
					continue;
				}
				FightingState ace=new FightingState();
				ace.setCamp(data.getCamp());
				ace.setMan(data.getMan());
				zls.add(ace);
				if (!Battlefield.isV(dsl+dsjc-manData.getQuality().getRolefmzl()-mzjc-manData.mz)) {
					ChangeFighting acec=new ChangeFighting();
					long ap=PhyAttack.Hurt(Zap,i+1,manData, data, type, ace,zls, battlefield,0,0);
					acec.setChangehp((int)-ap);
					FightingPackage.ChangeProcess(acec, manData, data, ace, TypeUtil.PTGJ, zls, battlefield);
					PhyAttack.neidan(type, manData, data, ap, battlefield, zls,i+1,0,0);
				}else {//躲闪成功
					ace.setStartState("技能");
					ace.setProcessState("躲闪");
				}
			}
			gjEvents.setAccepterlist(zls);
			battlefield.NewEvents.add(gjEvents);
		}
	}

}
