package org.come.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.until.SplitLingbaoValue;

public class SplitStringTool {
	public static List<String> splitString( String allNpcID){
		// 所有npcID的集合
		List<String> numbers = new ArrayList<String>();
		if (allNpcID != null) {
			// 将每个ID分开
			String[] v = allNpcID.split("\\|");
			for (int i = 0; i < v.length; i++) {
				if (v[i].indexOf("-")!=-1) {
					String[] count = v[i].split("-");
					Integer start = Integer.parseInt(count[0]);
					Integer end = Integer.parseInt(count[1]);
					for (int j = start; j <= end; j++) {
						numbers.add(String.valueOf(j));
					}
				}else if (v[i].indexOf("&")!=-1) {
					String[] count = v[i].split("&");
					int sum=Integer.parseInt(count[1]);
					for (int j = 0; j < sum; j++) {
						numbers.add(count[0]);
					}
				}else {
					numbers.add(v[i]);
				}
			}
			return numbers;
		}
		return numbers;
	}
	public static List<Long> splitLong(String allNpcID){
		List<Long> longs=new ArrayList<>();
		List<String> list=splitString(allNpcID);
		for (int i = 0,size=list.size(); i < size; i++) {
			try {longs.add(Long.parseLong(list.get(i)));} catch (Exception e) {}
		}
		return longs;
	}
	/**
	 * 随机物品格式 类型1000 
	 * 物品集合  -表示连续  |分别  80080-80082|81006  80080 80081 80082 81006
	 * $表示概率
	 * &分割
	 * 193-194|80059|171|748|80042|70175-70176|80060|80070|80073-80078|80080-80082|81006-81007$90&547-555$10
	 */
	public static BigDecimal GoodRandomId(String all){
		String[] vs=all.split("&");
		int up=0;
		for (int i = 0; i < vs.length; i++) {
			String[] vss=vs[i].split("\\$");
			if (vss.length==1) {
				return RandomId(vss[0]);
			}
			double d=Double.parseDouble(vss[1])*100;
			if (d+up>SplitLingbaoValue.random.nextInt(10000)) {
				return RandomId(vss[0]);
			}
			up+=d;
			if (i==vs.length-1) {
				return RandomId(vss[0]);
			}
		}
		return null;	
	}
	//随机一个id SplitLingbaoValue
	public static BigDecimal RandomId(String allNpcID){
		List<String> numbers=splitString(allNpcID);
		if (numbers==null||numbers.size()==0) {
			return null;
		}
		return new BigDecimal(numbers.get(SplitLingbaoValue.random.nextInt(numbers.size())));		
	}
	//随机一个id SplitLingbaoValue
	public static List<String> Randoms(String allNpcID){
		// 所有npcID的集合
		List<String> numbers = new ArrayList<String>();
		if (allNpcID==null||allNpcID.equals("")) {return numbers;}
		String[] v = allNpcID.split("\\|");
		for (int i = 1; i < v.length; i++) {
			if (v[i].indexOf("-")!=-1) {
				String[] count = v[i].split("-");
				Integer start = Integer.parseInt(count[0]);
				Integer end = Integer.parseInt(count[1]);
				for (int j = start; j <= end; j++) {
					numbers.add(String.valueOf(j));
				}
			}else if (v[i].indexOf("&")!=-1) {
				String[] count = v[i].split("&");
				int sum=Integer.parseInt(count[1]);
				for (int j = 0; j < sum; j++) {
					numbers.add(count[0]);
				}
			}else {
				numbers.add(v[i]);
			}
		}
		int size=Integer.parseInt(v[0]);
		List<String> ids = new ArrayList<String>();
	    for (int i = 0; ids.size()<size; i++) {
			String id=numbers.get(SplitLingbaoValue.random.nextInt(numbers.size()));
			if (!ids.contains(id)) {
				ids.add(id);	
			}
			if (i>1200) {break;}
		}
		return ids;
	}
}
