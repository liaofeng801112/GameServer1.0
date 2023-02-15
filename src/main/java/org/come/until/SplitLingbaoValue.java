package org.come.until;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import org.come.entity.Lingbao;
import org.come.server.GameServer;


//随机灵宝属性
public class SplitLingbaoValue {
	/** 落宝天赋列表*/
	static String[] v={"契合","闪现","活跃","抵抗","敏捷","归根","回灵","灵动","回光"};
	/** 攻击天赋列表*/
	static String[] v1={"契合","闪现","活跃","抵抗","敏捷","归根","回灵","灵动","回光","根骨增强","力量增强","灵性增强","敏捷增强"};
	/** 辅助天赋列表*/    	
	static String[] v2={"契合","闪现","活跃","抵抗","敏捷","归根","回灵","灵动","回光","根骨回生","力量回生","灵性回生","敏捷回生"};
	public static Random random=new Random();
	public static Lingbao addling(String ling,BigDecimal roleid){
		 Lingbao bean=GameServer.getLingbao(ling);
		 double min=0;
		 double max=0;
		 double sp;
		 double ap;
		 //初始化灵宝属性 速度 回复 伤害
		 //初始化速度
		 String[] v=bean.getBaospeed().split("-");	
		 min=Double.parseDouble(v[0]);
		 max=Double.parseDouble(v[1]); 
		 sp=getDouble(min, max, 0);
		 bean.setBaospeed((sp+"").split("\\.")[0]); 
		 v=(bean.getBaoreply()+"").split("-");
        if (v.length>1) {
       	 min=Double.parseDouble(v[0]);
   		 max=Double.parseDouble(v[1]); 
   		 ap=getDouble(min, max, 3);
   		 bean.setBaoreply(ap+"");
   		 bean.setBaoquality(BaoQuality(sp, ap));
		}
        v=(bean.getBaoap()+"").split("-");
        if (v.length>1) {
       	 min=Double.parseDouble(v[0]);
   		 max=Double.parseDouble(v[1]);
   		 ap=getDouble(min, max, 3);
   		 bean.setBaoap(ap+"");	
   		 bean.setBaoquality(BaoQuality(sp, ap));
		}
        if (bean.getBaoquality()==null||bean.getBaoquality().equals("")) {bean.setBaoquality(BaoQuality(sp,0.13));}
        bean.setLingbaolvl(new BigDecimal(1));
        bean.setLingbaoexe(new BigDecimal(0));
        bean.setTianfuskill(tianfu(bean.getGethard(), bean.getBaotype()));
        bean.setKangxing(kangxing(1));
        bean.setRoleid(roleid);
		bean.setOperation(null);
		AllServiceUtil.getLingbaoService().insertLingbao(bean);
		return bean;
	}
	static String[] fabaos=new String[]{"银索金铃","将军令","大势锤","七宝玲珑塔","黑龙珠","幽冥鬼手","大手印","绝情鞭"
			,"情网","宝莲灯","金箍儿","番天印","锦襕袈裟","白骨爪","化蝶"};
	/**获得法宝*/
	public static Lingbao addfa(long id,BigDecimal roleid){
		id-=69001;
		if (id>fabaos.length) {
			return null;
		}
		String fa=fabaos[(int) id];
		Lingbao lingbao=new Lingbao();
		lingbao.setBaoname(fa);
		lingbao.setBaotype("法宝");	
		lingbao.setBaoquality("把玩");
		lingbao.setLingbaolvl(new BigDecimal(1));
		lingbao.setLingbaoexe(new BigDecimal(0));
		lingbao.setBaoactive(0);
		lingbao.setBaospeed("0"); 
		lingbao.setBaoreply("0");
		lingbao.setBaoshot("0");
		lingbao.setBaoap("0");
		lingbao.setResistshot("0");
		lingbao.setAssistance("0");
		lingbao.setSkin((101+id)+"");
		lingbao.setKangxing(kangxing(1));
		lingbao.setRoleid(roleid);
		lingbao.setOperation(null);
		AllServiceUtil.getLingbaoService().insertLingbao(lingbao);
		return lingbao;	
	}
	  /**
     * 随机生成指定精确度的小数
     * @return
     */
    public static double getDouble(double min,double max,int type){
    	StringBuffer a=new StringBuffer();for (int i = 0; i < type; i++) {a.append("0");}
        max-=min;max=max/4*random.nextInt(5);
        DecimalFormat df=new DecimalFormat("#."+a);
        double b=Double.valueOf(df.format(random.nextDouble()*max+min));
        return b;
    }
    /**
     * 把玩   
     * 贴身   
     * 珍藏 
     * 无价       0.01  0.05  0.9  0.13 0.17 0.20           
     * 传世      80  60  40  20   0   sp-20  低     -40  -60 -80 -100
     * 根据初始化的灵宝属性获取灵宝的品质
     * @param args
     */
    public static String BaoQuality(double sp,double ap){
		sp=(int)sp/10+2;
		if (sp<1) {
			sp=-(sp-2);
		}
		ap=(int)((ap-0.01)/0.02)+1;
		int zhilian=(int) ((ap+sp)/4+0.5);
		switch (zhilian) {
		case 1:
			return "把玩";
		case 2:
			return "贴身";
		case 3:
			return "珍藏";
		case 4:
			return "无价";
		case 5:
			return "传世";
		}
		return "把玩";
    } 
    /**
     * 随机天赋技能
     */
    public static String tianfu(String dengji,String type){
		//根据类型获取能获得的技能列表
    	StringBuffer tianfu=new StringBuffer();
    	if (type.equals("攻击")) {
    		if (!dengji.equals("高")) {
    			tianfu.append("低级"+suiji(v1));				
			}else {
				tianfu.append("高级"+suiji(v1));
			}
    	}else if (type.equals("辅助")) {
    		if (!dengji.equals("高")) {
    			tianfu.append("低级"+suiji(v2));				
			}else {
				tianfu.append("高级"+suiji(v2));
			}
		}else {
			if (!dengji.equals("高")) {
    			tianfu.append("低级"+suiji(v));				
			}else {
				tianfu.append("高级"+suiji(v));
			}
		}
    	if (!type.equals("低")) {
    		String vs="高级"+suiji(v);
    		while (vs.equals(tianfu.toString())) {
    			vs="高级"+suiji(v);
			}
    		tianfu.append("|"+vs);
		}
    	return tianfu.toString();
    }
    /**
     * 随机抗性属性
     * 根据等级设定最低最大值
     */
    static String[] vk={"抗物理","抗震慑","抗风","抗雷","抗水","抗火","抗混乱","抗昏睡","抗封印","抗中毒","抗遗忘","抗鬼火"};
    public static String kangxing(int lvl){
		StringBuffer buffer=new StringBuffer();
		buffer.append(suiji(vk));
		buffer.append("=");
		int fudu=(lvl-1)/40+1;
		double a=getDouble((fudu-1)*2+0.1, fudu*2+0.1, 1);
		buffer.append(a);
		return buffer.toString();
    }
    /**
     * 获取字符串数组中随机一个数
     */
    public static String suiji(String[] v){return v[random.nextInt(v.length)];}
}
