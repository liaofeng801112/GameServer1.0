package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.extInterBean.ShopBuyTypeResult;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class ShopBuyTypeServlet extends HttpServlet {

	public ShopBuyTypeServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		List<ShopBuyTypeResult> selectShopBuyType = AllServiceUtil.getAppVersionService().selectShopBuyType();

		// 返回给用户信息
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(selectShopBuyType));
		pwPrintWriter.flush();
		pwPrintWriter.close();

	}

	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
