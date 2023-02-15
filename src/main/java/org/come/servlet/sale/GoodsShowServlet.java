package org.come.servlet.sale;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.action.sale.GoodsSearchAction;
import org.come.action.sale.MyOrderSearchAction;
import org.come.bean.SearchGoodsResultBean;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.entity.SalegoodsExample.Criteria;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 藏宝阁商品展示
 * 
 * @author
 * 
 */
public class GoodsShowServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GoodsShowServlet() {
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

		// 获得类型
		String type = request.getParameter("Contiontype");
		// 获得页数
		String pageNum = request.getParameter("PageNow");
		// 获得显示公示期标识
		String shows = request.getParameter("show");
		// 获得排序标识
		String orders = request.getParameter("order");
		// 获得类型标识
		String saletypes = request.getParameter("saletype");

		int saletype = 1;
		// 类型
		if (saletypes != null && !"".equals(saletypes)) {
			saletype = Integer.parseInt(saletypes);
		}
		int show = Integer.parseInt(shows);
		int order = Integer.parseInt(orders);
		// 根据条件搜索商品
		SalegoodsExample example = new SalegoodsExample();
		Criteria c = example.createCriteria();

		// 默认查询的每页条数
		int dataNum = 15;

		// 条件类型
		if (type != null && !"".equals(type)) {
			// 查询道具条件
			if (saletype == 3) {
				GoodsSearchAction.selectProp(type, c);
			}
			// 查询召唤兽条件
			else if (saletype == 4) {
				GoodsSearchAction.selectPet(type, c);
			}
			// 查询装备条件
			else if (saletype == 5) {
				GoodsSearchAction.selectEquip(type, c);
			}
		}

		// 类型
		if (saletype != 1) {
			// 类型
			c.andSaletypeEqualTo(saletype);
			// 大话币没有公示期
			if (saletype != 2) {
				// 判断公示期标识
				if (show == 0) {// 不显示公示期
					// 上架时间为三天前的表示已过公示期
					/** zrikka 2020-0408 */
					c.andUptimeLessThan(MyOrderSearchAction.getDate(1));
					/** 旧的 */
//					c.andUptimeLessThan(MyOrderSearchAction.getDate(3));
					
				} else if (show == 2) {// 只显示公示期
					// 上架时间为三天内的为公示期
					/** zrikka 2020-0408 */
					c.andUptimeGreaterThan(MyOrderSearchAction.getDate(1));
					/** 旧的 */
//					c.andUptimeGreaterThan(MyOrderSearchAction.getDate(3));
				}
			}

		} else {
			// 首页搜索全部商品，但是大话币没有公示期
			// 判断公示期标识
			if (show == 0) {// 不显示公示期
				// 上架时间为三天前的表示已过公示期
				/** zrikka 2020-0408 */
				c.andUptimeLessThan(MyOrderSearchAction.getDate(1));
				c.andSaletypeNotEqualTo(10);
				/** 旧的 */
//				c.andUptimeLessThan(MyOrderSearchAction.getDate(3));

				// 或者类型为大话币
				SalegoodsExample example2 = new SalegoodsExample();
				Criteria criteria = example2.createCriteria();
				criteria.andSaletypeEqualTo(2);
				criteria.andFlagEqualTo(2);
				example.or(criteria);
			} else if (show == 2) {// 只显示公示期
				/** zrikka 2020-0408 */
				// 上架时间为三天内的为公示期
				c.andUptimeGreaterThan(MyOrderSearchAction.getDate(1));
				// 排除大话币
				c.andSaletypeNotEqualTo(2);
				c.andSaletypeNotEqualTo(10);
				
				/** 旧的 */
				// 上架时间为三天内的为公示期
//				c.andUptimeGreaterThan(MyOrderSearchAction.getDate(3));
			}
		}

		// 判断排序标识
		if (order == 1) {
			// 根据价格升序
			example.setOrderByClause("saleprice");
		} else if (order == 2) {
			// 根据价格降序
			example.setOrderByClause("saleprice desc");
		}

		c.andFlagEqualTo(2);

		// 分页查询
		PageHelper.startPage(Integer.parseInt(pageNum), dataNum);
		List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
		PageInfo<Salegoods> pageInfo = new PageInfo<>(list);

		// 返回对象
		SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
		resultBean.setSalegoods(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());

		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
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
