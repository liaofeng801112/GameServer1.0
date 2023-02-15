package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.MountRoleUser;
import org.come.entity.SearchMontList;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class MountRoleUserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MountRoleUserServlet() {
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

		// 坐骑名称
		String zuijiname = request.getParameter("Zuijiname");
		// 用户名
		String roleName = request.getParameter("RoleName");
		// 当前页码，默认1
		String pagenum = request.getParameter("Pagenum");
		// 排序状态
		String style = request.getParameter("style");

		SearchMontList result = new SearchMontList();
		if (zuijiname == null || roleName == null || pagenum == null || style == null) {
			// 返回给用户信息
			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
			pwPrintWriter.flush();
			pwPrintWriter.close();
			return;
		}

		/**
		 * 查询操作
		 */
		MountRoleUser mru = new MountRoleUser();
		mru.setMountname(zuijiname);
		mru.setRolename(roleName);
		if ("".equals(pagenum)) {
			mru.setPageNum(0);
		} else {
			mru.setPageNum(Integer.valueOf(pagenum));
		}
		if ("".equals(style)) {
			mru.setOrderBy("");
		} else {
			switch (style) {
			case "1":
				mru.setOrderBy(" ORDER BY MOUNTNAME ");
				break;
			case "2":
				mru.setOrderBy(" ORDER BY ROLENAME ");
				break;

			default:
				break;
			}
		}

		Integer goodCount = AllServiceUtil.getMountService().selectMountCount(mru);
		Integer sumPage = 0;
		// 设置总页数
		if (goodCount % 10 == 0) {
			sumPage = goodCount / 10;
		} else {
			sumPage = (goodCount / 10) + 1;
		}
		List<MountRoleUser> mountList = AllServiceUtil.getMountService().selectMount(mru);

		result.setMontlist(mountList);
		result.setSumpage(sumPage);

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
