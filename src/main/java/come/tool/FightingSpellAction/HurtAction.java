package come.tool.FightingSpellAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.*;
import org.come.tool.CustomFunction;
import cn.hutool.core.util.RandomUtil;

public class HurtAction implements SpellAction{
	@SuppressWarnings("unused")
	@Override
	public void spellAction(ManData manData, FightingSkill skill,FightingEvents events, Battlefield battlefield) {
		// TODO Auto-generated method stub
		String skilltype=skill.getSkilltype();
		FightingSkill tz_yg=null;FightingSkill tz_cb=null;
		FightingSkill tz_ph=null;FightingSkill tz_xy=null;FightingSkill ksys=null;
		FightingSkill gqlx=null;  //TODO 灵听技能归去来兮
		FightingSkill bcjf=null;
		FightingSkill yqcs=null;FightingSkill bb_e_ttym=null;FightingSkill bb_e_flbd=null;
		for (int i = manData.getSkills().size()-1; i >=0; i--) {
			FightingSkill skill2=manData.getSkills().get(i);
			String lei=skill2.getSkilltype();
			if (lei.equals(TypeUtil.TZ_YGZG)) {
				tz_yg = skill2;
			} else if (lei.equals(TypeUtil.TZ_CBNX)) {
				tz_cb = skill2;
			} else if (lei.equals(TypeUtil.TZ_PHQY)) {
				tz_ph = skill2;
			} else if (lei.equals(TypeUtil.TZ_XYXG)) {
				tz_xy = skill2;
			} else if (lei.equals(TypeUtil.BB_KSYS)) {
				ksys = skill2;
			} else if (lei.equals(TypeUtil.BB_YQCS)) {
				yqcs = skill2;
			} else if (lei.equals(TypeUtil.BB_E_TTYM)) {
				bb_e_ttym = skill2;
			} else if (lei.equals(TypeUtil.BB_E_FLBD)) {
				bb_e_flbd = skill2;
			}else  if (lei.equals(TypeUtil.BB_GQLX)){
				gqlx=skill2;
			}else  if (lei.equals(TypeUtil.BB_百草竞发)){
				bcjf=skill2;
			}
		}
		int bb_size=0;
		if (bb_e_ttym!=null) {
			if (Battlefield.isV(bb_e_ttym.getSkillhurt())) {bb_size+=2;}
			else if (Battlefield.isV(bb_e_ttym.getSkillgain())) {bb_size+=1;}
		}else if (bb_e_flbd!=null) {
			if (Battlefield.isV(bb_e_flbd.getSkillhurt())) {bb_size+=2;}
			else if (Battlefield.isV(bb_e_flbd.getSkillgain())) {bb_size+=1;}
		}
		List<FightingState> Accepterlist=new ArrayList<>();
		skill.setSkillsum(skill.getSkillsum()+bb_size);
		List<ManData> datas=MixDeal.getjieshou(events, skill, manData,Accepterlist,battlefield);
		skill.setSkillsum(skill.getSkillsum()-bb_size);
		if (datas.size()==0){
			FightingState Originator=events.getOriginator();
			if (manData.daijia(skill,Originator,battlefield)) {return;}
			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skill.getSkillname());
			events.setOriginator(null);
			Accepterlist.add(Originator);
			events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(events);
			return;
		}

		if (manData.ymjr) {
			FightingState ace = events.getOriginator();
			ace.setCamp(manData.getCamp());
			ace.setMan(manData.getMan());
			ace.setText("看我的#G一鸣惊人");
			Accepterlist.add(ace);
		}

		double dds=1D;
		double jc=manData.getSpellJC();
		double wg=manData.getWGTB();
		if (bb_size!=0&&manData.getShanghai()>=450) {
			if (bb_e_ttym!=null) {
				wg+=(Battlefield.random.nextInt(1000)+400)*40;
			}else if (bb_e_flbd!=null) {
				wg+=manData.getShanghai()*40;
			}
		}
		double kbl=0;
		if (skill.getSkillid()==1049) {
//			9261|雷奔云谲|增加电闪雷鸣伤害（3%*等级）
//			9264|雷厉风行|增加电闪雷鸣狂暴几率（2.4%*等级）
			FightingSkill skill2=manData.skillId(9261);
			if (skill2!=null) {
				jc*=(1+skill2.getSkillhurt()/100D);
			}
			skill2=manData.skillId(9264);
			if (skill2!=null) {
				kbl+=skill2.getSkillhurt();
			}
		}
		List<FightingSkill> skills=ControlAction.getSkills(manData, skill,battlefield.BattleType);

