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
		/** ?????????????????? */
		// ExpensesReceipts expensesReceipts = new ExpensesReceipts();
		// expensesReceipts.setErid(new BigDecimal(mes));
		// expensesReceipts.setPlayeracc("ddd");
		// // expensesReceipts.setSid(new BigDecimal(20616));
		// expensesReceipts.setRecharge(new BigDecimal("100"));
		// expensesReceipts.setYuanbao(new BigDecimal("2000"));
		/** ??????????????????end */
		ExpensesReceipts expensesReceipts = GsonUtil.getGsonUtil().getgson().fromJson(mes, ExpensesReceipts.class);
		UserTable userTable = AllServiceUtil.getUserTableService().selectForUsername(expensesReceipts.getPlayeracc());
		BigDecimal sid = expensesReceipts.getSid();
		int con = -1;
		// ???Id??????
		// if ("1".equals(type)) {
		expensesReceipts.setSid(userTable.getQid());
		// ????????????????????????
		// con = Integer.parseInt(APIHttpClient.sendMesStr(URL,
		// GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts)));
		// } else
		if ("2".equals(type)) {// ????????????
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
	 * ????????????
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
			applyBean.setUserNameS(expensesReceipts.getPlayeracc());// ??????????????????
			applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// ??????????????????
			BigDecimal addC = new BigDecimal(applyBean.getRealmoney());
			int type = expensesReceipts.getType();
			// ???????????? 1???????????? 2??????????????? 3???????????????????????? 4????????????????????????

			userTable.setPayintegration(userTable.getPayintegration() + addC.intValue());
			ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
			LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
			if (login != null) {// ????????????
				AllServiceUtil.getUserTableService().updateUser(userTable);
				login.setPaysum(login.getPaysum().add(addC));// ????????????
				login.setDaypaysum(login.getDaypaysum().add(addC));// ??????????????????
				LaborScene.addRankValue(0, addC.intValue(), login);//???????????????
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
//							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "?????????=1|????????????=5|??????????????????=5|???????????????????????????,??????|????????????????????????|????????????268??????");
							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "?????????=1|????????????=5|??????????????????=5|???????????????????????????,??????|????????????????????????");
							roleData.addLimit(limit);
						}
						applyPayBean.setUseCardBean(limit);
						applyPayBean.setMsg("?????????" + (time / 1000 / 60 / 60 / 24) + "???VIP??????");
					}
				} else if (type == 3 && login.getLowOrHihtpack() == 0) {
					login.setLowOrHihtpack(1);
					applyPayBean.setLowOrHihtpack(new BigDecimal(1));
					applyPayBean.setMsg("???????????????????????????");
				} else if (type == 4 && login.getLowOrHihtpack() == 0) {
					login.setLowOrHihtpack(2);
					applyPayBean.setLowOrHihtpack(new BigDecimal(2));
					applyPayBean.setMsg("???????????????????????????");
				} else {
					applyBean.setPaymoney(expensesReceipts.getYuanbao() + "");// ?????????????????????
					login.setCodecard(login.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
					login.setMoney((login.getMoney() != null ? login.getMoney() : 0) + addC.intValue());
					applyPayBean.setAddX(new BigDecimal(applyBean.getPaymoney()));
					applyPayBean.setAddC(addC);
					if (addC.longValue() >= 30 && login.getDayfirstinorno() == 0) {// ????????????
																					// ??????????????????
						login.setDayandpayorno(login.getDayandpayorno().add(new BigDecimal(1)));
						login.setDayfirstinorno(1);
						applyPayBean.setDayandpayorno(login.getDayandpayorno());
					}
					StringBuffer buffer = new StringBuffer();
					if (type == 3 || type == 4) {
						buffer.append("???????????????????????????????????????????????????????????????,???????????????");
						buffer.append(login.getLowOrHihtpack() == 2 ? "??????????????????" : "??????????????????");
						buffer.append("????????????????????????????????????.");
					}
					buffer.append("???????????????:");
					buffer.append(addC.intValue());
					buffer.append(",????????????:");
					buffer.append(applyBean.getPaymoney());
					applyPayBean.setMsg(buffer.toString());
				}
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
				// ???????????????????????????(??????????????????)
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":??????" + expensesReceipts.getRecharge());
				jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", URL + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
				// ????????????????????????
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			} else {// ???????????????
				if (expensesReceipts.getRoleid() != null) {
					login = AllServiceUtil.getRoleTableService().selectRoleID(expensesReceipts.getRoleid());
				} else {
					List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(applyBean.getUserNameS(), null, null);
					if (loginResults.size() != 0) {
						login = loginResults.get(0);
					}
				}
				if (login != null) {
					login.setPaysum(login.getPaysum().add(addC));// ????????????
					login.setDaypaysum(login.getDaypaysum().add(addC));// ??????????????????
					LaborScene.addRankValue(0, addC.intValue(), login);//???????????????
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
//							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "?????????=1|????????????=5|??????????????????=5|???????????????????????????,??????|????????????????????????|????????????268??????");
							limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "?????????=1|????????????=5|??????????????????=5|???????????????????????????,??????|????????????????????????");
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
						applyBean.setPaymoney(expensesReceipts.getYuanbao() + "");// ?????????????????????
						userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
						userTable.setMoney(userTable.getMoney() + addC.intValue());
						MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
						MonitorUtil.getMoney().addC(addC.longValue());
						if (addC.longValue() >= 30 && login.getDayfirstinorno() == 0) {// ????????????
																						// ??????????????????
							login.setDayandpayorno(login.getDayandpayorno().add(new BigDecimal(1)));
							login.setDayfirstinorno(1);
						}
					}
					try {
						AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
					} catch (Exception e) {
						// TODO: handle exception
						WriteOut.addtxt("????????????????????????:" + GsonUtil.getGsonUtil().getgson().toJson(login), 9999);
					}
				} else {
					userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					userTable.setMoney(userTable.getMoney() + addC.intValue());
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
				}
				AllServiceUtil.getUserTableService().updateUser(userTable);
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":??????" + expensesReceipts.getRecharge());
				// ????????????????????????
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WriteOut.addtxt("????????????:" + MainServerHandler.getErrorMessage(e), 9999);
		}
		RedisPoolUntil.returnResource(jedis);
		//????????????
		AllServiceUtil.getRecordService().insert(new Record(8,GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts)));
	}

	/**
	 * ????????????
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
			applyBean.setUserNameS(expensesReceipts.getPlayeracc());// ??????????????????
			applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// ??????????????????
			BigDecimal addC = new BigDecimal(applyBean.getRealmoney());

			ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
			LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
			if (login != null) {// ????????????
				AllServiceUtil.getUserTableService().updateUser(userTable);
				login.setPaysum(login.getPaysum().add(addC));// ????????????
				LaborScene.addRankValue(0, addC.intValue(), login);//???????????????
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
				// ???????????????????????????(??????????????????)
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":??????" + expensesReceipts.getRecharge());
				jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", URL + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
				// ????????????????????????
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			} else {// ???????????????
				if (expensesReceipts.getRoleid() != null) {
					login = AllServiceUtil.getRoleTableService().selectRoleID(expensesReceipts.getRoleid());
				} else {
					List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(applyBean.getUserNameS(), null, null);
					if (loginResults.size() != 0) {
						login = loginResults.get(0);
					}
				}
				if (login != null) {
					login.setPaysum(login.getPaysum().add(addC));// ????????????
					LaborScene.addRankValue(0, addC.intValue(), login);//???????????????
					try {
						AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
					} catch (Exception e) {
						// TODO: handle exception
						WriteOut.addtxt("????????????????????????:" + GsonUtil.getGsonUtil().getgson().toJson(login), 9999);
					}
				} else {
					userTable.setCodecard(userTable.getCodecard().add(new BigDecimal(applyBean.getPaymoney())));
					userTable.setMoney(userTable.getMoney() + addC.intValue());
					MonitorUtil.getMoney().addX(new BigDecimal(applyBean.getPaymoney()).longValue(), 0);
					MonitorUtil.getMoney().addC(addC.longValue());
				}
				AllServiceUtil.getUserTableService().updateUser(userTable);
				jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":??????" + expensesReceipts.getRecharge());
				// ????????????????????????
				APIHttpClient.sendMes(URL, GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WriteOut.addtxt("????????????:" + MainServerHandler.getErrorMessage(e), 9999);
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
