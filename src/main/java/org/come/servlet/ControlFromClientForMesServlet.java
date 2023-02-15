package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.RequestReturnBean;
import org.come.bean.ServiceArea;
import org.come.bean.managerTable;
import org.come.service.IUserTableService;
import org.come.serviceImpl.ExpensesReceiptsServiceImpl;
import org.come.serviceImpl.ServiceAreaServiceImpl;
import org.come.serviceImpl.UserTableServiceImpl;
import org.come.serviceImpl.managerTableServiceImpl;
import org.come.until.GetTime;
import org.come.until.GsonUtil;
import org.come.until.InterfaceControlUntil;



/**
 * 对外来接口进行处理servelet
 * @author Administrator
 *
 */

public class ControlFromClientForMesServlet extends HttpServlet {



	//用户登录操作iml
	private managerTableServiceImpl impl;

	//查询用户下的区域
	private ServiceAreaServiceImpl  serimpl;

	//充值接口
	private ExpensesReceiptsServiceImpl expenImpl;

	//返回的对象
	private RequestReturnBean requestReturnBean;

	//物品记录
	private IUserTableService userTableService;

	//区域
	private ServiceAreaServiceImpl serviceAreaServiceImpl;

	//交易
	private ExpensesReceiptsServiceImpl expensesReceiptsServiceImpl;



	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ControlFromClientForMesServlet() {
		super();
		impl=new managerTableServiceImpl();

		serimpl=new ServiceAreaServiceImpl();

		requestReturnBean=new RequestReturnBean();
		userTableService = new UserTableServiceImpl();

		serviceAreaServiceImpl=new ServiceAreaServiceImpl();

		expensesReceiptsServiceImpl=new ExpensesReceiptsServiceImpl();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); 

	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 获得登录信息
		String condition = request.getParameter("Service");
		requestReturnBean=GsonUtil.getGsonUtil().getgson().fromJson(condition, RequestReturnBean.class);
		if(requestReturnBean.getRequresHeader().equals("10006")){
			managerTable manege= GsonUtil.getGsonUtil().getgson().fromJson(requestReturnBean.getContent(),managerTable.class);
			requestReturnBean=InterfaceControlUntil.controlManagerControl(requestReturnBean.getContent(), impl, serimpl);
            if(manege.getControlStyle()==6666){
            	request.getSession().setAttribute("manger", manege);
            	request.getSession().setAttribute("agentNameList", GsonUtil.getGsonUtil().getgson().fromJson(requestReturnBean.getContent(), List.class));}


		}else if(requestReturnBean.getRequresHeader().equals("10007")){
			ServiceArea service1= GsonUtil.getGsonUtil().getgson().fromJson(requestReturnBean.getContent(),ServiceArea.class);
			requestReturnBean=InterfaceControlUntil.serviceMoneyControl(requestReturnBean.getContent(), serviceAreaServiceImpl, expensesReceiptsServiceImpl);

			if(service1.getControlStyle()==10005){
				request.getSession().setAttribute("areaName", GsonUtil.getGsonUtil().getgson().fromJson(requestReturnBean.getContent(), List.class));
			}
		}else{
			requestReturnBean.setContent("请求失败");

			//设置返回消息true
			requestReturnBean.setStyle("error");

			requestReturnBean.setRequresHeader("000000");

			//设置返回时间
			requestReturnBean.setReturnDate(GetTime.getNowMinit());
		}
	//	System.out.println("返回用户信息:"+GsonUtil.getGsonUtil().getgson().toJson(requestReturnBean));
		//返回给用户信息
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(requestReturnBean));
		pwPrintWriter.flush();
		pwPrintWriter.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
	}


}
