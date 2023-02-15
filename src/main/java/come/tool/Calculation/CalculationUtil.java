package come.tool.Calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.MountSkill;
import org.come.entity.RoleSummoning;
import org.come.entity.Suit;
import org.come.model.ColorScheme;
import org.come.model.Skill;
import org.come.model.Title;
import org.come.server.GameServer;
import org.come.tool.CustomFunction;
import org.come.tool.Goodtype;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;

import com.gl.util.LingXiUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleMixDeal;
import come.tool.FightingData.FightingLingbao;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingSummon;
import come.tool.FightingData.GetqualityUntil;
import come.tool.FightingData.ManData;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.Ql;
import come.tool.Mixdeal.CreepsMixdeal;
import come.tool.Role.Hang;
import come.tool.Role.RoleData;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGRole;
import come.tool.Scene.DNTG.DNTGScene;

public class CalculationUtil {
	//掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半|每天领取268仙玉
	/**初始化等级抗性
	 * @return */
	public static void initGradeQl(){
		String[] kx={"抗混乱","抗封印","抗昏睡","抗中毒","抗风","抗火","抗雷","抗水","命中率","狂暴率","致命率","物理吸收","抗遗忘","抗三尸","抗鬼火","抗浩然正气","躲闪率"};
		String[] ren={"0=4=1","1=4=1","2=4=1","3=4=1"};
		String[] mo={"0=8=1","1=8=1","2=8=1","3=8=1","4=12=1","5=12=1","6=12=1","7=12=1","8=20=1","11=8=1"};
		String[] xian={"4=4=1","5=4=1","6=4=1","7=4=1"};
		String[] gui={"15=1=-100","16=4=1","0=6=1","1=6=1","2=6=1","3=6=1","14=6=1","12=6=1","13=6=120","4=8=-1","5=8=-1","6=8=-1","7=8=-1","8=12=1"};
		String[] LONG={"11=6=1","0=6=1","1=6=1","2=6=1","3=6=1","12=6=1","8=20=1"};
		BaseValue.GradeQls=new HashMap<>();
		BaseValue.GradeQls.put(new BigDecimal(10001), getGradeQl(kx, ren));
		BaseValue.GradeQls.put(new BigDecimal(10002), getGradeQl(kx, mo));
		BaseValue.GradeQls.put(new BigDecimal(10003), getGradeQl(kx, xian));
		BaseValue.GradeQls.put(new BigDecimal(10004), getGradeQl(kx, gui));
		BaseValue.GradeQls.put(new BigDecimal(10005), getGradeQl(kx, LONG));
	}
//	50
//	100
//	200
//	400
	/***/
	public static GradeQl[] getGradeQl(String[] kx,String[] vs){
		GradeQl[] vsQl=new GradeQl[vs.length];
		for (int i = 0; i < vs.length; i++) {
			String[] v=vs[i].split("=");
			vsQl[i]=new GradeQl(kx[Integer.parseInt(v[0])], Integer.parseInt(v[1]), Integer.parseInt(v[2]));
		}
		return vsQl;
	}

