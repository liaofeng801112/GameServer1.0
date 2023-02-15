package org.come.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.ApiValid;
import org.come.bean.BackRoleInfo;
import org.come.bean.managerTable;
import org.come.entity.RoleTable;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 玩家信息
 *
 * @author 叶豪芳
 */
public class RoleInfoServlet extends HttpServlet {
	RoleTable roleTable;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RoleInfoServlet() {
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
	 * <p>
	 * This method is called when a form has its tag value method equals to get.
	 *
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * <p>
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 获得角色名
		String rolename = request.getParameter("rolename");
		// 获得区号
		String qid = request.getParameter("qid");
		// 获得页数
		String pagenum = request.getParameter("pageNum");
		// 获得状态
		String statues = request.getParameter("statues");
		//获取查询的找账号
		String plarcess = request.getParameter("username");
		//获取排序状态
		String type = request.getParameter("type");
		if (type == null) {
			type = "0";
		}
		if(rolename == null) rolename = "";
		if(statues == null) statues = "";
		if(plarcess == null) plarcess = "";

		BackRoleInfo list = null;

		roleTable = new RoleTable();
		//设置搜索条件
		roleTable.setUserString(sureType(type));
		//判断qid是区域quID还是总得quID
		if (qid == null) {//if((qid!=null)&(qid.equals(0+""))){
			roleTable.setQid(null);
		} else {
			roleTable.setQid(new BigDecimal(qid));
		}
		if (pagenum == null) pagenum = "1";
		//设置当前页码
		roleTable.setStart((Integer.valueOf(pagenum) - 1) * 8);
		roleTable.setEnd((Integer.valueOf(pagenum)) * 8);
		//3、禁言 4、封号5、未封号  6、未禁言
		if (statues.equals(3 + "")) roleTable.setUnknown(1 + "");
		if (statues.equals(4 + "")) roleTable.setActivity(new Short(1 + ""));
		if (statues.equals(5 + "")) roleTable.setActivity(new Short(0 + ""));
		if (statues.equals(6 + "")) roleTable.setActivity(null);
		//设置角色名
		if ((!(rolename.equals(0 + ""))))
			roleTable.setRolename(rolename);
		else if (rolename.equals(0 + "")) roleTable.setRolename(null);

		//设置账号
		if ((!(plarcess.equals(0 + ""))))
			roleTable.setLocalname(plarcess);
		else if (plarcess.equals(0 + "")) roleTable.setLocalname(null);

		//查询总区域得玩家信息
		int page1 = AllServiceUtil.getUserTableService().selectSumForRoleUserHaterNumber(roleTable);
		//总页数
		int page = page1 / 8;
		if (page1 % 8 > 0) page++;
		//查询状态下的角色
		List<RoleTable> listall = AllServiceUtil.getUserTableService().selectSumForRoleUserHaterList(roleTable);
		list = new BackRoleInfo();
		//进行状态实例化
		for (RoleTable roleInfo : listall) {

			// 玩家状态1、在线 2、下线 3、禁言 4、封号5、未封号  6、未禁言
			String status = "";
			// 查询玩家状态
			if (GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null) {
				status += "/" + 1;
			} else {
				status += "/" + 2;
			}
			// 是否禁言

			if (roleInfo.getUnknown() != null) {
				status += "/" + 3;
			} else {
				status += "/" + 6;
			}

			if (roleInfo.getActivity() != 0) {
				status += "/" + 4;
			} else {
				status += "/" + 5;

			}

			roleInfo.setStatues(status.replaceFirst("/", ""));

			list.setPages(page);
			list.setPageNum(Integer.valueOf(pagenum));
			list.setList(listall);
		}


		PrintWriter pwPrintWriter = response.getWriter();
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
