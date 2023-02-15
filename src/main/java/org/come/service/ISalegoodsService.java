package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;

public interface ISalegoodsService {
    /**
    *
    * @mbggenerated 2019-03-05
    */
   int countByExample(SalegoodsExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByExample(SalegoodsExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByPrimaryKey(BigDecimal saleid);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insert(Salegoods record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insertSelective(Salegoods record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   List<Salegoods> selectByExample(SalegoodsExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   Salegoods selectByPrimaryKey(BigDecimal saleid);
   /**
     * @mbggenerated 2019-03-05
     */
   List<Salegoods> selectByAll();
   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExampleSelective(Salegoods record, SalegoodsExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExample(Salegoods record, SalegoodsExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKeySelective(Salegoods record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKey(Salegoods record);
   
   void    updateFlag(BigDecimal saleid,Integer flag);
   void    deleteFlag(BigDecimal saleid);
   Integer selectFlag(BigDecimal saleid);
   
	/** zrikka 2020-0408 */
	/**通过角色id 查询订单**/
	Salegoods selectSaleGoodsByRoleid(String roleid);
}