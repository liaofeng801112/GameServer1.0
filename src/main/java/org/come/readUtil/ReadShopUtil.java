package org.come.readUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.EshopBean;
import org.come.entity.BuyCount;
import org.come.handler.MainServerHandler;
import org.come.model.Eshop;
import org.come.model.Lshop;
import org.come.model.Shop;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 读取商店
 * @author 叶豪芳
 * @date : 2017年11月29日 下午5:08:17
 */
public class ReadShopUtil {

	/**根据物品ID储存每个商店的物品*/
	public static ConcurrentHashMap<String,Shop> getAllShop(String path, StringBuffer buffer){
		ConcurrentHashMap<String,Shop> allShopGood = new ConcurrentHashMap<String,Shop>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Shop shop = new Shop();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(shop, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				if (shop.getShopid().equals("")) {
					continue;
				}
				if (shop.getLvl()!=null&&!shop.getLvl().equals("")) {
					int[] lvls=new int[]{0,0,4,200,200};
					String[] lvls1=shop.getLvl().split("\\|");
					String[] vs=lvls1[0].split("\\-");
					lvls[1]=Integer.parseInt(vs[0]);
					lvls[3]=Integer.parseInt(vs[1]);
					if (lvls1.length>=2) {
						lvls[0]=Integer.parseInt(lvls1[1]);
					}
					if (lvls1.length==3) {
						vs=lvls1[2].split("\\-");
						lvls[2]=Integer.parseInt(vs[0]);
						lvls[4]=Integer.parseInt(vs[1]);
					}
					shop.setLvls(lvls);
				}
				if (shop.getPriceNum()!=0) {
					shop.setIsPrice(true);
				}
				BuyCount buyCount=AllServiceUtil.getBuyCountServeice().selectBuyCount(Long.parseLong(shop.getShopid())*100+1);
				if (buyCount==null) {
					buyCount=new BuyCount();
					buyCount.setBid(Long.parseLong(shop.getShopid())*100+1);
					buyCount.setPtype(1);
					buyCount.setShopId(Integer.parseInt(shop.getShopid()));
					buyCount.setShopType(shop.getShoptype());
					AllServiceUtil.getBuyCountServeice().insertBuyCount(buyCount);
				}
				shop.setBuyCount(buyCount);
				allShopGood.put(shop.getShopid(), shop);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return allShopGood;
		
	}
	/**根据物品ID储存每个商店的物品*/
	public static ConcurrentHashMap<String, Eshop> getAllEshopGoods(String path, StringBuffer buffer) {
		// 储存的集合
		ConcurrentHashMap<String, Eshop> allShopGoodsMap = new ConcurrentHashMap<String, Eshop>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Eshop eshop = new Eshop();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(eshop, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				if (eshop.getEshopid().equals("")) {
					continue;
				}
				if (eshop.getLvl() != null && !eshop.getLvl().equals("")) {
					int[] lvls = new int[] { 0, 0, 4, 200, 200 };
					String[] lvls1 = eshop.getLvl().split("\\|");
					String[] vs = lvls1[0].split("\\-");
					lvls[1] = Integer.parseInt(vs[0]);
					lvls[3] = Integer.parseInt(vs[1]);
					if (lvls1.length >= 2) {
						lvls[0] = Integer.parseInt(lvls1[1]);
					}
					if (lvls1.length == 3) {
						vs = lvls1[2].split("\\-");
						lvls[2] = Integer.parseInt(vs[0]);
						lvls[4] = Integer.parseInt(vs[1]);
					}
					eshop.setLvls(lvls);
				}
				BuyCount buyCount = AllServiceUtil.getBuyCountServeice().selectBuyCount(Long.parseLong(eshop.getEshopid()) * 100);
				if (buyCount == null) {
					buyCount = new BuyCount();
					buyCount.setBid(Long.parseLong(eshop.getEshopid()) * 100);
					buyCount.setPtype(0);
					buyCount.setShopId(Integer.parseInt(eshop.getEshopid()));
					buyCount.setShopType(Integer.parseInt(eshop.getEshoptype()));
					AllServiceUtil.getBuyCountServeice().insertBuyCount(buyCount);
				}
				eshop.setBuyCount(buyCount);
				allShopGoodsMap.put(eshop.getEshopid(), eshop);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return allShopGoodsMap;
	}
	/** 扫描文件，获得全部商店物品信息 */
	public static ConcurrentHashMap<String, Lshop> selectLShops(String path, StringBuffer buffer) {
		ConcurrentHashMap<String, Lshop> shops = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Lshop lshop = new Lshop();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(lshop, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				if (lshop.getId() != 0) {
					BuyCount buyCount = AllServiceUtil.getBuyCountServeice().selectBuyCount(lshop.getId() * 100 + 3);
					if (buyCount == null) {
						buyCount = new BuyCount();
						buyCount.setBid(lshop.getId() * 100 + 3);
						buyCount.setPtype(3);
						buyCount.setShopId(lshop.getId());
						buyCount.setShopType(lshop.getType());
						AllServiceUtil.getBuyCountServeice().insertBuyCount(buyCount);
					}
					lshop.setBuyCount(buyCount);
					shops.put(lshop.getId() + "", lshop);
				}
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return shops;
	}

	/**根据商店物品类型储存每个商店的物品*/
	public static Map<String, List<Shop>> getShop(ConcurrentHashMap<String,Shop> map){
		List<Shop> list=new ArrayList<>();
		S:for (Shop shop : map.values()) {
			int sid=Integer.parseInt(shop.getShopid());
			for (int i = 0,length=list.size(); i < length; i++) {
				Shop shop2=list.get(i);
				if (sid<Integer.parseInt(shop2.getShopid())) {
					list.add(i, shop);
					continue S;
				}
			}
			list.add(shop);
		}
		Map<String, List<Shop>> shopGoods = new HashMap<String, List<Shop>>();
		// 创建商店表
		for (Shop shop : list) {
			List<Shop> shops=shopGoods.get(shop.getShoptype()+"");
			if (shops==null) {
				shops=new ArrayList<>();
				shopGoods.put(shop.getShoptype()+"", shops);
			}
			shops.add(shop);
		}
		return shopGoods;
	}
	public static String getEShop(ConcurrentHashMap<String, Eshop> allShopGoodsMap){
		EshopBean eshopBean = new EshopBean();
		List<Eshop> eshops=new ArrayList<>();
		S:for (Eshop eshop:allShopGoodsMap.values()) {
			int id=Integer.parseInt(eshop.getEshopid());
			for (int i = 0,length=eshops.size(); i < length; i++) {
				int oid=Integer.parseInt(eshops.get(i).getEshopid());
				if (id<oid) {
					eshops.add(i,eshop);
					continue S;
				}
			}
			eshops.add(eshop);
		}
		eshopBean.setEshops(eshops);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(eshopBean);
		return msgString;
		
	}
}
