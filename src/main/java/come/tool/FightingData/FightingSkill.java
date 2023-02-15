package come.tool.FightingData;

import org.come.model.Skill;
import org.come.model.Talent;
import org.come.tool.CustomFunction;

import java.math.BigDecimal;

public class FightingSkill implements Cloneable{
	// 技能id
	private int skillid;
	// 技能名
	private String skillname;
	// 技能等级
	private int skilllvl;
	// 技能伤害
	private double skillhurt;
	// 附加状态百分比
	private double skillgain;
	// 技能命中率
	private double skillhitrate;
	// 技能命中个数
	private int skillsum;
	// 技能持续回合数
	private int skillcontinued;
	// 技能类型
	private String skilltype;
	// 技能耗蓝
	private int skillblue;
	// 技能阵营 技能选择的人的不标准
	private int camp;
	// 该技能是否被动使用 0允许使用 1被动使用
	private int skillbeidong = 0;


	public FightingSkill() {
		// TODO Auto-generated constructor stub
	}
	/**1001-1024天资技能加载*/
	public FightingSkill(Talent talent,int lvl){
		skillbeidong=1;
		skillid=talent.getId();
		skillname=talent.getTalentName();
		skilllvl=lvl;
		skillhitrate=talent.getTouch()*lvl;
		String[] v=talent.getValue().split("\\|");
		String[] v1=v[0].split("=")[1].split("\\+");
		skillhurt=Double.parseDouble(v1[0])+Double.parseDouble(v1[1])*lvl;
		v1=v[1].split("=")[1].split("\\+");
		skillgain=Double.parseDouble(v1[0])+Double.parseDouble(v1[1])*lvl;
		int type=(skillid-1001)/2;
		switch (type) {
			case  0:
				skilltype="加敏序";
				break;
			case  1:
				skilltype="忽视风";
				skillcontinued=1;
				break;
			case  2:
				skilltype="忽视水";
				skillcontinued=1;
				break;
			case  3:
				skilltype="忽视火";
				skillcontinued=1;
				break;
			case  4:
				skilltype="忽视雷";
				skillcontinued=1;
				break;
			case  5:
				skilltype="强震慑";
				skillcontinued=1;
				break;
			case  6:
				skilltype="抗水";
				break;
			case  7:
				skilltype="抗火";
				break;
			case  8:
				skilltype="抗雷";
				break;
			case  9:
				skilltype="抗风";
				break;
			case 10:
				skilltype="强普攻";
				skillcontinued=1;
				break;
			case 11:
				skilltype="忽视混乱";
				skillcontinued=1;
				break;
			case 12:
				skilltype="忽视封印";
				skillcontinued=1;
				break;
			case 13:
				skilltype="忽视中毒";
				skillcontinued=1;
				break;
			case 14:
				skilltype="忽视昏睡";
				skillcontinued=1;
				break;
			case 15:
				skilltype="解混乱";
				skillcontinued=3;
				break;
			case 16:
				skilltype="解封印";
				skillcontinued=3;
				break;
			case 17:
				skilltype="解中毒";
				break;
			case 18:
				skilltype="抗震慑";
				break;
			case 19:
				skilltype="免疫物理";
				skillcontinued=1;
				break;
			case 20:
				skilltype="忽视鬼火";
				skillcontinued=1;
				break;
			case 21:
				skilltype="强三尸虫";
				skillcontinued=1;
				break;
			case 22:
				skilltype="忽视遗忘";
				skillcontinued=1;
				break;
			case 23:
				skilltype="抗鬼火";
				break;
			case 24:
				skilltype="霹雳连击";
				skillcontinued=1;
				break;
			case 25:
				skilltype="甘霖回血";
				skillcontinued=1;
				break;
		}
	}
	/**内丹技能加载*/
	public FightingSkill(String goodname,double skillgain,double skillhurt){
		this.camp=-1;
		this.skillname=goodname;
		this.skillbeidong=1;
		this.skillgain=skillgain;
		this.skillhurt=(int)skillhurt;
		this.skillsum=1;
		if (skillname.equals("乘风破浪")) {
			this.skillid=1044;
			this.skilltype="风";
		}else if (skillname.equals("霹雳流星")) {
			this.skillid=1049;
			this.skilltype="雷";
		}else if (skillname.equals("大海无量")) {
			this.skillid=1054;
			this.skilltype="水";
		}else if (skillname.equals("祝融取火")) {
			skillid=1059;
			this.skilltype="火";
		}else if (skillname.equals("分光化影")||skillname.equals("天魔解体")||skillname.equals("小楼夜哭")||skillname.equals("青面獠牙")) {
			this.skillbeidong=0;
			this.skilltype="魔界内丹";
		}
	}


	public int getsum2(int sum) {// 1 2 3 5 7
		if (sum <= 3)
			return sum;
		else
			return (sum << 1) - 3;
	}

	public int getsum3(int sum) {// 1 3 5 7 9
		return (sum << 1) - 1;
	}

	public int getsum4(int sum) {// 1 3 5 7 10
		if (sum >= 5)
			return 10;
		else
			return (sum << 1) - 1;
	}

