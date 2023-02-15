package org.come.servlet.sale;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.LoginResult;
import org.come.entity.UserTable;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 账号改密
 * @author 
 *
 */
public class UserMoneyControlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor of the object.
	 */
	public UserMoneyControlServlet() {
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

		// 获得用户ID
		String userid = request.getParameter("Userid");
		// 获得操作的金钱
		String money = request.getParameter("Money");
		// 获得操作标识 1 扣除  2 增加
		String type = request.getParameter("Type");
		
		// 返回字段
		int YesOrNo = 1;
		
		// 获取用户信息
		UserTable userInfo = null;
		
		// 根据标识操作金钱
		/** zrikka 2020-0408 **/
		if ("2".equals(type)) {
			// 获取角色
			LoginResult roleInfo = AllServiceUtil.getRoleTableService().selectRoleByRoleId(new BigDecimal(userid));
			BigDecimal user_id = roleInfo.getUser_id();
			if(user_id.compareTo(new BigDecimal(0)) == -1){
				user_id = new BigDecimal(0).subtract(user_id);
			}
			userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(user_id);
			// 添加
			DecimalFormat df = new DecimalFormat("######0.00");
			Double balance = userInfo.getUsermoney() + Double.parseDouble(money);
			balance = Double.valueOf(df.format(balance));
			userInfo.setUsermoney(balance);
		} else {
			userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(new BigDecimal(userid));
			// 判断是否够钱
			if (userInfo.getUsermoney() < Double.parseDouble(money)) {
				YesOrNo = 2;
			} else {
				// 扣除
				DecimalFormat df = new DecimalFormat("######0.00");
				Double balance = userInfo.getUsermoney() - Double.parseDouble(money);
				balance = Double.valueOf(df.format(balance));
				userInfo.setUsermoney(balance);
			}
		}
		/** 旧的 */
//		if( "2".equals(type) ){
//			// 获取角色
//			LoginResult roleInfo = AllServiceUtil.getRoleTableService().selectRoleID(new BigDecimal(userid));
//			userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(roleInfo.getUser_id());
//			// 添加
//			userInfo.setUsermoney(userInfo.getUsermoney()+Double.parseDouble(money));
//		}else{
//			userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(new BigDecimal(userid));
//			// 判断是否够钱
//			if( userInfo.getUsermoney() < Double.parseDouble(money) ){
//				YesOrNo = 2;
//			}else{
//				// 扣除
//				userInfo.setUsermoney(userInfo.getUsermoney()-Double.parseDouble(money));
//			}
//		}
		
		// 修改金钱
		AllServiceUtil.getUserTableService().updateUser(userInfo);
		
		PrintWriter pwPrintWriter= response.getWriter();
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
