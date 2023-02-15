package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Message;
import org.come.entity.MessageExample;
@MyBatisAnnotation
public interface MessageMapper {
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
    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

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