package org.come.task;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.buy.BuyUtil;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.bean.Middle;
import org.come.entity.GoodssaledayrecordEntity;
import org.come.entity.ImportantgoodssumrecordEntity;
import org.come.entity.UserTable;
import org.come.entity.UserxyandroledhbcrEntity;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.model.Eshop;
import org.come.model.Lshop;
import org.come.model.Shop;
import org.come.model.TaskSet;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.server.GameServer;
import org.come.thread.RedisEqualWithSqlThread;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.BangBattle.BangBattlePool;
import come.tool.Battle.RewardLimit;
import come.tool.PK.PKPool;
import come.tool.Role.PrivateData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.LTS.LTSUtil;
import come.tool.Scene.LaborDay.LaborScene;
import come.tool.newGang.GangUtil;
import come.tool.newTask.TaskRecord;
import come.tool.newTask.TaskUtil;
import come.tool.oneArena.OneArenaAction;
import come.tool.teamArena.TeamArenaUtil;

/**
 * 定时刷新怪物，发送客户端刷新怪物信息
 *
 * @author 叶豪芳
 * @date 2017年12月28日 上午10:46:14
 */
public class RefreshMonsterTask implements Runnable {
	private static String str[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };// 字符串数组
	public static int day = 0;// 天
	public static int second = 0;// 秒
	public static int minute = 0;// 分钟
	public static int hour = 0;// 小时

	public RefreshMonsterTask() {
		MonsterUtil.init();
	}


