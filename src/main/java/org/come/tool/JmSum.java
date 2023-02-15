package org.come.tool;

import java.util.Random;

public class JmSum {
    static final int HHYZ=1341;
	/**z->m 加密思路 前一位 随机添加一个 1-9的数  随机数余3==0验证个位加4 1验证十位加2 2验证个位加1*/
	public static long ZM(long V){
		V+=HHYZ;
		boolean is=V<0;if (is) {V=-V;}
		int s=random.nextInt(9)+1;
		int w=0;
		long zhi=s;
		if (V!=0) {
			w=(int) Math.log10(V);
		}
		if (s>>2==0) {
			s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}
			int yzs=(int) ((V+4)%10);
			for (int i=w;i>=0;i--) {
				zhi*=10;zhi+=V%10;
				if (s==i) {zhi*=10;zhi+=yzs;}
				V/=10;
			}
		}else if (s>>2==1) {
			s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}
			int yzs=0;
			if (V>=10) {yzs=(int)(((V/10)+2)%10);}
			else {yzs=(int)((V+2)%10);}
			for (int i=w;i>=0;i--) {
				zhi*=10;zhi+=V%10;
				if (s==i) {zhi*=10;zhi+=yzs;}
				V/=10;
			}
		}else if (s>>2==2) {
			s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}
			int yzs=(int) ((V+1)%10);
			for (int i=w;i>=0;i--) {
				zhi*=10;zhi+=V%10;
				if (s==i) {zhi*=10;zhi+=yzs;}
				V/=10;
			}
		}
		if (is) {zhi=-zhi;}
		return zhi;
	}
	/**m->z*/
    public static long MZ(long V){
    	if (V==0) {return 0;}
    	boolean is=V<0;
    	if (is) {V=-V;}
    	long zhi=0;
    	int w=(int) Math.log10(V);
    	int s=(int) (V/(long)Math.pow(10,w));
    	w-=2;
    	if (s>>2==0) {
    		s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}w++;
			int yzs=0;
			int yzv=0;
			for (int i=0;i<=w;i++) {
				if (s==i) {yzs=(int) (V%10);yzs-=4;if (yzs<0){yzs+=10;}}
				else {if (i==w) {yzv=(int) (V%10);}zhi*=10;zhi+=V%10;}
			    V/=10;
			}
			if (yzs!=yzv) {return -1;}
    	}else if (s>>2==1) {
			s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}w++;
			int yzs=0;
			int yzv=0;
			for (int i=0;i<=w;i++) {
				if (s==i) {yzs=(int) (V%10);yzs-=2;if (yzs<0){yzs+=10;}}
				else {
					if (w>=2) {if (i==w-1) {yzv=(int) (V%10);}}
					else {if (i==w) {yzv=(int) (V%10);}}
					zhi*=10;zhi+=V%10;
				}
			    V/=10;
			}
			if (yzs!=yzv) {return -1;}
    	}else if (s>>2==2) {
    		s+=3;
			if (s==3) {s=1;}
			else if (s==10){s=5;}
			if (w!=0) {s%=w;}else {s=0;}w++;
			int yzs=0;
			int yzv=0;
			for (int i=0;i<=w;i++) {
				if (s==i) {yzs=(int) (V%10);yzs-=1;if (yzs<0){yzs+=10;}}
				else {if (i==w) {yzv=(int) (V%10);}zhi*=10;zhi+=V%10;}
			    V/=10;
			}
			if (yzs!=yzv) {return -1;}
    	}
    	if (is) {zhi=-zhi;}
		zhi-=HHYZ;
    	return zhi;
	}
    private static Random random=new Random();
}
