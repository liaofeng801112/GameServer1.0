package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Wechatrecord;
import org.come.entity.WechatrecordExample;
@MyBatisAnnotation
public interface WechatrecordMapper {
    /**
     *
     * @mbggenerated 2019-02-25
     */
    int countByExample(WechatrecordExample example);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int deleteByExample(WechatrecordExample example);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int deleteByPrimaryKey(BigDecimal chatId);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int insert(Wechatrecord record);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int insertSelective(Wechatrecord record);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    List<Wechatrecord> selectByExample(WechatrecordExample example);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    Wechatrecord selectByPrimaryKey(BigDecimal chatId);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int updateByExampleSelective(@Param("record") Wechatrecord record, @Param("example") WechatrecordExample example);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int updateByExample(@Param("record") Wechatrecord record, @Param("example") WechatrecordExample example);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int updateByPrimaryKeySelective(Wechatrecord record);

    /**
     *
     * @mbggenerated 2019-02-25
     */
    int updateByPrimaryKey(Wechatrecord record);
}