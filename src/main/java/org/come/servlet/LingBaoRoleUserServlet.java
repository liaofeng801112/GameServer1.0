package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.LingbaoRoleUser;
import org.come.entity.SearchLingbaoList;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class LingBaoRoleUserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LingBaoRoleUserServlet() {
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
		String lingbaoname = request.getParameter("Lingbaoname");
		// 用户名
		String roleName = request.getParameter("RoleName");
		// 当前页
		String pagenum = request.getParameter("Pagenum");
		// 排序类型
		String style = request.getParameter("style");

		SearchLingbaoList result = new SearchLingbaoList();
		if (lingbaoname == null || roleName == null || pagenum == null || style == null) {
			// 返回给用户信息
			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
			pwPrintWriter.flush();
			pwPrintWriter.close();
			return;
		}

		LingbaoRoleUser lru = new LingbaoRoleUser();
		lru.setBaoname(lingbaoname);
		lru.setRolename(roleName);
		if ("".equals(pagenum)) {
			lru.setPageNow(0);
		} else {
			lru.setPageNow(Integer.valueOf(pagenum));
		}

		if ("0".equals(style)) {
			lru.setOrderBy("");
		} else {
			switch (style) {
			case "1":
				lru.setOrderBy(" ORDER BY BAONAME ");
				break;
			case "2":
				lru.setOrderBy(" ORDER BY ROLENAME ");
				break;
			default:
				break;
			}
		}

		Integer lruCount = AllServiceUtil.getLingbaoService().selectLingBaoRUCount(lru);
		List<LingbaoRoleUser> lruList = AllServiceUtil.getLingbaoService().selectLingBaoRU(lru);

		Integer sumpage = 0;
		if (lruCount % 10 == 0) {
			sumpage = lruCount / 10;
		} else {
			sumpage = (lruCount / 10) + 1;
		}

		result.setLingbaolist(lruList);
		result.setSumpage(sumpage);

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
