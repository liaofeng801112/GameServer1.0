package come.tool.Calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.come.bean.LoginResult;
import org.come.model.Skill;
import org.come.server.GameServer;
import org.come.tool.Arith;

import come.tool.Role.PrivateData;


/**
 * 基础值
 * @author Administrator
 *
 */
public class BaseValue {
	
	public static Map<BigDecimal, GradeQl[]> GradeQls;
	static{
		CalculationUtil.initGradeQl();
	}
	public static int getPetValue(int lvl, int P, double G, int base, int type) {
		if (type == 0 || type == 1) {
			return (int) (lvl * P * G) + (int) ((0.7 * lvl * G + 1) * base);
		} else if (type == 2) {
			return (int) (lvl * P * G / 5)
					+ (int) ((0.14 * lvl * G + 1) * base);
		}else if (type == 3) {
			return (int) ((base + P) * G);
		}else {
			return P;
		}
	}
	// 获取人物的属性值 种族id 点数 等级是（1转15 等级就是15） 类型 0hp 1mp 2ap 3sp 4定力
	public static int getRoleValue(BigDecimal raceid, int P, int lvl, int type) {
		double G = getValue(raceid, 1, type);
		if (type >= 4) {return (int) (G * P);}
		double base = getValue(raceid, 0, type);
		if (G > 2) {G = 0.01;}
		if (base > 400) {G = 10;}
		int E = (100 - lvl) / 5;
		int LEPG = (int) ((lvl + E) * P * G);
		if (type == 0 || type == 1) {
			return (int) (LEPG + base);
		} else if (type == 2) {
			return (int) (LEPG / 5 + base);
		} else {
			return (int) ((10 + P) * G);
		}
	}
	//人魔仙鬼龙
	public static final double bases[] = { 
		    360, 300, 70, 8, 
		    330, 210, 80, 10,
			300, 390, 60, 10, 
			270, 350, 80, 9, 
			300, 240, 80, 10 };
	public static final double basevs[] = { 
		    1.2 , 1   , 0.95, 0.8 , 1.05, 
		    1.1 , 0.6 , 1.3 , 1   , 1, 
		    1   , 1.4 , 0.7 , 1   , 0.9, 
		    1.25, 1.05, 0.95, 0.75, 0.9, 
		    0.9 , 0.7 , 1.3 , 1   , 1    };

	// 获取值 根据种族 类型0表示值1表示成长 zhi=0血1蓝2ap3sp
	public static double getValue(BigDecimal raceid, int type, int zhi) {
		if (type == 0) {
			return bases[zhi + getratio(raceid) * 4];
		} else {
			return basevs[zhi + getratio(raceid) * 5];
		}
	}

	/** 获取种族系数 **/
	public static int getratio(BigDecimal raceid) {
		if (raceid.intValue() == 10001) {
			return 0;
		} else if (raceid.intValue() == 10002) {
			return 1;
		} else if (raceid.intValue() == 10003) {
			return 2;
		} else if (raceid.intValue() == 10004) {
			return 3;
		} else if (raceid.intValue() == 10005) {
			return 4;
		}
		return 0;
	}
	/**完整经脉基础算法*/
	public static int getMeridiansLvl(int i) {
		int i2 = 0;
		do {
			i2++;
			i -= getMeridiansExp(i2);
		} while (i >= 0);
		return i2;
	}
	//基础数值工具-方法2
	public static int getMeridiansExp(int i) {
		return i * 100;
	}
	//基础数值工具-方法3
	public static int getMeridiansTotalExp(int i) {
		return (i + 1) * i * 50;
	}
	/**等级抗性*/
	public static void gradeQl(BigDecimal raceid,int lvl,int zs,Map<String, Double> map){
		GradeQl[] qls=GradeQls.get(raceid);
		if (qls!=null) {
			int glvl=lvl>162?162:lvl;
			for (int i = 0; i < qls.length; i++) {
				GradeQl gradeQl=qls[i];
				double v= gradeQl.getV()*glvl/gradeQl.getP();
				if (v!=0) {
					CalculationUtil.addValue(map, gradeQl.getType(), v);	
				}
			}
		}
		if (zs == 4) {
			lvl -= 140;
			lvl /= 10;
			if (raceid.intValue() == 10001) {
				CalculationUtil.addValue(map, "四抗上限", lvl * 1.2);	
			} else if (raceid.intValue() == 10002) {
				CalculationUtil.addValue(map, "四抗上限", lvl * 1.1);	
			} else if (raceid.intValue() == 10003) {
				CalculationUtil.addValue(map, "四抗上限", lvl * 1.0);	
			} else if (raceid.intValue() == 10004) {
				CalculationUtil.addValue(map, "四抗上限", lvl * 0.9);
			}
		}
	}
	/**抗性上限*/
	/**人法 遗忘上限*/
	public static double Upper(String key,BigDecimal raceid){
		if (raceid.intValue()==10003||raceid.intValue()==10002) {
			return 210;
		}else if (raceid.intValue()==10001) {
			return 240;
		}else if (raceid.intValue()==10004) {
			if (key.equals("抗遗忘")) {return 240;}
			else {return 220;}
		}else if (raceid.intValue()==10005) {
			return 220;
		}
		return 210;
	}

