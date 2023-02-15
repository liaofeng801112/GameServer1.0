package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Haters;
import org.come.entity.HatersExample;
@MyBatisAnnotation
public interface HatersMapper {
    /**
     *
     * @mbggenerated 2019-01-16
     */
    int countByExample(HatersExample example);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int deleteByExample(HatersExample example);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int deleteByPrimaryKey(BigDecimal roleid);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int insert(Haters record);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int insertSelective(Haters record);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    List<Haters> selectByExample(HatersExample example);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    Haters selectByPrimaryKey(BigDecimal roleid);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int updateByExampleSelective(@Param("record") Haters record, @Param("example") HatersExample example);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int updateByExample(@Param("record") Haters record, @Param("example") HatersExample example);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int updateByPrimaryKeySelective(Haters record);

    /**
     *
     * @mbggenerated 2019-01-16
     */
    int updateByPrimaryKey(Haters record);
}