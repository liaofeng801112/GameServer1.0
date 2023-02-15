package come.tool.FightingData;

import java.util.ArrayList;
import java.util.List;

import org.come.bean.PathPoint;

import come.tool.FightingDataAction.PhyAttack;
import come.tool.FightingSpellAction.SpellActionType;
import come.tool.FightingSpellAction.SSCAction;

/**
 * 	杂项处理
 * @author Administrator
 *
 */
public class MixDeal {


	/**
	 * 判断出手类型
	 * 0正常 1普通攻击 2法术 3逃跑   4药 5召回 召唤 6灵宝 7捕捉 8混乱
	 */
	public static int disposetype(String type){
		if (type.equals(TypeUtil.PTGJ))return 1;
		if (type.equals(TypeUtil.JN))return 2;
		if (type.equals("逃跑"))return 3;
		if (type.equals("捕捉"))return 7;
		if (type.equals("药"))return 4;
		//召唤召回判断
		if (type.split("\\&")[0].equals("召唤"))return 5;
		if (type.equals("召回"))return 5;
		if (type.equals("闪现"))return 5;
		return 0;
	}
	/**移动生成*/
	public static void move(int camp,int man,String type,String path,Battlefield battlefield){
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();
		FightingState move=new FightingState();
		move.setCamp(camp);
		move.setMan(man);
		move.setStartState(type);
		move.setEndState(path);
		moves.add(move);
		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);
	}
	/**接受者补充*/
	public static List<ManData> getjieshou(FightingEvents fightingEvents,FightingSkill fightingSkill,ManData myData,List<FightingState> Accepterlist,Battlefield battlefield){
		return getjieshou(fightingEvents, fightingSkill, myData, Accepterlist, battlefield, 0);
	}
	/**接受者补充*/
	public static List<ManData> getjieshou(FightingEvents fightingEvents,FightingSkill fightingSkill,ManData myData,List<FightingState> Accepterlist,Battlefield battlefield,int sum){
		String type=fightingSkill.getSkilltype();
		if (MixDeal.getItself(type)==1) {
			List<ManData> datas=new ArrayList<>();
			if (myData!=null) {datas.add(myData);}
			return datas;
		}
		int MyCamp=myData.getCamp();
		int NoCamp=battlefield.nomy(myData.getCamp());



		int death=MixDeal.gethuo(type);
		int camp=MixDeal.getcamp(type, MyCamp, NoCamp);
		int yin=MixDeal.getyin(type);
		int ren=MixDeal.getren(type);
		int fengyin=MixDeal.getfengyin(type);


		if (type.equals("五行")) {
			// 灵犀-冰消瓦解判定
			if (myData.executeBxwj()){
				death = 1;
				fengyin = 1;
			}
		}

		ManData data=null;
		if (fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
			int path=-1;
			if (death==0) {
				path=battlefield.Datapathhuo(fightingEvents.getAccepterlist().get(0).getCamp(), fightingEvents.getAccepterlist().get(0).getMan());
			}else {
				path=battlefield.Datapath(fightingEvents.getAccepterlist().get(0).getCamp(), fightingEvents.getAccepterlist().get(0).getMan());
			}
			if (path!=-1)
				data=battlefield.fightingdata.get(path);
		}
		if (sum==0) { sum=fightingSkill.getSkillsum(); }
		if (PK_MixDeal.isPK(battlefield.BattleType)) {
			if (sum>1&&fightingSkill.getSkillid()>=1001&&fightingSkill.getSkillid()<=1100) {
				AddState addState=myData.xzstate(TypeUtil.TY_F_YYFQ_S);
				if (addState!=null) {
					sum--;
				}
			}
		}else {
			if (sum>1&&type.equals(TypeUtil.ZD)&&myData.getSkillType(TypeUtil.TY_ZD_BYHY)!=null) { sum-=2; }
		}
		if (fightingSkill.getSkillid()>=1001&&fightingSkill.getSkillid()<=1100) {
			if (Accepterlist!=null) {
				ManData child=battlefield.getChild(myData);
				if (child!=null) {
					String a=null;
					if (fightingSkill.getSkilltype().equals(TypeUtil.ZS)||fightingSkill.getSkilltype().equals(TypeUtil.SSC)){
						a="强"+fightingSkill.getSkilltype();
					}else if (fightingSkill.getSkilltype().equals(TypeUtil.L_GL)) {
						a="甘霖回血";
					}else if (fightingSkill.getSkilltype().equals(TypeUtil.L_PL)) {
						a="霹雳连击";
					}else {
						a="忽视"+fightingSkill.getSkilltype();
					}
					FightingSkill skill=child.getChildSkill(a);
					if (skill!=null) {
						if (fightingSkill.getSkilltype().equals(TypeUtil.ZS)||fightingSkill.getSkilllvl()!=4) {
							Accepterlist.add(MixDeal.getChildSkill(child,skill.getSkillname()));
							sum+=skill.getSkillhurt();
							ChangeFighting changeFighting=new ChangeFighting();
							changeFighting.setChangesum(skill.getSkillcontinued());
							changeFighting.setChangetype(skill.getSkilltype());
							changeFighting.setChangevlaue(skill.getSkillgain());
							myData.ChangeData(changeFighting,new FightingState());
						}
					}
				}
				//判断是否要反转阵营
				if (MixDeal.getFS(battlefield,Accepterlist,myData,NoCamp)) {
					camp=battlefield.nomy(camp);
				}
				if (MixDeal.getTGBG(battlefield,Accepterlist,myData,NoCamp)) {
					camp=battlefield.nomy(camp);
				}
			}
			if (type.equals(TypeUtil.F)||type.equals(TypeUtil.L)||type.equals(TypeUtil.S)||type.equals(TypeUtil.H)||type.equals(TypeUtil.GH)) {
				int hs=1;
				FightingSkill skill=myData.getSkillType(TypeUtil.TZ_YDFC);
				if (skill!=null) {hs=0;}
				return get(true,data, death,camp,yin,ren,fengyin,0,sum,fightingSkill.getCamp(),battlefield,hs);
			}else if (type.equals(TypeUtil.L_CB)||type.equals(TypeUtil.L_FY)) {
				AddState addState=myData.xzstate(TypeUtil.LL);
				if (addState!=null) {sum+=2;}
			}else if (type.equals(TypeUtil.L_GL)) {
				AddState addState=myData.xzstate(TypeUtil.LL);
				if (addState!=null) {death=1;}
			}
		}else if (type.equals("解除控制")) {
			FightingSkill skill=myData.getSkillType(TypeUtil.BB_E_QHFYCX);
			if (skill!=null&&Battlefield.isV(skill.getSkillgain())) {
				sum+=1;
				fightingEvents.getOriginator().setText("强化梵音初晓#2");
			}
		}else if (type.equals("魔界内丹")) {
			FightingSkill skill=myData.getSkillType(TypeUtil.BB_LSDC);
			if (skill!=null) {
				sum+=skill.getSkillsum();
				fightingEvents.getOriginator().setText("利涉大川#2");
			}
		}
		return get(true,data, death,camp,yin,ren,fengyin,0,sum,fightingSkill.getCamp(),battlefield,1);
	}
	/**接收者死亡后补充一个接收者*/
	public static ManData getjieshou(FightingSkill fightingSkill,ManData myData,List<ManData> Accepterlist,Battlefield battlefield){
		int death=0;
		int nocamp=myData.getCamp();
		int yin=0;
		int ren=0;
		int fengyin=0;
		int hs=1;
		FightingSkill skill=myData.getSkillType(TypeUtil.TZ_YDFC);
		if (skill!=null) {hs=0;}
//		skill=myData.getSkillType(TypeUtil.TZ_XMST);
//		if (skill!=null) {yin=1;}
		PathPoint point=new PathPoint(-1,-1);
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.zuoyong(death, nocamp, yin, ren, point, fengyin,0,hs)&&!Accepterlist.contains(data)){
				return data;
			}
		}

		return null;
	}
	/**
	 * 选人机制
	 * 排序类型0hp降 1升 2mp降 3升 4ap降 5升6sp 降 7升-1随机
	 * 之前选中的玩家
	 * 生存条件
	 * 不为相同阵营 不同为true
	 * 隐身条件
	 * 选人类型条件
	 * 封印条件
	 * 选的最大个数
	 * 能否回复血量 0能 1不能
	 * l true 留  false 不留
	 */

	/**
	 *
	 * @param l
	 * @param data
	 * @param death		0活人  1活人或死人 2死人
	 * @param nocamp	不为此阵营
	 * @param yin		0无隐身   1无视    2隐身的
	 * @param ren		0表示选择人 1表示选择灵宝 2表示选召唤兽 3表示选玩家 4玩家或者召唤兽
	 * @param fengyin	0无封印   1无视    2必须为封印
	 * @param yao		0 可以用药
	 * @param size		最多选几个人
	 * @param type		排序类型	0hp降 1升 		2mp降 3升 		4ap降 5升		6sp 降 7升		-1随机
	 * @param battlefield
	 * @param hs		0无昏睡   1无视    2必须为昏睡
	 * @return
	 */
	public static List<ManData> get(boolean l,ManData data,int death,int nocamp,int yin,int ren,int fengyin,int yao,int size,int type,Battlefield battlefield,int hs){
		PathPoint point=new PathPoint(-1,-1);
		List<ManData> datas=new ArrayList<>();
		if (data!=null) {
			int camp=data.getCamp();
			int man=data.getMan();
			if (l) {
				if (!data.zuoyong(death,nocamp,yin,ren, point,fengyin,yao,hs))data=null;
			}else {
				data=null;
			}
			point.setX(camp);
			point.setY(man);
		}
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			if (battlefield.fightingdata.get(i).zuoyong(death, nocamp, yin, ren, point, fengyin,yao,hs))
				datas.add(battlefield.fightingdata.get(i));
		}
		if (type==-1)type=Battlefield.random.nextInt(8);
		boolean a=false;
		if (type%2==0) a=true;
		for (int i = 0; i < datas.size(); i++) {
			for (int j = 1; j < datas.size(); j++) {
				ManData data1=datas.get(j-1);
				double value1=data1.getvalue(type);
				ManData data2=datas.get(j);
				double value2=data2.getvalue(type);
				if ((a&&value1<value2)||(!a&&value1>value2)) {
					datas.set((j - 1), data2);
					datas.set(j, data1);
				}
			}
		}
		for (int i = datas.size()-1; i>=0&&datas.size()>size; i--) {
			datas.remove(i);
		}
		if (data!=null) {
			if (datas.size()!=0&&datas.size()>=size)datas.remove(datas.size()-1);
			datas.add(0,data);
		}
		return datas;
	}
	/**隐身清除*/
	public static void ys(ManData data,boolean is,Battlefield battlefield){
		List<FightingState> States=null;
		AddState addState=data.xzstate("隐身");
		if (addState!=null) {
			data.getAddStates().remove(addState);
			FightingState org=new FightingState();
			org.setCamp(data.getCamp());
			org.setMan(data.getMan());
			org.setStartState("代价");
			org.setEndState_2("隐身");
			States=new ArrayList<>();
			States.add(org);
		}
		if (is) {
			FightingSkill skill=data.getSkillType(TypeUtil.TZ_XMST);
			if (skill!=null) {
				for (int i = battlefield.fightingdata.size()-1;i>=0; i--) {
					ManData manData=battlefield.fightingdata.get(i);
					if (manData.getStates()==0&&manData.getCamp()!=data.getCamp()&&manData.getMan()<10) {
						addState=manData.xzstate("隐身");
						if (addState!=null) {
							manData.getAddStates().remove(addState);
							FightingState org=new FightingState();
							org.setCamp(manData.getCamp());
							org.setMan(manData.getMan());
							org.setStartState("代价");
							org.setEndState_2("隐身");
							if (States==null) {
								States=new ArrayList<>();
							}
							States.add(org);
						}
					}
				}
			}
		}
		if (States!=null) {
			FightingEvents fightingEvents=new FightingEvents();
			fightingEvents.setAccepterlist(States);
			battlefield.NewEvents.add(fightingEvents);
		}
	}
	/**判断施法技能是否能触发玄妙神通*/
	public static boolean isTZ_XMST(int id){
		return (id>=1041&&id<=1100)||id==9270||id==9286||id==9287||id==9307||id==9372;
	}
	/**召回*/
	public static void zhaohui(ManData manData,FightingState fightingState,Battlefield battlefield){
		//清除未出手的指令
		if (manData.getType()!=0&&manData.getType()!=2) {
			for (int i = 0; i < battlefield.Events.size(); i++) {
				if (battlefield.Events.get(i).getOriginator().getCamp()==manData.getCamp()
						&&battlefield.Events.get(i).getOriginator().getMan()==manData.getMan()) {
					battlefield.Events.remove(i);
					break;
				}
			}
		}
		if (manData.getType()==1) {
			battlefield.Addbb(null,manData.getCamp(),manData.getMan());
		}
		//移除buff
		List<GroupBuff> list=battlefield.ClearBuff(manData);
		if (list!=null) {
			for (int i = list.size()-1; i >=0; i--) {
				String type=list.get(i).getBuffType();
				//义薄云天   明察秋毫 双管齐下 讨命 牵制重新选一个
				if (type.equals(TypeUtil.YBYT)||type.equals(TypeUtil.BB_MCQH)||
						type.equals(TypeUtil.BB_SGQX)||type.equals(TypeUtil.BB_TM)||
						type.equals(TypeUtil.BB_QZ)) {
					GroupBuff buff=battlefield.addbuff(manData.getCamp(),type);
					if (buff!=null) {
						list.remove(i);
					}
				}
			}
			fightingState.setBuff(getBuffStr(list,false));
		}

		if (manData.getSkillType("遗产")!=null) {
			first(manData, "遗产",battlefield);
		}
		if (manData.getSkillType(TypeUtil.BB_GQLX)!=null) {
			first(manData, TypeUtil.BB_GQLX,battlefield);
		}
		if (manData.getSkillType(TypeUtil.BB_JS)!=null) {
			first(manData,TypeUtil.BB_JS,battlefield);
		}
		FightingSkill skill=manData.getSkillType(TypeUtil.BB_TDTS);
		if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
			first(manData,TypeUtil.BB_TDTS,battlefield);
		}
	}
	/**死亡离场判断*/
	public static void DeathSkill(ManData manData,FightingState fightingState,Battlefield battlefield){
		if (manData.getStates()==1&&manData.xzstate("归墟")==null) {
			if (manData.getSkillType("复活")!=null) {
				first(manData,"复活",battlefield);
				return;
			}
			if (battlefield.CurrentRound > 3 && manData.getSkillType(TypeUtil.BB_JR)!=null) {
				first(manData,"复活",battlefield);
				return;
			}
		}
		// 回合判断
		if (manData.getStates()==1 && battlefield.CurrentRound > 3) {
			if (manData.executeJtcl()) {
				int path=battlefield.Datapath(manData.getCamp(),manData.getMan()-5);
				if (path!=-1) {
					ManData renData=battlefield.fightingdata.get(path);
					first(renData, "闪现&"+manData.getId(),battlefield);
					return;
				}
			}
		}

		zhaohui(manData,fightingState,battlefield);
		if (manData.getType()==0||manData.getType()==2) {
			FightingSkill skill=manData.getSkillType(TypeUtil.TJ_FSX);
			if (skill!=null) {
				if (skill.getSkillgain()>Battlefield.random.nextInt(100)) {
					skill.setSkillsum(skill.getSkillsum()-1);
					if (skill.getSkillsum()<=0) {
						manData.getSkills().remove(skill);
					}
					manData.addAddState("复活", 1.0*manData.getHp_z(), 0, 2);
					return;
				}
			}
			skill=manData.getSkillType(TypeUtil.TJ_FHFW);
			if (skill!=null) {
				skill.setSkillsum(skill.getSkillsum()-1);
				if (skill.getSkillsum()<=0) {
					manData.getSkills().remove(skill);
				}
				manData.addAddState("复活", 1.0*manData.getHp_z(), 0, 2);
				return;
			}
			//强化返生香
			skill=manData.getSkillType(TypeUtil.TJ_ZQLR);
			if (skill!=null) {
				skill.setSkillsum(skill.getSkillsum()-1);
				if (skill.getSkillsum()<=0) {
					manData.getSkills().remove(skill);
				}
				manData.addAddState("复活", 1.0*manData.getHp_z(), 0, 2);
				return;
			}
		}

		// 雪中送炭
		manData.executeXzst(battlefield);
		manData.executeBhnl(battlefield);
		manData.executeYcbb(battlefield);

//法门 鱼龙潜跃 虎踞龙蟠
		AddState addState = manData.xzstate("鱼龙潜跃");
		if (addState != null) {
			FightingEvents moveEvents=new FightingEvents();
			int camp=manData.getCamp();
			int camp1 =battlefield.nomy(camp);
			List<FightingState> Accepterlist=new ArrayList<>();
			FightingState Accepter=new FightingState();
			List<ManData> datas1= SSCAction.minhp(camp1, 1,battlefield);
			ManData data=datas1.get(0);
			fightingState=new FightingState();
			Accepter.setStartState("药");
			ChangeFighting changeFightinghf=new ChangeFighting();
			changeFightinghf.setChangehp((int)addState.getStateEffect());
			data.ChangeData(changeFightinghf, Accepter);

			//FightingPackage.ChangeProcess(changeFightinghf,manData,data,Accepter,"技能",Accepterlist,battlefield);
			Accepterlist.add(Accepter);
			moveEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(moveEvents);
		}
		addState = manData.xzstate("虎踞龙蟠");
		if (addState != null) {
			FightingEvents moveEvents=new FightingEvents();
			int camp=manData.getCamp();
			int camp1 =battlefield.nomy(camp);
			List<FightingState> Accepterlist=new ArrayList<>();
			FightingState Accepter=new FightingState();
			List<ManData> datas1=SSCAction.minhp(camp1, 1,battlefield);
			ManData data=datas1.get(0);
			fightingState=new FightingState();
			Accepter.setStartState("药");
			ChangeFighting changeFightinghf=new ChangeFighting();
			changeFightinghf.setChangehp((int)addState.getStateEffect());
			data.ChangeData(changeFightinghf, Accepter);

			Accepterlist.add(Accepter);
			moveEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(moveEvents);
		}


		if (manData.getStates()==1 && manData.executeSwbz()) {
			List<ManData> zwdy = battlefield.getZW(manData);
			if (zwdy.size() > 0) {
				// 死亡爆炸
				FightingEvents moveEvents=new FightingEvents();
				List<FightingState> zls = new ArrayList<>();
				FightingState ace = new FightingState();
				ace.setCamp(manData.getCamp());
				ace.setMan(manData.getMan());
				ace.setText("啊我要炸了");
				zls.add(ace);
				for (int j = zwdy.size() - 1; j >= 0; j--) {
					FightingState ace1 = new FightingState();
					ace1.setSkillskin("1056");
					ManData nomyData2 = zwdy.get(j);
					if (nomyData2.getStates() != 0)
						continue;
					ChangeFighting acec1 = new ChangeFighting();
					int ap = manData.getHp_z() / 5;
					acec1.setChangehp((int) -ap);
					FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, TypeUtil.H, zls, battlefield);
				}
				moveEvents.setAccepterlist(zls);
				battlefield.NewEvents.add(moveEvents);
			}
		}

		Appearance(manData,battlefield);
		if (manData.getCamp()==1)battlefield.MyDeath++;
		else battlefield.NoDeath++;

		if (manData.getType()==1) {//召唤兽死亡
			if (battlefield.bbDeathPoint==null) {battlefield.bbDeathPoint=new PathPoint();}
			if (manData.getCamp()==1) {battlefield.bbDeathPoint.setX(battlefield.bbDeathPoint.getX()+1);}
			else {battlefield.bbDeathPoint.setY(battlefield.bbDeathPoint.getY()+1);}
		}
	}
	/**闪现判断*/
	public static void Appearance(ManData manData,Battlefield battlefield){
		int path=battlefield.Datapath(manData.getCamp(),manData.getMan()-5);
		if (path!=-1) {
			ManData renData=battlefield.fightingdata.get(path);
//			9365|飞蛾扑火|自己每有一个召唤兽死亡,本次战斗中提升自己（0.4%*等级）忽视抗鬼火,最多提升（2%*等级）点。仅在与玩家之间战斗有效)
			if (manData.getStates()==1) {
				FightingSkill skill=renData.skillId(9365);
				if (skill!=null) {
					skill.setSkillgain(skill.getSkillgain()+1);
					if (skill.getSkillgain()<=5) {
						renData.getQuality().setRolehsgh(renData.getQuality().getRolehsgh()+skill.getSkillhurt());
					}
				}
			}
			if (renData.getType()==0&&renData.getAppendSkill(9348)==null) {
				//判断是否有需要闪现的人物
				for (int i = 0; i < renData.getPets().size(); i++) {
					if (renData.getPets().get(i).getPlay()==0) {
						int xs = manData.executeBfly(battlefield);
						xs += manData.executeBdxz(battlefield);
						int p=renData.getPets().get(i).getsx(PK_MixDeal.isBB(battlefield.BattleType)?1000:xs);
						if (p==2) {
							first(renData, "闪现&"+renData.getPets().get(i).getHang().getId(),battlefield);
							return;
						}else if (p==1) {
							return;
						}
					}
				}
			}
		}
	}
	/**进场技能*/
	public static void Approach(ManData manData,FightingState fightingState,Battlefield battlefield){
		//添加buff
		List<GroupBuff> list=battlefield.addBuff(manData);
		if (list!=null) {
			fightingState.setBuff(getBuffStr(list,true));
		}

		// 灵犀-进程处理
		if (manData.getType() < 3) {
			manData.before(null,fightingState);
		}

		if (manData.getSkillType(TypeUtil.BB_DT)!=null) {
			first(manData,TypeUtil.BB_DT,battlefield);
		}
		if (manData.getSkillType("击其不意")!=null) {
			first(manData, "击其不意",battlefield);
		} else {
			if (manData.executeYmjr()) {
				first(manData, "一鸣惊人",battlefield);
			}
		}
		if (manData.getSkillType(TypeUtil.BB_RHTY)!=null) {
			first(manData, TypeUtil.BB_RHTY,battlefield);
		}
		if (PK_MixDeal.isPK(battlefield.BattleType)) {
			if (manData.getSkillType(TypeUtil.BB_YAHY)!=null) {
				first(manData, TypeUtil.BB_YAHY,battlefield);
			}
			if (manData.getSkillType(TypeUtil.BB_NZQK)!=null) {
				first(manData, TypeUtil.BB_NZQK,battlefield);
			}
		}
//		if (manData.getSkillType(TypeUtil.BB_JR)!=null) {
//			AddState addState=new AddState();
//			addState.setStatename("冷却");
//			addState.setStateEffect(1838);
//			addState.setSurplus(3);
//			manData.getAddStates().add(addState);
//		}
	}
	/**
	 * 插队指令
	 */
	public static void first(ManData manData,String type,Battlefield battlefield){
		FightingEvents moveEvents=new FightingEvents();
		FightingState move=new FightingState();
		moveEvents.setOriginator(move);
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setStartState(type);
		if (type.equals("击其不意")) {
			move.setStartState("普通攻击");
			if (Battlefield.isV(manData.executeFbgs(7, null))){
				move.setStartState("技能");
				move.setEndState("兵临城下");
			}
			battlefield.erwai.add(0,moveEvents);
		}else if (type.equals("复活")||type.equals(TypeUtil.BB_RHTY)||type.equals(TypeUtil.BB_YAHY)||type.equals(TypeUtil.BB_NZQK)) {
			battlefield.erwai.add(0,moveEvents);
		}else if (type.equals("一鸣惊人")) {
			FightingSkill skill = manData.skillIds(1044,1049,1054,1059,1064);
			if (skill != null) {
				manData.ymjr = true;
				move.setStartState("技能");
				move.setEndState(skill.getSkillname());
				battlefield.erwai.add(0,moveEvents);
			}
		}else if (type.equals(TypeUtil.BB_TDTS)) {
			FightingSkill skill=manData.getSkillType(type);
			if (skill!=null&&manData.getSkin()!=null) {
				move.setEndState(manData.getSkin());
				battlefield.erwai.add(0,moveEvents);
			}
		}else {
			if (type.equals("遗产")) {
				try {
					move.setEndState((double)(manData.getMp()/manData.getMp_z())+"");
				} catch (Exception e) {
					// TODO: handle exception
					move.setEndState(1+"");
				}
			}
			if (type.equals(TypeUtil.BB_GQLX)) {
			}
			if (type.equals(TypeUtil.BB_JS)|| type.equals(TypeUtil.BB_DT)) {
				FightingSkill skill=manData.getSkillType(type);
				if (skill!=null) {
					move.setEndState(skill.getSkillsum()+"");
				}
			}
			battlefield.erwai.add(moveEvents);
		}
	}
	/**封印判断*/
	public static int getfengyin(String type){
		if (type.equals("封印")||type.equals("药")||type.equals("解除控制")||type.equals("解除召唤兽控制")||type.equals("作鸟兽散")) {
			return 1;
		}
		return 0;
	}
	/**判断是否对自己生效的技能*/
	public static int getItself(String type){
		if (type.equals(TypeUtil.TZ_FHJY)||type.equals(TypeUtil.TZ_PFCZ)||type.equals(TypeUtil.TZ_HGFZ)) {
			return 1;
		}
		return 0;
	}
	/**人判断*/
	public static int getren(String type){
		if (type.equals("灵宝技能")) {
			return 1;
		}else if (type.equals(TypeUtil.BB_SS)) {

		}
		return 0;
	}
	/**隐身判断*/
	public static int getyin(String type){
		if (type.equals("作鸟兽散")) {
			return 1;
		}
		return 0;
	}
	/**不为阵营判断*/
	public static int getcamp(String type,int mycamp,int nomycamp){
		if (type.equals("混乱技")||type.equals("药")||type.equals("移花接木")
				||type.equals(TypeUtil.FB_DSY)||type.equals("荆棘束身")) {
			return -1;
		}
		if (type.equals(TypeUtil.JS)||type.equals(TypeUtil.KX)||type.equals(TypeUtil.LL)
				||type.equals(TypeUtil.L_GL)||type.equals("庇护")||type.equals(TypeUtil.MH)
				||type.equals("解除控制")||type.equals("回血技")
				||type.equals("遗产")||type.equals("作鸟兽散")||type.equals("隐身技")
				||type.equals(TypeUtil.FB_YSJL)||type.equals(TypeUtil.FB_JJL)
				||type.equals(TypeUtil.FB_DSC)||type.equals(TypeUtil.FB_QBLLT)
				||type.equals(TypeUtil.FB_HLZ)||type.equals(TypeUtil.FB_YMGS)
				||type.equals(TypeUtil.FB_JQB)||type.equals(TypeUtil.FB_BLD)
				||type.equals(TypeUtil.FB_JLJS)||type.equals(TypeUtil.FB_FTY)
				||type.equals(TypeUtil.TZ_CXYF)||type.equals(TypeUtil.TY_MH_RSSQ)
				||type.equals(TypeUtil.BB_E_BLBJ)||type.equals(TypeUtil.BB_CNHK)
				||type.equals(TypeUtil.TZ_MXJX)||type.equals(TypeUtil.TZ_BDBQ)
				||type.equals("凝神一击")||type.equals("幻影迷踪")
				||type.equals("兽魂俯首")||type.equals("刚柔兼备")
				||type.equals("偷梁换柱")||type.equals("遮天蔽日")
				||type.equals("法魂护体")) {
			return nomycamp;
		}
		return mycamp;
	}
	/**判断对自己作用的技能*/
	/**活人判断*/
	public static int gethuo(String type){
		if (type.equals("回血技")||type.equals(TypeUtil.BB_CNHK)) {return 1;}
		if (type.equals("作鸟兽散")) {return 2;}
		return 0;
	}
	/**伤害性法术狂暴*/
	public static double addition(double kbl,FightingState ace,ManData myData,ManData data,String type){
		double xs=100;
		if (type.equals(TypeUtil.SSC)) {
			kbl+=myData.getQuality().getRolesskb();
			xs+=myData.getQuality().getBsccd();
		}else if (type.equals(TypeUtil.GH)) {
			kbl+=myData.getQuality().getRoleghkb() + myData.fskbjl;
			xs+=myData.getQuality().getBghcd() + myData.fskbcd;
			//9362|余烬复燃|使用鬼火狂暴时每杀死一个敌方目标,下回为自己增加（0.4%*等级）点鬼火狂暴程度。
			AddState addState=data.xzstate(TypeUtil.TY_GH_YJFR);
			if (addState!=null) {xs+=addState.getStateEffect();}
		}else if (type.equals(TypeUtil.F)) {
			kbl+=myData.getQuality().getRoleffkb() + myData.fskbjl;
			xs+=myData.getQuality().getBffcd() + myData.fskbcd;
		}else if (type.equals(TypeUtil.H)) {
			kbl+=myData.getQuality().getRolehfkb() + myData.fskbjl;
			xs+=myData.getQuality().getBhfcd() + myData.fskbcd;
			//9321|烘炉点雪|使用火法攻击目标后如果目标没有死亡,则下回合使用火法对该目标的狂暴几率增加（1%*等级）(仅在与玩家之间战斗有效)
			AddState addState=data.xzstate(TypeUtil.TY_H_HLDX);
			if (addState!=null) {kbl+=addState.getStateEffect();}
		}else if (type.equals(TypeUtil.S)) {
			kbl+=myData.getQuality().getRolesfkb() + myData.fskbjl;
			xs+=myData.getQuality().getBsfcd() + myData.fskbcd;
			AddState addState=myData.xzstate(TypeUtil.BB_E_BLBJ);
			if (addState!=null) {kbl-=addState.getStateEffect2();}
		}else if (type.equals(TypeUtil.L)) {
			kbl+=myData.getQuality().getRolelfkb() + myData.fskbjl;
			xs+=myData.getQuality().getBlfcd() + myData.fskbcd;
		}
		//狂暴
		if (Battlefield.isV(kbl)) {
			ace.setProcessState("狂暴");
			xs+=50;
			xs/=100D;
			return xs;
		}
		return 0;//没触发
	}
	public static double addition(double ap,double kbl,FightingState ace,ManData myData,ManData data,String type){
		double xs=100;
		if (type.equals(TypeUtil.SSC)) {
			kbl+=myData.getQuality().getRolesskb();
			xs+=myData.getQuality().getBsccd();
		}else if (type.equals(TypeUtil.GH)) {
			kbl+=myData.getQuality().getRoleghkb();
			xs+=myData.getQuality().getBghcd();
			//9362|余烬复燃|使用鬼火狂暴时每杀死一个敌方目标,下回为自己增加（0.4%*等级）点鬼火狂暴程度。
			AddState addState=data.xzstate(TypeUtil.TY_GH_YJFR);
			if (addState!=null) {xs+=addState.getStateEffect();}
		}else if (type.equals(TypeUtil.F)) {
			kbl+=myData.getQuality().getRoleffkb();
			xs+=myData.getQuality().getBffcd();
		}else if (type.equals(TypeUtil.H)) {
			kbl+=myData.getQuality().getRolehfkb();
			xs+=myData.getQuality().getBhfcd();
			//9321|烘炉点雪|使用火法攻击目标后如果目标没有死亡,则下回合使用火法对该目标的狂暴几率增加（1%*等级）(仅在与玩家之间战斗有效)
			AddState addState=data.xzstate(TypeUtil.TY_H_HLDX);
			if (addState!=null) {kbl+=addState.getStateEffect();}
		}else if (type.equals(TypeUtil.S)) {
			kbl+=myData.getQuality().getRolesfkb();
			xs+=myData.getQuality().getBsfcd();
			AddState addState=myData.xzstate(TypeUtil.BB_E_BLBJ);
			if (addState!=null) {kbl-=addState.getStateEffect2();}
		}else if (type.equals(TypeUtil.L)) {
			kbl+=myData.getQuality().getRolelfkb();
			xs+=myData.getQuality().getBlfcd();
		}
		//狂暴
		if (Battlefield.random.nextInt(100)+1<kbl) {
			ace.setProcessState("狂暴");
			xs+=50;
			xs/=100D;
			return (long) (ap*xs);
		}
		return ap;//没触发
	}
	/**狂暴判断*/
	public static double addition(FightingState ace,double ap,ManData mydaData,String type){
		double kbl=0;
		double kbcd = 0;
		if (type.equals(TypeUtil.SSC)) {
			kbl=mydaData.getQuality().getRolesskb();
			kbcd = mydaData.getQuality().getBsccd() / 100;
		}else if (type.equals(TypeUtil.GH)) {
			kbl=mydaData.getQuality().getRoleghkb();
		}else if (type.equals(TypeUtil.F)) {
			kbl=mydaData.getQuality().getRoleffkb();
		}else if (type.equals(TypeUtil.H)) {
			kbl=mydaData.getQuality().getRolehfkb();
		}else if (type.equals(TypeUtil.S)) {
			kbl=mydaData.getQuality().getRolesfkb();
		}else if (type.equals(TypeUtil.L)) {
			kbl=mydaData.getQuality().getRolelfkb();
		}
		//狂暴
		if (Battlefield.random.nextInt(100)+1<kbl) {
			ace.setProcessState("狂暴");
			return (long) (ap*(1.5+kbcd));
		}
		//没触发
		return ap;
	}
	/**作鸟兽散 忽如一夜判断*/
	public static void flee(Battlefield battlefield,int type){
		boolean is_a=true,is_b=true;
		ManData a_bb = null,b_bb = null;
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getType()!=1||data.getStates()!=0){continue;}
			if (a_bb!=null&&data.getCamp()==a_bb.getCamp()) {is_a=false;continue;}
			else if (b_bb!=null&&data.getCamp()==b_bb.getCamp()) {is_b=false;continue;}
			else if (a_bb==null) {a_bb=data;}
			else if (b_bb==null) {b_bb=data;}
		}
		if (is_a) {
			if (a_bb!=null) {
				if (battlefield.Datapathhuo(a_bb.getCamp(), a_bb.getMan()-5)==-1) {
					FightingSkill skill=a_bb.getSkillType(type==0?"作鸟兽散":TypeUtil.BB_E_HRYY);
					if (skill!=null) {SpellActionType.getActionById(type==0?14:15).spellAction(a_bb, skill, null, battlefield);}
				}
			}
		}
		if (is_b) {
			if (b_bb!=null) {
				if (battlefield.Datapathhuo(b_bb.getCamp(), b_bb.getMan()-5)==-1) {
					FightingSkill skill=b_bb.getSkillType(type==0?"作鸟兽散":TypeUtil.BB_E_HRYY);
					if (skill!=null) {SpellActionType.getActionById(type==0?14:15).spellAction(b_bb, skill, null, battlefield);}
				}
			}
		}
	}

	/**高低召唤兽支援判断，进入此方法必然是没有召唤兽的*/
	public static void zhszy(Battlefield battlefield,ManData man){
		// 人物死亡则直接返回
		if (man.getStates()!=0){
			return;
		}

		FightingSkill skill = man.getSkillType(TypeUtil.TJ_GJZY);
		if (skill == null) {
			skill = man.getSkillType(TypeUtil.TJ_DJZY);
			if (skill == null) {
				// 没有召唤兽支援特技返回
				return;
			}
		}
		if (Battlefield.random.nextInt(100) < skill.getSkillgain()) {
			if (man.getType()==0&&man.getAppendSkill(9348)==null) {
				//判断是否有需要闪现的召唤兽
				for (int i = 0; i < man.getPets().size(); i++) {
					FightingSummon  fs = man.getPets().get(i);
					if (fs.getPlay()==0) {
						int p=fs.getsx(0);
						if (p==2) {
							first(man, "闪现&"+fs.getHang().getId(),battlefield);
							return;
						}else if (p==1) {
							return;
						}
					}
				}
			}
		}
	}

	/**患难与共判断*/
	public static void HNYG(Battlefield battlefield){
		boolean is_Ba=true,is_Bb=true,is_Ra=true,is_Rb=true;
		ManData a_bb=null,b_bb=null,a_role=null,b_role=null;
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0){continue;}
			if (data.getType()==0) {
				if (a_role!=null&&data.getCamp()==a_role.getCamp()) {is_Ra=false;continue;}
				else if (b_role!=null&&data.getCamp()==b_role.getCamp()) {is_Rb=false;continue;}
				else if (a_role==null) {a_role=data;}
				else if (b_role==null) {b_role=data;}
			}else if (data.getType()==1) {
				if (a_bb!=null&&data.getCamp()==a_bb.getCamp()) {is_Ba=false;continue;}
				else if (b_bb!=null&&data.getCamp()==b_bb.getCamp()) {is_Bb=false;continue;}
				else if (a_bb==null) {a_bb=data;}
				else if (b_bb==null) {b_bb=data;}
			}

		}
		if (is_Ba&&is_Ra) {
			if (a_bb!=null&&a_role!=null&&(a_bb.getMan()-5)==a_role.getMan()) {
				FightingSkill skill=a_bb.getSkillType(TypeUtil.BB_E_HNYG);
				if (skill!=null) {SpellActionType.getActionById(16).spellAction(a_bb, skill, null, battlefield);}
			}
		}
		if (is_Bb&&is_Rb) {
			if (b_bb!=null&&b_role!=null&&(b_bb.getMan()-5)==b_role.getMan()) {
				FightingSkill skill=b_bb.getSkillType(TypeUtil.BB_E_HNYG);
				if (skill!=null) {SpellActionType.getActionById(16).spellAction(b_bb, skill, null, battlefield);}
			}
		}
	}
	/**技能对战类型是否有效*/
	public static boolean getyx(int id,int type){
		if (PK_MixDeal.isPK(type) || type == 886) {
			return true;
		}else {
			if (id>=3025&&id<=3030||id==1828)return false;
			return true;
		}

	}
	/**顾影自怜判断*/
	public static boolean isgyzl(int camp,Battlefield battlefield){
		int size=0;
		int sysum=0;
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()==2) continue;
			size++;
			if (camp!=data.getCamp())continue;
			if (data.getStates()!=0) continue;
			if (data.xzstate("封印")!=null)continue;
			if (data.xzstate("昏睡")!=null)continue;
			if (data.xzstate("混乱")!=null)continue;
			if (data.xzstate("遗忘")!=null)continue;
			if (data.xzstate("中毒")!=null)continue;
			sysum++;
		}
		if (sysum/(double)size<=0.4)
			return true;
		return false;
	}
	/**孙小圣移动生成*/
	public static FightingEvents sxsmove(ManData data,ManData manData,Battlefield battlefield){
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();

		FightingState gjz=new FightingState();
		gjz.setCamp(manData.getCamp());
		gjz.setMan(manData.getMan());
		gjz.setStartState("特效1");
		moves.add(gjz);

		FightingState move=new FightingState();
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setSkillskin("10");
		move.setStartState("技能移动");
		move.setEndState(data.getCamp()+"|"+data.getMan());
		moves.add(move);

		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);

		//返回分身回来动作
		FightingEvents events=new FightingEvents();
		List<FightingState> list=new ArrayList<>();
		FightingState gl=new FightingState();
		gl.setCamp(manData.getCamp());
		gl.setMan(manData.getMan());
		gl.setSkillskin("11");
		gl.setStartState("技能移动");
		gl.setEndState(data.getCamp()+"|"+data.getMan());
		list.add(gl);
		events.setAccepterlist(list);
		return events;
	}

	//TODO 莲生技能效果生成（原地射箭）  技能顺序倒序加载
	/**
	 *
	 * @param data				怪
	 * @param battlefield
	 * @param manData			我方
	 */
	public static void lsmove(ManData data,Battlefield battlefield,ManData manData){
		//4000是孟极 分身影子    其他是动作类型              10和4000需要移动位置         9:攻击
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();

		//技能移动
		FightingState move=new FightingState();
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setSkillskin("1237");
		move.setStartState("技能移动");
		move.setEndState(data.getCamp()+"|"+data.getMan()+"|3");		//技能移动，从我方到怪
		moves.add(move);

		//怪物被攻击
		FightingState gw=new FightingState();
		gw.setCamp(data.getCamp());
		gw.setMan(data.getMan());
		gw.setSkillskin("7004");
		gw.setStartState("被攻击");
		//gw.setSkillsy("hit");
		moves.add(gw);

		//莲生不动
		FightingState gjz=new FightingState();
		gjz.setCamp(manData.getCamp());
		gjz.setMan(manData.getMan());
		gjz.setStartState("特效2");			//特效2时间稍短   特效2<法术攻击<特效1
		moves.add(gjz);

		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);
	}
	/**生成技能移动*/
	public static void skillmove(List<ManData> datas,Battlefield battlefield,ManData manData,String type){
		//4000是孟极 分身影子    其他是动作类型              10和4000需要移动位置         9:攻击
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();
		for (int i =  datas.size()-1; i >=0; i--) {
			ManData data=datas.get(i);
			FightingState move=new FightingState();
			move.setCamp(manData.getCamp());
			move.setMan(manData.getMan());
			move.setSkillskin(type);
			move.setStartState("技能移动");
			move.setEndState(data.getCamp()+"|"+data.getMan()+"|3");
			moves.add(move);
		}
		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);
	}
	public static FightingState skillmove(ManData data,ManData manData,String type){
		return skillmove(data, manData, type,3);
	}
	public static FightingState skillmove(ManData data,ManData manData,String type,int dir){
		//4000是孟极 分身影子    其他是动作类型              10和4000需要移动位置         9:攻击
		FightingState move=new FightingState();
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setSkillskin(type);
		move.setStartState("技能移动");
		if (dir==-1) {
			move.setEndState(data.getCamp()+"|"+data.getMan());
		}else {
			move.setEndState(data.getCamp()+"|"+data.getMan()+"|"+dir);
		}
		return move;
	}
	//势如破竹开场动画
	public static void BB_SRPZ(List<ManData> datas,Battlefield battlefield,ManData manData,String skin,FightingSkill skill){
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();
		for (int i =  datas.size()-1; i >=0; i--) {
			ManData data=datas.get(i);
			FightingState move=new FightingState();
			move.setCamp(data.getCamp());
			move.setMan(data.getMan());
			move.setSkillskin(TypeUtil.BB_SRPZ);
			move.setStartState(TypeUtil.JN);
			moves.add(move);
		}
		FightingState move=new FightingState();
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setSkillskin(skin);
		move.setStartState("法术攻击");
		if (skill!=null) {manData.daijia(skill,move,battlefield);}
		moves.add(move);
		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);
	}
	public static void BB_DNTG(List<ManData> datas,Battlefield battlefield,ManData manData,String skin,FightingSkill skill){
		FightingEvents moveEvents=new FightingEvents();
		List<FightingState> moves=new ArrayList<>();
		for (int i =  datas.size()-1; i >=0; i--) {
			ManData data=datas.get(i);
			FightingState move=new FightingState();
			move.setCamp(data.getCamp());
			move.setMan(data.getMan());
			move.setSkillskin("1252");
			move.setStartState(TypeUtil.JN);
			moves.add(move);
		}
		FightingState move=new FightingState();
		move.setCamp(manData.getCamp());
		move.setMan(manData.getMan());
		move.setSkillskin(skin);
		move.setStartState("法术攻击");
		if (skill!=null) {manData.daijia(skill,move,battlefield);}
		moves.add(move);
		moveEvents.setAccepterlist(moves);
		battlefield.NewEvents.add(moveEvents);
	}

	/**返回一个主要打击人*/
	public static List<ManData> getdaji(int size,int nocamp,FightingEvents fightingEvents,Battlefield battlefield){
		ManData data=null;
		if (fightingEvents!=null&&fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
			int path=battlefield.Datapathhuo(fightingEvents.getAccepterlist().get(0).getCamp(),fightingEvents.getAccepterlist().get(0).getMan());
			if (path!=-1)data=battlefield.fightingdata.get(path);
		}
		List<ManData> datas=MixDeal.get(true, data, 0, nocamp, 0, 0, 0, 0, size, -1, battlefield,1);
		return datas;
	}
	/**
	 * 孩子喊话拼接
	 */
	public static String KAN1="看我的#Y";
	public static String KAN2="#46";
	public static FightingState getChildSkill(ManData child,String skillname){
		FightingState state=new FightingState();
		state.setCamp(child.getCamp());
		state.setMan(child.getMan());
		state.setStartState("代价");
		StringBuffer buffer=new StringBuffer();
		buffer.append(KAN1);
		buffer.append(skillname);
		buffer.append(KAN2);
		state.setText(buffer.toString());
		return state;
	}
	/**反射机制判断*/
	public static boolean getFS(Battlefield battlefield,List<FightingState> Accepterlist,ManData manData,int noCamp){
		GroupBuff groupBuff=battlefield.getBuff(noCamp, TypeUtil.BB_FBSF);
		if (groupBuff!=null&&Battlefield.isV(groupBuff.getValue())) {
			//触发成功,特效移除
			List<GroupBuff> removeBuffs=battlefield.ClearBuff(groupBuff);
			if (removeBuffs!=null) {
				FightingState state=new FightingState();
				state.setCamp(manData.getCamp());
				state.setMan(manData.getMan());
				state.setStartState("代价");
				state.setBuff(MixDeal.getBuffStr(removeBuffs, false));
				state.setSkillskin(groupBuff.getBuffType());
				Accepterlist.add(state);
			}
			return true;
		}
		return false;
	}

	public static boolean getTGBG(Battlefield battlefield,List<FightingState> Accepterlist,ManData manData,int noCamp){
		GroupBuff groupBuff=battlefield.getBuff(noCamp,"23009");
		if (groupBuff!=null&&Battlefield.isV(groupBuff.getValue())) {
			//触发成功,特效移除
			List<GroupBuff> removeBuffs=battlefield.ClearBuff(groupBuff);
			if (removeBuffs!=null) {
				FightingState state=new FightingState();
				state.setCamp(manData.getCamp());
				state.setMan(manData.getMan());
				state.setStartState("代价");
				state.setBuff(MixDeal.getBuffStr(removeBuffs, false));
				state.setSkillskin(groupBuff.getBuffType());
				Accepterlist.add(state);
			}
			return true;
		}
		return false;
	}
	//处理报复
	public static void clbf(FightingSkill skill,ManData manData,Battlefield battlefield){
		if (!PK_MixDeal.isPK(battlefield.BattleType)) {
			return;
		}
		for (int i = battlefield.fightingdata.size()-1; i >=0; i--) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0) {continue;}
			if (data==manData) {continue;}
			if (data.getType()==1) {
				if (data.xzstate(TypeUtil.FY)==null) {
					if (data.getCamp()==manData.getCamp()) {//回源
						FightingSkill fightingSkill=data.getSkillType(TypeUtil.BB_HY);
						if (fightingSkill!=null&&Battlefield.isV(fightingSkill.getSkillhitrate())) {
							List<FightingState> fightingStates=new ArrayList<>();
							FightingState org=new FightingState();
							org.setCamp(manData.getCamp());
							org.setMan(manData.getMan());
							org.setStartState(TypeUtil.JN);
							ChangeFighting change=new ChangeFighting();
							change.setChangemp(manData.getMp_z()/2);
							FightingPackage.ChangeProcess(change, null, manData, org,TypeUtil.BB_BF, fightingStates, battlefield);
							FightingEvents fightingEvents=new FightingEvents();
							fightingEvents.setAccepterlist(fightingStates);
							battlefield.NewEvents.add(fightingEvents);
						}
					}else {//报复
						FightingSkill fightingSkill=data.getSkillType(TypeUtil.BB_BF);
						if (fightingSkill!=null&&Battlefield.isV(fightingSkill.getSkillhitrate())) {//触发报复
							List<FightingState> fightingStates=new ArrayList<>();
							FightingState org=new FightingState();
							org.setCamp(manData.getCamp());
							org.setMan(manData.getMan());
							org.setSkillskin("1835");
							org.setStartState(TypeUtil.JN);
							ChangeFighting change=new ChangeFighting();
							int hurt=-(int)(skill.getSkillblue()*fightingSkill.getSkillhurt()/100.0);

//							9221|铜肝铁胆|悬刃、讨命、报复技能对自己造成的伤害减少（1%*等级）。（仅在与玩家之间战斗有效）
							FightingSkill skill2=manData.skillId(9221);
							if (skill2!=null) {hurt=(int) (hurt*(100-skill2.getSkillhurt())/100.0);}
							change.setChangehp(hurt);
							FightingPackage.ChangeProcess(change, null, manData, org,TypeUtil.BB_BF, fightingStates, battlefield);
							FightingEvents fightingEvents=new FightingEvents();
							fightingEvents.setAccepterlist(fightingStates);
							battlefield.NewEvents.add(fightingEvents);
						}
					}
				}
			}
		}
	}
	static String FG="|";
	static String DH="=";
	//拼接状态刷新 true表示 添加  false 表示 删除
	public static String getBuffStr(List<GroupBuff> buffs,boolean type){
		StringBuffer buffer=new StringBuffer();
		if (type) {
			for (int i = buffs.size()-1; i >=0; i--) {
				GroupBuff buff=buffs.get(i);
				buffer.append(0);
				buffer.append(DH);
				buffer.append(buff.getBuffId());
				buffer.append(DH);
				buffer.append(buff.getCamp());
				buffer.append(DH);
				buffer.append(buff.getBuffType());
				if (i!=0) {
					buffer.append(FG);
				}
			}
		}else {
			for (int i = buffs.size()-1; i >=0; i--) {
				GroupBuff buff=buffs.get(i);
				buffer.append(1);
				buffer.append(DH);
				buffer.append(buff.getBuffId());
				if (i!=0) {
					buffer.append(FG);
				}
			}
		}
		return buffer.toString();
	}
	/**法术免疫躲闪处理*/
	public static FightingState DSMY(ManData manData,ManData data,int skillId,Battlefield battlefield){
		int type=SkillType(skillId);
		if (type==0) {return null;}
		double xs=getXS(data.getQihe());
		xs+=data.getQuality().getEfsds();
		AddState addState=manData.xzstate(TypeUtil.L_CB);
		if (addState!=null) {xs+=addState.getStateEffect()/2;}
		if (data.getZs()>=4) {//能够躲闪
			FightingSkill skill=data.getAppendSkill(9229);
			if (skill!=null) {xs+=skill.getSkillhurt();}
		}
		GroupBuff buff=battlefield.getBuff(data.getCamp(),TypeUtil.BB_E_HYMB);
		if (buff!=null) {xs+=buff.getValue();}
		xs+=data.getds(skillId) - manData.fsmz;

		addState=manData.xzstate("神不守舍");
		if (addState!=null) {
			xs +=(manData.getFmsld()/143);
		}
		addState=data.xzstate("法门加闪避");
		if (addState!=null) {
			xs +=(addState.getStateEffect()/769);
		}
		addState=data.xzstate("刚柔兼备");
		if (addState!=null) {
			xs +=(addState.getStateEffect()*0.00167);
		}
		addState = data.xzstate("明镜止水");
		if (addState != null) {
			xs += (addState.getStateEffect()*5);
		}
		addState = data.xzstate("气聚神凝");
		if (addState != null) {
			xs += (addState.getStateEffect()*0.0005);
		}
		addState = data.xzstate("知耻后勇");
		if (addState != null) {
			xs += 12;
		}
		xs*=100;
		if (xs>Battlefield.random.nextInt(10000)) {
			return initFightingState(data, "躲闪");
		}

		if (type==1) {
			addState=data.xzstate(TypeUtil.TZ_BDBQ);
			if (addState!=null) {return initFightingState(data, "免疫");}
			addState=data.xzstate(TypeUtil.TY_S_XCWM);
			if (addState!=null&&Battlefield.isV(addState.getStateEffect2())) {return initFightingState(data, "免疫");}
			FightingSkill skill=data.getAppendSkill(9282);
			if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {return initFightingState(data, "免疫");}
		}else if (type==2) {
			addState=data.xzstate(TypeUtil.TZ_MXJX);
			if (addState!=null) {return initFightingState(data, "免疫");}
		}
		if (skillId>=1061&&skillId<=1075) {
			FightingSkill skill=data.getAppendSkill(9411);
			if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
				return initFightingState(data, "免疫");
			}
		}
		if (Sepcies_MixDeal.isSex(manData.getSe(),data.getSe())==2) {
			FightingSkill skill=data.getAppendSkill(9410);
			if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
				return initFightingState(data, "免疫");
			}
		}
		if ((skillId>=1001&&skillId<=1015)||(skillId>=1071&&skillId<=1075)) {
			addState=data.xzstate("焕然新生");
			if (addState!=null) {
				if (Battlefield.isV(25)) {
					return initFightingState(data, "免疫");
				}
			}
		}
		addState=data.xzstate(TypeUtil.TY_SSC_LFHX);
		if (addState!=null) {
			//9389|流风回雪|回合开始前使用,使用后清除身上所有负面状态（等级2并回复自身血法上限的60%；等级三同时免疫物理攻击，持续1回合；等级四改为同时免疫所有攻击，持续1回合 ； 等级5改为同时免疫所有的攻击和控制，持续1回合）
			//等级1 使用后清除身上所有负面状态
			//等级2 回复自身血法上限的60%
			//等级3 免疫物理攻击
			//等级4 免疫所有攻击
			//等级5 免疫所有的攻击和控制
			if (type==2&&addState.getStateEffect2()>=4) {
				return initFightingState(data, "免疫");
			}else if (type==1&&addState.getStateEffect2()>=5) {
				return initFightingState(data, "免疫");
			}
		}
		return null;
	}
	/**判断id属于范围*/
	public static int SkillType(int Id){
//      1001-1015 1071-1075//控制性混乱，封印，昏睡，遗忘。
//		1016-1025 1041-1070//伤害性震慑，仙法，三尸虫，毒
		if ((Id>=1001&&Id<=1015)||(Id>=1071&&Id<=1075)) {return 1;}
		else if ((Id>=1016&&Id<=1025)||(Id>=1041&&Id<=1070)) {return 2;}
		return 0;
	}
	/**生成FightingState*/
	public static FightingState initFightingState(ManData data,String type){
		FightingState fightingState=new FightingState();
		fightingState.setCamp(data.getCamp());
		fightingState.setMan(data.getMan());
		fightingState.setStartState(TypeUtil.JN);
		fightingState.setProcessState(type);
		return fightingState;
	}
	/**摆脱状态的处理*/
	public static void rid(ManData data,FightingState state,List<AddState> rAddStates){
		for (int i = 0; i < rAddStates.size(); i++) {
			rAddStates.get(i).rid(data, state);
		}
	}
	/**获取系数*/
	public static double getXS(long p){
		if (p>=1500) {p=1500;}
		return 0.04*p;
	}
	/**天演策代为释放的技能ID*/
	public static int getTYSkillId(int id){
		if (id==9130) {return 1015;}
		else if (id==9151) {return 1005;}
		else if (id==9169||id==9170||id==9171) {return 1020;}
		else if (id==9350||id==9352) {return 1075;}
		else if (id==9189||id==9190) {return 1025;}
		else if (id==9207) {return 1030;}
		else if (id==9208) {return 1029;}
		else if (id==9231) {return 1039;}
		else if (id==9232) {return 1040;}
		else if (id==9250) {return 1034;}
		else if (id==9251||id==9252) {return 1035;}
		else if (id==9286||id==9287) {return 1055;}
		else if (id==9307) {return 1045;}
		else if (id==9372) {return 1065;}
		else if (id==9611||id==9612) {return 1095;}
		else if (id==9610) {return 1094;}
		else if (id==9510||id==9512) {return 1085;}
		else if (id==9710) {return 1100;}
		else if (id==9711) {return 1099;}
		else if (id==9811) {return 1089;}
		else if (id==9812) {return 1090;}


//		9510	怒雷齐鸣	施放一个强力震天动地，同时无视敌方所有单位的物理躲闪，若致死敌方目标数不少于3个，则每致死1个目标，则恢复自身{公式一}点怒气，同时有{公式三十四}%几率减少此技能的冷却回合数1回合。（仅在与玩家之间战斗有效）
//		9512	擎天撼地	连续施放3次震天动地，目标为选定目标同一排的所有单位，伤害为正常伤害的{公式一}%，不能触发连击。
//		9710	云卷星飞	释放一个强力飞举九天，同时降低目标{公式一}%法力上限，持续3回合。（仅在与玩家之间战斗有效）
//		9711	气贯碧霄	释放一个强力凌虚御风，将目标气血上限降低至{公式九}点，持续3回合。
//		9811	沧涛怒澜	施放一个强力白浪滔天，同时降低目标{公式一}%点连击率和{公式三十二}%的攻击力，持续3回合。
//		9812	平波息浪	施放一个强力沧海横流，同时使沧波状态下的目标在使用5阶和4阶师门法术时有{公式一}%几率减弱为3阶和1阶法术，持续3回合。

		return 0;
	}
	/**天降脱兔处理*/
	public static void BB_TJTT(ManData myData,long Zap,int nocamp,FightingSkill skill,Battlefield battlefield){
		int size=(int) skill.getSkillhurt();
		for (int i = 0; i < 4; i++) {
			if (Battlefield.isV(66-i*6)) {
				size++;
			}else {
				break;
			}
		}
		if (size>8) {size=8;}
		//起飞
		FightingEvents gjEvents=new FightingEvents();
		//===================================
		List<FightingState> zls=new ArrayList<>();
		FightingState gjz=new FightingState();
		gjz.setCamp(myData.getCamp());
		gjz.setMan(myData.getMan());
		gjz.setStartState("法术攻击");
		zls.add(gjz);
		//===============================
		gjEvents.setAccepterlist(zls);


		battlefield.NewEvents.add(gjEvents);
		//获取选定的怪物
		List<ManData> datas=MixDeal.get(false, myData, 0, nocamp, 0, 0, 0, 0, 10, -1, battlefield,0);
		//拼装第二组数据
		gjEvents=new FightingEvents();
		zls=new ArrayList<>();
		gjz=new FightingState();
		//================================
		gjz.setCamp(myData.getCamp());
		gjz.setMan(myData.getMan());
		gjz.setStartState("特效1");
		gjz.setEndState("3");
		zls.add(gjz);
		//===================================
		if (datas.size()!=0) { //判断有没有怪
			List<ManData> list=new ArrayList<>();
			for (int i = 0; i < size; i++) {//for 是循环的意思
				if (datas.size()==0) {break;} //如果等于0只直接结束
				ManData data=datas.get(Battlefield.random.nextInt(datas.size()));
				if (data.getStates()!=0) {
					datas.remove(data);
					continue;
				}
				//一个单位最多只能打击3次
				list.add(data);
				int vs=0;
				for (int j = list.size()-1; j>=0; j--) {if (list.get(j)==data) {vs++;}}
				if (vs>=3) {datas.remove(data);}

				FightingState ace1=new FightingState();
				ChangeFighting acec1=new ChangeFighting();
				//取伤伤害计算
				long ap=PhyAttack.Hurt(Zap,1,myData,data,TypeUtil.PTGJ,null,zls,null,0,0);

				acec1.setChangehp((int)-ap);
				acec1.setChangevlaue(20);
				acec1.setChangetype(TypeUtil.BB_TJTT);
				acec1.setChangesum(2);
				FightingPackage.ChangeProcess(acec1,null, data, ace1, TypeUtil.PTGJ, zls, battlefield);
			}
		}
		gjEvents.setAccepterlist(zls);
		battlefield.NewEvents.add(gjEvents);
		//归位
		MixDeal.move(myData.getCamp(), myData.getMan(),"瞬移",myData.getCamp()+"|"+myData.getMan(),battlefield);
	}
}
