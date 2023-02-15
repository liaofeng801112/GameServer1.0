package org.come.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.service.IGoodsExchangeService;
import org.come.serviceImpl.GoodsexchangeServiceImpl;

public class ExchangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IGoodsExchangeService goodsExchangeService = new GoodsexchangeServiceImpl();

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	public void init() throws ServletException {
	}
}
