package come.tool.Mixdeal;


import java.util.HashMap;
import java.util.Map;

import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.ManData;

public class CreepsMixdeal {
//	剑:1 扇:2 锤:3 斧头:4
//	拳套:5 书:6 棍:7 鞭:8
//	钩:9 刀:10 双环:11 枪:12
//	幡:13 爪:14 浮尘:15 飘带:16
//	灯笼:17 弓:18
   /**根据物品皮肤获取武器皮肤*/
	public static int good(int id){
		if ((id>=1600&&id<=1615)||id==6100||id==7006) {return 1;}
		if ((id>=1400&&id<=1415)||id==6106||id==7012) {return 2;}
		if ((id>=1100&&id<=1115)||id==6124||id==7021) {return 3;}
		if ((id>=1200&&id<=1215)||id==6122||id==7022) {return 4;}
		if ((id>=2200&&id<=2215)||id==6109||id==7016) {return 5;}
		if ((id>=2400&&id<=2415)||id==6119||id==7020) {return 6;}
		if ((id>=1300&&id<=1315)||id==6103||id==7009) {return 7;}
		if ((id>=1700&&id<=1715)||id==6102||id==7008) {return 8;}
		if ((id>=2100&&id<=2115)||id==7013||id==6105) {return 9;}
		if ((id>=1000&&id<=1015)||id==6118||id==7007) {return 10;}
		if (id==7019||id==6120) {return 11;}
		if ((id>=1800&&id<=1815)||id==6104||id==7011) {return 12;}
		if ((id>=1900&&id<=1915)||id==6108||id==7017) {return 13;}
		if ((id>=2200&&id<=2215)||id==6109||id==7010) {return 14;}
		if ((id>=1500&&id<=1515)||id==7014||id==6117) {return 15;}
		if ((id>=2000&&id<=2015)||id==6107||id==7015) {return 16;}
		if (id==7018||id==6121) {return 17;}
		if ((id>=2617&&id<=2632)||id==6125||id==7023) {return 18;}
		return 0;  
	}
    public static String getL_LL(String model){
		if (model.indexOf("24001")!=-1) {
			return "51101";
		}else if (model.indexOf("24002")!=-1) {
			return "51102";
		}else if (model.indexOf("24003")!=-1) {
			return "51103";
		}else if (model.indexOf("24004")!=-1) {
			return "52101";
		}else if (model.indexOf("24005")!=-1) {
			return "52102";
		}else if (model.indexOf("24006")!=-1) {
			return "52103";
		}
    	return model; 	
    }
//    1511	分光化影
//    1512	天魔解体
//    1513	小楼夜哭
//    1514	青面獠牙
//    1515	浩然正气
//    1516	隔山打牛
//    1517	乘风破浪
//    1518	霹雳流星
//    1519	大海无量
//    1520	祝融取火
    static Map<Integer,String> map;
    static{
    	map=new HashMap<>();
    	map.put(1511, "分光化影");
    	map.put(1512, "天魔解体");
    	map.put(1513, "小楼夜哭");
    	map.put(1514, "青面獠牙");
    	map.put(1515, "浩然正气");
    	map.put(1516, "隔山打牛");
    	map.put(1517, "乘风破浪");
    	map.put(1518, "霹雳流星");
    	map.put(1519, "大海无量");
    	map.put(1520, "祝融取火");
    }
    /**添加内丹技能*/
    public static void addNeiDanSkill(ManData data,int id){
    	String goodname=map.get(id);
    	if (goodname==null) {return;}
    	if (goodname.equals("红颜白发")||goodname.equals("梅花三弄")||goodname.equals("开天辟地")||goodname.equals("万佛朝宗")) {data.neidang("tj", 4);}
		else if (goodname.equals("分光化影")||goodname.equals("天魔解体")||goodname.equals("小楼夜哭")||goodname.equals("青面獠牙")) {data.neidang("mj", 4);}
		else if (goodname.equals("乘风破浪")||goodname.equals("霹雳流星")||goodname.equals("大海无量")||goodname.equals("祝融取火")) {data.neidang("xl", 4);}
		else {data.neidang("rj",  4);}
    	if (id>=1511&&id<=1520) {
			FightingSkill skill=accessNedanMsg(goodname,100,3,100,3,2000000,data.getMp_z()/4);
			data.addSkill(skill);
		}
    }
    //非抗性型内丹获取几率、伤害率的方法
  	public static FightingSkill accessNedanMsg(String goodsname,int nddj,int ndzscs,int zhsdj,int zhszscs,long zhsqm,int zhsmpz){	
  		if(goodsname.equals("浩然正气")){
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
  			double ndcd=Math.floor((zhsdj*zhsdj*0.2/(zhsmpz*1+1)+ndjl)*10000)/10000;
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndcd*10000)/100D);
  		}else if(goodsname.equals("隔山打牛")){
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
  			double ndcd=Math.floor((zhsdj*zhsdj*0.2/(zhsmpz*1+1)+ndjl*3)*1000)/1000;	
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndcd*10000)/100D);
  		}else if(goodsname.equals("天魔解体")){
  			double ndjl = 0;
  			if(zhszscs==0)ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4000)*1000)/1000+0.01;
  			else ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/3755)*1000)/1000+0.01;
  			double ndhq=Math.floor(ndjl*(zhsdj*zhsdj*0.15/(zhsmpz*1+0.01)+0.2)*1000)/1000;
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
  		}else if(goodsname.equals("分光化影")){
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4000)*1000)/1000+0.01;
  			double ndhq=Math.floor(ndjl*(zhsdj*zhsdj*0.15/(zhsmpz*1+0.01)+0.2)*1000)/1000;
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
  		}else if(goodsname.equals("小楼夜哭")){
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj/206600.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4170)*1000)/1000+0.01;
  			double ndhq=ndjl*0.3;
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
  		}else if(goodsname.equals("青面獠牙")){
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj/698000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/7500)*1000)/1000+0.01;
  			double ndhq=ndjl*0.7;
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
  		}else {
  			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000004;
  			double ndcd=Math.floor(296.1572+0.0002364957*Math.pow(zhsmpz,1.57));
  			return new FightingSkill(goodsname, Math.round(ndjl*10000)/100D, ndcd);
  		}
  	}
	//获得转生系数的方法
	public static double xstz(int zhs_zscs,int nd_zscs){
		if(zhs_zscs*nd_zscs==1)return 1.04;
		else if(zhs_zscs*nd_zscs==4)return 1.071;
		else if(zhs_zscs*nd_zscs==6)return 1.073;
		else if(zhs_zscs*nd_zscs==9)return 1.09;
		else return 1;
	}
}
