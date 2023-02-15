package org.come.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Goodsexchange;
import org.come.entity.GoodsexchangeExample;
@MyBatisAnnotation
public interface GoodsexchangeMapper {
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
    int updateByExampleSelective(@Param("record") Goodsexchange record, @Param("example") GoodsexchangeExample example);

    /**
     *
     * @mbggenerated 2018-12-07
     */
    int updateByExample(@Param("record") Goodsexchange record, @Param("example") GoodsexchangeExample example);

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

    /**查询所有兑换码  2021-09-16*/
    List<Goodsexchange> selectListAll();
}