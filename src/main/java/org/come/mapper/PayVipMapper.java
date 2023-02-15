package org.come.mapper;

import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.PayvipBean;

@MyBatisAnnotation
public interface PayVipMapper {

	List<PayvipBean> selectAllVip();
}
