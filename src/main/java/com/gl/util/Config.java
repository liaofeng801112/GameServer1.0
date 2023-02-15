package com.gl.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件解析
 *
 * @author biggrren
 *
 */
public class Config {

   /**
    * 主配置获取
    */
   private static Map<String, String> CONFIG;

   private static Properties prop;

   private static final String PROPFILE = Config.class.getResource("/").getPath() + "game.config";

   static {
      CONFIG = new HashMap<String, String>();
      prop = new Properties();
   }

   /*
    *
    *  概率类全都是  1/N   越高概率越低
    *
    *
    */


   /**
    *  锁定炼化  1 不开启  0 开启
    */
   public static final String KEY_LH_SD = "sd_lianhua";

   /**
    *  仙器炼化最大条数
    */
   public static final String KEY_LH_XQ = "xq_lianhua";
   /**
    *  神兵炼化最大条数
    */
   public static final String KEY_LH_SB = "sb_lianhua";
   /**
    *  普通装备炼化最大条数
    */
   public static final String KEY_LH_PT = "pt_lianhua";

   /**
    *  仙器炼器最大条数
    */
   public static final String KEY_LQ_XQ = "xq_lianqi";
   /**
    *  神兵炼器最大条数
    */
   public static final String KEY_LQ_SB = "sb_lianqi";
   /**
    *  普通装备炼器最大条数
    */
   public static final String KEY_LQ_PT = "pt_lianqi";

   /**
    *  仙器特技1获取概率
    */
   public static final String KEY_TJ_XQ1 = "xq_teji1";
   /**
    *  仙器特技2获取概率
    */
   public static final String KEY_TJ_XQ2 = "xq_teji2";
   /**
    *  神兵特技1获取概率
    */
   public static final String KEY_TJ_SB1 = "sb_teji1";
   /**
    *  神兵特技2获取概率
    */
   public static final String KEY_TJ_SB2 = "sb_teji2";
   /**
    *  普通特技1获取概率
    */
   public static final String KEY_TJ_PT1 = "pt_teji1";
   /**
    *  普通特技2获取概率
    */
   public static final String KEY_TJ_PT2 = "pt_teji2";

   /**
    * 星卡获得星阵的概率
    */
   public static final String KEY_XK_XZ = "xz_xingka";


   public static void main(String[] args) {
      System.out.println(PROPFILE);
      CONFIG.put(KEY_LH_SD, "1");

      CONFIG.put(KEY_LH_XQ, "5");
      CONFIG.put(KEY_LH_SB, "5");
      CONFIG.put(KEY_LH_PT, "5");

      CONFIG.put(KEY_TJ_XQ1, "10");
      CONFIG.put(KEY_TJ_XQ2, "10");
      CONFIG.put(KEY_TJ_SB1, "10");
      CONFIG.put(KEY_TJ_SB2, "10");
      CONFIG.put(KEY_TJ_PT1, "10");
      CONFIG.put(KEY_TJ_PT2, "10");


      CONFIG.put(KEY_LQ_XQ, "2");
      CONFIG.put(KEY_LQ_SB, "2");
      CONFIG.put(KEY_LQ_PT, "5");

      CONFIG.put(KEY_XK_XZ, "5");
      write();
   }

   public static void reload() {
      try {
         // 读取属性文件
         InputStream in = new BufferedInputStream(new FileInputStream(PROPFILE));
         prop.load(in);
         Iterator<String> it = prop.stringPropertyNames().iterator();
         while (it.hasNext()) {
            String key = it.next();
            CONFIG.put(key, prop.getProperty(key));
         }
         in.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void write() {
      FileOutputStream oFile = null;
      try {
         oFile = new FileOutputStream(PROPFILE);
         for (String key : CONFIG.keySet()) {
            prop.setProperty(key, CONFIG.get(key));
         }
         prop.store(oFile, "Property file -- 45977309@qq.com  @BigGreen");
         oFile.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * 获取对应的配置项目
    * @param key
    * @return
    */
   public static String getString(String key) {
      if (CONFIG.size() == 0) {
         reload();
      }
      return CONFIG.get(key);
   }

   /**
    * 获取对应的配置项目
    * @param key
    * @return
    */
   public static int getInt(String key) {
      if (CONFIG.size() == 0) {
         reload();
      }
      return Integer.parseInt(CONFIG.get(key));
   }

   /**
    * 获取所有配置
    * @return
    */
   public static Map<String,String> getValue(){
      if (CONFIG.size() == 0) {
         reload();
      }
      return CONFIG;
   }

   /**
    * 更新配置
    * @param map
    */
   public static boolean update(Map<String,String> map) {
      boolean success = false;
      for (String key : map.keySet()) {
         // 只更新存在KEY的值,并且value不为空
         if (CONFIG.containsKey(key) && map.get(key) != null) {
            if (key.equals(KEY_LH_PT) || key.equals(KEY_LH_SB) || key.equals(KEY_LH_XQ) ||
                    key.equals(KEY_LQ_PT) || key.equals(KEY_LQ_SB) || key.equals(KEY_LQ_XQ)) {
               CONFIG.put(key, Integer.parseInt(map.get(key)) / 20 + "");
            } else {
               CONFIG.put(key, map.get(key));
            }
            success = true;
         }
      }
      write();
      return success;
   }

}
