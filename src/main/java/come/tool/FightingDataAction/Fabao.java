package come.tool.FightingDataAction;

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

public class Fabao implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill fightingSkill=manData.skillname(fightingEvents.getOriginator().getEndState());
		if (fightingSkill==null) return;
		String skilltype=fightingSkill.getSkilltype();
		if (skilltype.equals(TypeUtil.FB_HD)) {//化蝶
			//化蝶额外处理
			DataActionType.getActionById(23).analysisAction(manData, fightingEvents,type,battlefield);
			return;
        }
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(fightingEvents, fightingSkill, manData,Accepterlist,battlefield); 
		//技能生效 扣除代价
		FightingState Originator=fightingEvents.getOriginator();
		if (manData.daijia(fightingSkill,Originator,battlefield)) {
			return;
		}
		Originator.setStartState("法术攻击");
		Originator.setSkillsy(fightingSkill.getSkillname());
		for (int i = 0; i < datas.size(); i++) {
			ManData data=datas.get(i);
			FightingState Accepter=new FightingState();
			if (data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()) {
				Accepter=Originator;
			}
			Accepter.setCamp(data.getCamp());
			Accepter.setMan(data.getMan());
			if (skilltype.equals(TypeUtil.FB_BGZ)) {//白骨爪
            	bgz(Accepterlist, Accepter, manData, data, fightingSkill, battlefield);
			}else if (skilltype.equals(TypeUtil.FB_JGE)||skilltype.equals(TypeUtil.FB_QW)) {
			//情网x金箍儿
				kzfb(Accepterlist, Accepter, manData, data, fightingSkill, battlefield);
			}else if (skilltype.equals(TypeUtil.FB_BLD)||skilltype.equals(TypeUtil.FB_DSY)||skilltype.equals(TypeUtil.FB_FTY)) {
			//宝莲灯x大手印x翻天印	
				jzt(Accepterlist,Accepter, data, fightingSkill, battlefield);
			}else {//直接上状态
				state(Accepterlist,Accepter, data, fightingSkill, battlefield);
			}
			Accepter.setStartState(TypeUtil.JN);	
			if (data.getCamp()==manData.getCamp()&&manData.getMan()==data.getMan()) {
				Originator.setStartState("法术攻击");
				fightingEvents.setOriginator(null);
				if (fightingSkill.getSkillid()==5007||fightingSkill.getSkillid()==5010||fightingSkill.getSkillid()==5012||fightingSkill.getSkillid()==5011) {
					Originator.setSkillskin(fightingSkill.getSkillid()+"");	
				}	
			}else {
				if (fightingSkill.getSkillid()==5007||fightingSkill.getSkillid()==5010||fightingSkill.getSkillid()==5012||fightingSkill.getSkillid()==5011) {
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
	}
	//白骨爪处理
    public void bgz(List<FightingState> Accepterlist,FightingState fightingState,
    		ManData myData,ManData nomyData,FightingSkill skill,Battlefield battlefield){
    	double jichu=skill.getSkillhurt();
    	if (Battlefield.random.nextInt(100)<jichu) {
    		ChangeFighting changeFighting=new ChangeFighting();
    		changeFighting.setChangetype(skill.getSkilltype());
    		changeFighting.setChangevlaue(skill.getSkillgain());
    		changeFighting.setChangesum(skill.getSkillcontinued());
    		nomyData.ChangeData(changeFighting,fightingState);	
    		Accepterlist.add(fightingState);  
    	}
    	
	}
	//情网x金箍儿处理
    public void kzfb(List<FightingState> Accepterlist,FightingState fightingState,
    		ManData myData,ManData nomyData,FightingSkill skill,Battlefield battlefield){
    	double jichu=skill.getSkillhurt();
    	FightingSkill skill2=nomyData.getAppendSkill(9250);
    	if (skill2!=null) {jichu-=skill2.getSkillhurt()/6;}
    	
    	if (skill.getSkilltype().equals(TypeUtil.FB_QW)) {
    		skill2=nomyData.getAppendSkill(9205);
        	if (skill2!=null) {jichu-=skill2.getSkillhurt();}
    		jichu-=myData.getQuality().getK_qw();
		}else if (skill.getSkilltype().equals(TypeUtil.FB_JGE)) {
			jichu-=myData.getQuality().getK_jge();
		}
    	double xs=1+(myData.getlvl()-nomyData.getlvl())/150.0;
    	jichu*=xs;
  	    if (Battlefield.random.nextInt(100)<jichu) {
  	  	state(Accepterlist, fightingState, nomyData, skill, battlefield);		
		}
	}
    //宝莲灯x大手印x翻天印 解状态
    public void jzt(List<FightingState> Accepterlist,FightingState fightingState,
    		ManData nomyData,FightingSkill skill,Battlefield battlefield){
    	if (Battlefield.random.nextInt(100)<skill.getSkillhurt()) {
    		String type="混乱";
    		if (skill.getSkilltype().equals(TypeUtil.FB_DSY)) {
    			type="封印";
    		}else if (skill.getSkilltype().equals(TypeUtil.FB_FTY)) {
    			type="遗忘";
    		}
    		nomyData.RemoveAbnormal(new String[]{type});
    		fightingState.setEndState_2(type);
    		Accepterlist.add(fightingState);	
		}
		
	}
    /**
	 * 状态
	 */
	public static void state(List<FightingState> Accepterlist,FightingState fightingState,
		ManData nomyData,FightingSkill skill,Battlefield battlefield){
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype(skill.getSkilltype());
		changeFighting.setChangevlaue(skill.getSkillhurt());
		changeFighting.setChangesum(skill.getSkillcontinued());
		nomyData.ChangeData(changeFighting, fightingState);	
		Accepterlist.add(fightingState);
	}
}
