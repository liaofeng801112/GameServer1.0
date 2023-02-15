package org.come.action.monitor;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Goodstable;
import org.come.entity.Record;
import org.come.tool.ReadExelTool;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Role.PartJade;

/**全局物资监管*/
public class MonitorUtil {

	//刮刮乐
	private static MPoint lotteryM;
	//摆摊
	private static MPoint stallM;
	//前端掉落
	private static MPoint dropQM1;//战斗任务掉落
	private static MPoint dropQM2;//robot掉落
	//后端掉落
	private static MPoint dropHM;
	//抽奖
	private static PartJade dropCJ;
	//大宗金额流动
	private static PartJade moneyM;
	//七十二变系统消耗
	private static MPoint card;
	private static long UpMoney=1000000000L;
	private static long MAXBY=50000000L;
	private static MonitorMoney money;
	//玩家操作行为
	private static ConcurrentHashMap<BigDecimal,MonitorRole> sumMap;
	static{
		sumMap=new ConcurrentHashMap<>();
		lotteryM=new MPoint();
		stallM=new MPoint();
		dropQM1=new MPoint();
		dropQM2=new MPoint();
		dropHM=new MPoint();
		dropCJ=new PartJade(0, 0);
		moneyM=new PartJade(0, 0);//1交易  2给予  3摆摊
		card=new MPoint();
		try {
			String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "money.txt");
			if (text==null||text.equals("")) {
				money=new MonitorMoney();
			}else {
				money=GsonUtil.getGsonUtil().getgson().fromJson(text,MonitorMoney.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (money==null) {
			money=new MonitorMoney();
		}
	}	
	public static void addCJ(int type,int v){
		dropCJ.setJade(type,v);
	}
	/**金币流动监控*/
	public static boolean isUpMoney(int type,long v){
		v=Math.abs(v);
		if (v>=UpMoney) {
			moneyM.setJade(type,1);
			return true;
		}
		return false;
	}
	/**七十二变系统消耗*/
	public static void addCard(int type,int v){
		if (type==0) {
			card.setKey(card.getKey()+v);
		}else {
			card.setValue(card.getValue()+v);
		}
		
	}
	public static MPoint getLotteryM() {
		return lotteryM;
	}
	public static void setLotteryM(MPoint lotteryM) {
		MonitorUtil.lotteryM = lotteryM;
	}
	public static MPoint getStallM() {
		return stallM;
	}
	public static void setStallM(MPoint stallM) {
		MonitorUtil.stallM = stallM;
	}
	
	public static MPoint getDropQM1() {
		return dropQM1;
	}
	public static void setDropQM1(MPoint dropQM1) {
		MonitorUtil.dropQM1 = dropQM1;
	}
	public static MPoint getDropQM2() {
		return dropQM2;
	}
	public static void setDropQM2(MPoint dropQM2) {
		MonitorUtil.dropQM2 = dropQM2;
	}
	public static MPoint getDropHM() {
		return dropHM;
	}
	public static void setDropHM(MPoint dropHM) {
		MonitorUtil.dropHM = dropHM;
	}
	public static MonitorMoney getMoney() {
		return money;
	}
	public static void setMoney(MonitorMoney money) {
		MonitorUtil.money = money;
	}
	/**不自增*/
	public static int getBSumBase(BigDecimal roleID){
		MonitorRole role=sumMap.get(roleID);
		if (role==null) {return 0;}
		return role.getBSum();
	}
	/**获取战斗次数*/
	public static int getBSum(BigDecimal roleID){
		MonitorRole role=sumMap.get(roleID);
		if (role==null) {
			role=new MonitorRole();
			sumMap.put(roleID, role);
		}
		return role.addBSum();
	}
	/**获取驯养次数*/
	public static int getSum(BigDecimal roleID,int type){
		MonitorRole role=sumMap.get(roleID);
		if (role!=null) {
			return type==0?role.getKSum():role.getXSum();
		}
		return 0;
	}
	public static void addSum(BigDecimal roleID,int type){
		MonitorRole role=sumMap.get(roleID);
		if (role==null) {
			role=new MonitorRole();
			sumMap.put(roleID, role);
		}
		if (type==0) {role.addKSum();}
		else {role.addXSum();}
	}
	/**添加绑玉*/
	public static int addBY(BigDecimal roleID,int num,long moeny){
		MonitorRole role=sumMap.get(roleID);
		if (role==null) {
			role=new MonitorRole();
			sumMap.put(roleID, role);
		}
		long sy=MAXBY-role.getAddBY();
		int max=(int)(sy/moeny);
		if (max<=0) {return 0;}
		if (num>max) {num=max;}
		role.setAddBY(role.getAddBY()+moeny*num);
		return num;
	}
	/**重置*/
	public static void reset(){
		sumMap.clear();
	}
	/**物资总数打印*/
	public static void ASSize(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("仙玉总量:");
		buffer.append(AllServiceUtil.getUserTableService().selectAllCodecard());
		buffer.append("__充值总量:");
		buffer.append(AllServiceUtil.getUserTableService().selectAllPayintegration());
		buffer.append("__大话币总量:");
		buffer.append(AllServiceUtil.getUserTableService().selectAllGold());
		money.toString(buffer);
		money.reset();
		AllServiceUtil.getRecordService().insert(new Record(6,buffer.toString()));
		
	}
	
	public static void toSting(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("刮刮乐:");
		buffer.append(lotteryM.getKey());
		buffer.append(":");
		buffer.append(lotteryM.getValue()/10000);
		buffer.append("W,摆摊税收:");
		buffer.append(stallM.getKey());
		buffer.append(":");
		buffer.append(stallM.getValue()/10000);
		buffer.append("W,战斗任务掉落:");
		buffer.append(dropQM1.getKey());
		buffer.append(":");
		buffer.append(dropQM1.getValue()/10000);
		buffer.append("W,前端Robot掉落:");
		buffer.append(dropQM2.getKey());
		buffer.append(":");
		buffer.append(dropQM2.getValue()/10000);
		buffer.append("W,后端掉落");
		buffer.append(dropHM.getKey());
		buffer.append(":");
		buffer.append(dropHM.getValue()/10000);
		buffer.append("W,抽奖1使用次数");
		buffer.append(dropCJ.getJade1());
//		buffer.append(",抽奖2使用次数");
//		buffer.append(dropCJ.getJade2());
//		buffer.append(",抽奖3使用次数");
//		buffer.append(dropCJ.getJade3());
//		buffer.append(",抽奖4使用次数");
//		buffer.append(dropCJ.getJade4());
//		buffer.append(",抽奖5使用次数");
//		buffer.append(dropCJ.getJade5());
		buffer.append("。");
		buffer.append("大于10E的金额流动统计,交易次数:");
		buffer.append(moneyM.getJade1());
		buffer.append("给予次数:");
		buffer.append(moneyM.getJade2());
		buffer.append("摆摊次数:");
		buffer.append(moneyM.getJade3());
		buffer.append("。七十二变系统消耗大话币:");
		buffer.append(card.getKey()/10000);
		buffer.append("W,仙玉:");
		buffer.append(card.getValue());
		System.out.println(buffer.toString());
		AllServiceUtil.getRecordService().insert(new Record(0,buffer.toString()));
	}
	/**物品检测机制*/
	public static String isGoodMonitor(Goodstable good,Goodstable goodstable,int I){
		if (I!=0&&I!=1&&I!=2) {
			return "非修改范围类型";
		}
		if (good==null) {
			WriteOut.addtxt("物品找不到:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable),9999);
			return "找不到物品";
		}
		if (goodstable.getRole_id()!=null&&good.getRole_id().compareTo(goodstable.getRole_id())!=0) {
        	String value="使用物品角色ID改变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
    		WriteOut.addtxt(value,9999);
    		return value;
		}
		if (I==2&&!(good.getType()==80156||good.getType()==2012||good.getType()==729||good.getType()==2010||good.getType()==2011)) {
    		I=1;
		}
		if (goodstable.getGoodsid()!=null&&!good.getGoodsid().equals(goodstable.getGoodsid())) {
			String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
    		WriteOut.addtxt(value,9999);
    		return value;
		}	
		if (good.getType()!=goodstable.getType()) {
			String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
    		WriteOut.addtxt(value,9999);
    		return value;
		}
		goodstable.setQhv(good.getQhv());
		goodstable.setQht(good.getQht());
		goodstable.setQhb(good.getQhb());
		if (I==2) {
			if (good.getType()==729&&goodstable.getValue()!=null) {
	    		String[] vs1=good.getValue().split("\\|");
	    		String[] vs2=goodstable.getValue().split("\\|");
	    		int size1=0;int size2=0;
	    		for (int j = 0; j < vs1.length; j++) {
	    			String[] vss=vs1[j].split("=");
	    			if (vss[0].equals("根骨")||vss[0].equals("灵性")||vss[0].equals("力量")||vss[0].equals("敏捷")||vss[0].equals("品质")) {
	    				int p=Integer.parseInt(vss[1]);
	    				size1+=p;
	    			}
				}
	    		for (int j = 0; j < vs2.length; j++) {
	    			String[] vss=vs2[j].split("=");
	    			if (vss[0].equals("根骨")||vss[0].equals("灵性")||vss[0].equals("力量")||vss[0].equals("敏捷")||vss[0].equals("品质")) {
	    				int p=Integer.parseInt(vss[1]);
	    				size2+=p;
	    			}
				}
	    		if (size1!=size2) {
	    			String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
		    		WriteOut.addtxt(value,9999);
		    		return value;
				}
			}
		}else if (goodstable.getValue()!=null&&!good.getValue().equals(goodstable.getValue())) {
			String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
    		WriteOut.addtxt(value,9999);
    		return value;
		}else if (goodstable.getGoodsname()!=null&&!good.getGoodsname().equals(goodstable.getGoodsname())) {
			String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
    		WriteOut.addtxt(value,9999);
    		return value;
		}
		int yUse=good.getUsetime();
    	int nUse=goodstable.getUsetime();
    	if (I==1) {
    		if (nUse>=yUse) {//问题物品
        		String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
        		WriteOut.addtxt(value,9999);
        		return value;
    		}
		}else {
    		if (nUse>yUse) {
				String value="物品突变:原:"+GsonUtil.getGsonUtil().getgson().toJson(good)+":新:"+GsonUtil.getGsonUtil().getgson().toJson(goodstable);
        		WriteOut.addtxt(value,9999);
        		return value;
			}	
		}
		return null;
	}
}
