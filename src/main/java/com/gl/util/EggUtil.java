package com.gl.util;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Good.DropUtil;
import come.tool.Role.RoleData;
import come.tool.Stall.AssetUpdate;

/**
 * 处理元气蛋
 * @author gl
 *
 */
public class EggUtil {

   public static final BigDecimal EGG_ID = new BigDecimal(80500);

   private static final String[] MESSAGE = {
           "#Y元气蛋吸收了天地精华后毫无动静",
           "#Y元气蛋吸收了天地精华后好像有了一些动静",
           "#G元气蛋中传出了微微波动",
           "#G元气蛋的蛋壳中出现了一道小小的裂纹",
           "#R元气蛋的蛋壳出现了更多的裂纹似乎快要孵化了"
   };

   private static final String CONTENT = "#Y玩家#G{角色名}#Y成功孵化#R元气蛋#Y，获得了灵兽大仙奖励的#G{物品名}#Y。";

   /**
    * 每场战斗后调用，判断玩家是否有元气蛋，如果有则判断孵化值是否达到100，没有则增加1点，达到100则销毁元气蛋给予奖励物品
    * @param list
    */
   public static void success(List<RoleData> list) {

      for (RoleData role : list) {
         // 判断该角色是否拥有这件物品
         List<Goodstable> goodsList = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(role.getLoginResult().getRole_id(),EGG_ID);
         if (goodsList.size() > 0) {
            Goodstable goods = goodsList.get(0);
            String[] values = goods.getValue().split("=");
            if (values.length == 2) {
               int sence = Integer.valueOf(values[1]) + 1;
               // 达到100场
               if (sence == 100) {
                  // 判断背包是否满了
                  if (role.isGoodFull()) {
                     // 元气蛋吸收了足够的天地精华，蛋内的小东西已经快要破壳而出了，但是你的物品栏已经满了，请至少保留两个空位
                     SendMessage.sendMessageByRoleName(role.getLoginResult().getRolename(), Agreement.getAgreement().PromptAgreement("元气蛋吸收了足够的天地精华，蛋内的小东西已经快要破壳而出了，但是你的物品栏已经满了，请至少保留两个空位"));
                  } else {
                     // 销毁元气蛋发放奖励
                     AllServiceUtil.getGoodsTableService().deleteGoodsByRgid(goods.getRgid());
                     // 获取drop.xls配置中的物品掉落按概率随机物品并给玩家发放
                     Dorp dorp=GameServer.getDorp("10001");
                     if (dorp==null) {return;}
                     DropUtil.getDrop(role.getLoginResult(), dorp.getDorpValue(), CONTENT, 25,1D,null);
                     AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
                     assetUpdate.updata("G"+goods.getRgid()+"="+0);
                     assetUpdate.setMsg("#G元气蛋成功孵化了，新出生的灵兽化成一股青烟飞走了");
                     SendMessage.sendMessageByRoleName(role.getLoginResult().getRolename(),Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
                  }
               } else {
                  // 未达到100场则场次+1
                  goods.setValue("场数=" + sence);
                  // 更新商品信息
                  AllServiceUtil.getGoodsTableService().updateGoodRedis(goods);
                  // 发送消息
                  SendMessage.sendMessageByRoleName(role.getLoginResult().getRolename(), Agreement.getAgreement().PromptAgreement(MESSAGE[sence/20]));
               }
            }
         }
      }




   }

}
