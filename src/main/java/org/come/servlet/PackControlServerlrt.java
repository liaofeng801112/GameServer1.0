package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.GoodsRoleUser;
import org.come.entity.SearchGoodstableList;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 背包serverlet接口
 * 
 * @author Administrator
 * 
 */
public class PackControlServerlrt extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PackControlServerlrt() {
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

		doPost(request, response);
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

		// 物品名称
		String goodsname = request.getParameter("Goodsname");
		// 用户名
		String RoleName = request.getParameter("RoleName");
		// 物品加成
		String Goodsvalue = request.getParameter("Goodsvalue");
		// 状态
		String Statues = request.getParameter("Statues");
		// 物品ID
		String goodsId = request.getParameter("goodsId");
		// 当前页码，默认1
		String Pagenum = request.getParameter("Pagenum");
		// 排序状态
		String style = request.getParameter("style");

		SearchGoodstableList result = new SearchGoodstableList();
		if (goodsname == null || RoleName == null || Goodsvalue == null || Statues == null || goodsId == null || Pagenum == null || style == null) {
			// 返回给用户信息
			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
			pwPrintWriter.flush();
			pwPrintWriter.close();
			return;
		}
		/**查询操作*/
		GoodsRoleUser goodsRoleUser = new GoodsRoleUser();
		goodsRoleUser.setGoodsname(goodsname);
		goodsRoleUser.setRolename(RoleName);
		goodsRoleUser.setValue(Goodsvalue);
		// goodsRoleUser.setStatus(Statues);
		if ("".equals(goodsId)) {
			goodsRoleUser.setGoodsid(new BigDecimal(0));
		} else {
			goodsRoleUser.setGoodsid(new BigDecimal(goodsId));
		}
		if ("".equals(Pagenum)) {
			goodsRoleUser.setPageNow(0);
		} else {
			goodsRoleUser.setPageNow(Integer.valueOf(Pagenum));
		}
		if ("0".equals(style)) {
			goodsRoleUser.setOrderBy("");
		} else {
			switch (style) {
			case "1":
				goodsRoleUser.setOrderBy(" ORDER BY GOODSNAME ");
				break;
			case "2":
				goodsRoleUser.setOrderBy(" ORDER BY GOODSID ");
				break;
			case "3":
				goodsRoleUser.setOrderBy(" ORDER BY VALUE ");
				break;
			case "4":
				goodsRoleUser.setOrderBy(" ORDER BY USETIME desc");
				break;
			case "5":
				goodsRoleUser.setOrderBy(" ORDER BY ROLENAME ");
				break;
			default:
				break;
			}
		}
		// System.out.println("Statues: " + Statues);
		if ("".equals(Statues)) {
			goodsRoleUser.setStatus("");
		} else {
			switch (Statues) {
			case "6":
				goodsRoleUser.setStatus("0");
				break;
			case "7":
				goodsRoleUser.setStatus("1");
				break;
			case "8":
				goodsRoleUser.setStatus("2");
				break;
			case "9":
				goodsRoleUser.setStatus("");
				break;
			default:
				break;
			}
		}
		// System.out.println("status: " + goodsRoleUser.getStatus());

		Integer goodCount = AllServiceUtil.getGoodsRoleUsertService().selectGoodsCount(goodsRoleUser);
		Integer sumPage = 0;
		// 设置总页数
		if (goodCount % 10 == 0) {
			sumPage = goodCount / 10;
		} else {
			sumPage = (goodCount / 10) + 1;
		}
		List<GoodsRoleUser> goodsList = AllServiceUtil.getGoodsRoleUsertService().selectGoodsByPage(goodsRoleUser);

		result.setListGoodsTable(goodsList);
		result.setSumpage(sumPage);
		// result.setPageNow(Integer.valueOf(Pagenum));

		// 返回给用户信息
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
		pwPrintWriter.flush();
		pwPrintWriter.close();

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
