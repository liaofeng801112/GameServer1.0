package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;

public class TYSkillAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		9130|惊世骇俗|对地方释放强力的百日眠，目标数（8+（等级-1））
//		9151|万里云天|释放一个强力失心狂乱，目标数为（6+等级-1）（仅在玩家与玩家之间战斗有效）
//		9171|烽烟四起|释放万毒攻心，毒的目标数为（5+等级）。（仅在与玩家之间战斗有效）
		
//		9169|铺天盖地|释放万毒攻心，增加毒法伤害（10%*等级），持续两回合。
//		9170|哀鸿遍野|释放一个强力万毒攻心，使中毒的目标身上累计（5000*等级）点伤害，之后自己使用毒法每命中目标一次累计伤害翻一倍，目标在之后第3回合开始前受到累计伤害的毒发伤害。（仅在与玩家之间战斗有效）
//      9350|贵妃醉酒|使用孟婆汤,牺牲对法术遗忘的成功率（4%*等级）换取遗忘命中率增加。(仅在与玩间战斗有效。)
//		9352|梦笔生花|对敌方全体单位释放孟婆汤,对主目标额增加（2%*等级）点忽视抗遗忘,对其他目标额外增加（1%*等级）点忽视抗遗忘,同时对命中的单位的法宝和召唤兽技能的遗忘成功率提高至（25%+5%*等级）。(仅在与玩家之间战斗有效)
		
//		9189|御龙|释放一个强力的阎罗追命，对命中的单位至少造成当前气血（5%*等级+5%）的伤害，同时伤害至多不超过目标当前的气血的（55%-5%*等级）。（仅在与玩家之间战斗有效）
//		9190|覆雨	|释放一个强力的阎罗追命，使敌方人物受到的所有法力伤害的（70%+等级*10%）。（仅在与玩家之间战斗有效）

//		9207|拔山	   |释放一个强力的魔神附身，增加被牛的单位（1%*等级）点破物理几率和破物理程度，持续3回合。（仅在与玩家之间战斗有效）
//		9208|七星贯日|释放一个强力的兽王神力，使被牛的单位AP额外增加（4%*等级），持续3回合。（仅在与NPC之间战斗有效）
//		9231|一苇渡江|释放一个强力天外飞魔，    被速目标的强法增加（2%*等级），且被速的目标有（20%*等级）的几率免疫受到的所有伤害，持续2回合。
//		9232|苦海慈航|释放一个强力乾坤借速，    为所有被速的单位增加一个护盾，当被速目标气血百分比低于（20%*等级）时，该护盾可以抵御义词致死伤害，护盾生效时清除被速目标身上的速法效果，护盾最多存在3回合。此技能全队全场只能用一次。（仅在与玩家之间战斗有效）
//		9250|岳镇渊渟|释放一个强力魔神护体，    效果加强（6%*等级），同时增加目标抗金箍和情网（1%*等级），持续5回合。（仅在与玩家之间战斗有效）
//		9251|穿针引线|释放一个强力的含情脉脉，目标数增加至（7+（等级<=3?等级:3））。持续（5+（等级>3?等级-3:0））（仅在与NPC之间战斗有效）
//		9252|鸿雁长飞|释放一个强力的含情脉脉，加强加防（10%*等级），持续三回合。（仅在与玩家之间战斗有效）

