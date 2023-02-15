package org.come.action.exchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExchangeUtil {
	//补偿方案集合
	private static List<Compensation> compensations;
	//初始化补偿方案
	public static void init(){
		compensations=new ArrayList<>();
		//2019-1-8  2019-1-9 1546857200000-1547030000000 
		Compensation C1=new Compensation();
		C1.setCmin(new Long("1111111111111"));
		C1.setCmax(new Long("1575422950000"));
		C1.setCCDK("GXZC20191204");
		C1.setGoodId(new BigDecimal("81197"));
		compensations.add(C1);
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
	/**判断是否加入补偿人员中*/
	public static void addCompensation(BigDecimal roleId,long time){
		for (int i = compensations.size()-1; i >=0; i--) {
			Compensation compensation=compensations.get(i);
			if (compensation.contain(time)) {
				compensation.addMap(roleId);
			}
		}
	}
	/**根据CDK获取补偿方案*/
	public static Compensation getCompensation(String CDK){
		for (int i = compensations.size()-1; i >=0; i--) {
			Compensation compensation=compensations.get(i);
			if (compensation.getCCDK().equals(CDK)) {
				return compensation;
			}
		}
		return null;
	}
}
