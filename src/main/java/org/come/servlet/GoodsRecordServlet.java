package org.come.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.Goodsrecord;
import org.come.service.IGoodsrecordService;
import org.come.serviceImpl.GoodsrecordServiceImpl;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageInfo;
/**
 * 物品记录
 * @author 叶豪芳
 *
 */
public class GoodsRecordServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IGoodsrecordService goodsrecordService;

	/**
	 * Constructor of the object.
	 */
	public GoodsRecordServlet() {
		super();
		goodsrecordService = new GoodsrecordServiceImpl();
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

		// 获得条件和页数
		String condition = request.getParameter("goodsrecord");
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		// 查询结果
		PageInfo<Goodsrecord> list = goodsrecordService.selectGoodsRecord(pageNum, condition);
		
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(list));
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
