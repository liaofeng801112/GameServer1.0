package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.UserxyandroledhbcrEntity;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * HGC-2019-12-05 查询总消耗与总充值
 * 
 * @author Administrator
 * 
 */
public class RechargeConsumeSumServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RechargeConsumeSumServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		String type = request.getParameter("type");

		PrintWriter writer = response.getWriter();
		if ("1".equals(type)) {
			UserxyandroledhbcrEntity selectRechargeConsumeNowSum = AllServiceUtil.getUserTableService().selectRechargeConsumeNowSum();
			writer.write(GsonUtil.getGsonUtil().getgson().toJson(selectRechargeConsumeNowSum));
		} else if ("2".equals(type)) {
			String time = request.getParameter("time");
			List<UserxyandroledhbcrEntity> selectRechargeConsumeSum = AllServiceUtil.getUserTableService().selectRechargeConsumeSum(time);
			writer.write(GsonUtil.getGsonUtil().getgson().toJson(selectRechargeConsumeSum));
		}
		writer.flush();
		writer.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
