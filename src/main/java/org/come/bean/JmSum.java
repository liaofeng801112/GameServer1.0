package org.come.bean;


public class JmSum {
    static final int HHYZ=9707;
	/**z->m*/
	public static long ZM(long V){
		return V-HHYZ;
	}
	/**m->z*/
    public static long MZ(long V){
    	return V+HHYZ;
	}
}
