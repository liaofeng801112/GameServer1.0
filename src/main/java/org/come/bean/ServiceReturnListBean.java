package org.come.bean;

import java.util.List;

import org.come.entity.ExpensesReceipts;


/**
 * 区域查询的时候
 * 
 * 返回第一页的充值记录
 * 
 * 当月的日统计报表
 * 
 * 
 * 当月的月统计报表
 * @author Administrator
 *
 */
public class ServiceReturnListBean {
	
   //返回年月统计
	private OneAreaServiceMonthBean areaServiceMonthBean;
	//返回月日统计
	private DayForOneAreaServiceMonthBean dayForOneAreaServiceMonthBean;
	//返回当页的查询数据
	private List<ExpensesReceipts> expensesReceiptsList;
	public OneAreaServiceMonthBean getAreaServiceMonthBean() {
		return areaServiceMonthBean;
	}
	public void setAreaServiceMonthBean(OneAreaServiceMonthBean areaServiceMonthBean) {
		this.areaServiceMonthBean = areaServiceMonthBean;
	}
	public DayForOneAreaServiceMonthBean getDayForOneAreaServiceMonthBean() {
		return dayForOneAreaServiceMonthBean;
	}
	public void setDayForOneAreaServiceMonthBean(
			DayForOneAreaServiceMonthBean dayForOneAreaServiceMonthBean) {
		this.dayForOneAreaServiceMonthBean = dayForOneAreaServiceMonthBean;
	}
	public List<ExpensesReceipts> getExpensesReceiptsList() {
		return expensesReceiptsList;
	}
	public void setExpensesReceiptsList(List<ExpensesReceipts> expensesReceiptsList) {
		this.expensesReceiptsList = expensesReceiptsList;
	}

}
