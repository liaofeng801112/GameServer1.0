package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.LoginResult;
import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;

/**
 * 玩家信息
 */
public class UserRoleQueryServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public UserRoleQueryServlet() {
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

		// 搜索类型
		String type = request.getParameter("type");
		// 获得角色名
		String rolename = request.getParameter("rolename");
		// 获得角色id
		String roleid = request.getParameter("roleid");
		// 用户id
		String userid = request.getParameter("userid");

		String res = "";

		if ("1".equals(type)) {
			// 通过角色名 查询 角色id
			LoginResult role = AllServiceUtil.getRoleTableService().selectRoleName(rolename);
			if (role == null) {
				res = "roleid=0";
			} else {
				res = "roleid=" + role.getRole_id();
			}
		} else if ("2".equals(type)) {
			// 通过角色id 查询 角色名
			LoginResult role = AllServiceUtil.getRoleTableService().selectRoleID(new BigDecimal(roleid));
			if (role == null) {
				res = "rolename=0";
			} else {
				res = "rolename=" + role.getRolename();
			}
		} else if ("3".equals(type)) {
			UserTable usertable = AllServiceUtil.getUserTableService().selectByPrimaryKey(new BigDecimal(userid));
			if (usertable == null) {
				res = "userAcc=&userMoney=0";
			} else {
				res = "userAcc=" + usertable.getUsername() + "&userMoney=" + usertable.getUsermoney();
			}
		}
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(res);
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
	}

	/**
	 * 依据状态进行确认排序条件
	 */
	public String sureType(String type) {
		String returnMes = "";
		switch (type) {
		case "0":
			returnMes = "";

			break;
		case "1":
			returnMes = "order by rolename";
			break;
		case "2":
			returnMes = "order by LOCALNAME";
			break;

		case "3":
			returnMes = "order by QID";
			break;
		case "4":
			returnMes = "order by PASSWORD";
			break;
		// 大话币
		case "5":
			returnMes = "order by GOLD desc";
			break;
		// 等级
		case "6":
			returnMes = "order by GRADE desc";
			break;
		// hp
		case "7":
			returnMes = "order by HP desc";
			break;
		// ap
		// case "8":
		// returnMes = "order by ";
		// break;
		// sp
		// case "9":
		// returnMes = "order by ";
		// break;
		// mp
		case "10":
			returnMes = "order by MP desc";
			break;
		// 仙玉
		case "11":
			returnMes = "order by CODECARD desc";
			break;
		// 充值积分
		case "12":
			returnMes = "order by PAYINTEGRATION desc";
			break;

		}

		return returnMes;

	}
}
