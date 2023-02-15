package come.tool.Scene.LaborDay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.monitor.MonitorUtil;
import org.come.action.suit.SuitComposeAction;
import org.come.action.suit.SuitMixdeal;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.ReadExelTool;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Good.DropUtil;
import come.tool.Stall.AssetUpdate;
/**最短时间完成的质量*/
public class LaborScene {
	
	public static int I;//活动状态  0未开启   1开启中   2结束(预留兑换时间)  
	public static int MONEY=100;//每100充值金额兑换一次仙玉抽奖次数  
	public static String LABOR=Agreement.getAgreement().viconAgreement("A1");//仙玉充值转盘数据  
	public static int[] XYS=new int[]{30000,3000,5000,10000,50000,3000,10000,100000,3000,30000,5000,50000,3000,5000};//仙玉充值转盘数据  
	public static String CZVALUE;//仙玉充值转盘数据  
	public static Object   CZLOCK=new Object();//仙玉充值转盘锁  
	public static Object DRAWLOCK=new Object();//排名领取奖励锁  
	public static long CZSize=0;//抽奖的次数
	public static long CZValue=0;//抽奖获取的仙玉总值
	
	static{
		//30  3  5 10  50
		//5             3
		//3            10
		//50  5 30  3 100
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < XYS.length; i++) {
			if (buffer.length()!=0) { buffer.append("|"); }
			if (XYS[i]==100000) { buffer.append("WYXY10W"); }
			else if (XYS[i]==50000) { buffer.append("WYXY5W"); }
			else if (XYS[i]==30000) { buffer.append("WYXY3W"); }
			else if (XYS[i]==10000) { buffer.append("WYXY1W"); }
			else if (XYS[i]== 5000) { buffer.append("WYXY5K"); }
			else { buffer.append("WYXY3K"); }
		}
		CZVALUE=buffer.toString();
	}
	public static void main(String[] args) {
		long v=0;
		for (int i = 0; i < 1000000; i++) {
			v+=awardValue();
		}
		System.out.println(v+":"+(v/1000000));
	}
	/**
	 * 获取抽中的金额 金额单位 K  平均 一次抽奖获得 8200 仙玉
	 * 100   50    30   10    5    3
	 * 0.5%  2%  7.5%  15%  30%  45%
	 */
	public static int awardValue(){
		int v=SuitComposeAction.random.nextInt(1000);
		if (v<=4)        { v=100000; }
		else if (v<=25)  { v=50000; }
		else if (v<=100) { v=30000; }
		else if (v<=250) { v=10000; }
		else if (v<=550) { v=5000; }
		else             { v=3000; }		
		return v;
	}
	/**根据金额获取位置并且加上随机参数*/
	public synchronized static int awardLocation(int v){
		CZSize++;
		CZValue+=v;
		int location=-1;
		for (int i = 0; i < XYS.length; i++) {
			if (XYS[i]!=v) { continue; }
			if (location==-1) {
				location=i;
			}else if (SuitComposeAction.random.nextInt(11)<=4) {
				location=i;	
			}
		}
		if (location==-1) { return location; }
		return location+XYS.length*(CZSize%3==0?3:2);
	}
	public static LaborScene laborScene;
	/**初始化*/
	public static void init(){
		String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "labor.txt");
		if (text==null||text.equals("")) {
			laborScene=new LaborScene();
			laborScene.timeOpen=1588262400000L;
			laborScene.timeStop=1588608000000L;
			laborScene.timeEnd =1588694400000L;
		}else {
			laborScene=GsonUtil.getGsonUtil().getgson().fromJson(text,LaborScene.class);		
		}
		
		long time=System.currentTimeMillis();
		if (time>=laborScene.timeOpen&&time<laborScene.timeStop) { I=1; }
		else if (time>=laborScene.timeStop&&time<laborScene.timeEnd) { I=2; }
		else { I=0; }
		laborScene.initLabor();
	}
	
	/**定时保存  or 服务器关闭保存记录数据*/
	public static void Save(boolean is){
		//数据有被修改才需要保存
		synchronized (laborScene) {
			if (is||laborScene.isV) {
				try {
					byte[] bs=GsonUtil.getGsonUtil().getgson().toJson(laborScene).getBytes();
					CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "labor.txt",bs);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				laborScene.isV=false;
			}
		}
	}
	private long timeOpen;//开启时间
	private long timeStop;//停止时间
	private long timeEnd;//彻底结束时间
	private ConcurrentHashMap<Long,Long> rankMap;//排名记录 负数排名表示已经领取
	private ConcurrentHashMap<BigDecimal,LaborRole> roleMap;//被记录的玩家数据
	private transient List<LaborRole> CZRankList;//充值排行
	private transient List<LaborRole> CJRankList;//抽奖排行
	private transient boolean isV;//是否需要修改数据
	
	/**修改活动开启 停止 彻底结束时间*/
	public static void setLaborSceneTime(long time1,long time2,long time3){
		laborScene.timeOpen=time1;
		laborScene.timeStop=time2;
		laborScene.timeEnd =time3;
	}
	/**活动开启 停止  结束*/
	public static void upLaborScene(int hour,int minute){
		long time=System.currentTimeMillis();
		if (I==1&&hour==0&&minute==0) { laborScene.resetDay();	}
		
		if (I==0&&time>=laborScene.timeOpen&&time<laborScene.timeEnd) {//开启活动
			I=1;
			laborScene.initLabor();
			SendMessage.sendMessageToAllRoles(LABOR);
		}else if (I==1&&time>=laborScene.timeStop) {//停止活动
			I=2;
			laborScene.resetCZ();
		}else if (I==2&&time>=laborScene.timeEnd) {//关闭活动
			I=0;
			SendMessage.sendMessageToAllRoles(Agreement.getAgreement().viconAgreement("R1"));
			laborScene.initLabor();
		}
		if (hour%2==0&&minute==45) { 
			Save(false); 
			System.out.println("活动期间仙玉抽奖次数:"+CZSize+":获得仙玉数:"+CZValue+"平均值:"+(CZValue/CZSize));
		}
	}
	/**增加 充值 or 抽奖次数   type:0 添加充值  1:添加抽奖次数*/
	public static void addRankValue(int type,int value,LoginResult loginResult){
		//不在活动期间内不添加
		if (I!=1) { return; }
		synchronized (laborScene) {
			LaborRole laborRole=laborScene.addRole(loginResult);
			if (type==0) { laborRole.setCz(laborRole.getCz()+value); }
			else if (type==1) { laborRole.setCj(laborRole.getCj()+value); }
			laborScene.resetRank(type, laborRole);
			laborScene.isV=true;
		}
	}
	/**初始化排行榜*/
	public void initLabor(){
		if (I==0) {
			CZRankList=null;
			CJRankList=null;
			rankMap=null;
			roleMap=null;
			return; 
		}
		CZRankList=new ArrayList<>();
		CJRankList=new ArrayList<>();
		if (rankMap==null) { rankMap=new ConcurrentHashMap<>(); }
		if (roleMap==null) { roleMap=new ConcurrentHashMap<>(); }
		Iterator<BigDecimal> it = roleMap.keySet().iterator();
		while(it.hasNext()) {
			BigDecimal roleId=it.next();
			LaborRole laborRole=roleMap.get(roleId);
			if (laborRole.getCz()!=0) { resetRank(0, laborRole); }
			if (laborRole.getCj()!=0) { resetRank(1, laborRole); }
		}
	}
	/**获取排名*/
	public int getRank(int type,LaborRole role){
		int value=type==0?role.getCz():role.getCj();
		if (value==0) { return 0; }
		synchronized (laborScene) {
			if (type==0) {
				if (CZRankList.size()!=0&&value<CZRankList.get(CZRankList.size()-1).getCz()) {return 0;}
				for (int i=0,length=CZRankList.size();i<length;i++) {
					LaborRole laborRole=CZRankList.get(i);
					if (laborRole.getRoleId().compareTo(role.getRoleId())==0) {return i+1;}
				}
			}else if (type==1) {
				if (CJRankList.size()!=0&&value<CJRankList.get(CJRankList.size()-1).getCj()) {return 0;}
				for (int i=0,length=CJRankList.size();i<length;i++) {
					LaborRole laborRole=CJRankList.get(i);
					if (laborRole.getRoleId().compareTo(role.getRoleId())==0) {return i+1;}
				}
			}
		}
		return 0;
	}
	/**type 0:刷新充值排名 1:刷新抽奖排名*/
	public void resetRank(int type,LaborRole role){
		if (type==0) {
			if (CZRankList.size()<100||role.getCz()>=CZRankList.get(CZRankList.size()-1).getCz()) {
				CZRankList.remove(role);
				for (int i=0,length=CZRankList.size();i<length;i++) {
					LaborRole laborRole=CZRankList.get(i);
					if (role.getCz()>laborRole.getCz()) {
						CZRankList.add(i, role);
						for (int j=100;j<=length;j++) {CZRankList.remove(100);}
						return;
					}
				}
				if (CZRankList.size()<100) { CZRankList.add(role); }	
			}
		}else if (type==1) {
			if (CJRankList.size()<100||role.getCj()>=CJRankList.get(CJRankList.size()-1).getCj()) {
				CJRankList.remove(role);
				for (int i=0,length=CJRankList.size();i<length;i++) {
					LaborRole laborRole=CJRankList.get(i);
					if (role.getCj()>laborRole.getCj()) {
						CJRankList.add(i, role);
						for (int j=100;j<=length;j++) {CJRankList.remove(100);}
						return;
					}
				}
				if (CJRankList.size()<100) { CJRankList.add(role); }	
			}
		}
	}
	/**充值排行榜结算*/
	public void resetCZ(){
		synchronized (DRAWLOCK){
			for (int i=0,length=CZRankList.size();i<length;i++) {
				LaborRole laborRole=CZRankList.get(i);
				rankMap.put((1L<<32)|laborRole.getRoleId().longValue(), (i+1L));
			}
		}
	}
	/**每日凌晨重置接口*/
	public void resetDay(){
		synchronized (laborScene) {
			//留下一份日志记录 //先记录抽奖排行榜数据
			synchronized (DRAWLOCK){
				rankMap.clear();
				for (int i=0,length=CJRankList.size();i<length;i++) {
					LaborRole laborRole=CJRankList.get(i);
					rankMap.put(laborRole.getRoleId().longValue(), (i+1L));
				}	
			}
			CJRankList.clear();//清空抽奖
			Iterator<BigDecimal> it = roleMap.keySet().iterator();
			while(it.hasNext()) {
				BigDecimal roleId=it.next();
				LaborRole laborRole=roleMap.get(roleId);
				laborRole.setCj(0);
				if (laborRole.getCz()==0) {//不需要记录的玩家
					it.remove();
				}
			}	
		}
	}
	/**前端请求处理*/
	public static String request(LoginResult loginResult,String message){
		if (I==0) { return Agreement.getAgreement().PromptAgreement("活动未开启"); }
		int value=Integer.parseInt(message);
		if (value>=0&&value<=2) {//显示界面
			LaborRank laborRank=new LaborRank(value);
			LaborRole laborRole=laborScene.roleMap.get(loginResult.getRole_id());
			if (value==2) {
				laborRank.setRank(laborRole!=null?laborRole.getCZCJNum():0);
				laborRank.setValue(CZVALUE);
			}else {
				laborRank.setRoles(value==0?laborScene.CZRankList:laborScene.CJRankList);
				laborRank.setRole(laborRole!=null?laborRole:new LaborRole(loginResult));
				laborRank.setRank(laborScene.getRank(value, laborRank.getRole()));
			}
			return Agreement.getAgreement().laborAgreement(GsonUtil.getGsonUtil().getgson().toJson(laborRank));
		}else if (value==10||value==11) {//10领取充值排行 or 11领取抽奖排行
			if (value==10&&I!=2) { return Agreement.getAgreement().PromptAgreement("还未到结算时间"); }//未到结算时间
			long rankValue=0;
			synchronized (DRAWLOCK){
				long key=loginResult.getRole_id().longValue();
				if (value==10) { key=(1L<<32)|key; }
				Long rank=laborScene.rankMap.get(key);
				if (rank==null) { return Agreement.getAgreement().PromptAgreement("你未上榜无法领取排名奖励"); }
				if (rank<=0) { return Agreement.getAgreement().PromptAgreement("你已领取过奖励了"); }
				rankValue=rank.longValue();
				if (value==10&&(rankValue<=0||rankValue>100)||value==11&&(rankValue<=0||rankValue>10)) { return Agreement.getAgreement().PromptAgreement("你未上榜无法领取排名奖励"); }
				laborScene.rankMap.put(key, -rank);
			}
			//发送奖励
			String type=null;
			if (value==10) {
				if (rankValue==1) {type="8001";}
				else if (rankValue==2) {type="8002";}
				else if (rankValue==3) {type="8003";}
				else if (rankValue>=4&&rankValue<=20) {type="8004";}
				else if (rankValue>=21&&rankValue<=50) {type="8005";}
				else if (rankValue>=51&&rankValue<=80) {type="8006";}
				else if (rankValue>=81) {type="8007";}
			}else {
				type=(8007+rankValue)+"";
			}
			Dorp dorp = GameServer.getDorp(type);
			String msg=null;
			if (type.equals("8008")) { msg="#R{角色名}#Y在限时神宠活动中,出手阔绰,赢的#R{物品名}#Y神宠得青睐,并愿跟随其征战四方,实在令人羡慕不已!#23"; }
			DropUtil.getDrop(loginResult, dorp.getDorpValue(), msg, 999, 1D, null);
		}else if (value==12) {//仙玉抽奖
			LaborRole laborRole=laborScene.roleMap.get(loginResult.getRole_id());
			synchronized (CZLOCK) {
				if (laborRole==null||laborRole.getCZCJNum()<=0) { return Agreement.getAgreement().PromptAgreement("你没有抽奖次数了"); }
				laborRole.setCzcj(laborRole.getCzcj()+1);
			}
			//开始发放奖励
			int xy=awardValue();
			AssetUpdate assetUpdate = new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("X="+xy);
			loginResult.setCodecard(loginResult.getCodecard().add(new BigDecimal(xy)));
			MonitorUtil.getMoney().addX(xy, 1);
			LaborRank laborRank=new LaborRank(value);
			laborRank.setRank(awardLocation(xy));
			laborRank.setValue("你抽中了"+xy+"仙玉");
			//通知前端转盘
			SendMessage.sendMessageByRoleName(loginResult.getRolename(), 
					Agreement.getAgreement().laborAgreement(GsonUtil.getGsonUtil().getgson().toJson(laborRank))
				   +Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
			//发送系统提示
			SuitMixdeal.XYZP(loginResult.getRolename(), xy);
		}
		return null;
	}
	/**获取玩家 如果没有 新增需记录玩家*/
	public LaborRole addRole(LoginResult loginResult){
		LaborRole laborRole=laborScene.roleMap.get(loginResult.getRole_id());
		if (laborRole==null) {
			laborRole=new LaborRole(loginResult);
			loginResult.setLaborRole(laborRole);
			laborScene.roleMap.put(loginResult.getRole_id(), laborRole);
		}
		return laborRole;
	}
	/**是否需放置数据绑定对象*/
	public static void bindRole(LoginResult loginResult){
		if (laborScene.roleMap!=null) {
			loginResult.setLaborRole(laborScene.roleMap.get(loginResult.getRole_id()));
		}
	}
	/**清理抽奖次数 20210916*/
	public static void clearRoleMapByRoleId(BigDecimal roleId) {
		laborScene.roleMap.remove(roleId);
	}
}
