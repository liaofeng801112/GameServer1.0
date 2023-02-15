package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;


/**
 * 删除用户信息
 * @author Administrator
 *
 */
public class DeleteUserMesServerlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeleteUserMesServerlet() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 获得用户名信息
		String Username = request.getParameter("Username");
		//获取UID 信息
		String Uid = request.getParameter("Uid");
		
		String Controlstyle="";
		
		if(Username!=null){
			//判断用户是否存在
			UserTable userTable=AllServiceUtil.getUserTableService().selectForUsername(Username);
			if(userTable!=null){
				//删除用户表
				int a=AllServiceUtil.getUserTableService().delectUsertableForUsername(Username);
				if(a>0){
					//角色信息
					int a1=AllServiceUtil.getUserTableService().deleteRoletableForUid(userTable.getUser_id());
					if(a1>0){
						Controlstyle="1";
					}else  Controlstyle="2";
				}else{
					Controlstyle="2";
				}
			}else{
				
				Controlstyle="3";	
			}
		}else{
			Controlstyle="3";
		}
		
		//返回给用户信息
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(Controlstyle);
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

}
