package com.gl.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.come.entity.RoleTable;
import org.come.entity.UserTable;
import org.come.redis.RedisCacheUtil;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;

import com.gl.model.User;

public class UserService {

   public static final String USERNAME = "BG_NAME_XY2";

   public String register(User user ,String clientIP) {

      // 将注册信息写入用户bean中
      UserTable userTable = new UserTable();
      userTable.setUsername(user.getUserName());
      userTable.setUserpwd(user.getPassword());
      userTable.setSafety(user.getSecurity());
      userTable.setTuiji(user.getRecommend());

      // -- 三端
      if ("".equals(userTable.getUsername()) || "".equals(userTable.getUserpwd()) || "".equals(userTable.getSafety())) {
         return "信息不可为空";
      }
      // 账号 不可 小于8位 超过20位
      String checkUserAcc = checkUserAcc(userTable.getUsername());
      if (!"true".equals(checkUserAcc)) {
         return checkUserAcc;
      }
      // 密码不可少于6位 不可超过16位
      if (userTable.getUserpwd().length() < 6 || userTable.getUserpwd().length() > 16) {
         return "密码不可少于6位 不可超过16位";
      }
      // 安全码不可少于6位 不可超过16位
      if (userTable.getSafety().length() < 6 || userTable.getSafety().length() > 16) {
         return "安全码不可少于6位 不可超过16位";
      }

      // 推荐码
      String tuiji = userTable.getTuiji();
      if (tuiji == null || "".equals(tuiji)) {
         return "请输入推荐码";
      }

      List<BigDecimal> sid = AllServiceUtil.getOpenareatableService().selectTuijiNum(tuiji);
      if (sid.size() == 0) {
         return "没有找到该推荐码,请检查是否正确!";
      }
      userTable.setQid(sid.get(0));
      // 判断用户是否存在
      UserTable sameUser = AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userTable.getUsername(), null);

      if (sameUser == null) {
         userTable.setRegisterip(clientIP);
         // 注册用户
         userTable.setUser_id(RedisCacheUtil.getUser_pk());
         int isSuccess = AllServiceUtil.getUserTableService().insertIntoUser(userTable);

         if (isSuccess > 0) {
            return "";
         }

      } else {
         if (sameUser != null) {
            return "该账号已存在";
         }
      }
      return "注册信息有误";
   }

   // 判断是否只包含字母和数字
   public String checkUserAcc(String acc) {
      if (acc.length() < 8 || acc.length() > 20) {
         return "账号格式必须为8-20个字母和数字";
      }
      if (check(acc)) {
         return "true";
      }
      return "账号不可为纯字母、纯数字或带有符号!";
   }

   public boolean check(String acc) {
      String reg = "^(\\d+[A-Za-z]+[A-Za-z0-9]*)|([A-Za-z]+\\d+[A-Za-z0-9]*)$";
      return acc.matches(reg);
   }

   /**
    * 基础统计数据
    * @return
    */
   public List<Map<String,String>> getData(){
      List<Map<String,String>> pmap = new ArrayList<Map<String,String>>();
      Map<String,String> map = new LinkedHashMap<String, String>();
      // 玩家数
      int palyTotal = AllServiceUtil.getUserTableService().selectSumForRoleUserHaterNumber(new RoleTable());
      map.put("icon", "el-icon-s-custom");
      map.put("title", "游戏内玩家总数");
      map.put("total", palyTotal + "");
      map.put("bgColor", "#ebcc6f");
      pmap.add(map);
      // 物品数
      int goodsTotal = GameServer.getAllGoodsMap().size();
      map = new LinkedHashMap<String, String>();
      map.put("icon", "el-icon-s-shop");
      map.put("title", "物品种类总数");
      map.put("total", goodsTotal + "");
      map.put("bgColor", "#3acaa9");
      pmap.add(map);

      // 三日内活跃玩家
      UserTable userTable = new UserTable();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.HOUR_OF_DAY,8);
      calendar.add(Calendar.DAY_OF_MONTH, -3);
      userTable.setUSERLASTLOGIN(DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
      int userTotal = AllServiceUtil.getUserTableService().selectUsterTableForConcition(userTable);
      map = new LinkedHashMap<String, String>();
      map.put("icon", "el-icon-s-comment");
      map.put("title", "三日内活跃玩家");
      map.put("total", userTotal + "");
      map.put("bgColor", "#67c4ed");
      pmap.add(map);


      // 当前在线玩家
      map = new LinkedHashMap<String, String>();
      map.put("icon", "el-icon-s-check");
      map.put("title", "当前在线玩家");
      map.put("total", GameServer.getAllLoginRole().size() + "");
      map.put("bgColor", "#f56c6c");
      pmap.add(map);

      return pmap;
   }

}
