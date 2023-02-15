package org.come.until;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.come.bean.DayForOneAreaServiceMonthBean;
import org.come.bean.OneAreaServiceMonthBean;
import org.come.bean.RequestReturnBean;
import org.come.bean.ServiceArea;
import org.come.bean.ServiceReturnListBean;
import org.come.bean.managerTable;
import org.come.entity.ExpensesReceipts;
import org.come.serviceImpl.ExpensesReceiptsServiceImpl;
import org.come.serviceImpl.ServiceAreaServiceImpl;
import org.come.serviceImpl.managerTableServiceImpl;


/**
 * 处理接口信息类
 * @author Administrator
 *
 */
public class InterfaceControlUntil {
	//返回的对象
	private static RequestReturnBean requestReturnBean=new RequestReturnBean();

	//依据区域查询返回的bean 类
	private static ServiceReturnListBean serviceReturnListBean=new ServiceReturnListBean();

	//年暗语统计
	private static OneAreaServiceMonthBean areaServiceMonthBean=new OneAreaServiceMonthBean();

	//月日统计表
	private static DayForOneAreaServiceMonthBean dayForOneAreaServiceMonthBean=new DayForOneAreaServiceMonthBean();

	//充值记录bean
	private static ExpensesReceipts expensesReceipts=new ExpensesReceipts();
	/**
	 * 管理员登录管理
	 * 传入验证信息
	 */
	public static  RequestReturnBean controlManagerControl( String mes,managerTableServiceImpl impl,ServiceAreaServiceImpl  serimpl){
		
		managerTable manege= GsonUtil.getGsonUtil().getgson().fromJson(mes,managerTable.class);
		if(manege!=null){
			if(manege.getControlStyle()==6666){
				//登录操作
				// 查询结果
				managerTable returnManager=	impl.selectByUsername(manege);
				if(returnManager.getPwd().equals(manege.getPwd())){

					//依据用户id 查询管理的区域
					List<ServiceArea> userSerbice=serimpl.selectListAreaForUid(returnManager.getManaeid());

					if((userSerbice.size()!=0)||(userSerbice!=null)){
						//查询拥有区域 
						String content=GsonUtil.getGsonUtil().getgson().toJson(userSerbice);

						requestReturnBean.setContent(content);

						//设置返回消息true
						requestReturnBean.setStyle("success");	
						
						requestReturnBean.setRequresHeader(returnManager.getFlag()+"");

					}


				}else {

					requestReturnBean.setContent("");

					//设置返回消息true
					requestReturnBean.setStyle("error");	 


				}
			}
			//删除
			else if(manege.getControlStyle()==10002){
				int a=impl.deleteByPrimaryKey(manege.getManaeid());
				if(a==1){
					requestReturnBean.setContent("删除成功");

					//设置返回消息true
					requestReturnBean.setStyle("success");	 
				}else{

					requestReturnBean.setContent("删除失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");	 
				}


			}//增加
			else if(manege.getControlStyle()==10000){
				int a=impl.insert(manege);
				if(a==1){
					requestReturnBean.setContent("插入成功");

					//设置返回消息true
					requestReturnBean.setStyle("success");	 	
				}else{
					requestReturnBean.setContent("插入失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");

				}

			}//修改
			else if(manege.getControlStyle()==10001){

				int a=impl.updateByPrimaryKey(manege);
				if(a==1){
					requestReturnBean.setContent("修改成功");

					//设置返回消息true
					requestReturnBean.setStyle("success");
				}else{

					requestReturnBean.setContent("修改失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");
				}

			}//查询
			else if(manege.getControlStyle()==10003){

				List<managerTable> manegerlist=impl.selectManageForPage(manege.getPageNumber());
				String content=GsonUtil.getGsonUtil().getgson().toJson(manegerlist);
				requestReturnBean.setContent(content);
				requestReturnBean.setStyle("success");

			}
		}else{
			//没有发送请求消息

			requestReturnBean.setContent("请求错误");

			//设置返回消息true
			requestReturnBean.setStyle("error");

		}
		//设置返回时间
		requestReturnBean.setReturnDate(GetTime.getNowMinit());
		//设置协议头
		requestReturnBean.setStyle(10006+"");
		return requestReturnBean;	
	}



