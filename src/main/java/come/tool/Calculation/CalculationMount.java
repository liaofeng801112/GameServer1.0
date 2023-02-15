package come.tool.Calculation;

import java.math.BigDecimal;

import org.come.entity.Mount;

import come.tool.FightingData.GetqualityUntil;
import come.tool.FightingData.Ql;

public class CalculationMount {
	/** 坐骑技能效果系数 */
	public static double[] xishu = { 0.3, 0.3, 0.7, 0.7, 0, 0, 10000, 10000, 1.2 };
	public static double[][] zuoqi = { { 4.115226337, 1.141552511 }, { 14.40329218, 3.99543379 }, { 4.8, 1.333333333 },
			{ 14.4, 4 }, { 14.4, 4 }, { 3.6, 1 }, { 7.2, 2 }, { 4.8, 1.333333333 }, { 7.2, 2 }, { 4.8, 1.333333333 },
			{ 7.2, 2 }, { 4.8, 1.333333333 }, { 7.2, 2 }, { 7.2, 2 }, { 4.8, 1.333333333 }, { 7.2, 2 }, { 14.4, 4 },
			{ 4.8, 1.333333333 }, { 7.2, 2 }, { 4.8, 1.333333333 }, { 3.6, 1 }, { 14.4, 4 }, { 7.2, 2 } };
	/**计算坐骑技能加成的方法*/
	public static String calculateAddition(Mount mount, String skillname,Ql ql) {
		if (skillname.equals("夺命追魂")) {
			double xs=returnsCalculation("连击率", mount, skillname);
			GetqualityUntil.AddR(ql, "连击率", xs);
			GetqualityUntil.AddR(ql, "致命",   xs);
			GetqualityUntil.AddR(ql, "命中",   xs);
			return "SP="+(returnsCalculation("SP", mount, skillname)/100);
		} else if (skillname.equals("天雷怒火")) {
			double xs1=returnsCalculation("火法伤害", mount, skillname)*100;
			GetqualityUntil.AddR(ql, "火法伤害",   xs1);
			GetqualityUntil.AddR(ql, "雷法伤害",   xs1);
			double xs2=returnsCalculation("抗火", mount, skillname);
			GetqualityUntil.AddR(ql, "抗火",   xs2);
			GetqualityUntil.AddR(ql, "抗雷",   xs2);
			GetqualityUntil.AddR(ql, "抗鬼火", xs2);
			return "MP="+(returnsCalculation("MP", mount, skillname)/100);
		} else if (skillname.equals("金身不坏")) {
			GetqualityUntil.AddR(ql, "抗物理", returnsCalculation("抗物理", mount, skillname));
			GetqualityUntil.AddR(ql, "抗震慑", returnsCalculation("抗震慑", mount, skillname));
			GetqualityUntil.AddR(ql, "抗中毒", returnsCalculation("抗中毒", mount, skillname));
			GetqualityUntil.AddR(ql, "抗三尸", returnsCalculation("抗三尸虫", mount, skillname));
			return "HP="+(returnsCalculation("HP", mount, skillname)/100);
		} else if (skillname.equals("破釜沉舟")) {
			GetqualityUntil.AddR(ql, "狂暴率", returnsCalculation("狂暴", mount, skillname));
			double xs=returnsCalculation("忽视防御几率", mount, skillname);
			GetqualityUntil.AddR(ql, "忽视防御几率", xs);
			GetqualityUntil.AddR(ql, "忽视防御程度", xs);
			return "AP="+(returnsCalculation("AP", mount, skillname)/100);
		} else if (skillname.equals("兴风作浪")) {
			double xs1=returnsCalculation("风法伤害", mount, skillname)*100;
			GetqualityUntil.AddR(ql, "风法伤害",   xs1);
			GetqualityUntil.AddR(ql, "水法伤害",   xs1);
			double xs2=returnsCalculation("抗风", mount, skillname);
			GetqualityUntil.AddR(ql, "抗风",   xs2);
			GetqualityUntil.AddR(ql, "抗水",   xs2);
			GetqualityUntil.AddR(ql, "抗鬼火", xs2);
			return "MP="+(returnsCalculation("MP", mount, skillname)/100);
		} else if (skillname.equals("天神护体")) {
			double xs=returnsCalculation("抗风", mount, skillname);
			GetqualityUntil.AddR(ql, "抗风",   xs);
			GetqualityUntil.AddR(ql, "抗火",   xs);
			GetqualityUntil.AddR(ql, "抗水",   xs);
			GetqualityUntil.AddR(ql, "抗雷",   xs);
			GetqualityUntil.AddR(ql, "抗鬼火", xs);
			return "SP="+(returnsCalculation("SP", mount, skillname)/100);
		} else if (skillname.equals("后发制人")) {
			GetqualityUntil.AddR(ql, "狂暴率", returnsCalculation("狂暴", mount, skillname));
			return "HP="+(returnsCalculation("HP", mount, skillname)/100);
		} else if (skillname.equals("万劫不复")) {
			double xs=returnsCalculation("加强风", mount, skillname);
			GetqualityUntil.AddR(ql, "加强风",   xs);
			GetqualityUntil.AddR(ql, "加强火",   xs);
			GetqualityUntil.AddR(ql, "加强水",   xs);
			GetqualityUntil.AddR(ql, "加强雷",   xs);
			return "MP="+(returnsCalculation("MP", mount, skillname)/100);
		} else if (skillname.equals("心如止水")) {
			double xs=returnsCalculation("抗昏睡", mount, skillname);
			GetqualityUntil.AddR(ql, "抗昏睡",   xs);
			GetqualityUntil.AddR(ql, "抗封印",   xs);
			GetqualityUntil.AddR(ql, "抗中毒",   xs);
			GetqualityUntil.AddR(ql, "抗混乱",   xs);
			GetqualityUntil.AddR(ql, "抗遗忘",   xs);
			return "HP="+(returnsCalculation("HP", mount, skillname)/100);
		}
		return null;
	}
	/**
	 * 计算技能效果的方法
	 */
	public static double returnsCalculation(String mes, Mount mount, String skillname) {
		double zjxz = 1;
		if (mount.getMountid() == 2 || mount.getMountid() == 4) {
			zjxz = 1.2;
		}
		int grade = mount.getMountlvl();// 等级
		if (grade > 100) {
			grade -= 100;
		}
		// 最新的坐骑属性
		int lingxing = (int) Math.floor(mount.getSpri() + (float) grade / 10 * mount.getSpri() / 2);
		int liliang  = (int) Math.floor(mount.getPower() + (float) grade / 10 * mount.getPower() / 2);
		int genggu   = (int) Math.floor(mount.getBone() + (float) grade / 10 * mount.getBone() / 2);
		int shulian = mount.getProficiency();// 熟练度
		// 计算出来的技能效果值
		double jnxgz = 0;
		if (mes.equals("HP")) {
			if (skillname.equals("金身不坏")) {
				jnxgz = (genggu * xishu[2] + lingxing * xishu[4] + liliang * xishu[1]) * zjxz / zuoqi[4][0] + shulian/ 10000 / zuoqi[4][1];
			} else if (skillname.equals("后发制人")) {
				jnxgz = (genggu * xishu[0] + lingxing * xishu[4] + liliang * xishu[3]) * zjxz / zuoqi[16][0] + shulian/ xishu[7] / zuoqi[16][1];
			} else if (skillname.equals("心如止水")) {
				jnxgz = (genggu * xishu[2] + lingxing * xishu[1] + liliang * xishu[5]) * zjxz / zuoqi[1][0] + shulian/ xishu[6] / zuoqi[1][1];
			}
		} else if (mes.equals("MP")) {
			if (skillname.equals("天雷怒火")) {
				jnxgz = (genggu * xishu[4] + lingxing * xishu[3] + liliang * xishu[0]) * zjxz / zuoqi[13][0] + shulian/ xishu[6] / zuoqi[13][1];
			} else if (skillname.equals("兴风作浪")) {
				jnxgz = (genggu * xishu[1] + lingxing * xishu[2] + liliang * xishu[5]) * zjxz / zuoqi[10][0] + shulian/ xishu[6] / zuoqi[10][1];
			} else if (skillname.equals("万劫不复")) {
				jnxgz = (genggu * xishu[5] + lingxing * 1 + liliang * xishu[4]) * zjxz / zuoqi[8][0] + shulian/ xishu[6] / zuoqi[8][1];
			}
		} else if (mes.equals("AP")) {
			jnxgz = (genggu * xishu[5] + lingxing * xishu[4] + liliang * 1) * zjxz / zuoqi[18][0] + shulian / xishu[7]/ zuoqi[18][1];
		} else if (mes.equals("SP")) {
			if (skillname.equals("夺命追魂")) {
				jnxgz = (genggu * xishu[4] + lingxing * xishu[0] + liliang * xishu[3]) * zjxz / zuoqi[21][0] + shulian/ xishu[6] / zuoqi[21][1];
			} else if (skillname.equals("天神护体")) {
				jnxgz = (genggu * 1 + lingxing * xishu[4] + liliang * xishu[4]) * zjxz / zuoqi[3][0] + shulian/ xishu[6] / zuoqi[3][1];
			}
		} else if (mes.equals("连击率") || mes.equals("致命") || mes.equals("命中")) {
			jnxgz = (genggu * xishu[5] + lingxing * xishu[1] + liliang * xishu[2]) * zjxz / zuoqi[22][0] + shulian/ 10000 / zuoqi[22][1];
		} else if (mes.equals("抗风") || mes.equals("抗火") || mes.equals("抗水") || mes.equals("抗雷")||mes.equals("抗鬼火")) {
			if (skillname.equals("天神护体")) {
				jnxgz = (genggu * 1 + lingxing * xishu[4] + liliang * xishu[4]) * zjxz / zuoqi[2][0] + shulian/ xishu[6] / zuoqi[2][1];
			} else if (skillname.equals("兴风作浪")) {
				jnxgz = (genggu * xishu[1] + lingxing * xishu[2] + liliang * xishu[5]) * zjxz / zuoqi[12][0] + shulian/ xishu[6] / zuoqi[12][1];
			} else if (skillname.equals("天雷怒火")) {
				jnxgz = (genggu * xishu[4] + lingxing * xishu[2] + liliang * xishu[1]) * zjxz / zuoqi[15][0] + shulian/ xishu[6] / zuoqi[15][1];
			}
		} else if (mes.equals("火法伤害") || mes.equals("雷法伤害") || mes.equals("火雷伤害")) {
			jnxgz = (genggu * xishu[5] + lingxing * xishu[3] + liliang * xishu[0]) * zjxz / zuoqi[14][0] + shulian/ 10000 / zuoqi[14][1];
		} else if (mes.equals("抗火雷")) {
			jnxgz = (genggu * xishu[4] + lingxing * xishu[2] + liliang * xishu[1]) * zjxz / zuoqi[15][0] + shulian/ xishu[6] / zuoqi[15][1];
		} else if (mes.equals("抗物理")) {
			jnxgz = (genggu * xishu[3] + lingxing * xishu[5] + liliang * xishu[0]) * zjxz / zuoqi[5][0] + shulian/ xishu[6] / zuoqi[5][1];
		} else if (mes.equals("抗震慑")) {
			jnxgz = (genggu * xishu[3] + lingxing * xishu[4] + liliang * xishu[1]) * zjxz / zuoqi[6][0] + shulian/ xishu[6] / zuoqi[6][1];
		} else if (mes.equals("抗中毒")) {
			if (skillname.equals("金身不坏")) {
				jnxgz = (genggu * xishu[2] + lingxing * xishu[4] + liliang * xishu[0]) * zjxz / zuoqi[7][0] + shulian/ 10000 / zuoqi[7][1];
			} else if (skillname.equals("心如止水")) {
				jnxgz = (genggu * xishu[3] + lingxing * xishu[0] + liliang * xishu[4]) * zjxz / zuoqi[0][0] + shulian/ xishu[7] / zuoqi[0][1];
			}
		} else if (mes.equals("抗三尸虫")) {
			jnxgz = (genggu * xishu[2] + lingxing * xishu[4] + liliang * xishu[0]) * 125 / 3 + shulian * 1500 / 100000;
		} else if (mes.equals("狂暴")) {
			if (skillname.equals("破釜沉舟")) {
				jnxgz = (genggu * xishu[4] + lingxing * xishu[4] + liliang * 1) * zjxz / zuoqi[19][0] + shulian/ xishu[6] / zuoqi[19][1];
			} else if (skillname.equals("后发制人")) {
				jnxgz = (genggu * xishu[1] + lingxing * xishu[5] + liliang * xishu[3]) * zjxz / zuoqi[17][0] + shulian/ xishu[6] / zuoqi[17][1];
			}
		} else if (mes.equals("忽视防御几率") || mes.equals("忽视防御程度")) {
			jnxgz = (genggu * xishu[4] + lingxing * xishu[5] + liliang * 1) * zjxz / zuoqi[20][0] + shulian / xishu[6]/ zuoqi[20][1];
		} else if (mes.equals("风法伤害") || mes.equals("水法伤害")) {
			jnxgz = (genggu * xishu[0] + lingxing * xishu[3] + liliang * xishu[5]) * zjxz / zuoqi[11][0] + shulian/ xishu[6] / zuoqi[11][1];
		} else if (mes.equals("加强风") || mes.equals("加强火") || mes.equals("加强水") || mes.equals("加强雷")) {
			jnxgz = (genggu * xishu[5] + lingxing * 1 + liliang * xishu[5]) * zjxz / zuoqi[9][0] + shulian / xishu[6]/ zuoqi[9][1];
		} else if (mes.equals("抗昏睡") || mes.equals("抗封印") || mes.equals("抗混乱") || mes.equals("抗遗忘")) {
			jnxgz = (genggu * xishu[3] + lingxing * xishu[0] + liliang * xishu[4]) * zjxz / zuoqi[0][0] + shulian/ xishu[7] / zuoqi[0][1];
		}
//		String ss = null;
//		if (mes.equals("抗三尸虫")) {
//			ss = mes + "=" + (int) jnxgz;
//		}else {
//			ss = mes + "=" + keepTwoDecimals(jnxgz / 100);
//		}
		return jnxgz;
	}
	/**保留两位小数的方法*/
	public static double keepTwoDecimals(Double value) {
		BigDecimal b = new BigDecimal(value);
		double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}
}
