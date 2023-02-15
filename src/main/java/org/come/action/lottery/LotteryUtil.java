package org.come.action.lottery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.come.tool.SplitStringTool;

public class LotteryUtil {
    /**获取神兽 和 金老鼠*/
    public static int[] getint1(){
    	int[] a=new int[9];
    	a[0]=400107;//垂云叟
    	a[1]=400108;//范式之魂
    	a[2]=400109;//浪淘沙
    	a[3]=400110;//五叶
    	a[4]=400111;//颜如玉
    	a[5]=400127;//白泽
    	a[6]=400121;//画中仙
    	a[7]=400120;//年
    	a[8]=400151;//金老鼠
		return a;
    }
    /**获取神兽 和 金老鼠*/
    public static Map<Integer,Integer> getmap1(){
    	Map<Integer,Integer> map=new HashMap<>();
    	int[] a=getint1();
    	for (int i = 0; i < a.length; i++) {
    		if (i<5) {
    			map.put(a[i], 5);
			}else if (i==5) {
				map.put(a[i], 10);
			}else if (i==6) {
				map.put(a[i], 20);
			}else if (i==7) {
				map.put(a[i], 30);
			}else if (i==8) {
				map.put(a[i], 0);
			}
		}
		return map;
    }
	/**获取奖项*/
	public static int[] getint2(){
		String msg="200093-200094|200113|200125|200127|200139|200141|200144|200146|200152|200153|200156|200158|200163-200165|300031-300033|300064|300065|300205|300226|400001|400003|400007|400009|400011|400020|400022|400029|400030|400032|400033|400036|400038|400040|400041|400042|400045|400047-400051|400053|400059|400061|400064|400065|400066|400071-400073|400077|400078|400080|400081|400088-400092|400094|400095|400099|400103|400105-400111|400120|400121|400127|400132|400135|400137|400138|400140-400144|400146|400148|400149|400151|400154|400160|400171";
		List<String> vs=SplitStringTool.splitString(msg);
		int[] a=getint1();
		for (int i = 0; i < a.length; i++) {
			vs.remove(a[i]+"");
		}
		int size=50;
		if (size>vs.size()) {
			size=vs.size();
		}
		a=new int[size];
		for (int i = 0; i < size; i++) {
			a[i]=Integer.parseInt(vs.get(i));
		}
		return a;
	}
	/**随机今天的守护*/
	public static int[] getint3(int[] v){
		Random random=new Random();
		int[] a=new int[5];
		boolean is=true;
		s:while (is) {
			a[0]=v[random.nextInt(v.length)];
			a[1]=v[random.nextInt(v.length)];
			a[2]=v[random.nextInt(v.length)];
			a[3]=v[random.nextInt(v.length)];
			a[4]=v[random.nextInt(v.length)];
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a.length; j++) {
					if (i!=j&&a[i]==a[j]) {
						continue s;
					}
				}
			}
			is=false;
		}
		return a;	
	}
}
