package org.come.service;

import java.util.List;

import org.come.entity.PayvipBean;


public interface PayvipBeanServer {
	/**
	 * 查询vip 分页查询
	 */
	List<PayvipBean> selectAllVip();
	List<PayvipBean> selectVipPage(int page);
	int deletePayvioBean(Integer id);
	int insertPayvioBean(PayvipBean payvipBean);
	int updatePayvioBean(PayvipBean payvipBean);
}
