package org.come.action.sale;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.SearchGoodsBean;
import org.come.bean.SearchGoodsResultBean;
import org.come.entity.Salegoods;
import org.come.entity.SalegoodsExample;
import org.come.entity.SalegoodsExample.Criteria;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.SplitStringTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 商品搜索
 * 
 * @author Administrator
 * 
 */
public class GoodsSearchAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取搜索条件
		SearchGoodsBean bean = GsonUtil.getGsonUtil().getgson().fromJson(message, SearchGoodsBean.class);

		// 根据条件搜索商品
		SalegoodsExample example = new SalegoodsExample();
		Criteria c = example.createCriteria();

		// 默认查询的每页条数
		int dataNum = 15;

		// 商品名称
		if (bean.getSalename() != null && !"".equals(bean.getSalename())) {
			c.andSalenameLike("%" + bean.getSalename() + "%");

		}

		// 类型
		if (bean.getSaletype() != null && !"".equals(bean.getSaletype())) {

			// 判断类型为1时，返回最新几条上架的商品（已过公示期）首页展示商品
			if (bean.getSaletype() == 1 && (bean.getSalename() == null || "".equals(bean.getSalename()))) {
				// 上架时间为一天前的表示已过公示期
				c.andUptimeLessThan(MyOrderSearchAction.getDate(1));
				// 查询最开始的7条数据
				dataNum = 7;
			} else {
				c.andSaletypeEqualTo(bean.getSaletype());
			}
			
			// 大话币没有公示期
			if( bean.getSaletype() != 2 ){
				// 判断公示期标识
				if (bean.getShow() == 0) {// 不显示公示期
					// 上架时间为一天前的表示已过公示期
					c.andUptimeLessThan(MyOrderSearchAction.getDate(1));
				} else if (bean.getShow() == 2) {// 只显示公示期
					// 上架时间为一天内的为公示期
					c.andUptimeGreaterThan(MyOrderSearchAction.getDate(1));
				}
			}

		}else{
			// 首页搜索全部商品，但是大话币没有公示期
			// 判断公示期标识
			if (bean.getShow() == 0) {// 不显示公示期
				// 上架时间为一天前的表示已过公示期
				c.andUptimeLessThan(MyOrderSearchAction.getDate(1));			
				// 或者类型为大话币且为上架
				SalegoodsExample example2 = new SalegoodsExample();
				Criteria criteria = example2.createCriteria();
				criteria.andSaletypeEqualTo(2);
				criteria.andFlagEqualTo(2);
				// 商品名称
				if (bean.getSalename() != null && !"".equals(bean.getSalename())) {
					criteria.andSalenameLike("%" + bean.getSalename() + "%");
				}
				example.or(criteria);
			} else if (bean.getShow() == 2) {// 只显示公示期
				// 上架时间为一天内的为公示期
				c.andUptimeGreaterThan(MyOrderSearchAction.getDate(1));
				
				// 排除大话币
				c.andSaletypeNotEqualTo(2);
				/** zrikka 2020-0408 */
				c.andSaletypeNotEqualTo(10);
			}
		}

		// 条件类型
		if (bean.getContiontype() != null && !"".equals(bean.getContiontype())) {
			// 为查询大话币时
			if (bean.getSaletype() == 2) {
				// 判断大话币区间
				String[] range = bean.getContiontype().split("-");
				c.andOtheridBetween(new BigDecimal(range[0]), new BigDecimal(range[1]));
			}
			// 查询道具条件
			else if (bean.getSaletype() == 3) {
				selectProp(bean.getContiontype(), c);
			}
			// 查询召唤兽条件
			else if (bean.getSaletype() == 4) {
				selectPet(bean.getContiontype(), c);
			}
			// 查询装备条件
			else if (bean.getSaletype() == 5) {
				selectEquip(bean.getContiontype(), c);
			}
		}

		// 判断排序标识
		if( bean.getOrder() == 1 ){
			// 根据价格升序
			example.setOrderByClause("saleprice");
		}else if ( bean.getOrder() == 2 ){
			// 根据价格降序
			example.setOrderByClause("saleprice desc");
		}else{
			// 根据上架时间降序
			example.setOrderByClause("uptime desc");
		}
		
		// 状态为上架状态
		c.andFlagEqualTo(2);
		
		// 分页查询
		PageHelper.startPage(bean.getPageNum(), dataNum);
		List<Salegoods> list = AllServiceUtil.getSalegoodsService().selectByExample(example);
		PageInfo<Salegoods> pageInfo = new PageInfo<>(list);

		// 获取用户信息
		LoginResult role = GameServer.getAllLoginRole().get(ctx);
		// 查询该用户的收藏列表
		List<BigDecimal> userCollection = AllServiceUtil.getCollectionService().selectUserCollection(role.getRole_id());

		// 返回对象
		SearchGoodsResultBean resultBean = new SearchGoodsResultBean();
		resultBean.setSalegoods(pageInfo.getList());
		resultBean.setTotal(pageInfo.getPages());
		resultBean.setCollections(userCollection);

		// 返回客户端
		String msg = Agreement.getAgreement().CBGSearch1Agreement(GsonUtil.getGsonUtil().getgson().toJson(resultBean));
		SendMessage.sendMessageToSlef(ctx, msg);
	}

	/**
	 * 查询道具，判断条件添加查询条件
	 * @param c
	 */
	public static void selectProp(String contions, Criteria ctr) {
		// 0全部
		// 1召唤兽装备
		String a = "";
		// 2地煞星符
		String b = "";
		// 3宝石
		String c = "";
		// 4召唤兽饰品
		String d = "729";
		// 5孩子装备
		String e = "54|55|56|57|58|59|60|61";
		// 17符石
		String f = "188";
		// 6其它
		String qt="729|54|55|56|57|58|59|60|61|188";
		// 类型集合
		List<String> types = null;

		if ("1".equals(contions)) {
			types = SplitStringTool.splitString(a);
		} else if ("2".equals(contions)) {
			types = SplitStringTool.splitString(b);
		} else if ("3".equals(contions)) {
			types = SplitStringTool.splitString(c);
		} else if ("4".equals(contions)) {
			types = SplitStringTool.splitString(d);
		} else if ("5".equals(contions)) {
			types = SplitStringTool.splitString(e);
		} else if ("17".equals(contions)) {
			types = SplitStringTool.splitString(f);
		}

		// 添加查询条件
		if ("6".equals(contions)) { // 为其他时，不在所有范围之内
//			String g = a + "|" + b + "|" + c + "|" + d + "|" + e + "|" + f;
			ctr.andContiontypeNotIn(SplitStringTool.splitString(qt));
		}else if (!"0".equals(contions)) {
			ctr.andContiontypeIn(types);
		} 

	}
	
	/**
	 * 查询召唤兽，判断条件添加查询条件
	 * 
	 * @param c
	 */
	public static void selectPet(String contions, Criteria ctr) {
		// 7 1-14称
		String a = "0";
		// 8 特殊召唤兽
		String b = "1|5";
		// 9 高级守护
		String c = "6";
		// 10 神兽
		String d = "2|3|4";
		// 类型集合
		List<String> types = null;

		if ("7".equals(contions)) {
			types = SplitStringTool.splitString(a);
		} else if ("8".equals(contions)) {
			types = SplitStringTool.splitString(b);
		} else if ("9".equals(contions)) {
			types = SplitStringTool.splitString(c);
		} else if ("10".equals(contions)) {
			types = SplitStringTool.splitString(d);
		}

		// 添加查询条件
		if (!"0".equals(contions)) {
			ctr.andContiontypeIn(types);
		}
		
	}
	
	/**
	 * 查询装备，判断条件添加查询条件
	 * 
	 * @param c
	 */
	public static void selectEquip(String contions, Criteria ctr) {
		// 常规装备
		String a = "602|603|7002|7003|7004|800|6500|6800|6900";
		// 神兵
		String b = "6500|6900|6601|6600|6701|6700|6900|6800";
		// 仙器
		String c = "7000-7004";
		// 配饰
		String d = "609|608|610|607|606|611";
		// 护身符
		String e = "611|612";
		// 6其它
		// 类型集合
		List<String> types = null;

		if ("11".equals(contions)) {
			types = SplitStringTool.splitString(a);
		} else if ("12".equals(contions)) {
			types = SplitStringTool.splitString(b);
		} else if ("13".equals(contions)) {
			types = SplitStringTool.splitString(c);
		} else if ("14".equals(contions)) {
			types = SplitStringTool.splitString(d);
		} else if ("15".equals(contions)) {
			types = SplitStringTool.splitString(e);
		}

		// 添加查询条件
		if (!"0".equals(contions) && !"6".equals(contions)) {
			ctr.andContiontypeIn(types);
		} else if ("6".equals(contions)) { // 为其他时，不在所有范围之内 
			String f = a + "|" + b + "|" + c + "|" + d + "|" + e;
			ctr.andContiontypeNotIn(SplitStringTool.splitString(f));
		}
		
	}
}
