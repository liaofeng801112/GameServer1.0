package org.come.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.PayvipBean;

@MyBatisAnnotation
public interface PayvipBeanServerMapper{
	List<PayvipBean> selectAllVip();
	int deletePayvipBean(@Param("id") Integer id);
	int insertPayvioBean(PayvipBean payvipBean);
	int updatePayvioBean(PayvipBean payvipBean);
}
