package org.come.servlet;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.LoginResult;
import org.come.entity.Haters;
import org.come.entity.RoleTable;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.PayMd5;

/**
 * 账号操作
 * 
 * @author 叶豪芳
 * 
 */
public class UserControlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public UserControlServlet() {
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

		// 获取角色名
		String RoleName = request.getParameter("RoleName");
		/*
		 * 获取操作标识 1.表示禁言 2.表示踢下线 3.表示封号 4.解开禁言 5.解封账号
		 */
		String Style = request.getParameter("Style");
		if (Style == null) {
			System.out.println("操作标识为空！！！");
			return;
		}
		int control = Integer.parseInt(Style);
		// 签名
		String Sign = request.getParameter("Sign");
		// 返回字段
		/*
		 * 1. 表示禁言成功 2.表示踢下线成功 3.表示封号成功 4.解开禁言成功 5.解封账号成功 6.签名失败操作错误 7.根据用户名封号
		 * 8.根据用户名解封
		 */
		int YesOrNo = 0;

		/** HGC-2019-12-03 */
		String reason = request.getParameter("reason");
		String controlname = request.getParameter("controlname");
		
		// 校验签名
		if (PayMd5.encryption(RoleName + Style + GameServer.signNum).equals(Sign)) {
			if (control != 7 && control != 8) {
				// 查询角色
				RoleTable roleInfo = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(RoleName);
				if (roleInfo == null) {
					System.out.println("找不到该角色！！！   " + RoleName);
					return;
				}
				UserTable userInfo = AllServiceUtil.getUserTableService().selectByPrimaryKey(roleInfo.getUser_id());
				if (userInfo == null) {
					System.out.println("找不到该角色的账号！！！   " + RoleName);
					return;
				}
				// 执行操作
				switch (control) {
				case 1:// 表示禁言
					YesOrNo = 1;
					// 查找黑名单
					Haters hater = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
					if (hater == null) {
						// 禁言
						Haters record = new Haters();
						record.setRoleid(roleInfo.getRole_id());
						AllServiceUtil.getHatersService().insert(record);

						// 提示禁言
						if (GameServer.getRoleNameMap().get(RoleName) != null) {
							SendMessage.sendMessageByRoleName(RoleName, Agreement.getAgreement().tipAgreement("你已被禁言！！！"));
						}

					}
					break;
				case 2:// 表示踢下线
					YesOrNo = 2;
					// 断开连接
					if (GameServer.getRoleNameMap().get(RoleName) != null) {
						SendMessage.sendMessageByRoleName(RoleName, Agreement.getAgreement().serverstopAgreement());
					}
					break;
				case 3:// 表示封号
					YesOrNo = 3;
					if (GameServer.getRoleNameMap().get(RoleName) != null) {
						ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getRoleNameMap().get(RoleName), userInfo.getUsername());
					} else {
						// 获取账号名
						UserTable table = new UserTable();
						table.setUsername(userInfo.getUsername());
						table.setActivity((short) 1);
						// 修改用户信息
						AllServiceUtil.getUserTableService().updateUser(table);
						AllServiceUtil.getUserTableService().addRufenghaoControl(userInfo, roleInfo.getRolename(),reason,controlname,1);
						
					}
					break;
				case 4:// 解开禁言
					YesOrNo = 4;
					// 查找黑名单
					Haters hater2 = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
					if (hater2 != null) {
						// 禁言
						AllServiceUtil.getHatersService().deleteByPrimaryKey(hater2.getRoleid());

						// 提示解除禁言
						if (GameServer.getRoleNameMap().get(RoleName) != null) {
							SendMessage.sendMessageByRoleName(RoleName, Agreement.getAgreement().tipAgreement("禁言已被解除！！！"));
						}

					}
					break;
				case 5:// 解封账号
					YesOrNo = 5;
					// 获取账号名
					UserTable table = new UserTable();
					table.setUsername(userInfo.getUsername());
					table.setActivity((short) 0);
					// 修改用户信息
					AllServiceUtil.getUserTableService().updateUser(table);
					AllServiceUtil.getUserTableService().addRufenghaoControl(userInfo, roleInfo.getRolename(),reason,controlname,2);
					
					break;
				default:
					System.out.println("未知的操作！！！");
					break;
				}
			} else {
				UserTable userTable = AllServiceUtil.getUserTableService().selectForUsername(RoleName);
				if (userTable == null) {
					System.out.println("找不到该角色的账号！！！   " + RoleName);
					return;
				}
				if (control == 7) {
					YesOrNo = 3;
					if (GameServer.getInlineUserNameMap().get(RoleName) != null) {
						ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(RoleName), RoleName);
					} else {
						// 获取账号名
						UserTable table = new UserTable();
						table.setUsername(userTable.getUsername());
						table.setActivity((short) 1);
						// 修改用户信息
						AllServiceUtil.getUserTableService().updateUser(table);
						AllServiceUtil.getUserTableService().addRufenghaoControl(userTable, null,reason,controlname,1);
						
						
					}
				} else if (control == 8) {
					YesOrNo = 5;
					// 获取账号名
					UserTable table = new UserTable();
					table.setUsername(userTable.getUsername());
					table.setActivity((short) 0);
					// 修改用户信息
					AllServiceUtil.getUserTableService().updateUser(table);

					AllServiceUtil.getUserTableService().addRufenghaoControl(userTable, null,reason,controlname,2);
				}
			}
		} else {
			YesOrNo = 6;
		}

		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(YesOrNo));
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
	 * 判断是否被禁言
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isNoTalk(ChannelHandlerContext ctx) {
		// 获取角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo != null) {
			Haters hater = AllServiceUtil.getHatersService().selectByPrimaryKey(roleInfo.getRole_id());
			if (hater != null) {
				SendMessage.sendMessageByRoleName(roleInfo.getRolename(), Agreement.getAgreement().tipAgreement("你已被禁言！！！"));
				return true;
			}
		}
		return false;
	}

}
