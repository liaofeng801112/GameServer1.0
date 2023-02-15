package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

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

public class Kanglong implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
							   String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		boolean isHSSF=TypeUtil.BB_E_HSSF.equals(type);
		FightingSkill skill=manData.getAttacks("亢龙有悔").getSkill();
		//最大攻击次数
		int maxg=skill.getSkillsum()+(Battlefield.random.nextInt(100)<skill.getSkillgain()+manData.ljv?1:0);
		//已攻击次数
		int g=0;
		//伤害递减次数
		int d=1;
		double df=skill.getSkillhurt()/100.0;
		//基础伤害
		long ap=manData.getAp();
		double mzjc=0,dsjc=0;//命中加成,连击加成,躲闪加成
		GroupBuff buff=battlefield.getBuff(manData.getMan(), TypeUtil.YBYT);
		if (buff!=null) {mzjc+=buff.getValue();}
		buff=battlefield.getBuff(manData.getMan(), TypeUtil.BB_E_HYMB);
		if (buff!=null) {dsjc+=buff.getValue();}

		mzjc += manData.getQuality().getRolehsds() + manData.executeYszd(3) - manData.executeYszd(2);

		int nocamp=MixDeal.getcamp(manData.xzstate("混乱")==null?"普通攻击":"混乱技", manData.getCamp(),battlefield.nomy(manData.getCamp()));
		ManData nomyData=PhyAttack.getdaji(nocamp,fightingEvents,battlefield,manData,0);
		List<ManData> guiwei=new ArrayList<>();
		String type1=(nocamp==-1?"混乱技":"普通攻击");
		long zap=0;
		long zap7=0;
		long zap8=0;
		FightingSkill skill_1=null,skill_4=null,skill_5=null,skill_6=null,skill_7=null,skill_8=null;//箭无虚发,同仇敌忾,一击毙命,横扫四方
		for (int i=manData.getSkills().size()-1;i>=0;i--) {
			FightingSkill sl=manData.getSkills().get(i);
			if (sl.getSkillbeidong()!=1) {continue;}
			if (sl.getSkilltype().equals(TypeUtil.BB_E_JWXF)) {skill_1=sl;}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_TCDK)) {skill_4=sl;}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_YJBM)) {if (!PK_MixDeal.isPK(battlefield.CurrentRound)) {skill_5=sl;}}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_HSSF)) {skill_6=sl;}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_DSFS)) {skill_7=sl;zap7=(long) (manData.getKangluobao()*175);}
			else if (sl.getSkilltype().equals(TypeUtil.BB_E_QKYZ)) {
				skill_8=sl;
				zap8=PhyAttack.getMoney(manData, battlefield);
				if (zap8>=500000) {zap8=500000;}
				zap8=(long) (zap8/2.8);
			}
		}
		List<ManData> zzs=null;
		if (skill_4!=null&&Battlefield.isV(skill_4.getSkillgain())) {
			zzs=new ArrayList<>();
			for (int i = battlefield.fightingdata.size()-1; i >=0; i--) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getType()!=1||data.getCamp()!=manData.getCamp()||data.getStates()!=0) {continue;}
				if (data.getMan()==manData.getMan()) {continue;}
				zap+=data.getAp();
				zzs.add(data);
			}
			if (zap>=150000) {zap=150000;}
		}

		// 怒不可揭层数
		int nbkj = 0;
		// 触发战意分裂
		boolean zy = false;
		// 触发怒不可揭
		boolean nb = false;

		// 势不可遏附加伤害
		long sbke = 0;

		// 将功补过判定，等于true则回合末尾执行
		boolean jgbg = (maxg == 1);

		while (nomyData!=null&&g<maxg) {
			int gjw=Battlefield.random.nextInt(4)*2+1;
			MixDeal.move(manData.getCamp(), manData.getMan(),"瞬移", nomyData.getCamp()+"|"+nomyData.getMan()+"|"+gjw,battlefield);
			if (g==0) {
				if (type.equals("分裂")) {
					battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist().get(0).setSkillskin("1832");
				}else if (type.equals("追击")) {
					battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist().get(0).setSkillskin("1831");
				}
			}
			g++;
			int bb_e=0;
			if (skill_1!=null&&Battlefield.isV(skill_1.getSkillgain())) {bb_e=1;}

			FightingEvents gjEvents=new FightingEvents();
			List<FightingState> zls=new ArrayList<>();

			// 处理灵犀-火冒三丈、攻守兼备
			int hmgs = manData.executeHmsz(zls);
			if (hmgs == 2) {
				// 势不可揭额外攻击
				sbke = manData.getAp();
			} else {
				sbke = 0;
			}

			// 本次攻击为连击，并且为连击的第一次攻击
			if (g == 0 && maxg > 1) {
				// 处理灵犀-惊涛拍岸
				maxg = manData.executeJtpa(zls,maxg);
				jgbg = false;
			}

			FightingState ace=new FightingState();
			if (bb_e==1||!Battlefield.isV(nomyData.getsx(4, TypeUtil.SX_SBL)-manData.getQuality().getRolefmzl()+dsjc-mzjc-manData.mz)) {
				ap=manData.getAp()+zap+sbke;
				if (skill_7!=null&&Battlefield.isV(skill_7.getSkillgain())) {ap+=zap7;ace.setText("大圣附身#2");ace.setSkillskin(TypeUtil.BB_E_DSFS);}
				else if (skill_8!=null&&Battlefield.isV(skill_8.getSkillgain())) {ap+=zap8;ace.setText("乾坤一掷#2");ace.setSkillskin(TypeUtil.BB_E_QKYZ);}
				ChangeFighting acec=new ChangeFighting();
				ap=PhyAttack.Hurt(ap,d,manData,nomyData,type,ace,zls,battlefield,0,0);
				acec.setChangehp((int)-ap);
				if (skill_5!=null&&Battlefield.isV(skill_5.getSkillgain())) {
					FightingPackage.ChangeProcess(acec, manData, nomyData, ace, TypeUtil.ZSSH, zls, battlefield);
					ace.setText("不堪一击的选手#2");
				}else {
					FightingPackage.ChangeProcess(acec, manData, nomyData, ace, TypeUtil.PTGJ, zls, battlefield);
				}
				PhyAttack.feedback(type, manData, ap, battlefield, zls);
				PhyAttack.neidan(type1, manData, nomyData,ap, battlefield, zls, gjw,0,0);
				if (skill_6!=null&&(isHSSF||Battlefield.isV(skill_6.getSkillgain()/20))) {
					List<ManData> datas=battlefield.getZW(nomyData);
					for (int i = datas.size()-1; i >=0; i--) {
						FightingState ace1=new FightingState();
						ManData nomyData2=datas.get(i);
						if (nomyData2.getStates()!=0)continue;
						ChangeFighting acec1=new ChangeFighting();
						ap=PhyAttack.Hurt((long)(sbke+manData.getAp()*(skill_6.getSkillgain())/100D),g,manData,nomyData2,TypeUtil.PTGJ,ace1,zls,battlefield,0,0);
						acec1.setChangehp((int)-ap);
						FightingPackage.ChangeProcess(acec1, bb_e==2?null:manData, nomyData2, ace1, type, zls, battlefield);
					}
				}
			}else {
				//躲闪成功
				ace.setCamp(nomyData.getCamp());
				ace.setMan(nomyData.getMan());
				ace.setStartState("瞬移");
				ace.setProcessState("躲闪");
				ace.setEndState(nomyData.getCamp()+"|"+nomyData.getMan()+"|"+PhyAttack.getdir(gjw));
				zls.add(ace);
				guiwei.add(nomyData);
			}
			if (manData.getCamp()==nomyData.getCamp())gjw=PhyAttack.getdir(gjw);
			FightingState gjz=new FightingState();
			gjz.setCamp(manData.getCamp());
			gjz.setMan(manData.getMan());
			gjz.setStartState("普通攻击");
			gjz.setEndState(gjw+"");
			if (bb_e==1) {gjz.setText("箭无虚发#2");}
			zls.add(gjz);
			if (zzs!=null) {
				gjz.setText("合力一击#2");
				for (int i = 0; i < zzs.size(); i++) {
					ManData data=zzs.get(i);
					zls.add(MixDeal.skillmove(nomyData,data,"9"));
				}
			}

			if (zy) {
				// 怒不可揭判定
				if (nb) {
					FightingState gjz3 = new FightingState();
					gjz3.setCamp(manData.getCamp());
					gjz3.setMan(manData.getMan());;
					gjz3.setText("看我的#G怒不可揭");
					gjz3.setStartState("代价");
					zls.add(0,gjz3);
				}
				FightingState gjz2 = new FightingState();
				gjz2.setCamp(manData.getCamp());
				gjz2.setMan(manData.getMan());;
				gjz2.setText("看我的#G冲冠一怒");
				gjz2.setStartState("代价");
				zls.add(0,gjz2);
				gjz.setSkillskin("cgyn");
			}

			// 灵犀-化险为夷
			manData.addDun(ap, gjz);

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

			if (nomyData.getStates()!=0||nomyData.xzstate(TypeUtil.FY)!=null){//死亡中断连击
				g=maxg;

				if (nomyData.getStates()!=0) {
					// 目标死亡判定一往无前
					manData.executeYwwq(gjz);
				}
			}

//			换人连击
			int camp=nomyData.getCamp();
			int man=nomyData.getCamp();
			nomyData=PhyAttack.getdaji(nocamp,null,battlefield,manData,0);
			if (nomyData!=null&&camp==nomyData.getCamp()&&man==nomyData.getMan())d++;
			if (nomyData==null||nomyData.getStates()!=0||manData.getStates()!=0)g=maxg;
			//结束归位
			if (g>=maxg&&manData.getStates()==0){
				MixDeal.move(manData.getCamp(), manData.getMan(),"瞬移", manData.getCamp()+"|"+manData.getMan(),battlefield);
			}else if (g>=maxg) {
				MixDeal.move(manData.getCamp(), manData.getMan(),"瞬移", manData.getCamp()+"|"+manData.getMan(),battlefield);
			}


			zy = false;
			// 灵犀-战意分裂（不加追击数）
			if (g==maxg&&manData!=null&&manData.getStates()==0) {
				if (manData.executeZhanyi()) {
					if (nomyData!=null&&camp==nomyData.getCamp()&&man==nomyData.getMan()) {
						zy = true;
						nb = manData.executeNbkj(nbkj++);
						d=0;
						g=0;
						maxg=skill.getSkillsum()+(Battlefield.random.nextInt(100)<skill.getSkillgain()?1:0);
					}
				}
			}
		}

		// 从头到尾没有触发连击，判定将功补过
		if (jgbg) {
			manData.executeJgbg(battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist());
		}
	}

}
