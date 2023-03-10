package com.gl.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.ApplyBean;
import org.come.bean.ApplyPayBean;
import org.come.bean.BackRoleInfo;
import org.come.bean.ExpensesReceiptsInfo;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.ExpensesReceipts;
import org.come.entity.Haters;
import org.come.entity.PayvipBean;
import org.come.entity.Record;
import org.come.entity.RoleTable;
import org.come.entity.UserTable;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.redis.RedisPoolUntil;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.gl.model.Param;

import come.tool.Role.PrivateData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.LaborDay.LaborScene;
import io.netty.channel.ChannelHandlerContext;
import redis.clients.jedis.Jedis;

public class PlayerService {

   private static final int PageSize = 10;

   /**
    *
    */
   public BackRoleInfo getRole(Param param){

      String type = param.getValue1();
      String value = param.getValue2();

      int pageNum = param.getPageNum();
      int status = param.getStatus();
      int size = param.getPageSize();

      if (size < 10) {
         size = PageSize;
      }

      BackRoleInfo list = null;

      RoleTable roleTable=new RoleTable();
      roleTable.setQid(null);
      roleTable.setStart((pageNum - 1) * size);
      roleTable.setEnd(pageNum * size);


      switch (status) {
         case 3:
            roleTable.setUnknown("1");
            break;
         case 4:
            roleTable.setActivity(new Short(1+""));
            break;
         case 5:
            roleTable.setActivity(new Short(0+""));
            break;
         default:
            roleTable.setActivity(null);
            break;
      }
      if (StringUtil.isNotEmpty(type) && !"undefined".equals(type) && StringUtil.isNotEmpty(value) && !"undefined".equals(value)) {
         //???????????????
         if(type.equals("1") && NumberUtils.isDigits(value)) {
            roleTable.setRole_id(new BigDecimal(value));
         } else if(type.equals("2")) {
            roleTable.setRolename(value);
         } else if(type.equals("3")) {
            roleTable.setLocalname(value);
         }
      }

      //??????????????????????????????
      int total = AllServiceUtil.getUserTableService().selectSumForRoleUserHaterNumber(roleTable);
      //?????????
      int page = total / size;
      if (total % size > 0) {
         page++;
      }
      roleTable.setUserString(" Order By role_id ASC");
      //????????????????????????
      List<RoleTable> listall=AllServiceUtil.getUserTableService().selectSumForRoleUserHaterList(roleTable);

      list=new BackRoleInfo();
      //?????????????????????
      for (RoleTable roleInfo : listall) {
         // ????????????1????????? 2????????? 3????????? 4?????????5????????????  6????????????
         if (GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null) {
            roleInfo.setStatues("??????");
         } else {
            roleInfo.setStatues("??????");
         }
         roleInfo.setUnknown(StringUtil.isEmpty(roleInfo.getUnknown()) ? "0" : roleInfo.getUnknown());
         // ?????????????????????????????????????????????
         roleInfo.setPassword(null);
      }


      list.setList(listall);
      list.setPages(page);
      list.setPageNum(pageNum);
      list.setTotal(total);
      return list;
   }


   /**
    * ????????????ID???????????????
    * @param param		value1 ??????ID   value2 ????????????
    * @return
    */
   public boolean editLockPassword(Param param) {
      // ????????????ID
      String roleid = param.getValue1();
      // ????????????????????????
      String goodsecret = param.getValue2();

      RoleTable roleTable=new RoleTable();
      roleTable.setRole_id(new BigDecimal(roleid));
      roleTable.setPassword(goodsecret);

      int flag=AllServiceUtil.getRoleTableService().updateRolePwdForRid(roleTable);
      if(flag>0){
         return true;
      }else{
         return false;
      }
   }

