package come.tool.FightingData;

import java.util.ArrayList;
import java.util.List;


/**玩家对战的专用坑逼处理*/
public class PK_MixDeal {
    /**判断类型是否 玩家对战*/
	public static boolean isPK(int type){
		if ((type>=5&&type<=14)||type==21||(type>=31&&type<=33)||type==101) {
			return true;
		}
		return false;
	}
	/**判断类型是否 召唤兽专场*/
	public static boolean isBB(int type){
		return type==34; 	
	}
	/**单人竞技场*/
	public static boolean isArena(int type){
		return type==101; 	
	}
	/**可以添加伙伴填充的战斗场次*/
	public static boolean isPal(int type){
		if (isBB(type)) {
			return false;
		}else if (type==4) {
			return false;
		}else if (type==101) {
			return false;
		}else if (type==31) {	//TODO 是否允许伙伴参与战斗 FightingForesee.type  单挑还想带伙伴？做梦
			return false;
		}
		return true;
	}
	//双管和讨命处理
	public static void PK_ST(Battlefield battle){
		if (!isPK(battle.BattleType)) {
			return;
		}
		//先判断是否有双管
		GroupBuff buff=battle.getBuff(1, TypeUtil.BB_SGQX);
		//没双管在处理讨命
		if (buff!=null) {
		   //双管处理
		   PK_SG(buff, battle);
		   return;	
		}
		buff=battle.getBuff(1, TypeUtil.BB_TM);
		if (buff!=null) {
		   //讨命处理
		   PK_TM(buff, battle);
		   return;	
		}
	}
	/** 双管*/ 
	public static void PK_SG(GroupBuff buff,Battlefield battle){
		List<FightingState> Accepterlist=new ArrayList<>();
		for (int i = battle.Events.size()-1; i >=0; i--) {
			FightingState state=battle.Events.get(i).getOriginator();
			if (state.getStartState().equals(TypeUtil.JN)) {
				int path=battle.Datapathhuo(state.getCamp(), state.getMan());
				if (path!=-1) {
					ManData data=battle.fightingdata.get(path);
					FightingState org=new FightingState();
					data.getFightingState(org);
					org.setHp_Change(-20000);
					data.setHp(data.getHp()-20000);
					if (data.getHp()<=0) {
						data.setHp(0);
						data.setStates(1);
						MixDeal.DeathSkill(data,org,battle);
					}
					Accepterlist.add(org);
				}
			}
		}
		if (Accepterlist.size()!=0) {
			FightingEvents fightingEvents=new FightingEvents();
			fightingEvents.setAccepterlist(Accepterlist);
			battle.NewEvents.add(fightingEvents);		
		}
		
	}
    /**讨命*/ 
	public static void PK_TM(GroupBuff buff,Battlefield battle){
		//讨命处理
		List<ManData> datas=new ArrayList<>();
		for (int i = battle.fightingdata.size()-1; i >=0; i--) {
			ManData data=battle.fightingdata.get(i);
			if (data.getType()==1&&data.getStates()==0) {
				datas.add(data);
			}
		}
		if (datas.size()!=0) {
			List<FightingState> Accepterlist=new ArrayList<>();
			for (int i = datas.size()-1; i >=0; i--) {
				ManData data=datas.get(i);
				ManData jz=battle.getPetParents(data);
				int hurt=Battlefield.random.nextInt(15000)+20000;
//				9221|铜肝铁胆|悬刃、讨命、报复技能对自己造成的伤害减少（1%*等级）。（仅在与玩家之间战斗有效）
                FightingSkill skill2=data.skillId(9221);    
                if (skill2!=null) {
                	hurt=(int) (hurt*(100-skill2.getSkillhurt())/100.0);
				}
				if (jz!=null&&jz.getStates()==0) {
					FightingState og=new FightingState();
					jz.getFightingState(og);
					if (jz.getHp()>=hurt) {
						og.setHp_Change(-hurt);
						jz.setHp(jz.getHp()-hurt);
						hurt=0;
						if (jz.getHp()<=0) {
							jz.setStates(1);
						}
					}else {
						og.setHp_Change(-jz.getHp());
						hurt-=jz.getHp();
						jz.setHp(0);
						jz.setStates(1);
					}
					if (jz.getStates()==1) {
						//先判断是否能复活
						MixDeal.DeathSkill(jz,og,battle);
					}
					Accepterlist.add(og);
				}
				if (hurt>=0) {
					FightingState og=new FightingState();
					data.getFightingState(og);
					og.setHp_Change(-hurt);
					data.setHp(data.getHp()-hurt);
					if (data.getHp()<=0) {
						data.setHp(0);
						data.setStates(1);
						MixDeal.DeathSkill(data,og,battle);
					}
					Accepterlist.add(og);
				}
			}
			FightingEvents fightingEvents=new FightingEvents();
			fightingEvents.setAccepterlist(Accepterlist);
			battle.NewEvents.add(fightingEvents);	
		}
	
    }
	 /**遗患 悬刃*/ 
	public static void PK_YX(ManData data,FightingSkill skill,Battlefield battle){
		//同时判断 遗患 悬刃
		int nocamp=battle.nomy(data.getCamp());
		List<GroupBuff> buffs=null;
		GroupBuff buff=battle.getBuff(nocamp, TypeUtil.BB_YH);
		if (buff!=null) {
			if (buffs==null) {
				buffs=new ArrayList<>();
			}
			buffs.add(buff);
		}
		if (data.getType()==0&&skill.getSkillid()>1000&&skill.getSkillid()<1100) {
			buff=battle.getBuff(nocamp, TypeUtil.BB_XR);
			if (buff!=null) {
				if (buffs==null) {
					buffs=new ArrayList<>();
				}
				buffs.add(buff);
			}	
		}
		
		if (buffs==null) {
			return;
	    }
		FightingState og=new FightingState();
		data.getFightingState(og);
		for (int i = 0; i < buffs.size(); i++) {
			buff=buffs.get(i);
			battle.buffs.remove(buff);
			if (buff.getBuffType().equals(TypeUtil.BB_YH)) {
				//法力伤害
				int hurt=(int) (skill.getSkillblue()*buff.getValue()/100);
				if (data.getMp()<hurt) {
					hurt=data.getMp();
				}
				og.setMp_Change(-hurt);
				data.setMp(data.getMp()-hurt);
			}else{
				//血量伤害
				int hurt=(int) (skill.getSkillblue()*buff.getValue()/100);
//				9221|铜肝铁胆|悬刃、讨命、报复技能对自己造成的伤害减少（1%*等级）。（仅在与玩家之间战斗有效）
                FightingSkill skill2=data.skillId(9221);    
                if (skill2!=null) {
                	hurt=(int) (hurt*(100-skill2.getSkillhurt())/100.0);
				}
				og.setHp_Change(-hurt);
				data.setHp(data.getHp()-hurt);
			}
		}
		og.setBuff(MixDeal.getBuffStr(buffs, false));
		if (data.getHp()<=0) {
			data.setHp(0);
			data.setStates(1);
			MixDeal.DeathSkill(data,og,battle);
		}
		FightingEvents hhe=new FightingEvents();
		List<FightingState> hhs=new ArrayList<>();
		hhs.add(og);
		hhe.setAccepterlist(hhs);
		battle.NewEvents.add(hhe);
	}
	public static void PK_MSKK(ManData data,FightingSkill skill,Battlefield battle){
		int nocamp=battle.nomy(data.getCamp());
		List<GroupBuff> buffs=null;
		GroupBuff buff=battle.getBuff(nocamp, "妙手空空");
		if (buff!=null) {
			battle.buffs.remove(buff);
			List<FightingState> hhs=new ArrayList<>();
			if (buff.getData().getStates()==0) { //拥有者 展示特效
				if(buff.getData().getYuanzhu()<500) {return;}
				buff.getData().getSkills().add(skill); //给目标一个技能
				FightingState hhze=new FightingState();
				hhze.setCamp(buff.getData().getCamp());
				hhze.setMan(buff.getData().getMan());
				hhze.setStartState(TypeUtil.JN);
				ChangeFighting changeFighting=new ChangeFighting();
				changeFighting.setChangetype("隐身");
				changeFighting.setChangesum(2);
				buff.getData().ChangeData(changeFighting, hhze);
				hhze.setText("妙手空空#2");
				hhze.setSkillskin("1249");
				//hhze.setSkillskin("1828");
				hhs.add(hhze);
			}
			FightingState fs=new FightingState();
			fs.setCamp(data.getCamp());
			fs.setMan(data.getMan());
			fs.setStartState(TypeUtil.JN);
			//buffs=new ArrayList<>();
			//buffs.add(buff);
			//fs.setBuff(MixDeal.getBuffStr(buffs, false));
			hhs.add(fs);
			FightingEvents hhe=new FightingEvents();
			hhe.setAccepterlist(hhs);
			battle.NewEvents.add(hhe);
		}
	}
	/**化无*/
	public static boolean PK_HW(ManData data,FightingSkill skill,Battlefield battle){
		if (skill.getSkillid()>=5001&&skill.getSkillid()<=5015) {
			return false;
		}
		if (skill.getSkilltype().equals("魔界内丹")) {
			return false;
		}
		//化无
		int nocamp=battle.nomy(data.getCamp());
		GroupBuff buff=battle.getBuff(nocamp, TypeUtil.BB_HW);
		if (buff!=null) {
			battle.buffs.remove(buff);
			List<FightingState> hhs=new ArrayList<>();
			battle.systemMsg(data,hhs,8,skill);
			if (buff.getData().getStates()==0) {
				FightingState hhze=new FightingState();
				hhze.setCamp(buff.getData().getCamp());
				hhze.setMan(buff.getData().getMan());
				hhze.setStartState(TypeUtil.JN);
				hhze.setSkillskin("1828");
				hhs.add(hhze);
			}
			FightingState fs=new FightingState();
			fs.setCamp(data.getCamp());
			fs.setMan(data.getMan());
			fs.setStartState(TypeUtil.JN);
			List<GroupBuff> buffs=new ArrayList<>();
			buffs.add(buff);
			fs.setBuff(MixDeal.getBuffStr(buffs, false));
			hhs.add(fs);
			FightingEvents hhe=new FightingEvents();
			hhe.setAccepterlist(hhs);
			battle.NewEvents.add(hhe);
			return true;
		}
		return false;
	}
}