		boolean isTZ= tz_yg != null || tz_cb != null || tz_ph != null || tz_xy != null;
		//判断连击次数
		int sum=1;
		if (!skilltype.equals(TypeUtil.GH)) {//判断是否有仙法连
			if (Battlefield.isV(manData.getQuality().getRolexfljl())) {
				sum+=manData.getQuality().getRolexfljs();
				dds=0.85;
			}
		}


		if (skill.getSkillid() >= 1040 && skill.getSkillid() <= 1065 && manData.executeAbbs(battlefield)) {
			FightingState ace = new FightingState();
			ace.setCamp(manData.getCamp());
			ace.setMan(manData.getMan());
			ace.setText("看我的#G哀兵必败");
			Accepterlist.add(ace);
			sum++;
		}

		int totalHurt=0;
		FightingSkill skill2=null;
		if (PK_MixDeal.isPK(battlefield.BattleType)) {
			if (skill.getSkillid()==1050) {
				skill2=manData.skillId(9271);
				if (skill2!=null&&!Battlefield.isV(skill2.getSkillhurt())) {skill2=null;}
			}else if (skill.getSkillid()==1055) {
				skill2=manData.skillId(9288);
				if (skill2!=null&&!Battlefield.isV(skill2.getSkillhurt())) {skill2=null;}
				AddState addState=manData.xzstate(TypeUtil.TY_S_LPYJ);
				if (addState!=null) {skills=ControlAction.addSkill(manData.skillId(9286), skills);}
			}else if (skill.getSkillid()==1045) {
				skill2=manData.skillId(9308);
				if (skill2!=null&&!Battlefield.isV(skill2.getSkillhurt())) {skill2=null;}
				AddState addState=manData.xzstate(TypeUtil.TY_F_CFWL);
				if (addState!=null) {
					FightingSkill skill3=manData.skillId(9307);
					skills=ControlAction.addSkill(skill3, skills);
					if (skill3!=null) {
						jc*=skill3.getSkillhurt()/(datas.size()<5?5:datas.size())/100D;
						skill3.setSkillgain(1);
						if (Battlefield.isV((skill3.getSkillhurt()-350)/5*2)) {
							skill3.setSkillgain(2);
						}
					}
				}
			}else if (skill.getSkillid()==1060) {
				skill2=manData.skillId(9328);
				if (skill2!=null&&!Battlefield.isV(skill2.getSkillhurt())) {skill2=null;}
			}else if (skill.getSkillid()>=1061&&skill.getSkillid()<=1065) {
				skill2=manData.skillId(9364);
			}
		}else {
			if (skill.getSkillid()==1065) {
				AddState addState=manData.xzstate(TypeUtil.TY_GH_XXYX);
				if (addState!=null) {
					jc=1-(addState.getStateEffect()*0.05+(addState.getStateEffect()<=2?addState.getStateEffect()*0.05:0.1));
				}
			}
		}
		if (skill.getSkillid()==1055) {
			AddState addState=manData.xzstate(TypeUtil.TY_S_HSCS);
			if (addState!=null) {sum=2;dds=1D;jc*=addState.getStateEffect()/100D;}
		}
		if (datas.size()>1) {
			ManData data=datas.remove(0);
			datas.add(data);
		}
		boolean isyqcs=false;

		// 灵犀-青云直上伤害倍数
		double shbs = 1;
		if (sum > 1 && skill.getSkillid() >= 1040 && skill.getSkillid() <= 1065) {
			shbs += manData.executeQyzs() / 100.0;
		}

