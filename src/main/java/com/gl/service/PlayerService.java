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
         //设置角色名
         if(type.equals("1") && NumberUtils.isDigits(value)) {
            roleTable.setRole_id(new BigDecimal(value));
         } else if(type.equals("2")) {
            roleTable.setRolename(value);
         } else if(type.equals("3")) {
            roleTable.setLocalname(value);
         }
      }

      //查询总区域得玩家信息
      int total = AllServiceUtil.getUserTableService().selectSumForRoleUserHaterNumber(roleTable);
      //总页数
      int page = total / size;
      if (total % size > 0) {
         page++;
      }
      roleTable.setUserString(" Order By role_id ASC");
      //查询状态下的角色
      List<RoleTable> listall=AllServiceUtil.getUserTableService().selectSumForRoleUserHaterList(roleTable);

      list=new BackRoleInfo();
      //进行状态实例化
      for (RoleTable roleInfo : listall) {
         // 玩家状态1、在线 2、下线 3、禁言 4、封号5、未封号  6、未禁言
         if (GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null) {
            roleInfo.setStatues("在线");
         } else {
            roleInfo.setStatues("离线");
         }
         roleInfo.setUnknown(StringUtil.isEmpty(roleInfo.getUnknown()) ? "0" : roleInfo.getUnknown());
         // 清空密码，不将用户密码传到前端
         roleInfo.setPassword(null);
      }


      list.setList(listall);
      list.setPages(page);
      list.setPageNum(pageNum);
      list.setTotal(total);
      return list;
   }


   /**
    * 根据角色ID修改解锁码
    * @param param		value1 角色ID   value2 新解锁码
    * @return
    */
   public boolean editLockPassword(Param param) {
      // 获取角色ID
      String roleid = param.getValue1();
      // 修改的角色解锁码
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
    * 玩家控制
    * @param param   velue1  角色名       value2 1.表示禁言 2.表示踢下线 3.表示封号 4.解开禁言 5.解封账号   	value3  封号理由
    * @return
    */
   public boolean operation(Param param) {
      // 获取角色名
      String roleName = param.getValue1();
      // 获取操作标识 1.表示禁言 2.表示踢下线 3.表示封号 4.解开禁言 5.解封账号
      String type = param.getValue2();
      // 理由
      String reason = param.getValue3();
      // 操作人
      String controlname = "ADMIN";

      if (StringUtil.isEmpty(type)) {
         return false;
      }
      int control = Integer.parseInt(type);

      if (control != 7 && control != 8) {
         // 查询角色
         RoleTable roleInfo = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(roleName);
         if (roleInfo == null) {
            return false;
         }
         UserTable userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(roleInfo.getUser_id());
         if (userInfo == null) {
            return false;
         }
         // 执行操作
         switch (control) {
            case 1:
               // 禁言	查找黑名单
               Haters hater = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
               if (hater == null) {
                  // 禁言
                  Haters record = new Haters();
                  record.setRoleid(roleInfo.getRole_id());
                  AllServiceUtil.getHatersService().insert(record);
                  // 提示禁言
                  if (GameServer.getRoleNameMap().get(roleName) != null) {
                     SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().tipAgreement("你的行为违规，已被系统禁言"));
                  }
               }
               return true;
            case 2:
               // 踢下线断开连接
               if (GameServer.getRoleNameMap().get(roleName) != null) {
                  SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().serverstopAgreement());
               }
               return true;
            case 3:
               // 封号
               if (GameServer.getRoleNameMap().get(roleName) != null) {
                  ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getRoleNameMap().get(roleName), userInfo.getUsername());
               } else {
                  // 获取账号名
                  UserTable table = new UserTable();
                  table.setUsername(userInfo.getUsername());
                  table.setActivity((short) 1);
                  // 修改用户信息
                  AllServiceUtil.getUserTableService().updateUser(table);
                  AllServiceUtil.getUserTableService().addRufenghaoControl(userInfo, roleInfo.getRolename(),reason,controlname,1);
               }
               return true;
            case 4:
               // 解开禁言  	查找黑名单
               Haters hater2 = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
               if (hater2 != null) {
                  // 禁言
                  AllServiceUtil.getHatersService().deleteByPrimaryKey(hater2.getRoleid());
                  // 提示解除禁言
                  if (GameServer.getRoleNameMap().get(roleName) != null) {
                     SendMessage.sendMessageByRoleName(roleName, Agreement.getAgreement().tipAgreement("禁言已被解除"));
                  }
               }
               return true;
            case 5:
               // 解封账号 	获取账号名
               UserTable table = new UserTable();
               table.setUsername(userInfo.getUsername());
               table.setActivity((short) 0);
               // 修改用户信息
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
    * 充值	类型 1仙玉充值 2周月卡充值 3小资冲级礼包充值 4土豪冲级礼包字段
    * @param param
    * @return
    */
   public boolean rechargeCallBack(Param param) {
      // 用户ID
      String user_id = param.getValue1();

      // 金额/VIP天数
      String recharge = param.getValue2();

      // 仙玉
      String yuanbao = param.getValue3();

      // 赠送抽奖次数
      String count = param.getValue4();

      // 充值类型
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
         applyBean.setUserNameS(expensesReceipts.getPlayeracc());// 充值的帐户名
         applyBean.setRealmoney(expensesReceipts.getRecharge() + "");// 实际充值金额
         BigDecimal addC = new BigDecimal(applyBean.getRealmoney());
         // 支付类型 1仙玉充值 2周月卡充值 3小资冲级礼包充值 4土豪冲级礼包字段

         userTable.setPayintegration(userTable.getPayintegration() + addC.intValue());
         ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(applyBean.getUserNameS());
         LoginResult login = ctx != null ? GameServer.getAllLoginRole().get(ctx) : null;
         if (login != null) {// 在线充值
            AllServiceUtil.getUserTableService().updateUser(userTable);
            login.setPaysum(login.getPaysum().add(addC));// 累计充值
            login.setDaypaysum(login.getDaypaysum().add(addC));// 每日累计充值
            if (StringUtil.isNotEmpty(count) && !"undefined".equals(count)) {
               LaborScene.addRankValue(0, Integer.parseInt(count) * 10, login);//劳动节活动
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
            // 在线也要同步数据库
            AllServiceUtil.getRoleTableService().updateRoleWhenExit(login);
            SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().applyPay(GsonUtil.getGsonUtil().getgson().toJson(applyPayBean)));
            // 确保第一次处理订单(确保充值成功)
            jedis.hset("order_number_control_orno", expensesReceipts.getErid() + "", expensesReceipts.getPaytime() + ":金额" + expensesReceipts.getRecharge());
            jedis.hset("payReturnForpayServer", expensesReceipts.getErid() + "", "Sinmahod" + "=" + GsonUtil.getGsonUtil().getgson().toJson(expensesReceipts) + "");
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
               if (StringUtil.isNotEmpty(count) && !"undefined".equals(count)) {
                  LaborScene.addRankValue(0, Integer.parseInt(count) * 10, login);//劳动节活动
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
         }
      } catch (Exception e) {
         e.printStackTrace();
         WriteOut.addtxt("充值报错:" + MainServerHandler.getErrorMessage(e), 9999);
      }
      RedisPoolUntil.returnResource(jedis);
      //充值日志
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

      // 查询订单总数
      int total = AllServiceUtil.getExpensesReceiptsService().selectAllTotal(expensesReceipts);
      //总页数
      int page = total / size;
      if (total % size > 0) {
         page++;
      }

      // 分页查询
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
