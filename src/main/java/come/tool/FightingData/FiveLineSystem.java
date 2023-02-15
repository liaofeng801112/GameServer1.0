package come.tool.FightingData;

/**
 * 五行系统
 * @author Administrator
 *
 */
public class FiveLineSystem {
    	
	public static void main(String[] args) {
		double[] myline=new double[]  {0  ,0  ,0  ,0,  0  };
		double[] myforce=new double[] {0  ,0  ,0  ,0  ,200  };
		double[] nomyline=new double[]{0  ,0  ,0,0  ,100};
		System.out.println(fiveline(myline, nomyline, myforce));
		double xs=fivenexus(myline, nomyline);
		System.out.println(xs);
		System.out.println(xs*forcenexus(myforce, nomyline));
		
		
	}
//  金克木，木克土，土克水，水克火，火克金      金木土水火
	public static double getSwing(ManData mydata,ManData nomydata){
		double[] myline=shuxing(mydata,null, 0);
		double[] nomyline=shuxing(nomydata,null, 0);
		double[] myforce=shuxing(mydata,nomydata, 1);
		return fiveline(myline, nomyline, myforce);
	}
	//获取指定属性集合 0获取金木土水火 1获取强力克金木土水火
	public static double[] shuxing(ManData data,ManData nomydata,int type){
		double[] ds=new double[5];
		Ql roleQuality=data.getQuality();
		if (roleQuality!=null) {
			if (type==0) {
				ds[0]+=roleQuality.getRolewxj();
				ds[1]+=roleQuality.getRolewxm();
				ds[2]+=roleQuality.getRolewxt();
				ds[3]+=roleQuality.getRolewxs();
				ds[4]+=roleQuality.getRolewxh();
				if (data.xzstate("水")!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]/=2;}
					ds[3]+=50;
				}else if (data.xzstate("金")!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]/=2;}
					ds[0]+=50;
				}else if (data.xzstate("木")!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]/=2;}
					ds[1]+=50;
				}else if (data.xzstate("土")!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]/=2;}
					ds[2]+=50;
				}else if (data.xzstate("火")!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]/=2;}
					ds[4]+=50;
				}else if (data.xzstate(TypeUtil.BB_WYJK)!=null) {
					for (int i = 0; i < ds.length; i++) {ds[i]=20;}
				}
			}else {
				ds[0]=roleQuality.getRolewxqkj();
				ds[1]=roleQuality.getRolewxqkm();
				ds[2]=roleQuality.getRolewxqkt();
				ds[3]=roleQuality.getRolewxqks();
				ds[4]=roleQuality.getRolewxqkh();
				double xs=roleQuality.getQ_qk();
				if (nomydata!=null&&nomydata.getQuality()!=null) {
					xs-=nomydata.getQuality().getK_qk();
					FightingSkill skill=nomydata.getAppendSkill(9405);
					if (skill!=null) {xs-=skill.getSkillhurt();}
					AddState addState=nomydata.xzstate(TypeUtil.BB_E_BLBJ);
					if (addState!=null) {ds[3]-=addState.getStateEffect();}
				}
				ds[0]+=xs;ds[1]+=xs;ds[2]+=xs;ds[3]+=xs;ds[4]+=xs;				
			}	
		}else {
			ds[0]=0;
			ds[1]=0;
			ds[2]=0;
			ds[3]=0;
			ds[4]=0;
		}
		return ds;		
	}
	//五行伤害和强力克加成
	public static double fiveline(double[] myline,double[] nomyline,double[] myforce) {
		return forcenexus(myforce, nomyline)*fivenexus(myline, nomyline);	
	}
//  获取强力克的差值
	public static double forcenexus(double[] myforce,double[] nomyline){
		double cha=1;
		for (int i = 0; i < myforce.length; i++) {
			if (myforce[i]!=0&&nomyline[i]!=0) {
				cha+=myforce[i]*nomyline[i]*0.00008;
			}
		}
		return cha;	
	}
//	获取五行相克的差值
	public static double fivenexus(double[] myline,double[] nomyline){
		double cha=0;
		for (int i = 0; i < myline.length; i++) {
			if (myline[i]!=0) {
				if (nomyline[i<4?i+1:0]!=0) {
					cha+=myline[i]*nomyline[i<4?i+1:0]/100;
				}
			}
		}
        for (int i = 0; i < nomyline.length; i++) {
        	if (nomyline[i]!=0) {
        		if (myline[i<4?i+1:0]!=0) {
					cha-=nomyline[i]*myline[i<4?i+1:0]/100;
				}
			}
		}
		return cha/100*0.4+1;	
	}
}