//		9286|龙破云惊|释放九龙冰封,减少被命中的敌方人物单位（20+10*等级）点怒气。(仅在与玩家之间战斗有效）
//		9287|好事成双|连续释放两次九龙冰封,每次伤害为应有伤害的（55%+5%*等级）
//		9307|长风万里|释放袖里乾坤攻击敌方全体,总伤害为单目标伤害的（350%+50%*等级）,同时将随机1个速度快于自己的敌方人物单位吹走2回合,并有（20%*等级）的几率增加一个单位。(仅在与玩家之间战斗有效)
//		9372|潇潇雨歇|使用血海深仇,目标数增加（等级*1）,伤害降低为原有伤害的（100%-（等级<=2?等级*10% : 等级*5%））。(仅在与C之间战斗有效。)						
		
		FightingState Originator=new FightingState();
		if (manData.daijia(skill,Originator,battlefield)) {return;}//扣除代价
		List<FightingState> Accepterlist=new ArrayList<>();
		Originator.setCamp(manData.getCamp());
		Originator.setMan(manData.getMan());
		Originator.setStartState("代价");
		FightingEvents ksevents=new FightingEvents();
		ksevents.setCurrentRound(battlefield.CurrentRound);
		Accepterlist.add(Originator);
		ksevents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(ksevents);
		
		FightingSkill skill2=manData.skillId(MixDeal.getTYSkillId(skill.getSkillid()));
		if (skill2==null) {return;}
		int id=skill2.getSkillid();
		AddState addState=skillState(skill, manData);
		if (addState!=null) {manData.getAddStates().add(addState);}
		int num=skill2.getSkillsum();
		skill2.setSkillsum(skillNum(skill, num));
		int skillcontinued=skill2.getSkillcontinued();
		skill2.setSkillcontinued(skillContinued(skill,skillcontinued));
		if (id==1025) {
			SpellActionType.getActionById(4).spellAction(manData, skill2, events, battlefield);
		}else if (id==1029||id==1030||id==1034||id==1035||id==1039||id==1040) {
			SpellActionType.getActionById(5).spellAction(manData,skill2,events,battlefield);
		}else if (id==1005||id==1015||id==1020||id==1075) {
			SpellActionType.getActionById(1).spellAction(manData,skill2,events,battlefield);
		}else if (id==1005||id==1015||id==1020||id==1075) {
			SpellActionType.getActionById(1).spellAction(manData,skill2,events,battlefield);
		}else if (id==1045||id==1055||id==1065) {
			SpellActionType.getActionById(11).spellAction(manData,skill2,events,battlefield);
		}else if (id==1094||id==1095) {
			SpellActionType.getActionById(21).spellAction(manData,skill,events,battlefield);
		}else if (id==1085||id==1099||id==1100||id==1089||id==1090) {
			SpellActionType.getActionById(20).spellAction(manData,skill,events,battlefield);
		}	
		
		if (addState!=null) {manData.getAddStates().remove(addState);}
		skill2.setSkillsum(num);
		skill2.setSkillcontinued(skillcontinued);
	}	
	
    /**更改技能目标数的9130 9151 9171*/
	public Integer skillNum(FightingSkill skill,int num){
		if (skill.getSkillid()==9130||skill.getSkillid()==9151||skill.getSkillid()==9171) {
			return (int)skill.getSkillhurt();
		}else if (skill.getSkillid()==9352||skill.getSkillid()==9307) {
			return 10;
		}else if (skill.getSkillid()==9251) {
			return (int) skill.getSkillhurt();
		}else if (skill.getSkillid()==9372) {
			return (int) (num+skill.getSkillhurt());
		}else if (skill.getSkillid()==9612) {
			return (int) (num+skill.getSkillgain());
		}
		return num;
	}
	/**添加状态作为大招标识*/
	public AddState skillState(FightingSkill skill,ManData data){
		if (skill.getSkillid()==9169||skill.getSkillid()==9350||skill.getSkillid()==9352
				||skill.getSkillid()==9207||skill.getSkillid()==9208||skill.getSkillid()==9231
				||skill.getSkillid()==9232||skill.getSkillid()==9250||skill.getSkillid()==9252
				||skill.getSkillid()==9286||skill.getSkillid()==9287||skill.getSkillid()==9307
				||skill.getSkillid()==9372) {
			return new AddState(skill.getSkilltype(), skill.getSkillhurt(), 0);
		}else if (skill.getSkillid()==9170) {
			return new AddState("哀鸿遍野", skill.getSkillhurt(), 0);
		}
		return null;
	}
	/**持续回合更改*/
	public int skillContinued(FightingSkill skill,int skillcontinued){
		if (skill.getSkillid()==9207||skill.getSkillid()==9208||
				skill.getSkillid()==9232||skill.getSkillid()==9252) {
			return 3;
		}else if (skill.getSkillid()==9231) {
			return 2;
		}else if (skill.getSkillid()==9250) {
			return 5;
		}else if (skill.getSkillid()==9251) {
			return (int) skill.getSkillgain();
		}
		return skillcontinued;	
	}
}
