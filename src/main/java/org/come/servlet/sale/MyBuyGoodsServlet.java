package org.come.servlet.sale;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.SearchGoodsResultBean;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.entity.SalegoodsExample.Criteria;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 藏宝阁我的收购
 * @author 
 *
 */
public class MyBuyGoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor of the object.
	 */
	public MyBuyGoodsServlet() {
		super();
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

		// 页数
		String pageNum = request.getParameter("page");
		// 购买者ID
		String roleid = request.getParameter("Role_Id");
		// 状态
		String status = request.getParameter("status");
		
		// 查找商品绑定买家是该角色的商品
		SalegoodsExample example = new SalegoodsExample();
		Criteria c = example.createCriteria();
		c.andRoleidEqualTo(new BigDecimal(roleid));
		
		// 分页查询
		PageHelper.startPage(Integer.parseInt(pageNum), 15);
		List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
		PageInfo<Salegoods> pageInfo = new PageInfo<>(list);
		
		// 返回对象
		SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
		resultBean.setSalegoods(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
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