	/**
	 * 依据区域查询充值记录,和统计表
	 */
	public static RequestReturnBean serviceMoneyControl(String mes,ServiceAreaServiceImpl serviceAreaServiceImpl,ExpensesReceiptsServiceImpl expensesReceiptsServiceImpl){
		ServiceArea service= GsonUtil.getGsonUtil().getgson().fromJson(mes,ServiceArea.class);
		if(service!=null){
			//传送来的信息不是空的
			if(service.getControlStyle()==10000){
				//增加区域
				int a=serviceAreaServiceImpl.insert(service);	
				if(a==1){
					//添加成功
					requestReturnBean.setContent("添加成功");

					//设置返回消息true
					requestReturnBean.setStyle("success");
				}else{
					//添加失败
					requestReturnBean.setContent("添加失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");
				}

			}else  if(service.getControlStyle()==10001){
				//修改区域
				int a=	serviceAreaServiceImpl.updateByPrimaryKey(service);
				if(a==1){
					requestReturnBean.setContent("修改成功");

					//设置返回消息true
					requestReturnBean.setStyle("success");

				}else{

					requestReturnBean.setContent("修改失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");


				}

			}else  if(service.getControlStyle()==10002){
				//删除
				int a=serviceAreaServiceImpl.deleteByPrimaryKey(service.getSid());
				if(a==1){
					requestReturnBean.setContent("删除成功");
					//设置返回消息true
					requestReturnBean.setStyle("success");
				}else{

					requestReturnBean.setContent("删除失败");

					//设置返回消息true
					requestReturnBean.setStyle("error");

				}

			}else  if(service.getControlStyle()==10003){
				//查询初始化查询
				//月统计日报表(设置区域ID)
				dayForOneAreaServiceMonthBean=new DayForOneAreaServiceMonthBean();
				areaServiceMonthBean=new OneAreaServiceMonthBean();
				areaServiceMonthBean=new OneAreaServiceMonthBean();
				areaServiceMonthBean.setSid(service.getSid());
				dayForOneAreaServiceMonthBean.setSid(service.getSid());
				//设置区域ID
				expensesReceipts.setSid(service.getSid());

				//设置当前月份
				Calendar calendar=Calendar.getInstance();
				//获得当前时间的月份，月份从0开始所以结果要加1
				int month=calendar.get(Calendar.MONTH)+1;
				dayForOneAreaServiceMonthBean.setMonth(month+"");
				areaServiceMonthBean.setYear(calendar.get(Calendar.YEAR)+"");
				//查询月日统计
				dayForOneAreaServiceMonthBean=expensesReceiptsServiceImpl.selectChartForDayWithSid(dayForOneAreaServiceMonthBean);

				//年月统计
				areaServiceMonthBean=expensesReceiptsServiceImpl.selectChartForMoneth(areaServiceMonthBean);

				//按页数查询"selectAllForAreaId"
				expensesReceipts.setPageNumber(1);

				List<ExpensesReceipts> listExpen=expensesReceiptsServiceImpl.selectAllForAreaId(expensesReceipts);

				//设置返回bean
				serviceReturnListBean.setAreaServiceMonthBean(areaServiceMonthBean);
				serviceReturnListBean.setDayForOneAreaServiceMonthBean(dayForOneAreaServiceMonthBean);
				serviceReturnListBean.setExpensesReceiptsList(listExpen);
				String content=GsonUtil.getGsonUtil().getgson().toJson(serviceReturnListBean);				   
				//设置返回类型
				requestReturnBean.setContent(content);
				requestReturnBean.setStyle("success"); 

			}else  if(service.getControlStyle()==10004){
				//分页查询
				//按页数查询"selectAllForAreaId"
				expensesReceipts.setPageNumber(service.getPageNum());
				expensesReceipts.setSid(new BigDecimal(service.getSid()+""));
				List<ExpensesReceipts> listExpen=expensesReceiptsServiceImpl.selectAllForAreaId(expensesReceipts);
				String content = null;
				if((listExpen!=null)&&(listExpen.size()!=0)){
					//设置返回bean
					serviceReturnListBean.setAreaServiceMonthBean(null);
					serviceReturnListBean.setDayForOneAreaServiceMonthBean(null);
					serviceReturnListBean.setExpensesReceiptsList(listExpen);
					content=GsonUtil.getGsonUtil().getgson().toJson(serviceReturnListBean);	
					requestReturnBean.setStyle("success"); 	}	
				else{
					content="最新一页";
					requestReturnBean.setStyle("error"); }
				//设置返回类型
				requestReturnBean.setContent(content);
				//requestReturnBean.setStyle("success"); 	
			}else if(service.getControlStyle()==10005){
				//查询当前分页的区域信息
				//设置返回类型

				List<ServiceArea> serviceList=serviceAreaServiceImpl.selectServiceForPage(service.getPageNum());
				String content=GsonUtil.getGsonUtil().getgson().toJson(serviceList);	
				requestReturnBean.setContent(content);
				requestReturnBean.setStyle("success"); 		

			}

		}else{

			requestReturnBean.setContent("请求错误");

			//设置返回消息true
			requestReturnBean.setStyle("error");  

		}








		//设置返回时间
		requestReturnBean.setReturnDate(GetTime.getNowMinit());
		//设置协议头
		requestReturnBean.setStyle(10007+"");
		return requestReturnBean;	

	}

}