		for (int i = 0; i < sum; i++) {
			if (manData.getStates()!=0) {break;}
			if (i!=0) {Accepterlist=new ArrayList<>();}
			FightingState Originator=new FightingState();
			Originator.setEndState(skill.getSkillname());
			if (i==0) {
				if (bb_size!=0) {Originator.setText(bb_e_ttym!=null?"听天由命#2":"法力波动#2");}
				if (!manData.isLicense(skill)) {break;}
				if (manData.daijia(skill,Originator,battlefield)) {return;}
			}else {
				Originator.setCamp(manData.getCamp());
				Originator.setMan(manData.getMan());
			}

			// 群体法术记录最高的一次伤害
			int maxHurt = 0;

			String skin=null;
			isyqcs=(yqcs!=null&&Battlefield.isV(yqcs.getSkillhitrate()));
			for (int j = datas.size()-1; j>=0; j--) {
				ManData data=datas.get(j);
				if (data.getStates()!=0) {
					data=MixDeal.getjieshou(skill, manData, datas, battlefield);
					if (data!=null) {
						datas.set(j,data);
					}else {
						datas.remove(j);
						continue;
					}
				}
				FightingState Accepter=MixDeal.DSMY(manData,data,skill.getSkillid(),battlefield);
				if (Accepter==null) {
					data.addBear(skilltype);
					Accepter=new FightingState();
					if (isTZ) {addTZState(data, tz_yg, tz_cb, tz_ph, tz_xy,Accepter);isTZ=false;}//判断是否中了套装技能
					double hurt=skill.getSkillhurt();
					if (isyqcs&&j==datas.size()-1) {
						skin=yqcs.getSkilltype();
						if (manData.getMp_z()>100000) {hurt+=(CustomFunction.XS(manData.getMp_z(),4200)-15000)*(PK_MixDeal.isPK(battlefield.BattleType)?1:2);}
					}
					for (int k = 1; k < sum; k++) {hurt=hurt*dds;}

					// 只对主目标判定伤害之前的第一次攻击触发
					if (j == datas.size()-1 && i == 0) {
						manData.executeSdef(battlefield);
						manData.executeQfyx(data,Accepterlist);
					}

					if (sum > 1) {
						hurt *= shbs;
					}

					int fashang = 0;
					fashang = hurt((int)hurt, jc, wg, kbl, skills, Accepterlist, Accepter, manData, data, skill, battlefield);
					totalHurt += fashang;

					if (maxHurt < fashang) {
						maxHurt = fashang;
					}

					// 只对主目标判定伤害之后
					if (j == datas.size()-1) {
						// 清者自清，反隐+清Buff判定
						manData.executeQzzq(data, Accepter, Accepterlist, battlefield);
						// 清风送河判定
						manData.addFaDun(fashang, Originator);
					}
					// 锥心刺骨判定
					if (data.getStates() == 0 && skill.getSkillid() > 1040 && skill.getSkillid() < 1065 && skill.getSkillid() % 5 == 4){
						int zxcg = manData.executeZxcg(1);
						if (zxcg > 0 && Battlefield.isV(zxcg + manData.getShanghai() / 66.6)) {
							// 至圣伤害

							FightingState say = new FightingState();
							say.setCamp(manData.getCamp());
							say.setMan(manData.getMan());
							say.setText("看我的#G锥心刺骨");
							Accepterlist.add(0,say);

							int zssh = manData.executeZxcg(2) * manData.getMp_z() / 100;
							ChangeFighting changeFighting2=new ChangeFighting();
							changeFighting2.setChangehp(-zssh);
							FightingState fState2=new FightingState();
							FightingPackage.ChangeProcess(changeFighting2, null, data, fState2, "至圣", Accepterlist, battlefield);
						}
					}
					// 添加百草竞发标记，增强一下去疾在游戏中的效果，这里不做单一目标被标记的判断，改为每次攻击只要被技能打到必定被标记
					if (i == sum - 1  && bcjf != null) {  //&& j == datas.size() - 1
						AddState zt1=data.xzstate(bcjf.getSkilltype());
						if (zt1 == null) {
							data.addBear(bcjf.getSkilltype());
							AddState addState=new AddState();
							addState.setStatename(bcjf.getSkilltype());
							addState.setStateEffect (bcjf.getSkillhurt());
							addState.setStateEffect2(bcjf.getSkillgain());
							addState.setSurplus(bcjf.getSkillcontinued());
							Accepter.setEndState_1(addState.getStatename());
							data.getAddStates().add(addState);
						}
					}
					// 添加归去来兮标记，增强一下灵听在游戏中的效果，这里不做单一目标被标记的判断，改为每次攻击只要被技能打到必定被标记
					if (i == sum - 1  && gqlx != null) {  //&& j == datas.size() - 1
						AddState zt=data.xzstate(gqlx.getSkilltype());
						if (zt == null) {
							data.addBear(gqlx.getSkilltype());
							AddState addState=new AddState();
							addState.setStatename(gqlx.getSkilltype());
							addState.setStateEffect (gqlx.getSkillhurt());
							addState.setStateEffect2(gqlx.getSkillgain());
							addState.setSurplus(gqlx.getSkillcontinued());
							Accepter.setEndState_1(addState.getStatename());
							data.getAddStates().add(addState);
						} else {
							// 如果目标未死亡并且已经存在标记则在触发一次 法力上限12% 的伤害*等级/100的伤害  绝对伤害
							if (data.getStates() == 0) {
								FightingState Accepter2 = new FightingState();
								double gqlxbj = manData.getMp_z() * 0.12 * manData.getLvl() / 100;
								hurt(0, jc, gqlxbj, kbl, skills, Accepterlist, Accepter2, manData, data, skill, battlefield);
							}
						}
					}
				}else {
					Accepterlist.add(Accepter);
				}

				Accepter.setSkillskin(skin!=null?skin:skill.getSkillid()+"");

				//判断是否触发音
				if (i==0) {
					if (ksys!=null) {
						double gl=ksys.getSkillhitrate();
						if (skill.getSkilllvl()==2){gl*=1.2;}
						else if (skill.getSkilllvl()==3){gl*=1.45;}
						else if (skill.getSkilllvl()==4){gl*=2;}
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
					}else if (isyqcs&&j==datas.size()-1) {
						int size=1;
						for (int k = 0; k <4; k++) {
							if (Battlefield.isV(yqcs.getSkillhitrate())) {size++;}
						}
						List<ManData> datas2=MixDeal.get(false,data,0,manData.getCamp(),0,0,0,0,size,-1,battlefield,0);
						for (int k = 0; k < datas2.size(); k++) {
							int hurt=(int) skill.getSkillhurt();
							if (manData.getMp_z()>100000) {hurt+=(CustomFunction.XS(manData.getMp_z(),4200)-15000)*(PK_MixDeal.isPK(battlefield.BattleType)?1:2);}
							for (int l =0; l<=k;l++) {hurt*=0.8;}
							ManData data2=datas2.get(k);
							FightingState Accepter2=new FightingState();
//							hurt=Calculation.getCalculation().xianfa(manData,data2,hurt,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);
							hurt=Calculation.getCalculation().SMHurt(manData,data2,hurt,0,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);

							ChangeFighting fighting=new ChangeFighting();
							fighting.setChangehp(-hurt);
							fighting.setChangemp((int)(-hurt*0.2));
							Accepter2.setSkillskin(skin);
							FightingPackage.ChangeProcess(fighting,manData,data2,Accepter2,skill.getSkilltype(),Accepterlist,battlefield);
						}
					}
				}
			}

			// 师门法术触发狂暴判定大开杀戒随机周围1的1个目标造成等量伤害
			if (manData.fskbbj && maxHurt > 0 && Battlefield.isV(manData.executeDksj(2))){
				ManData friend = battlefield.getZW1(datas.get(datas.size()-1));
				if (friend != null) {
					FightingState Accepter=new FightingState();
					Accepter.setSkillskin(skin);
					ChangeFighting changeFighting2=new ChangeFighting();
					changeFighting2.setChangehp(-maxHurt);

					FightingState say = new FightingState();
					say.setCamp(manData.getCamp());
					say.setMan(manData.getMan());
					say.setText("看我的#G大开杀戒");
					Accepterlist.add(0,say);
					FightingPackage.ChangeProcess(changeFighting2, manData, friend, Accepter, skill.getSkilltype(), Accepterlist, battlefield);
				}
			}

			Originator.setStartState("法术攻击");
			Originator.setSkillsy(skill.getSkillname());
			Accepterlist.add(Originator);
			FightingEvents Events=new FightingEvents();
			Events.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(Events);
			if (Accepterlist.size()==1) {break;}
		}


