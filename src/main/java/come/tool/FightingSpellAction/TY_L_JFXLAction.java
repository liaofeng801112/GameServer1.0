package come.tool.FightingSpellAction;

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

public class TY_L_JFXLAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		FightingSkill skillSM=manData.skillId(1049);
		String skilltype=skillSM.getSkilltype();
		List<FightingState> Accepterlist=new ArrayList<>();
		List<ManData> datas=MixDeal.getjieshou(events, skillSM, manData,Accepterlist,battlefield,10); 
		if (datas.size()==0){
			FightingState Originator=events.getOriginator();
			if (manData.daijia(skill,Originator,battlefield)) {return;}
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skillSM.getSkillname());
			events.setOriginator(null);
			Accepterlist.add(Originator);
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);	
			return;		
		}
		int sum=3;		
		double dds=skill.getSkillhurt()/100D;
		double jc=manData.getSpellJC();
		double wg=manData.getWGTB();
		double kbl=0;
		FightingSkill skill2=manData.skillId(9261);
		if (skill2!=null) {jc*=(1+skill2.getSkillhurt()/100D);}
		skill2=manData.skillId(9264);
		if (skill2!=null) {kbl+=skill2.getSkillhurt();}
		
		FightingSkill tz_yg=null;FightingSkill tz_cb=null;
		FightingSkill tz_ph=null;FightingSkill tz_xy=null;FightingSkill ksys=null;
		List<FightingSkill> skills=ControlAction.getSkills(manData, skillSM,battlefield.BattleType);
		for (int i = manData.getSkills().size()-1; i >=0; i--) {
			skill2=manData.getSkills().get(i);
			String lei=skill2.getSkilltype();
			if (lei.equals(TypeUtil.TZ_YGZG)) {tz_yg=skill2;}
			else if (lei.equals(TypeUtil.TZ_CBNX)) {tz_cb=skill2;}
			else if (lei.equals(TypeUtil.TZ_PHQY)) {tz_ph=skill2;}
			else if (lei.equals(TypeUtil.TZ_XYXG)) {tz_xy=skill2;}
			else if (lei.equals(TypeUtil.BB_KSYS)) {ksys=skill2;}
		}
		boolean isTZ=false;
		boolean isXH=true;
		if (tz_yg!=null||tz_cb!=null||tz_ph!=null||tz_xy!=null) {isTZ=true;}
		int size=0;
		while (datas.size()!=0) {
			if (size>=21) {break;}
			ManData data=datas.remove(0);
			if (data.getStates()!=0) {continue;}
			for (int i = 0; i < sum; i++) {
				if (manData.getStates()!=0||data.getStates()!=0) {break;}
				size++;
				if (size>21) {break;}
				if (!isXH) {Accepterlist=new ArrayList<>();}
				FightingState Originator=new FightingState();
				Originator.setEndState(skillSM.getSkillname());
				if (isXH) {
					isXH=false;
					if (!manData.isLicense(skill)) {break;}
					if (manData.daijia(skill,Originator,battlefield)) {return;}	
				}else {
					Originator.setCamp(manData.getCamp());
					Originator.setMan(manData.getMan());
				}
			    FightingState Accepter=MixDeal.DSMY(manData,data,skillSM.getSkillid(),battlefield);
				if (Accepter==null) {
					data.addBear(skilltype);
					Accepter=new FightingState();
					if (isTZ) {HurtAction.addTZState(data, tz_yg, tz_cb, tz_ph, tz_xy,Accepter);isTZ=false;}//判断是否中了套装技能
					double hurt=skillSM.getSkillhurt();
					for (int k = 1; k < sum; k++) {hurt=hurt*dds;}
					HurtAction.hurt((int)hurt, jc, wg, kbl, skills, Accepterlist, Accepter, manData, data, skillSM, battlefield);		
				}else {
					Accepterlist.add(Accepter);
				}	
				Accepter.setSkillskin(skillSM.getSkillid()+"");	
				//判断是否触发音
				if (i==0&&ksys!=null) {
					double gl=ksys.getSkillhitrate();
					if (skillSM.getSkilllvl()==2){gl*=1.2;}
					else if (skillSM.getSkilllvl()==3){gl*=1.45;}
					else if (skillSM.getSkilllvl()==4){gl*=2;}
					if (gl>Battlefield.random.nextInt(100)) {//触发成功
						List<ManData> ksyss=battlefield.getZW(data);
						ChangeFighting fighting=new ChangeFighting();
						int hurt=(int) (ksys.getSkillhurt()/100*manData.getMp_z()/ksyss.size());
						for (int k = ksyss.size()-1; k >=0; k--) {
							ManData data2=ksyss.get(k);
							FightingState Accepter2=new FightingState();
							fighting.setChangehp(-hurt);
							int y=data2.getStates();
							data2.ChangeData(fighting,Accepter2);
							Accepterlist.add(Accepter2);
							if (data2.getStates()==1&&y!=data2.getStates()) {
								//先判断是否能复活
								MixDeal.DeathSkill(data2,Accepter2,battlefield);
							}
						}
					}
				}
				Originator.setStartState("法术攻击");
				Originator.setSkillsy(skillSM.getSkillname());
				Accepterlist.add(Originator);
				FightingEvents Events=new FightingEvents();
				Events.setAccepterlist(Accepterlist);
				battlefield.NewEvents.add(Events);	
				if (Accepterlist.size()==1) {break;}
			}
			if (data.getStates()==0) {break;}
		}
	}

}
