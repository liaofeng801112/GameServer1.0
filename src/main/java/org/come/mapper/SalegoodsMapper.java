package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
@MyBatisAnnotation
public interface SalegoodsMapper {
	
    /**
     *
     * @mbggenerated 2019-03-18
     */
    int countByExample(SalegoodsExample example);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int deleteByExample(SalegoodsExample example);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int deleteByPrimaryKey(BigDecimal saleid);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int insert(Salegoods record);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int insertSelective(Salegoods record);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    List<Salegoods> selectByExample(SalegoodsExample example);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    Salegoods selectByPrimaryKey(BigDecimal saleid);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int updateByExampleSelective(@Param("record") Salegoods record, @Param("example") SalegoodsExample example);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int updateByExample(@Param("record") Salegoods record, @Param("example") SalegoodsExample example);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int updateByPrimaryKeySelective(Salegoods record);

    /**
     *
     * @mbggenerated 2019-03-18
     */
    int updateByPrimaryKey(Salegoods record);

	List<Salegoods> selectByAll();
	
	/** zrikka 2020-0408 */
	/**通过角色id 查询订单**/
	Salegoods selectSaleGoodsByRoleid(@Param("roleid") String roleid);
}