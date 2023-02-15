package com.gl.util;

import java.util.ArrayList;
import java.util.List;

import org.come.action.reward.DrawnitemsAction;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.entity.BattleRole;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gl.service.GameService;

import come.tool.Battle.BattleData;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.ManData;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
import come.tool.activity.WSS.BattleInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.StringUtil;

public class GLUtil {

   // 名字
   public final static String NAME = "NAME";
   // 当前血量
   public final static String HP = "HP";
   // 当前法力
   public final static String MP = "MP";
   // 当前攻击
   public final static String AP = "AP";
   // 当前速度
   public final static String SP = "SP";
   // 当前位置
   public final static String MAN = "MAN";
   // 当前皮肤
   public final static String SKIN = "SKIN";
   // 当前模型
   public final static String MODEL = "MODEL";
   // 当前抗性
   public final static String KANG = "KANG";
   // 当前法术
   public final static String FASHU = "FASHU";
   // 当前等级
   public final static String DENGJI = "DENGJI";
   // 当前转生
   public final static String ZHUANSHENG = "ZHUANSHENG";


   // 召唤兽灵犀
   public final static String LINGXI = "LINGXI";


   // 契合度
   public final static String QIHE = "QIHE";
   // 活跃 根骨
   public final static String HUOYUE = "HUOYUE";
   // 伤害或者回复或者落宝 灵性
   public final static String SHANGHAI = "SHANGHAI";
   // 抗落宝 力量
   public final static String KANGLUOBAO = "KANGLUOBAO";
   // 支援 敏捷
   public final static String YUANZHU = "YUANZHU";


   /**
    * 删除前一队的玩家并将一队新玩家数据转换为json字符串并存入数据库
    * @param
    * @return
    */
   public static List<BattleRole> getStringToRole(BattleData battleData, String[] teams, int type) {



      if (teams.length == 0) {
         return new ArrayList<BattleRole>();
      }


      long time = System.currentTimeMillis();

      List<BattleRole> list = new ArrayList<BattleRole>();

      for (int i=teams.length-1;i>=0;i--) {

         BattleRole role = new BattleRole();


         ManData man=battleData.getBattlefield().getBattleEndData(teams[i]);

         ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
         LoginResult loginResult = ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
         if (man==null||loginResult==null) {
            continue;
         }
         role.setRoleid(loginResult.getRole_id());
         role.setRolename(man.getManname());
         role.setTeamid(type);
         role.setStarttime(time);

         // 人物数据转换
         JSONObject master = new JSONObject();
         master.put(HP, man.getHp_z());
         master.put(MP, man.getMp_z());
         master.put(AP, man.getAp());
         master.put(SP, man.getSp());
         master.put(MAN, man.getMan());
         master.put(SKIN, man.getSkin());
         master.put(MODEL, man.getModel());
         master.put(KANG, GsonUtil.getGsonUtil().getgson().toJson(man.getQuality()));
         JSONArray skillsList = new JSONArray();
         for (FightingSkill skill : man.getSkills()) {
            if (skill.getSkilllvl() >= 4) {
               skillsList.add(skill.getSkillid());
            }
         }
         master.put(FASHU, skillsList);
         master.put(DENGJI, man.getLvl());
         master.put(ZHUANSHENG, man.getZs());

         role.setProperty(master.toJSONString());

         /*
          *  设定AI：
          *  	仙族一直法术
          *  	魔族判定AP是否够高，够高则普通攻击，否则   先速/盘 后抽
          *  	人族   男人混、冰 		女人 毒 睡
          *  	鬼族  判定忽视最高的，男鬼判断队友血量，低于50则三尸，否则遗忘，	女鬼，鬼火
          *  	龙族  有法力值就技能，没有就平砍
          */

         // 孩子数据
         ManData baby = battleData.getBattlefield().getSeek(man,4);
         if (baby != null && baby.getType() == 4) {
            JSONObject child = new JSONObject();
            child.put(MAN, baby.getMan());
            child.put(SKIN, baby.getSkin());
            child.put(MODEL, baby.getModel());
            child.put(NAME, baby.getManname());
            JSONArray babySkillsList = new JSONArray();
            for (FightingSkill skill : baby.getSkills()) {
               babySkillsList.add(skill.getSkillid());
            }
            child.put(FASHU, babySkillsList);

            role.setBabyproperty(child.toJSONString());
         }


         // 召唤兽数据
         ManData pet = battleData.getBattlefield().getSeek(man,1);
         if (pet!=null && pet.getType() == 1) {
            JSONObject petObj = new JSONObject();
            petObj.put(NAME, pet.getManname());
            petObj.put(HP, pet.getHp_z());
            petObj.put(MP, pet.getMp_z());
            petObj.put(AP, pet.getAp());
            petObj.put(SP, pet.getSp());
            petObj.put(MAN, pet.getMan());
            petObj.put(SKIN, pet.getSkin());
            petObj.put(MODEL, pet.getModel());
            petObj.put(KANG, GsonUtil.getGsonUtil().getgson().toJson(pet.getQuality()));
            petObj.put(LINGXI, pet.getLingXi());

            petObj.put(HUOYUE, pet.getHuoyue());
            petObj.put(SHANGHAI, pet.getShanghai());
            petObj.put(KANGLUOBAO, pet.getKangluobao());
            petObj.put(YUANZHU, pet.getYuanzhu());

            JSONArray petSkillsList = new JSONArray();
            for (FightingSkill skill : pet.getSkills()) {
               petSkillsList.add(skill.getSkillid());
            }
            petObj.put(FASHU, petSkillsList);
            petObj.put(DENGJI, pet.getLvl());
            petObj.put(ZHUANSHENG, pet.getZs());

            role.setPetproperty(petObj.toJSONString());
         }


         // 灵宝数据
         ManData lingbao = battleData.getBattlefield().getSeek(man,3);
         if (pet!=null && lingbao.getType() == 3) {
            JSONObject lingbaoObj = new JSONObject();
            lingbaoObj.put(NAME, lingbao.getManname());
            lingbaoObj.put(SP, lingbao.getSp());
            lingbaoObj.put(MAN, lingbao.getMan());
            lingbaoObj.put(SKIN, lingbao.getSkin());
            lingbaoObj.put(MODEL, lingbao.getModel());
            lingbaoObj.put(KANGLUOBAO, lingbao.getKangluobao());
            lingbaoObj.put(HUOYUE, lingbao.getHuoyue());
            lingbaoObj.put(QIHE, lingbao.getQihe());
            lingbaoObj.put(SHANGHAI, lingbao.getShanghai());
            lingbaoObj.put(YUANZHU, lingbao.getYuanzhu());
            JSONArray lSkillsList = new JSONArray();
            for (FightingSkill skill : lingbao.getSkills()) {
               lSkillsList.add(skill.getSkillid());
            }
            lingbaoObj.put(FASHU, lSkillsList);

            role.setLingbaoproperty(lingbaoObj.toJSONString());
         }

         list.add(role);
      }


      speak(teams, type);
      awardAndEnd(teams, type);
      BattleInfo.setBattleRoles(type,list);
      return list;
   }

