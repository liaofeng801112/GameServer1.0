package org.come.servlet;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.ApplyBean;
import org.come.bean.ApplyPayBean;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.ExpensesReceipts;
import org.come.entity.PayvipBean;
import org.come.entity.Record;
import org.come.entity.UserTable;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.redis.RedisPoolUntil;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.APIHttpClient;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import redis.clients.jedis.Jedis;
import come.tool.Role.PrivateData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.LaborDay.LaborScene;

public class CallBackServlet extends HttpServlet {

	private String URL = "http://www.dongmengzhongchou.com:8080/TestMaven/updateExpensesReceipts";

	// private String URL =
	// "http://127.0.0.1:8080/TestMaven/updateExpensesReceipts";
	// private String URL =
	// "http://192.168.0.137:8080/TestMaven/updateExpensesReceipts";

	/**
	 * Constructor of the object.
	 */
	public CallBackServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		String mes = request.getParameter("mes");
		String type = request.getParameter("type");
		/** 测试数据演示 */
		// ExpensesReceipts expensesReceipts = new ExpensesReceipts();
		// expensesReceipts.setErid(new BigDecimal(mes));
		// expensesReceipts.setPlayeracc("ddd");
		// // expensesReceipts.setSid(new BigDecimal(20616));
		// expensesReceipts.setRecharge(new BigDecimal("100"));
		// expensesReceipts.setYuanbao(new BigDecimal("2000"));
		/** 测试数据演示end */
		ExpensesReceipts expensesReceipts = GsonUtil.getGsonUtil().getgson().fromJson(mes, ExpensesReceipts.class);
		UserTable userTable = AllServiceUtil.getUserTableService().selectForUsername(expensesReceipts.getPlayeracc());
		BigDecimal sid = expensesReceipts.getSid();
		int con = -1;
		// 区Id回调
		// if ("1".equals(type)) {
		expensesReceipts.setSid(userTable.getQid());
		// 发送到数据处理处
		// con = Integer.parseInt(APIHttpClient.sendMesStr(URL,
		// GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts)));
		// } else
		if ("2".equals(type)) {// 充值回调
			rechargeCallBack(expensesReceipts, sid, userTable);
		} else if ("3".equals(type)) {
			integralCallBack(expensesReceipts, userTable);
		}

		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
		pwPrintWriter.flush();
		pwPrintWriter.close();
	}

	/**
	 * 充值回调
	 * 
	 * @param expensesReceipts
	 * @param sid
	 * @param userTable
	 * @return
	 */
	public void rechargeCallBack(ExpensesReceipts expensesReceipts, BigDecimal sid, UserTable userTable) {
		Jedis jedis = RedisPoolUntil.getJedis();
		expensesReceipts.setSid(userTable.getQid());
		try {
			ApplyBean applyBean = new ApplyBean();
			applyBean.setUserNameS(expensesReceipts.getPlayeracc());// 充值的帐户名
			applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// 实际充值金额
			BigDecimal addC = new BigDecimal(applyBean.getRealmoney());
			int type = expensesReceipts.getType();
			// 支付类型 1仙玉充值 2周月卡充值 3小资冲级礼包充值 4土豪冲级礼包字段

			userTable.setPayintegration(userTable.getPayintegration() + addC.intValue());
			ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
			LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
			if (login != null) {// 在线充值
				AllServiceUtil.getUserTableService().updateUser(userTable);
				login.setPaysum(login.getPaysum().add(addC));// 累计充值
				login.setDaypaysum(login.getDaypaysum().add(addC));// 每日累计充值
				LaborScene.addRankValue(0, addC.intValue(), login);//劳动节活动
				ApplyPayBean applyPayBean = new ApplyPayBean();
				applyPayBean.setAddM(addC);
				RoleData roleData = RolePool.getRoleData(login.getRole_id());
				PayvipBean vipBean = GameServer.getVIP(login.getPaysum().longValue());
				if (vipBean != null && roleData != null) {
					UseCardBean limit = roleData.getLimit("SVIP");
					if (limit == null) {
						limit = new UseCardBean("VIP" + vipBean.getGrade(), "SVIP", "S" + (19 + vipBean.getGrade()), -1, vipBean.getIncreationtext());
						roleData.addLimit(limit);
						applyPayBean.setVIPBean(limit);
					} else if (!limit.getName().equals("VIP" + vipBean.getGrade())) {
						limit.setName("VIP" + vipBean.getGrade());
						limit.setSkin("S" + (19 + vipBean.getGrade()));
						limit.setValue(vipBean.getIncreationtext());
						applyPayBean.setVIPBean(limit);
					}
				}
				if (type == 2) {
					long time = 0;
					if (expensesReceipts.getRecharge().intValue() == 30) {
						time = 1000L * 60L * 60L * 24L * 30L;
					} else if (expensesReceipts.getRecharge().intValue() == 10) {
						time = 1000L * 60L * 60L * 24L * 7L;
					} else if (expensesReceipts.getRecharge().intValue() == 1) {
						time = 1000L * 60L * 60L * 1L;
					}
					if (time != 0 && roleData != null) {
						UseCardBean limit = roleData.getLimit("VIP");
						if (limit != null) {
							limit.setTime(limit.getTime() + time);
						} else {
//							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半|每天领取268仙玉");
							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半");
							roleData.addLimit(limit);
						}
						applyPayBean.setUseCardBean(limit);
						applyPayBean.setMsg("激活了" + (time / 1000 / 60 / 60 / 24) + "天VIP特权");
					}
				} else if (type == 3 && login.getLowOrHihtpack() == 0) {
					login.setLowOrHihtpack(1);
					applyPayBean.setLowOrHihtpack(new BigDecimal(1));
					applyPayBean.setMsg("开通了小资冲级礼包");
				} else if (type == 4 && login.getLowOrHihtpack() == 0) {
					login.setLowOrHihtpack(2);
					applyPayBean.setLowOrHihtpack(new BigDecimal(2));
					applyPayBean.setMsg("开通了土豪冲级礼包");
				} else {
					applyBean.setPaymoney(expensesReceipts.getYuanbao() + "");// 充值的元宝数量
					login.setCodecard(login.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
					login.setMoney((login.getMoney() != null ? login.getMoney() : 0) + addC.intValue());
					applyPayBean.setAddX(new BigDecimal(applyBean.getPaymoney()));
					applyPayBean.setAddC(addC);
					if (addC.longValue() >= 30 && login.getDayfirstinorno() == 0) {// 在线充值
																					// 添加连充天数
						login.setDayandpayorno(login.getDayandpayorno().add(new BigDecimal(1)));
						login.setDayfirstinorno(1);
						applyPayBean.setDayandpayorno(login.getDayandpayorno());
					}
					StringBuffer buffer = new StringBuffer();
					if (type == 3 || type == 4) {
						buffer.append("小资冲级礼包和土豪冲级礼包只能同时拥有一个,你已经有了");
						buffer.append(login.getLowOrHihtpack() == 2 ? "土豪冲级礼包" : "小资冲级礼包");
						buffer.append("本次充值变为正常仙玉充值.");
					}
					buffer.append("你充值积分:");
					buffer.append(addC.intValue());
					buffer.append(",获得仙玉:");
					buffer.append(applyBean.getPaymoney());
					applyPayBean.setMsg(buffer.toString());
				}
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
				// 确保第一次处理订单(确保充值成功)
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":金额" + expensesReceipts.getRecharge());
				jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", URL + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
				// 发送到数据处理处
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			} else {// 不在线充值
				if (expensesReceipts.getRoleid() != null) {
					login = AllServiceUtil.getRoleTableService().selectRoleID(expensesReceipts.getRoleid());
				} else {
					List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(applyBean.getUserNameS(), null, null);
					if (loginResults.size() != 0) {
						login = loginResults.get(0);
					}
				}
				if (login != null) {
					login.setPaysum(login.getPaysum().add(addC));// 累计充值
					login.setDaypaysum(login.getDaypaysum().add(addC));// 每日累计充值
					LaborScene.addRankValue(0, addC.intValue(), login);//劳动节活动
					if (type == 2) {
						long time = 0;
						if (expensesReceipts.getRecharge().intValue() == 30) {
							time = 1000L * 60L * 60L * 24L * 30L;
						} else if (expensesReceipts.getRecharge().intValue() == 10) {
							time = 1000L * 60L * 60L * 24L * 7L;
						} else if (expensesReceipts.getRecharge().intValue() == 1) {
							time = 1000L * 60L * 60L * 1L;
						}
						PrivateData privateData = new PrivateData();
						privateData.setTimingGood(login.getTimingGood());
						ConcurrentHashMap<String, UseCardBean> limitMap = privateData.initLimit(0);
						UseCardBean limit = limitMap.get("VIP");
						if (limit != null) {
							limit.setTime(limit.getTime() + time);
						} else {
//							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半|每天领取268仙玉");
							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半");
							limitMap.put("VIP", limit);
						}
						StringBuffer buffer = new StringBuffer();
						for (UseCardBean cardBean : limitMap.values()) {
							if (buffer.length() != 0) {
								buffer.append("^");
							}
							buffer.append(cardBean.getName());
							buffer.append("#");
							buffer.append(cardBean.getType());
							buffer.append("#");
							buffer.append(cardBean.getSkin());
							buffer.append("#");
							buffer.append(cardBean.getTime() / 60000);
							if (cardBean.getValue() != null && !cardBean.getValue().equals("")) {
								buffer.append("#");
								buffer.append(cardBean.getValue());
							}
						}
						login.setTimingGood(buffer.toString());
					} else if (type == 3 && login.getLowOrHihtpack() == 0) {
						login.setLowOrHihtpack(1);
					} else if (type == 4 && login.getLowOrHihtpack() == 0) {
						login.setLowOrHihtpack(2);
					} else {
						applyBean.setPaymoney(expensesReceipts.getYuanbao() + "");// 充值的元宝数量
						userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
						userTable.setMoney(userTable.getMoney() + addC.intValue());
						MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
						MonitorUtil.getMoney().addC(addC.longValue());
						if (addC.longValue() >= 30 && login.getDayfirstinorno() == 0) {// 在线充值
																						// 添加连充天数
							login.setDayandpayorno(login.getDayandpayorno().add(new BigDecimal(1)));
							login.setDayfirstinorno(1);
						}
					}
					try {
						AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
					} catch (Exception e) {
						// TODO: handle exception
						WriteOut.addtxt("人物数据保存报错:" + GsonUtil.getGsonUtil().getgson().toJson(login), 9999);
					}
				} else {
					userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					userTable.setMoney(userTable.getMoney() + addC.intValue());
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
				}
				AllServiceUtil.getUserTableService().updateUser(userTable);
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":金额" + expensesReceipts.getRecharge());
				// 发送到数据处理处
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WriteOut.addtxt("充值报错:" + MainServerHandler.getErrorMessage(e), 9999);
		}
		RedisPoolUntil.returnResource(jedis);
		//充值日志
		AllServiceUtil.getRecordService().insert(new Record(8,GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts)));
	}

	/**
	 * 积分回调
	 * 
	 * @param expensesReceipts
	 * @param
	 * @param userTable
	 * @return
	 */
	public void integralCallBack(ExpensesReceipts expensesReceipts,UserTable userTable) {
		Jedis jedis = RedisPoolUntil.getJedis();
		expensesReceipts.setSid(userTable.getQid());
		try {
			ApplyBean applyBean = new ApplyBean();
			applyBean.setUserNameS(expensesReceipts.getPlayeracc());// 充值的帐户名
			applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// 实际充值金额
			BigDecimal addC = new BigDecimal(applyBean.getRealmoney());

			ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
			LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
			if (login != null) {// 在线充值
				AllServiceUtil.getUserTableService().updateUser(userTable);
				login.setPaysum(login.getPaysum().add(addC));// 累计充值
				LaborScene.addRankValue(0, addC.intValue(), login);//劳动节活动
				ApplyPayBean applyPayBean = new ApplyPayBean();
				applyPayBean.setAddM(addC);
				PayvipBean vipBean=GameServer.getVIP(login.getPaysum().longValue());
				RoleData roleData=RolePool.getRoleData(login.getRole_id());
				if (vipBean!=null&&roleData!=null) {
					UseCardBean limit=roleData.getLimit("SVIP");
					if (limit==null) {
						limit=new UseCardBean("VIP"+vipBean.getGrade(),"SVIP","S"+(19+vipBean.getGrade()),-1,vipBean.getIncreationtext());
						roleData.addLimit(limit);
						applyPayBean.setVIPBean(limit);
					}else if (!limit.getName().equals("VIP"+vipBean.getGrade())) {
						limit.setName("VIP"+vipBean.getGrade());
						limit.setSkin("S"+(19+vipBean.getGrade()));
						limit.setValue(vipBean.getIncreationtext());
						applyPayBean.setVIPBean(limit);
					}
				}
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
				// 确保第一次处理订单(确保充值成功)
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":金额" + expensesReceipts.getRecharge());
				jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", URL + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
				// 发送到数据处理处
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			} else {// 不在线充值
				if (expensesReceipts.getRoleid() != null) {
					login = AllServiceUtil.getRoleTableService().selectRoleID(expensesReceipts.getRoleid());
				} else {
					List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(applyBean.getUserNameS(), null, null);
					if (loginResults.size() != 0) {
						login = loginResults.get(0);
					}
				}
				if (login != null) {
					login.setPaysum(login.getPaysum().add(addC));// 累计充值
					LaborScene.addRankValue(0, addC.intValue(), login);//劳动节活动
					try {
						AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
					} catch (Exception e) {
						// TODO: handle exception
						WriteOut.addtxt("人物数据保存报错:" + GsonUtil.getGsonUtil().getgson().toJson(login), 9999);
					}
				} else {
					userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					userTable.setMoney(userTable.getMoney() + addC.intValue());
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
				}
				AllServiceUtil.getUserTableService().updateUser(userTable);
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":金额" + expensesReceipts.getRecharge());
				// 发送到数据处理处
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WriteOut.addtxt("充值报错:" + MainServerHandler.getErrorMessage(e), 9999);
		}
		RedisPoolUntil.returnResource(jedis);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
