package org.come.action.lottery;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.QuackGameBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class PIXIU {
    private static PIXIU pixiu;
	public static PIXIU getPixiu() {
		if (pixiu==null) {
			pixiu=new PIXIU();
		}
		return pixiu;
	}
	//今日守护
	private int[] awards;
	private String sendAwards;//用来发送给前端今日奖项的缓存
	//抽奖池 记录所有可以被选中的召唤兽
	private int[] lotterys;
	//额外
	private int[] extras;
	private Map<Integer,Integer> itDouble;
	//奖池 记录盈亏
//	private long prizePool;//
	private long[] baseAwards;
	private Random random;
//	private Object object=new Object();
//	private static long BASE=1250000;
	private static long BASE=800000;
	private static long DOOR=BASE*20;
	private static long COST=BASE*5;
	public PIXIU() {
		// TODO Auto-generated constructor stub
		random=new Random();
		extras=LotteryUtil.getint1();
		itDouble=LotteryUtil.getmap1();
		lotterys=LotteryUtil.getint2();
		resetAwards();
		initAward();
	}
	/**抽奖*/
	public void lottery(ChannelHandlerContext ctx){
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {
			return;
		}
//		addPrizePool(COST);
		QuackGameBean bean=new QuackGameBean();
		bean.setType(2);
		StringBuffer buffer=new StringBuffer();
		List<Integer> zj=null;
		for (int i = 0; i < 20; i++) {
			int id=lotterys[random.nextInt(lotterys.length)];
			if (isAward(id)) {
				if (zj==null) {
					zj=new ArrayList<>();
					zj.add(id);	
				}else if (!zj.contains(id)) {
					zj.add(id);	
				}
			}else if(i<=4&&(zj==null||zj.size()<=1)){
				for (int j = 0; j<=i; j++) {
					id=lotterys[random.nextInt(lotterys.length)];
					if (isAward(id)) {
						if (zj==null) {
							zj=new ArrayList<>();
							zj.add(id);
							break;
						}else if (!zj.contains(id)) {
							zj.add(id);	
							break;
						}
					}
				}
			}
			
			buffer.append(id);
			buffer.append("|");
		}
		int size=zj==null?0:zj.size();
		long money=getBaseAward(size);
		//获取倍数
		int extra=getExtra();
		buffer.append(extra);
		money*=getDS(extra);
//		addPrizePool(-money);
		MonitorUtil.getLotteryM().add(money);
		bean.setPetmsg(buffer.toString());
		bean.setMoney(new BigDecimal(money));
		loginResult.setGold(loginResult.getGold().add(bean.getMoney()));

		MonitorUtil.getMoney().addD(money, 2);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().getfivemsgAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
        if (money>=DOOR) {
        	hh(loginResult.getRolename(),money,size,extra);//世界喊话
		}
	}
	public static void main(String[] args) {
		long size=5000000;
		for (int i = 0; i < 5000000; i++) {
			getPixiu().lottery(null);		
		}
//		System.out.println(getPixiu().prizePool);
//		System.out.println(getPixiu().prizePool/size);
		System.out.println("2个:"+a2);
		System.out.println("3个:"+a3);
		System.out.println("4个:"+a4);
		System.out.println("5个:"+a5);
		System.out.println((double)(a2+a3+a4+a5)/size);
	}
	/**世界喊话*/
	public void hh(String role,long money,int size,int extra){
		StringBuffer buffer=new StringBuffer();
		buffer.append("#c00ffff");
		buffer.append(role);
		buffer.append("#Y召唤兽");
		buffer.append(size);
		buffer.append("种守护召唤兽,进入宝库");
		if (size==5) {
			buffer.append("牛刀小试,");
		}else if (size==4) {
			buffer.append("如鱼得水,");
		}else if (size==3) {
			buffer.append("大显身手,");
		}else {
			buffer.append("炉火纯青,");
		}
		String v=getExtra(extra);
		if (v!=null) {
			buffer.append("兼有神兽");
			buffer.append(v);
			buffer.append("相助,");	
		}
		buffer.append("喜获得#G");
		buffer.append(money);
		buffer.append("银两");
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**获取外围别名*/
	public String getExtra(int extra){
		switch (extra) {
		case 400107:return "垂云叟";
		case 400108:return "范式之魂";
		case 400109:return "浪淘沙";
		case 400110:return "五叶";
		case 400111:return "颜如玉";
		case 400127:return "白泽";
		case 400121:return "画中仙";
		case 400120:return "年";
		}
		return null;
	}
	/**获取外围奖*/
	public int getExtra(){
		if (random.nextInt(25)==0) {
			if (random.nextInt(25)<=7) {
				if (random.nextInt(5)==0) {
					return extras[random.nextInt(extras.length-1)];		
				}else {
					return extras[random.nextInt(5)];
				}			
			}else {
				return extras[extras.length-1];
			}
		}
		return 0;
	}
	/**获取倍数*/
	public int getDS(int extra){
		Integer bs=itDouble.get(extra);
		if (bs!=null) {return bs;}
		return 1;
	}
	static int a2=0;
	static int a3=0;
	static int a4=0;
	static int a5=0;
	/**获取个数中奖的基础金额*/
	public long getBaseAward(int sum){
		if (sum>=2) {
			switch (sum) {
			case 2:a2++;break;
			case 3:a3++;break;
			case 4:a4++;break;
			case 5:a5++;break;
			}
		}
		sum-=2;if (sum<0) {return 0;}
		return baseAwards[sum];
	}
	/**判断是否为今日守护*/
	public boolean isAward(int id){
		for (int i = 0; i < awards.length; i++) {
			if (awards[i]==id) {
				return true;
			}
		}
		return false;
	}
	/**替换今日守护*/
	public void resetAwards(){
		awards=LotteryUtil.getint3(lotterys);
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < awards.length; i++) {
			if (i!=0) {
				buffer.append("|");
			}
			buffer.append(awards[i]);
		}
		QuackGameBean bean=new QuackGameBean();
		bean.setType(1);
		bean.setMoney(null);
		bean.setPetmsg(buffer.toString());
		sendAwards=Agreement.getAgreement().getfivemsgAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
	}
	/**初始化基础奖励*/
	public void initAward(){
		baseAwards=new long[4];
		baseAwards[0]=BASE*4;
		baseAwards[1]=BASE*8;
		baseAwards[2]=BASE*40;
		baseAwards[3]=BASE*100;
	}
	public String getSendAwards() {
		return sendAwards;
	}
//	public long getPrizePool() {
//		return prizePool;
//	}
//	public void addPrizePool(long prizePool) {
//		synchronized(object){
//			this.prizePool+=prizePool;
//		}
//	}
	
}
