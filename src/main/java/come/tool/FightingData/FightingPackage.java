package come.tool.FightingData;

import java.util.List;

import org.come.bean.PathPoint;
import come.tool.FightingSpellAction.SSCAction;

/**
 * 战斗封装
 * @author Administrator
 *
 */
public class FightingPackage {
    //数据接受处理ChangeFighting  递归调用情况 
	public static void ChangeProcess(ChangeFighting changeFighting,ManData mydata,ManData data,
			FightingState org2,String type,List<FightingState> list,Battlefield battlefield){
		list.add(org2);
		//获取是否为
		int yylz=0;
		if (mydata!=null&&changeFighting.getChangehp()<0) {
			yylz=data.getyylz();
			if (TypeUtil.PTGJ.equals(type)) {
				//判断是否有伤害加深
				FightingSkill fightingSkill=mydata.getSkillType("伤害加深");
				if (fightingSkill!=null) {
					double hurt=fightingSkill.getSkillhurt();
					int skillcontinued=fightingSkill.getSkillcontinued();
					fightingSkill=mydata.getSkillType(TypeUtil.BB_E_QHQSSW);
					if (fightingSkill!=null) {
						if (fightingSkill!=null) {hurt=hurt*(1+fightingSkill.getSkillgain()/100D);}
					}
					data.addAddState("伤害加深", hurt, 0, skillcontinued);
				}
				fightingSkill=mydata.getSkillType("虎踞龙蟠");
				if (fightingSkill!=null) {
					if(Battlefield.isV(mydata.getFmsld()/287)) {
						data.addAddState("虎踞龙蟠",mydata.getFmsld()*8.8,0,2);
						org2.setEndState_1("虎踞龙蟠");
					}
				}

				AddState addState=mydata.xzstate(TypeUtil.FB_JQB);
				if (addState!=null) {
					changeFighting.setChangemp(changeFighting.getChangemp()-(int)(data.getAp()*addState.getStateEffect()/100));				
				}
				fightingSkill=data.getSkillType(TypeUtil.TJ_JMHM);
				if (fightingSkill!=null&&33>Battlefield.random.nextInt(100)) {
					changeFighting.setChangehp(changeFighting.getChangehp()/2);
				}
			}	
			if (data.getType()==1) {
				changeFighting.setChangehp((int)(changeFighting.getChangehp()*(1+mydata.getQuality().getQ_zhssh()/100)));
			}
		}
		if (yylz==0) {

			//利刃加身
			AddState addState=data.xzstate("利刃加身");
			if (addState!=null) {
				if(data.getNrlrjs()>0) {
					int nrlrsh = (int)(data.getDffmsld()*10.04);
					if (nrlrsh >data.getNrlrjs()) {nrlrsh = data.getNrlrjs();};
					if(nrlrsh >changeFighting.getChangehp()) {nrlrsh = changeFighting.getChangehp();};
					int sh = changeFighting.getChangehp()+nrlrsh;
					changeFighting.setChangehp(sh);
					data.setNrlrjs(data.getNrlrjs()-nrlrsh);
				}
			}
			addState=data.xzstate("积羽沉舟");
			if (addState!=null) {
				if(data.getNrjycz()>0) {
					int nrlrsh = (int)(data.getDffmsld()*8.53);
					if (nrlrsh >data.getNrjycz()) {nrlrsh = data.getNrlrjs();};
					if(nrlrsh >changeFighting.getChangehp()) {nrlrsh = changeFighting.getChangehp();};
					int sh = changeFighting.getChangehp()+nrlrsh;
					changeFighting.setChangehp(sh);
					data.setNrjycz(data.getNrlrjs()-nrlrsh);
				}
			}


			//黑龙珠 或者 七宝玲珑塔 反弹使用
			ChangeFighting fbft = null;		
			//将军令使用
			ChangeFighting jjl = null;		
			//投桃报李 使用
			ChangeFighting ttbl = null;
			ChangeFighting tlhz = null;
			JS(mydata,data,changeFighting,org2,type,battlefield);
			if (changeFighting.getChangehp()<0) {
				//判断是发有雪上加霜
				if (battlefield.CurrentRound>1) {
					 //判断是否 自动触发回光
					FightingSkill TZ_HGFZ=data.getSkillType(TypeUtil.TZ_HGFZ);
					if (TZ_HGFZ!=null&&data.getvalue(0)<(TZ_HGFZ.getSkillgain()/100.0)) {
						data.addAddState(TZ_HGFZ.getSkilltype(), TZ_HGFZ.getSkillgain(),0,1);
						data.addAddState("冷却",6029,0, 15);
						data.RemoveAbnormal(org2, ManData.values2);
						org2.setEndState_1(TypeUtil.TZ_HGFZ);
					}	
				}
				AddState HGFZ=data.xzstate(TypeUtil.TZ_HGFZ);
				if (HGFZ!=null) {
					changeFighting.setChangehp(-changeFighting.getChangehp());
				}else {
					//法宝转换伤害
					zhsh(data, changeFighting);
					//妙转乾坤
					flhd(data, changeFighting);
					//庇护 泽披天下
					pztx(data, changeFighting, battlefield, list);
					org2.setStartState("被攻击");
					org2.setSkillsy("hit");
					//防御伤害减免 
					if (battlefield.fypd(data.getCamp(), data.getMan())) {
						double xs=1.0;
						if (type.equals(TypeUtil.PTGJ)) {
							org2.setProcessState("防御");
							org2.setStartState("防御");
							org2.setSkillsy("防御");
							xs-=0.5;
							if (data.getSkillType(TypeUtil.TJ_JZZ)!=null||	data.getSkillType(TypeUtil.TJ_XLYT)!=null) {
								xs-=0.2;
							}
						}else if (type.equals(TypeUtil.F)||type.equals(TypeUtil.H)||type.equals(TypeUtil.S)||
								  type.equals(TypeUtil.L)||type.equals(TypeUtil.GH)||type.equals(TypeUtil.ZD)||type.equals(TypeUtil.ZS)) {
							if (data.getSkillType(TypeUtil.TJ_RWJ)!=null||data.getSkillType(TypeUtil.TJ_XLYT)!=null) {
								xs-=0.2;
							}
						}
						changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));				
					}
			        fbft=fb_fbft(data,changeFighting);
			        jjl=fb_jjl(data,changeFighting);
			        ttbl=bb_e_ttbl(data,changeFighting,battlefield);
					tlhz=tj_tlhz(data,changeFighting);
					if (changeFighting.getChangehp()==0) {changeFighting.setChangehp(-1);}	
				}
		        if (type.equals("庇护")||type.equals("盾破")||type.equals("反震")) {
		        	org2.setProcessState(type);
				}
		        //判断是否有盾       
		        gxgf(org2, mydata, data, changeFighting, battlefield, list);	
			}
			ChildUptake(changeFighting, data, type, list, battlefield);
			int States=data.getStates();
			if (changeFighting.getChangehp()<0) {//受到伤害则增加怨气
				if (type.equals(TypeUtil.PTGJ)||type.equals(TypeUtil.GH)||type.equals(TypeUtil.ZS)||type.equals(TypeUtil.SSC)) {
				    data.addyq(25,org2);//加25
				}else if (type.equals(TypeUtil.F)||type.equals(TypeUtil.H)||type.equals(TypeUtil.S)||type.equals(TypeUtil.L)) {
				    data.addyq(50,org2);//加50	
				}
				FightingSkill skill=data.getSkillType("以静制动");
				if(skill != null) {
					int famencs = data.getfamencs();
					data.setfamencs(famencs+1);
					data.famenBuff(org2);
				}
				skill=data.getSkillType("妙法莲华");
				if(skill != null) {
					int famencs = data.getfamencs();
					data.setfamencs(famencs+1);
					data.famenBuff(org2);
				}
				skill=data.getSkillType("明镜止水");
				if(skill != null) {
					int famencs = data.getfamencs();
					data.setfamencs(famencs+1);
					data.famenBuff(org2);
				}

  //      根据双方血量来扣除
			if (Math.abs(changeFighting.getChangehp())>data.getHp()) {changeFighting.setChangehp(-data.getHp());}


			}
			if (TypeUtil.ZSSH.equals(type)) {data.ChangeDataZS(org2);}
			else {data.ChangeData(changeFighting,org2);}
			if (changeFighting.getChangehp()<0&&!type.equals("中毒")) {
	        	//判断身上是否有昏睡状态
				for (int i = 0; i < data.getAddStates().size(); i++) {
					 addState=data.getAddStates().get(i);
					if (addState.getStatename().equals("昏睡")) {
						FightingSkill skill=addState.getSkill(9124);
						if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
							break;
						}
						skill=addState.getSkill(9127);
						if (skill!=null) {
							addState.getSkills().remove(skill);
							break;
						}
						org2.setEndState_2("昏睡");
						data.getAddStates().remove(i);
						break;
					}
				}
			}
	        //判断是否有毒针
            if (type.equals("毒针轻刺")&&changeFighting.getChangehp()<0){dzqc(changeFighting.getChangehp(), data, battlefield, list);}
			if (data.getStates()==1&&States==0) {
				if (type.equals("盾破")) {data.getAddStates().add(new AddState("归墟", 0,1));org2.setEndState_1("归墟");}
				pztxDie(data,battlefield,list);
				if (mydata!=null&&mydata.getType()==1&&type.equals(TypeUtil.PTGJ)) {BB_E_HQYX(mydata, data,list,battlefield);}
				addState = data.xzstate("鱼龙潜跃");
				if (mydata!=null&&addState!=null) {FM_ylqy(mydata, data, (int)addState.getStateEffect(),list,battlefield);}
				addState = data.xzstate("虎踞龙蟠");
				if (mydata!=null&&addState!=null) {FM_hjlp(mydata, data, (int)addState.getStateEffect(),list,battlefield);}

				//先判断是否能复活
				MixDeal.DeathSkill(data,org2,battlefield);
				//判断是否有化羽状态
				if (data.xzstate("化羽")!=null) {
					battlefield.huayu.add(new PathPoint(data.getCamp(),changeFighting.getChangehp()));					
				}
			}	
			//反震处理 返回反震伤害
			if (mydata!=null&&mydata.getType()!=3&&mydata.getType()!=4) {
				if (changeFighting.getChangehp()<0&&!type.equals("震慑")) {
			    	int ap=(int) data.getfz(changeFighting.getChangehp());
			        if (ap!=0) {
						double wushi = mydata.hsfz;
						FightingSkill skill=mydata.getSkillId(23005);
						if (skill!=null) {
							wushi+=10;
						}
						if (!Battlefield.isV(wushi)) {
							//9206|月涌大江|被牛的单位物理攻击时有（2%*等级）的几率免疫反震伤害。
							skill = data.getAppendSkill(9206);
							if (skill == null || !Battlefield.isV(skill.getSkillhurt())) {
								ChangeFighting f = new ChangeFighting();
								if (mydata.getSkillType(TypeUtil.TJ_WZKR) != null) {
									f.setChangehp(ap / 2);
								} else {
									f.setChangehp(ap);
								}
								FightingState org3 = new FightingState();
								org3.setCamp(mydata.getCamp());
								org3.setMan(mydata.getMan());
								org3.setStartState("被攻击");
								ChangeProcess(f, null, mydata, org3, "反震", list, battlefield);
							}
						}
				    }
				}	
				if (fbft!=null) {
			    	FightingState org3=new FightingState();
					org3.setCamp(mydata.getCamp());
					org3.setMan(mydata.getMan());
					org3.setStartState(TypeUtil.JN);
			    	ChangeProcess(fbft,null,mydata,org3, "反射", list, battlefield);
				}
				if (jjl!=null) {
					FightingState org3=new FightingState();
					List<ManData> datas=MixDeal.get(false, null, 0,data.getCamp(), 0, 0, 0, 0, 1, 0, battlefield,1);
					if (datas.size()!=0) {
						org3.setCamp(datas.get(0).getCamp());
						org3.setMan(datas.get(0).getMan());
						org3.setStartState(TypeUtil.JN);
				    	ChangeProcess(jjl,null,datas.get(0),org3, "反射", list, battlefield);	
					}		
				}
				if (ttbl!=null) {
					FightingState org3=new FightingState();
					List<ManData> datas=MixDeal.get(false, null, 0,data.getCamp(), 0, 0, 0, 0, 1, 3, battlefield,1);
					if (datas.size()!=0) {
						org3.setCamp(datas.get(0).getCamp());
						org3.setMan(datas.get(0).getMan());
						org3.setStartState(TypeUtil.JN);
				    	ChangeProcess(ttbl,null,datas.get(0),org3, "反射", list, battlefield);	
					}
				}
			}		
		}else {
			ChangeFighting change=new ChangeFighting();
			change.setChangehp(changeFighting.getChangehp());
			changeFighting.setChangehp(0);
			data.ChangeData(changeFighting,org2);
			if (yylz==2)org2.setEndState_2("阴阳逆转");
			org2.setProcessState("阴阳逆转");
			org2.setStartState("代价");
			//选择对方血最多的单位
		    List<ManData> datas=MixDeal.get(false,null, 0, data.getCamp(), 0, 0, 0,0,1, 0, battlefield,1);
			if (datas.size()!=0){
				FightingState org3=new FightingState();
				org3.setCamp(datas.get(0).getCamp());
				org3.setMan(datas.get(0).getMan());
				org3.setStartState("代价");
				ChangeProcess(change, null, datas.get(0), org3, "阴阳逆转", list, battlefield);	
			}
			 
		}
			if("至圣".equals(type)){
               org2.setProcessState("至圣");
			}
		}	
	/**妙转乾坤(法力护盾)*/
	public static void flhd(ManData data,ChangeFighting changeFighting){
		if (changeFighting.getChangehp()>=0) return;
		FightingSkill skill=data.getSkillType("妙转乾坤");
		if (skill==null) return;
//		FightingSkill skill2=data.getSkillType(TypeUtil.BB_E_QHMZQK);
		int symp=data.getMp()+changeFighting.getChangemp();
		if (symp<=0)return;
		int hurt=-changeFighting.getChangehp()/2;
		if (symp>=hurt) {
			changeFighting.setChangehp(-hurt);
			changeFighting.setChangemp(-hurt+changeFighting.getChangemp());
		}else {
			changeFighting.setChangehp(changeFighting.getChangehp()+symp);
			changeFighting.setChangemp(changeFighting.getChangemp()-symp);
		}
	}
	/**骨盾处理*/
	public static void gxgf(FightingState org2,ManData mydata,ManData data,ChangeFighting changeFighting,Battlefield battlefield,List<FightingState> list){
		if (changeFighting.getChangehp()>=0) return;
		AddState addState=null;
		FightingSkill skill=null;
    	if (PK_MixDeal.isPK(battlefield.BattleType)) {
//    		9232|苦海慈航|释放一个强力乾坤借速，    为所有被速的单位增加一个护盾，当被速目标气血百分比低于（20%*等级）时，该护盾可以抵御义词致死伤害，护盾生效时清除被速目标身上的速法效果，护盾最多存在3回合。此技能全队全场只能用一次。（仅在与玩家之间战斗有效）
            skill=data.getAppendSkill(9232);
            if (skill!=null&&data.getvalue(0)<skill.getSkillhurt()/100.0) {
            	if (-changeFighting.getChangehp()>=data.getHp()) {
            		data.RemoveAbnormal(TypeUtil.JS);
                	org2.setProcessState("免疫");
        			org2.setStartState("防御");
        			org2.setEndState_2(TypeUtil.JS);
        			changeFighting.setChangehp(0);	
        			data.RemoveAppendSkill(9232);
        			return;
            	} 	
    		}
    		addState=data.xzstate(TypeUtil.TY_KX_JJCS);
    		if (addState!=null) {
    			if (addState.getStateEffect()>-changeFighting.getChangehp()) {
    				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
    				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
    				org2.setStartState("防御");
    				changeFighting.setChangehp(0);
    			}else {
    				data.RemoveAbnormal(addState.getStatename());
    				FightingState org=new FightingState();
    				org.setCamp(data.getCamp());
    				org.setMan(data.getMan());
    				org.setStartState("防御");
    				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
    				list.add(org);
    				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
    				FightingSkill skill2=addState.getSkill(9247);
    				if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
    					data.RemoveAbnormal(org,ManData.values2);
    					org.setEndState_2("清除异常状态");
    				}
    				if (mydata!=null&&mydata.getType()!=3&&mydata.getType()!=4) {
    					skill2=addState.getSkill(9244);
    					if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
    						ChangeFighting change=new ChangeFighting();
    						change.setChangehp(-(int) (skill2.getSkillhurt()*800));
    						FightingState org3=new FightingState();
    						org3.setCamp(mydata.getCamp());
    						org3.setMan(mydata.getMan());
    						org2.setStartState("被攻击");
    						ChangeProcess(change, data,mydata, org3, "盾破", list, battlefield);		
    					}
    				}
    			}
    			return;
    		}
    		addState=data.xzstate(TypeUtil.TY_MH_RSSQ);
    		if (addState!=null) {
    			int maxHurt=(int) (addState.getStateEffect2()-addState.getStateEffect());
    			if (maxHurt>0) {
    				if (maxHurt>-changeFighting.getChangehp()) {
    					addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
    					org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
    					org2.setStartState("防御");
    					changeFighting.setChangehp(0);
    				}else {
    					addState.setStateEffect(addState.getStateEffect()+maxHurt);
    					FightingState org=new FightingState();
    					org.setCamp(data.getCamp());
    					org.setMan(data.getMan());
    					org.setStartState("防御");
    					org.setProcessState("吸收  "+(maxHurt));
    					list.add(org);
    					changeFighting.setChangehp(changeFighting.getChangehp()+maxHurt);
    				}
    			}
    			return;
    		}
    		if (Sepcies_MixDeal.getRace(data.getSe())==10003) {
    			addState=data.xzstate(TypeUtil.TY_L_ZXLT);
    			if (addState!=null) {
        			//9271|紫霄雷霆|在使用天课地灭时有（15%+2%*等级）几率将造成伤害之和的20%(最大不超过自己的气血上限)转化为自己的护盾,
        			//护盾存在期间每次受到一次敌方的攻击，都向敌方释放一道电波，造成（2000*等级）点 气血或者法力伤害，护盾最多持续3回合。( 仅在与玩家之间战斗有效)
    				if (addState.getStateEffect()>-changeFighting.getChangehp()) {
        				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
        				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
        				org2.setStartState("防御");
        				changeFighting.setChangehp(0);
        			}else {
        				data.RemoveAbnormal(addState.getStatename());
        				FightingState org=new FightingState();
        				org.setCamp(data.getCamp());
        				org.setMan(data.getMan());
        				org.setStartState("防御");
        				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
        				org.setEndState_2(addState.getStatename());
        				list.add(org);
        				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
        			}
    				if (mydata!=null) {
    					ChangeFighting fighting=new ChangeFighting();
    					FightingState Accepter2=new FightingState();
    					Accepter2.setStartState("代价");
    					if (Battlefield.isV(50)) {fighting.setChangehp(-(int)addState.getStateEffect2());}
    					else {fighting.setChangemp(-(int)addState.getStateEffect2());}
    					mydata.ChangeData(fighting,Accepter2);
    					list.add(Accepter2);	
					}
					return;
				}
    			addState=data.xzstate(TypeUtil.TY_S_XCWM);
    			if (addState!=null) {
        			//9288|仙尘帷幔|在使用九龙冰封时有（15%+2%*等级）几率将造成伤害之和的20%(最大不超过自己的气血上限)转化为自己的护盾,
        			//护盾存在期间自己有（5%*等级）的几率免疫混冰睡忘和金箍控制,护盾消失时随机解除自身一个异常状态,护盾最多持续卖3回合。仅在与玩家之间战斗有效								
    				if (addState.getStateEffect()>-changeFighting.getChangehp()) {
        				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
        				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
        				org2.setStartState("防御");
        				changeFighting.setChangehp(0);
        			}else {
        				data.RemoveAbnormal(addState.getStatename());
        				FightingState org=new FightingState();
        				org.setCamp(data.getCamp());
        				org.setMan(data.getMan());
        				org.setStartState("防御");
        				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
        				org.setEndState_2(addState.getStatename());
        				list.add(org);
        				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
        			}
					return;
				}
    			addState=data.xzstate(TypeUtil.TY_F_YYFQ);
    			if (addState!=null) {
        			//9308|云涌风飞|在使用袖里乾坤时有（15%+2%*等级）几率将造成伤害之和的20%(最大不超过自己的气血上限)转化为自己的护盾,
        	    	//护盾存在期间对自己造成伤害的敌方人物单位有（5%*等级）的几率在下回合使用多体师门法术时目标数减1,护盾最多持续3回合。(仅在与玩家之间战斗有效)
    				if (addState.getStateEffect()>-changeFighting.getChangehp()) {
        				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
        				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
        				org2.setStartState("防御");
        				changeFighting.setChangehp(0);
        			}else {
        				data.RemoveAbnormal(addState.getStatename());
        				FightingState org=new FightingState();
        				org.setCamp(data.getCamp());
        				org.setMan(data.getMan());
        				org.setStartState("防御");
        				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
        				org.setEndState_2(addState.getStatename());
        				list.add(org);
        				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
        			}
    				if (mydata!=null&&Battlefield.isV(addState.getStateEffect2())) {
    					mydata.addAddState(TypeUtil.TY_F_YYFQ_S,0,0,2);
    				}
					return;
				}
    			addState=data.xzstate(TypeUtil.TY_H_ZSMH);
    			if (addState!=null) {
        			//9328|照世明火|在使用九阴纯火时有（15%+2%*等级）几率将造成伤害之和的20%(最大不超过自己的气血上限)转化为自己的护盾,
        	    	//护盾存在期间每个攻击自己的敌方目标都会受到灼烧,处于该状态下的目标下回合开始前随机受到自已气血上限（0.5%*等级）~（1.5%*等级）的伤害,护盾最多持续3回合。(仅在与玩家之间战斗有效
    				if (addState.getStateEffect()>-changeFighting.getChangehp()) {
        				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
        				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
        				org2.setStartState("防御");
        				changeFighting.setChangehp(0);
        			}else {
        				data.RemoveAbnormal(addState.getStatename());
        				FightingState org=new FightingState();
        				org.setCamp(data.getCamp());
        				org.setMan(data.getMan());
        				org.setStartState("防御");
        				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
        				org.setEndState_2(addState.getStatename());
        				list.add(org);
        				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
        			}
    				if (mydata!=null) {
    					mydata.addAddState(TypeUtil.TY_H_ZSMH_S,addState.getStateEffect2(),0,2);
    				}
					return;
				}
			}
    	}else {
//    		9262|销声匿迹|给自己释放一个可以持续3回合的护盾，该护盾消耗法力会抵挡所受的气血伤害。每回合最多吸收最大法力值（10%+5%*等级）的伤害。（仅在与NPC之间战斗有效）
            addState=data.xzstate(TypeUtil.TY_L_XSNJ);    
            if (addState!=null) {
            	if (addState.getStateEffect()>0) {
            		if (addState.getStateEffect()>-changeFighting.getChangehp()) {
                		addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
        				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
        				org2.setStartState("防御");
        				changeFighting.setChangehp(0);
        			}else {
        				FightingState org=new FightingState();
        				org.setCamp(data.getCamp());
        				org.setMan(data.getMan());
        				org.setStartState("防御");
        				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
        			    list.add(org);
        				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
        			}
    			}
            	if (mydata!=null) {
    				FightingSkill skill2=addState.getSkill(9265);
    				if (skill2!=null) {
    					ChangeFighting change2=new ChangeFighting();
    					int hurt=(int)skill2.getSkillhurt()-5000;
    					if (hurt>0) {hurt=Battlefield.random.nextInt(hurt);}
    					hurt+=skill2.getSkillhurt();
    					FightingSkill skill3=addState.getSkill(9268);
    				    if (skill3!=null&&Battlefield.isV(skill3.getSkillhurt())) {
							hurt+=data.getHp()*0.1;
						}
    					change2.setChangehp(-(hurt));	
    					FightingState org3=new FightingState();
    					org3.setCamp(mydata.getCamp());
    					org3.setMan(mydata.getMan());
    					org3.setStartState("被攻击");
    					ChangeProcess(change2,null,mydata,org3,TypeUtil.TY_L_DZXY,list,battlefield);	
    				}
    				if (changeFighting.getChangehp()<0) {
    					skill2=addState.getSkill(9302);
        				if (skill2!=null) {
        					ChangeFighting change2=new ChangeFighting();
        					int hurt=(int)(changeFighting.getChangehp()*skill2.getSkillhurt()/100.0);
        					if (hurt<0) {
        						change2.setChangehp(hurt);	
            					FightingState org3=new FightingState();
            					org3.setCamp(mydata.getCamp());
            					org3.setMan(mydata.getMan());
            					org3.setStartState("被攻击");
            					ChangeProcess(change2,null,mydata,org3,TypeUtil.TY_L_DZXY,list,battlefield);
        					}
        				}	
					}
    				skill2=addState.getSkill(9305);
    				if (skill2!=null&&Battlefield.isV(skill2.getSkillhurt())) {
    					data.addAddState(TypeUtil.TY_F_WHSF, 20, 0, 2);
    				}
    				skill2=addState.getSkill(9322);
    				if (skill2!=null) {
    					data.addAddState(TypeUtil.TY_H_JSYY, skill2.getSkillhurt(), 0, 2);
    				}
    				skill2=addState.getSkill(9325);
                    if (skill2!=null) {
                    	data.addAddState(TypeUtil.TY_H_YHRJ, skill2.getSkillhurt(), 0, 2);
                    }
    			}
            }
		}
		addState=data.xzstate("法魂护体");
		if (addState!=null) {
			if (addState.getStateEffect()>-changeFighting.getChangehp()) {
				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
				org2.setStartState("防御");
				changeFighting.setChangehp(0);
			}else {
				data.RemoveAbnormal(addState.getStatename());
				FightingState org=new FightingState();
				org.setCamp(data.getCamp());
				org.setMan(data.getMan());
				org.setStartState("防御");
				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
				list.add(org);
				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
			}
			return;
		}

		addState=data.xzstate("血蛊护盾");
		if (addState!=null) {
			if (addState.getStateEffect()>-changeFighting.getChangehp()) {
				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
				org2.setStartState("防御");
				changeFighting.setChangehp(0);
			}else {
				data.RemoveAbnormal(addState.getStatename());
				FightingState org=new FightingState();
				org.setCamp(data.getCamp());
				org.setMan(data.getMan());
				org.setStartState("防御");
				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
				list.add(org);
				changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
			}
			return;
		}
    	
        addState=data.xzstate("骨盾");
        if (addState==null) {
        	addState=data.xzstate(TypeUtil.TY_L_GL_YMFZ);
		}
		if (addState!=null) {
			if (addState.getStateEffect()>-changeFighting.getChangehp()) {
				addState.setStateEffect(addState.getStateEffect()+changeFighting.getChangehp());
				org2.setProcessState("吸收  "+(-changeFighting.getChangehp()));
				org2.setStartState("防御");
				changeFighting.setChangehp(0);
			}else {
				data.RemoveAbnormal(addState.getStatename());
				FightingState org=new FightingState();
				org.setCamp(data.getCamp());
				org.setMan(data.getMan());
				org.setStartState("防御");
				org.setProcessState("吸收  "+((int)addState.getStateEffect()));
				org.setEndState_2(addState.getStatename());
				list.add(org);
				if (addState.getStatename().equals("骨盾")) {
					changeFighting.setChangehp((int)(changeFighting.getChangehp()+addState.getStateEffect()));
					if (mydata!=null&&mydata.getType()!=3&&mydata.getType()!=4) {
						ChangeFighting change=new ChangeFighting();
						change.setChangehp((int)-addState.getStateEffect2());
						FightingState org3=new FightingState();
						org3.setCamp(mydata.getCamp());
						org3.setMan(mydata.getMan());
						org3.setStartState("被攻击");
						ChangeProcess(change, data,mydata, org3, "盾破", list, battlefield);				
					}
				}	
			}
			return;
		}
	}
	/**庇护处理*/
	public static void pztx(ManData mydata,ChangeFighting changeFighting,Battlefield battlefield,List<FightingState> list){
		if (changeFighting.getChangehp()>=0) return ;
		AddState addState=mydata.xzstate("庇护");
		if (addState==null) return ;
		if (mydata.getType()==1&&mydata.getId()==addState.getStateEffect()){
			changeFighting.setChangehp((int)(changeFighting.getChangehp()*(1-addState.getStateEffect2()*1.4/100)));
			return;
		}
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0)continue;
			if (data.getType()==1&&data.getId()==addState.getStateEffect()){
				ChangeFighting change=new ChangeFighting();
				change.setChangehp((int)(changeFighting.getChangehp()*(addState.getStateEffect2()/100)));
				changeFighting.setChangehp(changeFighting.getChangehp()-change.getChangehp());
				FightingState org3=new FightingState();
				org3.setCamp(mydata.getCamp());
				org3.setMan(mydata.getMan());
				ChangeProcess(change, null, data,org3, "庇护", list, battlefield);
				return;	
			}			
		}
	}
	/**庇护单位死亡处理*/
	public static void pztxDie(ManData mydata,Battlefield battlefield,List<FightingState> list){
		AddState addState=mydata.xzstate("庇护");
		if (addState==null) {return ;}
		if (mydata.getType()==1&&mydata.getId()==addState.getStateEffect()){return;}
		FightingSkill skill=addState.getSkill(1319);
		if (skill==null) {return;}
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getStates()!=0){continue;}
			if (data.getType()==1&&data.getId()==addState.getStateEffect()){
				ChangeFighting change=new ChangeFighting();
				change.setChangehp((int)(data.getHp_z()*0.1));
				FightingState org3=new FightingState();
				org3.setCamp(mydata.getCamp());
				org3.setMan(mydata.getMan());
				ChangeProcess(change, null, data,org3, "庇护", list, battlefield);
				return;	
			}			
		}
	}
	/**
	 * 毒针处理	
	 */
	public static void dzqc(int hurt,ManData data,Battlefield battlefield,List<FightingState> list){
		   List<ManData> datas=MixDeal.get(false,null, 0, data.getCamp(), 0, 0, 0,1,1, 1, battlefield,1);
		   hurt=-3*hurt;
		   if (datas.size()!=0) {
               FightingState org=new FightingState();
			   org.setCamp(datas.get(0).getCamp());
			   org.setMan(datas.get(0).getMan()); 
			   org.setStartState(TypeUtil.JN);
			   ChangeFighting change=new ChangeFighting();
			   change.setChangehp(hurt);
			   datas.get(0).ChangeData(change, org);
			   list.add(org);
		   }
	}
	/**
	 * 孩子伤害吸收
	 */
	public static void ChildUptake(ChangeFighting change,ManData data,String type,List<FightingState> list,Battlefield battlefield){
		if (!(type.equals(TypeUtil.F)||type.equals(TypeUtil.H)||type.equals(TypeUtil.S)||type.equals(TypeUtil.L)||type.equals(TypeUtil.GH)||type.equals(TypeUtil.ZS)))return;
        ManData child=battlefield.getChild(data);
        if (child==null)return;
        FightingSkill skill=child.getChildSkill("抗"+type);
        if (skill==null)return;
        list.add(MixDeal.getChildSkill(child, skill.getSkillname()));
        if (type.equals("震慑")) {
        	change.setChangemp((int)skill.getSkillgain());
		}else {
			int hp=change.getChangehp();
			hp+=skill.getSkillhurt();
			if (hp>=0)hp=-1;
			if (-hp>=data.getHp())
				hp=-(int) (data.getHp()-skill.getSkillgain());
			if (hp>=0)hp=0;
			change.setChangehp(hp);
		}
	}
	//判断是否存在七宝玲珑塔 黑龙珠
	public static ChangeFighting fb_fbft(ManData data,ChangeFighting changeFighting){
		ChangeFighting fighting=null;
		AddState addState=null;
		if (changeFighting.getChangehp()<0) {
			//黑龙珠 血反法
			addState=data.xzstate(TypeUtil.FB_HLZ);
			if (addState!=null) {
				if (fighting==null) {
					fighting=new ChangeFighting();
				}
				double xs=addState.getStateEffect()/100;
				fighting.setChangemp((int)(changeFighting.getChangehp()*xs));
				xs=1-xs;
				changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));
			}	
		}
		if (changeFighting.getChangemp()<0) {
			//七宝玲珑塔  法反血
			addState=data.xzstate(TypeUtil.FB_QBLLT);
	        if (addState!=null) {
	        	if (fighting==null) {
					fighting=new ChangeFighting();
				}
	        	double xs=addState.getStateEffect()/100;
	        	fighting.setChangehp((int)(changeFighting.getChangemp()*xs));
	        	xs=1-xs;
				changeFighting.setChangemp((int)(changeFighting.getChangemp()*xs));
			}	
		}		
		return fighting;
	}
	//法宝转化伤害 银索金铃 锦襕袈裟
	public static void zhsh(ManData data,ChangeFighting changeFighting){
		AddState addState=null;
		if (changeFighting.getChangehp()<0) {
			//银索金铃 血转法
			addState=data.xzstate(TypeUtil.FB_YSJL);
			if (addState!=null) {
				double xs=addState.getStateEffect()/100;
				changeFighting.setChangemp(changeFighting.getChangemp()+(int)(changeFighting.getChangehp()*xs));
				xs=1-xs;
				changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));
			}	
		}
		if (changeFighting.getChangemp()<0) {
			//锦襕袈裟  法转血
			addState=data.xzstate(TypeUtil.FB_JLJS);
	        if (addState!=null) {
	        	double xs=addState.getStateEffect()/100;
				changeFighting.setChangehp(changeFighting.getChangehp()+(int)(changeFighting.getChangemp()*xs));
				xs=1-xs;
				changeFighting.setChangemp((int)(changeFighting.getChangemp()*xs));
			}	
		}	
	}
	//将军令使用
	public static ChangeFighting fb_jjl(ManData data,ChangeFighting changeFighting){
		if (changeFighting.getChangemp()>=0) {return null;}
		AddState addState=data.xzstate(TypeUtil.FB_JJL);
        if (addState==null) {return null;}
		ChangeFighting fighting=new ChangeFighting();
		double xs=addState.getStateEffect()/100;
		fighting.setChangehp((int)(changeFighting.getChangehp()*xs));
		xs=1-xs;
		changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));
		return fighting;
	}
	//投桃报李使用
	public static ChangeFighting bb_e_ttbl(ManData data,ChangeFighting changeFighting,Battlefield battlefield){
		if (changeFighting.getChangemp()>=0) {return null;}
		FightingSkill skill=data.getSkillType(TypeUtil.BB_E_TTBL);
		if (skill==null) {return null;}
		if (battlefield.CurrentRound<=skill.getSkillhurt()||!Battlefield.isV(skill.getSkillgain())) {return null;}
		ChangeFighting fighting=new ChangeFighting();
		double xs=skill.getSkillgain()/100D;
		fighting.setChangemp((int)(changeFighting.getChangehp()*xs));
		xs=1-xs;
		changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));
		return fighting;
	}
	public static ChangeFighting tj_tlhz(ManData data,ChangeFighting changeFighting){
		if (changeFighting.getChangehp()>=0) {return null;}
		AddState addState=data.xzstate("偷梁换柱");
		if (addState==null) {return null;}
		ChangeFighting fighting=new ChangeFighting();
		fighting.setChangehp((int)(changeFighting.getChangehp()));//伤害全部转移
		changeFighting.setChangehp((int)(0));//自身伤害归0
		return fighting;
	}
	/**减伤*/
	public static void JS(ManData myData,ManData data,ChangeFighting changeFighting,FightingState org2,String type,Battlefield battlefield){
		if (data.getType()==3||changeFighting.getChangehp()>=0) {return;}
		AddState addState=null;
		FightingSkill skill=null;

		if (type.equals(TypeUtil.F)||type.equals(TypeUtil.H)||type.equals(TypeUtil.S)||type.equals(TypeUtil.L)||type.equals(TypeUtil.GH)) {
			FightingSkill BDRS=data.getSkillType("不动明王");
			if(BDRS!=null&&data.getHuoyue()>=500) {
				if ((data.getHp_z()*0.3)>data.getHp()) {
					changeFighting.setChangehp(0);
					org2.setSkillskin("1247");
					org2.setProcessState("免疫");
					return;
				}
			}
		}
		skill=data.getAppendSkill(9231);
		if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
			changeFighting.setChangehp(0);
			changeFighting.setChangemp(0);
		 	org2.setProcessState("免疫");
		 	return;
		}
		//心如止水
		addState=data.xzstate("心如水");
		if (addState!=null) {
			double bl=-((double)changeFighting.getChangehp()/data.getHp_z());	
			if (bl>=addState.getStateEffect()){
				changeFighting.setChangehp(0);
				changeFighting.setChangemp(0);
				org2.setProcessState("免疫");
				return;
			}
		}
