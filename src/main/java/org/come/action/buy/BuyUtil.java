package org.come.action.buy;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.BuyShopBean;
import org.come.bean.LoginResult;
import org.come.model.Eshop;
import org.come.model.Shop;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Battle.BattleMixDeal;

public class BuyUtil {
	//npc商店
	private static ConcurrentHashMap<String,ConcurrentHashMap<BigDecimal,Integer>> shops;
	//商城
	private static ConcurrentHashMap<String,ConcurrentHashMap<BigDecimal,Integer>> eshops;
	static{shops=new ConcurrentHashMap<>();eshops=new ConcurrentHashMap<>();}
	static String CHECKTS1=Agreement.getAgreement().PromptAgreement("达到最大购买次数");
	
	/**判断条件是否符合*/
	public static String isLimit(LoginResult loginResult,Shop shop,BuyShopBean buyShopBean){
		if (shop.getNum()==0) {return null;}
		ConcurrentHashMap<BigDecimal,Integer> map=shops.get(shop.getShopid());
		if (map==null) {
			map=new ConcurrentHashMap<>();
			shops.put(shop.getShopid(), map);
		}
		if (shop.getLvls()!=null) {
			String value=BattleMixDeal.isLvl(loginResult.getGrade(),shop.getLvls());
    		if (value!=null) {return value;}
		}
		Integer num=map.get(loginResult.getRole_id());
		if (num==null) {num=0;}
		if (num>=shop.getNum()) {return CHECKTS1;}
		if (num+buyShopBean.getSum()>shop.getNum()) {
			buyShopBean.setSum(shop.getNum()-num);
		}
		num+=buyShopBean.getSum();
		map.put(loginResult.getRole_id(),num);
		return null;
	}
	/**判断条件是否符合*/
	public static String isLimit(LoginResult loginResult,Eshop eshop,BuyShopBean buyShopBean){
		if (eshop.getNum()==0) {return null;}
		ConcurrentHashMap<BigDecimal,Integer> map=eshops.get(eshop.getEshopid());
		if (map==null) {
			map=new ConcurrentHashMap<>();
			eshops.put(eshop.getEshopid(), map);
		}
		if (eshop.getLvls()!=null) {
			String value=BattleMixDeal.isLvl(loginResult.getGrade(),eshop.getLvls());
    		if (value!=null) {return value;}
		}
		Integer num=map.get(loginResult.getRole_id());
		if (num==null) {num=0;}
		if (num>=eshop.getNum()) {return CHECKTS1;}
		if (num+buyShopBean.getSum()>eshop.getNum()) {
			buyShopBean.setSum(eshop.getNum()-num);
		}
		num+=buyShopBean.getSum();
		map.put(loginResult.getRole_id(),num);
		return null;
	}
	/**重置*/
	public static void reset(){
		eshops.clear();
		Iterator<String> it = shops.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Shop shop=GameServer.getAllShopGoods().get(key);
			if (shop==null) {
				it.remove();	
			}else if (shop.getShoptype()!=605) { 
				it.remove();
			}
	    }
	}
	//每次活动开启时候 重置大闹天宫兑换
	public static void resetShopType(int shopType){
		Iterator<String> it = shops.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Shop shop=GameServer.getAllShopGoods().get(key);
			if (shop!=null&&shop.getShoptype()==shopType) {
				it.remove();
			}
		}
	}
}
