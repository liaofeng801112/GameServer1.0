package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.Goodsbuyrecordsumbean;
import org.come.entity.GoodsbuyrecordEntity;
import org.come.entity.GoodssaledayrecordEntity;
import org.come.entity.Goodstable;
import org.come.entity.ShangchengshopEntity;

public interface IGoodsTableService {
	// 获得所有角色下的所有物品
	List<Goodstable> getAllGoods();
	// 获得角色下的所有物品
	List<Goodstable> getGoodsByRoleID(BigDecimal role_id);
	// 添加物品
	void insertGoods( Goodstable goodstable );
	//修改物品信息  主要修改redis的索引   修改的物品   修改后的角色id
	void updateGoodsIndex( Goodstable goodstable ,BigDecimal role_id,BigDecimal goodsid,Integer status);
    //修改物品 物品数量只能减少或者不变
    String updateGoodsNum( Goodstable goodstable,int type );
	// 删除物品
	void deleteGoodsByRgid( BigDecimal rgid );
	// 获取单个物品信息
	Goodstable getGoodsByRgID( BigDecimal rgid );
	// 查询是否含有该件物品
	List<Goodstable> selectGoodsByRoleIDAndGoodsID( BigDecimal roleid, BigDecimal goodsid );
	// 查询是否含有该件物品 该物品处于指定状态
	List<Goodstable> selectGoodsByRoleIDAndGoodsIDAndState( BigDecimal roleid, BigDecimal goodsid,int state );
	
	BigDecimal selectMaxID();

	void insertGoodssql(Goodstable goodstable);
	void insertGoodssqlS(List<Goodstable> goods);

	
	void updateGoodssql(Goodstable goodstable);

	void deleteGoodsByRgidsql(BigDecimal rgid);
    /**修改刚从redis取出来的物品  里面包含删除判断*/
	void updateGoodRedis(Goodstable goodstable);
	
	/** HGC-2019-11-18 */
	/** 查询仙玉商品销售 */
	List<Goodsbuyrecordsumbean> selectXianYuGoodsbuy(String time, String goodsname, int pageNum, String type);

	/** 查询仙玉商品销售对应物品销售柱状图 */
	List<Goodsbuyrecordsumbean> selectXianYuGoodsbuyZhuZhuangTu(BigDecimal gid);

	/**
	 * 售卖物品监控条件搜索
	 * 
	 * @param goodsid
	 * @param goodsname
	 * @param page
	 * @return
	 */
	List<ShangchengshopEntity> selectShangChengShopList(String goodsid, String goodsname, int page);

	/**
	 * 售卖物品监控修改
	 * 
	 * @param shangchengshopEntity
	 * @return
	 */
	int updateShangChengShop(ShangchengshopEntity shangchengshopEntity);

	/**
	 * 售卖物品监控删除
	 * 
	 * @param shangchengshopEntity
	 * @return
	 */
	int deleteShangChengShop(ShangchengshopEntity shangchengshopEntity);

	/**
	 * 售卖物品监控新增
	 * 
	 * @param shangchengshopEntity
	 * @return
	 */
	int addShangChengShop(ShangchengshopEntity shangchengshopEntity);

	/**
	 * 查询商城销售记录表的每个物品的销售总量与总消耗
	 * 
	 * @return
	 */
	List<GoodssaledayrecordEntity> selectGoodsBuyRecordSumList();

	/**
	 * 添加商场购买记录
	 * 
	 * @param goodssaledayrecordEntity
	 * @return
	 */
	int addGoodssaledayrecord(GoodssaledayrecordEntity goodssaledayrecordEntity);
	
	/**
	 * 新增商城销售记录 zrikka
	 * @param goodsBuy
	 * @return
	 */
	int addGoodsBuyRecord(GoodsbuyrecordEntity goodsBuy);
	


    /** HGC-2020-01-17 */
    /** 批量修改 */
    int updateGoodssqlS(List<Goodstable> list);

    /** 批量删除 */
    void deleteGoodsByRgidsqlS(List<BigDecimal> rgid);
}
