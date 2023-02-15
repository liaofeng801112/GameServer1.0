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
import come.tool.FightingData.SummonType;
import come.tool.FightingData.TypeUtil;

public class Spell implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill fightingSkill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (fightingSkill==null) return;
		String skilltype=fightingSkill.getSkilltype();	
		if (skilltype.equals("魔界内丹")) {
			//魔界内丹额外处理
			DataActionType.getActionById(12).analysisAction(manData, fightingEvents, skilltype,battlefield);
			return;
		}
		if (skilltype.equals("兵临城下")||skilltype.equals("奋蹄扬威")) {
			//自残技额外处理
			DataActionType.getActionById(16).analysisAction(manData, fightingEvents,skilltype,battlefield);
			return;
		}

		if (skilltype.equals("神力加身")||skilltype.equals("力挽狂澜")||skilltype.equals("势如破竹")) {
			DataActionType.getActionById(37).analysisAction(manData, fightingEvents,skilltype,battlefield);
			return;
		}

		if (skilltype.equals("移花接木")) {
			//移花接木额外处理
			DataActionType.getActionById(18).analysisAction(manData, fightingEvents,skilltype,battlefield);
			return;
		}
		if (skilltype.equals(TypeUtil.TZ_TGYJ)) {//同归于尽
			DataActionType.getActionById(26).analysisAction(manData, fightingEvents,skilltype,battlefield);
			return;
		}
		if (skilltype.equals(TypeUtil.TZ_YSTR)) {//一视同仁
			DataActionType.getActionById(27).analysisAction(manData, fightingEvents,skilltype,battlefield);
			return;
		}
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(fightingEvents,fightingSkill,manData,Accepterlist,battlefield); 
		if (datas.size()==0){
			FightingState Originator=fightingEvents.getOriginator();
			if (manData.daijia(fightingSkill,Originator,battlefield)) {return;}//扣除代价
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(fightingSkill.getSkillname());
			fightingEvents.setOriginator(null);
			Accepterlist.add(Originator);
			fightingEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(fightingEvents);	
			return;		
		}	
	    FightingSkill bb_e=null;
	    if (skilltype.equals("减速")) {
	    	bb_e=manData.getSkillType(TypeUtil.BB_E_QHJianS);
		}else if (skilltype.equals("庇护")) {
			bb_e=manData.getSkillType(TypeUtil.BB_E_QHZBTX);
		}
		//技能生效 扣除代价
		FightingState Originator=fightingEvents.getOriginator();
		if (bb_e!=null) {Originator.setText(bb_e.getSkillname()+"#2");}
		int state=manData.getStates();
		if (manData.daijia(fightingSkill,Originator,battlefield)) {return;}//扣除代价
		if (state==0&&state!=manData.getStates()) {state=1;}
		else {state=0;}
		S:if (fightingSkill.getSkillid()==1607) {
			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i).getCamp()==manData.getCamp()&&manData.getMan()==datas.get(i).getMan()) {
					break S;
				}
			}
			while (datas.size()>1)datas.remove(1);
			datas.add(manData);
		}
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(fightingSkill.getSkillname());
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState Accepter=new FightingState();
			if (skilltype.equals("减人仙")||skilltype.equals("减魔鬼")||skilltype.equals(TypeUtil.BB_SS)
					||skilltype.equals(TypeUtil.BB_E_YHSS)||skilltype.equals(TypeUtil.BB_DHSM)) {
				getBBNoHurt(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, 
						manData, data, fightingSkill, skilltype,battlefield);
			}else if (skilltype.equals("减速")||skilltype.equals("庇护")||
					skilltype.equals(TypeUtil.TZ_CXYF)||skilltype.equals(TypeUtil.TZ_FHJY)||
					skilltype.equals(TypeUtil.TZ_HGFZ)||skilltype.equals(TypeUtil.TZ_HJBF)||
					skilltype.equals(TypeUtil.TZ_XSJS)||skilltype.equals(TypeUtil.TZ_YSHY)||skilltype.equals("23004")) {//增益判断
				getmostate(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, 
						manData, data, fightingSkill, skilltype,battlefield,bb_e);
			}else if (skilltype.equals("五行")||skilltype.equals("隐身技")||skilltype.equals("解除控制")) {
            	SummonType.Fiveelements(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, 
            			manData, data, fightingSkill, skilltype,battlefield);
            }else if (skilltype.equals("回血技")) {
            	SummonType.Disposable(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter,
            			manData, data, fightingSkill, skilltype,battlefield);
            }else if (skilltype.equals(TypeUtil.TZ_BDBQ)||skilltype.equals(TypeUtil.TZ_MXJX)) {//增益判断 
            	getTZState(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield);
    		}else if (skilltype.equals(TypeUtil.TZ_HJYL)||skilltype.equals(TypeUtil.TZ_JLZT)) {//增益判断 
    			getTZ1(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield);
    		}else if (skilltype.equals(TypeUtil.TZ_YYJH)) {
    			getTZ2(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield);
        	}else if (skilltype.equals(TypeUtil.TZ_PFCZ)) {
    			getTZ3(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield);
        	}else if (skilltype.equals(TypeUtil.TJ_LJXS)) {
        		getTJ1(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield); 		
			}else if (skilltype.equals(TypeUtil.BB_CNHK)) {
				BB_CNHK(Accepterlist,data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()?Originator:Accepter, manData, data, fightingSkill, skilltype,battlefield); 		
			}
			Accepter.setStartState(TypeUtil.JN);	
			if (data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()) {
				Originator.setStartState("法术攻击");
				fightingEvents.setOriginator(null);
				if (fightingSkill.getSkillid()!=1607&&fightingSkill.getSkillid()!=1877) {
					Originator.setSkillskin(fightingSkill.getSkillid()+"");	
				}	
			}else {
				if (fightingSkill.getSkillid()!=1607&&fightingSkill.getSkillid()!=1877) {
					Accepter.setSkillskin(fightingSkill.getSkillid()+"");	
				}
			}
		
		}
		if (fightingEvents.getOriginator()!=null) {
			Accepterlist.add(Originator);
		}
		fightingEvents.setOriginator(null);
		if (Accepterlist.size()!=0) {
			fightingEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(fightingEvents);	
		}
		if (state==1) {
			//先判断是否能复活
			MixDeal.DeathSkill(manData,Originator,battlefield);
		}
	}
	/**减人仙 减魔鬼 舍生取义*/
	public static void getBBNoHurt(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		nomyData.addBear(skilltype);	
		ChangeFighting changeFighting=battlefield.BBNoHurt(manData, nomyData,skilltype,fightingSkill);
		FightingPackage.ChangeProcess(changeFighting, null, nomyData, fightingState,skilltype, Accepterlist, battlefield);	
	}
	/**魔状态*/
	public static void getmostate(List<FightingState> Accepterlist,FightingState fightingState,
		ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,
		Battlefield battlefield,FightingSkill bb_e){
		ChangeFighting changeFighting=battlefield.TypeGain(manData,skilltype,fightingSkill);
		if (bb_e!=null) {
			if (bb_e.getSkilltype().equals(TypeUtil.BB_E_QHZBTX)) {
				changeFighting.setChangevlaue2(changeFighting.getChangevlaue2()+bb_e.getSkillgain());
				changeFighting.setSkill(bb_e);
			}else {
				changeFighting.setChangevlaue(changeFighting.getChangevlaue()+bb_e.getSkillgain());
			}
		}
		nomyData.ChangeData(changeFighting, fightingState);	
		Accepterlist.add(fightingState);
	}
	/**套装状态 百毒 明心*/
	public static void getTZState(List<FightingState> Accepterlist,FightingState fightingState,
			ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		    ChangeFighting changeFighting=new ChangeFighting();
		    changeFighting.setChangetype(skilltype);	
		    if (fightingSkill.getSkillhurt()>Battlefield.random.nextInt(100)) {
		    	changeFighting.setChangesum(fightingSkill.getSkillcontinued()+1);
			}else {
				changeFighting.setChangesum(fightingSkill.getSkillcontinued());
			}
		    nomyData.ChangeData(changeFighting, fightingState);	
			Accepterlist.add(fightingState);
	}
	/**套装伤害 鸿渐于陆 见龙在田*/
	public static void getTZ1(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		ChangeFighting changeFighting=new ChangeFighting();
		if (skilltype.equals(TypeUtil.TZ_HJYL)) {//根据目标灵性造成法力伤害，同时造成法力伤害*60%的气血伤害
			int hurt=(int) -(nomyData.getShanghai()*(fightingSkill.getSkillhitrate()+nomyData.getShanghai()*fightingSkill.getSkillhurt())/2);;
			changeFighting.setChangemp(hurt);
			changeFighting.setChangehp((int)(changeFighting.getChangemp()*0.6));
		}else {//根据目标根骨造成气血伤害，同时造成气血伤害*60%的法力伤害
			int hurt=(int) -(nomyData.getHuoyue()*(fightingSkill.getSkillhitrate()+nomyData.getHuoyue()*fightingSkill.getSkillhurt())/2);;
			changeFighting.setChangehp(hurt);
			changeFighting.setChangemp((int)(changeFighting.getChangehp()*0.6));		
		}
		FightingPackage.ChangeProcess(changeFighting, manData,nomyData,fightingState,skilltype,Accepterlist,battlefield);
	}
	/**因缘际会*/
	public static void getTZ2(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangehp((int)fightingSkill.getSkillhurt());
		changeFighting.setChangemp((int)fightingSkill.getSkillhurt());
		nomyData.ChangeData(changeFighting, fightingState);
		Accepterlist.add(fightingState);
		List<ManData> datas=battlefield.getZW(nomyData);
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState fdata=new FightingState();
			data.ChangeData(changeFighting, fdata);
			fdata.setStartState("药");
			Accepterlist.add(fdata);
		}
	}
	/**破釜沉舟*/
	public static void getTZ3(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		ChangeFighting changeFighting=new ChangeFighting();
		int hurt=(int)(0.8*nomyData.getHp());
		changeFighting.setChangehp(-hurt);
		changeFighting.setChangetype(skilltype);
		changeFighting.setChangevlaue(hurt*fightingSkill.getSkillhurt()/100);
		changeFighting.setChangesum(fightingSkill.getSkillcontinued());
		nomyData.ChangeData(changeFighting, fightingState);
	}
	/**落井下石*/
    public static void getTJ1(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
    	fightingState.setCamp(nomyData.getCamp());
    	fightingState.setMan(nomyData.getMan());		
    	nomyData.addyq(-300,fightingState);	
    	Accepterlist.add(fightingState);
    }
    /**春暖花开*/
    public static void BB_CNHK(List<FightingState> Accepterlist,FightingState fightingState,ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
    	fightingState.setCamp(nomyData.getCamp());
    	fightingState.setMan(nomyData.getMan());	
    	nomyData.RemoveAbnormal("冷却");
    	Accepterlist.add(fightingState);
    }
}