	public static Calendar rightNow = Calendar.getInstance(Locale.CHINA);
	@Override
	public void run() {
		LaborScene.init();
		/**bug处理 结算 劳动节 神宠排行*/
		//LaborScene.laborScene.resetDay();
		while (true) {
			if (handleTime()) {
				try { Thread.sleep(1000*60); } catch (Exception e) { }
				continue;
			}
			if (GameServer.OPEN) { return; }//关闭程序时结束线程
			handleRole();
			handleEvent();
			handleOther();
		}
	}
	/**时间等待*/
	public boolean handleTime(){
		try {
			rightNow=Calendar.getInstance(Locale.CHINA);
			second=rightNow.get(Calendar.SECOND);
			minute=rightNow.get(Calendar.MINUTE);
			Thread.sleep(((60 - second) + (4 - minute % 5) * 60) * 1000);//取消这个注释 变为五分钟刷新一次
			rightNow = Calendar.getInstance(Locale.CHINA);
			day = rightNow.get(Calendar.DAY_OF_WEEK);// 获取时间
			hour = rightNow.get(Calendar.HOUR_OF_DAY);// 小时
			minute = rightNow.get(Calendar.MINUTE);// 分钟
			second = rightNow.get(Calendar.SECOND);// 秒
			if (minute % 5 != 0) {
				minute /= 5; minute *= 5;
				WriteOut.addtxt("时间不对:" + minute, 9999);
			}
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("刷新线程:" + MainServerHandler.getErrorMessage(e), 9999);
			return true;
		}
		return false;
	}
	/**日志打印  和数据重置清空*/
	public void handleOther(){
		try {
			if (minute == 0) { MonitorUtil.toSting(); }//数据打印
			if (hour == 0 && minute == 0) {
				RewardLimit.reset();
				BuyUtil.reset();
				MonitorUtil.reset();
				RedisCacheUtil.resetOneArenaTime();
				if (day == 2) { BangBattlePool.getBangBattlePool().NewWeek(); }//重置帮战周数
				/** HGC-2019-12-01 */
				shangChengGoodsSum();/** 统计商城购买记录 */
				userRoleXianyuSum();/** 玩家/角色大话币仙玉操作 */
			}
			if (minute % 10 == 0) {
				RedisCacheUtil.reset();  /**重置 内存缓存*/
				RolePool.checkRoleData();/**清除离线玩家缓存*/
			}
			// 五点刷新排行榜
			if (hour == 5 && minute == 0) {
				saveRoleInfo();        /**保存玩家数据*/
				GameServer.bangLists(); /**刷新排行榜*/
				GameServer.resetBbuy();/**重置收购次数*/
				MonitorUtil.ASSize();  /**记录物资数据*/
				LTSUtil.getLtsUtil().bangList(str[day - 1]);/**擂台排行榜刷新*/
			}
			/**redis->数据库 同步*/
			if ((hour == 2 || hour == 9 || hour == 16) && minute == (GameServer.getPortNumber() - 7100) * 5) {
				RedisEqualWithSqlThread.AllToDatabase();
			}
			/** HGC-2019-12-01 统计重要物资数量记录 */
			if (hour == 5 && minute == 30) { importantGoodsSum(); }
			/** HGC-2019-12-19 创建表空间并创建分区 */
			if (hour == 23 && minute == 55) { /**createTableSplace(1) */ }
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("刷新线程:日志类" + MainServerHandler.getErrorMessage(e), 9999);
		}
	}
	/**活动类*/
	public void handleEvent(){

		try {
			LaborScene.upLaborScene(hour, minute);/**劳动活动*/
			/**帮战开启*/
			if (day == 6 || day == 7) {
				if (minute == 0 && hour == 20) { BangBattlePool.getBangBattlePool().FightOpenClose(); }
				else if (minute == 30 && hour == 21) { BangBattlePool.getBangBattlePool().FightOpenClose(); }
			}
			if (hour == 21 && minute == 0) { OneArenaAction.OneArenaReset(); }//单人竞技场9点刷新奖励
			if (hour == 19 && minute == 0) { TeamArenaUtil.teamArenaOpen(); } //组队竞技场19点 开放匹配通道
			if (minute==5) { upBuyCount(day,hour==0); }/**保存销售记录*/
			if (minute==10) { GangUtil.upGangs(true); }/**保存帮派数据*/
			PKPool.getPkPool().OVERTIME();// 皇宫pk超时
			SceneUtil.activityOpen(str[day - 1], day, hour, minute, second);
			MonsterUtil.refurbishMonster(str[day - 1], day, hour, minute, second);
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("刷新线程:" + MainServerHandler.getErrorMessage(e), 9999);
		}
	}
	/**人物数据 0点重置*/
	public void handleRole(){
		try {
			if (hour == 0 && minute == 0) {
				int type=(day==2?2:1);
				Iterator<Entry<ChannelHandlerContext, LoginResult>> reset = GameServer.getAllLoginRole().entrySet().iterator();
				while (reset.hasNext()) {
					try {
						Entry<ChannelHandlerContext, LoginResult> entrys = reset.next();
						taskReset(entrys.getKey(), entrys.getValue(), type);
					} catch (Exception e) {
						// TODO: handle exception
						String abc = "0点刷新线程:" + MainServerHandler.getErrorMessage(e);
						System.out.println(abc);
						WriteOut.addtxt(abc, 9999);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("刷新线程:日志类" + MainServerHandler.getErrorMessage(e), 9999);
		}
	}
	/**重置*/
	public static void taskReset(ChannelHandlerContext ctx, LoginResult loginResult, int type) {
		if (loginResult == null) { return; }
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		PrivateData data=roleData.getPrivateData();
		boolean is=ResetTask(roleData.getTaskRecordMap(), type);
		int DBExp = 0;
		BigDecimal Dayandpayorno=loginResult.getDayandpayorno();
		int Dayfirstinorno=loginResult.getDayfirstinorno();
		BigDecimal Daypaysum=loginResult.getDaypaysum();
		BigDecimal Daygetorno=loginResult.getDaygetorno();
		String Vipget=loginResult.getVipget();
		//清空每日充值
		//如果每日充值金额数值为0中断连冲奖励
		//清空每日充值领取  如果连冲大于等于7天 清空连冲次数和领取记录
		if (Dayfirstinorno==0||loginResult.getDayandpayorno().longValue()>=7) {
			loginResult.setDayandpayorno(new BigDecimal(0));
			loginResult.removeVipget("3");
		}
		loginResult.setDayfirstinorno(0);
		loginResult.setDaypaysum(new BigDecimal(0));//清空每日充值
		loginResult.setDaygetorno(new BigDecimal(2));//清空每日特惠领取
		loginResult.removeVipget("2");//清空每日充值领取
		String Vipget2=loginResult.getVipget();
		if (Vipget==null) {Vipget="";}
		if (Vipget2==null) {Vipget2="";}
		if (!is && data.getDBExp() == DBExp
				&& loginResult.getDayandpayorno().compareTo(Dayandpayorno)==0
				&& loginResult.getDaypaysum().compareTo(Daypaysum)==0
				&& loginResult.getDaygetorno().compareTo(Daygetorno)==0
				&& (Vipget.equals(Vipget2))
				&& loginResult.getDayfirstinorno()==Dayfirstinorno){
			return;
		}
		Middle middle = new Middle();
		data.setDBExp(DBExp);
		String taskComplete=is?TaskUtil.toTaskRecord(roleData.getTaskRecordMap()):null;
		if (is) {
			data.setTaskComplete(taskComplete);
			middle.setTaskComplete(taskComplete);
		}
		middle.setRolename(loginResult.getRolename());
		middle.setTaskDaily(loginResult.getTaskDaily());
		middle.setDaypaysum(loginResult.getDaypaysum());
		middle.setDaygetorno(loginResult.getDaygetorno());
		middle.setDayandpayorno(loginResult.getDayandpayorno());
		middle.setVipget(loginResult.getVipget());
		middle.setDayfirstinorno(loginResult.getDayfirstinorno());
		String mes = Agreement.getAgreement().MiddleAgreement(GsonUtil.getGsonUtil().getgson().toJson(middle));
		SendMessage.sendMessageToSlef(ctx, mes);
	}

	/**重置任务*/
	public static boolean ResetTask(ConcurrentHashMap<Integer,TaskRecord> map, int type) {
		boolean is=false;
		for (Entry<Integer, TaskRecord> item : map.entrySet()){
			TaskRecord record=item.getValue();
			TaskSet taskSet=GameServer.getTaskSet(record.getTaskId());
			if (taskSet==null||taskSet.getResetcycle()==0) {
				continue;
			}
			if (type>=taskSet.getResetcycle()) {
				map.remove(item.getKey());
				is=true;
			}
		}
		return is;
	}
	public static String ResetTask(String record, int type) {
		if (record==null||record.equals("")) {
			return record;
		}
		StringBuffer buffer=new StringBuffer();
		String[] vs=record.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			String[] rs=vs[i].split("-");
			TaskSet taskSet=GameServer.getTaskSet(Integer.parseInt(rs[0]));
			if (taskSet==null||taskSet.getResetcycle()==0||type<taskSet.getResetcycle()) {
				if (buffer.length()!=0) {
					buffer.append("|");
				}
				buffer.append(vs[i]);
			}
		}
		return buffer.length()!=0?buffer.toString():null;
	}
	/**
	 * 产生随机数
	 *
	 * @param max
	 * @param n
	 * @return
	 */
	public static List<Integer> randomArray(int max, int n) {
		Random random = new Random();
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			int ran = random.nextInt(max);
			result.add(ran);
		}
		return result;
	}

	/** 保存玩家信息 */
	public static void saveRoleInfo() {
		// 保存玩家信息
		Iterator<Map.Entry<ChannelHandlerContext, LoginResult>> entries = GameServer.getAllLoginRole().entrySet().iterator();
		while (entries.hasNext()) {
			Entry<ChannelHandlerContext, LoginResult> entrys = entries.next();
			// 保存用户信息
			LoginResult loginResult = entrys.getValue();
			if (loginResult == null) {
				continue;
			}
			try {
				// 修改用户点卡
				UserTable userTable = new UserTable();
				userTable.setCodecard(loginResult.getCodecard());
				userTable.setMoney(loginResult.getMoney());
				userTable.setUsername(loginResult.getUserName());
				AllServiceUtil.getUserTableService().updateUser(userTable);
				// 保存角色信息
				RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
				loginResult.setUptime(System.currentTimeMillis() + "");
				roleData.roleRecover(loginResult);
				AllServiceUtil.getRoleTableService().updateRoleWhenExit(loginResult);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("保存玩家信息失败：" + loginResult.getRolename());
			}
		}
	}

	/**
	 * 统计重要物资数量记录
	 */
	public static void importantGoodsSum() {
		List<ImportantgoodssumrecordEntity> selectImportantGoods = AllServiceUtil.getImportantgoodtrcordService().selectImportantGoods();
		for (int i = 0; i < selectImportantGoods.size(); i++) {
			try {
				int addImporatantGoodsSum = AllServiceUtil.getImportantgoodtrcordService().addImporatantGoodsSum(selectImportantGoods.get(i));
				if (addImporatantGoodsSum < 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("统计重要物资数量记录失败，物品ID：" + selectImportantGoods.get(i).getGid());
			}
		}
	}

	/**
	 * 统计商城购买记录
	 */
	public static void shangChengGoodsSum() {
		List<GoodssaledayrecordEntity> selectGoodsBuyRecordSumList = AllServiceUtil.getGoodsTableService().selectGoodsBuyRecordSumList();
		for (int i = 0; i < selectGoodsBuyRecordSumList.size(); i++) {
			try {
				int addImporatantGoodsSum = AllServiceUtil.getGoodsTableService().addGoodssaledayrecord(selectGoodsBuyRecordSumList.get(i));
				if (addImporatantGoodsSum < 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("统计商城购买记录失败，物品ID：" + selectGoodsBuyRecordSumList.get(i).getGid());
			}
		}
	}

	/**
	 * 玩家/角色大话币仙玉操作统计记录
	 */
	public static void userRoleXianyuSum() {
		List<UserxyandroledhbcrEntity> selectUserRoleXianyuDahuabiList = AllServiceUtil.getUserTableService().selectUserRoleXianyuDahuabiList();
		for (int i = 0; i < selectUserRoleXianyuDahuabiList.size(); i++) {
			try {
				int addImporatantGoodsSum = AllServiceUtil.getUserTableService().addUserRoleXianyuDahuabi(selectUserRoleXianyuDahuabiList.get(i));
				if (addImporatantGoodsSum < 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("玩家/角色大话币仙玉操作统计记录，用户ID：" + selectUserRoleXianyuDahuabiList.get(i).getUserid() + "角色ID：" + selectUserRoleXianyuDahuabiList.get(i).getRoleid());
			}
		}
	}
	/**保存销售操作记录*/
	public static void upBuyCount(int day,boolean isReset){
		ConcurrentHashMap<String, Shop> shopMap=GameServer.getAllShopGoods();
		if (shopMap!=null) {
			Iterator<Shop>  iterShop=shopMap.values().iterator();
			while (iterShop.hasNext()){
				Shop value=iterShop.next();
				value.getBuyCount().upData();
				if (isReset) {
					value.getBuyCount().Reset(day);
				}
			}
		}
		ConcurrentHashMap<String, Eshop> eShopMap=GameServer.getAllEshopGoods();
		if (eShopMap!=null) {
			Iterator<Eshop> iterEShop=eShopMap.values().iterator();
			while (iterEShop.hasNext()){
				Eshop value=iterEShop.next();
				value.getBuyCount().upData();
				if (isReset) {
					value.getBuyCount().Reset(day);
				}
			}
		}
		ConcurrentHashMap<String, Lshop> lShopMap=GameServer.getAllLShopGoods();
		if (lShopMap!=null) {
			Iterator<Lshop> iterLShop=lShopMap.values().iterator();
			while (iterLShop.hasNext()){
				Lshop value=iterLShop.next();
				value.getBuyCount().upData();
				if (isReset) {
					value.getBuyCount().Reset(day);
				}
			}
		}
	}

	/**
	 * 添加对应的表空间
	 *
	 * @param num
	 */
	public static void createTableSplace(int num) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.add(Calendar.DATE, num);
		Date temp = cld.getTime();
		// 获得下一天日期字符串
		String nextDay = formatter.format(temp);
		Set<Entry<String, String>> entrySet = GameServer.tableZone.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String tableName = entry.getKey();
			String tableSpace = entry.getValue();
			String tableSpaceDay = tableSpace + nextDay;
			int selectTableSapce = AllServiceUtil.getImportantgoodtrcordService().selectTableSapce(tableSpaceDay);
			if (selectTableSapce == 0) {
				AllServiceUtil.getImportantgoodtrcordService().addImporatantGoodsLuTableSpace(nextDay, tableSpaceDay, GameServer.tablePath);
			}
			int selectTablePartition = AllServiceUtil.getImportantgoodtrcordService().selectTablePartition(tableSpaceDay, tableName);
			if (selectTablePartition == 0) {
				AllServiceUtil.getImportantgoodtrcordService().addTableImporatantGoodsLuTableSpace(nextDay, tableSpaceDay, tableName);
			}
		}
	}
}
