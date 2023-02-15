package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.PayvipBean;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class PayvipBeanUpdateServlet extends HttpServlet  {
	/**
	 * Constructor of the object.
	 */
	public PayvipBeanUpdateServlet() {
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
		String type = request.getParameter("Type");
		String Gson = request.getParameter("Gson");
		PayvipBean payvipBean = GsonUtil.getGsonUtil().getgson().fromJson(Gson, PayvipBean.class);
		String Controlstyle = "";
		int aa = Integer.parseInt(type);
		switch (aa) {
		case 1://修改
			int a =AllServiceUtil.getPayvipBeanServer().updatePayvioBean(payvipBean);
			if(a>0){
				Controlstyle=1+"";
			}else {
				Controlstyle=2+"";
			}
			break;
		case 2://删除
			int a2 =AllServiceUtil.getPayvipBeanServer().deletePayvioBean(payvipBean.getId());
			if(a2>0){
				Controlstyle=1+"";
			}else {
				Controlstyle=2+"";
			}
			break;
		case 3://新增
			int a3 =AllServiceUtil.getPayvipBeanServer().insertPayvioBean(payvipBean);
			if(a3>0){
				Controlstyle=1+"";
			}else {
				Controlstyle=2+"";
			}
			break;
		default:
			break;
		}
		GameServer.refreshBean();
		
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
