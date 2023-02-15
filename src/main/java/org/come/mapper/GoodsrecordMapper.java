package org.come.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Goodsrecord;
import org.come.entity.GoodsrecordExample;
@MyBatisAnnotation
public interface GoodsrecordMapper {
	
	/**
	 * 查询物品记录
	 * @param goodsrecord
	 * @return
	 */
	List<Goodsrecord> selectGoodsRecord( Goodsrecord goodsrecord);
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
    int deleteByPrimaryKey(Integer grid);

    /**
     *
     * @mbggenerated 2018-09-14
     */
    int insert(Goodsrecord record);

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
    int updateByExampleSelective(@Param("record") Goodsrecord record, @Param("example") GoodsrecordExample example);

    /**
     *
     * @mbggenerated 2018-09-14
     */
    int updateByExample(@Param("record") Goodsrecord record, @Param("example") GoodsrecordExample example);

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