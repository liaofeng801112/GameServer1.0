package com.gl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.come.bean.ChatBean;
import org.come.bean.LoginResult;
import org.come.entity.Wechatrecord;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.readUtil.ReadPoolUtil;
import org.come.server.GameServer;
import org.come.tool.NewAESUtil;
import org.come.tool.ReadExelTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.gl.model.Param;
import com.gl.util.Config;

import io.netty.channel.ChannelHandlerContext;

public class GameService {

   /**
    * 读取游戏公告
    *
    * @return
    */
   public String readGG() {
      return ReadTxtUtil.readFile1(
              ReadExelTool.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "") + "GetTXT\\gg.txt");
   }

   /**
    * 写入游戏公告
    *
    * @return
    */
   public boolean writeGG(Param param) {
      String gg = param.getValue1();
      if (StringUtil.isNotEmpty(gg)) {
         try {
            // 先备份之前的公告
            File file = new File(ReadExelTool.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "")
                    + "GetTXT\\gg.txt");
            if (file.exists()) {
               file.renameTo(
                       new File(ReadExelTool.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "")
                               + "GetTXT\\gg_" + System.currentTimeMillis() + ".txt"));
            }
            File f = new File(ReadExelTool.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "")
                    + "GetTXT\\gg.txt");
            if (!f.exists()) {
               f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(gg);
            writer.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
         return true;
      }
      return false;
   }

   /**
    * 发布系统消息 3 聊天框 系统消息，4 屏幕中心喇叭 5 屏幕下方滚动
    *
    * @return
    */
   public boolean sendMsg(Param param) {
      String type = param.getValue1();
      String message = param.getValue2();
      if (StringUtil.isNotEmpty(type) && NumberUtils.isDigits(type) && StringUtil.isNotEmpty(message)) {

         String msg = Agreement.getAgreement()
                 .chatAgreement("{\"id\":" + type + ",\"message\":\"" + message + "\"}");

         SendMessage.sendMessageToAllRoles(msg);
         return true;
      }
      return false;
   }

   /**
    * 发布私聊消息
    *
    * @return
    */
   public boolean sendMsgToPlayer(String message, String roleName) {
      // 如果玩家在线则发送消息
      ChannelHandlerContext ctx = GameServer.getRoleNameMap().get(roleName);
      if (ctx != null) {
         LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
         // 添加聊天记录
         Wechatrecord wechatrecord = new Wechatrecord();
         wechatrecord.setChatGetid(roleInfo.getRole_id());
         wechatrecord.setChatMes(message);
         wechatrecord.setChatSendid(new BigDecimal(0));
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String nowdayTime = dateFormat.format(new Date());
         wechatrecord.setTime(nowdayTime);
         AllServiceUtil.getWechatrecordService().insert(wechatrecord);

         ChatBean chatBean = new ChatBean();
         chatBean.setFriendName(roleName);
         chatBean.setMessage(message);
         chatBean.setRolename("游戏管理员");
         chatBean.setTime(new Date().getTime());

         String msg = Agreement.getAgreement()
                 .friendchatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
         // 获得该用户的输出流发送聊天内容
         SendMessage.sendMessageByRoleName(roleName, msg);
         return true;
      }
      return false;
   }

   /**
    * 加载配置
    */
   public Map<String, String> readConfig() {
      return Config.getValue();
   }

   /**
    * 保存配置
    */
   public boolean saveConfig(Map<String, String> map) {
      return Config.update(map);
   }

   public static Map<String, Integer> XLSmap;
   static {
      XLSmap = new HashMap<>();
      XLSmap.put("pet.xls", 0);
      XLSmap.put("petExchange.xls", 2);
      XLSmap.put("map.xls", 3);
      XLSmap.put("npc.xls", 4);
      XLSmap.put("door.xls", 5);
      XLSmap.put("taskSet.xls", 6);
      XLSmap.put("taskData.xls", 6);
      XLSmap.put("palData.xls", 7);
      XLSmap.put("boos.xls", 8);
      XLSmap.put("monster.xls", 9);
      XLSmap.put("robots.xls", 10);
      XLSmap.put("item.xls", 11);
      XLSmap.put("newequip.xls", 12);
      XLSmap.put("alchemy.xls", 13);
      XLSmap.put("decorate.xls", 14);
      XLSmap.put("godstone.xls", 15);
      XLSmap.put("palEquip.xls", 16);
      XLSmap.put("shop.xls", 17);
      XLSmap.put("eshop.xls", 18);
      XLSmap.put("lShop.xls", 19);
      XLSmap.put("sghostpoint.xls", 20);
      XLSmap.put("xianqi.xls", 21);
      XLSmap.put("lingbao.xls", 22);
      XLSmap.put("lingbaofushi.xls", 23);
      XLSmap.put("gem.xls", 24);
      XLSmap.put("skill.xls", 25);
      XLSmap.put("drop.xls", 26);
      XLSmap.put("dntg.xls", 27);
      XLSmap.put("bbuy.xls", 28);
      XLSmap.put("suit.xls", 29);
      XLSmap.put("tx.xls", 30);
      XLSmap.put("present.xls", 31);
      XLSmap.put("exp.xls", 32);
      XLSmap.put("mount.xls", 33);
      XLSmap.put("color.xls", 34);
      XLSmap.put("child.xls", 35);
      XLSmap.put("draw.xls", 36);
      XLSmap.put("acard.xls", 37);
      XLSmap.put("title.xls", 38);
      XLSmap.put("event.xls", 39);
      XLSmap.put("wingTraining.xls", 40);
      XLSmap.put("starPalace.xls", 41);
      XLSmap.put("tyc.xls", 42);
      XLSmap.put("babyresult.xls", 43);
      XLSmap.put("guide.xls", 44);
      XLSmap.put("active.xls", 45);
      XLSmap.put("achieve.xls", 46);
      XLSmap.put("lh.xls",47);//炼化费用
      XLSmap.put("Meridians.xls",48);//经脉系统
      XLSmap.put("goodsExchange.xls",49);//兑换系统

   }

   /**
    * 在线配置更新
    * @param file
    * @param
    * @return
    * @throws IllegalStateException
    * @throws IOException
    */
   public String updateXls(MultipartFile file) throws IllegalStateException, IOException {
      if (!file.isEmpty()) {
         String NewFileName = file.getOriginalFilename();
         String ext = NewFileName.indexOf(".") != -1
                 ? NewFileName.substring(NewFileName.lastIndexOf(".") + 1, NewFileName.length())
                 : null;
         if (ext != null) {
            if ("xls".equals(ext) && XLSmap.containsKey(NewFileName)) {
               long times = System.currentTimeMillis();
               // 根据文件名复制老配置文件为文件名.时间戳
               File oldFile = new File(ReadExelTool.class.getResource("/").getPath() + "config/" + NewFileName);
               File oldBackFile = new File(ReadExelTool.class.getResource("/").getPath() + "config/" + NewFileName + times);
               if (oldFile.exists()) {
                  oldFile.renameTo(oldBackFile);
               }
               // 上传文件
               File dest = new File(ReadExelTool.class.getResource("/").getPath() + "config/" + NewFileName);
               file.transferTo(dest);

               StringBuffer buffer = new StringBuffer();
               if (ReadPoolUtil.readTypeTwo(buffer, XLSmap.get(NewFileName))) {
                  return ""; // 成功加载配置
               } else {
                  System.out.println(buffer.toString());
                  // 加载配置失败，回滚文件
                  if (dest.exists()) {
                     dest.delete();
                  }
                  if (oldBackFile.exists()) {
                     oldBackFile.renameTo(dest);
                  }
                  return "配置文件错误加载失败";
               }
            } else {
               return "文件类型错误或不识别的配置文件";
            }
         } else {
            return "文件类型不正确";
         }
      } else {
         return "上传文件为空";
      }
   }



   /**
    * 配置文件列表（不分页）
    */
   public Map<String,Long> readXls()  {
      File dir = new File(ReadExelTool.class.getResource("/").getPath() + "config/");
      Map<String,Long> fileList = new LinkedHashMap<String,Long>();
      File[] fs = dir.listFiles();
      for(File f:fs){
         if(!f.isDirectory()) {
            fileList.put(f.getName(),f.lastModified());
         }
      }
      Map<String, Long> result = new LinkedHashMap<String, Long>();
      fileList.entrySet()
              .stream().sorted(Map.Entry.comparingByKey())
              .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
      return result;
   }

   /**
    * 配置文件下载（单个）
    */
   public String downloadXls(String fileName,OutputStream out)  {
      try {
         File file = new File(ReadExelTool.class.getResource("/").getPath() + "config/" + fileName);

         if(!file.exists()) {
            return "下载文件不存在";
         }

         /**
          * 读取要下载的图片或者文件，将其放在缓冲中，再下载
          */
         FileInputStream in = new FileInputStream(file);
         byte buffer[] = new byte[1024];
         int len = 0;
         while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
         }
         in.close();
         out.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return "";
   }

   /**
    * 配置文件删除（仅允许删除时间戳结尾的文件）
    */
   public String deleteXls(String fileName)  {
      File file = new File(ReadExelTool.class.getResource("/").getPath() + "config/" + fileName);
      if(!file.exists()) {
         return "要删除的文件不存在";
      }
      if(fileName.length() - fileName.indexOf("xls") != 16) {
         return "非备份文件不可删除";
      }
      file.delete();
      return "";
   }

}
