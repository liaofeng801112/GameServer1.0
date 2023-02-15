package org.come.mapper;

import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.RewardHall;
import org.come.entity.RewardHallExample;
@MyBatisAnnotation
public interface RewardHallMapper {
    /**
     *
     * @mbggenerated 2018-10-13
     */
    int countByExample(RewardHallExample example);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int deleteByExample(RewardHallExample example);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int insert(RewardHall record);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int insertSelective(RewardHall record);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    CopyOnWriteArrayList<RewardHall> selectByExample(RewardHallExample example);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    RewardHall selectByPrimaryKey(BigDecimal id);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int updateByExampleSelective(@Param("record") RewardHall record, @Param("example") RewardHallExample example);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int updateByExample(@Param("record") RewardHall record, @Param("example") RewardHallExample example);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int updateByPrimaryKeySelective(RewardHall record);

    /**
     *
     * @mbggenerated 2018-10-13
     */
    int updateByPrimaryKey(RewardHall record);
}