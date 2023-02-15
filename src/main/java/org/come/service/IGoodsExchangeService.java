package org.come.service;

import java.util.List;

import org.come.entity.Goodsexchange;
import org.come.entity.GoodsexchangeExample;

public interface IGoodsExchangeService {
    /**
    *
    * @mbggenerated 2018-12-07
    */
   int countByExample(GoodsexchangeExample example);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int deleteByExample(GoodsexchangeExample example);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int deleteByPrimaryKey(String goodsguid);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int insert(Goodsexchange record);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int insertSelective(Goodsexchange record);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   List<Goodsexchange> selectByExample(GoodsexchangeExample example);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   Goodsexchange selectByPrimaryKey(String goodsguid);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int updateByExampleSelective(Goodsexchange record, GoodsexchangeExample example);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int updateByExample( Goodsexchange record,  GoodsexchangeExample example);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int updateByPrimaryKeySelective(Goodsexchange record);

   /**
    *
    * @mbggenerated 2018-12-07
    */
   int updateByPrimaryKey(Goodsexchange record);

   /**查询所有兑换码 */
   List<Goodsexchange> selectListAll();
}
