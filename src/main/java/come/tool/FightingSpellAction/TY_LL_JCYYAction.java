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

public class TY_LL_JCYYAction implements SpellAction{

	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
//		9209|惊才艳艳   |随机砍出5次，每次攻击力等于自身攻击的（100%+10%*等级）倍，不会触发连击以及其他附法效果。（仅在与NPC之间战斗有效）
		if (PK_MixDeal.isPK(battlefield.BattleType)) {return;}
		AddState state1=manData.xzstate(TypeUtil.TZ_FHJY);
		long Hurt=(long) (skill.getSkillhurt()/100.0*manData.getAp());
		double mzjc=0;//命中加成
		double dsjc=0;//躲闪加成
		//判断是否触发孩子技能
		ManData child=battlefield.getChild(manData);
		FightingSkill skill2=manData.getAppendSkill(9203);
//		9203|穿杨	    |使用加攻法术时，目标额外增加（1%*等级）的命中率。（仅在与NPC之间战斗有效）
		if (skill2!=null) {
			mzjc+=skill2.getSkillhurt();
			skill2=null;
		}
		if (child!=null) {
			skill2=child.getChildSkill("强普攻");
			if (skill2!=null) {mzjc=10000;}
		}
		GroupBuff buff=battlefield.getBuff(manData.getMan(), TypeUtil.YBYT);
		if (buff!=null) {mzjc+=buff.getValue();}
		int g=0;//已攻击次数
		int maxf=0;//最大反击数
		int f=0;//已反击数
		ManData nomyData=null;
		List<ManData> guiwei=new ArrayList<>();
		List<ManData> nodatas=getdaji(manData.getCamp(), events, battlefield);
		int size=0;
		for (int i = 0; i < nodatas.size(); i++) {
			nomyData=nodatas.get(i);
			if (nomyData.getStates()!=0) {continue;}
			size++;
			if (size>5) {return;}
			guiwei.clear();
			MixDeal.move(manData.getCamp(), manData.getMan(),"移动", nomyData.getCamp()+"|"+nomyData.getMan()+"|3",battlefield);
			if (i==0) {
				FightingState state=battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist().get(0);
				manData.daijia(skill,state,battlefield);//扣除代价
			}
			if (skill2!=null) {
				battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist().add(MixDeal.getChildSkill(child,skill2.getSkillname()));
				skill2=null;
			}
			long hurt=Hurt;
			//一次攻击一个流程  先反震  在  反击
			FightingEvents gjEvents=new FightingEvents();
			List<FightingState> zls=new ArrayList<>();
			FightingState ace=new FightingState();
			FightingState bao=new FightingState();
			if (Battlefield.random.nextInt(100)+1>nomyData.getsx(4, TypeUtil.SX_SBL)+dsjc-manData.getQuality().getRolefmzl()-mzjc-manData.mz) {
				ChangeFighting acec=new ChangeFighting();

				hurt=PhyAttack.Hurt(hurt,1,manData,nomyData,TypeUtil.PTGJ,ace,zls,battlefield,0,0);
				ManData baodata=battlefield.bhpd(nomyData.getCamp(), nomyData.getMan());
				if (baodata==null) {
					acec.setChangehp((int)-hurt);
					FightingPackage.ChangeProcess(acec, manData, nomyData, ace, TypeUtil.PTGJ, zls, battlefield);
				}else {
					ChangeFighting baoc=new ChangeFighting();
					baoc.setChangehp(-(int)(hurt*0.7));
					acec.setChangehp(-(int)(hurt*0.4));
					FightingPackage.ChangeProcess(acec, manData, nomyData, ace,TypeUtil.PTGJ, zls, battlefield);
					FightingPackage.ChangeProcess(baoc, manData, baodata , bao,TypeUtil.PTGJ, zls, battlefield);
					//躲闪成功
					ace.setStartState("瞬移");
					ace.setEndState(nomyData.getCamp()+"|"+nomyData.getMan()+"|"+PhyAttack.getdir(3));
					bao.setStartState("瞬移");
					bao.setEndState(nomyData.getCamp()+"|"+nomyData.getMan());
					guiwei.add(baodata);
					guiwei.add(nomyData);
				}
				PhyAttack.neidan(TypeUtil.PTGJ,manData, nomyData,hurt, battlefield,zls, g,1,0);
				if (state1!=null) {
					List<ManData> datas=battlefield.getZW(nomyData);
					for (int j = datas.size()-1; j >=0; j--) {
						ManData nomyData2=datas.get(j);
						if (nomyData2.getStates()!=0)continue;
						FightingState ace1=new FightingState();
						ChangeFighting acec1=new ChangeFighting();
						hurt=PhyAttack.Hurt(Hurt,1,manData,nomyData2,TypeUtil.PTGJ,ace1,zls,battlefield,0,0);
						hurt=(long) (hurt*state1.getStateEffect()/100);
						acec1.setChangehp((int)-hurt);
						FightingPackage.ChangeProcess(acec1,manData,nomyData2, ace1,TypeUtil.PTGJ, zls, battlefield);
					}
				}
			}else {
				//躲闪成功
				ace.setCamp(nomyData.getCamp());
				ace.setMan(nomyData.getMan());
				ace.setStartState("瞬移");
				ace.setProcessState("躲闪");
				ace.setEndState(nomyData.getCamp()+"|"+nomyData.getMan()+"|"+PhyAttack.getdir(3));
				zls.add(ace);
				guiwei.add(nomyData);
			}
			FightingState gjz=new FightingState();
			gjz.setCamp(manData.getCamp());
			gjz.setMan(manData.getMan());
			gjz.setSkillsy("attack");
			gjz.setStartState(TypeUtil.PTGJ);
			gjz.setEndState("3");
			zls.add(gjz);
			gjEvents.setAccepterlist(zls);
			battlefield.NewEvents.add(gjEvents);
			/*归位*/
			if (guiwei.size()!=0) {
				FightingEvents moveEvents=new FightingEvents();
				List<FightingState> moves=new ArrayList<>();
				for (int j = 0; j < guiwei.size(); j++) {
					FightingState move=new FightingState();
					move.setCamp(guiwei.get(j).getCamp());
					move.setMan(guiwei.get(j).getMan());
					move.setStartState("瞬移");
					move.setEndState(guiwei.get(j).getCamp()+"|"+guiwei.get(j).getMan());
					moves.add(move);
					moveEvents.setAccepterlist(moves);
				}
				moveEvents.setAccepterlist(moves);
				battlefield.NewEvents.add(moveEvents);
			}
			//判断是否反击
			if (guiwei.size()==0&&nomyData.getStates()==0&&manData.getStates()==0){
				if (maxf==0) maxf=PhyAttack.AttackSum(nomyData.getQuality().getRoleffjl()-manData.getQuality().getRolehsfj(),nomyData.getQuality().getRoleffjv());
				if (f<maxf) {
					//反击出现
					f++;
					FightingEvents fjEvents=new FightingEvents();
					List<FightingState> fjs=new ArrayList<>();
					FightingState fj=new FightingState();
					ChangeFighting fjc=new ChangeFighting();
					hurt=PhyAttack.Hurt(nomyData.getAp(),1,nomyData,manData,"反击",fj,zls,battlefield,0,0);
					fjc.setChangehp((int)-hurt);
					FightingPackage.ChangeProcess(fjc, nomyData,manData, fj, TypeUtil.PTGJ, fjs, battlefield);
					FightingState fjgjz=new FightingState();
					fjgjz.setCamp(nomyData.getCamp());
					fjgjz.setMan(nomyData.getMan());
					fjgjz.setStartState(TypeUtil.PTGJ);
					fjgjz.setEndState("3");
					fjs.add(fjgjz);
					fjEvents.setAccepterlist(fjs);
					battlefield.NewEvents.add(fjEvents);
				}
			}
			if (manData.getStates()==0) {
				MixDeal.move(manData.getCamp(), manData.getMan(),"移动", manData.getCamp()+"|"+manData.getMan(),battlefield);
			}else {
				MixDeal.move(manData.getCamp(), manData.getMan(),"瞬移", manData.getCamp()+"|"+manData.getMan(),battlefield);
			}
		}
	}
	/**
	 * 返回一个主要打击人
	 */
	public static List<ManData> getdaji(int nocamp,FightingEvents fightingEvents,Battlefield battlefield){
		ManData data=null;
		if (nocamp!=-1) {
			if (fightingEvents!=null&&fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
				int path=battlefield.Datapathhuo(fightingEvents.getAccepterlist().get(0).getCamp(),fightingEvents.getAccepterlist().get(0).getMan());
				if (path!=-1){
					data=battlefield.fightingdata.get(path);
				}
			}
		}
		return MixDeal.get(true,data,0,nocamp,0, 0, 0, 0, 10, -1, battlefield,1);
	}
}
