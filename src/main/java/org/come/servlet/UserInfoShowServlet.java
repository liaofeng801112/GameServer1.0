
package org.come.servlet;

import come.tool.Role.PrivateData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.LaborDay.LaborScene;
import io.netty.channel.ChannelHandlerContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.action.monitor.MonitorUtil;
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
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class UserInfoShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ApiValid = null;

    public UserInfoShowServlet() {
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> returnData = new HashMap();
        String type = request.getParameter("type");
        String mKey = request.getParameter("mkey");
        String VALID_NAME = request.getParameter(ApiValid);
        Boolean passed = true;
        passed = true;
        returnData.put("status", 400);
        returnData.put("message", "非法请求，禁止通讯。已踢出");
        if (passed) {
            if (type.equals("gift")) {
                try {
                    String userName = request.getParameter("userName");
                    String czType = request.getParameter("czType");
                    UserTable userTable = AllServiceUtil.getUserTableService().selectForUsername(userName);
                    if (userTable != null) {
                        if (this.GiftSend(userName, userTable, Integer.parseInt(czType))) {
                            returnData.put("status", 200);
                        }
                    } else {
                        returnData.put("status", 400);
                        returnData.put("message", "无法定位该帐号,此帐号可能不存在。");
                    }
                } catch (Exception var24) {
                    returnData.put("status", 400);
                    returnData.put("message", "[出错啦]" + var24.getMessage());
                }
            } else if (type.equals("insert")) {
                try {
                    Properties properties = new Properties();
                    InputStream in = GameServer.class.getClassLoader().getResourceAsStream("important.properties");
                    properties.load(in);
                    String userName = request.getParameter("userName");
                    String codeCard = request.getParameter("codeCard");
                    int moneyRate = Integer.parseInt(properties.getProperty("server.moneyRate"));
                    int currCodeCard = Integer.parseInt(codeCard);
                    UserTable userTable = AllServiceUtil.getUserTableService().selectForUsername(userName);
                    if (userTable != null) {
                        BigDecimal allCards = null;
                        List<LoginResult> loginResults = GameServer.gameServer.getUserRole(userTable.getUser_id());
                        if (loginResults != null) {
                            ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(userName);

                            for(int m = 0; m < loginResults.size(); ++m) {
                                LoginResult login = loginResults.get(m);
                                allCards = loginResults.get(m).getCodecard().add(new BigDecimal(currCodeCard * moneyRate));
                                login.setCodecard(allCards);
                                ApplyPayBean applyPayBean = new ApplyPayBean();
                                applyPayBean.setAddC(new BigDecimal(currCodeCard));
                                applyPayBean.setAddX(new BigDecimal(currCodeCard * moneyRate));
                                login.setPaysum(login.getPaysum().add(new BigDecimal(currCodeCard)));
                                login.setDaypaysum(login.getDaypaysum().add(new BigDecimal(currCodeCard)));
                                LaborScene.addRankValue(0, currCodeCard, login);
                                login.setDayandpayorno(login.getDayandpayorno().add(new BigDecimal(1)));
                                login.setDayfirstinorno(1);
                                MonitorUtil.getMoney().addX((new BigDecimal(currCodeCard * moneyRate)).longValue(), 0);
                                MonitorUtil.getMoney().addC(currCodeCard);
                                PayvipBean vipBean = GameServer.getVIP(login.getPaysum().longValue());
                                RoleData roleData = RolePool.getRoleData(login.getRole_id());
                                if (vipBean != null && roleData != null) {
                                    UseCardBean limit = roleData.getLimit("SVIP");
                                    if (limit == null) {
                                        limit = new UseCardBean("VIP" + vipBean.getGrade(), "SVIP", "S" + (19 + vipBean.getGrade()), -1L, vipBean.getIncreationtext());
                                        roleData.addLimit(limit);
                                        applyPayBean.setVIPBean(limit);
                                    } else if (!limit.getName().equals("VIP" + vipBean.getGrade())) {
                                        limit.setName("VIP" + vipBean.getGrade());
                                        limit.setSkin("S" + (19 + vipBean.getGrade()));
                                        limit.setValue(vipBean.getIncreationtext());
                                        applyPayBean.setVIPBean(limit);
                                    }
                                }

                                applyPayBean.setDayandpayorno(login.getDayandpayorno());
                                applyPayBean.setMsg("恭喜您成功充值" + currCodeCard * moneyRate + "仙玉，" + currCodeCard + "积分，如果尚未到账可能是刷新延迟，请重新登录查看。");
                                SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
                            }
                        } else {
                            BigDecimal cards = userTable.getCodecard();
                            allCards = cards.add(new BigDecimal(currCodeCard * moneyRate));
                        }

                        userTable.setPayintegration(userTable.getPayintegration() + Integer.parseInt(codeCard));
                        userTable.setCodecard(allCards);
                        userTable.setMoney(userTable.getMoney() + currCodeCard);
                        AllServiceUtil.getUserTableService().updateUser(userTable);
                        ExpensesReceipts record = new ExpensesReceipts();
                        record.setPlayeracc(userName);
                        record.setRecharge(new BigDecimal(codeCard));
                        record.setPlayerpay(new BigDecimal(0));
                        record.setSid(new BigDecimal(100));
                        record.setYuanbao(new BigDecimal(currCodeCard * moneyRate));
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        record.setPaytime(df.format(new Date()));
                        AllServiceUtil.getExpensesReceiptsService().insert(record);
                        returnData.put("status", 200);
                    } else {
                        returnData.put("status", 400);
                        returnData.put("message", "无法定位该帐号,此帐号可能不存在。");
                    }
                } catch (Exception var25) {
                    returnData.put("status", 400);
                    returnData.put("message", "[出错啦]" + var25.getMessage());
                }
            } else {
                new ArrayList();
                ExpensesReceipts record = new ExpensesReceipts();
                record.setSid(new BigDecimal(100));
                List<ExpensesReceipts> userTables = AllServiceUtil.getExpensesReceiptsService().selectAllForAreaId(record);
                returnData.put("list", userTables);
                returnData.put("pageNum", 1);
                returnData.put("pages", 1);
                returnData.put("status", 200);
            }
        }

        PrintWriter pwPrintWriter = response.getWriter();
        pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        pwPrintWriter.flush();
        pwPrintWriter.close();
    }

    private boolean ApiValid(String token, String username) {
        return false;
    }

    private boolean GiftSend(String userName, UserTable userTable, int type) {
        try {
            ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(userName);
            LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
            if (login != null) {
                ApplyPayBean applyPayBean = new ApplyPayBean();
                RoleData roleData = RolePool.getRoleData(login.getRole_id());
                PayvipBean vipBean = GameServer.getVIP(login.getPaysum().longValue());
                if (vipBean != null && roleData != null) {
                    UseCardBean limit = roleData.getLimit("SVIP");
                    if (limit == null) {
                        limit = new UseCardBean("VIP" + vipBean.getGrade(), "SVIP", "S" + (19 + vipBean.getGrade()), -1L, vipBean.getIncreationtext());
                        roleData.addLimit(limit);
                        applyPayBean.setVIPBean(limit);
                    } else if (!limit.getName().equals("VIP" + vipBean.getGrade())) {
                        limit.setName("VIP" + vipBean.getGrade());
                        limit.setSkin("S" + (19 + vipBean.getGrade()));
                        limit.setValue(vipBean.getIncreationtext());
                        applyPayBean.setVIPBean(limit);
                        applyPayBean.setVIPBean(limit);
                    }
                }

                if (type != 1 && type != 2) {
                    if (type == 3 && login.getLowOrHihtpack() == 0) {
                        login.setLowOrHihtpack(1);
                        applyPayBean.setLowOrHihtpack(new BigDecimal(1));
                        applyPayBean.setMsg("恭喜您开通了小资冲级礼包");
                    } else if (type == 4 && login.getLowOrHihtpack() == 0) {
                        login.setLowOrHihtpack(2);
                        applyPayBean.setLowOrHihtpack(new BigDecimal(2));
                        applyPayBean.setMsg("恭喜您开通了土豪冲级礼包");
                    }
                } else {
                    long time = 86400000L;
                    if (type == 2) {
                        time *= 30L;
                    } else {
                        time *= 7L;
                    }

                    if (time != 0L && roleData != null) {
                        UseCardBean  limit = roleData.getLimit("VIP");
                        if (limit != null) {
                            limit.setTime(limit.getTime() + time);
                        } else {
                            limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚,血法|人物死亡惩罚减半");
                            roleData.addLimit(limit);
                        }

                        applyPayBean.setUseCardBean(limit);
                        applyPayBean.setMsg("恭喜您激活了" + time / 1000L / 60L / 60L / 24L + "天VIP特权");
                    }
                }

                SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
            } else {
                List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(userName, null, null);
                if (loginResults.size() != 0) {
                    login = loginResults.get(0);
                }

                if (login == null) {
                    return false;
                }

                if (type != 1 && type != 2) {
                    if (type == 3 && login.getLowOrHihtpack() == 0) {
                        login.setLowOrHihtpack(1);
                    } else if (type == 4 && login.getLowOrHihtpack() == 0) {
                        login.setLowOrHihtpack(2);
                    }
                } else {
                    long time = 86400000L;
                    if (type == 2) {
                        time *= 30L;
                    } else {
                        time *= 7L;
                    }

                    PrivateData privateData = new PrivateData();
                    privateData.setTimingGood(login.getTimingGood());
                    ConcurrentHashMap<String, UseCardBean> limitMap = privateData.initLimit(0L);
                    UseCardBean limit = limitMap.get("VIP");
                    if (limit != null) {
                        limit.setTime(limit.getTime() + time);
                    } else {
                        limit = new UseCardBean("VIP", "VIP", "1", System.currentTimeMillis() + time, "掉落率=1|经验加成=5|加强全系法术=5|召唤兽死亡不掉忠诚及血法|人物死亡惩罚减半");
                        limitMap.put("VIP", limit);
                    }

                    StringBuffer buffer = new StringBuffer();
                    Iterator var14 = limitMap.values().iterator();

                    while(var14.hasNext()) {
                        UseCardBean cardBean = (UseCardBean)var14.next();
                        if (buffer != null && buffer.length() != 0) {
                            buffer.append("^");
                        }

                        buffer.append(cardBean.getName());
                        buffer.append("#");
                        buffer.append(cardBean.getType());
                        buffer.append("#");
                        buffer.append(cardBean.getSkin());
                        buffer.append("#");
                        buffer.append(cardBean.getTime() / 60000L);
                        if (cardBean.getValue() != null && !cardBean.getValue().equals("")) {
                            buffer.append("#");
                            buffer.append(cardBean.getValue());
                        }
                    }

                    login.setTimingGood(buffer.toString());
                }

                try {
                    AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
                } catch (Exception var15) {
                    WriteOut.addtxt("人物数据保存报错:" + GsonUtil.getGsonUtil().getgson().toJson(login), 9999L);
                }

                AllServiceUtil.getUserTableService().updateUser(userTable);
            }
        } catch (Exception var16) {
            var16.printStackTrace();
            WriteOut.addtxt("充值报错:" + MainServerHandler.getErrorMessage(var16), 9999L);
            return false;
        }

        String[] giftStr = new String[]{"周卡充值", "月卡充值", "小资冲级礼包充值", "土豪冲级礼包充值"};
        AllServiceUtil.getRecordService().insert(new Record(7, userName + "充值礼包:" + giftStr[type - 1]));
        return true;
    }

    public void init() throws ServletException {
    }
}
