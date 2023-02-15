package org.come.readUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.GoodsBean;
import org.come.entity.Goodstable;
import org.come.handler.MainServerHandler;
import org.come.model.GoodModel;
import org.come.model.GoodsExchange;
import org.come.readBean.allGoodsExchange;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

/**
 * 批量导入物品
 * 
 * @author 叶豪芳
 * @date : 2017年11月28日 下午8:18:53
 */
public class ReadGoodsUtil {
	// 根据物品ID为所有物品标记组成map
	public static ConcurrentHashMap<BigDecimal, Goodstable> getAllGoodsMap(String path, StringBuffer buffer) {
		// 存放所有物品信息的集合
		ConcurrentHashMap<BigDecimal, Goodstable> allGoodsMap = new ConcurrentHashMap<BigDecimal, Goodstable>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
    	for (int i = 2; i < result.length; i++) {
    		if (result[i][0].equals("")) {continue;}
    		Goodstable good = new Goodstable();
    		for (int j = 0; j < 7; j++) {
				try {
					SettModelMemberTool.setReflect(good, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
    		allGoodsMap.put(good.getGoodsid(), good);
    	}
		return allGoodsMap;
	}
	public static String createGoods(ConcurrentHashMap<BigDecimal, Goodstable> map) {
		GoodsBean goodsBean = new GoodsBean();
		Map<BigDecimal, GoodModel> allGoodsMap=new HashMap<>();
		for (Goodstable good:map.values()) {
			GoodModel model=new GoodModel(good);
			allGoodsMap.put(model.getGoodsid(), model);
		}
		goodsBean.setAllGoodsMap(allGoodsMap);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(goodsBean);
		return msgString;
	}
	public static ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchangeMap(String path, StringBuffer buffer) {
		ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchange = new ConcurrentHashMap<Integer, GoodsExchange>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
			GoodsExchange goodsExchange=new GoodsExchange();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(goodsExchange,result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (goodsExchange.geteId()!=0) {
				Goodstable good= GameServer.getAllGoodsMap().get(goodsExchange.getGid());
				if (good==null) {
					buffer.append("未找到对应的GoodID:"+goodsExchange.getGid());
					return null;
				}
				String[] arr = goodsExchange.getConsume().split("\\|");
				List<Goodstable> goodstables = new ArrayList<>();
				for (int j = 0; j < arr.length; j++) {
					if (arr[j].startsWith("D"))continue;
					String str = arr[j].substring(2);
					BigDecimal id = BigDecimal.valueOf(Long.parseLong(str.split("=")[0]));
					Goodstable goodInfo=GameServer.getAllGoodsMap().get(id);
					goodstables.add(goodInfo);
				}
				goodsExchange.initGood(good, goodstables);
				allGoodsExchange.put(goodsExchange.geteId(), goodsExchange);
			}
		}
		return allGoodsExchange;
	}
	/**获取兑换表txt数据*/
	public static String createTxtGoodsExchange(ConcurrentHashMap<Integer, GoodsExchange> map){
		allGoodsExchange allGoodsExchange=new allGoodsExchange();
		allGoodsExchange.setAllGoodsExchange(map);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(allGoodsExchange);
		return msgString;
	}
}
