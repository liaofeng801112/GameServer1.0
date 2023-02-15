package org.come.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.until.GsonUtil;
import org.come.nettyClient.UrlUntil;
import org.come.until.HttpClient;
/**
 * 账号改密
 * @author 
 *
 */
public class UserInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor of the object.
	 */
	public UserInfoServlet() {
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
		// 获得安全码
		String SafeNumber = request.getParameter("SafeNumber");
		// 获得密码
		String userpwd = request.getParameter("userpwd");
		// 获得校验码
		String Sign = request.getParameter("Sign");

		/*
		 * 返回字段 1表示修改成功 2 表示用户名不存在 3 表示验证码错误 4 签名错误操作失败
		 */
		String YesOrNo = "0";

		String url = "http://" + UrlUntil.account_ip + ":" + UrlUntil.tomcat_port + "/" + UrlUntil.poject + "/userInfo/change";
		String param = "userName=" + userName + "&SafeNumber=" + SafeNumber + "&userpwd=" + userpwd + "&Sign=" + Sign;

		for (int i = 0; i < 5; i++) {
			YesOrNo = HttpClient.sendPost(url, param);
			if (!"postError".equals(YesOrNo)) {
				break;
			}
		}
		// 校验签名
		// if (PayMd5.encryption(userName + SafeNumber +
		// GameServer.signNum).equals(Sign)) {
		// // 获取账号信息
		// UserTable userInfo =
		// AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userName,
		// null);
		// if (userInfo != null) {
		// // 校验安全码
		// if (userInfo.getSafety() != null &&
		// userInfo.getSafety().equals(SafeNumber)) {
		// YesOrNo = 1;
		// // 修改密码
		// userInfo.setUserpwd(userpwd);
		// AllServiceUtil.getUserTableService().updateUser(userInfo);
		//
		// /** zrikka 2020 0416 **/
		// // 发送给账号服务器 进行修改 密码
		// UrlUntil.accountAction("updatePasw", userInfo.getFlag(),
		// userInfo.getUserpwd());
		// /***/
		// } else {
		// YesOrNo = 3;
		// }
		// } else {
		// YesOrNo = 2;
		// }
		// } else {
		// YesOrNo = 4;
		// }
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(YesOrNo));
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
