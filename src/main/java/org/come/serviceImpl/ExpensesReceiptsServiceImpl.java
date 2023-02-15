package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.DayForOneAreaServiceMonthBean;
import org.come.bean.OneAreaServiceMonthBean;
import org.come.entity.ExpensesReceipts;
import org.come.mapper.ExpensesReceiptsMapper;
import org.come.service.ExpensesReceiptsService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class ExpensesReceiptsServiceImpl  implements ExpensesReceiptsService{
	private ExpensesReceiptsMapper expensesReceiptsMapper;
	public ExpensesReceiptsServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		expensesReceiptsMapper = (ExpensesReceiptsMapper)ctx.getBean("expensesReceiptsMapper");
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal erid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ExpensesReceipts record) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.insert(record);
	}

	@Override
	public int insertSelective(ExpensesReceipts record) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.insertSelective(record);
	}

	@Override
	public ExpensesReceipts selectByPrimaryKey(BigDecimal erid) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectByPrimaryKey(erid);
	}

	@Override
	public int updateByPrimaryKeySelective(ExpensesReceipts record) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ExpensesReceipts record) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ExpensesReceipts> selectTimeAll(String start, String end) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectTimeAll(start, end);
	}

	@Override
	public List<ExpensesReceipts> selectAllForAreaId(ExpensesReceipts record) {
		// TODO Auto-generated method stub           ExpensesReceipts expensesReceipts
		return expensesReceiptsMapper.selectAllForAreaId(record);
	}

	@Override
	public OneAreaServiceMonthBean selectChartForMoneth(
			OneAreaServiceMonthBean areaServiceMonthBean) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectChartForMoneth(areaServiceMonthBean);
	}

	@Override
	public DayForOneAreaServiceMonthBean selectChartForDayWithSid(
			DayForOneAreaServiceMonthBean areaServiceMonthBean) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectChartForDayWithSid(areaServiceMonthBean);
	}

	@Override
	public List<Integer> selectAllfyId(Integer sid, String start, String end) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectAllfyId(sid, start, end);
	}
	@Override
	public int selectAllTotal(ExpensesReceipts expensesReceipts) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectAllTotal(expensesReceipts);
	}

	@Override
	public List<ExpensesReceipts> selectAll(ExpensesReceipts expensesReceipts) {
		// TODO Auto-generated method stub
		return expensesReceiptsMapper.selectAll(expensesReceipts);
	}
}
