package org.come.servlet.sale;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.LoginResult;
import org.come.bean.RoleListBean;
import org.come.bean.Role_bean;
import org.come.entity.UserTable;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.PayMd5;
import org.come.bean.Account;
import org.come.nettyClient.UrlUntil;
import org.come.until.HttpClient;
/**
 * 藏宝阁登入
 * @author 
 *
 */
public class UserLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor of the object.
	 */
	public UserLoginServlet() {
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

		// 获得用户名
		String userName = request.getParameter("userName");
		// 获得密码
		String userpwd = request.getParameter("userPwd");
		// 签名
		String Sign = request.getParameter("sign");
		// 返回bean
		RoleListBean bean = new RoleListBean();
		// 校验签名
		if (PayMd5.encryption(userName + userpwd + GameServer.signNum).equals(Sign)) {
			/** zrikka 0424 */
			String url = "http://" + UrlUntil.account_ip + ":" + UrlUntil.tomcat_port + "/" + UrlUntil.poject + "/userInfo/getUser";
			String param = "username=" + userName + "&password=" + userpwd;

			String res = "";
			for (int i = 0; i < 5; i++) {
				res = HttpClient.sendPost(url, param);
				if (!"postError".equals(res)) {
					break;
				}
			}

			if ("".equals(res)) {
				PrintWriter pwPrintWriter = response.getWriter();
				bean.setStatues(3);
				pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(bean));
				pwPrintWriter.flush();
				pwPrintWriter.close();
				return;
			}
			Account acc = GsonUtil.getGsonUtil().getgson().fromJson(res, Account.class);
			if (acc.getAc_flag() == null || "".equals(acc.getAc_flag())) {
				PrintWriter pwPrintWriter = response.getWriter();
				bean.setStatues(3);
				pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(bean));
				pwPrintWriter.flush();
				pwPrintWriter.close();
				return;
			}
			// userName = acc.getAc_account();
			// userpwd = acc.getAc_pasw();
			/***/

			// 查询是否有该用户
			// UserTable userTable =
			// AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userName,
			// null);
			UserTable userTable = AllServiceUtil.getUserTableService().selectByFlag(acc.getAc_flag());

			if (userTable != null) {
				// if (userpwd.equals(userTable.getUserpwd())) {
				userName = userTable.getUsername();
				userpwd = userTable.getUserpwd();

				List<LoginResult> list = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(userName, userpwd, null);
				List<Role_bean> beans = new ArrayList<>();
				for (LoginResult loginResult : list) {
					Role_bean bean2 = new Role_bean();
					bean2.setGangname(loginResult.getGangname());
					bean2.setGrade(loginResult.getGrade());
					bean2.setRace_name(loginResult.getRace_name());
					bean2.setRole_id(loginResult.getRole_id());
					bean2.setRolename(loginResult.getRolename());
					bean2.setTitle(loginResult.getTitle());
					bean2.setSkin(loginResult.getSpecies_id() + "");
					beans.add(bean2);
				}
				bean.setRoleList(beans);
				bean.setStatues(1);
				bean.setUserId(userTable.getUser_id());
				bean.setUsermoney(userTable.getUsermoney());
				bean.setQid(userTable.getQid() + "");
				bean.setPhone(userTable.getPhonenumber());
			} else {
				bean.setStatues(3);
			}
			// } else {
			// bean.setStatues(2);
			// }
		}

		PrintWriter pwPrintWriter = response.getWriter();
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