//      1323	慧心巧思	若受到的伤害小于最大气血、法力值之和的{公式一}%，则可以免疫这次伤害，每5回合触发一次。有{公式六}%概率在生效一次
		skill=data.getSkillType(TypeUtil.BB_E_HXQS);
		if (skill!=null) {
			if (skill.getSkillcontinued()==0||battlefield.CurrentRound>=skill.getSkillcontinued()+5) {
				if (!Battlefield.isV(skill.getSkillgain()/4)) {skill.setSkillcontinued(battlefield.CurrentRound);}
				double bl=-((double)changeFighting.getChangehp()/(data.getHp_z()+data.getMp_z()));	
				if (skill.getSkillgain()>bl){
					changeFighting.setChangehp(0);
					changeFighting.setChangemp(0);
					org2.setProcessState("免疫");
					return;
				}			
			}	
		}
		double xs=100D;
		if (type.equals(TypeUtil.PTGJ)) {
			addState=data.xzstate(TypeUtil.TY_SSC_LFHX);
			if (addState!=null&&addState.getStateEffect()>=3) {
				changeFighting.setChangehp(0);
				changeFighting.setChangemp(0);
			 	org2.setProcessState("免疫");
			 	return;
			}
			addState = data.xzstate("免疫物理");
			if (addState!= null) {
				addState.setStateEffect(addState.getStateEffect() - 1);
				if (addState.getStateEffect() <= 0) {
					data.getAddStates().remove(addState);
				}
				changeFighting.setChangehp(0);
				changeFighting.setChangemp(0);
			 	org2.setProcessState("免疫");
			 	return;
			}
			xs-=data.getQuality().getEwljs();
		}
		xs-=MixDeal.getXS(data.getQihe());
		xs-=data.getjs(type);
		if (myData!=null) {
			skill=myData.getSkillId(23002);
			if(skill!=null) {
				if(myData.getSp()>data.getSp()) {
					xs+=10;
				}
			}
			skill=data.getSkillId(23002);
			if(skill!=null) {
				if(myData.getSp()<data.getSp()) {
					xs-=10;
				}
			}
			xs+=myData.getQuality().getEzs();
			addState=myData.xzstate(TypeUtil.TY_FY_HQQY);
			if (addState!=null) {xs-=addState.getStateEffect();}
			skill=myData.getAppendSkill(9344);
			if (skill!=null&&data.getSkillType(TypeUtil.TY_YW_QQWK)!=null) {xs-=skill.getSkillhurt();}
			skill=myData.getAppendSkill(9148);
			if (skill!=null) {xs+=myData.getCamp()==data.getCamp()?skill.getSkillhurt():-skill.getSkillhurt();}
			if (Sepcies_MixDeal.getRace(myData.getSe())==10004) {
				skill=data.getAppendSkill(9408);
				if (skill!=null) {xs-=skill.getSkillhurt();}
			}
			addState=myData.xzstate(TypeUtil.TY_F_YQDS);
			if (addState!=null) {xs-=addState.getStateEffect();}
		}
		addState=data.xzstate(TypeUtil.TY_YW_MDHL);
		if (addState!=null) {xs+=addState.getStateEffect();}
		addState=data.xzstate("玉净散");
		if (addState!=null) {xs-=addState.getStateEffect();}
		if (type.equals(TypeUtil.F)||type.equals(TypeUtil.S)) {
			skill=data.getAppendSkill(9122);
			if (skill!=null) {xs+=skill.getSkillhurt();}	
			skill=data.getAppendSkill(9249);
			if (skill!=null) {xs-=skill.getSkillhurt();}	
			addState=data.xzstate("风水");
			if (addState!=null) {xs-=addState.getStateEffect();}
			xs-=data.getQuality().getExfjs();
			
		}else if (type.equals(TypeUtil.L)||type.equals(TypeUtil.H)) {
			skill=data.getAppendSkill(9122);
			if (skill!=null) {xs+=skill.getSkillhurt();}
			skill=data.getAppendSkill(9249);
			if (skill!=null) {xs-=skill.getSkillhurt();}	
			addState=data.xzstate("雷火");
			if (addState!=null) {xs-=addState.getStateEffect();}
			xs-=data.getQuality().getExfjs();
		}else if (type.equals(TypeUtil.GH)||type.equals(TypeUtil.PTGJ)) {
			skill=data.getAppendSkill(9122);
			if (skill!=null) {xs+=skill.getSkillhurt();}
			addState=data.xzstate("鬼力");
			if (addState!=null) {xs-=addState.getStateEffect();}
			if (type.equals(TypeUtil.PTGJ)) {
				skill=data.getAppendSkill(9347);
		    	if (skill!=null) {xs-=skill.getSkillhurt();}
			}else {
				xs-=data.getQuality().getExfjs();
			}
		}else if (type.equals(TypeUtil.ZS)) {
			xs-=data.getQuality().getEzsjs();
		}
		
		skill=data.getSkillType("鼓音三叠");
		if (skill!=null) {if (Battlefield.isV(skill.getSkillgain())) {xs-=skill.getSkillhurt();}}
		
		if (data.getvalue(0)<=0.3) {
			skill=data.getSkillType("天见尤怜");
			if (skill!=null) {xs-=80;}
		}
		addState=data.xzstate("伤害加深");
		if (addState!=null) {xs+=addState.getStateEffect();}
		addState=data.xzstate(TypeUtil.TZ_XSJS);
		if (addState!=null) {xs+=addState.getStateEffect();}
		
