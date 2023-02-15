package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.bean.Goodsbuyrecordsumbean;
import org.come.entity.GoodsbuyrecordEntity;
import org.come.entity.GoodssaledayrecordEntity;
import org.come.entity.Goodstable;
import org.come.entity.ShangchengshopEntity;

@MyBatisAnnotation
public interface GoodstableMapper {
	// 获得所有角色下的所有物品
	List<Goodstable> getAllGoods();
	
	// 获得角色下的所有物品
	List<Goodstable> getGoodsByRoleID(BigDecimal role_id);
	
	// 添加物品
	void insertGoods( Goodstable goodstable );
	void insertGoodssqlS(List<Goodstable> goods);

	// 修改物品信息
	void updateGoods( Goodstable goodstable );
	
	// 删除物品
	void deleteGoodsByRgid( BigDecimal rgid );
	
	// 获取单个物品信息
	Goodstable getGoodsByRgID( BigDecimal rgid );
	
	// 查询是否含有该件物品
	List<Goodstable> selectGoodsByRoleIDAndGoodsID(@Param("roleid") BigDecimal roleid, @Param("goodsid") BigDecimal goodsid );

	BigDecimal selectMaxID();
	
	
	/** HGC-2019-11-18 */
	/** 查询仙玉商品销售 */
	List<Goodsbuyrecordsumbean> selectXianYuGoodsbuy(@Param("time") String time, @Param("goodsname") String goodsname, @Param("type") String type);

	/** 查询仙玉商品销售对应物品销售柱状图 */
	List<Goodsbuyrecordsumbean> selectXianYuGoodsbuyZhuZhuangTu(BigDecimal gid);

	/**
	 * 售卖物品监控条件搜索
	 * 
	 * @param goodsid
	 * @param goodsname
	 * @return
	 */
	List<ShangchengshopEntity> selectShangChengShopList(@Param("goodsid") String goodsid, @Param("goodsname") String goodsname);

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
    /** 批量删除物品 */
    int deleteGoodsByRgids(List<BigDecimal> list);

    /** 批量修改物品 */
    int updateGoodsList(List<Goodstable> list);
	
}