		if (manData.ymjr) {
			manData.ymjr = false;
			// 触发一鸣惊人后判定是否有隐身技和飘忽不定
			manData.executePhbd(battlefield.NewEvents.get(battlefield.NewEvents.size() -1));
		}



		if (skill2!=null&&totalHurt>0&&manData.getStates()==0) {
			if (skill2.getSkillid()==9364) {//9364|月满西楼|鬼火造成伤害的（0.5%*等级）回复为自己的法力(仅在与玩家之间战斗有效。)
				ChangeFighting fighting=new ChangeFighting();
				FightingState Accepter2=new FightingState();
				Accepter2.setStartState("药");
				totalHurt*=skill2.getSkillhurt()/100D;
				fighting.setChangemp(totalHurt);
				manData.ChangeData(fighting,Accepter2);
				Accepterlist.add(Accepter2);
			}else {
				totalHurt*=0.2;if (totalHurt>manData.getHp_z()) {totalHurt=manData.getHp_z();}
				ChangeFighting fighting=new ChangeFighting();
				FightingState Accepter2=new FightingState();
				Accepter2.setStartState("代价");
				fighting.setChangetype(skill2.getSkilltype());
				fighting.setChangevlaue(totalHurt);
				if (skill2.getSkillid()==9271) {
					fighting.setChangevlaue2((skill.getSkillhurt()-15)/2*2000);
				}else if (skill2.getSkillid()==9328) {
					fighting.setChangevlaue2((skill.getSkillhurt()-15)/2*0.5);
				}else {
					fighting.setChangevlaue2((skill.getSkillhurt()-15)/2*5);
				}
				manData.ChangeData(fighting,Accepter2);
				Accepterlist.add(Accepter2);
			}
		}
		//归去来兮//标记生物

		//百草竞发