   /**
    * 击败守护喊话
    * @param
    * @param
    */
   public static void speak(String[] team,int wssType){
      StringBuffer buffer=new StringBuffer();
      buffer.append("#Y");
      buffer.append("玩家");
      buffer.append("#G");
      buffer.append(team[0]);
      buffer.append("#Y带领队员");
      buffer.append("#G");
      for (int i = 1 ; i < team.length ; i++) {
         if (i != 1) {
            buffer.append("、");
         }
         buffer.append(team[i]);
      }
      buffer.append("#Y成功击败了武神山"+(wssType == 4 ? "帝印" : "烛火")+"守卫，并将分身留在了烛火中继续守护烛火.");
      NChatBean bean=new NChatBean();
      bean.setId(7);
      bean.setMessage(buffer.toString());
      String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
      SendMessage.sendMessageToAllRoles(msg);
   }


   /**
    * 武神山守护者属性初始化
    * @param battlerole
    * @param
    * @param
    * @param xs
    */
   public static List<ManData> getManData(BattleRole battlerole, double xs) {

      List<ManData> datas = new ArrayList<ManData>();

      JSONObject renwu = JSONObject.parseObject(battlerole.getProperty());
      ManData ren = new ManData(battlerole.getRoleid().intValue(), battlerole.getRolename() , renwu,xs);


      JSONObject chongwu,haizi,lingbao;
      if (!StringUtil.isNullOrEmpty(battlerole.getPetproperty())) {
         chongwu = JSONObject.parseObject(battlerole.getPetproperty());
         datas.add(new ManData(chongwu,xs));
      }
      if (!StringUtil.isNullOrEmpty(battlerole.getBabyproperty())) {
         haizi = JSONObject.parseObject(battlerole.getBabyproperty());
         ManData baby = new ManData(haizi,true, 1);
         ren.setChild(baby);
         datas.add(baby);
      }
      if (!StringUtil.isNullOrEmpty(battlerole.getLingbaoproperty())) {
         lingbao = JSONObject.parseObject(battlerole.getLingbaoproperty());
         datas.add(new ManData(lingbao,false,xs));
      }
      datas.add(ren);
      return datas;
   }