	/**帮派抗性数值*/
	/**true gx贡献值zf true表示主  false表示副*/
	public static double getBangQuality(BigDecimal gx,boolean isZhu){
		if (gx==null){
			return 0;
		}
		double x= getCubeRoot(gx.longValue()/5000);
		if (isZhu) {
			return x<30?new BigDecimal(x).setScale(1, RoundingMode.DOWN).doubleValue():30;
		}
		x /= 2;
		return x<15?new BigDecimal(x).setScale(1, RoundingMode.DOWN).doubleValue():15;
	}
	public  static double getCubeRoot(long input){
		if(input==0)
			return 0;
		double x0,x1;
		x0=input;
		x1=(2*x0/3)+(input/(x0*x0*3));//利用迭代法求解
		while(Math.abs(x1-x0)>0.000001){
			x0=x1;
			x1=(2*x0/3)+(input/(x0*x0*3));
		}
		return  x1;
	}
	
	/**获取修正属性 */
	public static BaseQl[] reborn(String born){
		if (born==null||born.equals("")) {
			return null;
		}
		String[] vs=born.split("\\|");
		Map<String,Double> map=new HashMap<>();
		for (int i = 0; i < vs.length; i++) {
			String[] vss=vs[i].split("=");
			Double value=map.get(vss[0]);
			if (value==null) {
				value=Math.abs(Double.parseDouble(vss[1]));
			}else {
				value+=Math.abs(Double.parseDouble(vss[1]));
			}
			map.put(vss[0], value);			
		}
		return isV(map);
	}
	/**检测修正是否合理*/
	public static BaseQl[] isV(Map<String,Double> map){
		BaseQl[] qls=new BaseQl[map.size()];
		int i=0;
		for (Entry<String, Double> entry : map.entrySet()) { 
			String key=entry.getKey();
			double value=entry.getValue();
			double max=369000;
			if (key.equals("抗混乱")||key.equals("抗封印")||key.equals("抗昏睡")||key.equals("抗遗忘")||key.equals("反震程度")||key.equals("速度")) {
				max=Arith.mul(max, 0.000125);
			}else if (key.equals("抗混乱上限")||key.equals("抗封印上限")||key.equals("抗昏睡上限")||key.equals("抗遗忘上限")) {
				max=Arith.mul(max,0.000016875);
			}else if (key.equals("抗毒伤")) {
				max=Arith.mul(max,0.02);
			}else if (key.equals("MP成长")||key.equals("HP成长")) {
				max=Arith.mul(max,0.000001);
			}else if (key.equals("物理吸收")) {
				max=Arith.mul(max,0.0001875);
			}else if (key.equals("抗震慑")) {
				max=Arith.mul(max,0.0001125);
			}else if (key.equals("SP成长")) {
				max=Arith.mul(max,0.00000075);
			}else if (key.equals("抗风")||key.equals("抗雷")||key.equals("抗水")||key.equals("抗火")||key.equals("抗鬼火")) {
				max=Arith.mul(max,0.00016875);
			}else if (key.equals("抗三尸")) {
				max=Arith.mul(max,0.025);
			}else if (key.equals("反震率")||key.equals("躲闪")) {
				max=Arith.mul(max,0.000065);
			}else if (key.equals("伤害减免")) {
				max=Arith.mul(max,0.000033);
			}else if (key.equals("法术躲闪")) {
				max=Arith.mul(max,0.00001464);
			}else if (key.equals("抗天魔解体")||key.equals("抗分光化影")||key.equals("抗青面獠牙")||key.equals("抗小楼夜哭")) {
				max=Arith.mul(max,0.00625);
			}else {
				return null;
			}
			if (value>max) {return null;}
			qls[i]=new BaseQl(key, value);
			i++;
		}
		return qls;
	} 
	/**解析帮派掉落*/
	public static BaseQl[] xls(String[] xls){
		if (xls==null||xls.length==0) {
			return null;
		}
		BaseQl[] qls=new BaseQl[xls.length];
		for (int i = 0; i < xls.length; i++) {
			String[] vs=xls[i].split("=");
			qls[i]=new BaseQl(vs[0], Double.parseDouble(vs[1]));
		}
		return qls;
	}
	public static String[] XC=new String[]{"抗雷","抗火","抗风","抗水","抗混乱","抗封印","抗昏睡","抗中毒","抗震慑","抗物理","抗三尸虫","抗鬼火","抗遗忘"};
	public static String[] DC=new String[]{"物理躲闪","震慑躲闪","火法躲闪","雷法躲闪","风法躲闪","水法躲闪",
		"毒法躲闪","封印躲闪","混乱躲闪","昏睡躲闪","遗忘躲闪","鬼火躲闪","三尸虫躲闪",
		"水法伤害减免","风法伤害减免","雷法伤害减免","水法伤害减免","鬼火伤害减免"};
	