//		 1309	背向	受到伤害时，有{公式三}%几率背向敌人，以减少{公式一}%受到的物理、法术、灵宝伤害。
		skill=data.getSkillType(TypeUtil.BB_E_BX);
		if (skill!=null&&Battlefield.isV(skill.getSkillhurt())) {
			xs-=skill.getSkillgain();
			org2.setText("背向#2");
		}
		addState=data.xzstate("刚柔兼备");
		if (addState!=null) {
			xs +=(addState.getStateEffect()*0.00167);
		}

		addState=data.xzstate("以静制动");
		if (addState!=null) {xs-=(addState.getStateEffect()*0.42);}

		addState=data.xzstate("法门减伤害");
		if (addState!=null) {xs-=(addState.getStateEffect()*0.00176);}

		addState=data.xzstate("妙法莲华");
		if (addState!=null) {xs-=(addState.getStateEffect()*0.39);}
		if (xs!=100) {
			xs=xs/100.0;
			if (xs<0) {xs=0;}
			changeFighting.setChangehp((int)(changeFighting.getChangehp()*xs));		
		}
	} 
//	1320	黄泉一笑	物理攻击每致死1个目标，可为本方死亡单位回复{公式一}%最大血量(优先主人)，每回合最多可回复2个目标。
	/**黄泉一笑*/
	public static void BB_E_HQYX(ManData myData,ManData data,List<FightingState> list,Battlefield battlefield){
		FightingSkill skill=myData.getSkillType(TypeUtil.BB_E_HQYX);
		if (skill==null) {return;}
		if (battlefield.CurrentRound>skill.getSkillcontinued()) {
			skill.setSkillbeidong(battlefield.CurrentRound);
			skill.setSkillsum(0);
		}
		if (skill.getSkillsum()>=2) {return;}
		List<ManData> datas=MixDeal.get(false, myData, 2, data.getCamp(), 0, 3, 0, 0, 5, -1, battlefield, 0);
		if (datas.size()==0) {return;}
		skill.setSkillsum(skill.getSkillsum()+1);
		data=null;
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).getMan()+5==myData.getMan()) {
				data=datas.get(i);
				break;
			}
		}
		if (data==null) {data=datas.get(0);}
		FightingState org3=new FightingState();
		org3.setCamp(data.getCamp());
		org3.setMan(data.getMan());
		org3.setStartState(TypeUtil.JN);
		org3.setText("黄泉一笑#2");
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangehp((int)(data.getHp_z()*skill.getSkillgain()/100D));
		ChangeProcess(changeFighting,null,data,org3,"药",list,battlefield);
	}

	public static void FM_ylqy(ManData myData,ManData data,int fmhx, List<FightingState> list,Battlefield battlefield){
		AddState addState = data.xzstate("鱼龙潜跃");
		if (addState == null) {return;}
		int camp=myData.getCamp();
		List<ManData> datas1=SSCAction.minhp(camp, 1,battlefield);
		data=datas1.get(0);
		FightingState org3=new FightingState();
		org3.setCamp(data.getCamp());
		org3.setMan(data.getMan());
		org3.setStartState(TypeUtil.JN);
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangehp(fmhx);
		ChangeProcess(changeFighting,null,data,org3,"药",list,battlefield);
	}
	public static void FM_hjlp(ManData myData,ManData data,int fmhx, List<FightingState> list,Battlefield battlefield){
		AddState addState = data.xzstate("虎踞龙蟠");
		if (addState == null) {return;}
		int camp=myData.getCamp();
		List<ManData> datas1=SSCAction.minhp(camp, 1,battlefield);
		data=datas1.get(0);
		FightingState org3=new FightingState();
		org3.setCamp(data.getCamp());
		org3.setMan(data.getMan());
		org3.setStartState(TypeUtil.JN);
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangehp(fmhx);
		ChangeProcess(changeFighting,null,data,org3,"药",list,battlefield);
	}
}