	/**获取系数 人物等级 转生次数 熟练度  亲密 法宝等级 法宝品质*/
	/**灵宝                         合计数       1擅长                                         */
	/**获取系数                                                                                       */
	public FightingSkill(Skill skill,int lvl,int born,double sld,long qm,int pz) {
		double sv=skill.getGrow();
		double value=skill.getValue();
		this.camp=skill.getCamp();
		this.skillid=skill.getSkillid();
		this.skilllvl=skill.getSkilllevel();
		this.skillblue=(int)(skill.getDielectric());
		this.skillname=skill.getSkillname();
		this.skilltype=this.skillname;
		if (skillid>1000&&skillid<=1100) {//师门技能
			ShumenSkill(lvl, value, sv, sld);
		}else if (skillid>=1500&&skillid<2000) {//召唤兽技能
			PetSkill(value,sv,lvl,born, qm);
		}else if (skillid>=1200&&skillid<1300) {//召唤兽天生技能
			PetTalentSkill(skill,value,sv,qm);
		}else if (skillid>=1300&&skillid<1400) {//召唤兽装备技能
			this.skilltype=this.skillid+"";
			PetEquipSkill(value,sld,lvl);
		}else if (skillid>3000&&skillid<3100) {//灵宝技能
			LingSkill(value, sv, born, lvl, sld);
		}else if (skillid>5000&&skillid<5100) {//法宝技能
			FaSkill(value, sv, pz, lvl);
		}else if ((skillid>7000&&skillid<8000)||skillid==6038||skillid==6029) {//套装主动技能
			this.skilltype=this.skillid+"";
			SuitInitiative(value, sv,pz);
		}else if (skillid>6000&&skillid<7000) {//6038是主动技能6029
			//套装被动技能 6001-6017 6030-6032 6035-6036 6039加属性
			this.skillbeidong=1;
			this.skilltype=this.skillid+"";
			SuitPassive(value, sv,pz);

		}else if (skillid==8043) {//落井下石（主动）减少对方单人300点怨气
			this.skilltype=this.skillid+"";
			this.skillsum=1;
		}else if (skillid==8055) {//冰刃数
			this.skilltype=this.skillid+"";
			this.skillsum=2;
			this.skillbeidong =0;
		}else if (skillid==8066){//强化浩然正气
			this.camp=-1;
			this.skillname="浩然正气";
			this.skillhurt=25;
			this.skillgain=40;
			this.skillbeidong=1;
		} else if (skillid==8037||skillid==8038||skillid==8041||skillid==8042||skillid==8045||skillid>=8060&&skillid<=8065||(skillid>=8053&&skillid<=8059)) {//特技
			this.skillbeidong=1;
			this.skilltype = this.skillid + "";
			if (this.skillid==8042) {
				this.skillgain=35;
				this.skillsum=2;
			}else if (skillid==8045) {
				this.camp=-1;
				this.skillname="浩然正气";
				this.skillhurt=20;
				this.skillgain=35;
				this.skillbeidong=1;
			}
		}else if (skillid>=9000&&skillid<=9999) {//天演策
			this.skilltype=this.skillid+"";
			this.skillbeidong=1;
			TYC(value, sv, (int)sld);
		}else if (skillid>=22000&&skillid<=23000) {//法门

			//	this.skilltype=this.skillid+"";//类型等于技能ID
			//单数被动，双数主动
			if(skillid == 22001 ||skillid == 22003||skillid == 22005||skillid == 22007||skillid == 22009
					||skillid == 22011 ||skillid == 22013||skillid == 22015||skillid == 22017||skillid == 22019
					||skillid == 22021 ||skillid == 22023||skillid == 22025||skillid == 22027||skillid == 22029
					||skillid == 22031 ||skillid == 22033||skillid == 22035
			) {
				this.skillbeidong=1;//都是被动
			}

		}else if(skillid>=23001&&skillid<=23009) {
			if(skillid==23001) {
				this.skillbeidong=1;
			}else if (skillid==23002) {
				this.skillbeidong=1;
			}else if (skillid==23003) {
				this.skillbeidong=1;
			}else if (skillid==23004) {
				this.skilltype="23004";
				this.skillcontinued=2;//持续回合
				this.skillsum=1;
			}else if (skillid==23005) {
				this.skillbeidong=1;
			}else if (skillid==23006) {
				this.skillbeidong=1;
			}else if (skillid==23007) {
				this.skillbeidong=1;
			}else if (skillid==23008) {
				this.skillbeidong=1;
			}
			else if(skillid==23009) {
				this.skilltype="23009";
				this.skillgain=30;
				this.skillcontinued=3;
			}
		}
	}
	//TODO 法术伤害及命中计算
	//师门法术  单位等级  介质 成长 熟练度
	public void ShumenSkill(int lvl,double value,double sv,double sld){
		    skillsum=1;
		if (skillid>=1001&&skillid<=1005) {//混乱法术
			this.skilltype="混乱";
			this.skillcontinued=7;//持续回合
			//命中概率
			this.skillhitrate=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1006&&skillid<=1010) {//封印
			this.skilltype="封印";
			this.skillcontinued=7;//持续回合
			//命中概率
			this.skillhitrate=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1011&&skillid<=1015) {//昏睡
			this.skilltype="昏睡";
			this.skillcontinued=7;//持续回合
			//命中概率
			this.skillhitrate=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1016&&skillid<=1020) {//中毒
			this.skilltype="中毒";
			this.skillcontinued=3;//持续回合
			//命中概率
			this.skillhitrate=((double)((int)((value+sld*sv/1000)*1000))/15)*17;
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//总生命百分比伤害
			this.skillhurt=skilllvl>3?15:skilllvl>1?12.5:10;
			//毒伤上限
			this.skillgain=(lvl+sld/100)*8+(skilllvl==5?10000:skilllvl==4?12000:8000);
		}else if (skillid>=1021&&skillid<=1025) {//震慑
			this.skilltype="震慑";
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//当前生命百分比伤害
			this.skillhurt=(double)((int)((value+sld*sv/1000)*1000))/10;
		}else if (skillid>=1026&&skillid<=1030) {//力量增益
			this.skilltype="力量";
			this.skillcontinued=7;//持续回合
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//附加状态振幅百分比
			this.skillgain=(double)((int)((value+sld*sv/1000)*1000))/10;
		}else if ((skillid>=1031&&skillid<=1035)) {//抗性
			this.skilltype="抗性";
			this.skillcontinued=7;//持续回合
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//附加状态振幅百分比
			this.skillgain=(double)((int)((value+sld*sv/1000)*1000))/10;
		}else if (skillid>=1036&&skillid<=1040) { //加速
			this.skilltype="加速";
			//持续回合
			this.skillcontinued=7;
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//附加状态振幅百分比
			this.skillgain=(double)((int)((value+sld*sv/1000)*1000))/10;
		}else if (skillid>=1041&&skillid<=1065) {
			//风火水雷鬼火系
			if (skillid<1046) {
				this.skilltype="风";
			}else if (skillid<1051) {
				this.skilltype="雷";
			}else if (skillid<1056) {
				this.skilltype="水";
			}else if (skillid<1061) {
				this.skilltype="火";
			}else {
				this.skilltype="鬼火";
			}
			//命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//伤害
			this.skillhurt=(int)((value+sv*(1+5*sld/5000*(10-sld/5000)/2))*lvl);
		}else if (skillid>=1066&&skillid<=1070) {//三尸虫
			this.skilltype="三尸虫";
			//   命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//伤害
			this.skillhurt=(int)((value*lvl+sld*sv/1000)*1000)/10;
			//附加状态振幅百分比
			this.skillgain=value*100+(int)(sld/250);
		}else if (skillid>=1071&&skillid<=1075) {//遗忘性
			this.skilltype="遗忘";
			//持续回合
			this.skillcontinued=7;
			//	    命中概率
			this.skillhitrate=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			//   命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1076&&skillid<=1080) { //魅惑
			this.skilltype="smmh";
			this.skillcontinued=7;//持续回合
			//   命中个数
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
			//附加状态振幅百分比
			this.skillgain=(double)((int)((value+sld*sv/1000)*1000))/10;
		}else if (skillid>=1081&&skillid<=1085) {
			this.skilltype="霹雳";
			//命中概率
			this.skillhurt=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			this.skillgain=skilllvl==5?30:skilllvl==4?35:25;
			this.skillcontinued=skilllvl==5?3:skilllvl==4?5:2;
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1086&&skillid<=1090) {
			this.skilltype="沧波";
			//命中概率
			this.skillhurt=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			this.skillgain=skilllvl==5?30:skilllvl==4?35:25;
			this.skillhitrate=skilllvl==5?20:skilllvl==4?25:15;
			this.skillcontinued=3;
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1091&&skillid<=1095) {
			this.skilltype="甘霖";
			//命中概率
			this.skillhurt=(int)((value*lvl+sld*sv/1000)*1000)/10;//伤害
			this.skillcontinued=3;
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}else if (skillid>=1096&&skillid<=1100) {
			this.skilltype="扶摇";
			//命中概率
			this.skillhurt=value+sv*new BigDecimal(Math.pow(sld, 0.3)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			this.skillgain=skilllvl==5?30:skilllvl==4?35:25;
			this.skillhitrate=skilllvl==5?15:skilllvl==4?20:10;
			this.skillcontinued=3;
			this.skillsum=geshu(skilllvl,(int)sld,skilltype);
		}
		this.skillblue=(int)(skillblue*((sld/25000)+1));
	}

	//召唤兽天赋技能
	public void PetTalentSkill(Skill skill, double value,double sv,long qm){
		switch (skillid) {
			case 1201://顾影自怜
				skillbeidong=1;
				this.skillhurt=40;
				return;
			case 1206://天见犹怜
				skillbeidong=1;
				this.skillhurt=40;
				return;
			case 1210://如来神掌
				skillbeidong=1;
				return;
			case 1211://浪子回头
				skillbeidong=1;
				this.skillhurt=20;
				return;
			case 1212://秒转乾坤
				skillbeidong=1;
				this.skillhurt=50;
				return;
			case 1200://泽屁天下
				this.skilltype="庇护";
				this.skillcontinued=3;
				this.skillsum=10;
				return;
			case 1202://慧眼菩提
				this.skilltype="减魔鬼";
				//技能个数
				this.skillsum=3;
				//持续回合
				this.skillcontinued=2;
				this.skillhurt=1;
				return;
			case 1203://醍醐灌顶
				this.skilltype="减魔鬼";
				//技能个数
				this.skillsum=1;
				//持续回合
				this.skillcontinued=3;
				this.skillhurt=2;
				return;
			case 1204://红衰翠减
				this.skilltype="减人仙";
				//技能个数
				this.skillsum=3;
				//持续回合
				this.skillcontinued=2;
				this.skillhurt=1;
				return;
			case 1205://此恨绵绵
				this.skilltype="减人仙";
				//技能个数
				this.skillsum=1;
				//持续回合
				this.skillcontinued=3;
				this.skillhurt=2;
				return;
			case 1214://恭行天罚
			case 1215://移花接木
				//技能个数
				this.skillsum=1;
				return;
			case 1216://暗影离魂
				//技能个数
				this.skillsum=2;
				this.skillhurt=40+CustomFunction.XS(qm, 1);
				this.skillgain=20+CustomFunction.XS(qm, 0.7);
				this.skillbeidong=1;
				return;
			case 1217://亢龙有悔
				//技能个数
				this.skillsum=9;
				this.skillhurt=30+CustomFunction.XS(qm, 1);
				this.skillgain=20+CustomFunction.XS(qm, 0.7);
				this.skillbeidong=1;
				return;
			case 1218://水中探月
				//技能个数
				this.skillhurt=20+CustomFunction.XS(qm, 0.7);
				this.skillgain=20+CustomFunction.XS(qm, 0.7);
				this.skillbeidong=1;
				return;
			case 1219://雾里看花
				//技能个数
				this.skillhurt=30;
				this.skillbeidong=1;
				return;
			case 1220://杀神成仁
				//技能个数
				this.skillhurt=25;
				this.skillbeidong=1;
				return;
			case 1221://神迟魂钝
				//技能个数
				this.skillgain=15;
				this.skilltype="减速";
				//技能个数
				this.skillsum=2;
				//持续回合
				this.skillcontinued=2;
				return;
			case 1222://震慑抵抗
			case 1223://千松扫尾
				//技能个数
				this.skillhurt=15;
				this.skilltype="伤害加深";
				//持续回合
				this.skillcontinued=2;
				this.skillbeidong=1;
				return;
			case 1224://梵音初晓
				//技能个数
				this.skillhurt=40;
				this.skilltype="解除控制";
				this.skillsum=1;
				return;
			case 1225://鼓音三叠
				//技能个数
				this.skillhurt=33;
				this.skillgain=66;
				this.skillbeidong=1;
				return;
			case 1227://义薄云天
				//技能个数
				this.skillhurt=30;//加成
				this.skillbeidong=1;
				return;
			case 1228://昆山玉碎
				this.skilltype="bbksys";
				//技能个数     1 1.2 1.45 2   1.2成长  10的基础
				this.skillhitrate=value+sv*CustomFunction.XS(qm, 0.66);//命中率
				this.skillhurt=this.skillhitrate*1.3;//伤害加成 法力*加成/100
				this.skillbeidong=1;
				return;
			case 1229://云起潮生
				this.skilltype="1229";
				this.skillhitrate=value+sv*CustomFunction.XS(qm,0.8);//命中率
				this.skillbeidong=1;
				return;
			case 1230://势不可挡
				this.skilltype="1230";
				this.skillhurt=30+CustomFunction.XS(qm,0.8);
				this.skillgain=10+CustomFunction.XS(qm,0.4);
				return;
			case 1231://天降脱兔
				this.skilltype="1231";
				this.skillhurt=(int)CustomFunction.XS(qm,0.1);
				this.skillgain=35+CustomFunction.XS(qm,0.8);
				this.skillbeidong=1;
				return;
			case 1232://灵魂封魔
				this.skilltype="1232";
				this.skillsum=1+(int)CustomFunction.XS(qm,0.07);
				this.skillgain=20+CustomFunction.XS(qm,1.3);
				this.skillcontinued=1+(int)CustomFunction.XS(qm,0.05);
				this.skillbeidong=1;
				return;
			case 1233://重振旗鼓
				this.skilltype="1233";
				this.skillgain=value+CustomFunction.XS(qm,sv);
				this.skillbeidong=1;
				return;
			case 1234://防不慎防
				this.skilltype="1234";
				this.skillgain=value+CustomFunction.XS(qm,sv);
				this.skillcontinued=3;
				return;
			case 1235://坚若磐石
			case 1236://扭转乾坤
				this.skilltype=skillid+"";
				this.skillbeidong=1;
				return;
			case 1237:	//天降流火触发概率
				this.skilltype="1237";
				this.skillhurt=40+CustomFunction.XS(qm, 1);
				this.skillgain=100+CustomFunction.XS(qm, 0.7);
				this.skillbeidong=1;
				return;

			case 1238:	//归去来兮
				this.skilltype="1238";
				this.skillgain=value+CustomFunction.XS(qm,sv);
				this.skillhurt=qm;//伤害加成 法力*加成/100;
				this.skillbeidong=1;
				this.skillcontinued=50;
				return;
			case 1240://一御当千
				this.skilltype=skillid+"";
				this.skillgain=skill.getValue()+CustomFunction.XS(qm,skill.getGrow());
				this.skillhurt=skill.getValue1()+CustomFunction.XS(qm,skill.getGrow1());
				this.skillbeidong=1;
				this.skillcontinued=2;
				return;
			case 1241://百草竞发
				this.skilltype = String.valueOf(this.skillid);
				this.skillgain = skill.getValue() + CustomFunction.XS(qm, skill.getGrow());
				this.skillhurt = skill.getValue1() + CustomFunction.XS(qm, skill.getGrow1());
				this.skillbeidong = 1;
				this.skillcontinued = 2;
				return;
			case 1244:// 剑荡八荒
				//技能个数
				this.skillhurt=20+CustomFunction.XS(qm, 0.7);
				this.skillgain=20+CustomFunction.XS(qm, 0.7);
				this.skillbeidong=1;
				return;
			case 1246: //法天象地
				this.skillhurt=qm/400;
				this.skillbeidong=1;
				return;
			case 1247://不动明王
			case 1248://大威天龙
			case 1249://妙手空空
			case 1253://大日如来神掌
				this.skillbeidong=1;
				return;
			case 1250://偷梁换柱
			case 1251://遮天蔽日
			case 1252://大闹天宫
				this.skillbeidong=0;
				return;
			case 1254://万剑归宗
				this.skillhurt=qm/100;
				this.skillsum=5;
				this.skillbeidong=0;
				return;
		}
	}
	//召唤兽技能
	public void PetSkill(double value,double sv,int lvl,int born,long qm){
		if (skillid==1833||skillid==1832) {
			if (skillid==1832) {
				this.skillhurt=15;
				this.skillhurt+=CustomFunction.XS(qm, 0.3);
			}else {
				this.skillhurt=30;
				this.skillhurt+=CustomFunction.XS(qm, 0.6);
			}
			this.skilltype="分裂";
			skillbeidong=1;
			return;
		}else if (skillid==1831) {
			this.skilltype="追击";
			this.skillhurt=30+CustomFunction.XS(qm, 0.3);
			skillbeidong=1;
			return;
		}else if (skillid==1509) {
			this.skilltype="复活";
			skillbeidong=1;
			return;
		}else if (skillid==1829||skillid==1878) {
			this.skilltype="作鸟兽散";
			this.skillsum=skillid==1829?10:3;
			this.skillgain=value;
			skillbeidong=1;
			return;
		}else if (skillid==1828) {
			this.skilltype="化无";
			skillbeidong=1;
			return;
		}else if (skillid==1882) {
			this.skilltype = "高级帐饮东都";
			skillbeidong = 1;
			return;
		}else if (skillid==1883) {
			this.skilltype = "高级源泉万斛";
			skillbeidong = 1;
			return;
		}else if (skillid==1884) {
			this.skilltype = "高级神功鬼力";
			skillbeidong = 1;
			return;
		}else if (skillid==1885) {
			this.skilltype = "高级倍道兼行";
			skillbeidong = 1;
			return;
		} else if (skillid==1809||(skillid>=1825&&skillid<=1827)) {
			this.skilltype="隐身";
			skillbeidong=1;
			this.skillhitrate=40+CustomFunction.XS(qm, 0.7);
			return;
		}else if (skillid==1607||skillid==1877) {
			skillsum=skillid==1607?2:1;
			this.skilltype="隐身技";
			return;
		}else if (skillid==1808||skillid==1824) {
			skillbeidong=1;
			this.skilltype="遗产";
			this.skillhitrate=40+CustomFunction.XS(qm, 0.7);
			return;
		}else if (skillid==1804) {
			skillbeidong=1;
			this.skilltype="忠诚";
			return;
		}else if (skillid==1823||skillid==1805||skillid==1821) {
			skillbeidong=1;
			this.skilltype="自医";
			this.skillhitrate=40+CustomFunction.XS(qm, 0.7);
			return;
		}else if (skillid==1822||skillid==1820||skillid==1807) {
			skillbeidong=1;
			this.skilltype="击其不意";
			this.skillhitrate=40+CustomFunction.XS(qm, 0.7);
			return;
		}else if (skillid==1606||skillid==1611) {
			this.skillsum=skillid==1611?3:10;
			this.skillgain=skillid==1611?0.5:0.6;
			this.skilltype="回血技";
			return;
		}else if (skillid==1612||skillid==1608) {
			this.skillhurt=111;
			this.skillsum=skillid==1608?10:3;
			this.skilltype="解除控制";
			return;
		}else if ((skillid>=1600&&skillid<=1605)||skillid==1876) {
			this.skillsum=10;
			this.skilltype="五行";
			return;
		}else if (skillid==1806||(skillid>=1820&&skillid<=1827)) {
			skillbeidong=1;
			this.skilltype="闪现";
			this.skillhitrate=40+CustomFunction.XS(qm, 0.7);
			return;
		}else if (skillid==1609) {
			this.skilltype="兵临城下";
			return;
		}else if (skillid==1610) {
			this.skilltype="奋蹄扬威";
			return;
		}else if (skillid==1830||skillid==1872) {
			this.skilltype="bbjs";
			this.skillsum=skillid==1830?5:3;
			skillbeidong=1;
			return;
		}else if (skillid==1842||skillid==1871) {
			this.skilltype="bbdt";
			this.skillsum=skillid==1842?5:3;
			this.skillbeidong=1;
			return;
		}else if (skillid==1839) {
			this.skilltype="bbss";
			//持续回合
			this.skillsum=1;//命中个数
			//命中概率
			this.skillhitrate=28+CustomFunction.XS(qm, 0.6);
			this.skillcontinued=2;
			return;
		}else if (skillid==1838) {
			this.skilltype="bbjr";
			this.skillbeidong=1;
			return;
		}else if (skillid==1835||skillid==1875) {//报复  回源
			this.skillbeidong=1;
			this.skillhitrate=10;//命中概率
			if (skillid==1835) {
				this.skilltype="bbbf";
				this.skillhurt=250;
			}else {
				this.skilltype=skillid+"";
				this.skillhurt=50;
			}
			return;
		}else if (skillid==1834) {
			this.skilltype="bbxr";
			this.skillhurt=250;
			this.skillbeidong=1;
			return;
		}else if (skillid==1836) {
			this.skilltype="bbyh";
			this.skillhurt=250;
			this.skillbeidong=1;
			return;
		}else if (skillid==1837) {
			this.skilltype="bbtm";
			this.skillhurt=250;
			this.skillbeidong=1;
			return;
		}else if (skillid==1840) {
			this.skilltype="bbmcqh";
			this.skillhurt=250;
			this.skillbeidong=1;
			return;
		}else if (skillid==1841) {
			this.skilltype="bbsgqx";
			this.skillhurt=250;
			this.skillbeidong=1;
			return;
		}else if (skillid==1843||skillid==1844||skillid==1847) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
			return;
		}else if (skillid==1848||skillid==1858) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
			this.skillhitrate=value+CustomFunction.XS(qm, 0.16);
			return;
		}else if (skillid>=1849&&skillid<=1854) {
			this.skilltype=(skillid%2!=0?skillid:skillid-1)+"";
			this.skillbeidong=1;
			this.skillhitrate=value+CustomFunction.XS(qm, skillid%2!=0?0.16:0.2);
		}else if (skillid>=1861&&skillid<=1864) {
			this.skilltype=(skillid%2!=0?skillid:skillid-1)+"";
			this.skillbeidong=1;
			this.skillhitrate=value;
		}else if (skillid==1866) {
			this.skilltype=skillid+"";
			this.skillbeidong=1; //人法基础    仙法鬼法4倍   三尸虫 100倍
			this.skillhurt=value+CustomFunction.XS(qm,sv);
			this.skillcontinued=3;
		}else if (skillid==1867) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
		}else if (skillid==1868) {
			this.skilltype=skillid+"";
			this.skillsum=1;
		}else if (skillid==1869) {
			this.skilltype=skillid+"";
			this.skillsum=1;
			this.skillhitrate=value+CustomFunction.XS(qm,sv);
			this.skillcontinued=2;
		}else if (skillid==1870) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
			this.skillsum=2;
			this.skillhurt=value+CustomFunction.XS(qm,sv);
		}else if (skillid==1873||skillid==1874) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
			this.skillhurt=value;
		}else if (skillid==1879||skillid==1880) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
			this.skillhurt=value+CustomFunction.XS(qm,sv);
		}else if (skillid==1881) {
			this.skilltype=skillid+"";
			this.skillbeidong=1;
		}
	}

	/**召唤兽装备技能 基础值  星级  等级*/
	public void PetEquipSkill(double value,double sld,int lvl){
		this.skillgain=(int)(value*sld/5*Math.sqrt(lvl));
		if (!(this.skillid==1313||(this.skillid>=1332&&this.skillid<=1334))) {this.skillbeidong=1;}
		if (this.skillid==1306||this.skillid==1307) {
			this.skillhurt=this.skillgain*0.8;
		}else if (this.skillid==1309) {
			this.skillhurt=this.skillgain*0.7;
		}else if (this.skillid==1309) {
			this.skillhurt=this.skillgain*0.7;
		}else if (this.skillid==1309) {
			this.skillhurt=this.skillgain*0.7;
		}else if (this.skillid==1333) {
			this.skillhurt=100+this.skillgain;
			this.skillgain=(int)(1+this.skillgain*0.07);
		}else if(this.skillid==Integer.parseInt(TypeUtil.BB_TJLH)){
			this.skillbeidong = 1;
		}else if (this.skillid==1335) {
			//莲火流星
			this.skillhurt=this.skillgain*0.7;
		}else if (this.skillid==1336) {
			//百战重生
			this.skillhurt = this.skillgain * 0.7;
		}
	}
	//灵宝技能                    born合计数 sld大于0就是擅长
	public void LingSkill(double value,double sv,int born,int lvl,double sld){
		skilllvl=born;
		if (skillid==3034||skillid==3035) {
			skillblue = 1;
			return;
		}
		if (sld>0) {
			skillblue=1;
		}else {
			skillblue=0;
		}
		int sl=lvl*100;
		skillhurt=(value+sv*(1+5*sl/5000*(10-sl/5000)/2))*lvl;
		switch (skillid) {
			case 3001:
			case 3002:
			case 3003:
				skillsum=getsum2(skilllvl)+(skillblue==0?0:1);
				break;
			case 3004:
				skillsum=getsum2(skilllvl);
				break;
			case 3005:
			case 3006:
				skillsum=getsum2(skilllvl);
				skillhurt=skillhurt*(skillblue==0?1.0:1.3);
				break;
			case 3023:
				skilltype="骨盾";
				skillgain=skillhurt*(skillblue==0?1.0:1.3);
				skillcontinued=3;
				skillsum=getsum2(skilllvl);
				skillhurt=0;
				break;
			case 3007:
			case 3008:
				skillsum=getsum2(skilllvl);
				skilltype="烧法";
				skillgain=value;
				skillhurt=skillhurt*(skillblue==0?1.0:1.3);
				break;
			case 3009:
				skillsum=getsum2(skilllvl);
				skilltype="化羽";
				skillcontinued=(skillblue==0?1:2);
				break;
			case 3010:
				skillgain=skillhurt;
				skillsum=skilllvl;
				skillcontinued=(skillblue==0?2:3);
				break;
			case 3021:
				skillgain=value+(skillblue==0?0:-10);
				skillsum=skilllvl;
				break;
			case 3022:
				skillgain=skillhurt/2*(skillblue==0?1.0:1.5);
				skillsum=skilllvl;
				skillcontinued=2;
				break;
			case 3024:
				skillgain=skilllvl+(skillblue==0?0:1);
				skillcontinued=3;
				break;
			case 3033:
				skillhurt=65;
				skillhurt=skillhurt+(skillblue==0?0:10);
				skillsum=skilllvl;
				break;
			case 3011:
			case 3012:
				skillhurt=skillhurt*(1+skilllvl/2.0);
				break;
			case 3031:
			case 3032:
				skillgain=(skillblue==0?0:0.3);
				break;
			case 3013:
			case 3014:
			case 3015:
				skillsum=getsum3(skilllvl)+(skillblue==0?0:1);
				break;
			case 3016:
				skilltype="风水";
				skillsum=getsum4(skilllvl);
				skillgain=75+(skillblue==0?0:10);
				if (skilllvl>4)skillcontinued=3;
				else skillcontinued=2;
				break;
			case 3017:
				skilltype="雷火";
				skillsum=getsum4(skilllvl);
				skillgain=75+(skillblue==0?0:10);
				if (skilllvl>4)skillcontinued=3;
				else skillcontinued=2;
				break;
			case 3018:
				skilltype="鬼力";
				skillsum=getsum4(skilllvl);
				skillgain=75+(skillblue==0?0:10);
				if (skilllvl>4)skillcontinued=3;
				else skillcontinued=2;
				break;
			case 3019:
				skillsum=getsum4(skilllvl);
				skillgain=30+(skillblue==0?0:10);
				if (skilllvl>4)skillcontinued=3;
				else skillcontinued=2;
				break;
			case 3020:
				skillgain=33+(skillblue==0?0:5);
				skillsum=getsum4(skilllvl);
				break;
			case 3025:
				skilltype="禁言";
				skillcontinued=3+(skillblue==0?0:3);
				skillsum=10;
				break;
			case 3028:
				skillsum=skilllvl-3;
				skillcontinued=skilllvl-3;
				break;
			case 3029:
				skillsum=skilllvl-3;
				if (skilllvl>1)skillcontinued=2;
				else skillcontinued=1;
				skillgain=50+(skillblue==0?0:25);
				break;
			case 3030:
				skillsum=skilllvl-3;
				break;
			case 3026:
				skillsum=2;
				skillcontinued=2+(skillblue==0?0:1);
				break;
			case 3027:
				skillsum=getsum2(skilllvl)+(skillblue==0?0:1);
				skillcontinued=skilllvl;
				break;
		}
	}
	//法宝技能
	public void FaSkill(double value,double sv,int pz,int blvl){
		this.skillhurt=value+pz*sv;
		this.skillblue+=blvl;
		this.skillcontinued=FBUtil.getFBcx(skillid, blvl);
		initFB(skillid, blvl);
		if (skillid==5014) {
			this.skillgain=skillhurt*12500;
		}else if (skillid==5015) {
			this.skillgain=skillhurt*2/3;
		}
		this.skillsum=FBUtil.getFBsum(skillid, blvl);

	}
	/**套装被动技能*/
	public void SuitPassive(double value,double sv,double pz){
		pz=pz*(1+(pz-1)*0.02);
		switch (skillid) {
			case 6018://减抗
				this.skillhurt=value+sv*pz;//控制性法术
				this.skillgain=skillhurt*2+1;//伤害性法术
				this.skillcontinued=1;
				break;
			case 6019:
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				this.skillcontinued=1;
				break;
			case 6020:break;//不用
			case 6021:break;//不用
			case 6022:
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				break;
			case 6023:break;//不用
			case 6024:
				this.skillhurt=value+sv*pz;
				break;
			case 6025:
				this.skillhurt=value+sv*pz;
				break;
			case 6026:
				this.skillhurt=value+sv*pz;
				break;
			case 6027:
				this.skillcontinued=2;
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				break;
			case 6028:
				this.skillcontinued=2;
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				break;
			case 6033:
				this.skillhurt=value+sv*pz;
				break;
			case 6034:
				this.skillhurt=value+sv*pz;
				break;
			case 6037:
				this.skillhurt=value+sv*pz;
				break;
			case 6040:
				this.skillhurt=value+sv*pz;
				this.skillcontinued=2;
				break;
			case 6041:
			case 6042:
				this.skillhurt=value+sv*pz;
				break;
		}
	}
	/**套装主动技能*/
	public void SuitInitiative(double value,double sv,int pz){
		switch (skillid) {
			case 6029:
				this.skillcontinued=1;//持续回合
				this.skillgain=value+sv*pz;
				this.skillsum=1;
				break;
			case 6038:
				this.skillcontinued=7;//持续回合
				this.skillgain=value+sv*pz;
				this.skillsum=1;
				break;
			case 7001:break;
			case 7002:
				this.skillcontinued=2;//持续回合
				this.skillgain=value+sv*pz;
				this.skillsum=1;
				break;
			case 7003:
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				break;
			case 7004:
				this.skillhurt=value;
				this.skillhitrate=sv*pz;
				break;
			case 7005:break;
			case 7006:break;
			case 7007:
				this.skillhurt=value+sv*pz;
				break;
			case 7008:
				this.skillcontinued=1;//持续回合
				this.skillhurt=value+sv*pz;
				this.skillsum=1;
				break;
			case 7009:
				this.skillcontinued=1;//持续回合
				this.skillhurt=value+sv*pz;
				this.skillsum=1;
				break;
			case 7010:break;
			case 7011:
				this.skillcontinued=2;//持续回合
				break;
			case 7012:
				this.skillhurt=value-sv*pz;
				this.skillgain=value+sv*pz;
				break;
			case 7013:break;
			case 7014:break;
			case 7015:break;
			case 7016:break;
			case 7017:break;
			case 7018:break;
			case 7019:
				this.skilltype="隐身技";
				this.skillcontinued=1;//持续回合
				this.skillsum=10;
				break;
			case 7020:
				this.skillhurt=value+sv*pz;
				this.skillsum=1;
				break;
			case 7021:break;
			case 7022:break;
			case 7023:break;
			case 7024:break;
			case 7025:
				this.skillcontinued=2;//持续回合
				this.skillhurt=value+sv*pz;
				this.skillsum=1;
				break;
			case 7026:
				this.skillcontinued=3;//持续回合
				this.skillgain=value+sv*pz;
				this.skillsum=1;
				break;
			case 7027:break;
			case 7028:
				this.skillcontinued=1;//持续回合
				this.skillsum=1;
				break;
		}
	}
	public int getSkillid() {
		return skillid;
	}
	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}
	public double getSkillhurt() {
		return skillhurt;
	}
	public void setSkillhurt(double skillhurt) {
		this.skillhurt = skillhurt;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public double getSkillgain() {
		return skillgain;
	}
	public void setSkillgain(double skillgain) {
		this.skillgain = skillgain;
	}
	public String getSkilltype() {
		if (skilltype == null) {
			skilltype = "";
		}
		return skilltype;
	}
	public void setSkilltype(String skilltype) {
		this.skilltype = skilltype;
	}
	public int getSkillsum() {
		return skillsum;
	}
	public void setSkillsum(int skillsum) {
		this.skillsum = skillsum;
	}
	//初始化法宝技能属性
	public void initFB(int id,int blvl){
		switch (id) {
			case 5001:
				skilltype="fbYsjl";
				break;
			case 5002:
				skilltype="fbJjl";
				break;
			case 5003:
				skilltype="fbDsc";
				break;
			case 5004:
				skilltype="fbQbllt";
				break;
			case 5005:
				skilltype="fbHlz";
				break;
			case 5006:
				skilltype="fbYmgs";
				break;
			case 5007:
				skilltype="fbDsy";
				break;
			case 5008:
				skilltype="fbJqb";
				break;
			case 5009:
				skilltype="fbQw";
				break;
			case 5010:
				skilltype="fbBld";
				break;
			case 5011:
				skilltype="fbJge";
				break;
			case 5012:
				skilltype="fbFty";
				break;
			case 5013:
				skilltype="fbJljs";
				break;
			case 5014:
				skilltype="fbBgz";
				break;
			case 5015:
				skilltype="fbHd";
				break;
			default:
				break;
		}
	}
	/**天演策*/
	public void TYC(double value,double sv,int lvl){
		this.skillhurt=value+sv*lvl;
		if (skillid==9123) {//使用5睡命中超过5个目标时有（4%*等级）几率返还消耗法力的（10%*等级）
			this.skillgain=this.skillhurt*2.5;
		}else if (skillid==9125) {//被昏睡的目标摆脱昏睡状态时（非物理，法术攻击解除）有（4%*等级）几率扣除最大法量的（1.5%*等级）。（仅在与玩家之间战斗有效。）
			this.skillgain=this.skillhurt*0.375;
		}else if (skillid==9162) {//减少毒的回合数（1*等级），获得加强毒伤害（2%*等级）。（仅在与NPC之间战斗有效）
			this.skillgain=this.skillhurt*2;
		}else if (skillid==9511) {
			this.skillhurt=sv*(lvl+1);
		}else if (skillid==9612) {
			this.skillgain=lvl<=2?1:lvl<=4?3:4;
		}else if (skillid==9711||skillid==9387) {
			this.skillhurt=value-sv*lvl;
		}
		if (skillid==9110||skillid==9111||skillid==9126||skillid==9130
				||skillid==9151||skillid==9169||skillid==9170||skillid==9171
				||skillid==9189||skillid==9190||skillid==9207||skillid==9208||skillid==9209
				||skillid==9231||skillid==9232||skillid==9250||skillid==9251||skillid==9252
				||skillid==9262||skillid==9270||skillid==9286||skillid==9287||skillid==9307
				||skillid==9350||skillid==9352||skillid==9372||skillid==9389||skillid==9412
				||skillid==9510||skillid==9512||skillid==9610||skillid==9611||skillid==9612
				||skillid==9710||skillid==9711||skillid==9811||skillid==9812) {
			this.skillbeidong=0;
		}
	}
	public int getSkillcontinued() {
		return skillcontinued;
	}
	public void setSkillcontinued(int skillcontinued) {
		this.skillcontinued = skillcontinued;
	}
	public int getSkillblue() {
		return skillblue;
	}
	public void setSkillblue(int skillblue) {
		this.skillblue = skillblue;
	}
	public double getSkillhitrate() {
		return skillhitrate;
	}
	public void setSkillhitrate(double skillhitrate) {
		this.skillhitrate = skillhitrate;
	}
	/**为增益状态*/
	public boolean Goodskill() {
		if ((skillid >= 1026 && skillid <= 1040)
				|| (skillid >= 1076 && skillid <= 1080) || skillid == 1606
				|| skillid == 1607 || skillid == 1608 || skillid == 1611
				|| skillid == 1612 || skillid == 1804 || skillid == 1805
				|| skillid == 1808 || skillid == 1809 || skillid == 1821
				|| skillid == 1823 || skillid == 1824 || skillid == 1825
				|| skillid == 1826 || skillid == 1827 || skillid == 1829
				|| skillid == 1830) {
			return true;
		}
		return false;
	}
	/**根据熟练度计算个数*/
	public static int geshu(int level,int ed,String type){
		if (type.equals("鬼火") || type.equals("火") || type.equals("水")|| type.equals("雷") || type.equals("风")) {
			return xian(ed, level);
		} else if (type.equals("震慑")) {
			return moz(ed, level);
		} else if (type.equals("力量") || type.equals("抗性") || type.equals("加速")|| type.equals("smmh")) {
			return moq(ed, level);
		} else if (type.equals("中毒") || type.equals("封印") || type.equals("昏睡")|| type.equals("遗忘")) {
			return renq(ed, level);
		} else if (type.equals("混乱")) {
			return renh(ed, level);
		} else if (type.equals("三尸虫")) {
			return guis(ed, level);
		} else if (type.equals("霹雳")) {
			return lpl(ed, level);
		}
		return xian(ed, level);
	}
	/**仙法个数*/
	public static int xian(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 720) {return 2;}
			else if (ed < 5215) {return 3;}
			else if (ed < 16610) {return 4;}
			else {return 5;}
		} else if (lvl == 5) {
			if (ed < 558) {return 3;}
			else if (ed < 5621) {return 4;}
			else {return 5;}
		}
		return 1;
	}
	/**震慑*/
	public static int moz(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 426) {return 2;}
			else if (ed < 3098) {return 3;}
			else if (ed < 9866) {return 4;}
			else {return 5;}
		} else if (lvl == 5) {//等级5级
			if (ed < 226) {return 3;}
			else if (ed < 1638) {return 4;}
			else if (ed < 5215) {return 5;}
			else if (ed < 11868) {return 6;}
			else {return 7;}//目标数7个
		}
		return 1;
	}
	/**附攻、附防、加速 */
	public static int moq(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 214) {return 2;}
			else if (ed < 2155) {return 3;}
			else if (ed < 8324) {return 4;}
			else {return 5;}
		} else if (lvl == 5) {
			if (ed < 117) {return 3;}
			else if (ed < 1174) {return 4;}
			else if (ed < 4533) {return 5;}
			else if (ed < 11826) {return 6;}
			else {return 7;}
		}
		return 1;
	}
	/** 封印、昏睡、毒系 遗忘法术 */
	public static int renq(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 428) {
				return 2;
			} else if (ed < 3098) {
				return 3;
			} else if (ed < 9866) {
				return 4;
			} else {
				return 5;
			}
		} else if (lvl == 5) {
			if (ed < 226) {
				return 3;
			} else if (ed < 1638) {
				return 4;
			} else if (ed < 5215) {
				return 5;
			} else if (ed < 11864) {
				return 6;
			} else {
				return 7;
			}
		}
		return 1;
	}

	/** 混乱法术 */
	public static int renh(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 1362) {
				return 2;
			} else if (ed < 9866) {
				return 3;
			} else {
				return 4;
			}
		} else if (lvl == 5) {
			if (ed < 973) {
				return 3;
			} else if (ed < 7051) {
				return 4;
			} else {
				return 5;
			}
		}
		return 1;
	}

	/** 鬼魅惑 */
	public static int guiv(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 2200) {
				return 2;
			} else if (ed < 4600) {
				return 3;
			} else if (ed < 9600) {
				return 4;
			} else {
				return 5;
			}
		} else if (lvl == 5) {
			if (ed < 2200) {
				return 3;
			} else if (ed < 4600) {
				return 4;
			} else if (ed < 9600) {
				return 5;
			} else if (ed < 12000) {
				return 6;
			} else {
				return 7;
			}
		}
		return 1;
	}

	/** 鬼三尸 */
	public static int guis(int ed, int lvl) {
		if (lvl == 3) {
			if (ed < 5200) {return 2;}
			else if (ed < 6800) {return 3;}
			else {return 4;}
		} else if (lvl == 5) {
			if (ed < 2200) {return 3;}
			else if (ed < 6800) {return 4;}
			else {return 5;}
		}
		return 1;
	}
	/** 龙霹雳 */
	public static int lpl(int ed, int lvl) {
		if (lvl == 3||lvl == 5) {
			if (ed < 5200) {return 2;}
			else if (ed < 6800) {return 3;}
			else {return 5;}
		}
		return 1;
	}
	public int getSkilllvl() {
		return skilllvl;
	}
	public void setSkilllvl(int skilllvl) {
		this.skilllvl = skilllvl;
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
	public int getSkillbeidong() {
		return skillbeidong;
	}
	public void setSkillbeidong(int skillbeidong) {
		this.skillbeidong = skillbeidong;
	}
	@Override
	public FightingSkill clone(){
		try {
			return (FightingSkill) super.clone();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
