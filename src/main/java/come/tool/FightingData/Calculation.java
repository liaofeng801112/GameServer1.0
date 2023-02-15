package come.tool.FightingData;

import org.come.tool.WriteOut;

/**
 * 计算器
 * @author Administrator
 *
 */
public class Calculation {
	private static Calculation calculation;
	public static Calculation getCalculation(){
		if (calculation==null) calculation=new Calculation();
		return calculation;}
	
	//0 抗 1忽视 2强 3伤害
	//人法计算器
	public boolean renfa(ManData mydata,ManData nomyadata,double jichu,String type){
		return renfaCalculation(jichu,mydata.getsx(2, type),mydata.getsx(1, type),nomyadata.getsx(0, type));
	}
	public boolean renfaCalculation(double jichu,double q,double hs,double k){
		jichu=(jichu+hs-k)*(1+q/100);
		if (Battlefield.random.nextInt(108)<jichu) {
			return true;
		}
		return false;
	}
	public int PTGJ(ManData mydata,ManData nomyadata,double jichu,boolean isZM,double xs){
		double five=FiveLineSystem.getSwing(mydata,nomyadata);
		double w=(1+mydata.getsx(3,"无")/100);
		if (w>five) {
			five=w;
		}
		double hs=mydata.getsx(1,TypeUtil.PTGJ)*0.85;
		double kx=nomyadata.getsx(0,TypeUtil.PTGJ)/100D;
		kx*=(1-hs/100D);
		int hurt=(int) (jichu*(1-kx)*five);
		/**伤害上限过滤*/
		if (hurt>2139999999) {hurt=1;WriteOut.addtxt("伤害上限:"+mydata.getType()+":"+mydata.getManname()+":"+mydata.getId(), 9999);}
		
		if (isZM) {
			hurt+=nomyadata.getHp()*0.1*(1-kx);
		}
		hurt*=xs;
		return hurt>1?hurt:1;
	}
	/**仙法鬼火计算器*/
	public int SMHurt(ManData mydata,ManData nomyadata,double jichu,double wg,String type,int death){
		if (type.equals("鬼火")) {
			death+=1;
			if (death>10) {death=10;}
			jichu =jichu*(1+death/16.0);
		}
		double five=FiveLineSystem.getSwing(mydata,nomyadata);
		double w=(1+mydata.getsx(3,"无")/100);
		if (w>five) {five=w;}
		double hs=mydata.getsx(1,type);
		FightingSkill skill=mydata.getSkillType(TypeUtil.TZ_ZCCG);
		if (skill!=null&&nomyadata.xzstate(TypeUtil.HS)!=null) {
			hs+=skill.getSkillhurt();
		}
		double kx=nomyadata.getsx(0,type);
		double qf=1+(mydata.getsx(2,type)/100D);
		hs=(1+(hs-kx)/100);
		
		int hurt=(int) ((jichu+mydata.getsx(3,type))*hs*qf*five);
		if (wg!=0) {
			hurt+=wg*five;
		}
		if (hurt>2139999999) {hurt=1;WriteOut.addtxt("伤害上限:"+mydata.getType()+":"+mydata.getManname()+":"+mydata.getId(), 9999);}
		return hurt>1?hurt:1;
	}
	public int xianfaCalculation(double jichu,double erwai,double q,double hs,double k,double five){
		//最终数字=基础伤害*(1+强法/100)*(1+忽视/100-对方抗性/100)*五行或者无属性加成系数*(狂暴程度/100+1.5)
		jichu=(jichu+erwai)*(1+q/100)*(1+hs/100-k/100)*five;
		jichu=jichu>1?jichu:1;
		return (int)jichu;
	}
	//魔计算器 
	//震慑hp伤害 mp伤害只算技能基础伤害	
	public double mozs(ManData mydata,ManData nomyadata,double jichu){	
			return mozsCalculation(jichu,mydata.getsx(3,"无"),mydata.getsx(2, "震慑"),mydata.getsx(1, "震慑"),nomyadata.getsx(0, "震慑"),FiveLineSystem.getSwing(mydata, nomyadata));
	}
	public double mozsCalculation(double jichu,double w,double q,double hs,double k,double five){
//		(法术震慑伤害-对方抗震+忽视抗震慑)*(1+无属性伤害加成%)*(1+强吸)*五行加成;
		if ((1+w/100)>five) {
			five=(1+w/100);
		}
		jichu=(jichu-k+hs)*(1+q/100)*five;		
		jichu=jichu<50?jichu:50;
		jichu=jichu>0?jichu:0;
		return jichu;
	}
	public double mozs2(ManData mydata,ManData nomyadata,double jichu,double q){	
		return mozsCalculation2(jichu,mydata.getsx(3,"无"),mydata.getsx(2, "震慑")+q,mydata.getsx(1, "震慑"),nomyadata.getsx(0, "震慑"),FiveLineSystem.getSwing(mydata, nomyadata));
    }
	public double mozsCalculation2(double jichu,double w,double q,double hs,double k,double five){
		if ((1+w/100)>five) {
			five=(1+w/100);
		}
		jichu=(jichu-k+hs)*(1+q/100)*five;		
		jichu=jichu>0?jichu:0;
		return jichu;
	}
	//魔加成   目前多少就是多少
	public double mofa(double jichu,ManData mydata,String type){
		   return mofaCalculation(jichu,mydata.getsx(2, type));
	}
	//魔加成   目前多少就是多少
	public double mofa(double jichu,ManData mydata,String type,double qian){
		return mofaCalculation(jichu,mydata.getsx(2, type)+qian);

	}
	public double mofaCalculation(double jichu,double qian){
		return jichu*(1+qian/100.0);
	}
	//鬼计算器 
//	鬼三尸伤害
	public int sssh(ManData mydata,ManData nomyadata,double jichu){	
		int hurt=ssshCalculation(jichu+mydata.getsx(3,"三尸"),mydata.getsx(2,"三尸"),nomyadata.getsx(0, "三尸"),FiveLineSystem.getSwing(mydata, nomyadata));
		/**伤害上限过滤*/
		if (hurt>2139999999) {hurt=1;WriteOut.addtxt("伤害上限:"+mydata.getType()+":"+mydata.getManname()+":"+mydata.getId(), 9999);}
		
		return hurt;
    }
    public int ssshCalculation(double jichu,double q,double k,double five){
	    //(三尸法术伤害+装备强三尸伤害-抗三尸)×(1+强力克)×(1+五行伤害)
	    jichu=(jichu+q-k)*five;
	    jichu=jichu>1?jichu:1;
	    return (int) jichu;
    }
	//鬼三尸回血
	public int sshx(ManData mydata,ManData nomyadata,double jichu,double sh){	
		return sshxCalculation(jichu,sh,mydata.getsx(2, "三尸回血"));
    }
    public int sshxCalculation(double jichu,double sh,double q){
        //回血量=三尸伤害×(法术回血程度+装备回血程度)×2
    	jichu=sh*2*(jichu+q)/100;
    	jichu=jichu>1?jichu:1;
    	return (int) jichu;
    }
    /**
     * 浩然正气技能
     */
    public int hrzq(ManData mydata,ManData nomyadata,double xs){	
    	int sh=hrzqCalculation(xs,mydata.getMp_z(),nomyadata.getMp_z());
    	if (nomyadata.getType()==2) {
			if (sh>=72800) {sh=72800;}
		}else {
			if (sh>=59600) {sh=59600;}
		}
		return sh;
    }
    //敌方最大法力*系数
    public int hrzqCalculation(double xs,double mymp,double nomymp){
    	nomymp=(nomymp-mymp/10);
    	if (nomymp<=0) {
    		nomymp=1;
		}
    	return (int) (nomymp*xs/100);
    }
    /**计算毒伤上限*/
    //120
    public int getzdup(ManData mydata,FightingSkill skill,double qds,ManData nomyadata){
    	double qzds=mydata.getQuality().getQzds()+qds;
    	qzds*=3.6;
		double five = FiveLineSystem.getSwing(mydata, nomyadata);
		if (five == 1) {
			five = 1 + mydata.getsx(3, "无") / 100.0D;
		}
		return (int) (skill.getSkillgain()*(1+qzds/100)*five);
//		this.skillhurt=skilllvl>3?15:skilllvl>1?12.5:10;//毒伤上限
//		this.skillgain=(lvl+sld/100)*8+(skilllvl==5?10000:skilllvl==4?12000:8000);
    }
    /**计算毒伤害*/
    public int getzdsh(ManData mydata,ManData nomyadata,FightingSkill skill,double qds){
    	qds+=mydata.getQuality().getQzds();
    	double five=FiveLineSystem.getSwing(mydata,nomyadata);
		if (five==1){
			five=(1+mydata.getsx(3,"无")/100);
		}
    	return (int) (nomyadata.getHp_z()*(skill.getSkillhurt()/100D+qds/10D)*five);
    }
}