	static String[] limitTypes=new String[]{"变身卡","强法型","加抗型","增益型","VIP","回蓝符","经验","SVIP","帮派","单人竞技场"};
	static String[] baseTypes=new String[]{"根骨","灵性","力量","敏捷","定力"};
	//TODO 战斗抗性、技能等等
	/**人物战斗包加载*/
	public static void loadRoleBattle(ManData manData,LoginResult login,RoleData roleData,Map<String,Double> roleMap,Map<String,Double> map,Map<String,Double> eMap,List<BaseEquip> equips,BattleData battleData){
		if (roleMap==null) {
			roleMap=new HashMap<>();
		}else {
			roleMap.clear();
		}
		if (map==null) {
			map=new HashMap<>();
		}else {
			map.clear();
		}
		if (eMap==null) {
			eMap=new HashMap<>();
		}else {
			eMap.clear();
		}
		if (equips==null) {
			equips=new ArrayList<BaseEquip>();
		}else {
			equips.clear();
		}
		int lvl=BattleMixDeal.lvlint(login.getGrade());
		int zs =BattleMixDeal.lvltrue(login.getGrade());
		manData.setLvl(lvl);
		manData.setZs( zs );
		manData.setManname(login.getRolename());
		manData.setId(login.getRole_id().intValue());
		manData.setSe(login.getSpecies_id());
		manData.setFmsld(login.getFmsld());

		map.put("根骨", (double)login.getBone());
		map.put("灵性", (double)login.getSpir());
		map.put("力量", (double)login.getPower());
		map.put("敏捷", (double)login.getSpeed());
		if (zs==4) {
			map.put("定力", (double)login.getCalm());
		}
		addBaseQl(roleMap, roleData.getBorns());//修正
		BaseValue.gradeQl(login.getRace_id(),lvl,zs,roleMap);//等级
		Title title =GameServer.getTitle(login.getTitle());//称谓抗性
		addBaseQl(map, title!=null?title.getQls():null);
		manData.setExpXS(1D);
		for (int i = 0; i < limitTypes.length; i++) {//时效类道具
			UseCardBean limit=roleData.getLimit(limitTypes[i]);
			if (limit!=null) {
				if (i<5||i==8||i==9) {
					addBaseQl(map,limit.getQls(),manData);
				}else if (i==5) {
					manData.addAddState("回蓝", Integer.parseInt(limit.getValue()), 0, 9999);
				}else if (i==6) {
					manData.setExpXS(manData.getExpXS()+1);
				}else if (i==7) {
					if (battleData!=null&&!PK_MixDeal.isPK(battleData.getBattleType())) {
						addBaseQl(map,limit.getQls(),manData);
					}
				}
			}
		}
		//经脉开始
		String str = login.getMeridians();
		List<BaseMeridians> list = BaseMeridians.createBaseMeridiansList(str);
		BaseQl[] qls = BaseMeridians.createBaseQl(list);
		addBaseQl(map, qls);
		//经脉结束
		addBaseQl(roleMap, roleData.getXls(1));//小成修炼
		addBaseQl(roleMap, roleData.getXls(40));//帮派抗性
		addBaseQl(map, roleData.getXls(2));//大成修炼
		addBaseQl(map, roleData.getXls(41));//经脉加成
		addBaseQl(map, roleData.getXls(42));
		getSkill(manData,map,roleData.getSkills(),roleData.getLoginResult());//技能加成
		if (login.getRace_id().longValue()==10005) {//龙鳞技能加载
			Skill sl=GameServer.getSkill("3035");
			if (sl!=null) {
				FightingSkill fightingSkill=new FightingSkill(sl, lvl, zs,1,0,0);
				manData.addSkill(fightingSkill);
			}
		}
		for (int i = 0; i < roleData.getFs().size()&&i<2; i++) {
			Lingbao fabao=AllServiceUtil.getLingbaoService().selectLingbaoByID(roleData.getFs().get(i).getId());
			if (fabao!=null) {
				addVS(map, fabao.getKangxing());//法宝抗性
				Skill skill=GameServer.getSkill(fabao.getBaoname());
				if (skill!=null) {
					int pz=BaseValue.getFBlvl(manData.getZs(), manData.getlvl(),BaseValue.getQv(fabao.getBaoquality()), fabao.getLingbaolvl().intValue());
					FightingSkill fightingSkill=new FightingSkill(skill,fabao.getLingbaolvl().intValue(),0,1,0,pz);
					manData.addSkill(fightingSkill);
				}
			}
		}
		if (login.getBabyId()!=null) {//孩子技能加成
			Baby baby=AllServiceUtil.getBabyService().selectBabyById(login.getBabyId());
			if (baby!=null) {
				manData.setChild(new ManData(baby, manData.getCamp(), manData.getMan(), map));
			}
		}
		int s=0; //拥有6阶仙器/神兵数量

		manData.setXk_id(roleData.getGoodEquip()[13]);
		long equipType=0;//武器类型
		for (int i = 0; i < roleData.getGoodEquip().length; i++) {//装备加成
			if (roleData.getGoodEquip()[i]!=null) {
				Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(roleData.getGoodEquip()[i]);
				BaseEquip baseEquip=good!=null?good.getEquip():null;
				if (baseEquip!=null) {
					baseEquip.setQhv(good.getQhv());
					equips.add(baseEquip);
				}
				if (i==0&&good!=null) {
					equipType=Long.parseLong(good.getSkin());
				}

				// 身上装备是神兵
				if (Goodtype.GodEquipment_God(good.getType())) {
					for (BaseQl ql : good.getEquip().getQls()) {
						if (ql.getKey().equals("等级") && ql.getValue() == 6) {
							s++;
							break;
						}
					}
				} else if (Goodtype.GodEquipment_xian(good.getType())) {
					for (BaseQl ql : good.getEquip().getQls()) {
						if (ql.getKey().equals("阶数") && ql.getValue() == 6) {
							s++;
							break;
						}
					}
				}else if (Goodtype.GodEquipment_Ding(good.getType())) {
					for (BaseQl ql : good.getEquip().getQls()) {
						if (ql.getKey().equals("阶数") && ql.getValue() == 6) {
							s++;
							break;
						}
					}
				}
			}
		}
		//特技
		List<BaseSkill> baseSkills=null;
		//套装id
		Map<Integer,BaseSuit> suitMap=null;
		//星阵
		BaseStar baseStar = null;
		boolean is=true;
		int qhv=0;

		while (is) {
			is=false;
			eMap.clear();
			if (baseSkills!=null) {
				baseSkills.clear();
			}
			if (suitMap!=null) {
				suitMap.clear();
			}
			int size=0;
			qhv=0;
			for (int i = equips.size()-1; i >=0; i--) {
				BaseEquip baseEquip=equips.get(i);
				if (baseEquip.getQhv()!=null) {
					size++;
					if (qhv==0||qhv>baseEquip.getQhv()) {
						qhv=baseEquip.getQhv();
					}
				}
			}
			if (size==5&&qhv!=0) {
				addValue(eMap, "四抗上限",     qhv*0.4);
				addValue(eMap, "装备抗性上限", qhv*1.5);
				addValue(eMap, "HP", qhv*2000);
				addValue(eMap, "MP", qhv*1000);
			}else {
				qhv=0;
			}
			baseStar=null;
			for (int i = equips.size()-1; i >=0; i--) {
				BaseEquip baseEquip=equips.get(i);
				if (baseEquip.getQls()!=null) {
					double qhXS=1D;
					if (baseEquip.getQhv()!=null) {
						qhXS+=BaseEquip.getQHGemXS(baseEquip.getQhv())/100D;
					}
					for (int j = baseEquip.getQls().size()-1; j >=0; j--) {
						BaseQl baseQl=baseEquip.getQls().get(j);
						addValue(eMap, baseQl.getKey(), baseQl.getValue()*qhXS);
					}
				}
				if (baseEquip.getQlews()!=null) {
					for (int j = baseEquip.getQlews().size()-1; j >=0; j--) {
						BaseQl baseQl=baseEquip.getQlews().get(j);
						addValue(eMap, baseQl.getKey(), baseQl.getValue());
					}
				}
				if (baseEquip.getBaseSkills()!=null) {
					if (baseSkills==null) {baseSkills=new ArrayList<>();}
					for (int j = baseEquip.getBaseSkills().size()-1; j >=0; j--) {
						baseSkills.add(baseEquip.getBaseSkills().get(j));
					}
				}
				if (baseEquip.getBaseSuit()!=null) {
					if (suitMap==null) {suitMap=new HashMap<>();}
					BaseSuit baseSuit=suitMap.get(baseEquip.getBaseSuit().getSuitId());
					if (baseSuit==null) {
						baseSuit=new BaseSuit(baseEquip.getBaseSuit().getSuitId(), baseEquip.getBaseSuit().getLvl(), 1);
						suitMap.put(baseSuit.getSuitId(), baseSuit);
					}else {
						baseSuit.setSum(baseSuit.getSum()+1);
						if (baseSuit.getLvl()>baseEquip.getBaseSuit().getLvl()) {
							baseSuit.setLvl(baseEquip.getBaseSuit().getLvl());
						}
					}
				}
				if (baseEquip.getBaseStar()!=null) {
					baseStar=baseEquip.getBaseStar();
				}
			}
			for (int i = equips.size()-1; i >=0; i--) {
				BaseLimit baseLimit=equips.get(i).getBaseLimit();
				if (baseLimit!=null) {
					if (baseLimit.getXs()!=-999) {
						if (baseLimit.getGg()!=0&&!isValue("根骨",(int)(baseLimit.getGg()*baseLimit.getXs()/100),map, eMap,roleMap)) {
							equips.remove(i);
							is=true;
							break;
						}else if (baseLimit.getLx()!=0&&!isValue("灵性",(int)(baseLimit.getLx()*baseLimit.getXs()/100),map, eMap,roleMap)) {
							equips.remove(i);
							is=true;
							break;
						}else if (baseLimit.getLm()!=0&&!isValue("力量",(int)(baseLimit.getLm()*baseLimit.getXs()/100),map, eMap,roleMap)) {
							equips.remove(i);
							is=true;
							break;
						}else if (baseLimit.getMj()!=0&&!isValue("敏捷",(int)(baseLimit.getMj()*baseLimit.getXs()/100),map, eMap,roleMap)) {
							equips.remove(i);
							is=true;
							break;
						}
					}
					if (!baseLimit.isL()) {
						if (zs<baseLimit.getZs()) {
							equips.remove(i);
							is=true;
							break;
						}else if (zs==baseLimit.getZs()&&lvl<baseLimit.getLvl()) {
							equips.remove(i);
							is=true;
							break;
						}else if (zs>baseLimit.getZsMax()) {
							equips.remove(i);
							is=true;
							break;
						}else if (zs==baseLimit.getZsMax()&&lvl>baseLimit.getLvlMax()) {
							equips.remove(i);
							is=true;
							break;
						}
					}
				}
			}
		}

		UseCardBean card = roleData.getLimit("变身卡");
		Double cardpf = null;
		if (card!=null) {
			cardpf = card.getQlKey("皮肤");
		}
		// 判断是否存在变身卡皮肤，如果有变身卡不再获取光武皮肤
		equipType=CreepsMixdeal.good((int)equipType);
		if (equipType!=0&&qhv>=12) {
			long se=login.getSpecies_id().longValue();
			if ((equipType == 1 && se == 20001  || equipType == 2 && se == 20001  || equipType == 1 && se == 20002  ||
					equipType == 3 && se == 20002  || equipType == 4 && se == 20003  || equipType == 5 && se == 20003  ||
					equipType == 9 && se == 20004  || equipType == 8 && se == 20004  || equipType == 10 && se == 20005  ||
					equipType == 7 && se == 20005  || equipType == 10 && se == 20006  || equipType == 12 && se == 20006  ||
					equipType == 1 && se == 20007  || equipType == 5 && se == 20007  || equipType == 1 && se == 20008  ||
					equipType == 10 && se == 20008  || equipType == 2 && se == 20009  || equipType == 6 && se == 20009  ||
					equipType == 8 && se == 20010  || equipType == 1 && se == 20010  || equipType == 12 && se == 21001  ||
					equipType == 7 && se == 21001  || equipType == 10 && se == 21002  || equipType == 13 && se == 21002  ||
					equipType == 10 && se == 21003  || equipType == 12 && se == 21003  || equipType == 9 && se == 21004  ||
					equipType == 10 && se == 21004  || equipType == 7 && se == 21005  || equipType == 1 && se == 21005  ||
					equipType == 14 && se == 21006  || equipType == 8 && se == 21006  || equipType == 12 && se == 21007  ||
					equipType == 4 && se == 21007  || equipType == 10 && se == 21008  || equipType == 11 && se == 21008  ||
					equipType == 10 && se == 21009  || equipType == 4 && se == 21009  || equipType == 14 && se == 21010  ||
					equipType == 9 && se == 21010  || equipType == 12 && se == 22001  || equipType == 3 && se == 22001  ||
					equipType == 14 && se == 22002  || equipType == 1 && se == 22002  || equipType == 7 && se == 22003  ||
					equipType == 14 && se == 22003  || equipType == 10 && se == 22004  || equipType == 5 && se == 22004  ||
					equipType == 7 && se == 22005  || equipType == 16 && se == 22005  || equipType == 1 && se == 22006  ||
					equipType == 12 && se == 22006  || equipType == 12 && se == 22007  || equipType == 14 && se == 22007  ||
					equipType == 11 && se == 22008  || equipType == 16 && se == 22008  || equipType == 1 && se == 22009  ||
					equipType == 13 && se == 22009  || equipType == 16 && se == 22010  || equipType == 17 && se == 22010  ||
					equipType == 1 && se == 23001  || equipType == 10 && se == 23001  || equipType == 12 && se == 23002  ||
					equipType == 5 && se == 23002  || equipType == 13 && se == 23003  || equipType == 6 && se == 23003  ||
					equipType == 9 && se == 23004  || equipType == 8 && se == 23004  || equipType == 17 && se == 23005  ||
					equipType == 11 && se == 23005  || equipType == 11 && se == 23006  || equipType == 16 && se == 23006  ||
					equipType == 1 && se == 24001  || equipType == 6 && se == 24001  || equipType == 12 && se == 24002  ||
					equipType == 10 && se == 24002  || equipType == 18 && se == 24003  || equipType == 11 && se == 24003  ||
					equipType == 9 && se == 24004  || equipType == 3 && se == 24004  || equipType == 18 && se == 24005  ||
					equipType == 12 && se == 24005  || equipType == 1 && se == 24006  || equipType == 17 && se == 24006L))  {
				equipType += 18;

			}
			if (cardpf == null && roleData.getLimit("童卡") == null && equipType !=0 && s==5) {


				//long se=login.getSpecies_id().longValue();
				//if ((equipType==2&&se==20001)||(equipType==2&&se==20009)||(equipType==1&&se==20010)||(equipType==1&&se==21005)||(equipType==10&&se==21008)||(equipType==1&&se==22009)||(equipType==1&&se==23001)||(equipType==1&&se==24001)) {
				//equipType+=18;
			}
		}//TODO 获取光武皮肤
		manData.setModel(login.getBattleSkin(equipType));//皮肤
		manData.setHuoyue(removeValue("根骨", map, eMap,roleMap));
		manData.setShanghai(removeValue("灵性", map, eMap,roleMap));
		manData.setKangluobao(removeValue("力量", map, eMap,roleMap));
		manData.setYuanzhu(removeValue("敏捷",map, eMap,roleMap));
		if (zs==4) {
			manData.setQihe(BaseValue.getRoleValue(login.getRace_id(),(int)removeValue("定力",map,eMap,roleMap),lvl,4));
		}
		manData.setBaseStar(baseStar);
		if (baseSkills!=null) {
			for (int j = baseSkills.size()-1; j >= 0; j--) {
				BaseSkill baseSkill=baseSkills.get(j);
				if (baseSkill.getSkillId()==8001) {
					addValue(eMap, "忽视抗混", (int)(manData.getHuoyue()/50)*0.1);
				}else if (baseSkill.getSkillId()==8002) {
					addValue(eMap, "忽视抗封", (int)(manData.getHuoyue()/50)*0.1);
				}else if (baseSkill.getSkillId()==8003) {
					addValue(eMap, "忽视抗睡", (int)(manData.getHuoyue()/50)*0.1);
				}else if (baseSkill.getSkillId()==8004) {
					addValue(eMap, "忽视抗遗忘", (int)(manData.getHuoyue()/50)*0.1);
				}else if (baseSkill.getSkillId()==8005) {
					addValue(eMap, "加强毒", (int)(manData.getHuoyue()/10)*0.1);
				}else if (baseSkill.getSkillId()==8006) {
					addValue(eMap, "忽视抗混", (int)(manData.getShanghai()/30)*0.1);
				}else if (baseSkill.getSkillId()==8007) {
					addValue(eMap, "忽视抗封", (int)(manData.getShanghai()/30)*0.1);
				}else if (baseSkill.getSkillId()==8008) {
					addValue(eMap, "忽视抗睡", (int)(manData.getShanghai()/30)*0.1);
				}else if (baseSkill.getSkillId()==8009) {
					addValue(eMap, "忽视抗遗忘", (int)(manData.getShanghai()/30)*0.1);
				}else if (baseSkill.getSkillId()==8010) {
					addValue(eMap, "加强毒", (int)(manData.getShanghai()/10)*0.1);
				}else if (baseSkill.getSkillId()==8011) {
					addValue(eMap, "忽视抗雷", (int)(manData.getHuoyue()/10)*0.1);
				}else if (baseSkill.getSkillId()==8012) {
					addValue(eMap, "忽视抗火", (int)(manData.getHuoyue()/10)*0.1);
				}else if (baseSkill.getSkillId()==8013) {
					addValue(eMap, "忽视抗风", (int)(manData.getHuoyue()/10)*0.1);
				}else if (baseSkill.getSkillId()==8014) {
					addValue(eMap, "忽视抗水", (int)(manData.getHuoyue()/10)*0.1);
				}else if (baseSkill.getSkillId()==8015) {
					addValue(eMap, "忽视抗鬼火", (int)(manData.getHuoyue()/20)*0.1);
				}else if (baseSkill.getSkillId()==8018) {
					addValue(eMap, "命中率", 15);
				}else if (baseSkill.getSkillId()==8019) {
					addValue(eMap, "命中率", 30);
				}else if (baseSkill.getSkillId()==8022) {
					manData.setExpXS(manData.getExpXS()+0.1);
				}else {
					if (!baseSkill.isAffect()) {
						FightingSkill fightingSkill=new FightingSkill(baseSkill.getSkill(), lvl, zs,0,0,baseSkill.getLvl());
						manData.addSkill(fightingSkill);
					}
				}
			}
		}
		if (suitMap!=null) {
			for (BaseSuit baseSuit:suitMap.values()) {
				Suit suit=GameServer.getSuit(baseSuit.getSuitId());
				BaseSkill[] suitSkills=suit!=null?suit.getSuits():null;
				if (suitSkills!=null) {
					for (int i = 0; i < suitSkills.length; i++) {
						BaseSkill baseSkill=suitSkills[i];
						if (baseSuit.getSum()>=baseSkill.getLvl()) {
							String key=BaseSuit.getsuitSkill(baseSkill.getSkillId());
							if (key!=null) {
								double value=BaseSuit.getSuitValue(baseSkill.getSkillId(), baseSuit.getLvl());
								if (key.equals("加强法术")) {
									addValue(eMap, "加强风", value);
									addValue(eMap, "加强雷", value);
									addValue(eMap, "加强水", value);
									addValue(eMap, "加强火", value);
									addValue(eMap, "加强鬼火", value);
								} else if (key.equals("提抗上限")) {
									addValue(eMap, "四抗",    -value);
									addValue(eMap, "四抗上限", value);
								} else {
									addValue(eMap, key, value);
								}
							}else {
								Skill skill=GameServer.getSkill(baseSkill.getSkillId()+"");
								if (skill!=null) {
									FightingSkill fightingSkill=new FightingSkill(skill,lvl,zs,0,0,baseSuit.getLvl());
									manData.addSkill(fightingSkill);
								}
							}
						}
					}
				}
			}
		}
		manData.setHp_z(getBase(login.getRace_id(), lvl, (int)manData.getHuoyue(),     0, map, eMap,roleMap));
		manData.setMp_z(getBase(login.getRace_id(), lvl, (int)manData.getShanghai(),   1, map, eMap,roleMap));
		manData.setAp(  getBase(login.getRace_id(), lvl, (int)manData.getKangluobao(), 2, map, eMap,roleMap));
		manData.setSp(  getBase(login.getRace_id(), lvl, (int)manData.getYuanzhu(),    3, map, eMap,roleMap));
		manData.setHp(login.getHp().intValue());
		manData.setMp(login.getMp().intValue());
		if (manData.getHp()==0||manData.getHp()>manData.getHp_z()) {manData.setHp(manData.getHp_z());}
		if (manData.getMp()==0||manData.getMp()>manData.getMp_z()) {manData.setMp(manData.getMp_z());}

		List<FightingLingbao> lings=new ArrayList<>();
		Lingbao lingbao=roleData.getLs()!=null?AllServiceUtil.getLingbaoService().selectLingbaoByID(roleData.getLs().getId()):null;
		if (lingbao!=null) {
			addVS(map, lingbao.getKangxing());//灵宝抗性
			FightingLingbao ling=new FightingLingbao(new ManData(lingbao, manData), 1);
			lings.add(ling);
		}
		manData.setLings(lings);
		//召唤兽战斗对象
		List<FightingSummon> pets=new ArrayList<>();
		if (roleData.getPets()!=null) {
			for (int j = roleData.getPets().size()-1; j >=0; j--) {
				Hang hang=roleData.getPets().get(j);
				int play=login.getSummoning_id()!=null&&hang.getId().compareTo(login.getSummoning_id())==0?1:0;
				pets.add(new FightingSummon(play,hang,manData.getCamp(),manData.getMan()));
			}
			String helpBb=roleData.getPackRecord().getHelpBb();
			if (helpBb!=null&&!helpBb.equals("")) {
				String[] vs=helpBb.split("\\|");
				for (int i = vs.length - 1; i >= 0 ; i--) {
					int id=Integer.parseInt(vs[i]);
					for (int j = 0; j < pets.size(); j++) {
						FightingSummon pet=pets.get(j);
						if (pet.getHang().getId().intValue()==id) {
							pet=pets.remove(j);
							pets.add(0,pet);
							break;
						}
					}
				}
			}
		}
		manData.setPets(pets);
		double sk=removeValue("四抗",map,eMap,roleMap);
		if (sk!=0) {
			addValue(eMap, "抗封印", sk);
			addValue(eMap, "抗混乱", sk);
			addValue(eMap, "抗昏睡", sk);
			addValue(eMap, "抗遗忘", sk);
		}
		sk=removeValue("四抗上限",map,eMap,roleMap);
		if (sk!=0) {
			addValue(eMap, "抗封印上限", sk);
			addValue(eMap, "抗混乱上限", sk);
			addValue(eMap, "抗昏睡上限", sk);
			addValue(eMap, "抗遗忘上限", sk);
		}
		sk=removeValue("抗仙法",map,eMap,roleMap);
		if (sk!=0) {
			addValue(map, "抗风", sk);
			addValue(map, "抗火", sk);
			addValue(map, "抗水", sk);
			addValue(map, "抗雷", sk);
		}
		sk=removeValue("抗人法",map,eMap,roleMap);
		if (sk!=0) {
			addValue(map, "抗封印", sk);
			addValue(map, "抗混乱", sk);
			addValue(map, "抗昏睡", sk);
			addValue(map, "抗中毒", sk);
		}


		double ZBMAX=getValue(login.getRace_id(),"装备抗性上限",map,eMap,roleMap,300)+300;
		Ql ql=new Ql();
		List<String> keys = allProperty(map,eMap,roleMap);
		for (int i = keys.size()-1; i >=0; i--) {
			String key=keys.get(i);
			GetqualityUntil.AddR(ql,key,getValue(login.getRace_id(),key,map,eMap,roleMap,ZBMAX));
		}
		sk = removeValue("水魔附身",map,eMap,roleMap);
		if (sk!=0) {
			ql.setRolewxj(0);
			ql.setRolewxm(0);
			ql.setRolewxh(0);
			ql.setRolewxt(0);
			ql.setRolewxs(100);
			ql.setRolewxqkh(ql.getRolewxqkh()+60*sk);
		}
		manData.setQuality(ql);

		if (battleData!=null&&battleData.getSceneId()!=null&&battleData.getSceneId()==SceneUtil.DNTGID) {
			Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
			if (scene!=null) {
				DNTGRole dntgRole=((DNTGScene)scene).getRole(login.getRole_id());
				if (dntgRole!=null) {
					addDNTG(manData, dntgRole.getSLJC());
				}
			}
		}
	}
	/**取所有的抗性*/
	public static List<String> allProperty(Map<String,Double> map,Map<String,Double> eMap,Map<String,Double> roleMap) {
		List<String> Propertys = new ArrayList<String>();
		for (String key : eMap.keySet()) {
			Propertys.add(key);
		}
		for (String key : map.keySet()) {
			if (!Propertys.contains(key)){
				Propertys.add(key);
			}
		}
		for (String key : roleMap.keySet()) {
			if (!Propertys.contains(key)){
				Propertys.add(key);
			}
		}
		return Propertys;
	}
	public static int getBase(BigDecimal race_id,int lvl,int value,int type,Map<String,Double> map,Map<String,Double> eMap,Map<String,Double> roleMap){
		value=BaseValue.getRoleValue(race_id, value,lvl, type);
		if (type==0) {
			value+=removeValue("hp", map, eMap,roleMap);
			value+=removeValue("HP", map, eMap,roleMap);
			value+=removeValue("加气血", map, eMap,roleMap);
			value*=(removeValue("HP成长", map, eMap,roleMap)+1);
			value*=(removeValue("加强气血", map, eMap,roleMap)/100+1);
		}else if (type==1) {
			value+=removeValue("mp", map, eMap,roleMap);
			value+=removeValue("MP", map, eMap,roleMap);
			value+=removeValue("加法力", map, eMap,roleMap);
			value*=(removeValue("MP成长", map, eMap,roleMap)+(removeValue("加强法力", map, eMap,roleMap)/100)+1);
		}else if (type==2) {
			value+=removeValue("ap", map, eMap,roleMap);
			value+=removeValue("AP", map, eMap,roleMap);
			value+=removeValue("攻击", map, eMap,roleMap);
			value+=removeValue("加攻击", map, eMap,roleMap);
			value*=(removeValue("AP成长", map, eMap,roleMap)+(removeValue("加强攻击", map, eMap,roleMap)/100)+1);
		}else if (type==3) {
			value+=removeValue("sp", map, eMap,roleMap);
			value+=removeValue("SP", map, eMap,roleMap);
			value+=removeValue("速度", map, eMap,roleMap);
			value+=removeValue("加速度", map, eMap,roleMap);
			value+=removeValue("附加速度", map, eMap,roleMap);
			value*=(removeValue("SP成长", map, eMap,roleMap)+(removeValue("加强速度", map, eMap,roleMap)/100)+1);
		}
		return value;
	}
	/**取值 上限判断*/
	public static double getValue(BigDecimal raceId,String key,Map<String,Double> map,Map<String,Double> eMap,Map<String,Double> roleMap,double ZBMAX){
		Double additional=map.get(key);
		Double equip=eMap.get(key);
		Double role=roleMap.get(key);
		if (additional==null) {additional=0D;}
		if (equip==null) {equip=0D;}
		if (role==null) {role=0D;}
		if (key.endsWith("上限")) {
			return equip+additional+role;
		}else if (key.equals("抗混乱") || key.equals("抗昏睡") || key.equals("抗封印") || key.equals("抗遗忘")) {
			double sx = BaseValue.Upper(key, raceId);
			sx = sx * (1 + (getValue(raceId,key + "上限",map,eMap,roleMap,ZBMAX)/ 100.0));
			double z = equip + role;
			return (z > sx ? sx : z) + additional;
		}else if (key.equals("抗三尸")||key.equals("抗浩然正气")||key.equals("抗青面獠牙")||key.equals("抗天魔解体")||key.equals("抗小楼夜哭")||key.equals("抗分光化影")) {

		}else if (key.startsWith("抗")||key.equals("物理吸收")) {
			equip = equip > 120 ? 120 : equip;
		}else if (key.equals("连击率")) {
			return equip + additional+role > 120 ? 120 : equip + additional+role;
		}
		return equip+additional+role;
	}
	/**取值*/
	public static double removeValue(String key,Map<String,Double> map,Map<String,Double> eMap,Map<String,Double> roleMap){
		Double additional=map.remove(key);
		Double equip=eMap.remove(key);
		Double role=roleMap.remove(key);
		if (additional==null) {additional=0D;}
		if (equip==null) {equip=0D;}
		if (role==null) {role=0D;}
		return equip+additional+role;
	}
	/**取值*/
	public static double removeValue(String key,Map<String,Double> map){
		Double value=map.remove(key);
		return value!=null?value:0D;
	}
	/**满足 返回ture*/
	public static boolean isValue(String key,int value,Map<String,Double> map,Map<String,Double> eMap,Map<String,Double> roleMap){
		Double additional=map.get(key);
		Double equip=eMap.get(key);
		Double role=roleMap.get(key);
		if (additional==null) {additional=0D;}
		if (equip==null) {equip=0D;}
		if (role==null) {role=0D;}
		return equip+additional+role>=value;
	}
	/**技能*/
	public static void getSkill(ManData manData,Map<String,Double> map,List<BaseSkill> baseSkills,LoginResult login){
		if (baseSkills!=null) {
			for (int i = 0; i < baseSkills.size(); i++) {
				BaseSkill baseSkill=baseSkills.get(i);
				if (baseSkill.getQl()!=null) {
					addValue(map, baseSkill.getQl().getKey(),baseSkill.getQl().getValue());
				}else {
					double sl = login.getMeridiansValue("法术熟练");
					FightingSkill fightingSkill;
					if (baseSkill.getSkillId()>1000 && baseSkill.getSkillId()<=1100 && sl > 0) {
						fightingSkill=new FightingSkill(baseSkill.getSkill(),manData.getlvl(),manData.getZs(),baseSkill.getLvl()+(int)sl,0,0);
					} else {
						fightingSkill=new FightingSkill(baseSkill.getSkill(),manData.getlvl(),manData.getZs(),baseSkill.getLvl(),0,0);
					}
					manData.addSkill(fightingSkill);
				}
			}
		}
	}
	/**符文类添加*/
	public static void addBaseQl(Map<String,Double> map,BaseQl[] vs,ManData manData){
		if (vs==null) {return;}
		for (int i = 0; i < vs.length; i++) {
			if (vs[i]!=null) {
				if (vs[i].getKey().equals("技能")) {
					Skill skill=GameServer.getSkill(((int)vs[i].getValue())+"");
					if (skill!=null) {
						FightingSkill fightingSkill=new FightingSkill(skill, manData.getlvl(), manData.getZs(),1,0,1);
						if (fightingSkill.getSkillid()==1832) {
							fightingSkill.setSkillhurt(33);
						}else if (fightingSkill.getSkillid()==1833) {
							fightingSkill.setSkillhurt(66);
						}
						manData.addSkill(fightingSkill);
					}
				}else if (vs[i].getKey().equals("经验加成")) {
					manData.setExp2XS(manData.getExp2XS()+vs[i].getValue()/100D);
				}else {
					addValue(map, vs[i].getKey(), vs[i].getValue());
				}
			}
		}
	}
	public static void addBaseQl(Map<String,Double> map,BaseQl[] vs){
		if (vs==null) {return;}
		for (int i = 0; i < vs.length; i++) {
			if (vs[i]!=null) {
				addValue(map, vs[i].getKey(), vs[i].getValue());
			}
		}
	}
	public static void addValue(Map<String,Double> map,String key,double value){
		Double v=map.get(key);
		if (v!=null) {
			value+=v;
		}
		map.put(key, value);
	}
	public static void addVS(Map<String,Double> map,String v){
		if (v==null||v.equals("")||!v.startsWith("抗")) {
			return;
		}
		try {
			String[] vs=v.split("=");
			double value=Double.parseDouble(vs[1]);
			if (value<=10.1) {
				addValue(map,vs[0],value);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**大闹属性添加*/
	public static void addDNTG(ManData data,String text){
//		LID$LVL&ID$LVL
		if (text==null||text.equals("")) {
			return;
		}
		String[] vs=text.split("&");
		for (int i = 0; i < vs.length; i++) {
			String[] v=vs[i].split("\\$");

			Skill skill=GameServer.getSkill(v[0]);
			if (skill!=null) {
				int lvl=Integer.parseInt(v[1]);
				double value=lvl*skill.getGrow();
				if (skill.getSkillid()==10001) {
					data.setHp_z((int)(data.getHp_z()+value));
					data.setHp((int)(data.getHp()+value));
				}else if (skill.getSkillid()==10002) {
					GetqualityUntil.AddR(data.getQuality(),"忽视抗封",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗混",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗昏",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视遗忘",value);
				}else if (skill.getSkillid()==10003) {
					data.setMp_z((int)(data.getMp_z()+value));
					data.setMp((int)(data.getMp()+value));
				}else if (skill.getSkillid()==10004) {
					GetqualityUntil.AddR(data.getQuality(),"忽视抗风",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗雷",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗水",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗火",value);
					GetqualityUntil.AddR(data.getQuality(),"忽视抗鬼火",value);
					GetqualityUntil.AddR(data.getQuality(),"加强毒伤害",value);
				}else if (skill.getSkillid()==10005) {
					data.setSp((int)(data.getvalue(7)+value));
				}else if (skill.getSkillid()==10006) {
					GetqualityUntil.AddR(data.getQuality(),"加强三尸虫",value);
				}else if (skill.getSkillid()==10007) {
					data.setAp((int)(data.getvalue(5)+value));
				}else if (skill.getSkillid()==10008) {
					GetqualityUntil.AddR(data.getQuality(),"忽视抗震慑",value);
				}
			}
		}
	}

	static String[] evs=new String[]{"根骨","灵性","力量","敏捷","增加气血","增加法力","增加攻击"};
	public static void getPet(Ql ql,RoleSummoning pet,Mount mount,ManData data){
		int up=120;//抗性上限过滤
		int[] pets=new int[5];
		int lvl=data.getLvl();
		double grow=Double.valueOf(pet.getGrowlevel());
		pets[0]=pet.getHp();
		pets[1]=pet.getMp();
		pets[2]=pet.getAp();
		pets[3]=pet.getSp();
		pets[4]=0;
		int zBone=pet.getBone();
		int zSpir=pet.getSpir();
		int zPower=pet.getPower();
		int zSpeed=pet.getSpeed();
		int zCalm=pet.getCalm();
		int addhp=0,addmp=0,addap=0;
		StringBuffer buffer=new StringBuffer();
		buffer.append(pet.getSummoningskin());
		buffer.append("_1_7");
		if (pet.getColorScheme()!=null&&!pet.getColorScheme().equals("")) {
			buffer.append("_");
			buffer.append(pet.getColorScheme());
		}
		try {
			BaseSkill skill0=null;//召唤兽装备特技
			BaseSkill skill1=null;//召唤兽装备特技
			BaseSkill skill2=null;//召唤兽装备特技
			Map<String,Double> map=null;
			if (pet.getStye()!=null&&pet.getStye().length()>1) {
				String[] v=pet.getStye().split("\\|");
				for (int i = 1; i < v.length; i++) {
					String[] vs=v[i].split("-");
					if (vs.length>=2) {
						if (vs[0].equals("3")&&vs.length>2) {
							String color=null;
							if (vs.length>3) {
								ColorScheme value=GameServer.getColor(vs[3]);
								color=value!=null?value.getValue():null;
							}
							buffer.append("&");
							buffer.append(vs[2]);
							buffer.append("_1_7");
							if (color!=null) {
								buffer.append("_");
								buffer.append(color);
							}
						}
						Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[1]));
						BaseEquip baseEquip=good!=null?good.getEquip():null;
						if (baseEquip==null) {
							continue;
						}
						if (baseEquip.getQls()!=null) {
							for (int j = baseEquip.getQls().size()-1; j >=0; j--) {
								BaseQl baseQl=baseEquip.getQls().get(j);
								if (baseQl.getKey().endsWith("等级")) {
									if (map==null) {map=new HashMap<>();}
									addValue(map, baseQl.getKey(),baseQl.getValue());
								}else if (baseQl.getKey().endsWith(evs[0])) {
									zBone+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[1])) {
									zSpir+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[2])) {
									zPower+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[3])) {
									zSpeed+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[4])) {
									addhp+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[5])) {
									addmp+=baseQl.getValue();
								}else if (baseQl.getKey().endsWith(evs[6])) {
									addap+=baseQl.getValue();
								}else {
									GetqualityUntil.AddR(ql, baseQl.getKey(), baseQl.getValue());
								}
							}
						}
						if (baseEquip.getBaseSkills()!=null&&baseEquip.getBaseSkills().size()!=0) {
							if (good.getType()==510) {
								skill0=baseEquip.getBaseSkills().get(0);
							}else if (good.getType()==511) {
								skill1=baseEquip.getBaseSkills().get(0);
							}else if (good.getType()==512) {
								skill2=baseEquip.getBaseSkills().get(0);
							}
						}
					}
				}
			}
			pets[0]=BaseValue.getPetValue(lvl,zBone ,grow,pets[0],0)+addhp;
			pets[1]=BaseValue.getPetValue(lvl,zSpir ,grow,pets[1],1)+addmp;
			pets[2]=BaseValue.getPetValue(lvl,zPower,grow,pets[2],2)+addap;
			pets[3]=BaseValue.getPetValue(lvl,zSpeed,grow,pets[3],3);
			pets[4]=BaseValue.getPetValue(lvl,zCalm ,grow,pets[4],4);
			getSI(pets, pet.getSkillData());
			pet.getLX(pets);
			if(pet.getPetSkills()!=null) {
				if(pet.getPetSkills().contains("1248")) {
					pets[2]*=1.3;
				}
			}
			if (mount!=null) {
				up+=20;
				List<MountSkill> mountSkills=mount.getMountskill();
				if (mountSkills!=null) {
					double xs1=1,xs2=1,xs3=1,xs4=1;
					for (int i = 0; i < mountSkills.size(); i++) {
						String ms=CalculationMount.calculateAddition(mount, mountSkills.get(i).getSkillname(), ql);
						if (ms!=null) {
							String v1[] =ms.split("=");
							if (v1[0].equals("HP")){
								xs1+=Double.parseDouble(v1[1]);
							}else if (v1[0].equals("MP")) {
								xs2+=Double.parseDouble(v1[1]);
							}else if (v1[0].equals("AP")) {
								xs3+=Double.parseDouble(v1[1]);
							}else if (v1[0].equals("SP")) {
								xs4+=Double.parseDouble(v1[1]);
							}
						}
						pets[0]*=xs1;
						pets[1]*=xs2;
						pets[2]*=xs3;
						pets[3]*=xs4;
					}
				}
			}