   /**
    * 发放武神山奖励，并删除守护者
    */
   public static void awardAndEnd(String[] teams, int wssType) {
      // 给前一队人发放奖励
      List<BattleRole> before = BattleInfo.getBattleRoles(wssType);

      if (before.size() > 0) {
         // 每15分钟奖励10点积分
         long ke = (System.currentTimeMillis() - before.get(0).getStarttime())/1000/60/15;
         if (ke > 0) {
            for (BattleRole r : before) {
               RoleData roleData=RolePool.getRoleData(r.getRoleid());
               LoginResult loginResult = null;
               if (roleData != null) {
                  loginResult = roleData.getLoginResult();
               }
               if (roleData != null && loginResult != null) {
                  // 玩家在线
                  AssetUpdate assetUpdate = new AssetUpdate();
                  assetUpdate.setMsg("获得武神山积分：" + ke * 10 + "点");
                  assetUpdate.updata("武神山积分=" + ke * 10);
                  loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(),"武神山积分=" + ke * (wssType+5),2));
                  if (teams != null) {
                     String message = "#Y[武神山守护] #G你的守护位置被玩家#R" + teams[0]+"#G带领队伍占领了，根据守护时间你获得了奖励积分.";
                     new GameService().sendMsgToPlayer(message, loginResult.getRolename());
                  }
               }else {
                  // 玩家不在线
                  loginResult = AllServiceUtil.getRoleTableService().selectRoleID(r.getRoleid());
                  loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(),"武神山积分=" + ke * (wssType+5),2));
                  try {
                     AllServiceUtil.getRoleTableService().updateRoleWhenExit(loginResult);
                  } catch (Exception e) {
                     WriteOut.addtxt("人物数据保存报错:" + GsonUtil.getGsonUtil().getgson().toJson(loginResult), 9999);
                  }
               }
            }
         }
      }
   }

//	/**
//	 * 保存配置
//	 * @param map
//	 */
//	public static void saveconfig(Map<Integer,List<BattleRole>> map) {
//		JSONObject json = new JSONObject();
//		for (int key : map.keySet()) {
//			JSONArray array = new JSONArray();
//			List<BattleRole> list = map.get(key);
//			for (BattleRole role : list) {
//				array.add(GsonUtil.getGsonUtil().getgson().toJson(role));
//			}
//			json.put(key+"", array);
//		}
//		write(json.toJSONString());
//	}
//
//	/**
//	 * 保存文件
//	 * @param json
//	 */
//	private static void write(String json) {
//		File file = new File(ReadExelTool.class.getResource("/").getPath() + "wss.json");
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		OutputStreamWriter write = null;
//		try {
//			write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		BufferedWriter writer = new BufferedWriter(write);
//		try {
//			writer.write(json);
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * 读取文件
//	 * @param json
//	 */
//	private static String read() {
//		return ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath()+ "wss.json");
//	}
//
//	/**
//	 * 读取配置文件返回Map对象
//	 * @return
//	 */
//	public static Map<Integer,List<BattleRole>> wssconfig() {
//		Map<Integer,List<BattleRole>> map = new HashMap<Integer,List<BattleRole>>();
//		String json = read();
//		if (!StringUtil.isNullOrEmpty(json)) {
//			JSONObject wss = JSONObject.parseObject(json);
//			for (int i = 1 ; i <= 4; i ++) {
//				List<BattleRole> battleList = new ArrayList<BattleRole>();
//				JSONArray list = wss.getJSONArray(i + "");
//				if (list != null && list.size() > 0) {
//					for (Object battle : list) {
//						BattleRole role = GsonUtil.getGsonUtil().getgson().fromJson((String) battle, BattleRole.class);
//						battleList.add(role);
//					}
//					map.put(i, battleList);
//				}
//			}
//		}
//		return map;
//	}
//
}
