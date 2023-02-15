package org.come.service;


import java.math.BigDecimal;
import java.util.List;

import org.come.bean.ServiceArea;



public interface ServiceAreaService {
    /**
     *
     * @mbggenerated 2018-06-03
     */
    int deleteByPrimaryKey(BigDecimal sid);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int insert(ServiceArea record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int insertSelective(ServiceArea record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    ServiceArea selectByPrimaryKey(BigDecimal sid);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int updateByPrimaryKeySelective(ServiceArea record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int updateByPrimaryKey(ServiceArea record);

	List<BigDecimal> selectServiceAreaid(ServiceArea record);
	 
    /**
     * 查询所有的服务区
     */
    List<ServiceArea> selectAllService();
    /**
     * 依据用户ID查询对应的区域信息
     */
    List<ServiceArea>  selectListAreaForUid(BigDecimal manageid);
    /**
     * 依据页码查询所有服务区
     */
    List<ServiceArea> selectServiceForPage(int pageNumber);
}