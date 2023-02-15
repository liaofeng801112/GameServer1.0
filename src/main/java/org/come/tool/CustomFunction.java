package org.come.tool;

import java.math.BigDecimal;

public class CustomFunction {

	/**递增函数*/
	public static double XS(long v,double xs){
		if (v<=0) {return 0;}
		while (v/16>0) {
			v=v/16;
			xs*=1.86;
		}
		xs=xs*(1+0.86*(v/16.0));
		return new BigDecimal(xs).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static void main(String[] args) {
//		long qm=500000;//23.8
//		long qm=1000000;//25
//		long qm=2000000;//26.3
		long qm=100000000;//32.2
		System.out.println(45+CustomFunction.XS(qm,0.8));
//		System.out.println(new BigDecimal(Math.pow(qm, 0.2)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
	}
}
