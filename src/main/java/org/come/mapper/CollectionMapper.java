package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Collection;
import org.come.entity.CollectionExample;
@MyBatisAnnotation
public interface CollectionMapper {
	
	List<Collection> selectRoleCollect(BigDecimal roleid);
	/**
	 * 查询用户收藏列表
	 * @param roleid
	 * @return
	 */
	List<BigDecimal> selectUserCollection(BigDecimal roleid);
    /**
     *
     * @mbggenerated 2019-03-05
     */
    int countByExample(CollectionExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int deleteByExample(CollectionExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int deleteByPrimaryKey(BigDecimal colid);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int insert(Collection record);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int insertSelective(Collection record);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    List<Collection> selectByExample(CollectionExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    Collection selectByPrimaryKey(BigDecimal colid);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int updateByExampleSelective(@Param("record") Collection record, @Param("example") CollectionExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int updateByExample(@Param("record") Collection record, @Param("example") CollectionExample example);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int updateByPrimaryKeySelective(Collection record);

    /**
     *
     * @mbggenerated 2019-03-05
     */
    int updateByPrimaryKey(Collection record);
}