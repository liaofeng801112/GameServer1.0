package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Roleorder;
import org.come.entity.RoleorderExample;
@MyBatisAnnotation
public interface RoleorderMapper {
	
	/**
	 * 查询角色订单
	 * @param roleid
	 * @return
	 */
	List<Roleorder> selectRoleOrders(BigDecimal roleid);
    /**
     *
     * @mbggenerated 2019-03-06
     */
    int countByExample(RoleorderExample example);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int deleteByExample(RoleorderExample example);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int deleteByPrimaryKey(BigDecimal orderid);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int insert(Roleorder record);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int insertSelective(Roleorder record);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    List<Roleorder> selectByExample(RoleorderExample example);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    Roleorder selectByPrimaryKey(BigDecimal orderid);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int updateByExampleSelective(@Param("record") Roleorder record, @Param("example") RoleorderExample example);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int updateByExample(@Param("record") Roleorder record, @Param("example") RoleorderExample example);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int updateByPrimaryKeySelective(Roleorder record);

    /**
     *
     * @mbggenerated 2019-03-06
     */
    int updateByPrimaryKey(Roleorder record);
}