package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.ImportantgoodssumrecordBean;
import org.come.entity.BuytypeEntity;
import org.come.entity.ImportantgoodsluEntity;
import org.come.entity.ImportantgoodssumrecordEntity;

/**
 * HGC-2019-11-19 重要物资汇总
 * 
 * @author Administrator
 * 
 */
public interface IImportantgoodtrcordService {

	/**
	 * 重要物资汇总条件搜索
	 * 
	 * @param time
	 * @param weekendsum
	 * @param page
	 * @return
	 */
	List<ImportantgoodssumrecordBean> selectImportantgoodsrecordList(String time, String weekendsum, int page);

	/**
	 * 重要物资汇总条件搜索对应物品
	 * 
	 * @param time
	 * @param weekendsum
	 * @return
	 */
	List<ImportantgoodssumrecordBean> selectImportantgoodsrecordGoods(BigDecimal goodsid);

	/**
	 * 监控物资设置条件搜索
	 * 
	 * @param goodsid
	 * @param goodsname
	 * @param page
	 * @return
	 */
	List<ImportantgoodsluEntity> selectImportantGoodsLuList(String goodsid, String goodsname, int page);

	/**
	 * 监控物资设置修改
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int updateImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 监控物资设置新增
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int addImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 监控物资设置删除
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int deleteImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 类型设置条件搜索
	 * 
	 * @param typename
	 * @param type
	 * @param page
	 * @return
	 */
	List<BuytypeEntity> selectBuyTypeList(String typename, String type, int page);

	/**
	 * 类型设置修改
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int updateBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 类型设置删除
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int deleteBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 类型设置新增
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int addBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 查询重要物资记录统计数量
	 * 
	 * @return
	 */
	List<ImportantgoodssumrecordEntity> selectImportantGoods();

	/**
	 * 添加重要物资记录统计数量
	 * 
	 * @param importantgoodssumrecordBean
	 * @return
	 */
	int addImporatantGoodsSum(ImportantgoodssumrecordEntity importantgoodssumrecordEntity);
	
	

	/** HGC-2019-12-06 */
	/** 添加表空间 */
	void addImporatantGoodsLuTableSpace(String time, String tableSpaceName,String path);

	/** HGC-2019-12-19 */
	/** 表添加表分区 */
	void addTableImporatantGoodsLuTableSpace(String time, String partitionName,String tableName);
	
	/** 查询表空间是否存在 */
	int selectTableSapce(String tableSpaceName);
	/** 查询表分区是否存在 */
	int selectTablePartition(String partitionName,String tableName);
}
