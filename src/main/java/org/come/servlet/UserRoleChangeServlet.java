package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.UserRoleChangeBean;
import org.come.entity.RoleTable;
import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 进行角色迁移
 * <p>
 * Title : UserRoleChangeServlet
 * </p>
 * 
 * @author : HGC
 * @date : 2019年10月11日 上午11:10:20
 * @version : 1.0.0
 */
public class UserRoleChangeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserRoleChangeServlet() {
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
	 * This method is called when a form has its tag value method equals to post.
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

		String userName = request.getParameter("userName");
		String otherUserName = request.getParameter("otherUserName");
		String type = request.getParameter("type");

		if (type.equals("1")) {
			List<RoleTable> roleList = new ArrayList<>();
			List<RoleTable> otherRoleList = new ArrayList<>();
			if (userName != null && !"".equals(userName)) {
				UserTable user = AllServiceUtil.getUserTableService().selectForUsername(userName);
				if (user != null) {
					roleList = AllServiceUtil.getUserTableService().selectAllRoleTable(userName);
				}
			}
			if (otherUserName != null && !"".equals(otherUserName)) {
				UserTable user = AllServiceUtil.getUserTableService().selectForUsername(otherUserName);
				if (user != null) {
					otherRoleList = AllServiceUtil.getUserTableService().selectAllRoleTable(otherUserName);
				}
			}
			UserRoleChangeBean roleChangeBean = new UserRoleChangeBean();
			roleChangeBean.setRoleList(roleList);
			roleChangeBean.setOtherRoleList(otherRoleList);

			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(roleChangeBean));
			pwPrintWriter.flush();
			pwPrintWriter.close();
		} else if (type.equals("2")) {
			String roleId = request.getParameter("roleId");
			
			//判断情况：初始化0	转移用户角色数量到达最大上限-11	转移角色不存在-12
			int con = 0;
			
			if (otherUserName != null && !"".equals(otherUserName)) {
				UserTable user = AllServiceUtil.getUserTableService().selectForUsername(otherUserName);
				if (user != null) {
					List<RoleTable> otherRoleList = AllServiceUtil.getUserTableService().selectAllRoleTable(otherUserName);
					if(otherRoleList.size()>=5){
						con = -11;
					}else {
						con = AllServiceUtil.getUserTableService().roleChangeUser(userName, user.getUser_id(), roleId);
					}
				}else{
					con = -12;
				}
			}
			PrintWriter pwPrintWriter = response.getWriter();
			pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(con));
			pwPrintWriter.flush();
			pwPrintWriter.close();
		}

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
