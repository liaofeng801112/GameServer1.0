package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.RolesummoningRoleUser;
import org.come.entity.SearchSumminglist;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class RoleSummoningRoleUserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RoleSummoningRoleUserServlet() {
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

		// ?????????id
		String summingId = request.getParameter("summingId");
		// ?????????
		String roleName = request.getParameter("RoleName");
		// ??????
		String skill = request.getParameter("skill");
		// ?????????
		String pagenum = request.getParameter("Pagenum");
		// ????????????
		String style = request.getParameter("style");

		SearchSumminglist result = new SearchSumminglist();
		if (summingId == null || roleName == null || skill == null || pagenum == null || style == null) {
			// ?????????????????????
			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
			pwPrintWriter.flush();
			pwPrintWriter.close();
			return;
		}

		/**
		 * ????????????
		 */
		RolesummoningRoleUser rru = new RolesummoningRoleUser();
		rru.setSummoningid(summingId);
		rru.setRolename(roleName);
		rru.setSkill(skill);
		if ("".equals(pagenum)) {
			rru.setPageNow(0);
		} else {
			rru.setPageNow(Integer.valueOf(pagenum));
		}
		if ("0".equals(style)) {
			rru.setOrderBy("");
		} else {
			switch (style) {
			case "1":
				rru.setOrderBy(" ORDER BY SUMMONINGID ");
				break;
			case "2":
				rru.setOrderBy(" ORDER BY SUMMONINGNAME ");
				break;
			case "3":
				rru.setOrderBy(" ORDER BY ROLENAME ");
				break;
			default:
				break;
			}
		}

		Integer goodCount = AllServiceUtil.getRoleSummoningService().selectRsRUCount(rru);
		Integer sumPage = 0;
		// ???????????????
		if (goodCount % 10 == 0) {
			sumPage = goodCount / 10;
		} else {
			sumPage = (goodCount / 10) + 1;
		}
		List<RolesummoningRoleUser> rsRUList = AllServiceUtil.getRoleSummoningService().selectRsRU(rru);

		result.setRolesummingList(rsRUList);
		result.setSumpage(sumPage);
		// result.setPageNow(Integer.valueOf(Pagenum));

		// ?????????????????????
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
