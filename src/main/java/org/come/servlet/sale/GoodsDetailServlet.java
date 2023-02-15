package org.come.servlet.sale;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.GoodsDetailsBean;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.RoleSummoning;
import org.come.entity.Salegoods;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 藏宝阁商品详情
 * @author 
 *
 */
public class GoodsDetailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor of the object.
	 */
	public GoodsDetailServlet() {
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

		// 商品表ID
		String saleid = request.getParameter("saleid");
		// 商品对应的数据表ID
		String otherid = request.getParameter("otherid");
		// 商品种类表类型 1物品表，2召唤兽表  3灵宝表
		String saletype = request.getParameter("saletype");

		// 商品详情返回bean
		GoodsDetailsBean bean = new GoodsDetailsBean();
		// 根据表类型查找表数据
		if( "3".equals(saletype) || "5".equals(saletype) ){// 物品
			Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(otherid));
			bean.setGoodstable(goodstable);
		}else if( "4".equals(saletype) ){// 召唤兽
			RoleSummoning pet = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(new BigDecimal(otherid));
			bean.setRoleSummoning(pet);
		}else if( "6".equals(saletype) ){// 灵宝
			Lingbao lingbao = AllServiceUtil.getLingbaoService().selectByPrimaryKey(new BigDecimal(otherid));
			bean.setLingbao(lingbao);
		}
		// 查找商品
		Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(new BigDecimal(saleid));
		bean.setSalegoods(salegoods);
		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(bean));
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
