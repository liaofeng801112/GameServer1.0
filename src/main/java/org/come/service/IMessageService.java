package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Message;
import org.come.entity.MessageExample;

public interface IMessageService {
    /**
    *
    * @mbggenerated 2019-03-05
    */
   int countByExample(MessageExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByExample(MessageExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByPrimaryKey(BigDecimal mesid);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insert(Message record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insertSelective(Message record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   List<Message> selectByExample(MessageExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   Message selectByPrimaryKey(BigDecimal mesid);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExampleSelective(Message record, MessageExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExample(Message record, MessageExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKeySelective(Message record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKey(Message record);
}
