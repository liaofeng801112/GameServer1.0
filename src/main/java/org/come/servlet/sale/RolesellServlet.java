package org.come.servlet.sale;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.LoginResult;
import org.come.bean.SearchGoodsResultBean;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.entity.SalegoodsExample.Criteria;
import org.come.extInterBean.RoleSellQueryResp;
import org.come.extInterBean.RoleSellRoleInfo;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import come.tool.Role.CBGData;
import come.tool.Role.RolePool;

/**
 * 藏宝阁角色出售(/servlet/rolesell)
 * 
 * @author
 * 
 */
public class RolesellServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RolesellServlet() {
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

		// 类型
		String type = request.getParameter("type");

		RoleSellQueryResp resp = new RoleSellQueryResp();
		resp.setType(type);
		if ("selectRole".equals(type)) {
			/** 获取改账号的所有角色 */
			// 账号id
			String userid = request.getParameter("userid");
			// 获取所有角色信息
			List<LoginResult> allLogin = AllServiceUtil.getRoleTableService().selectRoleByUserid(new BigDecimal(userid), new BigDecimal("-" + userid));
			List<RoleSellRoleInfo> list = new ArrayList<>();
			//
			for (int i = 0; i < allLogin.size(); i++) {
				RoleSellRoleInfo roleInfo = new RoleSellRoleInfo();
				// 判断改角色是否售出
				if (allLogin.get(i).getUser_id().compareTo(new BigDecimal(0)) == -1) {
					Salegoods sale = AllServiceUtil.getSalegoodsService().selectSaleGoodsByRoleid(allLogin.get(i).getRole_id() + "");
					if (sale != null) {
						roleInfo.setSaleid(sale.getSaleid() + "");
						roleInfo.setSell("2");
					} else {
						AllServiceUtil.getRoleTableService().updateRoleStatues(allLogin.get(i).getRole_id());
					}
				}
				// 账号id
				roleInfo.setUserid(allLogin.get(i).getUser_id() + "");
				// 角色id
				roleInfo.setRoleid(allLogin.get(i).getRole_id() + "");
				// 角色等级
				roleInfo.setGrade(lvl(allLogin.get(i).getGrade()));
				// 角色种族名
				roleInfo.setRace_name(getRaceSting(allLogin.get(i).getSpecies_id()));
				// 角色名
				roleInfo.setRolename(allLogin.get(i).getRolename());
				// 种族id(头像)
				roleInfo.setSpecies_id(allLogin.get(i).getSpecies_id() + "");
				list.add(roleInfo);
			}
			resp.setRoleInfo(list);
		} else if ("roleUpperShelf".equals(type)) {
			/** 角色上架 */
			// 上架判断
			String price = request.getParameter("price");
			String userid = request.getParameter("userid");
			String roleid = request.getParameter("roleid");
			String species_id = request.getParameter("species_id");
			String salename = request.getParameter("salename");
			Salegoods salegoods = new Salegoods();
			// 价格
			salegoods.setSaleprice(new BigDecimal(price));
			// 用户id
			salegoods.setRoleid(new BigDecimal(userid));
			// 商品类型
			salegoods.setSaletype(10);
			// 要卖的角色id
			salegoods.setOtherid(new BigDecimal(roleid));
			// 设置条件分类
			salegoods.setContiontype("-1");
			// 设置皮肤
			salegoods.setSaleskin(species_id);
			// 设置商品名称
			salegoods.setSalename(salename);
			salegoods.setBuyrole(new BigDecimal(0));
			// 设置上架时间
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowdayTime = dateFormat.format(new Date());
			Date nowDate = null;
			try {
				nowDate = dateFormat.parse(nowdayTime);
				salegoods.setUptime(nowDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 设置上下架标识 上架
			salegoods.setFlag(2);
			// 插入商品
			AllServiceUtil.getSalegoodsService().insert(salegoods);
			// 将角色 userid 改成 -userid
			AllServiceUtil.getRoleTableService().updateRoleStatues(new BigDecimal(roleid));
			// 踢下线
			if (GameServer.getRoleNameMap().get(salename) != null) {
				SendMessage.sendMessageByRoleName(salename, Agreement.getAgreement().serverstopAgreement());
			}
		} else if ("roleLowerShelf".equals(type)) {
			/** 角色下架 */
			String saleid = request.getParameter("saleid");
			Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(new BigDecimal(saleid));
			if (salegoods != null) {
				Integer selectFlag = AllServiceUtil.getSalegoodsService().selectFlag(new BigDecimal(saleid));
				if (selectFlag != 3 && selectFlag != 4) {
					AllServiceUtil.getSalegoodsService().deleteByPrimaryKey(salegoods.getSaleid());
					// 将角色 userid 改回来
					BigDecimal roleid = salegoods.getOtherid();
					AllServiceUtil.getRoleTableService().updateRoleStatues(roleid);
				}
			}
		} else if ("orderQuery".equals(type)) {
			/** 订单查询 */
			// 获得页数
			String pageNum = request.getParameter("PageNow");
			int saletype = 10;
			// 根据条件搜索商品
			SalegoodsExample example = new SalegoodsExample();
			Criteria c = example.createCriteria();
			// 类型
			c.andSaletypeEqualTo(saletype);
			// 默认查询的每页条数
			int dataNum = 15;
			c.andFlagEqualTo(2);
			// 分页查询
			PageHelper.startPage(Integer.parseInt(pageNum), dataNum);
			List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
			PageInfo<Salegoods> pageInfo = new PageInfo<>(list);
			// 返回对象
			SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
			resultBean.setSalegoods(pageInfo.getList());
			resultBean.setTotal(pageInfo.getPages());
			resp.setSearchGoods(resultBean);
		} else if ("orderDetail".equals(type)) {
			/** 订单详情 */
			String saleid = request.getParameter("saleid");
			Salegoods salegoods = AllServiceUtil.getSalegoodsService().selectByPrimaryKey(new BigDecimal(saleid));

			CBGData cbgData = RolePool.getLineCBGRoleData(salegoods.getOtherid());
			if(cbgData != null){
				LoginResult log = new LoginResult();
				log.setRolename(cbgData.getLoginResult().getRolename());
				log.setSpecies_id(cbgData.getLoginResult().getSpecies_id());
				log.setRace_name(cbgData.getLoginResult().getRace_name());
				log.setGold(cbgData.getLoginResult().getGold());
				log.setGangname(cbgData.getLoginResult().getGangname());
				log.setScore(cbgData.getLoginResult().getScore());
				log.setGrade(cbgData.getLoginResult().getGrade());
				log.setLocalname(lvl(cbgData.getLoginResult().getGrade()));
				log.setContribution(cbgData.getLoginResult().getContribution());
				
				cbgData.setLoginResult(log);
				resp.setSalegoods(salegoods);
				resp.setCbgData(cbgData);
			}
		}

		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(resp));
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

	/** 人物根据等级解出几转几级 */
	public String lvl(int lvl) {
		if (lvl <= 102) {
			return "0转" + lvl;
		} else if (lvl <= 210) {
			return "1转" + (lvl - 102 + 14);
		} else if (lvl <= 338) {
			return "2转" + (lvl - 210 + 14);
		} else if (lvl <= 459) {
			return "3转" + (lvl - 338 + 59);
		} else {
			return "飞升" + (lvl - 459 + 139);
		}
	}

	/** 种族 */
	public String getRaceSting(BigDecimal se) {
		if (se == null) {
			return "";
		}
		int id = se.intValue();
		if (id >= 20001 && id <= 20010) {
			return "人";
		} else if (id >= 21001 && id <= 21010) {
			return "魔";
		} else if (id >= 22001 && id <= 22010) {
			return "仙";
		} else if (id >= 23001 && id <= 23010) {
			return "鬼";
		} else {
			return "龙";
		}
	}
}
