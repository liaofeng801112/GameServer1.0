package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.PackRecord;
import org.come.entity.PackRecordExample;
@MyBatisAnnotation
public interface PackRecordMapper {
    /**
     *
     * @mbggenerated 2018-10-31
     */
    int countByExample(PackRecordExample example);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int deleteByExample(PackRecordExample example);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int deleteByPrimaryKey(BigDecimal roleId);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int insert(PackRecord record);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int insertSelective(PackRecord record);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    List<PackRecord> selectByExample(PackRecordExample example);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    PackRecord selectByPrimaryKey(BigDecimal roleId);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int updateByExampleSelective(@Param("record") PackRecord record, @Param("example") PackRecordExample example);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int updateByExample(@Param("record") PackRecord record, @Param("example") PackRecordExample example);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int updateByPrimaryKeySelective(PackRecord record);

    /**
     *
     * @mbggenerated 2018-10-31
     */
    int updateByPrimaryKey(PackRecord record);
    
    /**增加水路大会积分*/
    void addSLDH(@Param("roleid")BigDecimal roleid,@Param("add")int add);
    /**清空所有水路大会积分*/
    void emptySLDH();
}