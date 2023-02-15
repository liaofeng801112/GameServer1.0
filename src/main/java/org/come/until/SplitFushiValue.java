package org.come.until;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 分解符石属性，随机生成属性
 * @author 叶豪芳
 * @date 2017年12月26日 下午8:13:56
 * 
 */ 
public class SplitFushiValue {
	
	public static String splitFushiValue( String fushiValue ){
		while(true){
			// 获得所有符石属性
			String[] values = fushiValue.split("\\|");
			// 获得的属性
			String newvalue = values[0];
			// 随机产生1-3个属性
			int[] a = randomArray(1,3,1);
			// 产生的属性
			int[] b = randomArray(1,values.length-1,a[0]);

			// 随机产生数值
			for (int i : b) {
				String[] value = values[i].split("=");
				String number = "";
				String[] numbers = value[1].split("-");
				if( value[0].equals("活跃") || value[0].equals("速度")||value[0].equals("负敏")  ){
					int min = Integer.parseInt(numbers[0]);
					int max = Integer.parseInt(numbers[1]);
					// 产生随机值
					int[] c = randomArray(min, max, 1);
					number = c[0] + "";
				}else{
					int min = (int)(Double.parseDouble(numbers[0])*10000);
					int max = (int)(Double.parseDouble(numbers[1])*10000);
					// 产生随机值
					int[] c = randomArray(min, max, 1);
					if (c[0]>=100) {
						c[0]=c[0]/10;
						number = (double)c[0]/10 + "";		
					
					}else {
						number = (double)c[0]/100 + "";		
						
					}
					
				}
				newvalue += "|" + value[0] + "=" + number;
			}
			if( newvalue.indexOf("速度") == -1 && newvalue.indexOf("负敏") != -1 || newvalue.indexOf("速度") != -1 && newvalue.indexOf("负敏") == -1 ){
			
				return newvalue;
			}
		}
	} 

	/**
	 * 随机指定范围内N个不重复的数
	 * 在初始化的无重复待选数组中随机产生一个数放入结果中，
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
	 * 然后从len-2里随机产生下一个随机数，如此类推
	 * @param max  指定范围最大值
	 * @param min  指定范围最小值
	 * @param n  随机数个数
	 * @return int[] 随机数结果集
	 */
	public static int[] randomArray(int min,int max,int n){
		int len = max-min+1;

		if(max < min || n > len){
			return null;
		}

		//初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min+len; i++){
			source[i-min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}
    
	/**
	 * 根据等级和类型获取到灵宝符石id
	 * @param args
	 */
	public static BigDecimal fushiid(String lvl,String type){
		BigDecimal id=new BigDecimal(lvl);
		if (type.equals("青龙符石")) {
			
		}else if (type.equals("朱雀符石")) {
			id=new BigDecimal(5+Integer.parseInt(lvl));
		}else if (type.equals("白虎符石")) {
			id=new BigDecimal(10+Integer.parseInt(lvl));
		}else if (type.equals("玄武符石")) {
			id=new BigDecimal(15+Integer.parseInt(lvl));
		}else if (type.equals("麒麟符石")) {
			id=new BigDecimal(20+Integer.parseInt(lvl));
	
		}
		
		return id;
		
	}
}
