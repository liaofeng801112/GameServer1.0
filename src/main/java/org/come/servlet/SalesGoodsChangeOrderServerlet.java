package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import redis.clients.jedis.Jedis;

/**
 * 进行物品状态的更改
 * @author Administrator
 *
 */
public class SalesGoodsChangeOrderServerlet extends HttpServlet {
	
	/**
	 * redis状态操作
	 */
    private Jedis jedis;
	/**
	 * Constructor of the object.
	 */
	public SalesGoodsChangeOrderServerlet() {
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
		
		//商品ID
		String Sale_id=request.getParameter("Sale_id");
		String Sale_id_statues="Sale_id_statues=error";
		String type=request.getParameter("Type");
		//更改状态
		if((Sale_id!=null)&&(type!=null)){
			AllServiceUtil.getSalegoodsService().updateFlag(new BigDecimal(Sale_id), Integer.valueOf(type));
			Sale_id_statues="Sale_id_statues=success";
		}
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(Sale_id_statues));
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
