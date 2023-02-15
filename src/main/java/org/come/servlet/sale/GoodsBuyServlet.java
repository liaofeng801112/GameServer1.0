package org.come.servlet.sale;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.entity.Roleorder;
import org.come.entity.Salegoods;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 藏宝阁商品购买
 * 
 * @author
 * 
 */
public class GoodsBuyServlet extends HttpServlet {

	/***/
	private static final long serialVersionUID = 1L;

	/***/
	public GoodsBuyServlet() {
		super();
	}

	/***/
	@Override
	public void destroy() {
		super.destroy();

	}

	/**
	 * The doGet method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to get.
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
	 * This method is called when a form has its tag value method equals to post.
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
		// 商品表ID
		String saleid = request.getParameter("saleid");
		// 购买者ID
		String roleid = request.getParameter("Role_ID");
		// 返回标识 1 success 0 fail
		int result = 0;
		// 获取该件商品
		Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(new BigDecimal(saleid));
		if (salegoods != null) {
			// 判断是否已被购买
//			  1未上架   2已上架   3已下单   4已卖出   5已取回   
			if ( salegoods.getFlag() == 2 ){
				// 添加订单
				Roleorder roleorder = new Roleorder();
				roleorder.setSaleid(salegoods.getSaleid());
				roleorder.setRoleid(new BigDecimal(roleid));
				roleorder.setStatus(3);
				// 设置下单时间
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowdayTime = dateFormat.format(new Date());
				Date nowDate = null;
				try {
					nowDate = dateFormat.parse(nowdayTime);
					roleorder.setBuytime(nowDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AllServiceUtil.getRoleorderService().insert(roleorder);
				// 修改商品状态，并且修改对应的数据表角色ID,发送该件商品回客户端
				salegoods.setFlag(4);
				AllServiceUtil.getSalegoodsService().updateByPrimaryKey(salegoods);
				result = 1;
			}else{
				result = 0;
			}
		}else{
			result = 0;
		}
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(result));
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

}
