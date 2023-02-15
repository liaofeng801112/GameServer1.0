package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Goodsrecord;
import org.come.entity.GoodsrecordExample;
import org.come.entity.Goodstable;

import com.github.pagehelper.PageInfo;

public interface IGoodsrecordService {
	
	/**
	 * 查询物品记录
	 * @param goodsrecord
	 * @return
	 */
	PageInfo<Goodsrecord> selectGoodsRecord(  Integer pageNum, String condition);
	
    /**
    *
    * @mbggenerated 2018-09-14
    */
   int countByExample(GoodsrecordExample example);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int deleteByExample(GoodsrecordExample example);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int deleteByPrimaryKey(BigDecimal grid);

   /**
    * @param goodstable	物品
    * @param otherrole	对方ID
    * @param goodsnum	物品数量
    * @param recordtype	记录类型
    * @mbggenerated 2018-09-14
    * @return
    */
   int insert(Goodstable goodstable, BigDecimal otherrole, Integer goodsnum, Integer recordtype);
   
   /**插入物品记录*/
   int insertGoodsrecord(BigDecimal roleID,BigDecimal otherID, int type,BigDecimal id, String name,String value,int num);
   
   /**
    *
    * @mbggenerated 2018-09-14
    */
   int insertSelective(Goodsrecord record);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   List<Goodsrecord> selectByExample(GoodsrecordExample example);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   Goodsrecord selectByPrimaryKey(Integer grid);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int updateByExampleSelective( Goodsrecord record,  GoodsrecordExample example);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int updateByExample( Goodsrecord record,  GoodsrecordExample example);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int updateByPrimaryKeySelective(Goodsrecord record);

   /**
    *
    * @mbggenerated 2018-09-14
    */
   int updateByPrimaryKey(Goodsrecord record);
}
