package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.Calculation;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

public class ZSAction implements SpellAction{
	
	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill skill1=null;//9181|射雕
		FightingSkill skill2=null;//9182|展眉
		FightingSkill skill3=null;//9184|悲怀
		FightingSkill skill4=null;//9187|仁心
		FightingSkill skill5=null;//9183|吞象
		FightingSkill skill6=null;//9186|伏虎
		FightingSkill skill7=null;//9188|固元
	    if (PK_MixDeal.isPK(battlefield.BattleType)) {
	    	skill1=manData.getSkillType(TypeUtil.TY_ZS_SD);
			skill2=manData.getSkillType(TypeUtil.TY_ZS_ZM);
		    if (skill2!=null&&!Battlefield.isV(skill2.getSkillhurt())) {skill2=null;}	
		    if (skill2!=null) {
		    	skill3=manData.getSkillType(TypeUtil.TY_ZS_BH);
			    if (skill3!=null&&!Battlefield.isV(skill3.getSkillhurt())) {skill3=null;}	
			    if (skill3!=null) {
				    skill4=manData.getSkillType(TypeUtil.TY_ZS_RX);		
				    if (skill4!=null&&!Battlefield.isV(skill4.getSkillhurt())) {skill4=null;}	
				}	
			}
		    if (skill.getSkillid()==1024) {
		        skill5=manData.getSkillType(TypeUtil.TY_ZS_TX);		
		    }
		    skill6=manData.getSkillType(TypeUtil.TY_ZS_FH);		
	    }
	    double qzsjc=0D;
	    double upMin=0D; //伤害下限
	    double upMax=50D;//伤害上限
	    double MPXs=skill.getSkillhurt()+8;//mp伤害
	    double MPHurt=0;//最大mp伤害
	    skill7=manData.getSkillType(TypeUtil.TY_ZS_FY2);	
	    if (skill7!=null) {
	    	qzsjc-=skill7.getSkillhurt();
			upMax=50+skill7.getSkillhurt();
		}
	    if (skill.getSkillid()==1025) {
	    	 AddState addState=manData.xzstate(TypeUtil.TY_ZS_YL);	
	    	 if (addState!=null) {
	    		 upMin=addState.getStateEffect();
	    		 upMax=addState.getStateEffect2();
			}
	    	addState=manData.xzstate(TypeUtil.TY_ZS_FY1);	
	    	if (addState!=null) {
	    		MPXs=addState.getStateEffect();
			} 
		}
	    if (upMin>upMax) {upMin=upMax;}
	    if (upMax>55) {upMax=55;}
	    if (!PK_MixDeal.isPK(battlefield.BattleType)) {
	    	upMax-=15;
		}
	    skill7=manData.getSkillType(TypeUtil.TY_ZS_GY);		
	    if (skill7!=null&&!Battlefield.isV(skill7.getSkillhurt())) {skill7=null;}	
	    
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events,skill,manData,Accepterlist,battlefield); 
		if (datas.size()==0){
			FightingState Originator=events.getOriginator();
			if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skill.getSkillname());
			events.setOriginator(null);
			Accepterlist.add(Originator);
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
			return;		
		}	
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState Accepter=null;
			if (skill1==null||!Battlefield.isV(skill1.getSkillhurt())) {
				Accepter=MixDeal.DSMY(manData,data,skill.getSkillid(),battlefield);		
			}
			if (Accepter==null) {
				data.addBear(TypeUtil.ZS);
				if (skill6!=null) {
					data.addAddState(skill6.getSkilltype(),skill6.getSkillhurt(),0,0);
				}
				//血量变化 蓝量变化 变化后的状态(0:活,1:死) 持续回合的类型   持续回合数
				ChangeFighting changeFighting=new ChangeFighting();
		        //接收人的当前的生命
				int hp=data.getHp();
		        int mp=data.getMp();
		        double hpjichu=skill.getSkillhurt();
		        double mpjichu=MPXs;
		        if (skill5!=null&&data.getType()==1) {hpjichu-=skill5.getSkillhurt();}
		        hpjichu=Calculation.getCalculation().mozs2(manData, data,hpjichu,qzsjc);
		        if (hpjichu<upMin) {hpjichu=upMin;}
		        else if (hpjichu>upMax) {hpjichu=upMax;}
		        hpjichu-=data.getQuality().getK_zshp();
		        hpjichu=hpjichu>0?hpjichu:0;
		        mpjichu-=data.getQuality().getK_zsmp();
                FightingSkill skill8=data.getAppendSkill(9404);
                if (skill8!=null) {mpjichu-=skill8.getSkillhurt();}
		        mpjichu=mpjichu>0?mpjichu:0;
				int   Hurt=(int)(hpjichu*hp/100);
				int MpHurt=(int)(mpjichu*mp/100);
				if (MpHurt>MPHurt) {MPHurt=MpHurt;}
				if (Hurt<=0) {Hurt=1;}
				changeFighting.setChangehp(-Hurt);	
				changeFighting.setChangemp(-MpHurt);
		        Accepter=new FightingState();
		        FightingPackage.ChangeProcess(changeFighting,manData, data, Accepter,TypeUtil.ZS, Accepterlist, battlefield);		
			}else {
				Accepterlist.add(Accepter);
			}
			Accepter.setSkillskin(skill.getSkillid()+"");	
		}
		if (events.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		events.setOriginator(null);
		if (Accepterlist.size()!=0) {
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
		}
		if (skill7!=null) {
			datas=MixDeal.get(true,manData,0,battlefield.nomy(manData.getCamp()),0,0,0,0,1,3,battlefield,1); 	
			if (datas.size()!=0) {
				ManData data=datas.get(0);
				FightingEvents events2=new FightingEvents();
				List<FightingState> fightingStates=new ArrayList<>();
				FightingState fightingState=new FightingState();
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangemp((int)skill7.getSkillhurt()*500);
				data.ChangeData(changeFighting, fightingState);
				fightingState.setStartState("药");
				fightingStates.add(fightingState);
				events2.setAccepterlist(fightingStates);
				battlefield.NewEvents.add(events2);	
			}		
		}
		if (skill2!=null) {
			MPXs=MPXs*(skill2.getSkillhurt()/2+10)/100.0;
			ManData data=manData;
			if (skill3!=null) {
				datas=MixDeal.get(true,manData,0,battlefield.nomy(manData.getCamp()),0,0,0,0,1,3,battlefield,1); 	
				if (datas.size()!=0) {
					data=datas.get(0);
				}
			}
			FightingEvents events2=new FightingEvents();
			List<FightingState> fightingStates=new ArrayList<>();
			FightingState fightingState=new FightingState();
			ChangeFighting changeFighting=new ChangeFighting();
			changeFighting.setChangemp((int)MPHurt);
			if (skill4!=null) {
				changeFighting.setChangehp(changeFighting.getChangemp());
			}
			data.ChangeData(changeFighting, fightingState);
			fightingState.setStartState("药");
			fightingStates.add(fightingState);
			events2.setAccepterlist(fightingStates);
			battlefield.NewEvents.add(events2);	
		}
	}
	/**平A附加震慑*/
	public static ChangeFighting TypeHurtCurrent(ManData myData,ManData nomyData,double jichu){
		//血量变化 蓝量变化 变化后的状态(0:活,1:死) 持续回合的类型   持续回合数
		ChangeFighting changeFighting=new ChangeFighting();
        //接收人的当前的生命
		int hp=nomyData.getHp();
        int mp=nomyData.getMp();
        double hpjichu=Calculation.getCalculation().mozs(myData, nomyData, jichu);
        hpjichu-=nomyData.getQuality().getK_zshp();
        hpjichu=hpjichu>0?hpjichu:0;
        jichu-=nomyData.getQuality().getK_zsmp();
        jichu=jichu>0?jichu:0;
		int Hurt=  (int) (hpjichu*hp/100);
		int MpHurt=(int) (  jichu*mp/100);
		if (Hurt<=0) {
			Hurt=1;
		}
		changeFighting.setChangehp(-Hurt);	
		changeFighting.setChangemp(-MpHurt);
        return changeFighting;
	}
}
