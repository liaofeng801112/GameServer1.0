package come.tool.FightingData;

import java.util.List;

public class SummonType {

	/**释放技能可能添加的状态*/
	public static void xianzhi(ManData data,FightingSkill skill){
		int id=skill.getSkillid();
		//只能使用一次的技能1608  1606 1611 1612 1828
		if (id==1606||id==1608||id==1611||id==1612||id==1828||id==7019||id==7013||id==7015||id==7027||id==1313||id==1314||id==1315||id==1250||id==1251) {
			data.getSkills().remove(skill);
		}else {
			AddState addState=null;
			if ((id>=1600&&id<=1605)||id==1607||id==1200||id==1215||id==7012||id==7015||id==1876) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(5);	
			}else if (id==1606||id==7008||id==7009||id==7026||id==1333||id==1334||id==9610||id==1868||id==1869||id==1877) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(10);	
			}else if (id==7002||id==7025||id==7027) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(3);	
			}else if (id==1230) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(2);	
			}else if (id==7016||id==3035) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(6);	
			}else if (id==6029) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(15);	
			}else if (id==22000||id==22002||id==22004||id==22006||id==22008||id==22010||id==22012||id==22014||id==22016||id==22018
					||id==22020||id==22022||id==22024||id==22026||id==22028||id==22030||id==22032||id==22034) {
				addState=new AddState();
				addState.setStatename("冷却");
				addState.setStateEffect(id);
				addState.setSurplus(10);
			}
			if (addState!=null) {
				data.getAddStates().add(addState);
			}
		}
	}
	/**        3    5  
	 * 技能id 1600-1605 1607
	 * @return
	 */
	public static void Fiveelements(List<FightingState> Accepterlist,FightingState fightingState,
			ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		if (fightingState==null)fightingState=new FightingState();
		int skillid=fightingSkill.getSkillid();
		//第二步
		ChangeFighting nomyChange=new ChangeFighting();
		//持续回合的数值
		nomyChange.setChangevlaue(50);
		//持续回合的回合数	
		nomyChange.setChangesum(3);
		//持续回合的类型
		switch (skillid) {
		case 1600:
			nomyChange.setChangetype("金");	
			break;
		case 1601:
			nomyChange.setChangetype("木");	
			break;
		case 1602:
			nomyChange.setChangetype("土");	
			break;
		case 1603:
			nomyChange.setChangetype("水");	
			break;
		case 1604:
			nomyChange.setChangetype("火");	
			break;
		case 1876:
			nomyChange.setChangetype(TypeUtil.BB_WYJK);	
			break;
		case 1605:
			nomyChange.setChangetype("消除五行");	
			break;
		case 1607:
		case 1877:
		case 7019:
			nomyChange.setChangetype("隐身");	
			break;
		case 1612:
		case 1608:
		case 1830:
		case 1224:
			nomyChange.setChangetype("清除异常状态");	
			break;
		default:
			break;
		}
		nomyData.ChangeData(nomyChange,fightingState);
		Accepterlist.add(fightingState);
	}	
	/**
	 * 回血技
	 * @return
	 */
	public static void Disposable(List<FightingState> Accepterlist,FightingState fightingState,
			ManData manData,ManData nomyData,FightingSkill fightingSkill,String skilltype,Battlefield battlefield){
		//第二步
		ChangeFighting nomyChange=new ChangeFighting();
		//持续回合的数值
		nomyChange.setChangehp((int)(nomyData.getHp_z()*fightingSkill.getSkillgain()));
		nomyChange.setChangemp((int)(nomyData.getMp_z()*fightingSkill.getSkillgain()));
		nomyData.ChangeData(nomyChange, fightingState);
		Accepterlist.add(fightingState);
	}

}