		FightingSkill skill_百草竞发 = manData.getSkillType(TypeUtil.BB_百草竞发);
		if (skill_百草竞发 != null) {
			int num = RandomUtil.randomInt(11);

		}
	}

	/**伤害处理*/
	public static int hurt(int hurt,double jc,double wg,double kbl,List<FightingSkill> skills,List<FightingState> list,
						   FightingState fState,ManData manData,ManData data,FightingSkill skill,Battlefield battlefield){
		int syhp=data.getHp();
		double kbxs=MixDeal.addition(kbl,fState, manData, data, skill.getSkilltype());
		if (kbxs!=0) {
			hurt*=kbxs;
			wg*=kbxs;
		}
		ChangeFighting changeFighting=new ChangeFighting();
		if (skills!=null) {
			for (int i = skills.size()-1; i >=0; i--) {
				FightingSkill skill2=skills.get(i);
				int id=skill2.getSkillid();
				if (id==9263) {
					if (data.getType()==1) {
						jc*=(1+skill2.getSkillhurt()/100.0);
					}
				}else if (id==9267) {
					if (kbxs!=0) {
						AddState addState=data.getGainState();
						if (addState!=null) {
							data.getAddStates().remove(addState);
							fState.setEndState_2(addState.getStatename());
						}
					}
				}else if (id==9269) {
					if (kbxs!=0&&Battlefield.random.nextInt(4)==0) {
						manData.getQuality().setRolewxqkm(manData.getQuality().getRolewxqkm()+100);
						List<ManData> ksyss=battlefield.getZW(data);
						ChangeFighting fighting=new ChangeFighting();
						for (int k = ksyss.size()-1; k >=0; k--) {
							int hurt2=(int) (skill2.getSkillhurt()+Battlefield.random.nextInt(10000));
							ManData data2=ksyss.get(k);
//							hurt2=Calculation.getCalculation().xianfa(manData,data,hurt2,  skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);
							hurt2=Calculation.getCalculation().SMHurt(manData,data,hurt2,0,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);

							FightingState Accepter2=new FightingState();
							fighting.setChangehp(-hurt2);
							int y=data2.getStates();
							data2.ChangeData(fighting,Accepter2);
							list.add(Accepter2);
							if (data2.getStates()==1&&y!=data2.getStates()) {
								//先判断是否能复活
								MixDeal.DeathSkill(data2,Accepter2,battlefield);
							}
						}
						manData.getQuality().setRolewxqkm(manData.getQuality().getRolewxqkm()-100);
					}
				}else if (id==9283) {
					if (Battlefield.isV(skill2.getSkillhurt())) {
						changeFighting.setSkill(skill2);
					}
				}else if (id==9323) {
//					9323|丹凤朝阳|对被控制的目标,使用火法时,伤害增加（3%*等级）。(仅在与玩家之间战斗有效。
					AddState addState=data.getControlState();
					if (addState!=null) {jc*=(1+skill2.getSkillhurt()/100.0);}
				}else if (id==9361) {
//					9361|荧惑守心|使用鬼火时,对非鬼族的目标额外增加伤（20*等级）。(仅在与玩家之间战斗有效。)
					if (Sepcies_MixDeal.getRace(data.getSe())!=10004) {jc*=(1+skill2.getSkillhurt()/100.0);}
				}else if (id==9363) {
//					9363|鬼使神差|使用鬼火时,有（2%*等级）几率清除掉目标身上的一个增益状态。(仅在与之间战斗有效
//					9366|遥相呼应|有(20%*等级)几率将鬼使神差清除掉的状态转移至随机一个友方单位。(仅在与之间战斗有效）
					if (Battlefield.isV(skill2.getSkillhurt())) {
						AddState addState=data.getGainState();
						if (addState!=null) {
							data.getAddStates().remove(addState);
							fState.setEndState_2(addState.getStatename());
							for (int j = skills.size()-1; j >=0; j--) {
								skill2=skills.get(j);
								if (skill2.getSkillid()==9366) {
									if (Battlefield.isV(skill2.getSkillhurt())) {
										List<ManData> datas=MixDeal.get(true,manData,0,data.getCamp(),0,0,0,0,1,-1,battlefield,1);
										if (datas.size()!=0) {
											ManData data2=datas.get(0);
											ChangeFighting changeFighting2=new ChangeFighting();
											changeFighting2.setChangetype(addState.getStatename());
											changeFighting2.setChangevlaue( addState.getStateEffect());
											changeFighting2.setChangevlaue2(addState.getStateEffect2());
											FightingState fState2=new FightingState();
											FightingPackage.ChangeProcess(changeFighting2, null, data2, fState2, addState.getStatename(), list, battlefield);
										}
									}
									break;
								}
							}
						}
					}
				}else if (id==9367) {
//					9367|轻云蔽月|每次鬼火攻击有33%降低敌方主属性的（1.2%*等级），如果主属性是敏捷,则效果减半,持续两回合。(仅在与玩家之间战斗有效）
					if (Battlefield.isV(33D)) {
						data.addAddState(TypeUtil.TY_GH_QYBY,skill.getSkillhurt(), 0, 2);
					}
				}else if (id==9286) {
//					9286|龙破云惊|释放九龙冰封,减少被命中的敌方人物单位（20+10*等级）点怒气。(仅在与玩家之间战斗有效）
					changeFighting.setSkill(skill2);
				}else if (id==9307) {
//					9307|长风万里|释放袖里乾坤攻击敌方全体,总伤害为单目标伤害的（350%+50%*等级）,同时将随机1个速度快于自己的敌方人物单位吹走2回合,并有（20%*等级）的几率增加一个单位。(仅在与玩家之间战斗有效)
					if (manData.getvalue(6)<data.getvalue(6)) {
						data.addAddState(TypeUtil.TY_F_CFWL_S, 0, 0, 2);
						skill2.setSkillgain(skill2.getSkillgain()-1);
						if (skill2.getSkillgain()<=0) {
							skills.remove(i);
						}
					}
				}
			}
		}
//        if (PK_MixDeal.isPK(battlefield.BattleType)) {
//        	//仙法计算方式二 寒鸦加成不考虑抗性
//        	  Hurt=Calculation.getCalculation().xianfa(manData,data,Hurt,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);
//            Hurt*=jc;
//            Hurt*=0.65;
//            Hurt+=wg;
//		}else {
//			//仙法计算方式一 寒鸦加成考虑抗性
//			Hurt*=jc;
//	        Hurt+=wg;
//	        Hurt=Calculation.getCalculation().xianfa(manData,data,Hurt,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);
//		}
		if (PK_MixDeal.isPK(battlefield.BattleType)) {
			hurt*=0.7;
		}

		// 类型为召唤兽时释放仙法鬼火法术基础伤害增加等级额外伤害
		if (manData.getType() == 1 && skill.getSkillid() > 1040 && skill.getSkillid() <= 1065) {
			hurt += manData.getLvl() * 100;
		}

		AddState addState = data.xzstate("无坚不摧");
		if (addState != null) {
			double baifenbi = (data.getFmwjbc()/288);
			double sh = hurt+((hurt* (baifenbi))/100);
			hurt = (int)sh;
		}

		hurt=Calculation.getCalculation().SMHurt(manData,data,hurt,wg,skill.getSkilltype(),manData.getCamp()==1?battlefield.MyDeath:battlefield.NoDeath);
		hurt*=jc;
		changeFighting.setChangehp(-hurt);
		FightingPackage.ChangeProcess(changeFighting, manData, data, fState, skill.getSkilltype(), list, battlefield);

		int cjdg = 50;
		addState = manData.xzstate("气吞山河");
		if (addState != null) {
			cjdg = 100;
		}
		FightingSkill skill12=manData.skillId(22013);
		if (skill12!=null&&Battlefield.isV(cjdg)) {
			//	if (Battlefield.isV(cjdg)) {
			double fmsh= (manData.getFmsld()/600);
			ChangeFighting changeFighting2=new ChangeFighting();
			addState = manData.xzstate("气吞山河");
			if (addState != null) {
				double fmsh1= (manData.getFmsld()/500);
				fmsh = fmsh+fmsh1;
			}
			double zssh = (hurt * fmsh)/100;
			//	int zssh = hurt ;
			changeFighting2.setChangehp((int)-zssh);
			FightingState fState2=new FightingState();
			FightingPackage.ChangeProcess(changeFighting2, manData, data, fState2, "至圣", list, battlefield);
		}



		if (kbxs != 0 && !manData.fskbbj) {
			manData.fskbbj = true;
		}

		if (skills!=null) {
			for (int i =skills.size()-1;i>=0;i--) {
				FightingSkill skill2=skills.get(i);
				int id=skill2.getSkillid();
				if (id==9266) {
//					9266|暗雷涌动|使用雷法秒死召唤兽时，对其主人造成（5000+1000*等级）-（5000+2000*等级）伤害。（仅在与玩家之间战斗有效）
					if (data.getType()==1&&data.getStates()==1) {
						ManData parents=battlefield.getPetParents(data);
						if (parents!=null&&parents.getStates()==0) {
							int hurt2=(int) (skill2.getSkillhurt()+Battlefield.random.nextInt(5000));
							ChangeFighting fighting=new ChangeFighting();
							FightingState Accepter2=new FightingState();
							fighting.setChangehp(-hurt2);
							int y=parents.getStates();
							parents.ChangeData(fighting,Accepter2);
							list.add(Accepter2);
							if (parents.getStates()==1&&y!=parents.getStates()) {
								//先判断是否能复活
								MixDeal.DeathSkill(parents,Accepter2,battlefield);
							}
						}
					}
				}else if (id==9281||id==9301||id==9321) {
//					9281|沧浪濯缨|使用水法攻击目标后如果目标没有死亡,则目标封印抗性降低0.5%*（等级+1）,持续2回合。(仅在与琉家之间战斗有效)
//					9301|沉醉东风|使用风法攻击目标后如果目标没有死亡,则目标混乱抗性降低（0.5%*（等级+1））,持续2回合。(仅在与玩家之间战斗有效)
//					9321|烘炉点雪|使用火法攻击目标后如果目标没有死亡,则下回合使用火法对该目标的狂暴几率增加（1%*等级）(仅在与玩家之间战斗有效)
					if (data.getStates()==0) {
						data.addAddState(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9284||id==9304||id==9324) {
//					9284|连消带打|使用水法杀死目标时,有（2%*等级）的几率会附带清除目标身上魅惑,加防,加速,加攻状态中的若目标身上没有随机到的状态,则无效。
//					9304|扫雪烹茶|使用风法杀死目标时,有（1%*等级）的几率会附带清除目标身上魅惑,加防,加速,加政状态中的一个,并把该状态附加到本方随机没有该状态的单位身上,若目标身上没有随机到的状态,则无效
//					9324|聚萤映雪|使用火法攻击目标后如果目标死亡,则有（4%*等级）的几率使目标身上所有的魅惑,加防,加速,加攻状态的持续回合数减1,如果目标身上没有这些状态,则无效。
					if (Battlefield.isV(skill2.getSkillhurt())) {
						 addState=data.getGainState();
						if (addState!=null) {
							if (id==9324) {
								addState.isEnd();
							}else {
								data.getAddStates().remove(addState);
								fState.setEndState_2(addState.getStatename());
								if (id==9304) {
									List<ManData> datas=MixDeal.get(true,manData,0,data.getCamp(),0,0,0,0,10,-1,battlefield,1);
									for (int j=datas.size()-1;j>=0;j--) {
										ManData data2=datas.get(j);
										if (data2.xzstate(addState.getStatename())==null) {
											ChangeFighting changeFighting2=new ChangeFighting();
											changeFighting2.setChangetype(addState.getStatename());
											changeFighting2.setChangevlaue( addState.getStateEffect());
											changeFighting2.setChangevlaue2(addState.getStateEffect2());
											FightingState fState2=new FightingState();
											FightingPackage.ChangeProcess(changeFighting2, null, data2, fState2, addState.getStatename(), list, battlefield);
											break;
										}
									}
								}
							}
						}
					}
				}else if (id==9303) {
//					9303|花开见佛|使用风法时,每杀死一个召唤兽,下回合为自己增加忽视抗风（2%*等级）(仅在与玩家之间战斗有效。)
					if (data.getType()==1&&data.getStates()==1) {
						manData.addAddState2(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9306) {
//					9306|元气大伤|被风法秒死的单位,在未来两回合内,对我方单位造成的伤害减少（3%*等级）。(仅在与玩之间战斗有效。
					if (data.getStates()==1) {
						data.addAddState2(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9326) {
//					9326|火上浇油|每次受到火法攻击的目标身上附加一个火种可以累加,狂暴时额外增加一个火种某一个目标身上的火种达到五个时,则会引爆所有的火种。每个火种对其目标造成（2000+1000*等级）-（5000*等级+1000）的伤害,该伤害属性为100%强力克金。(仅在与玩家之间战斗有效。)
					int hurt2=(int)skill2.getSkillhurt()+Battlefield.random.nextInt(3000);
					if (kbxs!=0) {
						hurt2=+(int)skill2.getSkillhurt()+Battlefield.random.nextInt(3000);
					}
					 addState=data.addAddState2(skill2.getSkilltype(),kbxs!=0?2:1,hurt2,9999);
					if (addState.getStateEffect()>=5) {battlefield.isHSJY=true;}
					fState.setEndState_1(TypeUtil.TY_H_HSJY);
				}else if (id==9362) {
//					9362|余烬复燃|使用鬼火狂暴时每杀死一个敌方目标,下回为自己增加（0.4%*等级）点鬼火狂暴程度。
					if (data.getStates()==1&&kbxs!=0) {
						manData.addAddState2(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9371) {
//					9371|小鬼难缠|鬼火秒死敌方时,有（3%+1%*等级）几率触发小鬼难缠状态,处于该状态下的单位无法被用使药物和三尸回血,持续2回合。(仅在与玩家之间战斗有效。)
					if (data.getStates()==1&&Battlefield.isV(skill2.getSkillhurt())) {
						data.addAddState2(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9369) {
//					9369|白泣残红|鬼火对敌方单位造成伤害后如果目标当前血还高于（80%-5%*等级）,则下回合对目标使用鬼火时忽视鬼火抗性（2%*等级）点。(仅在与距之间战斗有效)
					if (data.getvalue(0)>(80-skill2.getSkillhurt()/40D)) {
						data.addAddState2(skill2.getSkilltype(),skill2.getSkillhurt(),0,2);
					}
				}else if (id==9370) {
//					9370|鬼蜮魑魅|鬼火对敌方造成伤害后如果目标当前血量低于（10%+1%*等级）,则有20%几率对目标再造成随制（3000*等级）~（15000+3000*等级）点伤害。(仅在与玩家之间战斗有效)
					if (data.getStates()==0&&data.getvalue(0)<skill2.getSkillhurt()/100D&&Battlefield.isV(20)) {
						int hurt2=(int)skill2.getSkillhurt()*20+Battlefield.random.nextInt(15000);
						ChangeFighting fighting=new ChangeFighting();
						FightingState Accepter2=new FightingState();
						fighting.setChangehp(-hurt2);
						data.ChangeData(fighting,Accepter2);
						list.add(Accepter2);
					}
				}else if (id==9327) {
//					9327|薪火相传|释放九阴纯火攻击敌方,如果有目标死亡则将过量伤害(数值最高的一个)的（4%*等级）转移至敌方剩余血量最高的目标。(仅在与玩家之间战斗有效。)
					if (data.getStates()==1) {
						int hurt2=-changeFighting.getChangehp()-syhp;
						hurt2*=skill2.getSkillhurt()/100D;
						List<ManData> datas=MixDeal.get(false,null,0,manData.getCamp(),0,0,0,0,1,0,battlefield,1);
						if (datas.size()!=0) {
							ManData data2=datas.get(0);
							ChangeFighting changeFighting2=new ChangeFighting();
							changeFighting2.setChangehp(-hurt2);
							FightingState fState2=new FightingState();
							FightingPackage.ChangeProcess(changeFighting2, null, data2, fState2, TypeUtil.H, list, battlefield);
						}
					}
				}
			}
		}
		return -changeFighting.getChangehp();
	}
	/**套装状态添加*/
	public static void addTZState(ManData data,FightingSkill tz_yg,FightingSkill tz_cb,FightingSkill tz_ph,FightingSkill tz_xy,FightingState Accepter){
		/**以戈止戈 法术攻击(仙法,鬼火)时降低对方控制系法术抗性和伤害性法术
		 寸步难行 法术攻击(仙法,鬼火)时有几率降低对方sp
		 平湖秋月 释放法术(仙法,鬼火)时降低目标控制法(冰混睡忘)抗性
		 夕阳箫鼓 释放法术(仙法,鬼火)时降低目标仙法鬼火抗性*/
		if (tz_yg!=null) {
			AddState addState=new AddState();
			addState.setStatename(tz_yg.getSkilltype());
			addState.setStateEffect (tz_yg.getSkillhurt());
			addState.setStateEffect2(tz_yg.getSkillgain());
			addState.setSurplus(tz_yg.getSkillcontinued());
			Accepter.setEndState_1(addState.getStatename());
			data.getAddStates().add(addState);
			data.getQuality().addkr(-tz_yg.getSkillhurt());
			data.getQuality().addks(-tz_yg.getSkillgain());
		}
		if (tz_cb!=null) {
			if (Battlefield.isV(tz_cb.getSkillhitrate())) {
				AddState addState=new AddState();
				addState.setStatename(tz_cb.getSkilltype());
				addState.setStateEffect (tz_cb.getSkillhurt());
				addState.setSurplus(tz_cb.getSkillcontinued());
				Accepter.setEndState_1(addState.getStatename());
				data.getAddStates().add(addState);
				data.setSp2((int)(data.getSp2()-tz_cb.getSkillhurt()));
			}
		}
		if (tz_ph!=null) {
			double v=tz_ph.getSkillhurt();
			if (data.xzstate(tz_ph.getSkilltype())==null&&Battlefield.isV(tz_ph.getSkillhitrate())) {
				v*=2;
			}
			AddState addState=new AddState();
			addState.setStatename(tz_ph.getSkilltype());
			addState.setStateEffect (v);
			addState.setSurplus(tz_ph.getSkillcontinued());
			Accepter.setEndState_1(addState.getStatename());
			data.getAddStates().add(addState);
			data.getQuality().addkr(-v);
		}
		if (tz_xy!=null) {
			double v=tz_xy.getSkillhurt();
			if (data.xzstate(tz_xy.getSkilltype())==null&&Battlefield.random.nextInt(100)<tz_xy.getSkillhitrate()) {
				v*=2;
			}
			AddState addState=new AddState();
			addState.setStatename(tz_xy.getSkilltype());
			addState.setStateEffect (v);
			addState.setSurplus(tz_xy.getSkillcontinued());
			Accepter.setEndState_1(addState.getStatename());
			data.getAddStates().add(addState);
			data.getQuality().addks(-v);
		}
	}
}
