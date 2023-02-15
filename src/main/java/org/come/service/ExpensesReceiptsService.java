package org.come.service;


import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.bean.DayForOneAreaServiceMonthBean;
import org.come.bean.OneAreaServiceMonthBean;
import org.come.entity.ExpensesReceipts;


public interface ExpensesReceiptsService {
    /**
     *
     * @mbggenerated 2018-06-03
     */
    int deleteByPrimaryKey(BigDecimal erid);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int insert(ExpensesReceipts record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int insertSelective(ExpensesReceipts record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    ExpensesReceipts selectByPrimaryKey(BigDecimal erid);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int updateByPrimaryKeySelective(ExpensesReceipts record);

    /**
     *
     * @mbggenerated 2018-06-03
     */
    int updateByPrimaryKey(ExpensesReceipts record);
    
List<ExpensesReceipts> selectTimeAll(@Param("start")String start, @Param("end")String end);
	
	/**
	 * 依据Id查询报表List
	 */
	List<ExpensesReceipts>  selectAllForAreaId(ExpensesReceipts record);
	
	/**
	 * 依据区号查询每月的报表
	 */
	OneAreaServiceMonthBean selectChartForMoneth(OneAreaServiceMonthBean areaServiceMonthBean);
	
	/**
	 * 依据区号查询每日的报表
	 */
	DayForOneAreaServiceMonthBean selectChartForDayWithSid(DayForOneAreaServiceMonthBean areaServiceMonthBean);
	
    List<Integer> selectAllfyId(@Param("sid")Integer sid,@Param("start")String start, @Param("end")String end);


    /**
     * 查询当前所有的充值个数
     */
    int selectAllTotal(ExpensesReceipts expensesReceipts);

    List<ExpensesReceipts> selectAll(ExpensesReceipts expensesReceipts);

}