			//天生抗性
			if (pet.getResistance()!=null&&!pet.getResistance().equals("")) {
				String[] v=pet.getResistance().split("\\|");
				if (v.length>=3) {
					cl(data);
					return;
				}
				for (int i=0;i<v.length;i++) {
					String[] v1=v[i].split("=");
					double value=Double.parseDouble(v1[1]);
					if (value!=30) {cl(data);}//防修改
					GetqualityUntil.AddR(ql, v1[0], value);
				}
			}
			if (pet.getInnerGoods()!=null&&!pet.getInnerGoods().equals("")) {
				String[] vv=pet.getInnerGoods().split("\\|");
				for (int i = 0; i < vv.length; i++) {
					Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vv[i]));
					if (good!=null){
						String goodname=good.getGoodsname();
						boolean type=(goodname.equals("红颜白发")||goodname.equals("梅花三弄")||goodname.equals("开天辟地")||goodname.equals("万佛朝宗")||goodname.equals("暗渡陈仓")||goodname.equals("借力打力")||goodname.equals("凌波微步"));
						if (data==null&&!type) {continue;}
						String[] strings = good.getValue().split("\\|");
						String[] stringLevel = strings[2].split("\\=");
						String[] stringLevel2 = stringLevel[1].split("\\转");
						int nddj = Integer.parseInt(stringLevel2[1]);//内丹等级
						if (map!=null) {
							Double lll=map.get(goodname+"等级");
							if (lll!=null) {nddj+=lll;}
						}
						int ndzscs = Integer.parseInt(stringLevel2[0]);;//内丹转生次数
						if (data!=null) {
							if (goodname.equals("红颜白发")||goodname.equals("梅花三弄")||goodname.equals("开天辟地")||goodname.equals("万佛朝宗")) {data.neidang("tj", ndzscs);}
							else if (goodname.equals("分光化影")||goodname.equals("天魔解体")||goodname.equals("小楼夜哭")||goodname.equals("青面獠牙")) {data.neidang("mj", ndzscs);}
							else if (goodname.equals("乘风破浪")||goodname.equals("霹雳流星")||goodname.equals("大海无量")||goodname.equals("祝融取火")) {data.neidang("xl", ndzscs);}
							else {data.neidang("rj",  ndzscs);}
						}
						if (type) {
							CalculationPet.addNedanMsg(pet, ql, nddj, ndzscs, goodname);

							if (goodname.equals("暗渡陈仓")) {
								int zhsdj = BattleMixDeal.petLvlint(pet.getGrade());//召唤兽等级
								int zhszscs = pet.getTurnRount();//召唤兽转生次数
								long zhsqm = pet.getFriendliness();//召唤兽亲密值
								double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*CalculationPet.xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;

								FightingSkill skill=new FightingSkill();
								skill.setCamp(-1);
								skill.setSkillbeidong(1);
								skill.setSkillname(goodname);
								skill.setSkilltype(goodname);
								skill.setSkillhurt(Math.round(ndjl*10000)/100D);		// 忽视躲闪、忽视反击
								skill.setSkillid(0);
								data.addSkill(skill);
							}

						}else if (data!=null) {
							FightingSkill skill=CalculationPet.accessNedanMsg(good,nddj,ndzscs,data.getLvl(),data.getZs(),pet.getFriendliness(),pets[1]);
							data.addSkill(skill);
						}
					}
				}
			}
			//炼妖属性
			if (pet.getLyk()!=null&&!pet.getLyk().equals("")) {
				String[] v=pet.getLyk().split("\\|");
				for (int i = 0; i < v.length; i++) {
					String[] v1=v[i].split("=");
					GetqualityUntil.AddR(ql, v1[0],Double.parseDouble(v1[1]));
				}
			}
			if (pet.getFriendliness()>100000) {
				GetqualityUntil.AddR(ql, "连击率",5+CustomFunction.XS(pet.getFriendliness(), 0.7));
				GetqualityUntil.AddR(ql, "连击次数",(int)(3+CustomFunction.XS(pet.getFriendliness(), 0.2)));
				GetqualityUntil.AddR(ql, "命中率",2+CustomFunction.XS(pet.getFriendliness(), 0.4));
			}
			FightingSkill skill=CalculationPet.JX(skill0,skill1,skill2);
			if (skill!=null) {
				data.addSkill(skill);
				buffer.append("&jx_6_-1");
			}
