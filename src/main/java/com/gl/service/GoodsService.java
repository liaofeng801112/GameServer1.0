package com.gl.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.math.NumberUtils;
import org.come.bean.BackGoodsExchange;
import org.come.bean.XXGDBean;
import org.come.entity.Goodsexchange;
import org.come.entity.GoodsexchangeExample;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Record;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.SplitLingbaoValue;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.gl.model.Param;

import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class GoodsService {

   private static final int PageSize = 15;

   /**
    *
    * @param
    * @return
    */
   public BackGoodsExchange getExchange(Param param){

      String type = param.getValue1();
      String value = param.getValue2();

      int pageNum = param.getPageNum();
      int size = param.getPageSize();

      if (size < 20) {
         size = PageSize;
      }

      GoodsexchangeExample example = new GoodsexchangeExample();

      if(StringUtil.isNotEmpty(type)) {
         if ("1".equals(type)) {
            example.setGoodsguid(value);
         } else if ("2".equals(type)) {
            if(StringUtil.isNotEmpty(value) && NumberUtils.isDigits(value)) {
               example.setGoodsid(value);
            }
         }
      }



      // 查询推广码总数
      int total = AllServiceUtil.getGoodsExchangeService().countByExample(example);
      //总页数
      int page = total / size;
      if (total % size > 0) {
         page++;
      }

      example.setOrderByClause("GOODSID ASC");

      // 分页查询
      PageHelper.startPage(pageNum, size);
      List<Goodsexchange> list = AllServiceUtil.getGoodsExchangeService().selectByExample(example);
      PageInfo<Goodsexchange> pageInfo = new PageInfo<>(list);


      // 进行物品名称实例化
      for (Goodsexchange change : list) {
         change.setGoodsid(GameServer.getAllGoodsMap().get(new BigDecimal(change.getGoodsid())).getGoodsname());
      }

      BackGoodsExchange goods = new BackGoodsExchange();
      goods.setList(pageInfo.getList());
      goods.setPages(page);
      goods.setPageNum(pageNum);
      goods.setTotal(total);
      return goods;
   }


   /**
    * 生成推广礼包
    * @param param
    * @return
    */
   public boolean createExchange(Param param) {
      String goodsId = param.getValue1();
      String sum = param.getValue2();
      if (StringUtil.isNotEmpty(goodsId) && StringUtil.isNotEmpty(sum)) {
         if (NumberUtils.isDigits(goodsId) && NumberUtils.isDigits(sum) && GameServer.getAllGoodsMap().containsKey(new BigDecimal(goodsId))) {
            int count = Integer.valueOf(sum);
            for (int i = 0 ; i < count ; i++) {
               Goodsexchange record = new Goodsexchange();
               record.setGoodsguid(UUID.randomUUID().toString().toUpperCase());
               record.setGoodsid(goodsId);
               record.setFlag(0);
               AllServiceUtil.getGoodsExchangeService().insert(record);
            }
            return true;
         }
      }
      return false;
   }

   /**
    * 返回所有物品列表
    * @param
    * @return
    */
   public Map<String,String> goodsMap() {
      ConcurrentHashMap<BigDecimal, Goodstable> map = GameServer.getAllGoodsMap();
      Map<String,String> goodsMap = new ConcurrentHashMap<String,String>();

      map.forEach((k,v) -> {
         goodsMap.put(v.getGoodsname() + "(" + v.getGoodsid() + ")", v.getGoodsid().longValue() + "");
      });

      return goodsMap;
   }


   /**
    * 给玩家发送物品
    * @param param
    * @return
    */
   public boolean sendGoods(Param param) {

      String roleName = param.getValue1();
      String goodsId = param.getValue2();
      String num = param.getValue3();


      // 查询是否有该用户
      RoleTable userTable = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(roleName);

      if (userTable != null) {

         RoleData roleData = RolePool.getRoleData(userTable.getRole_id());
         XXGDBean xxgdBean = new XXGDBean();
         xxgdBean.setId(goodsId);
         xxgdBean.setSum(Integer.parseInt(num));

         // 获得购买的物品的ID查找excel表，获得物品信息
         BigDecimal id=new BigDecimal(xxgdBean.getId());
         Goodstable goodstable = GameServer.getGood(id);
         if (goodstable == null) {
            return false;
         }
         StringBuffer buffer=new StringBuffer();
         buffer.append("刷物资接口物品id:");
         buffer.append(goodsId);
         buffer.append(",");
         buffer.append(xxgdBean.getSum()+"个"+goodstable.getGoodsname());
         buffer.append(",接收人:");
         buffer.append(userTable.getRole_id());
         buffer.append("_");
         buffer.append(roleName);
         AllServiceUtil.getRecordService().insert(new Record(4,buffer.toString()));

         AssetUpdate assetUpdate = new AssetUpdate();
         assetUpdate.setMsg("获得" + xxgdBean.getSum() + "个" + goodstable.getGoodsname());




         // 添加记录
         goodstable.setRole_id(userTable.getRole_id());
         long yid = id.longValue();
         for (int i = 0; i < xxgdBean.getSum(); i++) {
            if (i != 0) {
               goodstable = GameServer.getGood(id);
            }
            goodstable.setRole_id(userTable.getRole_id());
            long sid = goodstable.getGoodsid().longValue();
            if (sid >= 70001 && sid <= 70030) {
               Lingbao lingbao = SplitLingbaoValue.addling(goodstable.getGoodsname(), userTable.getRole_id());
               assetUpdate.setLingbao(lingbao);
               AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
            } else if (sid >= 69001 && sid <= 69015) {
               Lingbao lingbao = SplitLingbaoValue.addfa(sid, userTable.getRole_id());
               assetUpdate.setLingbao(lingbao);
               if (i==0) {
                  AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);
               }
            } else if (goodstable.getType() == 825) {// 是玉符
               if (goodstable.getValue().equals("")) {
                  continue;
               }
               String[] v = goodstable.getValue().split("\\|");
               int suitid = Integer.parseInt(v[0]);
               int part = Integer.parseInt(v[1]);
               int pz = Integer.parseInt(v[2]);
               PartJade jade = roleData.getPackRecord().setPartJade(suitid, part, pz, 1);
               assetUpdate.setJade(jade);
               AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
            } else if (EquipTool.canSuper(goodstable.getType())) {// 可叠加
               int sum = yid == sid ? xxgdBean.getSum() : 1;
               // 判断该角色是否拥有这件物品
               List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(userTable.getRole_id(), goodstable.getGoodsid());
               if (sameGoodstable.size() != 0) {
                  // 修改使用次数
                  int uses = sameGoodstable.get(0).getUsetime() + sum;
                  sameGoodstable.get(0).setUsetime(uses);
                  // 修改数据库
                  AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
                  assetUpdate.setGood(sameGoodstable.get(0));
                  AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);
               } else {
                  goodstable.setUsetime(sum);
                  // 插入数据库
                  AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
                  assetUpdate.setGood(goodstable);
                  AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);
               }
               if (yid == sid) {
                  break;
               }
            } else {
               goodstable.setUsetime(1);
               AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
               assetUpdate.setGood(goodstable);
               AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
            }
         }
         //玩家在线
         if( GameServer.getRoleNameMap().get(roleName) != null ){
            SendMessage.sendMessageToSlef(GameServer.getRoleNameMap().get(roleName), Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
         }


         String message = "#Y[系统消息] #G你获得了#R" + xxgdBean.getSum()+"#G个#Y"+goodstable.getGoodsname();
         new GameService().sendMsgToPlayer(message, roleName);

         return true;
      }
      return false;
   }

}
