package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.ShangchengshopEntity;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class ShangChengShopUpdateServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShangChengShopUpdateServlet() {
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

		String gsonString = request.getParameter("Gson");
		String type = request.getParameter("type");// 1 删除 ,2 修改
		
		ShangchengshopEntity shangchengshopEntity = GsonUtil.getGsonUtil().getgson().fromJson(gsonString, ShangchengshopEntity.class);
		int result = 2;
		if ("1".equals(type)) {
			if (AllServiceUtil.getGoodsTableService().deleteShangChengShop(shangchengshopEntity) == 1) {
				result = 1;
			}
		} else if ("2".equals(type)) {
			if (AllServiceUtil.getGoodsTableService().updateShangChengShop(shangchengshopEntity) == 1) {
				result = 1;
			}
		}else if("3".equals(type)){
			if (AllServiceUtil.getGoodsTableService().addShangChengShop(shangchengshopEntity) == 1) {
				result = 1;
			}
		}

		PrintWriter printWriter = response.getWriter();
		printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
		printWriter.flush();
		printWriter.close();

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
