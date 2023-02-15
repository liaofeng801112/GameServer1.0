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
 * 进行salegoods 物品进行
 * @author Administrator
 *
 */
public class SaleGoodsStatuesServerlet extends HttpServlet {

	//获取jedis 存储对象
	private Jedis jedis;
	/**
	 * Constructor of the object.
	 */
	public SaleGoodsStatuesServerlet() {
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
		
		//获取参数
		String Sale_id=request.getParameter("Sale_id");
		//返回状态
		String Type="Type=4";
		//请求商品状态
		if(Sale_id!=null){
			Integer flag=AllServiceUtil.getSalegoodsService().selectFlag(new BigDecimal(Sale_id));
			if (flag!=null) {
				if(flag==1){
					Type="Type=1";	
				}else if(flag==2){
					Type="Type=2";
				}else if(flag==3){
					Type="Type=3";
				}
			}else {
				Type="Type=4";
			}
		}
		
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(Type));
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
