package come.tool.newTask;

import org.come.server.GameServer;

public class RandomDaily {
	static String[] hours = "子,丑,寅,卯,辰,巳,午,未,申,酉,戌,亥".split(",");
	static String[] min = "三,二".split(",");
	static String[] names = "万,不,严,乐,予,于,云,仁,任,佑,何,俞,倪,值,假,傻,公,冯,凤,刀,千,华,卫,厨,史,名,吕,吴,周,唐,喻,图,土,地,大,天,奚,如,姜,娃,孔,孙,安,小,尤,岑,帮,常,廉,张,强,彭,意,慧,戚,才,搜,方,施,时,旺,昌,春,曹,有,朱,李,村,杨,柏,柱,柳,栓,楚,殷,毕,水,汤,沈,滕,潘,火,炮,点,犬,王,痴,瞎,福,秦,窦,章,级,罗,翻,聚,花,苏,苗,范,葛,蒋,薛,藏,虎,袁,褚,讨,许,谢,财,费,贺,赵,路,转,辉,通,邬,邹,郎,郑,郝,酆,金,钱,陈,陶,雷,震,韦,韩,马,魏,鲁,鲍".split(",");
	
	public static String RandomName1(String type){	
		String name = "";
		String hour  = hours[GameServer.random.nextInt(hours.length-1)];
		String minit = min[GameServer.random.nextInt(min.length-1)];
		int[] a =  randomArray(0,names.length-1,2);
		for (int i = 0; i < a.length; i++) {name = name+ names[a[i]];}
		return hour+"时"+minit+"刻"+name+type;	
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
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(GameServer.random.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}	
}
