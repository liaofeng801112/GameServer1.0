package org.come.readBean;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.model.GoodsExchange;
//TODO 物品兑换
public class allGoodsExchange {
    private ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchange;

    public ConcurrentHashMap<Integer, GoodsExchange> getAllGoodsExchange() {
        return allGoodsExchange;
    }

    public void setAllGoodsExchange(ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchange) {
        this.allGoodsExchange = allGoodsExchange;
    }

}