//			int sid=Integer.parseInt(pet.getSummoningid());
//			if (sid>=100&&sid<500) {buffer.append("&W3");}
//			else if (sid>=500&&sid<1000) {buffer.append("&W3");}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		data.setModel(buffer.toString());
		data.setHuoyue(zBone);
		data.setShanghai(zSpir);
		data.setKangluobao(zPower);
		data.setYuanzhu(zSpeed);
		data.setHp_z(pets[0]);
		data.setMp_z(pets[1]);
		data.setAp(pets[2]);
		data.setSp(pets[3]);
		data.setQihe(pets[4]);
		data.setHp(pet.getBasishp());
		data.setMp(pet.getBasismp());
		if (data.getHp()==0||data.getHp()>data.getHp_z()) {
			data.setHp(data.getHp_z());
		}
		if (data.getMp()==0||data.getMp()>data.getMp_z()) {
			data.setMp(data.getMp_z());
		}
		ql.addKUp(up);
		ql.addKKUp(up+(pet.getTurnRount()>=4?10:0));
		//ql.addkr(30);

		// 灵犀额外控制抗性
		int kx = LingXiUtil.getNumberByStr(pet.getLingxi(), "11005", 1);
		if (kx > 0) {
			GetqualityUntil.AddR(ql,"抗混乱",kx);
			GetqualityUntil.AddR(ql,"抗封印",kx);
			GetqualityUntil.AddR(ql,"抗昏睡",kx);
			GetqualityUntil.AddR(ql,"抗遗忘",kx);
		}

		//抗性五行调整
		if (ql.getRolewxj()!=0||ql.getRolewxm()!=0||ql.getRolewxt()!=0||ql.getRolewxs()!=0||ql.getRolewxh()!=0) {
			GetqualityUntil.AddR(ql, "金",Double.parseDouble(pet.getGold())/2);
			GetqualityUntil.AddR(ql, "木",Double.parseDouble(pet.getWood())/2);
			GetqualityUntil.AddR(ql, "土",Double.parseDouble(pet.getSoil())/2);
			GetqualityUntil.AddR(ql, "水",Double.parseDouble(pet.getWater())/2);
			GetqualityUntil.AddR(ql, "火",Double.parseDouble(pet.getFire())/2);
		}else {
			GetqualityUntil.AddR(ql, "金",Double.parseDouble(pet.getGold()));
			GetqualityUntil.AddR(ql, "木",Double.parseDouble(pet.getWood()));
			GetqualityUntil.AddR(ql, "土",Double.parseDouble(pet.getSoil()));
			GetqualityUntil.AddR(ql, "水",Double.parseDouble(pet.getWater()));
			GetqualityUntil.AddR(ql, "火",Double.parseDouble(pet.getFire()));
		}
		ql.setRolewxj((int)ql.getRolewxj());
		ql.setRolewxm((int)ql.getRolewxm());
		ql.setRolewxt((int)ql.getRolewxt());
		ql.setRolewxs((int)ql.getRolewxs());
		ql.setRolewxh((int)ql.getRolewxh());
		if (UPBase(data)) {
			cl(data);
		}
	}
	/**4大基础属性 数值加成*/
	public static void getSI(int[] pets,String vs) {
		if (vs == null || vs.equals(""))
			return;
		String[] v = vs.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v1 = v[i].split("=");
			if (v1[0].equals("HP")){
				pets[0]+=Double.parseDouble(v1[1]);
			}else if (v1[0].equals("MP")) {
				pets[1]+=Double.parseDouble(v1[1]);
			}else if (v1[0].equals("AP")) {
				pets[2]+=Double.parseDouble(v1[1]);
			}else if (v1[0].equals("SP")) {
				pets[3]+=Double.parseDouble(v1[1]);
			}
		}
	}

	/**5维上限判断*/
	public static boolean UPBase(ManData data){
		if (data.getYuanzhu()<0) {return true;}
		double size=data.getQihe()+data.getHuoyue()+data.getShanghai()+data.getKangluobao()+data.getYuanzhu();
		return size>4000;
	}
	/***/
	public static void cl(ManData data) {
		data.setHp(1);
		data.setHp_z(1);
		data.setMp(1);
		data.setMp_z(1);
		data.setAp(1);
		data.setSp(0);
		data.setQuality(new Ql());
		if (data.getType() == 0) {
			data.getPets().clear();
			data.getLings().clear();
			data.setChild(null);
		}
		WriteOut.addtxt("抗性字段溢出:" + data.getManname() + ":" + data.getId()+ ":" + data.getType(), 9999);
	}
}
