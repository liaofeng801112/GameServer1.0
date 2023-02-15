package come.tool.Mixdeal;

import java.math.BigDecimal;

/**
 * 解析字符串
 * @author Administrator
 *
 */
public class AnalysisString {
    /**
     * 禁止交易
     * true表示禁止交易
     */
	public static boolean jiaoyi(long type){
		if (type==1||type==3||type==5) {
			return true;
		}
		return false;	
	}
	/**
	 * 药品属性解析
	 */
	public static int[] yao(String value){
		 int[] yao=new int[4];	
		  // 注意要加\\,要不出不来,yeah
		  String[] names = value.split("\\|");
		  for (int i = 0; i < names.length; i++) { 
			 if (names[i].split("=").length>=2)  {
				if (names[i].split("=")[0].equals("HP")) {
					yao[0]=+Integer.parseInt(names[i].split("=")[1]);
				}else if (names[i].split("=")[0].equals("MP")) {
					 yao[1]=+Integer.parseInt(names[i].split("=")[1]);
				}else if (names[i].split("=")[0].equals("HP%")) {
					yao[2]=+Integer.parseInt(names[i].split("=")[1]);
				}else if (names[i].split("=")[0].equals("MP%")) {
					 yao[3]=+Integer.parseInt(names[i].split("=")[1]);
				}
			}
		  }		  
		  return yao;	
	}
	/**
	 * 钱 点卡 经验 亲密丹 技能熟练度使用
	 */
	public static long[] xiaohao(String value){
		long[] xiaohao=new long[5];	
		  // 注意要加\\,要不出不来,yeah
		  String[] names = value.split("\\|");
		  for (int i = 0; i < names.length; i++) { 
				 if (names[i].split("=").length>=2)  {
						if (names[i].split("=")[0].equals("钱")) {
							xiaohao[0]=+Long.parseLong(names[i].split("=")[1]);
						}else if (names[i].split("=")[0].equals("点")) {
							xiaohao[1]=+Long.parseLong(names[i].split("=")[1]);
						}else if (names[i].split("=")[0].equals("经验")) {
							xiaohao[2]=+Long.parseLong(names[i].split("=")[1]);
						}else if (names[i].split("=")[0].equals("亲密")) {
							xiaohao[3]=+Long.parseLong(names[i].split("=")[1]);
						}else if (names[i].split("=")[0].equals("技能")) {
							xiaohao[4]=+Long.parseLong(names[i].split("=")[1]);
						}
					}
				  }		  
		  return xiaohao;
	}
	/**
	 * 满熟练度值
	 */
	public static int shuliandu(int lvl){
		int sld=5000;
		if (lvl<=102) {
			sld+=5000;
		}else if (lvl<=210) {
			sld+=10000;
		}else if (lvl<=338) {
			sld+=15000;
		}else {
			sld+=20000;	
		}
		return sld;
	}
	/**
	 * 人物根据等级解出几转几级
	 */
	public static String lvl(int lvl){
		
		if (lvl<=102) {
			return "0转"+lvl;
		}else if (lvl<=210) {
			return "1转"+(lvl-102+14);
		}else if (lvl<=338) {
			return "2转"+(lvl-210+14);
		}else if (lvl<=504){
			return "3转"+(lvl-338+59);	
		}else {
			return "飞升"+(lvl-459+139);  
		}
	} 
	/**
	 * 人物根据等级解出几转
	 */
	public static int lvltrue(int lvl){	
		if (lvl<=102) {
			return 0;
		}else if (lvl<=210) {
			return 1;
		}else if (lvl<=338) {
			return 2;
		}else if (lvl<=504){
			return 3;	
		}else {
			return 4;  
		}
	}
	/**
	 * 召唤兽根据等级解出几转几级
	 */
	public static String petLvl(int lvl){
		
		if (lvl<=100) {
			return "0转"+lvl;
		}else if (lvl<=221) {
			return "1转"+(lvl-101);
		}else if (lvl<=362) {
			return "2转"+(lvl-222);
		}else if (lvl<=543){
			return "3转"+(lvl-363);	
		}else {
			return "飞升"+(lvl-544);
		}
	}
	/**
	 * 召唤兽根据等级解出几转
	 */
	public static int petTurnRount(int lvl){
		
		if (lvl<=100) {
			return 0;
		}else if (lvl<=221) {
			return 1;
		}else if (lvl<=362) {
			return 2;
		}else if (lvl<=543){
			return 3;	
		}else {
			return 4;
		}
	}
	/**
	 * 召唤兽根据等级解出几级
	 */
	public static int petLvlint(int lvl){
		
		if (lvl<=100) {
			return lvl;
		}else if (lvl<=221) {
			return (lvl-101);
		}else if (lvl<=362) {
			return (lvl-222);
		}else if (lvl<=543){
			return (lvl-363);	
		}else {
			return (lvl-544);
		}
	}
	/**
	 * 计算Double类型相加的算法
	 */
	public static BigDecimal mathDouble(double v1, double v2) {
		 
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

	/**
	 * 根据等级解出几级
	 */
	public static int lvlint(int lvl){
		if (lvl<=102) {
			return lvl;
		}else if (lvl<=210) {
			return (lvl-102+14);
		}else if (lvl<=338) {
			return (lvl-210+14);
		}else if (lvl<=459){
			return (lvl-338+59);	
		}else {
			return (lvl-459+139);
		}
	} 
	
	/**
	 * 判断等级反解
	 */
	public static int lvldirection(String lvlstring){
		
		String[] lvls=lvlstring.split("转");
		if (lvls.length==1) {
			return Integer.parseInt(lvls[0]);
		}else
			if (lvls[0].equals("1")) {
			return Integer.parseInt(lvls[1])+102-14;
		}else if (lvls[0].equals("2")) {
			return Integer.parseInt(lvls[1])+209-14;
		}else if (lvls[0].equals("3")) {

			return Integer.parseInt(lvls[1])+336-59;
		}else if (lvlstring.indexOf("飞升")==0) {
			return Integer.parseInt(lvlstring.split("升")[1])+504-14;
		}else {
			return Integer.parseInt(lvls[0]);
		}	
	}

	
	
	/**
	 * 判断等级是否满足
	 */
	public static boolean lvlfull(int lvl,String lvlstring){
		
		if (lvl>=lvldirection(lvlstring)) {
			return true;
		}
		return false;
		
	}
    /**
     * 找到字段返回double 数值
     */
	public static double valuejie(String value,String type){
		String[] v=value.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v2=v[i].split("=");
			if (v2.length>1) {
				if (type.equals(v2[0])) {
					if (type.equals("活跃")||type.equals("速度")||type.equals("负敏")) {
						return Double.parseDouble(v2[1]);		
					}else {
						return Double.parseDouble(v2[1])/100;		
					}}}}		
		return 0;
	}
}