    /**是否是符合字段*/
	public static boolean isXl(String key,String[] v){
		for (int i = 0; i < v.length; i++) {if (v[i].equals(key)) {return false;}}
		return true;
	}
	/**判断格式是否正常*/
	public static BaseQl[] isXls(BaseQl[] oldXls,String[] xls,int max,int type){
		try {
			int up=type==1?20:30;
			int total=0;
			BaseQl[] qls=new BaseQl[xls.length];
			for (int i = 0; i < xls.length; i++) {
				String[] vs=xls[i].split("=");
				if (isXl(vs[0],type==1?XC:DC)) {return null;}
				qls[i]=new BaseQl(vs[0], Double.parseDouble(vs[1]));
				//同样抗性
				for (int j = 0; j < i; j++) {if (qls[j].getKey().equals(vs[0])) {return null;}}
				double value=0;
				int lvl=0;
				if (vs[0].equals("抗三尸虫")) {
					lvl=(int) (qls[i].getValue()/100);
					value=lvl*100D;
				}else if (vs[0].endsWith("躲闪")) {
					lvl=(int) (qls[i].getValue()/0.5);
					value=lvl*0.5D;
				}else if (vs[0].endsWith("减免")) {
					lvl=(int) (qls[i].getValue()/1);
					value=lvl*1D;
				}else {
					lvl=(int) (qls[i].getValue()/1.5);
					value=lvl*1.5D;
				}
				if (lvl>up) {return null;}
				total+=lvl;
				if (value!=qls[i].getValue()) {return null;}
			}
			if (total>max) {return null;}
			if (oldXls!=null) {
				int sum=oldXls.length;
				for (int i = 0; i < oldXls.length; i++) {
					for (int j = 0; j < qls.length; j++) {
						if (oldXls[i].getKey().equals(qls[j].getKey())) {
							sum-=oldXls[i].getValue()<=qls[j].getValue()?1:0;
							break;
						}
					}
				}
				if (sum>0) {
					return null;
				}
			}
			return qls;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/** 根据品质获取系数 */
	public static int getQv(String quality) {
		switch (quality) {
		case "把玩":
			return 10;
		case "贴身":
			return 20;
		case "珍藏":
			return 30;
		case "无价":
			return 40;
		case "传世":
			return 50;
		case "神迹":
			return 60;
		}
		return 10;
	}
	
	/**获取人物技能 S技能 T天演策 F法门 Q其他 */
	public static List<BaseSkill> reSkill(PrivateData data,LoginResult login){
		String skill=data.getSkills();
		if (skill==null||skill.equals("")) {
			return null;
		}
		List<BaseSkill> baseSkills=new ArrayList<>();
		String[] vs = skill.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].startsWith("S")) {
				String[] vss=vs[i].split("#");
				vss[0]=vss[0].substring(1);
				for (int j = 0; j < vss.length; j++) {
					String[] ss=vss[j].split("_");
					int id=Integer.parseInt(ss[0]);
					if (id<=1000||id>1100) {
						continue;
					}
					int sld=Integer.parseInt(ss[1]);
					if (sld>25000) {
						sld=25000;
					}
					Skill skill2=GameServer.getSkill(id+"");
					if (skill2!=null) {
						baseSkills.add(new BaseSkill(id, sld, skill2, null));	
					}
				}	
			}else if (vs[i].startsWith("T")) {
				String[] vss=vs[i].split("#");
				int T=getTSP(login.getExtraPointInt("T"));
				for (int j = 1; j < vss.length; j++) {
					String[] ss=vss[j].split("_");
					int id=Integer.parseInt(ss[0]);
					if (id<=9000||id>10000) {
						continue;
					}
					int lvl=Integer.parseInt(ss[1]);
					if (lvl>5) {
						lvl=5;
					}
					T-=lvl;
					if (T<0) {
						data.setSkills("T", null);
						return reSkill(data,login);
					}
					BaseQl baseQl=null;
					if (id == 9001) {
						baseQl=new BaseQl("MP", lvl * 2000);
					} else if (id == 9002) {
						baseQl=new BaseQl("HP", lvl * 4000);
					} else if (id == 9003) {
						baseQl=new BaseQl("HP", lvl * 1000);
					} else if (id == 9004) {
						baseQl=new BaseQl("根骨", lvl);
					} else if (id == 9005) {
						baseQl=new BaseQl("力量", lvl);
					} else if (id == 9006) {
						baseQl=new BaseQl("AP", lvl * 100);
					} else if (id == 9007) {
						baseQl=new BaseQl("SP", lvl);
					} else if (id == 9008) {
						baseQl=new BaseQl("抗风", lvl);
					} else if (id == 9009) {
						baseQl=new BaseQl("抗火", lvl);
					} else if (id == 9010) {
						baseQl=new BaseQl("抗水", lvl);
					} else if (id == 9011) {
						baseQl=new BaseQl("抗雷", lvl);
					} else if (id == 9012) {
						baseQl=new BaseQl("抗鬼火", lvl);
					} else if (id == 9013) {
						baseQl=new BaseQl("抗三尸", lvl * 100);
					} else if (id == 9014) {
						baseQl=new BaseQl("抗反震", lvl * 500);
					} else if (id == 9015) {
						baseQl=new BaseQl("风法狂暴", lvl);
					} else if (id == 9016) {
						baseQl=new BaseQl("火法狂暴", lvl);
					} else if (id == 9017) {
						baseQl=new BaseQl("水法狂暴", lvl);
					} else if (id == 9018) {
						baseQl=new BaseQl("鬼火伤害", lvl * 100);
					} else if (id == 9019) {
						baseQl=new BaseQl("强力克金", lvl);
					} else if (id == 9020) {
						baseQl=new BaseQl("强力克木", lvl);
					} else if (id == 9021) {
						baseQl=new BaseQl("强力克火", lvl);
					} else if (id == 9022) {
						baseQl=new BaseQl("强力克土", lvl);
					} else if (id == 9023) {
						baseQl=new BaseQl("物理吸收", lvl);
					} else if (id == 9024) {
						baseQl=new BaseQl("躲闪率", lvl);
					} else if (id == 9025) {
						baseQl=new BaseQl("强震慑", lvl);
					} else if (id == 9026) {
						baseQl=new BaseQl("反击率", lvl);
					} else if (id == 9027) {
						baseQl=new BaseQl("反击次数", lvl);
					} else if (id == 9028) {
						baseQl=new BaseQl("狂暴率", lvl);
					} else if (id == 9029) {
						baseQl=new BaseQl("反震率", lvl);
					} else if (id == 9030) {
						baseQl=new BaseQl("敏捷", lvl);
					} else if (id == 9031) {
						baseQl=new BaseQl("恢复气血", lvl * 400);
					}
					Skill skill2=GameServer.getSkill(id+"");
					if (skill2!=null) {
						baseSkills.add(new BaseSkill(id, lvl, skill2,baseQl));	
					}
				}
			}else if (vs[i].startsWith("F")) {
				String[] vss=vs[i].split("#");
				vss[0]=vss[0].substring(1);
				for (int j = 0; j < vss.length; j++) {
					String[] ss=vss[j].split("_");
					int id=Integer.parseInt(ss[0]);
					if (id<22000||id>23000) {
						continue;
					}
					int sld=Integer.parseInt(ss[1]);
					if (sld>25000) {
						sld=25000;
					}
					Skill skill2=GameServer.getSkill(id+"");
					if (skill2!=null) {
						baseSkills.add(new BaseSkill(id, sld, skill2, null));
					}
				}
			}else if (vs[i].startsWith("X")) {
				String[] vss=vs[i].split("#");
				vss[0]=vss[0].substring(1);
				for (int j = 0; j < vss.length; j++) {
					String[] ss=vss[j].split("_");
					int id=Integer.parseInt(ss[0]);
					if (id<23000||id>23010) {
						continue;
					}
					int sld=Integer.parseInt(ss[1]);
					if (sld>25000) {
						sld=25000;
					}
					BaseQl baseQl=null;
					if (id == 23003) {
						baseQl=new BaseQl("AP", login.getPower() * 5);
					}
					Skill skill2=GameServer.getSkill(id+"");
					if (skill2!=null) {
						baseSkills.add(new BaseSkill(id, sld, skill2, null));
					}
				}
			}
		}
		return baseSkills;
	}
	/**获取系数 转生次数 人物等级  法宝总等级  法宝品质  法宝等级*/
	public static int getFBlvl(int t,int g,int qv,int lvl){
//		200  70基础 30是等级  100是总等级
		int pj=0;
		g+=t*25;
		pj+=g*3;
		pj+=(qv+lvl)*15;
		qv+=lvl;
		pj+=qv*4;
		return pj>>5;	
	}
	/**根据点数获取当前的天枢点*/
	public static int getTSP(int P){
		int x=9;
		int dd=0;
		int i=0;
		while (true) {
			if (i%2==0) {dd++;}
			if (i%38==4) {dd++;}
			x+=dd;
			if (P>=x) {
				P-=x;
				i++;	
			}else {
				return i;
			}	
		}
	}
}
