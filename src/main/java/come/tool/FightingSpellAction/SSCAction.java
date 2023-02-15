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
import come.tool.FightingData.TypeUtil;

public class SSCAction implements SpellAction{


//	9384|迎霜隔雪|受到物理攻击时,有（2%*等级）几率打断对方的连击。(仅在与玩家之间战斗有效。)


//	9381|日月叠壁|使用吸星大法时,如果目标数不超过两个则有（4%*等级）几率造成双重伤害
//	9383|含沙射影|当日月叠璧造成双重伤害时,有（4%*等级）几率造成双重回复
//	9386|峰回路转|含沙射影双重回复时,第二次回复有（20%*等级）的几率回复到己方当前血量最低的单位身上。

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		String type=skill.getSkilltype();
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
		//
		int die      =0;//死亡人数
		double qssc  =0;//强三尸虫
		double qsschf=0;//强三尸回复
		FightingSkill skill2=null;
		FightingSkill skill3=null;
		FightingSkill skill4=null;
		FightingSkill skill5=null;
		FightingSkill skill6=null;
		FightingSkill skill7=null;
		skill7 =manData.skillId(22025);
//		9382|朝花夕拾|当己方单位血量都高于70%时,三尸虫伤害提高（500*（等级+1））,回血程度下降（4%*等级）
		skill2=manData.skillId(9382);
		if (skill2!=null) {
			for (int i=0;i<battlefield.fightingdata.size();i++) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getType()==3||data.getType()==4||data.getCamp()!=manData.getCamp()) {continue;}
				if (data.getType()==1&&data.getStates()!=0) {continue;}
				if (data.getvalue(0)<0.7) {
					skill2=null;
					break;
				}
			}
			if (skill2!=null) {
				qssc=skill2.getSkillhurt();
				qsschf=-skill2.getSkillhurt()/125D;	
			}
			skill2=null;
		}
		if (skill.getSkillid()==1070&&datas.size()<=2) {
			skill2=manData.skillId(9381);
			if (skill2!=null) {
				skill3=manData.skillId(9383);
				if (skill3!=null) {
					skill4=manData.skillId(9386);
				}
			}
//			9381|日月叠壁|使用吸星大法时,如果目标数不超过两个则有（4%*等级）几率造成双重伤害
//			9383|含沙射影|当日月叠璧造成双重伤害时,有（4%*等级）几率造成双重回复
//			9386|峰回路转|含沙射影双重回复时,第二次回复有（20%*等级）的几率回复到己方当前血量最低的单位身上。
		}
		//技能生效 扣除代价
		FightingState Originator=events.getOriginator();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(skill.getSkillname());
		List<Integer> hfl=new ArrayList<>();
		List<Integer> hfl2 = null;
		
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState Accepter=MixDeal.DSMY(manData,data,skill.getSkillid(),battlefield);
			if (Accepter!=null) {//法术闪避
				Accepterlist.add(Accepter);
			}else {
				data.addBear(type);
				Accepter=new FightingState();
				Accepter.setStartState(TypeUtil.JN);	
				ChangeFighting nomychange=new ChangeFighting();
				int sh=Calculation.getCalculation().sssh(manData,data,MixDeal.addition(Accepter, skill.getSkillhurt()+qssc,manData, type));
				nomychange.setChangehp(-sh);
				int Vampire=Calculation.getCalculation().sshx(manData,data,skill.getSkillgain()+qsschf, sh);
				hfl.add(Vampire);
				FightingPackage.ChangeProcess(nomychange, manData, data, Accepter, type,Accepterlist,battlefield);
				if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
					FightingState org=new FightingState();
					org.setStartState(TypeUtil.JN);
					nomychange.setChangehp(-sh);
					nomychange.setChangemp(0);	
					FightingPackage.ChangeProcess(nomychange, manData, data, org, type,Accepterlist,battlefield);
					if (skill3!=null&&Battlefield.isV(skill3.getSkillhurt())) {
						if (hfl2==null) {hfl2=new ArrayList<>();}
						hfl2.add(hfl.size()-1);	
					}
				}
			    if (data.getStates()!=0) {die++;}
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

		if (die>=3) {
		    skill5=manData.skillId(9385);
			if (skill5!=null) {skill6=manData.skillId(9388);}
		}
		//回复
		santiaochong(hfl, hfl2, manData,skill4,skill5,skill6,skill7,battlefield);
	}
	 /**3条虫将List<FightingEvents>里面的数据整合成FightingEvents*/
	 public static void santiaochong(List<Integer> hps,List<Integer> hps2,ManData myData,
			 FightingSkill skill4,FightingSkill skill5,FightingSkill skill6,FightingSkill skill7,Battlefield battlefield){
			if (hps.size()==0) return;
			int camp=myData.getCamp();
			FightingEvents fEvents=new FightingEvents();
			List<FightingState> Accepterlist=new ArrayList<>();
			List<ManData> datas=minhp(camp, hps.size(),battlefield);
			for (int i = 0; i < hps.size()&&i<datas.size(); i++) {
				FightingState fightingState=new FightingState();
				int hp=hps.get(i);
				ManData data=datas.get(i);
				AddState addState=data.xzstate("伤害加深");
				if (addState!=null) {
					hp=(int) (hp*(1-addState.getStateEffect()/100));
				}
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangehp(hp);
				if (skill5!=null&&Battlefield.isV(skill5.getSkillhurt())) {
					if (data.RemoveNegativeState(fightingState)) {
						if (skill6!=null) {
							changeFighting.setChangemp((int)skill6.getSkillhurt());
						}
					}	
				}
				//法门 血蛊佑身
				if(skill7!=null) {
					if (data.getvalue(0)<0.4) {
						if(hp> data.getHp()) {
							changeFighting.setChangetype("血蛊护盾");
							changeFighting.setChangesum(3);
							changeFighting.setChangevlaue(myData.getFmsld() *14);
						}


					}
				}

				data.ChangeData(changeFighting, fightingState);
				fightingState.setStartState("药");
				Accepterlist.add(fightingState);	
			}
			if (hps2!=null) {
				for (int i = 0; i < hps2.size(); i++) {
					int p=hps2.get(i);
					if (p>=hps.size()||p>=datas.size()) {continue;}
					FightingState fightingState=new FightingState();
					int hp=hps.get(p);
					ManData data=datas.get(p);
					if (skill4!=null&&Battlefield.isV(skill4.getSkillhurt())) {
					     List<ManData> datas2=minhp(camp, 1, battlefield);
					     if (datas2.size()!=0) {data=datas2.get(0);}
					}
					AddState addState=data.xzstate("伤害加深");
					if (addState!=null) {hp=(int) (hp*(1-addState.getStateEffect()/100));}
					ChangeFighting changeFighting=new ChangeFighting();
					changeFighting.setChangehp(hp);
				    data.ChangeData(changeFighting, fightingState);
					fightingState.setStartState("药");
					Accepterlist.add(fightingState);	
				}
			}
			if (Accepterlist.size()!=0) {
				fEvents.setAccepterlist(Accepterlist);
				battlefield.NewEvents.add(fEvents);	
			}	   
		}
		/**找出相同阵营且血量最低   同血量比速度*/
		public static List<ManData> minhp(int camp,int sum,Battlefield battlefield){
			List<ManData> datas=new ArrayList<>();
			for (int i = 0; i < battlefield.fightingdata.size(); i++) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getCamp()!=camp) continue;
				if (data.getType()==3||data.getType()==4) continue;
				if (data.getType()==1&&data.getStates()!=0) continue;
				if (data.xzstate("归墟")!=null) continue;
				datas.add(data);
			}
			if (datas.size()<=sum)return datas;
			boolean a=false;
			for (int i = 0; i < datas.size(); i++) {
   			for (int j = 1; j < datas.size(); j++) {
   				ManData data1=datas.get(j-1);
   				double value1=data1.getvalue(1);
   				ManData data2=datas.get(j);
   				double value2=data2.getvalue(1);
   				if ((a&&value1<value2)||(!a&&value1>value2)) {
   			        datas.set((j - 1), data2);  
   					datas.set(j, data1);
   				}
   			}
   		}
			return datas;	
	   }
}