   /**
    * ????????????
    * @param param   velue1  ?????????       value2 1.???????????? 2.??????????????? 3.???????????? 4.???????????? 5.????????????   	value3  ????????????
    * @return
    */
   public boolean operation(Param param) {
      // ???????????????
      String roleName = param.getValue1();
      // ?????????????????? 1.???????????? 2.??????????????? 3.???????????? 4.???????????? 5.????????????
      String type = param.getValue2();
      // ??????
      String reason = param.getValue3();
      // ?????????
      String controlname = "ADMIN";

      if (StringUtil.isEmpty(type)) {
         return false;
      }
      int control = Integer.parseInt(type);

      if (control != 7 && control != 8) {
         // ????????????
         RoleTable roleInfo = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(roleName);
         if (roleInfo == null) {
            return false;
         }
         UserTable userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(roleInfo.getUser_id());
         if (userInfo == null) {
            return false;
         }
         // ????????????
         switch (control) {
            case 1:
               // ??????	???????????????
               Haters hater = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
               if (hater == null) {
                  // ??????
                  Haters record = new Haters();
                  record.setRoleid(roleInfo.getRole_id());
                  AllServiceUtil.getHatersService().insert(record);
                  // ????????????
                  if (GameServer.getRoleNameMap().get(roleName) != null) {
                     SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().tipAgreement("???????????????????????????????????????"));
                  }
               }
               return true;
            case 2:
               // ?????????????????????
               if (GameServer.getRoleNameMap().get(roleName) != null) {
                  SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().serverstopAgreement());
               }
               return true;
            case 3:
               // ??????
               if (GameServer.getRoleNameMap().get(roleName) != null) {
                  ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getRoleNameMap().get(roleName), userInfo.getUsername());
               } else {
                  // ???????????????
                  UserTable table = new UserTable();
                  table.setUsername(userInfo.getUsername());
                  table.setActivity((short) 1);
                  // ??????????????????
                  AllServiceUtil.getUserTableService().updateUser(table);
                  AllServiceUtil.getUserTableService().addRufenghaoControl(userInfo, roleInfo.getRolename(),reason,controlname,1);
               }
               return true;
            case 4:
               // ????????????  	???????????????
               Haters hater2 = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
               if (hater2 != null) {
                  // ??????
                  AllServiceUtil.getHatersService().deleteByPrimaryKey(hater2.getRoleid());
                  // ??????????????????
                  if (GameServer.getRoleNameMap().get(roleName) != null) {
                     SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().tipAgreement("??????????????????"));
                  }
               }
               return true;
            case 5:
               // ???????????? 	???????????????
               UserTable table = new UserTable();
               table.setUsername(userInfo.getUsername());
               table.setActivity((short) 0);
               // ??????????????????
               AllServiceUtil.getUserTableService().updateUser(table);
               AllServiceUtil.getUserTableService().addRufenghaoControl(userInfo, roleInfo.getRolename(),reason,controlname,2);
               return true;
            default:
               return false;
         }
      }
      return false;
   }


   /**
    * ??????	?????? 1???????????? 2??????????????? 3???????????????????????? 4????????????????????????
    * @param param
    * @return
    */
   public boolean rechargeCallBack(Param param) {
      // ??????ID
      String user_id = param.getValue1();

      // ??????/VIP??????
      String recharge = param.getValue2();

      // ??????
      String yuanbao = param.getValue3();

      // ??????????????????
      String count = param.getValue4();

      // ????????????
      String saveType = param.getValue5();

      if(StringUtil.isEmpty(saveType)) {
         return false;
      }

      int type = Integer.parseInt(saveType);

      if(StringUtil.isEmpty(user_id)) {
         return false;
      }

      if(StringUtil.isEmpty(yuanbao)) {
         yuanbao = "0";
      }

      BigDecimal userId = new BigDecimal(user_id);

      UserTable userTable = AllServiceUtil.getUserTableService().selectByPrimaryKey(userId);

      Random r = new Random(System.currentTimeMillis());
      ExpensesReceipts expensesReceipts = new ExpensesReceipts();
      expensesReceipts.setErid(new BigDecimal(System.currentTimeMillis() + "" + r.nextInt(9999)));
      expensesReceipts.setPlayeracc(userTable.getUsername());
      expensesReceipts.setSid(userTable.getQid());
      expensesReceipts.setRecharge(new BigDecimal(recharge));
      expensesReceipts.setYuanbao(new BigDecimal(yuanbao));
      expensesReceipts.setType(type);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.HOUR_OF_DAY,8);
      expensesReceipts.setPaytime(DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

      Jedis jedis = RedisPoolUntil.getJedis();

      try {
         ApplyBean applyBean = new ApplyBean();
         applyBean.setUserNameS(expensesReceipts.getPlayeracc());// ??????????????????
         applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// ??????????????????
         BigDecimal addC = new BigDecimal(applyBean.getRealmoney());
         // ???????????? 1???????????? 2??????????????? 3???????????????????????? 4????????????????????????

         userTable.setPayintegration(userTable.getPayintegration() + addC.intValue());
         ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
         LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
         if (login != null) {// ????????????
            AllServiceUtil.getUserTableService().updateUser(userTable);
            login.setPaysum(login.getPaysum().add(addC));// ????????????
            login.setDaypaysum(login.getDaypaysum().add(addC));// ??????????????????
            if (StringUtil.isNotEmpty(count) && !"undefined".equals(count)) {
               LaborScene.addRankValue(0, Integer.parseInt(count) * 10, login);//???????????????
            }
            ApplyPayBean applyPayBean = new ApplyPayBean();
            applyPayBean.setAddM(addC);
            expensesReceipts.setRoleid(login.getRole_id());
            expensesReceipts.setBuyroleName(login.getRolename());
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
               long time = 1000L * 60L * 60L * 24L * expensesReceipts.getRecharge().intValue();
               if (time != 0 && roleData != null) {
                  UseCardBean limit = roleData.getLimit("VIP");
                  if (limit != null) {
                     limit.setTime(limit.getTime() + time);
                  } else {
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
            // ???????????????????????????
            AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
            SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
            // ???????????????????????????(??????????????????)
            jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":??????" + expensesReceipts.getRecharge());
            jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", "Sinmahod" + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
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
               if (StringUtil.isNotEmpty(count) && !"undefined".equals(count)) {
                  LaborScene.addRankValue(0, Integer.parseInt(count) * 10, login);//???????????????
               }
               expensesReceipts.setRoleid(login.getRole_id());
               expensesReceipts.setBuyroleName(login.getRolename());
               if (type == 2) {
                  long time = 1000L * 60L * 60L * expensesReceipts.getRecharge().intValue();
                  PrivateData privateData = new PrivateData();
                  privateData.setTimingGood(login.getTimingGood());
                  ConcurrentHashMap<String, UseCardBean> limitMap = privateData.initLimit(0);
                  UseCardBean limit = limitMap.get("VIP");
                  if (limit != null) {
                     limit.setTime(limit.getTime() + time);
                  } else {
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
         }
      } catch (Exception e) {
         e.printStackTrace();
         WriteOut.addtxt("????????????:" + MainServerHandler.getErrorMessage(e), 9999);
      }
      RedisPoolUntil.returnResource(jedis);
      //????????????
      AllServiceUtil.getRecordService().insert(new Record(8,GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts)));
      AllServiceUtil.getExpensesReceiptsService().insert(expensesReceipts);
      return true;
   }



   /**
    *
    */
   public ExpensesReceiptsInfo getReceipts(Param param){

      String searchType = param.getValue1();
      String searchValue = param.getValue2();

      String type = param.getValue3();

      int pageNum = param.getPageNum();
      int size = param.getPageSize();

      if (size < 15) {
         size = 15;
      }

      ExpensesReceipts expensesReceipts = new ExpensesReceipts();
      if (StringUtil.isNotEmpty(type) || NumberUtils.isDigits(type)) {
         expensesReceipts.setType(Integer.parseInt(type));
      }

      if (StringUtil.isNotEmpty(searchType)) {
         if ("3".equals(searchType) && StringUtil.isNotEmpty(searchValue)) {
            expensesReceipts.setBuyroleName(searchValue);
         }

         if ("2".equals(searchType) && StringUtil.isNotEmpty(searchValue) || NumberUtils.isDigits(searchValue)) {
            expensesReceipts.setRoleid(new BigDecimal(searchValue));
         }

         if ("1".equals(searchType) && StringUtil.isNotEmpty(searchValue)) {
            expensesReceipts.setPlayeracc(searchValue);
         }
      }

      // ??????????????????
      int total = AllServiceUtil.getExpensesReceiptsService().selectAllTotal(expensesReceipts);
      //?????????
      int page = total / size;
      if (total % size > 0) {
         page++;
      }

      // ????????????
      PageHelper.startPage(pageNum, size);
      List<ExpensesReceipts> list = AllServiceUtil.getExpensesReceiptsService().selectAll(expensesReceipts);
      PageInfo<ExpensesReceipts> pageInfo = new PageInfo<>(list);

      ExpensesReceiptsInfo info = new ExpensesReceiptsInfo();
      info.setList(pageInfo.getList());
      info.setPages(page);
      info.setPageNum(pageNum);
      info.setTotal(total);
      return info;
   }

}
