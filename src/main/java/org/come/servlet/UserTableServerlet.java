package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.UsertableListBean;
import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 用户角色表操作表和查询接口
 * @author Administrator
 *
 */
public class UserTableServerlet extends HttpServlet {
	UserTable userTable;
	//查询返回的bean
	private UsertableListBean usertableListBean;
	
	//查询总页码
	int page=0,pagenowget=0;

	/**
	 * Constructor of the object.
	 */
	public UserTableServerlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		//用户名
		String username=request.getParameter("Username");
		//当前页码
		String Pagenow=request.getParameter("Pagenow");
		//状态
		String statues=request.getParameter("statues");
		//排序标识
		String type=request.getParameter("type");
		// zeng-190711修改---
		// 密码
		String userPasw = request.getParameter("userPasw");
		// zeng-190712--
		String safePasw = request.getParameter("safePasw");
		String userip = request.getParameter("userip");
		// zrikka-20200301
		String phone = request.getParameter("phone");
		// ----
		userTable=new UserTable();
		
		//设置查询标识
		userTable.setUseString(sureType(type));
		//增加用户名
		if(username!=null){
			  
			userTable.setUsername(username);  
		 }else{
			 userTable.setUsername(null);   
		 }
		//查询状态
		if(statues.equals(1+"")){
			userTable.setActivity((short) 1);
		}else{
			
			userTable.setActivity((short) 0);
		}
		//设置当前页码
		if(Pagenow!=null){
			
			userTable.setStart((Integer.valueOf(Pagenow)-1)*8);
			
			userTable.setEnd((Integer.valueOf(Pagenow))*8);
		}
		// zeng-190711修改---
		if (userPasw != null) {
			userTable.setUserpwd(userPasw);
		}
		// zeng-190712
		if (safePasw != null) {
			userTable.setSafety(safePasw);
		}
		if (userip != null) {
			userTable.setLoginip(userip);
			userTable.setRegisterip(userip);
		}
		// zrikka 20200301
		if(phone != null){
			userTable.setPhonenumber(phone);
		}
		// -----
		
		//查询符合条件额list
		
		List<UserTable> listForCondition=AllServiceUtil.getUserTableService().selectForConditionForUsertable(userTable);
		
		//查询总页码
		 page=AllServiceUtil.getUserTableService().selectUsterTableForConcition(userTable);
		 pagenowget=page/8;
		 if(page%8>0){
			 pagenowget++; 
		 }
		usertableListBean=new UsertableListBean();
		//设置当前页码
		usertableListBean.setUsertablelist(listForCondition);
		//设置总页码
		usertableListBean.setSumpage(pagenowget);
		//设置当前页码
		usertableListBean.setNowpage(Integer.valueOf(Pagenow) );
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(usertableListBean));
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
		// Put your code here
	}
	/**
	 * 依据状态进行确认排序条件
	 */
	public String sureType(String type){
		String returnMes="";
		switch (type) {
		case "0":
			returnMes="";
			
			break;
		case "1":
			returnMes="order by QID"	;	
			break;
		case "2":
			returnMes="order by SAFETY";
			break;
			
		case "3":
			returnMes="order by USERPWD";
			break;
		case "4":
			returnMes="order by USERNAME";
			break;
		// zeng-190712--
		case "5":
			returnMes = "order by REGISTERIP desc";
			break;
		case "6":
			returnMes = "order by LOGINIP desc";
			break;
		// --
	
		}
		
		
		
		return returnMes;
	
	}
}
