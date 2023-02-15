package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import come.tool.Role.RoleData;
import come.tool.Stall.AssetUpdate;
import io.netty.channel.ChannelHandlerContext;
import org.come.ApiValid;
import org.come.bean.LoginResult;
import org.come.bean.managerTable;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.PayMd5;

import static org.come.server.GameServer.gameServer;

/**
 *  修改角色解锁码
 * @author Administrator
 *
 */
public class RoleTableChangePwdSererlet extends HttpServlet {
	private String controlResult=null;
	RoleTable roleTable;
	/**
	 * Constructor of the object.
	 */
	public RoleTableChangePwdSererlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 获取角色ID
		String roleid = request.getParameter("roleid");
		// 修改的角色密码
		String goodsecret = request.getParameter("goodsecret");
		// 获得页数
		String secret = request.getParameter("secret");
		//获取md5签名
		String sign=PayMd5.encryption(roleid+goodsecret);
		roleTable=new RoleTable();
		if(sign.equals(secret)){
			LoginResult loginResultTemp = AllServiceUtil.getRoleTableService().selectRoleID(new BigDecimal(roleid));
			roleTable.setRole_id(new BigDecimal(roleid));
			roleTable.setPassword(goodsecret);
			int flag=AllServiceUtil.getRoleTableService().updateRolePwdForRid(roleTable);
			if(flag>0){
				controlResult="Success";

				ChannelHandlerContext ctx = GameServer.getInlineUserNameMap().get(loginResultTemp.getUserName());
				if(null!=ctx){
					LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
					loginResult.setPassword(goodsecret);
					AssetUpdate assetUpdate = new AssetUpdate();
					assetUpdate.setType(25);
					assetUpdate.setMsg("您成功修改背包解锁码为#G"+goodsecret+",重新登录后生效!");
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
				}

			}else{
				controlResult="Erore";
			}

		}else{

			controlResult="Erore";
		}

		PrintWriter pwPrintWriter= response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(controlResult));
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
		// Put your code here
	}

}
