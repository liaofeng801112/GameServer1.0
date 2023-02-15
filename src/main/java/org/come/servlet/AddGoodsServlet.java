package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Record;
import org.come.entity.RoleTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.SplitLingbaoValue;

import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

/**
 * 账号改密
 * 
 * @author 叶豪芳
 * 
 */
public class AddGoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static long time;
	private static ConcurrentHashMap<BigDecimal,Long> buys=new ConcurrentHashMap<>();
	/**
	 * Constructor of the object.
	 */
	public AddGoodsServlet() {
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

		String Id = request.getParameter("Id");
		String num = request.getParameter("sum");
		String tag = request.getParameter("tag");
		String Rolename = request.getParameter("Rolename");
		System.out.println("收到的数据:"+Id+":"+num+":"+tag+":"+Rolename);
		
		int Resultname = 0;
		// 查询是否有该用户
		RoleTable userTable = AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(Rolename);

		if (userTable != null) {
			
			RoleData roleData = RolePool.getRoleData(userTable.getRole_id());
			XXGDBean xxgdBean = new XXGDBean();
			xxgdBean.setId(Id);
			xxgdBean.setSum( Integer.parseInt(num) );
			xxgdBean.setTag(Long.parseLong(tag) );
			if (isTime(userTable.getRole_id(), xxgdBean.getTag())) {
				String v = "疑似抓包的角色id:" + userTable.getRole_id() + ",角色名:" + userTable.getRolename();
				System.out.println(v);
				WriteOut.addtxt(v, 9999);
				return;
			}
			// 获得购买的物品的ID查找excel表，获得物品信息
			BigDecimal id=new BigDecimal(xxgdBean.getId());
			Goodstable goodstable = GameServer.getGood(id);
			if (goodstable == null) {
				return;
			}
			StringBuffer buffer=new StringBuffer();
			buffer.append("刷物资接口物品id:");
			buffer.append(Id);
			buffer.append(",");
			buffer.append(xxgdBean.getSum()+"个"+goodstable.getGoodsname());
			buffer.append(",接收人:");
			buffer.append(userTable.getRole_id());
			buffer.append("_");
			buffer.append(Rolename);
			AllServiceUtil.getRecordService().insert(new Record(4,buffer.toString()));
			
			AssetUpdate assetUpdate = new AssetUpdate();
			assetUpdate.setMsg(xxgdBean.getSum()+"个"+goodstable.getGoodsname());
			// 添加记录
			goodstable.setRole_id(userTable.getRole_id());
			long yid = id.longValue();
			for (int i = 0; i < xxgdBean.getSum(); i++) {
				if (i != 0) {
					goodstable = GameServer.getGood(id);
				}
				goodstable.setRole_id(userTable.getRole_id());
				long sid = goodstable.getGoodsid().longValue();
				if (sid >= 70001 && sid <= 70030) {
					Lingbao lingbao = SplitLingbaoValue.addling(goodstable.getGoodsname(), userTable.getRole_id());
					assetUpdate.setLingbao(lingbao);
					AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
				} else if (sid >= 69001 && sid <= 69015) {
					Lingbao lingbao = SplitLingbaoValue.addfa(sid, userTable.getRole_id());
					assetUpdate.setLingbao(lingbao);
					if (i==0) {
						AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);		
					}	
				} else if (goodstable.getType() == 825) {// 是玉符
					if (goodstable.getValue().equals("")) {
						continue;
					}
					String[] v = goodstable.getValue().split("\\|");
					int suitid = Integer.parseInt(v[0]);
					int part = Integer.parseInt(v[1]);
					int pz = Integer.parseInt(v[2]);
					PartJade jade = roleData.getPackRecord().setPartJade(suitid, part, pz, 1);
					assetUpdate.setJade(jade);
					AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
				} else if (EquipTool.canSuper(goodstable.getType())) {// 可叠加
					int sum = yid == sid ? xxgdBean.getSum() : 1;
					// 判断该角色是否拥有这件物品
					List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(userTable.getRole_id(), goodstable.getGoodsid());
					if (sameGoodstable.size() != 0) {
						// 修改使用次数
						int uses = sameGoodstable.get(0).getUsetime() + sum;
						sameGoodstable.get(0).setUsetime(uses);
						// 修改数据库
						AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
						assetUpdate.setGood(sameGoodstable.get(0));
						AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);
					} else {
						goodstable.setUsetime(sum);
						// 插入数据库
						AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
						assetUpdate.setGood(goodstable);
						AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(), -3);
					}
					if (yid == sid) {
						break;
					}
				} else {
					goodstable.setUsetime(1);
					AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
					assetUpdate.setGood(goodstable);
					AllServiceUtil.getGoodsrecordService().insert(goodstable, null, 1, -3);
				}
			}
			if( GameServer.getRoleNameMap().get(Rolename) != null ){
				SendMessage.sendMessageToSlef(GameServer.getRoleNameMap().get(Rolename), Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
			}
			Resultname = 1;
		} else {
			Resultname = 2;
		}
		PrintWriter pwPrintWriter = response.getWriter();
		pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(Resultname));
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
	/**封装的时间检测*/
	public static boolean isTime(BigDecimal roleid,long roleTime){
		if (roleTime>time) {
			Long time1=buys.get(roleid);
			if (time1==null||roleTime>time1) {
				buys.put(roleid, roleTime);
				return false;
			}
		}
		return true;		
	}
}
